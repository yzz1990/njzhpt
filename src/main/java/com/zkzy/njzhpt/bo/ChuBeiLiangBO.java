package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.ChuBeiLiangDAO;

public class ChuBeiLiangBO {
	public Ret queryChuBeiLiang(HashMap<String, Object> map) throws Exception {
		
		Page<Record> PageRecord = ChuBeiLiangDAO.queryChuBeiLiang(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}

}
