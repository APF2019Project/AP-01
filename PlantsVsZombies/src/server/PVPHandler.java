package server;

import java.util.ArrayList;

public class PVPHandler {
  public static String handle(String url, ArrayList<String> body) {
    if (url.equals("list")){
      // inja liste bazia ro mide
    }
    if (url.equals("create")){
      // inja plantia mitoonan bazi besazan
      // bayad token va hando bedan
      return "OK";
    }
    if (url.equals("join")){
      // inja zombia mitoonan join shan
    }
    if (url.equals("get")){
      // inja hame mitoonan chizaye bazio begiran
      // ham baraye bazikonast ke hey seda konan bebinan yaroo che khabar shod
      // ham bara mellati ke mikhan bazio bebinan
    }
    if (url.equals("play")){
      // inja oonayi ke access daran tokeneshoono 
      // midan va ye harekat too bazi mizanan
    }
    return "404";
  }
}