package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-09-30.
 */
public class Fragment2Model1 implements Serializable {
    /**
     * id : 1
     * now_state : 8月8日 12：00
     * now_state_action : 装货
     * send_name : 赵小姐
     * send_mobile : 15873232323
     * industry : 餐饮
     * sn : x20191018
     * created_at : 20191018
     * take_time : 8月8日 12:00
     * send_addr : 广东省创业元二号
     * send_addr_detail : 凡物科技
     * send_addr_distance : 10km
     * send_lat :
     * send_lng :
     * next_recive_addr : 重庆南坪
     * next_recive_addr_detail : 天福克拉广场
     * next_recive_distance : 2km
     * next_recive_lat :
     * next_recive_lng :
     * tag : ["专车","6吨","恒温","5.5方"]
     * goods_desc : ["冰鲜","总总量","体积5.5方"]
     * remark : 备注
     * price : 18000
     * fee : {"start":"10","milleage":"10"}
     */

    private String id;
    private String now_state;
    private String now_state_action;
    private String send_name;
    private String send_mobile;
    private String industry;
    private String sn;
    private String created_at;
    private String take_time;
    private String send_addr;
    private String send_addr_detail;
    private String send_addr_distance;
    private String send_lat;
    private String send_lng;
    private String next_recive_addr;
    private String next_recive_addr_detail;
    private String next_recive_distance;
    private String next_recive_lat;
    private String next_recive_lng;
    private String remark;
    private String price;
    private FeeBean fee;
    private List<String> tag;
    private List<String> goods_desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNow_state() {
        return now_state;
    }

    public void setNow_state(String now_state) {
        this.now_state = now_state;
    }

    public String getNow_state_action() {
        return now_state_action;
    }

    public void setNow_state_action(String now_state_action) {
        this.now_state_action = now_state_action;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_mobile() {
        return send_mobile;
    }

    public void setSend_mobile(String send_mobile) {
        this.send_mobile = send_mobile;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTake_time() {
        return take_time;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }

    public String getSend_addr() {
        return send_addr;
    }

    public void setSend_addr(String send_addr) {
        this.send_addr = send_addr;
    }

    public String getSend_addr_detail() {
        return send_addr_detail;
    }

    public void setSend_addr_detail(String send_addr_detail) {
        this.send_addr_detail = send_addr_detail;
    }

    public String getSend_addr_distance() {
        return send_addr_distance;
    }

    public void setSend_addr_distance(String send_addr_distance) {
        this.send_addr_distance = send_addr_distance;
    }

    public String getSend_lat() {
        return send_lat;
    }

    public void setSend_lat(String send_lat) {
        this.send_lat = send_lat;
    }

    public String getSend_lng() {
        return send_lng;
    }

    public void setSend_lng(String send_lng) {
        this.send_lng = send_lng;
    }

    public String getNext_recive_addr() {
        return next_recive_addr;
    }

    public void setNext_recive_addr(String next_recive_addr) {
        this.next_recive_addr = next_recive_addr;
    }

    public String getNext_recive_addr_detail() {
        return next_recive_addr_detail;
    }

    public void setNext_recive_addr_detail(String next_recive_addr_detail) {
        this.next_recive_addr_detail = next_recive_addr_detail;
    }

    public String getNext_recive_distance() {
        return next_recive_distance;
    }

    public void setNext_recive_distance(String next_recive_distance) {
        this.next_recive_distance = next_recive_distance;
    }

    public String getNext_recive_lat() {
        return next_recive_lat;
    }

    public void setNext_recive_lat(String next_recive_lat) {
        this.next_recive_lat = next_recive_lat;
    }

    public String getNext_recive_lng() {
        return next_recive_lng;
    }

    public void setNext_recive_lng(String next_recive_lng) {
        this.next_recive_lng = next_recive_lng;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FeeBean getFee() {
        return fee;
    }

    public void setFee(FeeBean fee) {
        this.fee = fee;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(List<String> goods_desc) {
        this.goods_desc = goods_desc;
    }

    public static class FeeBean {
        /**
         * start : 10
         * milleage : 10
         */

        private String start;
        private String milleage;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getMilleage() {
            return milleage;
        }

        public void setMilleage(String milleage) {
            this.milleage = milleage;
        }
    }
}
