package com.sjtu.Objects;

import java.sql.Timestamp;

/**
 * Created by Sophie on 2017/6/29.
 */
public class OrderF {
    private String order_id;
    private String user_id;
    private Timestamp timestamp;

    public OrderF() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.timestamp = now;
    };

    public String getOrder_id() {return order_id; }
    public void setOrder_id(String order_id) {this.order_id = order_id; }

    public String getUser_id() {return user_id; }
    public void setUser_id(String user_id) {this.user_id = user_id; }

    public Timestamp getTimestamp() {return timestamp;}
    public void setTimestamp(Timestamp timestamp) {
        timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = timestamp;
    }
}
