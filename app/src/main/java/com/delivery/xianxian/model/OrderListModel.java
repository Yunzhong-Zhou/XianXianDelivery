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
         * id : 93
         * sn : T2020022693
         * created_at : 2020-02-26 16:48
         * use_type : 专车
         * use_type_id : 1
         * car_type : 面包车
         * status : 6
         * status_text : 转单
         * temperature :
         * start_addr : 重庆市南岸区南坪街道天福克拉广场
         * end_addr : 重庆市南岸区南坪街道天福克拉广场
         * remark :
         * price : 48
         * is_plan : 2
         * plan_time : 2020-02-26 16:48
         * addr_list : [{"type":1,"number":1,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-1","name":"yang","mobile":"17189991141","lat":"29.529064","lng":"106.563828","arrive_time":"2020-03-03 10:56:24","leave_time":"2020-03-03 10:56:24","mileage":3059,"pre_time":3059,"status":"4","status_text":"已装货 运输中","other":"","is_end":2,"is_show":2},{"type":2,"number":2,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"1-2","name":"yang","mobile":"17189991141","lat":"29.529064","lng":"106.563828","arrive_time":"未到达","leave_time":"未到达","mileage":1,"pre_time":1,"status":"6","status_text":"","other":"","is_end":1,"is_show":2}]
         */

        private String id;
        private String sn;
        private String created_at;
        private String use_type;
        private int use_type_id;
        private String car_type;
        private int status;
        private String status_text;
        private int is_attach_fee;
        private String temperature;
        private String start_addr;
        private String end_addr;
        private String remark;
        private String price;
        private int is_plan;
        private String plan_time;
        private List<AddrListBean> addr_list;

        public int getIs_attach_fee() {
            return is_attach_fee;
        }

        public void setIs_attach_fee(int is_attach_fee) {
            this.is_attach_fee = is_attach_fee;
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

        public int getUse_type_id() {
            return use_type_id;
        }

        public void setUse_type_id(int use_type_id) {
            this.use_type_id = use_type_id;
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

        public List<AddrListBean> getAddr_list() {
            return addr_list;
        }

        public void setAddr_list(List<AddrListBean> addr_list) {
            this.addr_list = addr_list;
        }

        public static class AddrListBean {
            /**
             * type : 1
             * number : 1
             * addr : 重庆市南岸区南坪街道天福克拉广场
             * addr_detail : 1-1
             * name : yang
             * mobile : 17189991141
             * lat : 29.529064
             * lng : 106.563828
             * arrive_time : 2020-03-03 10:56:24
             * leave_time : 2020-03-03 10:56:24
             * mileage : 3059
             * pre_time : 3059
             * status : 4
             * status_text : 已装货 运输中
             * other :
             * is_end : 2
             * is_show : 2
             */

            private int type;
            private String number;
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
            private String status;
            private String status_text;
            private String other;
            private int is_end;
            private int is_show;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public int getIs_end() {
                return is_end;
            }

            public void setIs_end(int is_end) {
                this.is_end = is_end;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }
        }
    }
}
