package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class editYjZtdjValidator extends Validator {

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
		if ("/emergency/upemergencydj".equals(this.getActionKey())) {
			validateRequiredString("slevel", "nameMsg", "请输入状态等级名称！");
			validateRequiredString("sgrade", "nameMsg", "请选择状态级别！");
			validateRequiredString("sdescription", "nameMsg", "请添加状态描述！");	
		}
	}

}
