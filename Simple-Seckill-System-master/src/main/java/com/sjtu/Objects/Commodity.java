package com.sjtu.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sophie on 2017/6/29.
 */
@Entity
@Table(name = "commodity")
public class Commodity {
    @Id
    private String commodity_id;
    @Column(name = "commodity_name")
    private String commodity_name;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double unit_price;

    public Commodity() {};

    public String getCommodity_id() {return commodity_id; }
    public void setCommodity_id(String commodity_id) {this.commodity_id = commodity_id; }

    public String getCommodity_name() {return commodity_name; }
    public void setCommodity_name(String commodity_name) {this.commodity_name = commodity_name; }

    public Integer getQuantity() {return quantity; }
    public void setQuantity(Integer quantity) {this.quantity = quantity; }

    public Double getUnit_price() {return unit_price; }
    public void setUnit_price(Double unit_price) {this.unit_price = unit_price; }
}
