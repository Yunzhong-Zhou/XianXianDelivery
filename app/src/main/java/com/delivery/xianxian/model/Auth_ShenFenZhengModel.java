package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-17.
 */
public class Auth_ShenFenZhengModel implements Serializable {
    /**
     * identity_name : 黄是明
     * identity_number : 400243199100223232
     * identity_front_image : a.jpg
     * identity_reverse_image : b.jpg
     */

    private int is_certification;

    public int getIs_certification() {
        return is_certification;
    }

    public void setIs_certification(int is_certification) {
        this.is_certification = is_certification;
    }

    private String identity_name;
    private String identity_number;
    private String identity_front_image;
    private String identity_reverse_image;

    public String getIdentity_name() {
        return identity_name;
    }

    public void setIdentity_name(String identity_name) {
        this.identity_name = identity_name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public String getIdentity_front_image() {
        return identity_front_image;
    }

    public void setIdentity_front_image(String identity_front_image) {
        this.identity_front_image = identity_front_image;
    }

    public String getIdentity_reverse_image() {
        return identity_reverse_image;
    }

    public void setIdentity_reverse_image(String identity_reverse_image) {
        this.identity_reverse_image = identity_reverse_image;
    }
}
