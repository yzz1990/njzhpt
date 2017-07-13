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

public class JibenxinxiDao {
	
	/**
	 * 查询首页菜单使用频率
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Ret findFate(HashMap<String, Object> queryparam) throws Exception{
		Ret ret=new Ret();
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("page", 1);	
		map.put("rows", 10000);	
		map.put("userid", UserKit.currentUserInfo().getUser().getString("id"));	
		Page<Record>  records=JibenxinxiDao.getFate(map);
		boolean bool=false;
		if(records.getList().size()==0){
			map.remove("userid");
			Page<Record>  recordList=JibenxinxiDao.findNoFate(map);
			for(Record record:recordList.getList()){
				Record  rate=new Record();
				rate.set("id", SqlUtil.uuid());
				rate.set("userid", UserKit.currentUserInfo().getUser().getString("id"));
				rate.set("code", record.getStr("code"));
				bool=Db.save("fw_rate_menu", rate);	
			}
		}
		ret.put("ret", bool);
		return ret;
	}
	
	
	public static Page<Record> getFate(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.code = ?", "code");
		p.put("and a.name = ?", "name");
		p.put("and b.userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.*,b.userid as userid,b.rate as rate ",
				"from fw_rate a LEFT JOIN fw_rate_menu b on a.code=b.code  where 1=1  " + s.getSql() + " ORDER BY b.rate DESC,a.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
		public static Page<Record> getFate1(HashMap<String, Object> queryparam) throws Exception{
		
		Param p = new Param();
		p.put("and a.code = ?", "code");
		p.put("and a.name = ?", "name");
		p.put("and b.userid = ?", "userid");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select a.*,bb.id as amsid,b.rate ",
				"from fw_rate a INNER JOIN fw_rate_menu b on a.code=b.code  " 
				+"left join (select e.id from  fw_ams e INNER JOIN fw_role_ams f on e.id=f.amsid LEFT JOIN fw_role j on f.roleid=j.id where e.parentid='36c3b611a19f496c927e76a4570e1061' and j.code='"+UserKit.currentUserInfo().getRole().getString("code")+"')  bb on a.id=bb.id "
				+" WHERE 1=1 " + s.getSql() + " ORDER BY b.rate desc,a.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
		
	}
	
	
	
	/**
	 * 查询首页菜单使用频率
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findNoFate(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and code = ?", "code");
		p.put("and name = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select * ",
				"from fw_rate   where 1=1  " + s.getSql() + " ORDER BY orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询企业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiyexinxi(HashMap<String, Object> queryparam) throws Exception{
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
				"select a.qyzzjgdm,a.jyywlxbh,a.xzqhdm,a.ID,a.sheng,a.shi,a.xian,a.xxdz,a.qyxzmc,a.jyywlxmc,a.fddbr,a.status,a.qymc,c.loginname as loginname,c.password as password, e.orderno as orderno",
				"from tz_qiye a left JOIN tz_kudian f ON a.qyzzjgdm=f.qyzzjgdm left JOIN fw_user c on c.id=a.ID left JOIN tz_cangfang b on a.qyzzjgdm=b.qyzzjgdm and f.kdbm=b.kdbm LEFT JOIN njpt_diqu d on a.xzqhdm =d.xzqhdm LEFT JOIN fw_area e on e.id=d.areaid  where 1=1  " + s.getSql() + " GROUP BY a.qymc,c.loginname,c.password,e.orderno,a.sheng,a.shi,a.xian,a.xxdz,a.qyxzmc,a.jyywlxmc,a.fddbr,a.status,a.ID,a.qyzzjgdm ,a.xzqhdm,a.jyywlxbh,a.qyzzjgdm ORDER BY e.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 分页查询企业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findQiyexinxibf(HashMap<String, Object> queryparam) throws Exception{
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
				"select a.*,c.loginname as loginname,c.password as password, convert(varchar(10),a.zhuceshijian ,120) as zcsj ",
				"from tz_qiye a  inner JOIN fw_user c on c.id=a.ID LEFT JOIN njpt_diqu d on a.xzqhdm =d.xzqhdm LEFT JOIN fw_area e on e.id=d.areaid where 1=1  " + s.getSql() + " ORDER BY e.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询地区信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findDiqu(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and b.id = ?", "id");
		p.put("and b.areaid = ?", "areaid");
		p.put("and a.name = ?", "name");
		p.put("and b.xzqhdm = ?", "xzqhdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select b.*,a.name",
				" from fw_area a LEFT JOIN njpt_diqu b on a.id=b.areaid where a.parentid!='root' " + s.getSql() + " ORDER BY a.orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询地区年度信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findDiquniandu(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and quyu = ?", "quyu");
		p.put("and niandu = ?", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select *",
				"from njpt_diquniandu where 1=1" + s.getSql() + "order by niandu desc, quyu desc", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 排序查询区县年度
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> findDiquniandupx(HashMap<String, Object> queryparam) throws Exception {
		// TODO Auto-generated method stub
		Param p = new Param();
		p.put("and nd.id = ?", "id");
		p.put("and nd.quyu = ?", "quyu");
		p.put("and nd.niandu = ?", "niandu");
		p.put("and area.name = ?", "name");
		SqlAndParam s = SqlUtil.buildSql(p,queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select nd.*",
				"from njpt_diquniandu as nd left join "
				+ "njpt_diqu as dq on nd.xzqhdm = dq.xzqhdm left join fw_area as area "
				+ "on dq.areaid = area.id  where 1=1" + s.getSql() + "order by nd.niandu desc, area.orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 分页查询库点信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findKudian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and t.ID = ?", "ID");
		p.put("and t.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and t.kdbm = ?", "kdbm");
		p.put("and t.kdlxbh = ?", "kdlxbh");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select t.* ",
				"from tz_kudian t " 
				+"left join njpt_diqu t1 on t.xzqhdm=t1.xzqhdm "
				+"left join (SELECT qyzzjgdm,kdbm,sum(convert(decimal(18,3),sjcr)) sjcr from tz_cangfang where cfztbh='01' group by qyzzjgdm,kdbm)t0 on t.qyzzjgdm=t0.qyzzjgdm and t.kdbm=t0.kdbm  "
				+"left join (SELECT kc.qyzzjgdm,kc.kdbm,sum(kc.dmStock) dmStock from kc_CurrentStock kc LEFT JOIN tz_cangfang cf on kc.qyzzjgdm=cf.qyzzjgdm and kc.kdbm=cf.kdbm and kc.vWareHouseCode=cf.cfbm  "
				+"where cf.cfztbh='01' group by kc.qyzzjgdm,kc.kdbm)t3 on t.qyzzjgdm=t3.qyzzjgdm and t.kdbm=t3.kdbm "
				+"where t.kdmc like '%%' and t.xzqhdm in (210008,320111,320115,320116,320124,320125) and t.kdlxbh in (01,02,03,04,05,06) "
				+ s.getSql()+" order by t1.orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 查询地区
	 * @return
	 */
	public List<Record> finddiqu() {
		String sql = "select quyu from njpt_diqu order by orderno";
		List<Record> list = Db.find(sql);
		return list;
	}
	
