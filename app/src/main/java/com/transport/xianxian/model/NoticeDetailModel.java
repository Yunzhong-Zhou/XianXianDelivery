package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-03.
 */
public class NoticeDetailModel implements Serializable {
    /**
     * id : 1
     * member_id : 3
     * title : 提现
     * message : 提醒下谢谢
     * created_at : 2019-10-17 16:13:40
     */

    private int id;
    private int member_id;
    private String title;
    private String message;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
