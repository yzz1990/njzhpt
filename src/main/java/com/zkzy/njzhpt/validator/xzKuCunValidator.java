package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ZhiLiangGLBO;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class xzKuCunValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		String json = "{\"ret\":false,\"msg\":\"" + arg0.getAttr("nameMsg")
				+ "\"}";
		arg0.renderJson(json);
	}

	@Override
	protected void validate(Controller arg0) {
		this.setShortCircuit(true);
		if ("/zhiliangGL/xzkucun".equals(this.getActionKey())) {
			validateRequiredString("cfstr", "nameMsg", "请添加仓廒名称！");	
			validateRequiredString("pzstr", "nameMsg", "请选择品种名称！");
			validateRequiredString("xzstr", "nameMsg", "请选择粮食性质！");
			validateRequiredString("ndstr", "nameMsg", "请选择收获年度！");
			validateRequiredString("djstr", "nameMsg", "请选择粮食等级！");
			validateRequiredString("slstr", "nameMsg", "请输入库存数量！");
			try{
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("qyzzjgdm", arg0.getPara("qyzzjgdm"));
					param1.put("kdbm", arg0.getPara("kdbm"));
					param1.put("cfstr", arg0.getPara("cfstr"));
					param1.put("page",1);
					param1.put("rows", 1);
					Ret  ret=Duang.duang(ZhiLiangGLBO.class).vdexist(param1);
					if((boolean) ret.get("ret")){
						addError("nameMsg", ret.get("cfmc")+"库存已存在，请删除后在提交!");
					}

			}catch(Exception e){
				handleError(arg0);
			}
		}
	}

}
