package tk.monkeycode.redditclone.util;

import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.Vote;
import tk.monkeycode.redditclone.model.dto.VoteDto;

public final class VoteMapper {

	public static VoteDto mapToDto(Vote vote) {
		return VoteDto.builder()
					  .postId(vote.getPost().getId())
					  .voteType(vote.getVoteType())
			          .build();
	}
	
	public static Vote map(VoteDto voteDto, Post post, User user) {
		return Vote.builder()
				   .voteType(voteDto.getVoteType())
				   .post(post)
				   .user(user)
				   .build();
	}
	
}
