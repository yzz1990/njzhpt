package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.LunHuanGuanLiBO;

public class LHSQWHValidator extends Validator {

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		String method = arg0.getAttr("_url_");
		this.setShortCircuit(true);
		if ("saveLunHuanB".equals(method)) {
			try{	
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("sqwh", arg0.getPara("sqwh"));
					/*boolean bool=Duang.duang(LunHuanGuanLiBO.class).validatesqwh(param1);
					if(bool){
						addError("msg", "轮换申请失败，申请文号已存在！");
					}*/
				//}
		}catch(Exception e){
			handleError(arg0);
		}
		}
	}

	@Override
	protected void handleError(Controller c) {
		// TODO Auto-generated method stub
		c.keepPara("sqwh");
		c.renderJson("{\"ret\":false,\"message\":\""
				+ c.getAttr("nameMsg") + "\"}");
	}

}
