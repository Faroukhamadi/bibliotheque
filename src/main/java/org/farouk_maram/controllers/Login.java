package org.farouk_maram.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.farouk_maram.App;
import org.farouk_maram.Authentication.Authenticate;
import org.farouk_maram.db.Database;
import org.farouk_maram.utils.SubmitFailAlert;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.ValidationResult;
import net.synedra.validatorfx.Validator;

public class Login extends App {
  private Validator validator = new Validator();

  public Login(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    StackPane stackPane = new StackPane();

    Scene scene = new Scene(stackPane, 640, 480);

    GridPane grid = new GridPane();

    stage.setTitle("Login");
    grid.setAlignment(javafx.geometry.Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(5, 5, 5, 5));

    Text scenetitle = new Text("Login");
    scenetitle.setFont(Font.font("Sans-serif", 100));

    Label usernameLabel = new Label("Username");
    TextField usernameField = new TextField();

    Label passwordLabel = new Label("Password");
    PasswordField passwordField = new PasswordField();

    Tooltip tooltip = new Tooltip();
    tooltip.setShowDelay(Duration.ZERO);

    Button loginButton = new Button("Login");

    Hyperlink registerLink = new Hyperlink("Is this your first time here? Register now!");

    // password check
    validator.createCheck()
        .dependsOn("password", passwordField.textProperty())
        .withMethod(c -> {
          String password = c.get("password");
          if (!isValidPassword(password)) {
            tooltip.setText("Password must contain digits and numbers and be at least 8 characters long");
            Tooltip.install(passwordField, tooltip);
            c.error("Password must contain digits and numbers and be at least 8 characters long");
          } else {
            Tooltip.uninstall(passwordField, tooltip);
          }
        }).decorates(passwordField)
        .immediate();

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ValidationResult result = validator.getValidationResult();
        if (result.getMessages().size() > 0) {
          SubmitFailAlert alert = new SubmitFailAlert();
          alert.showAlert("Login");
          return;
        }
        System.out.println("Login button clicked");
        Database db = new Database();
        try {
          db.connect();
          Connection conn = db.getConn();

          Argon2 argon2 = Argon2Factory.create();

          PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gestionnaire WHERE username = ?");
          stmt.setString(1, usernameField.getText());

          ResultSet rs = stmt.executeQuery();
          if (rs.next()) {
            boolean passwordMatches = argon2.verify(rs.getString("password"), passwordField.getText().toCharArray());
            if (passwordMatches) {
              System.out.println("password matches");
              Authenticate.login(usernameField.getText());
              System.out.println("is logged in: " + Authenticate.isLoggedIn());
              System.out.println("username is: " + Authenticate.getUsername());

            } else {
              System.out.println("Login failed");
              SubmitFailAlert alert = new SubmitFailAlert();
              alert.showAlert("Login");
            }
          } else {
            System.out.println("Login failed");
            // user already exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username does not exist");
            alert.setContentText("Please register first or try again");
            alert.showAndWait();
          }
        } catch (SQLException e) {
          System.err.println("SQLException: " + e);
        }
      }
    });

    registerLink.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        System.out.println("is logged in before logging out: " + Authenticate.isLoggedIn());
        Authenticate.logout();
        System.out.println("is logged in after logging out: " + Authenticate.isLoggedIn());
        changeScences2();
      }
    });

    grid.add(scenetitle, 0, 0);
    grid.add(usernameLabel, 0, 1);
    grid.add(usernameField, 0, 2);
    grid.add(passwordLabel, 0, 3);
    grid.add(passwordField, 0, 4);
    grid.add(loginButton, 0, 5);
    grid.add(registerLink, 0, 6);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().add(grid);

    return scene;
  }

}
