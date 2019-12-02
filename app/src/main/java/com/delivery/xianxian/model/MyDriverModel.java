package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-12-02.
 */
public class MyDriverModel implements Serializable {

    /**
     * head : /upload/head/14.png
     * nickname : 158****9471
     * mobile : 15823629471
     * card_number :
     * comment_score : 0.00
     */

    private String head;
    private String nickname;
    private String mobile;
    private String card_number;
    private String comment_score;
    private String total_indent_count;

    public String getTotal_indent_count() {
        return total_indent_count;
    }

    public void setTotal_indent_count(String total_indent_count) {
        this.total_indent_count = total_indent_count;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getComment_score() {
        return comment_score;
    }

    public void setComment_score(String comment_score) {
        this.comment_score = comment_score;
    }
}
