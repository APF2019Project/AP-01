package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Serial {
  public static String toBase64(Serializable x) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      new ObjectOutputStream(baos).writeObject(x);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new String(Base64.getEncoder().encode(baos.toByteArray()));
  }
}