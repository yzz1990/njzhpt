package com.zkzy.njzhpt.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.RemoteBO;
import com.zkzy.njzhpt.config.interfaces.ICommond;

public class RemoteController extends Controller {

	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) RemoteBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(RemoteBO.class),
								ParamUtil.getParamMap(this.getParaMap()));
				if (ret.getData().containsKey("list")) {
					renderJson(ret.getData().get("list"));
				} else {
					renderJson(ret.getData());
				}
			} else {
				render("login.html");
			}
		} catch (Exception e) {
			String message = e.getCause().getMessage();
			LogKit.error(message);
			ret = Ret.create("ret", false).put("message", message);
			renderJson(ret.getData());
		}

	}
	/*
	public void findzongkucun(){
		String sql = "select vGrainSubTypeCode, isnull(sum(dmStock),0) as sum from kc_CurrentStock where vGrainPropertyCode in (123,122)"
				+ "group by vGrainSubTypeCode";
		renderJson(Db.find(sql));
	}*/
	
	/*public void findzongjihua(){
		Calendar a=Calendar.getInstance();
		String year= (a.get(Calendar.YEAR)-1)+"";
		String sql = "select  isnull(sum(jhsl)/1000,0) as jhsl,isnull(sum(cbljh_xm)/1000,0) as xm, isnull(sum(cbljh_jd)/1000,0) as jd,isnull(sum(cbljh_xd)/1000,0) as xd "
				+ " from njpt_chubeiliangjihua where niandu='"+year+"'";
		renderJson(Db.find(sql));
	}*/
	//市级储备
	public void findzongjihua() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findzongjihua(queryparam).getData().get("list"));
	}
	public void findzongkucun() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findzongkucun(queryparam).getData().get("list"));
	}
	//县级储备
	public void findzongjihuaxian() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findzongjihuaxian(queryparam).getData().get("list"));
	}
	public void findzongkucunxian() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findzongkucunxian(queryparam).getData().get("list"));
	}
}
