package account;

import java.util.HashMap;
import java.util.Map;

import util.Result;
import util.Unit;

public class Account {
  private String username;
  private String passwordHash;
  private String passwordSalt;
  private static Map<String, Account> ALL = new HashMap<>();

  private Account(String username, String password) {
    this.username = username;
    this.passwordHash = password;
    ALL.put(username, this);
  }
  
  public static Account getByUsername(String username) {
    return ALL.get(username);
  }

  public static Result<Unit> create(String username, String password) {
    if (getByUsername(username) != null) return Result.error("invalid username");
    new Account(username, password);
    return Result.ok();
  }
}