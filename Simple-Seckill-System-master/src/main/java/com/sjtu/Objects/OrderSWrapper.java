package com.sjtu.Objects;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Sophie on 2017/6/30.
 */
public class OrderSWrapper {
    private String order_id;
    private String user_id;
    private String commodity_id;
    private String timestamp;

    public OrderSWrapper() {
        //Timestamp now = new Timestamp(System.currentTimeMillis());
        //this.timestamp = now;
    };

    public String getOrder_id() {return order_id; }
    public void setOrder_id(String order_id) {this.order_id = order_id; }

    public String getUser_id() {return user_id; }
    public void setUser_id(String user_id) {this.user_id = user_id; }

    public String getCommodity_id() {return commodity_id; }
    public void setCommodity_id(String commodity_id) {this.commodity_id = commodity_id; }

    public String getTimestamp() {return timestamp;}
    public void setTimestamp(String timestamp) {
        //timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = timestamp;
    }
}
