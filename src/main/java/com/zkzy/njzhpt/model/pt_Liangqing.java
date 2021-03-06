package com.zkzy.njzhpt.model;

import com.alibaba.fastjson.JSONArray;

public class pt_Liangqing {
	private String pointx;// 平面图x坐标
	private String pointy;// 平面图y坐标
	private String canghao;// 仓号
	private String cangming;// 仓房名称
	private String cfbm;// 仓房名称
	private String ajbm;// 仓房名称
	private String pinzhong;// 品种
	private String kucun;// 库存
	private String qiwen;// 气温
	private String qishi;// 气湿
	private String cangwen;// 仓温
	private String cangshi;// 仓湿
	private String pingjunwen;// 平均温
	private String zuigaowen;// 最高温
	private String zuidiwen;// 最低温
	private String liangwen;// 粮温
	private String shijian;// 时间
	private String x;// x轴长度
	private String y;// y轴长度
	private String z;// 层数
	private JSONArray layers;

	
	
	

	public String getAjbm() {
		return ajbm;
	}

	public void setAjbm(String ajbm) {
		this.ajbm = ajbm;
	}

	public String getCfbm() {
		return cfbm;
	}

	public void setCfbm(String cfbm) {
		this.cfbm = cfbm;
	}

	public JSONArray getLayers() {
		return layers;
	}

	public void setLayers(JSONArray layers) {
		this.layers = layers;
	}

	public String getCanghao() {
		return canghao;
	}

	public void setCanghao(String canghao) {
		this.canghao = canghao;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
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

	public String getCangming() {
		return cangming;
	}

	public void setCangming(String cangming) {
		this.cangming = cangming;
	}

	public String getPinzhong() {
		return pinzhong;
	}

	public void setPinzhong(String pinzhong) {
		this.pinzhong = pinzhong;
	}

	public String getKucun() {
		return kucun;
	}

	public void setKucun(String kucun) {
		this.kucun = kucun;
	}

	public String getQiwen() {
		return qiwen;
	}

	public void setQiwen(String qiwen) {
		this.qiwen = qiwen;
	}

	public String getQishi() {
		return qishi;
	}

	public void setQishi(String qishi) {
		this.qishi = qishi;
	}

	public String getCangwen() {
		return cangwen;
	}

	public void setCangwen(String cangwen) {
		this.cangwen = cangwen;
	}

	public String getCangshi() {
		return cangshi;
	}

	public void setCangshi(String cangshi) {
		this.cangshi = cangshi;
	}

	public String getPingjunwen() {
		return pingjunwen;
	}

	public void setPingjunwen(String pingjunwen) {
		this.pingjunwen = pingjunwen;
	}

	public String getZuigaowen() {
		return zuigaowen;
	}

	public void setZuigaowen(String zuigaowen) {
		this.zuigaowen = zuigaowen;
	}

	public String getZuidiwen() {
		return zuidiwen;
	}

	public void setZuidiwen(String zuidiwen) {
		this.zuidiwen = zuidiwen;
	}

	public String getLiangwen() {
		return liangwen;
	}

	public void setLiangwen(String liangwen) {
		this.liangwen = liangwen;
	}

	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

}
