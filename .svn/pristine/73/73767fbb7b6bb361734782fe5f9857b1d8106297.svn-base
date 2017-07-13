package com.zkzy.njzhpt.bo;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class RenyuanBo {

	
	
	/**
	 * 获取人员通讯录信息
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findRenyuan(HashMap<String, Object> param) throws Exception {		
			return RenyuanDao.findRenyuan(param);
	}


	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findDept(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findDept(param);
	}
	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYjdept(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findYjdept(param);
	}
	



	/**
	 * 保存人员信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret addRenyuan(HashMap<String, Object> param) {
		return RenyuanDao.addRenyuan(param);
	}

	
	
	
	

	/**
	 * 删除人员信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret deleteRenyuan(HashMap<String, Object> param) {
			
		return  Ret.create("ret",Db.deleteById("oa_renyuan", param.get("id")));
	}

	

	/**
	 * 部门表中的粮食局
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret queryLSjAndQy(HashMap<String, Object> param) {
		List<Record>  list=null;
		if(param.get("role").equals("粮食局")){
			list=Db.find("select * from fw_dep  where id not in(select id from tz_qiye) ");	
		}else{
			list=Db.find("select * from fw_dep  where id  in(select id from tz_qiye) ");
		}
		return Ret.create("list",  list);
	}


	/**
	 * 保存工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret saveWorkPlan(HashMap<String, Object> param) {
		return RenyuanDao.saveWorkPlan(param);
	}



	/**
	 * 获取工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public  Page<Record> queryWorkPlan(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.queryWorkPlan(param);
		for(Record record:page.getList()){
			if((record.getStr("starttime").length() >= 10) && (record.getStr("endtime").length() >= 10)){
				if (!record.getStr("starttime").substring(0, 10)
						.equals(record.getStr("endtime").substring(0, 10))) {
					record.set("allDay","true");
				}
			}
		}
		return page;
	}
	

	/**
	 * 删除工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret deleteWorkPlan(HashMap<String, Object> param) {
		
		return  Ret.create("ret", Db.deleteById("oa_workplan", param.get("id")));
	}

	/**
	 * 获取流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findliucheng(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			Record name=Db.findFirst("SELECT a.name from fw_dep a LEFT JOIN fw_user_dep b on a.id=b.depid where 1=1 and b.userid=?",record.getStr("shenpiren"));
			record.set("bumenname", name.getStr("name"));
			record.set("xingming", user.getStr("realname"));
		}
		return page;
	}
	@Before(AutoPageInterceptor.class)
	public Page<Record> findshenpiliucheng(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findshenpiliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			Record name=Db.findFirst("SELECT a.name from fw_dep a LEFT JOIN fw_user_dep b on a.id=b.depid where 1=1 and b.userid=?",record.getStr("shenpiren"));
			
			record.set("xingming", user.getStr("realname"));
			record.set("bumenname", name.getStr("name"));
			if(record.getStr("liuchengtype").equals("0")){
				Record qiye=Db.findFirst("select * from oa_qiyebeian where liuchengid=?",record.getStr("liuchenghao"));
				if(qiye==null){
					record.set("status", "1");
				}else{
					record.set("status", qiye.getStr("status"));
				}
				
			}else if(record.getStr("liuchengtype").equals("1")){
				Record xunzhen=Db.findFirst("select * from oa_xunzhengbeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(xunzhen==null){
					record.set("status", "1");
				}else{
					record.set("status", xunzhen.getStr("status"));
				}
			}else if(record.getStr("liuchengtype").equals("2")){
				Record yaoji=Db.findFirst("select * from oa_yaojibeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(yaoji==null){
					record.set("status", "1");
				}else{
					record.set("status", yaoji.getStr("status"));
				}
				
			}
			
		}
		return page;
	}
	/**
	 * 获取流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxzliucheng(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findxzliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			Record name=Db.findFirst("SELECT a.name from fw_dep a LEFT JOIN fw_user_dep b on a.id=b.depid where 1=1 and b.userid=?",record.getStr("shenpiren"));
			
			record.set("bumenname", name.getStr("name"));
			record.set("xingming", user.getStr("realname"));
		}
		return page;
	}
	/**
	 * 获取轮换申请流程
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findlunhuanlc(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findlunhuanlc(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			Record name=Db.findFirst("SELECT a.name from fw_dep a LEFT JOIN fw_user_dep b on a.id=b.depid where 1=1 and b.userid=?",record.getStr("shenpiren"));
			
			record.set("bumenname", name.getStr("name"));
			record.set("xingming", user.getStr("realname"));
		}
		return page;
	}
	/**
	 * 获取流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findyjliucheng(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findyjliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			record.set("xingming", user.getStr("realname"));
		}
		return page;
	}

	
	/**
	 * 保存流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret saveliucheng(HashMap<String, Object> param) {
		return RenyuanDao.saveliucheng(param);
	}
	/**
	 * 保存截取信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret savejiequxinxi(HashMap<String, Object> param) {
		return RenyuanDao.savejiequxinxi(param);
	}
	/**
	 * 获取流程名称
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret findlc(HashMap<String, Object> param) {
		return RenyuanDao.findlc(param);
	}
	/**
	 * 获取等级名称
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret finddj(HashMap<String, Object> param) {
		return RenyuanDao.finddj(param);
	}
	

	/**
	 * 编辑流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret updateliucheng(HashMap<String, Object> param) {
		return RenyuanDao.updateliucheng(param);
	}


	/**
	 * 删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret delLc(HashMap<String, Object> param) {
		String liuchenghao = param.get("id").toString();
		String type = param.get("type").toString();
		int flag=0;
		//int flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
		if(type.equals("0")){
			Record re=Db.findFirst("SELECT status from oa_qiyebeian where liuchengid=? order by status desc",liuchenghao);
			if(re==null){
				flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
			}else{
				if(re.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM oa_qiyebeian WHERE liuchengid=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}
			
		}else if(type.equals("1")){
			Record re=Db.findFirst("SELECT status from oa_xunzhengbeian where liuchenghao=? order by status desc",liuchenghao);
			if(re==null){
				flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
			}else{
				if(re.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM oa_xunzhengbeian WHERE liuchenghao=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}
			
		}else if(type.equals("2")){
			Record re=Db.findFirst("SELECT status from oa_yaojibeian where liuchenghao=? order by status desc",liuchenghao);
			if(re==null){
				flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
			}else{
				if(re.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM oa_yaojibeian WHERE liuchenghao=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}
			
		}else if(type.equals("4")){
			Record re=Db.findFirst("SELECT status from njpt_lunrushenqingb where liuchenghao=? order by status desc",liuchenghao);
			Record re1=Db.findFirst("SELECT status from njpt_lunhuanshenqingb where liuchenghao=? order by status desc",liuchenghao);
			if(re==null&&re1==null){
				flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
			}else if(re!=null&&re1==null){
				if(re.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM njpt_lunrushenqingb WHERE liuchenghao=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}else if(re==null&&re1!=null){
				if(re1.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM njpt_lunhuanshenqingb WHERE liuchenghao=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}else{
				if(re.getStr("status").equals("1")&&re1.getStr("status").equals("1")){
					flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM njpt_lunrushenqingb WHERE liuchenghao=?",liuchenghao);
					Db.update("DELETE FROM njpt_lunhuanshenqingb WHERE liuchenghao=?",liuchenghao);
				}else{
					flag=Db.update("UPDATE oa_shenpiliucheng SET nowstatus='1' WHERE liuchenghao=?",liuchenghao);
				}
			}
			
		}
		if (flag>0) {
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
	 * 关闭前判断删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret guanbidelLc(HashMap<String, Object> param) {
		String liuchenghao = param.get("id").toString();
		int flag=Db.update("DELETE FROM oa_shenpiliucheng WHERE liuchenghao=?",liuchenghao);
		if (flag>0) {
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
	 * 删除流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret dellc(HashMap<String, Object> param) {
		return Ret.create("ret", Db.deleteById("oa_shenpiliucheng", param.get("id")));
	}


	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findUserDep(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findUserDep(param);
	}
	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findUserDepId(HashMap<String, Object> param) throws Exception {
		Page<Record> record=RenyuanDao.findUserDepId(param);
		List<Record> listrecord = Db.find("SELECT * FROM fw_dep WHERE id not in (SELECT id from tz_qiye) and parentid=?", param.get("deptid"));
		for (Record record2 : listrecord) {
			String sql="SELECT a.*,c.id as deptid ,c.name as deptname FROM fw_user a JOIN fw_user_dep b ON a.id=b.userid LEFT JOIN fw_dep c ON b.depid=c.id WHERE b.depid=?";
			List<Record> list=Db.find(sql,record2.get("id"));
			for (Record record3 : list) {
				record.getList().add(record3);
			}
		}
		
		return record;
	}
	/**
	 * 获取审核企业
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiye(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findQiye(param);
	}
	
	/**
	 * 获取药库点
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaokun(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findYaokun(param);
	}
	
	/**
	 * 获取收储企业
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyexinxi(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findQiyexinxi(param);
	}
	/**
	 * 获取省市名称
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findcity(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findcity(param);
	}
	/**
	 * 获取区域名称
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findquyu(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findquyu(param);
	}
	/**
	 * 获取熏蒸名称
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxunzhengname(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findxunzhengname(param);
	}
	/**
	 * 获取药剂库点名称
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findyaojiname(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findyaojiname(param);
	}
	/**
	 * 保存企业备案
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret addQiyebeian(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addQiyebeian(param));
	}
	/**
	 * 保存夏粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret addXlwuri(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addXlwuri(param));
	}
	/**
	 * 保存秋粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret addQlwuri(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addQlwuri(param));
	}
	
	/**
	 * 获取企业备案编号查重
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findqiyebeianbianhao(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findqiyebeianbianhao(param);
	}
	/**
	 * 获取熏蒸备案编号查重
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxunzhengbeianbianhao(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findxunzhengbeianbianhao(param);
	}
	/**
	 * 获取药剂备案编号查重
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findyaojibeianbianhao(HashMap<String, Object> param) throws Exception {
		return RenyuanDao.findyaojibeianbianhao(param);
	}
	/**
	 * 获取企业备案信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyebeian(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findQiyebeian(param);
		for(Record record:page.getList()){
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
	 * 获取企业信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findqiye(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findqiye(param);
		return page;
	}
	/**
	 * 获取熏蒸备案信息
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findXunzhengbeian(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findXunzheng(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),record.getStr("shenpiren"));
				if(liucheng!=null){
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
	 * 删除企业备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret delqyba(HashMap<String, Object> param) {
		return Ret.create("ret", Db.deleteById("oa_qiyebeian", param.get("id")));
	}

	/**
	 * 删除熏蒸备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret delxzba(HashMap<String, Object> param) {
		Record re=Db.findFirst("SELECT beianbianhao from oa_xunzhengbeian where 1=1 and id= ?",param.get("id"));
		
		Db.update("DELETE from xz_zuoyerenyuan WHERE 1=1 and beianbianhao='"+re.get("beianbianhao")+"'");
		Db.update("DELETE from xz_xunzhendx WHERE 1=1 and beianbianhao='"+re.get("beianbianhao")+"'");
		Db.update("DELETE from xz_yaojixinxi WHERE 1=1 and beianbianhao='"+re.get("beianbianhao")+"'");
		Db.update("DELETE from oa_xunzhengzuoye WHERE 1=1 and beianbianhao='"+re.get("beianbianhao")+"'");
		return Ret.create("ret", Db.deleteById("oa_xunzhengbeian", param.get("id")));
	}
	/**
	 * 删除药剂备案记录
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret delyjba(HashMap<String, Object> param) {
		return Ret.create("ret", Db.deleteById("oa_yaojibeian", param.get("id")));
	}
	
	/**
	 * 续期信息查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> queryxuqi(HashMap<String, Object> map) throws Exception {
		
		if(UserKit.currentUserInfo().getAuth().contains("auth_county")==true){
			map.put("dizhi", UserKit.currentUserInfo().getArea().get("name"));
		}else if(UserKit.currentUserInfo().getRole().get("code").equals("ssqiye")){
			map.put("dizhi", UserKit.currentUserInfo().getArea().get("name"));
			map.put("qiye", UserKit.currentUserInfo().getUser().get("realname"));
		}
		Page<Record> userPageRecord = RenyuanDao.queryxuqi(map);
		return userPageRecord;
	}
	/**
	 * 续期信息查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> queryxuqiql(HashMap<String, Object> map) throws Exception {
		
		if(UserKit.currentUserInfo().getAuth().contains("auth_county")){
			map.put("quming", UserKit.currentUserInfo().getArea().get("name"));
		}else if(UserKit.currentUserInfo().getRole().get("code").equals("ssqiye")){
			map.put("quming", UserKit.currentUserInfo().getArea().get("name"));
			map.put("qiye", UserKit.currentUserInfo().getUser().get("realname"));
		}
		Page<Record> userPageRecord = RenyuanDao.queryxuqiql(map);
		return userPageRecord;
	}
	/**
	 * 获取审批列表
	 * @author yzz
	 * @throws Exception 
	 */
	public Page<Record>  findQyba(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findQyba(param);
		int index=1;
		for(Record record:page.getList()){
			record.set("xuhao", index);
			index++;
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchengid"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),record.getStr("shenpiren"));
				if(liucheng!=null){
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
	 * 获取审批熏蒸列表
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record>  findXzbaa(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findXzbaa(param);
		int index=1;
		for(Record record:page.getList()){
			record.set("xuhao", index);
			index++;
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchenghao"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("jd",redb.get("jiedian"));
						record.set("depname", dep.getStr("name"));
						//record.set("jiedian", liucheng.getInt("jiedian"));
					}
					
				}
			}
			
		}
		return page;
	}
	/**
	 * 获取审批熏蒸列表
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record>  findXzba(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findXzba(param);
		int index=1;
		for(Record record:page.getList()){
			record.set("xuhao", index);
			index++;
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchenghao"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("jd",redb.get("jiedian"));
						record.set("depname", dep.getStr("name"));
						//record.set("jiedian", liucheng.getInt("jiedian"));
					}
					
				}
			}
			
		}
		return page;
	}
	/**
	 * 获取审批药剂列表
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record>  findYjba(HashMap<String, Object> param) throws Exception {
		
		Page<Record> page=RenyuanDao.findYjba(param);
		int index=1;
		for(Record record:page.getList()){
			record.set("xuhao", index);
			index++;
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),UserKit.currentUserInfo().getUser().getString("id"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
					}
					//record.set("jiedian", liucheng.getInt("jiedian"));
				}
			}
			
		}
		return page;
	}
	/**
	 * 获取审批轮换列表
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record>  findLHba(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findLHba(param);
		/*for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),UserKit.currentUserInfo().getUser().getString("id"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
					}
					record.set("jiedian", liucheng.getInt("jiedian"));
				}
			}
			
		}*/
		return page;
	}

	/**
	 * 保存驳回理由
	 * @throws Exception
	 */
	public Ret saveBohui(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update oa_qiyebeian set status=? ,liyou=?,shenpiren=? where id=?",param.get("status")+"0",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
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
	/**
	 * 获取上一步流程信息
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findliuchengxinxi(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findliuchengxinxi(param);
		return page;
	}
	/**
	 * 获取熏蒸备案是的备案编号信息
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findXzbeianxinxi(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findXzbeianxinxi(param);
		return page;
	}
	
	
	public Page<Record> findyjkcxinxi(HashMap<String, Object> param) throws Exception {
 		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		if(UserKit.currentUserInfo().getAuth().contains("auth_manager")){
			
		}else{
			param.put("xzqhdm", re.get("xzqhdm"));
		}
		
		Page<Record> page=RenyuanDao.findyjkcxinxi(param);
		return page;
	}
	/**
	 * 查找作业人员信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findzuoyerenyuan(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findzuoyerenyuan(param);
		return page;
	}
	/**
	 * 查找熏蒸对象信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findxunzhendx(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findxunzhendx(param);
		return page;
	}
	/**
	 * 查找熏蒸药剂信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findxunzhenyj(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findxunzhenyj(param);
		return page;
	}
	
	/**
	 * 保存熏蒸备案
	 * @author yzz
	 * @throws Exception 
	 */

	public Ret addXzBa(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addXzBa(param));
	}
	
	/**
	 * 保存药剂审批信息
	 * @author yzz
	 * @throws Exception 
	 */

	public Ret addYjshenpixinxi(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addYjshenpixinxi(param));
	}
	/**
	 * 保存药剂备案
	 * @author yzz
	 * @throws Exception 
	 */

	public Ret addYjBa(HashMap<String, Object> param) {
		return Ret.create("ret", RenyuanDao.addYjBa(param));
	}

	/**
	 * 保存熏蒸驳回理由
	 * @throws Exception
	 */
	public Ret saveBohuixz(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update oa_xunzhengbeian set status=? ,liyou=?,shenpiren=? where id=?",param.get("status")+"0",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	public Ret saveBohuiyjsq(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update( "update njpt_yaojichuku set status=? ,liyou=? where id=? ",param.get("status")+"0",param.get("liyou"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	
	/**
	 * 保存药剂驳回理由
	 * @throws Exception
	 */
	public Ret saveBohuiyj(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update oa_yaojibeian set status=? ,liyou=?,shenpiren=? where id=?",param.get("status")+"0",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	/**
	 * 保存轮换驳回理由
	 * @throws Exception
	 */
	public Ret saveBohuilunhuan(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update njpt_lunhuanshenqingb set lhsq_zt=? ,liyou=?,shenpiren=? where id=?","10",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	/**
	 * 保存验收申请驳回理由
	 * @throws Exception
	 */
	public Ret saveBohuishenqing(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update njpt_lunhuanshenqingb set lhsq_zt=? ,liyou=?,shenpiren=? where id=?","20",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	
	/**
	 * 保存新闻
	 * @param param
	 * @return
	 */
	public Ret addnews(HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("t_news", record);
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
	 * 修改新闻
	 * @param param
	 * @return
	 */
	public Ret upnews(HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("t_news", "id", record);
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
	 * 删除新闻
	 * @param param
	 * @return
	 */
	public Ret delnews(HashMap<String, Object> param) {
		String id = param.get("id").toString();
		boolean result = Db.deleteById("t_news", id);
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

	@Before(AutoPageInterceptor.class)
	public Page<Record> findtongguo(HashMap<String, Object> param) throws Exception {
		Page<Record> page=RenyuanDao.findtongguo(param);
		/*for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),UserKit.currentUserInfo().getUser().getString("id"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
					}
					record.set("jiedian", liucheng.getInt("jiedian"));
				}
			}
			
		}*/
		return page;
	}
}
