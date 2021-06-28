package tk.monkeycode.redditclone.exception;

public class VoteNotFoundException extends RedditException {
	
	private static final long serialVersionUID = 1L;

	public VoteNotFoundException(String message) {
		super(message);
	}
	
	public VoteNotFoundException() {
		this("Vote not found.");
	}

}
