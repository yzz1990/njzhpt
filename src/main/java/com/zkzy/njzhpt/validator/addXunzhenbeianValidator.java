package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.RenyuanBo;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class addXunzhenbeianValidator extends Validator {

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
			if ("/renyuan/addXzBa".equals(this.getActionKey())) {
				validateRequiredString("beianbianhao", "nameMsg", "请填写备案编号！");
				validateRequiredString("qiyeid", "nameMsg", "请选择备案企业！");
				validateRequiredString("liuchenghao", "nameMsg", "请选择备案流程！");
				validateRequiredString("kudian", "nameMsg", "请选择熏蒸库点！");
				//validateRequiredString("shuliang", "nameMsg", "请填写药剂数量！");
				validateRequiredString("fuzeren", "nameMsg", "请填写负责人！");
				//validateRequiredString("fangshi", "nameMsg", "请填写熏蒸方式！");
				try{
						HashMap<String, Object> param1=new HashMap<String, Object>();
						param1.put("beianbianhao", arg0.getPara("beianbianhao"));
						param1.put("page",1);
						param1.put("rows", 1000);
						Page<Record>  jibenxinxi=Duang.duang(RenyuanBo.class).findxunzhengbeianbianhao(param1);
						if(jibenxinxi.getList().size()>0){
							addError("nameMsg", "该备案编号已经存在!");
						}
	
				}catch(Exception e){
					handleError(arg0);
				}
			}
		}else{
			if ("/renyuan/addXzBa".equals(this.getActionKey())) {
				validateRequiredString("beianbianhao", "nameMsg", "请填写备案编号！");
				validateRequiredString("qiyeid", "nameMsg", "请选择备案企业！");
				validateRequiredString("liuchenghao", "nameMsg", "请选择备案流程！");
				validateRequiredString("kudian", "nameMsg", "请选择熏蒸库点！");
				//validateRequiredString("shuliang", "nameMsg", "请填写药剂数量！");
				validateRequiredString("fuzeren", "nameMsg", "请填写负责人！");
				//validateRequiredString("fangshi", "nameMsg", "请填写熏蒸方式！");
				try{
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("beianbianhao", arg0.getPara("beianbianhao"));
					param1.put("id", arg0.getPara("id"));
					param1.put("page",1);
					param1.put("rows", 1000);
					Page<Record>  jibenxinxi=Duang.duang(RenyuanBo.class).findxunzhengbeianbianhao(param1);
					if(!(jibenxinxi.getList().size()>0)){
						param1.remove("id");
						jibenxinxi=Duang.duang(RenyuanBo.class).findxunzhengbeianbianhao(param1);
						if(jibenxinxi.getList().size()>0){
							addError("nameMsg", "该备案编号已经存在!");
						}
					}
	
			}catch(Exception e){
				handleError(arg0);
			}
		}
		}
	}

}
