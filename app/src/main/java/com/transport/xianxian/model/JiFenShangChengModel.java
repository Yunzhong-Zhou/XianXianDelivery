package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-16.
 */
public class JiFenShangChengModel implements Serializable {
    /**
     * nickname : 18306043086
     * head :
     * mobile : 18306043086
     * score :
     * banner : [{"url":"a.jpg","type":"1"},{"url":"a.jpg","type":"2"}]
     * type : [{"type":"1","title":"兑好礼"},{"type":"2","title":"兑话费"},{"type":"3","title":"兑油费"}]
     */

    private String nickname;
    private String head;
    private String mobile;
    private String score;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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
        private String type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class TypeBean {
        /**
         * type : 1
         * title : 兑好礼
         */

        private String type;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
