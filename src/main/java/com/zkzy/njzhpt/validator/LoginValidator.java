package com.zkzy.njzhpt.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		String callback = arg0.getPara("callback");
		String json = "{\"ret\":false,\"message\":\"" + arg0.getAttr("nameMsg")
				+ "\"}";
		arg0.renderText(callback + "(" + json + ")");
	}

	@Override
	protected void validate(Controller arg0) {
		this.setShortCircuit(true);
		if ("/doLogin".equals(this.getActionKey())) {
			validateRequiredString("loginname", "nameMsg", "请输入用户名");
			validateRequiredString("password", "nameMsg", "请输入密码");
		}
	}

}
