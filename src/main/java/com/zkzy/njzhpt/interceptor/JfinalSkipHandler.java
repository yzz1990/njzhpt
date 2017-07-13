package com.zkzy.njzhpt.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.handler.Handler;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.bo.PeizhiBO;
import com.zkzy.njzhpt.bo.RenyuanBo;

public class JfinalSkipHandler extends Handler {
	public static Ret tianqi = new Ret();

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {

		request.setAttribute("servletPath", request.getServletPath());
		request.setAttribute("requestURI", request.getRequestURI());
		String Referer = request.getHeader("Referer");
		request.setAttribute("Referer", StrKit.isBlank(Referer) ? "" : Referer);
		request.setAttribute("gisCityHtml", "/WEB-INF/web/ui/globalStyle.html");
		//request.getParameter("isedit");
		
		//t_property表  配置基础信息    yzz 
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.putAll(new PeizhiBO().getTProperty().getColumns());
		BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
		 
		
		if (target.contains(".html")) {
			if(!"".equals(org.apache.commons.lang.StringUtils.defaultString(request.getParameter("isedit")))){
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='"+request.getParameter("isedit")+"' and jiequname=? collate Chinese_PRC_CS_AI ",jiequname);
				if(UserKit.currentUserInfo()!=null&&record!=null){
					Date date=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
					param1.put("caozuotime",sf.format(date));
					param1.put("caidanname", record.getStr("name"));
					param1.put("jiequname", jiequname);
					new RenyuanBo().savejiequxinxi(param1);
				}
			}else{
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				String mokuai=name[name.length-2];
				if(jiequname.equals("gis.html")){
					Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='0' and jiequname=? and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,mokuai);
					if(UserKit.currentUserInfo()!=null&&record!=null){
						Date date=new Date();
						SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
						param1.put("caozuotime",sf.format(date));
						param1.put("caidanname", record.getStr("name"));
						param1.put("jiequname", jiequname);
						new RenyuanBo().savejiequxinxi(param1);
					}
				}else if(jiequname.equals("yaokuguanli.html")||jiequname.equals("anquan_bzh_qslsj.html")){
					String moku=name[name.length-3];
					Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='0' and jiequname=? and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,moku);
					if(UserKit.currentUserInfo()!=null&&record!=null){
						Date date=new Date();
						SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
						param1.put("caozuotime",sf.format(date));
						param1.put("caidanname", record.getStr("name"));
						param1.put("jiequname", jiequname);
						new RenyuanBo().savejiequxinxi(param1);
					}
				}else{
					Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='0' and jiequname=? collate Chinese_PRC_CS_AI ",jiequname);
					if(UserKit.currentUserInfo()!=null&&record!=null){
						Date date=new Date();
						SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
						param1.put("caozuotime",sf.format(date));
						param1.put("caidanname", record.getStr("name"));
						param1.put("jiequname", jiequname);
						new RenyuanBo().savejiequxinxi(param1);
					}
				}
				
			}
			
