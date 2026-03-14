package com.ritesh.springjdbc;

import com.ritesh.springjdbc.model.Alien;
import com.ritesh.springjdbc.repo.AlienRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringjdbcApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringjdbcApplication.class, args);

		Alien obj = (Alien) context.getBean(Alien.class);
		obj.setId(12);
		obj.setName("Ottkar");
		obj.setTech("Java");

		AlienRepo repo = context.getBean(AlienRepo.class);
		repo.save(obj);
		System.out.println(repo.getAll());


	}

}
