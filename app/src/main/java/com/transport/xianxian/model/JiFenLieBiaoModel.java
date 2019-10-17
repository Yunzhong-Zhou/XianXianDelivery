package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-08.
 */
public class JiFenLieBiaoModel implements Serializable {
    private List<BannerBean> banner;
    private List<GoodsDataBean> goods_data;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<GoodsDataBean> getGoods_data() {
        return goods_data;
    }

    public void setGoods_data(List<GoodsDataBean> goods_data) {
        this.goods_data = goods_data;
    }

    public static class BannerBean {
        /**
         * url : a.jpg
         * goods_id : 1
         */

        private String url;
        private String goods_id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }

    public static class GoodsDataBean {
        /**
         * id : 1
         * title : 话费
         * sub_title : ces
         * image : a.jpg
         * price : 100
         * score : 100
         */

        private int id;
        private String title;
        private String sub_title;
        private String image;
        private String store;
        private String score;

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

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
