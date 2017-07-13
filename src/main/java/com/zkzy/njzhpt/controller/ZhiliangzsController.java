package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.ShouchukeshihuaBO;
import com.zkzy.njzhpt.bo.ZhiliangzsBO;
import com.zkzy.njzhpt.config.interfaces.ICommond;

public class ZhiliangzsController extends Controller {
	
	public void index(){
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) ZhiliangzsBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(ZhiliangzsBO.class),
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
	 * 保存入库质量追溯表
	 */
	public void addrukuzhiliang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ZhiliangzsBO.class).addrukuzhiliang(param).getData());
	}
	
	public void upqiuliangwuri(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ShouchukeshihuaBO.class).upqiuliangwuri(param).getData());
	}
	
	/**
	 * 实时库点信息
	 * @throws Exception
	 */
	public void querykudian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.querykudian(queryparam).getData().get("list"));
	}
	
	
	
}
