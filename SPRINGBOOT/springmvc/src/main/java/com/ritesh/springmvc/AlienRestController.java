package com.ritesh.springmvc;

import com.ritesh.springmvc.AlienRepo;
import com.ritesh.springmvc.model.Alien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RestController
@RequestMapping("/api")
public class AlienRestController {
    @Autowired
    AlienRepo repo;

    @GetMapping("allaliens")
//    @ResponseBody
    public List<Alien> getAliens(Model m){
        List<Alien> res = repo.findAll();
        return res;
    }

    @GetMapping("/getalien/{id}")
//    @ResponseBody
    public Alien getAlien(@PathVariable int id){   //path variable to pass in getmapping
        Alien alien = repo.getOne(id);
        return alien;
    }


    @PostMapping(path="alien" ,consumes = "application/json")   //only accept json
    public Map<String, String> addAlien(@RequestBody Alien alien){
        repo.save(alien);
        Map<String,String> response = new HashMap<>();
        response.put("message","Regsitered Succesfully");
        return response;

    }

}

