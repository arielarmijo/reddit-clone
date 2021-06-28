package tk.monkeycode.redditclone.model.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
	
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;

}
