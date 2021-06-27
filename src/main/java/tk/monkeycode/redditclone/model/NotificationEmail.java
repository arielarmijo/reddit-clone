package tk.monkeycode.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationEmail {
	
	private String subject;
    private String recipient;
    private String body;

}
