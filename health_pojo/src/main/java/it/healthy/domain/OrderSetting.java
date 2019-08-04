package it.healthy.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderSetting implements Serializable {
    private Integer id              ;
    private Date orderDate;
    private Integer number          ;
    private Integer reservations    ;

    public OrderSetting(Date order, Integer number) {
        this.orderDate = order;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrder(Date order) {
        this.orderDate = order;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }

    public OrderSetting() {
    }
}
