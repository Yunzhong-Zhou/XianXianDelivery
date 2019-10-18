package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-18.
 */
public class Auth_ChePaiHaoMaModel implements Serializable {
    /**
     * car_number : 155555
     */

    private String car_number;
    private String car_number_image;

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_number_image() {
        return car_number_image;
    }

    public void setCar_number_image(String car_number_image) {
        this.car_number_image = car_number_image;
    }
}
