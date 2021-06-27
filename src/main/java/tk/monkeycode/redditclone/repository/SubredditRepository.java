package tk.monkeycode.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.monkeycode.redditclone.model.Subreddit;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
	
	Optional<Subreddit> findByName(String subredditName);

}
