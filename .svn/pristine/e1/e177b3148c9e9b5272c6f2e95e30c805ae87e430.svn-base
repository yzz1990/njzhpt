package com.zkzy.njzhpt.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.OaDao;


public class OaBo {
	//region 内部邮件
	//加载收件箱
	public Page<Record> loadSJX(HashMap<String, Object> param) throws Exception {
		param.put("status",0);
		param.put("receiver_name",param.get("receiver_name").toString());
		Page<Record> list=OaDao.queryEmail(param);
		return list;
	}
	//加载发件箱
	public Page<Record> loadFJX(HashMap<String, Object> param) throws Exception {
		param.put("status",1);
		param.put("sender_name",param.get("sender_name").toString());
		Page<Record> list=OaDao.queryEmail(param);
		return list;
	}
	//加载草稿箱
	public Page<Record> loadCGX(HashMap<String, Object> param) throws Exception {
		param.put("status",2);
		Page<Record> list=OaDao.queryEmail(param);
		return list;
	}
	//加载垃圾箱
	public Page<Record> loadLJX(HashMap<String, Object> param) throws Exception {
		param.put("status",3);
		param.put("receiver_name",param.get("receiver_name").toString());
		Page<Record> list=OaDao.queryEmail(param);
		return list;
	}
	//新增邮件
	public Ret addEmail(HashMap<String, Object> param) {
		String id = SqlUtil.uuid();
		Object obj = param.get("receiver_name");
		
		String[] rnames = null;
		if (obj != null) {
			if (obj.toString().startsWith("[Ljava.lang.String")) {
				rnames = (String[]) param.get("receiver_name");
			} else {
				String str = (String) param.get("receiver_name");
				rnames = new String[1];
				rnames[0] = str;
			}
		}
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		String pname="";
		for(int i=0;i<rnames.length;i++){
			pname+=rnames[i];
		}
		for(int i=0;i<rnames.length;i++){
			Record nr=new Record().setColumns(param).set("id",id).set("receiver_name",rnames[i]).set("status","0")
					.set("isRead",0).set("sendTime",time).set("receiveTime",time);
			Db.save("njlk_email_info",nr);
			
			
			
		}
		Record mr=new Record().setColumns(param).set("id",id).set("receiver_name",pname).set("status","1").set("isRead",0).set("sendTime",time).set("receiveTime",time);;
		Db.save("njlk_email_info",mr);
		Ret msg = Ret.create("ret", true);
		msg.put(Ret.create("msg", "发送成功"));
		return msg;
			
			
	}
	//邮件移入垃圾箱
	public Ret updateToLJ(String id){
		boolean result=OaDao.updateToLJ(id);
		if(result){
			Ret msg = Ret.create("ret", true);
			msg.put(Ret.create("msg", "删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", false);
			msg.put(Ret.create("msg", "删除失败"));
			return msg;
		}	
	}
	//彻底删除
	public Ret delEmail(String id){
		boolean result=OaDao.delEmail(id);
		if(result){
			Ret msg = Ret.create("ret", true);
			msg.put(Ret.create("msg", "删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", false);
			msg.put(Ret.create("msg", "删除失败"));
			return msg;
		}
	}
	//点击后变更已阅
	public Ret updateIsRead(String id){
		boolean result=OaDao.updateIsRead(id);
		if(result){
			Ret msg = Ret.create("ret", true);
			msg.put(Ret.create("msg", "成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", false);
			msg.put(Ret.create("msg", "失败"));
			return msg;
		}
	}
	//#region 
	public static boolean addLogin(String username,String rolename){
		String id = SqlUtil.uuid();
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Record nr=new Record().set("id",id).set("username",username).set("rolename",rolename).set("time",time).set("options","登陆");
		boolean result=Db.save("njlk_login_jl", nr);
		return result;
		
	}
	public Page<Record> loadLogin(HashMap<String, Object> param) throws Exception {
		Page<Record> list=OaDao.queryLogin(param);
		return list;
	}
}
