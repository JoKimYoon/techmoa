package team.jokimyoon.techmoa.domain.collector;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import team.jokimyoon.techmoa.domain.collector.model.dto.TossPostDto;
import team.jokimyoon.techmoa.domain.collector.model.vo.CollectorName;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;
import team.jokimyoon.techmoa.global.util.RestClientUtil;
import team.jokimyoon.techmoa.global.util.StringUtil;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component(CollectorName.TOSS)
@RequiredArgsConstructor
public class TossPostCollector implements PostCollector {

	private final PostRepository postRepository;
	private final PostCompanyRepository postCompanyRepository;
	private final RestClientUtil restClientUtil;

	private final DateTimeFormatter formatter
			= DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

	@Async
	@Override
	public void collectPosts(String postCompanyUrl, Long postCompanyId) {

		String tossRssData = restClientUtil.get(postCompanyUrl, String.class);

		PostCompany postCompany = postCompanyRepository.findById(postCompanyId)
				.orElseThrow(() -> new RuntimeException("Toss Company not found"));

		Map<String, TossPostDto> tossPostDtoMap = parseTossRssData(tossRssData);

		List<Post> postList = postRepository.findAllByPostCompany(postCompany);

		for (Post post : postList) {
			if (tossPostDtoMap.containsKey(post.getGuid())) {
				TossPostDto tossPostDto = tossPostDtoMap.get(post.getGuid());

				if (!post.getTitle().equals(tossPostDto.getTitle())) {
					post.changeTitle(tossPostDto.getTitle());
				}

				if (!post.getSummary().equals(tossPostDto.getDescription())) {
					post.changeSummary(tossPostDto.getDescription());
				}

				if (!post.getUrl().equals(tossPostDto.getLink())) {
					post.changeUrl(tossPostDto.getLink());
				}

				if (!post.getPublishedAt().equals(tossPostDto.getPubDate())) {
					post.changePublishedAt(tossPostDto.getPubDate());
				}
			}
			tossPostDtoMap.remove(post.getGuid());
		}

		for (String key : tossPostDtoMap.keySet()) {
			postList.add(tossPostDtoMap.get(key).toEntity(postCompany));
		}

		postRepository.saveAll(postList);
	}

	public Map<String, TossPostDto> parseTossRssData(String tossRssData) {

		Map<String, TossPostDto> tossPostDtoList = new HashMap<>();

		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document tossRssDocument = saxBuilder.build(new StringReader(tossRssData));

			List<Element> itemList = tossRssDocument
					.getRootElement()
					.getChild("channel")
					.getChildren("item"	);

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

				LocalDateTime pubDate = extractPubDate(item);

				String guid = extractGuid(item);
				if (StringUtil.isNullOrEmpty(guid)) {
					guid = link;
				}

				TossPostDto tossPostDto = TossPostDto.builder()
						.title(title)
						.description(description)
						.link(link)
						.pubDate(pubDate)
						.guid(guid)
						.build();

				tossPostDtoList.put(tossPostDto.getGuid(), tossPostDto);
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}

		return tossPostDtoList;
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

	private LocalDateTime extractPubDate(Element item) {
		try {
			String pubDateRaw = item.getChild("pubDate").getContent().getFirst().getValue();
			return ZonedDateTime.parse(pubDateRaw, formatter).toLocalDateTime();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return LocalDateTime.now();
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
