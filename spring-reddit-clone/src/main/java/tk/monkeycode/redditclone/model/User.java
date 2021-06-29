package tk.monkeycode.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
    @NotBlank(message = "Username is required")
    private String username;
    
    @JsonIgnore
    @NotBlank(message = "Password is required")
    private String password;
    
    //@NotEmpty(message = "First name is required")
    private String firstName;
    
    //@NotEmpty(message = "Last name is required")
    private String lastName;
    
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    
    private Instant createdAt;
    
    @JsonIgnore
    private boolean enabled;

}
