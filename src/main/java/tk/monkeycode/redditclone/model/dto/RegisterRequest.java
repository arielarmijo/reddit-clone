package tk.monkeycode.redditclone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

	private String username;
	private String password;
	private String rePassword;
	private String email;
	
}
