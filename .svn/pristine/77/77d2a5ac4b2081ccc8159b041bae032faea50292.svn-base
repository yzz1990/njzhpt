package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class editKudianValidator extends Validator {

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
		if ("/jibenxinxi/upKudian".equals(this.getActionKey())) {	
			validateRequiredString("sheng", "nameMsg", "请选择省信息！");
			validateRequiredString("shi", "nameMsg", "请选择市信息！");
			validateRequiredString("xian", "nameMsg", "请选择县信息！");
			validateRequiredString("qyzzjgdm", "nameMsg", "请输入组织机构代码！"); 
			validateRequiredString("kdmc", "nameMsg", "请输入库点名称！");
			validateRequiredString("kdbm", "nameMsg", "请输入库点编码！");
			validateRequiredString("kdlxbh", "nameMsg", "请选择库点类型！");
			try{		
				HashMap<String, Object> param=new HashMap<String, Object>();	
				HashMap<String, Object> param1=new HashMap<String, Object>();	
					param.put("ID", arg0.getPara("ID"));
					param.put("page",1);
					param.put("rows", 1);
					Record  kudian=Duang.duang(jibenxinxiBo.class).findKudian(param).getList().get(0);
					if(kudian!=null){
						if(!kudian.getStr("kdbm").equals(arg0.getPara("kdbm"))){
							param1.put("qyzzjgdm",arg0.getPara("qyzzjgdm"));
							param1.put("kdbm",arg0.getPara("kdbm"));
							param1.put("page",1);
							param1.put("rows",1);
							Record  kudian1=Duang.duang(jibenxinxiBo.class).findKudian(param1).getList().get(0);
							if(kudian1!=null){
								addError("nameMsg", "该库点已经存在!");
							}
						}
					}
			}catch(Exception e){
				handleError(arg0);
			}
			validateRequiredString("tbsj", "nameMsg", "请输入填报时间！");
			validateRequiredString("cfzcr", "nameMsg", "请输入仓房总仓容！");
		}
	}

}
