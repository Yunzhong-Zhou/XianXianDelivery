package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-19.
 */
public class AddOtherModel implements Serializable {
    private List<OtherBean> other;

    public List<OtherBean> getOther() {
        return other;
    }

    public void setOther(List<OtherBean> other) {
        this.other = other;
    }

    public static class OtherBean {
        /**
         * id : 1
         * title : 搬运费
         * price : 查看平台计价
         */

        private String id;
        private String title;
        private String price;
        private int isgouxuan;

        public int getIsgouxuan() {
            return isgouxuan;
        }

        public void setIsgouxuan(int isgouxuan) {
            this.isgouxuan = isgouxuan;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
