package team.jokimyoon.techmoa.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team.jokimyoon.techmoa.global.security.oauth.Oauth2Provider;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.oauthId = :oauthId AND u.oauthProvider = :oauthProvider")
	Optional<User> findBy(String oauthId, Oauth2Provider oauthProvider);

	@Query("SELECT u FROM User u WHERE u.uuid = :uuid")
	Optional<User> findByUuid(String uuid);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.webHook")
	List<User> findAllFetchWebhook();

}
