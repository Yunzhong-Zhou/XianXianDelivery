package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-18.
 */
public class TemperatureModel implements Serializable {
    private List<TemperatureListBean> temperature_list;

    public List<TemperatureListBean> getTemperature_list() {
        return temperature_list;
    }

    public void setTemperature_list(List<TemperatureListBean> temperature_list) {
        this.temperature_list = temperature_list;
    }

    public static class TemperatureListBean {
        /**
         * id : 1
         * title : 冷冻
         * temperature : -18°到-2°
         * price : 100
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
