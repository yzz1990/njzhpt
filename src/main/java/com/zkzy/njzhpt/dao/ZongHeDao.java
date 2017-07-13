package com.zkzy.njzhpt.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ZongHeDao {

	
	
	/**
	 * 仓房利用率
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findcflyl(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and c.ID = ?", "ID");
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  sum(CAST(c.shjcr as FLOAT)) as zcr ",
				"from tz_qiye a "
				+ "LEFT join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+ "LEFT join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
				+ "where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
		
	}

public static Page<Record> findzycr(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and c.ID = ?", "ID");
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select sum(CAST(c.sjcr as FLOAT))/sum(CAST(c.shjcr as FLOAT)) as lyl, sum(CAST(c.shjcr as FLOAT)) as zcr ",
				"from tz_qiye a "
				+ "LEFT join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+ "LEFT join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
				+ "where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'  and b.isksh='1' and  c.cfztbh='01' " 
				+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	

	/**
	 * 仓房利用率
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record>  findcfInfo(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and c.ID = ?", "ID");
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");	
		p.put("and c.cflxbh = ?", "cflxbh");	
		p.put("and c.jxtf = ?", "jxtf");	
		p.put("and c.jsjcw = ?", "jsjcw");	
		p.put("and c.hlxz = ?", "hlxz");	
		p.put("and c.dwcl = ?", "dwcl");	
		p.put("and c.qtcl = ?", "qtcl");	
		p.put("and c.qtjs = ?", "qtjs");	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select c.* ",
				"from tz_qiye a LEFT "
				+ "join tz_kudian b on a.qyzzjgdm=b.qyzzjgdm "
				+ "LEFT join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
				+ "where c.sjcr is not null and c.shjcr is not null " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	
	/**
	 * 获取地区
	 */
	public static  List<Record> findQuyu(HashMap<String, Object> param) {
		return Db.find("select a.*,b.xzqhdm as xzqhdm from fw_area a,njpt_diqu b where  a.id=b.areaid and parentid!='root' ORDER BY a.orderno");
		
	}

	/**
	 * 获取仓房
	 * @param param
	 * @throws Exception 
	 */
	public static Page<Record> finCf(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		p.put("and xian = ?", "xian");
		p.put("and xzqhdm = ?", "xzqhdm");
		p.put("and cflxbh = ?", "cflxbh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from tz_cangfang where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 * 获取烘干能力值
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Record finHgnlV(HashMap<String, Object> param) throws Exception {
		Record record=Db.findFirst("select  SUM(cast(hgnl as FLOAT)) as hgnl  from tz_kudian  where xian=?",param.get("xian"));
		return record;
	}
	

	/**
	 * 获取仓房类型
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> finCflx(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and BH = ?", "code");
		p.put("and MC = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from dm_cflx where 1=1 " + s.getSql()+" order by TAB",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	
	/**
	 * 保粮技术
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findBljs(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from dm_bljs where 1=1 " + s.getSql()+" order by code",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 库存占比
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findcrk(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and a.vGrainSubTypeCode = ?", "vGrainSubTypeCode");
		p.put("and a.vGrainPropertyCode = ?", "vGrainPropertyCode");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");	
		p.put("and b.xzqhdm = ?", "xzqhdm");
		p.put("and b.xian = ?", "xian");
		p.put("and c.vCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*,c.vName as vName,c.vCode as vCode  ",
				"from  kc_CurrentStock a,tz_qiye b,tz_GrainType c where a.qyzzjgdm=b.qyzzjgdm  and a.vGrainSubTypeCode=c.vCode " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 库存总计
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findzcrk(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and cs.id = ?", "id");
		p.put("and cs.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and cs.vGrainSubTypeCode like ?", "vCode","%s%%");
		p.put("and cs.vGrainPropertyCode = ?", "vGrainPropertyCode");
		p.put("and AS.kdbm = ?", "kdbm");
		p.put("and qy.xzqhdm = ?", "xzqhdm");
		p.put("and qy.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and qy.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"SELECT sum(CAST(cs.dmStock  as FLOAT)/1000) as dmStock ",
						"FROM tz_qiye AS qy INNER JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm "
						+ "INNER JOIN tz_cangfang AS cf ON kd.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
						+ "INNER JOIN kc_CurrentStock AS cs ON "
						+ "cf.qyzzjgdm = cs.qyzzjgdm and cf.kdbm = cs.kdbm and cf.cfbm = cs.vWareHouseCode "
						+ "WHERE kd.kdlxbh <> '06' and kd.kdlxbh <> '05' and kd.kdlxbh <> '04' and  cf.cfztbh='01' and kd.isksh='1' " + s.getSql(),
						s.getParam().toArray(new Object[0]));
		
		
		return page;
	}
	
	
	/**
	 * 库存总计
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findzcrk1(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		String sql="";
		if(param.get("vCode").equals("888")){
			sql+=" and  (d.vGrainSubTypeCode not like '111%' or d.vGrainSubTypeCode not like '113%' or d.vGrainSubTypeCode not like '112%' or d.vGrainSubTypeCode not in ('1411001')) ";
		}
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and d.vGrainSubTypeCode like ?", "vCode","%s%%");
		p.put("and d.vGrainPropertyCode = ?", "vGrainPropertyCode");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
					Integer.valueOf(String.valueOf(param.get("page"))),
					Integer.valueOf(String.valueOf(param.get("rows"))),
					"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
					"from tz_qiye a   " 
					+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
					+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
					+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
					+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' " 
					+ s.getSql()+sql,
					s.getParam().toArray(new Object[0]));
		
		
		return page;
	}


	/**
	 * 库存总计
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findzcrk2(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		String sql="";
		if(param.get("vGrainPropertyCode").equals("a")){
			sql+=" and (d.vGrainPropertyCode in ('1') or d.vGrainPropertyCode like '11%') ";
		}else if(param.get("vGrainPropertyCode").equals("b")){
			sql+=" and d.vGrainPropertyCode in ('121') ";
		}else if(param.get("vGrainPropertyCode").equals("c")){
			sql+=" and d.vGrainPropertyCode in ('122') ";
		}else if(param.get("vGrainPropertyCode").equals("d")){
			sql+=" and d.vGrainPropertyCode in ('123') ";		
		}else if(param.get("vGrainPropertyCode").equals("e")){
			sql+=" and d.vGrainPropertyCode in ('32','33','34') ";		
		}else if(param.get("vGrainPropertyCode").equals("f")){
			sql+=" and d.vGrainPropertyCode like ('2%') ";		
		}else if(param.get("vGrainPropertyCode").equals("g")){
			sql+=" and d.vGrainPropertyCode in ('31') ";		
		}else if(param.get("vGrainPropertyCode").equals("h")){
			sql+=" and d.vGrainPropertyCode in ('129','99') ";		
		}
		
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						"from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));
		return page;
	}


	public static Page<Record> findzkcpz(HashMap<String, Object> param) throws Exception {
		String sql="";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		if(!param.get("vCode").equals("888")){
			p.put("and d.vGrainSubTypeCode like ?", "vCode","%s%%");
		}else{
			sql+=" and  (d.vGrainSubTypeCode not like '111%' and d.vGrainSubTypeCode not like '113%' and d.vGrainSubTypeCode not like '112%' and d.vGrainSubTypeCode not in ('1411001')) ";
		}
		if(param.get("vGrainPropertyCode").equals("a")){
			sql+=" and (d.vGrainPropertyCode in ('1') or d.vGrainPropertyCode like '11%') ";
		}else if(param.get("vGrainPropertyCode").equals("b")){
			sql+=" and d.vGrainPropertyCode in ('121') ";
		}else if(param.get("vGrainPropertyCode").equals("c")){
			sql+=" and d.vGrainPropertyCode in ('122') ";
		}else if(param.get("vGrainPropertyCode").equals("d")){
			sql+=" and d.vGrainPropertyCode in ('123') ";		
		}else if(param.get("vGrainPropertyCode").equals("e")){
			sql+=" and d.vGrainPropertyCode in ('32','33','34') ";		
		}else if(param.get("vGrainPropertyCode").equals("f")){
			sql+=" and d.vGrainPropertyCode like ('2%') ";		
		}else if(param.get("vGrainPropertyCode").equals("g")){
			sql+=" and d.vGrainPropertyCode in ('31') ";		
		}else if(param.get("vGrainPropertyCode").equals("h")){
			sql+=" and d.vGrainPropertyCode in ('129','99') ";		
		}
		
		
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	
	public static Page<Record> findzkcpz1(HashMap<String, Object> param) throws Exception {
		String sql="";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		if(!param.get("vCode").equals("888")){
			p.put("and d.vGrainSubTypeCode like ?", "vCode","%s%%");
		}else{
			sql+=" and  (d.vGrainSubTypeCode not like '111%' and d.vGrainSubTypeCode not like '113%' and d.vGrainSubTypeCode not like '112%' and d.vGrainSubTypeCode not in ('1411001')) ";
		}
		if(!param.get("grade").equals("03")){
			p.put("and d.grade = ?", "grade");//等级编码
			sql+=" and d.grade is not null ";
		}else{
			sql+=" and (d.grade is NULL or d.grade = '' or d.grade = '03') ";
		}
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	public static Page<Record> findzkcpz2(HashMap<String, Object> param) throws Exception {
		String sql="";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		if(!param.get("vCode").equals("888")){
			p.put("and d.vGrainSubTypeCode like ?", "vCode","%s%%");
		}else{
			sql+=" and  (d.vGrainSubTypeCode not like '111%' and d.vGrainSubTypeCode not like '113%' and d.vGrainSubTypeCode not like '112%' and d.vGrainSubTypeCode not in ('1411001')) ";
		}
		if(!param.get("year").equals("其他")){
			p.put("and d.[year] = ?", "year");//
			sql+=" and d.[year] is not NULL  ";
		}else{
			sql+=" and (d.[year] is NULL or d.[year] = '') ";
		}
		
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' " 
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	public static Page<Record> findzkcpz3(HashMap<String, Object> param) throws Exception {
		String sql="";
		if(param.get("vGrainPropertyCode").equals("a")){
			sql+=" and (d.vGrainPropertyCode in ('1') or d.vGrainPropertyCode like '11%') ";
		}else if(param.get("vGrainPropertyCode").equals("b")){
			sql+=" and d.vGrainPropertyCode in ('121') ";
		}else if(param.get("vGrainPropertyCode").equals("c")){
			sql+=" and d.vGrainPropertyCode in ('122') ";
		}else if(param.get("vGrainPropertyCode").equals("d")){
			sql+=" and d.vGrainPropertyCode in ('123') ";		
		}else if(param.get("vGrainPropertyCode").equals("e")){
			sql+=" and d.vGrainPropertyCode in ('32','33','34') ";		
		}else if(param.get("vGrainPropertyCode").equals("f")){
			sql+=" and d.vGrainPropertyCode like ('2%') ";		
		}else if(param.get("vGrainPropertyCode").equals("g")){
			sql+=" and d.vGrainPropertyCode in ('31') ";		
		}else if(param.get("vGrainPropertyCode").equals("h")){
			sql+=" and d.vGrainPropertyCode in ('129','99') ";		
		}
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		
		if(!param.get("grade").equals("03")){
			p.put("and d.grade = ?", "grade");//等级编码
			sql+=" and d.grade is not null ";
		}else{
			sql+=" and (d.grade is NULL or d.grade = '' or d.grade = '03') ";
		}
		
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' " 
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	
	public static Page<Record> findzkcpz4(HashMap<String, Object> param) throws Exception {
		String sql="";
		if(param.get("vGrainPropertyCode").equals("a")){
			sql+=" and (d.vGrainPropertyCode in ('1') or d.vGrainPropertyCode like '11%') ";
		}else if(param.get("vGrainPropertyCode").equals("b")){
			sql+=" and d.vGrainPropertyCode in ('121') ";
		}else if(param.get("vGrainPropertyCode").equals("c")){
			sql+=" and d.vGrainPropertyCode in ('122') ";
		}else if(param.get("vGrainPropertyCode").equals("d")){
			sql+=" and d.vGrainPropertyCode in ('123') ";		
		}else if(param.get("vGrainPropertyCode").equals("e")){
			sql+=" and d.vGrainPropertyCode in ('32','33','34') ";		
		}else if(param.get("vGrainPropertyCode").equals("f")){
			sql+=" and d.vGrainPropertyCode like ('2%') ";		
		}else if(param.get("vGrainPropertyCode").equals("g")){
			sql+=" and d.vGrainPropertyCode in ('31') ";		
		}else if(param.get("vGrainPropertyCode").equals("h")){
			sql+=" and d.vGrainPropertyCode in ('129','99') ";		
		}
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		
		if(!param.get("year").equals("其他")){
			p.put("and d.[year] = ?", "year");//
			sql+=" and d.[year] is not NULL  ";
		}else{
			sql+=" and (d.[year] is NULL or d.[year] = '') ";
		}
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "  
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	public static Page<Record> findzkcpz5(HashMap<String, Object> param) throws Exception {
		String sql="";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		if(!param.get("grade").equals("03")){
			p.put("and d.grade = ?", "grade");//等级编码
			sql+=" and d.grade is not null ";
		}else{
			sql+=" and (d.grade is NULL or d.grade = '' or d.grade = '03') ";
		}
		
		if(!param.get("year").equals("其他")){
			p.put("and d.[year] = ?", "year");//
			sql+=" and d.[year] is not NULL  ";
		}else{
			sql+=" and (d.[year] is NULL or d.[year] = '') ";
		}
		
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page= Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(d.dmStock  as FLOAT)/1000) as dmStock  ",
						" from tz_qiye a   " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
						+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' " 
						+ s.getSql()+sql,
						s.getParam().toArray(new Object[0]));	
		return page;
	}
	

	/**
	 * 分区品种统计库存
	 * @throws Exception
	 */
	public static List<Record>  findlspz() {
		return	Db.find("select c.vName as name,c.vCode as code from  kc_CurrentStock a,tz_qiye b,tz_GrainType c where a.qyzzjgdm=b.qyzzjgdm  and a.vGrainSubTypeCode=c.vCode  GROUP BY c.vName ,c.vCode ");	
	}
	
	/**
	 * 分区品种统计库存
	 * @throws Exception
	 */
	
	
	public static List<Record>  findlsxz() {
		return	Db.find("select c.vGrainProperties as name,c.vCode as code  from  kc_CurrentStock a,tz_qiye b,tz_GrainProperties c where a.qyzzjgdm=b.qyzzjgdm  and a.vGrainPropertyCode =c.vCode  GROUP BY c.vGrainProperties ,c.vCode ORDER BY c.vCode ");	
	}
	
	/**
	 * 分年度
	 * @return
	 */
	
	public static List<Record>  findNianDu() {
		return	Db.use("grainplat").find("select * from onstorage a where LENGTH(a.`year`)=4 GROUP BY a.`year` ORDER BY a.`year` ");	
	}
	

	/**
	 * 分等级粮食库存统计
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findlsdj(HashMap<String, Object> param) throws Exception {

		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and vLevelName = ?", "vLevelName");
		p.put("and vCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from  tz_GrainLevel  " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	public static Page<Record> finddjck(HashMap<String, Object> queryParam) throws Exception {
   
		Param p = new Param();
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and d.vGrainSubTypeCode = ?", "vGrainSubTypeCode");
		p.put("and d.grade = ?", "vCode");//等级编码
		p.put("and a.xian = ?", "xian");//地区
		p.put("and d.[year] = ?", "year");//地区
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		
		Page<Record> page  = Db.paginate(
					Integer.valueOf(String.valueOf(queryParam.get("page"))),
					Integer.valueOf(String.valueOf(queryParam.get("rows"))),
					"select  sum(CAST(d.dmStock  as FLOAT))/1000 as dmStock  ",
					"from tz_qiye a   " 
					+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
					+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
					+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
					+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
					+ " and d.grade is not null "
					+ s.getSql(),
					s.getParam().toArray(new Object[0]));
		
		
		return page;
	}

	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	public static Page<Record> finddjck1(HashMap<String, Object> queryParam) throws Exception {
    String sql="";
    Param p = new Param();
		if(!queryParam.get("vCode").equals("03")){
			p.put("and d.grade = ?", "vCode");//等级编码
			sql=" and (d.grade is not null and  d.grade != '') ";
		}else{
			sql=" and (d.grade is NULL or d.grade = '' or d.grade = '03') ";
		}
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and d.vGrainSubTypeCode = ?", "vGrainSubTypeCode");
		p.put("and a.xian = ?", "xian");//地区
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		
		Page<Record> page  = Db.paginate(
					Integer.valueOf(String.valueOf(queryParam.get("page"))),
					Integer.valueOf(String.valueOf(queryParam.get("rows"))),
					"select  sum(CAST(d.dmStock  as FLOAT))/1000 as dmStock  ",
					"from tz_qiye a   " 
					+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
					+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
					+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
					+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
					+ s.getSql()+sql,
					s.getParam().toArray(new Object[0]));
		
		
		return page;
	}
	
	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	public static Page<Record> findnxck(HashMap<String, Object> queryParam) throws Exception {
   
		Param p = new Param();
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and d.vGrainSubTypeCode = ?", "vGrainSubTypeCode");
		p.put("and d.grade = ?", "vCode");//等级编码
		p.put("and a.xian = ?", "xian");//地区
		p.put("and d.[year] = ?", "year");//地区
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		
		Page<Record> page  = Db.paginate(
					Integer.valueOf(String.valueOf(queryParam.get("page"))),
					Integer.valueOf(String.valueOf(queryParam.get("rows"))),
					"select  sum(CAST(d.dmStock  as FLOAT))/1000 as dmStock  ",
					"from tz_qiye a   " 
					+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
					+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
					+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
					+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
					+ s.getSql(),
					s.getParam().toArray(new Object[0]));
		
		
		return page;
	}
	
	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	public static Page<Record> findnxck1(HashMap<String, Object> queryParam) throws Exception {
    String sql="";
    Param p = new Param();
		if(!queryParam.get("year").equals("其他")){
			p.put("and d.[year] = ?", "year");//
			sql=" and d.[year] is not NULL  ";
		}else{
			sql=" and (d.[year] is NULL or d.[year] = '') ";
		}
		p.put("and d.vWareHouseCode = ?", "vWareHouseCode");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and d.vGrainSubTypeCode = ?", "vGrainSubTypeCode");
		p.put("and a.xian = ?", "xian");//地区
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		
		Page<Record> page  = Db.paginate(
					Integer.valueOf(String.valueOf(queryParam.get("page"))),
					Integer.valueOf(String.valueOf(queryParam.get("rows"))),
					"select  sum(CAST(d.dmStock  as FLOAT))/1000 as dmStock  ",
					"from tz_qiye a   " 
					+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
					+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
					+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
					+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
					+ s.getSql()+sql,
					s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	
	


	/**
	 * 获取分区域状态仓容
	 * @param param1
	 * @return
	 */
	public static Record findfqcfzt(HashMap<String, Object> param1) {
		String cfztmc = (String) param1.get("cfztmc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cfztmc +" ";
		   sql += "from tz_qiye a ";
		   sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
		   sql += "inner JOIN  tz_cangfang c  on a.qyzzjgdm=c.qyzzjgdm ";
		   sql += "and b.qyzzjgdm = c.qyzzjgdm and b.kdbm=c.kdbm ";
		   sql += "where a.xzqhdm=? and  c.cfztmc=? and b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'";
		   sql += "GROUP BY a.xzqhdm  ";
		   
		/*String sql = "SELECT SUM(cs.dmStock) as " + cfztmc +" ";
			   sql += "FROM tz_cangfang AS cf ";
			   sql += "LEFT JOIN tz_qiye AS qy ON cf.qyzzjgdm = qy.qyzzjgdm ";
			   sql += "LEFT JOIN kc_CurrentStock cs ON cf.qyzzjgdm = cs.qyzzjgdm ";
			   sql += "AND cf.kdbm = cs.kdbm AND cf.cfbm = cs.vWareHouseCode ";
			   sql += "WHERE qy.xzqhdm = ? and cf.cfztmc = ? ";
			   sql += "GROUP BY qy.xzqhdm ";*/
		String xzqhdm = param1.get("xzqhdm").toString();
		String cfzt = param1.get("cfzt").toString();
		Record record = Db.findFirst(sql,xzqhdm,cfzt);
		//Record record = Db.findFirst("select SUM(CAST(c.shjcr as FLOAT)) as "+cfztmc+" from tz_qiye a inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm inner JOIN  tz_cangfang c on a.qyzzjgdm=c.qyzzjgdm  and b.qyzzjgdm = c.qyzzjgdm and b.kdbm=c.kdbm where b.xzqhdm="+(String)param1.get("xzqhdm")+"and  c.cfztmc="+(String)param1.get("cfzt")+" and b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'GROUP BY a.xzqhdm  ");
		//Record record = Db.findFirst("select SUM(CAST(c.shjcr as FLOAT)) as " + cfztmc + " from tz_qiye a inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and c.qyzzjgdm=a.qyzzjgdm and c.kdbm=b.kdbm where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and a.xzqhdm="+(String) param1.get("xzqhdm")+" and  c.cfztmc="+(String) param1.get("cfzt")+" GROUP BY a.xzqhdm " );

		return record;
		
		
		
		
	}
	
	/**
	 * 获取分区域状态仓容
	 * @param param1
	 * @return
	 */
	public static Record findfqcfztqt(HashMap<String, Object> param1) {
		String cfztmc = (String) param1.get("cfztmc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cfztmc +" ";
		   sql += "from tz_qiye a ";
		   sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
		   sql += "inner JOIN  tz_cangfang c  on a.qyzzjgdm=c.qyzzjgdm ";
		   sql += "and b.qyzzjgdm = c.qyzzjgdm and b.kdbm=c.kdbm ";
		   sql += "where b.xzqhdm=? and  c.cfztmc not in ('完好可用','需大修','待报废','待拆除','死角仓') and b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'";
		   sql += "GROUP BY a.xzqhdm  ";
		   
		/*String sql = "SELECT SUM(cs.dmStock) as " + cfztmc +" ";
			   sql += "FROM tz_cangfang AS cf ";
			   sql += "LEFT JOIN tz_qiye AS qy ON cf.qyzzjgdm = qy.qyzzjgdm ";
			   sql += "LEFT JOIN kc_CurrentStock cs ON cf.qyzzjgdm = cs.qyzzjgdm ";
			   sql += "AND cf.kdbm = cs.kdbm AND cf.cfbm = cs.vWareHouseCode ";
			   sql += "WHERE qy.xzqhdm = ? and cf.cfztmc = ? ";
			   sql += "GROUP BY qy.xzqhdm ";*/
		String xzqhdm = (String) param1.get("xzqhdm");
		String cfzt = (String) param1.get("cfzt");
		Record record = Db.findFirst(sql,xzqhdm);
		return record;
	}


	/**
	 * 获得分区域使用仓容情况
	 * @param param1
	 * @return
	 */
	public static Record findfqcfsy(HashMap<String, Object> param1) {
		String cfsymc = (String) param1.get("cfsymc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cfsymc +" ";
		   sql += "from tz_qiye a ";
		   sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
		   sql += "inner JOIN  tz_cangfang c  on a.qyzzjgdm=c.qyzzjgdm ";
		   sql += "and b.qyzzjgdm=c.qyzzjgdm and c.kdbm=b.kdbm ";
		   sql += "where b.xzqhdm=? and  c.cfsyqkmc=? and b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' ";
		   sql += "GROUP BY a.xzqhdm  ";
/*
		String sql = "select SUM(CAST(a.shjcr as FLOAT)) as" + cfsymc +" ";
			   sql += "FROM tz_cangfang AS cf ";
			   sql += "LEFT JOIN tz_qiye AS qy ON cf.qyzzjgdm = qy.qyzzjgdm ";
			   sql += "LEFT JOIN kc_CurrentStock cs ON cf.qyzzjgdm = cs.qyzzjgdm ";
			   sql += "AND cf.kdbm = cs.kdbm AND cf.cfbm = cs.vWareHouseCode ";
			   sql += "WHERE qy.xzqhdm = ? and cf.cfsyqkmc = ? ";
			   sql += "GROUP BY qy.xzqhdm ";*/
		String xzqhdm = (String) param1.get("xzqhdm");
		String cfsy = (String) param1.get("cfsy");
		Record record = Db.findFirst(sql,xzqhdm,cfsy);
		return record;
	}


	/**
	 * 获取分年代使用情况
	 * @param param1
	 * @return
	 */
	public static Record findfqcfnd(HashMap<String, Object> param1) {
		String cfndmc = (String) param1.get("cfndmc");
		String sql = "SELECT SUM(cast(c.shjcr as float)) as " + cfndmc +" ";
			   sql += "FROM tz_qiye AS a ";
			   sql += "inner JOIN tz_kudian AS b ON a.qyzzjgdm = b.qyzzjgdm ";
			   sql += "inner JOIN tz_cangfang AS c ON a.qyzzjgdm = c.qyzzjgdm ";
			   sql += "and b.qyzzjgdm = c.qyzzjgdm and b.kdbm = c.kdbm ";
			   sql += "WHERE a.xzqhdm = ? and c.jsnd >= ? and c.jsnd <= ? ";
			   sql += "GROUP BY a.xzqhdm ";
		String xzqhdm = (String) param1.get("xzqhdm");
		int cfnd = Integer.parseInt(param1.get("cfnd").toString());
		int ndks = 0;
		int ndjs = 0;
		if(cfnd == 1999){
			ndks = 0;
			ndjs = 1999;
		}else if(cfnd == 2000){
			ndks = 2000;
			ndjs = 2013;
		}else{
			ndks = 2014;
			ndjs = Calendar.getInstance().get(Calendar.YEAR);
		}
		Record record = Db.findFirst(sql,xzqhdm,ndks,ndjs);
		return record;
	}


	/**
	 * 获取仓房类型数据
	 * @param param1
	 * @return
	 */
	public static Record findcflxzcrzb(HashMap<String, Object> param1) {
		String cflxmc = (String) param1.get("cflxmc");
		String sql = "SELECT SUM(cs.dmStock) as " + cflxmc +" ";
			   sql += "FROM tz_cangfang AS cf ";
			   sql += "LEFT JOIN tz_qiye AS qy ON cf.qyzzjgdm = qy.qyzzjgdm ";
			   sql += "LEFT JOIN kc_CurrentStock cs ON cf.qyzzjgdm = cs.qyzzjgdm ";
			   sql += "AND cf.kdbm = cs.kdbm AND cf.cfbm = cs.vWareHouseCode ";
			   sql += "WHERE qy.xzqhdm = ? and cf.cflxmc = ? ";
			   sql += "GROUP BY qy.xzqhdm ";
		String xzqhdm = (String) param1.get("xzqhdm");
		String cflx = (String) param1.get("cflx");
		Record record = Db.findFirst(sql,xzqhdm,cflx);
		return record;
	}

	/**
	 * 根据地区获取不同仓房类型的仓容
	 * @author yzz
	 * @timr  2017-04-08 11:21
	 * @param param1
	 * @remark  上面的findcflxzcrzb方法获取的不同仓房类型的库存，并非仓容，导致数据不准确，故重写次方法，以作修改。
	 * @return
	 */
	public static Record findcfcr(HashMap<String, Object> param1) {
		String cflxmc = (String) param1.get("cflxmc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cflxmc +" ";
			   sql += "from tz_qiye a ";
			   sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
			   sql += "INNER JOIN tz_cangfang c on a.qyzzjgdm=c.qyzzjgdm and c.qyzzjgdm=b.qyzzjgdm and c.kdbm=b.kdbm";
			   sql += " where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and a.xzqhdm=? and  c.cflxmc in ? ";
			   sql += "GROUP BY b.xzqhdm  ";
		String xzqhdm = (String) param1.get("xzqhdm");
		String cflx = (String) param1.get("cflx");
		//Record record = Db.findFirst(sql,xzqhdm,cflx);
		//Record record = Db.findFirst(sql, xzqhdm,cflx);
		
		Record record = Db.findFirst("select SUM(CAST(c.shjcr as FLOAT)) as " + cflxmc + " from tz_qiye a inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and c.qyzzjgdm=a.qyzzjgdm and c.kdbm=b.kdbm where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and a.xzqhdm="+(String) param1.get("xzqhdm")+" and  c.cflxmc in "+(String) param1.get("cflx")+" GROUP BY a.xzqhdm " );
		return record;
	}
	
	public static Record findcfcrqt(HashMap<String, Object> param1) {
		String cflxmc = (String) param1.get("cflxmc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cflxmc +" ";
			   sql += "from tz_qiye a ";
			   sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
			   sql += "INNER JOIN tz_cangfang c on a.qyzzjgdm=c.qyzzjgdm and c.qyzzjgdm=b.qyzzjgdm and c.kdbm=b.kdbm";
			   sql += " where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and a.xzqhdm=? and  c.cflxmc in ? ";
			   sql += "GROUP BY b.xzqhdm  ";
		String xzqhdm = (String) param1.get("xzqhdm");
		String cflx = (String) param1.get("cflx");
		//Record record = Db.findFirst(sql,xzqhdm,cflx);
		//Record record = Db.findFirst(sql, xzqhdm,cflx);
		
		Record record = Db.findFirst("select SUM(CAST(c.shjcr as FLOAT)) as " + cflxmc + " "
				+ "from tz_qiye a inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm "
				+ "INNER JOIN tz_cangfang c on b.qyzzjgdm=c.qyzzjgdm and c.qyzzjgdm=a.qyzzjgdm "
				+ "and c.kdbm=b.kdbm where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' "
				+ "and a.xzqhdm="+(String) param1.get("xzqhdm")+" and  "
						+ "c.cflxmc not in ('平房仓','浅圆仓','立筒仓','楼房仓','储粮罩棚','仓间罩棚','铁路罩棚','其他简易仓') GROUP BY a.xzqhdm " );
		return record;
	}
	
	/**
	 * 获取仓房储粮技术
	 * @param param1
	 * @return
	 */
	public static Record cfcljs(HashMap<String, Object> param1) {
		String cfclmc = (String) param1.get("cfclmc");
		String sql = "select SUM(CAST(c.shjcr as FLOAT)) as " + cfclmc +" ";
				sql += "from tz_qiye a ";
				sql += "inner JOIN  tz_kudian b  on a.qyzzjgdm=b.qyzzjgdm ";
				sql += "inner JOIN  tz_cangfang c  on a.qyzzjgdm=c.qyzzjgdm ";
				sql += "and b.qyzzjgdm = c.qyzzjgdm and b.kdbm = c.kdbm ";
			   sql += "WHERE a.xzqhdm = ? and c."+cfclmc+" = 1 ";
			   sql += "and b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' ";
			   sql += "GROUP BY a.xzqhdm  ";
		String xzqhdm = (String) param1.get("xzqhdm");
		Record record = Db.findFirst(sql,xzqhdm);
		return record;
	}


	/**
	 * 统计烘干能力
	 * @param param1
	 * @return
	 */
	public static Record findhgnl(HashMap<String, Object> param1) {
		String xzqhdm = (String) param1.get("xzqhdm");
		String sql = "SELECT SUM(convert(float,hgnl)) as hgnl from tz_kudian where xzqhdm = ? ";
		Record record = Db.findFirst(sql,xzqhdm);
		return record;
	}


	public static Record findzzmj(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String sql ="";
		Record record=new Record();
		if(param.containsKey("niandu")) {
			sql="select * from njpt_diquniandu "
					+ "where xzqhdm = ? and niandu=? order by niandu desc ";
			record = Db.findFirst(sql, param.get("xzqhdm"), param.get("niandu"));
		}else{
			sql="select * from njpt_diquniandu "
					+ "where xzqhdm = ? order by niandu desc ";
			record = Db.findFirst(sql, param.get("xzqhdm"));
		}
		return record;
	}


	/**
	 * 寻找年度
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static List<Record> findyear(HashMap<String, Object> param) throws Exception {
		String sql = "select distinct(niandu) as niandu from njpt_diquniandu  order by niandu desc";
		List<Record> list = Db.find(sql);
		return list;
	}


	/**
	 * 查找各区总库存
	 * @param param
	 * @return
	 */
	public static List<Record> findzkc(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String sql = "SELECT SUM (dmStock)/1000 AS SUM, "
				+ "area.orderno as orderno, MIN (area.name) as xian FROM "
				+ "tz_qiye as qy left join tz_kudian AS kd on qy.qyzzjgdm = kd.qyzzjgdm "
				+ "LEFT JOIN tz_cangfang AS cf on kd.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ "LEFT JOIN fw_area AS area ON qy.xian = area.name "
				+ "LEFT JOIN kc_CurrentStock as cs ON cs.qyzzjgdm = cf.qyzzjgdm "
				+ "and cs.kdbm = cf.kdbm AND cf.cfbm = cs.vWareHouseCode "
				+ "WHERE kd.isksh = '1' and kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' "
				+ "and cf.cfztbh = '01' and area.name = ? GROUP BY area.orderno ORDER BY area.orderno ";
		return Db.find(sql,param.get("xian"));
	}

	/**
	 * 查找总仓容
	 * @param param
	 * @return
	 */
	public static List<Record> findzcr(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String sql = "SELECT MIN (area.name) AS xian , SUM (CAST(shjcr AS FLOAT)) AS shjcr "
				+ "FROM fw_area as area left join "
				+ "tz_qiye AS qy on  area.name = qy.xian "
				+ "LEFT JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm  "
				+ "LEFT JOIN tz_cangfang as cf on cf.qyzzjgdm = kd.qyzzjgdm and cf.kdbm = kd.kdbm "
				+ "WHERE kd.isksh = '1' and kd.kdlxbh<>'06' "
				+ "and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and cf.cfztbh = '01' and area.name=? "
				+ "GROUP BY area.orderno ORDER BY area.orderno";
		return Db.find(sql,param.get("xian"));
	}


	public static List<Record> findqt(HashMap<String, Object> param) {
		String sql = "SELECT SUM (CAST(shjcr AS FLOAT))  AS shjcr, "
				+ "MIN (area.name) AS xian FROM fw_area as area left join tz_qiye AS qy "
				+ "on area.name = qy.xian "
				+ "LEFT JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm  "
				+ "LEFT JOIN tz_cangfang as cf on qy.qyzzjgdm = cf.qyzzjgdm "
				+ "and kd.kdbm = cf.kdbm "
				+ "WHERE kd.isksh = '1' and kd.kdlxbh<>'06' "
				+ "and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and cf.cfztmc <> '完好可用' and area.name = ?"
				+ "GROUP BY area.orderno ORDER BY area.orderno ";
		return Db.find(sql,param.get("xian"));
	}

}