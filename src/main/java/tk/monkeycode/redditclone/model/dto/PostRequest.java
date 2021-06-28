package tk.monkeycode.redditclone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRequest {

	private Long postId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;
    
}
