package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-09.
 */
public class JiangLiListModel implements Serializable {
    /**
     * total_score : 100000
     * activity_score : 8000
     * tscore_data : [{"id":1,"title":"登录获取","score":"1","remark":"备注","created_at":"2019-10-17 11:04:13"}]
     */

    private String total_score;
    private String activity_score;
    private List<TscoreDataBean> tscore_data;

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public String getActivity_score() {
        return activity_score;
    }

    public void setActivity_score(String activity_score) {
        this.activity_score = activity_score;
    }

    public List<TscoreDataBean> getTscore_data() {
        return tscore_data;
    }

    public void setTscore_data(List<TscoreDataBean> tscore_data) {
        this.tscore_data = tscore_data;
    }

    public static class TscoreDataBean {
        /**
         * id : 1
         * title : 登录获取
         * score : 1
         * remark : 备注
         * created_at : 2019-10-17 11:04:13
         */

        private int id;
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
}
