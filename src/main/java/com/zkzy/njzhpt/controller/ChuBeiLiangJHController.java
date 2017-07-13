package com.zkzy.njzhpt.controller;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.ParamUtil;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.LunHuanGuanLiBO;
import com.zkzy.njzhpt.bo.RenyuanBo;
import com.zkzy.njzhpt.bo.ShouchukeshihuaBO;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;
import com.zkzy.njzhpt.validator.CBLJHRenWuValidator;
import com.zkzy.njzhpt.validator.LoginValidator;
import com.zkzy.njzhpt.validator.ShiChuQiYeValidator;
import com.zkzy.njzhpt.validator.chubeliangjihuaValidator;
import com.zkzy.njzhpt.validator.CBLJHRenWuValidator;

public class ChuBeiLiangJHController extends Controller {
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) ChuBeiLiangJHBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(ChuBeiLiangJHBO.class), ParamUtil.getParamMap(this.getParaMap()));
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


	public void chubeilianghuizongb() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chubeilianghuizongb(queryparam));
	}
	public void chubeilianghuizongheji() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chubeilianghuizongheji(queryparam).getData());
	}
	
	public void fenxingzhi() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.fenxingzhi(queryparam).getData());
	}
	@Before(CBLJHRenWuValidator.class)
	public void cbljhrenwu() throws Exception {
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).cbljhrenwuzsg(param).getData().get("list"));
	}
	//储备粮年度计划列表
	public void cbljhniandurenwu() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cbljhniandurenwu(queryparam).getData().get("list"));
	}
	//储备粮年度计划-合计
	public void cbljhnianduheji() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cbljhnianduheji(queryparam).getData().get("list"));
	}
	public void chubeiliangrenwuheji() throws Exception {
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).chubeiliangrenwuheji1(param).getData());
	}
	
	public void cblswkc() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cblswkc(queryparam).getData().get("list"));
	}
	public void cblswkck() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cblswkck(queryparam).getData().get("list"));
	}
	public void cbljh() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cbljh(queryparam).getData().get("list"));
	}
	public void chakankudian() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakankudian(queryparam).getData().get("list"));
	}
	public void chakankudiankc() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakankudiankc(queryparam).getData().get("list"));
	}
	public void ckcbljherjiqiye() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.ckcbljherjiqiye(queryparam).getData().get("list"));
	}
	public void chakankudian2() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakankudian2(queryparam).getData().get("list"));
	}
	public void chakankudian2kc() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakankudian2kc(queryparam).getData().get("list"));
	}
	public void chakanecbljhkd() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakanecbljhkd(queryparam).getData().get("list"));
	}
	public void chakancangfang() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakancangfang(queryparam).getData().get("list"));
	}
	public void chakancangfangkc() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakancangfangkc(queryparam).getData().get("list"));
	}
	public void chakancbljhcf() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakancbljhcf(queryparam).getData().get("list"));
	}
	public void shiwukucunheji() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.shiwukucunheji(queryparam).getData().get("list"));
	}
	public void shiwukucunhejixinxi() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.shiwukucunhejixinxi(queryparam).getData().get("list"));
	}
	public void cbljhheji() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cbljhheji(queryparam).getData().get("list"));
	}
	/**
	 * 查询有储备粮计划的一级企业
	 * @throws Exception
	 */
	public void findcompany() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findcompany(queryparam).getData().get("list"));
	}
	/**
	 * 查询有储备粮计划的一级企业
	 * @throws Exception
	 */
	public void findcompanyy() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findcompanyy(queryparam).getData().get("list"));
	}
	/**
	 * 查询市储企业
	 */
	public void shichuqiye(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).shichuqiye(param));
	}
	/**
	 * 查询有储备粮计划的库点
	 */
	public void findcckd(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		param.put("qyzzjgdm", getPara("qyzzjgdm"));
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).findcckd(param));
	}
	/**
	 * 查询所有承储企业(包含往年的)
	 * @throws Exception 
	 */
	public void findallcompany() throws Exception{
		
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findallcompany(queryparam).getData().get("list"));
		
		/*HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).findallcompany(param));*/
	}
	/**
	 * 查询承储库-qyzzjgdm
	 * @throws Exception 
	 */
	public void chengchuku() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).chengchuku(param));
	}
	/**
	 * 查询承储库-qiyeid
	 * @throws Exception 
	 */
	public void chengchuku1() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).chengchuku1(param));
	}
	/**
	 * 查询所有承储库-qiyeid
	 * @throws Exception 
	 */
	public void allchengchuku() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).allchengchuku(param));
	}
	/**
	 * 查询承储点
	 * @throws Exception 
	 */
	public void chengchudian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).chengchudian(param));
	}
	/**
	 * 查询二级企业和库点
	 * @throws Exception 
	 *//*
	public void finderjiqiyekd() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		param.put("yijiqiye", getPara("yijiqiye"));
		//角色
		List<String> authlist=UserKit.currentUserInfo().getAuth();
		if(authlist.contains("auth_company")) {
			String sql="SELECT qyzzjgdm,xzqhdm FROM tz_qiye WHERE id=?";
			Record re=Db.findFirst(sql,UserKit.currentUserInfo().getUser().getString("id"));
			param.put("qyzzjgdm", re.get("qyzzjgdm"));
		}
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).finderjiqiyekd(param));
	}*/
	/**
	 * 查询有储备粮计划的二级企业
	 * @throws Exception
	 */
	public void finderjiqiyekd() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.finderjiqiyekd(queryparam).getData().get("list"));
	}
	/**
	 * 查询有储备粮计划的仓房
	 */
	public void selectcfmc(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		param.put("qyzzjgdm", getPara("qyzzjgdm"));
		param.put("kdbm", getPara("kdbm"));
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).selectcfmc(param));
	}
	public void chakanxiangxich() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.chakanxiangxich(queryparam).getData().get("list"));
	}
	public void shengchenghuizongb() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.shengchenghuizongb(queryparam).getData().get("list"));
	}
	public void cblglzxShenHe() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.cblglzxShenHe(queryparam).getData().get("list"));
	}
	@Before(chubeliangjihuaValidator.class)
	public void bianji() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).bianji(param).getData());
	}
	@Before(CBLJHRenWuValidator.class)
	public void cbljhrwbianji() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).cbljhrwbianji(param).getData());
	}
	@Before(CBLJHRenWuValidator.class)
	public void cbljhrwbj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).cbljhrwbj(param).getData());
	}
	/**
	 * 保存储备粮计划驳回理由
	 * @throws Exception
	 */
	public void saveBohuicbljh() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(ChuBeiLiangJHBO.class).saveBohuicbljh(param).getData());
	}
	public void delete() {
		String id = getPara("id");
		if (id != null) {
			boolean flag = Db.deleteById("njpt_chubeiliangjihua", id);
			if (flag) {
				Ret ret = Ret.create("ret", true);
				renderJson(ret.getData());
			}
		} else {
			Ret ret = Ret.create("ret", false);
			renderJson("ret");
		}
	}
	
	public void deleterenwu() {
		String id = getPara("id");
		if (id != null) {
			boolean flag = Db.deleteById("njpt_chubeiliangjihua_renwu", id);
			if (flag) {
				Ret ret = Ret.create("ret", true);
				renderJson(ret.getData());
			}
		} else {
			Ret ret = Ret.create("ret", false);
			renderJson("ret");
		}
	}
	@Before(ShiChuQiYeValidator.class)
	public void saveshichuqiye() throws Exception {
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).saveshichuqiye(param).getData());
	}
	public void bianjishichuqiye() throws Exception {
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ChuBeiLiangJHBO.class).bianjishichuqiye(param).getData());
	}
	public void deleteshichuqiye() {
		String id = getPara("id");
		if (id != null) {
			boolean flag = Db.deleteById("njpt_chubeiliangjihua_diqu", id);
			if (flag) {
				Ret ret = Ret.create("ret", true);
				renderJson(ret.getData());
			}else {
				Ret ret = Ret.create("ret", false);
				renderJson(ret.getData());
			}
		} else {
			Ret ret = Ret.create("ret", false);
			renderJson(ret);
		}
	}
	public void xinzeng() {
		String id = getPara("id");
		if (id != null) {
			String sql = "update njpt_chubeiliangjihua set blogname=?,password=? where id =?";
			Db.update(sql, getPara(""), getPara(""), getPara(""));

			Ret ret = Ret.create("ret", sql);
			renderJson(ret.getData());
		} else {
			HashMap<String, Object> map = ParamUtil.getParamMap(this.getParaMap());
			Record jihua = new Record().set("id", SqlUtil.uuid());
			jihua.setColumns(map);
			Ret ret = Ret.create("ret", Db.save("njpt_chubeiliangjihua", jihua));
			renderJson(ret.getData());
		}

	}
}
