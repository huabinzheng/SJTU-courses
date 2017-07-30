package com.sjtu.Services;


import com.sjtu.Objects.OrderS;
import com.sjtu.Objects.OrderSWrapper;
import com.sjtu.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @CachePut(value = "order", key = "#o.order_id")
    public OrderS save(OrderS o) {
        OrderS order = orderRepository.save(o);
        return order;
    }

    public OrderSWrapper getOrderSWrapperById(String order_id) {
        OrderSWrapper order = new OrderSWrapper();
        OrderS orderS = getOrder(order_id);
        if (orderS != null) {
            order.setOrder_id(orderS.getOrder_id());
            order.setUser_id(orderS.getUser_id());
            order.setCommodity_id(orderS.getCommodity_id());
            Timestamp temp = orderS.getTimestamp();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = df.format(temp);
            order.setTimestamp(str);
            return order;
        } else return null;
    }


    @Cacheable(value = "order", key = "#order_id")
    public OrderS getOrder(String order_id){
        OrderS orderS = orderRepository.findOne(order_id);
        return orderS;
    }

    public List<OrderSWrapper> getList(){
        List<OrderS> orderSs = orderRepository.findAll();
        List<OrderSWrapper> result = new ArrayList<>();
        if (orderSs != null && orderSs.size() != 0) {
            for (OrderS orderS:orderSs) {
                OrderSWrapper order = new OrderSWrapper();
                order.setOrder_id(orderS.getOrder_id());
                order.setUser_id(orderS.getUser_id());
                order.setCommodity_id(orderS.getCommodity_id());
                Timestamp temp = orderS.getTimestamp();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = df.format(temp);
                order.setTimestamp(str);
                result.add(order);
            }
        }
        return result;
    }

    @CacheEvict(value = "order",allEntries = true)
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
