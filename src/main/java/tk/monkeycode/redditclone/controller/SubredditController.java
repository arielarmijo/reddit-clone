package tk.monkeycode.redditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.dto.SubredditDto;
import tk.monkeycode.redditclone.service.SubredditService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {
	
	private final SubredditService redditService;

	@PostMapping()
	public ResponseEntity<Subreddit> createSubreddit(@RequestBody SubredditDto subredditDto) {
		Subreddit subreddit = redditService.save(subredditDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(subreddit);
	}
	
	@GetMapping()
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		List<SubredditDto> subredditsDto = redditService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(subredditsDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
		SubredditDto subredditDto = redditService.getSubreddit(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditDto);
	}
	
}
