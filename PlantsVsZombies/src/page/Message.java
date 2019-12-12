package page;

import main.Program;
import util.Result;
import util.Unit;

/**
 * show a message string
 */
public class Message implements Page<Unit> {

  private final String message;
  @Override
  public Result<Unit> action() {
    Program.clearScreen();
    System.out.println(message);
    System.out.println("Press enter to continue...");
    Program.scanner.nextLine();
    return Result.ok();
  }

  /**
   * @param message The message to be shown
   */
  public Message(String message) {
    this.message = message;
  }

}