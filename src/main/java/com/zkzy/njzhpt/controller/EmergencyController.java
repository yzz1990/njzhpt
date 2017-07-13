package com.zkzy.njzhpt.controller;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ggy.common.utils.ParamUtil;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.EmergencyBO;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.validator.addYjZtValidator;
import com.zkzy.njzhpt.validator.addYjZtdjValidator;
import com.zkzy.njzhpt.validator.addRenkouValidator;
import com.zkzy.njzhpt.validator.editYjZtValidator;
import com.zkzy.njzhpt.validator.editYjZtdjValidator;
import com.zkzy.njzhpt.validator.editRenkouValidator;

public class EmergencyController extends Controller {
	public void index(){
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) EmergencyBO.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(EmergencyBO.class),
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
	
	public void toHTML() {
		String url = (String) getAttr("_url_");
		render(url);
	}
	public void queryrenkou() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.queryrenkou(param));
	}
	
	/**
	 * 保存紧急状态
	 */
	@Before(addYjZtValidator.class)
	public void addemergency(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addemergency(param).getData());
	}
	/**
	 * 修改紧急状态
	 */
	@Before(editYjZtValidator.class)
	public void upemergency(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).upemergency(param).getData());
	}
	/**
	 * 删除紧急状态
	 */
	public void delemergency(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delemergency(param).getData());
	}
	
	/**
	 * 保存紧急状态等级
	 */
	@Before(addYjZtdjValidator.class)
	public void addemergencydj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addemergencydj(param).getData());
	}
	
	/**
	 * 获取人员通讯录
	 * @throws Exception 
	 */
	@Clear
	public void querytongxun() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).querytongxun(param));
	}
	

	/**
	 * 
	 */
	@Before(editYjZtdjValidator.class)
	public void upemergencydj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).upemergencydj(param).getData());
	}
	/**
	 * 
	 */
	public void upbumen(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).upbumen(param).getData());
	}
	/**
	 * 
	 */
	public void uprenyuanyj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).uprenyuanyj(param).getData());
	}
	/**
	 * 
	 */
	public void uprenyuantxl(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).uprenyuantxl(param).getData());
	}
	
	/**
	 * 删除紧急状态等级
	 */
	public void delemergencydj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delemergencydj(param).getData());
	}
	
	/**
	 * 保存人口
	 */
	@Before(addRenkouValidator.class)
	public void addrenkou(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addrenkou(param).getData());
	}
	
	/**
	 * 修改人口
	 */
	@Before(editRenkouValidator.class)
	public void uprenkou(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).uprenkou(param).getData());
	}
	
	/**
	 * 删除人口
	 */
	public void delrenkou(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delrenkou(param).getData());
	}
	
	/**
	 * 保存粮食收储企业资格备案
	 */
	public void addlsscba(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addlsscba(param).getData());
	}
	
	/**
	 * 修改粮食收储企业资格备案
	 */
	public void uplsscba(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).uplsscba(param).getData());
	}
	
	/**
	 * 删除粮食收储企业资格备案
	 */
	public void dellsscba(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).dellsscba(param).getData());
	}
	
	/**
	 * 删除通讯录人员
	 */
	@Before(Tx.class)
	public void delry(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delry(param).getData());
	}
	
	/**
	 * 删除应急通讯录人员
	 */
	@Before(Tx.class)
	public void delrenyuanyj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delrenyuanyj(param).getData());
	}
	
	/**
	 * 删除部门
	 */
	@Before(Tx.class)
	public void delbumen(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).delbumen(param).getData());
	}
	
	/**
	 * 保存部门
	 */
	@Before(Tx.class)
	public void addbumen(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addbumen(param).getData());
	}
	
	/**
	 * 保存人员
	 */
	@Before(Tx.class)
	public void addrenyuan(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addrenyuan(param).getData());
	}
	/**
	 * 保存应急人员
	 */
	@Before(Tx.class)
	public void addrenyuanyj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(EmergencyBO.class).addrenyuanyj(param).getData());
	}
	
	
	public void findzhlw() throws IOException{
		String url = "http://www.cngrain.com/info/67108864";
		Document doc = Jsoup.connect(url).get();
		renderText(doc.html());
	}
	
	/**
	 * 获取流程节点数
	 */
	public void getlcno(){
		String sql = "select count(liuchenghao) as num from oa_shenpiliucheng "
				+ "where liuchenghao = ?";
		Record record = Db.findFirst(sql, getPara("liuchenghao"));
		renderJson(record);
	}
	
	/**
	 * 更新每日新闻
	 * @throws IOException
	 */
	public void savenews(){
		
			//查找所有网站
			//新浪网
			String name = "新浪网";
			String url = "http://finance.sina.com.cn/futuremarket/";
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Element content = doc.select(".part02").first().select(".blk22").first().select(".newslist").get(1).select("ul")
						.first().select("li").first().select(".grey43").first();
				String title = content.text();
				String purl = content.attr("href");
				String uuid = SqlUtil.uuid();
				Record record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//中国粮食信息网
				name = "中国粮食信息网";
				url = "http://www.grain.gov.cn/Grain/Index.aspx";
				doc = Jsoup.connect(url).get();
				content = doc.select(".firstNews").first().select("a").first();
				title = content.text();
				purl = "http://www.grain.gov.cn/Grain/" + content.attr("href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//江苏粮网
				name = "江苏粮网";
				url = "http://www.jsgrain.gov.cn/default.php?mod=article&settype=0&fid=1684";
				doc = Jsoup.connect(url).get();
				content = doc.select("#s2945971_content").first().select("a").first();
				title = content.text();
				purl = content.attr("abs:href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//中国粮食行业网
				name = "中国粮食行业网";
				url = "http://www.chinagrains.org.cn/info/1904/type_4881.html";
				doc = Jsoup.connect(url).get();
				content = doc.select(".li_sali1").first().select("a").first();
				title = content.text();
				purl = content.attr("abs:href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//国家粮食局
				name = "国家粮食局";
				url = "http://www.chinagrain.gov.cn/";
				doc = Jsoup.connect(url).get();
				content = doc.select(".list_act_1").first().select("a").first();
				title = content.text();
				purl = content.attr("abs:href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//中国粮食网
				name = "中国粮食网";
				url = "http://www.cereal.com.cn/";
				doc = Jsoup.connect(url).get();
				content = doc.select(".headline-news").first().select("a").first();
				title = content.text();
				purl = content.attr("abs:href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
				//中国粮油信息网
				name = "中国粮油信息网";
				url = "http://www.chinagrain.cn/";
				doc = Jsoup.connect(url).get();
				content = doc.select("#eq_news_bg").first().select("a").first();
				title = content.text();
				purl = content.attr("abs:href");
				uuid = SqlUtil.uuid();
				record = new Record();
				record.set("id", uuid);
				record.set("webname", name);
				record.set("title", title);
				record.set("url", purl);
				if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
					String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
					Db.update(upsql,title,purl,name);
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

}
