package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class addYjZtValidator extends Validator {

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
		if ("/emergency/addemergency".equals(this.getActionKey())) {
			validateRequiredString("sname", "nameMsg", "请添加状态名称！");
			validateRequiredString("slevel", "nameMsg", "请选择状态等级！");
			validateRequiredString("sdescription", "nameMsg", "请添加状态描述！");	
		}
	}

}
