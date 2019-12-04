package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-11-21.
 */
public class InvoiceModel implements Serializable {
    /**
     * t_indent_id : 10
     * sn : T2019112610
     * use_type : 1
     * use_type_text : 专车
     * created_at : 2019-11-26 13:55:08
     * start : 重庆市南岸区南坪街道天福克拉广场
     * end : 重庆市南岸区南坪街道天福克拉广场
     * price : 18.00
     */

    private String t_indent_id;
    private String sn;
    private int use_type;
    private String use_type_text;
    private String created_at;
    private String start;
    private String end;
    private String price;
    private String tax_point;
    private String tax_amount;

    public String getTax_point() {
        return tax_point;
    }

    public void setTax_point(String tax_point) {
        this.tax_point = tax_point;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    private int isgouxuan;

    public int getIsgouxuan() {
        return isgouxuan;
    }

    public void setIsgouxuan(int isgouxuan) {
        this.isgouxuan = isgouxuan;
    }

    public String getT_indent_id() {
        return t_indent_id;
    }

    public void setT_indent_id(String t_indent_id) {
        this.t_indent_id = t_indent_id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getUse_type() {
        return use_type;
    }

    public void setUse_type(int use_type) {
        this.use_type = use_type;
    }

    public String getUse_type_text() {
        return use_type_text;
    }

    public void setUse_type_text(String use_type_text) {
        this.use_type_text = use_type_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
