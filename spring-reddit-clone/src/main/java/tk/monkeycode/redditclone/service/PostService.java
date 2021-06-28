package tk.monkeycode.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.monkeycode.redditclone.exception.RedditException;
import tk.monkeycode.redditclone.exception.SubredditNotFoundException;
import tk.monkeycode.redditclone.exception.UserNotFoundException;
import tk.monkeycode.redditclone.model.Comment;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.PostRequest;
import tk.monkeycode.redditclone.model.dto.PostResponse;
import tk.monkeycode.redditclone.repository.CommentRepository;
import tk.monkeycode.redditclone.repository.PostRepository;
import tk.monkeycode.redditclone.repository.SubredditRepository;
import tk.monkeycode.redditclone.repository.UserRepository;
import tk.monkeycode.redditclone.util.PostMapper;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {
	
	private final UserRepository userRepository;
	private final SubredditRepository subredditRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final AuthService authService;
	
	@Transactional
	public PostResponse save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(SubredditNotFoundException::new);
		User currentUser = authService.getCurrentUser();
		Post post = postRepository.save(PostMapper.map(postRequest, subreddit, currentUser));
		List<Comment> comments = commentRepository.findByPost(post);
		log.info("Current user: ", currentUser.getUsername());
		return PostMapper.mapToDto(post, comments.size());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new RedditException("Post not found."));
		List<Comment> comments = commentRepository.findByPost(post);
		return PostMapper.mapToDto(post, comments.size());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(SubredditNotFoundException::new);
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
		return postRepository.findByUser(user).stream().map(this::mapToDto).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	private PostResponse mapToDto(Post post) {
		List<Comment> comments = commentRepository.findByPost(post);
		return PostMapper.mapToDto(post, comments.size());
	}

}
