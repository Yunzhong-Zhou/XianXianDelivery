package com.delivery.xianxian.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-23.
 */
public class OrderDetailsModel implements Serializable {
    /**
     * tindent : {"id":20,"sn":"T2019111920","created_at":1574161061,"use_type":"专车","car_type":"面包车","is_plan":2,"plan_time":"2019-11-19 18:57:41","status":1,"status_text":"未装货","driver_info":{"nickname":"18306043086","mobile":"18306043086","head":"/upload/head/1.png","comment_score":"0.00","card_number":"","hx_username":"18306043086"},"addr_list":[{"type":1,"number":1,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"阿斯顿","name":"yang","mobile":"17189991141","lat":"29.529091","lng":"106.563827","is_end":2,"arrive_time":1574249624,"leave_time":0,"mileage":4,"pre_time":4,"status":"3","status_text":"已到达 开始装货","other":""},{"type":2,"number":2,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"阿斯顿马丁","name":"yang","mobile":"17189991141","lat":"29.529087","lng":"106.56383","is_end":1,"arrive_time":0,"leave_time":0,"mileage":178,"pre_time":143,"status":"5","status_text":"前卸货","other":""}],"temperature":"","remark":"","other_tag":["小推车"],"pay_status":"已支付","total_price":"18.00","price_detail":[{"title":"里程费","price":"18.00"},{"title":"货主附加费","price":"0.00"},{"title":"司机附加费用","price":"0.00"},{"title":"其他费用","price":"0.00"}],"confirm_shipment_msg":{"id":41,"created_at":"2019-11-20 19:33:44"},"confirm_text":{"name":"确认装货","id":41}}
     */
    private TindentBean tindent;

    public TindentBean getTindent() {
        return tindent;
    }

    public void setTindent(TindentBean tindent) {
        this.tindent = tindent;
    }

    public static class TindentBean {
        /**
         * id : 20
         * sn : T2019111920
         * created_at : 1574161061
         * use_type : 专车
         * car_type : 面包车
         * is_plan : 2
         * plan_time : 2019-11-19 18:57:41
         * status : 1
         * status_text : 未装货
         * driver_info : {"nickname":"18306043086","mobile":"18306043086","head":"/upload/head/1.png","comment_score":"0.00","card_number":"","hx_username":"18306043086"}
         * addr_list : [{"type":1,"number":1,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"阿斯顿","name":"yang","mobile":"17189991141","lat":"29.529091","lng":"106.563827","is_end":2,"arrive_time":1574249624,"leave_time":0,"mileage":4,"pre_time":4,"status":"3","status_text":"已到达 开始装货","other":""},{"type":2,"number":2,"addr":"重庆市南岸区南坪街道天福克拉广场","addr_detail":"阿斯顿马丁","name":"yang","mobile":"17189991141","lat":"29.529087","lng":"106.56383","is_end":1,"arrive_time":0,"leave_time":0,"mileage":178,"pre_time":143,"status":"5","status_text":"前卸货","other":""}]
         * temperature :
         * remark :
         * other_tag : ["小推车"]
         * pay_status : 已支付
         * total_price : 18.00
         * price_detail : [{"title":"里程费","price":"18.00"},{"title":"货主附加费","price":"0.00"},{"title":"司机附加费用","price":"0.00"},{"title":"其他费用","price":"0.00"}]
         * confirm_shipment_msg : {"id":41,"created_at":"2019-11-20 19:33:44"}
         * confirm_text : {"name":"确认装货","id":41}
         */

        private String id;
        private String sn;
        private long wait_time;
        private String use_type;
        private String car_type;
        private int is_plan;
        private String plan_time;
        private int status;
        private String status_text;
        private DriverInfoBean driver_info;
        private String temperature;
        private String remark;
        private String pay_status;
        private String total_price;
        private String owner_fee;
        private String contacts_mobile;
        private ConfirmTextBean confirm_text;
        private List<AddrListBean> addr_list;
        private List<String> other_tag;
        private List<PriceDetailBean> price_detail;

        private String cancel_at;
        private String cancel_reason;
        /**
         * confirm_attach_data : {"id":32,"need_pay":1,"money":"1600","detail":[{"name":"逾时等候费","money":"200"},{"name":"路桥费","money":"300"},{"name":"搬运费","money":"500"},{"name":"阿斯顿","money":"600"}]}
         */

        private ConfirmAttachDataBean confirm_attach_data;

        public String getOwner_fee() {
            return owner_fee;
        }

        public void setOwner_fee(String owner_fee) {
            this.owner_fee = owner_fee;
        }

        public String getCancel_at() {
            return cancel_at;
        }

