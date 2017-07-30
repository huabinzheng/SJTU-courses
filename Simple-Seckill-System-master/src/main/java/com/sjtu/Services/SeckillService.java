package com.sjtu.Services;


import com.sjtu.Objects.*;

import com.sjtu.Repository.CommodityRepository;
import com.sjtu.Repository.OrderRepository;
import com.sjtu.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by zhenghb on 2017/7/2.
 */
@Service
public class SeckillService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;

    @Transactional
    public SuccessSeckillResult getSuccessResult(User user, Commodity commodity){
        OrderS order = new OrderS();
        order.setUser_id(user.getUser_id());
        order.setCommodity_id(commodity.getCommodity_id());
        orderService.save(order);

        SuccessSeckillResult successResult = new SuccessSeckillResult();
        successResult.setResult(1);
        successResult.setOrder_id(order.getOrder_id());
        successResult.setUser_id(user.getUser_id());
        successResult.setCommodity_id(commodity.getCommodity_id());

        user.setAccount_balance(user.getAccount_balance() - commodity.getUnit_price());
        userService.save(user);
        commodity.setQuantity(commodity.getQuantity() - 1);
        commodityService.save(commodity);

        return successResult;
    }

    @Transactional
    public BaseSeckillResult getFailResult(String user_id){
        BaseSeckillResult result = new BaseSeckillResult();
        result.setResult(0);
        result.setUser_id(user_id);
        result.setOrder_id(System.currentTimeMillis() + user_id);
        return result;
    }
}
