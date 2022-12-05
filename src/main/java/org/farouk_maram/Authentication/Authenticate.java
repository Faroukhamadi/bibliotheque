package org.farouk_maram.Authentication;

public class Authenticate {
  private static Authenticate authenticateInstance;

  private static String username;

  private Authenticate(String username) {
    Authenticate.username = username;
  }

  public static Authenticate login(String username) {
    if (authenticateInstance == null) {
      authenticateInstance = new Authenticate(username);
    }
    return authenticateInstance;
  }

  public static void logout() {
    username = null;
  }

  public static boolean isLoggedIn() {
    return username != null;
  }

  public static String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return "username: " + getUsername();
  }

}