package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.ChengPinLiangDAO;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.dao.EmergencyDAO;
import com.zkzy.njzhpt.dao.JibenxinxiDao;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.dao.ZhiLiangGLDAO;
import com.zkzy.njzhpt.dao.ZhiliangzsDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class jibenxinxiBo implements ICommond {
	/**
	 * 获取用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findUserInfo (HashMap<String, Object> param) throws Exception {
		return JibenxinxiDao.findUserInfo(param);
	} 
	
	/**
	 *获取库点实时收购情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryshishi(HashMap<String, Object> map) throws Exception {
		
		HashMap<String,Object> param = map;
		if("请选择".equals(param.get("xian"))){
			param.remove("xian");
		}
		if("请选择".equals(param.get("qyzzjgdm"))){
			param.remove("qyzzjgdm");
		}
		if("请选择".equals(param.get("kdmc"))){
			param.remove("kdmc");
		}
		if("0".equals(param.get("isksh"))){
			param.remove("isksh");
		}
		Page<Record> userPageRecord = ShouchukeshihuaDAO.querykchz(param);
		
		//========================================================
		//获取当前分期
		Date dt = new Date();
		String starttime;
		String endtime;
		String jidu;
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String today = matter1.format(dt);
	    String sql = "select starttime , endtime, jidu "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
	    Record recordt = Db.findFirst(sql, today,today);
	    if(recordt == null){
	    	sql = "select starttime , endtime,jidu "
		    		+ "from njpt_fenqishangbao "
		    		+ "where convert(date,endtime) <= ? order by endtime desc ";
	    	recordt = Db.findFirst(sql,today);
	    	if(recordt == null){
	    		starttime = "2100-01-01";
		    	endtime = "2000-01-01";	
		    	jidu = "夏季";
	    	}else{
	    		starttime = recordt.getStr("starttime");
			    endtime = recordt.getStr("endtime");
			    jidu = recordt.getStr("jidu");
	    	}
	    }else{
	    	starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
		    jidu = recordt.getStr("jidu");
	    }
	    
	    if(StrKit.isBlank(starttime) || starttime.length() != 10){
	    	
	    }
		//=====================================================
		
	    int index=1;
	    int index1=0;
		System.out.println(userPageRecord.getList().size());
		for(Record userRecord:userPageRecord.getList()){
			int page=userPageRecord.getPageNumber();
			int rows=userPageRecord.getPageSize();
			index1=(page-1)*rows+index;
			userRecord.set("xuhao",index1);
			index++;
			String qiye = userRecord.getStr("qyzzjgdm");
			String kdbm = userRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("qyzzjgdm", qiye);
			param1.put("kdbm", kdbm);
			param1.put("page", 1);
			param1.put("rows", 1);
			//Record pzkc = ShouchukeshihuaDAO.findzongkucun(param1);
			Record pzkc = new Record();
			if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
				pzkc.set("dmStock", 0);
			}else{
				pzkc = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
			}
			
			Record kdcr = new Record();
			if(JibenxinxiDao.queryCfZcr(param1).getTotalPage() == 0){
				kdcr.set("dmStock", 0);
			}else{
				kdcr = JibenxinxiDao.queryCfZcr(param1).getList().get(0);
			}
			
			userRecord.setColumns(pzkc);
			userRecord.setColumns(kdcr);
			
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("qyzzjgdm", qiye);
			param2.put("kdbm", kdbm);
			param2.put("starttime", starttime);
			param2.put("endtime", endtime);
			param2.put("page", 1);
			param2.put("rows", 1);
			if("夏季".equals(jidu)){
				param2.put("djpz", "113");
			}else{
				param2.put("djpz", "111");
			}
			Record pzrk = new Record();
			if(JibenxinxiDao.findzongrk(param2).getTotalPage() == 0){
				pzrk.set("sumNW", 0);
			}else{
				
				pzrk = JibenxinxiDao.findzongrk(param2).getList().get(0);
			}
			userRecord.setColumns(pzrk);
		}
		
		Ret ret = Ret.create("list", userPageRecord);
		
		return ret;
	}
	
	/**
	 *获取库点库存情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret querykchz(HashMap<String, Object> map) throws Exception {
		
		HashMap<String,Object> param = map;
		if("请选择".equals(param.get("xian"))){
			param.remove("xian");
		}
		if("请选择".equals(param.get("qyzzjgdm"))){
			param.remove("qyzzjgdm");
		}
		if("请选择".equals(param.get("kdmc"))){
			param.remove("kdmc");
		}
		if("0".equals(param.get("isksh"))){
			param.remove("isksh");
		}
		Page<Record> userPageRecord = ShouchukeshihuaDAO.querykchz(param);
		
		//========================================================
		//获取当前分期
		Date dt = new Date();
		String starttime;
		String endtime;
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String today = matter1.format(dt);
	    String sql = "select starttime , endtime "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
	    Record recordt = Db.findFirst(sql, today,today);
	    if(recordt == null){
	    	starttime = "2100-01-01";
	    	endtime = "2000-01-01";	
	    }else{
	    	starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
	    }
	    
	    System.out.println(starttime);
	    System.out.println(starttime.length());
	    if(StrKit.isBlank(starttime) || starttime.length() != 10){
	    	
	    }
		//=====================================================
		
		
		int index=1;
		for(Record userRecord:userPageRecord.getList()){
			userRecord.set("xuhao",index);
			index++;
			String qiye = userRecord.getStr("qyzzjgdm");
			String kdbm = userRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("qyzzjgdm", qiye);
			param1.put("kdbm", kdbm);
			param1.put("page", 1);
			param1.put("rows",1);
			Record pzkc = new Record();
			if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
				pzkc.set("dmStock", 0);
			}else{
				pzkc = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
			}
			userRecord.setColumns(pzkc);
			
			Record kdcr = new Record();
			if(JibenxinxiDao.queryCfZcr(param1).getTotalPage() == 0){
				kdcr.set("dmStock", 0);
			}else{
				kdcr = JibenxinxiDao.queryCfZcr(param1).getList().get(0);
			}
			userRecord.setColumns(kdcr);
			
			
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("qyzzjgdm", qiye);
			param2.put("kdbm", kdbm);
			param2.put("starttime", starttime);
			param2.put("endtime", endtime);
			param2.put("page", 1);
			param2.put("rows", 1);
			Record pzrk = new Record();
			if(JibenxinxiDao.findzongrk(param2).getTotalPage() == 0){
				pzrk.set("sumNW", 0);
			}else{
				pzrk = JibenxinxiDao.findzongrk(param2).getList().get(0);
			}
			userRecord.setColumns(pzrk);

			String[] lspzbm = {"111","1132","1131"};
			String[] lspz = {"xiaomai","jingdao","xiandao"};
			Record r = new Record();
			for(int i = 0; i < lspzbm.length; i++){
				param1.remove("pzcode");
				param1.put("pzcode", lspzbm[i]);
				
				Record record = new Record();
				if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
					record.set("dmStock", 0);
				}else{
					record = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
				}
				if(lspzbm[i] == "111"){
					r.set("xiaomai", record.get("dmStock"));
				}
				if(lspzbm[i] == "1132"){
					r.set("jingdao", record.get("dmStock"));
				}
				if(lspzbm[i] == "1131"){
					r.set("xiandao", record.get("dmStock"));
				}
				userRecord.setColumns(r);
			}	
		}
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	
	public Ret querycfhj(HashMap<String, Object> map) throws Exception {

		return new Ret().create("list", ShouchukeshihuaDAO.querycfhj(map));
	}
	
	
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
	 * 分页查询企业信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiye(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findQiyexq(param);
	}
	
	
	/**
	 * 分页查询企业信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiyexinxi (HashMap<String, Object> param) throws Exception {
		
		return JibenxinxiDao.findQiyexinxi(param);
	} 
	
	/**
	 * 删除企业信息
	 * @author yzz
	 * @param param
	 * @return
	 */
	public Ret delQiyexinxi (HashMap<String, Object> param) {
		
		Record userinfo=Db.findFirst("select * from fw_user_dep where depid=?",param.get("ID"));
		boolean result3=false,result4=false,result5=false;
		if(userinfo!=null){
			String userid=userinfo.getStr("userid");
			result4 = Db.deleteById("fw_user_dep", "userid", userid);
			result5 = Db.deleteById("fw_user_role", "userid", userid);	
			result3 = Db.deleteById("fw_user", "id", userid);
		}

		boolean result6= Db.deleteById("fw_dep", param.get("ID"));
		boolean result = Db.deleteById("tz_cangfang", "qyzzjgdm", param.get("qyzzjgdm"));
		boolean result1 = Db.deleteById("tz_kudian", "qyzzjgdm", param.get("qyzzjgdm"));
		boolean result2 = Db.deleteById("tz_qiye", "qyzzjgdm", param.get("qyzzjgdm"));
		
		if (result&&result1&&result2&&result3&&result4&&result5&&result6){
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}
	
	public Ret addQiyexinxi (HashMap<String, Object> param) {
		//添加部门信息
		String depid=SqlUtil.uuid();
		Record dept = new Record();
		dept.set("id", depid);
		dept.set("name",param.get("qymc"));
		dept.set("canuse",1);
		dept.set("orderno",7);
		dept.set("childcount",0);
		dept.set("parentid","root");
		dept.set("areaid",Db.findFirst("select * from fw_area where name=?",param.get("xian")).getStr("id"));
		boolean bool1=Db.save("fw_dep", dept);
		//添加用户信息
		Record userinfo = new Record();
		userinfo.set("id", depid);
		userinfo.set("loginname", param.get("ln"));
		userinfo.set("password", param.get("pw"));
		userinfo.set("realname", param.get("qymc"));
		userinfo.set("canuse",1);
		userinfo.set("orderno", 1);
		boolean bool2=Db.save("fw_user", userinfo);
		//添加角色_用户
		Record user_role = new Record();
		user_role.set("id", SqlUtil.uuid());
		user_role.set("userid", depid);
		user_role.set("roleid", Db.findFirst("select * from fw_role where code='qiye'").getStr("id"));
		boolean bool3=Db.save("fw_user_role", user_role);
		//添加用户——部门
		Record user_dept = new Record();
		user_dept.set("id", SqlUtil.uuid());
		user_dept.set("userid", depid);
		user_dept.set("depid", depid);
		boolean bool4=Db.save("fw_user_dep", user_dept);
		
		//企业信息
		Record record = new Record().setColumns(param).set("status", 1).set("ID", depid).remove("ln").remove("pw");
		if(param.get("maplevel")==""){
			record.set("maplevel", null);
		}
		boolean result = Db.save("tz_qiye", "ID", record);
		if (result&&bool1&&bool2&&bool3&&bool4) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("id",depid));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","新增失败"));
			return msg;
		}
	}
	
	public Ret upQiyexinxi (HashMap<String, Object> param) {
		
		//修改部门
		Record dept = new Record();
		
		dept.set("name",param.get("qymc"));
		dept.set("canuse",1);
		dept.set("orderno",7);
		dept.set("childcount",0);
		dept.set("parentid","root");
		dept.set("areaid",Db.findFirst("select * from fw_area where name=?",param.get("xian")).getStr("id"));
		Record deptInfo=   Db.findFirst("select * from fw_dep where id=?",param.get("ID"));
		boolean bool1=false;
		if(deptInfo!=null){
			dept.set("id", deptInfo.getStr("id"));
			bool1=Db.update("fw_dep", dept);
		}else{
			dept.set("id", param.get("ID"));
			bool1=Db.save("fw_dep", dept);
		}
		
		//修改用户
		Record userinfo = new Record();
		//userinfo.set("id", param.get("ID"));
		userinfo.set("loginname", param.get("ln"));
		userinfo.set("password", param.get("pw"));
		userinfo.set("realname", param.get("qymc"));
		userinfo.set("canuse",1);
		userinfo.set("orderno", 1);
		Record userdetail=  Db.findFirst("select * from fw_user where id=?",param.get("ID"));
		boolean bool2=false;
		if(userdetail!=null){
			userinfo.set("id", userdetail.getStr("id"));
			bool2=Db.update("fw_user",userinfo);
		}else{
			userinfo.set("id", param.get("ID"));
			bool2=Db.save("fw_user", userinfo);	
			
		}
		
		//修改企业基本信息
		Record record = new Record().setColumns(param).set("status", 1).remove("ln").remove("pw");
		if(param.get("maplevel")==""){
			record.set("maplevel", null);
		}
		boolean result = Db.update("tz_qiye", "ID", record);
		if (result&&bool1&&bool2) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("id",param.get("ID")));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","修改失败"));
			return msg;
		}
	}
	
	
	/**
	 * 地区表 --- 查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public  Page<Record> findDiqu (HashMap<String, Object> param) throws Exception {	
		 Page<Record>  diqulist=JibenxinxiDao.findDiqu(param);
		return diqulist;
	} 
	
	/**
	 * 地区年度表
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findDiquniandu(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Page<Record>  diqulist=JibenxinxiDao.findDiquniandu(param);
		return diqulist;
	}
	/**
	 * 地区年度排序
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	public Page<Record> findDiquniandupx(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Page<Record>  diqulist=JibenxinxiDao.findDiquniandupx(param);
		return diqulist;
	}
	
	public Ret delDiqu (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_diqu", "areaid", param.get("id"));
		boolean result2 = Db.deleteById("fw_area", "id", param.get("id"));
		
		if (result&&result2) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}
	
	

	public Ret addDiqu (HashMap<String, Object> param) {
			
		/*添加地区信息--fw_area*/
		String  areaid=SqlUtil.uuid();
		Record areainfo=new Record();
		areainfo.set("name", param.get("name"));
		areainfo.set("canuse",1);
		areainfo.set("childcount",1);
		//areainfo.set("orderno",Db.use("njpt").findFirst("select * from fw_area where parentid!='root' ORDER BY orderno desc").getInt("orderno")+1);
		areainfo.set("parentid",Db.use("njpt").findFirst("select * from fw_area where parentid='root' ").getStr("id"));
		boolean  bool2=false;
		if(!param.get("id").equals("")){
			areainfo.set("id", param.get("areaid"));
			bool2=Db.use("njpt").update("fw_area", areainfo);
		}else{
			areainfo.set("id", areaid);
			bool2=Db.use("njpt").save("fw_area", areainfo);
		}
		
		/*添加地区基本信息*/
		param.remove("loginname");param.remove("password");param.remove("name");
		Record record = new Record().setColumns(param);
		if(param.get("orderno")==""){
			record.set("orderno", null);
		}
		if(param.get("maplevel")==""){
			record.set("maplevel", null);
		}
		boolean bool4= false;
		if(!param.get("id").equals("")){
			
			bool4= Db.use("njpt").update("njpt_diqu", record);
		}else{
			record.set("id", SqlUtil.uuid()).set("areaid", areaid);
			bool4= Db.use("njpt").save("njpt_diqu", record);
		}
		
		
		if (bool2&&bool4) {
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
	 * 修改地区年度
	 * @param param
	 * @return
	 */
	public Ret upDiqu (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_diquniandu", "id", record);
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
	 * 库点信息 --- 查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findKudian (HashMap<String, Object> param) throws Exception {
		return JibenxinxiDao.findKudian(param);
	} 
	
	public Ret delKudian (HashMap<String, Object> param) {
		int result = Db.update("delete  from tz_cangfang where qyzzjgdm=? and kdbm=?", param.get("qyzzjgdm"),param.get("kdbm"));
		int result1 = Db.update("delete from tz_kudian where qyzzjgdm=? and kdbm=?", param.get("qyzzjgdm"),param.get("kdbm"));
		if (result==1&&result1==1) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}
	
	public Ret addKudian (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("ID", uuid);
		if(param.get("maplevel")==""){
			record.set("maplevel", null);
		}
		boolean result = Db.save("tz_kudian", "ID", record);
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
	
	public Ret upKudian (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		if(param.get("maplevel")==""){
			record.set("maplevel", null);
		}
		Db.update("update tz_qiye SET status ='1' WHERE qyzzjgdm=?",param.get("qyzzjgdm"));
		boolean result = Db.update("tz_kudian", "ID", record);
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
	 * 仓房信息 查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findCangfang (HashMap<String, Object> param) throws Exception {
		return JibenxinxiDao.findCangfang(param);
	}
	/**
	 * 仓房信息 查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Ret findtianqi (HashMap<String, Object> param) throws Exception {
		Page<Record> userpage=JibenxinxiDao.findtianqi(param);
		Ret ret=Ret.create("list", userpage);
		return ret;
	} 
	
	public Ret delCangfang (HashMap<String, Object> param) {
		int result = Db.update("delete from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=?",param.get("qyzzjgdm"),param.get("kdbm"),param.get("cfbm"));
		if (result==1) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}
	
	public Ret addCangfang (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).remove("ID");
		boolean result = Db.save("tz_cangfang", "ID",record);
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
	
	public Ret upCangfang (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("tz_cangfang", "ID",record);
		Db.update("update tz_qiye SET status ='1' WHERE qyzzjgdm=?",param.get("qyzzjgdm"));
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
	 * 廒间信息  查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findAojian (HashMap<String, Object> param) throws Exception {
		return JibenxinxiDao.findAojian(param);
	} 
	
	public Ret delAojian (HashMap<String, Object> param) {
		boolean result = Db.deleteById("tz_aojian", "ID",param.get("ID"));
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
	
	public Ret addAojian (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("ID", uuid);
		boolean result = Db.save("tz_aojian", "ID",record);
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
	
	public Ret upAojian (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("tz_aojian", "ID",record);
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
	 * 通过qyzzjgdm查询企业下库点
	 * @param qyzzjgdm
	 * @return
	 */
	public List<Record> findsuoshukd(String qyzzjgdm){
		return Db.find("select * from tz_kudian where qyzzjgdm = ?",qyzzjgdm);
	}

	
	
	
	/**
	 * 保存地区年度数据
	 * @param param
	 * @return
	 */
	public Ret addDiquniandu(HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id", uuid);
		boolean result = Db.save("njpt_diquniandu", "ID",record);
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
	 * 删除地区年度数据
	 * @param param
	 * @return
	 */
	public Ret delDiquniandu(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		boolean result = Db.deleteById("njpt_diquniandu", "id", param.get("id"));
		
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
	 * 分页查询储备粮汇总表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<Record>  chubeilianghuizongb(HashMap<String, Object> map)throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.queryswkc(map);//查询所有的
		double cbljh_xm=0,cbljh_jd=0,cbljh_xd=0,cbljh_xj=0;
		for(int i=0;i<PageRecord.getList().size();i++){
			Record qykdRecord= PageRecord.getList().get(i);
			String xzqhdm = qykdRecord.getStr("xzqhdm");
			String qyzzjgdm = qykdRecord.getStr("qyzzjgdm");
			String kdbm = qykdRecord.getStr("kdbm");
			Object xingzhi =map.get("xingzhi");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("xzqhdm", xzqhdm);
			param1.put("qyzzjgdm", qyzzjgdm);
			param1.put("kdbm", kdbm);
			if(xingzhi!=null){
				param1.put("xingzhi", xingzhi);
			}
			Record cangaosl = ChuBeiLiangJHDAO.findcangaosl(param1);//查询仓廒数量
			qykdRecord.setColumns(cangaosl);
			qykdRecord.set("xingzhi", "");
			/*if(null!=map.get("niandu")){
				param1.put("niandu", map.get("niandu"));
			}
			if(null!=map.get("xingzhi")){
				param1.put("xingzhi", map.get("xingzhi"));
			}*/
			/*Record record = ChuBeiLiangJHDAO.findcbljhkudiankc(param1);//统计库点下储备粮计划分品种数量
			qykdRecord.setColumns(record);
			cbljh_xm+=(qykdRecord.getBigDecimal("cbljh_xm").doubleValue());
			cbljh_jd+=(qykdRecord.getBigDecimal("cbljh_jd").doubleValue());
			cbljh_xd+=(qykdRecord.getBigDecimal("cbljh_xd").doubleValue());
			cbljh_xj+=(qykdRecord.getBigDecimal("cbljh_xj").doubleValue());*/
		}
		Ret ret =new Ret().put("list", PageRecord).put("cbljh_xm", cbljh_xm).put("cbljh_jd", cbljh_jd).put("cbljh_xd", cbljh_xd).put("cbljh_xj", cbljh_xj); 
		
		return PageRecord;
	}
 
	@Override
	public Ret fenxingzhi(HashMap<String, Object> map)
			throws Exception {
		Ret ret=new Ret();
		HashMap<String, Object> xz=new HashMap<>();
		xz.put("xingzhibm", "122");
		xz.put("xingzhimc", "地市级储备粮");
		return  ret.put(xz);	
	}
	@Override
	public Ret chubeilianghuizongheji(HashMap<String, Object> map)
			throws Exception {
		Ret ret=new Ret();
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and k.kdmc like ?", "kdmc","%%%s%%");
		p.put("and niandu = ?", "niandu");
		p.put("and c.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select sum(cbljh_xm)/1000 as cbljh_xm,SUM(cbljh_jd)/1000 cbljh_jd,sum(cbljh_xd)/1000 cbljh_xd,sum(cbljh_xj)/1000 cbljh_xj ",
				"from njpt_chubeiliangjihua c LEFT JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm where 1=1  " + s.getSql(), s.getParam().toArray(new Object[0]));
		if(page.getList().size()==0){
			return  ret.put("heji",null);
		}else{
			return  ret.put("heji",page.getList().get(0));	
		}
	}
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryLunHuanShenQing(HashMap<String, Object> map)
			throws Exception {
		
		Page<Record> lastPageRecord=LunHuanGuanLiDAO.queryLunHuanShenQing(map);
		Ret ret = Ret.create("list", lastPageRecord);
		return lastPageRecord;
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> selectCPLCBJH(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChengPinLiangDAO.selectCPLCBJH(map);
		return PageRecord;
	}

	@Override
	public Page<Record> selectZLXQ(HashMap<String, Object> map)
			throws Exception {
		Page<Record> PageRecord = ZhiLiangGLDAO.selectZLXQ(map);
		Ret ret = Ret.create("list", PageRecord);
		return PageRecord;
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> selectlunhuanHZ(HashMap<String, Object> map)
			throws Exception {
		Page<Record> list= LunHuanGuanLiDAO.selectlunhuanHZ(map);
		Ret ret = Ret.create("list", list);
		return list;
	}
	
	
	/**
	 * 获取企业备案信息
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyebeian(HashMap<String, Object> param)
			throws Exception {
		Page<Record> page=RenyuanDao.findQiyebeian(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchengid"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),record.getStr("shenpiren"));
				if(liucheng!=null){
					record.set("jiedian", liucheng.getInt("jiedian"));
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("jd",redb.get("jiedian"));
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
		Page<Record> page=RenyuanDao.findXunzheng(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchenghao"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					
					if(dep!=null){
						record.set("jd",redb.get("jiedian"));
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
	public Ret qyTjSh(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ret qyTjShBtg(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Ret remeberRate(HashMap<String, Object> queryparam) {
		Ret ret=new Ret();
		Record  rate=   Db.findFirst("select * from fw_rate_menu where userid=? and code=? ",UserKit.currentUserInfo().getUser().getString("id"),queryparam.get("code"));
		int flag=0;boolean bool=false;
		if(rate!=null){
			flag=Db.update("update fw_rate_menu set rate=? where  userid=? and code=? ",rate.getInt("rate")+1,UserKit.currentUserInfo().getUser().getString("id"),queryparam.get("code"));
		}else{			
			Record record =new Record();
			record.set("id", SqlUtil.uuid());
			record.set("userid", UserKit.currentUserInfo().getUser().getString("id"));
			record.set("code", queryparam.get("code"));
			record.set("rate", 1);
			bool=Db.save("fw_rate_menu", record);
		}
		if(flag>0||bool){
			ret.put("ret",true);
		}else{
			ret.put("ret",false);
		}
		
		return ret;
	}

	public Ret findFate(HashMap<String, Object> queryparam) throws Exception {
		
		return JibenxinxiDao.findFate(queryparam);
	}

	
	
	

	/**
	 * 药库分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoku (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findYaoku(param);
	}

/**
	 *获取夏粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryxialiangyuce(HashMap<String, Object> map) throws Exception {
		
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxialiangyuce(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 *获取秋粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
 	
	public Ret queryqiuliangyuce(HashMap<String, Object> map) throws Exception {
		
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangyuce(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

/**
	 * 获取储备粮计划列表--企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret shengchenghuizongb(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.shengchenghuizongb(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	/**
	 * 获取储备粮计划审核列表——储备粮管理中心
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cblglzxShenHe(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.cblglzxShenHe(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	/**
	 * 查询企业安全标准化统计表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findanquantj(HashMap<String, Object> param) throws Exception{
		Page<Record> userpage=CangchuglDao.findanquantj(param);
		/*for(Record page:userpage.getList()){
			Record name=Db.findFirst("SELECT a.name FROM fw_area a LEFT JOIN njpt_diqu b ON a.id=b.areaid WHERE b.xzqhdm=?",page.getStr("sb"));
			page.set("name", name.getStr("name"));
			Record qymc=Db.findFirst("SELECT qymc FROM tz_qiye WHERE qyzzjgdm=?",page.getStr("dbdw"));
			page.set("qymc", qymc.getStr("qymc"));
		}*/
		return userpage;
	}


/**
	 * 遍历人口表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryrenkou(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = EmergencyDAO.queryrenkou(map);
		return userPageRecord;
	}

@Override
	public Page<Record> findshenpiliucheng(HashMap<String, Object> param)
			throws Exception {
		//param.put("shenpibumen", UserKit.currentUserInfo().getDep().getString("id"));
		Page<Record> page=RenyuanDao.findshenpiliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			record.set("xingming", user.getStr("realname"));
			if(record.getStr("liuchengtype").equals("0")){
				Record qiye=Db.findFirst("select * from oa_qiyebeian where liuchengid=?",record.getStr("liuchenghao"));
				if(qiye==null){
					record.set("status", "0");
				}else{
					record.set("status", qiye.getStr("status"));
				}
				
			}else if(record.getStr("liuchengtype").equals("1")){
				Record xunzhen=Db.findFirst("select * from oa_xunzhengbeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(xunzhen==null){
					record.set("status", "0");
				}else{
					record.set("status", xunzhen.getStr("status"));
				}
			}else if(record.getStr("liuchengtype").equals("2")){
				Record yaoji=Db.findFirst("select * from oa_yaojibeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(yaoji==null){
					record.set("status", "0");
				}else{
					record.set("status", yaoji.getStr("status"));
				}
				
			}
			
		}
		return page;
	}

/**
 * 应急小组的增删改查
 */
public Page<Record> findxiaozu(HashMap<String, Object> param) throws Exception {
	Page<Record> userpage=CangchuglDao.findxiaozu(param);
	int index=1;
	for(Record record:userpage.getList()){
		record.set("xuhao", index);
		index++;
	}
	return userpage;
}

@Override
@Before(AutoPageInterceptor.class)
public Ret chakanxiangxich(HashMap<String, Object> map) throws Exception {
	List<Record> cangao= ChuBeiLiangJHDAO.findcangaolist(map);//查询仓廒编码
	for(Record record:cangao){
			map.remove("cfbm");
			map.put("cfbm", record.get("cfbm"));
			Record cfpzRecord=new Record();
			cfpzRecord = ChuBeiLiangJHDAO.findcbljhpzkucun(map);
			
			if(cfpzRecord!=null){
				
				record.setColumns(cfpzRecord);
			}
	}
	
	Ret ret = Ret.create("list", new Page<>(cangao, 1, 15, 1, cangao.size()));
	return ret;
}
//市级储备
@Override
public Ret findzongjihua(HashMap<String, Object> queryparam) throws Exception {
	Calendar a=Calendar.getInstance();
	String year= (a.get(Calendar.YEAR)-1)+"";
	String sql = "select  isnull(sum(jhsl)/1000,0) as jhsl,isnull(sum(cbljh_xm)/1000,0) as xm, isnull(sum(cbljh_jd)/1000,0) as jd,isnull(sum(cbljh_xd)/1000,0) as xd "
			+ " from njpt_chubeiliangjihua where niandu='"+year+"' and xingzhi='122'";
	List<Record> list=Db.find(sql);
	Ret ret = Ret.create("list",list);
	return	ret;
}
//市级储备
@Override
public Ret findzongkucun(HashMap<String, Object> queryparam) throws Exception {
	Calendar a=Calendar.getInstance();
	String year= (a.get(Calendar.YEAR)-1)+"";
	String sql = "select b.vGrainSubTypeCode , isnull(sum(b.dmStock),0) as sum from njpt_chubeiliangjihua a inner join kc_CurrentStock b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode where b.vGrainPropertyCode in ('122') and a.niandu='"+year
			+ "' group by b.vGrainSubTypeCode ";
	List<Record> list=Db.find(sql);
	Ret ret = Ret.create("list",list);
	return ret;
}
//县级储备
@Override
public Ret findzongjihuaxian(HashMap<String, Object> queryparam) throws Exception {
	Calendar a=Calendar.getInstance();
	String year= (a.get(Calendar.YEAR)-1)+"";
	String sql = "select  isnull(sum(jhsl)/1000,0) as jhsl,isnull(sum(cbljh_xm)/1000,0) as xm, isnull(sum(cbljh_jd)/1000,0) as jd,isnull(sum(cbljh_xd)/1000,0) as xd "
			+ " from njpt_chubeiliangjihua where niandu='"+year+"' and xingzhi='123'";
	List<Record> list=Db.find(sql);
	Ret ret = Ret.create("list",list);
	return	ret;
}
//县级储备
@Override
public Ret findzongkucunxian(HashMap<String, Object> queryparam) throws Exception {
	Calendar a=Calendar.getInstance();
	String year= (a.get(Calendar.YEAR)-1)+"";
	String sql = "select b.vGrainSubTypeCode , isnull(sum(b.dmStock),0) as sum from njpt_chubeiliangjihua a inner join kc_CurrentStock b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode where b.vGrainPropertyCode in ('123') and a.niandu='"+year
			+ "' group by b.vGrainSubTypeCode ";
	List<Record> list=Db.find(sql);
	Ret ret = Ret.create("list",list);
	return ret;
}

@Override
@Before(AutoPageInterceptor.class)
public Ret cbljhrenwu(HashMap<String, Object> queryparam) throws Exception {
	queryparam.put("xingzhi", "122");
	Page<Record> PageRecord = ChuBeiLiangJHDAO.cbljhrenwuzsg(queryparam);
	Ret ret = Ret.create("list", PageRecord);
	return ret;
}

@Override
public Ret chubeiliangrenwuheji(HashMap<String, Object> queryparam) throws Exception {
	queryparam.put("xingzhi", "122");
	return ChuBeiLiangJHDAO.chubeiliangrenwuheji(queryparam);
}

@Override
public Ret queryxingzhi(HashMap<String, Object> map) throws Exception {
	Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxingzhi(map);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}

@Override
@Before(AutoPageInterceptor.class)
public Ret cblswkc(HashMap<String, Object> queryparam) throws Exception {
	Page<Record> userPageRecord = ChuBeiLiangJHBO.cblswkc(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret cblswkck(HashMap<String, Object> queryparam) throws Exception {
	Page<Record> userPageRecord = ChuBeiLiangJHBO.cblswkck(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret cbljhniandurenwu(HashMap<String, Object> queryparam) throws Exception {
	Page<Record> userPageRecord = ChuBeiLiangJHBO.cbljhniandurenwu(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret cbljhnianduheji(HashMap<String, Object> queryparam) throws Exception {
	List<Record> userPageRecord = ChuBeiLiangJHBO.cbljhnianduheji(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret cbljh(HashMap<String, Object> queryparam) throws Exception {
	Page<Record> userPageRecord = ChuBeiLiangJHBO.cbljh(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
/**
 * 实物库存-点击区域，显示有计划的企业实物库存
 * @param queryparam
 * @return
 * @throws Exception
 */
@Override
public Ret chakankudian(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
/**
 * 实物库存-点击区域，显示有计划的企业实物库存
 * @param queryparam
 * @return
 * @throws Exception
 */
@Override
public Ret chakankudiankc(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudiankc(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
/**
 * 储备粮计划-点击区域，显示有计划的企业实物库存
 * @param queryparam
 * @return
 * @throws Exception
 */
@Override
public Ret ckcbljherjiqiye(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.ckcbljherjiqiye(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
public Ret chakankudian2(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian2(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
public Ret chakankudian2kc(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian2kc(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
public Ret chakanecbljhkd(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakanecbljhkd(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
public Ret chakancangfang(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancangfang(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
public Ret chakancangfangkc(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancangfangkc(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
public Ret chakancbljhcf(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancbljhcf(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
public Ret shiwukucunheji(HashMap<String, Object> queryparam) throws Exception {
	Record userPageRecord = ChuBeiLiangJHBO.shiwukucunheji(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
public Ret shiwukucunhejixinxi(HashMap<String, Object> queryparam) throws Exception {
	Record userPageRecord = ChuBeiLiangJHBO.shiwukucunhejixinxi(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
public Ret cbljhheji(HashMap<String, Object> queryparam) throws Exception {
	Record userPageRecord = ChuBeiLiangJHBO.cbljhheji(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret findcompany(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.findcompany(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
@Before(AutoPageInterceptor.class)
public Ret findcompanyy(HashMap<String, Object> queryparam) throws Exception {
	
	Page<Record> userPageRecord = ChuBeiLiangJHBO.findcompanyy(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
@Before(AutoPageInterceptor.class)
public Ret finderjiqiyekd(HashMap<String, Object> queryparam) throws Exception {
	
	List<Record> userPageRecord = ChuBeiLiangJHBO.finderjiqiyekd(queryparam);
	Ret ret = Ret.create("list", userPageRecord);
	return ret;
	
}
@Override
public Page<Record> findYjck(HashMap<String, Object> param) throws Exception {
	Page<Record> userPageRecord=CangchuglDao.findYjck(param);
	param.put("page", 1);
	param.put("rows", 10000);
 	int index=1;
 	int index1=0;
 	for(Record userRecord:userPageRecord.getList()){
		int page=userPageRecord.getPageNumber();
		int rows=userPageRecord.getPageSize();
		index1=(page-1)*rows+index;
		userRecord.set("xuhao",index1);
		index++;
		List<Record> record=Db.find("select * from xz_yaojixinxi where 1=1 and beianbianhao=?",userRecord.get("xzbah").toString());
		int sum=0;
		for(int i=0;i<record.size();i++){
			sum+=Integer.valueOf(record.get(i).getStr("gg"))*Integer.valueOf(record.get(i).getStr("lysl"));
		}
		userRecord.set("weight", sum);
	}
return userPageRecord;
}

@Override
public Page<Record> selectfeiksh(HashMap<String, Object> param) throws Exception {
	Page<Record> PageRecord = ZhiLiangGLDAO.selectfeiksh(param);
	Ret ret = Ret.create("list", PageRecord);
	return PageRecord;
}
@Override
public Page<Record> kucunliebiao(HashMap<String, Object> param) throws Exception {
	Page<Record> PageRecord = ZhiLiangGLBO.kucunliebiao(param);
	return PageRecord;
}
@Override
public Page<Record> findyjkc(HashMap<String, Object> param) throws Exception {
	Page<Record> userPageRecord=CangchuglDao.findyjkc(param);
	int index=1;
    int index1=0;
	for(Record userRecord:userPageRecord.getList()){
		int page=userPageRecord.getPageNumber();
		index1=(page-1)*10+index;
		userRecord.set("xuhao",index1);
		index++;
	}
	return userPageRecord;
}

@Override
@Before(AutoPageInterceptor.class)
public Page<Record> findkongping(HashMap<String, Object> queryparam) throws Exception {
	// TODO Auto-generated method stub
	return CangchuglDao.findkongping(queryparam);
}

@Override
public Page<Record> findYjrk(HashMap<String, Object> param) throws Exception {
	Page<Record> userpage=CangchuglDao.findYjrk(param);
	for (Record Record : userpage.getList()) {
		int gg=Record.get("gg");
		int sl=Record.get("sl");
		Record.set("weight", gg*sl);
	}
	return userpage;
}


public Page<Record> findfeikshkdcrk(HashMap<String, Object> param) throws Exception {
	return JibenxinxiDao.findfeikshkdcrk(param);
}

@Override
@Before(AutoPageInterceptor.class)
public Page<Record> selectQuYu(HashMap<String, Object> param) throws Exception {
	Page<Record> PageRecord = ZhiLiangGLDAO.selectQuYu(param);
	return PageRecord;
}
@Override
@Before(AutoPageInterceptor.class)
public Ret kucunheji(HashMap<String, Object> param) throws Exception {
	Ret ret = ZhiLiangGLBO.kucunheji(param);
	return ret;
}

@Override
public Page<Record> findxzzy(HashMap<String, Object> queryparam) {
	// TODO Auto-generated method stub
	return null;
}

/**
 * 获取仓房总仓容--统一方法
 */
@Before(AutoPageInterceptor.class)
public Page<Record> queryCfZcr(HashMap<String, Object> param) {
	if(param.get("qyzzjgdm") != null){
		if("请选择".equals(param.get("qyzzjgdm").toString()) || "".equals(param.get("qyzzjgdm").toString())){
			param.remove("qyzzjgdm");
		}
	}
	if(param.get("xian") != null){
		if("请选择".equals(param.get("xian").toString()) || "".equals(param.get("xian").toString())){
			param.remove("xian");
		}
	}
	if(param.get("kdmc") != null){
		if("请选择".equals(param.get("kdmc").toString()) || "".equals(param.get("kdmc").toString())){
			param.remove("kdmc");
		}
	}
	
	return JibenxinxiDao.queryCfZcr(param);
}

/**
 * 获取仓房总库存-统一方法
 */
@Before(AutoPageInterceptor.class)
public Page<Record> queryCfZkc(HashMap<String, Object> param) {
	if(param.get("qyzzjgdm") != null){
		if("请选择".equals(param.get("qyzzjgdm").toString()) || "".equals(param.get("qyzzjgdm").toString())){
			param.remove("qyzzjgdm");
		}
	}
	if(param.get("xian") != null){
		if("请选择".equals(param.get("xian").toString()) || "".equals(param.get("xian").toString())){
			param.remove("xian");
		}
	}
	if(param.get("kdmc") != null){
		if("请选择".equals(param.get("kdmc").toString()) || "".equals(param.get("kdmc").toString())){
			param.remove("kdmc");
		}
	}
	
	return JibenxinxiDao.queryCfZkc(param);
}

/**
 * 获取仓房总库存-统一方法
 */
@Before(AutoPageInterceptor.class)
public Page<Record> findzongrk(HashMap<String, Object> param) {
	if(param.get("qyzzjgdm") != null){
		if("请选择".equals(param.get("qyzzjgdm").toString()) || "".equals(param.get("qyzzjgdm").toString())){
			param.remove("qyzzjgdm");
		}
	}
	if(param.get("xian") != null){
		if("请选择".equals(param.get("xian").toString()) || "".equals(param.get("xian").toString())){
			param.remove("xian");
		}
	}
	if(param.get("kdmc") != null){
		if("请选择".equals(param.get("kdmc").toString()) || "".equals(param.get("kdmc").toString())){
			param.remove("kdmc");
		}
	}
	//获取当前分期
	Date dt = new Date();
	String starttime;
	String endtime;
	String jidu;
    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
    String today = matter1.format(dt);
    String sql = "select starttime , endtime,jidu "
    		+ "from njpt_fenqishangbao "
    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
    Record recordt = Db.findFirst(sql, today,today);
    if(recordt == null){
    	sql = "select starttime , endtime,jidu "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,endtime) <= ? order by endtime desc ";
    	recordt = Db.findFirst(sql,today);
    	if(recordt == null){
    		starttime = "2100-01-01";
	    	endtime = "2000-01-01";	
	    	jidu = "夏季";
    	}else{
    		starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
		    jidu = recordt.getStr("jidu");
    	}
    }else{
    	starttime = recordt.getStr("starttime");
	    endtime = recordt.getStr("endtime");
	    jidu = recordt.getStr("jidu");
    }
	param.put("starttime", starttime);
	param.put("endtime", endtime);
	
	if("夏季".equals(jidu)){
		param.put("djpz", "113");
	}else{
		param.put("djpz", "111");
	}
	
	return JibenxinxiDao.findzongrk(param);
}

@Override
@Before(AutoPageInterceptor.class)
public Page<Record> findlcpw(HashMap<String, Object> map) throws Exception {
	Page<Record> list= LunHuanGuanLiDAO.findlcpw(map);
	return list;
}

@Override
@Before(AutoPageInterceptor.class)
public Ret lcpwheji(HashMap<String, Object> map) throws Exception {
	// TODO Auto-generated method stub
	Record record= LunHuanGuanLiDAO.lcpwheji(map);
	Ret ret=new Ret().put("list", record);
	return ret;
}

@Override
public Ret findallcompany(HashMap<String, Object> map) throws Exception {
	// TODO Auto-generated method stub
	List<Record> reclist= ChuBeiLiangJHBO.findallcompany(map);
	Ret ret=new Ret().put("list", reclist);
	return ret;
}



}
