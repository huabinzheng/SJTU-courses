package com.sjtu.Services;

import com.sjtu.Objects.User;
import com.sjtu.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenghb on 2017/7/8.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @CachePut(value = "user", key = "#u.user_id")
    public User save(User u) {
        User user = userRepository.save(u);
        return user;
    }

    @Cacheable(value = "user", key = "#user_id")
    public User getUserById(String user_id){
        return userRepository.findOne(user_id);
    }

    @Cacheable(value = "user")
    public List<User> getUserAll(){
        return userRepository.findAll();
    }

    @CacheEvict(value = "user",allEntries = true)
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
