package org.example.task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.task.containers.ContainerAdd;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WindowEdit implements Initializable {
    @FXML
    private Button editButton;
    @FXML
    private TextField taskName,description;
    @FXML
    private DatePicker dueDate;
    @FXML
    private RadioButton completed;
    HelloController helloController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        editButton.setOnMouseClicked(event -> {
            helloController.editTask(returnEdit());
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();
        });
    }
    private ContainerAdd returnEdit(){
        String task_name = taskName.getText();
        String description_text = description.getText();
        LocalDate due_date = dueDate.getValue();
        boolean completedFlag = completed.isSelected();
        ContainerAdd containerAdd = new ContainerAdd(task_name,description_text,due_date,completedFlag);
        return containerAdd;
    }
    public void setHelloController(HelloController helloController,ContainerAdd containerAdd) {
        this.helloController = helloController;
        taskName.setText(containerAdd.return_task_name());
        description.setText(containerAdd.return_description());
        dueDate.setValue(containerAdd.return_due_date());
        completed.setSelected(containerAdd.return_completed());
    }
}
