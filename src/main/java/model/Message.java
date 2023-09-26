package model;

import java.io.Serializable;

public class Message implements Serializable {
    private static int IdCounter = 0;
    public int Id;
    public String text;
    public User sender;

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
        this.Id = IdCounter;
        IdCounter++;
    }
}
