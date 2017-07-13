package com.zkzy.njzhpt.bo;

import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.QuXianDao;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class QuxianBo {


	/**
	 * 百度地图获取企业点位
	 * @author yzz
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findAllQiye(HashMap<String, Object> param) throws Exception {
		
		return QuXianDao.findAllQiye(param);
	}
	

	/**
	 * 百度地图获取库点点位
	 * @author yzz
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findAllKudian(HashMap<String, Object> param) throws Exception {
		
		return QuXianDao.findAllKudian(param);
	}


	/**
	 * 百度地图获取企业列表
	 * @author yzz
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryQiyeInfo(HashMap<String, Object> param) throws Exception {

		Ret ret=new Ret();
		ret.put("rows",QuXianDao.queryQiyeInfo(param).getList());
		ret.put("total",Db.find("select * from tz_kudian").size());
		
		return ret;
	}

	

	/**
	 * 区县平台获取视频点信息
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryShipin(HashMap<String, Object> param) throws Exception {
		
		return QuXianDao.queryShipin(param);
	}

	/**
	 * 获取视频厂家
	 * @param param
	 */
	public Ret queryspcj( HashMap<String, Object> param) {
		Ret ret=new Ret();
		Record  record=Db.findFirst("select * from dw_Property where qyzzjgdm=? and kdbm=? and propname=?",param.get("qyzzjgdm"),param.get("kdbm"),"spcj");
		if(record!=null){
			ret.put("spcj",record.getStr("propvalue"));
		}else{
			ret.put("spcj","");
		}
		Record  record1=Db.findFirst("select * from dw_Property where qyzzjgdm=? and kdbm=? and propname=?",param.get("qyzzjgdm"),param.get("kdbm"),"spnet");
		if(record1!=null){
			ret.put("spnet",record1.getStr("propvalue"));
		}else{
			ret.put("spnet","");
		}
		
		return ret;
	}

	/**
	 * 获取百度地图元素
	 * @author yzz
	 * @throws Exception
	 */

	public Ret getMapDocument(HashMap<String, Object> param) {
		Ret ret=new Ret();
		List<Record>  records=   Db.find("select * from t_Property");
		for(Record record:records){
			ret.put(record.getStr("propname"), record.getStr("propvalue"));
		}
		return ret;
	}


	/**
	 * 百度地图获取库点信息
	 * @author yzz
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> getqyinfo(HashMap<String, Object> param) throws Exception {
		return QuXianDao.getqyinfo(param);
	}

}
