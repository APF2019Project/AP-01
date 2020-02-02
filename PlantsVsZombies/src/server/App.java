package server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import account.Account;
import main.Program;

public class App {

  public static void main(String[] args) throws Exception {
    preload();
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", (t) -> {
      Scanner scanner = new Scanner(t.getRequestBody());
      String response = handle(t.getRequestURI().getPath(), scanner);
      System.out.println("response: "+response);
      scanner.close();
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    });
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  public static String getBackupPath(String file) {
    String workingDirectory;
    String OS = (System.getProperty("os.name")).toUpperCase();
    if (OS.contains("WIN")) {
      workingDirectory = System.getenv("AppData");
    } else {
      workingDirectory = System.getProperty("user.home");
      workingDirectory += "/Library/Application Support/PVZ4/";
    }
    return workingDirectory + file;
  }

  private static void preload() {
    File mainPath = new File(Program.getBackupPath(""));
    if (mainPath.exists()) {
      Account.recoverAll();
    } else {
      mainPath.mkdirs();
    }
  }

  static String handle(String url, Scanner body) {
    System.out.println("request: "+url);
    if (url.equals("/login")) {
      if (!body.hasNextLine()) return "BAD";
      String username = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String password = body.nextLine();
      String token = Account.athenticate(username, password);
      if (token != null) {
        return "OK\n"+token+"\n";
      }
      else {
        return "FAIL";
      }
    }
    if (url.equals("/createAccount")) {
      if (!body.hasNextLine()) return "BAD";
      String username = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String password = body.nextLine();
      if (Account.createSync(username, password)) {
        return "OK\n";
      }
      else{
        return "FAIL\n";
      }
    }
    if (url.equals("/getUser")) {
      if (!body.hasNextLine()) return "BAD";
      String username = body.nextLine();
      Account user = Account.getByUsername(username);
      if (user == null) return "FAIL404\n";
      return user.toBase64();
    }
    if (url.equals("/getAllUsers")) {
      return Account.all().stream()
        .map(x->x.getUsername() + "\n").collect(Collectors.joining());
    }
    /*if (url.equals("/getChat")) {
      if (!body.hasNextLine()) return "BAD";
      String token = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String username = body.nextLine();
      Account user = Account.getByToken(token);
      if (user == null) return "FAIL401\n";
      return user.getChat(username).toString();
    }
    if (url.equals("/sendMessage")) {
      if (!body.hasNextLine()) return "BAD";
      String token = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String username = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String text = body.nextLine();
      User user = User.getByToken(token);
      if (user == null) return "FAIL401\n";
      user.sendMessage(username, text);
      return "OK\n";
    }
    if (url.equals("/newPost")) {
      if (!body.hasNextLine()) return "BAD";
      String token = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String caption = body.nextLine();
      if (!body.hasNextLine()) return "BAD";
      String photo = body.nextLine();
      User user = User.getByToken(token);
      if (user == null) return "FAIL401\n";
      user.newPost(new Post(caption, photo));
      return "OK\n";
    }*/
    return "404\n";
  }

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

    }
  }

}