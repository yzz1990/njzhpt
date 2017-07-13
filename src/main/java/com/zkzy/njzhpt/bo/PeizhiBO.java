package com.zkzy.njzhpt.bo;

import java.util.HashMap;
import java.util.List;

import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.JibenDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class PeizhiBO {

	public Ret saveTProperty(HashMap<String, Object> param) {
		Db.deleteById("t_Property", "propname", param.get("propname"));
		Record r = new Record();
		r.set("propname", param.get("propname"))
				.set("propvalue", param.get("propvalue"))
				.set("propremark", param.get("propremark"));
		Db.save("t_Property", r);
		BeetlRenderFactory.groupTemplate.getSharedVars().put(
				String.valueOf(param.get("propname")), param.get("propvalue"));
		return Ret.create("ret", true);
	}

	public Record getTProperty() {
		Record r = new Record();
		List<Record> recordList = Db.find("select * from t_Property");
		for (Record record : recordList) {
			r.set(record.get("propname"), record.get("propvalue"));
		}
		return r;
	}

	public Record getTProperty(String propname) {
		Record r = Db.findById("t_Property", "propname", propname);
		return r;
	}

	/**qitixielou
	 * 气体熏蒸泄露--yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret qitixielou(HashMap<String, Object> queryparam) throws Exception {
		Ret r = new Ret();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("vStation", "仓外");
		param.put("page", "1");
		param.put("rows", "1000");
		param.put("qyzzjgdm", queryparam.get("qyzzjgdm"));
		param.put("kdbm", queryparam.get("kdbm"));
		Page<Record> qitixielouPage = JibenDAO.qitixielou(param);
		r.put("total", qitixielouPage.getTotalRow());
		r.put("rows", qitixielouPage.getList());
		return r;

	}
}
