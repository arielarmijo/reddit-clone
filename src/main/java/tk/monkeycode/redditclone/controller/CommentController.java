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
import tk.monkeycode.redditclone.model.dto.CommentDto;
import tk.monkeycode.redditclone.service.CommentService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
		commentService.save(commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentForPost(@PathVariable Long postId) {
		List<CommentDto> commentsDto = commentService.getAllCommentsForPost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
	}
	
	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentDto>> getAllCommentForUser(@PathVariable String userName) {
		List<CommentDto> commentsDto = commentService.getAllCommentsForUserName(userName);
		return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
	}

}
