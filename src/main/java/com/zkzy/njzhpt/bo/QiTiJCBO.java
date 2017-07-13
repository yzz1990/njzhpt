package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.QiTiJCDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class QiTiJCBO {
	@Before(AutoPageInterceptor.class)
	public Ret selectqiti(HashMap<String, Object> map) throws Exception {
		
		Page<Record> list= QiTiJCDAO.selectqiti(map);
		Ret ret = Ret.create("list", list);
		return ret;
	}
}
