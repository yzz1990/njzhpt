package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.ZhiliangzsDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class ZhiliangzsBO {

	/**
	 * 遍历库点质量大概
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret querykudian(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ZhiliangzsDAO.querykudian(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 遍历质量追溯
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryzhiliang(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ZhiliangzsDAO.queryzhiliang(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 遍历视频追溯
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryshipingzs(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ZhiliangzsDAO.queryshipingzs(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 遍历入库登记表
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryrukudj(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ZhiliangzsDAO.queryrukudj(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 遍历出库登记表
	 */
	@Before(AutoPageInterceptor.class)
	public Ret querychukudj(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ZhiliangzsDAO.querychukudj(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}


	/**
	 * 保存入库质量
	 * @param param
	 * @return
	 */
	public Ret addrukuzhiliang(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		System.out.println(param.get("qyzzjgdm"));
		System.out.println(param.get("kdbm"));
		boolean result = Db.save("tz_zhiliangmx", "id", record);
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","新增成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","新增失败"));
			return msg;
		}
	}
	
	
}
