package com.zte.shopping.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "skip")
public class SkipController {
    @RequestMapping(value = "userManager", method = {RequestMethod.POST, RequestMethod.GET})
    public String userManager() {
        return "backend/userManager";
    }

    @RequestMapping(value = "productManager", method = {RequestMethod.POST, RequestMethod.GET})
    public String productManager() {
        return "backend/productManager";
    }

    @RequestMapping(value = "productTypeManager", method = {RequestMethod.POST, RequestMethod.GET})
    public String productTypeManager() {
        return "backend/productTypeManager";
    }

    @RequestMapping(value = "staffManager", method = {RequestMethod.POST, RequestMethod.GET})
    public String staffManager() {
        return "backend/staffManager";
    }

    @RequestMapping(value = "deptManager", method = {RequestMethod.POST, RequestMethod.GET})
    public String deptManager() {
        return "backend/deptManager";
    }
}
