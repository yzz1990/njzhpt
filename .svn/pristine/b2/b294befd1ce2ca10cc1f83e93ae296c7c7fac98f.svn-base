package com.zkzy.njzhpt.bo;

import java.util.HashMap;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.EmergencyDAO;
import com.zkzy.njzhpt.dao.GonggongDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class GonggongBO {
	
	/**
	 * 遍历公告信息表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret querygonggao(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = GonggongDAO.querygonggao(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 遍历
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret querycunliang(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = GonggongDAO.querycunliang(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 * 新增公告
	 * @param param
	 * @return
	 */
	public Ret addgonggao(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id", uuid);
		boolean result = Db.save("gg_xinxi", record);
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

	/**
	 * 修改公告
	 * @param param
	 * @return
	 */
	public Ret upgonggao(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		Record record = new Record().setColumns(param);
		boolean result = Db.update("gg_xinxi", "id", record);
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","修改成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","修改失败"));
			return msg;
		}
	}

	/**
	 * 删除公告
	 * @param param
	 * @return
	 */
	public Ret delgonggao(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		boolean result = Db.deleteById("gg_xinxi", param.get("id"));
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}

	/**
	 * 新增存粮
	 * @param param
	 * @return
	 */
	public Ret addcunliang(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id", uuid);
		boolean result = Db.save("gg_yuyuecunliang", record);
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
	
	/**
	 * 修改存粮
	 * @param param
	 * @return
	 */
	public Ret upcunliang(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		Record record = new Record().setColumns(param);
		boolean result = Db.update("gg_yuyuecunliang", "id", record);
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","修改成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","修改失败"));
			return msg;
		}
	}
	
	/**
	 * 删除存粮
	 * @param param
	 * @return
	 */
	public Ret delcunliang(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		boolean result = Db.deleteById("gg_yuyuecunliang", param.get("id"));
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}

}
