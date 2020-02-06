package com.delivery.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2020-02-05.
 */
public class ConfirmOrderPayModel implements Serializable {

    /**
     * id : 84
     * pay_type : 3
     * result : alipay_root_cert_sn=687b59193f3f462dd5336e5abf83c5d8_02941eef3187dddf3d3b83462e1dfcf6&amp;alipay_sdk=alipay-sdk-php-easyalipay-20190926&amp;app_cert_sn=cd0f5cf1d482e5e0e8c6942c9e18ceb6&amp;biz_content=%7B%22body%22%3A%22%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98%E8%BF%90%E8%B4%B9%22%2C%22subject%22%3A+%22%E4%B8%8B%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A+%22cb41d6cc986a%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=utf-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=http%3A%2F%2Fwuliu.zhenyongkj.com%2Fapi%2Faplipay%2Fnotify&amp;sign_type=RSA2&amp;timestamp=2020-02-05+13%3A22%3A17&amp;version=1.0&amp;sign=rnH0vGyPx%2Fk94YagwoydOR4YdOasn616Gqv8i6riNJXnPkQmbF1%2F9CZ%2BBJ3yQo0rW922RibAk%2FeRSy%2FzwQIfz%2BVz%2F1Qt1sd6Pqes9Yf99s9JBxTnEvy90aa6OAbe8YBv3jA8pj4rSU5vkfNPc%2Be9zn2SeM9tlF6tydHlh0gN0VMvSOXBOFlVYXZs8qxeWP6Tmanjy%2Fkb3ZEWwWvU%2Fb%2FCaR9XjeByo0vZYnsjzzze3vAnLp2LcXerV7722rsfFK0uNIppU1K8%2Bf6hiVKBolgshrElaylTTVCW5X1E0WK1WgcfkqB6Fpx1K88GPN10d4DpcxKo3yGNYhs%2B%2B0r2UdptOA%3D%3D
     */

    private int id;
    private String pay_type;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
