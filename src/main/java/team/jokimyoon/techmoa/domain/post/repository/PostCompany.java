package team.jokimyoon.techmoa.domain.post.repository;

import jakarta.persistence.*;
import lombok.*;
import team.jokimyoon.techmoa.global.model.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String iconImage;

    @Column
    private String crawlingUrl;

    @Column
    private int isUse;

    @Builder
    public PostCompany(String name, String iconImage, String crawlingUrl, int isUse) {
        this.name = name;
        this.iconImage = iconImage;
        this.crawlingUrl = crawlingUrl;
        this.isUse = isUse;
    }
}
