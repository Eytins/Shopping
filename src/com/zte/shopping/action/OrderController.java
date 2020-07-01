package com.zte.shopping.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.Order;
import com.zte.shopping.entity.User;
import com.zte.shopping.service.IOrderService;
import com.zte.shopping.util.ParameterUtil;
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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /**
     * 生成订单信息以及该订单名下的所有明细信息
     * 添加一个订单
     * 添加多个明细
     */
    @ResponseBody
    @RequestMapping("/createOrder")
    public String createOrder(HttpSession session) {
        CartVo cart = (CartVo) session.getAttribute("cart");
        User   user = (User) session.getAttribute("user");

        String no = orderService.createOrder(user, cart);

        session.setAttribute("cart", new CartVo());

        return no;
    }

    /**
     * "我的订单"
     * 查询该会员下的订单信息
     */
    @RequestMapping("/findAll")
    public ModelAndView findAll(String pageNo, String pageSize, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) session.getAttribute("user");

        if (ParameterUtil.isnull(pageNo)) {
            pageNo = DictConstant.PAGE_NO;
        }

        if (ParameterUtil.isnull(pageSize)) {
            pageSize = DictConstant.PAGE_SIZE;
        }

        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        // 查询某个会员 下的所有订单
        List<Order> orderList = orderService.findByUserId(user);

        PageInfo<Order> pageOrderList = new PageInfo<Order>(orderList);

        modelAndView.addObject("pageOrderList", pageOrderList);

        modelAndView.setViewName("myOrders");  // 跳转到/WEB-INF/pages/myOrders.jsp 页面

        return modelAndView;
    }

    /**
     * 根据订单号  查询订单信息
     */
    @RequestMapping("/findDetail")
    public ModelAndView findDetail(String orderId) {
        ModelAndView modelandview = new ModelAndView();

        // 根据订单id  查询该订单的详情
        Order order = orderService.findByOrderId(orderId);

        modelandview.addObject("order", order);

        modelandview.setViewName("orderDetail");

        return modelandview;
    }
}
