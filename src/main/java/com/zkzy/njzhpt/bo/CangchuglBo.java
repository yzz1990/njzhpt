package com.zkzy.njzhpt.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.jfinal.kit.StrKit;

import org.beetl.ext.fn.ParseInt;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class CangchuglBo {
	
	
	/**
	 * 获取区县信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryDiqu (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.queryDiqu(param);
	}
	
	/**
	 * 获取熏蒸备案企业
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public List<Record> querybaqy(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return CangchuglDao.querybaqy(param);
	}
	
	/**
	 * 获取企业性质信息
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryQiyeXZ (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.queryQiyeXZ(param);
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> querySanwei (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.querySanwei(param);
	}
	
	/**
	 * 获取仓房主要业务
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryZYYW(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryZYYW(queryParam);
	}
	/**
	 * 获取仓房主要业务
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findUsername(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.findUsername(queryParam);
	}
	/**
	 * 获取仓廒类型
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryCALX(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryCALX(queryParam);
	}
	/**
	 * 获取仓廒状态名称
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryCAZTMC(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryCAZTMC(queryParam);
	}
	/**
	 * 获取仓廒使用情况
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryCASYQK(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryCASYQK(queryParam);
	}
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYjckxx(HashMap<String, Object> param) throws Exception {
		Page<Record> userPageRecord=CangchuglDao.findYjckxx(param);
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
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYjckxinxi(HashMap<String, Object> param) throws Exception {
		Page<Record> userPageRecord=CangchuglDao.findYjckxinxi(param);
		
		for (Record record : userPageRecord.getList()) {
			Record re=Db.findFirst("select xzqhdm from njpt_diqu where 1=1 and areaid=?",UserKit.currentUserInfo().getArea().get("id"));
			String yjmc = record.get("yjmc");
			String pp = record.get("pp");
			int  gg= Integer.parseInt(record.get("gg"));
			String  danwei= record.get("dw");
			String  cj= record.get("sccj");
			
			String sql= "select *from njpt_yaojikucunguanli WHERE 1=1 and xzqydm=? and yjmc=? and pp=? and gg=? and danwei=? and cj=?";
			List<Record> listkc = Db.find(sql, re.get("xzqhdm"),yjmc,pp,gg,danwei,cj);
			
			record.get("qyzzjgdm");
			record.get("beianbianhao");
			record.get("yjmc");
			record.get("pp");
			record.get("gg");
			record.get("dw");
			record.get("sccj");
			List<Record> list=Db.find("select * from xz_yaojixinxi where 1=1 and qyzzjgdm=? and beianbianhao=? and yjmc=? and pp=? and gg=? and dw=? and sccj=?",
					record.get("qyzzjgdm"),record.get("beianbianhao"),record.get("yjmc"),record.get("pp"),record.get("gg"),
					record.get("dw"),record.get("sccj"));
			int sum=0;
			for (int i = 0; i < list.size(); i++) {
				sum+=Integer.valueOf(list.get(i).get("lysl"));
			}
			
			if(sum>listkc.get(0).getInt("kcsl")){
				record.set("bijiao", record.get("yjmc")+"申请数量大于库存数量,请驳回!");
			}
		}
		return userPageRecord;
	}
	
	/**
	 * 获取仓廒结构名称
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryCAJGMC(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryCAJGMC(queryParam);
	}
	

	/**
	 * 获取企业经营类型
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryJYLX(HashMap<String, Object> queryParam) throws Exception {
	
		return CangchuglDao.queryJYLX(queryParam);
	}
	/**
	 * 获取企业经营类型
	 * @author yzz
	 * @throws Exception 
	 */
	public Ret queryjylx(String param) {
		String name= param;
		System.out.println(name);
		List<Record> userPageRecord=new ArrayList<Record>();
		HashMap<String, Object> param1 = new HashMap<>();
		if(name.contains(",")){
			String mc[]=name.split(",");
			for(int i=0;i<mc.length;i++){
				param1.put("name", mc[i]);
				Record record = CangchuglDao.findjylx(param1);
				userPageRecord.add(record);
			}
		}else{
			param1.put("name", name);
			Record record = CangchuglDao.findjylx(param1);
			userPageRecord.add(record);
		}
		
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	
	}
	/**
	 * 查视频
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findShipin(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findShipin(param);
	}
	/**
	 * 查放心粮油信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findFangxinly(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findFangxinly(param);
	}
	/**
	 * 查应急供应点
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYingjidian(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findYingjidian(param);
	}

	/**
	 * 查应急加工点
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYingjijiagongdian(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findYingjijiagongdian(param);
	}
	
	/**
	 * 查用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findUserxinxi(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findUserxinxi(param);
	}
	
	/**
	 * 查方法操作
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findfangfacaozuo(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findfangfacaozuo(param);
	}
	
	/**
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findkongping(HashMap<String, Object> queryparam) throws Exception {
		// TODO Auto-generated method stub
		return CangchuglDao.findkongping(queryparam);
	}
	
	
	/**
	 * 查找首页使用频率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findindexweihu(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findindexweihu(param);
	}
	/**
	 * 查找页面方法的使用
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findcaidanfangfaweihu(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findcaidanfangfaweihu(param);
	}
	
	/**
	 * 查找企业备案
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> querybeian(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.querybeian(param);
	}
	
	/**
	 *企业名称下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryKudian
	(HashMap<String, Object> map) throws Exception {
		
		return  CangchuglDao.queryKudian(map);
	}
	/**
	 * 视频 删
	 */
	public Ret delShipin(HashMap<String, Object> param) {
		boolean result = Db.deleteById("sp_ShiPing", "id", param.get("id"));
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
	 * 放心粮油 删
	 */
	public Ret delFangxinliangyou(HashMap<String, Object> param) {
		boolean result = Db.deleteById("tz_kudian", "ID", param.get("ID"));
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
	 * 方法操作删 删
	 */
	public Ret delfangfacaozuo(HashMap<String, Object> param) {
		boolean result = Db.deleteById("fw_caozuo", "id", param.get("id"));
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
	 * 方法操作删 删
	 */
	public Ret qingkongfacaozuo(HashMap<String, Object> param) {
		int result = Db.update("DELETE FROM fw_caozuo WHERE 1=1");
		if (result>0) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","清空成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","清空失败"));
			return msg;
		}
	}
	
	
	/**
	 * 模块使用信息 删
	 */
	public Ret delIndexweihu(HashMap<String, Object> param) {
		boolean result = Db.deleteById("fw_rate_menu", "id", param.get("id"));
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
	 * 菜单方法模块使用信息 删
	 */
	public Ret delCaidanweihu(HashMap<String, Object> param) {
		boolean result = Db.deleteById("fw_caidan_fangfa", "id", param.get("id"));
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
	 * 模块使用信息 删
	 */
	public Ret delchengpinliangCBJH(HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_chengpinliangjihuab", "id", param.get("id"));
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
	 * 视频 增
	 * @param param
	 * @return
	 */
	public Ret addShipin(HashMap<String, Object> param) {
		
		Record record = new Record().setColumns(param).remove("id");
		
		boolean result = Db.save("sp_ShiPing", "id", record);
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
	 * 模块 增
	 * @param param
	 * @return
	 */
	public Ret addIndexweihu(HashMap<String, Object> param) {
		
		Record record = new Record().setColumns(param).remove("id");
		record.set("id", SqlUtil.uuid());
		boolean result = Db.save("fw_rate_menu", record);
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
	 * 菜单方法 增
	 * @param param
	 * @return
	 */
	public Ret addCaidanweihu(HashMap<String, Object> param) {
		
		Record record = new Record().setColumns(param).remove("id");
		record.set("id", SqlUtil.uuid());
		boolean result = Db.save("fw_caidan_fangfa", record);
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
	 * 应急储备计划 增
	 * @param param
	 * @return
	 */
	public Ret addchenpinliangCBJH(HashMap<String, Object> param) {
		
		Record record = new Record().setColumns(param).remove("id");
		record.set("id", SqlUtil.uuid());
		boolean result = Db.save("njpt_chengpinliangjihuab", record);
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
	 * 增加cgx
	 * @param param
	 * @return
	 */
	public Ret addcgx(HashMap<String, Object> param) {
		
		Record record = new Record().setColumns(param).remove("cgx_id");
		
		boolean result = Db.save("oa_MailCgx", "cgx_id", record);
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
	 * 视频改
	 * @param param
	 * @return
	 */
	public Ret upShipin(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("sp_ShiPing", "id", record);
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
	 * 密码改
	 * @param param
	 * @return
	 */
	public Ret upmima(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("fw_user", "id", record);
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
	 * 模块信息改
	 * @param param
	 * @return
	 */
	public Ret upIndexweihu(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("fw_rate_menu", "id", record);
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
	 * 菜单方法使用信息改
	 * @param param
	 * @return
	 */
	public Ret upCaidanweihu(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("fw_caidan_fangfa", "id", record);
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
	 * 模块信息改
	 * @param param
	 * @return
	 */
	public Ret upchenpinliangCBJH(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_chengpinliangjihuab", "id", record);
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
	 * 邮件收件
	 */
	public Page<Record> findshoujian(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findshoujian(param);
	}
	/**
	 * 邮件发件
	 */
	public Page<Record> findfajian(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findfajian(param);
	}
	/**
	 * 邮件草稿
	 */
	public Page<Record> findcaogao(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findcaogao(param);
	}
	/**
	 * 邮件垃圾
	 */
	public Page<Record> findlaji(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findlaji(param);
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
	public Ret addxiaozu(HashMap<String, Object> param) {
			
			Record record = new Record().setColumns(param).remove("id");
			
			boolean result = Db.save("njpt_yingjixiaozu", "id", record);
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
	public Ret delxiaozu(HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yingjixiaozu", "id", param.get("id"));
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
	public Ret upxiaozu(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yingjixiaozu", "id", record);
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
	 * 数据库表t_property的增删改查
	 */
	public Page<Record> findprop(HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findprop(param);
	}
	public Ret addprop(HashMap<String, Object> param) {
			
			Record record = new Record().setColumns(param).remove("id");
			
			boolean result = Db.save("t_Property", "id", record);
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
	public Ret delprop(HashMap<String, Object> param) {
		boolean result = Db.deleteById("t_Property", "id", param.get("id"));
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
	public Ret upprop(HashMap<String, Object> param){ 
		Record record = new Record().setColumns(param);
		boolean result = Db.update("t_Property", "id", record);
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
	 * 药库分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoku (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findYaoku(param);
	}
	
	public Ret delYaoku (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yaokuguanli", "id", param.get("id"));
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
	
	public Ret addYaoku (HashMap<String, Object> param) {
		/*String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_yaokuguanli", "id", record);
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","新增成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","新增失败"));
			return msg;
		}*/
		Record record = new Record().setColumns(param).remove("id");
		
		boolean result = Db.save("njpt_yaokuguanli", "id", record);
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
	
	public Ret upYaoku (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaokuguanli", "id", record);
		String sql="UPDATE njpt_yaojikucunguanli SET kdbm='"+param.get("cfkudian")+"' WHERE 1=1 and xzqydm=? ";
		Db.update(sql,param.get("xzqydm"));
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
	 * 企业备案分页
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiyebeian (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findQiyebeian(param);
	}
	
	/**
	 * 查找药剂审批
	 * @param queryParam
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxjsp(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return CangchuglDao.findxjsp(param);
	}
	
	public Page<Record> findqyba (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findqyba(param);
	}
	
	public Ret delQiyebeian (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_qiyebeian", "id", param.get("id"));
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
	
	public Ret addQiyebeian (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_qiyebeian", "id", record);
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
	
	public Ret upQiyebeian (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_qiyebeian", "id", record);
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
	 * 出入库分页
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	public Page<Record> findChuruku (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findChuruku(param);
	}
	
	public Ret delChuruku (HashMap<String, Object> param) {
		boolean result = Db.deleteById("crk_Purchase", "id", param.get("id"));
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
	
	public Ret addChuruku (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("crk_Purchase", "id", record);
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
	
	public Ret upChuruku (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("crk_Purchase", "id", record);
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
	 * 分页查询粮库作业信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findlkzuoyexinxi (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findlkzuoyexinxi(param);
	}
	
	public Ret dellkzuoyexinxi (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_zuoyehuizong", "id", param.get("id"));
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
	
	public Ret addlkzuoyexinxi (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_zuoyehuizong", "id", record);
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
	
	public Ret uplkzuoyexinxi (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_zuoyehuizong", "id", record);
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
	 * 通风分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findTongfeng (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findTongfeng(param);
	}
	
	public Ret delTongfeng (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_tongfengxinxi", "id", param.get("id"));
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
	
	public Ret addTongfeng (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_tongfengxinxi", "id", record);
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
	
	public Ret upTongfeng (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_tongfengxinxi", "id", record);
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
	 * 熏蒸分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findXunzheng (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findXunzheng(param);
	}
	
	public Ret delXunzheng  (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_xunzhengxinxi", "id", param.get("id"));
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
	
	public Ret addXunzheng  (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_xunzhengxinxi", "id", record);
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
	
	public Ret upXunzheng  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_xunzhengxinxi", "id", record);
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
	 * 冷却分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findLengque (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findLengque(param);
	}
	
	public Ret delLengque  (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_lengquexinxi", "id", param.get("id"));
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
	
	public Ret addLengque  (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_lengquexinxi", "id", record);
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
	
	public Ret upLengque  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_lengquexinxi", "id", record);
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
	 * 实时粮情分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findShisliangq (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findShisliangq(param);
	}
	
	public Ret delShisliangq  (HashMap<String, Object> param) {
		boolean result = Db.deleteById("lq_WareHouseMeasure", "id", param.get("id"));
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
	
	public Ret addShisliangq  (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("lq_WareHouseMeasure", "id", record);
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
	
	public Ret upShisliangq  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("lq_WareHouseMeasure", "id", record);
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
	 * 粮食质量分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findLiangshizl (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findLiangshizl(param);
	}
	
	public Ret delLiangshizl (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_liangshizhiliang", "id", param.get("id"));
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
	
	public Ret addLiangshizl (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_liangshizhiliang", "id", record);
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
	
	public Ret upLiangshizl  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_liangshizhiliang", "id", record);
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
	 * 药剂追踪分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYaojizz (HashMap<String, Object> param) throws Exception {
		return CangchuglDao.findYaojizz(param);
	}
	
	public Ret delYaojizz (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yaojizhuizong", "id", param.get("id"));
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
	
	public Ret addYaojizz (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id",uuid);
		boolean result = Db.save("njpt_yaojizhuizong", "id", record);
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
	
	public Ret upYaojizz  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaojizhuizong", "id", record);
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
	 * 查询企业申请备案信息
	 * @param qiyeid
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findbeianqiye(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findbeianqiye(param);
	}
	
	/**
	 * 查询企业安全标准化统计表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findanquantj(HashMap<String, Object> param) throws Exception{
		/*for(Record page:userpage.getList()){
			Record name=Db.findFirst("SELECT a.name FROM fw_area a LEFT JOIN njpt_diqu b ON a.id=b.areaid WHERE b.xzqhdm=?",page.getStr("sb"));
			page.set("name", name.getStr("name"));
			Record qymc=Db.findFirst("SELECT qymc FROM tz_qiye WHERE qyzzjgdm=?",page.getStr("dbdw"));
			page.set("qymc", qymc.getStr("qymc"));
		}*/
		return CangchuglDao.findanquantj(param);
	}

	/**
	 * 安全生产等级
	 * @param param
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryanquandj(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.queryanquandj(param);
	}

	/**
	 * 安全生产情况
	 * @param param
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryanquanqk(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.queryanquanqk(param);
	}
	
	public Ret delanquantj (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_anquanshengchan", "id", param.get("id"));
		if (result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message", "删除成功"));
			return msg;
		} else {
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message", "删除失败"));
			return msg;
		}

	}

	public Ret addanquantj (HashMap<String, Object> param) {
		List<Record> rec = Db.find("select * from njpt_anquanshengchan where qiyeId = ?",param.get("qiyeId") );
		if (rec.size() > 0){
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","该企业安全生产自评信息已添加，不能重复添加！"));
			return msg;
		}else {
			Record record = new Record().setColumns(param).remove("id").remove("quyu");
			boolean result = Db.save("njpt_anquanshengchan", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message", "新增成功"));
				return msg;
			} else {
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message", "新增失败"));
				return msg;
			}
		}
	}
	
	public Ret upanquantj  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("quyu");
		boolean result = Db.update("njpt_anquanshengchan", "id", record);
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
	 * 分页查询安全教育视频
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findzhisjijg(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findzhisjijg(param);
	}
	
	public Ret delzhisjijg (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_anquanjysp", "id", param.get("id"));
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
	
	public Ret addzhisjijg (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_anquanjysp", "id", record);
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
	
	public Ret upzhisjijg  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_anquanjysp", "id", record);
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
	 * 分页查询安全教育试题
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findjiaoyutm(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findjiaoyutm(param);
	}
	
	public Ret deljiaoyutm (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_jiaoyutiku", "id", param.get("id"));
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
	
	public Ret addjiaoyutm (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_jiaoyutiku", "id", record);
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
	
	public Ret upjiaoyutm  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_jiaoyutiku", "id", record);
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
	 * 获得随机字符,判断账号不重复
	 * 
	 * @param length
	 * @return
	 */
	public String getRandomString(int length) {
		String str = "0123456789abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; ++i) {
				int number = random.nextInt(str.length());
				sb.append(str.charAt(number));
			
		}
			return sb.toString();
	}
	
	
	/**
	 * 分页查询企业信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiye(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findQiye(param);
	}
	/**
	 * 分页查找药剂库存
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findyjkc(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return CangchuglDao.findyjkc(param);
	}

	
	/**
	 * 分页查询药剂库存
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYjkucun(HashMap<String, Object> param) throws Exception{
			return CangchuglDao.findYjkucun(param);
	}
	
	public Ret delYjkucun (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yaojikucunguanli", "id", param.get("id"));
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
	
	public Ret addYjkucun (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_yaojikucunguanli", "id", record);
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
	 * 增加药剂空瓶
	 * @param param
	 * @return
	 */
	public Ret addkongping(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		Record record = new Record().setColumns(param).remove("id");
		String uuid = SqlUtil.uuid();
		record.set("id", uuid);
		boolean result = Db.save("njpt_yaojikongping", "id", record);
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
	
	public Ret upYjkucun  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaojikucunguanli", "id", record);
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
	
	//查询药剂入库数量
	public List<Record> findrksl(){
		return Db.find("select sl from njpt_yaojiruku");
	}
	
	//查询药剂出库数量
	public List<Record> findcksl(){
		return Db.find("select lysl from njpt_yaojichuku");
	}
	
	//查询药剂损耗数量
	public List<Record> findshsl(){
		return Db.find("select bssl from njpt_yaojikucunguanli");
	}
	
	
	/**
	 * 分页查询药剂台账
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYjtaiz(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findYjtaiz(param);
	}
	
	public Ret addYjtaiz (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_yjtaizhang", "id", record);
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
	
	public Ret delYjtaiz (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yjtaizhang", "id", param.get("id"));
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
	
	
	public Ret upYjtaiz  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yjtaizhang", "id", record);
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
	 * 查询药剂出库
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYjck(HashMap<String, Object> param) throws Exception{
		Page<Record> userPageRecord=CangchuglDao.findYjck(param);
		 	int index=1;
		 	int index1=0;
			for(Record userRecord:userPageRecord.getList()){
				int page=userPageRecord.getPageNumber();
				int rows=userPageRecord.getPageSize();
				index1=(page-1)*rows+index;
				userRecord.set("xuhao",index1);
				index++;
			}
		return userPageRecord;
	}
	
	public Ret addYjck (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("xzqydm", re.get("xzqhdm"));
		Record record = new Record().setColumns(param).set("id", uuid).remove("cftable_length");
		boolean result = Db.save("njpt_yaojichuku", "id", record);
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
	
	public Ret delYjck (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yaojichuku", "id", param.get("id"));
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
	
	
	public Ret upYjck  (HashMap<String, Object> param) throws Exception {
		if(param.get("status").equals("0")){
			String sql2="select a.* ,b.qymc from njpt_yaojichuku a LEFT JOIN tz_qiye b on a.lydw=b.ID where 1=1 and a.id=? ";
			Record list=Db.findFirst(sql2, param.get("id"));
			if(list!=null){
					HashMap<String, Object> param1=new HashMap<String, Object>();
					param1.put("page", "1");
					param1.put("rows", "1000");
					List<Record> records=Db.find("select *from xz_yaojixinxi where 1=1 and beianbianhao=?",list.get("xzbah"));
					for(int j=0;j<records.size();j++){
						param1.put("yjmc", records.get(j).get("yjmc"));
						param1.put("pp", records.get(j).get("pp"));
						param1.put("gg", records.get(j).get("gg"));
						param1.put("danwei", records.get(j).get("dw"));
						param1.put("cj", records.get(j).get("sccj"));
						Page<Record> page=CangchuglDao.findYjkucun(param1);
						if(page.getList().size()>0){
							for (Record record : page.getList()) {
								int kcsl=record.getInt("kcsl");
								int kcsl2=kcsl-Integer.valueOf(records.get(j).get("lysl"));
								Db.update("UPDATE njpt_yaojikucunguanli SET kcsl='"+kcsl2+"' where id='"+record.get("id")+"'");
							}
						}
					}
			}
		}else if(param.get("status").equals("10")){
			param.put("status", 1);
		}
		Record record = new Record().setColumns(param).remove("cftable_length");
		boolean result = Db.update("njpt_yaojichuku", "id", record);
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
	 * 分页查询药剂入库
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYjrk(HashMap<String, Object> param) throws Exception{
		Page<Record> userpage=CangchuglDao.findYjrk(param);
		for (Record Record : userpage.getList()) {
			int gg=Record.get("gg");
			int sl=Record.get("sl");
			Record.set("weight", gg*sl);
		}
		return userpage;
	}
	  
	public Ret addYjrk (HashMap<String, Object> param) throws Exception {
					HashMap<String, Object> param1=new HashMap<String, Object>();
					HashMap<String, Object> param2=new HashMap<String, Object>();
					param1.put("page", "1");
					param1.put("rows", "1000");
					param1.put("yjmc", param.get("yjmc"));
					param1.put("pp", param.get("pp"));
					param1.put("gg", param.get("gg"));
					param1.put("cj", param.get("sccj"));
					param1.put("xzqydm", param.get("xzqydm"));
					param1.put("kdbm", param.get("kdbm"));
					Page<Record> page=CangchuglDao.findYjkucun(param1);
						if(page.getList().size()>0){
							for (Record record : page.getList()) {
								int kcsl=record.getInt("kcsl");
								int kcsl2=kcsl+ Integer.valueOf((String) param.get("sl"));
								Db.update("UPDATE njpt_yaojikucunguanli SET kcsl=? where id=?",kcsl2,record.get("id"));
							}
						}else{
							param2.put("xzqydm", param.get("xzqydm"));
							param2.put("kdbm", param.get("kdbm"));
							param2.put("yjmc", param.get("yjmc"));
							param2.put("pp", param.get("pp"));
							param2.put("gg", param.get("gg"));
							param2.put("cj", param.get("sccj"));
							param2.put("kcsl", param.get("sl"));
							param2.put("scrq", param.get("yxqz"));
							param2.put("gjsj", param.get("rksj"));
							param2.put("bgy", param.get("gly"));
							param2.put("danwei", param.get("dw"));
							
							Record record = new Record().setColumns(param2).remove("id");
							 Db.save("njpt_yaojikucunguanli", "id", record);
						}
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id", uuid);
		boolean result = Db.save("njpt_yaojiruku", "id", record);
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
	
	public Ret upYjrk  (HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> param1=new HashMap<String, Object>();
		HashMap<String, Object> param2=new HashMap<String, Object>();
		param1.put("page", "1");
		param1.put("rows", "1000");
		param1.put("yjmc", param.get("yjmc"));
		param1.put("pp", param.get("pp"));
		param1.put("gg", param.get("gg"));
		param1.put("cj", param.get("sccj"));
		param1.put("xzqydm", param.get("xzqydm"));
		param1.put("kdbm", param.get("kdbm"));
		Page<Record> page=CangchuglDao.findYjkucun(param1);
			if(page.getList().size()>0){
				for (Record record : page.getList()) {
					int kcsl=record.getInt("kcsl");
					int kcsl2=kcsl+ Integer.valueOf((String) param.get("sl"));
					Db.update("UPDATE njpt_yaojikucunguanli SET kcsl=? where id=?",kcsl2,record.get("id"));
				}
			}else{
				param2.put("xzqydm", param.get("xzqydm"));
				param2.put("kdbm", param.get("kdbm"));
				param2.put("yjmc", param.get("yjmc"));
				param2.put("pp", param.get("pp"));
				param2.put("gg", param.get("gg"));
				param2.put("cj", param.get("sccj"));
				param2.put("kcsl", param.get("sl"));
				param2.put("scrq", param.get("yxqz"));
				param2.put("gjsj", param.get("rksj"));
				param2.put("bgy", param.get("gly"));
				param2.put("danwei", param.get("dw"));
				
				Record record = new Record().setColumns(param2).remove("id");
				 Db.save("njpt_yaojikucunguanli", "id", record);
			}
		
		
		
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaojiruku", "id", record);
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
	
	public Ret delYjrk (HashMap<String, Object> param) throws Exception {
		Record record=Db.findFirst("SELECT *from njpt_yaojiruku where 1=1 and id=? ",param.get("id"));
		HashMap<String, Object> param1=new HashMap<String, Object>();
		param1.put("page", "1");
		param1.put("rows", "1000");
		param1.put("yjmc", record.get("yjmc"));
		param1.put("pp", record.get("pp"));
		param1.put("gg", record.get("gg"));
		param1.put("cj", record.get("sccj"));
		param1.put("xzqydm", record.get("xzqydm"));
		param1.put("kdbm", record.get("kdbm"));
		Page<Record> page=CangchuglDao.findYjkucun(param1);
		if(page.getList().size()>0){
			for (Record re : page.getList()) {
				int kcsl=re.getInt("kcsl");
				int kcsl2=kcsl- record.getInt("sl");
				Db.update("UPDATE njpt_yaojikucunguanli SET kcsl=? where id=?",kcsl2,re.get("id"));
			}
		}
		
		boolean result = Db.deleteById("njpt_yaojiruku", "id", param.get("id"));
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
	 * 邮件发送
	 */
	public Ret sendemail (HashMap<String, Object> param) {
		String sjr=(String) param.get("mailInfo.sjrname");
		String sjrname[]=sjr.split(",");
		String sjri=(String) param.get("mailInfo.sjrid");
		String sjrid[]=sjri.split(",");
		boolean result=true;
		Record record1 = new Record().setColumns(param).remove("id");
		Db.save("oa_Mailinfyj", "id", record1);
		for (int i = 0; i < sjrname.length; i++) {
			Record record = new Record().setColumns(param).remove("id").set("mailInfo.sjrname", sjrname[i]).set("mailInfo.sjrid", sjrid[i]);
			result = result&&Db.save("oa_Mailinfo", "id", record);
		}
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
	 * 邮件保存至草稿箱
	 */
	public Ret addemail (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("oa_Mailinfo", "id", record);
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
	 * 邮件彻底删除
	 */
	public Ret delemail (HashMap<String, Object> param) {
		
		boolean result = Db.deleteById("oa_Mailinfyj", "id", param.get("id"));
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
	 * 收件箱邮件彻底删除
	 */
	public Ret delemails (HashMap<String, Object> param) {
		
		boolean result = Db.deleteById("oa_Mailinfo", "id", param.get("id"));
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
	 * 邮件删除
	 */
	public Ret upemail (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("oa_Mailinfo", "id", record);
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
	 * 邮件恢复
	 */
	public Ret huifuemail (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("oa_Mailinfo", "id", record);
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
	 * 药剂损耗 --- 查删增改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYjsh(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findYjsh(param);
	}
	/**
	 * 新增药剂损耗
	 * @param param
	 * @return
	 */
	@Before(Tx.class)
	public Ret addYjsh (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Record record = new Record().setColumns(param).set("id", uuid).set("state", 1);
		boolean result = Db.save("njpt_yaojish", "id", record);
		String shuliang = param.get("shuliang").toString();
		String kdbm = param.get("ykmc").toString();
		String yjmc = param.get("yjmc").toString();
		String yjpp = param.get("yjpp").toString();
		String gg = param.get("gg").toString();
		String danwei = param.get("danwei").toString();
		String cj = param.get("cj").toString();
		float sl = Float.parseFloat(shuliang);
		String sql = "update njpt_yaojikucunguanli set kcsl = kcsl - ? "
				+ "where kdbm = ? and yjmc = ? and pp = ? and gg = ? "
				+ "and danwei = ? and cj = ? ";
		int num = Db.update(sql,sl,kdbm,yjmc,yjpp,gg,danwei,cj);
		if (result && num > 0) {
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
	 * 新增熏蒸作业
	 * @param param
	 * @return
	 */
	@Before(Tx.class)
	public Ret addxzzy(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String xzqhdm = param.get("xzqhdm").toString();
		String qyzzjgdm = param.get("qyzzjgdm").toString();
		String kdbm = param.get("kdbm").toString();
		String beianbianhao = param.get("beianbianhao").toString();
		String fuzeren = param.get("fuzeren").toString();
		String canyuren = param.get("canyuren").toString();
		String xunzhengriqi = param.get("xunzhengriqi").toString();
		String cfs = param.get("cfs").toString();
		String yjmcs = param.get("yjmcs").toString();
		String yjsls = param.get("yjsls").toString();
		String lssls = param.get("lssls").toString();
		String xgs = param.get("xgs").toString();
		//保存熏蒸id
		String xzid = SqlUtil.uuid();
		
		String[] scf = cfs.split(",");
		String[] syjmc = yjmcs.split(",");
		String[] syjsl = yjsls.split(",");
		String[] slssl = lssls.split(",");
		String[] sxg = xgs.split(",");
		
		boolean result = true;
		for(int i = 0; i < scf.length; i++){
			String id = SqlUtil.uuid();
			Record record = new Record();
			record.set("id", id);
			record.set("xzqhdm", xzqhdm);
			record.set("qyzzjgdm", qyzzjgdm);
			record.set("beianbianhao", beianbianhao);
			record.set("fuzeren", fuzeren);
			record.set("canyuren", canyuren);
			record.set("xunzhengriqi", xunzhengriqi);
			record.set("canghao", scf[i]);
			record.set("yjmc", syjmc[i]);
			record.set("yjsl", syjsl[i]);
			record.set("lssl", slssl[i]);
			record.set("xiaoguo", sxg[i]);
			record.set("xzid", xzid);
			record.set("kdbm",kdbm);
			boolean r = Db.save("oa_xunzhengzuoye", record);
			result = result && r;
		}
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

	
	@Before(Tx.class)
	public Ret delYjsh (HashMap<String, Object> param) {
		String id = param.get("id").toString();
		String sql = "update njpt_yaojikucunguanli set kcsl = kcsl + (select shuliang from "
				+ "njpt_yaojish where id = ? ) ";
		int num = Db.update(sql,id);
		boolean result = Db.deleteById("njpt_yaojish", "id", id);
		if (num > 0 && result) {
			Ret msg = Ret.create("ret", "true");
			msg.put(Ret.create("message","删除成功"));
			return msg;
		}else{
			Ret msg = Ret.create("ret", "false");
			msg.put(Ret.create("message","删除失败"));
			return msg;
		}
	}
	
	
	public Ret upYjsh  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaojisunhao", "id", record);
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
	 * 分页查询仓储设备
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findCcsb(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findCcsb(param);
	}
	
	public Ret addCcsb (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_cangchushebei", "id", record);
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
	
	public Ret delCcsb (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_cangchushebei", "id", param.get("id"));
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
	
	
	public Ret upCcsb  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_cangchushebei", "id", record);
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
	 * 分页查询专职保管
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findZzbg(HashMap<String, Object> param) throws Exception{
		Page<Record> userpage=CangchuglDao.findZzbg(param);
		int index=1;
		for(Record record:userpage.getList()){
			record.set("xuhao", index);
			index++;
		}
		return userpage;
	}
	
	public Ret addZzbg (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_zhuanzhibaoguian", "id", record);
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
	
	public Ret delZzbg (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_zhuanzhibaoguian", "id", param.get("id"));
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
	
	
	public Ret upZzbg  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_zhuanzhibaoguian", "id", record);
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
	 * 分页查询库区情况
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findKqqk(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findKqqk(param);
	}
	
	public Ret addkuqqk (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param).remove("id");
		boolean result = Db.save("njpt_kuquqingkuang", "id", record);
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
	
	public Ret delkuqqk (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_kuquqingkuang", "id", param.get("id"));
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
	
	
	public Ret upkuqqk  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_kuquqingkuang", "id", record);
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
	 * 分页查询企业简介信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findqiyejianjie(HashMap<String, Object> param) throws Exception{
		return CangchuglDao.findqiyejianjie(param);
	}
	
	public Ret addqiyejianjie (HashMap<String, Object> param) {
		String uuid = SqlUtil.uuid();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format( date ); 
		Record record = new Record().setColumns(param).set("id",uuid).set("cjsj", time);
		boolean result = Db.save("njpt_qiyejianjie", "id", record);
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
	
	public Ret delqiyejianjie (HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_qiyejianjie", "id", param.get("id"));
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
	 * 删除药剂空瓶
	 * @param param
	 * @return
	 */
	public Ret delyjkp(HashMap<String, Object> param) {
		boolean result = Db.deleteById("njpt_yaojikongping", "id", param.get("id"));
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
	 * 删除熏蒸作业
	 * @param param
	 * @return
	 */
	public Ret delxzzy(HashMap<String, Object> param) {
		boolean result = Db.deleteById("oa_xunzhengzuoye", "xzid", param.get("xzid"));
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

	
	
	public Ret upqiyejianjie  (HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_qiyejianjie", "id", record);
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
	 * 修改空瓶
	 * @param param
	 * @return
	 */
	public Ret upkongping(HashMap<String, Object> param) {
		Record record = new Record().setColumns(param);
		boolean result = Db.update("njpt_yaojikongping", "id", record);
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
	 * 修改熏蒸作业
	 * @param param
	 * @return
	 */
	@Before(Tx.class)
	public Ret upxzzy(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String xzqhdm = param.get("xzqhdm").toString();
		String qyzzjgdm = param.get("qyzzjgdm").toString();
		String kdbm = param.get("kdbm").toString();
		String beianbianhao = param.get("beianbianhao").toString();
		String fuzeren = param.get("fuzeren").toString();
		String canyuren = param.get("canyuren").toString();
		String xunzhengriqi = param.get("xunzhengriqi").toString();
		String cfs = param.get("cfs").toString();
		String yjmcs = param.get("yjmcs").toString();
		String yjsls = param.get("yjsls").toString();
		String lssls = param.get("lssls").toString();
		String xgs = param.get("xgs").toString();
		String oxzid =  param.get("xzid").toString();
		boolean result = Db.deleteById("oa_xunzhengzuoye", "xzid", oxzid);
		
		//保存熏蒸id
		String xzid = SqlUtil.uuid();
		
		
		
		String[] scf = cfs.split(",");
		String[] syjmc = yjmcs.split(",");
		String[] syjsl = yjsls.split(",");
		String[] slssl = lssls.split(",");
		String[] sxg = xgs.split(",");
		
		for(int i = 0; i < scf.length; i++){
			String id = SqlUtil.uuid();
			Record record = new Record();
			record.set("id", id);
			record.set("xzqhdm", xzqhdm);
			record.set("qyzzjgdm", qyzzjgdm);
			record.set("beianbianhao", beianbianhao);
			record.set("fuzeren", fuzeren);
			record.set("canyuren", canyuren);
			record.set("xunzhengriqi", xunzhengriqi);
			record.set("canghao", scf[i]);
			record.set("yjmc", syjmc[i]);
			record.set("yjsl", syjsl[i]);
			record.set("lssl", slssl[i]);
			record.set("xiaoguo", sxg[i]);
			record.set("xzid", xzid);
			record.set("kdbm",kdbm);
			boolean r = Db.save("oa_xunzhengzuoye", record);
			result = result && r;
		}
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
	 * 查询企业名称
	 * @param map
	 * @return
	 */
	public List<Record> findqiyemc(HashMap<String, Object> map) {
		String xian = (String) map.get("xian");
		String sql;
		List<Record> list = null;
		if("请选择区域".equals(xian)){
			sql = "select qymc,qyzzjgdm from tz_qiye";
			list = Db.find(sql);
		}else{
			sql = "select qymc,qyzzjgdm from tz_qiye where xian = ?";
			list = Db.find(sql, xian);
		}
		return list;
	}
	
	/**
	 * 查询库点名称
	 * @param map
	 * @return
	 */
	public List<Record> findkudianmc(HashMap<String, Object> map) {
		String qyzzjgdm = (String) map.get("qyzzjgdm");
		String sql;
		List<Record> list = null;
		if("请选择企业名称".equals(qyzzjgdm)){
			sql = "select kdmc from tz_kudian";
			list = Db.find(sql);
		}else{
			sql = "select kdmc from tz_kudian where qyzzjgdm = ?";
			list = Db.find(sql, qyzzjgdm);
		}
		return list;
	}
	
	/**
	 * 查找粮食类别
	 * @param map
	 * @return
	 */
	public List<Record> findlslb() {
		String sql;
		List<Record> list = null;
			sql = "select vName,vCode from tz_GrainType";
			list = Db.find(sql);
		return list;
	}
	
	
	/**
	 * 查找粮食性质
	 * @param map
	 * @return
	 */
	public List<Record> findlsxz() {
		String sql;
		List<Record> list = null;
			sql = "select vCode,vGrainProperties from tz_GrainProperties";
			list = Db.find(sql);
		return list;
	}
	
	/**
	 * 查询地区
	 * @return
	 */
	public List<Record> finddiqu() {
		String sql = "select a.xzqhdm,b.name from njpt_diqu a join fw_area b on a.areaid=b.id order by a.orderno";
		List<Record> list = Db.find(sql);
		return list;
	}
	
	
	/**
	 * 查找药库
	 * @return
	 */

	public List<Record> findyaoku(HashMap<String, Object> param) throws Exception {
		String name=(String) param.get("name");
		if(name==""){
			String sql = "select cfkudian,kdbm from njpt_yaokuguanli";
			List<Record> list = Db.find(sql);
			return list;
		}else{
			String sql = "select cfkudian,kdbm from njpt_yaokuguanli where 1=1 and shiqu='"+name+"'";
			List<Record> list = Db.find(sql);
			return list;
		}
		
	}
	
	
	/**
	 * 查询药剂名称和品牌
	 * @return
	 */
	public List<Record> findyjmcpp() {
		String sql = "select yjmc,yjpp from njpt_yjmingchengpingpai";
		List<Record> list = Db.find(sql);
		return list;
	}
	
	/**
	 * 查找达标单位
	 * @return
	 */
	public List<Record> finddbdw(HashMap<String, Object> param) {
		if(param.get("id")!=""){
			String sql = "select a.dbdw,b.qymc from njpt_anquanbzhzp a LEFT JOIN tz_qiye b ON a.dbdw=b.qyzzjgdm where 1=1 and b.ID='"+param.get("id")+"'";
			List<Record> list = Db.find(sql);
			return list;
		}else{
			String sql = "select a.dbdw,b.qymc from njpt_anquanbzhzp a LEFT JOIN tz_qiye b ON a.dbdw=b.qyzzjgdm";
			List<Record> list = Db.find(sql);
			return list;
		}
		
	}
	
	
	/**
	 * 保存上传的示意图
	 * @param filepath
	 * @param qydm
	 * @return
	 */
	public int upQysyt(String filepath,String qydm){
		return Db.update("UPDATE tz_qiye SET qysypath = ? WHERE qyzzjgdm = ?",filepath,qydm);
	}
	
	/**
	 * 保存上传的全貌图
	 * @param filepath
	 * @param qydm
	 * @return
	 */
	public int upQyqmt(String filepath,String qydm){
		return Db.update("UPDATE tz_qiye SET qyqmpath = ? WHERE qyzzjgdm = ?",filepath,qydm);
	}
	
	/**
	 * 保存图片信息
	 * @param filepath
	 * @return
	 */
	public boolean savetupian(String filepath){
		String uuid = SqlUtil.uuid();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format( date ); 
		Record record = new Record().set("id", uuid).set("scsj",time).set("tppath", filepath);
		return Db.save("njpt_shangchuantupian", record);
	}
	
	/**
	 * 查图片的id
	 * @param filepath
	 * @return
	 */
	public List<Record> findtupian(String filepath){
		return Db.find("select id from njpt_shangchuantupian where tppath = ?",filepath);
	}
	
	/**
	 * 根据企业组织机构代码和库点编码查图片
	 * @param qyzzjgdm
	 * @param kdbm
	 * @return
	 */
	public List<Record> findkdtp(String qyzzjgdm,String kdbm){
		return Db.find("select kdsypath,kdqmpath from tz_kudian where qyzzjgdm = ? and kdbm = ?",qyzzjgdm,kdbm);
	}
	
	
	/**
	 * 根据企业组织机构代码和库点编码查图片
	 * @param qyzzjgdm
	 * @param kdbm
	 * @return
	 */
	public List<Record> findqytp(String qyzzjgdm){
		return Db.find("select qysypath,qyqmpath from tz_qiye where qyzzjgdm = ? ",qyzzjgdm);
	}
	

	/**
	 * 获取库点类型
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryKdlx(HashMap<String, Object> queryParam) throws Exception {
		
		return CangchuglDao.queryKdlx(queryParam);
	}
	
	/**
	 * 获取熏蒸库点
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryxzkd(HashMap<String, Object> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return CangchuglDao.queryxzkd(queryParam);
	}

	/**
	 * 获取熏蒸作业
	 * @param queryparam
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxzzy(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		return CangchuglDao.findxzzy(queryparam);
	}
	
	
	/**
	 * 获取熏蒸作业明细
	 * @param queryparam
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findxzzymx(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		return CangchuglDao.findxzzymx(queryparam);
	}


	
	/**
	 * 查找企業簡介
	 * @param qyzzjgdm
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findjianjie(HashMap<String, Object> queryParam) throws Exception{
		return CangchuglDao.findShipin(queryParam);
	}
	
	/**
	 * 寻找熏蒸仓号
	 * @param queryparam
	 * @return
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> selectXzch(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		return CangchuglDao.selectXzch(queryparam);
	}
	
	

	/**
	 * 查找企業簡介
	 * @param qyzzjgdm
	 * @return
	 */
	public List<Record> findjianjie(String qyzzjgdm){
		List<Record> list = Db.find("select * from t_ShiPing where qyzzjgdm = ?",qyzzjgdm);
		return list;
	}
	
	
	
	public List<Record> findQiyemc(){
		return Db.find("select qymc from tz_qiye");
	}
	
	
	public List<Record> findKudianmc(){
		return Db.find("select kdmc from tz_kudian");
	}
	
	
	public List<Record> findBeianqiye(){
		return Db.find("select qymc from tz_qiye where qyzzjgdm in (select qyzzjgdm from njpt_qiyebeian)");
	}

	


	/**
	 * * author:dx
	 * date:2017/5/23
	 * time:14:59
	 * description:删除三维信息
	 * @param param
	 * @return
	 */
	public Ret deleteSanweiInfo(HashMap<String, Object> param)
	{
		if(param.containsKey("id")){
			//删除相关信息
			return Ret.create("ret", Db.deleteById("njpt_sanwei_info", param.get("id")));
		}else{
			return Ret.create("ret",false).put("message","参数不全");
		}
	}

	/**
	 * * author:dx
	 * date:2017/5/23
	 * time:15:26
	 * description:新增或者更新三维信息
	 * @param param
	 * @return
	 */
	public Ret addOrUpdateSanwei(HashMap<String, Object> param)
	{
		if(StrKit.notBlank(String.valueOf(param.get("id")))){
			//更新
			param.remove("xian");
			return Ret.create("ret",Db.update("njpt_sanwei_info",new Record().setColumns(param)));
		}else{
			//新增
			param.remove("id");
			param.remove("xian");
			return Ret.create("ret",Db.save("njpt_sanwei_info",new Record().setColumns(param)));
		}
	}

	
	
}