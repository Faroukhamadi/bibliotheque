package org.farouk_maram.Authentication;

public class Authenticate {
  private static Authenticate authenticateInstance;

  private static String userId;
  private static String prenom;

  private Authenticate(String userId, String prenom) {
    Authenticate.userId = userId;
    Authenticate.prenom = prenom;
  }

  public static Authenticate login(String userId, String prenom) {
    if (authenticateInstance == null) {
      authenticateInstance = new Authenticate(userId, prenom);
    }
    return authenticateInstance;
  }

  public static void logout() {
    userId = null;
    prenom = null;
  }

  public static boolean isLoggedIn() {
    return userId != null && prenom != null;
  }

  public static String getUserId() {
    return userId;
  }

  public static String getPrenom() {
    return prenom;
  }

  @Override
  public String toString() {
    return "userId: " + getUserId() + ", prenom: " + getPrenom();
  }

}