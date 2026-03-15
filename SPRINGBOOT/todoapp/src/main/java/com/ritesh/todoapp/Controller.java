package com.ritesh.todoapp;

import java.util.List;
import com.ritesh.todoapp.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class Controller {
    @Autowired
    private TaskRepo repo;

    @PostMapping("/tasks")
    public Map<String,String> addTask(@RequestBody Task t){
        repo.save(t);

        Map<String,String> response = new HashMap<>();
        response.put("message","Task Added Successfully");
        return response;
    }
    @DeleteMapping("/tasks/{id}")
    public Map<String,String> deleteTask(@PathVariable int id){
        repo.deleteById(id);

        Map<String,String> response = new HashMap<>();
        response.put("message","Task Deleted Successfully");
        return response;

    }
    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        List<Task> tasks = repo.findAll();
        return tasks;
    }
    @GetMapping("tasks/{id}")
    public Task getTask(@PathVariable int id){
        Task task = repo.getOne(id);
        return task;
    }

    @PutMapping("tasks/{id}")
    public Map<String,String> updateTask(@PathVariable int id, @RequestBody Task t){
        Task existing = repo.findById(id).orElse(null);

        existing.setStatus(t.getStatus());
        existing.setTaskdesc(t.getTaskdesc());
        existing.setTaskid(t.getTaskid());
        existing.setTaskname(t.getTaskname());

        repo.save(existing);
        Map<String,String> response = new HashMap<>();
        response.put("message","Task Updated Successfully");
        return response;
    }

}
