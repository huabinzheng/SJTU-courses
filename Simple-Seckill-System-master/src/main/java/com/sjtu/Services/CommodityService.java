package com.sjtu.Services;


import com.sjtu.Objects.Commodity;
import com.sjtu.Objects.CommodityWrapper;
import com.sjtu.Repository.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityService {
    @Autowired
    CommodityRepository commodityRepository;

    @CachePut(value = "commodity", key = "#c.commodity_id")
    public Commodity save(Commodity c) {
        Commodity commodity = commodityRepository.save(c);
        return commodity;
    }

    @Cacheable(value = "commodity", key = "#commodity_id")
    public Commodity getCommodity(String commodity_id){
        return commodityRepository.findOne(commodity_id);
    }

    public List<CommodityWrapper> getList(){
        List<Commodity> commodities = commodityRepository.findAll();
        List<CommodityWrapper> result = new ArrayList<>();
        for (Commodity c:commodities) {
            CommodityWrapper commodity = new CommodityWrapper();
            commodity.setCommodity_id(c.getCommodity_id());
            commodity.setCommodity_name(c.getCommodity_name());
            commodity.setQuantity(c.getQuantity());
            result.add(commodity);
        }
        return result;
    }

    @CacheEvict(value = "commodity",allEntries = true)
    public void deleteAll() {
        commodityRepository.deleteAll();
    }
}
