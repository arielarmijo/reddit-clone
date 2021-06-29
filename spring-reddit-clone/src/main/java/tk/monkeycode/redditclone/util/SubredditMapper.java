package tk.monkeycode.redditclone.util;

import java.time.Instant;
import java.util.List;

import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.dto.SubredditDto;

public final class SubredditMapper {
	
	public static SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName())
			               .id(subreddit.getId())
			               .numberOfPost(getNumberOfPost(subreddit.getPosts()))
			               .description(subreddit.getDescription())
			               .build();
	}
	
	public static Subreddit map(SubredditDto subredditDto, User user) {
		return Subreddit.builder()
						.name(subredditDto.getName())
						.description(subredditDto.getDescription())
						.createdAt(Instant.now())
						.user(user)
						.build();
	}
	
	private static Integer getNumberOfPost(List<Post> posts) {
		if (posts != null) {
			return posts.size();
		}
		return 0;
	}
	
}
