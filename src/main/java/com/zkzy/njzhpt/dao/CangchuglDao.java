package com.zkzy.njzhpt.dao;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class CangchuglDao {
	
	/**
	 * 药库分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYaoku(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and c.name = ?", "shiqu");
		p.put("and a.cfkudian like ?", "cfkudian","%%%s%%");
		p.put("and a.fuzeren like ?", "fuzeren","%%%s%%");
		p.put("and a.id = ?", "id");
		p.put("and a.xzqydm = ?", "quyu");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select a.*",
				"from njpt_yaokuguanli a LEFT JOIN "
				+ "njpt_diqu b ON a.xzqydm=b.xzqhdm "
				+ "LEFT JOIN fw_area c ON b.areaid=c.id where 1=1 " + s.getSql()+" ORDER BY c.orderno ASC", s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	/**
	 * 视频分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findShiping(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and c.kdmc like ?", "kdmc","%%%s%%");
		p.put("and b.qymc like ?", "qymc","%%%s%%");
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select a.*,b.qymc,c.kdmc",
				"from sp_ShiPing a join tz_qiye b on a.qyzzjgdm =b.qyzzjgdm join tz_kudian c on a.kdbm=c.kdbm where 1=1 and a.qyzzjgdm=c.qyzzjgdm" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	/**
	 * 用户分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findUserxinxi(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from fw_user where 1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}

	/**
	 * 分页获取三维
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querySanwei(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and b.xian = ?", "xian");
		p.put("and a.id = ?", "id");
		p.put("and b.qymc = ?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select a.id,a.url,a.qiye,a.kudianId,b.qymc,b.sheng,b.shi,b.xian,d.kdmc",
				"from njpt_sanwei_info a " +
						"left join tz_qiye b on a.qiye=b.qyzzjgdm " +
						"left join tz_kudian d on a.kudianId=d.id where 1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 应急小组的查询
	 */
	public static Page<Record> findxiaozu(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.fuzeren like ?", "fuzeren","%%%s%%");
		p.put("and a.chenyuan like ?", "chenyuan","%%%s%%");
		p.put("and a.danwei_id like ?", "danwei_id","%%%s%%");
		p.put("and a.id = ?", "id");
		p.put("and c.xzqhdm=?", "xzqhdm");
		p.put("and d.qyzzjgdm=?", "qyzzjgdm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select a.*,b.name,c.xzqhdm,d.qyzzjgdm ",
				"from njpt_yingjixiaozu a left join fw_dep b on a.danwei_id=b.id LEFT JOIN njpt_diqu c ON b.areaid=c.areaid LEFT JOIN tz_qiye d ON a.danwei_id=d.ID where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		return page;
	} 
	/**
	 * 邮件收件箱
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	
	public static Page<Record> findshoujian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.state = ?", "state");
		p.put("and a.title like ?", "title","%%%s%%");
		p.put("and b.realname like ?", "realname","%%%s%%");
		p.put("and a.sendtime >  ?" , "sendtime");
		p.put("and a.sendtime <  ?" , "endtime");
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname",
				"from oa_Mailinfo a left join fw_user b on a.fjrid = b.id  where a.sjrid = '"+UserKit.currentUserInfo().getUser().getString("id")+"' and a.hassend = '1' and a.isdel = '0'" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	/**
	 * 邮件发件箱
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findfajian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.title like ?", "title","%%%s%%");
		p.put("and b.realname like ?", "realname","%%%s%%");
		p.put("and a.sendtime >  ?" , "sendtime");
		p.put("and a.sendtime <  ?" , "endtime");
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname",
				"from oa_Mailinfyj"
				+ ""
				+ " a left join fw_user b on a.fjrid = b.id  where a.fjrid = '"+UserKit.currentUserInfo().getUser().getString("id")+"' and a.hassend = '1' and a.isdel = '0'" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	/**
	 * 邮件草稿箱
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findcaogao(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
 		String queryparam1=(String) queryparam.get("param");
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select a.*,b.realname",
				"from oa_Mailinfo a left join fw_user b on a.fjrid = b.id  where a.fjrid = '"+UserKit.currentUserInfo().getUser().getString("id")+"' and a.hassend = '0' and a.isdel = '0'"+queryparam1);
		return page;
	} 
	/**
	 * 邮件垃圾箱
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findlaji(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.title like ?", "title","%%%s%%");
		p.put("and b.realname like ?", "realname","%%%s%%");
		p.put("and a.sendtime >  ?" , "sendtime");
		p.put("and a.sendtime <  ?" , "endtime");
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname",
				"from oa_Mailinfo a left join fw_user b on a.fjrid = b.id  where a.sjrid = '"+UserKit.currentUserInfo().getUser().getString("id")+"' and a.isdel = '1'" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	/**
	 * t_property的查询
	 */
	public static Page<Record> findprop(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and propvalue like ?", "propvalue","%%%s%%");
		p.put("and propname like ?", "propname","%%%s%%");
		p.put("and id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from t_Property where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
		return page;
	} 
	
	/**
	 *企业名称下拉信息获取
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryKudian(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and name = ?", "name");
		p.put("and id = ?", "id");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.use("njpt").paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select * ",
				"from tz_kudian where 1=1 " + s.getSql() + " ORDER BY ID",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 企业备案分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiyebeian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and quyu = ?", "quyu");
		p.put("and qymc like ?", "qymc","%%%s%%");
		p.put("and beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"SELECT a.* ,a.id as jzid, b.qymc AS qymc",
				"from njpt_qiyebeian a LEFT JOIN tz_qiye b ON a.qyzzjgdm = b.qyzzjgdm "
				+ " where 1=1" + s.getSql(), 
				s.getParam().toArray(new Object[0]));
		return page;
	} 
	
	
	public static Page<Record> findqyba(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and quyu = ?", "quyu");
		p.put("and qyzzjgdm like ?", "qyzzjgdm","%%%s%%");
		p.put("and beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"SELECT *",
				"from njpt_qiyebeian where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 出入库分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> findChuruku(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and vDirection = ?", "vDirection");
		p.put("and vSwiftNumber = ?", "vSwiftNumber");
		p.put("and c.vGrainProperties like ?", "lsxz","%%%s%%");
		p.put("and c.vCode like ?", "vGraProCode","%s%%");
		p.put("and b.vCode like ?", "lslb","%s%%");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"SELECT a.* ,CONVERT(DateTime2(0),a.dtRegistrateTime) as bgtime,k.kdmc as kudianmc,q.qymc as qiyemc ,b.vName as lslb ,c.vGrainProperties as lsxz",
				"from crk_Purchase a LEFT JOIN  tz_kudian k  on a.qyzzjgdm =k.qyzzjgdm  and a.kdbm =k.kdbm left "
				+ "JOIN tz_qiye q on k.qyzzjgdm=q.qyzzjgdm LEFT JOIN tz_GrainType b ON a.vGrainSubTypeCode = b.vCode "
				+ "LEFT JOIN tz_GrainProperties c ON a.vGrainPropertyId = c.vCode where 1 =1" + s.getSql() +"order by bgtime desc", s.getParam()
						.toArray(new Object[0]));
		return page;
	} 

	/**
	 * 通风信息分页
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findTongfeng(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		if("稻谷".equals(queryparam.get("pz"))){
			queryparam.put("pz", "稻");
		}
		if("小麦".equals(queryparam.get("pz"))){
			queryparam.put("pz", "麦");
		}
		p.put("and a.lspz like ?", "pz","%%%s%%");
		p.put("and a.jlsj>= ?", "time");
		p.put("and a.jlsj <= ?", "endtime");
		p.put("and id = ?", "id");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"SELECT a.*,b.kdmc AS kdmc,c.qymc AS qymc",
				"FROM njpt_tongfengxinxi a " +
						"inner JOIN tz_qiye c ON a.qyzzjgdm = c.qyzzjgdm " +
						"inner JOIN tz_kudian b ON a.kdbm = b.kdbm AND a.qyzzjgdm = b.qyzzjgdm " +
						"  WHERE 1=1" + s.getSql()+ " ORDER BY jlsj desc ", s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 熏蒸信息分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findXunzheng(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		if("稻谷".equals(queryparam.get("pz"))){
			queryparam.put("pz", "稻");
		}
		if("小麦".equals(queryparam.get("pz"))){
			queryparam.put("pz", "麦");
		}
		p.put("and a.lspz like  ?", "pz","%%%s%%");
		p.put("and a.jlsj>= ?", "time");
		p.put("and a.jlsj <= ?", "endtime");
		p.put("and a.xzlx = ?", "xzlx");
		p.put("and a.id = ?", "id");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))),
				"SELECT a.* ",
				"FROM njpt_xunzhengxinxi a " +
						" WHERE 1=1" + s.getSql()+ " ORDER BY a.jlsj desc", s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 冷却信息分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findLengque(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		if("稻谷".equals(queryparam.get("pz"))){
			queryparam.put("pz", "稻");
		}
		if("小麦".equals(queryparam.get("pz"))){
			queryparam.put("pz", "麦");
		}
		p.put("and a.lspz like ?", "pz","%%%s%%");
		p.put("and a.jlsj>= ?", "time");
		p.put("and a.jlsj <= ?", "endtime");
		p.put("and a.id = ?", "id");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.kdbm = ?", "kdbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"SELECT a.* ",
				"FROM njpt_lengquexinxi a " +
						" WHERE 1=1" + s.getSql()+ " ORDER BY a.jlsj desc", s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 分页查询粮库作业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findlkzuoyexinxi(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and suozaiquyu like ?", "suozaiquyu","%%%s%%");
		p.put("and qyzzjgdm like ?", "qyzzjgdm","%%%s%%");
		p.put("and caozuorenyuan like ?", "caozuorenyuan","%%%s%%");
		p.put("and zuoyelx = ?", "zuoyelx");
		p.put("and id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from njpt_zuoyehuizong where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	
	/**
	 * 实时粮情分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findShisliangq(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and qyzzjgdm like ?", "qyzzjgdm","%%%s%%");
		p.put("and kdbm like ?", "kdbm","%%%s%%");
		p.put("and WareHouseCode like ?", "WareHouseCode","%%%s%%");
		p.put("and datetime = ?", "datetime");
		p.put("and id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from lq_WareHouseMeasure where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 粮食质量分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findLiangshizl(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and ssxq like ?", "ssxq","%%%s%%");
		p.put("and qymc like ?", "qymc","%%%s%%");
		p.put("and kudian like ?", "kudian","%%%s%%");
		p.put("and lsxz like ?", "lsxz","%%%s%%");
		p.put("and id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from njpt_liangshizhiliang where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 药剂追踪分页查询
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYaojizz(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and suoshuxiaqu like ? ", "suoshuxiaqu","%%%s%%");
		p.put("and suoshuqiye like ? ", "cansuoshuqiyeghao","%%%s%%");
		p.put("and kudianmc like ? ", "kudianmc","%%%s%%");
		p.put("and canghao like ? ", "canghao","%%%s%%");
		p.put("and id = ? ", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *","from njpt_yaojizhuizong where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 查询qiyebeianshenqing信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findbeianqiye(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qiyeid = ?", "qiyeid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from njpt_qiyebeiansq where  1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	} 
	
	/**
	 * 分页查询企业安全标准统计表
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findanquantj(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.id = ?","id");
		p.put("and b.qyzzjgdm = ? ", "qymc");
		p.put("and b.xzqhdm = ? ", "quyu");
		p.put("and e.djid = ? ", "psdj");
		p.put("and a.qiyeId = ? ", "qiyeId");
		p.put("and d.name = ? ", "name");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select a.*,b.qymc AS qymc,d.name as quyu,e.djmc AS psdjmc",
				"FROM njpt_anquanshengchan a " +
						"LEFT JOIN tz_qiye b ON a.qiyeId = b.ID " +
						"LEFT JOIN fw_dep c ON b.ID = c.id " +
						"LEFT JOIN fw_area d ON c.areaid = d.id " +
						"LEFT JOIN njpt_anquanshengchandj e ON a.psdj = e.djid where 1=1 " +s.getSql()+" ORDER BY d.orderno ASC",s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 安全生产等级
	 */
	public static Page<Record> queryanquandj(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from njpt_anquanshengchandj where 1=1 " +s.getSql()+" ORDER BY id ASC",s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 安全生产情况
	 */
	public static Page<Record> queryanquanqk(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from njpt_anquanscpdqk where 1=1 " +s.getSql()+" ORDER BY id ASC",s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 分页查询企业安全教育视频
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findzhisjijg(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		p.put("and wjsslb = ? ", "wjsslb");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *","from njpt_zhishijiegou where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询安全教育试题
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findjiaoyutm(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *","from njpt_jiaoyutiku where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询企业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiye(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.xian like ? ", "quyu","%%%s%%");
		p.put("and qymc like ? ", "qymc","%%%s%%");
		p.put("and kdmc like ? ", "kdmc","%%%s%%");
		p.put("and id = ? ", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"SELECT a.*,a.xian as quyu, b.kdmc AS kdmc,b.kdbm as kdbm ",
				"from tz_qiye a LEFT JOIN tz_kudian b ON a.qyzzjgdm = b.qyzzjgdm "
				+ "left join njpt_diqu as dq on a.xzqhdm = dq.xzqhdm "
				+ "left join fw_area as area on dq.areaid = area.id where 1=1 and b.kdlxbh<>'06' "+s.getSql() +
				" order by area.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询企业详情信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiyexq(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.xian = ? ", "quyu");
		p.put("and b.qymc = ? ", "qymc");
		p.put("and a.kdmc = ? ", "kdmc");
		p.put("and id = ? ", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"SELECT a.*,b.qymc as qymc,b.fddbr as fddbr,b.lxdh as qylxdh,b.xxdz as qyxxdz,p.count as count ",
				"from tz_kudian a LEFT JOIN tz_qiye b ON a.qyzzjgdm = b.qyzzjgdm  "
				+ "left join njpt_diqu as dq on a.xzqhdm = dq.xzqhdm "
				+ "left join fw_area as area on dq.areaid = area.id "
				+ "left join (select count(qyzzjgdm) as count,qyzzjgdm,kdbm  from crk_Purchase group by qyzzjgdm,kdbm) as p "
				+ "on p.qyzzjgdm = a.qyzzjgdm and p.kdbm = a.kdbm"
				+ "  where 1=1 and a.isksh = '1'and (CAST(a.isznh as FLOAT) + CAST(a.isscxt as FLOAT)) > '0'" + s.getSql() + " order by area.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询药剂库存
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYjkucun(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and a.pp like ? ", "pp","%%%s%%");
		p.put("and a.gg = ? ", "gg");
		p.put("and a.yjmc like ? ", "yjmc","%%%s%%");
		p.put("and a.pp = ? ", "pp");
		p.put("and a.yjmc = ? ", "yjmc");
		p.put("and a.id = ? ", "id");
		p.put("and a.kdbm = ?", "kdbm");
		p.put("and a.cj = ?", "cj");
		p.put("and a.xzqydm = ?", "xzqydm");
		p.put("and a.danwei = ?", "danwei");
		p.put("and a.clsj > ?", "qssj");
		p.put("and a.clsj < ?", "jssj");
		p.put("and c.name = ?", "xian");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.* ", 
				"from njpt_yaojikucunguanli as a left join njpt_diqu as b on "
				+ "a.xzqydm = b.xzqhdm left join fw_area as c on b.areaid = c.id "
				+ "where a.kcsl > 0 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询药剂台账
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYjtaiz(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and pp like ? ", "pp","%%%s%%");
		p.put("and yjmc like ? ", "yjmc","%%%s%%");
		p.put("and lqr like ? ", "lqr","%%%s%%");
		p.put("and id = ? ", "id");
		p.put("and kdbm = ?", "kdbm");
		p.put("and xzqydm = ?", "xzqydm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_yjtaizhang where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询药剂出库
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYjck(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and pp = ? ", "pp");
		p.put("and yjmc = ? ", "yjmc");
		p.put("and a.id = ? ", "id");
		p.put("and lyrq >= ?", "qssj");
		p.put("and lyrq <= ?", "jssj");
		p.put("and a.xzqydm=?", "xzqhdm");
		p.put("and kdbm=?", "cfkudian");
		p.put("and b.qyzzjgdm=?", "qyzzjgdm");
		p.put("and lqr like ?","fuzeren" ,"%%%s%%");
		p.put("and a.lydw = ? ", "qyid");
		p.put("and a.xzbah like ? ", "xzbah","%%%s%%");
		p.put("and b.ID=?", "ID");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.* ,b.qymc", 
				"from njpt_yaojichuku a LEFT JOIN tz_qiye b on a.lydw=b.ID where 1=1  "+s.getSql()+" order by a.lyrq",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询药剂入库
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYjrk(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and gmdw = ? ", "gmdw");
		p.put("and yjmc = ? ", "yjmc");
		p.put("and id = ? ", "id");
		p.put("and rksj > ?", "qssj");
		p.put("and rksj < ?", "jssj");
		p.put("and xzqydm=?", "shiqu");
		p.put("and kdbm=?", "cfkudian");
		p.put("and gly like ?","fuzeren" ,"%%%s%%");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_yaojiruku where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询药剂损耗
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYjsh(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and yjpp = ? ", "yjpp");
		p.put("and yjmc like ? ", "yjmc","%%%s%%");
		p.put("and cyry like ? ", "fuzeren","%%%s%%");
		p.put("and id = ? ", "id");
		p.put("and ykmc = ? ", "ykmc");
		p.put("and dealday >= ? ", "dealtime");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_yaojish where state=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询仓储设备
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findCcsb(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		p.put("and qyzzjgdm = ? ", "qyzzjgdm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_cangchushebei where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询专职保管
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findZzbg(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		p.put("and qyzzjgdm = ? ", "qyzzjgdm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_zhuanzhibaoguian where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询库区情况
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findKqqk(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		p.put("and qyzzjgdm = ? ", "qyzzjgdm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_kuquqingkuang where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询企业简介
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findqiyejianjie(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ? ", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_qiyejianjie where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 查询地区
	 * @return
	 */
	/*public List<Record> finddiqu() {
		String sql = "select quyu,xzqhdm from njpt_diqu order by orderno";
		List<Record> list = Db.find(sql);
		return list;
	}*/
	
	/**
	 * 通过xzqhdm查库点
	 * @param xzqhdm
	 * @return
	 */
	public List<Record> findkudian(String xzqhdm){
		List<Record> list = Db.find("select * from tz_kudian where kdlxbh<>'06' and haveshipin='1' and xzqhdm = ? order by ID ",xzqhdm);
		return list;
	}
	
	
	/**
	 * 查找视频信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findShipin(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and orgcode = ? ", "kdbm");
		p.put("and entcode = ? ", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from nvr where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	/**
	 * 查找放心粮油信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findFangxinly(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.ID = ? ", "id");
		p.put("and a.xzqhdm = ? ", "quyu");
		p.put("and a.kdmc = ? ", "kdmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.* ", 
				"from tz_kudian a LEFT JOIN fw_area b ON a.xian=b.name WHERE 1=1 and a.kdlxbh='06' "+s.getSql()+"ORDER BY b.orderno ASC,a.ID DESC ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	/**
	 * 查找应急供应点信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findYingjidian(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.ID = ? ", "id");
		p.put("and a.xzqhdm = ? ", "quyu");
		p.put("and a.kdmc = ? ", "kdmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.* ", 
				"from tz_kudian a LEFT JOIN fw_area b ON a.xian=b.name WHERE 1=1 and a.kdlxbh='05' "+s.getSql()+"ORDER BY b.orderno ASC,a.ID DESC ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}


	/**
	 * 查找应急加工点信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findYingjijiagongdian(HashMap<String, Object> queryparam) throws Exception{

		Param p = new Param();
		p.put("and a.ID = ? ", "id");
		p.put("and a.xzqhdm = ? ", "quyu");
		p.put("and a.kdmc = ? ", "kdmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select a.* ",
				"from tz_kudian a LEFT JOIN fw_area b ON a.xian=b.name WHERE 1=1 and a.kdlxbh='04' "+s.getSql()+"ORDER BY b.orderno ASC,a.ID DESC ",
				s.getParam().toArray(new Object[0]));
		return page;

	}
	
	
	/**
	 * 查找视频信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findfangfacaozuo(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.caozuotime = ? ", "caozuotime");
		p.put("and a.userid = ? ", "userid");
		p.put("and a.caidanname = ? ", "caidanname");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.*,b.realname ", 
				"from fw_caozuo a LEFT JOIN fw_user b ON a.userid=b.id where 1=1 "+s.getSql()+" order by a.caozuotime desc",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 * 查找首页使用频率
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findindexweihu(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.id =?", "id");
		p.put("and a.code =?", "code");
		p.put("and a.userid =?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.*,b.name,c.realname  ", 
				"from fw_rate_menu a LEFT JOIN fw_rate b on a.code=b.code LEFT JOIN fw_user c ON a.userid=c.id where 1=1 "+s.getSql()+" ORDER BY b.orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	/**
	 * 查找页面方法的使用
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findcaidanfangfaweihu(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and id =?", "id");
		p.put("and code =?", "code");
		p.put("and userid =?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from fw_caidan_fangfa where 1=1 "+s.getSql()+" ORDER by code ",
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	/**
	 * 获取区县信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> queryDiqu(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and quyu = ? ", "quyu");
		p.put("and xzqhdm = ? ", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_diqu where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取企业性质信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> queryQiyeXZ(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and name = ? ", "name");
		p.put("and code = ? ", "code");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_qyxz where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	
	/**
	 * 获取企业经营类型
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> queryJYLX(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and jyywlxmc = ? ", "name");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ", 
				"from njpt_qyjyywlx where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取企业经营类型
	 * @author yzz
	 * @throws Exception 
	 */
	public static Record findjylx(HashMap<String, Object> param2)  {
		
		String mc =  (String) param2.get("jyywlxbh");
		
		String sql ="select * "
				+ "from njpt_qyjyywlx where 1=1 "
				+ "and jyywlxbh = ? ";
		
		Record record = Db.findFirst(sql,mc);
		return record;	
	}


	/**
	 * 获取库点类型
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> queryKdlx(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and kdlxbh = ? ", "kdlxbh");
		p.put("and kdlxmc = ? ", "kdlxmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from njpt_kdlx where 1=1 "+s.getSql()+" order by orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 获取仓房主要业务
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryZYYW(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and BH = ? ", "BH");
		p.put("and MC = ? ", "MC");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from dm_zyyw where 1=1 "+s.getSql()+" order by BH",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取仓房主要业务
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findUsername(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and id = ? ", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from fw_user where 1=1 "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取仓廒类型
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryCALX(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and BH = ? ", "BH");
		p.put("and MC = ? ", "MC");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from dm_cflx where 1=1 "+s.getSql()+" order by BH",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取仓廒状态名称
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryCAZTMC(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and BH = ? ", "BH");
		p.put("and MC = ? ", "MC");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from dm_cfzt where 1=1 "+s.getSql()+" order by BH",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取仓廒使用情况
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryCASYQK(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and BH = ? ", "BH");
		p.put("and MC = ? ", "MC");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from dm_cfsyqk where 1=1 "+s.getSql()+" order by BH",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取仓廒结构名称
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryCAJGMC(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and BH = ? ", "BH");
		p.put("and MC = ? ", "MC");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select * ", 
				"from dm_cfjg where 1=1 "+s.getSql()+" order by BH",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取备案号
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> querybeian(HashMap<String, Object> queryParam) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and a.qiyeid = ?", "qiyeid");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryParam.get("page"))), 
				Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
				"select a.* ", 
				"from oa_xunzhengbeian a LEFT JOIN njpt_yaojichuku b on a.beianbianhao=b.xzbah where 1=1 and b.status='0' "+s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * @param queryparam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findkongping(HashMap<String, Object> queryParam) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qiye = ?", "qiye");
		p.put("and xzbabh = ?", "xzbabh");
		p.put("and kudian = ?", "kudian");
		p.put("and yjmc = ?", "yjmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select * ", 
			"from njpt_yaojikongping where 1=1 "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取熏蒸备案企业
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static List<Record> querybaqy(HashMap<String, Object> queryParam) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select min(qyzzjgdm) as qyzzjgdm, min(qy.qymc) as qymc, xz.qiyeid as qiyeid "
				+"from oa_xunzhengbeian as xz left join tz_qiye "
				+ "as qy on xz.qiyeid = qy.id group by xz.qiyeid";
		List<Record> page = Db.find(sql);
		return page;
	}
	
	/**
	 * 获取熏蒸库点
	 * @param queryParam
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryxzkd(HashMap<String, Object> queryParam) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and xz.beianbianhao = ?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select kd.kdmc as kdmc, kd.kdbm as kdbm ", 
			"from oa_xunzhengbeian as xz left join "
			+ "tz_qiye as qy on xz.qiyeid = qy.id "
			+ "left join tz_kudian as kd on "
			+ "qy.qyzzjgdm = kd.qyzzjgdm and kd.kdbm = xz.kudian where 1=1 "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂库存
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findyjkc(HashMap<String, Object> queryParam) throws Exception {
		Param p = new Param();
		p.put("and area.name = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select yj.*,area.orderno as orderno ", 
			"from (select xzqydm,sum(gg * kcsl) as weight, min(kdbm) as kdbm"
			+ " from njpt_yaojikucunguanli group by xzqydm) as yj left join "
			+ "njpt_diqu as dq on yj.xzqydm = dq.xzqhdm "
			+ "left join fw_area as area on "
			+ "area.id = dq.areaid where 1=1 "+s.getSql() + " order by area.orderno",
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
	 * 获取药剂库存
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findYjckxinxi(HashMap<String, Object> queryParam) throws Exception {
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
	
	public static Page<Record> findxzzy(HashMap<String, Object> queryParam) {
		Param p = new Param();
		p.put("and xz.xzqhdm = ?", "xzqhdm");
		p.put("and xz.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and xz.beianbianhao = ?", "beianbianhao");
		p.put("and xz.xzid = ?", "xzid");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select * ", 
			"from (select xzid, min(xzqhdm) as xzqhdm, min(kdbm) as kdbm, min(fuzeren) as fuzeren,"
			+ "min(canyuren) as canyuren, min(xunzhengriqi) as xunzhengriqi, min(qyzzjgdm) as qyzzjgdm, "
			+ "sum(yjsl) as yjsl, sum(lssl) as lssl, min(beianbianhao) as beianbianhao "
			+ "from oa_xunzhengzuoye group by xzid) as xz "
			+ "where 1=1 "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取熏蒸作业明细
	 * @param queryparam
	 * @return
	 */
	public static Page<Record> findxzzymx(HashMap<String, Object> queryParam) {
		Param p = new Param();
		p.put("and xzqhdm = ?", "xzqhdm");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and beianbianhao = ?", "beianbianhao");
		p.put("and xzid = ?", "xzid");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select * ", 
			"from oa_xunzhengzuoye  where 1=1 "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 获取熏蒸仓号
	 * @param queryParam
	 * @return
	 */
	public static Page<Record> selectXzch(HashMap<String, Object> queryParam) {
		Param p = new Param();
		p.put("and a.kdbm = ?", "kdbm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.cfmcyj = ?", "cfmcyj");
		p.put("and a.beianbianhao = ?", "beianbianhao");
		p.put("and a.yjmc = ?", "yjmc");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select a.* ", 
			"from xz_yaojixinxi a LEFT JOIN njpt_yaojichuku b on a.beianbianhao=b.xzbah where 1=1 AND b.status='0' "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取药剂审批数量
	 * @param param
	 * @return
	 */
	public static Page<Record> findxjsp(HashMap<String, Object> queryParam) {
		Param p = new Param();
		p.put("and c.name = ?", "xian");
		p.put("and a.lydw = ?", "lydw");
		p.put("and a.status = ?", "status");
		SqlAndParam s = SqlUtil.buildSql(p, queryParam);
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(queryParam.get("page"))), 
			Integer.valueOf(String.valueOf(queryParam.get("rows"))), 
			"select a.* ", 
			"from njpt_yaojichuku a LEFT JOIN njpt_diqu b on a.xzqydm=b.xzqhdm "
			+ "left join fw_area c on b.areaid = c.id where 1=1 "+s.getSql(),
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
}
