package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.entity.Attache;
import com.zte.shopping.entity.User;
import com.zte.shopping.exception.CodeErrorException;
import com.zte.shopping.exception.LoginNameOrPasswordErrorException;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserExistException;
import com.zte.shopping.service.IAttacheService;
import com.zte.shopping.service.IUserService;
import com.zte.shopping.util.ParameterUtil;
import com.zte.shopping.util.ResponseResult;
import com.zte.shopping.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Eytins
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAttacheService attacheService;

    @RequestMapping(value = "/showMain")
    public String showMain() {
        return "main";
    }

    @RequestMapping("/findByFuzzyParamList")
    public ModelAndView findByFuzzyParamList(String pageNo, String pageSize, User userParam) {
        ModelAndView modelAndView = new ModelAndView();

        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }


        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        List<User> userList = iUserService.findByFuzzyParamList(userParam);

        PageInfo<User> pageUserList = new PageInfo<User>(userList);

        modelAndView.addObject("pageUserList", pageUserList);

        modelAndView.addObject("userParam", userParam);

        modelAndView.setViewName("backend/userManager");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/modifyStatus")
    public ResponseResult modifyStatus(String id, String status) {
        ResponseResult result = new ResponseResult();

        try {
            iUserService.modifyStatus(id, status);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public ResponseResult findById(String id) {
        ResponseResult result = new ResponseResult();

        try {
            User user = iUserService.findById(id);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);
            result.setReturnObject(user);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/modifyById")
    public ResponseResult modifyById(User user) {
        ResponseResult result = new ResponseResult();

        try {
            iUserService.modifyById(user);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (UserExistException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 会员登录
     */
    @ResponseBody
    @RequestMapping("/login")
    public ResponseResult login(String loginName, String password, String code, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            User user = iUserService.login(loginName, password, code, session);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

            // 登录成功后把  会员信息放入到 HttpSesssion对象中
            session.setAttribute("user", user);

            // 登录后,创建一个空的购物车
            session.setAttribute("cart", new CartVo());

        } catch (LoginNameOrPasswordErrorException | CodeErrorException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 会员退出
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        iUserService.logout(session);

        System.out.println("====================" + session.getAttribute("user"));

        // ProductController类中的findProductFuzzyParamList()方法中的@RequestMapping("/findProductFuzzyParamList")
        return "redirect:/product/findProductFuzzyParamList";
    }

    /**
     * 进入会员中心
     */
    @RequestMapping("/showCenter")
    public ModelAndView showCenter(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        // 查询当前会员的头像
        Attache headImg = attacheService.findHeadImageByUserId(session);

        List<Attache> lifeImgs = attacheService.findLifeImageByUserId(session);

        modelAndView.addObject("headImg", headImg);
        modelAndView.addObject("lifeImgs", lifeImgs);
        modelAndView.setViewName("center");

        return modelAndView;
    }

    /**
     * 前台  密码会员修改
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String userId, String newPassword) {
        iUserService.modifyPassword(userId, newPassword);

        return "redirect:logout";
    }

    @ResponseBody
    @RequestMapping("/regist")
    public ResponseResult regist(User user, HttpSession session) {
        ResponseResult result = new ResponseResult();

        try {
            iUserService.regist(user);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (UserExistException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * (前台) 个人中心 根据会员id 修改会员 基本资料(会员的联系电话以及联系地址 )
     */
    @RequestMapping("/modifyUser")
    public String modifyUser(String userId, String phone, String address, HttpSession session) {
        iUserService.modifyUser(userId, phone, address);

        User user = (User) session.getAttribute("user");

        user.setPassword(address);
        user.setPhone(phone);

        return "redirect:showCenter";
    }

}
