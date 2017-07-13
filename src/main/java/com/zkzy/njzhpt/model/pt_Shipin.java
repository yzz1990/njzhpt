package com.zkzy.njzhpt.model;

import com.alibaba.fastjson.JSONObject;

public class pt_Shipin {
	private String pointx;// 平面图x坐标
	private String pointy;// 平面图y坐标
	private String name;// 视频名称
	private String status;// 视频厂家 0海康,1RTSP通用
	private JSONObject info;// 视频信息

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPointx() {
		return pointx;
	}

	public void setPointx(String pointx) {
		this.pointx = pointx;
	}

	public String getPointy() {
		return pointy;
	}

	public void setPointy(String pointy) {
		this.pointy = pointy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getInfo() {
		return info;
	}

	public void setInfo(JSONObject info) {
		this.info = info;
	}

}
