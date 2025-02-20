package team.jokimyoon.techmoa.domain.collector;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.post.model.Company;
import team.jokimyoon.techmoa.domain.post.repository.PostCompany;
import team.jokimyoon.techmoa.domain.post.repository.PostCompanyRepository;
import team.jokimyoon.techmoa.global.exception.BusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostCollectorScheduler {

	private final Map<String, PostCollector> postCollectorMap;
	private final PostCompanyRepository postCompanyRepository;

	@Scheduled(cron = "0 0 0 1 * *")
	public void collectPosts() {

		List<PostCompany> postCompanyList = postCompanyRepository.findAll();

		for (PostCompany postCompany : postCompanyList) {

			Company company = postCompany.getName();

			if (!postCollectorMap.containsKey(company.getCollectorName())) {
				throw new BusinessException("No Controller Found for Company: " + company.getCollectorName());
			}

			postCollectorMap.get(company.getCollectorName()).collectPosts(postCompany.getTargetUrl());
		}
	}
}
