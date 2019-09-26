package com.transport.xianxian.model;

import java.io.Serializable;

/**
 * Created by zyz on 2017/9/30.
 * 更新
 */

public class UpgradeModel implements Serializable {
    /**
     * title : 2=2.0.2#1=1.0.0
     * version_code : 1
     * version_title : 1.0.0
     * url : http://xxx.xxx.xxx
     */

    private String title;
    private String version_code;
    private String version_title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_title() {
        return version_title;
    }

    public void setVersion_title(String version_title) {
        this.version_title = version_title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
