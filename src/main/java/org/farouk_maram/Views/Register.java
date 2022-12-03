package org.farouk_maram.Views;

import org.farouk_maram.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class Register extends App {
  private Validator validator = new Validator();

  public Register(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {
    Scene scene = new Scene(new Group(), 640, 480);

    GridPane grid = new GridPane();
    stage.setTitle("Register");
    grid.setAlignment(javafx.geometry.Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    Text scenetitle = new Text("Register");
    scenetitle.setFont(Font.font("Sans-serif", 100));

    Label usernameLabel = new Label("Username");
    TextField usernameField = new TextField();

    Label passwordLabel = new Label("Password");
    PasswordField passwordField = new PasswordField();

    Label passwordConfirmLabel = new Label("Password Confirm");
    PasswordField passwordConfirmField = new PasswordField();

    Tooltip tooltip = new Tooltip();
    tooltip.setShowDelay(Duration.ZERO);

    Button registerButton = new Button("Register");

    Hyperlink loginLink = new Hyperlink("Already have an account? Login");

    loginLink.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        System.out.println("changing scenes from the register class");
        changeScences();
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

    // password confirmation check
    validator.createCheck()
        .dependsOn("password-confirm", passwordConfirmField.textProperty())
        .withMethod(c -> {
          String passwordConfirm = c.get("password-confirm");
          if (!passwordConfirm.equals(passwordField.getText())) {
            tooltip.setText("Password confirm must match password");
            Tooltip.install(passwordConfirmField, tooltip);
            c.error("Password Confirm must match Password");
          } else {
            Tooltip.uninstall(passwordConfirmField, tooltip);
          }
        }).decorates(passwordConfirmField)
        .immediate();

    registerButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (usernameField.getText().equals("farouk") &&
            passwordField.getText().equals("123456")) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Register");
          alert.setHeaderText("Registration");
          alert.setContentText("Registration Successful");
          alert.showAndWait();
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Register");
          alert.setHeaderText("Register");
          alert.setContentText("Register Failed");
          alert.showAndWait();
        }
      }
    });

    grid.add(scenetitle, 0, 0);
    grid.add(usernameLabel, 0, 1);
    grid.add(usernameField, 0, 2);
    grid.add(passwordLabel, 0, 3);
    grid.add(passwordField, 0, 4);
    grid.add(passwordConfirmLabel, 0, 5);
    grid.add(passwordConfirmField, 0, 6);
    grid.add(registerButton, 0, 7);
    grid.add(loginLink, 0, 8);

    Group root = (Group) scene.getRoot();
    root.getChildren().add(grid);

    return scene;
  }
}
