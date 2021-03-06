package com.zkzy.njzhpt.bo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.config.interfaces.AppICommond;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.ChengPinLiangDAO;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.QiyeCommondDao;
import com.zkzy.njzhpt.dao.QuxuanCommondDao;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.dao.ZhiLiangGLDAO;
import com.zkzy.njzhpt.dao.ZhiliangzsDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class QiyeQuxuanAppCommondBo implements AppICommond {

	/**
	 * 获取企业备案信息
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyebeian(HashMap<String, Object> param)
			throws Exception {
		String sql="SELECT qyzzjgdm FROM tz_qiye WHERE id=?";
		Record re=Db.findFirst(sql,param.get("shenpiren"));
		param.put("qymc", re.get("qyzzjgdm"));
		Page<Record> page=RenyuanDao.findQiyebeian(param);
		for(Record record:page.getList()){
			//获取流程名称
			Record re1=Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? order by jiedian desc",record.getStr("liuchengid"));
			 record.set("liuchengname", re1.get("name"));
			 record.set("jd",re1.get("jiedian"));
			 
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),record.getStr("shenpiren"));
				if(liucheng!=null){
					record.set("jiedian", liucheng.getInt("jiedian"));
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
					}
				}
			}
		}
		return page;
	}

	/**
	 * 获取熏蒸备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception {
		String sql="SELECT qyzzjgdm FROM tz_qiye WHERE id=?";
		Record re=Db.findFirst(sql,param.get("shenpiren"));
		param.put("qymc", re.get("qyzzjgdm"));
		Page<Record> page=RenyuanDao.findXunzheng(param);
		for(Record record:page.getList()){
			
			//获取流程名称与库点名称
			Record re2=Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? order by jiedian desc",record.getStr("liuchenghao"));
			 record.set("liuchengname", re2.get("name"));
			 Record re1=Db.findFirst("select * from tz_kudian where qyzzjgdm=? and kdbm=?",record.getStr("qyzzjgdm"),record.getStr("kudian"));
			 record.set("kdmc", re1.get("kdmc"));
			 record.set("jd",re2.get("jiedian"));
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
						record.set("jiedian", liucheng.getInt("jiedian"));
					}
				}
			}
		}
		return page;
	}

	/**
	 * 获取药剂备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoji(HashMap<String, Object> param) throws Exception {
		String sql="SELECT qyzzjgdm FROM tz_qiye WHERE id=?";
		Record re=Db.findFirst(sql,param.get("shenpiren"));
		param.put("qymc", re.get("qyzzjgdm"));
		Page<Record> page=RenyuanDao.findYaoji(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
						record.set("jiedian", liucheng.getInt("jiedian"));
					}
				}
			}
		}
		return page;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxzzy(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		String qymc = UserKit.currentUserInfo().getDep().getString("name");
		queryparam.put("qyzzjgdm",qymc);
		Page<Record> PageRecord = CangchuglDao.findxzzy(queryparam);
		return PageRecord;
	}

}
