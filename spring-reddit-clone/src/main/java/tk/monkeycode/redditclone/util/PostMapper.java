package tk.monkeycode.redditclone.util;

import java.time.Instant;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.PostRequest;
import tk.monkeycode.redditclone.model.dto.PostResponse;

@AllArgsConstructor
public final class PostMapper {
	
	public static PostResponse mapToDto(Post post, int commentCount) {
		Long timeAgo = Instant.now().toEpochMilli() - post.getCreatedAt().toEpochMilli();
		return PostResponse.builder()
						   .id(post.getId())
						   .postName(post.getName())
						   .url(post.getUrl())
						   .description(post.getDescription())
						   .userName(post.getUser().getUsername())
						   .subredditName(post.getSubreddit().getName())
						   .voteCount(post.getVoteCount())
						   .commentCount(commentCount)
						   .duration(TimeAgo.toRelative(timeAgo, 1))
						   .build();
	}
	
	public static Post map(PostRequest postRequest, Subreddit subreddit, User user) {
		return Post.builder()
				   .name(postRequest.getPostName())
				   .createdAt(Instant.now())
				   .url(postRequest.getUrl())
				   .description(postRequest.getDescription())
				   .voteCount(0)
				   .user(user)
				   .subreddit(subreddit)
				   .build();
	}
	
}
