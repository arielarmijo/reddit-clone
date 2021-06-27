package tk.monkeycode.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tk.monkeycode.redditclone.model.Comment;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;

public interface CommnetRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
    
}
