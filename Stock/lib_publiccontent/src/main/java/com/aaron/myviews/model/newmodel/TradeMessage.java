package com.aaron.myviews.model.newmodel;

public class TradeMessage {
    private Integer id;    
    private String title;     
    private String content;    
    private Integer order;   
    private String createDate;
    private String updateDate;
    
    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public Integer getOrder() {
        return order;
    }
    public String getCreateDate() {
        return createDate;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
