package com.hundsun.quote.viewmodel;

import java.util.ArrayList;

import com.hundsun.quote.model.BlockData;
import com.hundsun.quote.model.BlockItem;
import com.hundsun.quote.model.Stock;

/**
 *源程序名称:BlockViewModel.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QIIPro-1.1
 *模块名称: 获取一级板块及所属子板块  ViewMode
 *功能说明:
 *作    者: 梁浩 
 *开发日期:2014-9-28 下午5:02:47
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class BlockViewModel extends ViewModel{

	private BlockData  mBlockData;
	
	public BlockViewModel(Stock stock) {
		super(stock);
	}

	public BlockData getBlockData() {
		return mBlockData;
	}

	public void setBlockData(BlockData blockData) {
		this.mBlockData = blockData;
	}
   
	public BlockItem getBlockItem( int idx ){
		if (null == mBlockData) {
			return null;
		}
		ArrayList<BlockItem> items = mBlockData.getBlockData();
		if (idx >= 0 && idx < items.size()) {
			return items.get(idx);
		}
		return null;
	}

	public int getDataCount(){
		if (null != mBlockData) {
			return mBlockData.getBlockData().size();
		}
		return 0;
	}
	
}
