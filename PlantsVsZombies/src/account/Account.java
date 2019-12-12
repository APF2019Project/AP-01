package account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import main.Program;
import page.ErrorMessage;
import util.Result;
import util.Unit;

public class Account implements Serializable {
  private static final long serialVersionUID = -5582985394951882515L;
  private String username;
  private String passwordHash;
  private String passwordSalt;
  private static Map<String, Account> ALL = new HashMap<>();
  private static Account current;
  private static final String BACKUP_ADDRESS = Program.getBackupPath("account.ser");
  public static void backupAll() {
    try {
      FileOutputStream fileOut =
      new FileOutputStream(BACKUP_ADDRESS);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(ALL);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      ErrorMessage.from(i).action();
    }
  }

  @SuppressWarnings("unchecked")
  public static void recoverAll() {
    try {
      FileInputStream fileIn = new FileInputStream(BACKUP_ADDRESS);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      ALL = (HashMap<String, Account>) in.readObject();
      in.close();
      fileIn.close();
    } catch (Exception i) {
      ErrorMessage.from(i).action();
    }
  }

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

  public static Result<Unit> login(String username, String password) {
    Result<Unit> error = Result.error("invalid username or password");
    Account user = getByUsername(username);
    if (user == null) return error;
    if (!user.matchPassword(password)) return error;
    current = user;
    return Result.ok();
  }

  private boolean matchPassword(String password) {
    return this.passwordHash.equals(password);
  }
}