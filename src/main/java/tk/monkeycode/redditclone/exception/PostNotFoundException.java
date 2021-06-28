package tk.monkeycode.redditclone.exception;

public class PostNotFoundException extends RedditException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String message) {
		super(message);
	}
	
	public PostNotFoundException() {
		this("Post not found.");
	}
}
