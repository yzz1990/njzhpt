package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class GonggongDAO {

	/**
	 * 遍历公告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querygonggao(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and title = ?", "title");
		p.put("and zhuangtai = ?", "zhuangtai");
		p.put("and leixin = ?", "leixin");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from gg_xinxi where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历存量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycunliang(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and pinzhong = ?", "pinzhong");
		p.put("and shenfenzheng = ?", "shenfenzheng");
		p.put("and name = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from gg_yuyuecunliang where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
}
