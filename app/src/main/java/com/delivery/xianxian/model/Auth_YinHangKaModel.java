package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-18.
 */
public class Auth_YinHangKaModel implements Serializable {
    /**
     * bank_card :
     * bank_name :
     */

    private String bank_card;
    private String bank_name;

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
