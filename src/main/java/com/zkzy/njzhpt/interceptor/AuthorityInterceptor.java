package com.zkzy.njzhpt.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ggy.common.utils.UserInfo;
import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.bo.FrameworkBO;
import com.zkzy.framework.kit.UserKit;

public class AuthorityInterceptor implements Interceptor {

	private final static String[] agent = { "Android"};
	
	
	public void intercept(Invocation inv) {
		String userAgent =inv.getController().getRequest().getHeader("user-agent");
		Controller controller = inv.getController();
		inv.getController().setSessionAttr("fontsize", inv.getController().getSessionAttr("size")==null?14:inv.getController().getSessionAttr("size"));
		LogKit.info(inv.getController().getSessionAttr("size")+"  intercept++++++++++++++++++++++++++");
		UserInfo userinfo = controller.getSessionAttr("userinfo");
		if (userinfo != null) {
			UserKit.buildUser(userinfo);
			controller.setAttr("userinfo", userinfo);		
			inv.invoke();
		}else{
			
			if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
				// 排除 苹果桌面系统
				if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
					for (String item : agent) {
						if (userAgent.contains(item)) {//如果是手机端访问  放开所有权限
							// TODO 手机端要访问 暂时放开所有页面的权限
							HashMap<String, Object> queryparam = new HashMap<String, Object>();
							queryparam.put("loginname", "admin");
							queryparam.put("password", "admin123456");
							try {
								Ret r = Duang.duang(FrameworkBO.class).queryFwUser(queryparam);
								Page<Record> userPageRecord = r.get("list");
								userinfo = setUser(userPageRecord.getList().get(0));
								UserKit.buildUser(userinfo);
								controller.setAttr("userinfo", userinfo);
							} catch (Exception e) {
								e.printStackTrace();
							}
							inv.invoke();
						}else{
							controller.redirect("/login/login.html");
						}
					}
				}
			}else{
				controller.redirect("/login/login.html");
			}
		}
	}

	public UserInfo setUser(Record record) throws Exception {
		UserInfo user = new UserInfo();
		user.setUser(JSON.parseObject(JsonKit.toJson(record)));
		List<Record> depList = Db
				.use("njpt")
				.find("select * from fw_dep where id in (select depid from fw_user_dep where userid = ?) order by orderno",
						user.getUser().getString("id"));
		List<Record> roleList = Db
				.use("njpt")
				.find("select * from fw_role where id in (select roleid from fw_user_role where userid = ?) order by orderno",
						user.getUser().getString("id"));
		List<Record> amsList = Db
				.use("njpt")
				.find("select * from fw_ams where id in (select amsid from fw_role_ams where roleid in (select roleid from fw_user_role where userid = ?)) order by orderno",
						user.getUser().getString("id"));
		user.setDeps(JSON.parseArray(JsonKit.toJson(depList)));
		user.setRoles(JSON.parseArray(JsonKit.toJson(roleList)));
		user.setAms(JSON.parseArray(JsonKit.toJson(queryAmsByRoot(amsList,
				"36c3b611a19f496c927e76a4570e1061"))));

		user.setDep(JSON.parseArray(JsonKit.toJson(depList)).getJSONObject(0));
		user.setRole(JSON.parseArray(JsonKit.toJson(roleList)).getJSONObject(0));
		List<String> authList = new ArrayList<String>();
		List<Record> authRecordlist = Db.use("njpt").find(
				"select * from fw_role_authority where roleid = ?",
				roleList.get(0).getStr("id"));
		for (Record r : authRecordlist) {
			authList.add(r.getStr("authoritycode"));
		}
		user.setAuth(authList);

		Record area = Db.use("njpt").findFirst(
				"select * from fw_area where id = ?",
				depList.get(0).getStr("areaid"));
		user.setArea(JSON.parseObject(JsonKit.toJson(area)));
		return user;
	}

	private List<Record> queryAmsByRoot(List<Record> recordList, String root)
			throws Exception {
		List<Record> r = new ArrayList<Record>();
		for (int i = 0; i < recordList.size(); i++) {
			Record ams = recordList.get(i);
			if (root.equals(ams.getStr("parentid"))) {
				int childcount = ams.get("childcount");
				if (childcount > 0) {
					List<Record> childret = queryAmsByRoot(recordList,
							ams.get("id"));
					ams.set("children", childret);
				}
				r.add(ams);
			}
		}
		return r;
	}
}