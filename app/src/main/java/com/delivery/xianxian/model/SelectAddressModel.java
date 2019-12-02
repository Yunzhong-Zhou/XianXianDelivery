package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-15.
 */
public class SelectAddressModel implements Serializable {
    /**
     * often_used : [{"id":5,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-1","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:26"},{"id":6,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-2","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:36"},{"id":7,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-3","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:49"}]
     * history : [{"id":5,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-1","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:26"},{"id":6,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-2","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:36"},{"id":7,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-3","lat":"29.529064","lng":"106.563828","mobile":"17189991141","name":"yang","created_at":"2019-11-26 12:33:49"}]
     * city : 重庆
     */

    private String city;
    private List<OftenUsedBean> often_used;
    private List<HistoryBean> history;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<OftenUsedBean> getOften_used() {
        return often_used;
    }

    public void setOften_used(List<OftenUsedBean> often_used) {
        this.often_used = often_used;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class OftenUsedBean {
        /**
         * id : 5
         * addr : 重庆市南岸区南坪街道天福克拉广场
         * addr_detail : 1-1
         * lat : 29.529064
         * lng : 106.563828
         * mobile : 17189991141
         * name : yang
         * created_at : 2019-11-26 12:33:26
         */

        private String id;
        private String addr;
        private String addr_detail;
        private String lat;
        private String lng;
        private String mobile;
        private String name;
        private String created_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class HistoryBean {
        /**
         * id : 5
         * addr : 重庆市南岸区南坪街道天福克拉广场
         * addr_detail : 1-1
         * lat : 29.529064
         * lng : 106.563828
         * mobile : 17189991141
         * name : yang
         * created_at : 2019-11-26 12:33:26
         */

        private String id;
        private String addr;
        private String addr_detail;
        private String lat;
        private String lng;
        private String mobile;
        private String name;
        private String created_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
