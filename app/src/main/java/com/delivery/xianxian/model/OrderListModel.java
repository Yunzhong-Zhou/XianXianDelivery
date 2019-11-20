package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-09-30.
 */
public class OrderListModel implements Serializable {
    private List<TindentListBean> tindent_list;

    public List<TindentListBean> getTindent_list() {
        return tindent_list;
    }

    public void setTindent_list(List<TindentListBean> tindent_list) {
        this.tindent_list = tindent_list;
    }

    public static class TindentListBean {
        /**
         * id : 16
         * sn : T2019111916
         * created_at : 2019-11-19 17:48
         * use_type : 专车
         * car_type : 面包车
         * status : 0
         * status_text : 未分配
         * temperature :
         * start_addr : 重庆市南岸区南坪街道天福克拉广场
         * end_addr : 重庆市南岸区南坪街道天福克拉广场
         * price : 18.00
         * is_plan : 2
         * plan_time : 2019-11-19 17:48
         */

        private String id;
        private String sn;
        private String created_at;
        private int use_type_id;
        private String use_type;
        private String car_type;
        private int status;
        private String status_text;
        private String temperature;
        private String start_addr;
        private String end_addr;
        private String price;
        private int is_plan;
        private String plan_time;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getUse_type_id() {
            return use_type_id;
        }

        public void setUse_type_id(int use_type_id) {
            this.use_type_id = use_type_id;
        }

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

        public String getUse_type() {
            return use_type;
        }

        public void setUse_type(String use_type) {
            this.use_type = use_type;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getStart_addr() {
            return start_addr;
        }

        public void setStart_addr(String start_addr) {
            this.start_addr = start_addr;
        }

        public String getEnd_addr() {
            return end_addr;
        }

        public void setEnd_addr(String end_addr) {
            this.end_addr = end_addr;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getIs_plan() {
            return is_plan;
        }

        public void setIs_plan(int is_plan) {
            this.is_plan = is_plan;
        }

        public String getPlan_time() {
            return plan_time;
        }

        public void setPlan_time(String plan_time) {
            this.plan_time = plan_time;
        }
    }

}
