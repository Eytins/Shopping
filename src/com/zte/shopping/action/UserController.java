package com.zte.shopping.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Eytins
 */

@Controller
public class UserController {

    @RequestMapping(value = "ShowMain")
    public String showMain() {
        return "main";
    }
}
