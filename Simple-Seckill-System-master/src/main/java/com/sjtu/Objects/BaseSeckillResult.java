package com.sjtu.Objects;

/**
 * Created by zhenghb on 2017/7/2.
 */
public class BaseSeckillResult {
    private int result;
    private String order_id;
    private String user_id;

    public BaseSeckillResult() {}

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
