package com.hundsun.quote.model;

import java.util.ArrayList;
/**
 *源程序名称:BlockData.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:板块
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-10-22 下午2:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class BlockData {
	private ArrayList<BlockItem> mBlockData ;

	public ArrayList<BlockItem> getBlockData() {
		return mBlockData;
	}

	public void setBlockData(ArrayList<BlockItem> blockData) {
		this.mBlockData = blockData;
	}


}
