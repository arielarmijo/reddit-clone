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
import tk.monkeycode.redditclone.model.dto.PostRequest;
import tk.monkeycode.redditclone.model.dto.PostResponse;
import tk.monkeycode.redditclone.service.PostService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
	
	private final PostService postService;
	
	@PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.save(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

}
