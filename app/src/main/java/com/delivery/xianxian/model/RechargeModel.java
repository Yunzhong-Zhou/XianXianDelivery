package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-26.
 */
public class RechargeModel implements Serializable {
    private List<MoneyListBean> money_list;
    private List<PayTypeListBean> pay_type_list;

    public List<MoneyListBean> getMoney_list() {
        return money_list;
    }

    public void setMoney_list(List<MoneyListBean> money_list) {
        this.money_list = money_list;
    }

    public List<PayTypeListBean> getPay_type_list() {
        return pay_type_list;
    }

    public void setPay_type_list(List<PayTypeListBean> pay_type_list) {
        this.pay_type_list = pay_type_list;
    }

    public static class MoneyListBean {
        /**
         * price : 10
         * award : 1
         */

        private String price;
        private String award;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAward() {
            return award;
        }

        public void setAward(String award) {
            this.award = award;
        }
    }

    public static class PayTypeListBean {
        /**
         * type : 3
         * icon : /img/pay/zfb.png
         * title : 支付宝
         * sub_title :
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
