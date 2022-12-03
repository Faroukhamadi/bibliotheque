package org.farouk_maram.Views;

import org.farouk_maram.App;
import org.farouk_maram.Authentication.Authenticate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.Validator;

public class Login extends App {
  private Validator validator = new Validator();

  public Login(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    Scene scene = new Scene(new Group(), 640, 480);

    GridPane grid = new GridPane();
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

    registerLink.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        System.out.println("is logged in before logging out: " + Authenticate.isLoggedIn());
        Authenticate.logout();
        System.out.println("is logged in after logging out: " + Authenticate.isLoggedIn());

        changeScences2();
      }
    });

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
        if (usernameField.getText().equals("farouk") &&
            passwordField.getText().equals("123456")) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Login");
          alert.setHeaderText("Login");
          alert.setContentText("Login Success");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Login");
          alert.setHeaderText("Login");
          alert.setContentText("Login Failed");
          alert.showAndWait();
        }
      }
    });

    grid.add(scenetitle, 0, 0);
    grid.add(usernameLabel, 0, 1);
    grid.add(usernameField, 0, 2);
    grid.add(passwordLabel, 0, 3);
    grid.add(passwordField, 0, 4);
    grid.add(loginButton, 0, 5);
    grid.add(registerLink, 0, 6);

    Group root = (Group) scene.getRoot();
    root.getChildren().add(grid);

    return scene;
  }

}
