package com.delivery.xianxian.model;

import java.util.List;

/**
 * Created by zyz on 2019-09-30.
 */
public class Fragment1Model {
    private List<CarTypeBean> car_type;

    public List<CarTypeBean> getCar_type() {
        return car_type;
    }

    public void setCar_type(List<CarTypeBean> car_type) {
        this.car_type = car_type;
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
}
