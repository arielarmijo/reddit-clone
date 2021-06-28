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
import tk.monkeycode.redditclone.util.SubredditMapper;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	
	@Transactional
	public Subreddit save(SubredditDto subredditDto) {
		Subreddit subreddit = SubredditMapper.map(subredditDto);
		return subredditRepository.save(subreddit);
	}
	
	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
								  .stream()
								  .map(SubredditMapper::mapToDto)
								  .collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
												 .orElseThrow(() -> new RedditException("Subreddit not found."));
		return SubredditMapper.mapToDto(subreddit);
	}
	
	
	
}
