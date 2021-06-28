package tk.monkeycode.redditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tk.monkeycode.redditclone.exception.PostNotFoundException;
import tk.monkeycode.redditclone.exception.RedditException;
import tk.monkeycode.redditclone.model.Post;
import tk.monkeycode.redditclone.model.User;
import tk.monkeycode.redditclone.model.Vote;
import tk.monkeycode.redditclone.model.dto.VoteDto;
import tk.monkeycode.redditclone.repository.PostRepository;
import tk.monkeycode.redditclone.repository.VoteRepository;
import tk.monkeycode.redditclone.util.VoteMapper;

@Service
@AllArgsConstructor
public class VoteService {

	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;
	
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(PostNotFoundException::new);
		User user = authService.getCurrentUser();
		Optional<Vote> voteByPostAndUser = voteRepository.findFirstByPostAndUserOrderByIdDesc(post, user);
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new RedditException(String.format("you have already %s'd for this post.", voteDto.getVoteType()));
		}
		post.setVoteCount(post.getVoteCount() + voteDto.getVoteType().getDirection());
		voteRepository.save(VoteMapper.map(voteDto, post, user));
		postRepository.save(post);
	}

}
