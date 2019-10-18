package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019-10-08.
 */
public class DuiHuanListModel implements Serializable {
    /**
     * title : 话费
     * image : null
     * sub_title : ces
     * score : 100
     * crated_at : null
     */

    private String title;
    private String image;
    private String sub_title;
    private String score;
    private String crated_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCrated_at() {
        return crated_at;
    }

    public void setCrated_at(String crated_at) {
        this.crated_at = crated_at;
    }
}
