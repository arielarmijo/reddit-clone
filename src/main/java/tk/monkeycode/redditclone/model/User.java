package tk.monkeycode.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    //@NotEmpty(message = "First name is required")
    private String firstName;
    
    //@NotEmpty(message = "Last name is required")
    private String lastName;
    
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    
    private Instant created;
    
    private boolean enabled;

}
