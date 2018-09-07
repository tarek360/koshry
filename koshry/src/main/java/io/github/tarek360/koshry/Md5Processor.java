package io.github.tarek360.koshry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Processor {

  public static String toMd5(String text) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(text.getBytes());
    byte byteData[] = md.digest();

    //convert the byte to hex format method 1
    StringBuilder sb = new StringBuilder();
    for (byte aByteData : byteData) {
      sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
    }

    System.out.println("Digest(in hex format):: " + sb.toString());

    //convert the byte to hex format method 2
    StringBuilder hexString = new StringBuilder();
    for (byte aByteData : byteData) {
      String hex = Integer.toHexString(0xff & aByteData);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
