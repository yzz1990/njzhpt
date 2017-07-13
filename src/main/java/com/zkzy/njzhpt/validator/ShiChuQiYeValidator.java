package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class ShiChuQiYeValidator extends Validator{

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		this.setShortCircuit(true);
		if ("/chubeiliangJH/saveshichuqiye".equals(this.getActionKey())) {
			try{	
					if(arg0.getPara("id").isEmpty()){
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("niandu", arg0.getPara("niandu"));
						param1.put("xzqhdm", arg0.getPara("xzqhdm"));
						param1.put("diqu", arg0.getPara("diqu"));
						param1.put("qyzzjgdm", arg0.getPara("qyzzjgdm"));
						param1.put("qymc", arg0.getPara("qymc"));
						param1.put("orderno", arg0.getPara("orderno"));
						boolean bool=Duang.duang(ChuBeiLiangJHBO.class).findShiChuQiye(param1);
						if(bool){
							addError("msg", "配置失败，同一年度已存在！");
						}
					}
			}catch(Exception e){
				handleError(arg0);
			}
		}
		
	}

	@Override
	protected void handleError(Controller arg0) {
		// TODO Auto-generated method stub
		String json = "{\"ret\":false,\"msg\":\"" + arg0.getAttr("msg")
				+ "\"}";
		arg0.renderJson(json);
	}

}
