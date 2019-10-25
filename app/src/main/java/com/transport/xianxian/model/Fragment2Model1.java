package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-09-30.
 */
public class Fragment2Model1 implements Serializable {
    /**
     * id : 1
     * sn : 2018101511
     * created_at : 20191018
     * is_attach_fee : 0
     * user_type : 专车
     * send_time : 3.4小时
     * compare_time : 早1小时
     * now_state : 8月8日 12：00
     * now_state_action : 已装货
     * now_state_sub_action : 配送中
     * send_name : 赵小姐
     * send_mobile : 15873232323
     * industry : 餐饮
     * hx_username : 餐饮
     * send_head : a.jpg
     * addr_list : [{"type":2,"number":1,"addr":"重庆井口","addr_detail":"美丽阳光10-1","name":"张追踪","mobile":"18888888888","lat":"29.643795","lng":"106.447151","arrive_time":"2019-10-23 12:18","leave_time":"2019-10-23 12:18","mileage":"10000","pre_time":"160","statust":"运输中","status_text":"运输中","other":"搬五楼楼梯"},{"type":1,"number":2,"addr":"重庆南坪","addr_detail":"天福克拉10-1","name":"廓可","mobile":"15823629471","lat":"29.529205","lng":"106.56385","arrive_time":"2019-10-23 12:18","leave_time":"2019-10-23 12:18","mileage":"10000","pre_time":"160","statust":"运输中","status_text":"运输中","other":"搬五楼楼梯"}]
     * tag : ["专车","6吨","恒温","5.5方"]
     * goods_desc : [{"key":0,"val":"冰鲜"},{"key":1,"val":"总总量"},{"key":2,"val":"体积5.5方"}]
     * remark : 备注
     * price : 18000
     * price_detail : {"start":"10","milleage":"10"}
     */

    private String id;
    private String sn;
    private String created_at;
    private int is_attach_fee;
    private String user_type;
    private String send_time;
    private String compare_time;
    private String now_state;
    private String now_state_action;
    private String now_state_sub_action;
    private String send_name;
    private String send_mobile;
    private String industry;
    private String hx_username;
    private String send_head;
    private String remark;
    private String price;
    private PriceDetailBean price_detail;
    private List<AddrListBean> addr_list;
    private List<String> tag;
    private List<String> goods_desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIs_attach_fee() {
        return is_attach_fee;
    }

    public void setIs_attach_fee(int is_attach_fee) {
        this.is_attach_fee = is_attach_fee;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getCompare_time() {
        return compare_time;
    }

    public void setCompare_time(String compare_time) {
        this.compare_time = compare_time;
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

    public String getNow_state_sub_action() {
        return now_state_sub_action;
    }

    public void setNow_state_sub_action(String now_state_sub_action) {
        this.now_state_sub_action = now_state_sub_action;
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

    public String getHx_username() {
        return hx_username;
    }

    public void setHx_username(String hx_username) {
        this.hx_username = hx_username;
    }

    public String getSend_head() {
        return send_head;
    }

    public void setSend_head(String send_head) {
        this.send_head = send_head;
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

    public PriceDetailBean getPrice_detail() {
        return price_detail;
    }

    public void setPrice_detail(PriceDetailBean price_detail) {
        this.price_detail = price_detail;
    }

    public List<AddrListBean> getAddr_list() {
        return addr_list;
    }

    public void setAddr_list(List<AddrListBean> addr_list) {
        this.addr_list = addr_list;
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

    public static class PriceDetailBean {
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

    public static class AddrListBean {
        /**
         * type : 2
         * number : 1
         * addr : 重庆井口
         * addr_detail : 美丽阳光10-1
         * name : 张追踪
         * mobile : 18888888888
         * lat : 29.643795
         * lng : 106.447151
         * arrive_time : 2019-10-23 12:18
         * leave_time : 2019-10-23 12:18
         * mileage : 10000
         * pre_time : 160
         * statust : 运输中
         * status_text : 运输中
         * other : 搬五楼楼梯
         */

        private int type;
        private int number;
        private String addr;
        private String addr_detail;
        private String name;
        private String mobile;
        private String lat;
        private String lng;
        private String arrive_time;
        private String leave_time;
        private String mileage;
        private String pre_time;
        private String statust;
        private String status_text;
        private String other;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddr_detail() {
            return addr_detail;
        }

        public void setAddr_detail(String addr_detail) {
            this.addr_detail = addr_detail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getLeave_time() {
            return leave_time;
        }

        public void setLeave_time(String leave_time) {
            this.leave_time = leave_time;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getPre_time() {
            return pre_time;
        }

        public void setPre_time(String pre_time) {
            this.pre_time = pre_time;
        }

        public String getStatust() {
            return statust;
        }

        public void setStatust(String statust) {
            this.statust = statust;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}
