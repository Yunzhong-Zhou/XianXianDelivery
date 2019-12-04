package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-22.
 */
public class AppraiseModel implements Serializable {
    private List<TagListBean> tag_list;
    /**
     * driver_info : {"nickname":"18306043086","mobile":"18306043086","head":"/upload/head/1.png","comment_score":"0.00","card_number":"","hx_username":"18306043086"}
     */

    private DriverInfoBean driver_info;
    /**
     * driver_info : {"nickname":"18306043086","mobile":"18306043086","head":"/upload/head/1.png","comment_score":"0.00","card_number":"","hx_username":"18306043086","money":"18.00","use_type":"专车"}
     */


    public List<TagListBean> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<TagListBean> tag_list) {
        this.tag_list = tag_list;
    }

    public DriverInfoBean getDriver_info() {
        return driver_info;
    }

    public void setDriver_info(DriverInfoBean driver_info) {
        this.driver_info = driver_info;
    }

    public static class TagListBean {
        /**
         * key : 1
         * val : 好评
         */

        private int key;
        private String val;
        private int isgouxuan;

        public int getIsgouxuan() {
            return isgouxuan;
        }

        public void setIsgouxuan(int isgouxuan) {
            this.isgouxuan = isgouxuan;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }


    public static class DriverInfoBean {
        private String nickname;
        private String mobile;
        private String head;
        private String comment_score;
        private String car_number;
        private String hx_username;
        private String money;
        private String use_type;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getComment_score() {
            return comment_score;
        }

        public void setComment_score(String comment_score) {
            this.comment_score = comment_score;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getHx_username() {
            return hx_username;
        }

        public void setHx_username(String hx_username) {
            this.hx_username = hx_username;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUse_type() {
            return use_type;
        }

        public void setUse_type(String use_type) {
            this.use_type = use_type;
        }
    }
}
