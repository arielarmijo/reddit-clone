package tk.monkeycode.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.Subreddit;
import tk.monkeycode.redditclone.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
    
}
