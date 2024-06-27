package org.example.task.MySQL;

import org.example.task.taskManager.HelloController;
import org.example.task.containers.ContainerAdd;

import java.sql.*;
import java.time.LocalDate;

public class SQL {
    private static final String url = "jdbc:mysql://localhost:3306/task_manager";
    private static final String user = "root";
    private static final String password = "Porfik 10";

    private static Connection con;
    private static PreparedStatement preparedStatement;
    static String query;
    private HelloController helloController;

    public SQL (HelloController helloController){
        try {
            this.helloController = helloController;
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // Выключаем автозафиксацию
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
    public SQL (){
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // Выключаем автозафиксацию
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void sorting_by_date_up(){
        query = "select * from tasks\n" +
                "order by due_date";

        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sorting_by_date_down(){
        query = "select * from tasks\n" +
                "order by due_date desc";

        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sorting_by_completed_up(){
        query = "select * from tasks\n" +
                "order by completed";

        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sorting_by_completed_down(){
        query = "select * from tasks\n" +
                "order by completed DESC";

        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTaskSQL(String task_name, String description, LocalDate due_date, boolean completed,int user_id){
        query = "insert into tasks (task_name, description, due_date, completed,user_id) value (?,?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,task_name);
            preparedStatement.setString(2,description);
            preparedStatement.setDate(3, Date.valueOf(due_date));
            preparedStatement.setBoolean(4, completed);
            preparedStatement.setInt(5, user_id);
            preparedStatement.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editTaskSQL(String task_name, String description, LocalDate due_date, boolean completed, int tasrk_id){
        query = "update tasks \n" +
                "set task_name = ?, `description` = ?, due_date = ?, completed = ?\n" +
                "where tasrk_id = ?";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,task_name);
            preparedStatement.setString(2,description);
            preparedStatement.setDate(3, Date.valueOf(due_date));
            preparedStatement.setBoolean(4,completed);
            preparedStatement.setInt(5,tasrk_id);
            preparedStatement.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ContainerAdd returnTask(int id){
        query = "select * from tasks where tasrk_id = ?";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return new ContainerAdd( resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteTaskSQL(int id){
        query = "delete from tasks where tasrk_id = ? ";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTable(){
        query = "select * from tasks";

        try {
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showPersonalTable(int user_id){
        query = "SELECT t.tasrk_id, t.task_name, t.`description`, t.due_date, t.completed\n" +
                "FROM tasks t\n" +
                "JOIN users u ON t.user_id = u.user_id\n" +
                "WHERE u.user_id = ?";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.updateList(new ContainerAdd(resultSet.getInt("tasrk_id"), resultSet.getString("task_name"),resultSet.getString("description"),resultSet.getDate("due_date").toLocalDate(),resultSet.getBoolean("completed")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void signIn(String login, String password){
        query = "select * from users where username = ? and `password` = ?";
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                helloController.setPerson(resultSet.getInt("user_id"),resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerSQL(String username,String password) throws SQLException {
        query = "insert into users (username,`password`,`role`) VALUE (?,?,?)";

        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,"user");
        preparedStatement.executeUpdate();

        con.commit();
    }
}