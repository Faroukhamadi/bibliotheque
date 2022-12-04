
package org.farouk_maram.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
  private Connection conn = null;
  private final String url = "jdbc:mysql://localhost:3306/bibliotheque";

  public Connection getConn() {
    return conn;
  }

  public void connect() throws SQLException {
    if (conn != null && !conn.isClosed() && conn.isValid(0)) {
      return;
    }
    System.out.println("Connecting to database...");
    Properties connectionProps = new Properties();

    Dotenv dotenv = Dotenv.configure().load();

    String userName = dotenv.get("DB_USERNAME");
    String password = dotenv.get("DB_PASSWORD");

    connectionProps.put("user", userName);
    connectionProps.put("password", password);

    conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/bibliotheque", connectionProps);

    System.out.println("Connected to database!");
  }

}
