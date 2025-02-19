package team.jokimyoon.techmoa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCompanyRepository extends JpaRepository<PostCompany, Long> {
    Optional<PostCompany> findByIdAndIsUseTrue(Long id);
}
