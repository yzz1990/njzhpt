package com.zkzy.njzhpt.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
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
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class ChuBeiLiangJHDAO {

	private static final int DIQU_NUM = 6;
	private static final int XINGZHI_NUM = 5;
	private static final int PINZHONG_NUM = 3;

	
	public static boolean findchubeiliangjhRW(HashMap<String, Object> map) throws Exception {
		List<Record> listrecord=Db.find("select * from njpt_chubeiliangjihua_renwu where niandu=? and xzqhdm=? and qyzzjgdm=? and xingzhi=? ",map.get("niandu"),map.get("xzqhdm"),map.get("qyzzjgdm"),map.get("xingzhi"));
		if(listrecord.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean findShiChuQiye(HashMap<String, Object> map) throws Exception {
		if("市区".equals(map.get("diqu"))){
			List<Record> listrecord=Db.find("select * from njpt_chubeiliangjihua_diqu where niandu=? and qyzzjgdm=? ",map.get("niandu"),map.get("qyzzjgdm"));
			if(listrecord.size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			List<Record> listrecord=Db.find("select * from njpt_chubeiliangjihua_diqu where niandu=? and diqu=? ",map.get("niandu"),map.get("diqu"));
			if(listrecord.size()>0){
				return true;
			}else{
				return false;
			}
		}
		
	}
	public static boolean findchubeiliangjh(HashMap<String, Object> map) throws Exception {
		List<Record> listrecord=Db.find("select * from njpt_chubeiliangjihua where niandu=? and quming=? and ccqy=? and cckd=? and ccch=? ",map.get("niandu"),map.get("quming"),map.get("ccqy"),map.get("cckd"),map.get("ccch"));
		if(listrecord.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static Ret cbljhrwbianji(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.setColumns(map);
		boolean bool = false;
		Ret ret=new Ret();
		String id = (String) map.get("id");
		String xm=(String) map.get("xm");
		if("".equals(xm)){
			xm="0";
		}
		String jd=(String) map.get("jd");
		if("".equals(jd)){
			jd="0";
		}
		String xd=(String) map.get("xd");
		if("".equals(xd)){
			xd="0";
		}
		record.set("xm", xm).set("jd", jd).set("xd", xd);
		if (StrKit.isBlank(id)) {
			record.set("id", SqlUtil.uuid());
			record.set("xiaoji", Integer.valueOf(xm)+Integer.valueOf(jd)+Integer.valueOf(xd));
			bool = Db.save("njpt_chubeiliangjihua_renwu", record);
		} else {
			Date date=new Date();
			SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			record.set("scxgtime", s.format(date));
			record.set("id", map.get("id"));
			record.set("xiaoji", Integer.valueOf(xm)+Integer.valueOf(jd)+Integer.valueOf(xd));
			bool = Db.update("njpt_chubeiliangjihua_renwu", record);
		}
		return ret.put("ret",bool);
		
	}
	
	public static Ret cbljhrwbj(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.setColumns(map);
		boolean bool = false;
		Ret ret=new Ret();
		String id = (String) map.get("id");
		String xm=(String) map.get("xm");
		if("".equals(xm)){
			xm="0";
		}
		String jd=(String) map.get("jd");
		if("".equals(jd)){
			jd="0";
		}
		String xd=(String) map.get("xd");
		if("".equals(xd)){
			xd="0";
		}
		record.set("xm", xm).set("jd", jd).set("xd", xd);
		if (StrKit.isBlank(id)) {
			record.set("id", SqlUtil.uuid());
			record.set("xiaoji", Integer.valueOf(xm)+Integer.valueOf(jd)+Integer.valueOf(xd));
			bool = Db.save("njpt_chubeiliangjihua_renwu", record);
		} else {
			Date date=new Date();
			SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			record.remove("niandu");
			record.set("id",SqlUtil.uuid());
			record.set("niandu", Integer.valueOf((String) map.get("niandu"))+1);
			record.set("scxgtime", s.format(date));
			//record.set("id", map.get("id"));
			record.set("xiaoji", Integer.valueOf(xm)+Integer.valueOf(jd)+Integer.valueOf(xd));
			bool = Db.save("njpt_chubeiliangjihua_renwu", record);
		}
		return ret.put("ret",bool);
		
	}
	public static Ret bianji(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.setColumns(map);
		boolean bool = false;
		Ret ret=new Ret();
		String id = (String) map.get("id");
		String pz = (String) map.get("pinzhong");
		String xingzhi=(String)map.get("xingzhi");
		if (StrKit.isBlank(id)) {
			/*List<Record> listrecord=Db.find("select * from njpt_chubeiliangjihua where niandu=? and quming=? and ccqy=? and cckd=? and ccch=? ",niandu,quming,ccqy,cckd,ccch);
			if(!listrecord.isEmpty()){
				return ret.put("ret",bool).put("msg","保存失败，同一年度该仓已存在储备粮计划！");
			}*/
			record.set("id", SqlUtil.uuid());
			if ("111".equals(pz)) {
				record.set("cbljh_xm", map.get("jhsl"));
				record.set("cbljh_jd", 0);
				record.set("cbljh_xd", 0);
			} else if ("1132".equals(pz)) {
				record.set("cbljh_jd", map.get("jhsl"));
				record.set("cbljh_xm", 0);
				record.set("cbljh_xd", 0);
			} else if ("1131".equals(pz)) {
				record.set("cbljh_xm", 0);
				record.set("cbljh_jd", 0);
				record.set("cbljh_xd", map.get("jhsl"));
			}
			if("122".equals(xingzhi)){
				record.set("status", "1");
			}else if("123".equals(xingzhi)){
				record.set("status", "0");
			}
			record.set("cbljh_xj", map.get("jhsl"));
			bool = Db.save("njpt_chubeiliangjihua", record);
		} else {
			record.set("id", map.get("id"));
			if ("111".equals(pz)) {
				record.set("cbljh_xm", map.get("jhsl"));
				record.set("cbljh_jd", 0);
				record.set("cbljh_xd", 0);
			} else if ("1132".equals(pz)) {
				record.set("cbljh_jd", map.get("jhsl"));
				record.set("cbljh_xm", 0);
				record.set("cbljh_xd", 0);
			} else if ("1131".equals(pz)) {
				record.set("cbljh_xd", map.get("jhsl"));
				record.set("cbljh_xm", 0);
				record.set("cbljh_jd", 0);
			}
			if("122".equals(xingzhi)){
				record.set("status", "1");
			}else if("123".equals(xingzhi)){
				record.set("status", "0");
			}
			record.set("cbljh_xj", map.get("jhsl"));
			bool = Db.update("njpt_chubeiliangjihua", record);
			
		}
		return ret.put("ret",bool);
		
	}
	
	public static Ret savecbljhpz(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.setColumns(map);
		boolean bool = false;
		Ret ret=new Ret();
		record.set("status", "0");
		bool = Db.update("njpt_chubeiliangjihua", record);
		return ret.put("ret",bool);
		
	}
	/**
	 * 获取仓廒数量
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static List<Record>  findcangaolist(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and a.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,10000, "select b.cfmc,b.cfbm ",
				"from njpt_chubeiliangjihua a inner join tz_cangfang b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 " + s.getSql()+" order BY ccch ", s.getParam().toArray(new Object[0]));
		//List<Record> listrecord = Db.find("select b.cfmc,b.cfbm from njpt_chubeiliangjihua a inner join tz_cangfang b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where ccqy=? and cckd=? and xingzhi= ? order BY ccch ", map.get("qyzzjgdm"),map.get("kdbm"));
		return page.getList();
	}
	/**
	 * 获取仓廒数量
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Record  findcangaosl(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,10000, "select COUNT(*) as cangaosl",
				"from njpt_chubeiliangjihua where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		//Record record = Db.findFirst("select COUNT(*) as cangaosl from njpt_chubeiliangjihua where quming=? and ccqy=? and cckd=?  ",map.get("xzqhdm"), map.get("qyzzjgdm"),map.get("kdbm"));
		
		return page.getList().get(0);
	}
	/**
	 * 获取该仓房下的储备粮计划分品种库存(市粮食局-只取市级储备)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Record  findcbljhkudiankc(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and niandu = ?", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Record record=new Record();
		record = Db.findFirst("select  ISNULL(sum(cbljh_xj)/1000, 0) as cbljh_xj,ISNULL(sum(cbljh_xm)/1000, 0) as cbljh_xm,ISNULL(sum(cbljh_jd)/1000, 0) as cbljh_jd,ISNULL(sum(cbljh_xd)/1000, 0) as cbljh_xd from njpt_chubeiliangjihua  where 1=1 and xingzhi='122' and quming = ? and ccqy = ? and cckd = ? and niandu = ? ",map.get("xzqhdm"),map.get("qyzzjgdm"),map.get("kdbm"),map.get("niandu"));
		if(record!=null){
		
		}else{
			Record emp=new Record();
			emp.set("cbljh_xj", 0);
			emp.set("cbljh_xm", 0);
			emp.set("cbljh_jd", 0);
			emp.set("cbljh_xd", 0);
			record=emp;
		}
		record.set("xingzhi", "");
		return record;
	}
	/**
	 * 获取该仓房下的储备粮计划分品种库存（区粮食局-取市级和区级储备）
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Record  findcbljhkudiankcquxian(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and niandu = ?", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Record record=new Record();
		record = Db.findFirst("select  ISNULL(sum(cbljh_xj)/1000, 0) as cbljh_xj,ISNULL(sum(cbljh_xm)/1000, 0) as cbljh_xm,ISNULL(sum(cbljh_jd)/1000, 0) as cbljh_jd,ISNULL(sum(cbljh_xd)/1000, 0) as cbljh_xd from njpt_chubeiliangjihua  where 1=1  and quming = ? and ccqy = ? and cckd = ? and niandu = ? ",map.get("xzqhdm"),map.get("qyzzjgdm"),map.get("kdbm"),map.get("niandu"));
		if(record!=null){
		
		}else{
			Record emp=new Record();
			emp.set("cbljh_xj", 0);
			emp.set("cbljh_xm", 0);
			emp.set("cbljh_jd", 0);
			emp.set("cbljh_xd", 0);
			record=emp;
		}
		record.set("xingzhi", "");
		return record;
	}
	/**
	 * 获取该仓房下的储备粮计划分品种库存
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Record  findcbljhpzkucun(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and ccch = ?", "cfbm");
		p.put("and niandu = ?", "niandu");
		p.put("and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Record record=new Record();
		record = Db.findFirst("select ISNULL(xingzhi, '') as xingzhi, ISNULL(cbljh_xj/1000, 0) as cbljh_xj,ISNULL(cbljh_xm/1000, 0) as cbljh_xm,ISNULL(cbljh_jd/1000, 0) as cbljh_jd,ISNULL(cbljh_xd/1000, 0) as cbljh_xd from njpt_chubeiliangjihua  where 1=1  "+ s.getSql(), s.getParam().toArray(new Object[0]));
		if(record!=null){
			
			Record xingzhi=Db.findFirst("select *  from tz_GrainProperties where vCode=? ",record.getStr("xingzhi"));
			if(xingzhi!=null){
				record.set("xingzhi", xingzhi.getStr("vGrainProperties"));
			}
		}else{
			Record emp=new Record();
			emp.set("xingzhi", "--");
			emp.set("cbljh_xj", 0);
			emp.set("cbljh_xm", 0);
			emp.set("cbljh_jd", 0);
			emp.set("cbljh_xd", 0);
			record=emp;
		}
		return record;
	}
	/**
	 * 获取该仓房下的储备粮计划分品种库存
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Record  findcbljhpzkucunquxian(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and cckd = ?", "kdbm");
		p.put("and ccch = ?", "cfbm");
		p.put("and niandu = ?", "niandu");
		p.put("and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Record record=new Record();
		record = Db.findFirst("select ISNULL(xingzhi, '') as xingzhi, ISNULL(cbljh_xj/1000, 0) as cbljh_xj,ISNULL(cbljh_xm/1000, 0) as cbljh_xm,ISNULL(cbljh_jd/1000, 0) as cbljh_jd,ISNULL(cbljh_xd/1000, 0) as cbljh_xd from njpt_chubeiliangjihua  where 1=1 "+ s.getSql(), s.getParam().toArray(new Object[0]));
		if(record!=null){
			
			Record xingzhi=Db.findFirst("select *  from tz_GrainProperties where vCode=? ",record.getStr("xingzhi"));
			if(xingzhi!=null){
				record.set("xingzhi", xingzhi.getStr("vGrainProperties"));
			}
		}else{
			Record emp=new Record();
			emp.set("xingzhi", "--");
			emp.set("cbljh_xj", 0);
			emp.set("cbljh_xm", 0);
			emp.set("cbljh_jd", 0);
			emp.set("cbljh_xd", 0);
			record=emp;
		}
		return record;
	}
	/**
	 * 获取该仓房下的实物库存分品种库存
	 * @param map
	 * @return
	 */
	public static Record  findcfpzkucun(HashMap<String, Object> map){
		String type = (String) map.get("lspz");
		String sql = "select sum(dmStock) as "+type+" from kc_CurrentStock where "
				+ "qyzzjgdm=? and kdbm=? and vWareHouseCode=? and vGrainSubTypeCode=? and vGrainPropertyCode in('122','123')";
		Record record = Db.findFirst(sql, map.get("qyzzjgdm"),map.get("kdbm"),map.get("cfbm"),map.get("lspzbm"));
		return record;
	}
	/**
	 * 获取各粮食品种库存
	 * @param map
	 * @return
	 */
	public static Record  findpzkucun(HashMap<String, Object> map){
		String type = (String) map.get("lspz");
		String sql = "select sum(dmStock) as "+type+" from kc_CurrentStock where "
				+ "qyzzjgdm=? and kdbm=? and vGrainSubTypeCode=? and vGrainPropertyCode in('122','123')";
		Record record = Db.findFirst(sql, map.get("qyzzjgdm"),map.get("kdbm"),map.get("lspzbm"));
		return record;
	}
	public static Page<Record> quECharts(HashMap<String, Object> map) throws Exception {
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String niandu=String.valueOf(yearInt);
		List<Record> list=new ArrayList<>();
		//南京市
				Record nanjingshi=nanjingshiEChart(niandu);
				Record nanjinshijhs=new Record()
						.set("leixing", "jhs")
						.set("shuliang", nanjingshi.get("jhsl"));
				Record nanjinshiswkc=new Record()
						.set("leixing", "swkc")
						.set("shuliang", nanjingshi.get("swkc"));
				list.add(nanjinshijhs);
				list.add(nanjinshiswkc);
				
		//区
				String[] qu = { "320115", "320116", "320111", "320124" };// 江宁 六合 浦口 溧水
				for(String xzqhdm:qu){
					Record quRecord=quEChart(xzqhdm,niandu);
					Record qujhs=new Record()
							.set("leixing", "jhs")
							.set("shuliang", quRecord.get("jhsl"));
					Record quswkc=new Record()
							.set("leixing", "swkc")
							.set("shuliang", quRecord.get("swkc"));
					list.add(qujhs);
					list.add(quswkc);
				}
		
		//高淳
				Record gaochunRecord=gaochunEChart(niandu);
				Record gaochunjhs=new Record()
						.set("leixing", "jhs")
						.set("shuliang", gaochunRecord.get("jhsl"));
				Record gaochunswkc=new Record()
						.set("leixing", "swkc")
						.set("shuliang", gaochunRecord.get("swkc"));
				list.add(gaochunjhs);
				list.add(gaochunswkc);
		//其他
				
				Record qitaRecord= qitaEchart(niandu);
				Record qitajhs=new Record()
						.set("leixing", "jhs")
						.set("shuliang", qitaRecord.get("jhsl"));
				Record qitaswkc=new Record()
						.set("leixing", "swkc")
						.set("shuliang", qitaRecord.get("swkc"));
				list.add(qitajhs);
				list.add(qitaswkc);
		//南京市属企业实物库存和储备粮计划
				Record shishuqiyeRecord=shishuqiyeEChart(niandu);
				Record shishujhs=new Record()
						.set("leixing", "jhs")
						.set("shuliang", shishuqiyeRecord.get("jhsl"));
				Record shishuswkc=new Record()
						.set("leixing", "swkc")
						.set("shuliang", shishuqiyeRecord.get("swkc"));
				list.add(shishujhs);
				list.add(shishuswkc);
		
		Page<Record> listRecord =new Page<>(list, 1, 15, 1, 15);
		return listRecord;

		
	}
	public static Record nanjingshiEChart(String niandu){
		Record record=new Record();
		Record qujhs = Db.findFirst(
				"select sum(jhsl) as jhsl from njpt_chubeiliangjihua where  niandu=?",niandu);
		Record quswkc = Db.findFirst(
				"select SUM(k.dmStock) as swkc from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm and k.vGrainPropertyCode in(122,123)");
		record.set("jhsl", qujhs.get("jhsl",new BigDecimal(0.0)))
			  .set("swkc", quswkc.get("swkc",new BigDecimal(0.0)))
		;
		return record;
	}
	public static Record gaochunEChart(String niandu){
		Record record=new Record();
		Record qujhs = Db.findFirst(
				"select sum(jhsl) as jhsl from njpt_chubeiliangjihua where pinzhong in('1131','1132') and quming='320125' and niandu=?",niandu);
		Record quswkc = Db.findFirst(
				"select SUM(k.dmStock) as swkc from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm and k.vGrainPropertyCode in(122,123) and vGrainSubTypeCode in('1131','1132') and d.xzqhdm='320125'");
		record.set("jhsl", qujhs.get("jhsl",new BigDecimal(0.0)))
			  .set("swkc", quswkc.get("swkc",new BigDecimal(0.0)))
		;
		return record;
		
	}
	public static Record quEChart(String xzqhdm,String niandu){
		Record record=new Record();
		Record qujhs = Db.findFirst(
				"select sum(jhsl) as jhsl from njpt_chubeiliangjihua where quming=? and niandu=?",xzqhdm,niandu);
		Record quswkc = Db.findFirst(
				"select SUM(k.dmStock) as swkc from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm and k.vGrainPropertyCode in(122,123) and d.xzqhdm=?",xzqhdm);
		record.set("jhsl", qujhs.get("jhsl",new BigDecimal(0.0)))
			  .set("swkc", quswkc.get("swkc",new BigDecimal(0.0)))
		;
		return record;
		
	}
	
	public static Record qitaEchart(String niandu){
		Record record=new Record();
		Record qiyejhs = Db.findFirst(
				"select sum(jhsl) as jhsl from njpt_chubeiliangjihua where ccqy in('765432125','765432126') and pinzhong in('1131','1132') and niandu=?",niandu);
		Record qiyeswkc = Db.findFirst(
				"select SUM(k.dmStock) as swkc from  kc_CurrentStock k where k.vGrainPropertyCode in(122,123) and vGrainSubTypeCode in('1131','1132') and k.qyzzjgdm in ('765432125','765432126')");
		record.set("jhsl", qiyejhs.get("jhsl",new BigDecimal(0.0)))
			  .set("swkc", qiyeswkc.get("swkc",new BigDecimal(0.0)))
		;
		return record;
		
	}
	public static Record shishuqiyeEChart(String niandu){
		Record record=new Record();
		Record qiyejhs = Db.findFirst(
				"select sum(jhsl) as jhsl from njpt_chubeiliangjihua where ccqy in('765432124','660696497') and pinzhong in('111','1132') and niandu=?",niandu);
		Record qiyeswkc = Db.findFirst(
				"select SUM(k.dmStock) as swkc from  kc_CurrentStock k where k.vGrainPropertyCode in(122,123) and vGrainSubTypeCode in('111','1132') and k.qyzzjgdm in ('765432124','660696497')");
		record.set("jhsl", qiyejhs.get("jhsl",new BigDecimal(0.0)))
			  .set("swkc", qiyeswkc.get("swkc",new BigDecimal(0.0)))
		;
		return record;
		
	}
	public static Page<Record> fpzECharts(HashMap<String, Object> map) throws Exception {
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String niandu=String.valueOf(yearInt);
		List<Record> list=new ArrayList<>();
		//分品种储备粮计划
		Record cbljh=new Record();
		cbljh=Db.findFirst("select SUM(cbljh_xm) as xm,sum(cbljh_jd) as jd,SUM(cbljh_xd) as xd,sum(cbljh_xj) as xj FROM njpt_chubeiliangjihua where niandu=?",niandu);
		//求实物库存
		Record qsxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%111%' and k.vGrainPropertyCode in(122,123)");
		Record qsjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%1132%' and k.vGrainPropertyCode in(122,123)");
		Record qsxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%1131%' and k.vGrainPropertyCode in(122,123)");
		BigDecimal qsxmSWKC = qsxmRrcord.get("xm",new BigDecimal(0.0));
		BigDecimal qsjdSWKC = qsjdRrcord.get("jd",new BigDecimal(0.0));
		BigDecimal qsxdSWKC = qsxdRrcord.get("xd",new BigDecimal(0.0));
		double qsxjSWKC = qsxmSWKC.doubleValue() + qsjdSWKC.doubleValue() + qsxdSWKC.doubleValue();
		//小麦储备粮计划和实物库存
		Record xmjhs=new Record().set("shuliang", cbljh.get("xm")).set("leixing", "jhs");
		Record xmswkc=new Record().set("shuliang", qsxmSWKC).set("leixing", "swkc");
		//粳稻储备粮计划和实物库存
		Record jdjhs=new Record().set("shuliang", cbljh.get("jd")).set("leixing", "jhs");
		Record jdswkc=new Record().set("shuliang", qsjdSWKC).set("leixing", "swkc");
		//籼稻储备粮计划和实物库存
		Record xdjhs=new Record().set("shuliang", cbljh.get("xd")).set("leixing", "jhs");
		Record xdswkc=new Record().set("shuliang", qsxdSWKC).set("leixing", "swkc");
		//总量储备粮计划和实物库存
		Record xjjhs=new Record().set("shuliang", cbljh.get("xj")).set("leixing", "jhs");
		Record xjswkc=new Record().set("shuliang", qsxjSWKC).set("leixing", "swkc");
		list.add(xjjhs);
		list.add(xjswkc);
		list.add(xdjhs);
		list.add(xdswkc);
		list.add(jdjhs);
		list.add(jdswkc);
		list.add(xmjhs);
		list.add(xmswkc);
		Page<Record> listRecord =new Page<>(list, 1, 15, 1, 15);
		return listRecord;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	public static Page<Record> queryswkc(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and d.xzqhdm = ?", "xzqhdm");
		p.put("and q.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?", "kdbm");
		p.put("and k.kdmc like ?", "kdmc","%%%s%%");
		p.put("and cbl.niandu = ?", "niandu");
		p.put(" and cbl.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select d.xzqhdm as xzqhdm,q.qyzzjgdm as qyzzjgdm,k.kdbm as kdbm,fw.name as quyu,q.qymc as qymc,k.kdmc as kdmc,ISNULL(sum(cbl.cbljh_xj)/1000, 0.000) as cbljh_xj,ISNULL(sum(cbl.cbljh_xm)/1000, 0.000) as cbljh_xm,ISNULL(sum(cbl.cbljh_jd)/1000, 0.000) as cbljh_jd,ISNULL(sum(cbl.cbljh_xd)/1000, 0) as cbljh_xd  ",
				"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm inner JOIN njpt_chubeiliangjihua cbl on d.xzqhdm=cbl.quming and q.qyzzjgdm=cbl.ccqy and k.kdbm=cbl.cckd where 1=1 and k.kdlxbh<>'06' " + s.getSql()+" GROUP BY fw.orderno  ,d.xzqhdm,q.qyzzjgdm,k.kdbm ,fw.name,q.qymc,k.kdmc ORDER BY fw.orderno,q.qyzzjgdm,k.kdbm", s.getParam().toArray(new Object[0]));
		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	

	/**
	 * 获取全市 储备粮计划和实物库存
	 * 
	 * @param year
	 * @param pz
	 * @return
	 */
	public static Record quanshi(HashMap<String, Object> map) {
		String year= (String)map.get("year");
		Record record = new Record();
		// 储备粮计划数
		//铁心桥，下关（小麦，粳稻，籼稻）
		Record tiexinqiao =Db.findFirst("select sum(cbljh_xm) as xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and ccqy in('134967560','660696497')",map.get("xingzhi"),year);
		//军粮（粳稻，籼稻）
		Record junliang =Db.findFirst("select sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and ccqy=?",map.get("xingzhi"),year,"660696498");
		//江宁，浦口，六合（小麦，粳稻，籼稻）
		Record jnpklh =Db.findFirst("select sum(cbljh_xm) xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and quming in('320115','320111','320116')",map.get("xingzhi"),year);
		//溧水，高淳(粳稻)
		Record lsgc =Db.findFirst("select sum(cbljh_jd) as jd from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and quming in('320124','320125')",map.get("xingzhi"),year);
		//沙塘(粳稻)
		Record shatang =Db.findFirst("select sum(cbljh_jd) as jd from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and ccqy=?",map.get("xingzhi"),year,"736094548");
		//海悦（小麦）
		Record haiyue =Db.findFirst("select sum(cbljh_xm) as xm from njpt_chubeiliangjihua where xingzhi = ? and niandu=? and ccqy=?",map.get("xingzhi"),year,"765432125");
		//小麦储备粮总计划
		double xm=(((BigDecimal)tiexinqiao.get("xm",new BigDecimal(0))).doubleValue()+((BigDecimal)jnpklh.get("xm",new BigDecimal(0))).doubleValue()+((BigDecimal)haiyue.get("xm",new BigDecimal(0))).doubleValue())/1000;
		//粳稻储备粮计划
		double jd=(((BigDecimal)tiexinqiao.get("jd",new BigDecimal(0))).doubleValue()+((BigDecimal)junliang.get("jd",new BigDecimal(0))).doubleValue()+((BigDecimal)jnpklh.get("jd",new BigDecimal(0))).doubleValue()+((BigDecimal)lsgc.get("jd",new BigDecimal(0))).doubleValue()+((BigDecimal)shatang.get("jd",new BigDecimal(0))).doubleValue())/1000;
		//籼稻储备粮计划
		double xd=(((BigDecimal)tiexinqiao.get("xd",new BigDecimal(0))).doubleValue()+((BigDecimal)junliang.get("xd",new BigDecimal(0))).doubleValue()+((BigDecimal)jnpklh.get("xd",new BigDecimal(0))).doubleValue())/1000;
				
				
		/*Record quanshijhs = Db.findFirst(
				"select sum(cbljh_xm) xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi='122' and niandu=?",year);
*/
		/*BigDecimal qsxmcbljh = quanshijhs.get("xm", new BigDecimal(0.0));
		BigDecimal qsjdcbljh = quanshijhs.get("jd", new BigDecimal(0.0));
		BigDecimal qsxdcbljh = quanshijhs.get("xd", new BigDecimal(0.0));
		double qsxjcbljh = (qsxmcbljh.doubleValue() + qsjdcbljh.doubleValue() + qsxdcbljh.doubleValue())/1000;*/
		double qsxjcbljh =(xm+jd+xd);
		record.set("xmcbljh", xm);
		record.set("jdcbljh", jd);
		record.set("xdcbljh", xd);
		record.set("xjcbljh", qsxjcbljh);
		// 全市实际库存
		//小麦--铁心桥，下关，海悦
		Record qsxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '111%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and k.qyzzjgdm  in ('134967560','660696497','765432125') ");
		//小麦--江宁，浦口，六合
		Record jnpklhxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '111%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and d.xzqhdm in('320115','320111','320116') ");
		//粳稻--
		Record qsjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and k.qyzzjgdm  in ('134967560','660696497','660696498','736094548') ");
		Record diqujdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and d.xzqhdm in('320115','320111','320116','320124','320125') ");
		//籼稻--
		Record qsxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '1131%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and k.qyzzjgdm  in ('134967560','660696497','660696498') ");
		Record diquxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '1131%' and k.vGrainPropertyCode = '"+map.get("xingzhi")+"' and d.xzqhdm in('320115','320111','320116') ");
		BigDecimal qsxmSWKC = qsxmRrcord.get("xm",new BigDecimal(0.0));
		BigDecimal jnpklhSWKC =jnpklhxmRrcord.get("xm",new BigDecimal(0.0));
		BigDecimal qsjdSWKC = qsjdRrcord.get("jd",new BigDecimal(0.0));
		BigDecimal diqujdSWKC = diqujdRrcord.get("jd",new BigDecimal(0.0));
		BigDecimal diquxdSWKC = diquxdRrcord.get("xd",new BigDecimal(0.0));
		BigDecimal qsxdSWKC = qsxdRrcord.get("xd",new BigDecimal(0.0));
		double qsxjSWKC = (qsxmSWKC.doubleValue()+jnpklhSWKC.doubleValue() + qsjdSWKC.doubleValue()+diqujdSWKC.doubleValue() + qsxdSWKC.doubleValue()+diquxdSWKC.doubleValue())/1000;
		record.set("xmswkc", (qsxmSWKC.doubleValue()+jnpklhSWKC.doubleValue())/1000)
				.set("jdswkc", (qsjdSWKC.doubleValue()+diqujdSWKC.doubleValue())/1000)
				.set("xdswkc", (qsxdSWKC.doubleValue()+diquxdSWKC.doubleValue())/1000)
				.set("xjswkc", qsxjSWKC);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		// 实际库存占计划数%
		String qssjkczb="100%";
		if(qsxjSWKC==0.0&&qsxjcbljh==0.0){
			record.set("sjkczb", qssjkczb);
			record.set("sjkczbbool", "1");
		}else{
			
			qssjkczb = nf.format(qsxjSWKC / qsxjcbljh);
			record.set("sjkczb", qssjkczb);
			if(qsxjSWKC / qsxjcbljh>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", qssjkczb);
			}
			if (((qsxjSWKC / qsxjcbljh) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String qssjjdzb="100%";
		if((qsjdSWKC.doubleValue())==0.0&&(jd)==0.0){
			record.set("sjjdzb", qssjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			qssjjdzb = nf.format(qsjdSWKC.doubleValue() / jd);
			if(qsjdSWKC.doubleValue() / jd>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", qssjjdzb);
			}
			if (((qsjdSWKC.doubleValue() / jd) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%
		String qssjxdzb="100%";
		if((qsxdSWKC .doubleValue())==0.0&&(xd)==0.0){
			record.set("sjxdzb", qssjxdzb);
			record.set("sjxdzbbool", "1");
		}else {
			qssjxdzb = nf.format(qsxdSWKC .doubleValue()/ xd);
			if(qsxdSWKC .doubleValue()/ xd>=1){
				record.set("sjxdzb", "100%");
			}else{
			record.set("sjxdzb", qssjxdzb);
			
			}
			if (((qsxdSWKC.doubleValue() / xd) - (1.0 / 3.0)) >= 0) {
				record.set("sjxdzbbool", "1");
			} else {
				record.set("sjxdzbbool", "0");
			}
		}
		// 实际小麦库存占计划数%
		String qssjxmzb="100%";
		if((qsxmSWKC.doubleValue())==0.0&&xm==0.0){
			record.set("sjxmzb", qssjxmzb);
			record.set("sjxmzbbool", "1");
		}else {
			qssjxmzb = nf.format(qsxmSWKC.doubleValue() / xm);
		
			if(qsxmSWKC.doubleValue() / xm>=1){
				record.set("sjxmzb", "100%");
			}else {
			
				record.set("sjxmzb", qssjxmzb);
			}
			if (((qsxmSWKC.doubleValue() / xm) - (1.0 / 2.0)) >= 0) {
				record.set("sjxmzbbool", "1");
			} else {
				record.set("sjxmzbbool", "0");
			}
		}
		return record;

	}
	/**
	 * 转换占比 （计划，库存，比例）
	 * @param jihua
	 * @param kucun
	 * @param zhanbi
	 * @return
	 */
	public static HashMap<String, Object> baifenbi(BigDecimal jihua,BigDecimal kucun,int zhanbi){
		double jihuadou=Math.round(jihua.doubleValue());
		double kucundou=Math.round(kucun.doubleValue());
		HashMap<String, Object> zb=new HashMap<>();
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		String point ="0.00%";
		String bool="1";
		if((jihuadou==0.0&&kucundou==0.0)){
			zb.put("zb", point);
			bool="0";
		}else{
			if(kucundou / jihuadou>=1){
				point="100%";
				zb.put("zb", point);
			}else {
				point = nf.format(kucundou/ jihuadou);
				zb.put("zb", point);
			}
		}
		if((jihuadou>0&&kucundou==0)||(kucundou/jihuadou)<(1.0/zhanbi)){
			bool="0";
		}
		zb.put("bool", bool);
		return zb;
		
	}
	public static Record gaochunShiWuKuCun(String xzqhdm, HashMap<String, Object> map) {
		String year=(String)map.get("year");
		Record record = new Record();
		// 得到区域名称
		Record quyu = Db.findFirst("select * from njpt_diqu where xzqhdm=?", xzqhdm);
		String quming = Db.findFirst("select * from fw_area where id=?",quyu.getStr("areaid")).getStr("name");
		
		record.set("quming", quming);
		record.set("xzqhdm", xzqhdm);

		// 得到区的小麦，粳稻，籼稻 --计划数
		Record jihuashu = Db.findFirst(
				"select sum(cbljh_jd) as jd from njpt_chubeiliangjihua where xingzhi = ? and  quming=? and niandu=?",
				map.get("xingzhi"),xzqhdm, year);
		BigDecimal jdcbljh = jihuashu.get("jd",new BigDecimal(0.0));
		record.set("jdcbljh", jdcbljh.doubleValue()/1000);
		// 得到高淳的小麦，粳稻，籼稻 --实物库存
		Record jdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  where d.xzqhdm=? and k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = ? ",
				xzqhdm,map.get("xingzhi"));
		BigDecimal jdSWKC = jdRrcord.get("jd",new BigDecimal(0.0));
		record.set("jdswkc", jdSWKC.doubleValue()/1000);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		// String zlbl = nf.format(budiyu);

		// 实际库存占计划数%
		String sjkczb="100%";
		if((jdSWKC.doubleValue())==0.0&&(jdcbljh.doubleValue())==0.0){
			record.set("sjkczb", sjkczb);
			record.set("sjkczbbool", "1");
		}else {
			sjkczb = nf.format((jdSWKC.doubleValue()) / (jdcbljh.doubleValue()));
			if(((jdSWKC.doubleValue()) / (jdcbljh.doubleValue()))>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", sjkczb);
			}
			if ((((jdSWKC.doubleValue()) / (jdcbljh.doubleValue())) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String sjjdzb="100%";
		if((jdSWKC.doubleValue())==0.0&&(jdcbljh.doubleValue())==0.0){
			record.set("sjjdzb", sjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			sjjdzb = nf.format(jdSWKC.doubleValue() / jdcbljh.doubleValue());
			if((jdSWKC.doubleValue() / jdcbljh.doubleValue())>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", sjjdzb);
			}
			if (((jdSWKC.doubleValue() / jdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%
				record.set("sjxdzb", "");
				record.set("sjxdzbbool", "1");
		// 实际小麦库存占计划数%(高淳没有)
		record.set("sjxmzb", "");
		record.set("sjxmzbbool", "1");

		return record;

	}
	public static Record haiyueqita(String qyzzjgdm, HashMap<String, Object> map) {
		String year=(String)map.get("year");
		Record record = new Record();
		// 得到企业名称
		Record qiyemc = Db.findFirst("select qymc from tz_qiye where qyzzjgdm=?", qyzzjgdm);
		if(qiyemc!=null){
			record.set("qymc", qiyemc.get("qymc"));
			record.set("qyzzjgdm", qyzzjgdm);
		}else{
			record.set("qymc", "");
			record.set("qyzzjgdm", qyzzjgdm);
		}
		
		// 得到企业下的储备粮计划
		Record qiyejhs = Db.findFirst(
				"select sum(cbljh_xm) as xm from njpt_chubeiliangjihua where xingzhi = ? and  pinzhong in('111') and ccqy=? and niandu=? ",
				map.get("xingzhi"),qyzzjgdm, year);
		BigDecimal qyxmcbljh = qiyejhs.get("xd",new BigDecimal(0.0));
		record.set("xmcbljh", qyxmcbljh.doubleValue()/1000);
		// 得到该企业实物库存
		Record qyxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  kc_CurrentStock k where k.vGrainSubTypeCode like '111%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		BigDecimal qyxmSWKC = qyxmRrcord.get("xm",new BigDecimal(0.0));
		record.set("xmswkc", qyxmSWKC.doubleValue()/1000);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式

		// 实际库存占计划数%
		String qysjkczb="100%";
		if((qyxmSWKC.doubleValue())==0.0&&(qyxmcbljh.doubleValue())==0.0){
			record.set("sjkczb", qysjkczb);
			record.set("sjkczbbool", "1");
		}else {
			qysjkczb = nf.format((qyxmSWKC.doubleValue()) / (qyxmcbljh.doubleValue()));
			if(((qyxmSWKC.doubleValue()) / (qyxmcbljh.doubleValue()))>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", qysjkczb);
			}
			if ((((qyxmSWKC.doubleValue()) / (qyxmcbljh.doubleValue())) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		
			record.set("sjjdzb", "");
			record.set("sjjdzbbool", "1");
		
		// 实际籼稻库存占计划数%
		
			record.set("sjxdzb", "");
			record.set("sjxdzbbool", "1");
		

		// 实际小麦库存占计划数%(其他 没有小麦为空)
			String qitasjxmzb="100%";
			if((qyxmSWKC.doubleValue())==0.0&&(qyxmcbljh.doubleValue())==0.0){
				record.set("sjxmzb", qitasjxmzb);
				record.set("sjxmzbbool", "1");
			}else {
				qitasjxmzb = nf.format(qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue());
				if((qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue())>=1){
					record.set("sjxmzb", "100%");
				}else {
				record.set("sjxmzb", qitasjxmzb);
				}
				if (((qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue()) - (1.0 / 2.0)) >= 0) {
					record.set("sjxmzbbool", "1");
				} else {
					record.set("sjxmzbbool", "0");
				}
			}
		return record;
	}
	public static Record shatangqita(String qyzzjgdm, HashMap<String, Object> map) {
		String year=(String)map.get("year");
		Record record = new Record();
		// 得到市属企业名称
		Record qiyemc = Db.findFirst("select qymc from tz_qiye where qyzzjgdm=?", qyzzjgdm);
		//record.set("qymc", qiyemc.get("qymc"));
		//record.set("qyzzjgdm", qyzzjgdm);
			record.set("qymc", "沙塘庵市场");
			record.set("qyzzjgdm", qyzzjgdm);
		// 得到市属企业下的储备粮计划
		Record qiyejhs = Db.findFirst(
				"select sum(cbljh_jd) as jd from njpt_chubeiliangjihua where xingzhi = ? and  pinzhong in('1132') and ccqy=? and niandu=? ",
				map.get("xingzhi"),qyzzjgdm, year);
		BigDecimal qyjdcbljh = qiyejhs.get("jd",new BigDecimal(0.0));
		record.set("jdcbljh", qyjdcbljh.doubleValue()/1000);
		// 得到该企业实物库存
		Record qyjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  kc_CurrentStock k where k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		BigDecimal qyjdSWKC = qyjdRrcord.get("jd",new BigDecimal(0.0));
		record.set("jdswkc", qyjdSWKC.doubleValue()/1000);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式

		// 实际库存占计划数%
		String qysjkczb="100%";
		if((qyjdcbljh.doubleValue())==0.0&&(qyjdSWKC.doubleValue())==0.0){
			record.set("sjkczb", qysjkczb);
			record.set("sjkczbbool", "1");
		}else {
			qysjkczb = nf.format((qyjdcbljh.doubleValue()) / (qyjdSWKC.doubleValue()));
			if(((qyjdcbljh.doubleValue()) / (qyjdSWKC.doubleValue()))>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", qysjkczb);
			}
			if ((((qyjdcbljh.doubleValue()) / (qyjdSWKC.doubleValue())) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String qysjjdzb="100%";
		if((qyjdSWKC.doubleValue())==0.0&&qyjdcbljh.doubleValue()==0.0){
			record.set("sjjdzb", qysjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			
			qysjjdzb = nf.format(qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue());
			if((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue())>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", qysjjdzb);
			}
			if (((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%
		
			record.set("sjxdzb", "");
			record.set("sjxdzbbool", "1");
		

		// 实际小麦库存占计划数%(其他 没有小麦为空)
		record.set("sjxmzb", "");
		record.set("sjxmzbbool", "1");

		return record;
	}
	public static Record qitashiwukucun(String qyzzjgdm, HashMap<String, Object> map) {
		String year=(String) map.get("year");
		Record record = new Record();
		// 得到市属企业名称
		/*Record qiyemc = Db.findFirst("select qymc from tz_qiye where qyzzjgdm=?", qyzzjgdm);
		if(qiyemc!=null){
			record.set("qymc", qiyemc.get("qymc"));
			record.set("qyzzjgdm", qyzzjgdm);
		}else{
			record.set("qymc", "");
			record.set("qyzzjgdm", qyzzjgdm);
		}*/
		
		// 得到市属企业下的储备粮计划
		Record qiyejhs = Db.findFirst(
				"select sum(cbljh_xd) xd,sum(cbljh_jd) as jd from njpt_chubeiliangjihua where xingzhi = ? and  pinzhong in('1131','1132') and ccqy=? and niandu=? ",map.get("xingzhi"),
				qyzzjgdm, year);
		BigDecimal qyjdcbljh = qiyejhs.get("jd",new BigDecimal(0.0));
		BigDecimal qyxdcbljh = qiyejhs.get("xd",new BigDecimal(0.0));
		double qyxjcbljh = (qyxdcbljh.doubleValue() + qyjdcbljh.doubleValue())/1000;
		record.set("xdcbljh", qyxdcbljh.doubleValue()/1000);
		record.set("jdcbljh", qyjdcbljh.doubleValue()/1000);
		record.set("xjcbljh", qyxjcbljh);
		// 得到该企业实物库存
		Record qyxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  kc_CurrentStock k where k.vGrainSubTypeCode like '1131%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		Record qyjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  kc_CurrentStock k where k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		BigDecimal qyxdSWKC = qyxdRrcord.get("xd",new BigDecimal(0.0));
		BigDecimal qyjdSWKC = qyjdRrcord.get("jd",new BigDecimal(0.0));
		double qyxjSWKC = (qyxdSWKC.doubleValue() + qyjdSWKC.doubleValue())/1000;
		record.set("xdswkc", qyxdSWKC.doubleValue()/1000)
			.set("jdswkc", qyjdSWKC.doubleValue()/1000)
			.set("xjswkc", qyxjSWKC);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式

		// 实际库存占计划数%
		String qysjkczb="100%";
		if(qyxjSWKC==0.0&&qyxjcbljh==0.0){
			record.set("sjkczb", qysjkczb);
			record.set("sjkczbbool", "1");
		}else {
			qysjkczb = nf.format(qyxjSWKC / qyxjcbljh);
		
			if((qyxjSWKC / qyxjcbljh)>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", qysjkczb);
			}
			if (((qyxjSWKC / qyxjcbljh) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String qysjjdzb="100%";
		if((qyjdSWKC.doubleValue())==0.0&&qyjdcbljh.doubleValue()==0.0){
			record.set("sjjdzb", qysjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			
			qysjjdzb = nf.format(qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue());
			if((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue())>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", qysjjdzb);
			}
			if (((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%
		if (qyxdSWKC.doubleValue() == 0.0 && qyxdcbljh.doubleValue() == 0.0) {
			record.set("sjxdzb", "100%");
			record.set("sjxdzbbool", "1");
		} else {
			String qysjxdzb = nf.format(qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue());
			if((qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue())>=1){
				record.set("sjxdzb", "100%");
			}else {
				record.set("sjxdzb", qysjxdzb);
			}
			if (((qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue()) - (1.0 / 3.0)) >= 0) {
				record.set("sjxdzbbool", "1");
			} else {
				record.set("sjxdzbbool", "0");
			}
		}

		// 实际小麦库存占计划数%(其他 没有小麦为空)
		record.set("sjxmzb", "");
		record.set("sjxmzbbool", "1");

		return record;
	}

	public static Record shishuqiyeshiwukucun(String qyzzjgdm, HashMap<String, Object> map) {
		String year=(String)map.get("year");
		Record record = new Record();
		// 得到市属企业名称
		Record qiyemc = Db.findFirst("select qymc from tz_qiye where qyzzjgdm=?", qyzzjgdm);
		if(qyzzjgdm.equals("134967560")){
			record.set("qymc", "铁心桥粮库");
		}else if(qyzzjgdm.equals("660696497")){
			record.set("qymc", "下关粮库");
		}
		record.set("qyzzjgdm", qyzzjgdm);
		// 得到市属企业下的储备粮计划
		Record qiyejhs = Db.findFirst(
				"select sum(cbljh_xm) xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi = ? and  ccqy=? and niandu=?",map.get("xingzhi"),qyzzjgdm, year);
		BigDecimal qyxmcbljh = qiyejhs.get("xm", new BigDecimal(0.0));
		BigDecimal qyjdcbljh = qiyejhs.get("jd", new BigDecimal(0.0));
		BigDecimal qyxdcbljh = qiyejhs.get("xd", new BigDecimal(0.0));
		double qyxjcbljh = (qyxmcbljh.doubleValue() + qyjdcbljh.doubleValue()+qyxdcbljh.doubleValue())/1000;
		record.set("xmcbljh", qyxmcbljh.doubleValue()/1000);
		record.set("jdcbljh", qyjdcbljh.doubleValue()/1000);
		record.set("xdcbljh", qyxdcbljh.doubleValue()/1000);
		record.set("xjcbljh", qyxjcbljh);
		// 得到该企业实物库存
		Record qyxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  kc_CurrentStock k where k.vGrainSubTypeCode like '111%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		Record qyjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  kc_CurrentStock k where k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		Record qyxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  kc_CurrentStock k where k.vGrainSubTypeCode like '1131%' and k.vGrainPropertyCode = ? and k.qyzzjgdm=?",map.get("xingzhi"),qyzzjgdm);
		BigDecimal qyxmSWKC =qyxmRrcord.get("xm",new BigDecimal(0.0));
		BigDecimal qyjdSWKC = qyjdRrcord.get("jd",new BigDecimal(0.0));
		BigDecimal qyxdSWKC = qyxdRrcord.get("xd",new BigDecimal(0.0));
		double qyxjSWKC = (qyxmSWKC.doubleValue() + qyjdSWKC.doubleValue()+qyxdSWKC.doubleValue())/1000;
		record.set("xmswkc", qyxmSWKC.doubleValue()/1000)
			.set("jdswkc", qyjdSWKC.doubleValue()/1000)
			.set("xdswkc", qyxdSWKC.doubleValue()/1000)
			.set("xjswkc", qyxjSWKC);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		// 实际库存占计划数%
		String qysjkczb="100%";
		if(qyxjSWKC==0.0&&qyxjcbljh==0.0){
			record.set("sjkczb", qysjkczb);
			record.set("sjkczbbool", "1");
		}else {
			qysjkczb = nf.format(qyxjSWKC / qyxjcbljh);
			if((qyxjSWKC / qyxjcbljh)>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", qysjkczb);
			}
			if (((qyxjSWKC / qyxjcbljh) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String qysjjdzb="100%";
		if((qyjdSWKC.doubleValue())==0.0&&(qyjdcbljh.doubleValue())==0.0){
			record.set("sjjdzb", qysjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			qysjjdzb = nf.format(qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue());
		
			if((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue())>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", qysjjdzb);
			}
			if (((qyjdSWKC.doubleValue() / qyjdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%（市属没有籼稻为空）
		
		String qysjxdzb="100%";
		if((qyxdSWKC.doubleValue())==0.0&&(qyxdcbljh.doubleValue())==0.0){
			record.set("sjxdzb", qysjxdzb);
			record.set("sjxdzbbool", "1");
		}else {
			qysjxdzb = nf.format(qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue());
		
			if((qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue())>=1){
				record.set("sjxdzb", "100%");
			}else {
				record.set("sjxdzb", qysjxdzb);
			}
			if (((qyxdSWKC.doubleValue() / qyxdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjxdzbbool", "1");
			} else {
				record.set("sjxdzbbool", "0");
			}
		}
		// 实际小麦库存占计划数%
		String qysjxmzb="100%";
		if((qyxmSWKC.doubleValue())==0.0&&(qyxmcbljh.doubleValue())==0.0){
			record.set("sjxmzb", qysjxmzb);
			record.set("sjxmzbbool", "1");
		}else {
			qysjxmzb = nf.format(qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue());
			if((qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue())>=1){
				record.set("sjxmzb", "100%");
			}else {
			record.set("sjxmzb", qysjxmzb);
			}
			if (((qyxmSWKC.doubleValue() / qyxmcbljh.doubleValue()) - (1.0 / 2.0)) >= 0) {
				record.set("sjxmzbbool", "1");
			} else {
				record.set("sjxmzbbool", "0");
			}
		}
		return record;
	}

	public static List<Record> quanshiShiWuKuCun(String year) {
		List<Record> quanshilist = new ArrayList<>();
		Record hejiRecord = new Record();
		Record xmRecord = new Record();
		Record jdRecord = new Record();
		Record xdRecord = new Record();
		Record record = new Record();
		record.set("quming", "南京市");
		record.set("xzqhdm", "nanjingshi");
		// 得到全市储备粮计划数
		Record quanshijhs = Db.findFirst(
				"select sum(cbljh_xm) xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where niandu=?",
				year);
		double qsxmcbljh = 0.0;
		double qsjdcbljh = 0.0;
		double qsxdcbljh = 0.0;
		if (quanshijhs.get("xm") != null) {
			qsxmcbljh = ((BigDecimal) quanshijhs.get("xm")).doubleValue();
		}
		if (quanshijhs.get("jd") != null) {
			qsjdcbljh = ((BigDecimal) quanshijhs.get("jd")).doubleValue();
		}
		if (quanshijhs.get("xd") != null) {
			qsxdcbljh = ((BigDecimal) quanshijhs.get("xd")).doubleValue();
		}
		double qsxjcbljh = qsxmcbljh + qsjdcbljh + qsxdcbljh;
		record.set("xmcbljh", qsxmcbljh);
		record.set("jdcbljh", qsjdcbljh);
		record.set("xdcbljh", qsxdcbljh);
		record.set("xjcbljh", qsxjcbljh);

		// 得到全市实物库存
		Record qsxmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode='111' and k.vGrainPropertyCode in(132,133)");
		Record qsjdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode='430' and k.vGrainPropertyCode in(132,133)");
		Record qsxdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode='431' and k.vGrainPropertyCode in(132,133)");
		double qsxmSWKC = 0.0;
		double qsjdSWKC = 0.0;
		double qsxdSWKC = 0.0;
		if (qsxdRrcord.get("xm") != null) {
			qsxmSWKC = ((BigDecimal) qsxmRrcord.get("xm")).doubleValue();
		}
		if (qsjdRrcord.get("jd") != null) {
			qsjdSWKC = ((BigDecimal) qsjdRrcord.get("jd")).doubleValue();
		}
		if (qsxdRrcord.get("xd") != null) {
			qsxdSWKC = ((BigDecimal) qsxdRrcord.get("xd")).doubleValue();
		}
		double qsxjSWKC = qsxmSWKC + qsjdSWKC + qsxdSWKC;
		record.set("xmswkc", qsxmSWKC).set("jdswkc", qsjdSWKC).set("xdswkc", qsxdSWKC).set("xjswkc", qsxjSWKC);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		// 实际库存占计划数%
		String qssjkczb = nf.format(qsxjSWKC / qsxjcbljh);
		record.set("sjkczb", qssjkczb);
		if (((qsxjSWKC / qsxjcbljh) - 0.5) >= 0) {
			record.set("sjkczbbool", true);
		} else {
			record.set("sjkczbbool", false);
		}

		// 实际粳稻库存占计划数%
		String qssjjdzb = nf.format(qsjdSWKC / qsjdcbljh);
		record.set("sjjdzb", qssjjdzb);
		if (((qsjdSWKC / qsjdcbljh) - (1.0 / 4.0)) >= 0) {
			record.set("sjjdzbbool", true);
		} else {
			record.set("sjjdzbbool", false);
		}

		// 实际籼稻库存占计划数%
		String qssjxdzb = nf.format(qsxdSWKC / qsxdcbljh);
		record.set("sjxdzb", qssjxdzb);
		if (((qsxdSWKC / qsxdcbljh) - (1.0 / 3.0)) >= 0) {
			record.set("sjxdzbbool", true);
		} else {
			record.set("sjxdzbbool", false);
		}

		// 实际小麦库存占计划数%
		String qssjxmzb = nf.format(qsxmSWKC / qsxmcbljh);
		record.set("sjxmzb", qssjxmzb);
		if (((qsxmSWKC / qsxmcbljh) - (1.0 / 2.0)) >= 0) {
			record.set("sjxmzbbool", true);
		} else {
			record.set("sjxmzbbool", false);
		}

		hejiRecord.set("quming", "南京市").set("qymc", "").set("pz", "合计").set("cbljh", qsxjcbljh).set("sjkc", qsxjSWKC)
				.set("sjkczb", qssjkczb).set("sjjdzb", qssjjdzb).set("sjxdzb", qssjxdzb).set("sjxmzb", qssjxmzb);
		xmRecord.set("quming", "南京市").set("qymc", "").set("pz", "小麦").set("cbljh", qsxmcbljh).set("sjkc", qsxmSWKC)
				.set("sjkczb", qssjkczb).set("sjjdzb", qssjjdzb).set("sjxdzb", qssjxdzb).set("sjxmzb", qssjxmzb);
		jdRecord.set("quming", "南京市").set("qymc", "").set("pz", "粳稻").set("cbljh", qsjdcbljh).set("sjkc", qsjdSWKC)
				.set("sjkczb", qssjkczb).set("sjjdzb", qssjjdzb).set("sjxdzb", qssjxdzb).set("sjxmzb", qssjxmzb);
		xdRecord.set("quming", "南京市").set("qymc", "").set("pz", "籼稻").set("cbljh", qsxdcbljh).set("sjkc", qsxdSWKC)
				.set("sjkczb", qssjkczb).set("sjjdzb", qssjjdzb).set("sjxdzb", qssjxdzb).set("sjxmzb", qssjxmzb);
		quanshilist.add(hejiRecord);
		quanshilist.add(xmRecord);
		quanshilist.add(jdRecord);
		quanshilist.add(xdRecord);
		return quanshilist;

	}

	public static Record quShiWuKuCun(String xzqhdm, HashMap<String, Object> map) {
		String year=(String)map.get("year");
		Record record = new Record();
		// 得到区域名称
		Record quyu = Db.findFirst("select * from njpt_diqu where xzqhdm=?", xzqhdm);
		
		String quming = Db.findFirst("select * from fw_area where id=?",quyu.getStr("areaid")).getStr("name");
		
		
		record.set("quming", quming);
		record.set("xzqhdm", xzqhdm);

		// 得到区的小麦，粳稻，籼稻 --计划数
		Record jihuashu = Db.findFirst(
				"select sum(cbljh_xm) xm,sum(cbljh_jd) as jd,sum(cbljh_xd) as xd from njpt_chubeiliangjihua where xingzhi = ? and  quming=? and niandu=?",map.get("xingzhi"),
				xzqhdm, year);
		BigDecimal xmcbljh = jihuashu.get("xm",new BigDecimal(0.0));
		BigDecimal jdcbljh = jihuashu.get("jd",new BigDecimal(0.0));
		BigDecimal xdcbljh = jihuashu.get("xd",new BigDecimal(0.0));
		double xjcbljh = (xmcbljh.doubleValue() + jdcbljh.doubleValue() + xdcbljh.doubleValue())/1000;
		record.set("xmcbljh", xmcbljh.doubleValue()/1000)
			.set("jdcbljh", jdcbljh.doubleValue()/1000)
			.set("xdcbljh", xdcbljh.doubleValue()/1000)
			.set("xjcbljh", xjcbljh);
		// 得到区的小麦，粳稻，籼稻 --实物库存
		Record xmRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  where d.xzqhdm=? and k.vGrainSubTypeCode like '111%' and k.vGrainPropertyCode = ? ",
				xzqhdm,map.get("xingzhi"));
		Record jdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  where d.xzqhdm=? and k.vGrainSubTypeCode like '1132%' and k.vGrainPropertyCode = ? ",
				xzqhdm,map.get("xingzhi"));
		Record xdRrcord = Db.findFirst(
				"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  where d.xzqhdm=? and k.vGrainSubTypeCode like '1131%' and k.vGrainPropertyCode = ? ",
				xzqhdm,map.get("xingzhi"));
		BigDecimal xmSWKC = xmRrcord.get("xm",new BigDecimal(0.0));
		BigDecimal jdSWKC = jdRrcord.get("jd",new BigDecimal(0.0));
		BigDecimal xdSWKC = xdRrcord.get("xd",new BigDecimal(0.0));
		double xjSWKC = (xmSWKC.doubleValue() + jdSWKC.doubleValue() + xdSWKC.doubleValue())/1000;
		record.set("xmswkc", xmSWKC.doubleValue()/1000)
			.set("jdswkc", jdSWKC.doubleValue()/1000)
			.set("xdswkc", xdSWKC.doubleValue()/1000)
			.set("xjswkc", xjSWKC);
		// 转成百分比
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);// 设置保留小数位
		nf.setRoundingMode(RoundingMode.HALF_UP); // 设置舍入模式
		// String zlbl = nf.format(budiyu);

		// 实际库存占计划数%
		String sjkczb="100%";
		if(xjSWKC==0.0&&xjcbljh==0.0){
			record.set("sjkczb", sjkczb);
			record.set("sjkczbbool", "1");
		}else {
			
			sjkczb = nf.format(xjSWKC / xjcbljh);
			if((xjSWKC / xjcbljh)>=1){
				record.set("sjkczb", "100%");
			}else {
				record.set("sjkczb", sjkczb);
			}
			if (((xjSWKC / xjcbljh) - 0.5) >= 0) {
				record.set("sjkczbbool", "1");
			} else {
				record.set("sjkczbbool", "0");
			}
		}
		// 实际粳稻库存占计划数%
		String sjjdzb="100%";
		if((jdSWKC.doubleValue())==0.0&&(jdcbljh.doubleValue())==0.0){
			record.set("sjjdzb", sjjdzb);
			record.set("sjjdzbbool", "1");
		}else {
			sjjdzb = nf.format(jdSWKC.doubleValue() / jdcbljh.doubleValue());
			if((jdSWKC.doubleValue() / jdcbljh.doubleValue())>=1){
				record.set("sjjdzb", "100%");
			}else {
				record.set("sjjdzb", sjjdzb);
			}
			if (((jdSWKC.doubleValue() / jdcbljh.doubleValue()) - (1.0 / 4.0)) >= 0) {
				record.set("sjjdzbbool", "1");
			} else {
				record.set("sjjdzbbool", "0");
			}
		}
		// 实际籼稻库存占计划数%
		String sjxdzb="100%";
		if ((xdSWKC.doubleValue())==0.0&&(xdcbljh.doubleValue())==0.0) {
			record.set("sjxdzb", sjxdzb);
			record.set("sjxdzbbool", "1");
		}else {
			sjxdzb = nf.format(xdSWKC.doubleValue() / xdcbljh.doubleValue());
			if((xdSWKC.doubleValue() / xdcbljh.doubleValue())>=1){
				record.set("sjxdzb", "100%");
			}else {
				record.set("sjxdzb", sjxdzb);
			}
			if (((xdSWKC.doubleValue() / xdcbljh.doubleValue()) - (1.0 / 3.0)) >= 0) {
				record.set("sjxdzbbool", "1");
			} else {
				record.set("sjxdzbbool", "0");
			}
		}
		// 实际小麦库存占计划数%
		String sjxmzb="100%";
		if((xmSWKC.doubleValue())==0.0&&(xmcbljh.doubleValue())==0.0){
			record.set("sjxmzb", sjxmzb);
			record.set("sjxmzbbool", "1");
		}else {
			
			sjxmzb = nf.format(xmSWKC.doubleValue() / xmcbljh.doubleValue());
		
			if((xmSWKC.doubleValue() / xmcbljh.doubleValue())>=1){
				record.set("sjxmzb", "100%");
			}else {
				record.set("sjxmzb", sjxmzb);
			}
			if (((xmSWKC.doubleValue() / xmcbljh.doubleValue()) - (1.0 / 2.0)) >= 0) {
				record.set("sjxmzbbool", "1");
			} else {
				record.set("sjxmzbbool", "0");
			}
		}
		return record;

	}
	/**
	 * 储备粮计划（点击加号时显示详情）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakanxiangxich(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and ccqy = ?", "ccqy");
		p.put("and cckd = ?", "cckd");
		p.put("and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select *",
				"from njpt_chubeiliangjihua where 1=1 " + s.getSql()+" order by ccch", s.getParam().toArray(new Object[0]));
		for (Record r : page.getList()) {
			Object qyzzjgdm = r.get("ccqy");
			Object kdbm = r.get("cckd");
			Object cfbm = r.get("ccch");
			Object lsxzbm = r.get("xingzhi");
			Record qymc = Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?", qyzzjgdm);
			if (qymc != null) {
				r.set("ccqy", qymc.getStr("qymc"));
				// r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("quming", qymc.getStr("xian"));
			}
			Record kdmc = Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?", kdbm, qyzzjgdm);
			if (kdmc != null) {
				r.set("cckd", kdmc.getStr("kdmc"));
			}
			Record cfmc = Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ", qyzzjgdm,
					kdbm, cfbm);
			if (cfmc != null) {
				r.set("ccch", cfmc.getStr("cfmc"));
			}
			Record xz = Db.findFirst("select vCode,vGrainProperties from tz_GrainProperties where vCode=?", lsxzbm);
			if (xz != null) {
				r.set("xingzhi", xz.getStr("vGrainProperties"));
			}
		}

		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	
	
	/**
	 *  储备粮汇总 （仓号为几个）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chubeilianghuizongb(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and cbl.niandu = ?", "niandu");
		p.put("and d.xzqhdm = ?", "quming");
		p.put("and q.qyzzjgdm = ?", "ccqy");
		p.put("and k.kdbm = ?", "cckd");
		p.put("and cbl.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select d.xzqhdm,q.qyzzjgdm,k.kdbm, COUNT(c.cfbm) as ccchsl,SUM(ISNULL(cbljh_xm,0)) as cbljh_xm,sum(ISNULL(cbljh_jd,0)) as cbljh_jd,sum(ISNULL(cbljh_xd,0)) as cbljh_xd,sum(ISNULL(cbljh_xj,0)) as cbljh_xj ",
				" from njpt_diqu d LEFT JOIN tz_qiye q on d.xzqhdm=q.xzqhdm LEFT JOIN tz_kudian k ON q.qyzzjgdm=k.qyzzjgdm LEFT JOIN tz_cangfang c on k.qyzzjgdm=c.qyzzjgdm and k.kdbm=c.kdbm LEFT JOIN  njpt_chubeiliangjihua cbl on c.qyzzjgdm=cbl.ccqy and c.kdbm=cbl.cckd and c.cfbm=cbl.ccch where 1=1 " + s.getSql() + " group by d.orderno,d.xzqhdm,q.qyzzjgdm,k.kdbm order by d.orderno ",
				s.getParam().toArray(new Object[0]));
		for (Record r : page.getList()) {
			Object qyzzjgdm = r.get("qyzzjgdm");
			Object kdbm = r.get("kdbm");
			// Object lsxzbm=r.get("lsxz");
			Record qymc = Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?", qyzzjgdm);
			if (qymc != null) {
				r.set("ccqy", qymc.getStr("qymc"));
				r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("quming", qymc.getStr("xian"));
			}
			Record kdmc = Db.findFirst("select kdmc,kdbm from tz_kudian where kdbm=? and qyzzjgdm=?", kdbm, qyzzjgdm);
			if (kdmc != null) {
				r.set("cckd", kdmc.getStr("kdmc"));
				r.set("kdbm", kdmc.getStr("kdbm"));
			}else{
				r.set("cckd", "");
				r.set("kdbm", "");
			}
			r.set("xingzhi", "");
		}
/*
 * Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and quming = ?", "quming");
		p.put("and ccqy = ?", "ccqy");
		p.put("and cckd = ?", "cckd");
		p.put("and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select ccqy,cckd, COUNT(ccch) as ccchsl,SUM(cbljh_xm) as cbljh_xm,sum(cbljh_jd) as cbljh_jd,sum(cbljh_xd) as cbljh_xd,sum(cbljh_xj) as cbljh_xj ",
				" from njpt_chubeiliangjihua where 1=1 " + s.getSql() + " group by ccqy,cckd",
				s.getParam().toArray(new Object[0]));
		for (Record r : page.getList()) {
			Object qyzzjgdm = r.get("ccqy");
			Object kdbm = r.get("cckd");
			// Object lsxzbm=r.get("lsxz");
			Record qymc = Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?", qyzzjgdm);
			if (qymc != null) {
				r.set("ccqy", qymc.getStr("qymc"));
				r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("quming", qymc.getStr("xian"));
			}
			Record kdmc = Db.findFirst("select kdmc,kdbm from tz_kudian where kdbm=? and qyzzjgdm=?", kdbm, qyzzjgdm);
			if (kdmc != null) {
				r.set("cckd", kdmc.getStr("kdmc"));
				r.set("kdbm", kdmc.getStr("kdbm"));
			}
			r.set("xingzhi", "");
		}

 */
		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}

	public static Page<Record> shengchenghuizongb(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.quming = ?", "quming");
		p.put("and c.ccqy = ?", "ccqy");
		p.put("and c.cckd = ?", "cckd");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and fw.name = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select fw.name,q.qymc,k.kdmc,cf.cfmc,g.vGrainProperties,c.* ",
				" from njpt_chubeiliangjihua c inner JOIN njpt_diqu d on c.quming=d.xzqhdm inner join fw_area fw on d.areaid=fw.id inner join tz_qiye q on q.qyzzjgdm=c.ccqy INNER JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm INNER JOIN tz_cangfang cf on c.ccqy=cf.qyzzjgdm and c.cckd=cf.kdbm and c.ccch=cf.cfbm INNER JOIN tz_GrainProperties g on c.xingzhi=g.vCode where 1=1 " + s.getSql()+" order by c.status desc,d.orderno,c.ccqy,c.cckd,c.ccch,c.xingzhi", s.getParam().toArray(new Object[0]));
		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	
	public static Page<Record> cblglzxShenHe(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.quming = ?", "quming");
		p.put("and c.ccqy = ?", "ccqy");
		p.put("and c.cckd = ?", "cckd");
		p.put("and c.status = ?", "status");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select fw.name,q.qymc,k.kdmc,cf.cfmc,c.* ",
				" from njpt_chubeiliangjihua c inner JOIN njpt_diqu d on c.quming=d.xzqhdm inner join fw_area fw on d.areaid=fw.id inner join tz_qiye q on q.qyzzjgdm=c.ccqy INNER JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm INNER JOIN tz_cangfang cf on c.ccqy=cf.qyzzjgdm and c.cckd=cf.kdbm and c.ccch=cf.cfbm  where 1=1 and c.xingzhi='122' " + s.getSql()+" order by c.status desc,d.orderno,c.ccqy,c.cckd,c.ccch", s.getParam().toArray(new Object[0]));
		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	/**
	 * 获取储备粮计划列表
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findcbljhbohui2(HashMap<String, Object> param) throws Exception {
		Page<Record> page=cblglzxchakan(param);
		for(Record record:page.getList()){
				Record liucheng=  Db.findFirst("select * from fw_user where id=? ",UserKit.currentUserInfo().getUser().getString("id"));
				if(liucheng!=null){
					Record dep= Db.findFirst("select * from fw_dep  where id=? ",liucheng.getStr("id"));
					if(dep!=null){
						record.set("realname", dep.getStr("name"));	
					}
			}
			
		}
		return page;
	}
	public static Page<Record> cblglzxchakan(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.id = ?", "id");
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.ccqy = ?", "ccqy");
		p.put("and c.cckd = ?", "cckd");
		p.put("and fw.name=?", "quming");
		p.put("and fw.name = ?", "xian");
		p.put("and fw.name = ?", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select fw.name,q.qymc,k.kdmc,cf.cfmc,c.* ",
				" from njpt_chubeiliangjihua c inner JOIN njpt_diqu d on c.quming=d.xzqhdm inner join fw_area fw on d.areaid=fw.id inner join tz_qiye q on q.qyzzjgdm=c.ccqy INNER JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm INNER JOIN tz_cangfang cf on c.ccqy=cf.qyzzjgdm and c.cckd=cf.kdbm and c.ccch=cf.cfbm  where 1=1  " + s.getSql()+" order by c.status desc,d.orderno,c.ccqy,c.cckd,c.ccch", s.getParam().toArray(new Object[0]));
		return page;

		/*
		 * String niandu=(String) map.get("niandu"); return getDataList(niandu);
		 */
	}
	public static Page<Record> querycbljhrenwuById(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select * ",
				"from njpt_chubeiliangjihua_renwu where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));

		return page;
	}
	public static Page<Record> querycbljhById(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select * ",
				"from njpt_chubeiliangjihua where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		for (Record r : page.getList()) {
			Object qyzzjgdm = r.get("ccqy");
			Object kdbm = r.get("cckd");
			Object cfbm = r.get("ccch");
			Record qymc = Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?", qyzzjgdm);
			if (qymc != null) {
				r.set("ccqy", qymc.getStr("qymc"));
				r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("quyumc", qymc.getStr("xian"));
			}
			Record kdmc = Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?", kdbm, qyzzjgdm);
			if (kdmc != null) {
				r.set("kudianmc", kdmc.getStr("kdmc"));
			}
			Record cfmc = Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ", qyzzjgdm,
					kdbm, cfbm);
			if (cfmc != null) {
				r.set("canghaomc", cfmc.getStr("cfmc"));
			}

		}

		return page;
	}
	
	public static Page<Record> querycbljhbohui(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select * ",
				"from njpt_chubeiliangjihua where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		return page;
	}
	public static String[] areaCode = new String[] { "320115", "320111", "320116", "320124", "320125", "210008" };
	public static String[] areaCode1 = new String[] { "江宁区", "浦口区", "六合区", "溧水区", "高淳区", "市区" };

	public static String[] xingzhi = new String[] { "中央储备", "省级储备", "市级储备", "区级储备", "临时储备" };
	public static String[] xingzhi1 = new String[] { "zycb_", "shenjcb_", "shijicb_", "qujcb_", "lscb_" };
	public static String[] pinzhong = new String[] { "小麦", "籼稻", "粳稻" };
	public static String[] pinzhong1 = new String[] { "xm", "xd", "jd" };

	private static List<Record> getDataList(String niandu) {

		List<Record> dataList = new ArrayList<Record>();

		// 先遍历区名
		for (int i = 0; i <= DIQU_NUM; i++) {
			double total_total = 0;
			Record sub_list = new Record();
			// 再遍历性质
			for (int j = 0; j <= XINGZHI_NUM; j++) {
				// 最后遍历品种
				for (int k = 0; k <= PINZHONG_NUM; k++) {
					double total_num = getBlockNumner(niandu, areaCode[i], xingzhi[j], pinzhong[k]);
					sub_list.set(xingzhi1[j] + pinzhong1[k], total_num);
					total_total += total_num;
				}
			}
			sub_list.set("quming", areaCode1[i]);
			sub_list.set("niandu", niandu);
			sub_list.set("zjh", total_total);
			dataList.add(sub_list);
		}
		return dataList;
	}

	private static double getBlockNumner(String niandu, String quming, String xingzhi, String pinzhong) {
		Record total_jhsl = Db.findFirst(
				"select sum(jhsl) as total_jhsl from njpt_chubeiliangjihua where niandu = ? and quming = ? and xingzhi=? and pinzhong=?",
				niandu, quming, xingzhi, pinzhong);
		if (total_jhsl != null) {
			return total_jhsl.get("total_jhsl");
		} else {

			return 0;
		}
	}

	public static void HandleXingZhi(Record record, Record qu) {
		switch (record.getStr("xingzhi")) {
		case "中央储备":
			break;
		case "省级储备":

			break;
		case "市级储备":

			break;
		case "区级储备":

			break;
		case "临时储备":

			break;

		}

	}

	public static void HandleDiQu(Record record) {
		switch (record.getStr("pinzhong")) {
		case "小麦":

			break;
		case "籼稻":

			break;
		case "粳稻":

			break;
		}

	}
	
	public static Record cbljhrenwuxianji(HashMap<String, Object> map) throws Exception {
		/*
		 * double zjh; double zjh_xm; double zjh_xd; double zjh_jd;
		 */
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,15, "select aa.* ",
				" from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi = '123' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
			
		return page.getList().get(0);

	}
	public static Page<Record> cbljhrenwuzsg(HashMap<String, Object> map) throws Exception {
		/*
		 * double zjh; double zjh_xm; double zjh_xd; double zjh_jd;
		 */
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and xzqhdm = ?", "xzqhdm");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and xingzhi = ?", "xingzhi");
		p.put("and pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select * ",
				"from njpt_chubeiliangjihua_renwu where 1=1  " + s.getSql() + " order by niandu desc,orderno asc",
				s.getParam().toArray(new Object[0]));
		return page;

	}
	/**
	 * 遍历企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqiye(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_chubeiliangjihua_diqu  where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	public static Page<Record> selectQiYe(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and b.xzqhdm=?", "xzqhdm");
		p.put("and a.name = ?", "name");
		p.put("and c.niandu = ?", "niandu");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.qyzzjgdm,c.qymc ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua_diqu c on a.name=c.diqu  where 1=1 " + s.getSql() + " ORDER BY b.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static Page<Record> selectallQiYe(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and b.xzqhdm=?", "xzqhdm");
		p.put("and a.name = ?", "name");
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.qyzzjgdm,c.qymc ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join tz_qiye c on c.xzqhdm=b.xzqhdm  where 1=1  " + s.getSql() + " GROUP BY b.orderno,c.qyzzjgdm,c.qymc ORDER BY b.orderno,c.qyzzjgdm ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static Page<Record> selectQYbydm(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.* ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join tz_qiye c on c.xzqhdm=b.xzqhdm  where 1=1  " + s.getSql() + "  ORDER BY b.orderno,c.qyzzjgdm ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static Page<Record> selectorderno(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and a.name = ?", "diqu");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select b.orderno,c.* ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join tz_qiye c on c.xzqhdm=b.xzqhdm inner join tz_kudian d on c.qyzzjgdm=d.qyzzjgdm where 1=1 and d.kdlxbh<>'06' " + s.getSql() + " ORDER BY b.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static Page<Record> selectqymc(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.* ",
				" from  tz_qiye c where 1=1 " + s.getSql() + " ORDER BY qyzzjgdm",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static Page<Record> selectQY(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and a.xzqhdm=?", "xzqhdm");
		p.put("and a.name = ?", "name");
		//p.put("and areaid like ?", "areaid","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select b.*,a.name",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid where 1=1 " + s.getSql() + " ORDER BY a.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
		
		
	}
	public static Page<Record> cbljhrenwuzsg1(HashMap<String, Object> map) throws Exception {
		/*
		 * double zjh; double zjh_xm; double zjh_xd; double zjh_jd;
		 */
		Param p = new Param();
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.xzqhdm = ?", "xzqhdm");
		p.put("and a.name =?", "diqu");
		p.put("and c.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select e.qymc,c.*,a.name,d.vGrainProperties ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_renwu c on b.xzqhdm=c.xzqhdm inner JOIN njpt_chubeiliangjihua_diqu f on c.qyzzjgdm=f.qyzzjgdm INNER JOIN tz_GrainProperties d on c.xingzhi=d.vCode INNER JOIN tz_qiye e on c.qyzzjgdm=e.qyzzjgdm where 1=1 " + s.getSql() + " order by f.orderno asc",
				s.getParam().toArray(new Object[0]));
	
		return page;

	}
	public static Page<Record> cbljhrenwu(HashMap<String, Object> map) throws Exception {
		/*
		 * double zjh; double zjh_xm; double zjh_xd; double zjh_jd;
		 */
		Param p = new Param();
		p.put("and a.niandu = ?", "niandu");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xingzhi = ?", "xingzhi");
		p.put("and c.name=?", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select a.*,c.name ",
				" from njpt_chubeiliangjihua_renwu a inner join njpt_diqu b on a.xzqhdm=b.xzqhdm inner join fw_area c on b.areaid=c.id where 1=1 and xingzhi ='122' " + s.getSql() + " order by a.niandu desc,a.orderno asc",
				s.getParam().toArray(new Object[0]));
		// List<Record> records = page.getList();
		// for (int i = 0; i < records.size(); i++) {
		// //records.get(i).set("ccqy", Db.findFirst("select qymc
		// from tz_qiye where
		// qyzzjgdm=?",records.get(i).get("ccqy")).get("qymc"));
		// //records.get(i).set("cckd", Db.findFirst("select kdmc
		// from tz_kudian where qyzzjgdm=? and
		// kdbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd")).get("kdmc"));
		// //records.get(i).set("ccch", Db.findFirst("select cfmc
		// from tz_cangfang where qyzzjgdm=? and kdbm=? and
		// cfbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd"),records.get(i).get("ccch")).get("cfmc"));
		// zjh_xm=(double)records.get(i).get("zycb_xm")+(double)records.get(i).get("shenjcb_xm")+(double)records.get(i).get("shijcb_xm")+(double)records.get(i).get("qjcb_xm")+(double)records.get(i).get("lscb_xm");
		// zjh_xd=(double)records.get(i).get("zycb_xd")+(double)records.get(i).get("shenjcb_xd")+(double)records.get(i).get("shijcb_xd")+(double)records.get(i).get("qjcb_xd")+(double)records.get(i).get("lscb_xd");
		// zjh_jd=(double)records.get(i).get("zycb_jd")+(double)records.get(i).get("shenjcb_jd")+(double)records.get(i).get("shijcb_jd")+(double)records.get(i).get("qjcb_jd")+(double)records.get(i).get("lscb_jd");
		// zjh=zjh_xm+zjh_xd+zjh_jd;
		// records.get(i).set("zjh_xm", zjh_xm);
		// records.get(i).set("zjh_xd", zjh_xd);
		// records.get(i).set("zjh_jd", zjh_jd);
		// records.get(i).set("zjh", zjh);
		// }

		return page;

	}
	public static Page<Record> selectCBLJH(HashMap<String, Object> map) throws Exception {
		/*
		 * double zjh; double zjh_xm; double zjh_xd; double zjh_jd;
		 */
		Param p = new Param();
		p.put("and ccqy like ?", "ccqy", "%%%s%%");
		p.put("and cckd like ?", "cckd", "%%%s%%");
		p.put("and ccch like ?", "ccch", "%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select * ",
				"from njpt_chubeiliangjihua where pdyh=1 " + s.getSql() + " order by niandu desc",
				s.getParam().toArray(new Object[0]));
		// List<Record> records = page.getList();
		// for (int i = 0; i < records.size(); i++) {
		// //records.get(i).set("ccqy", Db.findFirst("select qymc
		// from tz_qiye where
		// qyzzjgdm=?",records.get(i).get("ccqy")).get("qymc"));
		// //records.get(i).set("cckd", Db.findFirst("select kdmc
		// from tz_kudian where qyzzjgdm=? and
		// kdbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd")).get("kdmc"));
		// //records.get(i).set("ccch", Db.findFirst("select cfmc
		// from tz_cangfang where qyzzjgdm=? and kdbm=? and
		// cfbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd"),records.get(i).get("ccch")).get("cfmc"));
		// zjh_xm=(double)records.get(i).get("zycb_xm")+(double)records.get(i).get("shenjcb_xm")+(double)records.get(i).get("shijcb_xm")+(double)records.get(i).get("qjcb_xm")+(double)records.get(i).get("lscb_xm");
		// zjh_xd=(double)records.get(i).get("zycb_xd")+(double)records.get(i).get("shenjcb_xd")+(double)records.get(i).get("shijcb_xd")+(double)records.get(i).get("qjcb_xd")+(double)records.get(i).get("lscb_xd");
		// zjh_jd=(double)records.get(i).get("zycb_jd")+(double)records.get(i).get("shenjcb_jd")+(double)records.get(i).get("shijcb_jd")+(double)records.get(i).get("qjcb_jd")+(double)records.get(i).get("lscb_jd");
		// zjh=zjh_xm+zjh_xd+zjh_jd;
		// records.get(i).set("zjh_xm", zjh_xm);
		// records.get(i).set("zjh_xd", zjh_xd);
		// records.get(i).set("zjh_jd", zjh_jd);
		// records.get(i).set("zjh", zjh);
		// }

		return page;

	}

	public static Page<Record> chakanCBLJH(HashMap<String, Object> map) throws Exception {
		double zjh;
		double zjh_xm;
		double zjh_xd;
		double zjh_jd;
		Param p = new Param();
		p.put("and ccqy=?", "ccqy");
		/* p.put("and cckd=?", "cckd"); */
		p.put("and quming=?", "quming");
		p.put("and niandu=?", "nian");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select *",
				"from njpt_chubeiliangjihua where pdyh=2" + s.getSql(), s.getParam().toArray(new Object[0]));
		List<Record> records = page.getList();
		for (int i = 0; i < records.size(); i++) {
			// records.get(i).set("ccqy", Db.findFirst("select qymc
			// from tz_qiye where
			// qyzzjgdm=?",records.get(i).get("ccqy")).get("qymc"));
			// records.get(i).set("cckd", Db.findFirst("select kdmc
			// from tz_kudian where qyzzjgdm=? and
			// kdbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd")).get("kdmc"));
			// records.get(i).set("ccch", Db.findFirst("select cfmc
			// from tz_cangfang where qyzzjgdm=? and kdbm=? and
			// cfbm=?",records.get(i).get("ccqy"),records.get(i).get("cckd"),records.get(i).get("ccch")).get("cfmc"));
			zjh_xm = (double) records.get(i).get("zycb_xm") + (double) records.get(i).get("shenjcb_xm")
					+ (double) records.get(i).get("shijcb_xm") + (double) records.get(i).get("qjcb_xm")
					+ (double) records.get(i).get("lscb_xm");
			zjh_xd = (double) records.get(i).get("zycb_xd") + (double) records.get(i).get("shenjcb_xd")
					+ (double) records.get(i).get("shijcb_xd") + (double) records.get(i).get("qjcb_xd")
					+ (double) records.get(i).get("lscb_xd");
			zjh_jd = (double) records.get(i).get("zycb_jd") + (double) records.get(i).get("shenjcb_jd")
					+ (double) records.get(i).get("shijcb_jd") + (double) records.get(i).get("qjcb_jd")
					+ (double) records.get(i).get("lscb_jd");
			zjh = zjh_xm + zjh_xd + zjh_jd;
			records.get(i).set("zjh_xm", zjh_xm);
			records.get(i).set("zjh_xd", zjh_xd);
			records.get(i).set("zjh_jd", zjh_jd);
			records.get(i).set("zjh", zjh);
		}

		return page;

	}

	public static Page<Record> selectCBLJHHZ(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and uuid=?", "uuid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select *",
				"from njpt_cbljh_linshib where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		return page;

	}

	public static Page<Record> liebiaoSWKC(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select *",
				"from kc_CurrentStock where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		for (Record r : page.getList()) {
			Object qyzzjgdm = r.get("qyzzjgdm");
			Object kdbm = r.get("kdmc");
			Object cfbm = r.get("vWareHouseCode");
			Object aojinabm = r.get("house");
			Object huoweibm = r.get("allocation");
			Object pinzhong = r.get("vGrainSubTypeCode");// 品种
			Object xingzhi = r.get("vGrainPropertyCode");// 粮食性质
			// 转换成企业名称和 地区名称
			Record qymc = Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?", qyzzjgdm);
			if (qymc != null) {
				r.set("qyzzjgdm", qymc.getStr("qymc"));
				r.set("szqy", qymc.getStr("xian"));
			}
			// 转换成库点名称
			Record kdmc = Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?", kdbm, qyzzjgdm);
			if (kdmc != null) {
				r.set("kdbm", kdmc.getStr("kdmc"));
			}
			// 转换成仓房名称
			Record cfmc = Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ", qyzzjgdm,
					kdbm, cfbm);
			if (cfmc != null) {
				r.set("vWareHouseCode", cfmc.getStr("cfmc"));
			}
			// 转换成廒间名称
			Record aojianmc = Db.findFirst(
					"select ajmc from tz_aojian where qyzzjgdm=? and kdbm=? and cfbm=? and ajbh=? ", qyzzjgdm, kdbm,
					cfbm, aojinabm);
			if (aojianmc != null) {
				r.set("house", aojianmc.getStr("ajmc"));
			}
			// 转换成货位名称
			Record huoweiMC = Db.findFirst(
					"select hwmc from tz_huowei where qyzzjgdm=? and kdbm=? and cfbm=? and ajbh=? and hwbh=? ",
					qyzzjgdm, kdbm, cfbm, aojinabm, huoweibm);
			if (aojianmc != null) {
				r.set("allocation", huoweiMC.getStr("hwmc"));
			}
			// 转换成品种名称
			Record pinzhongMC = Db.findFirst("select vName from tz_GrainType where vCode=?", pinzhong);
			if (pinzhongMC != null) {
				r.set("vGrainSubTypeCode", pinzhongMC.getStr("vName"));
			}
			// 转换成性质名称
			Record xingzhiMC = Db.findFirst("select vGrainProperties from tz_GrainProperties where vCode=?", xingzhi);
			if (xingzhiMC != null) {
				r.set("vGrainPropertyCode", xingzhiMC.getStr("vGrainProperties"));
			}
		}
		return page;

	}

	public static boolean saveCBLJH(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.set("id", SqlUtil.uuid());
		record.setColumns(map);
		record.set("pdyh", 1);
		boolean bool = Db.save("njpt_chubeiliangjihua", "id", record);
		return bool;

	}

	public static boolean saveQYCBLJH(HashMap<String, Object> map) throws Exception {
		Record record = new Record();
		record.set("id", SqlUtil.uuid());
		record.setColumns(map);
		record.set("pdyh", 2);
		boolean bool = Db.save("njpt_chubeiliangjihua", "id", record);
		return bool;

	}
	public static Ret chubeiliangrenwuheji(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Param p = new Param();
		p.put(" and xzqhdm = ?", "xzqhdm");
		p.put(" and niandu = ?", "niandu");
		p.put(" and xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select aa.* ",
				" from (select isnull(sum(cast(xm as int)),0) as cbljh_xm,isnull(sum(cast(jd as int)),0) cbljh_jd,isnull(sum(cast(xd as int)),0) cbljh_xd,isnull(sum(cast(xiaoji as int)),0) cbljh_xj from njpt_chubeiliangjihua_renwu where 1=1 "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
			return  ret.put("heji",page.getList().get(0));	
	
	}
	/**
	 * 得到所有一级企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> cblswkcqu(HashMap<String, Object> map) throws Exception {
		//取出所有 一级企业
		Param quParam = new Param();
		quParam.put("and b.xzqhdm = ?", "xzqhdm");
		quParam.put("and c.qyzzjgdm = ?", "yijiqiye");
		SqlAndParam sQu = SqlUtil.buildSql(quParam, map);
		Page<Record> pageQu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select b.xzqhdm,a.name,qyzzjgdm='',kdmc='',cfmc='' ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN tz_qiye c on c.xzqhdm=b.xzqhdm    where 1=1 " + sQu.getSql()+" GROUP BY b.xzqhdm,a.name,b.orderno ORDER BY b.orderno ", sQu.getParam().toArray(new Object[0]));
		return pageQu;
	}
	/**
	 * 得到所有一级企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> cblswkcyijiqiye(HashMap<String, Object> map) throws Exception {
		//取出一级企业(市属企业和区平级)
		Param quParam = new Param();
		quParam.put("and c.diqu not in (?)", "qiyebyxz");
		quParam.put("and b.xzqhdm = ?", "xzqhdm");
		quParam.put("and c.qyzzjgdm = ?", "yijiqiye");
		quParam.put("and c.niandu = ?", "year");
		SqlAndParam sQu = SqlUtil.buildSql(quParam, map);
		Page<Record> pageQu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select b.xzqhdm,a.name,c.qymc,c.qyzzjgdm,kdmc='',cfmc='',nianxian='' ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 " + sQu.getSql()+" GROUP BY b.xzqhdm,a.name,c.qymc,c.qyzzjgdm,c.orderno ORDER BY c.orderno ", sQu.getParam().toArray(new Object[0]));
		return pageQu;
	}
	/**
	 * 获取市区固定的三家企业承储计划
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cblswkcshiqu(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "yearsub");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		//p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select c.niandu,ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' "+q.getSql()+" GROUP BY c.niandu,a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pageshiqu.getList().size()>0){
			jihua=pageshiqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
		
	}
	/**
	 * 获取市区下的市级储备
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cbljhshiji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "year");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		//p.put("and c.xingzhi = ?", "xingzhi");2017/5/17
		//p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select c.niandu,ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' and c.xingzhi='122' "+q.getSql()+" GROUP BY c.niandu,a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pageshiqu.getList().size()>0){
			jihua=pageshiqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
		
	}
	/**
	 * 获取市区下的县级储备
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cbljhxianji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "year");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		//p.put("and c.xingzhi = ?", "xingzhi");
		//p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select c.niandu,ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' and c.xingzhi='122' "+q.getSql()+" GROUP BY c.niandu,a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pageshiqu.getList().size()>0){
			jihua=pageshiqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
		
	}
	public static BigDecimal cblswkcdiqu(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "yearsub");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pagediqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' and c.ccqy not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("year")+"') "+q.getSql()+" GROUP BY a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pagediqu.getList().size()>0){
			jihua=pagediqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;	
	}
	/**
	 * 储备粮计划-地区-市级储备
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cbljhdqshiji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "year");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		//p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pagediqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' and c.xingzhi='122' and c.ccqy not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区') "+q.getSql()+" GROUP BY a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pagediqu.getList().size()>0){
			jihua=pagediqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;	
	}
	/**
	 * 储备粮计划-地区-县级储备
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cbljhdqxianji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "year");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		//p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pagediqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1 and c.status='0' and c.xingzhi='123' and c.ccqy not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区') "+q.getSql()+" GROUP BY a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(pagediqu.getList().size()>0){
			jihua=pagediqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;	
	}
	public static BigDecimal cbljhrenwupz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and d.vGrainPropertyId = ?", "xingzhi");
		p.put("and d.vGrainSubTypeCode like ?", "pinzhong","%%%s");
		p.put("and d.dtRegistrateTime < ?", "month");
		p.put("and e.niandu= ? ", "year");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		//实际库存-入库
		Page<Record> pageruku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select aa.weight as weight ",
				" from ( select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1  and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm ) as aa", q.getParam().toArray(new Object[0]));
		//实际库存-出库
		Page<Record> pagechuku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select aa.weight as weight  ",
				" from (select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1  and d.vDirection='出库' "+q.getSql()+" GROUP BY a.xzqhdm ) as aa", q.getParam().toArray(new Object[0]));
		
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku.subtract(chuku);
		return weight;
	}
	public static BigDecimal cbljhkucunpz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		p.put("and a.cfbm = ?", "cfbm");
		p.put("and a.xingzhi = ?", "xingzhi");
		p.put("and a.pinzhong like ?", "pinzhong","%%%s");
		p.put("and a.niandu= ? ", "year");
		p.put("and a.yuefen = ?", "month");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		//实物库存-每月库存
		Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(a.dmStock)/1000, 0) as weight ",
				"  from njpt_chubeiliangjihua_kucun_yue a where 1=1 "+q.getSql()+"", q.getParam().toArray(new Object[0]));
		BigDecimal weight=new BigDecimal(0);
		if(kucun.getList().size()>0){
			weight=kucun.getList().get(0).getBigDecimal("weight");
		}else{
			Param p1 = new Param();
			//p.put("and a.xzqhdm = ?", "xzqhdm");
			p1.put("and a.qyzzjgdm = ?", "qyzzjgdm");
			p1.put("and a.kdbm = ?", "kdbm");
			p1.put("and a.cfbm = ?", "cfbm");
			p1.put("and a.xingzhi = ?", "xingzhi");
			p1.put("and a.pinzhong like ?", "pinzhong","%%%s");
			SqlAndParam q1 = SqlUtil.buildSql(p1, map);
			Page<Record> shishikc = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(a.dmStock)/1000, 0) as weight ",
					"  from njpt_chubeiliangjihua_kucun a where 1=1 "+q1.getSql()+"", q1.getParam().toArray(new Object[0]));
			if(shishikc.getList().size()>0){
				weight=shishikc.getList().get(0).getBigDecimal("weight");
			}
		}
		return weight;
	}
	//从储备粮-实时库存表里查询库存---市属企业
	public static BigDecimal cbljhCurrentKc(HashMap<String, Object> map) throws Exception {
			Param p = new Param();
			//p.put("and a.xzqhdm = ?", "xzqhdm");
			p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and b.kdbm = ?", "kdbm");
			p.put("and b.vWareHouseCode = ?", "cfbm");
			p.put("and b.vGrainPropertyCode = ?", "xingzhi");
			p.put("and b.vGrainSubTypeCode like ?", "pinzhong","%s%%");
			p.put("and a.niandu = ?", "yearsub");
			SqlAndParam q = SqlUtil.buildSql(p, map);
			//实物库存-每月库存
			Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
					"  from njpt_chubeiliangjihua a INNER JOIN kc_CurrentStock b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode where 1=1 and b.vGrainPropertyCode in ('122','123') "+q.getSql()+"", q.getParam().toArray(new Object[0]));
			BigDecimal weight=new BigDecimal(0);
			if(kucun.getList().size()>0){
				weight=kucun.getList().get(0).getBigDecimal("weight");
			}
			return weight;
		}
	//从储备粮-实时库存表里查询库存---市属企业
		public static BigDecimal cbljhNjptKc(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
				//p.put("and a.xzqhdm = ?", "xzqhdm");
				p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
				p.put("and b.kdbm = ?", "kdbm");
				p.put("and b.cfbm = ?", "cfbm");
				p.put("and b.xingzhi = ?", "xingzhi");
				p.put("and b.pinzhong like ?", "pinzhong","%s%%");
				p.put("and a.niandu = ?", "yearsub");
				SqlAndParam q = SqlUtil.buildSql(p, map);
				//实物库存-每月库存
				Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
						Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
						"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm where 1=1 and b.xingzhi in ('122','123') "+q.getSql()+"", q.getParam().toArray(new Object[0]));
				BigDecimal weight=new BigDecimal(0);
				if(kucun.getList().size()>0){
					weight=kucun.getList().get(0).getBigDecimal("weight");
				}
				return weight;
			}
	//从每月28号定时保存的库存表里查询库存---市属企业
	public static BigDecimal cbljhPastKc(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and b.cfbm = ?", "cfbm");
		p.put("and b.xingzhi = ?", "xingzhi");
		p.put("and b.pinzhong like ?", "pinzhong","%s%%");
		p.put("and a.niandu= ? ", "yearsub");
		p.put("and b.niandu= ? ", "year");
		p.put("and b.yuefen = ?", "month");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		//实物库存-每月库存
		Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
				"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun_yue b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm   where 1=1 and b.xingzhi in ('122','123') "+q.getSql()+"", q.getParam().toArray(new Object[0]));
		BigDecimal weight=new BigDecimal(0);
		if(kucun.getList().size()>0){
			weight=kucun.getList().get(0).getBigDecimal("weight");
		}
		return weight;
	}
	//从每月28号定时保存的库存表里查询库存---地区
	public static BigDecimal cbljhPastKcBydiqu(HashMap<String, Object> map) throws Exception {
			Param p = new Param();
			p.put("and a.quming = ?", "xzqhdm");
			p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and b.kdbm = ?", "kdbm");
			p.put("and b.cfbm = ?", "cfbm");
			p.put("and b.xingzhi = ?", "xingzhi");
			p.put("and b.pinzhong like ?", "pinzhong","%s%%");
			p.put("and a.niandu= ? ", "yearsub");
			p.put("and b.niandu= ? ", "year");
			p.put("and b.yuefen = ?", "month");
			SqlAndParam q = SqlUtil.buildSql(p, map);
			//实物库存-每月库存
			Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
					"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun_yue b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 and b.qyzzjgdm not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("year")+"') and b.xingzhi in ('122','123')  "+q.getSql()+" GROUP BY b.xzqhdm ", q.getParam().toArray(new Object[0]));
			BigDecimal weight=new BigDecimal(0);
			if(kucun.getList().size()>0){
				weight=kucun.getList().get(0).getBigDecimal("weight");
			}
			return weight;
		}
	//从每月28号定时保存的库存表里查询库存---到地区-企业-库点
			public static BigDecimal cbljhPastKcToKuDian(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
					p.put("and a.quming = ?", "xzqhdm");
					p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
					p.put("and b.kdbm = ?", "kdbm");
					p.put("and b.cfbm = ?", "cfbm");
					p.put("and b.xingzhi = ?", "xingzhi");
					p.put("and b.pinzhong like ?", "pinzhong","%s%%");
					p.put("and a.niandu= ? ", "niandusub");
					p.put("and b.niandu= ? ", "niandu");
					p.put("and b.yuefen = ?", "month");
					SqlAndParam q = SqlUtil.buildSql(p, map);
					//实物库存-每月库存
					Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
							Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
							"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun_yue b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 and b.xingzhi in ('122','123') "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
					BigDecimal weight=new BigDecimal(0);
					if(kucun.getList().size()>0){
						weight=kucun.getList().get(0).getBigDecimal("weight");
					}
					return weight;
				}
			//从实时库存表里查询库存---到地区-企业-库点
			public static BigDecimal cbljhCurrentKcToKuDian(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
					p.put("and a.quming = ?", "xzqhdm");
					p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
					p.put("and b.kdbm = ?", "kdbm");
					p.put("and b.vWareHouseCode = ?", "cfbm");
					p.put("and b.vGrainPropertyCode = ?", "xingzhi");
					p.put("and b.vGrainSubTypeCode like ?", "pinzhong","%s%%");
					p.put("and a.niandu= ? ", "niandusub");
					SqlAndParam q = SqlUtil.buildSql(p, map);
					//实物库存-每月库存
					Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
							Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
							"  from njpt_chubeiliangjihua a INNER JOIN kc_CurrentStock b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode  where 1=1 and b.vGrainPropertyCode in ('122','123') "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
					BigDecimal weight=new BigDecimal(0);
					if(kucun.getList().size()>0){
						weight=kucun.getList().get(0).getBigDecimal("weight");
					}
					return weight;
				}
			//从实时库存表里查询库存---到地区-企业-库点
			public static BigDecimal cbljhNjptToKuDian(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
					p.put("and a.quming = ?", "xzqhdm");
					p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
					p.put("and b.kdbm = ?", "kdbm");
					p.put("and b.cfbm = ?", "cfbm");
					p.put("and b.xingzhi = ?", "xingzhi");
					p.put("and b.pinzhong like ?", "pinzhong","%s%%");
					p.put("and a.niandu= ? ", "niandusub");
					SqlAndParam q = SqlUtil.buildSql(p, map);
					//实物库存-每月库存
					Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
							Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
							"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 and b.xingzhi in ('122','123') "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
					BigDecimal weight=new BigDecimal(0);
					if(kucun.getList().size()>0){
						weight=kucun.getList().get(0).getBigDecimal("weight");
					}
					return weight;
				}
	//从每月28号定时保存的库存表里查询库存---到地区-企业
		public static BigDecimal cbljhPastKcToqiye(HashMap<String, Object> map) throws Exception {
			Param p = new Param();
				p.put("and b.xzqhdm = ?", "xzqhdm");
				p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
				p.put("and b.kdbm = ?", "kdbm");
				p.put("and b.cfbm = ?", "cfbm");
				p.put("and b.xingzhi = ?", "xingzhi");
				p.put("and b.pinzhong like ?", "pinzhong","%s%%");
				p.put("and a.niandu= ? ", "niandusub");
				p.put("and b.niandu= ? ", "niandu");
				p.put("and b.yuefen = ?", "month");
				SqlAndParam q = SqlUtil.buildSql(p, map);
				//实物库存-每月库存
				Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
						Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
						"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun_yue b on a.quming=b.xzqhdm and a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 and b.xingzhi in ('122','123') "+q.getSql()+" GROUP BY b.xzqhdm,b.qyzzjgdm ", q.getParam().toArray(new Object[0]));
				BigDecimal weight=new BigDecimal(0);
				if(kucun.getList().size()>0){
					weight=kucun.getList().get(0).getBigDecimal("weight");
				}
				return weight;
			}
		//从实时库存表里查询库存---到地区-企业
				public static BigDecimal cbljhCurrentKcToqiye(HashMap<String, Object> map) throws Exception {
					Param p = new Param();
						p.put("and a.quming = ?", "xzqhdm");
						p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
						p.put("and b.kdbm = ?", "kdbm");
						p.put("and b.vWareHouseCode = ?", "cfbm");
						p.put("and b.vGrainPropertyCode = ?", "xingzhi");
						p.put("and b.vGrainSubTypeCode like ?", "pinzhong","%s%%");
						p.put("and a.niandu= ? ", "niandusub");
						SqlAndParam q = SqlUtil.buildSql(p, map);
						//实物库存-每月库存
						Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
								Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
								"  from njpt_chubeiliangjihua a INNER JOIN kc_CurrentStock b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode  where 1=1 and b.vGrainPropertyCode in ('122','123') "+q.getSql()+"  ", q.getParam().toArray(new Object[0]));
						BigDecimal weight=new BigDecimal(0);
						if(kucun.getList().size()>0){
							weight=kucun.getList().get(0).getBigDecimal("weight");
						}
						return weight;
					}
	//从储备粮-实时库存表里查询库存---地区
		public static BigDecimal cbljhCurrentKcBydiqu(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
				p.put("and a.quming = ?", "xzqhdm");
				p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
				p.put("and b.kdbm = ?", "kdbm");
				p.put("and b.vWareHouseCode = ?", "cfbm");
				p.put("and b.vGrainPropertyCode = ?", "xingzhi");
				p.put("and b.vGrainSubTypeCode like ?", "pinzhong","%s%%");
				p.put("and a.niandu= ? ", "yearsub");
				SqlAndParam q = SqlUtil.buildSql(p, map);
				//实物库存-每月库存
				Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
						Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
						"  from njpt_chubeiliangjihua a INNER JOIN kc_CurrentStock b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode  where 1=1 and b.qyzzjgdm not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("year")+"') and b.vGrainPropertyCode in ('122','123') "+q.getSql()+"  ", q.getParam().toArray(new Object[0]));
				BigDecimal weight=new BigDecimal(0);
				if(kucun.getList().size()>0){
					weight=kucun.getList().get(0).getBigDecimal("weight");
				}
				return weight;
			}
		//从储备粮-实时库存表里查询库存---地区
				public static BigDecimal cbljhNjptKcBydiqu(HashMap<String, Object> map) throws Exception {
						Param p = new Param();
						p.put("and a.quming = ?", "xzqhdm");
						p.put("and b.qyzzjgdm = ?", "qyzzjgdm");
						p.put("and b.kdbm = ?", "kdbm");
						p.put("and b.cfbm = ?", "cfbm");
						p.put("and b.xingzhi = ?", "xingzhi");
						p.put("and b.pinzhong like ?", "pinzhong","%s%%");
						p.put("and a.niandu= ? ", "yearsub");
						SqlAndParam q = SqlUtil.buildSql(p, map);
						//实物库存-每月库存
						Page<Record> kucun = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
								Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(b.dmStock)/1000, 0) as weight ",
								"  from njpt_chubeiliangjihua a INNER JOIN njpt_chubeiliangjihua_kucun b on  a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.cfbm  where 1=1 and b.qyzzjgdm not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("year")+"') and b.xingzhi in ('122','123') "+q.getSql()+"  ", q.getParam().toArray(new Object[0]));
						BigDecimal weight=new BigDecimal(0);
						if(kucun.getList().size()>0){
							weight=kucun.getList().get(0).getBigDecimal("weight");
						}
						return weight;
					}
		
	public static List<Record> CurrentOrPast(String year,String month) throws Exception {
		List<Record> recordlist=Db.find("select a.id from njpt_chubeiliangjihua_kucun_yue a where a.niandu=? and a.yuefen=? ",year,month);
		return recordlist;
	}
	//各地区下的实际库存
	public static BigDecimal cbljhrenwudiqupz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and d.vGrainPropertyId = ?", "xingzhi");
		p.put("and d.vGrainSubTypeCode like ?", "pinzhong","%%%s%%");
		p.put("and d.dtRegistrateTime < ?", "month");
		p.put("and e.niandu= ? ", "year");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		//实际库存-入库
		Page<Record> pageruku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='入库' and d.qyzzjgdm not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区') "+q.getSql()+" GROUP BY a.xzqhdm", q.getParam().toArray(new Object[0]));
		//实际库存-出库
		Page<Record> pagechuku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='出库' and d.qyzzjgdm not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区') "+q.getSql()+" GROUP BY a.xzqhdm", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku.subtract(chuku);
		return weight;
	}
	public static Page<Record> cblqiye(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandusub");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page=new Page<>(new ArrayList<Record>(), 1, 10000, 1, 10000);
			page = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), "select c.ccqy,d.qymc,qyzzjgdm='',kdbm='',cfbm='' ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming INNER JOIN tz_qiye d on c.ccqy=d.qyzzjgdm   where 1=1 and c.status='0' and c.ccqy not in( select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+queryparam.get("niandusub")+"') " + s.getSql()+" GROUP BY c.ccqy,d.qymc,b.orderno  ORDER BY b.orderno,c.ccqy desc ", s.getParam().toArray(new Object[0]));
		return page;
		
	}
	public static BigDecimal cbljhqiye(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandusub");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), " select c.niandu,ISNULL(SUM(c.jhsl)/1000,0) as jhsl",
				" from  njpt_chubeiliangjihua c   where 1=1 and c.status='0'  "+s.getSql()+" GROUP BY c.niandu,c.quming,c.ccqy ", s.getParam().toArray(new Object[0]));
		
		BigDecimal jihua=new BigDecimal(0);
		if(pageshiqu.getList().size()>0){
			jihua=pageshiqu.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
	}
	public static BigDecimal cbljhrenwupzbyqiye(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and d.vGrainPropertyId = ?", "xingzhi");
		p.put("and d.vGrainSubTypeCode like ?", "pinzhong","%%%s%%");
		p.put("and d.dtRegistrateTime < ?", "month");
		p.put("and e.niandu= ? ", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm ", q.getParam().toArray(new Object[0]));
		Page<Record> pagechuku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='出库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm ", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku.subtract(chuku);
		return weight;
	}
	public static Page<Record> cblkudian(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandusub");
		//p.put("and c.quming = ?", "xzqhdm");
		p.put("and a.qymc = ?", "qymc");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page=new Page<>(new ArrayList<Record>(), 1, 10000, 1, 10000);
		page = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), "select c.ccqy,b.kdmc,c.cckd,qyzzjgdm='',kdbm='',cfbm='' ",
				" from tz_qiye a inner JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm inner join njpt_chubeiliangjihua c on a.qyzzjgdm=c.ccqy and b.kdbm=c.cckd   where 1=1 and c.status='0' " + s.getSql()+" GROUP BY c.ccqy,c.cckd,b.kdmc order by c.ccqy,c.cckd desc ", s.getParam().toArray(new Object[0]));
		return page;
	}
	public static BigDecimal cblswkckudian(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandusub");
		//p.put("and c.quming = ?", "xzqhdm");
		p.put("and a.qymc = ?", "qymc");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page=new Page<>(new ArrayList<Record>(), 1, 10000, 1, 10000);
		page = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), "select c.niandu,ISNULL(SUM(c.jhsl)/1000,0) as jhsl ",
				" from tz_qiye a inner JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm inner join njpt_chubeiliangjihua c on a.qyzzjgdm=c.ccqy and b.kdbm=c.cckd   where 1=1 and c.status='0'  " + s.getSql()+" GROUP BY c.niandu,c.ccqy,c.cckd,b.kdmc order by c.cckd ", s.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(page.getList().size()>0){
			jihua=page.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
	}
	public static Page<Record> cbljhkudian(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.quming = ?", "xzqhdm");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.ccch = ?", "cfbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), " select c.niandu,ISNULL(SUM(c.cbljh_xm)/1000,0) as xm,ISNULL(SUM(c.cbljh_jd)/1000,0) as jd,ISNULL(SUM(c.cbljh_xd)/1000,0) as xd,ISNULL(SUM(c.cbljh_xj)/1000,0) as xj ",
				" from  njpt_chubeiliangjihua c   where 1=1 "+s.getSql()+" GROUP BY c.niandu,c.quming,c.ccqy ", s.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	public static BigDecimal cbljhrenwupzbykudian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and d.vGrainPropertyId = ?", "xingzhi");
		p.put("and d.vGrainSubTypeCode like ?", "pinzhong","%%%s%%");
		p.put("and d.dtRegistrateTime < ?", "month");
		p.put("and e.niandu= ? ", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm ", q.getParam().toArray(new Object[0]));
		Page<Record> pagechuku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='出库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm ", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku.subtract(chuku);
		return weight;
	}
	/**
	 * 有储备计划的企业
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> cblcangfang(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and d.niandu = ?", "niandusub");
		//p.put("and d.quming = ?", "xzqhdm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and d.ccqy = ?", "qyzzjgdm");
		p.put("and d.cckd = ?", "kdbm");
		p.put("and d.ccch = ?", "cfbm");
		p.put("and d.xingzhi = ?", "xingzhi");
		p.put("and d.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page=new Page<>(new ArrayList<Record>(), 1, 10000, 1, 10000);
		page = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), "select d.ccqy,d.cckd,d.ccch,c.cfmc,qyzzjgdm='',kdbm='',cfbm='' ",
				" from tz_qiye a inner JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm inner join tz_cangfang c on a.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN njpt_chubeiliangjihua d on a.qyzzjgdm=d.ccqy and b.kdbm=d.cckd and c.cfbm=d.ccch  where 1=1 and d.status='0'  " + s.getSql()+" GROUP BY d.ccqy,d.cckd,d.ccch,c.cfmc ORDER BY d.ccqy,d.cckd,d.ccch desc ", s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 仓房储备计划
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal cblswkccangfang(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and d.niandu = ?", "niandusub");
		//p.put("and d.quming = ?", "xzqhdm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and d.ccqy = ?", "qyzzjgdm");
		p.put("and d.cckd = ?", "kdbm");
		p.put("and d.ccch = ?", "cfbm");
		p.put("and d.xingzhi = ?", "xingzhi");
		p.put("and d.pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page=new Page<>(new ArrayList<Record>(), 1, 10000, 1, 10000);
		page = Db.paginate(Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), "select d.niandu,ISNULL(SUM(d.jhsl)/1000,0) as jhsl ",
				" from tz_qiye a inner JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm inner join tz_cangfang c on a.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN njpt_chubeiliangjihua d on a.qyzzjgdm=d.ccqy and b.kdbm=d.cckd and c.cfbm=d.ccch  where 1=1 and d.status='0'  " + s.getSql()+" GROUP BY d.niandu,d.ccqy,d.cckd,d.ccch,c.cfmc ORDER BY d.ccch ", s.getParam().toArray(new Object[0]));
		BigDecimal jihua=new BigDecimal(0);
		if(page.getList().size()>0){
			jihua=page.getList().get(0).getBigDecimal("jhsl");
		}
		return jihua;
	}
	public static BigDecimal cbljhrenwupzbycangfang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and d.vGrainPropertyId = ?", "xingzhi");
		p.put("and d.vGrainSubTypeCode like ?", "pinzhong","%%%s%%");
		p.put("and d.dtRegistrateTime < ?", "month");
		p.put("and e.niandu= ? ", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm,c.cfbm ", q.getParam().toArray(new Object[0]));
		Page<Record> pagechuku = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(sum(d.dmCheckWeight)/1000, 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm INNER JOIN njpt_chubeiliangjihua e on d.qyzzjgdm=e.ccqy and d.kdbm=e.cckd and d.vWareHouseCode=e.ccch  where 1=1 and d.vDirection='出库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm,c.cfbm ", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku.subtract(chuku);
		return weight;
	}
	/**
	 * 定时任务查询仓下的入库
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal findRuKubycangfang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and CONVERT(varchar(100), d.dtRegistrateTime, 21) > ?", "time");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(1,
				10000, " select ISNULL(sum(d.dmCheckWeight), 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm   where 1=1 and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm,c.cfbm ", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=ruku;
		return weight;
	}
	/**
	 * 根据传来的年份，月份 得到当前月库存（即该月底之前的入库-出库）
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year,int month)
	{
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, 28);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String lastDayOfMonth = sdf.format(cal.getTime());
		
		return lastDayOfMonth;
	}
	/**
	 * 实物库存合计-储备粮计划
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> swkccblheji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.niandu = ?", "year");
		p.put("and c.ccqy = ?", "qyzzjgdm");
		p.put("and c.cckd = ?", "kdbm");
		p.put("and c.xingzhi = ?", "xingzhi");
		p.put("and c.quming = ?", "xzqhdm");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select ISNULL(SUM(c.cbljh_xm)/1000,0) as xm,ISNULL(SUM(c.cbljh_jd)/1000,0) as jd,ISNULL(SUM(c.cbljh_xd)/1000,0) as xd,ISNULL(SUM(c.cbljh_xj)/1000,0) as xj ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua c on b.xzqhdm=c.quming  where 1=1   "+q.getSql()+" GROUP BY c.niandu,a.name,c.quming,b.orderno  ORDER BY b.orderno", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	public static String[] swkccheckpz(HashMap<String, Object> map) {
		String pzStr=(String) map.get("pinzhong");
		String[] pinzhong={"111","1132","1131"};
		String[] pzlast = {"999999","999999","999999"};
		if("".equals(pzStr)||pzStr==null||"null".equals(pzStr)){
			return pinzhong;
		}else{
			for(int i=0;i<pinzhong.length;i++){
				if(pzStr.equals(pinzhong[i])){
					pzlast[i]=pzStr;
				}
			}
			return pzlast;
		}
		
	}
	public static String[] swkccheckxz(HashMap<String, Object> map) {
		String xzStr=(String) map.get("xingzhi");
		String[] xz={"122","123"};
		String[] xzlast = {"999999","999999"};
		if("".equals(xzStr)||xzStr==null||"null".equals(xzStr)){
			return xz;
		}else{
			for(int i=0;i<xz.length;i++){
				if(xzStr.equals(xz[i])){
					xzlast[i]=xzStr;
				}
			}
			return xzlast;
		}
		
	}
	/**
	 * 返回市储企业list
	 * @param map
	 * @return
	 */
	public static Page<Record> shichuqiye(HashMap<String, Object> map) {
		Param p = new Param();
		p.put(" and a.niandu=? ", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select a.*,b.qyxzmc,b.fddbr ",
				" from njpt_chubeiliangjihua_diqu a inner join tz_qiye b on a.qyzzjgdm=b.qyzzjgdm where 1=1 "+q.getSql()+" order by a.orderno,qyzzjgdm ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	/**
	 * 根据传来的一级qyzzjgdm返回xzqhdm
	 * @param map
	 * @return
	 */
	public static Record findisssqy(HashMap<String, Object> map) {
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		Record xzqhdm=Db.findFirst("select a.diqu from njpt_chubeiliangjihua_diqu a where  a.qyzzjgdm=? and a.niandu=? ",map.get("yijiqiye"),year);
		return xzqhdm;
	}
	/**
	 * 根据传来的一级企业的id返回xzqhdm
	 * @param map
	 * @return
	 */
	public static Record findisssqy1(HashMap<String, Object> map) {
		Record xzqhdm=Db.findFirst("select DISTINCT a.diqu from njpt_chubeiliangjihua_diqu a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm where  b.id=?  ",map.get("yijiqiyeid"));
		return xzqhdm;
	}
	/**
	 * 根据传来的一级qyzzjgdm返回xzqhdm(包含往年的去，掉重复值)
	 * @param map
	 * @return
	 */
	public static Record findallssqy(HashMap<String, Object> map) {
		Record xzqhdm=Db.findFirst("select DISTINCT a.diqu from njpt_chubeiliangjihua_diqu a where  a.qyzzjgdm=?  ",map.get("yijiqiye"));
		return xzqhdm;
	}
	/**
	 * 查询企业下的库点
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findssqykd(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and a.qyzzjgdm = ?", "yijiqiye");
		p.put(" and b.niandu = ?", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select a.qyzzjgdm as kdbm,a.qymc as kdmc",
				" from tz_qiye a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy  where 1=1  "+q.getSql()+" GROUP BY a.qyzzjgdm,a.qymc order by a.qyzzjgdm ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	/**
	 * 查询市属企业下的承储库（即本身）--by qiyeid
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findssqykd1(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and a.ID = ?", "yijiqiyeid");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select a.qyzzjgdm as kdbm,a.qymc as kdmc ",
				" from tz_qiye a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy  where 1=1  "+q.getSql()+" GROUP BY a.qyzzjgdm,a.qymc order by a.qyzzjgdm  ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	/**
	 * 查询市属企业下的库点（就是本身）
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findallssqykd(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and a.qyzzjgdm = ?", "yijiqiye");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select a.qyzzjgdm as kdbm,a.qymc as kdmc ",
				" from tz_qiye a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy  where 1=1  "+q.getSql()+" GROUP BY a.qyzzjgdm,a.qymc order by a.qyzzjgdm ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	public static Page<Record> finddqqykd(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and b.niandu = ?", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select c.qyzzjgdm,c.kdbm,c.kdmc ",
				" from tz_qiye a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy INNER JOIN tz_kudian c on b.ccqy=c.qyzzjgdm and b.cckd=c.kdbm where 1=1 and b.status='0'  "+q.getSql()+" GROUP BY c.qyzzjgdm,c.kdbm,c.kdmc order by c.kdbm ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	public static Page<Record> finddiquqykd(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and b.xzqhdm = ?", "xzqhdm");
		p.put("and a.name = ?", "name");
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and d.niandu = ?", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), " select c.qyzzjgdm,c.qymc ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN tz_qiye c on b.xzqhdm=c.xzqhdm INNER JOIN njpt_chubeiliangjihua d on c.qyzzjgdm=d.ccqy where 1=1 and d.ccqy not in(select qyzzjgdm from njpt_chubeiliangjihua_diqu where diqu='市区' ) and d.status='0' "+q.getSql()+" GROUP BY c.qyzzjgdm,c.qymc order by c.qyzzjgdm asc ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}
	@Before(AutoPageInterceptor.class)
	public static Page<Record> findQYbyJiHua(HashMap<String, Object> map) throws Exception {
		
		Param p = new Param();
		p.put(" and a.niandu=? ", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageshiqu = Db.paginate(1,
				10000, " select a.quming as xzqhdm,b.qyzzjgdm,c.kdbm,d.cfbm ",
				" from njpt_chubeiliangjihua a INNER JOIN tz_qiye b on a.ccqy=b.qyzzjgdm   INNER JOIN tz_kudian c on a.ccqy=c.qyzzjgdm and a.cckd=c.kdbm INNER JOIN tz_cangfang d on a.ccqy=d.qyzzjgdm and a.cckd=d.kdbm and a.ccch=d.cfbm INNER JOIN njpt_diqu e on a.quming=e.xzqhdm where 1=1  "+q.getSql()+" GROUP BY a.quming,b.qyzzjgdm,c.kdbm,d.cfbm,e.orderno ORDER BY e.orderno ", q.getParam().toArray(new Object[0]));
		return pageshiqu;
	}

	public static Record findlunhuanbycf(HashMap<String, Object> map) {
		Record record=new Record();
		record=Db.findFirst("select top 1 * from njpt_lunhuanshenqingb where qymc=? and lhsq_kdmc=? and lhsq_ch=? and lhsq_zt not in ('1','6','10') order by lhpzsj desc ",map.get("qyzzjgdm"),map.get("kdbm"),map.get("cfbm"));
		return record;
	}
	public static List<Record> findsecondlunhuanbycf(HashMap<String, Object> map) {
		
		List<Record> list=Db.find("select * from njpt_lunhuanshenqingb where qymc=? and lhsq_kdmc=? and lhsq_ch=?  and lhsq_zt not in ('1','6','10') order by lhpzsj desc ",map.get("qyzzjgdm"),map.get("kdbm"),map.get("cfbm"));
		return list;
	}
	public static BigDecimal findsskcbycf(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and CONVERT(varchar(100), d.dtRegistrateTime, 21) < ?", "nowtime");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(1,
				10000, " select ISNULL(sum(d.dmCheckWeight), 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm   where 1=1 and d.vDirection='入库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm,c.cfbm ", q.getParam().toArray(new Object[0]));
		Page<Record> pagechuku = Db.paginate(1,
				10000, " select ISNULL(sum(d.dmCheckWeight), 0) as weight ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm   where 1=1 and d.vDirection='出库' "+q.getSql()+" GROUP BY a.xzqhdm,a.qyzzjgdm,b.kdbm,c.cfbm ", q.getParam().toArray(new Object[0]));
		BigDecimal ruku=new BigDecimal(0);
		BigDecimal chuku=new BigDecimal(0);
		int chushicl=0;
		Record record=Db.findFirst("select ISNULL(sum(CONVERT(int,chushicl)), 0) as chushicl from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=?",map.get("qyzzjgdm"),map.get("kdbm"),map.get("cfbm"));
		if(record!=null){
			chushicl=record.getInt("chushicl");
		}
		if(pageruku.getList().size()>0){
			ruku=pageruku.getList().get(0).getBigDecimal("weight");
		}
		if(pagechuku.getList().size()>0){
			chuku=pagechuku.getList().get(0).getBigDecimal("weight");
		}
		BigDecimal weight=new BigDecimal(0);
		weight=ruku.add(new BigDecimal(chushicl));
		weight=weight.subtract(chuku);
		if(-1==weight.compareTo(new BigDecimal(0))){
			weight=new BigDecimal(0);
		}
		return weight;
	}

	public static Record findpzxzyear(HashMap<String, Object> map) {
		HashMap<String, Object> retmap=new HashMap<>();
		Record record=new Record();
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and b.kdbm = ?", "kdbm");
		p.put(" and c.cfbm = ?", "cfbm");
		p.put(" and CONVERT(varchar(100), d.dtRegistrateTime, 21) > ?", "time");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(1,
				10000, " select d.vGrainLevel as grade ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm   where 1=1 and d.vDirection='入库' "+q.getSql()+" ORDER BY d.dtRegistrateTime desc ", q.getParam().toArray(new Object[0]));
		if(pageruku.getList().size()>0){
			record.setColumns(pageruku.getList().get(0));
		}
		Param p1 = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p1.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p1.put(" and a.kdbm = ?", "kdbm");
		p1.put(" and a.vWareHouseCode = ?", "cfbm");
		SqlAndParam q1 = SqlUtil.buildSql(p1, map);
		Page<Record> kucun = Db.paginate(1,
				10000, " select a.vGrainSubTypeCode as pinzhong,a.vGrainPropertyCode as xingzhi,year ",
				" from kc_CurrentStock a where 1=1 "+q1.getSql()+"  ", q1.getParam().toArray(new Object[0]));
		if(kucun.getList().size()>0){
			record.setColumns(kucun.getList().get(0));
		}
		return record;
	}
	public static Page<Record> findcfcrkjilu(HashMap<String, Object> map) {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and a.kdbm = ?", "kdbm");
		p.put(" and a.vWareHouseCode = ?", "cfbm");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(1,
				10000, " select a.vSwiftNumber ",
				" from  crk_Purchase a where 1=1 "+q.getSql()+" ORDER BY a.dtRegistrateTime desc ", q.getParam().toArray(new Object[0]));
		return pageruku;
	}
	public static Page<Record> findcfkucunjilu(HashMap<String, Object> map) {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and a.kdbm = ?", "kdbm");
		p.put(" and a.vWareHouseCode = ?", "cfbm");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(1,
				10000, " select a.id ",
				" from  kc_CurrentStock a where 1=1 "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
		return pageruku;
	}
	public static Record findPZaboutcrk(HashMap<String, Object> map) {
		Record record=new Record();
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and b.kdbm = ?", "kdbm");
		p.put(" and c.cfbm = ?", "cfbm");
		p.put(" and CONVERT(varchar(100), d.dtRegistrateTime, 21) < ?", "nowtime");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageruku = Db.paginate(
				1,
				10000, 
				" select  d.vGrainSubTypeCode as pinzhong ,d.vGrainPropertyId as xingzhi ,d.vHarvestPeriod as year,d.vGrainLevel as grade ",
				" from tz_qiye a INNER JOIN tz_kudian b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm INNER JOIN crk_Purchase d on d.qyzzjgdm=a.qyzzjgdm and d.kdbm=c.kdbm and d.vWareHouseCode=c.cfbm   where 1=1 and d.vDirection='入库' "+q.getSql()+" ORDER BY d.dtRegistrateTime desc ", q.getParam().toArray(new Object[0]));
		if(pageruku.getList().size()>0){
			record.setColumns(pageruku.getList().get(0));
		}
		return record;
	}

	public static Page<Record> findallchlqukuucn(HashMap<String, Object> map) {
		Param p = new Param();
		//p.put("and a.xzqhdm = ?", "xzqhdm");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageallkc = Db.paginate(
				1,
				10000, 
				" select * ",
				" from njpt_chubeiliangjihua_kucun  "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
		return pageallkc;
	}
	public static Page<Record> findallndjihua(HashMap<String, Object> map) {
		Param p = new Param();
		p.put("and niandu=  ?", "niandu");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pageallkc = Db.paginate(
				1,
				10000, 
				" select *",
				" from njpt_chubeiliangjihua_renwu where 1=1 "+q.getSql()+" ", q.getParam().toArray(new Object[0]));
		return pageallkc;
	}
	public static Record queryscqiyeById(HashMap<String, Object> map) {
		Record record=new Record();
		Param p = new Param();
		p.put(" and a.id=? ", "id");
		SqlAndParam q = SqlUtil.buildSql(p, map);
		Page<Record> pagescqiye = Db.paginate(
				1,
				10000, 
				" select a.*,b.qyxzmc,b.fddbr,b.lxdh,b.xxdz,b.zdmj,b.kqh,b.cfzcr ",
				" from njpt_chubeiliangjihua_diqu a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm where 1=1 "+q.getSql()+" ORDER BY a.id ", q.getParam().toArray(new Object[0]));
		if(pagescqiye.getList().size()>0){
			record.setColumns(pagescqiye.getList().get(0));
		}
		return record;
	}

	public static Page<Record> getcbljhsp(HashMap<java.lang.String, Object> param) {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select c.status ",
				"from njpt_chubeiliangjihua c inner JOIN njpt_diqu d on c.quming=d.xzqhdm inner join fw_area fw on d.areaid=fw.id inner join tz_qiye q on q.qyzzjgdm=c.ccqy INNER JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm INNER JOIN tz_cangfang cf on c.ccqy=cf.qyzzjgdm and c.cckd=cf.kdbm and c.ccch=cf.cfbm  where 1=1 and c.xingzhi='122' and c.status='1' " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	public static Page<Record> getcbljhsq(HashMap<java.lang.String, Object> param) {
		Param p = new Param();
		p.put(" and q.ID=? ", "qiyeid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select c.status ",
				"from njpt_chubeiliangjihua c inner JOIN njpt_diqu d on c.quming=d.xzqhdm inner join fw_area fw on d.areaid=fw.id inner join tz_qiye q on q.qyzzjgdm=c.ccqy INNER JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm INNER JOIN tz_cangfang cf on c.ccqy=cf.qyzzjgdm and c.cckd=cf.kdbm and c.ccch=cf.cfbm  where 1=1 and c.xingzhi='122' and c.status not in ('0') " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

}
