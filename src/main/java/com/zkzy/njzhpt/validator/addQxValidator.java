package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class addQxValidator extends Validator {

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		String json = "{\"ret\":false,\"message\":\"" + arg0.getAttr("nameMsg") + "\"}";
		arg0.renderJson(json);
	}

	@Override
	protected void validate(Controller arg0) {
		this.setShortCircuit(true);
		if ("/jibenxinxi/addDiqu".equals(this.getActionKey())) {
			validateRequiredString("xzqhdm", "nameMsg", "请输入行政区划代码！");			
			validateRequiredString("name", "nameMsg", "请输入区域名称！");	
			try {
				if(!arg0.getPara("id").equals("")){//编辑
					HashMap<String, Object> param=new HashMap<String, Object>();
					param.put("id", arg0.getPara("id"));
					param.put("page",1);
					param.put("rows", 1);
					Page<Record> quxianList = Duang.duang(jibenxinxiBo.class).findDiqu(param);
					if(quxianList.getList().size()>0){
						String xzqhdm=quxianList.getList().get(0).getStr("xzqhdm");
						if(!xzqhdm.equals(arg0.getPara("xzqhdm"))){
							param.remove("id");
							param.put("xzqhdm", arg0.getPara("xzqhdm"));
							Page<Record> qxListqxList = Duang.duang(jibenxinxiBo.class).findDiqu(param);
							if(qxListqxList.getList().size()>0){
								addError("nameMsg", "该区县已添加!");
							}else{
								param.remove("xzqhdm");
								param.put("name", arg0.getPara("name"));
								Page<Record> qxListqxList1 = Duang.duang(jibenxinxiBo.class).findDiqu(param);
								if(qxListqxList1.getList().size()>0){
									addError("nameMsg", "该区县已添加!");
								}
							}
						}else{
							param.remove("id");
							String name=quxianList.getList().get(0).getStr("name");
							if(!name.equals(arg0.getPara("name"))){
								param.put("name", arg0.getPara("name"));
								Page<Record> qxListqxList = Duang.duang(jibenxinxiBo.class).findDiqu(param);
								if(qxListqxList.getList().size()>0){
									addError("nameMsg", "该区县已添加!");
								}else{
									param.remove("xzqhdm");
									param.put("name", arg0.getPara("name"));
									Page<Record> qxListqxList1 = Duang.duang(jibenxinxiBo.class).findDiqu(param);
									if(qxListqxList1.getList().size()>0){
										addError("nameMsg", "该区县已添加!");
									}
								}
							}
						}
					}
				}else{
					HashMap<String, Object> param=new HashMap<String, Object>();
					param.put("xzqhdm", arg0.getPara("xzqhdm"));
					param.put("page",1);
					param.put("rows", 1);
					Page<Record> quxianList = Duang.duang(jibenxinxiBo.class).findDiqu(param);
					if(quxianList.getList().size()>0){					
							addError("nameMsg", "该区县已添加!");
					}else{
						param.remove("xzqhdm");
						param.put("name", arg0.getPara("name"));
						Page<Record> qxListqxList1 = Duang.duang(jibenxinxiBo.class).findDiqu(param);
						if(qxListqxList1.getList().size()>0){
							addError("nameMsg", "该区县已添加!");
						}
					}
				}
				
			} catch (Exception e) {
				handleError(arg0);
			}
		}
	}

}
