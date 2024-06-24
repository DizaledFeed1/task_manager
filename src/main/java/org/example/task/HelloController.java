package org.example.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.task.MySQL.SQL;
import org.example.task.containers.ContainerAdd;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class HelloController implements Initializable {
    @FXML
    Button addTask,show,edit,deleteTask;
    @FXML
    ListView listTask;
    @FXML
    MenuItem date_up,date_down, statusup,statusdown;
    WindowAdd windowAdd;
    WindowEdit windowEdit;
    SQL sql;
    int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sql = new SQL(this);

        date_up.setOnAction(event -> {
            listTask.getItems().clear();
            sql.sorting_by_date_up();
        });
        date_down.setOnAction(event -> {
            listTask.getItems().clear();
            sql.sorting_by_date_down();
        });
        statusup.setOnAction(event -> {
            listTask.getItems().clear();
            sql.sorting_by_completed_up();
        });
        statusdown.setOnAction(event -> {
            listTask.getItems().clear();
            sql.sorting_by_completed_down();
        });

        addTask.setOnMouseClicked(event -> {
            addTaskWindow();
        });
        show.setOnMouseClicked(event -> {
            showFullTable();
        });
        deleteTask.setOnMouseClicked(event -> {
            deleteTask();
        });
        listTask.setOnMouseClicked(this::handleDoubleClick);
    }

    private void handleDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            editTaskWindow();
        }
    }
    private void addTaskWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addTask.fxml"));

            Stage addStage = new Stage();
            addStage.initModality(Modality.APPLICATION_MODAL);
            Scene consoleScene = new Scene(fxmlLoader.load());

            windowAdd = fxmlLoader.getController();
            windowAdd.setHelloController(this);

            addStage.setTitle("Консоль");
            addStage.setMinWidth(522);
            addStage.setMinHeight(268);

            addStage.setScene(consoleScene);
            addStage.showAndWait();
            addStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void editTaskWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editTask.fxml"));

            Stage addStage = new Stage();
            addStage.initModality(Modality.APPLICATION_MODAL);
            Scene consoleScene = new Scene(fxmlLoader.load());

            windowEdit = fxmlLoader.getController();
            windowEdit.setHelloController(this,sql.returnTask(getId()));

            addStage.setTitle("Консоль");
            addStage.setMinWidth(522);
            addStage.setMinHeight(268);

            addStage.setScene(consoleScene);
            addStage.showAndWait();
            addStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getId() {
        String selectedItems = listTask.getSelectionModel().getSelectedItems().toString();
        selectedItems = selectedItems.replace("[", "").replace("]", "");
        String[] parts = selectedItems.split(" ");

        // Извлечение значения id
        id = Integer.parseInt(parts[1]);
        return id;
    }

    private void deleteTask(){
        sql.deleteTaskSQL(getId());
        showFullTable();
    }

    public void editTask(ContainerAdd containerAdd){
        sql.editTaskSQL(containerAdd.return_task_name(), containerAdd.return_description(), containerAdd.return_due_date(),containerAdd.return_completed(),id);
        showFullTable();
    }

    public void addTask(ContainerAdd containerAdd){
        sql.addTaskSQL(containerAdd.return_task_name(), containerAdd.return_description(), containerAdd.return_due_date(),containerAdd.return_completed());
        updateList(containerAdd);
    }
    public void updateList(ContainerAdd containerAdd){
        String text = "id: " + containerAdd.return_tasrk_id() + " task_name: " + containerAdd.return_task_name() + " description: " + containerAdd.return_description() + " due_date: " + containerAdd.return_due_date() + " completed: " + containerAdd.return_completed();
        System.out.println(text);
        listTask.getItems().add(text);
        listTask.setStyle("-fx-background-color: #636363;");
    }
    private void showFullTable(){
        listTask.getItems().clear();
        sql.showTable();
    }
}