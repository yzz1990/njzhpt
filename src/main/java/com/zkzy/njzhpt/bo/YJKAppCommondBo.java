package com.zkzy.njzhpt.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.config.interfaces.AppICommond;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.ChengPinLiangDAO;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.dao.EmergencyDAO;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.QuxuanCommondDao;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ZhiLiangGLDAO;
import com.zkzy.njzhpt.dao.ZhiliangzsDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;

public class YJKAppCommondBo implements AppICommond {
	/**
	 * 获取企业备案信息
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyebeian(HashMap<String, Object> param)
			throws Exception {
		List<Record> list=new ArrayList<Record>();
		Page<Record> page= new Page<Record>(list,1,1,10,1000);
		return page;
	}

	/**
	 * 获取熏蒸备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception {
		List<Record> list=new ArrayList<Record>();
		Page<Record> page= new Page<Record>(list,1,1,10,1000);
		return page;
	}

	/**
	 * 获取药剂备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoji(HashMap<String, Object> param) throws Exception {
		String sql="SELECT c.xzqhdm FROM fw_user_dep a LEFT JOIN fw_dep b on a.depid=b.id LEFT JOIN njpt_diqu c ON b.areaid=c.areaid WHERE a.userid=?";
		Record re=Db.findFirst(sql,param.get("shenpiren"));
		param.put("quyu", re.get("xzqhdm"));
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
				}else{
					Record liu=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=?",record.getStr("liuchenghao"));
					if(liu!=null){
						
						Record dep= Db.findFirst("select * from fw_dep  where id=?",liu.getStr("shenpibumen"));
						
						if(dep!=null){
							record.set("depname", dep.getStr("name"));	
						}
					}
				}
			}
		}
		return page;
	}

	@Override
	public Page<Record> findxzzy(HashMap<String, Object> queryparam)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
