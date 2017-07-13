package com.zkzy.njzhpt.bo;

import java.util.List;

import com.jfinal.aop.Duang;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.config.interfaces.ICommond;

public class CommondBo {

	
	public static ICommond getCommond() throws Exception {
		ICommond icomond = null;
		List<String> authList = UserKit.currentUserInfo().getAuth();
		if(authList.contains("auth_manager")){//管理员/南京市粮食局//监督检查处科员//储备调控处科员//局综合处科员权限//局领导及局科室处长
			icomond=Duang.duang(new jibenxinxiBo());
		}else if(authList.contains("auth_county")){//区县粮食局用户
			icomond=Duang.duang(new QuxuanCommondBo());		
		}else if(authList.contains("auth_company")){//企业用户
			icomond=Duang.duang(new QiyeQuxuanCommondBo());			
		}
		return icomond;
	}
	
}
