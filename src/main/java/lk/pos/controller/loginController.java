package lk.pos.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    @FXML
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.equals("admin") && password.equals("1234")){
            lblMessage.setText("Login successfull");
        }else {
            lblMessage.setText("Invalid username or password");
        }
    }
}
