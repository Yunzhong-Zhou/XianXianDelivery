package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2020-04-01.
 */
public class WeChatLoginModel implements Serializable {
    /**
     * third_id : 0
     * login_data : {"id":272,"mobile":"18306043086","nickname":"18306043086","role_type":2,"hx_username":"hx000272","is_certification":2,"fresh_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjI3MiwiaXNzIjoiaHR0cDovL3d1bGl1LnpoZW55b25na2ouY29tL2FwaS9tZW1iZXIvdGhpcmQtbG9naW4iLCJpYXQiOjE1ODU3MzEzMDYsImV4cCI6MTYxNzI2NzMwNiwibmJmIjoxNTg1NzMxMzA2LCJqdGkiOiJlMEFPYVdkQUJUY3VId004In0.MUTglsjzbG7r7B6YejCONYb9GlMXYYw9PGuhRo1YfGI"}
     */

    private String third_id;
    private LoginDataBean login_data;

    public String getThird_id() {
        return third_id;
    }

    public void setThird_id(String third_id) {
        this.third_id = third_id;
    }

    public LoginDataBean getLogin_data() {
        return login_data;
    }

    public void setLogin_data(LoginDataBean login_data) {
        this.login_data = login_data;
    }

    public static class LoginDataBean {
        /**
         * id : 272
         * mobile : 18306043086
         * nickname : 18306043086
         * role_type : 2
         * hx_username : hx000272
         * is_certification : 2
         * fresh_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjI3MiwiaXNzIjoiaHR0cDovL3d1bGl1LnpoZW55b25na2ouY29tL2FwaS9tZW1iZXIvdGhpcmQtbG9naW4iLCJpYXQiOjE1ODU3MzEzMDYsImV4cCI6MTYxNzI2NzMwNiwibmJmIjoxNTg1NzMxMzA2LCJqdGkiOiJlMEFPYVdkQUJUY3VId004In0.MUTglsjzbG7r7B6YejCONYb9GlMXYYw9PGuhRo1YfGI
         */

        private String id;
        private String mobile;
        private String nickname;
        private int role_type;
        private String hx_username;
        private int is_certification;
        private String fresh_token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getRole_type() {
            return role_type;
        }

        public void setRole_type(int role_type) {
            this.role_type = role_type;
        }

        public String getHx_username() {
            return hx_username;
        }

        public void setHx_username(String hx_username) {
            this.hx_username = hx_username;
        }

        public int getIs_certification() {
            return is_certification;
        }

        public void setIs_certification(int is_certification) {
            this.is_certification = is_certification;
        }

        public String getFresh_token() {
            return fresh_token;
        }

        public void setFresh_token(String fresh_token) {
            this.fresh_token = fresh_token;
        }
    }
}