        public void setCancel_at(String cancel_at) {
            this.cancel_at = cancel_at;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContacts_mobile() {
            return contacts_mobile;
        }

        public void setContacts_mobile(String contacts_mobile) {
            this.contacts_mobile = contacts_mobile;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public long getWait_time() {
            return wait_time;
        }

        public void setWait_time(long wait_time) {
            this.wait_time = wait_time;
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

        public DriverInfoBean getDriver_info() {
            return driver_info;
        }

        public void setDriver_info(DriverInfoBean driver_info) {
            this.driver_info = driver_info;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public ConfirmTextBean getConfirm_text() {
            return confirm_text;
        }

        public void setConfirm_text(ConfirmTextBean confirm_text) {
            this.confirm_text = confirm_text;
        }

        public List<AddrListBean> getAddr_list() {
            return addr_list;
        }

        public void setAddr_list(List<AddrListBean> addr_list) {
            this.addr_list = addr_list;
        }

        public List<String> getOther_tag() {
            return other_tag;
        }

        public void setOther_tag(List<String> other_tag) {
            this.other_tag = other_tag;
        }

        public List<PriceDetailBean> getPrice_detail() {
            return price_detail;
        }

        public void setPrice_detail(List<PriceDetailBean> price_detail) {
            this.price_detail = price_detail;
        }

        public ConfirmAttachDataBean getConfirm_attach_data() {
            return confirm_attach_data;
        }

        public void setConfirm_attach_data(ConfirmAttachDataBean confirm_attach_data) {
            this.confirm_attach_data = confirm_attach_data;
        }

        public static class DriverInfoBean {
            /**
             * nickname : 18306043086
             * mobile : 18306043086
             * head : /upload/head/1.png
             * comment_score : 0.00
             * card_number :
             * hx_username : 18306043086
             */

            private String nickname;
            private String mobile;
            private String head;
            private String comment_score;
            private String card_number;
            private String hx_username;

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

            public String getCard_number() {
                return card_number;
            }

            public void setCard_number(String card_number) {
                this.card_number = card_number;
            }

            public String getHx_username() {
                return hx_username;
            }

            public void setHx_username(String hx_username) {
                this.hx_username = hx_username;
            }
        }

        public static class ConfirmTextBean {
            /**
             * name : 确认装货
             * id : 41
             */

            private String name;
            private String id;
            private String option;

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class AddrListBean {
            /**
             * type : 1
             * number : 1
             * addr : 重庆市南岸区南坪街道天福克拉广场
             * addr_detail : 阿斯顿
             * name : yang
             * mobile : 17189991141
             * lat : 29.529091
             * lng : 106.563827
             * is_end : 2
             * arrive_time : 1574249624
             * leave_time : 0
             * mileage : 4
             * pre_time : 4
             * status : 3
             * status_text : 已到达 开始装货
             * other :
             */

            private int type;
            private int number;
            private String addr;
            private String addr_detail;
            private String name;
            private String mobile;
            private String lat;
            private String lng;
            private int is_end;
            private int arrive_time;
            private int leave_time;
            private int mileage;
            private int pre_time;
            private String status;
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

            public int getIs_end() {
                return is_end;
            }

            public void setIs_end(int is_end) {
                this.is_end = is_end;
            }

            public int getArrive_time() {
                return arrive_time;
            }

            public void setArrive_time(int arrive_time) {
                this.arrive_time = arrive_time;
            }

            public int getLeave_time() {
                return leave_time;
            }

            public void setLeave_time(int leave_time) {
                this.leave_time = leave_time;
            }

            public int getMileage() {
                return mileage;
            }

            public void setMileage(int mileage) {
                this.mileage = mileage;
            }

            public int getPre_time() {
                return pre_time;
            }

            public void setPre_time(int pre_time) {
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
        }

        public static class PriceDetailBean {
            /**
             * title : 里程费
             * price : 18.00
             */

            private String title;
            private String price;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }


        public static class ConfirmAttachDataBean {
            /**
             * id : 32
             * need_pay : 1
             * money : 1600
             * detail : [{"name":"逾时等候费","money":"200"},{"name":"路桥费","money":"300"},{"name":"搬运费","money":"500"},{"name":"阿斯顿","money":"600"}]
             */

            @SerializedName("id")
            private String idX;
            private int need_pay;
            private String money;
            private List<DetailBean> detail;

            public String getIdX() {
                return idX;
            }

            public void setIdX(String idX) {
                this.idX = idX;
            }

            public int getNeed_pay() {
                return need_pay;
            }

            public void setNeed_pay(int need_pay) {
                this.need_pay = need_pay;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * name : 逾时等候费
                 * money : 200
                 */

                private String name;
                private String money;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }
            }
        }
    }
}
