package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class OaDao {
	// 通用/分页
		public static Page<Record> queryEmail(HashMap<String, Object> queryparam)
				throws Exception {
			Param p = new Param();
			p.put("and id = ?", "id");
			p.put("and status = ?","status");
			p.put("and isRead","isRead");
			p.put("and receiver_name = ?", "receiver_name");
			p.put("and sender_name = ?","sender_name");
			//p.put("and receiver_id = ?","receiver_id");
			//p.put("and sender_id = ?","sender_id");
			//p.put("and sender_name like ?","sender_name","%%%s%%");
			SqlAndParam s = SqlUtil.buildSql(p, queryparam);
			Page<Record> page = Db.paginate(Integer.valueOf(String
					.valueOf(queryparam.get("page"))), Integer.valueOf(String
					.valueOf(queryparam.get("rows"))), "select *",
					"from njlk_email_info where 1=1" + s.getSql()
							+ " order by sendTime desc", s.getParam()
							.toArray(new Object[0]));
			return page;
		}
		
		public static Record findPerson(String id){
			Record nr=Db.findById("njlk_email_info","id",id);
			return nr;
		}
		
		public static boolean updateToLJ(String id){
			Record nr=new Record().set("id",id).set("status",3);
			boolean result=Db.update("njlk_email_info","id",nr);
			return result;
		}
		public static boolean updateIsRead(String id){
			Record nr=new Record().set("id",id).set("isRead",1);
			boolean result=Db.update("njlk_email_info","id",nr);
			return result;
		}
		public static boolean delEmail(String id){
			boolean result=Db.deleteById("njlk_email_info","id",id);
			return result;
		}
		
		public static Page<Record> queryLogin(HashMap<String, Object> queryparam)
				throws Exception {
			Param p = new Param();
			p.put("and id = ?", "id");
			SqlAndParam s = SqlUtil.buildSql(p, queryparam);
			Page<Record> page = Db.paginate(Integer.valueOf(String
					.valueOf(queryparam.get("page"))), Integer.valueOf(String
					.valueOf(queryparam.get("rows"))), "select *",
					"from njlk_login_jl where 1=1" + s.getSql()
							+ " order by time desc", s.getParam()
							.toArray(new Object[0]));
			return page;
		}
}
