package org.farouk_maram.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SubmitFailAlert extends Alert {

  public SubmitFailAlert(AlertType alertType, String contentText, ButtonType... buttons) {
    super(AlertType.ERROR, contentText, buttons);
  }

  public SubmitFailAlert(AlertType alertType) {
    super(AlertType.ERROR);
  }

  public SubmitFailAlert() {
    super(AlertType.ERROR);
  }

  public void showAlert(String text) {
    this.setTitle(text);
    this.setHeaderText(text);
    this.setContentText(text + " Failed");
    this.showAndWait();
  }
}
