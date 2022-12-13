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
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    scenetitle.setStyle("-fx-font-weight: bold; -fx-fill: #040f16; -fx-margin: 10; -fx-padding: 5;");
    scenetitle.setFont(Font.font("Sans-serif", 100));

    Label usernameLabel = new Label("Nom d'utilisateur");
    usernameLabel.setStyle("-fx-text-fill: #0b4f6c;");
    TextField usernameField = new TextField();
    usernameField.setStyle(
        " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");

    usernameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          usernameField.setStyle(
              "-fx-background-color: -fx-focus-color, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
        } else {
          usernameField.setStyle(
              " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
        }
      }

    });

    Label passwordLabel = new Label("Mot de passe");
    passwordLabel.setStyle("-fx-text-fill: #0b4f6c;");

    PasswordField passwordField = new PasswordField();

    Label passwordErrorLabel = new Label();

    passwordField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!isValidPassword(newValue)) {
          passwordErrorLabel
              .setText("Le mot de passe doit contenir au moins 8 caractères, une lettre et un chiffre");
          passwordErrorLabel.setStyle("-fx-text-fill: red;");
        } else {
          passwordErrorLabel.setText("");
        }
      }
    });

    passwordField.setStyle(
        " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");

    passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          passwordField.setStyle(
              "-fx-background-color: -fx-focus-color, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
        } else {
          passwordField.setStyle(
              " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
        }
      }

    });

    Tooltip tooltip = new Tooltip();
    tooltip.setShowDelay(Duration.ZERO);

    Button loginButton = new Button("Se connecter");

    loginButton.setStyle(
        "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

    // add on hover effect to button and change background color to 040F16
    loginButton.setOnMouseEntered(event -> {
      // add cursor pointer
      loginButton.setStyle(
          "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");

    });

    // remove effect when mouse is not on button
    loginButton.setOnMouseExited(event -> {
      loginButton.setStyle(
          "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
    });

    loginButton.disableProperty().bind(
        Bindings.isEmpty(usernameField.textProperty())
            .or(Bindings.isEmpty(passwordField.textProperty()))
            .or(Bindings.createBooleanBinding(() -> !isValidPassword(passwordField.getText()),
                passwordField.textProperty())));

    Hyperlink registerLink = new Hyperlink("Est-ce votre première visite? Inscrivez-vous maintenant!");

    // password check
    validator.createCheck()
        .dependsOn("password", passwordField.textProperty())
        .withMethod(c -> {
          String password = c.get("password");
          if (!isValidPassword(password)) {
            tooltip.setText(
                "Le mot de passe doit contenir des chiffres et des lettres et faire au moins 8 caractères de long");
            Tooltip.install(passwordField, tooltip);
            c.error("Le mot de passe doit contenir des chiffres et des lettres et faire au moins 8 caractères de long");
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
              Authenticate.login(usernameField.getText());
              changeScencesToWelcome();
            } else {
              SubmitFailAlert alert = new SubmitFailAlert();
              alert.showAlert("Login");
            }
          } else {
            // user already exists
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Le nom d'utilisateur n'existe pas");
            alert.setContentText("S'il vous plaît réessayer ou créer un nouveau compte");
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
        changeScencesToRegister();
      }
    });

    grid.add(scenetitle, 0, 0);
    grid.add(usernameLabel, 0, 1);
    grid.add(usernameField, 0, 2);
    grid.add(passwordLabel, 0, 3);
    grid.add(passwordField, 0, 4);
    grid.add(passwordErrorLabel, 0, 5);
    grid.add(loginButton, 0, 6);
    grid.add(registerLink, 0, 7);

    StackPane root = (StackPane) scene.getRoot();
    root.getChildren().add(grid);

    return scene;
  }

}
