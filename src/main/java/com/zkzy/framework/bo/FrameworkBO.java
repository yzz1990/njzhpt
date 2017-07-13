package com.zkzy.framework.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.framework.dao.FrameworkDAO;
import com.zkzy.framework.lnterceptor.AutoPageInterceptor;
import com.zkzy.framework.lnterceptor.UserDepCacheInterceptor;

public class FrameworkBO {

	// region 用户
	@Before(Tx.class)
	public Ret saveUser(HashMap<String, Object> map) throws Exception {
		String userid = SqlUtil.uuid();
		Object depids = map.get("depid");
		Object roleids = map.get("roleid");
		Record userRecord = new Record().setColumns(map).set("id", userid)
				.remove("depid").remove("roleid");
		Db.use("njpt").save("fw_user", userRecord);
		if (depids instanceof String) {
			depids = new String[] { String.valueOf(depids) };
		}
		for (String depid : (String[]) depids) {
			Record user_depRecord = new Record().set("id", SqlUtil.uuid())
					.set("userid", userid).set("depid", depid);
			Db.use("njpt").save("fw_user_dep", user_depRecord);
		}
		if (roleids instanceof String) {
			roleids = new String[] { String.valueOf(roleids) };
		}
		for (String roleid : (String[]) roleids) {
			Record user_roleRecord = new Record().set("id", SqlUtil.uuid())
					.set("userid", userid).set("roleid", roleid);
			Db.use("njpt").save("fw_user_role", user_roleRecord);
		}
		return Ret.create("ret", true);
	}

	@Before(Tx.class)
	public Ret updateUser(HashMap<String, Object> map) throws Exception {
		String userid = String.valueOf(map.get("id"));
		Object depids = map.get("depid");
		Object roleids = map.get("roleid");
		Record userRecord = new Record().setColumns(map).set("id", userid)
				.remove("depid").remove("roleid");
		Db.use("njpt").update("fw_user", userRecord);
		Db.use("njpt").delete("fw_user_dep", "userid",
				new Record().set("userid", userid));
		Db.use("njpt").delete("fw_user_role", "userid",
				new Record().set("userid", userid));
		if (depids instanceof String) {
			depids = new String[] { String.valueOf(depids) };
		}
		for (String depid : (String[]) depids) {
			Record user_depRecord = new Record().set("id", SqlUtil.uuid())
					.set("userid", userid).set("depid", depid);
			Db.use("njpt").save("fw_user_dep", user_depRecord);
		}
		if (roleids instanceof String) {
			roleids = new String[] { String.valueOf(roleids) };
		}
		for (String roleid : (String[]) roleids) {
			Record user_roleRecord = new Record().set("id", SqlUtil.uuid())
					.set("userid", userid).set("roleid", roleid);
			Db.use("njpt").save("fw_user_role", user_roleRecord);
		}
		return Ret.create("ret", true);
	}

