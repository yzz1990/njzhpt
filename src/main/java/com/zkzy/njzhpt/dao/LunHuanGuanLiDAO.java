package com.zkzy.njzhpt.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.select.Evaluator.Id;


import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.server.Scanner;
import com.zkzy.framework.kit.UserKit;

public class LunHuanGuanLiDAO {
	public static String creatuuid(HashMap<String, Object> map) throws Exception{
		String uuid=SqlUtil.uuid();
		
		return uuid;
		
	}
	public static Record selectSWKC(HashMap<String, Object> map) throws Exception{
		Calendar curr = Calendar.getInstance();
		String year =String.valueOf((curr.get(Calendar.YEAR)));//当前年
		String ndyear =String.valueOf((curr.get(Calendar.YEAR)));//当前年
		Param p = new Param();
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.niandu = ?", "niandu");
		SqlAndParam sQu = SqlUtil.buildSql(p, map);
		Page<Record> pageQu = Db.paginate(1,
				10000, " select b.xzqhdm,a.name,c.qymc,c.qyzzjgdm ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 " + sQu.getSql()+" GROUP BY b.xzqhdm,a.name,c.qymc,c.qyzzjgdm,c.orderno ORDER BY c.orderno ", sQu.getParam().toArray(new Object[0]));
		Record xm=new Record();
		Record jd=new Record();
		Record xd=new Record();
		Record xmcbl=null;
		Record jdcbl=null;
		Record xdcbl=null;
		Record quanshijh=null;
		Record quanshikc=null;
		List<Record> xmfndkucun=new ArrayList<>();
		List<Record> jdfndkucun=new ArrayList<>();
		List<Record> xdfndkucun=new ArrayList<>();
		//全市总计划
		quanshijh =Db.findFirst("select ISNULL(SUM(CONVERT(int,xiaoji)), 0) as zongjh from njpt_chubeiliangjihua_renwu where xingzhi='122' and niandu=? ",year);
		quanshikc =Db.findFirst("SELECT ISNULL(SUM(a.dmStock)/1000, 0) as zongkc from njpt_chubeiliangjihua_kucun a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where a.pinzhong like '111%' or a.pinzhong like '1131' or a.pinzhong like '1132' and b.niandu=? ",year);
		//全市总库存
		if(pageQu.getList().size()>0){
			Record record=pageQu.getList().get(0);
			//市储企业
			if("210008".equals(record.getStr("xzqhdm"))){
				//查询实物库存
				xm=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as xmkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '111%' and a.qyzzjgdm=? and b.niandu=?",map.get("qyzzjgdm"),year);
				jd=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as jdkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '1132%' and a.qyzzjgdm=? and b.niandu=?",map.get("qyzzjgdm"),year);
				xd=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as xdkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '1131%' and a.qyzzjgdm=? and b.niandu=?",map.get("qyzzjgdm"),year);
				//查询储备粮计划
				xmcbl=Db.findFirst("select isnull(sum(convert(int,xm)),0) as xmcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122' and qyzzjgdm=? and niandu=? ",map.get("qyzzjgdm"),ndyear);
				jdcbl=Db.findFirst("select isnull(sum(convert(int,jd)),0) as jdcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122'  and qyzzjgdm=? and niandu=?",map.get("qyzzjgdm"),ndyear);
				xdcbl=Db.findFirst("select isnull(sum(convert(int,xd)),0) as xdcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122' and qyzzjgdm=? and niandu=?",map.get("qyzzjgdm"),ndyear);
				//分品种分年度库存
				xmfndkucun=Db.find("select  sum(a.dmStock)/1000 as xmkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '111%' and a.qyzzjgdm=? and b.niandu=? GROUP BY a.year ORDER BY a.year ",map.get("qyzzjgdm"),year);
				jdfndkucun=Db.find("select  sum(a.dmStock)/1000 as jdkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '1132%' and a.qyzzjgdm=? and b.niandu=? GROUP BY a.year ORDER BY a.year ",map.get("qyzzjgdm"),year);
				xdfndkucun=Db.find("select  sum(a.dmStock)/1000 as xdkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.pinzhong like '1131%' and a.qyzzjgdm=? and b.niandu=? GROUP BY a.year ORDER BY a.year ",map.get("qyzzjgdm"),year);
			}else{
			//各个购销公司
				//查询实物库存
				xm=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as xmkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '111%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"'");
				jd=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as jdkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '1132%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"'");
				xd=Db.findFirst("select isnull(sum(a.dmStock),0)/1000 as xdkc  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '1131%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"'");
				//查询储备粮计划
				xmcbl=Db.findFirst("select isnull(sum(convert(int,xm)),0) as xmcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122' and qyzzjgdm=? and niandu=? ",map.get("qyzzjgdm"),ndyear);
				jdcbl=Db.findFirst("select isnull(sum(convert(int,jd)),0) as jdcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122'  and qyzzjgdm=? and niandu=?",map.get("qyzzjgdm"),ndyear);
				xdcbl=Db.findFirst("select isnull(sum(convert(int,xd)),0) as xdcbljh from njpt_chubeiliangjihua_renwu where 1=1 and xingzhi='122' and qyzzjgdm=? and niandu=? ",map.get("qyzzjgdm"),ndyear);
				//分品种分年度库存
				xmfndkucun=Db.find("select sum(a.dmStock)/1000 as xmkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '111%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"' GROUP BY a.year ORDER BY a.year ");
				jdfndkucun=Db.find("select sum(a.dmStock)/1000 as jdkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '1132%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"' GROUP BY a.year ORDER BY a.year ");
				xdfndkucun=Db.find("select sum(a.dmStock)/1000 as xdkc,a.year  from njpt_chubeiliangjihua_kucun a inner join njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where 1=1 and a.xingzhi='122' and a.qyzzjgdm not in (select dq.qyzzjgdm from njpt_chubeiliangjihua_diqu dq where dq.diqu='市区' and dq.niandu='"+map.get("niandu")+"') and a.pinzhong like '1131%' and a.xzqhdm='"+record.get("xzqhdm")+"' and b.niandu='"+year+"' GROUP BY a.year ORDER BY a.year ");
			}
		}
		//轮换管理下拉时 根据企业查询实物库存
		BigDecimal xmkcbg=null;
		BigDecimal jdkcbg=null;
		BigDecimal xdkcgb=null;
		double xmkc = 0;
		double jdkc=0;
		double xdkc=0;
			xmkcbg = xm.get("xmkc");
			if (xmkcbg!=null) {
				xmkc=xmkcbg.doubleValue();
				
			}
			jdkcbg = jd.get("jdkc");
			if (jdkcbg!=null) {
				
				jdkc=jdkcbg.doubleValue();
			}
			xdkcgb = xd.get("xdkc");
			if (xdkcgb!=null) {
				
				xdkc=xdkcgb.doubleValue();
			}
		double xj=xmkc+jdkc+xdkc;
		DecimalFormat    df   = new DecimalFormat("#0.000");  
		Record record=new Record().set("swkc_xm", df.format(xmkc)).set("swkc_jd", df.format(jdkc)).set("swkc_xd", df.format(xdkc)).set("swkc_xj", df.format(xj));
		
		int xmcbljh=0;
		int jdcbljh=0;
		int xdcbljh=0;
		if(null!=xmcbl){
			xmcbljh = xmcbl.get("xmcbljh");
		}
		if(null!=jdcbl){
			
			jdcbljh = jdcbl.get("jdcbljh");
		}
		if(null!=xdcbl){
			xdcbljh = xdcbl.get("xdcbljh");
		}
		double xjcbl=xmcbljh+jdcbljh+xdcbljh;
		record.set("cbljh_xm", xmcbljh).set("cbljh_jd", jdcbljh).set("cbljh_xd", xdcbljh).set("cbljh_xj", xjcbl);
		HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(new BigDecimal(xjcbl), new BigDecimal(xj), 2);
		HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi( BigDecimal.valueOf(xmcbljh),new BigDecimal(xmkc), 2);
		HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(BigDecimal.valueOf(jdcbljh),new BigDecimal(jdkc) , 2);
		HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(BigDecimal.valueOf(xdcbljh), new BigDecimal(xdkc), 2);
		
		//分品种分年度库存
		String xmString="";
		String jdString="";
		String xdString="";
		if(xmfndkucun.size()>0){
			for(Record xmRecord:xmfndkucun){
				xmString=xmString+xmRecord.getStr("year")+"年"+df.format(xmRecord.getBigDecimal("xmkc").doubleValue())+"吨、";
			}
		}
		if(jdfndkucun.size()>0){
			for(Record jdRecord:jdfndkucun){
				jdString=jdString+jdRecord.getStr("year")+"年"+df.format(jdRecord.getBigDecimal("jdkc").doubleValue())+"吨、";
			}
		}
		if(xdfndkucun.size()>0){
			for(Record xdRecord:xdfndkucun){
				xdString=xdString+xdRecord.getStr("year")+"年"+df.format(xdRecord.getBigDecimal("xdkc").doubleValue())+"吨、";
			}
		}
		//分品种承储计划和分年度分品种库存
		record.set("xmzb", xmzb.get("zb")).set("jdzb", jdzb.get("zb")).set("xdzb", xdzb.get("zb")).set("xjzb", xjzb.get("zb")).set("xmfndkc", xmString).set("jdfndkc", jdString).set("xdfndkc", xdString);
		if(quanshijh!=null){
			record.set("quanshijh", quanshijh.get("zongjh"));
		}else{
			record.set("quanshijh", 0);
		}
		if(quanshikc!=null){
			record.set("quanshikc", quanshikc.get("zongkc"));
		}else{
			record.set("quanshikc", 0);
		}
		/*	Record ispzjh=Db.findFirst("select isnull(CONVERT(int,xm),0) as xm,isnull(CONVERT(int,jd),0) as jd,isnull(CONVERT(int,xd),0) as xd from njpt_chubeiliangjihua_renwu where niandu=? and qyzzjgdm=? and xingzhi='122'", year,map.get("qyzzjgdm"));
		if(0>=ispzjh.getInt("xm")){
			record.set("cbljh_xm", "/").set("xmzb", "/");
		}
		if(0>=ispzjh.getInt("jd")){
			record.set("cbljh_jd", "/").set("jdzb", "/");
		}
		if(0>=ispzjh.getInt("xd")){
			record.set("cbljh_xd", "/").set("xdzb", "/");
		}*/
		return record;
	}
	public static Record selectCCJH(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
//		p.put("and kdbm = ?", "kdbm");
//		p.put("and vWareHouseCode = ?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Record xmcbl=Db.findFirst("select sum(cbljh_xm) as xmcbljh from njpt_chubeiliangjihua where 1=1   and qyzzjgdm=?",map.get("qyzzjgdm"));
		Record jdcbl=Db.findFirst("select sum(cbljh_jd) as jdcbljh from njpt_chubeiliangjihua where 1=1   and qyzzjgdm=?",map.get("qyzzjgdm"));
		Record xdcbl=Db.findFirst("select sum(cbljh_xd) as xdcbljh from njpt_chubeiliangjihua where 1=1   and qyzzjgdm=?",map.get("qyzzjgdm"));
		String xmcbljh=null;
		String jdcbljh=null;
		String xdcbljh=null;
		double xmsl=0;
		double jdsl=0;
		double xdsl=0;
		if (xmcbl!=null) {
			xmcbljh = xmcbl.get("xmcbljh");
			if (xmcbljh!=null&&"".equals(xmcbljh)) {
				xmsl=Double.valueOf(xmcbljh);
			}
		}
		if (jdcbl!=null) {
			
			jdcbljh = jdcbl.get("jdcbljh");
			if (jdcbljh!=null&&"".equals(jdcbljh)) {
				jdsl=Double.valueOf(jdcbljh);
			}
		}
		if (xdcbl!=null) {
			xdcbljh = xdcbl.get("xdcbljh");
			if (xdcbljh!=null&&"".equals(xdcbljh)) {
				xdsl=Double.valueOf(xdcbljh);
			}
		}
	
		double xjcbl=xmsl+jdsl+xdsl;
		Record record=new Record().set("cbljh_xm", xmsl).set("cbljh_jd", jdsl).set("cbljh_xd", xdsl).set("cbljh_xj", xjcbl);
		return record;
	}
	
	/**
	 * 获取轮出批文列表
	 * @param param
	 * @return
	 * @throws ParseException 
	 */
	public static Page<Record> findlcpw(HashMap<String, Object> param) throws ParseException {
		String sql=" ";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.sqwh like ?", "sqwh","%%%s%%");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.iscjkq = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and a.qyzzjgdm=?", "qyzzjgdm");
		p.put("and a.ccku=?", "ccku");
		p.put("and a.ccdian=?", "ccdian");
		p.put("and a.pz like ? ", "pz","%s%%");
		p.put("and a.year = ?", "year");
		p.put("and a.pzsj >= ?", "qssj");
		p.put("and a.pzsj <= ?", "jssj");
		p.put("and h.diqu=?", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select h.diqu,c.qymc,d.kdmc,e.cfmc,f.vName,g.vLevelName,a.* ",
				"from njpt_lunhuanshenqingb a  INNER JOIN tz_qiye c on a.ccku=c.qyzzjgdm INNER JOIN tz_kudian d on a.ccdian=d.kdbm and a.ccku=d.qyzzjgdm inner join tz_cangfang e on a.ccku=e.qyzzjgdm and a.ccdian=e.kdbm and a.cfbm=e.cfbm INNER JOIN tz_GrainType f on a.pz=f.vCode LEFT JOIN tz_GrainLevel g on a.grade=g.vCode INNER JOIN njpt_chubeiliangjihua_diqu h on SUBSTRING(a.pzsj, 1, 4)=h.niandu and a.qyzzjgdm=h.qyzzjgdm where a.status>0  " + s.getSql()+sql+" ORDER BY a.iscjkq desc,a.status asc,a.pzsj desc ",
				s.getParam().toArray(new Object[0]));
		int index=1;
		for(Record record:page.getList()){
			record.set("xuhao", index);
			index++;
			if("on".equals(record.getStr("iszclc"))){
				record.set("iszclc", record.getStr("vName")+"整仓轮出");
			}else{
				record.set("iszclc", "");
			}
		}
		return page;
	}
	
	//回显，根据编辑申请时传回的id回显
	public static Page<Record> querylhsqById(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.*,f.vName,c.qymc,d.kdmc,e.cfmc ",
				"from njpt_lunhuanshenqingb a INNER JOIN tz_qiye c on a.ccku=c.qyzzjgdm INNER JOIN tz_kudian d on a.ccku=d.qyzzjgdm and a.ccdian=d.kdbm INNER JOIN tz_cangfang e on a.ccku=e.qyzzjgdm and a.ccdian=e.kdbm and a.cfbm=e.cfbm INNER JOIN tz_GrainType f on a.pz=f.vCode where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		for(Record record:page.getList()){
			Record lcRecord=Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? ",record.getStr("liuchenghao"));
			if(lcRecord!=null){
				record.set("lcname", lcRecord.get("name"));
			}
			List<Record> teqlist=Db.find("select id from njpt_lunhuanshenqingb where yxdybllc='on' and qyzzjgdm='"+record.getStr("qyzzjgdm")+"' and id not in('"+map.get("id")+"')");
			if(teqlist.size()>0){
				//没有每年一次低于比例轮出机会
				record.set("tequan", "0");
			}else{
				record.set("tequan", "1");
			}
		}
		return page;
	}
	/**
	 * 根据id查询超架空期行文和延长时间
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylengthenjkqById(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.id,a.yccjkq_sz,a.yccjkq_sj,a.yccjkq_zf,a.sz_month,a.sj_month,a.zf_month,a.lhsl ",
				"from njpt_lunhuanshenqingb a  where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
	}
	
	//根据企业名称或库点名称 模糊查询
	public static Page<Record> queryLunHuanShenQingQX(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		
		String userid=UserKit.currentUserInfo().getUser().getString("id");
		map.put("shenpiren", userid);
		p.put("and l.lhsq_zt = ?", "lhsq_zt");
		p.put(" and (q.qymc like ?", "qymc","%%%s%%");
		p.put(" or k.kdmc like ?)", "qymc","%%%s%%");
		p.put("and l.lhsq_pz = ?", "lhsq_pz");
		p.put("and l.lhsqsj >= ?", "qssj");
		p.put("and l.lhsqsj <= ?", "jssj");
		p.put("and l.shenpiren=?", "shenpiren");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT l.*,k.kdmc as kudianmc,q.qymc as qiyemc,q.xian as diqu ",
				"from njpt_lunhuanshenqingb l LEFT JOIN  tz_kudian k  on l.qymc=k.qyzzjgdm and l.lhsq_kdmc =k.kdbm left JOIN tz_qiye q on k.qyzzjgdm=q.qyzzjgdm where 1=1 and l.lhsq_zt not in(6)"+s.getSql()+" order by l.lhsq_zt asc ",s.getParam().toArray(new Object[0]));
		
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
			Object lsxzbm=r.get("lsxz");
			Object pzbm=r.get("lhsq_pz");
			Record qymc= Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			/*if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("szqy", qymc.getStr("xian"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}*/
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
			//粮食性质
			Record lsxzmc= Db.findFirst("select vGrainProperties from tz_GrainProperties where vCode=? ",lsxzbm);
			if(lsxzmc!=null){
				r.set("lsxz", lsxzmc.getStr("vGrainProperties"));
			}
			//粮食品种
			Record lspzmc= Db.findFirst("select vName from tz_GrainType  where vCode=? ",pzbm);
			if(lspzmc!=null){
				r.set("lhsq_pz", lspzmc.getStr("vName"));
			}
		
			
		}
		
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	//根据企业名称或库点名称 模糊查询
		public static Page<Record> queryLunHuanShenQingQY(HashMap<String, Object> map) throws Exception{
			Param p = new Param();
			//UserKit.currentUserInfo().getUser().getString("id");
			String sql="SELECT c.xzqhdm as xzqhdm,c.dizhi,e.qyzzjgdm from fw_user_dep a LEFT JOIN fw_dep b ON a.depid=b.id LEFT JOIN njpt_diqu c ON b.areaid=c.areaid LEFT JOIN fw_user d ON a.userid=d.id LEFT JOIN tz_qiye e ON d.realname=e.qymc WHERE a.userid= ?";
			Record record= Db.findFirst(sql,UserKit.currentUserInfo().getUser().getString("id"));
			map.put("qyzzjgdm", record.getStr("qyzzjgdm"));
			p.put("and a.ccku = ?", "ccku");
			p.put("and a.ccdian = ?", "ccdian");
			p.put("and a.year = ?", "scnd");
			p.put("and a.status = ?", "status");
			p.put("and a.pz like ?", "pz","%s%%");
			p.put("and a.sqsj >= ?", "qssj");
			p.put("and a.sqsj <= ?", "jssj");
			p.put("and a.id=?","id");
			p.put("and a.qyzzjgdm=?","qyzzjgdm");
			SqlAndParam s = SqlUtil.buildSql(p, map);
			Page<Record> page = Db.paginate(
						Integer.valueOf(String.valueOf(map.get("page"))),
						Integer.valueOf(String.valueOf(map.get("rows"))),
						" select b.qymc,c.kdmc,d.cfmc,e.vName,a.* ",
						" from njpt_lunhuanshenqingb a INNER JOIN tz_qiye b on a.ccku=b.qyzzjgdm INNER JOIN tz_kudian c on a.ccku=c.qyzzjgdm and a.ccdian=c.kdbm INNER JOIN tz_cangfang d on a.ccku=d.qyzzjgdm and a.ccdian=d.kdbm and a.cfbm=d.cfbm  INNER JOIN tz_GrainType e on a.pz=e.vCode  where 1=1 and a.vDirection='0' "+ s.getSql()+" order by a.status asc,SUBSTRING(a.lcstatus, 2, 1) desc,a.lcstatus asc,sqsj desc ",s.getParam().toArray(new Object[0]));
			return page;
			
		}
	//根据企业名称或库点名称 模糊查询
	public static Page<Record> queryLunHuanShenQing(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		//UserKit.currentUserInfo().getUser().getString("id");
		p.put("and l.lhsq_zt = ?", "lhsq_zt");
		p.put(" and (q.qymc like ?", "qymc","%%%s%%");
		p.put(" or k.kdmc like ?)", "qymc","%%%s%%");
		p.put("and l.lhsq_pz like ?", "lhsq_pz","%s%%");
		p.put("and l.lhsqsj >= ?", "qssj");
		p.put("and l.lhsqsj <= ?", "jssj");
		
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT l.*,k.kdmc as kudianmc,q.qymc as qiyemc,q.xian as diqu ",
				"from njpt_lunhuanshenqingb l LEFT JOIN  tz_kudian k  on l.qymc=k.qyzzjgdm and l.lhsq_kdmc =k.kdbm left JOIN tz_qiye q on k.qyzzjgdm=q.qyzzjgdm where lhsq_zt in (0,1,4,5,10,20) "+ s.getSql()+" order by l.lhsq_zt asc ",s.getParam().toArray(new Object[0]));
		
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
			Object lsxzbm=r.get("lsxz");
			Object pzbm=r.get("lhsq_pz");
			Record qymc= Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			/*if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("szqy", qymc.getStr("xian"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}*/
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
			//粮食性质
			Record lsxzmc= Db.findFirst("select vGrainProperties from tz_GrainProperties where vCode=? ",lsxzbm);
			if(lsxzmc!=null){
				r.set("lsxz", lsxzmc.getStr("vGrainProperties"));
			}
			//粮食品种
			Record lspzmc= Db.findFirst("select vName from tz_GrainType  where vCode=? ",pzbm);
			if(lspzmc!=null){
				r.set("lhsq_pz", lspzmc.getStr("vName"));
			}
		
			
		}
		
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	public static Page<Record> queryqykd(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put(" and (a.qymc like ?", "qymc","%%%s%%");
		p.put(" or b.kdmc like ?)", "qymc","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select b.qyzzjgdm,b.kdbm ",
				"from tz_qiye a,tz_kudian b where a.qyzzjgdm = b.qyzzjgdm "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	public static Page<Record> selectlunhuanHZ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and szqy=?", "szqy");
		p.put("and qymc=?", "qymc");
		p.put("and lhsq_kdmc=?", "lhsq_kdmc");
		p.put("and lhsq_ch=?", "lhsq_ch");
		p.put("and lhsq_pz like ?", "lhsq_pz","%s%%");
		p.put("and lhsqsj >= ?", "qssj");
		p.put("and lhsqsj <= ?", "jssj");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb where lhsq_zt=0 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		
		for(Record r:page.getList()){
//			Object xzqhdm=r.get("xzqhdm");
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
			Object vCode=r.get("lhsq_pz");
			
			Record qymc= Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("szqy", qymc.getStr("xian"));
			}
			Record  liangshiPZ= Db.findFirst("select vName from tz_GrainType where vCode=?",vCode);
			if(liangshiPZ!=null){
				r.set("lhsq_pz", liangshiPZ.getStr("vName"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
		}
		
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	
	public static Page<Record> selectlhsqlb(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and szqy=?", "szqy");
		p.put("and qymc=?", "qymc");
		p.put("and lhsq_kdmc=?", "lhsq_kdmc");
		p.put("and lsxz=?", "lsxz");
		p.put("and lhsq_pz=?", "lhsq_pz");
		p.put("and sqlhsj=?", "sqlhsj");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb where 1=1"+ s.getSql(),s.getParam().toArray(new Object[0]));
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
			Record qymc= Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("szqy", qymc.getStr("xian"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
		}
		
		
		
		return page;
	
		
	}
	public static Page<Record> selectQRSQ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and lhsq_sqwh=?", "lhsq_sqwh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
		//	Object lsxzbm=r.get("lsxz");
			Object pzbm=r.get("lhsq_pz");
			Object lhsq_kdbg=r.get("lhsq_kdbg");//库点变更
			Object lhsq_chbg=r.get("lhsq_chbg");//仓号变更
			Object lhsq_pzbg=r.get("lhsq_pzbg");//品种变更
			Record qymc= Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("szqymc", qymc.getStr("xian"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
			//粮食品种
			Record lspzmc= Db.findFirst("select vName from tz_GrainType  where vCode=? ",pzbm);
			if(lspzmc!=null){
				r.set("lhsq_pz", lspzmc.getStr("vName"));
			}
			
		}
		
	
		
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	public static Page<Record> selectQRTZ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and lhsq_sqwh=?", "lhsq_sqwh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qymc");
			Object kdbm=r.get("lhsq_kdmc");
			Object cfbm=r.get("lhsq_ch");
		//	Object lsxzbm=r.get("lsxz");
			Object pzbm=r.get("lhsq_pz");
			Object lhsq_kdbg=r.get("lhsq_kdbg");//库点变更
			Object lhsq_chbg=r.get("lhsq_chbg");//仓号变更
			Object lhsq_pzbg=r.get("lhsq_pzbg");//品种变更
			Record qymc= Db.findFirst("select qymc,qyzzjgdm,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			if(qymc!=null){
				r.set("qymc", qymc.getStr("qymc"));
				r.set("qyzzjgdm", qymc.getStr("qyzzjgdm"));
				r.set("szqymc", qymc.getStr("xian"));
			}
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("lhsq_kdmc", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("lhsq_ch", cfmc.getStr("cfmc"));
			}
			//粮食品种
			Record lspzmc= Db.findFirst("select vName from tz_GrainType  where vCode=? ",pzbm);
			if(lspzmc!=null){
				r.set("lhsq_pz", lspzmc.getStr("vName"));
			}
			
		}
		
		return page;
		//Db.find("SELECT j.biaodihao,j.pinzhong,j.chengchudanwei,j.nianfen,j.jiaoyishuliang,j.qibaojia,j.zuixinbaojia,c.daojishi,c.liupaishijian,j.zhuangtai FROM gnjy_jingbiao j, gnjy_canshu c WHERE j.biaodihao=c.biaodihao");
	
		
	}
	//编辑轮换申请
	public static boolean saveLunHuanB1(HashMap<String, Object> map) throws Exception {
		boolean bool=false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String time=sdf.format(date);
		Db.update("delete from njpt_shenheyijian where id=? and liuchenghao=?",map.get("id"),map.get("liuchenghao"));
		Record record=new Record();
			record.setColumns(map);
			record.set("lcstatus",0);
			record.set("sqsj", time);
			record.set("status",0);
			record.remove("uuid");
			if(!map.containsKey("yxdybllc")){
				record.set("yxdybllc","");
			}
			if(!map.containsKey("iszclc")){
				record.set("iszclc","");
			}
			bool= Db.update("njpt_lunhuanshenqingb", record);
		
		return bool;
		
	}
	public static Page<Record> querybylhsqwh(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and lhsq_sqwh=?", "lhsq_sqwh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb_duotiao where 1=1"+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> selectlunhuan(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and szqy=?", "szqy");
		p.put("and qymc=?", "qymc");
		p.put("and lhsq_sqwh=?", "lhsq_sqwh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb where 1=1"+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Record selectlhpz(HashMap<String, Object> map) throws Exception{
	
		Record pizhun=Db.findFirst("select * from njpt_lunhuanshenqingb where lhsq_sqwh=?",map.get("lhsq_sqwh"));
			return pizhun;
	}
	public static Page<Record> selectquanbuCH(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.cfbm,c.cfmc ",
				"from tz_cangfang c where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
		//"select c.cfmc,c.cfbm",
		//+" GROUP BY c.cfmc,c.cfbm"
	}
	public static Page<Record> selectpz(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select vCode,vName ",
				"from tz_GrainType  where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
		//"select c.cfmc,c.cfbm",
		//+" GROUP BY c.cfmc,c.cfbm"
	}
	public static Page<Record> selectPZ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and b.qyzzjgdm=?", "qyzzjgdm");
		p.put("and b.kdbm=?", "kdbm");
		p.put("and b.vWareHouseCode =?", "cfmc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT a.vCode,a.vName ",
				"from tz_GrainType a LEFT JOIN kc_CurrentStock b on a.vCode=b.vGrainSubTypeCode WHERE 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;

	}
	
	//查询性质
	public static Page<Record> selectXZ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select vCode,vGrainProperties ",
				"from tz_GrainProperties  where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;

	}
	public static Page<Record> selectCH(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and c.qyzzjgdm=?", "qyzzjgdm");
		p.put("and c.kdbm=?", "kdbm");
		p.put("and k.kdmc =?", "kdmc");
		p.put("and c.cfbm =?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.cfbm,c.cfmc,c.kdbm,c.qyzzjgdm ",
				"from tz_cangfang c,tz_kudian k WHERE  k.kdbm=c.kdbm and k.qyzzjgdm=c.qyzzjgdm "+ s.getSql()+"GROUP BY c.cfbm,c.cfmc,c.kdbm,c.qyzzjgdm",s.getParam().toArray(new Object[0]));
		return page;
		//"select c.cfmc,c.cfbm",
		//+" GROUP BY c.cfmc,c.cfbm"
	}
	
	public static Page<Record> selectcbjhCH(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and c.qyzzjgdm=?", "qyzzjgdm");
		p.put("and c.kdbm=?", "kdbm");
		p.put("and k.kdmc =?", "kdmc");
		p.put("and c.cfbm =?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.cfbm,c.cfmc,c.kdbm,c.qyzzjgdm ",
				"from tz_cangfang c INNER JOIN njpt_chubeiliangjihua_kucun k on c.qyzzjgdm=k.qyzzjgdm and c.kdbm=k.kdbm and c.cfbm=k.cfbm  "+ s.getSql()+"GROUP BY c.cfbm,c.cfmc,c.kdbm,c.qyzzjgdm order by c.cfbm",s.getParam().toArray(new Object[0]));
		return page;
		//"select c.cfmc,c.cfbm",
		//+" GROUP BY c.cfmc,c.cfbm"
	}
	public static Page<Record> selectAJ(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and a.qyzzjgdm=?", "qyzzjgdm");
		p.put("and a.kdbm=?", "kdbm");
		p.put("and a.cfbm=?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.ajbh,a.ajmc ",
				"from tz_cangfang c,tz_aojian a WHERE  a.cfbm=c.cfbm and a.qyzzjgdm=c.qyzzjgdm and c.kdbm=a.kdbm "+ s.getSql()+" GROUP BY a.ajbh,a.ajmc",s.getParam().toArray(new Object[0]));
		return page;
	//"select c.cfmc,c.cfbm",
	//+" GROUP BY c.cfmc,c.cfbm"
	}
	public static Page<Record> selectKDMC(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and q.qyzzjgdm=?", "qyzzjgdm");
		p.put("and fw.name=?", "name");
		p.put("and q.qymc=?", "qymc"); 
		p.put("and k.isksh=?", "isksh"); 
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select q.qyzzjgdm as qyzzjgdm,k.kdbm as kdbm,k.kdmc as kdmc,fw.orderno as orderno,k.ID as ID,q.xzqhdm as xzqhdm ",
				"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm where 1=1 and k.kdlxbh<>'06' "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,k.kdbm,k.kdmc,k.ID,q.xzqhdm ORDER BY fw.orderno ",s.getParam().toArray(new Object[0]));
		return page;
	
		
	}
	public static Page<Record> selectKDMCJK(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and q.qyzzjgdm=?", "qyzzjgdm");
		p.put("and fw.name=?", "name");
		p.put("and q.qymc=?", "qymc"); 
		p.put("and d.xzqhdm=?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select q.qyzzjgdm as qyzzjgdm,k.kdbm as kdbm,k.kdmc as kdmc,fw.orderno as orderno,k.ID as ID,q.xzqhdm as xzqhdm ",
				"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm where 1=1 and k.kdlxbh<>'06' AND k.haveshipin='1' "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,k.kdbm,k.kdmc,k.ID,q.xzqhdm ORDER BY fw.orderno ",s.getParam().toArray(new Object[0]));
		return page;
	
		
	}
	
	public static Page<Record> selectQYMC(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and q.xzqhdm=?", "xzqhdm");
		p.put("and q.qymc like ?", "qymc","%%%s%%");
		p.put("and q.ID = ?", "qiyeid");
		p.put("and fw.name=?", "name"); 
		if(!UserKit.currentUserInfo().getAuth().contains("auth_directly")){
			p.put("and k.isksh=?", "isksh"); 
		}
		SqlAndParam s = SqlUtil.buildSql(p, map);
		if(UserKit.currentUserInfo().getAuth().contains("auth_directly")){
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))),
					"select q.ID as id,q.qyzzjgdm as qyzzjgdm,q.qymc as qymc,q.xzqhdm as xzqhdm,fw.orderno as orderno ",
					"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,q.qymc,q.xzqhdm,q.ID ORDER BY fw.orderno",s.getParam().toArray(new Object[0]));
			return page;
		}else{
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))),
					"select q.ID as id,q.qyzzjgdm as qyzzjgdm,q.qymc as qymc,q.xzqhdm as xzqhdm,fw.orderno as orderno ",
					"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm where 1=1 and k.kdlxbh<>'06' "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,q.qymc,q.xzqhdm,q.ID ORDER BY fw.orderno",s.getParam().toArray(new Object[0]));
			return page;
		}
		
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
				" from fw_area a LEFT JOIN njpt_diqu b on a.id=b.areaid where a.parentid!='root' " + s.getSql() + " ORDER BY a.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
		
		
	}
	public static boolean querenshenqing(HashMap<String, Object> map) throws Exception {
		String lhsq_lhwc=(String) map.get("lhsq_lhwc");
		String id=Db.find("select * from njpt_lunhuanshenqingb where lhsq_sqwh=?",map.get("lhsq_sqwh")).get(0).getStr("id");
		Record record=new Record().set("id", id).set("lhsq_lhwc", 1);
		boolean bool= Db.update("njpt_lunhuanshenqingb", record);
		return bool;
		
	}
	
	public static boolean deleteSQ(HashMap<String, Object> map) throws Exception {
		String id=(String) map.get("id");
		boolean bool= Db.deleteById("njpt_lunhuanshenqingb", id);
		return bool;
		
	}
	public static boolean querentongzhi(HashMap<String, Object> map) throws Exception {
		String lhsq_lhwc=(String) map.get("lhsq_lhwc");
		String id=Db.find("select * from njpt_lunhuanshenqingb where lhsq_sqwh=?",map.get("lhsq_sqwh")).get(0).getStr("id");
		Record record=new Record().set("id", id).set("lhsq_lhwc", 2);
		boolean bool= Db.update("njpt_lunhuanshenqingb", record);
		return bool;
		
	}
	/**
	 * 根据临时的uuid查询多条轮换记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylinshi(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		
		p.put(" and lhsq_sqwh", "lhsq_sqwh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_lunhuanshenqingb_duotiao where 1=1 "+ s.getSql(),s.getParam().toArray(new Object[0]));
		return page;
		
	}
	//如果id为空为新增，不为空为编辑，
	public static boolean saveLHSQ(HashMap<String, Object> map) throws Exception {
		String id=(String)map.get("id");
		Record record = new Record().setColumns(map);
				
		boolean bool=false;
		if(StrKit.isBlank(id)){//
			record.set("id", SqlUtil.uuid());
			  bool=Db.save("njpt_lunhuanshenqingb", record);
		}else{
			record.set("id", id);
			bool=Db.update("njpt_lunhuanshenqingb", record);
			
		
		}
		
		return bool;
		
	}	
		//提交轮换批准，状态改为1（轮换申请提交）
			public static boolean LunHuanSQTJ(HashMap<String, Object> map) throws Exception {
				Calendar curr = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Record record=new Record();
				record.set("id", map.get("id"));
				record.set("lcstatus",1);
				record.set("sqsj_tijiao", sdf.format(curr.getTime()));
				boolean bool= Db.update("njpt_lunhuanshenqingb","id", record);
				return bool;
				
			}
			//提交轮换批准，状态改为1（轮换申请提交）
			public static Ret LunHuanSQSC(HashMap<String, Object> map) throws Exception {
				Record sq= Db.findById("njpt_lunhuanshenqingb", map.get("id"));
				Ret ret=new Ret();
				boolean bool=false;
				Record lcRecord=Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? order by jiedian desc",sq.getStr("liuchenghao"));
				String lcstatus=sq.getStr("lcstatus");
				if(("1".equals(sq.getStr("status")))||(Integer.valueOf(lcstatus)==(lcRecord.getInt("jiedian")))||(Integer.valueOf(lcstatus)==(lcRecord.getInt("jiedian")+1))){
					ret.put("msg","删除失败，已完成或待审核的信息不可删除").put("bool",bool);
				}else{
					bool= Db.deleteById("njpt_lunhuanshenqingb","id", map.get("id"));
					ret.put("bool",bool);
				}
				return ret;
				
			}
		//保存轮换申请（待提交状态）
		public static boolean saveLunHuanB(HashMap<String, Object> map) throws Exception {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String time=sdf.format(date);
			Record record=new Record();
			record.set("id", SqlUtil.uuid());
			record.set("sqsj", time);
			record.setColumns(map);
			record.set("lcstatus",0);
			record.set("iscjkq",0);
			record.set("status",0);
			record.set("vDirection", 0);
			record.remove("uuid");
			if(!map.containsKey("yxdybllc")){
				record.set("yxdybllc","");
			}
			if(!map.containsKey("iszclc")){
				record.set("iszclc","");
			}
			boolean bool= Db.save("njpt_lunhuanshenqingb","id", record);
			return bool;
			
		}
		
		/**
		 * 保存轮入审批
		 * @param map
		 * @return
		 */
		@Before(Tx.class)
		public static boolean lunrushenpi(HashMap<String, Object> map) {
			String sqwh = map.get("sqwh").toString();
			String pzwh = map.get("pzwh").toString();
			String pzsj = map.get("pzsj").toString();
			String lunruimg = map.get("lunruimg").toString();
			String bgkdimg = map.get("bgkdimg").toString();
			String bgpzimg = map.get("bgpzimg").toString();
			String rsql = "select * from njpt_lunrushenqingb where sqwh = ?";
			Record record = Db.findFirst(rsql, sqwh);
			String lcstatus = record.getStr("lcstatus");
			String sumjiedian = record.getStr("sumjiedian");
			String sql;
			if(lcstatus.equals(sumjiedian)){
				sql = "update njpt_lunrushenqingb set status=1, lcstatus=lcstatus+1, pzwh= ?,pzsj = ?,lunruimg = ?,bgkdimg = ?, bgpzimg = ?  where sqwh = ?";
			}else{
				sql = "update njpt_lunrushenqingb set lcstatus=lcstatus+1, pzwh= ?,pzsj = ?,lunruimg = ?,bgkdimg = ?, bgpzimg = ?  where sqwh = ?";
			}
			
			int num= Db.update(sql,pzwh,pzsj,lunruimg,bgkdimg,bgpzimg,sqwh);
			boolean bool = true;
			if(num>0){
				bool = true;
			}else{
				bool = false;
			}
			
			//是否审批完成
			if(lcstatus.equals(sumjiedian)){
				String psql = "select * from njpt_lunrushenqingb where sqwh = ?";
				List<Record> listrecord = Db.find(psql, sqwh);
				
				Calendar a=Calendar.getInstance();
				int niandu = a.get(Calendar.YEAR);
				for(Record r:listrecord){
					
					/**
					 * 新增库存
					 */
					String qyzzjgdm = r.getStr("ccku");
					String kdbm = r.getStr("ccdian");
					String cfbm = r.getStr("cfbm");
					double cflhsl = Double.parseDouble(r.getStr("cflhsl")) * 1000;
					String pz = r.getStr("pz");
					String xz = "122";
					String xzqhdm = r.getStr("xzqhdm");
					String grade = r.getStr("grade");
					String shuifen = r.getStr("shuifen");
					String year = r.getStr("year");
					Record rekc = new Record();
					rekc.set("xzqhdm", xzqhdm).set("qyzzjgdm", qyzzjgdm).set("kdbm", kdbm).set("cfbm", cfbm)
					.set("dmStock", cflhsl).set("pinzhong", pz).set("xingzhi", xz).set("year", year)
					.set("shuifen", shuifen).set("grade", grade);
					boolean flag = Db.save("njpt_chubeiliangjihua_kucun", rekc);
					bool = bool && flag;
					
					String findsql = "select * from njpt_chubeiliangjihua where ccqy = ? and cckd = ? and ccch = ? and niandu = ?";
					List<Record> fr = Db.find(findsql, qyzzjgdm,kdbm,cfbm,niandu);
					
					/**
					 * 如果储备粮计划表没有，新增计划
					 */
					if(fr.isEmpty()){
						Record rejh = new Record();
						String id = SqlUtil.uuid();
						if("111".equals(pz)){		//更改储备粮计划小麦
							rejh.set("quming", xzqhdm).set("ccqy", qyzzjgdm).set("cckd", kdbm).set("ccch", cfbm)
							.set("niandu", niandu).set("jhsl", cflhsl).set("xingzhi", xz).set("pinzhong", pz)
							.set("cbljh_xm", cflhsl).set("cbljh_jd", "0").set("cbljh_xd", "0").set("cbljh_xj", cflhsl)
							.set("status", "0").set("id", id);
						}
						if("1131".equals(pz)){		//更改储备粮计划籼稻
							rejh.set("quming", xzqhdm).set("ccqy", qyzzjgdm).set("cckd", kdbm).set("ccch", cfbm)
							.set("niandu", niandu).set("jhsl", cflhsl).set("xingzhi", xz).set("pinzhong", pz)
							.set("cbljh_xm", "0").set("cbljh_jd", "0").set("cbljh_xd", cflhsl).set("cbljh_xj", cflhsl)
							.set("status", "0").set("id", id);
						}
						if("1132".equals(pz)){		//更改储备粮计划粳稻
							rejh.set("quming", xzqhdm).set("ccqy", qyzzjgdm).set("cckd", kdbm).set("ccch", cfbm)
							.set("niandu", niandu).set("jhsl", cflhsl).set("xingzhi", xz).set("pinzhong", pz)
							.set("cbljh_xm", "0").set("cbljh_jd", cflhsl).set("cbljh_xd", "0").set("cbljh_xj", cflhsl)
							.set("status", "0").set("id", id);
						}
						boolean jhcount = Db.save("njpt_chubeiliangjihua", rejh);
						bool = bool && jhcount;
						
					}else{		//如果有计划 更改计划
						String jhsql = ""; 
						if("111".equals(pz)){		//更改储备粮计划小麦
							jhsql = "update njpt_chubeiliangjihua set pinzhong=111, jhsl = ?, cbljh_xm = ?, cbljh_jd = 0,cbljh_xd = 0,"
									+ "cbljh_xj = ?,niandu = ?  where ccqy = ? and cckd = ? and ccch = ?";
						}
						if("1131".equals(pz)){		//更改储备粮计划籼稻
							jhsql = "update njpt_chubeiliangjihua set pinzhong=1131, jhsl = ?, cbljh_xm = 0, cbljh_jd = 0,cbljh_xd = ?,"
									+ "cbljh_xj = ?,niandu = ?  where ccqy = ? and cckd = ? and ccch = ?";
						}
						if("1132".equals(pz)){		//更改储备粮计划粳稻
							jhsql = "update njpt_chubeiliangjihua set pinzhong=1132, jhsl = ?, cbljh_xm = 0, cbljh_jd = ?,cbljh_xd = 0,"
									+ "cbljh_xj = ?,niandu = ?  where ccqy = ? and cckd = ? and ccch = ?";
						}
						int jhc = Db.update(jhsql, cflhsl,cflhsl,cflhsl,niandu,qyzzjgdm,kdbm,cfbm);
						boolean jflag = true;
						if(jhc > 0){
							jflag = true;
						}else{
							jflag = false;
						}
						bool = bool && jflag;
					}
					
				}
			}
			
			return bool;
		}
		
		/**
		 * 保存轮入总数
		 * @param map
		 * @return
		 */
		public static boolean saveSumlunru(HashMap<String, Object> map) {
			String qyzzjgdm = map.get("qyzzjgdm").toString();
			String kdbm = map.get("kdbm").toString();
			String pz = map.get("pz").toString();
			String sumlunru = map.get("sumlunru").toString();
			String bsql = "select * from njpt_lunruhj where qyzzjgdm = ? and kdbm = ? and pz = ?";
			boolean flag = Db.find(bsql, qyzzjgdm,kdbm,pz).isEmpty();

			Record record = new Record();
			record.setColumns(map);
			record.set("id", SqlUtil.uuid());
			
			boolean bool;
			if(flag){
				bool = Db.save("njpt_lunruhj", record);
			}else{
				String sql = "update njpt_lunruhj set sumlunru=sumlunru+"+sumlunru+" "
						+ "where qyzzjgdm = ? and kdbm = ? and pz = ?";
				int num = Db.update(sql, qyzzjgdm,kdbm,pz);
				if(num > 0){
					bool = true;
				}else{
					bool = false;
				}
			}
			return bool;
		}
		
		//保存轮换批准，状态改为4（确认申请）
		public static boolean saveLunHuanPZ(HashMap<String, Object> map) throws Exception {
			Record record=new Record();
			record.set("id", map.get("id"));
			record.setColumns(map);
			
		
			boolean bool=Db.tx(new IAtom() {
				@Override
				public boolean run() throws SQLException {
					boolean upd= Db.update("njpt_lunhuanshenqingb", record);
					return upd;
				}
			});
			return bool;
			
		}
		//保存轮换批准通过意见
			@Before(Tx.class)
				public static Ret savetongguoidea(HashMap<String, Object> map) throws Exception {
					Ret ret=new Ret();
					Record record=new Record();
					record.setColumns(map);
					record.remove("liyou");
					Record lhRecord=Db.findFirst("select * from njpt_lunhuanshenqingb where id=? ",map.get("id"));
					
					List<Record> list= Db.find("select * from njpt_lunhuanshenqingb where pzwh='"+map.get("pzwh")+"' and id<>'"+map.get("id")+"'");
					if(("".equals((String)map.get("pzwh")))&&(list.size()>0)){
						ret.put("ret",false).put("message","轮换批准失败,批准文号已存在！");
						return ret;
					}
					
					int lcstatus=Integer.valueOf(lhRecord.getStr("lcstatus"));
					//流程节点+1
					record.set("lcstatus",lcstatus+1 );
					//最后一个节点：修改status为1 和是否上传批准行文
					Record lcrecord=new Record();
					lcrecord=Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? order by jiedian desc ",lhRecord.getStr("liuchenghao"));
					if((lcstatus+1)==(Integer.valueOf(lcrecord.getInt("jiedian")+1))){
						record.set("status",1 );
						record.set("iscjkq", 0);
							if("".equals((String)map.get("pzwh"))||null==map.get("pzwh")){
								ret.put("ret",false).put("message","轮换批准失败,请输入批准文号！");
								return ret;
							}
							if("".equals((String)map.get("pzsj"))||null==map.get("pzsj")){
								ret.put("ret",false).put("message","轮换批准失败,请先输入批准日期！");
								return ret;
							}
							if("".equals((String)map.get("lrqx"))||null==map.get("lrqx")){
								ret.put("ret",false).put("message","轮换批准失败,请先输入轮入期限！");
								return ret;
							}
							if("".equals((String)map.get("wclrsj"))||null==map.get("wclrsj")){
								ret.put("ret",false).put("message","轮换批准失败,请先输入轮入结束日期！");
								return ret;
							}
							if("".equals((String)map.get("pzxingwen"))||null==map.get("pzxingwen")){
								ret.put("ret",false).put("message","轮换批准失败,请先上传行文！");
								return ret;
							}
						
					}
					Calendar curr = Calendar.getInstance();
					SimpleDateFormat shifenmiao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String lhpzsj=shifenmiao.format(curr.getTime());
					record.set("pzsj_shiji", lhpzsj);//轮换批准系统实际批准时间
					
					Record yijian=new Record();
					yijian.set("id", map.get("id"))
						.set("liuchenghao", lhRecord.get("liuchenghao"))
						.set("jiedian", lcstatus)
						.set("shenpiren", UserKit.currentUserInfo().getUser().getString("id"))
						.set("liyou", map.get("liyou"))
						.set("type", 1);
					boolean uplh= Db.update("njpt_lunhuanshenqingb", record);
					boolean upyj=Db.save("njpt_shenheyijian", yijian);
					boolean updmstock=true;
					if((lcstatus+1)==(Integer.valueOf(lcrecord.getInt("jiedian")+1))){
						Record stockrec=Db.findFirst("select * from njpt_chubeiliangjihua_kucun where qyzzjgdm=? and kdbm=? and cfbm=? ",lhRecord.get("ccku"),lhRecord.get("ccdian"),lhRecord.get("cfbm"));
						BigDecimal dmstock=stockrec.getBigDecimal("dmStock");
						BigDecimal lhsl=new BigDecimal(lhRecord.getStr("lhsl"));
						BigDecimal jingdu=new BigDecimal(1000);
						BigDecimal last= dmstock.subtract(lhsl.multiply(jingdu));
						if(last.compareTo(new BigDecimal(0))<=0){
							last=new BigDecimal(0);
							updmstock = Db.delete("njpt_chubeiliangjihua_kucun", new Record().set("id", stockrec.get("id")));
						}else{
							updmstock = Db.update("njpt_chubeiliangjihua_kucun", new Record().set("id", stockrec.get("id")).set("dmStock", last));
						}
					}	
							
					return ret.put("ret",uplh&&upyj&&updmstock);
					
				}
				//保存轮换批准-驳回理由
				public static boolean saveBohuilunhuan(HashMap<String, Object> map) throws Exception {
					Record record=new Record();
					record.set("id", map.get("id"));
					Record lhRecord=Db.findFirst("select * from njpt_lunhuanshenqingb where id=? ",map.get("id"));
					
					record.set("lcstatus",lhRecord.getStr("lcstatus")+"0" );
					record.set("pzsj", "");
					record.set("lrqx", "");
					record.set("wclrsj", "");
					record.set("pzwh", "");
					Calendar curr = Calendar.getInstance();
					SimpleDateFormat shifenmiao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String lhpzsj=shifenmiao.format(curr.getTime());
					record.set("pzsj_shiji", lhpzsj);//轮换批准系统实际批准时间
					
					Record yijian=new Record();
					yijian.set("id", map.get("id"))
						.set("liuchenghao", lhRecord.get("liuchenghao"))
						.set("jiedian", lhRecord.getStr("lcstatus"))
						.set("shenpiren", UserKit.currentUserInfo().getUser().getString("id"))
						.set("liyou", map.get("liyou"))
						.set("type", 0);
					
					boolean bool=Db.tx(new IAtom() {
						@Override
						public boolean run() throws SQLException {
							boolean upd= Db.update("njpt_lunhuanshenqingb", record);
							boolean upd2=Db.save("njpt_shenheyijian", yijian);
							return upd&&upd2;
						}
					});
					return bool;
					
				}
		//保存确认申请，状态改为5（确认通知）
		public static boolean saveLunHuanQRSQ(HashMap<String, Object> map) throws Exception {
			Record record=new Record();
			record.set("id", map.get("id"));
			record.setColumns(map);
			record.set("lhsq_zt",5);
			
			Calendar curr = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String qrsqsj=sdf.format(curr.getTime());
			record.set("qrsqsj", qrsqsj);//确认申请时间

			
			boolean bool= Db.update("njpt_lunhuanshenqingb", record);
			return bool;
			
		}
		//保存确认通知，状态改为0（完成）
		public static boolean saveLunHuanQRTZ(HashMap<String, Object> map) throws Exception {
			Calendar curr = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String qrtzsj=sdf.format(curr.getTime());
			Record record=new Record();
			record.set("id", map.get("id"));
			record.setColumns(map);
			record.set("lhsq_zt",0);
			record.set("qrtzsj", qrtzsj);
			boolean bool= Db.update("njpt_lunhuanshenqingb", record);
			return bool;
			
		}
		//根据uuid查询当条记录的提示结果
		public static Record querytishiByuuid(HashMap<String, Object> map) throws Exception{
			String id=(String) map.get("id");
			//查出年限，品种
			Record record =Db.findFirst("select * from njpt_lunhuanshenqingb_duotiao where id=?", id);
			//全市总量比例，总量比例，分品种比例，最早生产年限
			return record;
		}
		public static Record findYearPZ(HashMap<String, Object> map) throws Exception{
			String cfbm=(String) map.get("cfbm");
			String kdbm=(String) map.get("kdbm");
			String qyzzjgdm=(String) map.get("qyzzjgdm");
			//查出年限，品种
			Record record =Db.findFirst("select c.year ,g.vCode,g.vName,round((sum(c.dmStock)/1000),3) as zongkucun from njpt_chubeiliangjihua_kucun c INNER JOIN tz_GrainType g on c.pinzhong=g.vCode where c.qyzzjgdm=? and c.kdbm=? and c.cfbm=? GROUP BY c.year,g.vCode,g.vName", qyzzjgdm,kdbm,cfbm);
			//全市总量比例，总量比例，分品种比例，最早生产年限
			return record;
			
		}
		/**
		 * 添加一条轮换申请，且得到轮换批准提示结果，并保存
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public static Record pizhuntishi(HashMap<String, Object> map) throws Exception{
			String qyzzjgdm=(String) map.get("qyzzjgdm");//企业组织机构代码
			String kdbm=(String) map.get("kdbm");		 //库点编码
			String cfbm=(String) map.get("cfbm");		 //仓房编码
			String scnx=(String)map.get("scnx");		 //生产年限
			double lhsl=Double.valueOf((String) map.get("lhsl"));		 //轮换数量
			String pz=(String) map.get("pz");			 //品种
			//得到当前年度
			Calendar cal = Calendar.getInstance();
			int yearInt = cal.get(Calendar.YEAR);
			String year=String.valueOf(yearInt);
			//全市总量比例，总量比例，分品种比例，最早生产年限
			Record tishi=new Record();
			
			//全市总量比例
			BigDecimal quanshizl =Db.findFirst("select sum(dmStock) as quanshizl from kc_CurrentStock where vGrainPropertyCode in(123,122) ").get("quanshizl");
			double qszlbldouble=(double)(lhsl/(quanshizl.doubleValue()));
			DecimalFormat format = new DecimalFormat("0.00%");
			String qszlbl = format.format(qszlbldouble);
			/*//总量比例
			BigDecimal zl =Db.findFirst("select sum(dmStock) as zl from kc_CurrentStock where  qyzzjgdm=? and kdbm=?",qyzzjgdm,kdbm).get("zl");
			double zldouble=(double)(lhsl/(zl.doubleValue()));
			DecimalFormat format2 = new DecimalFormat("0.00%");
			String zlbl=format2.format(zldouble);
			//分品种比例
			BigDecimal fpz=Db.findFirst("select sum(dmStock) as fpz from kc_CurrentStock where  qyzzjgdm=? and kdbm=? and vGrainSubTypeCode=?",qyzzjgdm,kdbm,pz).get("fpz");
			double fpzdouble=(double)(lhsl/(fpz.doubleValue()));
			DecimalFormat format3 = new DecimalFormat("0.00%");
			String fpzbl=format3.format(fpzdouble);*/
			//最早生产年限
			String zzscnx=Db.findFirst("select min(year) as year  from kc_CurrentStock where  qyzzjgdm=? and kdbm=? and vGrainSubTypeCode=? and vGrainPropertyCode in(122,123)",qyzzjgdm,kdbm,pz).get("year");
			
			//提示结果——是否最早生产年限
			if (scnx.equals(zzscnx)) {
				tishi.set("sfzzscnx", "1");
			}else {
				tishi.set("sfzzscnx", "0");
			}
			//现有库存实物数-报批数/计划数不得少于1/2
			//先取出当前库存
			Record cfswkcrecord=Db.findFirst("select sum(dmStock) as cfswkc from kc_CurrentStock where  qyzzjgdm=? and kdbm=?  and vGrainPropertyCode in(122,123)",qyzzjgdm,kdbm);
			BigDecimal cfswkcbigdecimal=cfswkcrecord.get("cfswkc");
			double cfswkc=cfswkcbigdecimal.doubleValue();
			//查询当前年度该库点储备计划数
			Record cbljhsRecord=Db.findFirst("select sum(cbljh_xj) as cbljhs from njpt_chubeiliangjihua where  ccqy=? and cckd=? and niandu=? ",qyzzjgdm,kdbm,year);
			double cbljhs=Double.valueOf(((BigDecimal)(cbljhsRecord.get("cbljhs"))).doubleValue());
			//得到现有库存实物数-报批数/计划数
			double budiyu=(cfswkc-lhsl)/cbljhs;
		
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);//设置保留小数位
			nf.setRoundingMode(RoundingMode.HALF_UP); //设置舍入模式
			String zlbl = nf.format(budiyu);
			String fpzbl="";
			if((budiyu-0.5)>=0){
				tishi.set("budiyu", "1");
			}else {
				tishi.set("budiyu", "0");
			}
			//分品种比例——小麦：不低于小麦库存的1/2
			if ((pz).startsWith("111")) {
				//小麦实际库存
				Record xmswkcRecord=Db.findFirst("select sum(dmStock) as xmswkc from kc_CurrentStock where qyzzjgdm=? and kdbm=? and  vGrainSubTypeCode=? and vGrainPropertyCode in(122,123)",qyzzjgdm,kdbm,pz);
				BigDecimal xmswkcBigDecimal=xmswkcRecord.get("xmswkc");
				double xmswkc=xmswkcBigDecimal.doubleValue();
				Record xmcbljhRecord=Db.findFirst("select sum(cbljh_xm) as cbljhxm from njpt_chubeiliangjihua where  ccqy=? and cckd=? and niandu=?",qyzzjgdm,kdbm,year);
				double xmcbljh=Double.valueOf(((BigDecimal)(xmcbljhRecord.get("cbljhxm"))).doubleValue());
				double xmbudiyuDouble=(xmswkc-lhsl)/xmcbljh;
				fpzbl = nf.format(xmbudiyuDouble);
				if ((xmbudiyuDouble-(1.0/2.0))>=0) {
					tishi.set("fpzbudiyu", "1");
				}else {
					tishi.set("fpzbudiyu", "2");
				}
			}
			//分品种比例——粳稻：不低于粳稻库存的1/4
			if (pz.startsWith("1132")) {
				Record jdswkcRecord=Db.findFirst("select sum(dmStock) as jdswkc from kc_CurrentStock where qyzzjgdm=? and kdbm=? and  vGrainSubTypeCode=? and vGrainPropertyCode in(122,123)",qyzzjgdm,kdbm,pz);
				BigDecimal jdswkcBigDecimal=jdswkcRecord.get("jdswkc");
				double jdswkc=jdswkcBigDecimal.doubleValue();
				Record jdcbljhRecord=Db.findFirst("select sum(cbljh_jd) as cbljhjd from njpt_chubeiliangjihua where  ccqy=? and cckd=? and niandu=?",qyzzjgdm,kdbm,year);
				double jdcbljh=Double.valueOf(((BigDecimal)(jdcbljhRecord.get("cbljhjd"))).doubleValue());
				double jdbudiyuDouble=(jdswkc-lhsl)/jdcbljh;
				fpzbl = nf.format(jdbudiyuDouble);
				if ((jdbudiyuDouble)-(1.0/4.0)>=0) {
					tishi.set("fpzbudiyu", "3");
				}else {
					tishi.set("fpzbudiyu", "4");
				} 
			}
			//分品种比例——籼稻：不低于籼稻库存的1/3
			if (pz.startsWith("1131")) {
				Record xdswkcRecord=Db.findFirst("select sum(dmStock) as xdswkc from kc_CurrentStock where qyzzjgdm=? and kdbm=? and  vGrainSubTypeCode=? and vGrainPropertyCode in(122,123)",qyzzjgdm,kdbm,pz);
				BigDecimal xdswkcBigDecimal=xdswkcRecord.get("xdswkc");
				double xdswkc=xdswkcBigDecimal.doubleValue();
				Record xdcbljhRecord=Db.findFirst("select sum(cbljh_xd) as cbljhxd from njpt_chubeiliangjihua where  ccqy=? and cckd=? and niandu=?",qyzzjgdm,kdbm,year);
				double xdcbljh=Double.valueOf(((BigDecimal)(xdcbljhRecord.get("cbljhxd"))).doubleValue());
				double xdbudiyuDouble=(xdswkc-lhsl)/xdcbljh;
				fpzbl = nf.format(xdbudiyuDouble);
				if ((xdbudiyuDouble)-(1.0/3.0)>=0) {
					tishi.set("fpzbudiyu", "5");
				}else {
					tishi.set("fpzbudiyu", "6");
				}
			}
			
			tishi.set("qszlbl", qszlbl).set("zlbl", zlbl).set("fpzbl", fpzbl).set("zzscnx", zzscnx);
			
			return tishi;
		}
		
		public static boolean deleteduotiao(HashMap<String, Object> map) throws Exception {
			String id=(String) map.get("id");
			boolean bool= Db.deleteById("njpt_lunhuanshenqingb_duotiao", id);
			return bool;
			
		}
		public static boolean saveLunHuanYanQiB(HashMap<String, Object> map) throws Exception {
			Record record=new Record();
			record.set("id", SqlUtil.uuid());
			record.setColumns(map);
			boolean bool= Db.save("njpt_yanqilunhuansq","id", record);
			return bool;
			
		}
		public static boolean lunhuanpizhunTG(HashMap<String, Object> map) throws Exception {
			String id=Db.find("select * from njpt_lunhuanshenqingb where lhsq_sqwh=?",map.get("lhsq_sqwh")).get(0).getStr("id");
			Record record=new Record().set("id", id).set("lhsq_pzwh", SqlUtil.uuid()).set("lhsq_lhpz", 1).set("lhsq_lhwc", 0);
			boolean bool= Db.update("njpt_lunhuanshenqingb", record);
			return bool;
			
		}
		public static boolean lunhuanpizhun(HashMap<String, Object> map) throws Exception {
			Record record=new Record().set("id", map.get("id")).set("lhsq_zt", 1);
			boolean bool= Db.update("njpt_lunhuanshenqingb", record);
			return bool;
			
		}
		public static Page<Record> selectfeikshKDMC(HashMap<String, Object> map) throws Exception{
			Param p = new Param();
			p.put("and q.qyzzjgdm=?", "qyzzjgdm");
			p.put("and fw.name=?", "name");
			p.put("and q.qymc=?", "qymc"); 
			SqlAndParam s = SqlUtil.buildSql(p, map);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(map.get("page"))),
					Integer.valueOf(String.valueOf(map.get("rows"))),
					"select q.qyzzjgdm as qyzzjgdm,k.kdbm as kdbm,k.kdmc as kdmc,fw.orderno as orderno,k.ID as ID,q.xzqhdm as xzqhdm ",
					"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm where 1=1 and k.kdlxbh<>'06' and k.id not in( select kk.id from tz_kudian kk where kk.isksh = '1' ) "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,k.kdbm,k.kdmc,k.ID,q.xzqhdm ORDER BY fw.orderno ",s.getParam().toArray(new Object[0]));
			return page;
		
			
		}
		public static Page<Record> getlhsp(HashMap<java.lang.String, Object> param) {
			Param p = new Param();
			p.put(" and q.ID=? ", "qiyeid");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select c.lhsq_zt ",
					"from njpt_lunhuanshenqingb c inner join tz_qiye q on q.qyzzjgdm=c.qymc INNER JOIN tz_kudian k on c.qymc=k.qyzzjgdm and c.lhsq_kdmc=k.kdbm INNER JOIN tz_cangfang cf on c.qymc=cf.qyzzjgdm and c.lhsq_kdmc=cf.kdbm and c.lhsq_ch=cf.cfbm  where 1=1 " + s.getSql(),
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		public static Page<Record> getlhsqbysp(HashMap<java.lang.String, Object> param) {
			Param p = new Param();
			p.put(" and c.shenpiren=? ", "shenpiren");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select c.lhsq_zt ",
					"from njpt_lunhuanshenqingb c inner join tz_qiye q on q.qyzzjgdm=c.qymc INNER JOIN tz_kudian k on c.qymc=k.qyzzjgdm and c.lhsq_kdmc=k.kdbm INNER JOIN tz_cangfang cf on c.qymc=cf.qyzzjgdm and c.lhsq_kdmc=cf.kdbm and c.lhsq_ch=cf.cfbm  where 1=1 " + s.getSql(),
					s.getParam().toArray(new Object[0]));
			return page;
		}
		public static Page<Record> findlcbylc(HashMap<String, Object> param) {
			Param p = new Param();
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(1),
					Integer.valueOf(10000),
					"select liuchenghao,name,max(jiedian) as jiedian ",
					"from  oa_shenpiliucheng where liuchengtype='4' " + s.getSql()+" group by liuchenghao,name ",
					s.getParam().toArray(new Object[0]));
			return page;
		}
		public static Page<Record> findLhsp(HashMap<String, Object> param) {
			List<String> authList = UserKit.currentUserInfo().getAuth();
			//if(authList.contains("auth_county")){
				param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
			//}
			String sql=" ";
			Param p = new Param();
			p.put("and a.id = ?", "id");
			p.put("and a.sqwh like ?", "sqwh","%%%s%%");
			p.put("and a.liuchenghao = ?", "liuchenghao");
			p.put("and a.status = ?", "status");
			p.put("and a.qiyeid = ?", "qiyeid");
			p.put("and b.shenpiren = ?", "shenpiren");
			p.put("and a.qyzzjgdm=?", "qyzzjgdm");
			p.put("and a.ccku=?", "ccku");
			p.put("and a.ccdian=?", "ccdian");
			p.put("and a.pz=?", "pz");
			p.put("and a.year=?", "year");
			p.put("and a.sqsj >= ?", "qssj");
			p.put("and a.sqsj <= ?", "jssj");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select c.qymc,d.kdmc,e.cfmc,f.vName,g.vLevelName,b.jiedian as jiedian,a.* ",
					"from njpt_lunhuanshenqingb a INNER JOIN oa_shenpiliucheng b on a.liuchenghao=b.liuchenghao INNER JOIN tz_qiye c on a.ccku=c.qyzzjgdm INNER JOIN tz_kudian d on a.ccdian=d.kdbm and a.ccku=d.qyzzjgdm inner join tz_cangfang e on a.ccku=e.qyzzjgdm and a.ccdian=e.kdbm and a.cfbm=e.cfbm INNER JOIN tz_GrainType f on a.pz=f.vCode LEFT JOIN tz_GrainLevel g on a.grade=g.vCode where a.lcstatus>0 and SUBSTRING(a.lcstatus, 1, 1)>=b.jiedian " + s.getSql()+sql+" ORDER BY a.status asc,a.lcstatus asc,a.sqsj desc ",
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		/**
		 * 查询遍历轮出申请
		 * @param map
		 * @return
		 */
		public static Page<Record> findlunchu(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.ccku = ?", "ccku");
			p.put("and a.ccdian = ?", "ccdian");
			p.put("and b.kdmc = ?", "cckuname");
			p.put("and g.vName = ?", "pz");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select a.*,g.vName as vName,b.kdmc as kdmc ",
					"from njpt_lunhuanshenqingb a INNER JOIN tz_kudian b on "
					+ "a.ccku = b.qyzzjgdm and a.ccdian = b.kdbm "
					+ "LEFT JOIN tz_GrainType g on a.pz=g.vCode where a.status=1 "
					+ s.getSql()+" ORDER BY a.sqsj desc ",
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		public static boolean validatesqwh(HashMap<String, Object> param1) {
			// TODO Auto-generated method stub
			List<Record> sqwh=Db.find("select * from njpt_lunhuanshenqingb where sqwh=? ",param1.get("sqwh"));
			if(sqwh.size()>0){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * 获取承储
		 * @param param
		 * @return
		 */
		public static Page<Record> queryCcqy(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.ccqy = ?", "qyzzjgdm");
			p.put("and d.name = ?","xian");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select a.*, b.qymc as qymc,d.name as quyu ",
					"from njpt_chubeiliangjihua a left join tz_qiye b on a.ccqy = b.qyzzjgdm "
					+ "left join njpt_diqu c on a.quming = c.xzqhdm "
					+ "left join fw_area d on c.areaid = d.id where 1=1 " + s.getSql()+" ORDER BY d.orderno ",
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		/**
		 * 寻找轮出审批
		 * @param param
		 * @return
		 */
		public static Page<Record> findlcsp(HashMap<String, Object> param) {
			// TODO Auto-generated method stub
			Param p = new Param();
			p.put("and ccku = ?", "qyzzjgdm");
			p.put("and ccdian = ?","kdbm");
			p.put("and vDirection = ?", "direction");
			p.put("and status = ?", "status");
			p.put("and pzwh = ?", "pzwh");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select * ",
					"from njpt_lunhuanshenqingb where 1=1 " + s.getSql() ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		/**
		 * 寻找空仓
		 * @param param
		 * @return
		 */
		public static Page<Record> findkongc(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and c.kdbm = ?","kdbm");
			p.put("and c.cfbm = ?", "cfbm");
			//p.put("and c.weight = ?", "weight");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			/**
			 * 排除空仓
			 */
			/*
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select c.* ",
					"from (select a.*,isnull(b.dmStock, 0) as weight "
					+ "from tz_cangfang a left join njpt_chubeiliangjihua_kucun b on "
					+ "a.qyzzjgdm = b.qyzzjgdm and a.kdbm = b.kdbm and a.cfbm = b.cfbm) as c "
					+ "where c.cfbm not in (select cfbm from njpt_lunrushenqingb where status = '0') " + s.getSql() ,
					s.getParam().toArray(new Object[0]));
			return page;
			*/
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select c.* ",
					"from  tz_cangfang c  where 1=1 " + s.getSql() ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		
		public static boolean isxingwen(HashMap<String, Object> map) {
			Record record=Db.findFirst("select * from njpt_lunhuanshenqingb where id=?",map.get("id"));
			if("".equals(record.getStr("sqxingwen"))||null==record.get("sqxingwen")){
				return true;
			}else{
				return false;
			}
		}
		public static boolean existpzwh(HashMap<String, Object> map) {
			// TODO Auto-generated method stub
			//最后一次判断批准文号是否存在
			Db.findFirst("");
			return false;
		}
		
		/**
		 * 遍历轮入申请
		 * @param map
		 * @return
		 */
		public static Page<Record> queryLunRuShenQing(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and a.ccku = ?", "qymc");
			p.put("and a.ccdian = ?","kdbm");
			p.put("and a.cfbm = ?", "cfbm");
			p.put("and a.pz = ?", "pz");
			p.put("and a.sqwh = ?", "sqwh");
			p.put("and a.status = ?", "status");
			p.put("and a.sqsj >= ?", "starttime");
			p.put("and a.sqsj <= ?", "endtime");
			p.put("and s.shenpiren = ?", "shenpiren");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select a.sqwh,min(b.qymc) as qymc,min(c.qymc) as ccku,min(d.kdmc) as kdmc, "
					+ "min(e.vName) as pz,min(a.year) as year,min(f.vLevelName) as grade, "
					+ "min(a.shuifen) as shuifen,min(a.lhsl) as lhsl,min(a.avgprice) as avgprice, "
					+ "min(a.sqsj) as sqsj,min(a.pzsj) as pzsj,min(a.status) as status,"
					+ "min(a.liuchenghao) as liuchenghao,min(tbaoren) as tbaoren,min(a.lrlx) as lrlx,"
					+ "min(a.kudian) as bgkd,min(a.bgpz) as bgpz, "
					+ "min(a.ccku) as cckuname,min(ccdian) as ccdian,min(lcstatus) as lcstatus,"
					+ "min(a.pzwh) as pzwh,min(a.ccku) as qyzzjgdm,min(a.ccdian) as kdbm,"
					+ "min(a.lunrudoc) as lunrudoc,min(a.bgkddoc) as bgkddoc, min(a.bgpzdoc) as bgpzdoc,"
					+ "min(a.lunruimg) as lunruimg, min(a.bgpzimg) as bgpzimg, min(a.bgkdimg) as bgkdimg,"
					+ "min(a.ccjhid) as ccjhid ",
					"from njpt_lunrushenqingb as a left join tz_qiye as b on "
					+ "a.qyzzjgdm = b.qyzzjgdm left join tz_qiye as c on a.ccku = c.qyzzjgdm "
					+ "left join tz_kudian as d on a.ccku = d.qyzzjgdm and a.ccdian = d.kdbm "
					+ "left join tz_GrainType as e on a.pz = e.vCode "
					+ "left join tz_GrainLevel as f on a.grade = f.vCode "
					+ "left join oa_shenpiliucheng as s on a.liuchenghao = s.liuchenghao "
					+ "where 1=1 " + s.getSql() + " group by a.sqwh,a.sqsj order by a.sqsj" ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		
		public static Page<Record> findpfhj(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and a.ccku = ?", "qymc");
			p.put("and a.ccdian = ?","kdbm");
			p.put("and a.cfbm = ?", "cfbm");
			p.put("and a.pz = ?", "pz");
			p.put("and a.status = ?", "status");
			p.put("and s.shenpiren = ?", "shenpiren");
			p.put("and a.pzsj >= ?", "starttime");
			p.put("and a.pzsj <= ?", "endtime");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select sum(convert(int,a.cflhsl)) as sumweight ",
					"from njpt_lunrushenqingb as a "
					+ "left join oa_shenpiliucheng as s on a.liuchenghao = s.liuchenghao "
					+ "where a.lcstatus >= s.jiedian " + s.getSql() + "" ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		/**
		 * 遍历轮入申请
		 * @param map
		 * @return
		 */
		public static Page<Record> queryLunRuShenQingsp(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and a.ccku = ?", "qymc");
			p.put("and a.ccdian = ?","kdbm");
			p.put("and a.cfbm = ?", "cfbm");
			p.put("and a.pz = ?", "pz");
			p.put("and a.status = ?", "status");
			p.put("and s.shenpiren = ?", "shenpiren");
			p.put("and a.pzsj >= ?", "starttime");
			p.put("and a.pzsj <= ?", "endtime");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select a.sqwh,min(b.qymc) as qymc,min(c.qymc) as ccku,min(d.kdmc) as kdmc, "
					+ "min(e.vName) as pz,min(a.year) as year,min(f.vLevelName) as grade, "
					+ "min(a.shuifen) as shuifen,min(a.lhsl) as lhsl,min(a.avgprice) as avgprice, "
					+ "min(a.sqsj) as sqsj,min(a.pzsj) as pzsj,min(a.status) as status,max(s.jiedian) as jiedian,"
					+ "min(a.lcstatus) as lcstatus,min(a.pzwh) as pzwh,min(a.lunrudoc) as lunrudoc,"
					+ "min(a.lunruimg) as lunruimg ",
					"from njpt_lunrushenqingb as a left join tz_qiye as b on "
					+ "a.qyzzjgdm = b.qyzzjgdm left join tz_qiye as c on a.ccku = c.qyzzjgdm "
					+ "left join tz_kudian as d on a.ccku = d.qyzzjgdm and a.ccdian = d.kdbm "
					+ "left join tz_GrainType as e on a.pz = e.vCode "
					+ "left join tz_GrainLevel as f on a.grade = f.vCode "
					+ "left join oa_shenpiliucheng as s on a.liuchenghao = s.liuchenghao "
					+ "where a.lcstatus >= s.jiedian " + s.getSql() + " group by a.sqwh,a.sqsj order by a.sqsj" ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		public static Page<Record> selectlrsqmx(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and sqwh = ?", "sqwh");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select * ",
					"from njpt_lunrushenqingb  "
					+ "where 1=1 " + s.getSql() + " order by cfbm" ,
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		
		/**
		 * 寻找易库点库点
		 * @param param
		 * @return
		 */
		public static Page<Record> findykd(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.ccqy <> ?", "qyzzjgdm");
			p.put("and a.cckd <> ?", "kdbm");
			p.put("and d.name = ?","xian");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select a.*, b.kdmc as kdmc,d.name as quyu ",
					"from njpt_chubeiliangjihua a left join tz_kudian b on a.ccqy = b.qyzzjgdm and a.cckd = b.kdbm "
					+ "left join njpt_diqu c on a.quming = c.xzqhdm "
					+ "left join fw_area d on c.areaid = d.id where 1=1 " + s.getSql()+" ORDER BY d.orderno ",
					s.getParam().toArray(new Object[0]));
			return page;
		}
		
		/**
		 * 删除轮入申请
		 * @param map
		 * @return
		 */
		@Before(Tx.class)
		public static boolean deleteLunRu(HashMap<String, Object> map) {
			String sqwh = (String) map.get("sqwh");
			
			String sql = "delete from njpt_lunrushenqingb where sqwh = ?";
			String rsql = "select * from njpt_lunrushenqingb where sqwh = ?";
			
			Record record = Db.findFirst(rsql, sqwh);
			int bool= Db.update(sql, sqwh);
			if(bool > 0){
				String qyzzjgdm = record.getStr("ccku");
				String kdbm = record.getStr("ccdian");
				String pz = record.getStr("pz");
				String lhsl = record.getStr("lhsl");
				String lrlx = record.getStr("lrlx");
				String kdmc = record.getStr("kudian");
				String bsql = "update njpt_lunruhj set sumlunru=sumlunru-"+lhsl+" "
						+ "where qyzzjgdm = ? and kdbm = ? and pz = ?";
				int num = 0;
				if("1".equals(lrlx)){
					bsql = "update njpt_lunruhj set sumlunru=sumlunru-"+lhsl+" "
							+ "where kdmc = ? and pz = ?";
					num = Db.update(bsql, kdmc,pz);
				}
				if("2".equals(lrlx)){
					pz = record.getStr("bgpz");
					num = Db.update(bsql,qyzzjgdm,kdbm,pz);
				}else{
					num = Db.update(bsql,qyzzjgdm,kdbm,pz);
				}
				if(num > 0){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		/**
		 * 保存轮入审批
		 * @param map
		 * @return
		 */
		@Before(Tx.class)
		public static boolean saveLunRu(HashMap<String, Object> map) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String time=sdf.format(date);
			String[] cfs = map.get("cfs").toString().split(",");
			String[] cflrsl = map.get("cflrsl").toString().split(",");
			String changekd = map.get("changekd").toString();
			String changepz = map.get("changepz").toString();
			String lrlx = "0";
			if("1".equals(changekd)){
				lrlx = "1";
			}
			if("1".equals(changepz)){
				lrlx = "2";
			}
			boolean result = true;
			for(int i = 0; i < cfs.length; i++){
				Record record = new Record();
				record.set("id", SqlUtil.uuid());
				record.setColumns(map);
				record.remove("cfs");
				record.remove("cflrsl");
				record.remove("szqy");
				record.remove("changekd");
				record.remove("changepz");
				record.set("lrlx", lrlx);
				record.remove("qymc");
				record.set("cfbm", cfs[i]);
				record.set("cflhsl", cflrsl[i]);
				record.set("status", "0");
				record.set("lcstatus", "1");
				if("0".equals(lrlx)){
					record.remove("kudian");
					record.remove("bgpz");
				}
				if("1".equals(lrlx)){
					record.remove("bgpz");
				}
				if("2".equals(lrlx)){
					record.remove("kudian");
				}
				result = result && Db.save("njpt_lunrushenqingb","id", record);
			}	
			return result;
		}
		public static boolean validatelhsl(HashMap<String, Object> param1) {
			double lhsl= Double.parseDouble((String)param1.get("lhsl"));
			Calendar a=Calendar.getInstance();
			int year= a.get(Calendar.YEAR);
			Record sumlhslRec=Db.findFirst("select isnull(sum(convert(float,lhsl)),0) as lhsl from njpt_lunhuanshenqingb where status='0' and SUBSTRING(lcstatus, 2, 1)<>'0' and ccku=? and ccdian=? and cfbm=? ",param1.get("ccku"),param1.get("ccdian"),param1.get("cfbm"));
			Record kucunRec=Db.findFirst("SELECT ISNULL(sum(dmStock), 0) as dmStock from njpt_chubeiliangjihua_kucun where qyzzjgdm=? and kdbm=? and cfbm=? ",param1.get("ccku"),param1.get("ccdian"),param1.get("cfbm"));
			double sumlhsldou=((double)sumlhslRec.get("lhsl"));
			double kucun=kucunRec.getBigDecimal("dmStock").doubleValue();
			if((lhsl*1000)>(kucun-(sumlhsldou*1000))){//添加轮换申请时，验证轮出数量不能大于库存（减去已提交的轮出申请）
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * 编辑时验证申请文号
		 * @param param1
		 * @return
		 */
		public static boolean validatesqwh1(HashMap<String, Object> param1) {
			// TODO Auto-generated method stub
			List<Record> sqwh=Db.find("select * from njpt_lunhuanshenqingb where sqwh=? and id<>?",param1.get("sqwh"),param1.get("id"));
			if(sqwh.size()>0){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * 编辑时验证 -轮出数量和库存数量
		 * @param param1
		 * @return
		 */
		public static boolean validatelhsl1(HashMap<String, Object> param1) {
			double lhsl= Double.parseDouble((String)param1.get("lhsl"));
			Calendar a=Calendar.getInstance();
			int year= a.get(Calendar.YEAR);
			Record sumlhslRec=Db.findFirst("select isnull(sum(convert(float,lhsl)),0) as lhsl from njpt_lunhuanshenqingb where status='0' and SUBSTRING(lcstatus, 2, 1)<>'0' and ccku=? and ccdian=? and cfbm=?  and id<>? ",param1.get("ccku"),param1.get("ccdian"),param1.get("cfbm"),param1.get("id"));
			Record kucunRec=Db.findFirst("SELECT ISNULL(sum(dmStock), 0) as dmStock from njpt_chubeiliangjihua_kucun where qyzzjgdm=? and kdbm=? and cfbm=? ",param1.get("ccku"),param1.get("ccdian"),param1.get("cfbm"));
			double sumlhsldou=((double)sumlhslRec.get("lhsl"));
			double kucun=kucunRec.getBigDecimal("dmStock").doubleValue();
			if((lhsl*1000)>(kucun-(sumlhsldou*1000))){//添加轮换申请时，验证轮出数量不能大于库存（减去已提交的轮出申请）
				return true;
			}else{
				return false;
			}
			
		}
		
		/**
		 * 轮入
		 * @param map
		 * @return
		 */
		@Before(Tx.class)
		public static boolean lunrubohui(HashMap<String, Object> map) {
			//更改轮入申请表
			String sqwh = map.get("sqwh").toString();
			
			//查找轮入申请
			String findsql = "select * from njpt_lunrushenqingb where sqwh = ?";
			Record record = Db.findFirst(findsql, sqwh);
			
			String sql = "update njpt_lunrushenqingb set status=2, lcstatus = lcstatus*10 where sqwh = ? ";
			int uplr = Db.update(sql,sqwh);
			
			String lhsl = record.getStr("lhsl");
			String qyzzjgdm = record.getStr("ccku");
			String kdbm = record.getStr("ccdian");
			String pz = record.getStr("pz");
			
			//更改轮入计划数量表
			String lsql = "update njpt_lunruhj set sumlunru = sumlunru - "+lhsl+" "
					+ "where qyzzjgdm = ? and kdbm = ? and pz = ? ";
			int uplrhj = Db.update(lsql,qyzzjgdm,kdbm,pz);
			if(uplr > 0 && uplrhj > 0){
				return true;
			}else{
				return false;
			}
			
		}
		public static boolean saveycjkq(HashMap<String, Object> param) {
			Record record=new Record();
			record.setColumns(param);
			int month=0;
			if("".equals(param.get("sj_month"))||null==param.get("sj_month")){
				
			}else{
				month+=Integer.valueOf((String)param.get("sj_month"));
			}
			if("".equals(param.get("zf_month"))||null==param.get("zf_month")){
				
			}else{
				month+=Integer.valueOf((String)param.get("zf_month"));
			}
			Record lhRecord=Db.findFirst("select * from njpt_lunhuanshenqingb where id=?",param.get("id"));
			String wclrsj=lhRecord.getStr("wclrsj");
			String[] qssj=wclrsj.split("-");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar a=Calendar.getInstance();
			a.set(Integer.valueOf(qssj[0]), (Integer.valueOf(qssj[1])-1), Integer.valueOf(qssj[2]));
			a.add(Calendar.MONTH, month);
			String jssj= sdf.format(a.getTime());
			if(month>0){
				record.set("wclrsj", jssj);
				record.set("iscjkq", 0);
			}
			boolean bool= Db.update("njpt_lunhuanshenqingb", record);
			return bool;
		}
		/*@Test
		public  void saveycjkq() {
			String sj_month="1";
			String zf_month="";
			
			int month=0;
			if("".equals(sj_month)||null==sj_month){
				
			}else{
				month+=Integer.valueOf(sj_month);
			}
			if("".equals(zf_month)||null==zf_month){
				
			}else{
				month+=Integer.valueOf(zf_month);
			}
			String wclrsj="2017-02-04";
			String[] qssj=wclrsj.split("-");
			System.out.println(Integer.valueOf(qssj[1]));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar a=Calendar.getInstance();
			a.set(Integer.valueOf(qssj[0]), (Integer.valueOf(qssj[1])-1), Integer.valueOf(qssj[2]));
			a.add(Calendar.MONTH, month);
			String jssj= sdf.format(a.getTime());
			System.out.println(jssj);
		}*/
		public static Record lcpwheji(HashMap<String, Object> param) {
			Param p = new Param();
			p.put("and a.id = ?", "id");
			p.put("and a.sqwh like ?", "sqwh","%%%s%%");
			p.put("and a.liuchenghao = ?", "liuchenghao");
			p.put("and a.iscjkq = ?", "status");
			p.put("and a.qiyeid = ?", "qiyeid");
			p.put("and a.qyzzjgdm=?", "qyzzjgdm");
			p.put("and a.ccku=?", "ccku");
			p.put("and a.ccdian=?", "ccdian");
			p.put("and a.pz like ? ", "pz","%s%%");
			p.put("and a.year = ?", "year");
			p.put("and a.pzsj >= ?", "qssj");
			p.put("and a.pzsj <= ?", "jssj");
			p.put("and h.diqu=?", "diqu");
			SqlAndParam s = SqlUtil.buildSql(p, param);
			Page<Record> page = Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select isnull(sum(CONVERT(float,a.lhsl)),0) as zonglhsl ",
					"from njpt_lunhuanshenqingb a  INNER JOIN tz_qiye c on a.ccku=c.qyzzjgdm INNER JOIN tz_kudian d on a.ccdian=d.kdbm and a.ccku=d.qyzzjgdm inner join tz_cangfang e on a.ccku=e.qyzzjgdm and a.ccdian=e.kdbm and a.cfbm=e.cfbm INNER JOIN tz_GrainType f on a.pz=f.vCode LEFT JOIN tz_GrainLevel g on a.grade=g.vCode INNER JOIN njpt_chubeiliangjihua_diqu h on SUBSTRING(a.pzsj, 1, 4)=h.niandu and a.qyzzjgdm=h.qyzzjgdm where a.status>0  " + s.getSql()+"  ",
					s.getParam().toArray(new Object[0]));
			Record record=new Record();
			if(page.getList().size()>0){
				record=page.getList().get(0);
			}
			return record;
		}
		public static Map<String, Object> validatedybl(HashMap<String, Object> map) {
			// TODO Auto-generated method stub
			HashMap<String, Object> ret=new HashMap<>();
			Calendar c=Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			if("on".equals((String)map.get("yxdybllc"))){
				List<Record> dbllsit= Db.find("select * from njpt_lunhuanshenqingb where SUBSTRING(sqsj,1,4)=? and qyzzjgdm=? and yxdybllc='on' ",year,map.get("qyzzjgdm"));
				if(dbllsit.size()>0){
					ret.put("ret", "轮换申请失败，今年已经使用过低于比例轮出机会了！");
				}else{
					
				}
			}else{
				
			}
			return null;
		}
		
		
		
		
		
}