package com.ritesh.springmvc;

import com.ritesh.springmvc.model.Alien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    AlienRepo repo;


    //will create a model object m at first
    @ModelAttribute
    public  void modelData(Model m){
        m.addAttribute("name","aliens");
    }


    @RequestMapping("/")
    public String home(){

        System.out.println("Home page Requested");
        return "index";
    }

//    @RequestMapping("/add")
//    public String add(HttpServletRequest req){
//
//        int num1 = Integer.parseInt(req.getParameter("num1"));
//        int num2 = Integer.parseInt(req.getParameter("num2"));
//
//        int result = num1 + num2;
//
//        HttpSession session = req.getSession();
//
//        session.setAttribute("res",result);
//        return "add.jsp";
//
//
//    }

//    @RequestMapping("/add")
//    public String add(@RequestParam("num1") int num1, @RequestParam("num2") int num2, HttpSession session){
//
//        int result = num1+num2;
//        session.setAttribute("res",result);
//        return "add.jsp";
//

    //Model View
    @RequestMapping("/add")
    public ModelAndView add(@RequestParam("num1") int num1, @RequestParam("num2") int num2){

        ModelAndView mv = new ModelAndView();    //can pass view in controller also

//      mv.setViewName("add.jsp");
        mv.setViewName("add");   //configure suffix in application properties to avoid jsp and prefix /views

        int result = num1+num2;
        mv.addObject("res",result);
        return mv;

    }


//
//    @RequestMapping("/addalien")
//    public String addAlien(@ModelAttribute("a1") Alien a){
//        return "addalien";
//    }



    //post mapping  == @PostMapping or  @RequestMapping(value = "/addalien",method = RequestMethod.POST)

    //get mapping
//    @GetMapping("/allaliens")
//    public String getAliens(Model m){
//        List<Alien> list = Arrays.asList(new Alien(101,"Ritesh"), new Alien(102,"Ayush"));
//
//        m.addAttribute("aliens",list);
//        return "aliens";
//    }


    //using JPARepository
    @GetMapping("/allaliens")
    public String getAliens(Model m){
        m.addAttribute("aliens",repo.findAll());
        return "aliens";
    }

    @GetMapping("/getalien")
    public String getAlien(@RequestParam("id") int id, Model m){
        m.addAttribute("alien",repo.findById(id));
        return "alien";
    }

    @PostMapping("/addalien")
    public String addAlien(@ModelAttribute("a1") Alien a){
        repo.save(a);
        return "addalien";
    }

    @GetMapping("/getalienbyname")
    public String getAlienByname(@RequestParam("name") String name, Model m){
        m.addAttribute("alien",repo.findByAname(name));
//        m.addAttribute("alien", repo.find(name));    //own function
        return "alien";
    }

    @GetMapping("/getalienbynamedesc")
    public String getAlienByNameDesc(@RequestParam("name") String name, Model m){
        m.addAttribute("alien",repo.findByAnameOrderByAidDesc(name));
        return "alien";
    }





}
