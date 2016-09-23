package com.aaron.myviews.model.newmodel.information;

import java.io.Serializable;

/**
 * /user/newsArticle/newsList
 *
 * {
 “code”: 200,
 “msg”: “success”,
 “msgType”: 0,
 “errparam”: “”,
 “data”: [
     {
     "summary": "降价大甩卖ddddddd",
     "sourceType": 0,
     "status": 2,
     "keyword": null,
     "sectionName": "",

     "modifyDate": "2015-12-21 11:44:32",
     "section": 1,
     "outSourceUrl": null,
     "orderWeight": 0,
     "id": 7,

     "content": "降价大甩卖ddddddd",
     "subTitle": "",
     "permitComment": 0,
     "title": "降价",
     "bannerUrl": null,

     "newsPlateIdList": "",
     "plateName": "",
     "createStaffName": "admin",
     "outSourceName": null,
     "createDate": "2015-12-16 15:02:59",

     "cmtCount": 0,
     "readCount": 0
     }
 ]
 }

 成功结果说明
 summary 资讯简介
 sourceType 来源类型
 status
 keyword 关键词
 sectionName 栏目名称

 modifyDate
 section 栏目ID
 outSourceUrl 外部来源地址
 orderWeight
 id 资讯ID

 content 咨询内容
 subTitle 子标题
 permitComment 是否允许评论0不允许1允许
 title 资讯标题
 bannerUrl 资讯banner图

 newsPlateIdList 标签ID，英文,隔开
 plateName 标签名称，英文,隔开，与标签ID一一对应
 createStaffName
 outSourceName 外部来源名称
 createDate 创建时间

 cmtCount 评论数
 readCount 阅读数
 */
public class Article implements Serializable {

    public static final String CACHE_KEY = "article_cache";

    private String summary;
    private int sourceType;
    private int status;
    private String keyword;
    private String sectionName;

    private String modifyDate;
    private int section;
    private String outSourceUrl;
    private int orderWeight;
    private long id;

    private String content;
    private String subTitle;
    private int permitComment;
    private String title;
    private String bannerUrl;

    private String newsPlateIdList;
    private String plateName;
    private String createStaffName;
    private String outSourceName;
    private String createDate;

    private int cmtCount;
    private int readCount;

    public long getId() {
        return id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getPlateName() {
        return plateName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getCmtCount() {
        return cmtCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public boolean allowedComment() {
        return permitComment == 1; // 允许评论
    }

    public boolean isStrategy() {
        return section == 57; // 策略类型
    }
}
