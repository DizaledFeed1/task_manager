package org.example.task.containers;

import java.time.LocalDate;
import java.util.Date;

public class ContainerAdd {
    private int tasrk_id;
    private String task_name;
    private String description;
    private LocalDate due_date;
    private Boolean completed;

    public ContainerAdd (String task_name,String description,LocalDate due_date, Boolean completed){
        this.task_name = task_name;
        this.description = description;
        this.due_date = due_date;
        this.completed = completed;
    }

    public ContainerAdd (int tasrk_id, String task_name,String description,LocalDate due_date, Boolean completed){
        this.tasrk_id = tasrk_id;
        this.task_name = task_name;
        this.description = description;
        this.due_date = due_date;
        this.completed = completed;
    }
    public String return_task_name(){
        return task_name;
    }
    public String return_description(){
        return description;
    }
    public LocalDate return_due_date(){
        return due_date;
    }
    public  boolean return_completed(){
        return completed;
    }
    public  int return_tasrk_id(){
        return tasrk_id;
    }
}
