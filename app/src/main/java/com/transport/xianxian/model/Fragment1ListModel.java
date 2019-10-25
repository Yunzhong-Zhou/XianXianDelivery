package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-19.
 */
public class Fragment1ListModel implements Serializable {
    /**
     * id : 1
     * sn : 20190917
     * now_state : 8月8日 12：00
     * now_state_action : 装货
     * addr_list : [{"type":2,"number":1,"addr":"重庆井口","addr_detail":"美丽阳光20-1","name":"张追踪","mobile":"18888888888","lat":"29.661435","lng":"106.448628"},{"type":1,"number":2,"addr":"重庆南坪","addr_detail":"天福克拉10-1","name":"廓可","mobile":"15823629471","lat":"29.524504","lng":"106.579996"}]
     * tag : ["专车","6吨","恒温","5.5方"]
     * remark : 装车时间有限
     * created_at : 2019-10-17 15:00
     * price : 1800
     */

    private String id;
    private String sn;
    private String now_state;
    private String now_state_action;
    private String remark;
    private String created_at;
    private String price;
    private List<AddrListBean> addr_list;
    private List<String> tag;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public static class AddrListBean {
        /**
         * type : 2
         * number : 1
         * addr : 重庆井口
         * addr_detail : 美丽阳光20-1
         * name : 张追踪
         * mobile : 18888888888
         * lat : 29.661435
         * lng : 106.448628
         */

        private int type;
        private int number;
        private String addr;
        private String addr_detail;
        private String name;
        private String mobile;
        private String lat;
        private String lng;

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
    }
}
