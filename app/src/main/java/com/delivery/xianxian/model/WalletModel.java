package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-03.
 */
public class WalletModel implements Serializable {
    /**
     * nickname : 183****3089
     * head : /upload/head/7.png
     * money : 0
     * coupon_list : [{"id":1,"title":"专车卷","money":"50","status":"正常","expired_at":"8天5小时","image":"a.jpg"}]
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
         * id : 1
         * title : 专车卷
         * money : 50
         * status : 正常
         * expired_at : 8天5小时
         * image : a.jpg
         */

        private String id;
        private String title;
        private String money;
        private String status;
        private String expired_at;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(String expired_at) {
            this.expired_at = expired_at;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
