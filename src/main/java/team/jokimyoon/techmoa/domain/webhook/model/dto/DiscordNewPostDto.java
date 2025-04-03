package team.jokimyoon.techmoa.domain.webhook.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscordNewPostDto {

	private String content;
	private List<Embed> embeds;
	@Builder.Default
	private String username = "TechMoa";

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Embed {
		private String title;
		private String description;
		private String url;
		@Builder.Default
		private String color = null;
		private Author author;

		@Data
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Author {
			private String name;
			@JsonProperty("icon_url")
			private String iconUrl;
		}

	}
}
