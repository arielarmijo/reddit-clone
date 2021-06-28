package tk.monkeycode.redditclone.exception;

public class RefreshTokenNotFoundException extends RedditException {

	private static final long serialVersionUID = 1L;

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}

	public RefreshTokenNotFoundException(String message, Exception exception) {
		super(message, exception);
	}
	
	public RefreshTokenNotFoundException() {
		this("Refresh Token not found.");
	}

}
