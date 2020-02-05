package server;

import java.util.ArrayList;

import account.Account;
import creature.being.BeingDna;
import creature.being.plant.PlantDna;
import creature.being.zombie.ZombieDna;

public class ShopHandler {
  public static String handle(String url, ArrayList<String> body) {
    if (url.equals("getDna")){
      return PlantDna.getAllBase64()+"\n"+ZombieDna.getAllBase64();
    }
    if (url.equals("buy")){
      Account user = Account.getByToken(body.get(0));
      if (user == null) return "FAIL401";
      BeingDna dna = BeingDna.getByName(body.get(1));
      if (dna == null) return "FAIL404";
      user.getStore().buyCard(dna);
      return "OK";
    }
    if (url.equals("join")){
      // inja zombia mitoonan join shan
    }
    return "404";
  }
}