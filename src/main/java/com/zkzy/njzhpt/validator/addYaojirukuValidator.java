package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.RenyuanBo;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class addYaojirukuValidator extends Validator {

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
		if(arg0.getPara("id")==null||arg0.getPara("id")==""){
			if ("/cangchugl/addYjrk".equals(this.getActionKey())) {
				validateRequiredString("xzqydm", "nameMsg", "请选择行政区域！");
				validateRequiredString("kdbm", "nameMsg", "请选择库点！");
				validateRequiredString("yjmc", "nameMsg", "请填写药剂名称！");
				validateRequiredString("pp", "nameMsg", "请填写药剂品牌！");
				validateRequiredString("yxqz", "nameMsg", "请填写生产日期！");
				validateRequiredString("rksj", "nameMsg", "请填写入库时间！");
				validateRequiredString("gg", "nameMsg", "请填写规格！");
				validateRequiredString("dw", "nameMsg", "请选择单位！");
				validateRequiredString("sl", "nameMsg", "请填写数量！");
				validateRequiredString("sccj", "nameMsg", "请填写生产厂家！");
				validateRequiredString("gly", "nameMsg", "请填写管理员！");
			}
		}
	}

}
