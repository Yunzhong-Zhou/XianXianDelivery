package com.transport.xianxian.model;

import java.io.Serializable;

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
     */

    private String car_type_id;
    private String car_image_front;
    private String car_image_back;
    private String car_image_left;
    private String car_image_right;

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
}
