package com.zkzy.njzhpt.bo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.ChengPinLiangDAO;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.dao.EmergencyDAO;
import com.zkzy.njzhpt.dao.JibenxinxiDao;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.QuxuanCommondDao;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.dao.ZhiLiangGLDAO;
import com.zkzy.njzhpt.dao.ZhiliangzsDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class QuxuanCommondBo implements ICommond {

	@Override
	public Page<Record> findQiyexinxi(HashMap<String, Object> param)
			throws Exception {
		
		return QuxuanCommondDao.findQiyexinxi(param);
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Page<Record> findDiqu(HashMap<String, Object> param)
			throws Exception {
		return QuxuanCommondDao.findDiqu(param);
	}

	@Override
	public Page<Record> findDiquniandupx(HashMap<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return QuxuanCommondDao.findDiquniandupx(param);
	}

	@Override
	public Page<Record> chubeilianghuizongb(HashMap<String, Object> map)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> PageRecord = ChuBeiLiangJHDAO.queryswkc(map);//查询所有的
		double cbljh_xm=0,cbljh_jd=0,cbljh_xd=0,cbljh_xj=0;
		for(Record qykdRecord:PageRecord.getList()){
			String xzqhdm = qykdRecord.getStr("xzqhdm");
			String qyzzjgdm = qykdRecord.getStr("qyzzjgdm");
			String kdbm = qykdRecord.getStr("kdbm");
			Object xingzhi =map.get("xingzhi");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("xzqhdm", xzqhdm);
			param1.put("qyzzjgdm", qyzzjgdm);
			param1.put("kdbm", kdbm);
			if(xingzhi!=null){
				param1.put("xingzhi", xingzhi);
			}
			Record cangaosl = ChuBeiLiangJHDAO.findcangaosl(param1);//查询仓廒数量
			qykdRecord.setColumns(cangaosl);
			qykdRecord.set("xingzhi", "");
			/*if(null!=map.get("niandu")){
				param1.put("niandu", map.get("niandu"));
			}
			if(null!=map.get("xingzhi")){
				param1.put("xingzhi", map.get("xingzhi"));
			}
			Record record = ChuBeiLiangJHDAO.findcbljhkudiankcquxian(param1);//统计库点下储备粮计划分品种数量
			qykdRecord.setColumns(record);
			cbljh_xm+=(qykdRecord.getBigDecimal("cbljh_xm").doubleValue());
			cbljh_jd+=(qykdRecord.getBigDecimal("cbljh_jd").doubleValue());
			cbljh_xd+=(qykdRecord.getBigDecimal("cbljh_xd").doubleValue());
			cbljh_xj+=(qykdRecord.getBigDecimal("cbljh_xj").doubleValue());*/
		}
		Ret ret =new Ret().put("list", PageRecord).put("cbljh_xm", cbljh_xm).put("cbljh_jd", cbljh_jd).put("cbljh_xd", cbljh_xd).put("cbljh_xj", cbljh_xj); 
		
		return PageRecord;
	}

	@Override
	public Ret chubeilianghuizongheji(HashMap<String, Object> map)
			throws Exception {
		if(map.get("xzqhdm")==""){
			String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
			Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
			map.put("xzqhdm", re.get("xzqhdm"));
		}
		Ret ret=new Ret();
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and k.kdmc like ?", "kdmc","%%%s%%");
		p.put("and niandu = ?", "niandu");
		p.put("and c.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select sum(cbljh_xm)/1000 as cbljh_xm,SUM(cbljh_jd)/1000 cbljh_jd,sum(cbljh_xd)/1000 cbljh_xd,sum(cbljh_xj)/1000 cbljh_xj ",
				"from njpt_chubeiliangjihua c LEFT JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
			if(page.getList().size()==0){
				return  ret.put("heji",null);
			}else{
				return  ret.put("heji",page.getList().get(0));	
			}
			
		
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> queryLunHuanShenQing(HashMap<String, Object> map)
			throws Exception {
		Page<Record> lastPageRecord=LunHuanGuanLiDAO.queryLunHuanShenQingQX(map);
		Ret ret = Ret.create("list", lastPageRecord);
		return lastPageRecord;
	}
	
	/**
	 *获取库点实时收购情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryshishi(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		HashMap<String,Object> param = map;
		if("请选择".equals(param.get("xian"))){
			param.remove("xian");
		}
		if("请选择".equals(param.get("qyzzjgdm"))){
			param.remove("qyzzjgdm");
		}
		if("请选择".equals(param.get("kdmc"))){
			param.remove("kdmc");
		}
		if("0".equals(param.get("isksh"))){
			param.remove("isksh");
		}
		param.put("xian", xian);
		
		Page<Record> userPageRecord = ShouchukeshihuaDAO.querykchz(param);
		
		//========================================================
		//获取当前分期
		Date dt = new Date();
		String starttime;
		String endtime;
		String jidu = "夏季";
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String today = matter1.format(dt);
	    String sql = "select starttime , endtime,jidu "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
	    Record recordt = Db.findFirst(sql, today,today);
	    if(recordt == null){
	    	sql = "select starttime , endtime,jidu "
		    		+ "from njpt_fenqishangbao "
		    		+ "where convert(date,endtime) <= ? order by endtime desc ";
	    	recordt = Db.findFirst(sql,today);
	    	if(recordt == null){
	    		starttime = "2100-01-01";
		    	endtime = "2000-01-01";	
	    	}else{
	    		starttime = recordt.getStr("starttime");
			    endtime = recordt.getStr("endtime");
			    jidu = recordt.getStr("jidu");
	    	}
	    }else{
	    	starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
		    jidu = recordt.getStr("jidu");
	    }
	    
	    if(StrKit.isBlank(starttime) || starttime.length() != 10){
	    	
	    }
		//=====================================================
		
	    int index=1;
	    int index1=0;
		System.out.println(userPageRecord.getList().size());
		for(Record userRecord:userPageRecord.getList()){
			int page=userPageRecord.getPageNumber();
			int rows=userPageRecord.getPageSize();
			index1=(page-1)*rows+index;
			userRecord.set("xuhao",index1);
			index++;
			String qiye = userRecord.getStr("qyzzjgdm");
			String kdbm = userRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("qyzzjgdm", qiye);
			param1.put("kdbm", kdbm);
			param1.put("page", 1);
			param1.put("rows", 1);
			//Record pzkc = ShouchukeshihuaDAO.findzongkucun(param1);
			Record pzkc = new Record();
			if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
				pzkc.set("dmStock", 0);
			}else{
				pzkc = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
			}
			
			Record kdcr = new Record();
			if(JibenxinxiDao.queryCfZcr(param1).getTotalPage() == 0){
				kdcr.set("dmStock", 0);
			}else{
				kdcr = JibenxinxiDao.queryCfZcr(param1).getList().get(0);
			}
			
			userRecord.setColumns(pzkc);
			userRecord.setColumns(kdcr);
			
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("qyzzjgdm", qiye);
			param2.put("kdbm", kdbm);
			param2.put("starttime", starttime);
			param2.put("endtime", endtime);
			param2.put("page", 1);
			param2.put("rows", 1);
			if("夏季".equals(jidu)){
				param2.put("djpz", "113");
			}else{
				param2.put("djpz", "111");
			}
			Record pzrk = new Record();
			if(JibenxinxiDao.findzongrk(param2).getTotalPage() == 0){
				pzrk.set("sumNW", 0);
			}else{
				pzrk = JibenxinxiDao.findzongrk(param2).getList().get(0);
			}
			userRecord.setColumns(pzrk);
			/*
			String[] xingzhi = {"121","122","2","34"};
			String[] lsmc = {"shengchu","shichu","shangping","linshichu"};
			for(int i = 0; i < xingzhi.length; i++){
				param.remove("lsxz");
				param.remove("lsmcc");
				param.put("lsxz", xingzhi[i]);
				param.put("lsmcc",lsmc[i]);
				Record record = ShouchukeshihuaDAO.findxzkucun(param);
				userRecord.setColumns(record);
			}*/
			
			/*
			String[] lspzbm = {"111","1132","1131"};
			String[] lspz = {"xiaomai","jingdao","xiandao"};
			for(int i = 0; i < lspzbm.length; i++){
				param1.remove("lspzbm");
				param1.remove("lspz");
				param1.put("lspzbm", lspzbm[i]);
				param1.put("lspz",lspz[i]);
				Record record = ShouchukeshihuaDAO.findpzkucun(param1);
				userRecord.setColumns(record);
			}
			*/	
		}
		
		Ret ret = Ret.create("list", userPageRecord);
		
		return ret;
	}
	
	
	/**
	 *获取库点库存情况
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret querykchz(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		HashMap<String,Object> param = map;
		System.out.println("11111");
		if("请选择".equals(param.get("xian"))){
			param.remove("xian");
		}
		if("请选择".equals(param.get("qyzzjgdm"))){
			param.remove("qyzzjgdm");
		}
		if("请选择".equals(param.get("kdmc"))){
			param.remove("kdmc");
		}
		if("0".equals(param.get("isksh"))){
			param.remove("isksh");
		}
		param.put("xian", xian);
		Page<Record> userPageRecord = ShouchukeshihuaDAO.querykchz(param);
		
		//========================================================
		//获取当前分期
		Date dt = new Date();
		String starttime;
		String endtime;
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String today = matter1.format(dt);
	    String sql = "select starttime , endtime "
	    		+ "from njpt_fenqishangbao "
	    		+ "where convert(date,starttime) <= ? and convert(date,endtime) >= ?";
	    Record recordt = Db.findFirst(sql, today,today);
	    if(recordt == null){
	    	starttime = "2100-01-01";
	    	endtime = "2000-01-01";	
	    }else{
	    	starttime = recordt.getStr("starttime");
		    endtime = recordt.getStr("endtime");
	    }
	    
	    System.out.println(starttime);
	    System.out.println(starttime.length());
	    if(StrKit.isBlank(starttime) || starttime.length() != 10){
	    	
	    }
		//=====================================================
		
		
		int index=1;
		for(Record userRecord:userPageRecord.getList()){
			userRecord.set("xuhao",index);
			index++;
			String qiye = userRecord.getStr("qyzzjgdm");
			String kdbm = userRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("qyzzjgdm", qiye);
			param1.put("kdbm", kdbm);
			param1.put("page", 1);
			param1.put("rows",1);
			Record pzkc = new Record();
			if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
				pzkc.set("dmStock", 0);
			}else{
				pzkc = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
			}
			userRecord.setColumns(pzkc);
			
			Record kdcr = new Record();
			if(JibenxinxiDao.queryCfZcr(param1).getTotalPage() == 0){
				kdcr.set("dmStock", 0);
			}else{
				kdcr = JibenxinxiDao.queryCfZcr(param1).getList().get(0);
			}
			userRecord.setColumns(kdcr);
			
			
			HashMap<String, Object> param2 = new HashMap<>();
			param2.put("qyzzjgdm", qiye);
			param2.put("kdbm", kdbm);
			param2.put("starttime", starttime);
			param2.put("endtime", endtime);
			param2.put("page", 1);
			param2.put("rows", 1);
			Record pzrk = new Record();
			if(JibenxinxiDao.findzongrk(param2).getTotalPage() == 0){
				pzrk.set("sumNW", 0);
			}else{
				pzrk = JibenxinxiDao.findzongrk(param2).getList().get(0);
			}
			userRecord.setColumns(pzrk);

			String[] lspzbm = {"111","1132","1131"};
			String[] lspz = {"xiaomai","jingdao","xiandao"};
			Record r = new Record();
			for(int i = 0; i < lspzbm.length; i++){
				param1.remove("pzcode");
				param1.put("pzcode", lspzbm[i]);
				
				Record record = new Record();
				if(JibenxinxiDao.queryCfZkc(param1).getTotalRow() == 0){
					record.set("dmStock", 0);
				}else{
					record = JibenxinxiDao.queryCfZkc(param1).getList().get(0);
				}
				if(lspzbm[i] == "111"){
					r.set("xiaomai", record.get("dmStock"));
				}
				if(lspzbm[i] == "1132"){
					r.set("jingdao", record.get("dmStock"));
				}
				if(lspzbm[i] == "1131"){
					r.set("xiandao", record.get("dmStock"));
				}
				userRecord.setColumns(r);
			}	
		}
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
		
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> selectCPLCBJH(HashMap<String, Object> map) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("quming", re.get("xzqhdm"));
		Page<Record> PageRecord = ChengPinLiangDAO.selectCPLCBJH(map);
		return PageRecord;
	}

	@Override
	public Page<Record> selectZLXQ(HashMap<String, Object> map)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> PageRecord = ZhiLiangGLDAO.selectZLXQ(map);
		Ret ret = Ret.create("list", PageRecord);
		return PageRecord;
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> selectlunhuanHZ(HashMap<String, Object> map)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("szqy", re.get("xzqhdm"));
		Page<Record> list= LunHuanGuanLiDAO.selectlunhuanHZ(map);
		Ret ret = Ret.create("list", list);
		return list;
	}

	
	
	/**
	 * 分页查询企业信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiye(HashMap<String, Object> param) throws Exception{
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		param.put("quyu", xian);
		return CangchuglDao.findQiyexq(param);
	}

	@Override
	public Ret qyTjSh(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		  
		 int flag= Db.update("update tz_qiye SET status='3' where ID=?",map.get("id"));
		 if(flag>0){
			 ret.put("ret",true); 
		 }else{
			 ret.put("ret",false); 
			 
		 }
		return ret;
	}

	@Override
	public Ret qyTjShBtg(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		  
		 int flag= Db.update("update tz_qiye SET status='4' where ID=?",map.get("id"));
		 if(flag>0){
			 ret.put("ret",true); 
		 }else{
			 ret.put("ret",false); 
			 
		 }
		return ret;
	}

	/**
	 * 获取企业备案信息
	 * @author njl
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findQiyebeian(HashMap<String, Object> param)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("quyu", re.get("xzqhdm"));
		Page<Record> page=RenyuanDao.findQiyebeian(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchengid"),record.getStr("shenpiren"));
				if(liucheng!=null){
					record.set("jiedian", liucheng.getInt("jiedian"));
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
					}
				}
			}
		}
		return page;
	}

	/**
	 * 获取熏蒸备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("quyu", re.get("xzqhdm"));
		Page<Record> page=RenyuanDao.findXunzheng(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record redb=Db.findFirst("select *from oa_shenpiliucheng where 1=1 and liuchenghao=? order by jiedian desc", record.getStr("liuchenghao"));
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					
					if(dep!=null){
						record.set("jd",redb.get("jiedian"));
						record.set("depname", dep.getStr("name"));	
						record.set("jiedian", liucheng.getInt("jiedian"));
					}
				}
			}
		}
		return page;
	}

	/**
	 * 获取药剂备案列表
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoji(HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("quyu", re.get("xzqhdm"));
		Page<Record> page=RenyuanDao.findYaoji(param);
		for(Record record:page.getList()){
			if(record.getStr("shenpiren")!=null){
				Record liucheng=  Db.findFirst("select * from oa_shenpiliucheng where liuchenghao=? and shenpiren=?",record.getStr("liuchenghao"),record.getStr("shenpiren"));
				if(liucheng!=null){
					
					Record dep= Db.findFirst("select * from fw_dep  where id=?",liucheng.getStr("shenpibumen"));
					
					if(dep!=null){
						record.set("depname", dep.getStr("name"));	
						record.set("jiedian", liucheng.getInt("jiedian"));
					}
				}
			}
		}
		return page;
	}

	/**
	 * 药库分页查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findYaoku (HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("quyu", re.get("xzqhdm"));
		return CangchuglDao.findYaoku(param);
	}
	
	/**
	 * 遍历库点质量大概
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret querykudian(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		map.put("xian", xian);
		Page<Record> userPageRecord = ZhiliangzsDAO.querykudian(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 *获取夏粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryxialiangyuce(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		map.put("xian", xian);
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxialiangyuce(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 *获取秋粮生产收购预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryqiuliangyuce(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		map.put("xian", xian);
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangyuce(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 获取储备粮计划
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret shengchenghuizongb(HashMap<String, Object> map) throws Exception {
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		map.put("name", xian);
		Page<Record> PageRecord = ChuBeiLiangJHDAO.shengchenghuizongb(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	/**
	 * 获取储备粮计划
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cblglzxShenHe(HashMap<String, Object> map) throws Exception {
		/*String xian = UserKit.currentUserInfo().getArea().getString("name");
		map.put("xian", xian);*/
		Page<Record> PageRecord = ChuBeiLiangJHDAO.cblglzxShenHe(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	/**
	 * 查询企业安全标准化统计表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findanquantj(HashMap<String, Object> param) throws Exception{
		param.put("name", UserKit.currentUserInfo().getArea().getString("name"));
		Page<Record> userpage=CangchuglDao.findanquantj(param);
		/*for(Record page:userpage.getList()){
			Record name=Db.findFirst("SELECT a.name FROM fw_area a LEFT JOIN njpt_diqu b ON a.id=b.areaid WHERE b.xzqhdm=?",page.getStr("sb"));
			page.set("name", name.getStr("name"));
			Record qymc=Db.findFirst("SELECT qymc FROM tz_qiye WHERE qyzzjgdm=?",page.getStr("dbdw"));
			page.set("qymc", qymc.getStr("qymc"));
		}*/
		return userpage;
	}

	/**
	 * 遍历人口表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryrenkou(HashMap<String, Object> map) throws Exception {
		map.put("xian", UserKit.currentUserInfo().getArea().getString("name"));
		Page<Record> userPageRecord = EmergencyDAO.queryrenkou(map);
		return userPageRecord;
	}

	@Before(AutoPageInterceptor.class)
	public Page<Record> findshenpiliucheng(HashMap<String, Object> param) throws Exception {
		param.put("shenpibumen", UserKit.currentUserInfo().getDep().getString("id"));
		Page<Record> page=RenyuanDao.findshenpiliucheng(param);
		for(Record record:page.getList()){
			Record dept=Db.findFirst("select * from fw_dep where id=?",record.getStr("shenpibumen"));
			
			record.set("dept", dept.getStr("name"));
			Record user=Db.findFirst("select * from fw_user where id=?",record.getStr("shenpiren"));
			record.set("xingming", user.getStr("realname"));
			if(record.getStr("liuchengtype").equals("0")){
				Record qiye=Db.findFirst("select * from oa_qiyebeian where liuchengid=?",record.getStr("liuchenghao"));
				if(qiye==null){
					record.set("status", "0");
				}else{
					record.set("status", qiye.getStr("status"));
				}
				
			}else if(record.getStr("liuchengtype").equals("1")){
				Record xunzhen=Db.findFirst("select * from oa_xunzhengbeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(xunzhen==null){
					record.set("status", "0");
				}else{
					record.set("status", xunzhen.getStr("status"));
				}
			}else if(record.getStr("liuchengtype").equals("2")){
				Record yaoji=Db.findFirst("select * from oa_yaojibeian where liuchenghao=?",record.getStr("liuchenghao"));
				if(yaoji==null){
					record.set("status", "0");
				}else{
					record.set("status", yaoji.getStr("status"));
				}
				
			}
			
		}
		return page;
	}

	/**
	 * 应急小组的增删改查
	 */
	public Page<Record> findxiaozu(HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userpage=CangchuglDao.findxiaozu(param);
		int index=1;
		for(Record record:userpage.getList()){
			record.set("xuhao", index);
			index++;
		}
		return userpage;
	}

	@Override
	public Ret chakanxiangxich(HashMap<String, Object> map) throws Exception {

		List<Record> cangao= ChuBeiLiangJHDAO.findcangaolist(map);//查询仓廒编码
		for(Record record:cangao){
				map.remove("cfbm");
				map.put("cfbm", record.get("cfbm"));
				Record cfpzRecord=new Record();
				cfpzRecord = ChuBeiLiangJHDAO.findcbljhpzkucunquxian(map);
				
				if(cfpzRecord!=null){
					
					record.setColumns(cfpzRecord);
				}
		}
		
		Ret ret = Ret.create("list", new Page<>(cangao, 1, 15, 1, cangao.size()));
		return ret;
	}
	//市级储备
	@Override
	public Ret findzongjihua(HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("xzqhdm", re.get("xzqhdm"));
		Calendar a=Calendar.getInstance();
		String year= (a.get(Calendar.YEAR)-1)+"";
		String sql1 = "select  isnull(sum(jhsl)/1000,0) as jhsl,isnull(sum(cbljh_xm)/1000,0) as xm, isnull(sum(cbljh_jd)/1000,0) as jd,isnull(sum(cbljh_xd)/1000,0) as xd "
				+ " from njpt_chubeiliangjihua where niandu='"+year+"' and quming='"+(String)param.get("xzqhdm")+"' and xingzhi='122' ";
		List<Record> list=Db.find(sql1);
		Ret ret = Ret.create("list", list);
		return	ret;
	}
	//市级储备
	@Override
	public Ret findzongkucun(HashMap<String, Object> param) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("xzqhdm", re.get("xzqhdm"));
		Calendar a=Calendar.getInstance();
		String year= (a.get(Calendar.YEAR)-1)+"";
		String sql1 = "select b.vGrainSubTypeCode, isnull(sum(b.dmStock),0) as sum from njpt_chubeiliangjihua a inner join kc_CurrentStock b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode where b.vGrainPropertyCode in ('122') and a.quming='"+(String)param.get("xzqhdm")+"' and a.niandu='"+year
				+ "' group by b.vGrainSubTypeCode";
		List<Record> list=Db.find(sql1);
		Ret ret = Ret.create("list",list);
		return ret;
	}
	//县级储备
		@Override
		public Ret findzongjihuaxian(HashMap<String, Object> param) throws Exception {
			String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
			Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
			param.put("xzqhdm", re.get("xzqhdm"));
			Calendar a=Calendar.getInstance();
			String year= (a.get(Calendar.YEAR)-1)+"";
			String sql1 = "select  isnull(sum(jhsl)/1000,0) as jhsl,isnull(sum(cbljh_xm)/1000,0) as xm, isnull(sum(cbljh_jd)/1000,0) as jd,isnull(sum(cbljh_xd)/1000,0) as xd "
					+ " from njpt_chubeiliangjihua where niandu='"+year+"' and quming='"+(String)param.get("xzqhdm")+"' and xingzhi='123' ";
			List<Record> list=Db.find(sql1);
			Ret ret = Ret.create("list", list);
			return	ret;
		}
		//县级储备
		@Override
		public Ret findzongkucunxian(HashMap<String, Object> param) throws Exception {
			String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
			Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
			param.put("xzqhdm", re.get("xzqhdm"));
			Calendar a=Calendar.getInstance();
			String year= (a.get(Calendar.YEAR)-1)+"";
			String sql1 = "select b.vGrainSubTypeCode, isnull(sum(b.dmStock),0) as sum from njpt_chubeiliangjihua a inner join kc_CurrentStock b on a.ccqy=b.qyzzjgdm and a.cckd=b.kdbm and a.ccch=b.vWareHouseCode where b.vGrainPropertyCode in ('123') and a.quming='"+(String)param.get("xzqhdm")+"' and a.niandu='"+year
					+ "' group by b.vGrainSubTypeCode";
			List<Record> list=Db.find(sql1);
			Ret ret = Ret.create("list",list);
			return ret;
		}
	@Override
	public Ret fenxingzhi(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Ret ret=new Ret();
		HashMap<String, Object> xz=new HashMap<>();
		xz.put("xingzhibm", "123");
		xz.put("xingzhimc", "县级储备粮");
		return  ret.put(xz);	
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cbljhrenwu(HashMap<String, Object> queryparam) throws Exception {
		queryparam.put("xingzhi", "123");
		Page<Record> PageRecord = ChuBeiLiangJHDAO.cbljhrenwuzsg(queryparam);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}

	@Override
	public Ret chubeiliangrenwuheji(HashMap<String, Object> queryparam) throws Exception {
		queryparam.put("xingzhi", "123");
		return ChuBeiLiangJHDAO.chubeiliangrenwuheji(queryparam);
	}

	@Override
	public Ret queryxingzhi(HashMap<String, Object> map) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxingzhi(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cblswkc(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.cblswkc(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cblswkck(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.cblswkck(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cbljhniandurenwu(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.cbljhniandurenwu(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cbljhnianduheji(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm,b.name FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("diqu", re.get("name"));
		List<Record> userPageRecord = ChuBeiLiangJHBO.cbljhnianduheji(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret cbljh(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.cbljh(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakankudian(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		
		
		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakankudiankc(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		
		
		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudiankc(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret ckcbljherjiqiye(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		
		
		Page<Record> userPageRecord = ChuBeiLiangJHBO.ckcbljherjiqiye(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakankudian2(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));

		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian2(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakankudian2kc(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));

		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakankudian2kc(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakanecbljhkd(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));

		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakanecbljhkd(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakancangfang(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancangfang(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	@Override
	public Ret chakancangfangkc(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancangfangkc(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret chakancbljhcf(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.chakancbljhcf(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret shiwukucunheji(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Record userPageRecord = ChuBeiLiangJHBO.shiwukucunheji(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret shiwukucunhejixinxi(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Record userPageRecord = ChuBeiLiangJHBO.shiwukucunhejixinxi(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Ret cbljhheji(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("xzqhdm", re.get("xzqhdm"));
		Record userPageRecord = ChuBeiLiangJHBO.cbljhheji(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	public Page<Record> findYjck(HashMap<String, Object> param)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("xzqhdm", re.get("xzqhdm"));
		param.put("page", 1);
		param.put("rows", 10000);
		Page<Record> userPageRecord=CangchuglDao.findYjck(param);
	 	int index=1;
	 	int index1=0;
		for(Record userRecord:userPageRecord.getList()){
			int page=userPageRecord.getPageNumber();
			int rows=userPageRecord.getPageSize();
			index1=(page-1)*rows+index;
			userRecord.set("xuhao",index1);
			index++;
			List<Record> record=Db.find("select * from xz_yaojixinxi where 1=1 and beianbianhao=?",userRecord.get("xzbah").toString());
			int sum=0;
			for(int i=0;i<record.size();i++){
				sum+=Integer.valueOf(record.get(i).getStr("gg"))*Integer.valueOf(record.get(i).getStr("lysl"));
			}
			userRecord.set("weight", sum);
		}
	return userPageRecord;
	}

	@Override
	public Page<Record> selectfeiksh(HashMap<String, Object> map) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> PageRecord = ZhiLiangGLDAO.selectfeiksh(map);
		Ret ret = Ret.create("list", PageRecord);
		return PageRecord;
	}
	@Override
	public Page<Record> kucunliebiao(HashMap<String, Object> map) throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("xzqhdm", re.get("xzqhdm"));
		Page<Record> PageRecord = ZhiLiangGLBO.kucunliebiao(map);
		return PageRecord;
	}
	@Override
	public Page<Record> findyjkc(HashMap<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String xian = UserKit.currentUserInfo().getArea().getString("name");
		param.put("name", xian);
		Page<Record> page=CangchuglDao.findyjkc(param);
		int index=1;
		for (Record re : page.getList()) {
			re.set("xuhao", index);
			index++;
		}
		return page;
	}

	@Override
	public Page<Record> findkongping(HashMap<String, Object> queryparam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Record> findYjrk(HashMap<String, Object> param)
			throws Exception {
		String sql="SELECT a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		param.put("shiqu", re.get("xzqhdm"));
		Page<Record> userpage=CangchuglDao.findYjrk(param);
		for (Record Record : userpage.getList()) {
			int gg=Record.get("gg");
			int sl=Record.get("sl");
			Record.set("weight", gg*sl);
		}
		return userpage;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Ret findcompany(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT b.name,a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("name", re.get("name"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.findcompany(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret findcompanyy(HashMap<String, Object> queryparam) throws Exception {
		String sql="SELECT b.name,a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		queryparam.put("name", re.get("name"));
		Page<Record> userPageRecord = ChuBeiLiangJHBO.findcompanyy(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret finderjiqiyekd(HashMap<String, Object> queryparam) throws Exception {
		
		List<Record> userPageRecord = ChuBeiLiangJHBO.finderjiqiyekd(queryparam);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Page<Record> selectQuYu(HashMap<String, Object> param) throws Exception {
		param.put("diquid", UserKit.currentUserInfo().getArea().getString("id"));
		Page<Record> PageRecord = ZhiLiangGLDAO.selectQuYu(param);
		return PageRecord;
	}
	@Override
	@Before(AutoPageInterceptor.class)
	public Ret kucunheji(HashMap<String, Object> param) throws Exception {
		param.put("diquid", UserKit.currentUserInfo().getArea().getString("id"));
		Ret ret = ZhiLiangGLBO.kucunheji(param);
		return ret;
	}

	@Override
	public Page<Record> findxzzy(HashMap<String, Object> queryparam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Page<Record> findlcpw(HashMap<String, Object> map) throws Exception {
		String sql="SELECT b.name,a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("diqu", re.get("name"));
		Page<Record> list= LunHuanGuanLiDAO.findlcpw(map);
		return list;
	}

	@Override
	@Before(AutoPageInterceptor.class)
	public Ret lcpwheji(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT b.name,a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("diqu", re.get("name"));
		Record record2= LunHuanGuanLiDAO.lcpwheji(map);
		return new Ret().put("list",record2);
	}

	@Override
	public Ret findallcompany(HashMap<String, Object> map) throws Exception {
		String sql="SELECT b.name,a.xzqhdm FROM njpt_diqu a LEFT JOIN fw_area b ON a.areaid=b.id WHERE b.id=?";
		Record re=Db.findFirst(sql,UserKit.currentUserInfo().getArea().getString("id"));
		map.put("diqu", re.get("name"));
		List<Record> reclist= ChuBeiLiangJHBO.findallcompany(map);
		Ret ret=new Ret().put("list", reclist);
		return ret;
	}

}
