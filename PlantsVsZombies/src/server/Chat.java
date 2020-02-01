package server;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Chat {
  public static class Message {
    User sender;
    String text;

    public Message(User sender, String text) {
      this.sender = sender;
      this.text = text;
    }

    @Override
    public String toString() {
      return sender.getUsername() + "\n" + text + "\n";
    }
  }
  
  ArrayList<Message> messages = new ArrayList<>();

  public String toString() {
    return messages.stream().map(x->x.toString()).collect(Collectors.joining());
  }
}