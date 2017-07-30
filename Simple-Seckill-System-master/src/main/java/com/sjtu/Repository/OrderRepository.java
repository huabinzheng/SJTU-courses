package com.sjtu.Repository;

import com.sjtu.Objects.OrderS;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhenghb on 2017/7/8.
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderS, String> {
    OrderS findOne(String order_id);
    List<OrderS> findAll();
    OrderS save(OrderS s);
    void deleteAll();
}
