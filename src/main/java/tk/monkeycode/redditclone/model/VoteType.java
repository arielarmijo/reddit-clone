package tk.monkeycode.redditclone.model;

import java.util.Arrays;

import tk.monkeycode.redditclone.exception.VoteNotFoundException;

public enum VoteType {

	UPVOTE(1), DOWNVOTE(-1);
	
	private int direction;
	
    private VoteType(int direction) {
    	this.direction = direction;
    }
    
    public Integer getDirection() {
    	return direction;
    }
    
    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny().orElseThrow(VoteNotFoundException::new);
    }
    
}
