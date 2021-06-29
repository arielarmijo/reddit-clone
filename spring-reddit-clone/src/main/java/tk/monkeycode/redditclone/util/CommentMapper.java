package tk.monkeycode.redditclone.util;

import java.time.Instant;

import tk.monkeycode.redditclone.model.Comment;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.CommentDto;

public final class CommentMapper {

	public static Comment map(CommentDto commentDto, Post post, User user) {
		return Comment.builder()
					  .createdAt(Instant.now())
					  .text(commentDto.getText())
					  .post(post)
					  .user(user)
					  .build();
	}
	
	public static CommentDto mapToDto(Comment comment) {
		return CommentDto.builder()
					  .id(comment.getId())
					  .postId(comment.getPost().getId())
					  .createdAt(comment.getCreatedAt())
					  .text(comment.getText())
					  .userName(comment.getUser().getUsername())
					  .build();
	}
}
