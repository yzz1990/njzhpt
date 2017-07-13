package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class CBLJHRenWuValidator extends Validator{

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		this.setShortCircuit(true);
		if ("/chubeiliangJH/cbljhrwbianji".equals(this.getActionKey())) {
			try{	
					//if(arg0.getPara("id").isEmpty()){
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("niandu", arg0.getPara("niandu"));
						param1.put("xzqhdm", arg0.getPara("xzqhdm"));
						param1.put("qyzzjgdm", arg0.getPara("qyzzjgdm"));
						param1.put("xingzhi", arg0.getPara("xingzhi"));
						boolean bool=Duang.duang(ChuBeiLiangJHBO.class).findchubeiliangjhRW(param1);
						if(bool){
							addError("msg", "保存失败，该储备粮计划已存在！");
						}
					//}
			}catch(Exception e){
				handleError(arg0);
			}
		}
		if ("/chubeiliangJH/cbljhrwbj".equals(this.getActionKey())) {
			try{	
					//if(arg0.getPara("id").isEmpty()){
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("niandu", Integer.valueOf(arg0.getPara("niandu"))+1);
						param1.put("xzqhdm", arg0.getPara("xzqhdm"));
						param1.put("qyzzjgdm", arg0.getPara("qyzzjgdm"));
						param1.put("xingzhi", arg0.getPara("xingzhi"));
						boolean bool=Duang.duang(ChuBeiLiangJHBO.class).findchubeiliangjhRW(param1);
						if(bool){
							addError("msg", "保存失败，该储备粮计划已存在！");
						}
					//}
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
