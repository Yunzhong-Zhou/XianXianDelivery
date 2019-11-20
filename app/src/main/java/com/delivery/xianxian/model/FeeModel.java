package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-19.
 */
public class FeeModel implements Serializable {
    private List<CarTypeBean> car_type;
    private List<BaseBean> base;
    private List<OtherBean> other;

    public List<CarTypeBean> getCar_type() {
        return car_type;
    }

    public void setCar_type(List<CarTypeBean> car_type) {
        this.car_type = car_type;
    }

    public List<BaseBean> getBase() {
        return base;
    }

    public void setBase(List<BaseBean> base) {
        this.base = base;
    }

    public List<OtherBean> getOther() {
        return other;
    }

    public void setOther(List<OtherBean> other) {
        this.other = other;
    }

    public static class CarTypeBean {
        /**
         * id : 1
         * name : 面包车
         * size : 2600mm*1000mm*900mm
         * weight : 2
         * bulk : 3
         * image : 1.jpg
         */

        private int id;
        private String name;
        private String size;
        private int weight;
        private int bulk;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getBulk() {
            return bulk;
        }

        public void setBulk(int bulk) {
            this.bulk = bulk;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class BaseBean {
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

    public static class OtherBean {
        /**
         * title : 搬运费
         * price : 查看平台计价
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
