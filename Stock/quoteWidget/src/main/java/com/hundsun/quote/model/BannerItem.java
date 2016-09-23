package com.hundsun.quote.model;

import android.R.integer;

/**  
 * @类描述：  活动图
 * @创建人：龙章煌  
 * @创建时间：2014-04-02  
 * @修改人： 
 * @修改时间：  
 * @修改备注：   
 * @version
 */
public class BannerItem {
	private integer id;
	private String name;          //标题
	private String c_photopath;   //图片地址
	private String imageAdress ;    //SD卡中文件存储地址
	private String promotionId; //活动序列号
	private String des;   //活动内容
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getC_photopath()
	{
		return c_photopath;
	}
	public void setC_photopath(String c_photopath)
	{
		this.c_photopath = c_photopath;
	}
	public String getPromotionId()
	{
		return promotionId;
	}
	public void setPromotionId(String promotionId)
	{
		this.promotionId = promotionId;
	}
	public String getDes()
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	public String getImageAdress()
	{
		return imageAdress;
	}
	public void setImageAdress(String imageAdress)
	{
		this.imageAdress = imageAdress;
	}
	public integer getId() {
		return id;
	}
	public void setId(integer id) {
		this.id = id;
	}
	
}
