package com.zkzy.njzhpt.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LunHuanSQValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		// TODO Auto-generated method stub
		String method = c.getAttr("_url_");
		this.setShortCircuit(true);
		if ("saveLunHuanB".equals(method)) {
			validateRequiredString("lhsq_sqwh", "nameMsg", "请输入申请文号");
		}
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepPara("lhsq_sqwh");
		c.renderJson("{\"ret\":false,\"message\":\""
				+ c.getAttr("nameMsg") + "\"}");
	}

}
