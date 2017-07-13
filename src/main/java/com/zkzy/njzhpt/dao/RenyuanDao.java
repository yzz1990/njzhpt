package com.zkzy.njzhpt.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;

public class RenyuanDao {

	
	
	/**
	 * 保存熏蒸备案
	 * @author yzz
	 * @throws Exception 
	 */

	public static boolean addXzBa(HashMap<String, Object> param) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Record record=new Record();
		//从业人员
		Record record1=new Record();
		//熏蒸对象
		Record record2=new Record();
		//药剂信息
		Record record3=new Record();
		param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
		boolean bool=false;
		boolean bool1=false;
		boolean bool2=false;
		boolean bool3=false;
		if(param.get("id").equals("")){
			record.set("status", 1).set("beianriqi", sdf.format(new Date())).set("id", SqlUtil.uuid())
			.set("beianbianhao", param.get("beianbianhao")).set("qiyeid", param.get("qiyeid")).set("liuchenghao", param.get("liuchenghao"))
			.set("kudian", param.get("kudian")).set("fuzeren", param.get("fuzeren")).set("sqrq", param.get("sqrq"))
			.set("remark", param.get("remark")).set("yingjiyuan", param.get("yingjiyuan")).set("fujianpath", param.get("fujianpath"))
			.set("fujianname", param.get("fujianname")).set("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
			//人员信息
			String xm=(String) param.get("xingming");
			String [] xinm=xm.split(",");
			String zz=(String) param.get("zz");
			String [] zza=zz.split(",");
			for(int i=0;i<xinm.length;i++){
				record1.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("xingming", xinm[i]).set("zz", zza[i]);
				bool1=Db.save("xz_zuoyerenyuan", record1);
				record1.clear();
			}
			
			//熏蒸对象
			String cfmc=(String) param.get("cfmc");
			String[] cf=cfmc.split(",");
			String lspz=(String) param.get("lspz");
			String[] pz=lspz.split(",");
			String lssl=(String) param.get("lssl");
			String[] sl=lssl.split(",");
			String jhyyl=(String) param.get("jhyyl");
			String[] yyl=jhyyl.split(",");
			for(int i=0;i<cf.length;i++){
				record2.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("cfmc", cf[i]).set("lspz", pz[i]).set("lssl", sl[i]).set("jhyyl", yyl[i]);
				bool2=Db.save("xz_xunzhendx", record2);
				record2.clear();
			}
			//药剂信息
			String cfmcyj=(String) param.get("cfmcyj");
			String[] cfyj=cfmcyj.split(",");
			String yjmc=(String) param.get("yjmc");
			String[] yj=yjmc.split(",");
			String pp=(String) param.get("pp");
			String[] ppmc=pp.split(",");
			String gg=(String) param.get("gg");
			String[] ggmc=gg.split(",");
			String dw=(String) param.get("dw");
			String[] dwmc=dw.split(",");
			String sccj=(String) param.get("sccj");
			String[] sccjmc=sccj.split(",");
			String lysl=(String) param.get("lysl");
			String[] lyslmc=lysl.split(",");
			String syl=(String) param.get("syl");
			String[] sylmc=syl.split(",");
			String fangshi=(String) param.get("fangshi");
			String[] fsmc=fangshi.split(",");
			for(int i=0;i<cfyj.length;i++){
				record3.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("cfmcyj", cfyj[i]).set("yjmc", yj[i]).set("pp", ppmc[i]).set("gg", ggmc[i])
				.set("dw", dwmc[i]).set("sccj", sccjmc[i]).set("lysl", lyslmc[i]).set("syl", sylmc[i])
				.set("fangshi",fsmc[i]);
				bool3=Db.save("xz_yaojixinxi", record3);
				record3.clear();
			}
			bool=Db.save("oa_xunzhengbeian", record)&&bool1&&bool2&&bool3;
			
		}else{
			if(!param.get("shenpi").equals("")){
				record.clear();
				record.set("status", param.get("status")).set("beianriqi", sdf.format(new Date())).set("id", param.get("id"))
				.set("beianbianhao", param.get("beianbianhao")).set("qiyeid", param.get("qiyeid")).set("liuchenghao", param.get("liuchenghao"))
				.set("kudian", param.get("kudian")).set("fuzeren", param.get("fuzeren")).set("sqrq", param.get("sqrq"))
				.set("remark", param.get("remark")).set("yingjiyuan", param.get("yingjiyuan")).set("fujianpath", param.get("fujianpath"))
				.set("fujianname", param.get("fujianname")).set("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
			}else{
				record.clear();
				record.set("status", 1).set("beianriqi", sdf.format(new Date())).set("id", param.get("id"))
				.set("beianbianhao", param.get("beianbianhao")).set("qiyeid", param.get("qiyeid")).set("liuchenghao", param.get("liuchenghao"))
				.set("kudian", param.get("kudian")).set("fuzeren", param.get("fuzeren")).set("sqrq", param.get("sqrq"))
				.set("remark", param.get("remark")).set("yingjiyuan", param.get("yingjiyuan")).set("fujianpath", param.get("fujianpath"))
				.set("fujianname", param.get("fujianname")).set("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
			}
			
			
			//人员信息
			String xm=(String) param.get("xingming");
			String [] xinm=xm.split(",");
			String zz=(String) param.get("zz");
			String [] zza=zz.split(",");
			Db.update("DELETE from xz_zuoyerenyuan WHERE 1=1 and beianbianhao='"+param.get("beianbianhao")+"'");
			for(int i=0;i<xinm.length;i++){
				record1.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("xingming", xinm[i]).set("zz", zza[i]);
				bool1=Db.save("xz_zuoyerenyuan", record1);
				record1.clear();
			}
			
			//熏蒸对象
			String cfmc=(String) param.get("cfmc");
			String[] cf=cfmc.split(",");
			String lspz=(String) param.get("lspz");
			String[] pz=lspz.split(",");
			String lssl=(String) param.get("lssl");
			String[] sl=lssl.split(",");
			String jhyyl=(String) param.get("jhyyl");
			String[] yyl=jhyyl.split(",");
			
			Db.update("DELETE from xz_xunzhendx WHERE 1=1 and beianbianhao='"+param.get("beianbianhao")+"'");
			for(int i=0;i<cf.length;i++){
				record2.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("cfmc", cf[i]).set("lspz", pz[i]).set("lssl", sl[i]).set("jhyyl", yyl[i]);
				bool2=Db.save("xz_xunzhendx", record2);
				record2.clear();
			}
			//药剂信息
			String cfmcyj=(String) param.get("cfmcyj");
			String[] cfyj=cfmcyj.split(",");
			String yjmc=(String) param.get("yjmc");
			String[] yj=yjmc.split(",");
			String pp=(String) param.get("pp");
			String[] ppmc=pp.split(",");
			String gg=(String) param.get("gg");
			String[] ggmc=gg.split(",");
			String dw=(String) param.get("dw");
			String[] dwmc=dw.split(",");
			String sccj=(String) param.get("sccj");
			String[] sccjmc=sccj.split(",");
			String lysl=(String) param.get("lysl");
			String[] lyslmc=lysl.split(",");
			String syl=(String) param.get("syl");
			String[] sylmc=syl.split(",");
			String fangshi=(String) param.get("fangshi");
			String[] fsmc=fangshi.split(",");
			Db.update("DELETE from xz_yaojixinxi WHERE 1=1 and beianbianhao='"+param.get("beianbianhao")+"'");
			for(int i=0;i<cfyj.length;i++){
				record3.set("beianbianhao", param.get("beianbianhao")).set("qyzzjgdm", param.get("qyzzjgdm")).set("kdbm", param.get("kudian"))
				.set("cfmcyj", cfyj[i]).set("yjmc", yj[i]).set("pp", ppmc[i]).set("gg", ggmc[i])
				.set("dw", dwmc[i]).set("sccj", sccjmc[i]).set("lysl", lyslmc[i]).set("syl", sylmc[i])
				.set("fangshi",fsmc[i]);
				bool3=Db.save("xz_yaojixinxi", record3);
				record3.clear();
			}
			bool=Db.update("oa_xunzhengbeian",record)&&bool1&&bool2&&bool3;
			
		}		
		return bool;		
	}
	
	/**
	 * 保存药剂审批信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static boolean addYjshenpixinxi(HashMap<String, Object> param) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Record record=new Record();
		param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
		boolean bool=false;
		
			record.setColumns(param).set("shenpishijian", sdf.format(new Date())).set("id", SqlUtil.uuid());
			bool=Db.save("oa_yaojishenpirenxinxi", record);
		
		/*else{
			record.setColumns(param);
			bool=Db.update("oa_yaojishenpirenxinxi",record);
		}	*/	
		return bool;		
	}
	/**
	 * 保存药剂备案
	 * @author yzz
	 * @throws Exception 
	 */

	public static boolean addYjBa(HashMap<String, Object> param) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Record record=new Record();
		param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
		boolean bool=false;
		if(param.get("id").equals("")){
			record.setColumns(param).set("status", 1).set("beianriqi", sdf.format(new Date())).set("id", SqlUtil.uuid());
			bool=Db.save("oa_yaojibeian", record);
		}else{
			record.setColumns(param).remove("isedit");
			bool=Db.update("oa_yaojibeian",record);
		}		
		return bool;		
	}
	
	
	/**
	 * 获取人员通讯录信息
	 * @throws Exception 
	 */
	public static Page<Record> findRenyuan(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and b.id = ?", "id");
		p.put("and a.name = ?", "dept");//部门姓名
		p.put("and b.deptname = ?", "deptid");//部门姓名
		p.put("and b.name like ?", "name","%%%s%%");//人员姓名
		p.put("and c.name = ?", "quxian");//区县名称
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  b.*,a.name as dept,c.name as quxian ",
				"from oa_renyuan b LEFT JOIN fw_dep a on a.id=b.deptname LEFT JOIN fw_area c on c.id=a.areaid where 1=1 " + s.getSql()+" ORDER BY a.orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 *获取部门下的审批人员 
	 */
	
	
	

	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findDept(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and name = ?", "name");//人员姓名
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  * ",
				"from fw_dep where 1=1 " + s.getSql()+" ORDER BY orderno",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取部门信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findYjdept(HashMap<String, Object> param) throws Exception {
		if(UserKit.currentUserInfo().getAuth().contains("auth_county")){
			param.put("areaid", UserKit.currentUserInfo().getArea().get("id"));
		}
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.name = ?", "name");//人员姓名
		p.put("and a.areaid = ?", "areaid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select  a.id,a.name,a.canuse,a.orderno,a.childcount,a.parentid,a.areaid  ",
				"from fw_dep a LEFT JOIN tz_qiye b on a.id=b.ID LEFT JOIN  tz_kudian c ON b.qyzzjgdm =c.qyzzjgdm where 1=1 and isnull(c.kdlxbh,'')<>'06' " + s.getSql()+" "
				+"GROUP BY a.id,a.name,a.canuse,a.orderno,a.childcount,a.parentid,a.areaid ORDER BY orderno" ,
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 保存人员信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret addRenyuan(HashMap<String, Object> param) {
		Record record=new Record().setColumns(param);
		boolean bool=false;
		if( StrKit.isBlank((String)param.get("id"))){//编辑
			record.set("id", SqlUtil.uuid());
			bool=Db.save("oa_renyuan", record);
			
		}else{
			bool=Db.update("oa_renyuan", record);
		}
		
		return Ret.create("ret",bool ).put("message","保存失败！");
	}

	
	/**
	 * 保存工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret saveWorkPlan(HashMap<String, Object> param) {
		Record record=new Record();
		record.setColumns(param).set("id", SqlUtil.uuid());
		return Ret.create("ret", Db.save("oa_workplan", record));
	}

	
	/**
	 * 获取工作安排
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> queryWorkPlan(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and userid = ?", "userid");
		p.put("and deptid = ?", "deptid");//人员姓名
		p.put("and starttime >= ?", "starttime");
		p.put("and endtime <= ?", "endtime");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_workplan where 1=1 " + s.getSql() + "order by starttime desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	
	/**
	 * 获取企业流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findliucheng(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.name = ?", "name");
		p.put("and b.qiyeid= ?", "qiyeid");
		p.put("and b.beianriqi=?", "beianriqi");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				
				"select a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status",
				"from  oa_shenpiliucheng a LEFT JOIN oa_qiyebeian b on a.liuchenghao=b.liuchengid where 1=1 " + s.getSql()+" GROUP BY a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status order by jiedian",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> findshenpiliucheng(HashMap<String, Object> param) throws Exception {
		String q="";
		List<String> authList = UserKit.currentUserInfo().getAuth();
		if(!authList.contains("auth_manager")&&!authList.contains("auth_municompany")){
			q=" and liuchengtype<>'4' ";
		}
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and liuchenghao = ?", "liuchenghao");
		p.put("and name = ?", "name");
		p.put("and jiedian=?", "jiedian");
		p.put("and shenpibumen=?","shenpibumen");
		p.put("and nowstatus=?", "nowstatus");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				
				"select *",
				"from  oa_shenpiliucheng where 1=1 " +q+ s.getSql()+" order by liuchenghao,jiedian ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	

	/**
	 * 获取熏蒸流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findxzliucheng(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.name = ?", "name");
		p.put("and b.qiyeid= ?", "qiyeid");
		p.put("and b.beianriqi=?", "beianriqi");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status",
				"from  oa_shenpiliucheng a LEFT JOIN oa_xunzhengbeian b on a.liuchenghao=b.liuchenghao where 1=1 " + s.getSql()+" GROUP BY a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status order by jiedian",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取轮换申请
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findlunhuanlc(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.name = ?", "name");
		p.put("and b.qyzzjgdm= ?", "qyzzjgdm");
		p.put("and b.id=?", "lhid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.lcstatus as status",
				"from  oa_shenpiliucheng a LEFT JOIN njpt_lunhuanshenqingb b on a.liuchenghao=b.liuchenghao where 1=1 " + s.getSql()+" GROUP BY a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.lcstatus order by jiedian",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findyjliucheng(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.name = ?", "name");
		p.put("and b.qiyeid= ?", "qiyeid");
		p.put("and b.beianriqi=?", "beianriqi");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status",
				"from  oa_shenpiliucheng a LEFT JOIN oa_yaojibeian b on a.liuchenghao=b.liuchenghao where 1=1 " + s.getSql()+" GROUP BY a.id,a.liuchenghao,a.shenpiren,a.shenpibumen,a.isedit,a.jiedian,a.name,b.status order by jiedian",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	

	/**
	 * 保存流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret saveliucheng(HashMap<String, Object> param) {
         Record record=new Record();
         if(param.get("id")!=null&&param.get("id")!=""){
        	 record.setColumns(param);
        	 return Ret.create("ret",Db.update("oa_shenpiliucheng", record));
         }else{
        	 record.setColumns(param).set("id",SqlUtil.uuid());
			if(!param.get("liuchenghao").equals("")){
				Record liucheng= Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=?",param.get("liuchenghao"));
				if(liucheng!=null){
					Record lc= Db.findFirst("select top 1 * from oa_shenpiliucheng where liuchenghao=? ORDER BY jiedian desc",param.get("liuchenghao"));
					record.set("jiedian",lc.getInt("jiedian")+1);
				}else{
					record.set("jiedian",1);
				}	
			}
			return Ret.create("ret",Db.save("oa_shenpiliucheng", record));
         }
	}
	
	/**
	 * 保存截取信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret savejiequxinxi(HashMap<String, Object> param) {
         Record record=new Record();
         record.set("id",SqlUtil.uuid());
         record.set("userid",param.get("userid"));
         record.set("caozuotime",param.get("caozuotime"));
         record.set("caidanname",param.get("caidanname"));
         record.set("jiequname",param.get("jiequname"));
		return Ret.create("ret",Db.save("fw_caozuo", record));
	}
	/**
	 * 获取流程名称
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret findlc(HashMap<String, Object> param) {
		Object liuchengtype=param.get("liuchengtype");
		Object shenpibumen=param.get("shenpibumen");
		Object nowstatus=param.get("nowstatus");
		if(!nowstatus.equals("2")){
			if(liuchengtype==""||liuchengtype==null){
				return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where nowstatus='"+nowstatus+"' GROUP BY name,liuchenghao"));
			}else if(liuchengtype.equals("99")){
				return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where nowstatus='"+nowstatus+"' and shenpibumen='" +shenpibumen+ "' GROUP BY name,liuchenghao"));
			}else{
				if(shenpibumen==""||shenpibumen==null){
					return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where nowstatus='"+nowstatus+"' and liuchengtype="+liuchengtype+" GROUP BY name,liuchenghao"));
				}else{
					return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where nowstatus='"+nowstatus+"' and liuchengtype="+liuchengtype+" and shenpibumen='" +shenpibumen+ "' GROUP BY name,liuchenghao"));
				}
			}
		}else{
			if(liuchengtype==""||liuchengtype==null){
				return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng GROUP BY name,liuchenghao"));
			}else if(liuchengtype.equals("99")){
				return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where shenpibumen='" +shenpibumen+ "' GROUP BY name,liuchenghao"));
			}else{
				if(shenpibumen==""||shenpibumen==null){
					return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where liuchengtype="+liuchengtype+" GROUP BY name,liuchenghao"));
				}else{
					return Ret.create("list",Db.find("select  name,liuchenghao from oa_shenpiliucheng where liuchengtype="+liuchengtype+" and shenpibumen='" +shenpibumen+ "' GROUP BY name,liuchenghao"));
				}	
			}
		}
	}
	/**
	 * 获取等级名称
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret finddj(HashMap<String, Object> param) {
		
		return Ret.create("list",Db.find("select  slevel,sname from dm_level GROUP BY slevel,sname"));
	}
	
	/**
	 * 编辑流程信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Ret updateliucheng(HashMap<String, Object> param) {
		Record record=new Record();
		record.setColumns(param);
		return Ret.create("ret",Db.update("oa_shenpiliucheng", record));
	}

	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findUserDep(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and c.id = ?", "deptid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*,c.id as deptid ,c.name as deptname ",
				"from fw_user a,fw_user_dep b,fw_dep c where  a.id=b.userid and b.depid =c.id " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取用户中与部门关联的人员--fw_user
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findUserDepId(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and b.depid = ?", "deptid");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT a.*,c.id as deptid ,c.name as deptname ",
				"FROM (SELECT * FROM fw_user WHERE id not in (SELECT id from tz_qiye)) as a LEFT JOIN  fw_user_dep b ON a.id=b.userid LEFT JOIN fw_dep c on b.depid=c.id where 1=1 " + s.getSql()+" ORDER BY a.orderno DESC",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取审核企业
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findQiye(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.qymc =?", "name");
		p.put("and a.xian =?", "xian");
		p.put("and a.qyzzjgdm =?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.* ",
				"from tz_qiye a,fw_dep b where a.id=b.id " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取药库点
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findYaokun(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_yaokuguanli where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取收储企业
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findQiyexinxi(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.xian =?", "xian");
		p.put("and a.jyywlxbh =?", "jyywlxbh");
		p.put("and a.qymc =?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.* ",
				"from tz_qiye a ,fw_dep b where a.id=b.id " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取省市企业
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findcity(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and ID = ?", "id");
		p.put("and 省 = ?", "sheng");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from city where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取区域名称
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findquyu(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.name as name,b.xzqhdm as xzqhdm",
				"from fw_area a,njpt_diqu b WHERE a.id=b.areaid" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取熏蒸名称
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findxunzhengname(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		//p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from dm_xzfs  where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂库点名称
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findyaojiname(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		//p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from njpt_yaokuguanli  where 1=1" + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 保存企业备案
	 * @author yzz
	 * @throws Exception 
	 */
	public static boolean addQiyebeian(HashMap<String, Object> param) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Record record=new Record();
		boolean bool=false;
		if(param.get("id").equals("")){
			record.setColumns(param).set("status", 1).set("beianriqi", sdf.format(new Date())).remove("id");
			bool=Db.save("oa_qiyebeian", record);
		}else{
			record.setColumns(param).remove("isedit");;
			bool=Db.update("oa_qiyebeian",record);
		}		
		return bool;		
	}
	/**
	 * 保存夏粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public static boolean addXlwuri(HashMap<String, Object> param) {
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		String name=UserKit.currentUserInfo().getArea().getString("name");
		String qiye=UserKit.currentUserInfo().getUser().getString("realname");
		Record record;
		if(UserKit.currentUserInfo().getAuth().contains("auth_county")){
			record=Db.findFirst("SELECT *from njpt_xialiangshougouwuribb where 1=1 and year='"+year+"' and dizhi='"+name+"'order by qishu desc");
		}else{
			record=Db.findFirst("SELECT *from njpt_xialiangshougouwuribb where 1=1 and year='"+year+"' and dizhi='"+name+"' and qiye='"+qiye+"'order by qishu desc");
		}
		boolean bool=false;
		record.remove("id").set("id", SqlUtil.uuid()).set("qishu", record.getInt("qishu")+1);
		bool=Db.save("njpt_xialiangshougouwuribb", record);
			
		return bool;			
	}
	/**
	 * 保存秋粮五日报表
	 * @author yzz
	 * @throws Exception 
	 */
	public static boolean addQlwuri(HashMap<String, Object> param) {
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		String name=UserKit.currentUserInfo().getArea().getString("name");
		String qiye=UserKit.currentUserInfo().getUser().getString("realname");
		Record record;
		if(UserKit.currentUserInfo().getAuth().contains("auth_county")){
			record=Db.findFirst("SELECT *from njpt_qiuliangshougouwuribb where 1=1 and nianfen='"+year+"' and quming='"+name+"'order by qishu desc");
		}else{
			record=Db.findFirst("SELECT *from njpt_qiuliangshougouwuribb where 1=1 and nianfen='"+year+"' and quming='"+name+"' and qiye='"+qiye+"'order by qishu desc");
		}
		boolean bool=false;
		record.remove("id").set("id", SqlUtil.uuid()).set("qishu", record.getInt("qishu")+1);
		bool=Db.save("njpt_qiuliangshougouwuribb", record);
			
		return bool;		
	}
	
	
	/**
	 * 获取企业编号查重信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findqiyebeianbianhao(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and beianbianhao =?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_qiyebeian where 1=1 " + s.getSql()+" ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取熏蒸编号查重信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findxunzhengbeianbianhao(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and beianbianhao =?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_xunzhengbeian where 1=1 " + s.getSql()+" ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂编号查重信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findyaojibeianbianhao(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and beianbianhao =?", "beianbianhao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_yaojibeian where 1=1 " + s.getSql()+" ",
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
	/**
	 * 获取企业信息
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findqiye(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.qyzzjgdm=?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT a.qyzzjgdm as qyzzjgdm,a.qymc as qymc,c.name as name ",
				"FROM tz_qiye a,njpt_diqu b,fw_area c where a.xzqhdm=b.xzqhdm and b.areaid=c.id  " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 续期信息查询
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryxuqi(HashMap<String, Object> map) throws Exception {		
		Param p = new Param();
		p.put("and year = ?", "year");
		p.put("and dizhi = ?", "dizhi");
		p.put("and qiye = ?", "qiye");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(map.get("page"))),
			Integer.valueOf(String.valueOf(map.get("rows"))),
			"select * ",
			"from njpt_xialiangshougouwuribb  "
			+ "where 1 = 1 " + s.getSql()+" order by qishu desc",
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 续期信息查询
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public static Page<Record> queryxuqiql(HashMap<String, Object> map) throws Exception {		
		Param p = new Param();
		p.put("and nianfen = ?", "year");
		p.put("and quming = ?", "quming");
		p.put("and qiye = ?", "qiye");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		
		Page<Record> page = Db.paginate(
			Integer.valueOf(String.valueOf(map.get("page"))),
			Integer.valueOf(String.valueOf(map.get("rows"))),
			"select * ",
			"from njpt_qiuliangshougouwuribb "
			+ "where 1 = 1 " + s.getSql()+" order by qishu desc",
			s.getParam().toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 获取审批列表
	 * @author yzz
	 * @throws Exception 
	 */
	public static Page<Record> findQyba(HashMap<String, Object> param) throws Exception {
		param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
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
				"select a.*, b.qymc as qymc,d.areaid as areaid,e.name as quyu,f.jiedian as jiedian",
				"from oa_qiyebeian a,tz_qiye b,fw_dep c,njpt_diqu d,fw_area e,oa_shenpiliucheng f where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchengid and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno,a.beianriqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取熏蒸备案列表
	 * @throws Exception
	 */
	public static Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and d.xzqhdm=?", "quyu");
		p.put("and b.qyzzjgdm=?", "qymc");
		p.put("and a.sqrq>=?", "starttime");
		p.put("and a.sqrq<=?", "endtime");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,b.qyzzjgdm as qyzzjgdm,d.areaid as areaid,e.name as quyu ",
				"from oa_xunzhengbeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid " + s.getSql()+" ORDER BY d.orderno ASC,a.beianriqi desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取药剂备案列表
	 * @throws Exception
	 */
	public static Page<Record> findYaoji(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.beianbianhao like ?", "beianbianhao","%%%s%%");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		p.put("and a.status = ?", "status");
		p.put("and a.qiyeid = ?", "qiyeid");
		p.put("and d.xzqhdm=?", "quyu");
		p.put("and b.qyzzjgdm=?", "qymc");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,d.areaid as areaid,e.name as quyu ",
				"from oa_yaojibeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid " + s.getSql()+" ORDER BY d.orderno ASC",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取上一步流程信息
	 * @throws Exception
	 */
	public static Page<Record> findliuchengxinxi(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		p.put("and a.liuchenghao = ?", "liuchenghao");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select a.*, b.qymc as qymc,d.areaid as areaid,e.name as quyu ",
				"from oa_yaojibeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取熏蒸备案是的备案编号信息
	 * @throws Exception
	 */
	public static Page<Record> findXzbeianxinxi(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and beianbianhao like ?", "beianbianhao","%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select * ",
				"from oa_xunzhengbeian where 1=1 " + s.getSql()+"order by beianbianhao desc",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	public static Page<Record> findyjkcxinxi(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and xzqydm = ?", "xzqhdm");
		p.put("and yjmc = ?", "yjmc");
		p.put("and pp = ?", "pp");
		p.put("and danwei = ?", "dw");
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"SELECT *",
				"from njpt_yaojikucunguanli WHERE kcsl >0 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取作业人员信息
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
	 * 获取审批熏蒸备案列表
	 * @throws Exception
	 */
	public static Page<Record> findXzbaa(HashMap<String, Object> param) throws Exception {
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
				"from oa_xunzhengbeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e,oa_shenpiliucheng f  where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchenghao  and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno,a.beianriqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取审批熏蒸备案列表
	 * @throws Exception
	 */
	public static Page<Record> findXzba(HashMap<String, Object> param) throws Exception {
		List<String> authList = UserKit.currentUserInfo().getAuth();
		//if(authList.contains("auth_county")){
			param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
		//}
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
				"from oa_xunzhengbeian a,tz_qiye b,fw_dep c ,njpt_diqu d,fw_area e,oa_shenpiliucheng f  where a.qiyeid=b.ID and b.ID=c.id and c.areaid=d.areaid and e.id=d.areaid and f.liuchenghao=a.liuchenghao  and e.parentid !='root' " + s.getSql()+sql+" ORDER BY e.orderno,a.beianriqi desc ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 获取审批药剂备案列表
	 * @throws Exception
	 */
	public static Page<Record> findYjba(HashMap<String, Object> param) throws Exception {
		param.put("shenpiren", UserKit.currentUserInfo().getUser().getString("id"));
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
	 * 获取轮换备案列表
	 * @throws Exception
	 */
	public static Page<Record> findLHba(HashMap<String, Object> param) throws Exception {
		
		Param p = new Param();
		p.put("and a.id = ?", "id");
		/*p.put("and a.liuchengid = ?", "liuchenghao");
		p.put("and a.lhsq_zt = ?", "status");
		p.put("and a.shenpiren = ?", "shenpiren");
		p.put("and d.xzqhdm=?","quyu");
		p.put("and b.qyzzjgdm=?", "qymc");*/
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				" select b.*,c.realname,a.qyzzjgdm,a.id as lhid ",
				" from njpt_lunhuanshenqingb a inner join njpt_shenheyijian b on a.id=b.id and a.liuchenghao=b.liuchenghao INNER JOIN fw_user c on b.shenpiren=c.id  where 1=1 " + s.getSql()+" order by jiedian  ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findtongguo(HashMap<String, Object> param) {
		Param p = new Param();
		p.put("and a.id = ?", "id");
		/*p.put("and a.liuchengid = ?", "liuchenghao");
		p.put("and a.lhsq_zt = ?", "status");
		p.put("and a.shenpiren = ?", "shenpiren");
		p.put("and d.xzqhdm=?","quyu");
		p.put("and b.qyzzjgdm=?", "qymc");*/
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				" select b.*,c.realname,a.qyzzjgdm ",
				" from njpt_lunhuanshenqingb a inner join oa_shenpiliucheng b on a.liuchneghao=b.id and a.liuchenghao=b.liuchenghao INNER JOIN fw_user c on b.shenpiren=c.id  where 1=1 " + s.getSql()+" order by jiedian  ",
				s.getParam().toArray(new Object[0]));
		return page;
	}


	

	
	
}