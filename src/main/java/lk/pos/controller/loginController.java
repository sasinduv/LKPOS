package lk.pos.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.pos.dao.UserDAO;
import lk.pos.model.User;

public class loginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;


//    public void handleLogin() {
////        String username = txtUsername.getText();
////        String password = txtPassword.getText();
////
////        if(username.equals("admin") && password.equals("1234")){
////            lblMessage.setText("Login successfull");
////        }else {
////            lblMessage.setText("Invalid username or password");
////        }
////    }
    public void login() {
        try {
            User user = UserDAO.login(
                    txtUsername.getText(),
                    txtPassword.getText()
            );

            if (user == null ) {
                lblMessage.setText("Invalid credintial");
                return;
            }
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dashboard.fxml")
            );
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            DashboardController controller = loader.getController();
            controller.setUser(user);

            stage.show();
            ((Stage) txtUsername.getScene().getWindow()).close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
