package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-19.
 */
public class ConfirmOrderModel implements Serializable {
    /**
     * id : 4
     * total_price : 118
     * useable_money : 0.00
     * pay_type_list : [{"type":1,"icon":"/img/pay/balance.png","title":"余额支付","sub_title":"可用余额0.00元"},[{"type":3,"icon":"/img/pay/zfb.png","title":"支付宝","sub_title":""},{"type":2,"icon":"/img/pay/wx.png","title":"微信","sub_title":""}]]
     */

    private String id;
    private String total_price;
    private String useable_money;
    private List<PayTypeListBean> pay_type_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUseable_money() {
        return useable_money;
    }

    public void setUseable_money(String useable_money) {
        this.useable_money = useable_money;
    }

    public List<PayTypeListBean> getPay_type_list() {
        return pay_type_list;
    }

    public void setPay_type_list(List<PayTypeListBean> pay_type_list) {
        this.pay_type_list = pay_type_list;
    }

    public static class PayTypeListBean {
        /**
         * type : 1
         * icon : /img/pay/balance.png
         * title : 余额支付
         * sub_title : 可用余额0.00元
         */

        private String type;
        private String icon;
        private String title;
        private String sub_title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }
    }
}
