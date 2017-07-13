package com.zkzy.njzhpt.controller;

import java.util.HashMap;

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
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.bo.CangchuglBo;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.RenyuanBo;
import com.zkzy.njzhpt.bo.jibenxinxiBo;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;
import com.zkzy.njzhpt.validator.addQiyebeianValidator;
import com.zkzy.njzhpt.validator.addXunzhenbeianValidator;
import com.zkzy.njzhpt.validator.addYaojibeianValidator;
import com.zkzy.njzhpt.validator.renYuanValidator;

public class RenyuanController extends Controller{

	
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) RenyuanBo.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(RenyuanBo.class),
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
	
	
	

	/**
	 * 编辑熏蒸备案信息
	 * @throws Exception
	 */
	public void xunzhengbeianEdit() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findXunzheng(param).getList().get(0));
		
		render("cangchuguanli_zsg/qiyebeian/xunzhengbeian_edit.html");
	}
	
	/**
	 * 编辑熏蒸备案信息
	 * @throws Exception
	 */
	public void XunzhengbeianEdit() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findXunzheng(param).getList().get(0));
		
		render("cangchuguanli_zsg/qiyebeian/xunzhengbeian_zsg.html");
	}
	
	/**
	 * 编辑药剂备案信息
	 * @throws Exception
	 */
	public void yaojibeianEdit() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findYaoji(param).getList().get(0));
		
		render("cangchuguanli_zsg/qiyebeian/yaojibeian_edit.html");
	}

	/**
	 * 保存熏蒸备案
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(addXunzhenbeianValidator.class)
	public void addXzBa() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addXzBa(param).getData());
	}
	/**
	 * 获取熏蒸备案列表
	 * @throws Exception
	 */
	public void findXunzheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		ICommond icommond=CommondBo.getCommond();
	    renderJson(icommond.findXunzheng(param));
	}
	/**
	 * 药剂出库 新增
	 */
	public void findXunztg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findXunzheng(param));
	}
	/**
	 * 保存药剂审批人历史信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void addYjshenpixinxi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addYjshenpixinxi(param).getData());
	}
	/**
	 * 保存药剂备案
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(addYaojibeianValidator.class)
	public void addYjBa() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addYjBa(param).getData());
	}
	/**
	 * 获取药剂备案列表
	 * @throws Exception
	 */
	public void findYaoji() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		ICommond icommond=CommondBo.getCommond();
	    renderJson(icommond.findYaoji(param));
	}
	/**
	 * 获取上一步流程信息
	 * @throws Exception
	 */
	public void findliuchengxinxi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findliuchengxinxi(param));
	}
	/**
	 * 获取熏蒸备案是的备案信息
	 * @throws Exception
	 */
	public void findXzbeianxinxi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findXzbeianxinxi(param));
	}
	/**
	 * 获取药剂库存信息
	 * @throws Exception
	 */
	public void findyjkcxinxi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findyjkcxinxi(param));
	}
	/**
	 * 获取作业人员信息
	 * @throws Exception
	 */
	public void findzuoyerenyuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findzuoyerenyuan(param));
	}
	/**
	 * 获取熏蒸对象信息
	 * @throws Exception
	 */
	public void findxunzhendx() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findxunzhendx(param));
	}
	/**
	 * 获取熏蒸药剂信息
	 * @throws Exception
	 */
	public void findxunzhenyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).findxunzhenyj(param));
	}
	/**
	 * 查看审核界面获取审批信息
	 * @throws Exception
	 */
	public void shenpiview() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findQiyebeian(param).getList().get(0));
		render("qiyebeian/shenpiview.html");
	}
	
	
	/**
	 * 查看熏蒸界面获取审批信息
	 * @throws Exception
	 */
	public void shenpixzview() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findXzba(param).getList().get(0));
		render("qiyebeian/shenpixzview.html");
	}
	/**
	 * 查看熏蒸界面获取审批信息
	 * @throws Exception
	 */
	public void shenpiyjview() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findYjba(param).getList().get(0));
		render("qiyebeian/shenpiyjview.html");
	}
	
	/**
	 * 保存驳回理由
	 * @throws Exception
	 */
	public void saveBohui() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohui(param).getData());
	}
	
	

	/**
	 * 保存熏蒸驳回理由
	 * @throws Exception
	 */
	public void saveBohuixz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohuixz(param).getData());
	}
	/**
	 * 保存熏蒸驳回理由
	 * @throws Exception
	 */
	public void saveBohuiyjsq() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohuiyjsq(param).getData());
	}
	/**
	 * 保存药剂驳回理由
	 * @throws Exception
	 */
	public void saveBohuiyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohuiyj(param).getData());
	}
	/**
	 * 保存轮换审批驳回理由
	 * @throws Exception
	 */
	public void saveBohuilunhuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohuilunhuan(param).getData());
	}
	/**
	 * 保存验收申请驳回理由
	 * @throws Exception
	 */
	public void saveBohuishenqing() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(RenyuanBo.class).saveBohuishenqing(param).getData());
	}
	
	/**
	 * 驳回界面
	 * @throws Exception
	 */
	public void bohui() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findQiyebeian(param).getList().get(0));
	    //判断到底是 审核完成查看理由 还是 驳回页面的
	    if(param.get("ck").equals("0")){
	    	setAttr("isshenhe", "1");
	    }else{
	    	setAttr("isshenhe", "0");
	    }
		render("qiyebeian/bohui.html");
	}
	
	/**
	 * 熏蒸驳回界面
	 * @throws Exception
	 */
	public void bohuixz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findXzbaa(param).getList().get(0));
		
		render("qiyebeian/bohuixz.html");
	}
	/**
	 * 药剂驳回界面
	 * @throws Exception
	 */
	public void bohuiyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findYjba(param).getList().get(0));
		
		render("qiyebeian/bohuiyj.html");
	}
	/**
	 * 轮换驳回界面
	 * @throws Exception
	 */
	public void bohuilunhuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findLHba(param).getList());
		
		render("qiyebeian/bohuilunhuan.html");
	}
	/**
	 * 轮换驳回界面-填写理由页面
	 * @throws Exception
	 */
	public void bohuilunhuanxz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", new Record().set("id", param.get("id")));
		
		render("qiyebeian/bohuilunhuan_xz.html");
	}
	/**
	 * 轮换驳回界面-填写理由页面
	 * @throws Exception
	 */
	public void tongguolunhuanxz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", new Record().set("id", param.get("id")));
		
		render("qiyebeian/tongguolunhuan_xz.html");
	}
	/**
	 * 轮换驳回界面-填写理由页面
	 * @throws Exception
	 */
	public void bohuiyaojishenpi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		   setAttr("object", Duang.duang(CangchuglBo.class).findYjck(param).getList().get(0));
		render("qiyebeian/bohuiyaojishenpi_yj.html");
	}
	/**
	 * 审核界面获取审批信息
	 * @throws Exception
	 */
	public void shenpi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findQiyebeian(param).getList().get(0));
		render("qiyebeian/shenpi.html");
	}
	
	
	/**
	 * 审核--熏蒸备案
	 * @throws Exception
	 */
	public void shenpixzba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findXunzheng(param).getList().get(0));
		
		render("qiyebeian/shenpixzba.html");
	}
	/**
	 * 审核--熏蒸备案
	 * @throws Exception
	 */
	public void shenpiyjba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findYaoji(param).getList().get(0));
		
		render("qiyebeian/shenpiyjba.html");
	}
	
	/**
	 * 获取审批列表
	 * @author yzz
	 * @throws Exception 
	 */
	public void findQyba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findQyba(param));
	}
	/**
	 * 夏粮五日报表续期查询信息
	 * @throws Exception
	 */
	public void queryxuqi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).queryxuqi(param));
	}
	/**
	 * 秋粮五日报表续期查询信息
	 * @throws Exception
	 */
	public void queryxuqiql() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).queryxuqiql(param));
	}
	
	/**
	 * 保存续期的夏粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public void addXlwuri() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addXlwuri(param).getData());
	}
	/**
	 * 保存续期的秋粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public void addQlwuri() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addQlwuri(param).getData());
	}
	/**
	 * 获取审批列表
	 * @author yzz
	 * @throws Exception 
	 */
	public void findXzba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findXzba(param));
	}

	/**
	 * 获取审批列表
	 * @author yzz
	 * @throws Exception 
	 */
	public void findYjba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findYjba(param));
	}
	
	/**
	 * 删除企业备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public void delQiyebeian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).delqyba(param).getData());
	}
	
	
	
	/**
	 * 删除熏蒸备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public void delxzba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).delxzba(param).getData());
	}
	/**
	 * 删除药剂备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public void delyjba() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).delyjba(param).getData());
	}
	
	/**
	 * 编辑企业备案信息
	 * @throws Exception
	 */
	public void qiyebeianEdit() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findQiyebeian(param).getList().get(0));
		
		render("cangchuguanli_zsg/qiyebeian/qiyebeian_zsg.html");
	}
	/**
	 * 编辑企业备案信息
	 * @throws Exception
	 */
	public void QiyebeianEdit() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
	    setAttr("object", Duang.duang(RenyuanBo.class).findQiyebeian(param).getList().get(0));
		
		render("cangchuguanli_zsg/qiyebeian/qiyebeian_zsgg.html");
	}
	
	
	/**
	 * 获取流程图信息
	 * @throws Exception
	 */
	public void liuchengtu() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());	
		HashMap<String, Object> qiyeparam=new HashMap<String, Object>();
		qiyeparam.put("id", param.get("qiyeid"));
		HashMap<String, Object> liuchengparam=new HashMap<String, Object>();
		liuchengparam.put("liuchenghao", param.get("liuchenghao"));
		String beianriqi=param.get("nyr")+" "+param.get("sfm");
		liuchengparam.put("beianriqi", beianriqi);
		liuchengparam.put("qiyeid", param.get("qiyeid"));
		setAttr("qyinfo", Duang.duang(RenyuanBo.class).findQiye(qiyeparam).getList().get(0));
	    setAttr("list", Duang.duang(RenyuanBo.class).findliucheng(liuchengparam).getList());
		render("cangchuguanli_zsg/qiyebeian/liuchengtu.html");
	}
	
	/**
	 * 获取流程图信息
	 * @throws Exception
	 */
	public void liuchengtuxz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());	
		HashMap<String, Object> qiyeparam=new HashMap<String, Object>();
		qiyeparam.put("id", param.get("qiyeid"));
		HashMap<String, Object> liuchengparam=new HashMap<String, Object>();
		liuchengparam.put("liuchenghao", param.get("liuchenghao"));
		String beianriqi=param.get("nyr")+" "+param.get("sfm");
		liuchengparam.put("beianriqi", beianriqi);
		liuchengparam.put("qiyeid", param.get("qiyeid"));
		setAttr("qyinfo", Duang.duang(RenyuanBo.class).findQiye(qiyeparam).getList().get(0));
	    setAttr("list", Duang.duang(RenyuanBo.class).findxzliucheng(liuchengparam).getList());
		render("cangchuguanli_zsg/qiyebeian/liuchengtu.html");
	}
	/**
	 * 获取流程图信息(轮换)
	 * @throws Exception
	 */
	public void liuchengtulunhuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());	
		HashMap<String, Object> qiyeparam=new HashMap<String, Object>();
		qiyeparam.put("qyzzjgdm", param.get("qiyeid"));
		HashMap<String, Object> liuchengparam=new HashMap<String, Object>();
		liuchengparam.put("liuchenghao", param.get("liuchenghao"));
		liuchengparam.put("qyzzjgdm", param.get("qiyeid"));
		liuchengparam.put("lhid", param.get("lhid"));
		setAttr("qyinfo", Duang.duang(RenyuanBo.class).findQiye(qiyeparam).getList().get(0));
	    setAttr("list", Duang.duang(RenyuanBo.class).findlunhuanlc(liuchengparam).getList());
		render("cangchuguanli_zsg/qiyebeian/liuchengtu.html");
	}
	/**
	 * 获取流程图信息
	 * @throws Exception
	 */
	public void liuchengtuyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());	
		HashMap<String, Object> qiyeparam=new HashMap<String, Object>();
		qiyeparam.put("id", param.get("qiyeid"));
		HashMap<String, Object> liuchengparam=new HashMap<String, Object>();
		liuchengparam.put("liuchenghao", param.get("liuchenghao"));
		String beianriqi=param.get("nyr")+" "+param.get("sfm");
		liuchengparam.put("beianriqi", beianriqi);
		liuchengparam.put("qiyeid", param.get("qiyeid"));
		setAttr("qyinfo", Duang.duang(RenyuanBo.class).findQiye(qiyeparam).getList().get(0));
	    setAttr("list", Duang.duang(RenyuanBo.class).findyjliucheng(liuchengparam).getList());
		render("cangchuguanli_zsg/qiyebeian/liuchengtu.html");
	}
	
	
	/**
	 * 获取流程图信息
	 * @throws Exception
	 */
	public void liuchengtu1() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());	
		HashMap<String, Object> liuchenparam=new HashMap<String, Object>();
		liuchenparam.put("liuchenghao", param.get("liuchenghao"));

	    setAttr("list", Duang.duang(RenyuanBo.class).findshenpiliucheng(liuchenparam).getList());
		render("cangchuguanli_zsg/qiyebeian/liuchengtu1.html");
	}
	
	
	
	/**
	 * 获取企业备案信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findQiyebeian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findQiyebeian(param));
	}
	/**
	 * 获取企业信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findqiye() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findqiye(param));
	}
	
	/**
	 * 保存企业备案
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(addQiyebeianValidator.class)
	public void addQiyebeian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addQiyebeian(param).getData());
	}
	
	
	/**
	 * 获取审核企业
	 * @author yzz
	 * @throws Exception 
	 */
	public void findQiye() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findQiye(param));
	}
	/**
	 * 获取药库点
	 * @author yzz
	 * @throws Exception 
	 */
	public void findYaokun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findYaokun(param));
	}
	/**
	 * 获取收储企业
	 * @author yzz
	 * @throws Exception 
	 */
	public void findQiyexinxi() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findQiyexinxi(param));
	}
	
	/**
	 * 获取熏蒸方式
	 * @author njl
	 * @throws Exception 
	 */
	public void findXunzhengname() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findxunzhengname(param));
	}
	/**
	 * 获取药剂库点名
	 * @author njl
	 * @throws Exception 
	 */
	public void findYaojiname() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findyaojiname(param));
	}
		
	/**
	 * 获取人员通讯录信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findRenyuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findRenyuan(param));
	}
	
	
	
	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	public void findUserDep() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findUserDep(param));
	}
	
	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	public void findUserDepId() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findUserDepId(param));
	}
	
	
	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findDept() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findDept(param).getList());
	}
	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findYjdept() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findYjdept(param).getList());
	}
	
	
	/**
	 * 保存人员信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(renYuanValidator.class)
	public void addRenyuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addRenyuan(param).getData());
	}
	
	
	
	/**
	 * 获取部门粮食局
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryLSjAndQy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).queryLSjAndQy(param).getData());
	}
	
	
	
	/**
	 * 保存工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public void saveWorkPlan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).saveWorkPlan(param).getData());
	}
	
	
	
	/**
	 * 获取工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryWorkPlan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).queryWorkPlan(param).getList());
	}
	
	
	/**
	 * 删除工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public void deleteWorkPlan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).deleteWorkPlan(param).getData());
	}
	
	
	

	/**
	 * 获取流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findliucheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findliucheng(param));
	}
	
	/*public void findshenpiliucheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findshenpiliucheng(param));
	}*/
	public void findshenpiliucheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findshenpiliucheng(param));
	}
	
	
	/**
	 * 获取流程名称
	 * @author yzz
	 * @throws Exception 
	 */
	public void findlc() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findlc(param).getData());
	}

	/**
	 * 获取等级名称
	 * @author yzz
	 * @throws Exception 
	 */
	public void finddj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).finddj(param).getData());
	}
	/**
	 * 获取省市信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findcity() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findcity(param));
	}
	/**
	 * 获取区域信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void findquyu() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).findquyu(param));
	}

	/**
	 * 保存流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void saveliucheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).saveliucheng(param).getData());
	}
	
	
	
	/**
	 * 编辑流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void updateliucheng() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).updateliucheng(param).getData());
	}
	
	
	
	/**
	 * 删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void delLc() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).delLc(param).getData());
	}
	
	/**
	 * 关闭前判断删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void guanbidelLc() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).guanbidelLc(param).getData());
	}
	/**
	 * 删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void dellc() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).dellc(param).getData());
	}
	
	/**
	 * 保存新闻信息
	 * @author hw
	 * @throws Exception 
	 */
	public void addnews() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).addnews(param).getData());
	}
	
	/**
	 * 修改新闻信息
	 * @author hw
	 * @throws Exception 
	 */
	public void upnews() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson((Duang.duang(RenyuanBo.class)).upnews(param).getData());
	}
	
	/**
	 * 删除新闻
	 */
	public void delnews(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(RenyuanBo.class).delnews(param).getData());
	}
	
	/**
	 * 保存临时新闻
	 */
	@Before(Tx.class)
	public void uplsnews(){
		
		String userid = UserKit.currentUserInfo().getUser().getString("id");
		String newsid = getPara("newsid");
		//删除临时表中原来的内容
		String delsql = "Delete ls_newsconflict";
		int del = Db.update(delsql);
		//更新临时表
		if(!StrKit.isBlank(newsid)){  
			String[] idsnew = newsid.split(","); 
			 for (String str : idsnew) {  
				String uuid = SqlUtil.uuid();
	        	String savesql = "insert into ls_newsconflict values(?,?,?)";
	        	Db.update(savesql,uuid,userid,str);
	        }      
		}
		renderJson(del);
	}
	
	/**
	 * 加载临时采价点商品
	 */
	@Before(Tx.class)
	public void upconews(){
		String userid = getPara("userid");
		//删除临时表中原来的内容
		String delsql = "Delete njpt_newsconflict where userid = ?";
		Db.update(delsql,userid);
		//在临时表中插入对应的数据
		String insql = "insert into njpt_newsconflict select * from ls_newsconflict ";
		renderJson(Db.update(insql)); 
	}
	
}
