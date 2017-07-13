package com.zkzy.framework.validator;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.validate.Validator;

public class FrameworkValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.renderJson("{\"ret\":false,\"message\":\""
				+ arg0.getAttr("nameMsg") + "\"}");
	}

	@Override
	protected void validate(Controller arg0) {
		// 错误后就返回
		this.setShortCircuit(true);
		String actionName = arg0.getAttr("_url_");
		if ("saveUser".equals(actionName) || "updateUser".equals(actionName)) {
			validateRequiredString("loginname", "nameMsg", "请输入用户名");
			validateRequiredString("password", "nameMsg", "请输入密码");
			validateRequiredString("realname", "nameMsg", "请输入姓名");
			validateRequiredString("depid", "nameMsg", "请选择至少一个部门");
			validateRequiredString("roleid", "nameMsg", "请选择至少一个角色");
			validateInteger("canuse", "nameMsg", "请选择用户类型");
			validateInteger("orderno", "nameMsg", "请输入排序号[整数]");
			if ("saveUser".equals(actionName)) {
				String loginname = arg0.getPara("loginname");
				if (Db.queryLong(
						"select count(*) from fw_user where loginname = ?",
						loginname) > 0) {
					addError("nameMsg", "用户名已被占用!");
				}
			}
		} else if ("saveDep".equals(actionName)
				|| "updateDep".equals(actionName)) {
			if (arg0.getPara("id").equals(arg0.getPara("parentid"))) {
				addError("nameMsg", "不能设置父节点为其本身");
			}
			validateRequiredString("name", "nameMsg", "请输入部门名称");
			validateInteger("orderno", "nameMsg", "请输入排序号[整数]");
		} else if ("deleteDep".equals(actionName)) {
			String depid = arg0.getPara("id");
			if (Db.findById("fw_dep", depid).getInt("childcount") > 0) {
				addError("nameMsg", "请先删除或转移子部门!");
			}
		} else if ("deleteAms".equals(actionName)) {
			String amsid = arg0.getPara("id");
			if (Db.findById("fw_ams", amsid).getInt("childcount") > 0) {
				addError("nameMsg", "请先删除或转移子菜单!");
			}
		} else if ("saveAms".equals(actionName)
				|| "updateAms".equals(actionName)) {
			if (arg0.getPara("id").equals(arg0.getPara("parentid"))) {
				addError("nameMsg", "不能设置父节点为其本身");
			}
			validateRequiredString("name", "nameMsg", "请输入菜单名称");
			validateInteger("orderno", "nameMsg", "请输入排序号[整数]");
		} else if ("/query".equals(actionName)) {
			if ("fw_role_insert".equals(arg0.getPara("execute"))
					|| "fw_role_update".equals(arg0.getPara("execute"))) {
				validateRequiredString("name", "nameMsg", "请输入角色名称");
				validateInteger("orderno", "nameMsg", "请输入排序号[整数]");
			} else if ("fw_config_insert".equals(arg0.getPara("execute"))
					|| "fw_config_update".equals(arg0.getPara("execute"))) {
				validateRequiredString("conf_name", "nameMsg", "请输配置名称");
				validateRequiredString("conf_value", "nameMsg", "请输配置值");
			}
		}
	}
}
