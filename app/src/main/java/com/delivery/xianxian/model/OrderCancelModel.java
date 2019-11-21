package com.delivery.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-21.
 */
public class OrderCancelModel implements Serializable {
    private List<OwnerCancelReasonListBean> owner_cancel_reason_list;

    public List<OwnerCancelReasonListBean> getOwner_cancel_reason_list() {
        return owner_cancel_reason_list;
    }

    public void setOwner_cancel_reason_list(List<OwnerCancelReasonListBean> owner_cancel_reason_list) {
        this.owner_cancel_reason_list = owner_cancel_reason_list;
    }

    public static class OwnerCancelReasonListBean implements Serializable{
        /**
         * key : 1
         * val : 无法储备货物
         */

        private String key;
        private String val;
        private int isgouxuan;

        public int getIsgouxuan() {
            return isgouxuan;
        }

        public void setIsgouxuan(int isgouxuan) {
            this.isgouxuan = isgouxuan;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

    }
}
