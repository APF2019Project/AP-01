package game;

import creature.being.plant.PlantDna;
import main.Pages;
import page.ActionButton;
import page.Button;
import page.LinkButton;
import page.Menu;
import page.Message;
import page.Page;
import util.Result;

public class GamePages {
    public static Page<GameResult> dayPage(PlantDna[] hand) {
        return new Page<GameResult>() {
            @Override
            public Result<GameResult> action() {
                return (new Menu<>(
                    new ActionButton<GameResult>("Show hand", () -> Message.show("hand is:"+hand)),
                    new LinkButton<>("End turn", Pages.notImplemented())
                )).action();
            }
        };
    };
}