package com.sjtu.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sophie on 2017/6/29.
 */
@Entity
@Table(name = "user")
public class User{
    @Id
    private String user_id;

    @Column(name = "user_name")
    private String user_name;
    @Column(name = "account_balance")
    private double account_balance;

    public User() {}

    public String getUser_id() {return user_id; }
    public void setUser_id(String user_id) {this.user_id = user_id; }

    public String getUser_name() {return user_name; }
    public void setUser_name(String user_name) {this.user_name = user_name; }

    public Double getAccount_balance() {return account_balance; }
    public void setAccount_balance(Double account_balance) {this.account_balance = account_balance; }
}
