package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.jibenxinxiBo;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.validator.addCangfangValidator;
import com.zkzy.njzhpt.validator.addKudianValidator;
import com.zkzy.njzhpt.validator.addQiyexinxiValidator;
import com.zkzy.njzhpt.validator.addQxValidator;
import com.zkzy.njzhpt.validator.editCangfangValidator;
import com.zkzy.njzhpt.validator.editKudianValidator;
import com.zkzy.njzhpt.validator.editQiyexinxiValidator;

public class JibenxinxiController extends Controller {
	
	public void index(){
		
	}
	
	public void toHTML() {
		String url = (String) getAttr("_url_");
		render(url);
	}
	
	public void changeFontSize() {

		String callback = getPara("callback");
		String url = getPara("url");
		String size = getPara("size");
		if ("".equals(size) || size == null) {
			getSession().setAttribute("size", 14);
			setAttr("size", "14px");
		} else {
			getSession().setAttribute("size", size);
			setAttr("size", size + "px");
		}
		String json = "{\"ret\":true,\"message\":\"成功！\",\"size\":'" + size
				+ "px'}";
		// System.out.println("hello world==="+size);
		renderText(callback + "(" + json + ")");
	}
	
	/**
	 * 企业
	 * @throws Exception
	 */
	public void remeberRate() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).remeberRate(queryparam).getData());
	}

	/**
	 * 企业
	 * @throws Exception
	 */
	public void findFate() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findFate(queryparam).getData());
	}
	
	
	
	/**
	 * 企业信息提交审核_区县审核不通过
	 * @throws Exception
	 */
	public void qyTjShBtg() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.qyTjShBtg(queryparam).getData());
	}
	
	/**
	 * 企业信息提交审核
	 * @throws Exception
	 */
	public void qyTjSh() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.qyTjSh(queryparam).getData());
	}
	
	
	/**
	 * 企业信息---查删增改
	 * @throws Exception
	 */
	
	public void findQiyexinxi() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.findQiyexinxi(queryparam));
	}
	
	/**
	 * 删除企业信息
	 */
	@Before(Tx.class)
	public void delQiyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delQiyexinxi(param).getData());
	}
	
	/**
	 * 新增企业信息
	 * @author yzz
	 */
	@Before({addQiyexinxiValidator.class,Tx.class})
	public void addQiyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addQiyexinxi(param).getData());
	}
	
	/**
	 * 编辑企业信息
	 * @author yzz
	 */
	@Before({editQiyexinxiValidator.class,Tx.class})
	public void upQiyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).upQiyexinxi(param).getData());
	}
	
	
	/**
	 * 地区信息 --- 查删增改
	 * @throws Exception
	 */
	
	public void findDiqu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.findDiqu(queryparam));
	}
	
	/**
	 * 查找地区年度
	 * @throws Exception
	 */
	
	public void findDiquniandu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findDiquniandu(queryparam));
	}
	
	/**
	 * 查找地区年度排序
	 * @throws Exception
	 */
	
	public void findDiquniandupx() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.findDiquniandupx(queryparam));
	}
	
	/**
	 * 
	 */
	@Before(Tx.class)
	public void addDiquniandu(){
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addDiquniandu(queryparam).getData());
	}
	
	@Before(Tx.class)
	public void delDiqu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delDiqu(param).getData());
	}
	
	@Before(Tx.class)
	public void delDiquniandu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delDiquniandu(param).getData());
	}
	
	@Before({Tx.class,addQxValidator.class})
	public void addDiqu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addDiqu(param).getData());
	}
	
	public void upDiqu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).upDiqu(param).getData());
	}
	
	
	/**
	 * 库点信息 --- 查删增改
	 * @throws Exception
	 */
	@Clear
	public void findKudian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findKudian(queryparam));
	}
	/**
	 * 删除库点信息
	 */
	public void delKudian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delKudian(param).getData());
	}
	
	/**
	 * 新增库点信息
	 */
	@Before(addKudianValidator.class)
	public void addKudian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addKudian(param).getData());
	}
	/**
	 * 编辑库点信息
	 */
	@Before(editKudianValidator.class)
	public void upKudian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).upKudian(param).getData());
	}
	/**
	 * 获取天气信息
	 */
	
	public void findtianqi() throws Exception{
		HashMap<String, Object> queryparam= ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findtianqi(queryparam).getData());
	}
	/**
	 * 查找地区
	 */
	/*public void finddiqu(){
		renderJson(Duang.duang(JibenxinxiDao.class).finddiqu());
	}*/
	
	
	/**
	 * 仓发信息   查删增改
	 * @throws Exception
	 */
	public void findCangfang() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findCangfang(queryparam));
	}
	/**
	 * 删除仓房信息
	 */
	public void delCangfang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delCangfang(param).getData());
	}
	
	/**
	 * 添加仓房信息
	 */
	@Before(addCangfangValidator.class)
	public void addCangfang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addCangfang(param).getData());
	}
	
	/**
	 * 编辑仓房信息
	 */
	@Before(editCangfangValidator.class)
	public void upCangfang(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).upCangfang(param).getData());
	}
	
	
	
	/**
	 * 廒间信息       查删增改
	 * @throws Exception
	 */
	public void findAojian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findAojian(queryparam));
	}
	
	public void delAojian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).delAojian(param).getData());
	}
	
	public void addAojian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).addAojian(param).getData());
	}
	
	public void upAojian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).upAojian(param).getData());
	}
	
	/**
	 * 查询企业下库点
	 */
	public void findsuoshukd(){
		String qyzzjgdm = getPara("qyzzjgdm");
		renderJson(Duang.duang(jibenxinxiBo.class).findsuoshukd(qyzzjgdm));
	}
	
	/**
	 * 获取仓房总仓容--统一方法
	 */
	
	public void queryCfZcr(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).queryCfZcr(param));
	}	
	
	
	/**
	 * 获取仓房总库存-统一方法
	 */
	
	public void queryCfZkc(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).queryCfZkc(param));
	}	
	
	public void findzongrk(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findzongrk(param));
	}
	
	

}
