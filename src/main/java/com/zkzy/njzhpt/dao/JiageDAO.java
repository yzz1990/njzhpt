package com.zkzy.njzhpt.dao;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class JiageDAO {

	/*
	 * 遍历品种
	 */
	public static Page<Record> querypinzhong(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and name = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_pinzhong where state = 1 " + s.getSql() + " order by leixing ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 遍历未对照商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querywdzpz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and id not in (SELECT pzid FROM jg_conflixls WHERE cjdid = ?)", "cjdid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT *",
				"from jg_pinzhong "
				+ " where state = 1 " 
				+ s.getSql() + " order by leixing ",
				s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	/**
	 * 遍历采价点
	 */
	public static Page<Record> querycaijiadian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and cjdname = ?", "cjdname");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_caijiadian where state=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 遍历职员
	 */
	public static Page<Record> queryzhiyuan(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and zymc = ?", "zymc");
		p.put("and dlmc = ?", "dlmc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cjzy.*,zylb.zylbmc as zylbmc,zylx.zylxmc as zylxmc,area.name as quname ",
				"from tz_caijiazhiyuan as cjzy left join tz_zhiyuanlb as zylb on cjzy.zylbcode = zylb.zylbbh "
				+ "left join tz_zhiyuanlx as zylx on cjzy.zylxcode = zylx.zylxbh left join fw_area as area "
				+ "on cjzy.ssqycode = area.id where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历职员类型
	 */
	public static Page<Record> queryzylx(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_zhiyuanlx where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 遍历职员类别
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryzylb(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_zhiyuanlb where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 遍历已选商品
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryyxsp(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cf.cjdid = ?", "cjdid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"SELECT pz.id,pz.name,pz.level ",
				" from jg_pinzhong as pz left join (select distinct(cjdid+pzid) as cjpz,cjdid,pzid from jg_conflixls ) "
				+ "as cf on pz.id = cf.pzid "
				+ " where pz.state = 1 " 
				+ s.getSql() + " order by pz.leixing ",
				s.getParam().toArray(new Object[0]));	
		return page;
	}
	
	/**
	 * 遍历价格类型
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryjiagelx(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and jglx = ?", "jglx");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_jiagelx where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历价格点对应品种
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycjdpz(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cjdcode = ?", "cjdcode");
		p.put("and spmccode = ?", "spmccode");
		p.put("and qiye.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select spmccode, min(gt.vName) as gtname, min(jglx.jglx) as vjglx,min(qiye.qymc) as qymc ",
				"from ls_caijiasp as cjsp left join tz_GrainType as gt on cjsp.spmccode = gt.vCode "
				+ "left join tz_jiagelx as jglx on cjsp.jglx = jglx.jglxbh left join tz_qiye as qiye on "
				+ "cjsp.cjdcode = qiye.qyzzjgdm where 1=1 " + s.getSql() + "group by cjsp.spmccode",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> query(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_caijiazhiyuan where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历粮食等级
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querygrade(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainLevel where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历粮食品种
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> graintype(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and SUBSTRING(vCode,1,3) = ? and datalength(vCode) > 3", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainType where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历父节点品种
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> graintypeall(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_GrainType where datalength(vCode) = 3 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历企业经营类型
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryqylx(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_qylx where 1=1 " + s.getSql() + "order by qylxid ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历报送频率表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querybspl(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_bspl where 1=1 " + s.getSql() + "order by bsplid ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历临时对照表
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querylsconflix(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cjdid = ?", "cjdid");
		p.put("and pzid = ?", "pzid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_conflixls where 1=1 " + s.getSql() + "order by jglxid ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 遍历报送日表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querybsr(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_bsr where 1=1 " + s.getSql() + "order by bsrid ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历联系人
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylxr(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_caijiazhiyuan where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历价格类型
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryjglx(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_jglx where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历临时对照表采价点商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycjdsp(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cjdcode = ?", "cjdcode");
		p.put("and spmccode = ?", "spmccode");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from ls_caijiasp where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历采价点对照的商品
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querydzcjdsp(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cf.cjdid = ?", "cjdid");
		p.put("and cjd.cjdname = ?", "cjdname");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select min(cjd.cjdname) as cjdname, cf.cjdid as cjdid ",
				"from jg_conflix as cf left join jg_caijiadian as cjd on cf.cjdid = cjd.id "
				+ " where cjd.state=1 " + s.getSql() +" group by cf.cjdid ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历采价商品明细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycaijia(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cjsp.cjdcode = ?", "cjdcode");
		p.put("and cjsp.spmccode = ?", "spmccode");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cjsp.*,gt.vName as vname,gl.vLevelName as levelname,npz.grade as vgrade ",
				"from tz_caijiasp as cjsp left join tz_GrainType gt on cjsp.spmccode = gt.vCode  "
				+ "left join tz_jiagelx as jglx on cjsp.jglx = jglx.jglxbh "
				+ "left join njpt_pinzhong as npz on cjsp.spmccode = npz.typecode "
				+ "left join tz_GrainLevel as gl on npz.grade = gl.vCode "
				+ " where 1=1 " + s.getSql() +""
				+ " order by cjsp.spmccode, gl.vCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历计量单位
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryjldw(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_jldw where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	
	
	/**
	 * 获取采价点价格表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycjdjg(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and cf.cjdid = ?", "cjdid");
		p.put("and cf.pzid = ?", "pzid");
		p.put("and cf.jglxid = ?", "jglxid");
		p.put("and bj.year = ?", "year");
		p.put("and bj.week = ?", "week");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cf.*,bj.price as price,bj.id as bjid, bj.year as year, bj.week as week,cjd.cjdname as cjdname,"
				+ "pz.name as pzname,pz.level as level,jglx.jglxname as jglxname ",
				"from jg_baojia as bj left join jg_conflix as cf on bj.conflixid = cf.id "
				+ "left join jg_caijiadian as cjd on cjd.id = cf.cjdid left join "
				+ "jg_pinzhong as pz on pz.id = cf.pzid left join jg_jglx as jglx "
				+ "on jglx.jglxid = cf.jglxid "
				+ "where cjd.state = 1 and pz.state = 1 " + s.getSql() + "order by cjd.id, pz.leixing, bj.id",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取采价点价格对照
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findjgpz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and cf.cjdid = ?", "cjdid");
		p.put("and cf.pzid = ?", "pzid");
		p.put("and cf.jglxid = ?", "jglxid");
		p.put("and cf.id = ?", "conflixid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cf.*,cjd.cjdname as cjdname,pz.name as pzname,"
				+ "pz.level as level,jglx.jglxname as jglxname ",
				"from jg_conflix as cf left join jg_caijiadian as cjd "
				+ "on cjd.id = cf.cjdid left join "
				+ "jg_pinzhong as pz on pz.id = cf.pzid left join jg_jglx as jglx "
				+ "on jglx.jglxid = cf.jglxid "
				+ "where pz.state=1 and cjd.state=1 " + s.getSql() + "order by pz.leixing ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查询报价单
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querybaojia(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and bj.cjdbh = ?", "cjdbh");
		p.put("and qy.xian = ?", "xian");
		p.put("and qy.qymc = ?", "cjdmc");
		p.put("and bj.djzt = ?", "djzt");
		p.put("and bj.year = ?", "year");
		p.put("and bj.iweek = ?", "week");
		p.put("and bj.cjrq > ?", "starttime");
		p.put("and bj.cjrq < ?", "endtime");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select bj.*,qy.qymc as qymc, qy.xian as xian, cjzy.zymc as zymc,gt.vName as vName, gl.vLevelName as vLevelName ",
				"from tz_baojia as bj left join tz_qiye as qy on bj.cjdbh = qy.qyzzjgdm "
				+ "left join tz_caijiazhiyuan as cjzy on bj.cjybh = cjzy.id "
				+ "left join tz_GrainType as gt on bj.pzcode = gt.vCode "
				+ "left join tz_GrainLevel as gl on bj.gradecode = gl.vCode "
				+ "left join tz_jiagelx as jglx on bj.jglxcode = jglx.jglxbh "
				+ " where 1=1 " + s.getSql() + "order by qy.xian,qy.qyzzjgdm, bj.year desc, bj.iweek desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取市内价格明细
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findshinei(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and bj.year = ?", "year");
		p.put("and bj.iweek = ?", "iweek");
		p.put("and pz.typecode = ?", "pzcode");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select pz.typecode as typecode, bj.* ",
				"from njpt_pinzhong as pz left join (select avg(price) as avprice, min(year) as year, "
				+ "min(iweek) as iweek, min(cjdbh) as cjdbh, min(pzcode) as pzcode, "
				+ "min(jglxcode) as jglxcode,min(djzt) as djzt from tz_baojia "
				+ "where djzt = '已审批'  "
				+ "group by pzcode,year,iweek,jglxcode ) as bj on pz.typecode = bj.pzcode "
				+ " where 1=1 " + s.getSql() ,
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查询国内现货价格
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findguonei(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and week = ?", "week");
		p.put("and pzname = ?", "pzname");
		p.put("and lslx = ?", "lslx");
		p.put("and jglx = ?", "jglx");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from jg_baojiagn where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查询国内期货价格
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findqihuo(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and month = ?", "month");
		p.put("and pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_qihuogn  where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 获取外盘期货价格
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findwpqihuo(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and month = ?", "month");
		p.put("and pinzhong = ?", "pinzhong");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_qihuogw  where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取价格指数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findzhishu(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and week = ?", "week");
		p.put("and pzcode = ?", "pzcode");
		p.put("and jglxcode = ?", "jglxcode");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_jiagezhishu  where 1=1 " + s.getSql()+ "order by year desc,week desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 一段时间的价格指数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> coverzhishu(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and week = ?", "week");
		p.put("and pzcode = ?", "pzcode");
		p.put("and jglxcode = ?", "jglxcode");
		p.put("and riqi >= ?", "firstday");
		p.put("and riqi <= ?", "endday");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_jiagezhishu  where 1=1 " + s.getSql() + "order by year,week ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 找到一段时间的期货指数
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryqihuo(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select riqi from tz_qihuogn where year = ? and month = ?";
		String riqi = Db.findFirst(sql,param.get("year"),param.get("month")).getStr("riqi");
		param.put("riqi", riqi);
		System.out.println(riqi);
		
		Param p = new Param();
		p.put("and riqi <= ?", "riqi");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from tz_qihuogn where 1=1 "+ s.getSql() + " order by riqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}

	public static Page<Record> querywpqihuo(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select riqi from tz_qihuogw where year = ? and month = ?";
		String riqi = Db.findFirst(sql,param.get("year"),param.get("month")).getStr("riqi");
		param.put("riqi", riqi);
		System.out.println(riqi);
		
		Param p = new Param();
		p.put("and riqi <= ?", "riqi");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from tz_qihuogw where 1=1 "+ s.getSql() + " order by riqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查找品种
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findpinzhong(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from tz_GrainType where 1=1 "+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/*
	 * 遍历年周数
	 */
	public static Page<Record> findzhous(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("param:"+param);
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and iweek = ?", "week");
		p.put("and beginday <= ?", "today");
		p.put("and endday >= ?", "today");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from tz_nianzhou where 1=1 "+ s.getSql() + " order by iweek",
				s.getParam().toArray(new Object[0]));
			return page;
	}
	
	/**
	 * 获取一年的价格变动
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> covershineiprice(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and nz.beginday >= ?", "qday");
		p.put("and nz.endday <= ?", "day");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select nz.year as nyear, nz.iweek as nweek, bj.*",
				"from tz_nianzhou AS nz LEFT JOIN tz_baojia AS bj "
				+ "ON nz.year = bj.year AND nz.iweek = bj.iweek "
				+ " where 1=1 "+ s.getSql() + "order by nz.year,nz.iweek ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取一年的价格变动
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querynzjg(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and convert(date,nz.beginday) >= ?", "qday");
		p.put("and convert(date,nz.beginday) <= ?", "eday");
		p.put("and jglx.jglxid = ?", "jglxid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select nz.year as nyear, nz.iweek as nweek, bjcf.*,"
				+ "jglx.jglxname as jglxname,jglx.jglxid as idjglx,"
				+ "pz.name as pzname,pz.[level] as [level]",
				"from tz_nianzhou AS nz CROSS JOIN jg_jglx as jglx "
				+ "LEFT JOIN "
				+ "(SELECT Round(AVG (bj.price),2)  AS price,bj.[year] AS [year],"
				+ "bj.week AS week,cf.pzid AS pzid,"
				+ "cf.jglxid AS jglxid FROM jg_baojia AS bj "
				+ "LEFT JOIN jg_conflix AS cf ON "
				+ "bj.conflixid = cf.id GROUP BY bj.[year], bj.week, cf.pzid, cf.jglxid ) "
				+ "AS bjcf ON bjcf.year = nz.year AND bjcf.week = nz.iweek "
				+ "and jglx.jglxid = bjcf.jglxid "
				+ "left join jg_pinzhong as pz on bjcf.pzid = pz.id "
				+ " where 1=1 "+ s.getSql() + "order by nz.year,nz.iweek ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> coverguoneiprice(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and nz.beginday >= ?", "qday");
		p.put("and nz.endday <= ?", "day");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select nz.year as nyear, nz.iweek as nweek, bj.*",
				"from tz_nianzhou AS nz LEFT JOIN jg_baojiagn AS bj "
				+ "ON nz.year = bj.year AND nz.iweek = bj.week "
				+ " where 1=1 "+ s.getSql() + "order by nz.year desc,nz.iweek desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查找未填报采价点
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querywtbcaijia(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and wtbb.year = ?", "year");
		p.put("and wtbb.iweek = ?", "week");
		p.put("and wtbb.endday <= ?", "endday");
		p.put("and wtbb.cjdid = ?", "cjdid");
		p.put("and wtbb.bweek = ?", "bweek");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(param.get("page"))),
			Integer.valueOf(String.valueOf(param.get("rows"))),
			"SELECT wtbb.* ",
			"FROM (SELECT nzcjd.*, CASE WHEN bjcj.week > 0 THEN 1 ELSE 0 END AS bweek "
			+"FROM ( SELECT cjd.id AS cjdid, cjd.cjdname AS cjdname,"
			+ "cjd.lxdh AS lxdh,cjd.lxr AS lxr,nz.* FROM tz_nianzhou AS nz "
			+ "CROSS JOIN jg_caijiadian AS cjd WHERE nz.[year] >= 2015 and cjd.state = 1) AS nzcjd "
			+ "LEFT JOIN ( SELECT bj.[year], bj.week, cf.cjdid FROM "
			+ "jg_baojia AS bj LEFT JOIN jg_conflix AS cf ON bj.conflixid = cf.id "
			+ "GROUP BY cf.cjdid, bj.[year],bj.week ) AS bjcj ON "
			+ "nzcjd.cjdid = bjcj.cjdid AND nzcjd. YEAR = bjcj.[year] "
			+ "AND nzcjd.iweek = bjcj.week ) as wtbb "
			+ " where 1=1 " + s.getSql() + "order by wtbb.endday desc ",
			s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 寻找同比报价
	 * @param param1
	 * @return
	 */
	public static Record findtbbaojia(HashMap<String, Object> param) {
		String sql = "select price as tbprice from jg_baojia where conflixid = ? and year = ? and week = ?";
		Record record = Db.findFirst(sql, param.get("conflixid"), param.get("year"), param.get("week"));
		return record;
	}

	/**
	 * 寻找环比报价C
	 * @param param1
	 * @return
	 */
	public static Record findhbbaojia(HashMap<String, Object> param) {
		String sql = "select price as hbprice from jg_baojia where conflixid = ? and year = ? and week = ?";
		Record record = Db.findFirst(sql, param.get("conflixid"), param.get("year"), param.get("week"));
		return record;
	}

	/**
	 * 查询平均价格
	 * @param param
	 * @return
	 */
	public static List<Record> findgnht(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String sql = "select avg(price) as price, lslx, pzname,min(year) as year,min(week) as week "
				+ "from jg_baojiagn where year = ? and week = ? group by lslx,pzname";
		return Db.find(sql,param.get("year"),param.get("week"));
	}

	/**
	 * 查询历史具体品种平均价格
	 * @param param
	 * @return
	 */
	public static List<Record> findgnls(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		String sql = "select avg(price) as price, lslx, pzname,min(year) as year,min(week) as week "
				+ "from jg_baojiagn where year = ? and week = ? and lslx = ? and pzname = ? "
				+ "group by lslx,pzname";
		return Db.find(sql,param.get("year"),param.get("week"),param.get("lslx"),param.get("pzname"));
	}
	


	
}
