package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class chubeliangjihuaValidator extends Validator{

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		this.setShortCircuit(true);
		if ("/chubeiliangJH/bianji".equals(this.getActionKey())) {
			try{	
					if(arg0.getPara("id").isEmpty()){
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("niandu", arg0.getPara("niandu"));
						param1.put("quming", arg0.getPara("quming"));
						param1.put("ccqy", arg0.getPara("ccqy"));
						param1.put("cckd",arg0.getPara("cckd"));
						param1.put("ccch", arg0.getPara("ccch"));
						boolean bool=Duang.duang(ChuBeiLiangJHBO.class).findchubeiliangjh(param1);
						if(bool){
							addError("msg", "保存失败，同一年度该仓已存在储备粮计划！");
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
