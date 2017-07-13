package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.ChengPinLiangDAO;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class ChengPinLiangBO {
	@Before(AutoPageInterceptor.class)
	public Ret selectCPLCBJH(HashMap<String, Object> map) throws Exception {
		
		Page<Record> PageRecord = ChengPinLiangDAO.selectCPLCBJH(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	public Ret saveCPLCBJH(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChengPinLiangDAO.saveCPLCBJH(map);
		Ret ret = Ret.create("ret", bool);
		return ret;
	}
	public Ret deleteCPLCBJH(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChengPinLiangDAO.deleteCPLCBJH(map);
		Ret ret = Ret.create("ret", bool);
		return ret;
	}
	
}
