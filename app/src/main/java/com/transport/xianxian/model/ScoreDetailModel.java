package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-03.
 */
public class ScoreDetailModel implements Serializable {
    /**
     * id : 1
     * member_id : 3
     * title : 五星好评
     * remark :
     * out_in : 1
     * score : 5
     * created_at : null
     */

    private int id;
    private int member_id;
    private String title;
    private String remark;
    private int out_in;
    private int score;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOut_in() {
        return out_in;
    }

    public void setOut_in(int out_in) {
        this.out_in = out_in;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
