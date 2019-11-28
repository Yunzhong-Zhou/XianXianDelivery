package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-11-26.
 */
public class MoneyListModel implements Serializable {
    /**
     * id : 3
     * type : 2
     * title : 货主充值
     * money : 1000.00
     * remark : 货主充值，后台审核
     * created_at : 2019-11-27 16:58:32
     */

    private int id;
    private int type;
    private int out_in;
    private String title;
    private String money;
    private String remark;
    private String created_at;

    public int getOut_in() {
        return out_in;
    }

    public void setOut_in(int out_in) {
        this.out_in = out_in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
