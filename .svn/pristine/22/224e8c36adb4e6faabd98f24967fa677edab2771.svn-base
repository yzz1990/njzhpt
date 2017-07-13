package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.zkzy.njzhpt.bo.EmergencyBO;
import com.zkzy.njzhpt.bo.GonggongBO;

public class GonggongController extends Controller {
	
	public void index(){
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) GonggongBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(GonggongBO.class),
								ParamUtil.getParamMap(this.getParaMap()));
				if (ret.getData().containsKey("list")) {
					renderJson(ret.getData().get("list"));
				} else {
					renderJson(ret.getData());
				}
			} else {
				renderText("FrameworkController");
			}
		} catch (Exception e) {
			String message = e.getCause().getMessage();
			LogKit.error(message);
			ret = Ret.create("ret", false).put("message", message);
			renderJson(ret.getData());
		}
	}
	
	/**
	 * 保存公告
	 */
	public void addgonggao(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).addgonggao(param).getData());
	}
	
	/**
	 * 修改公告
	 */
	public void upgonggao(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).upgonggao(param).getData());
	}
	
	/**
	 * 删除公告
	 */
	public void delgonggao(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).delgonggao(param).getData());
	}
	
	/**
	 * 保存存粮
	 */
	public void addcunliang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).addcunliang(param).getData());
	}
	
	/**
	 * 修改存粮
	 */
	public void upcunliang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).upcunliang(param).getData());
	}
	
	/**
	 * 删除存粮
	 */
	public void delcunliang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(GonggongBO.class).delcunliang(param).getData());
	}
	

}
