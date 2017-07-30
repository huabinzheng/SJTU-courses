package com.sjtu.Repository;

import com.sjtu.Objects.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by zhenghb on 2017/7/8.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findOne(String user_id);
    List<User> findAll();
    User save(User user);
    void deleteAll();
}
