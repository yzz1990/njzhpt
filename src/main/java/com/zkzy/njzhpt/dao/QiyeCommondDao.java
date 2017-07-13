package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;

public class QiyeCommondDao {

	/**
	 * 分页查询企业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiyexinxi(HashMap<String, Object> queryparam) throws Exception{
		queryparam.put("ID", UserKit.currentUserInfo().getUser().getString("id"));
		System.out.println(UserKit.currentUserInfo().getUser().getString("id"));
		Param p = new Param();
		p.put("and a.ID = ?", "ID");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.qyxzmc = ?", "qyxzmc");
		p.put("and a.jyywlxbh = ?", "jyywlxbh");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select a.*,c.loginname as loginname,c.password as password ",
				"from tz_qiye a left JOIN fw_user_dep b on a.ID=b.depid left JOIN fw_user c on c.id=b.userid LEFT JOIN njpt_diqu d on a.xzqhdm =d.xzqhdm LEFT JOIN fw_area e on e.id=d.areaid where 1=1  " + s.getSql() + " ORDER BY e.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findDiqu(HashMap<String, Object> queryparam) throws Exception {
		queryparam.put("name", UserKit.currentUserInfo().getArea().getString("name"));
		Param p = new Param();
		p.put("and b.id = ?", "id");
		p.put("and b.areaid = ?", "areaid");
		p.put("and a.name = ?", "name");
		p.put("and b.xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(1, 10000, 
				"select b.*,a.name",
				" from fw_area a LEFT JOIN njpt_diqu b on a.id=b.areaid where a.parentid!='root' " + s.getSql() + " ORDER BY a.orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}

	

}
