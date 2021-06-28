package tk.monkeycode.redditclone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank(message = "Post Name cannot be empty or Null")
	private String name;
	
	private Instant createdDate;
	
	@Nullable
	private String url;
	
	@Lob
	@Nullable
	private String description;
	
	private Integer voteCount;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subreddit_id")
	private Subreddit subreddit;

}
