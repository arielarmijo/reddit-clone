package tk.monkeycode.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.exception.RedditException;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.dto.SubredditDto;
import tk.monkeycode.redditclone.repository.SubredditRepository;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	
	@Transactional
	public Subreddit save(SubredditDto subredditDto) {
		Subreddit subreddit = mapSubredditDto(subredditDto);
		return subredditRepository.save(subreddit);
	}
	
	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
								  .stream()
								  .map(this::mapSubreddit)
								  .collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
												 .orElseThrow(() -> new RedditException("Subreddit not found."));
		return mapSubreddit(subreddit);
	}
	
	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
		return Subreddit.builder()
						.name(subredditDto.getName())
						.description(subredditDto.getDescription())
						.build();
	}
	
	private SubredditDto mapSubreddit(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName())
			               .id(subreddit.getId())
			               .numberOfPost(subreddit.getPosts().size())
			               .build();
	}
	
}
