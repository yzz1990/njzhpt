package com.zkzy.njzhpt.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class MainController extends Controller {

	public void index(){
		Object size = getSession().getServletContext().getAttribute("fontsize");
		if (size == null) {
			setAttr("fontsize", "14px");
		} else {
			setAttr("fontsize", String.valueOf(size) + "px");
		}
		String _url = getAttrForStr("_url_");
		if (StrKit.notBlank(_url)) {
			if (getRequest().getServletPath().startsWith("/gis/")) {
				setAttr("title", "地理信息系统").setAttr("amsroot", "root");
			} else if (getRequest().getServletPath().startsWith("/remote/")) {
				setAttr("title", "储备粮远程监管系统").setAttr("amsroot", "remote");
			} else if (getRequest().getServletPath().startsWith(
					"/educationsystem/")) {
				setAttr("title", "生产安全教育系统").setAttr("amsroot",
						"educationsystem");
			} else if (getRequest().getServletPath().startsWith(
					"/pricemonitoring/")) {
				setAttr("title", "价格监测与分析系统").setAttr("amsroot",
						"pricemonitoring");
			} else if (getRequest().getServletPath().startsWith(
					"/publicservice/")) {
				setAttr("title", "公众服务平台").setAttr("amsroot", "publicservice");
			} else if (getRequest().getServletPath().startsWith(
					"/storagemanage/")) {
				setAttr("title", "粮食仓储管理系统")
						.setAttr("amsroot", "storagemanage");
			} else if (getRequest().getServletPath().startsWith(
					"/supervisioncheck/")) {
				setAttr("title", "监督检查执法系统").setAttr("amsroot",
						"supervisioncheck");
			} else if (getRequest().getServletPath().startsWith("/datacenter/")) {
				setAttr("title", "粮食海量实时数据中心").setAttr("amsroot",
						"supervisioncheck");
			} else if (getRequest().getServletPath().startsWith("/visualize/")) {
				setAttr("title", "粮食收储可视化管理系统").setAttr("amsroot", "visualize");
			} else if (getRequest().getServletPath().startsWith(
					"/yuanliangzhuishu/")) {
				setAttr("title", "原粮质量安全追溯系统").setAttr("amsroot",
						"yuanliangzhuishu");
			}
			render(_url);
		} else {
			render("login.html");
		}
	}
	
	
	public void toHTML() {
		String url = (String) getAttr("_url_");
		render(url);
	}
}
