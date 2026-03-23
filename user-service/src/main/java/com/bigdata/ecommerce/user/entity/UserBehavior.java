package com.bigdata.ecommerce.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserBehavior implements Serializable {
    private Long id;
    private Long userId;
    private String behaviorType;
    private String behaviorData;
    private String ipAddress;
    private String userAgent;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getBehaviorData() {
        return behaviorData;
    }

    public void setBehaviorData(String behaviorData) {
        this.behaviorData = behaviorData;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
