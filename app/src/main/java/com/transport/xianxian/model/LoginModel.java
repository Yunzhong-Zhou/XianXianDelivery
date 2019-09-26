package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2018/6/26.
 */
public class LoginModel implements Serializable {
    /**
     * id : 6d3f2f6a08905ad516c3c2f94d1c629b
     * mobile_state_code : 86
     * mobile : 18306043086
     * head : /upload/head/2018-06-23/40cc0f5b3c57b84f92993a30763d1395.jpg
     * merchant : 1
     * pay : 2
     * gather : 2
     * nickname : 18306043086
     * invite_code : 433787
     * integrity_grade : 3
     * fresh_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2ZDNmMmY2YTA4OTA1YWQ1MTZjM2MyZjk0ZDFjNjI5YiIsImlzcyI6Imh0dHA6XC9cLzE5Mi4xNjguMC4xODhcL2FwaVwvbWVtYmVyXC9sb2dpbiIsImlhdCI6MTUyOTk5OTY4NCwiZXhwIjoxNTMwMDg2MDg0LCJuYmYiOjE1Mjk5OTk2ODQsImp0aSI6IjU5ZWM2N2QwMTExMzBhNWUxNzg5NDUwMzYyMjAyZDIyIn0.NSjUrNdSnlYdACExZuT03Rg8FbJelird3IpgT1jwImA
     */

    private String id;
    private String mobile_state_code;
    private String mobile;
    private String head;
    private int merchant;
    private int pay;
    private int gather;
    private String nickname;
    private String invite_code;
    private int integrity_grade;
    private String fresh_token;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_state_code() {
        return mobile_state_code;
    }

    public void setMobile_state_code(String mobile_state_code) {
        this.mobile_state_code = mobile_state_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getMerchant() {
        return merchant;
    }

    public void setMerchant(int merchant) {
        this.merchant = merchant;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getGather() {
        return gather;
    }

    public void setGather(int gather) {
        this.gather = gather;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getIntegrity_grade() {
        return integrity_grade;
    }

    public void setIntegrity_grade(int integrity_grade) {
        this.integrity_grade = integrity_grade;
    }

    public String getFresh_token() {
        return fresh_token;
    }

    public void setFresh_token(String fresh_token) {
        this.fresh_token = fresh_token;
    }
}
