package com.zkzy.njzhpt.dao;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class EmergencyDAO {

	/**
	 * 遍历应急状态表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryyingjizt(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_emergency  where 1=1 " + s.getSql() + " order by slevel ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历应急状态等级
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryyingjidj(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from njpt_emergencyLevel  where 1=1 " + s.getSql() + " order by slevel ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历人口表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryrenkou(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.xian = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.* ",
				"from njpt_populationInfo a LEFT JOIN fw_area b ON a.xian=b.name where 1=1 " + s.getSql() + " order by b.orderno,a.id DESC ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 获取新闻名称
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querynews(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from t_news where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取用户关联的新闻网站
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> getnews(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and ncf.userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select tn.* ",
				"from njpt_newsconflict as ncf left join t_news as tn on "
				+ "ncf.newid = tn.id  where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历粮食收购资格许可信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylssgzg(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qymc = ?", "qymc");
		p.put("and jylx = ?", "jylx");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_lssgzzxkxx where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历商品粮油收支月报
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylysz(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and yuedu = ?", "yuedu");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select yuedu, min(tiandata) as tiandata ",
				"from njpt_splyszybb where 1=1 " + s.getSql() + " group by yuedu order by yuedu desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历从业人员表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querycyry(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and year = ?", "year");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_congyerenyuan where 1=1 " + s.getSql() + " order by year desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历期末库存表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylyjgqy(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and year = ?", "year");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select min(year) as year,min(tiandata) as tiandata ",
				"from njpt_lyjgzhqynb where 1=1 " + s.getSql() + "group by year order by year desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 遍历粮油科技加工
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylykjjb(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and year = ?", "year");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_lykjjbqk where 1=1 " + s.getSql() + " order by year desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历粮食产业经济统计
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querylscyjjtj(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and year = ?", "year");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_lscyjjtj where 1=1 " + s.getSql() + " order by year desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取树形结构列表
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querytree(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and bianma = ?", "bianma");
		p.put("and cengshu = ?", "cengshu");
		p.put("and name = ?", "name");
		p.put("and isend = ?", "isend");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_treelist where leixing = 'txl' " + s.getSql() + " order by parentorderno,orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取树形部门结构列表
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querytreebumen(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.bianma = ?", "bianma");
		p.put("and a.cengshu = ?", "cengshu");
		p.put("and a.name = ?", "name");
		p.put("and a.isend = ?", "isend");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*,b.name as bname ",
				"from oa_treelist as a left join oa_treelist as b on a.shangji = b.id  where a.leixing = 'txl' " + s.getSql() + " order by parentorderno,orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 遍历应急小组通讯录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querytongxunyj(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and tl.shangji = ?", "fid");
		p.put("and tx.id = ?", "id");
		p.put("and tx.name like ?", "name","%%%s%%");
		p.put("and tx.tel like ?", "tel","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select tx.*,tl.orderno,tl.cengshu,tl.leixing,tl.isend,tl.shangji,tl.parentorderno",
				"from oa_tongxunyj as tx left join oa_treelist as tl "
				+ " on tx.id = tl.id  where 1 = 1 " + s.getSql()+" order by parentorderno,orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 遍历应急小组通讯录
	 * @param param
	 * @return
	 * @throws Exception
	 * 
	 */
	public static Page<Record> querytongxuntxl(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and tl.shangji = ?", "fid");
		p.put("and tx.id = ?", "id");
		p.put("and tx.name like ?", "name","%%%s%%");
		p.put("and tx.tel like ?", "tel","%%%s%%");
		//p.put("and tx.name = ?", "name");
		//p.put("and tx.tel = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select tx.*,tl.orderno,tl.cengshu,tl.leixing,tl.isend,tl.shangji,tl.parentorderno",
				"from oa_tongxun as tx left join oa_treelist as tl "
				+ " on tx.id = tl.id  where 1 = 1 " + s.getSql()+" order by parentorderno,orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取应急小组通讯录树形结构列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querytreeyj(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and bianma = ?", "bianma");
		p.put("and cengshu = ?", "cengshu");
		p.put("and name = ?", "name");
		p.put("and isend = ?", "isend");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_treelist where leixing = 'yj' " + s.getSql() + " order by parentorderno,orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取应急小组通讯录树形结构列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querytongxun(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and bianma = ?", "bianma");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_treelist where leixing = 'yj' " + s.getSql() + " order by cengshu,isend,orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取企业备案
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> getqiyeba(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and xz.id = ?", "id");
		p.put("and qy.qiyeid = ?", "qiyeid");
		p.put("and sp.shenpiren = ?", "shenpiren");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select qy.*,spl.countlc ",
				"from oa_qiyebeian as qy left join "
				+ "oa_shenpiliucheng as sp on qy.liuchengid = sp.liuchenghao and sp.jiedian = qy.status "
				+ "left join "
				+ "(SELECT COUNT(liuchenghao) AS countlc,liuchenghao "
				+ "FROM oa_shenpiliucheng GROUP BY liuchenghao ) "
				+ "as spl on spl.liuchenghao = sp.liuchenghao "
				+ "where 1 = 1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取熏蒸备案
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> getxunzhenba(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and xz.id = ?", "id");
		p.put("and xz.qiyeid = ?", "qiyeid");
		p.put("and spl.shenpiren = ?", "shenpiren");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select xz.*,sp.countlc ",
				"from oa_xunzhengbeian as xz "
				+ "LEFT JOIN oa_shenpiliucheng as spl on "
				+ "xz.liuchenghao = spl.liuchenghao AND xz.status = spl.jiedian  "
				+ "left join "
				+ "(SELECT COUNT(liuchenghao) AS countlc,liuchenghao "
				+ "FROM oa_shenpiliucheng GROUP BY liuchenghao ) "
				+ "as sp on spl.liuchenghao = sp.liuchenghao  "
				+ "where 1 = 1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取药剂备案
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> getyaojiba(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qiyeid = ?", "qiyeid");
		p.put("and sp.shenpiren = ?", "shenpiren");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select yj.* ",
				"from oa_yaojibeian as yj left join "
				+ "oa_shenpiliucheng as sp on yj.liuchenghao = sp.liuchenghao and sp.jiedian = yj.status "
				+ "where 1 = 1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryccss(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and year = ?", "year");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_ccssjbqk where 1 = 1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 查找指定网站新闻
	 * @param name
	 * @return
	 */
	public static Record getnewnow(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from t_newnow where webname = ?";
		
		Record record = Db.findFirst(sql,name); 
		return record;
	}

}
