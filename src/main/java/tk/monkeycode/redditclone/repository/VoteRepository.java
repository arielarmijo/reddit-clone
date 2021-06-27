package tk.monkeycode.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	Optional<Vote> findFirstByPostAndUserOrderByIdDesc(Post post, User currentUser);

}
