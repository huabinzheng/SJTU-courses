package com.sjtu.Repository;

import com.sjtu.Objects.Commodity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

/**
 * Created by zhenghb on 2017/7/8.
 */
@Repository
public interface CommodityRepository extends JpaRepository<Commodity, String> {
    Commodity findOne(String commodity_id);
    List<Commodity> findAll();
    Commodity save(Commodity commodity);
    void deleteAll();
}
