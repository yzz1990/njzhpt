package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.RemoteDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class RemoteBO {
	/**
	 * 获取仓库
	 * @throws Exception 
	 */
	@Before(com.zkzy.framework.lnterceptor.AutoPageInterceptor.class)
	public Ret findAllCangku(HashMap<String, Object> queryparam) throws Exception {
		Ret r = new Ret();
		Page<Record> list=RemoteDAO.findAllCangku(queryparam);
		r.put("total", list.getTotalRow());
		r.put("rows", list.getList());
		return r;
	}
	
	/**
	 * 获取视频点
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findShiPin(HashMap<String, Object> queryparam) throws Exception {
		Ret r = new Ret();
		String code = queryparam.get("vcode").toString();
		Page<Record> list=RemoteDAO.findShiPin(queryparam,code);
		r.put("total", list.getTotalRow());
		r.put("rows", list.getList());
		return r;
	}
}
