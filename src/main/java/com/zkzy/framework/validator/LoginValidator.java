package com.zkzy.framework.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		arg0.renderJson("{\"ret\":false,\"message\":\""
				+ arg0.getAttr("nameMsg") + "\"}");
	}

	@Override
	protected void validate(Controller arg0) {
		// 错误后就返回
		this.setShortCircuit(true);
		if ("/doLogin".equals(this.getActionKey())) {
			validateRequiredString("loginname", "nameMsg", "请输入用户名");
			validateRequiredString("password", "nameMsg", "请输入密码");
		}
	}

}
