package org.farouk_maram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.Authentication.Authenticate;
import org.farouk_maram.controllers.HomeEmprunt;
import org.farouk_maram.controllers.HomeLivre;
import org.farouk_maram.controllers.HomeUsager;
import org.farouk_maram.controllers.Login;
import org.farouk_maram.controllers.Register;
import org.farouk_maram.controllers.Welcome;
import org.farouk_maram.db.Database;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.application.Application;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.ValidationResult;
import net.synedra.validatorfx.Validator;

public class App extends Application {
    private Validator validator = new Validator();
    protected Stage stage;

    public void changeScencesToWelcome() {
        Welcome welcome = new Welcome(stage);
        Scene scene = welcome.getScene();

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToHomeLivre() {
        HomeLivre homeLivre = new HomeLivre(stage);
        Scene scene = homeLivre.getScene();

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToHomeEmprunt() {
        HomeEmprunt homeEmprunt = new HomeEmprunt(stage);
        Scene scene = homeEmprunt.getScene();

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToHomeUsager() {
        HomeUsager homeUsager = new HomeUsager(stage);
        Scene scene = homeUsager.getScene();

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToRegister() {
        Register register = new Register(stage);
        Scene scene = register.getScene();

        stage.setTitle("Register");
        stage.setScene(scene);
    }

    public void changeScencesToLogin() {
        Login login = new Login(stage);
        Scene scene = login.getScene();

        stage.setTitle("Login");

        stage.setScene(scene);
    }

    protected boolean isValidPassword(String password) {
        return true;
        // TODO: remove return true after testing
        // boolean containsDigits = false;
        // boolean containsLetters = false;
        // for (int i = 0; i < password.length(); i++) {
        // char c = password.charAt(i);
        // if (Character.isDigit(c)) {
        // containsDigits = true;
        // } else if (Character.isLetter(c)) {
        // containsLetters = true;
        // }
        // }
        // if (containsDigits == true && containsLetters == true && password.length() >=
        // {
        // return true;
        // } else {
        // return false;
        // }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setTitle("Register");
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        VBox vbox = new VBox();
        // add margin to vbox around items in vbox
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        Text scenetitle = new Text("Register");
        scenetitle.setStyle("-fx-font-weight: bold; -fx-fill: #040f16; -fx-margin: 10; -fx-padding: 5;");
        scenetitle.setFont(Font.font("Sans-serif", 100));

        Label usernameLabel = new Label("Username");

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

        Label passwordLabel = new Label("Password");

        passwordLabel.setStyle("-fx-text-fill: #0b4f6c;");

        PasswordField passwordField = new PasswordField();

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

        Label passwordConfirmLabel = new Label("Password Confirm");

        passwordConfirmLabel.setStyle("-fx-text-fill: #0b4f6c;");

        PasswordField passwordConfirmField = new PasswordField();

        passwordConfirmField.setStyle(
                " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
        passwordConfirmField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    passwordConfirmField.setStyle(
                            "-fx-background-color: -fx-focus-color, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
                } else {
                    passwordConfirmField.setStyle(
                            " -fx-background-color: -fx-text-box-border, -fx-background ; -fx-background-insets: 0, 0 0 1 0 ; -fx-background-radius: 0 ;");
                }
            }

        });

        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);

        Button registerButton = new Button("Register");
        registerButton.setStyle(
                "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

        // add on hover effect to button and change background color to 040F16
        registerButton.setOnMouseEntered(event -> {
            // add cursor pointer
            registerButton.setStyle(
                    "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");

        });

        // remove effect when mouse is not on button
        registerButton.setOnMouseExited(event -> {
            registerButton.setStyle(
                    "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
        });

        registerButton.disableProperty().bind(
                Bindings.isEmpty(usernameField.textProperty())
                        .or(Bindings.isEmpty(passwordField.textProperty()))
                        .or(Bindings.isEmpty(passwordConfirmField.textProperty())));

        Hyperlink loginLink = new Hyperlink("Already have an account? Login");

        loginLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                changeScencesToLogin();
            }
        });

        // password confirmation check
        validator.createCheck()
                .dependsOn("password-confirm", passwordConfirmField.textProperty())
                .withMethod(c -> {
                    String passwordConfirm = c.get("password-confirm");
                    if (!isValidPassword(passwordConfirm) || !passwordConfirm.equals(passwordField.getText())
                            || passwordConfirm.isEmpty()) {
                        tooltip.setText("Password confirm must match password");
                        Tooltip.install(passwordConfirmField, tooltip);
                        c.error("Password Confirm must match Password");
                    } else {
                        Tooltip.uninstall(passwordConfirmField, tooltip);
                    }
                }).decorates(passwordConfirmField)
                .immediate();
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

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // enum for validation
                ValidationResult result = validator.getValidationResult();
                if (result.getMessages().size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Register");
                    alert.setHeaderText("Register");
                    alert.setContentText("Register Failed");
                    alert.showAndWait();
                    return;
                }

                Database db = new Database();
                try {
                    db.connect();
                    Connection conn = db.getConn();

                    Argon2 argon2 = Argon2Factory.create();
                    String hash = argon2.hash(10, 65536, 1, passwordField.getText().toCharArray());

                    PreparedStatement insertStatement = conn
                            .prepareStatement(
                                    "INSERT INTO gestionnaire (username, password) VALUES (?, ?)");
                    insertStatement.setString(1, usernameField.getText());
                    insertStatement.setString(2, hash);
                    insertStatement.executeUpdate();
                    Authenticate.login(usernameField.getText());
                    changeScencesToWelcome();

                } catch (SQLException e) {
                    // use error codes to distinguish between different errors
                    System.out.println(e.getErrorCode());
                    if (e.getErrorCode() == 1062) {
                        // user already exists
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Username already taken");
                        alert.setContentText("Please choose another username");
                        alert.showAndWait();
                    } else {
                        e.printStackTrace();
                    }
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

        Scene scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public String sayHello() {
        return "";
    }

    public static void main(String[] args) {
        Database db = new Database();
        try {
            db.connect();
            Connection conn = db.getConn();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usager");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id_usager"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e);
        }
        launch();
    }

}