	/**
	 * 分页查询仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findCangfang(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select *",
				"from tz_cangfang where 1=1 " + s.getSql()+ "ORDER BY cfbm", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 分页查询仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findtianqi(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select wendu,tq,tianqitupian",
				"FROM tianqi where 1=1 " + s.getSql()+ "ORDER BY sj DESC", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询廒间
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findAojian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and ID = ?", "ID");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select *",
				"from tz_aojian where 1=1" + s.getSql() + "ORDER BY cfbm , ajbh", 
				s.getParam().toArray(new Object[0]));
		return page;
	}

	
	/**
	 * 获取用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findUserInfo(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.loginname = ?", "loginname");
		p.put("and b.depid = ?", "depid");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select a.* ",
				" from  fw_user a inner JOIN fw_user_dep b on a.id=b.userid where 1=1 " + s.getSql(), 
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Ret setRateInfo(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		return null;
	}


	

	/**
	 * 获取应急事件
	 * @throws Exception 
	 */
	public static Page<Record> queryyjsj(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and ID = ?", "id");
		p.put("and NAME like ?", "name","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select * ",
				" from njpt_emergency_event where 1=1 " + s.getSql(), 
				s.getParam().toArray(new Object[0]));
		return page;
	}


	public static Page<Record> findfeikshkdcrk(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and vSwiftNumber = ?", "vSwiftNumber");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and vWareHouseCode = ?", "vWareHouseCode");
		
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select * ",
				"from crk_Purchase where 1=1 " + s.getSql()+ "ORDER BY vSwiftNumber", 
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取可视化库点
	 * @param param
	 * @return
	 */
	public static Page<Record> getKdKsh(HashMap<String, Object> param) {
		Param p = new Param();	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select kdmc,qyzzjgdm,kdbm ",
				"from tz_kudian as kd left join fw_area as area on kd.xian = area.name where isksh=1 order by area.orderno " + s.getSql(), 
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取办公人员通讯录
	 * @param param
	 * @return
	 */
	public static Page<Record> getTxBg(HashMap<String, Object> param) {
		Param p = new Param();	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select ID,shangji,name,isend,orderno ",
				"from oa_treelist where leixing='txl' " + s.getSql() + "order by orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取通讯录人员明细
	 * @param param
	 * @return
	 */
	public static Page<Record> getBgRy(HashMap<String, Object> param) {
		Param p = new Param();	
		p.put("and tl.shangji = ?", "bmid");
		p.put("and tl.id = ?", "ryid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select tx.* ",
				"from oa_tongxun as tx left join oa_treelist as tl "
				+ "on tx.id = tl.id where leixing='txl' " + s.getSql() + "order by tl.orderno", 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取应急人员通讯录
	 * @param param
	 * @return
	 */
	public static Page<Record> getTxyj(HashMap<String, Object> param) {
		Param p = new Param();	
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select ID,shangji,name,isend,orderno ",
				"from oa_treelist where leixing='yj' " + s.getSql() + "order by orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 获取应急人员明细
	 * @param param
	 * @return
	 */
	public static Page<Record> getyjRy(HashMap<String, Object> param) {
		Param p = new Param();	
		p.put("and tl.shangji = ?", "bmid");
		p.put("and tl.id = ?", "ryid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(param.get("page"))), Integer.valueOf(String
				.valueOf(param.get("rows"))), 
				"select tx.* ",
				"from oa_tongxunyj as tx left join oa_treelist as tl "
				+ "on tx.id = tl.id where leixing='yj' " + s.getSql() +"order by tl.orderno ", 
				s.getParam().toArray(new Object[0]));
		return page;
	}	
	


	/**
	 * 获取仓房自有总仓容--统一方法
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> queryCfZycr(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select  sum(CAST(c.sjcr as FLOAT)) as sjcr,sum(CAST(c.shjcr as FLOAT)) as zcr  ",
						"from tz_qiye a  " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm "
						+ "and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'  and b.isksh='1'and c.cfztbh='01' "
						+ s.getSql(),
						s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取综合分析仓房总仓容--统一方法
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> queryZhCfZcr(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select sum(CAST(c.shjcr as FLOAT)) as zcr  ",
						"from tz_qiye a  " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm "
						+ "and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04'  "
						+ s.getSql(),
						s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 获取仓房总仓容--统一方法
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> queryCfZcr(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
						Integer.valueOf(String.valueOf(param.get("page"))),
						Integer.valueOf(String.valueOf(param.get("rows"))),
						"select sum(CAST(c.shjcr as FLOAT)) as zcr  ",
						"from tz_qiye a  " 
						+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
						+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm "
						+ "and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "
						+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01'"
						+ s.getSql(),
						s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 获取仓房总库存-统一方法
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> queryCfZkc(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and d.vGrainSubTypeCode like ?", "pzcode" , "%s%%");
		p.put("and a.xian = ?", "xian");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  sum(CAST(d.dmStock  as FLOAT))/1000 as dmStock  ",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取已对接进系统的企业（仓房总库存）-统一方法
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> duijiezcr(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and a.qymc = ?", "qymc");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and d.vGrainSubTypeCode like ?", "pzcode" , "%s%%");
		p.put("and a.xian = ?", "xian");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  sum(CAST(c.shjcr as FLOAT)) as duijiezcr  ",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN  kc_CurrentStock d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode"
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 获取仓房收购量
	 * @param param
	 * @return
	 */
	public static Page<Record> findzongrk(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.kdbm = ?", "kdbm");
		p.put("and b.kdmc = ?", "kdmc");
		p.put("and a.qymc = ?", "qymc");
		p.put("and c.cfbm = ?", "cfbm");
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and a.xian = ?", "xian");
		p.put("and d.vGrainSubTypeCode not like ?", "djpz", "%s%%");
		p.put("and (CAST(b.isznh as FLOAT) + CAST(b.isscxt as FLOAT)) >= ?", "isksh");
		p.put("and d.dtRegistrateTime >= ? ", "starttime");
		p.put("and d.dtRegistrateTime <= ? ", "endtime");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page=Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  sum(CAST(d.dmNW  as FLOAT))/1000 as sumNW  ",
				"from tz_qiye a " 
				+" inner join tz_kudian b on  a.qyzzjgdm=b.qyzzjgdm "
				+" inner join tz_cangfang c on  a.qyzzjgdm=c.qyzzjgdm and b.qyzzjgdm=c.qyzzjgdm and b.kdbm=c.kdbm "		
				+" INNER JOIN crk_Purchase d on a.qyzzjgdm=d.qyzzjgdm and b.kdbm=d.kdbm and c.cfbm=d.vWareHouseCode "
				+" where b.kdlxbh<>'06' and b.kdlxbh<>'05' and b.kdlxbh<>'04' and b.isksh='1' and c.cfztbh='01' "
				+ "and d.vDirection='入库' "
				+ s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}


	/**
	 * 分页查询仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findExchange(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and cfbm = ?", "cfbm");
		p.put("and ajbh = ?", "ajbh");
		p.put("and cfbm2 = ?", "cfbm2");
		
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("exchange").paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), 
				"select * ",
				"from exchange where 1=1 " + s.getSql(), 
				s.getParam().toArray(new Object[0]));
		return page;
	}
	


	
}
