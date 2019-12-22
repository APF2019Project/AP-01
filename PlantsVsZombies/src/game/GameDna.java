package game;

import line.Line;
import player.plant_player.PlantPlayer;
import player.zombie_player.ZombiePlayer;

import java.util.List;

public class GameDna {

    final GameMode gameMode;
    final PlantPlayer plantPlayer;
    final ZombiePlayer zombiePlayer;
    final List<Line> lines;

    GameDna(GameMode gameMode, PlantPlayer plantPlayer, ZombiePlayer zombiePlayer, List<Line> lines) {
        this.gameMode = gameMode;
        this.plantPlayer = plantPlayer;
        this.zombiePlayer = zombiePlayer;
        this.lines = lines;
    }
}
