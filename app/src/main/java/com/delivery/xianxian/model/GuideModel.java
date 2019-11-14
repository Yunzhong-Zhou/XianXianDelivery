package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-10-30.
 */
public class GuideModel implements Serializable {
    private List<AppBannerListBean> app_banner_list;

    public List<AppBannerListBean> getApp_banner_list() {
        return app_banner_list;
    }

    public void setApp_banner_list(List<AppBannerListBean> app_banner_list) {
        this.app_banner_list = app_banner_list;
    }

    public static class AppBannerListBean {
        /**
         * image : /upload/goods/2019-10-24/7568d47d63f3cc50c9890a80dd01047f.png
         * number : 1
         * title : 第一张
         */

        private String image;
        private int number;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
