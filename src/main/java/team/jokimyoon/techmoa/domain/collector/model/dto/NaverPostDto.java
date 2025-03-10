package team.jokimyoon.techmoa.domain.collector.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.jokimyoon.techmoa.domain.post.repository.entity.Post;
import team.jokimyoon.techmoa.domain.post.repository.entity.PostCompany;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NaverPostDto {

    private String title;
    private String content;
    private String link;
    private String id;
    private LocalDateTime updated;

    public Post toEntity(PostCompany postCompany) {
        return Post.builder()
                .title(title)
                .summary(content)
                .guid(id)
                .url(link)
                .publishedAt(updated)
                .postCompany(postCompany)
                .build();
    }
}