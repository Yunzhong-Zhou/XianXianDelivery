package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-08.
 */
public class JiFenMingXiModel implements Serializable {
    /**
     * id : 1
     * out_in : 1
     * title : 登录获取
     * score : 1
     * remark : 备注
     * created_at : 2019-10-17 11:04:13
     */

    private int id;
    private int out_in;
    private String title;
    private String score;
    private String remark;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOut_in() {
        return out_in;
    }

    public void setOut_in(int out_in) {
        this.out_in = out_in;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
