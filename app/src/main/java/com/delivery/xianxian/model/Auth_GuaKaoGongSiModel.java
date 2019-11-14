package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-18.
 */
public class Auth_GuaKaoGongSiModel implements Serializable {
    /**
     * depend_image : /tmp/phphSgZMC
     * depend_name : 阿斯顿马丁路德金
     */

    private String depend_image;
    private String depend_name;

    public String getDepend_image() {
        return depend_image;
    }

    public void setDepend_image(String depend_image) {
        this.depend_image = depend_image;
    }

    public String getDepend_name() {
        return depend_name;
    }

    public void setDepend_name(String depend_name) {
        this.depend_name = depend_name;
    }
}
