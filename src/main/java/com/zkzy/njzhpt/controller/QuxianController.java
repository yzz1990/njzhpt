package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.zkzy.njzhpt.bo.QuxianBo;

public class QuxianController extends Controller  {
	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
	
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) QuxianBo.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(QuxianBo.class),
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
		render(url);
	}
	
	
	
	@Clear
	public void cangfangdetail(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		setAttr("kdbm", getPara("kdbm"));
		setAttr("cfbm", getPara("cfbm"));
		render("dilixinxi/cangfangdetail.html");
	}
	
	@Clear
	public void kudian(){	
	
		String userAgent =getRequest().getHeader("user-agent");
		
		boolean flag = false;
		if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
				for (String item : agent) {
					if (userAgent.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
	
		setAttr("flag", flag);
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		setAttr("kdbm", getPara("kdbm"));
		render("dilixinxi/kudian.html");
	}
	
	
	@Clear
	public void cangfangxx1(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		setAttr("kdbm", getPara("kdbm"));
		render("dilixinxi/cangfangxx.html");
	}
	
	@Clear
	public void shipinxx(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		setAttr("kdbm", getPara("kdbm"));
		//setAttr("spcj",Duang.duang(QuxianBo.class).queryspcj(getPara("qyzzjgdm"),getPara("kdbm"))); 
		render("dilixinxi/shipinxx.html");
	}
	
	
	
	
	/**
	 * 百度地图获取库点信息
	 * @author yzz
	 * @throws Exception
	 */
	public void getqyinfo() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(QuxianBo.class).getqyinfo(param).getList());
	}
	
	/**
	 * 获取百度地图元素
	 * @author yzz
	 * @throws Exception
	 */
	public void getMapDocument() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(QuxianBo.class).getMapDocument(param).getData());
	}
	
	
	/**
	 * 百度地图获取企业点位
	 * @author yzz
	 * @throws Exception
	 */
	@Clear
	public void findAllQiye() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(QuxianBo.class).findAllQiye(param));
	}
	
	
	/**
	 * 百度地图获取库点点位
	 * @author yzz
	 * @throws Exception
	 */
	@Clear
	public void findAllKudian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(QuxianBo.class).findAllKudian(param));
	}
	
	
	
	
	
	/**
	 * 百度地图获取企业列表
	 * @author yzz
	 * @throws Exception
	 */
	public void queryQiyeInfo() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(QuxianBo.class).queryQiyeInfo(param).getData());
	}
	
	
	
	/**
	 * 区县平台获取视频点信息
	 * @throws Exception
	 */
	@Clear
	public void queryShipin() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
		renderJson(Duang.duang(QuxianBo.class).queryShipin(param));
	}
	
	

	/**
	 * 区县平台获取视频点信息
	 * @throws Exception
	 */
	public void querySpcj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());

		renderJson(Duang.duang(QuxianBo.class).queryspcj(param).getData());
	}
	
	
	

}
