package tk.monkeycode.redditclone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.monkeycode.redditclone.model.VoteType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VoteDto {

	private Long postId;
	private VoteType voteType;
	
}
