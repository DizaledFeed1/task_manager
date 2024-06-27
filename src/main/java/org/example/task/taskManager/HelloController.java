package org.example.task.taskManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.task.HelloApplication;
import org.example.task.MySQL.SQL;
import org.example.task.reception.ReceptionController;
import org.example.task.containers.ContainerAdd;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    Button addTask,show,edit,deleteTask,singIn;
    @FXML
    ListView listTask;
    @FXML
    MenuItem date_up,date_down, statusup,statusdown;
    WindowAdd windowAdd;
    WindowEdit windowEdit;
    ReceptionController receptionController;
    int user_id = 0;
    String role;
    SQL sql;
    int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sql = new SQL(this);

        singIn.setOnMouseClicked(event -> {
            if (user_id == 0) {
                signInWindow();
                System.out.println(user_id + role);
            }
        });

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
            if (user_id == 0) {
                signInWindow();
            }
            addTaskWindow();
        });
        show.setOnMouseClicked(event -> {
            if (user_id == 0) {
                signInWindow();
            }
            showFullTable();
        });
        deleteTask.setOnMouseClicked(event -> {
            if (user_id == 0) {
                signInWindow();
            }
            deleteTask();
        });
        listTask.setOnMouseClicked(this::handleDoubleClick);
    }

    private void handleDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            editTaskWindow();
        }
    }

    public void setPerson(int user_id, String role){
        this.user_id = user_id;
        this.role = role;
    }

    private void signInWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("reception.fxml"));
        try {
            Stage signInStage = new Stage();
            signInStage.initModality(Modality.APPLICATION_MODAL);
            Scene consoleScene = new Scene(fxmlLoader.load());


            receptionController = fxmlLoader.getController();
            receptionController.setHelloController(this);

            signInStage.setTitle("Консоль");
            signInStage.setMinWidth(300);
            signInStage.setMinHeight(400);

            signInStage.setScene(consoleScene);
            signInStage.setOnCloseRequest(event -> {
                event.consume(); // Игнорировать запрос на закрытие окна
            });
            signInStage.showAndWait();
            signInStage.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
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
        sql.addTaskSQL(containerAdd.return_task_name(), containerAdd.return_description(), containerAdd.return_due_date(),containerAdd.return_completed(),user_id);
        updateList(containerAdd);
    }

    public void updateList(ContainerAdd containerAdd){
        String text = "id: " + containerAdd.return_tasrk_id() + " task_name: " + containerAdd.return_task_name() + " description: " + containerAdd.return_description() + " due_date: " + containerAdd.return_due_date() + " completed: " + containerAdd.return_completed();
        System.out.println(text);
        listTask.getItems().add(text);
    }

    private void showFullTable(){
        listTask.getItems().clear();
        if (role.equals("admin")) {
            sql.showTable();
        } else if (user_id != 0 && role.equals("user")){
            sql.showPersonalTable(user_id);
        }
    }
}