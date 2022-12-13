package org.farouk_maram.controllers;

import org.farouk_maram.App;
import org.farouk_maram.Authentication.Authenticate;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Welcome extends App {

  public Welcome(Stage stage) {
    this.stage = stage;
  }

  public Scene getScene() {

    Button livresButton = new Button("Livres");
    livresButton.setOnAction(event -> {
      changeScencesToHomeLivre();
    });

    // change button background color to 040F16 and add border of color 040f16 and
    // make text fbfbff and add padding and margin and make font bigger and increase
    livresButton.setStyle(
        "-fx-background-color: #1e293b; -fx-border-color: #040f16; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

    // add on hover effect to button and change background color to 040F16
    livresButton.setOnMouseEntered(event -> {
      // add cursor pointer
      livresButton.setStyle(
          "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");

    });

    // remove effect when mouse is not on button
    livresButton.setOnMouseExited(event -> {
      livresButton.setStyle(
          "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
    });

    Button usagersButton = new Button("Usagers");
    usagersButton.setOnAction(event -> {
      changeScencesToHomeUsager();
    });

    usagersButton.setStyle(
        "-fx-background-color: #1e293b; -fx-border-color: #040f16; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

    // add on hover effect to button and change background color to 040F16
    usagersButton.setOnMouseEntered(event -> {
      usagersButton.setStyle(
          "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
    });

    // remove effect when mouse is not on button
    usagersButton.setOnMouseExited(event -> {
      usagersButton.setStyle(
          "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
    });

    Button empruntsEnCoursButton = new Button("Emprunts En Cours");
    empruntsEnCoursButton.setOnAction(event -> {
      changeScencesToEnCoursEmprunts();
    });

    empruntsEnCoursButton.setStyle(
        "-fx-background-color: #1e293b; -fx-border-color: #040f16; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

    // add on hover effect to button and change background color to 040F16
    empruntsEnCoursButton.setOnMouseEntered(event -> {
      empruntsEnCoursButton.setStyle(
          "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
    });

    // remove effect when mouse is not on button
    empruntsEnCoursButton.setOnMouseExited(event -> {
      empruntsEnCoursButton.setStyle(
          "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
    });

    Button HistoriqueEmpruntsButton = new Button("Historique Emprunts");
    HistoriqueEmpruntsButton.setOnAction(event -> {
      changeScencesToHistoriqueEmprunts();
    });

    HistoriqueEmpruntsButton.setStyle(
        "-fx-background-color: #1e293b; -fx-border-color: #040f16; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");

    // add on hover effect to button and change background color to 040F16
    HistoriqueEmpruntsButton.setOnMouseEntered(event -> {
      HistoriqueEmpruntsButton.setStyle(
          "-fx-background-color: #475569; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
    });

    // remove effect when mouse is not on button
    HistoriqueEmpruntsButton.setOnMouseExited(event -> {
      HistoriqueEmpruntsButton.setStyle(
          "-fx-background-color: #1e293b; -fx-border-color: #475569; -fx-text-fill: #fbfbff; -fx-margin: 10; -fx-padding: 5; -fx-font-size: 20; -fx-min-width: 200; -fx-border-radius: 5; -fx-background-radius: 5;");
    });

    Text usernameText = new Text("Unknown user");
    if (Authenticate.isLoggedIn()) {
      usernameText.setText(Authenticate.getUsername());
    }
    usernameText.setStyle("-fx-fill: #040f16; -fx-font-weight: bold;");
    usernameText.setFont(new javafx.scene.text.Font(30));
    Text title = new Text("Bienvenue dans le système de gestion de bibliothèque!");
    Text subTitle = new Text("Ici vous pouvez gérer les livres, les emprunts et les usagers.");

    subTitle.setStyle("-fx-fill: #0b4f6c;");

    subTitle.setFont(new javafx.scene.text.Font(15));
    title.setFont(new javafx.scene.text.Font(25));
    title.setStyle("-fx-font-weight: bold; -fx-fill: #040f16; -fx-margin: 10; -fx-padding: 5;");

    VBox textContainer = new VBox(usernameText, title, subTitle);
    textContainer.setStyle("-fx-alignment: center; -fx-background-color: #fbfbff;");
    textContainer.setPadding(new Insets(10, 10, 10, 10));
    textContainer.setSpacing(10);

    VBox buttonsContainer = new VBox(livresButton, usagersButton, HistoriqueEmpruntsButton, empruntsEnCoursButton);
    buttonsContainer.setStyle("-fx-alignment: center; -fx-background-color: #fbfbff;");
    buttonsContainer.setPadding(new Insets(10, 10, 10, 10));
    buttonsContainer.setSpacing(10);

    VBox root = new VBox(textContainer, buttonsContainer);

    root.setStyle("-fx-alignment: center; -fx-background-color: #fbfbff;");
    root.setPadding(new Insets(10, 10, 10, 10));
    root.setSpacing(10);

    Scene scene = new Scene(root);
    return scene;

  }

}
