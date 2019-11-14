package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2018/2/10.
 */

public class ChangeProfileModel implements Serializable {
    /**
     * head : /upload/head/2018-02-09/e8dbd51bb1dddc591d421b778cc6caf9.jpg
     * email : 123456@admin.com
     */

    private String head;
    private String email;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ChangeProfileModel{" +
                "head='" + head + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
