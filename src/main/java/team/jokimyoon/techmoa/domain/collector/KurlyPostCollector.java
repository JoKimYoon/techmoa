package team.jokimyoon.techmoa.domain.collector;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.collector.model.dto.KurlyPostDto;
import team.jokimyoon.techmoa.domain.collector.model.vo.CollectorName;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;
import team.jokimyoon.techmoa.global.util.RestClientUtil;
import team.jokimyoon.techmoa.global.util.StringUtil;

@Slf4j
@Component(CollectorName.KURLY)
@RequiredArgsConstructor
public class KurlyPostCollector implements PostCollector {

	private final PostRepository postRepository;
	private final PostCompanyRepository postCompanyRepository;
	private final RestClientUtil restClientUtil;

	private final DateTimeFormatter formatter
		= DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

	@Async
	@Override
	@Transactional
	public void collectPosts(String postCompanyUrl, Long postCompanyId) {

		String kurlyRssData = restClientUtil.get(postCompanyUrl, String.class);

		PostCompany postCompany = postCompanyRepository.findById(postCompanyId)
			.orElseThrow(() -> new RuntimeException("Kurly Company not found"));

		Map<String, KurlyPostDto> kurlyPostDtoMap = parseKurlyRssData(kurlyRssData);

		List<Post> postList = postRepository.findAllByPostCompany(postCompany);

		for (Post post : postList) {
			if (kurlyPostDtoMap.containsKey(post.getGuid())) {
				KurlyPostDto kurlyPostDto = kurlyPostDtoMap.get(post.getGuid());

				if (!post.getTitle().equals(kurlyPostDto.getTitle())) {
					post.changeTitle(kurlyPostDto.getTitle());
				}

				if (!post.getSummary().equals(kurlyPostDto.getDescription())) {
					post.changeSummary(kurlyPostDto.getDescription());
				}

				if (!post.getUrl().equals(kurlyPostDto.getLink())) {
					post.changeUrl(kurlyPostDto.getLink());
				}

				if (!post.getPublishedAt().equals(kurlyPostDto.getPubDate())) {
					post.changePublishedAt(kurlyPostDto.getPubDate());
				}
			}
			kurlyPostDtoMap.remove(post.getGuid());
		}

		for (String key : kurlyPostDtoMap.keySet()) {
			postList.add(kurlyPostDtoMap.get(key).toEntity(postCompany));
		}

		postRepository.saveAll(postList);

	}

	public Map<String, KurlyPostDto> parseKurlyRssData(String kurlyRssData) {

		Map<String, KurlyPostDto> kurlyPostDtoList = new HashMap<>();

		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document kurlyRssDocument = saxBuilder.build(new StringReader(kurlyRssData));

			List<Element> itemList = kurlyRssDocument
				.getRootElement()
				.getChild("channel")
				.getChildren("item");

			for (Element item : itemList) {

				String link = extractLink(item);
				String title = extractTitle(item);
				if (StringUtil.isNullOrEmpty(title) || StringUtil.isNullOrEmpty(link)) {
					continue;
				}

				String description = extractDescription(item);
				if (StringUtil.isNullOrEmpty(description)) {
					description = title;
				}

				LocalDate pubDate = extractPubDate(item);

				String guid = extractGuid(item);
				if (StringUtil.isNullOrEmpty(guid)) {
					guid = link;
				}

				KurlyPostDto kurlyPostDto = KurlyPostDto.builder()
					.title(title)
					.description(description)
					.link(link)
					.pubDate(pubDate)
					.guid(guid)
					.build();

				kurlyPostDtoList.put(kurlyPostDto.getGuid(), kurlyPostDto);
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}

		return kurlyPostDtoList;
	}

	private String extractTitle(Element item) {
		try {
			return item.getChild("title").getContent().getFirst().getValue();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return "";
		}
	}

	private String extractDescription(Element item) {
		try {
			return item.getChild("description").getContent().getFirst().getValue();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return "";
		}
	}

	private String extractLink(Element item) {
		try {
			return item.getChild("link").getContent().getFirst().getValue();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return "";
		}
	}

	private LocalDate extractPubDate(Element item) {
		try {
			String pubDateRaw = item.getChild("pubDate").getContent().getFirst().getValue();
			LocalDate pubDate;
			return ZonedDateTime.parse(pubDateRaw, formatter).toLocalDate();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return LocalDate.now();
		}
	}

	private String extractGuid(Element item) {
		try {
			return item.getChild("guid").getContent().getFirst().getValue();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return "";
		}
	}

}
