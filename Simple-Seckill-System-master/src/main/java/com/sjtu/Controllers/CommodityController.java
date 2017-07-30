package com.sjtu.Controllers;

import com.sjtu.Objects.Commodity;
import com.sjtu.Objects.CommodityWrapper;
import com.sjtu.Services.CommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Sophie on 2017/6/29.
 */
@RestController
@RequestMapping("/seckill")
public class CommodityController {
    private static final Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityService;

    @RequestMapping("/getCommodityById")
    public Commodity getCommodity(HttpServletRequest request){
        String commodity_id = request.getParameter("commodity_id");
        logger.info("从数据库读取特定Commodity " + commodity_id);
        return commodityService.getCommodity(commodity_id);
    }

    @RequestMapping("/getCommodityAll")
    public List<CommodityWrapper> getStus(){
        logger.info("从数据库读取Commodity集合");
        return commodityService.getList();
    }
}
