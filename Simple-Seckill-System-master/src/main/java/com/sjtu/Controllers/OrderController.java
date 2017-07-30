package com.sjtu.Controllers;

import com.sjtu.Objects.OrderSWrapper;
import com.sjtu.Services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Sophie on 2017/6/30.
 */
@RestController
@RequestMapping("/seckill")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping("/getOrderById")
    public OrderSWrapper getCommodity(HttpServletRequest request){
        String order_id = request.getParameter("order_id");
        logger.info("从数据库读取特定Order " + order_id);
        return orderService.getOrderSWrapperById(order_id);
    }

    @RequestMapping("/getOrderAll")
    public List<OrderSWrapper> getStus(){
        logger.info("从数据库读取Order集合");
        //List<OrderS> list = orderService.getList();
        //for(OrderS temp:list) {
        //    System.out.println(temp.getTimestamp());
        //}
        return orderService.getList();
    }
}
