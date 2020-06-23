package com.zte.shopping.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Eytins
 */

@Controller
public class UserController {

    @RequestMapping("/showLogin")
    public String showLogin(HttpServletRequest request, HttpServletResponse response){

        return "backend/login";
    }

}
