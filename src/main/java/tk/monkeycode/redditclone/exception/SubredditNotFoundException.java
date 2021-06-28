package tk.monkeycode.redditclone.exception;

public class SubredditNotFoundException extends RedditException {

	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String message) {
		super(message);
	}
	
	public SubredditNotFoundException() {
		this("Subreddit not found.");
	}

	
}
