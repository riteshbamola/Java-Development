package com.ritesh.todoapp.model;

import com.ritesh.todoapp.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    private int taskid;
    private String taskname;
    private String taskdesc;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(){}
    public Task(int taskid, String taskname, String taskdesc, TaskStatus status) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.taskdesc = taskdesc;
        this.status = status;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskdesc() {
        return taskdesc;
    }

    public void setTaskdesc(String taskdesc) {
        this.taskdesc = taskdesc;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
