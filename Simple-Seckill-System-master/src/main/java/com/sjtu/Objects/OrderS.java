package com.sjtu.Objects;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Sophie on 2017/6/29.
 */
@Entity
@Table(name = "orders")
public class OrderS {
    @Id
    @GeneratedValue
    private String order_id;

    @Column(name = "user_id")
    private String user_id;
    @Column(name = "commodity_id")
    private String commodity_id;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public OrderS() {
        //Timestamp now = new Timestamp(System.currentTimeMillis());
        //this.timestamp = now;
    };

    public String getOrder_id() {return order_id; }
    public void setOrder_id(String order_id) {this.order_id = order_id; }

    public String getUser_id() {return user_id; }
    public void setUser_id(String user_id) {this.user_id = user_id; }

    public String getCommodity_id() {return commodity_id; }
    public void setCommodity_id(String commodity_id) {this.commodity_id = commodity_id; }

    public Timestamp getTimestamp() {return timestamp;}
    public void setTimestamp(Timestamp timestamp) {
        //timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = timestamp;
    }
}
