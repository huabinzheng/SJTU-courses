package com.sjtu.Controllers;

import com.sjtu.Objects.BaseSeckillResult;
import com.sjtu.Objects.Commodity;
import com.sjtu.Objects.User;
import com.sjtu.Services.CommodityService;
import com.sjtu.Services.SeckillService;
import com.sjtu.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zhenghb on 2017/7/2.
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {
    private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);

        @Autowired
        private SeckillService seckillService;
        @Autowired
        private UserService userService;
        @Autowired
        private CommodityService commodityService;


        @RequestMapping("/seckill")
        public BaseSeckillResult getResult(HttpServletRequest request){
            String user_id = request.getParameter("user_id");
            String commodity_id = request.getParameter("commodity_id");
            logger.info("User " + user_id + "想要秒杀Commodity " + commodity_id);
            User user =  userService.getUserById(user_id);
            logger.info(user.getUser_id() + " " + user.getAccount_balance());
            Commodity commodity = commodityService.getCommodity(commodity_id);
            logger.info(commodity.getCommodity_id() + " " + commodity.getQuantity());

            if (commodity.getQuantity() > 0 && user.getAccount_balance() > commodity.getUnit_price()) {
                return seckillService.getSuccessResult(user, commodity);
            } else {
                return seckillService.getFailResult(user_id);
            }
        }
}
