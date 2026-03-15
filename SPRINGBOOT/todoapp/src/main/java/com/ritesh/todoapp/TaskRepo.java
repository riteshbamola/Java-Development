package com.ritesh.todoapp;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.ritesh.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,Integer>{
}
