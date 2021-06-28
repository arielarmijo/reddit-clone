package tk.monkeycode.redditclone.util;

import java.time.Instant;

import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.PostRequest;
import tk.monkeycode.redditclone.model.dto.PostResponse;

public final class PostMapper {

	public static PostResponse mapToDto(Post post) {
		return PostResponse.builder()
						   .id(post.getId())
						   .postName(post.getName())
						   .description(post.getDescription())
						   .url(post.getUrl())
						   .subredditName(post.getSubreddit().getName())
						   .userName(post.getUser().getUsername())
						   .build();
	}
	
	public static Post map(PostRequest postRequest, Subreddit subreddit, User user) {
		return Post.builder()
				   .createdDate(Instant.now())
				   .subreddit(subreddit)
				   .name(postRequest.getPostName())
				   .user(user)
				   .description(postRequest.getDescription())
				   .build();
	}
}
