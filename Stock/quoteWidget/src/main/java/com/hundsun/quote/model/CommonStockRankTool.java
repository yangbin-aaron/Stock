package com.hundsun.quote.model;

import com.hundsun.quote.tools.CommonTools.QUOTE_SORT;

/**
 *源程序名称:CommonColumnHeader.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:列头元素。
 *作    者: 梁浩 
 *开发日期:2014-11-12-下午6:12:05
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class CommonStockRankTool {
	/* 关键字 */
	public int        key   ; 
	/*显示名*/
	public String     name  ; 
	/*携带值*/
	public String     value ; 
	/*是固定还是移动*/
	public boolean    isFixed; 
	/*排序标记*/
	public QUOTE_SORT sort  ; 
	/*0升序、1降序 、-1不排序*/
	public int        ascending = -1; 
	/*是否支持排序*/
	public boolean    isSupportSort = true; 
	/*表头字体大小*/
	public int        headFontSize;     
	 /*表格内容字体大小*/
	public int        contentFontSize; 
	/*表头字体颜色*/
	public int        headFontColor;    
	/*表格内容字体颜色*/
	public int        contentFontColor; 

	public CommonStockRankTool(){

	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 */
	public CommonStockRankTool(String name,String value){
		this.name  = name;
		this.value = value;
	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 * @param value  携带值
	 */
	public CommonStockRankTool(int key,String name,String value){
		this.name  = name;
		this.value = value;
		this.key   = key;
	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 * @param value  携带值
	 * @param isFixed  列是否固定
	 */
	public CommonStockRankTool(int key,String name,String value,boolean isFixed){
		this.name  = name;
		this.value = value;
		this.key   = key;
		this.isFixed = isFixed;
	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 * @param value  携带值
	 * @param isFixed  列是否固定
	 * @param sort     排序值
	 * @param isSupportSort 不支持排序
	 */
	public CommonStockRankTool(int key,String name,String value,boolean isFixed,boolean isSupportSort,QUOTE_SORT sort){
		this.name  = name;
		this.value = value;
		this.key   = key;
		this.isFixed = isFixed;
		this.isSupportSort = isSupportSort;
		this.sort  = sort;
	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 * @param value  携带值
	 * @param isFixed  列是否固定
	 * @param sort     排序值
	 * @param ascending  支持排序的话，是升序还是降序
	 * @param isSupportSort 不支持排序
	 */
	public CommonStockRankTool(int key,String name,String value,boolean isFixed,boolean isSupportSort,QUOTE_SORT sort,int ascending ){
		this.name  = name;
		this.value = value;
		this.key   = key;
		this.isFixed = isFixed;
		this.sort  = sort;
		this.ascending  = ascending;
		this.isSupportSort = isSupportSort;
	}

	/**
	 * @param key    区别关键字 - 唯一指示列头
	 * @param name   显示名称
	 * @param value  携带值
	 * @param isFixed  列是否固定
	 * @param sort     排序值
	 * @param ascending  支持排序的话，是升序还是降序
	 * @param isSupportSort 不支持排序
	 * @param headFontSize     表头字体大小（可配置）
	 * @param headFontColor    表头字体颜色（可配置）
	 * @param contentFontSize  表格字体大小（可配置）
	 * @param contentFontColor 表格字体颜色（可配置）
	 */
	public CommonStockRankTool(int key,String name,String value,boolean isFixed,boolean isSupportSort,QUOTE_SORT sort,int ascending,int headFontSize,int headFontColor,int contentFontSize,int contentFontColor){
		this.name  = name;
		this.value = value;
		this.key   = key;
		this.isFixed = isFixed;
		this.sort  = sort;
		this.ascending  = ascending;
		this.isSupportSort = isSupportSort;
		this.headFontSize  = headFontSize;
		this.headFontColor = headFontColor;
		this.contentFontSize  = contentFontSize;
		this.contentFontColor = contentFontColor;
	}

	public int getAscending() {
		return ascending;
	}

	public void setAscending(int ascending) {
		this.ascending = ascending;
	}

	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public boolean isFixed() {
		return isFixed;
	}


	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public QUOTE_SORT getSort() {
		return sort;
	}

	public void setSort(QUOTE_SORT sort) {
		this.sort = sort;
	}

	public boolean isSupportSort() {
		return isSupportSort;
	}

	public void setSupportSort(boolean isSupportSort) {
		this.isSupportSort = isSupportSort;
	}

	public int getHeadFontSize() {
		return headFontSize;
	}

	public void setHeadFontSize(int headFontSize) {
		this.headFontSize = headFontSize;
	}

	public int getContentFontSize() {
		return contentFontSize;
	}

	public void setContentFontSize(int contentFontSize) {
		this.contentFontSize = contentFontSize;
	}

	public int getHeadFontColor() {
		return headFontColor;
	}

	public void setHeadFontColor(int headFontColor) {
		this.headFontColor = headFontColor;
	}

	public int getContentFontColor() {
		return contentFontColor;
	}

	public void setContentFontColor(int contentFontColor) {
		this.contentFontColor = contentFontColor;
	}


}
