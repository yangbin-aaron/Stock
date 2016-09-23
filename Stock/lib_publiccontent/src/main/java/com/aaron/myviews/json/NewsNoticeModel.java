package com.aaron.myviews.json;


import java.util.List;

/**
 * 广告图片 /user/newsNotice/newsList
 */
public class NewsNoticeModel {

	public static final String CACHE_KEY = "news_cache";
	public static final String CACHE_KEY_HOME_AD = "home_ads";

    public static final String NEWS = "1"; //新闻
    public static final String AD = "2"; //公告
	public static final String HOME_AD = "3"; //首页广告

	private List<New> news_notice_list;

	public List<New> getNewsNoticeList() {
		return news_notice_list;
	}

	public static class New {

		private String id;
		private String middleBanner;
		private String createDate;
		private String title;
		private String url;

		public String getId() {
			return id;
		}

		public String getMiddleBanner() {
			return middleBanner;
		}

		public String getCreateDate() {
			return createDate;
		}

		public String getTitle() {
			return title;
		}

		public String getUrl() {
			return url;
		}

//		public String getFullUrl() {
//			return ApiConfig.getFullUrl(getUrl());
//		}

//		public String getFullMiddleBanner() {
//			return ApiConfig.getFullUrl(getMiddleBanner());
//		}
	}
}
