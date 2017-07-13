package com.zkzy.njzhpt.controller;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.bo.CangchuglBo;
import com.zkzy.njzhpt.bo.ShouchukeshihuaBO;
import com.zkzy.njzhpt.bo.YewuBO;

public class YewuController extends Controller {
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) YewuBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(YewuBO.class),
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

	public void toHTML() {
		String url = (String) getAttr("_url_");
		if (url.contains("jiankong.html")) {
			setAttr("spnet", "2");
		}
		render(url);
	}

	public void pingmiantu() throws Exception {
		HashMap<String, Object> param = ParamUtil
				.getParamMap(this.getParaMap());
		List<Record> plan = Duang.duang(YewuBO.class).queryPlan(param);

		
		if(plan.size()>0){
			setAttr("pictrue", plan.get(0).getStr("base64code"));
			setAttr("pingmiantu_width", plan.get(0).getStr("width"));
			setAttr("spcj", "2");
			render("pingmiantu.html");
		}else{
			render("noshuju.html");
		}
	}
	public void pingmiantu1() throws Exception{
		HashMap<String, Object> param = ParamUtil
				.getParamMap(this.getParaMap());
		renderJson(Duang.duang(YewuBO.class).queryPlan(param));

	}
	public void pingmiantuapp() throws Exception {
		HashMap<String, Object> param = ParamUtil
				.getParamMap(this.getParaMap());
		List<Record> plan = Duang.duang(YewuBO.class).queryPlan(param);

		
		if(plan.size()>0){
			setAttr("pictrue", plan.get(0).getStr("base64code"));
			setAttr("pingmiantuapp_width", plan.get(0).getStr("width"));
			setAttr("spcj", "2");
			render("pingmiantuapp.html");
		}else{
			render("noshuju.html");
		}
	}
	
	
	public void queryTongBu() throws Exception {
		Duang.duang(YewuBO.class).queryTongBu();
		
		renderNull();
	}
	public void queryLiangqingJcInfo() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(YewuBO.class).queryLiangqingJcInfo(queryparam));
	}
	
	public void queryLqqipaoJcInfo() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(YewuBO.class).queryLqqipaoJcInfo(queryparam));
	}
	public void queryCangfangJcQT() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(YewuBO.class).queryCangfangJcQT(queryparam));
	}
	
}
