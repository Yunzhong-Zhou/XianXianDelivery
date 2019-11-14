package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-16.
 */
public class JiFenShangChengModel implements Serializable {
    /**
     * nickname : ajglja
     * head : /upload/head/2019-10-24/f45094af06e9db38cf7c34b5894133b3.jpg
     * mobile : 15823629471
     * score : 0
     * banner : [{"url":"a.jpg","type":"1"},{"url":"a.jpg","type":"2"}]
     * type : [{"key":1,"val":"兑好礼"},{"key":2,"val":"兑话费"},{"key":3,"val":"兑油费"}]
     */

    private String nickname;
    private String head;
    private String mobile;
    private int score;
    private List<BannerBean> banner;
    private List<TypeBean> type;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class BannerBean {
        /**
         * url : a.jpg
         * type : 1
         */

        private String url;
        private int type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class TypeBean {
        /**
         * key : 1
         * val : 兑好礼
         */

        private int key;
        private String val;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
}
