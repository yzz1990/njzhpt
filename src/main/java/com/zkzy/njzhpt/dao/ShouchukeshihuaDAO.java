package com.zkzy.njzhpt.dao;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ShouchukeshihuaDAO {
	
	/**
	 *获取夏粮五日报表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryxialiangshougouwuri(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and qishu = ?", "qishu");
		p.put("and dizhi = ?", "dizhi");
		p.put("and qiye = ?", "qiye");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select *",
				"from njpt_xialiangshougouwuribb where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 *获取秋粮五日报表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqiuliangshougouwuri(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and nianfen = ?", "year");
		p.put("and qishu = ?", "qishu");
		p.put("and quming = ?", "dizhi");
		p.put("and qiye = ?", "qiye");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select *",
				"from njpt_qiuliangshougouwuribb where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *获取五日分期
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querywuri(HashMap<String, Object> map) throws Exception {
	
		
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and qishu = ?", "qishu");
		p.put("and jidu = ?", "jidu");
		p.put("and convert(date,fenqiendtime) <= ?", "today");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select *",
				"from njpt_fenqishangbao where 1=1" + s.getSql() + "order by fenqiendtime desc",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 *获取秋粮五日分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqlwuri(HashMap<String, Object> map) throws Exception {
	
		
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and qishu = ?", "qishu");
		p.put("and jidu = ?", "jidu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select *",
				"from njpt_fenqishangbao where 1=1" + s.getSql() + "order by qishu desc",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 *获取秋粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqiuliangyuce(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.niandu = ?", "niandu");
		p.put("and a.id = ?", "id");
		p.put("and a.diqu = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.*",
				"from njpt_qiuliangyuce a LEFT JOIN fw_area b ON a.diqu=b.name where 1=1" + s.getSql() +" order by a.niandu desc, b.orderno ASC",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 *获取夏粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryxialiangyuce(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.niandu = ?", "niandu");
		p.put("and a.id = ?", "id");
		p.put("and a.diqu = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.*",
				"from njpt_xialiangyuce a LEFT JOIN fw_area b ON a.diqu=b.name where 1=1" + s.getSql() + "order by a.niandu desc, b.orderno ASC",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 *获取库点实时入库情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryshishi(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and k.xzqhdm = ?", "xzqhdm");
		p.put("and k.xian = ?", "xian");
		p.put("and k.kdmc = ?", "kdmc");
		p.put("and q.qymc = ?", "qymc");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?","kdbm");
		p.put("and k.isksh = ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(map.get("page"))),
			Integer.valueOf(String.valueOf(map.get("rows"))),
			"select k.*,q.qymc as qymc",
			"from tz_qiye as q left join tz_kudian as k on k.qyzzjgdm = q.qyzzjgdm "
			+ "left join njpt_diqu as dq on k.xzqhdm = dq.xzqhdm "
			+ "left join fw_area as area on dq.areaid = area.id "
			+ "where 1 = 1 and k.kdlxbh<>'06' " + s.getSql() +" order by area.orderno asc, k.ID desc ",
			s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 * 查询库存汇总
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querykchz(HashMap<String, Object> map) throws Exception {	
		String isksh = "0";
		if(map.get("isksh") != null){
			isksh = map.get("isksh").toString();
		}
		if("1".equals(isksh) && !StrKit.isBlank(isksh)){
			
			Param p = new Param();
			p.put("and k.xzqhdm = ?", "xzqhdm");
			p.put("and k.xian = ?", "xian");
			p.put("and k.kdmc = ?", "kdmc");
			p.put("and q.qymc = ?", "qymc");
			p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and k.kdbm = ?","kdbm");
			p.put("and (CAST(k.isznh as FLOAT) + CAST(k.isscxt as FLOAT)) >= ?", "isksh");
			SqlAndParam s = SqlUtil.buildSql(p, map);
			Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select k.*,q.qymc as qymc",
				"from tz_qiye as q left join tz_kudian as k on k.qyzzjgdm = q.qyzzjgdm "
				+ "left join njpt_diqu as dq on k.xzqhdm = dq.xzqhdm "
				+ "left join fw_area as area on dq.areaid = area.id "
				+ "left join (select sum(dmStock) as sum, CASE WHEN SUM(dmStock) > 0 "
				+ "THEN 1 END ctm,qyzzjgdm,kdbm  from kc_CurrentStock group by qyzzjgdm,kdbm ) as kc"
				+ " on kc.qyzzjgdm = k.qyzzjgdm and kc.kdbm = k.kdbm "
				+ "where k.kdlxbh <> '06' and k.isksh = '1' " + s.getSql() +" order by kc.ctm desc, area.orderno asc, k.ID desc ",
				s.getParam().toArray(new Object[0]));
			return page;
		}else{
			
			Param p = new Param();
			p.put("and k.xzqhdm = ?", "xzqhdm");
			p.put("and k.xian = ?", "xian");
			p.put("and k.kdmc = ?", "kdmc");
			p.put("and q.qymc = ?", "qymc");
			p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
			p.put("and k.kdbm = ?","kdbm");
			SqlAndParam s = SqlUtil.buildSql(p, map);
			Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select k.*,q.qymc as qymc",
				"from tz_qiye as q left join tz_kudian as k on k.qyzzjgdm = q.qyzzjgdm "
				+ "left join njpt_diqu as dq on k.xzqhdm = dq.xzqhdm "
				+ "left join fw_area as area on dq.areaid = area.id "
				+ "left join (select sum(dmStock) as sum, CASE WHEN SUM(dmStock) > 0 "
				+ "THEN 1 END ctm,qyzzjgdm,kdbm  from kc_CurrentStock group by qyzzjgdm,kdbm ) as kc"
				+ " on kc.qyzzjgdm = k.qyzzjgdm and kc.kdbm = k.kdbm "
				+ "where 1 = 1 and k.kdlxbh <> '06' and k.isksh = '1'" + s.getSql() +" order by kc.ctm desc, area.orderno asc, k.ID desc ",
				s.getParam().toArray(new Object[0]));
			return page;
			
		}
	}
	/**
	 * 查询库存汇总
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querycfhj(HashMap<String, Object> map) throws Exception {		
		Param p = new Param();
		p.put("and k.xzqhdm = ?", "xzqhdm");
		p.put("and k.xian = ?", "xian");
		p.put("and k.kdmc = ?", "kdmc");
		p.put("and q.qymc = ?", "qymc");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?","kdbm");
		p.put("and k.isksh = ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(map.get("page"))),
			Integer.valueOf(String.valueOf(map.get("rows"))),
			"select sum(CAST(e.shjcr as FLOAT)) as cfzcr ",
			"from tz_kudian as k left join njpt_diqu as dq on k.xzqhdm = dq.xzqhdm  "
			+ "left join tz_qiye as q on k.qyzzjgdm = q.qyzzjgdm "
			+ "left join fw_area as area on dq.areaid = area.id "
			+ "LEFT join tz_cangfang e ON q.qyzzjgdm=e.qyzzjgdm and k.qyzzjgdm=e.qyzzjgdm and k.kdbm=e.kdbm "
			+ "where 1 = 1 and k.kdlxbh <> '06' " + s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	
	/**
	 * 
	 * @param param
	 * @throws Exception 
	 */
	public static Page<Record> queryNx(HashMap<String, Object> param) throws Exception {
 		Param p = new Param();
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		p.put("and a.vWareHouseCode=?", "vWareHouseCode");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				1,
				10000,
				"SELECT a.year,a.grade ,b.dtRegistrateTime ",
				"FROM kc_CurrentStock a LEFT JOIN crk_Purchase b ON a.kdbm=b.kdbm AND a.qyzzjgdm=b.qyzzjgdm and a.vWareHouseCode=b.vWareHouseCode " + s.getSql()+" ORDER BY b.dtRegistrateTime DESC",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取当期入库
	 * @param param2
	 * @return
	 */
	public static Record findzongrk(HashMap<String, Object> param2) {
		// TODO Auto-generated method stub
		String qyzzjgdm = (String) param2.get("qiye");
		String kdbm = (String) param2.get("kdbm");
		String starttime = (String) param2.get("starttime");
		String endtime = (String) param2.get("endtime");
		String sql ="select sum(dmNW) as sumNW "
				+ "from crk_Purchase where vDirection='入库' "
				+ "and dtRegistrateTime >= ? "
				+ "and dtRegistrateTime <= ? "
				+ "and qyzzjgdm = ? "
				+ "and kdbm = ? "
				+ "Group by qyzzjgdm,kdbm ";
		Record record = Db.findFirst(sql,starttime,endtime,qyzzjgdm,kdbm);
		
		return record;
		
	}

	
	
	/**
	 *获取库点明细情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querymingxi(HashMap<String, Object> map) throws Exception {
		String sql="";
		if(map.get("vGraPorCode") != null){
			if(map.get("vGraPorCode").equals("11")){
				sql+=" and (mx.vGraPorCode in ('1') or mx.vGraPorCode like '11%') ";
			}else if(map.get("vGraPorCode").equals("121")){
				sql+=" and mx.vGraPorCode in ('121') ";
			}else if(map.get("vGraPorCode").equals("122")){
				sql+=" and mx.vGraPorCode in ('122') ";
			}else if(map.get("vGraPorCode").equals("123")){
				sql+=" and mx.vGraPorCode in ('123') ";		
			}else if(map.get("vGraPorCode").equals("32")){
				sql+=" and mx.vGraPorCode in ('32','33','34') ";		
			}else if(map.get("vGraPorCode").equals("2")){
				sql+=" and mx.vGraPorCode like ('2%') ";		
			}else if(map.get("vGraPorCode").equals("31")){
				sql+=" and mx.vGraPorCode in ('31') ";		
			}else if(map.get("vGraPorCode").equals("99")){
				sql+=" and mx.vGraPorCode in ('129','99') ";		
			}
		}
		
		
		Param p = new Param();
		p.put("and mx.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and mx.kdbm = ?", "kdbm");
		p.put("and mx.vSwiftNumber like ?", "vSwiftNumber","%%%s%%");
		p.put("and convert(date,mx.dtRegistrateTime) >= ?", "dtRegistrateTime");
		p.put("and convert(date,mx.dtRegistrateTime) <= ?", "vEndTime");
		p.put("and mx.vWareHouseCode like ?", "vWareHouseCode","%%%s%%");
		p.put("and mx.vGrainProperties =?", "vGrainProperties");
		//p.put("and mx.vGraPorCode in ?", "vGraPorCode");
		p.put("and mx.vDirection = ?", "vDirection");
		p.put("and mx.vGrainLevel = ?", "vGrainLevel");
		p.put("and mx.vName = ?", "vName");
		p.put("and mx.vGraTypeCode like ?", "vGraTypeCode","%s%%");
		//p.put("and pu.vHarvestPeriod = ?", "vHarvestPeriod");
		p.put("and year(mx.dtRegistrateTime) = ?","vHarvestPeriod");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select mx.* "
				,
				" from (select pu.vSwiftNumber as vSwiftNumber,pu.dtRegistrateTime as dtRegistrateTime,"
				+ "pu.vViechelNumber as vViechelNumber,gt.vName as vName,gt.vCode as vGraTypeCode,"
				+ "gp.vGrainProperties as vGrainProperties,gp.vCode as vGraPorCode,abs(pu.dmNW) as dmNW,"
				+ "pu.vHarvestPeriod as vHarvestPeriod,isnull(pu.vGrainLevel,'03') as vGrainLevel,"
				+ "pu.vWareHouseCode as vWareHouseCode,pu.qyzzjgdm as qyzzjgdm,"
				+ "pu.kdbm as kdbm,pu.vDirection as vDirection,kc.grade as grade,kc.[year] as [year] "+
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase pu on a.qyzzjgdm=pu.qyzzjgdm "
				+ "and b.kdbm=pu.kdbm and c.cfbm=pu.vWareHouseCode "
				+ " LEFT JOIN dbo.tz_GrainType AS gt ON"
				+ " pu.vGrainSubTypeCode= gt.vCode LEFT JOIN "
				+ "dbo.tz_GrainProperties AS gp ON "
				+ "pu.vGrainPropertyId= gp.vCode LEFT JOIN kc_CurrentStock kc on pu.qyzzjgdm=kc.qyzzjgdm and pu.kdbm=kc.kdbm and pu.vWareHouseCode=kc.vWareHouseCode "
				+ "where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' ) as mx  where 1=1 " + s.getSql() + sql + " order by mx.dtRegistrateTime desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *获取库点明细合计 2017-06-16
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> minxiheji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and hj.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and hj.kdbm = ?", "kdbm");
		p.put("and convert(date,hj.dtRegistrateTime) >= ?", "dtRegistrateTime");
		p.put("and convert(date,hj.dtRegistrateTime) <= ?", "vEndTime");
		p.put("and hj.vGrainPropertyId like ?", "vGraPorCode","%s%%");
		p.put("and hj.vDirection = ?", "vDirection");
		p.put("and hj.vGrainLevel = ?", "vGrainLevel");
		p.put("and hj.vGrainSubTypeCode like ?", "vGraTypeCode","%s%%");
		//p.put("and pu.vHarvestPeriod = ?", "vHarvestPeriod");
		//p.put("and year(pu.dtRegistrateTime) = ?","vHarvestPeriod");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				" select isnull(sum(hj.dmNW)/1000,0) as weight ",
				" from (select pu.qyzzjgdm as qyzzjgdm,pu.kdbm as kdbm,pu.dtRegistrateTime as dtRegistrateTime,pu.vGrainPropertyId as vGrainPropertyId,pu.vDirection as vDirection,isnull(pu.vGrainLevel,'03') as vGrainLevel,pu.vGrainSubTypeCode as vGrainSubTypeCode,pu.dmNW as dmNW from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase pu on a.qyzzjgdm=pu.qyzzjgdm "
				+ "and b.kdbm=pu.kdbm and c.cfbm=pu.vWareHouseCode "
				+ "where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' ) as hj where 1=1 " + s.getSql()+"  ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取总库存
	 * @param map
	 * @return
	 */
	public static Record  findzongkucun(HashMap<String, Object> map){
		String sql = "select sum(kc.dmStock) as sum from tz_qiye AS qy "
				+ "LEFT JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm "
				+ "LEFT JOIN tz_cangfang AS cf on qy.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ "LEFT JOIN kc_CurrentStock AS kc ON cf.qyzzjgdm = kc.qyzzjgdm  AND cf.kdbm = kc.kdbm "
				+ "AND cf.cfbm = kc.vWareHouseCode "
				+ "where kc.qyzzjgdm=? and kc.kdbm=? ";
		Record record = Db.findFirst(sql, map.get("qiye"),map.get("kdbm"));
		return record;
	}
	
	/**
	 * 出入库的量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querychurukuliang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and convert(date,p.dtRegistrateTime) >= ?", "dtRegistrateTime");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select abs(sum(dmNW)) as sum,k.qyzzjgdm as qyzzjgdm,"
				+ "k.kdbm as kdbm,p.vDirection as vDirection,k.dtRegistrateTime ",
				"from crk_Purchase p left join tz_kudian k on p.qyzzjgdm = k.qyzzjgdm and "
				+ "p.kdbm = k.kdbm where 1 = 1" + s.getSql() +"group p.vDirection order by ",
				s.getParam().toArray(new Object[0]));
		return page;	
	}
	
	
	/**
	 * 获取各粮食品种库存
	 * @param map
	 * @return
	 */
	public static Record  findpzkucun(HashMap<String, Object> map){
		String type = (String) map.get("lspz");
		String sql = "select sum(c.dmStock) as "+type+" from tz_qiye AS qy "
				+ "LEFT JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm "
				+ "LEFT JOIN tz_cangfang AS cf on qy.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm  "
				+ "LEFT JOIN kc_CurrentStock AS c ON cf.qyzzjgdm = c.qyzzjgdm   "
				+ "AND cf.kdbm = c.kdbm AND cf.cfbm = c.vWareHouseCode "
				+ "kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and kd.isksh='1' and cf.cfztbh='01' "
				+ "where c.qyzzjgdm=? and c.kdbm=? and c.vGrainSubTypeCode like ?";
		String qiye = (String) map.get("qiye");
		String kdbm = (String) map.get("kdbm");
		String lspzbm = (String) map.get("pzcode");
		Record record = Db.findFirst(sql, qiye,kdbm,lspzbm+"%");
		return record;
	}
	
	/**
	 * 计算合计
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findheji(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and k.xian = ?", "xian");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdmc = ?", "kdmc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(map.get("page"))),
			Integer.valueOf(String.valueOf(map.get("rows"))),
			"select k.*,c.sum as sum",
			"from tz_kudian k left join "
			+ "(select sum(dmStock) as sum,min(qyzzjgdm) as qyzzjgdm,"
			+ "min(kdbm) as kdbm from kc_CurrentStock group by qyzzjgdm,kdbm) c"
			+ " on k.qyzzjgdm = c.qyzzjgdm and k.kdbm = c.kdbm  where 1=1" + s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	

	
	/**
	 * 获取视频
	 * @param map
	 * @return
	 */
	public static Record  readshiping(HashMap<String, Object> map){
		String qyzzjgdm = (String) map.get("qyzzjgdm");
		String kdbm = (String)map.get("kdbm");
		String sql = "select kdmc from tz_kudian where qyzzjgdm = ? and kdbm = ?";
		Record record = Db.findFirst(sql,qyzzjgdm,kdbm);
		return record;
	}

	public static Page<Record> findshuiza(HashMap<String, Object> map) throws Exception {

		Param p = new Param();
		p.put("and a.xian = ?", "xian");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and a.qymc = ?", "qymc");
		p.put("and convert(date,d.dtRegistrateTime) = ?", "dtRegistrateTime");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select d.*,a.xian ",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}


	public static Page<Record> queryfengquzongliang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and convert(date,p.dtRegistrateTime) = ?", "dtRegistrateTime");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select abs(sum(d.dmNW)) as sum,a.xian as xian,d.vDirection as vDirection, area.orderno as orderno ",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
				+" inner join fw_area as area on a.xian = area.name "
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ s.getSql() +"group by k.xian,p.vDirection,area.orderno order by area.orderno ",
				s.getParam().toArray(new Object[0]));
		return page;	
	}


	/**
	 * 实时汇总查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryhuizong(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and ku.xian = ?", "xian");
		p.put("and pu.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and pu.kdbm = ?", "kdbm");
		p.put("and ku.kdmc = ?", "kdmc");
		p.put("and qi.qymc = ?", "qymc");
		p.put("and gp.vGrainProperties = ?", "vGrainProperties");
		p.put("and gt.vName = ?", "vName");
		p.put("and pu.vGrainLevel = ?", "vGrainLevel");
		p.put("and pu.vDirection = ?", "vDirection");
		p.put("and convert(date,pu.dtRegistrateTime) >= ?", "dtRegistrateTime");
		p.put("and convert(date,pu.dtRegistrateTime) <= ?", "vEndTime");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),			
				"select ku.xian AS xian,qi.qymc AS qymc,ku.kdmc AS kdmc,"
				+ "gp.vGrainProperties AS vGrainProperties,gt.vName AS vName,pu.vGrainLevel AS vGrainLevel,"
				+ "abs(pu.dmNW/1000) AS dmNW,pu.vDirection AS vDirection,convert(varchar(10),pu.dtRegistrateTime,120) AS dtRegistrateTime,"
				+ "pu.qyzzjgdm as qyzzjgdm, pu.kdbm as kdbm",
				"from dbo.crk_Purchase AS pu LEFT JOIN dbo.tz_kudian AS ku ON "
				+ "pu.qyzzjgdm = ku.qyzzjgdm AND pu.kdbm = ku.kdbm LEFT JOIN dbo.tz_qiye AS qi ON "
				+ "pu.qyzzjgdm = qi.qyzzjgdm LEFT JOIN dbo.tz_GrainProperties AS gp ON "
				+ "pu.vGrainPropertyId = gp.vCode LEFT JOIN dbo.tz_GrainType AS gt ON "
				+ "pu.vGrainSubTypeCode = gt.vCode where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));		
		return page;	
	}
	
	/**
	 * 实时汇总存储明细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findzhj(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.kdbm = ?", "kdbm");
		p.put("and c.vGrainSubTypeCode = ?", "gtvCode");
		p.put("and c.vGrainPropertyCode = ?", "gpvCode");
		p.put("and gt.vName = ?", "vName");
		p.put("and gp.vGrainProperties = ?", "vGrainProperties");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.*,gt.vName,gp.vGrainProperties,cf.cfmc as cfmc ",
				"from tz_qiye AS qy "
				+ "inner JOIN tz_kudian AS kd ON qy.qyzzjgdm = kd.qyzzjgdm "
				+ "inner JOIN tz_cangfang AS cf "
				+ "on qy.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ "inner join kc_CurrentStock as c on "
				+ "cf.qyzzjgdm = c.qyzzjgdm and cf.kdbm = c.kdbm and cf.cfbm = c.vWareHouseCode "
				+ "inner join tz_GrainType as gt on c.vGrainSubTypeCode = gt.vCode "
				+ "inner join tz_GrainProperties as gp on c.vGrainPropertyCode = gp.vCode "
				+ "where kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and c.dmStock <> 0 "
				+ "and kd.isksh='1' and cf.cfztbh='01' " + s.getSql() + "order by gt.vCode,cf.cfbm",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 全市汇总
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findqszhj(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and qy.xian = ?", "xian");
		p.put("and qy.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select sum(c.dmStock)/1000 as dmStock,min(gt.vName) as vName, "
				+ "min(gp.vGrainProperties) as vgp, gt.vCode as gtcode,"
				+ "gp.vCode as gpcode",
				" from tz_qiye as qy inner join tz_kudian as kd on qy.qyzzjgdm = kd.qyzzjgdm inner join "
				+ "tz_cangfang as cf on kd.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ " left join kc_CurrentStock as c on "
				+ "cf.qyzzjgdm = c.qyzzjgdm and cf.kdbm = c.kdbm and cf.cfbm = c.vWareHouseCode "			
				+ "left join tz_GrainType as gt on c.vGrainSubTypeCode = gt.vCode "
				+ "left join tz_GrainProperties as gp on c.vGrainPropertyCode = gp.vCode "
				+ "where kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and kd.isksh='1' and cf.cfztbh='01' " + s.getSql() + " group by gt.vCode,gp.vCode order by gt.vCode ",
				s.getParam().toArray(new Object[0]));
		return page;	
	}
	
	/**
	 * 全市汇总
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findqszhjyear(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and qy.xian = ?", "xian");
		p.put("and qy.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select sum(c.dmStock)/1000 as dmStock,min(gt.vName) as vName, "
				+ "gt.vCode as gtcode,"
				+ "c.year as year ",
				" from tz_qiye as qy inner join tz_kudian as kd on qy.qyzzjgdm = kd.qyzzjgdm inner join "
				+ "tz_cangfang as cf on kd.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ " left join kc_CurrentStock as c on "
				+ "cf.qyzzjgdm = c.qyzzjgdm and cf.kdbm = c.kdbm and cf.cfbm = c.vWareHouseCode "			
				+ "left join tz_GrainType as gt on c.vGrainSubTypeCode = gt.vCode "
				+ "left join tz_GrainProperties as gp on c.vGrainPropertyCode = gp.vCode "
				+ "where kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and kd.isksh='1' and cf.cfztbh='01' " + s.getSql() + " group by gt.vCode,c.year order by gt.vCode ",
				s.getParam().toArray(new Object[0]));
		return page;	
	}
	
	/**
	 * 全市汇总大类
	 * @param param
	 * @return
	 */
	public static Page<Record> findqszhjdl(HashMap<String, Object> map) {
		Param p = new Param();
		p.put("and qy.xian = ?", "xian");
		p.put("and qy.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select sum(c.dmStock) as dmStock,min(gt.vName) as vName, "
				+ "min(gp.vGrainProperties) as vgp, gt.vCode as gtcode,"
				+ "gp.vCode as gpcode",
				"from tz_qiye as qy inner join tz_kudian as kd on qy.qyzzjgdm = kd.qyzzjgdm inner join "
				+ "tz_cangfang as cf on kd.qyzzjgdm = cf.qyzzjgdm and kd.kdbm = cf.kdbm "
				+ " left join kc_CurrentStock as c on "
				+ "cf.qyzzjgdm = c.qyzzjgdm and cf.kdbm = c.kdbm and cf.cfbm = c.vWareHouseCode "			
				+ "left join tz_GrainType as gt on c.vGrainSubTypeCode = gt.vCode "
				+ "left join tz_GrainProperties as gp on c.vGrainPropertyCode = gp.vCode "
				+ "where kd.kdlxbh<>'06' and kd.kdlxbh<>'05' and kd.kdlxbh<>'04' and kd.isksh='1' and cf.cfztbh='01' " + s.getSql() + "group by gt.vCode,gp.vCode order by gt.vCode ",
				s.getParam().toArray(new Object[0]));
		return page;	
	}


	public static Page<Record> queryliangqing(HashMap<String, Object> map) throws Exception {
		//获取当前分期
		Date dt = new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String today = matter1.format(dt);
	    String sql = "select * "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
	    Record recordt = Db.findFirst(sql, today,today);
	    String starttime;
	    String endtime;
	    if(recordt == null){
	    	sql = "select starttime , endtime "
		    		+ "from njpt_fenqishangbao "
		    		+ "where convert(date,endtime) <= ? order by endtime desc ";
	    	recordt = Db.findFirst(sql,today);
	    	if(recordt == null){
	    		starttime = "2100-01-01";
		    	endtime = "2000-01-01";	
	    	}else{
	    		starttime = recordt.getStr("starttime");
			    endtime = recordt.getStr("endtime");
	    	}
	    }else{
	    	starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
	    }
	    

		Param p = new Param();
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select sum(dmNW)/1000 as sum, vGrainPropertyId",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
				+ "where d.vDirection = '入库' "
				+ "and d.dtRegistrateTime >= '"+starttime+"' "
				+ "and d.dtRegistrateTime <= '"+endtime+"' " + s.getSql() + "group by vGrainPropertyId",
				s.getParam().toArray(new Object[0]));
		return page;	
		
	}
	
	
	public static Page<Record> queryqiuliangshebei(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_qiuliangshebei "
				+ "where 1=1 " + s.getSql() + "order by diqu",
				s.getParam().toArray(new Object[0]));
		return page;	
		
	}

	/**
	 * 查找库点明细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findkcxinxi(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and cf.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and cf.kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cf.*, cs.dmStock as dmStock, cs.grade as grade, cs.year as year, "
				+ "gt.vName as vName, gp.vGrainProperties as vGrainProperties ",
				"from tz_cangfang as cf left join kc_CurrentStock as cs on "
				+ "cf.qyzzjgdm = cs.qyzzjgdm and cf.kdbm = cs.kdbm and cf.cfbm = cs.vWareHouseCode"
				+ " left join tz_GrainType as gt on "
				+ "cs.vGrainSubTypeCode = gt.vCode left join tz_GrainProperties as gp on "
				+ "cs.vGrainPropertyCode = gp.vCode "
				+ "where 1=1 " + s.getSql() + "order by cf.cfbm",
				s.getParam().toArray(new Object[0]));
		return page;	
	}
	
	/**
	 * 查询库点基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findkudian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select k.*,q.qymc,q.sjdw,q.qyxzmc,q.fddbr,q.lxdh as qylxdh, q.qyyx, q.fax,q.zdmj as zhdmj,q.kqh as kqs,q.cfzcr as cafzcr,q.yxcr as youxcr,"
				+ "q.bgy as bgy,q.czlybgy as czlybgy,q.jhyy as jhyy,q.czlyzljyy as czlyzljyy, q.xxdz as qyxxdz, q.lsgx, q.cyry, q.tlzyx as qytlzyx, q.zymt as qyzymt, q.dp as qydp,"
				+ "q.zhaopeng as qyzhaopeng, q.jixiezhaopeng as qyjixiezhaopeng, q.jhyshi as qyjhyshi,"
				+ "q.yaojiku as qyyaojiku, q.xiaofangshuichi as qyxiaofangshuichi ",
				"from tz_kudian as k left join tz_qiye as q on "
				+ "k.qyzzjgdm = q.qyzzjgdm "
				+ "where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> queryxingzhi(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.xzqhdm=?", "xzqhdm");
		p.put("and a.qyzzjgdm=?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select d.*,a.xzqhdm",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ s.getSql() ,
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
		p.put("and xzqhdm = ?", "quyu");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		//p.put("and qyzzjgdm like ?", "qyzzjgdm","%%%s%%");
		p.put("and qymc = ?", "qymc");
		p.put("and qyxzmc = ?", "qyxzmc");
		p.put("and xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				" from tz_qiye  where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 遍历企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqiye1(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.xzqhdm = ?", "quyu");
		//p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.qyzzjgdm like ?", "qyzzjgdm","%%%s%%");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.qyxzmc = ?", "qyxzmc");
		p.put("and a.xian = ?", "xian");
		p.put("and b.isksh = ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.qyzzjgdm,a.qymc ",
				"from tz_qiye a INNER JOIN tz_kudian b  on  a.qyzzjgdm=b.qyzzjgdm where 1=1 " + s.getSql()+" GROUP BY a.qyzzjgdm,a.qymc",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *区县下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryquxian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.name = ?", "name");
		p.put("and id = ?", "id");
		p.put("and b.xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.*,b.xzqhdm as xzqhdm ",
				"from fw_area a,njpt_diqu b where a.id=b.areaid and "
				+ "parentid!='root' " + s.getSql() + " ORDER BY orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *区县下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryquxianhk(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and pid = ?", "id");
		p.put("and level = ?","level");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from hikvision WHERE 1=1 "+ s.getSql()+"  ORDER BY `level` ,orderno,`open`DESC " ,s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *区县下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findyaokuxinxi(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and shiqu = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_yaokuguanli WHERE 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *区县下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findcfkudian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and name = ?", "name");
		p.put("and id = ?", "id");
		p.put("and xzqydm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"FROM njpt_yaokuguanli WHERE 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *数据表t_property 属性名下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querypropname(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and propname = ?", "propname");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from t_Property where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *数据表t_property 属性值下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querypropvalue(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and propvalue = ?", "propvalue");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from t_Property where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *应急小组负责人下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryfuzeren(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and fuzeren = ?", "fuzeren");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_yingjixiaozu where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *应急小组成员下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querychenyuan(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and chenyuan = ?", "chenyuan");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_yingjixiaozu where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	public static Page<Record> queryquxianmingxi(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and xian = ?", "xian");
		p.put("and xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_diquniandu where 1=1 " + s.getSql() + "order by niandu desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 查询区县基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryquxiancontent(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_diqu where 1=1 " + s.getSql() + "order by orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	public static Page<Record> findcangfang(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_cangfang where 1=1 " + s.getSql() +" order by cfbm",
				s.getParam().toArray(new Object[0]));
		
		
		for(Record re:page.getList()){
			re.set("isexists", "0");
			File file = new File("WebContent/"+re.get("cfzppath").toString());  
		       if (file.exists()) {  
		    	   re.set("isexists", "1");
		           System.out.println("文件：" + file.getPath() + "|||||存在");  
		       } else {  
		    	   re.set("isexists", "0");
		           System.out.println("文件：" + file.getPath() + "|||||不存在!");  
		       }   
		}
		return page;
	}
	public static Page<Record> findmysqlcf(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from house where 1=1 " + s.getSql() +" order by cfbm",
				s.getParam().toArray(new Object[0]));
		
		return page;
	}
	
	public static Page<Record> querylqtime(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and house = ?", "vCode");
		p.put("and entcode = ?", "qyzzjgdm");
		p.put("and orgcode = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select time ",
				"from graintemp where 1=1 " + s.getSql() +" order by time desc",
				s.getParam().toArray(new Object[0]));
		
		return page;
	}
	
	/**
	 * 遍历库点信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querykudian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and k.xian = ?","xian");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?", "kdbm");
		p.put("and k.kdmc = ?", "kdmc");
		p.put("and q.qymc = ?", "qymc");
		p.put("and k.isksh = ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select k.* ",
				"from tz_kudian as k left join tz_qiye as q on k.qyzzjgdm = q.qyzzjgdm where 1=1 and k.kdlxbh<>'06' " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 企业用户下，只显示该企业下的库点
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querykudianbyqiyeid(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.xian = ?","xian");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and b.id = ?", "qiyeid");
		p.put("and a.kdbm = ?", "kdbm");
		p.put("and a.kdmc = ?", "kdmc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.* ",
				"from tz_kudian a left join tz_qiye b on a.qyzzjgdm=b.qyzzjgdm where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 根据企业遍历库点
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querykud(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and qymc = ?", "qymc");
		p.put("and a.kdlxbh = ?", "bh");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT a.kdmc ",
				" FROM tz_kudian a JOIN tz_qiye b ON "
				+ "a.qyzzjgdm=b.qyzzjgdm WHERE b.qyzzjgdm "
				+ "IN (SELECT qyzzjgdm FROM tz_qiye WHERE 1=1 "+ s.getSql()+")",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 根据区域遍历库点
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryyjjgkud(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and xzqhdm = ?", "xian");
		p.put("and kdlxbh = ?", "bh");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT kdmc ",
				" FROM tz_kudian  "
						+ " WHERE 1=1 "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 *企业名称下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqymc(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and name = ?", "name");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_qiye where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *企业名称下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findcaidanname(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and caidanname = ?", "caidanname");
		p.put("and userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from fw_caozuo where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *模块下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryrate(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from fw_rate where 1=1 " + s.getSql() + " ORDER BY orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *密码信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querymima(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id=?", "userid");
		p.put("and password=?", "yuanpassword");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from fw_user where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *用户下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> finduserid(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from fw_user where 1=1 " + s.getSql() + " ORDER BY orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *人员通讯录用户下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findrenyuanid(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from fw_dep where parentid ='root' " + s.getSql() + " ORDER BY orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 *库点名称下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryKDmc(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and name = ?", "name");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_kudian where 1=1 " + s.getSql() + " ORDER BY id",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryfenqi(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select starttime, min(endtime) as endtime, min(niandu) as niandu, min(jidu) as jidu ",
				"from njpt_fenqishangbao where 1=1 " + s.getSql() + " GROUP BY starttime order by starttime desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取粮食性质
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querylsxz(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainProperties where 1=1 " + s.getSql() + "order by vCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取粮食品种
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylspz(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainType where 1=1 " + s.getSql() + "order by vCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取粮食等级
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querylsdj(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainLevel where 1=1 " + s.getSql() + "order by vCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取年度夏粮预测
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryxialiangyucend(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select min(niandu) as niandu ",
				"from njpt_xialiangyuce where 1=1" + s.getSql() + "group by niandu order by niandu desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取秋粮预测年度数据
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryqiuliangyucend(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and niandu = ?", "niandu");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select min(niandu) as niandu ",
				"from njpt_qiuliangyuce where 1=1" + s.getSql() + "group by niandu order by niandu desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	


	

	

	


	
	
}
