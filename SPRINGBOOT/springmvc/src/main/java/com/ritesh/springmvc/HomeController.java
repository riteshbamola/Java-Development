package com.ritesh.springmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;



@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){

        System.out.println("Home page Requested");
        return "index.jsp";
    }

    @RequestMapping("/add")
    public String add(HttpServletRequest req){

        int num1 = Integer.parseInt(req.getParameter("num1"));
        int num2 = Integer.parseInt(req.getParameter("num2"));

        int result = num1 + num2;

        HttpSession session = req.getSession();

        session.setAttribute("res",result);
        return "add.jsp";


    }
}
