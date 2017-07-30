package com.sjtu.Services;

import com.sjtu.Objects.Commodity;
import com.sjtu.Objects.User;
import com.sjtu.Repository.CommodityRepository;
import com.sjtu.Repository.OrderRepository;
import com.sjtu.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;


/**
 * Created by liulo on 2017/6/30.
 */
@Service
public class ImportTextService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private OrderService orderService;
    public String importText(String dataPath) throws IOException {
        File f = new File(dataPath);
        BufferedReader bf = new BufferedReader(new FileReader(f));
        String line;
        int flag = -1; //0: user; 1: commodity
        int userCount = 0;
        int commodityCount = 0;
        while(null!=(line=bf.readLine())) {
            String s[]=line.split(",");
            if (s.length == 1) {
                flag += 1;
            }
            if (flag == 0 && s.length == 3) {
                String user_id = s[0];
                String user_name = s[1];
                String account_balance = s[2];

                User user = new User();
                user.setUser_id(user_id);
                user.setUser_name(user_name);
                user.setAccount_balance(Double.parseDouble(account_balance));
                userService.save(user);
                //String insertUserSql = "INSERT INTO user values(?, ?, ?)";
                //jdbcTemplate.update(insertUserSql, user_id, user_name, account_balance);
                userCount ++;
            }
            if (flag == 1 && s.length == 4) {
                String commodity_id = s[0];
                String commodity_name = s[1];
                String quantity = s[2];
                String price = s[3];

                Commodity commodity = new Commodity();
                commodity.setCommodity_id(commodity_id);
                commodity.setCommodity_name(commodity_name);
                commodity.setQuantity(Integer.parseInt(quantity));
                commodity.setUnit_price(Double.parseDouble(price));
                commodityService.save(commodity);
                //String insertCommoditySql = "INSERT INTO commodity values(?, ?, ?, ?)";
                //jdbcTemplate.update(insertCommoditySql, commodity_id, commodity_name, quantity, price);
                commodityCount ++;
            }

        }
        return "导入了"+userCount+"条用户信息，"+commodityCount+"条商品信息";
    }

    public String clearDB() {
        //jdbcTemplate.execute("TRUNCATE TABLE user");
        //jdbcTemplate.execute("TRUNCATE TABLE commodity");
        userService.deleteAll();
        commodityService.deleteAll();
        orderService.deleteAll();
        return "clear over";
    }
}