			request.setAttribute("tianqi", tianqi);
			if (target.contains("/framework/")) {
				request.setAttribute("_url_", target.replace("/framework/", ""));
				target = "/framework/toHTML";
			} else if (target.contains("/login/")) {
				request.setAttribute("_url_", target.replace("/login/", ""));
				target = "/login/toHTML";
			} else if (target.contains("/yewu")) {
				request.setAttribute("_url_", target.replace("/yewu/", ""));
				target = "/yewu/toHTML";
			} else if (target.contains("/renyuan")) {
				request.setAttribute("_url_", target.replace("/renyuan/", ""));
				target = "/renyuan/toHTML";
			}else if (target.contains("/shouchukeshihua")) {  
				request.setAttribute("_url_", target.replace("/shouchukeshihua/", ""));
				target = "/shouchukeshihua/toHTML";
			}else if (target.contains("/quxian")) {  
				request.setAttribute("_url_", target.replace("/quxian/", ""));
				target = "/quxian/toHTML";
			}else if (target.contains("/zonghe")) {  
				request.setAttribute("_url_", target.replace("/zonghe/", ""));
				target = "/zonghe/toHTML";
			}else if (target.contains("/emergency")) {
				request.setAttribute("_url_", target.replace("/emergency/", ""));
				target = "/emergency/toHTML";
			}else if (target.contains("/oa")) {
				request.setAttribute("_url_", target.replace("/oa/", ""));
				target = "/oa/toHTML";
			}else if (target.contains("/securityedu")) {
				request.setAttribute("_url_", target.replace("/securityedu/", ""));
				target = "/securityedu/toHTML";
			}else {
				request.setAttribute("_url_", target.substring(1));
				target = "/";
			}
		} else if (target.contains(".do")) {
			if(!"".equals(org.apache.commons.lang.StringUtils.defaultString(request.getParameter("isedit")))){
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				String mokuai=name[name.length-2];
				Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='"+request.getParameter("isedit")+"' and jiequname=? and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,mokuai);
				if(UserKit.currentUserInfo()!=null&&record!=null){
					Date date=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
					param1.put("caozuotime",sf.format(date));
					param1.put("caidanname", record.getStr("name"));
					param1.put("jiequname", jiequname);
					new RenyuanBo().savejiequxinxi(param1);
				}
			}else{
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				String mokuai=name[name.length-2];
				Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='0' and jiequname=? and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,mokuai);
				if(UserKit.currentUserInfo()!=null&&record!=null){
					Date date=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
					param1.put("caozuotime",sf.format(date));
					param1.put("caidanname", record.getStr("name"));
					param1.put("jiequname", jiequname);
					new RenyuanBo().savejiequxinxi(param1);
				}
			}
			
			String[] urls = target.split("/");
			request.setAttribute("_url_",
					urls[urls.length - 1].replace(".do", ""));
			if (target.contains("/framework/")) {
				target = "/framework/index";
			} else if (target.contains("/lunhuan/")) {
				target = "/lunhuan/index";
			} else if (target.contains("/qitiJC/")) {
				target = "/qitiJC/index";
			} else if (target.contains("/chubeiliangJH/")) {
				target = "/chubeiliangJH/index";
			} else if (target.contains("/chengpinliang/")) {

				target = "/chengpinliang/index";
			} else if (target.contains("/shouchukeshihua")) {
				target = "/shouchukeshihua/index";
			} else if (target.contains("/zhiliangGL")) {
				target = "/zhiliangGL/index";
			} else if (target.contains("/zhiliangGL")) {
				target = "/zhiliangGL/index";
			} else if (target.contains("/yewu")) {
				target = "/yewu/index";
			} else if (target.contains("/peizhi")) {
				target = "/peizhi/index";
			} else if(target.contains("/jiage")){
				target = "/jiage/index";
			} else if(target.contains("/renyuan")){
				target = "/renyuan/index";
			} else if(target.contains("/zhiliangzs")){
				target = "/zhiliangzs/index";
			} else if(target.contains("/emergency")){
				target = "/emergency/index";
			} else if(target.contains("/gonggong")){
				target = "/gonggong/index";
			}else if(target.contains("/securityedu")){
				target = "/securityedu/index";
			}
		} else if (target.contains(".svg")) {
			if (target.contains("/yewu/")) {
				request.setAttribute("_url_", target.replace("/yewu/", ""));
				target = "/yewu/toHTML";
			}
		} else if (target.contains(".js")) {
			if (target.contains("/yewu/")) {
				request.setAttribute("_url_", target.replace("/yewu/", ""));
				target = "/yewu/toHTML";
			}else if(target.contains("/quxian/")){
				request.setAttribute("_url_", target.replace("/quxian/", ""));
				target = "/quxian/toHTML";
			}
		}else if (target.contains(".apk")) {
			request.setAttribute("_url_", target.replace("/login/", ""));
			target = "/login/toHTML";
			
		}else{
				
			if(!"".equals(org.apache.commons.lang.StringUtils.defaultString(request.getParameter("isedit")))){
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				String mokuai=name[name.length-2];
				Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='"+request.getParameter("isedit")+"' and jiequname=? and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,mokuai);
				if(UserKit.currentUserInfo()!=null&&record!=null){
					Date date=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
					param1.put("caozuotime",sf.format(date));
					param1.put("caidanname", record.getStr("name"));
					param1.put("jiequname", jiequname);
					new RenyuanBo().savejiequxinxi(param1);
				}
			}else{
				String name[]=target.split("/");
				String jiequname=name[name.length-1];
				String mokuai=name[name.length-2];
				Record record=Db.findFirst("SELECT name FROM fw_caidan_fangfa where isedit='0' and jiequname=? collate Chinese_PRC_CS_AI and mokuai=? collate Chinese_PRC_CS_AI ",jiequname,mokuai);
				if(UserKit.currentUserInfo()!=null&&record!=null){
					Date date=new Date();
					SimpleDateFormat sf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("userid", UserKit.currentUserInfo().getUser().get("id"));
					param1.put("caozuotime",sf.format(date));
					param1.put("caidanname", record.getStr("name"));
					param1.put("jiequname", jiequname);
					new RenyuanBo().savejiequxinxi(param1);
				}
			}
			
		}
		next.handle(target, request, response, isHandled);
	}
}
