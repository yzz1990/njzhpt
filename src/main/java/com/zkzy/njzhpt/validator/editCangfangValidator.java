package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class editCangfangValidator extends Validator {

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
		if ("/jibenxinxi/upCangfang".equals(this.getActionKey())) {
			validateRequiredString("cfbm", "nameMsg", "请输入仓廒编码！");
			try{
				HashMap<String, Object> param=new HashMap<String, Object>();
				HashMap<String, Object> param1=new HashMap<String, Object>();
					param.put("ID", arg0.getPara("ID"));
					param.put("page",1);
					param.put("rows", 1);
					Record  cangfang=Duang.duang(jibenxinxiBo.class).findCangfang(param).getList().get(0);
					if(cangfang!=null){
						if(!cangfang.getStr("cfbm").equals(arg0.getPara("cfbm"))){
							param1.put("qyzzjgdm",arg0.getPara("qyzzjgdm"));
							param1.put("kdbm",arg0.getPara("kdbm"));
							param1.put("cfbm",arg0.getPara("cfbm"));
							param1.put("page",1);
							param1.put("rows",1);
							Record  cangfang1=Duang.duang(jibenxinxiBo.class).findCangfang(param1).getList().get(0);
							if(cangfang1!=null){
								addError("nameMsg", "该仓廒已经存在!");
							}
						}
					}
			}catch(Exception e){
				handleError(arg0);
			}
			validateRequiredString("cfmc", "nameMsg", "请输入仓廒名称！");
			validateRequiredString("cflxmc", "nameMsg", "请输入仓房类型！");
			validateRequiredString("sjcr", "nameMsg", "请输入设计仓容！");
			validateRequiredString("shjcr", "nameMsg", "请输入实际仓容！");
		}
	}

}
