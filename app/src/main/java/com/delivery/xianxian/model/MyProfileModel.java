package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2018/2/10.
 */

public class MyProfileModel implements Serializable {
    /**
     * nickname : 阿斯顿马丁
     * mobile : 18306043086
     * head : /upload/head/24.png
     * industry :
     * industry_list : [{"key":1,"val":"餐饮"},{"key":2,"val":"生鲜"},{"key":3,"val":"批发"},{"key":4,"val":"冷饮"}]
     * is_certification : 2
     */

    private String nickname;
    private String mobile;
    private String head;
    private String industry;
    private int is_certification;
    private List<IndustryListBean> industry_list;

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getIs_certification() {
        return is_certification;
    }

    public void setIs_certification(int is_certification) {
        this.is_certification = is_certification;
    }

    public List<IndustryListBean> getIndustry_list() {
        return industry_list;
    }

    public void setIndustry_list(List<IndustryListBean> industry_list) {
        this.industry_list = industry_list;
    }

    public static class IndustryListBean {
        /**
         * key : 1
         * val : 餐饮
         */

        private int key;
        private String val;

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
}
