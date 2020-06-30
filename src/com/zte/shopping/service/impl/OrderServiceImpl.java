package com.zte.shopping.service.impl;

import com.zte.shopping.constant.DictConstant;
import com.zte.shopping.entity.Item;
import com.zte.shopping.entity.Order;
import com.zte.shopping.entity.Sequence;
import com.zte.shopping.entity.User;
import com.zte.shopping.mapper.IItemMapper;
import com.zte.shopping.mapper.IOrderMapper;
import com.zte.shopping.mapper.ISequenceMapper;
import com.zte.shopping.service.IOrderService;
import com.zte.shopping.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Eytins
 */

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderMapper orderDao;

    @Autowired
    private IItemMapper itemDao;

    @Autowired
    private ISequenceMapper sequenceDao;

    /**
     * 生成订单信息以及该订单名下的所有明细信息
     * 添加一个订单
     * 添加多个明细
     */
    public String createOrder(User user, CartVo cartVo) {
        Order order = new Order();
        order.setPrice(cartVo.getPrice());
        order.setUser(user);

        // 生成订单号
        // 订单号生成规则:  DD + 年月日  + 序列化
        // 根据订单号前缀去Sequence表中查询对应信息
        // 其中  DictConstant.ORDER_NO_PREFIX 表示   订单编号前缀
        Sequence selectSequence = sequenceDao.selectByName(DictConstant.ORDER_NO_PREFIX);

        // 若不存在,则新建
        if (selectSequence == null) {
            Sequence sequence = new Sequence();

            sequence.setName(DictConstant.ORDER_NO_PREFIX);
            sequence.setValue(DictConstant.ORDER_NO_SEQUENCE_MIN);

            sequenceDao.insertSequence(sequence);

            order.setNo(DictConstant.ORDER_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequence.getValue());
        } else {
            // 若存在,则修改
            String value = selectSequence.getValue();

            if (DictConstant.ORDER_NO_SEQUENCE_MAX.equals(value)) {
                // 如果原来的序列号达到了最大值
                // 则初始化为最小值,从头开始
                value = DictConstant.ORDER_NO_SEQUENCE_MIN;
            } else {
                // 将原来的序列号 + 1
                value = String.format("%06d", Integer.parseInt(value) + 1);
            }

            selectSequence.setValue(value);

            sequenceDao.updateSequenceValue(DictConstant.ORDER_NO_PREFIX, value);

            order.setNo(DictConstant.ORDER_NO_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + value);
        }


        // 生成订单信息
        orderDao.insertOrder(order);

        // 对该订单名下的所有的明细信息进行保存
        // 购物车中每一条明细对应一条数据

        List<Item> items = cartVo.getItems();
        for (Item item : items) {
            item.setOrder(order);

            itemDao.insertItem(item);
        }

        return order.getNo();
    }

    /**
     * 查询某个会员 下的所有订单
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Order> findByUserId(User user) {
        // 根据userId查询对应的订单信息

        return orderDao.selectByUserId(user.getUserId());
    }

    /**
     * 根据订单id  查询该订单的详情
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Order findByOrderId(String orderId) {
        return orderDao.selectByOrderId(Integer.parseInt(orderId));
    }
}
