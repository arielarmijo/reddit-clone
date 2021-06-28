package tk.monkeycode.redditclone.util;

import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.dto.SubredditDto;

public final class SubredditMapper {
	
	public static SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName())
			               .id(subreddit.getId())
			               .numberOfPost(subreddit.getPosts().size())
			               .build();
	}
	
	public static Subreddit map(SubredditDto subredditDto) {
		return Subreddit.builder()
						.name(subredditDto.getName())
						.description(subredditDto.getDescription())
						.build();
	}
	
}
