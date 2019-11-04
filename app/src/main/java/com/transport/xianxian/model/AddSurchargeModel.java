package com.transport.xianxian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyz on 2019-11-01.
 */
public class AddSurchargeModel implements Serializable {
    /**
     * city : 重庆
     * rule : 重庆附加费标准
     * attach : [{"name":"逾时等候费","money":"100"},{"name":"路桥费","money":"200"},{"name":"搬运费","money":"300"},{"name":"过路费","money":"400"}]
     * can_update : true
     */

    private String city;
    private String rule;
    private boolean can_update;
    private List<AttachBean> attach;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isCan_update() {
        return can_update;
    }

    public void setCan_update(boolean can_update) {
        this.can_update = can_update;
    }

    public List<AttachBean> getAttach() {
        return attach;
    }

    public void setAttach(List<AttachBean> attach) {
        this.attach = attach;
    }

    public static class AttachBean {
        /**
         * name : 逾时等候费
         * money : 100
         */

        private String name;
        private String money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
