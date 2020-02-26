package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-09-30.
 */
public class Fragment3Model implements Serializable {
    /**
     * id : 5
     * nickname : 阿斯顿马丁
     * mobile : 18306043086
     * head : /upload/head/2019-11-27/718d5be3ee1b187809699e330fd6d1f3.jpeg
     * share : {"text":"分享文案","url":"http://www.baidu.com"}
     * msg : 17
     */

    private int id;
    private String nickname;
    private String mobile;
    private String head;
    private ShareBean share;
    private int msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public static class ShareBean {
        /**
         * text : 分享文案
         * url : http://www.baidu.com
         */

        private String text;
        private String url;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
