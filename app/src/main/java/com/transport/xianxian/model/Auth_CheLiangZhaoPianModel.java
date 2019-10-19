package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-18.
 */
public class Auth_CheLiangZhaoPianModel implements Serializable {
    /**
     * car_type_id :
     * car_image_front :
     * car_image_back :
     * car_image_left :
     * car_image_right :
     * car_type_list : [{"id":1,"type":"1","size":"10吨","weight":"1.8米*1.1米*3米","bulk":"25方","image":"","type_text":"小型车"},{"id":2,"type":"2","size":"20吨","weight":"1.8米*1.1米*3米","bulk":"50方","image":"","type_text":"中型车"},{"id":3,"type":"3","size":"50吨","weight":"1.8米*1.1米*3米","bulk":"100方","image":"","type_text":"大型车"}]
     */

    private String car_type_id;
    private String car_image_front;
    private String car_image_back;
    private String car_image_left;
    private String car_image_right;
    private List<CarTypeListBean> car_type_list;

    public String getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(String car_type_id) {
        this.car_type_id = car_type_id;
    }

    public String getCar_image_front() {
        return car_image_front;
    }

    public void setCar_image_front(String car_image_front) {
        this.car_image_front = car_image_front;
    }

    public String getCar_image_back() {
        return car_image_back;
    }

    public void setCar_image_back(String car_image_back) {
        this.car_image_back = car_image_back;
    }

    public String getCar_image_left() {
        return car_image_left;
    }

    public void setCar_image_left(String car_image_left) {
        this.car_image_left = car_image_left;
    }

    public String getCar_image_right() {
        return car_image_right;
    }

    public void setCar_image_right(String car_image_right) {
        this.car_image_right = car_image_right;
    }

    public List<CarTypeListBean> getCar_type_list() {
        return car_type_list;
    }

    public void setCar_type_list(List<CarTypeListBean> car_type_list) {
        this.car_type_list = car_type_list;
    }

    public static class CarTypeListBean {
        /**
         * id : 1
         * type : 1
         * size : 10吨
         * weight : 1.8米*1.1米*3米
         * bulk : 25方
         * image :
         * type_text : 小型车
         */

        private String id;
        private String type;
        private String size;
        private String weight;
        private String bulk;
        private String image;
        private String type_text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBulk() {
            return bulk;
        }

        public void setBulk(String bulk) {
            this.bulk = bulk;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }
    }
}
