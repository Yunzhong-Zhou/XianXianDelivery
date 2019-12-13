package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-12-12.
 */
public class AddInvoiceModel implements Serializable {
    /**
     * price : 100
     * tax_point : 3
     * tax_amount : 97
     */

    private String price;
    private String tax_point;
    private String tax_amount;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

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
}
