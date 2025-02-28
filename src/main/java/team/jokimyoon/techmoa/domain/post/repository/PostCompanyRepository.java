package team.jokimyoon.techmoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;

public interface PostCompanyRepository extends JpaRepository<PostCompany, Long> {
}
