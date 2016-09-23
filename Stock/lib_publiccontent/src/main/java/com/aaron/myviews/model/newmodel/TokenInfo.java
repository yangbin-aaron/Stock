package com.aaron.myviews.model.newmodel;

/**
 * Token数据 /user/login
 */
public class TokenInfo {

    private String userSecret;
    private String token;
    private Long userId;
    private Boolean isdeline;

    public String getUserSecret() {
        return userSecret;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getIsDeline() {
        return isdeline;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setIsDeline(Boolean isdeline) {
        this.isdeline = isdeline;
    }
}
