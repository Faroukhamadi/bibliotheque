package org.farouk_maram.Authentication;

import java.util.HashMap;

public class Authenticate {
  private static Authenticate authenticateInstance;
  private static HashMap<String, String> userInformation = new HashMap<>();

  private Authenticate(String userId, String prenom) {
    userInformation.put("userId", userId);
    userInformation.put("prenom", prenom);
  }

  public static Authenticate login(String userId, String prenom) {
    if (authenticateInstance == null) {
      authenticateInstance = new Authenticate(userId, prenom);
    }
    return authenticateInstance;
  }

  public static void logout() {
    userInformation = null;
  }

  public static String getUserId() {
    return userInformation == null ? null : userInformation.get("userId");
  }

  public static String getPrenom() {
    return userInformation == null ? null : userInformation.get("prenom");
  }

  @Override
  public String toString() {
    return "userId: " + getUserId() + ", prenom: " + getPrenom();
  }

}