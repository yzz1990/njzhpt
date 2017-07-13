package com.zkzy.framework.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.zkzy.framework.validator.LoginValidator;

@Before(LoginValidator.class)
public class LoginController extends Controller {

	public void index() {
		String url = getAttr("_url_");
		if (StrKit.notBlank(url)) {
			String servletPath = getRequest().getServletPath();
			if (servletPath.startsWith("/framework/")) {
				setAttr("title", "后台框架").setAttr("amsroot", "root");
			}
			if (servletPath.contains("ajax.html")) {
				render(url + "#" + getPara("ajax"));
			} else {
				render(url);
			}
		} else {
			redirect("/framework/yonghuguanli.html");
		}
	}

	@Clear
	public void login() {
		render("login.html");
	}

	@Clear
	public void doLogin() throws Exception {
		// int retInt = -1;
		// CodeSearchRet ret = null;
		// ret = LoginBO.getUser(this);
		// // 放到Session里面去
		// if (ret.getRows().getTotalRow() > 0) {
		// Record r = (Record) ret.getRows().getList().get(0);
		//
		// UserInfo userInfo = new UserInfo();
		//
		// JSONObject user = (JSONObject) JSON.toJSON(r.getColumns());
		// String uid = user.getString("id");
		//
		// userInfo.setUser(user);
		//
		// // 获取用户角色
		// ret = LoginBO.getRoleByUid(uid);
		// JSONArray roles = (JSONArray) JSON.toJSON(ret.getRows().getList());
		// userInfo.setRoles(roles);
		// if (roles.size() > 0) {
		// userInfo.setRole(roles.getJSONObject(0));
		// }
		//
		// // 获取用户部门
		// ret = LoginBO.getDepByUid(uid);
		// JSONArray deps = (JSONArray) JSON.toJSON(ret.getRows().getList());
		// userInfo.setDeps(deps);
		// if (deps.size() > 0) {
		// userInfo.setDep(deps.getJSONObject(0));
		// }
		//
		// // 获取用户菜单
		// if ("4".equals(user.getString("usertype"))) {
		// ret = LoginBO.getAms();
		// userInfo.setAms((JSONArray) JSON
		// .toJSON(ret.getRows().getList()));
		// } else {
		// // queryparam.put("level", "1");
		// // ret = PageBO.getInstance().saveCodeSearch(
		// // "fw_role_ams_user_select2", queryparam, 1, 100);
		// // userInfo.setL1ams(JSONArray.fromObject(ret.getRows()));
		// // queryparam.put("level", "2");
		// // ret = PageBO.getInstance().saveCodeSearch(
		// // "fw_role_ams_user_select2", queryparam, 1, 100);
		// // userInfo.setL2ams(JSONArray.fromObject(ret.getRows()));
		// // queryparam.put("level", "3");
		// // ret = PageBO.getInstance().saveCodeSearch(
		// // "fw_role_ams_user_select2", queryparam, 1, 100);
		// // userInfo.setL3ams(JSONArray.fromObject(ret.getRows()));
		// }
		// setSessionAttr("userinfo", userInfo);
		// retInt = 1;
		// } else {
		// retInt = -3;// 密码不正确
		// }
		// renderJson("{\"ret\":true,\"message\":" + retInt + "}");
	}

	public void logout() {
		setSessionAttr("userinfo", null);
		redirect("/login");
	}

}
