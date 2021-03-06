package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ZhiliangzsDAO {

	/**
	 * 遍历库点信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querykudian(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and kd.xian = ?", "xian");
		p.put("and kd.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and qy.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select kd.*,qy.qymc as qymc ",
				"from tz_kudian as kd left join njpt_diqu as dq on kd.xzqhdm = dq.xzqhdm "
				+ " inner join tz_qiye as qy on "
				+ "kd.qyzzjgdm = qy.qyzzjgdm where 1=1 and kd.kdlxbh<>'06' " + s.getSql() + " order by dq.orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历质量追溯
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryzhiliang(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and zlmx.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and zlmx.kdbm = ?", "kdbm");
		p.put("and zlmx.zhiliangleixing = ?", "zhiliangleixing");
		p.put("and zlmx.pinzhong = ?","pz");
		p.put("and zlmx.jianyanriqi >= ?","starttime");
		p.put("and zlmx.jianyanriqi <= ?", "endtime");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select zlmx.*,gt.vName as vName ",
				"from tz_zhiliangmx as zlmx left join tz_GrainType as gt on "
				+ "zlmx.pinzhong = gt.vCode where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历视频追溯
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryshipingzs(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and zlmx.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and zlmx.kdbm = ?", "kdbm");
		p.put("and zlmx.zhiliangleixing = ?", "zhiliangleixing");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_shipingzhuisu where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历入库信息登记
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryrukudj(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and huoweidm like ?", "huoweidm","%%%s%%");
		p.put("and huoweimc like ?", "huoweimc","%%%s%%");
		p.put("and liangshipz = ?", "liangshipz");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_rukuxinxidj where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历出库登记表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querychukudj(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and huoweibm = ?", "huoweibm");
		p.put("and huoweimc = ?", "huoweimc");
		p.put("and kudianmc = ?", "kudianmc");
		p.put("and cangfangmc = ?", "cangfangmc");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_chukuxinxidj where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

}
