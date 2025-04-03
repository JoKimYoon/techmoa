package team.jokimyoon.techmoa.domain.webhook.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.domain.user.repository.User;
import team.jokimyoon.techmoa.domain.user.repository.UserRepository;
import team.jokimyoon.techmoa.domain.user.repository.UserWebHook;
import team.jokimyoon.techmoa.domain.webhook.model.dto.DiscordNewPostDto;
import team.jokimyoon.techmoa.global.model.CronExpression;
import team.jokimyoon.techmoa.global.model.KoreanDay;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookScheduler {

	private final UserRepository userRepository;
	private final DiscordWebhookSender discordWebhookSender;
	private final SlackWebhookSender slackWebhookSender;
	private final PostRepository postRepository;

	@Scheduled(cron = CronExpression.EVERY_9_O_CLOCK)
	public void sendNewPosts() {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDateTime = now.minusDays(1).with(LocalTime.MIN);
		LocalDateTime endDateTime = now.minusDays(1).withHour(23).withMinute(23).withSecond(0).withNano(0);

		List<PostDto> postDtoList = postRepository.findAllByPublishedAtDesc(startDateTime, endDateTime);

		List<User> users = userRepository.findAllFetchWebhook();
		for (User user : users) {
			UserWebHook userWebHook = user.getWebHook();

			if (userWebHook == null) {
				continue;

			}

			if (userWebHook.getDiscord() != null) {
				sendDiscordWebhook(userWebHook.getDiscord(), postDtoList);
			}

			if (userWebHook.getSlack() != null) {
				sendSlackWebhook(userWebHook.getSlack(), postDtoList);
			}

		}

	}

	public void sendDiscordWebhook(String targetUrl, List<PostDto> newPostList) {

		List<DiscordNewPostDto.Embed> embeds = new ArrayList<>();
		for (PostDto postDto : newPostList) {
			DiscordNewPostDto.Embed embed = DiscordNewPostDto.Embed.builder()
				.title(postDto.getTitle())
				.description(postDto.getSummary())
				.url(postDto.getUrl())
				.author(DiscordNewPostDto.Embed.Author.builder()
					.name(postDto.getPostCompany().getName().toString())
					.iconUrl(postDto.getPostCompany().getIconImage())
					.build()
				).build();

			embeds.add(embed);
		}

		DiscordNewPostDto discordNewPostDto = DiscordNewPostDto.builder()
			.content(getToday() + " 오늘의 새로운 포스팅")
			.embeds(embeds)
			.build();

		discordWebhookSender.sendMessage(targetUrl, discordNewPostDto);
	}

	public void sendSlackWebhook(String targetUrl, List<PostDto> newPostList) {

	}

	private String getToday() {

		LocalDate today = LocalDate.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		String dayOfWeek = KoreanDay.valueOf(today.getDayOfWeek()).getKoreanDay();
		return today.format(dateFormatter) + " (" + dayOfWeek + ")";
	}
}
