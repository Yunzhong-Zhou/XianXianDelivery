package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-18.
 */
public class Auth_CheLiangBaoXianModel implements Serializable {
    /**
     * insurance_sn :
     * insurance_name :
     * insurance_number :
     * insurance_addr :
     * insurance_start :
     * insurance_end :
     * insurance_image :
     */

    private String insurance_sn;
    private String insurance_name;
    private String insurance_number;
    private String insurance_addr;
    private String insurance_start;
    private String insurance_end;
    private String insurance_image;

    public String getInsurance_sn() {
        return insurance_sn;
    }

    public void setInsurance_sn(String insurance_sn) {
        this.insurance_sn = insurance_sn;
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getInsurance_number() {
        return insurance_number;
    }

    public void setInsurance_number(String insurance_number) {
        this.insurance_number = insurance_number;
    }

    public String getInsurance_addr() {
        return insurance_addr;
    }

    public void setInsurance_addr(String insurance_addr) {
        this.insurance_addr = insurance_addr;
    }

    public String getInsurance_start() {
        return insurance_start;
    }

    public void setInsurance_start(String insurance_start) {
        this.insurance_start = insurance_start;
    }

    public String getInsurance_end() {
        return insurance_end;
    }

    public void setInsurance_end(String insurance_end) {
        this.insurance_end = insurance_end;
    }

    public String getInsurance_image() {
        return insurance_image;
    }

    public void setInsurance_image(String insurance_image) {
        this.insurance_image = insurance_image;
    }
}
