package tk.monkeycode.redditclone.model.dto;

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
public class SubredditDto {

	private Long id;
	private String name;
	private String description;
	private Integer numberOfPost;
}
