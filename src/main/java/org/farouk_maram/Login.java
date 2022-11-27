package org.farouk_maram;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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

/**
 * JavaFX App
 */
public class Login extends App {
  private Validator validator = new Validator();

  @Override
  public void start(Stage stage) {
    stage.setTitle("Login");
    GridPane grid = new GridPane();
    grid.setAlignment(javafx.geometry.Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    Text scenetitle = new Text("Login");
    scenetitle.setFont(Font.font("Sans-serif", 100));

    Label usernameLabel = new Label("Username");
    TextField usernameField = new TextField();

    Label passwordLabel = new Label("Password");
    PasswordField passwordField = new PasswordField();

    Tooltip tooltip = new Tooltip();
    tooltip.setShowDelay(Duration.ZERO);

    Button loginButton = new Button("Login");

    Hyperlink loginLink = new Hyperlink("Is this your first time here? Register now!");
    loginLink.setOnAction(e -> {
      stage.close();
      new App().start(new Stage());
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

    Alert emptyFieldAlert = new Alert(
        Alert.AlertType.ERROR);
    emptyFieldAlert.setHeaderText("Empty Field");
    emptyFieldAlert.setContentText("Please fill all fields");

    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (usernameField.getText().equals("")) {
          System.out.println("Username is empty");
          emptyFieldAlert.showAndWait();
        } else {
          System.out.println("Username is not empty " + usernameField.getText());
        }
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
    grid.add(loginButton, 0, 7);
    grid.add(loginLink, 0, 8);

    Scene scene = new Scene(grid, 640, 480);
    stage.setScene(scene);
    stage.show();
  }

}
