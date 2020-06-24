package com.zte.shopping.action;

import com.zte.shopping.service.IUserService;
import com.zte.shopping.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "dologin", method = {RequestMethod.POST, RequestMethod.GET})
    public String doLogin(@RequestParam(value = "username") String userCode,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "userRole") String userRole) {
        MD5 md5 = new MD5();
        int i   = iUserService.dologin(userCode, md5.md5Change(password), userRole);
        if (i == 1 && userRole.equals("2001")) {
            return "backend/admin_main";
        } else if (i == 1 && userRole.equals("2002")) {
            return "main";
        } else {
            return "backend/login";
        }

    }
}
