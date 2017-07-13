package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.ZhiLiangGLBO;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.validator.CBLJHRenWuValidator;
import com.zkzy.njzhpt.validator.FeiKSHCRKSJ;
import com.zkzy.njzhpt.validator.xzKuCunValidator;

public class ZhiLiangGLController extends Controller {
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) ZhiLiangGLBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(ZhiLiangGLBO.class),
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
	public void selectZLXQ() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.selectZLXQ(param));
	}
	/**
	 * 查询非可视化库点出入库数据列表
	 * @throws Exception
	 */
	public void selectfeiksh() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.selectfeiksh(param));
	}
	
	/**
	 * 查询非可视化库点出入库数据列表
	 * @throws Exception
	 */
	public void kucunliebiao() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.kucunliebiao(param));
	}
	/**
	 * 分权限查询地区
	 * @throws Exception
	 */
	public void selectQuYu() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.selectQuYu(param));
	}
	/**
	 * 新增非可视化出入库数据
	 * @throws Exception
	 */

	@Before(FeiKSHCRKSJ.class)
	public void xzfeikshcrksj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZhiLiangGLBO.class).xzfeikshcrksj(param).getData());
	}
	public void kucunheji() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		if(param.get("xzqhdm") == null || "".equals(param.get("xzqhdm").toString())){
			param.remove("xzqhdm");
		}
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.kucunheji(param).getData());
	}
	/**
	 * 新增非可视化库存数据
	 * @throws Exception
	 */
	@Before(xzKuCunValidator.class)
	public void xzkucun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZhiLiangGLBO.class).xzkucun(param).getData());
	}
	/**
	 * 编辑非可视化库存数据
	 * @throws Exception
	 */
	public void bjkucun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZhiLiangGLBO.class).bjkucun(param).getData());
	}
	/**
	 * 删除非可视化库存数据-库点下所有库存
	 * @throws Exception
	 */
	public void delkucun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZhiLiangGLBO.class).delkucun(param).getData());
	}
	/**
	 * 编辑非可视化出入库数据
	 * @throws Exception
	 */
	
	@Before(FeiKSHCRKSJ.class)
	
	public void bjfeikshcrksj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZhiLiangGLBO.class).bjfeikshcrksj(param).getData());
	}
	/**
	 * 删除非可视化出入库数据
	 * @throws Exception
	 */
	public void delete() {
		String vSwiftNumber = getPara("vSwiftNumber");
		String qyzzjgdm = getPara("qyzzjgdm");
		String kdbm = getPara("kdbm");
		int sq=Db.update("delete from crk_Purchase where vSwiftNumber=? and qyzzjgdm=? and kdbm=? ",vSwiftNumber,qyzzjgdm,kdbm);
			if (sq>0) {
				Ret ret = Ret.create("ret", true);
				renderJson(ret.getData());
			} else {
			Ret ret = Ret.create("ret", false);
			renderJson("ret");
		}
	}

}
