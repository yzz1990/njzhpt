package com.zkzy.framework.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class FrameworkDAO {

	public static Page<Record> queryFwAms(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and parentid = ?", "parentid");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from fw_ams where 1=1" + s.getSql() + "order by orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwRoleAms(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and roleid = ?", "roleid");
		p.put("and amsid = ?", "amsid");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from fw_role_ams where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwUser(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and LOCATE(id,?) > 0 ", "ids");
		p.put("and canuse < 2", "notdelete");
		p.put("and id = ?", "id");
		p.put("and loginname = ?", "loginname");
		p.put("and password = ?", "password");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from fw_user where 1=1" + s.getSql() + "order by orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwDep(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and parentid = ?", "parentid");
		p.put("and id = ?", "id");
		p.put("and canuse < 2", "notdelete");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from fw_dep where 1=1" + s.getSql() + "order by orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwRole(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from fw_role where 1=1" + s.getSql() + "order by orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwUserDep(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from fw_user_dep where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryFwUserRole(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from fw_user_role where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
}
