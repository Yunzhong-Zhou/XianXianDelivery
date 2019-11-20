package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-18.
 */
public class AddFeeModel implements Serializable {
    /**
     * price : 18
     * price_list : [{"title":"起步价（5公里）","price":"48元"},{"title":"分阶段（5公里以上）","price":"6元/公里"}]
     * millage : 0
     * duration : 2
     */

    private String price;
    private String millage;
    private String duration;
    private List<PriceListBean> price_list;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMillage() {
        return millage;
    }

    public void setMillage(String millage) {
        this.millage = millage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<PriceListBean> getPrice_list() {
        return price_list;
    }

    public void setPrice_list(List<PriceListBean> price_list) {
        this.price_list = price_list;
    }

    public static class PriceListBean implements Serializable{
        /**
         * title : 起步价（5公里）
         * price : 48元
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
}
