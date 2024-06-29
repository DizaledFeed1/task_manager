package org.example.task.reception;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.task.HelloApplication;
import org.example.task.MySQL.SQL;
import org.example.task.taskManager.HelloController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionController implements Initializable {
    @FXML
    Button signIn;
    @FXML
    PasswordField password;
    @FXML
    TextField login;
    @FXML
    Hyperlink register;
    RegisterController registerController;
    HelloController helloController;
    public void setHelloController(HelloController helloController){
        this.helloController = helloController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signIn.setOnMouseClicked(event -> {
            SQL sql = new SQL(helloController);
            if (!password.getText().equals("") && !login.getText().equals("")){
                sql.signIn(login.getText(), password.getText());
                Stage stage = (Stage) signIn.getScene().getWindow();
                stage.close();
                }else {
                System.out.println("Пусто");
            }

        });
        register.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));

                Stage addStage = new Stage();
                addStage.initModality(Modality.APPLICATION_MODAL);
                Scene consoleScene = new Scene(fxmlLoader.load());

                registerController = fxmlLoader.getController();

                addStage.setTitle("Регистрация");
                addStage.setMinWidth(450);
                addStage.setMinHeight(300);

                addStage.setScene(consoleScene);
                addStage.showAndWait();
                addStage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
