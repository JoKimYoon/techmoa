package team.jokimyoon.techmoa.domain.collector;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.collector.model.dto.LinePostDto;
import team.jokimyoon.techmoa.domain.collector.model.vo.CollectorName;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;
import team.jokimyoon.techmoa.global.util.RestClientUtil;
import team.jokimyoon.techmoa.global.util.StringUtil;

@Slf4j
@Component(CollectorName.LINE)
@RequiredArgsConstructor
public class LinePostCollector implements PostCollector {

	private final PostRepository postRepository;
	private final PostCompanyRepository postCompanyRepository;
	private final RestClientUtil restClientUtil;

	private final DateTimeFormatter formatter
		= DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

	@Async
	@Override
	public void collectPosts(String postCompanyUrl, Long postCompanyId) {

		String lineRssData = restClientUtil.get(postCompanyUrl, String.class);

		PostCompany postCompany = postCompanyRepository.findById(postCompanyId)
			.orElseThrow(() -> new RuntimeException("Line Company not found"));

		Map<String, LinePostDto> linePostDtoMap = parseLineRssData(lineRssData);

		List<Post> postList = postRepository.findAllByPostCompany(postCompany);

		for (Post post : postList) {
			if (linePostDtoMap.containsKey(post.getGuid())) {
				LinePostDto linePostDto = linePostDtoMap.get(post.getGuid());

				if (!post.getTitle().equals(linePostDto.getTitle())) {
					post.changeTitle(linePostDto.getTitle());
				}

				if (!post.getSummary().equals(linePostDto.getDescription())) {
					post.changeSummary(linePostDto.getDescription());
				}

				if (!post.getUrl().equals(linePostDto.getLink())) {
					post.changeUrl(linePostDto.getLink());
				}

				if (!post.getPublishedAt().equals(linePostDto.getPubDate())) {
					post.changePublishedAt(linePostDto.getPubDate());
				}
			}
			linePostDtoMap.remove(post.getGuid());
		}

		for (String key : linePostDtoMap.keySet()) {
			postList.add(linePostDtoMap.get(key).toEntity(postCompany));
		}

		postList.sort(Comparator.comparing(Post::getPublishedAt).thenComparing(Post::getTitle));

		postRepository.saveAll(postList);
	}

	public Map<String, LinePostDto> parseLineRssData(String lineRssData) {

		Map<String, LinePostDto> linePostDtoList = new HashMap<>();

		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document lineRssDocument = saxBuilder.build(new StringReader(lineRssData));

			List<Element> itemList = lineRssDocument
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

				LocalDateTime pubDate = extractPubDate(item);

				String guid = extractGuid(item);
				if (StringUtil.isNullOrEmpty(guid)) {
					guid = link;
				}

				LinePostDto linePostDto = LinePostDto.builder()
					.title(title)
					.description(description)
					.link(link)
					.pubDate(pubDate)
					.guid(guid)
					.build();

				linePostDtoList.put(linePostDto.getGuid(), linePostDto);
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}

		return linePostDtoList;
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
