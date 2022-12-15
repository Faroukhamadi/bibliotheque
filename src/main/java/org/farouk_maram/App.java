package org.farouk_maram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.farouk_maram.Authentication.Authenticate;
import org.farouk_maram.controllers.HomeEmprunt;
import org.farouk_maram.controllers.HomeEmpruntEnCours;
import org.farouk_maram.controllers.HomeEmpruntHistory;
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
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
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

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
    }

    public void changeScencesToHistoriqueEmprunts() {
        HomeEmpruntHistory homeEmprunt = new HomeEmpruntHistory(stage);
        Scene scene = homeEmprunt.getScene();

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Welcome");

        stage.setScene(scene);
    }

    public void changeScencesToEnCoursEmprunts() {
        HomeEmpruntEnCours homeEmprunt = new HomeEmpruntEnCours(stage);
        Scene scene = homeEmprunt.getScene();

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToHomeEmprunt() {
        HomeEmprunt homeEmprunt = new HomeEmprunt(stage);
        Scene scene = homeEmprunt.getScene();

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Welcome");
        stage.setScene(scene);
    }

    public void changeScencesToHomeUsager() {
        HomeUsager homeUsager = new HomeUsager(stage);
        Scene scene = homeUsager.getScene();

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

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
        boolean containsDigits = false;
        boolean containsLetters = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                containsDigits = true;
            } else if (Character.isLetter(c)) {
                containsLetters = true;
            }
        }
        if (containsDigits == true && containsLetters == true && password.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setTitle("Inscription");
        stage.setMaximized(true);
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        VBox vbox = new VBox();
        // add margin to vbox around items in vbox
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        Text scenetitle = new Text("Inscription");
        scenetitle.setStyle("-fx-font-weight: bold; -fx-fill: #040f16; -fx-margin: 10; -fx-padding: 5;");
        scenetitle.setFont(Font.font("Sans-serif", 100));

        Label usernameLabel = new Label("Nom d'utilisateur");
        Label passwordConfirmationErrorLabel = new Label();
        Label passwordErrorLabel = new Label();

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

        Label passwordConfirmLabel = new Label("Confirmation du mot de passe");

        passwordConfirmLabel.setStyle("-fx-text-fill: #0b4f6c;");

        PasswordField passwordConfirmField = new PasswordField();

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

        passwordConfirmField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals(passwordField.getText())) {
                    passwordConfirmationErrorLabel.setText("Les mots de passe ne correspondent pas");
                    passwordConfirmationErrorLabel.setStyle("-fx-text-fill: red;");
                } else {
                    passwordConfirmationErrorLabel.setText("");
                }
            }
        });

        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals(passwordConfirmField.getText())) {
                    passwordConfirmationErrorLabel.setText("Les mots de passe ne correspondent pas");
                    passwordConfirmationErrorLabel.setStyle("-fx-text-fill: red;");
                } else {
                    passwordConfirmationErrorLabel.setText("");
                }
            }
        });

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

        Button registerButton = new Button("S'inscrire");
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
                        .or(Bindings.isEmpty(passwordConfirmField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> !isValidPassword(passwordField.getText()),
                                passwordField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> !isValidPassword(passwordConfirmField.getText()),
                                passwordConfirmField.textProperty())
                                .or(Bindings.createBooleanBinding(() -> !passwordConfirmField.getText()
                                        .equals(passwordField.getText()), passwordConfirmField.textProperty()))));

        Hyperlink loginLink = new Hyperlink("Avez-vous déjà un compte? Connectez-vous");

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
                        tooltip.setText("La confirmation du mot de passe doit correspondre au mot de passe");
                        Tooltip.install(passwordConfirmField, tooltip);
                        c.error("La confirmation du mot de passe doit correspondre au mot de passe");
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
                        tooltip.setText(
                                "Le mot de passe doit contenir des chiffres et des lettres et faire au moins 8 caractères de long");
                        Tooltip.install(passwordField, tooltip);
                        c.error("Le mot de passe doit contenir des chiffres et des lettres et faire au moins 8 caractères de long");
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
                    alert.setTitle("Inscription");
                    alert.setTitle("Inscription");
                    alert.setContentText("Inscription échouée");
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
                        alert.setHeaderText("Le nom d'utilisateur existe déjà");
                        alert.setContentText("Veuillez choisir un autre nom d'utilisateur");
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
        grid.add(passwordErrorLabel, 0, 5);
        grid.add(passwordConfirmLabel, 0, 6);
        grid.add(passwordConfirmField, 0, 7);
        grid.add(passwordConfirmationErrorLabel, 0, 8);
        grid.add(registerButton, 0, 9);
        grid.add(loginLink, 0, 10);

        Scene scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        stage.show();
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
