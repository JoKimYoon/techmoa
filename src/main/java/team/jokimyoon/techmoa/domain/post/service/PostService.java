package team.jokimyoon.techmoa.domain.post.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.jokimyoon.techmoa.domain.post.model.dto.PostDto;
import team.jokimyoon.techmoa.domain.post.repository.PostRepository;
import team.jokimyoon.techmoa.global.model.SliceCustom;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public SliceCustom<PostDto> getPostList(LocalDate lastPublishedAt, int pageSize) {
		List<PostDto> postList = postRepository.findAllByPublishedAtDesc(lastPublishedAt, pageSize + 1);

		boolean hasNext = postList.size() > pageSize;

		return SliceCustom.<PostDto>builder()
			.data(postList)
			.hasNext(hasNext)
			.pageSize(pageSize)
			.build();
	}
}
