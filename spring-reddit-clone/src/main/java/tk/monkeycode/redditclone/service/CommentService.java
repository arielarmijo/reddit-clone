package tk.monkeycode.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.exception.PostNotFoundException;
import tk.monkeycode.redditclone.exception.UserNotFoundException;
import tk.monkeycode.redditclone.model.Comment;
import tk.monkeycode.redditclone.model.NotificationEmail;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.CommentDto;
import tk.monkeycode.redditclone.repository.CommentRepository;
import tk.monkeycode.redditclone.repository.PostRepository;
import tk.monkeycode.redditclone.repository.UserRepository;
import tk.monkeycode.redditclone.util.CommentMapper;

@Service
@AllArgsConstructor
public class CommentService {
	
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final EmailService emailService;
	
	@Transactional
	public Comment save(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(PostNotFoundException::new);
		User user = authService.getCurrentUser();
		Comment comment = CommentMapper.map(commentDto, post, user);
		comment = commentRepository.save(comment);
		NotificationEmail email = NotificationEmail.builder()
				.recipient(user.getEmail())
				.subject("Your post have been commented.")
				.body(String.format("%s have commented your post %s", user.getUsername(), post.getName()))
				.build();
		emailService.sendMail(email);
		return comment;
	}
	
	@Transactional(readOnly = true)
	public List<CommentDto> getAllCommentsByPostId(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
		List<Comment> comments = commentRepository.findByPost(post);
		return comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<CommentDto> getAllCommentsByUserName(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
		List<Comment> comments = commentRepository.findAllByUser(user);
		return comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
	}

}
