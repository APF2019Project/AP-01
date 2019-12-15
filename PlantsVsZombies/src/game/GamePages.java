package game;

import main.Pages;
import page.ActionButton;
import page.Button;
import page.LinkButton;
import page.Menu;
import page.Page;
import util.Result;

public class GamePages {
    public static Page<GameResult> dayPage = new Page<>() {

        @Override
        public Result<GameResult> action() {
            return (new Menu<>(
                new ActionButton<GameResult>("Show hand", () -> {}),
                new LinkButton<>("End turn", Pages.notImplemented())
            )).action();
        }
        
    };
}