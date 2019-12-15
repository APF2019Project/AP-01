package game;

import exception.NotImplementedException;
import player.plant_player.PlantPlayer;
import player.zombie_player.ZombiePlayer;

public class GameDNA {

    final GameMode gameMode;
    final PlantPlayer plantPlayer;
    final ZombiePlayer zombiePlayer;

    public GameDNA(GameMode gameMode, PlantPlayer plantPlayer, ZombiePlayer zombiePlayer) throws NotImplementedException {

        this.gameMode = gameMode;
        this.plantPlayer = plantPlayer;
        this.zombiePlayer = zombiePlayer;

        throw new NotImplementedException("gameDNA");
    }
}