	@Before(AutoPageInterceptor.class)
	public Ret queryFwUser(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwUser(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	public Ret deleteUser(HashMap<String, String> map) throws Exception {
		String id = map.get("id");
		Db.deleteById("fw_user", id);
		return Ret.create("ret", true);
	}

	// #region

	// region 角色

	@Before(Tx.class)
	public Ret deleteRole(HashMap<String, String> map) throws Exception {
		Db.use("njpt").deleteById("fw_role", map.get("id"));
		updateAmsChildCount();
		return Ret.create("ret", true);
	}

	public Ret saveRole(HashMap<String, Object> map) throws Exception {
		Record depRecord = new Record().setColumns(map).set("id",
				SqlUtil.uuid());
		Db.use("njpt").save("fw_role", depRecord);
		return Ret.create("ret", true);
	}

	public Ret updateRole(HashMap<String, Object> map) throws Exception {
		Record roleRecord = new Record().setColumns(map);
		Db.use("njpt").update("fw_role", roleRecord);
		return Ret.create("ret", true);
	}

	@Before(AutoPageInterceptor.class)
	public Ret queryFwRole(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwRole(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	// #region

	// region 部门

	@Before(AutoPageInterceptor.class)
	public Ret queryFwDep(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwDep(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	@Before(Tx.class)
	public Ret saveDep(HashMap<String, Object> map) throws Exception {
		Record depRecord = new Record().setColumns(map).set("id",
				SqlUtil.uuid());
		Db.use("njpt").save("fw_dep", depRecord);
		updateDepChildCount();
		return Ret.create("ret", true);
	}

	@Before(Tx.class)
	public Ret updateDep(HashMap<String, Object> map) throws Exception {
		Record depRecord = new Record().setColumns(map);
		Db.use("njpt").update("fw_dep", depRecord);
		updateDepChildCount();
		return Ret.create("ret", true);
	}

	@Before(Tx.class)
	public Ret deleteDep(HashMap<String, String> map) throws Exception {
		String id = map.get("id");
		Db.deleteById("fw_dep", id);
		return Ret.create("ret", true);
	}

	public void updateDepChildCount() {
		Db.use("njpt")
				.update("update fw_dep a left JOIN (select count(*) as count,parentid from fw_dep where canuse = 1 GROUP BY parentid) b on a.id = b.parentid SET a.childcount= IFNULL(b.count,0)");
	}

	// #region

	// region 菜单
	@Before(AutoPageInterceptor.class)
	public Ret queryFwAms(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwAms(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	@Before(Tx.class)
	public Ret saveAms(HashMap<String, Object> map) throws Exception {
		Record depRecord = new Record().setColumns(map).set("id",
				SqlUtil.uuid());
		Db.use("njpt").save("fw_ams", depRecord);
		updateAmsChildCount();
		return Ret.create("ret", true);
	}

	@Before(Tx.class)
	public Ret updateAms(HashMap<String, Object> map) throws Exception {
		Record depRecord = new Record().setColumns(map);
		Db.use("njpt").update("fw_ams", depRecord);
		updateAmsChildCount();
		return Ret.create("ret", true);
	}

	@Before(Tx.class)
	public Ret deleteAms(HashMap<String, String> map) throws Exception {
		Db.use("njpt").deleteById("fw_ams", map.get("id"));
		updateAmsChildCount();
		return Ret.create("ret", true);
	}

	public void updateAmsChildCount() {
		Db.use("njpt")
				.update("update fw_ams a left JOIN (select count(*) as count,parentid from fw_ams GROUP BY parentid) b on a.id = b.parentid SET a.childcount= IFNULL(b.count,0)");
	}

	// #region

	// region 角色/菜单绑定
	@Before(AutoPageInterceptor.class)
	public Ret queryFwRoleAms(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwRoleAms(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 * 菜单-角色 绑定
	 * 
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Ret saveAmsRole(HashMap<String, Object> map) throws Exception {
		Record r = new Record().set("roleid", map.get("roleid"));
		Db.use("njpt").delete("fw_role_ams", "roleid", r);
		map.remove("roleid");
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			r.set("amsid", key);
			r.set("id", SqlUtil.uuid());
			Db.use("njpt").save("fw_role_ams", r);
		}
		return Ret.create("ret", true);
	}

	// #region

	// region 用户/角色绑定

	/**
	 * 获取用户角色
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryFwUserRole(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwUserRole(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 * 菜单-角色 绑定
	 * 
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Ret saveYonghuRole(HashMap<String, Object> map) throws Exception {
		Record r = new Record().set("userid", map.get("userid"));
		Db.use("njpt").delete("fw_user_role", "userid", r);
		map.remove("userid");

		Object roleids = map.get("roleid");
		if (roleids == null) {
			roleids = new String[] {};
		} else if (roleids instanceof String) {
			roleids = new String[] { String.valueOf(roleids) };
		}
		for (String roleid : (String[]) roleids) {
			r.set("roleid", roleid);
			r.set("id", SqlUtil.uuid());
			Db.use("njpt").save("fw_user_role", r);
		}
		return Ret.create("ret", true);
	}

	// #region

	// region 用户/部门绑定
	@Before({ AutoPageInterceptor.class, UserDepCacheInterceptor.class })
	public Ret queryFwYonghuBumen(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = FrameworkDAO.queryFwUserDep(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 * 菜单-角色 绑定
	 * 
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	@Before(Tx.class)
	public Ret saveYonghuBumen(HashMap<String, Object> map) throws Exception {
		Record r = new Record().set("userid", map.get("userid"));
		Db.use("njpt").delete("fw_user_dep", "userid", r);
		map.remove("userid");
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			r.set("depid", key);
			r.set("id", SqlUtil.uuid());
			Db.use("njpt").save("fw_user_dep", r);
		}
		return Ret.create("ret", true);
	}
	// #region
	
}