package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class QuXianDao {


	/**
	 * 百度地图获取企业点位
	 * @author yzz
	 * @throws Exception
	 */
	public static Page<Record> findAllQiye(HashMap<String, Object> param) throws Exception {
	
			Param p = new Param();
			p.put("and ID = ?", "ID");
			p.put("and qyzzjgdm = ?", "qyzzjgdm");
			p.put("and maplevel <= ?", "maplevel");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select * ",
					"from  tz_qiye where qyjd is NOT NULL and qywd is NOT NULL "+ s.getSql(),
					s.getParam().toArray(new Object[0]));
			return page;
		}

	

	/**
	 * 百度地图获取库点点位
	 * @author yzz
	 * @throws Exception
	 */
	public static Page<Record> findAllKudian(HashMap<String, Object> param) throws Exception {

		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and kdmc like ?", "kdmc","%%%s%%");
		p.put("and maplevel <= ?", "maplevel");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from  tz_kudian where qyjd is NOT NULL and qywd is NOT NULL "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}



	/**
	 * 百度地图获取企业列表
	 * @author yzz
	 * @throws Exception
	 */
	public static Page<Record> queryQiyeInfo(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and kdmc like ?", "kdmc","%%%s%%");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from  tz_kudian where 1=1 "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}



	/**
	 * 区县平台获取视频点信息
	 * @throws Exception
	 */
	public static Page<Record> queryShipin(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from  sp_ShiPing where 1=1 "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}



	/**
	 * 百度地图获取库点信息
	 * @author yzz
	 * @throws Exception
	 */
	public static Page<Record> getqyinfo(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from  tz_kudian where 1=1 "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

}
