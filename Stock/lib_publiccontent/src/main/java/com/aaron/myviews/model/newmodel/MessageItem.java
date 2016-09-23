package com.aaron.myviews.model.newmodel;

import java.io.Serializable;

/**
 * 消息列表接口data数组元素模型
 * 对应消息列表的item
 * @author bvin
 */
public class MessageItem implements Serializable{

    private static final long serialVersionUID = -3640014352801467659L;
    
    private Integer id;
    private Integer type;
    private String title;
    private String content;
    private Integer userId;
    private String createDate;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    @Override
    public String toString() {
        return "MessageItem [id=" + id + ", type=" + type + ", content=" + content + ", userId=" + userId
                + ", createDate=" + createDate + "]";
    }
    
}
