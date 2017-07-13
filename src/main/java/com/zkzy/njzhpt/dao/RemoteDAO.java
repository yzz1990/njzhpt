package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class RemoteDAO {
	/**
	 * 获取仓库
	 * @throws Exception 
	 */
	public static Page<Record> findAllCangku(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"FROM dbo.t_GrainPoint" + s.getSql()
						+ "order by vCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取视频
	 * @throws Exception 
	 */
	public static Page<Record> findShiPin(HashMap<String, Object> queryparam,String code) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"FROM dbo.t_ShiPing where vWareHouseCode="+ code
				+ "order by code",
				s.getParam().toArray(new Object[0]));
		return page;
	}
}
