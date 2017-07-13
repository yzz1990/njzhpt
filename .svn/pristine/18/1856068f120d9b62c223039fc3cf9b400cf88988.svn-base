package com.zkzy.njzhpt.validator;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.validate.Validator;
import com.zkzy.njzhpt.bo.ChuBeiLiangJHBO;
import com.zkzy.njzhpt.bo.jibenxinxiBo;

public class FeiKSHCRKSJ extends Validator{

	@Override
	protected void validate(Controller arg0) {
		this.setShortCircuit(true);
		if ("/zhiliangGL/xzfeikshcrksj".equals(this.getActionKey())) {
			validateRequiredString("vSwiftNumber", "nameMsg", "请输入流水号！");			
			try{
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("vSwiftNumber", arg0.getPara("vSwiftNumber"));
					param1.put("qyzzjgdm", arg0.getPara("qyzzjgdm"));
					param1.put("kdbm", arg0.getPara("kdbm"));
					param1.put("vWareHouseCode", arg0.getPara("vWareHouseCode"));
					param1.put("page",1);
					param1.put("rows", 1);
					Page<Record>  jibenxinxi=Duang.duang(jibenxinxiBo.class).findfeikshkdcrk(param1);
					if(jibenxinxi.getList().size()>0){
						addError("nameMsg", "该流水号已存在!");
					}

			}catch(Exception e){
				handleError(arg0);
			}
			validateRequiredString("dtRegistrateTime", "nameMsg", "请输入报港时间！");
			validateRequiredString("vDirection", "nameMsg", "请选择出入库标识！");
			validateRequiredString("xzqhdm", "nameMsg", "请选择地区！");
			validateRequiredString("qyzzjgdm", "nameMsg", "请选择企业名称！");
			validateRequiredString("kdbm", "nameMsg", "请选择库点名称！");
			validateRequiredString("vWareHouseCode", "nameMsg", "请选择仓房名称！");
			validateRequiredString("vGrainSubTypeCode", "nameMsg", "请选择粮食品种！");
			validateRequiredString("vGrainPropertyId", "nameMsg", "请选择粮食性质！");
			validateRequiredString("vGrainLevel", "nameMsg", "请输入粮食等级！");
			validateRequiredString("vViechelNumber", "nameMsg", "请输入车辆编号！");
			/*validateRequiredString("bGWAlPrimoPWAlSecondo", "nameMsg", "请选择先毛重还是先皮重！");
			validateRequiredString("bIsUseMulitViechle", "nameMsg", "请选择是否多车！");
			validateRequiredString("bIsMultiWeight", "nameMsg", "请选择是否多次称重！");
			validateRequiredString("bIsStandardProgress", "nameMsg", "请选择是否标准流程！");
			validateRequiredString("bIsWild", "nameMsg", "请选择是否是散户！");*/
			/*validateRequiredString("dmOriginalMoisture", "nameMsg", "请输入原始水扣！");
			validateRequiredString("dmOriginalImpurity", "nameMsg", "请输入原始杂扣！");
			validateRequiredString("dmOriginalMineral", "nameMsg", "请输入原始矿物质扣！");
			validateRequiredString("dmOriginalGamma", "nameMsg", "请输入原始容重！");
			validateRequiredString("dmOriginalUnsoundKernel", "nameMsg", "请输入原始不完善扣！");*/
			validateRequiredString("dmNW", "nameMsg", "请输入净重！");
			validateRequiredString("dmCheckWeight", "nameMsg", "请输入结算重量！");
			
		}
		
	}

	@Override
	protected void handleError(Controller arg0) {
		arg0.keepPara("name");
		String json = "{\"ret\":false,\"msg\":\"" + arg0.getAttr("nameMsg")
				+ "\"}";
		arg0.renderJson(json);
	}

}
