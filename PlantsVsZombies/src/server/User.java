package server;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class User {
  public static final Map<String, User> BY_USERNAME = new HashMap<>();
  public static final Map<String, User> BY_TOKEN = new HashMap<>();

  private String username, password, profile, token = null;
  private ArrayList<Post> posts = new ArrayList<>();
  private Map<String, Chat> chats = new HashMap<>();

  private User(String username, String password, String profile) {
    this.username = username;
    this.password = password;
    this.profile = profile;
    BY_USERNAME.put(this.username, this);
  }

  public void sendMessage(String username, String text) {
    Chat chat = getChat(username);
    chat.messages.add(new Chat.Message(this, text));
  }

  public Chat getChat(String username) {
    if (!this.chats.containsKey(username)) {
      return createChat(this, getByUsername(username));
    }
    return this.chats.get(username);
  }

  private static Chat createChat(User user1, User user2) {
    Chat chat = new Chat();
    user1.chats.put(user2.username, chat);
    user2.chats.put(user1.username, chat);
    return chat;
  }

  public static boolean isStrongPassword(String password) {
    boolean haveBig = false, haveSmall = false, haveNumber = false;
    for (char c: password.toCharArray()) {
      if (c >= 'A' && c <= 'Z') haveBig = true;
      if (c >= 'a' && c <= 'z') haveSmall = true;
      if (c >= '0' && c <= '9') haveNumber = true;
    }
    return haveBig && haveNumber && haveSmall;
  }

  public static boolean createAccount(String username, String password, String profile) {
    if (BY_USERNAME.containsKey(username)) return false;
    new User(username, password, profile);
    return true;
  }

  public static String athenticate(String username, String password) {
    if (!BY_USERNAME.containsKey(username)) return null;
    User user = BY_USERNAME.get(username);
    if (user.password.equals(password)) {
      return user.generateToken();
    }
    return null;
  }

  public void newPost(Post post) {
    posts.add(post);
  }

  public String getProfile() {
    return this.profile+"\n"+this.posts.stream().map(x->x.toString()).collect(Collectors.joining());
  }

  static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  static SecureRandom rnd = new SecureRandom();
  
  String randomString( int len ){
     StringBuilder sb = new StringBuilder( len );
     for( int i = 0; i < len; i++ ) 
        sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
     return sb.toString();
  }
  
  private String generateToken() {
    if (this.token != null) {
      BY_TOKEN.remove(this.token);
    }
    this.token = randomString(100);
    BY_TOKEN.put(this.token, this);
    return this.token;
  }

  public static User getByUsername(String username) {
    return BY_USERNAME.get(username);
  }

  public static User getByToken(String token) {
    return BY_TOKEN.get(token);
  }

  public static Collection<User> all() {
    return BY_USERNAME.values();
  }

  public String getUsername() {
    return username;
  }
}