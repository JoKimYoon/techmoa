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
public class TossPostDto {

    private String title;
    private String description;
    private String link;
    private String guid;
    private LocalDateTime pubDate;

    public Post toEntity(PostCompany postCompany) {
        return Post.builder()
                .title(title)
                .summary(description)
                .guid(guid)
                .url(link)
                .publishedAt(pubDate)
                .postCompany(postCompany)
                .build();
    }

}
