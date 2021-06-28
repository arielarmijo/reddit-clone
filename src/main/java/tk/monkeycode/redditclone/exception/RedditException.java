package tk.monkeycode.redditclone.exception;

public class RedditException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RedditException(String message) {
		super(message);
	}
	
	public RedditException(String message, Exception exception) {
        super(message, exception);
    }
	
}
