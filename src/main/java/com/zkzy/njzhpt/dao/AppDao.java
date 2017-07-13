package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public class AppDao {

	public static Page<Record> getQyInfo(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.jyywlxbh = ?", "jyywlxbh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select sum(CAST(a.zdmj as FLOAT)) as zdmj,sum(CAST(a.yxcr as FLOAT)) as yxcr,sum(CAST(a.cfzcr as FLOAT)) as cfzcr,a.jyywlxbh as jyywlxbh,count(a.jyywlxbh) as num ,a.jyywlxmc as jyywlxmc ",
				"from  tz_qiye a where 1=1 " + s.getSql()+" GROUP BY a.jyywlxbh,a.jyywlxmc", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	public static Page<Record> getKdKsh(HashMap<String,Object> param){
		Param p = new Param();
		p.put("and k.xzqhdm = ?", "xzqhdm");
		p.put("and k.xian = ?", "xian");
		p.put("and k.kdmc = ?", "kdmc");
		p.put("and q.qymc = ?", "qymc");
		p.put("and k.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and k.kdbm = ?","kdbm");
		p.put("and (CAST(k.isznh as FLOAT) + CAST(k.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(param.get("page"))),
			Integer.valueOf(String.valueOf(param.get("rows"))),
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
	}
	
	
	public static Page<Record> searchlscrk(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and vSwiftNumber = ?", "vSwiftNumber");
		p.put("and vCustomerIdentificationId = ?", "vCustomerIdentificationId");
		p.put("and vWareHouseCode = ?","vWareHouseCode" );
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from crk_Purchase where qyzzjgdm = '134967559' and kdbm = '003' and vDirection = '入库' " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}


	public static Page<Record> searchlscf(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select cfbm,cfmc ",
				"from tz_cangfang where qyzzjgdm = '134967559' and kdbm = '003'" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	
	public static Page<Record> findQiyexinxi(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.ID = ?", "ID");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.qyxzmc = ?", "qyxzmc");
		p.put("and a.jyywlxbh = ?", "jyywlxbh");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select a.* ",
				"from tz_qiye a left join njpt_diqu t1 on a.xzqhdm=t1.xzqhdm where a.xzqhdm in (210008,320111,320115,320116,320124,320125) and  a.qyxzbh in (01,02,05,06) and a.jyywlxbh in (01,02,03,04,05,06)   " + s.getSql()+" order by t1.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findQyba(HashMap<String, Object> param) throws Exception {
	
		String sql=" and  SUBSTRING(a.status, 0, 2)>=f.jiedian ";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and f.shenpiren = ?", "shenpiren");
		p.put("and d.xzqhdm= ?", "quyu");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.liuchengid = ?", "liuchengid");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and b.qyzzjgdm=?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,b.qyzzjgdm as qyzzjgdm,d.areaid as areaid,e.name as quyu,f.jiedian as jiedian",
				"from oa_qiyebeian a,tz_qiye b,fw_dep c,njpt_diqu d,fw_area e,oa_shenpiliucheng f where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchengid and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno,a.beianriqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findXzba(HashMap<String, Object> param) throws Exception {
		String sql=" and  SUBSTRING(a.status, 0, 2)>=f.jiedian ";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and f.liuchenghao = ?", "liuchenghao");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and f.shenpiren = ?", "shenpiren");
		p.put("and d.xzqhdm=?","quyu");
		p.put("and b.qyzzjgdm=?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,b.qyzzjgdm as qyzzjgdm, d.areaid as areaid,e.name as quyu,f.jiedian as jiedian",
				"from oa_xunzhengbeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e,oa_shenpiliucheng f  where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchenghao and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno,a.beianriqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static boolean  updateQyba(HashMap<String, Object> param) {
		int flag=Db.update("update oa_qiyebeian set status=? , shenpiren=? where id=?",param.get("status"),param.get("shenpiren"),param.get("id"));
		return flag==1?true:false;
	}

	
	
	public static Page<Record> findYjba(HashMap<String, Object> param) throws Exception {
		String sql=" and  SUBSTRING(a.status, 0, 2)>=f.jiedian ";
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and f.shenpiren = ?", "shenpiren");
		p.put("and d.xzqhdm=?","quyu");
		p.put("and b.qyzzjgdm=?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,d.areaid as areaid,e.name as quyu,f.jiedian as jiedian ",
				"from oa_yaojibeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e,oa_shenpiliucheng f  where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchenghao  and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取企业备案信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findQiyebeian(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.liuchengid = ?", "liuchengid");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and d.xzqhdm=?", "quyu");
		p.put("and b.qyzzjgdm =?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,d.areaid as areaid,e.name as quyu ",
				"from oa_qiyebeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid " + s.getSql()+ " ORDER BY d.orderno ASC,a.beianriqi desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryLsj(HashMap<String, Object> param) throws Exception {
		Record niandu= Db.findFirst("select a.niandu from njpt_diquniandu  a GROUP BY a.niandu ORDER BY a.niandu  DESC");
		if(niandu!=null){
			param.put("niandu", String.valueOf(niandu.getInt("niandu")));
		}
		Param p = new Param();
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and b.quyu = ?", "name","%%%s%%");
		p.put("and b.niandu = ?", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT b.*,a.qxjd as qxjd,a.qxwd as qxwd,a.dizhi as dizhi,a.fangwei as fangwei,a.lsjmc as lsjmc,a.maplevel as maplevel,a.qumappath as qumappath,a.qushiyipath as qushiyipath,a.tel as tel, a.textcontent as textcontent  ",
				" from njpt_diqu a INNER JOIN njpt_diquniandu b ON a.xzqhdm=b.xzqhdm " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取熏蒸备案作业人员信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findzuoyerenyuan(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and beianbianhao = ?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT *",
				"from xz_zuoyerenyuan WHERE 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取熏蒸对象信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findxunzhendx(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and beianbianhao = ?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT *",
				"from xz_xunzhendx WHERE 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取熏蒸药剂信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findxunzhenyj(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and beianbianhao = ?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT *",
				"from xz_yaojixinxi WHERE 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂库存
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findYjckxx(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and beianbianhao = ?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select * ", 
			"from xz_yaojixinxi where 1=1 "+s.getSql() + " order by id",
			s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取轮换批文
	 * @param queryparam
	 */
	public static Record findInDoc(HashMap<String, Object> queryparam) {
		
		return Db.findFirst("select * from njpt_lunhuanshenqingb a where a.id=?",queryparam.get("id"));
	}
	
	/**
	 * 获取轮换批文
	 * @param queryparam
	 */
	public static Record findOutDoc(HashMap<String, Object> queryparam) {
		return  Db.findFirst("select * from njpt_lunrushenqingb a where a.id=?",queryparam.get("id"));
		
	}
}
