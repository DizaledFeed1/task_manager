package org.example.task.reception;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.task.MySQL.SQL;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    PasswordField password,checkPass;
    @FXML
    Button create;
    @FXML
    TextField login;
    @FXML
    ImageView logimg,passimg;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create.setOnMouseClicked(event -> {
            register();
        });

    }
    private void register(){
        if (password.getText().equals(checkPass.getText()) && !password.getText().equals("") && !checkPass.getText().equals("")){
            SQL sql = new SQL();
            passimg.setVisible(false);
            logimg.setVisible(false);
            try {
                sql.registerSQL(login.getText(),password.getText());
                Stage stage = (Stage) create.getScene().getWindow();
                stage.close();
            }  catch (SQLIntegrityConstraintViolationException e) {
                passimg.setVisible(true);
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else logimg.setVisible(true);
    }
}
