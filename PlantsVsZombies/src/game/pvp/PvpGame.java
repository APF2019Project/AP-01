package game.pvp;

import account.Account;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.Program;

import java.util.ArrayList;

public class PvpGame {
    private static final Integer randomizer = 1982043865;
    private static final ArrayList<PvpGame> allIds = new ArrayList<>();

    private final Integer id;
    private final String plant;
    private final String zombie;

    private ArrayList<ArrayList<String>> moves = new ArrayList<>();

    private GameState gameState;

    public PvpGame(String plant, String zombie) {
        this.plant = plant;
        this.zombie = zombie;
        gameState = GameState.WAITING_TO_JOIN;

        id = allIds.size() ^ randomizer;
        allIds.add(this);

    }

    public static HBox packet(String id, String plant, String zombie, String gameState) {

        String format = "    %-9s %5s %-7s %5s %-7s ";

        Text text = new Text();
        Button button = new Button();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        text.setFill(Color.WHITESMOKE);
        text.setFont(Font.font("monospaced", Program.screenX / 60));
        button.setTextAlignment(TextAlignment.CENTER);
        button.setStyle("-fx-background-color: transparent;");
        button.setTextFill(Color.WHITESMOKE);
        button.setFont(Font.font("monospaced", Program.screenX / 60));

        if (gameState == null) {
            text.setText(String.format(format, "game id", "", "plants", "", "zombies"));
            button.setVisible(false);
        } else {
            String username = Account.getCurrentAccount().getUsername();
            if (gameState.equals("WAITING_TO_JOIN")) {
                if (zombie.equals(username)) {
                    button.setText("join");
                    // TODO:
                } else {
                    button.setText("waiting");
                }
            } else if (gameState.equals("RUNNING_ZOMBIE") || gameState.equals("RUNNING_PLANTS") ||
                    gameState.equals("PLANTS_WIN") || gameState.equals("ZOMBIES_WIN")) {
                button.setText("watch");
                // TODO:
            } else if (gameState.equals("CANCELED")) {
                button.setText("canceled");
            }
            text.setText(String.format(format, id, "", plant, "", zombie));
        }

        hBox.getChildren().addAll(text, button);
        return hBox;
    }

    // body's first argument is username !!!
    public static String handle(String url, ArrayList<String> body) {
        if (url.equals("list")) {
            StringBuilder res = new StringBuilder();
            for (PvpGame game : allIds)
                res.append(game.toString()).append('\n');

            return res.toString();
        }
        if (url.equals("stateOfGame")) {
            Integer gameId = Integer.parseInt(body.get(1));
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    return game.gameState.toString();
                }
            return "No game with such ID found";
        }
        if (url.equals("create")) {
            if (Account.getByUsername(body.get(1)) == null)
                return "Not a valid username for zombie player";
            return new PvpGame(body.get(0), body.get(1)).id + "";
        }
        if (url.equals("cancel")) {
            Integer gameId = Integer.parseInt(body.get(1));
            String username = body.get(0);
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    if (game.plant.equals(username) || game.zombie.equals(username)) {
                        game.gameState = GameState.CANCELED;
                        return "OK";
                    }
                    return "Action not allowed";
                }
            return "No game with such ID found";
        }
        if (url.equals("join")) {
            Integer gameId = Integer.parseInt(body.get(1));
            String username = body.get(0);
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    if (game.gameState == GameState.WAITING_TO_JOIN && game.zombie.equals(username)) {
                        game.gameState = GameState.RUNNING_PLANTS;
                        return "OK";
                    }
                    return "Action not allowed";
                }
            return "No game with such ID found";
        }
        if (url.equals("get")) {
            Integer gameId = Integer.parseInt(body.get(1));
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    StringBuilder res = new StringBuilder();
                    for (ArrayList<String> arr : game.moves) {
                        for (String s : arr)
                            res.append(s).append("\t");
                        res.append("\n");
                    }
                    return res.toString();
                }
            return "No game with such ID found";
        }
        if (url.equals("getLast")) {
            Integer gameId = Integer.parseInt(body.get(1));
            String username = body.get(0);
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    StringBuilder res = new StringBuilder();
                    if (!game.moves.isEmpty()) {
                        for (String e : game.moves.get(game.moves.size() - 1)) {
                            res.append(e).append('\t');
                        }
                    }
                    if (username.equals(game.zombie)) {
                        if (game.gameState == GameState.RUNNING_ZOMBIE) {
                            return res.toString();
                        }
                        return "WAIT";
                    }
                    if (username.equals(game.plant)) {
                        if (game.gameState == GameState.RUNNING_PLANTS) {
                            return res.toString();
                        }
                        return "WAIT";
                    }
                    return res.toString();
                }
            return "No game with such ID found";
        }
        if (url.equals("play")) {
            String username = body.get(0);
            Integer gameId = Integer.parseInt(body.get(1));
            for (PvpGame game : allIds)
                if (gameId.equals(game.id)) {
                    if (username.equals(game.plant) || username.equals(game.zombie)) {
                        if (username.equals(game.zombie) && game.gameState == GameState.RUNNING_ZOMBIE)
                            game.gameState = GameState.RUNNING_PLANTS;
                        else if (username.equals(game.plant) && game.gameState == GameState.RUNNING_PLANTS)
                            game.gameState = GameState.RUNNING_ZOMBIE;
                        else
                            return "NOT YOUR TURN";
                        game.moves.add((ArrayList<String>) body.subList(2, body.size()));
                        if (game.moves.get(game.moves.size() - 1).get(0).equals("END")) {
                            game.gameState = game.moves.get(game.moves.size() - 1).get(1).equals("PLANTS") ?
                                    GameState.PLANTS_WIN : GameState.ZOMBIES_WIN;
                        }
                        return "OK";
                    }
                    return "Action not allowed";
                }
            return "No game with such ID found";
        }
        return "404";
    }

    @Override
    public String toString() {
        return id + "\t" + plant + "\t" + zombie + "\t" + gameState;
    }
}
