package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-03.
 */
public class WalletModel implements Serializable {

    /**
     * nickname : 18306043085
     * head :
     * money : 0
     * wait_money : 0
     * frozen_money : 0
     * total_money : 0
     * withdrawal_money : 0
     */

    private String nickname;
    private String head;
    private String money;
    private String wait_money;
    private String frozen_money;
    private String total_money;
    private String withdrawal_money;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWait_money() {
        return wait_money;
    }

    public void setWait_money(String wait_money) {
        this.wait_money = wait_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getWithdrawal_money() {
        return withdrawal_money;
    }

    public void setWithdrawal_money(String withdrawal_money) {
        this.withdrawal_money = withdrawal_money;
    }
}
