package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ChuBeiLiangDAO {
	public static Page<Record> queryChuBeiLiang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select *",
				"from njpt_fenqishangbao where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
		
	}

}
