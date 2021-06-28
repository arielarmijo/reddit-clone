package tk.monkeycode.redditclone.exception;

public class UserNotFoundException extends RedditException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException() {
		this("User not found.");
	}

}
