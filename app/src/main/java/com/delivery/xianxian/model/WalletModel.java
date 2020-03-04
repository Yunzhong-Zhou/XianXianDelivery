package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-03.
 */
public class WalletModel implements Serializable {
    /**
     * nickname : 阿斯顿马丁
     * head : /upload/head/2019-11-27/718d5be3ee1b187809699e330fd6d1f3.jpeg
     * money : 6296.00
     * coupon_list : [{"title":"专车8折优惠卷","type_text":"专车","type":1,"money":"8折","money_type":1,"status_text":"已使用","status":2},{"title":"专车50元优惠卷","type_text":"专车","type":1,"money":"50","money_type":2,"status_text":"已使用","status":2}]
     */

    private String nickname;
    private String head;
    private String money;
    private List<CouponListBean> coupon_list;

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

    public List<CouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<CouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }

    public static class CouponListBean {
        /**
         * title : 专车8折优惠卷
         * type_text : 专车
         * type : 1
         * money : 8折
         * money_type : 1
         * status_text : 已使用
         * status : 2
         */

        private String title;
        private String type_text;
        private int type;
        private String money;
        private int money_type;
        private String status_text;
        private int status;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getMoney_type() {
            return money_type;
        }

        public void setMoney_type(int money_type) {
            this.money_type = money_type;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
