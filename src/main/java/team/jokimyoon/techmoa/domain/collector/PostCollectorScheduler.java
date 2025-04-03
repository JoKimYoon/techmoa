package team.jokimyoon.techmoa.domain.collector;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.post.model.vo.Company;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;
import team.jokimyoon.techmoa.global.model.CronExpression;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostCollectorScheduler {

	private final Map<String, PostCollector> postCollectorMap;
	private final PostCompanyRepository postCompanyRepository;
	private final PostRepository postRepository;

	@Scheduled(cron = CronExpression.EVERY_0_O_CLOCK)
	public void collectPosts() {

		List<PostCompany> postCompanyList = postCompanyRepository.findAll();

		for (PostCompany postCompany : postCompanyList) {

			Company company = postCompany.getName();

			if (!postCollectorMap.containsKey(company.getCollectorName())) {
				log.warn("No Controller Found for Company : {}", company.getCollectorName());
				//Todo: 디스코드로 메세지 출력
			}

			postCollectorMap.get(company.getCollectorName())
				.collectPosts(postCompany.getTargetUrl(), postCompany.getId());
		}
	}

	@Scheduled(cron = CronExpression.EVERY_1_O_CLOCK)
	public void insertToElasticsearch() {
		postRepository.deleteAllPostInElasticSearch();
		List<Post> postList = postRepository.findAll();
		postRepository.saveAllPostInElasticSearch(postList);
	}
}
