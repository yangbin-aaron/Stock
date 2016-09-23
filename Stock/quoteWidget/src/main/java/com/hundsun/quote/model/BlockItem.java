package com.hundsun.quote.model;
/**
 *源程序名称:BlockItem.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:板块 Item
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-10-22 下午2:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class BlockItem {
	/*板块代码*/
	private String mBlockCode;
	/*板块名称*/
	private String mBlockName;
	/*当前板块是否存在子板块*/
	private boolean mHasSubBlock;
	/* 所属板块下的股票数*/ 
	private int     mStocksCount;

	public String getBlockCode() {
		return mBlockCode;
	}
	public void setBlockCode(String blockCode) {
		this.mBlockCode = blockCode;
	}
	public String getBlockName() {
		return mBlockName;
	}
	public void setBlockName(String blockName) {
		this.mBlockName = blockName;
	}
	public boolean ismHasSubBlock() {
		return mHasSubBlock;
	}
	public void setHasSubBlock(boolean hasSubBlock) {
		this.mHasSubBlock = hasSubBlock;
	}
	public int getStocksCount() {
		return mStocksCount;
	}
	public void setStocksCount(int stocksCount) {
		this.mStocksCount = stocksCount;
	} 


}
