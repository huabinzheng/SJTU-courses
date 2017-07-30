package com.sjtu.Objects;

/**
 * Created by zhenghb on 2017/6/29.
 */
public class CommodityWrapper{
    private String commodity_id;
    private String commodity_name;
    private Integer quantity;

    public CommodityWrapper() {};

    public String getCommodity_id() {return commodity_id; }
    public void setCommodity_id(String commodity_id) {this.commodity_id = commodity_id; }

    public String getCommodity_name() {return commodity_name; }
    public void setCommodity_name(String commodity_name) {this.commodity_name = commodity_name; }

    public Integer getQuantity() {return quantity; }
    public void setQuantity(Integer quantity) {this.quantity = quantity; }
}
