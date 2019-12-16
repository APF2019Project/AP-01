package game;

import creature.being.plant.PlantDna;
import main.Pages;
import page.*;
import util.Result;

public class GamePages {
    public static Page<GameResult> dayPage(PlantDna[] hand) {
        return new Page<GameResult>() {
            @Override
            public Result<GameResult> action() {
                // gameEngine ro new nakonin jayee, hamun new game ina ro seda bezanin !!!!!!!!!!!!!!!!!
                // GameEngine gameEngine = new GameEngine();
                return (new Menu<>(
                    new ActionButton<GameResult>("Show hand", () -> Message.show("hand is:"+hand)),
                    new LinkButton<>("End turn", Pages.notImplemented())
                )).action();
            }
        };
    }
}