package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-18.
 */
public class TemperatureModel implements Serializable {
    private List<CouponListBean> coupon_list;
    private List<TemperatureListBean> temperature_list;

    public List<CouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<CouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }

    public List<TemperatureListBean> getTemperature_list() {
        return temperature_list;
    }

    public void setTemperature_list(List<TemperatureListBean> temperature_list) {
        this.temperature_list = temperature_list;
    }

    public static class CouponListBean {
        /**
         * id : 4
         * title : 专车50元优惠卷
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TemperatureListBean {
        /**
         * id : 1
         * title : 冷冻
         * temperature : -5°到-25°
         * price : 15.00
         */

        private String id;
        private String title;
        private String temperature;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
