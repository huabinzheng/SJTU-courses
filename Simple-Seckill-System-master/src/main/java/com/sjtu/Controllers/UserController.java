package com.sjtu.Controllers;

import com.sjtu.Objects.User;
import com.sjtu.Services.UserService;
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
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById")
    public User getUserById(HttpServletRequest request){
        String user_id = request.getParameter("user_id");
        logger.info("从数据库读取特定User " + user_id);
        return userService.getUserById(user_id);
    }

    @RequestMapping("/getUserAll")
    public List<User> getUserAll(){
        logger.info("从数据库读取User集合");
        return userService.getUserAll();
    }
}
