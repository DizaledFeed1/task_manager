module org.example.task {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.task to javafx.fxml;
    exports org.example.task.taskManager;
    opens org.example.task.taskManager to javafx.fxml;
    exports org.example.task;
    exports org.example.task.reception;
    opens org.example.task.reception to javafx.fxml;
}