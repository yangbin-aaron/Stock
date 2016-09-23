package com.aaron.myviews.model.gson;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreFlowModel implements Parcelable {
	private String id, type, flowway, intro, curflowAmt, status, createDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlowway() {
		return flowway;
	}

	public void setFlowway(String flowway) {
		this.flowway = flowway;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCurflowAmt() {
		return curflowAmt;
	}

	public void setCurflowAmt(String curflowAmt) {
		this.curflowAmt = curflowAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
    
}
