package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.zkzy.njzhpt.bo.ChengPinLiangBO;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.config.interfaces.ICommond;

public class ChengPinLiangController extends Controller {
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) ChengPinLiangBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(ChengPinLiangBO.class),
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
	public void selectCPLCBJH() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.selectCPLCBJH(param));
	}
}
