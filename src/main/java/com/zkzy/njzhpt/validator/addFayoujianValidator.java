package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class addFayoujianValidator extends Validator {

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
		if ("/shoujian/sendemail".equals(this.getActionKey())) {
			validateRequiredString("mailInfo.sjrid", "nameMsg", "请添加收件人！");
			validateRequiredString("mailInfo.title", "nameMsg", "请添加主题！");	
			/*validateRequiredString("cfmc", "nameMsg", "请输入仓廒名称！");
			validateRequiredString("cflxmc", "nameMsg", "请输入仓房类型！");
			validateRequiredString("sjcr", "nameMsg", "请输入设计仓容！");
			validateRequiredString("shjcr", "nameMsg", "请输入实际仓容！");*/
		}
	}

}
