package com.zkzy.njzhpt.controller;

import java.io.File;
import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.bo.AppBo;
import com.zkzy.njzhpt.bo.AppCommondBo;
import com.zkzy.njzhpt.bo.CangchuglBo;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.EmergencyBO;
import com.zkzy.njzhpt.bo.RenyuanBo;
import com.zkzy.njzhpt.bo.jibenxinxiBo;
import com.zkzy.njzhpt.config.interfaces.AppICommond;
import com.zkzy.njzhpt.config.interfaces.ICommond;

@Clear
public class AppController extends Controller {
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) AppBo.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(AppBo.class),
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
	
	/**
	 * 查找灵山出入库信息
	 * @throws Exception 
	 */
	public void searchlscrk() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson((Duang.duang(AppBo.class).searchlscrk(param)).getData());
	}
	
	/**
	 * 查找灵山仓房
	 * @throws Exception 
	 */
	public void searchlscf() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson((Duang.duang(AppBo.class).searchlscf(param)).getData().get("list"));
	}
	
	
	
	/**
	 * app中根据企业经营类型分类统计
	 * @throws Exception 
	 */
	
	public void GetQyInfo() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getQyInfo(param));
		
	}
	
	/**
	 * app中查询可视化库
	 * @throws Exception 
	 */
	public void GetKdKsh() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getKdKsh(param));
	}
	
	/**
	 * app中办公通讯录
	 * @throws Exception 
	 */
	public void GetTxBg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getTxBg(param));
	}
	
	/**
	 * app中获取办公人员
	 * @throws Exception 
	 */
	public void GetBgRy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getBgRy(param));
	}
	
	/**
	 * app中办公通讯录
	 * @throws Exception 
	 */
	public void GetTxyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getTxyj(param));
	}
	
	/**
	 * app中获取办公人员
	 * @throws Exception 
	 */
	public void GetyjRy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).getyjRy(param));
	}
	
	
	
	/**
	 * 地区信息 --- 查删增改
	 * @throws Exception
	 */
	
	public void findDiqu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findDiqu(queryparam));
	}
	
	/**
	 * 企业信息---查删增改
	 * @throws Exception
	 */
	
	public void findQiyexinxi() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findQiyexinxi(queryparam));
	}
	
	
	/**
	 * 库点信息 --- 查删增改
	 * @throws Exception
	 */
	
	public void findKudian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(jibenxinxiBo.class).findKudian(queryparam));
	}
	

	/**
	 * 查找视频（带参）
	 * @throws Exception 
	 */
	
	public void findshipinTree() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findShipin(queryparam).getList());
	}
	
	
	/**
	 * 获取人员通讯录
	 * @throws Exception 
	 */
	
	public void querytongxun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).querytongxuntxl(param));
	}
	
	/**
	 * 获取应急事件
	 * @throws Exception 
	 */
	
	public void queryyjsj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).queryyjsj(param));
	}
	
	/**
	 * 应急小组
	 */
	public void findxiaozu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		//ICommond icommond=CommondBo.getCommond();
		renderJson(Duang.duang(CangchuglBo.class).findxiaozu(queryparam));
	}
	
	/**
	 * 灵山报港
	 */
	public void loadlsbaogang(){	

		render("lingshan/baogang.html");
	}
	
	/**
	 * 灵山
	 */
	public void loadlschengzhong(){	
		render("lingshan/chengzhong.html");
	}
	
	/**
	 * 灵山
	 */
	public void loadlszhiliangjiance(){	
		render("lingshan/zhiliangjiance.html");
	}
	
	
	
	/**
	 * 灵山三维内部视频1
	 */
	public void loadneibuone(){
		setAttr("ip", "221.226.38.213:8010");
		setAttr("outterip","221.226.38.213:8010");
		setAttr("channel","8");
		setAttr("username","admin");
		setAttr("password","admin12345");
		setAttr("divid","sp1");
		render("yewu/shipindianjiankong/jiankongattr.html");
	}
	/**
	 * 灵山三维内部视频2
	 */
	public void loadwaibu(){
		setAttr("ip", "221.226.38.213:8010");
		setAttr("outterip","221.226.38.213:8010");
		setAttr("channel","2");
		setAttr("username","admin");
		setAttr("password","admin12345");
		setAttr("divid","sp1");
		render("yewu/shipindianjiankong/jiankongattr.html");
	}

	/**
	 * 灵山三维外部视频
	 */
	public void loadneibutwo(){
		setAttr("ip", "221.226.38.213:8010");
		setAttr("outterip","221.226.38.213:8010");
		setAttr("channel","9");
		setAttr("username","admin");
		setAttr("password","admin12345");
		setAttr("divid","sp1");
		render("yewu/shipindianjiankong/jiankongattr.html");
	}
	
	
	/**
	 * 应急保障人员通讯录
	 */
	
	public void queryYjbzTxl() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).querytongxunyjApp(param));
	}
	
	
	/**
	 * 仓储管理企业备案
	 */
	
	public void queryCcbaqy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		AppICommond icommond=AppCommondBo.getCommond(param);
		renderJson(icommond.findQiyebeian(param));
	}
	
	
	/**
	 * 仓储管理熏蒸备案
	 */
	
	public void queryCcbaXz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		AppICommond icommond=AppCommondBo.getCommond(param);
		renderJson(icommond.findXunzheng(param));
	}
	
	
	/**
	 * 仓储管理药剂备案
	 */
	
	public void queryCcbaYj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		AppICommond icommond=AppCommondBo.getCommond(param);
		renderJson(icommond.findYaoji(param));
	}
	
	
	/**
	 * 政务办公企业备案
	 */
	
	public void queryzwbgQy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findQyba(param));
	}
	
	/**
	 * 政务办公企业备案状态(审核通过)
	 */
	
	public void queryzwbgQyTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).updateQyba(param));
	}
	
	/**
	 * 政务办公企业备案状态(审核不通过)
	 */
	
	public void queryzwbgQyBuTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).saveBohui(param));
	}
	
	
	
	/**
	 * 政务办公熏蒸备案
	 */
	
	public void queryzwbgXz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findXzba(param));
	}
	
	

	/**
	 * 政务办公企业备案状态(审核通过)
	 */
	
	public void queryzwbgXzTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).updateXzbaTg(param));
	}
	
	/**
	 * 政务办公企业备案状态(审核不通过)
	 */
	
	public void queryzwbgXzBuTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).saveXzBohui(param));
	}
	
	
	
	
	/**
	 * 政务办公药剂备案
	 */
	
	public void queryzwbgYj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findYjba(param));
	}
	
	
	/**
	 * 政务办公药剂备案状态(审核通过)
	 */
	
	public void queryzwbgYjTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).updateYjbaTg(param));
	}
	
	/**
	 * 政务办公药剂备案状态(审核不通过)
	 */
	
	public void queryzwbgYjBuTg() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).saveYjBohui(param));
	}
	/**
	 * 地图中粮食局接口
	 */
	
	public void queryLsj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).queryLsj(param));
	}
	/**
	 * 熏蒸备案 作业人员获取
	 */
	public void findzuoyerenyuan() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(AppBo.class).findzuoyerenyuan(param));
	}
	/**
	 * 获取熏蒸对象信息
	 * @throws Exception
	 */
	public void findxunzhendx() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(AppBo.class).findxunzhendx(param));
	}
	
	/**
	 * 获取熏蒸药剂信息
	 * @throws Exception
	 */
	public void findxunzhenyj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
	    renderJson(Duang.duang(AppBo.class).findxunzhenyj(param));
	}
	/**
	 * 仓储管理 药剂出库查找药剂库存
	 * @throws Exception
	 */
	public void findYjckxx() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(AppBo.class).findYjckxx(queryparam));
	}
	
	/**
	 * 熏蒸作业查询
	 * @throws Exception
	 */
	public void findxzzy() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		//renderJson(Duang.duang(CangchuglBo.class).findxzzy(queryparam));
		AppICommond icommond=AppCommondBo.getCommond(queryparam);
		renderJson(icommond.findxzzy(queryparam));
	}
	/**
	 * 获取轮换批文
	 * @param queryparam
	 */
	public  void  findInDoc(){
		
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		Record record=AppBo.findInDoc(queryparam);
		if(record!=null){
			setAttr("ret", true);
			File  file =new File("D:/upload/"+record.get("docpath"));
			renderFile(file);
		}else{
			setAttr("ret", false);
			renderJson(Ret.create("ret", false));
		}
		
		
		
	}
	
	/**
	 * 获取轮入批文
	 * @param queryparam
	 */
	
   public  void  findOutDoc(){
		
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());		
		Record record=  AppBo.findOutDoc(queryparam);
		if(record!=null){
			File  file =new File("D:/upload/"+record.get("docpath"));
			renderFile(file);
		}else{
			setAttr("ret", false);
			renderJson(Ret.create("ret", false));
		}
		
		
		
	}
   
   
   public void getDocFile(){
	   getResponse().setContentType("text/html;charset=utf-8");
	   HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());	
	   File file=new File((String)queryparam.get("docpath"));
	   renderFile(file);
   }
   
	
}
