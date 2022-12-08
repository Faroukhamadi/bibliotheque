package org.farouk_maram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.Authentication.Authenticate;
import org.farouk_maram.controllers.HomeEmprunt;
import org.farouk_maram.controllers.Login;
import org.farouk_maram.db.Database;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.synedra.validatorfx.ValidationResult;
import net.synedra.validatorfx.Validator;

public class App extends Application {
    private Validator validator = new Validator();
    protected Stage stage;

    public void changeScences2() {
        HomeEmprunt home = new HomeEmprunt(stage);
        Scene scene = home.getScene();

        stage.setTitle("Home");
        stage.setScene(scene);

        // reset this when done experimenting
        // Register register = new Register(stage);
        // Scene scene = register.getScene();

        // stage.setTitle("Register");
        // stage.setScene(scene);
    }

    public void changeScences() {
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
        System.out.println("grid props: " + grid.getProperties());
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

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
                changeScences();
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
                    // TODO: change schenes after creating user

                } catch (SQLException e) {
                    // use error codes to distinguish between different errors
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
