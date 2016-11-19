package com.chewbyte.geogab;

/**
 * Created by Chris on 17/09/2016.
 */
public class ChatMessage {
    public boolean left;

    public String getMessage() {
        return message;
    }

    public String message;
    public int ownerId;

    public ChatMessage(boolean left, int ownerId, String message) {
        super();
        this.left = left;
        this.ownerId = ownerId;
        this.message = message;
    }
}
