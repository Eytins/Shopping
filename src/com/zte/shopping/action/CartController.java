package com.zte.shopping.action;

import com.zte.shopping.constant.ResponseCodeConstant;
import com.zte.shopping.exception.RequestParameterException;
import com.zte.shopping.exception.UserNotLoginException;
import com.zte.shopping.service.ICartService;
import com.zte.shopping.util.ResponseResult;
import com.zte.shopping.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by Eytins
 */

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @RequestMapping("/show")
    public String show() {
        return "cart";
    }

    /**
     * 添加购物车
     */
    @ResponseBody
    @RequestMapping("/addCart")
    public ResponseResult addCart(String productId, HttpSession session) {
        ResponseResult result = new ResponseResult();

        CartVo cartVo = (CartVo) session.getAttribute("cart");

        try {
            cartService.addCart(cartVo, productId);

            result.setMessage("成功");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_SUCCESS);

        } catch (RequestParameterException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_REQUEST_PARAMETER_ERROR);

        } catch (UserNotLoginException e) {
            result.setMessage(e.getMessage());
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_LOGIN_TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("服务器内部异常");
            result.setResponseCode(ResponseCodeConstant.RESPONSE_CODE_FAIL);
        }

        return result;
    }

    /**
     * 修改 "我的购物车" 中 商品数量
     */
    @ResponseBody
    @RequestMapping("/modifyNum")
    public ResponseResult modifyNum(String productId, String num, HttpSession session) {
        ResponseResult result = new ResponseResult();

        CartVo cartVo = (CartVo) session.getAttribute("cart");

        try {
            // 修改 "我的购物车" 中 商品数量
            cartService.modifyNum(productId, num, cartVo);

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

    /**
     * "我的购物车"中根据商品ID删除"我的购物车"中的某个商品信息
     */
    @ResponseBody
    @RequestMapping("/removeByProductId")
    public ResponseResult removeByProductId(String productId, HttpSession session) {
        ResponseResult result = new ResponseResult();

        CartVo cartVo = (CartVo) session.getAttribute("cart");

        try {
            cartService.removeByProductId(productId, cartVo);

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

    /**
     * "我的购物车"中删除选中项(支持多个商品删除)
     */
    @ResponseBody
    @RequestMapping("/removeByProductIds")
    public ModelAndView removeByProductIds(String[] productIds, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        CartVo cartVo = (CartVo) session.getAttribute("cart");

        try {
            for (String productId : productIds) {
                cartService.removeByProductId(productId, cartVo);
            }
        } catch (RequestParameterException e) {
            modelAndView.addObject("cartMsg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("cartMsg", "服务器内部异常");
        }

        modelAndView.setViewName("redirect:show");

        return modelAndView;
    }

    /**
     * "我的购物车" 中删除我的购物车
     */
    @RequestMapping("/clearCart")
    public String clearCart(HttpSession session) {
        // 创建一个空的购物车  覆盖原来session中同名的key("cart")
        session.setAttribute("cart", new CartVo());
        // 跳转到cart.jsp页面
        return "cart";
    }

    /**
     * "我的购物车" 中  结算
     */
    @RequestMapping("/settleAccounts")
    public String settleAccounts() {
        // 跳转到order.jsp
        return "order";
    }
}
