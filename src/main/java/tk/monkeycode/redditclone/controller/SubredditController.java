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
import tk.monkeycode.redditclone.model.dto.SubredditDto;
import tk.monkeycode.redditclone.service.SubredditService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddits")
public class SubredditController {
	
	private final SubredditService redditService;

	@PostMapping()
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(redditService.save(subredditDto));
	}
	
	@GetMapping()
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(redditService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(redditService.getSubreddit(id));
	}
	
}
