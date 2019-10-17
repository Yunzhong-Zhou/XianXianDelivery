package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-17.
 */
public class Auth_JiaShiZhengModel implements Serializable {
    /**
     * license_driver_image :
     * license_vehicle_image :
     */

    private String license_driver_image;
    private String license_vehicle_image;

    public String getLicense_driver_image() {
        return license_driver_image;
    }

    public void setLicense_driver_image(String license_driver_image) {
        this.license_driver_image = license_driver_image;
    }

    public String getLicense_vehicle_image() {
        return license_vehicle_image;
    }

    public void setLicense_vehicle_image(String license_vehicle_image) {
        this.license_vehicle_image = license_vehicle_image;
    }
}
