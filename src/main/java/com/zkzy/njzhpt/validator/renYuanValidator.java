package com.zkzy.njzhpt.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class renYuanValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		String json = "{\"ret\":false,\"message\":\"" + arg0.getAttr("nameMsg")
				+ "\"}";
		arg0.renderJson(json);
	}

	@Override
	protected void validate(Controller arg0) {
		this.setShortCircuit(true);
		if ("/renyuan/addRenyuan".equals(this.getActionKey())) {
			validateRequiredString("name", "nameMsg", "请输入姓名！");			
			validateRequiredString("phone", "nameMsg", "请输入电话！");			
			validateRequiredString("deptname", "nameMsg", "请选择部门！");
		}
	}

}
