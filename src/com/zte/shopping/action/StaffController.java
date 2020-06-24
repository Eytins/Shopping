package com.zte.shopping.action;

import com.zte.shopping.entity.SysStaff;
import com.zte.shopping.service.IStaffService;
import com.zte.shopping.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "staff")
public class StaffController {

    @Autowired
    private IStaffService iStaffService;

    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public String staffLogin(@RequestParam(value = "username") String userCode,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "userRole") String userRole,
                             HttpSession session) {
        MD5      md5      = new MD5();
        SysStaff sysStaff = this.iStaffService.staffLogin(userCode, md5.md5Change(password), userRole);
        if (sysStaff != null) {
            session.setAttribute("staff", sysStaff.getLoginName());
            return "backend/admin_main";
        } else {
            return "backend/login";
        }
    }

    @RequestMapping(value = "exit", method = {RequestMethod.GET, RequestMethod.POST})
    public String staffExit(HttpSession session) {
        session.invalidate();
        return "backend/login";
    }
}
