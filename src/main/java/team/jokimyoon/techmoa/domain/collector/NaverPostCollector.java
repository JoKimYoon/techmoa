package team.jokimyoon.techmoa.domain.collector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import team.jokimyoon.techmoa.domain.collector.model.dto.NaverPostDto;
import team.jokimyoon.techmoa.domain.collector.model.vo.CollectorName;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;
import team.jokimyoon.techmoa.global.util.RestClientUtil;
import team.jokimyoon.techmoa.global.util.StringUtil;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component(CollectorName.NAVER)
@RequiredArgsConstructor
public class NaverPostCollector implements PostCollector {

    private final PostRepository postRepository;
    private final PostCompanyRepository postCompanyRepository;
    private final RestClientUtil restClientUtil;

    @Async
    @Override
    public void collectPosts(String postCompanyUrl, Long postCompanyId) {

        String naverAtomData = restClientUtil.get(postCompanyUrl, String.class);

        PostCompany postCompany = postCompanyRepository.findById(postCompanyId)
                .orElseThrow(() -> new RuntimeException("Naver Company not found"));

        Map<String, NaverPostDto> naverPostDtoMap = parseNaverAtomData(naverAtomData);

        List<Post> postList = postRepository.findAllByPostCompany(postCompany);

        for (Post post : postList) {
            if (naverPostDtoMap.containsKey(post.getGuid())) {
                NaverPostDto naverPostDto = naverPostDtoMap.get(post.getGuid());

                if (!post.getTitle().equals(naverPostDto.getTitle())) {
                    post.changeTitle(naverPostDto.getTitle());
                }

                if (!post.getSummary().equals(naverPostDto.getContent())) {
                    post.changeSummary(naverPostDto.getContent());
                }

                if (!post.getUrl().equals(naverPostDto.getLink())) {
                    post.changeUrl(naverPostDto.getLink());
                }

                if (!post.getPublishedAt().equals(naverPostDto.getUpdated())) {
                    post.changePublishedAt(naverPostDto.getUpdated());
                }
            }
            naverPostDtoMap.remove(post.getGuid());
        }

        for (String key : naverPostDtoMap.keySet()) {
            postList.add(naverPostDtoMap.get(key).toEntity(postCompany));
        }

        postRepository.saveAll(postList);
    }

    public Map<String, NaverPostDto> parseNaverAtomData(String naverAtomData) {
        Map<String, NaverPostDto> naverPostDtoList = new HashMap<>();

        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document naverAtomDocument = saxBuilder.build(new StringReader(naverAtomData));

            Element root = naverAtomDocument.getRootElement();
            List<Element> entryList = root.getChildren("entry", root.getNamespace());

            for (Element entry : entryList) {
                String link = extractLink(entry);
                String title = extractTitle(entry);
                if (StringUtil.isNullOrEmpty(title) || StringUtil.isNullOrEmpty(link)) {
                    continue;
                }

                String content = extractContent(entry);
                if (StringUtil.isNullOrEmpty(content)) {
                    content = title;
                }

                LocalDateTime updated = extractUpdated(entry);

                String id = extractId(entry);
                if (StringUtil.isNullOrEmpty(id)) {
                    id = link;
                }

                NaverPostDto naverPostDto = NaverPostDto.builder()
                        .title(title)
                        .content(content)
                        .link(link)
                        .updated(updated)
                        .id(id)
                        .build();

                naverPostDtoList.put(naverPostDto.getId(), naverPostDto);
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return naverPostDtoList;
    }

    private String extractTitle(Element entry) {
        try {
            return entry.getChildText("title", entry.getNamespace());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return "";
        }
    }

    private String extractContent(Element entry) {
        try {
            return entry.getChildText("content", entry.getNamespace());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return "";
        }
    }

    private String extractLink(Element entry) {
        try {
            List<Element> links = entry.getChildren("link", entry.getNamespace());
            for (Element linkElement : links) {
                if ("alternate".equals(linkElement.getAttributeValue("rel"))) {
                    return linkElement.getAttributeValue("href");
                }
            }
            return "";
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return "";
        }
    }

    private LocalDateTime extractUpdated(Element entry) {
        try {
            String updatedRaw = entry.getChildText("updated", entry.getNamespace());
            return ZonedDateTime.parse(updatedRaw).toLocalDateTime();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return LocalDateTime.now();
        }
    }

    private String extractId(Element entry) {
        try {
            return entry.getChildText("id", entry.getNamespace());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return "";
        }
    }
}
