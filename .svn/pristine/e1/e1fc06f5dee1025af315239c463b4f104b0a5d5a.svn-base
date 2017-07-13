package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class editRenkouValidator extends Validator {

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
		if ("/emergency/uprenkou".equals(this.getActionKey())) {
			validateRequiredString("xian", "nameMsg", "请选择区县！");
			validateRequiredString("zhen", "nameMsg", "请输入区域！");
			validateRequiredString("tongjiriqi", "nameMsg", "请选择统计日期！");
			validateRequiredString("changzhu", "nameMsg", "请输入常住人口！");
			validateRequiredString("huji", "nameMsg", "请输入户籍人口！");
			validateRequiredString("biaozhun", "nameMsg", "请输入口粮标准！");
			validateRequiredString("creator", "nameMsg", "请输入创建人！");
		}
	}

}
