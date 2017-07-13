package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.dao.EmergencyDAO;
import com.zkzy.njzhpt.dao.LunHuanGuanLiDAO;
import com.zkzy.njzhpt.dao.RenyuanDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class ChuBeiLiangJHBO {
	@Before(AutoPageInterceptor.class)
	public Ret shengchenghuizongb(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.shengchenghuizongb(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	public Ret chubeiliangrenwuheji1(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Param p = new Param();
		p.put("and a.name = ?", "diqu");
		p.put("and c.niandu = ?", "niandu");
		p.put("and c.xingzhi = ?", "xingzhi");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,15, "select aa.* ",
				" from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_renwu c on b.xzqhdm=c.xzqhdm where a.parentid!='root' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
		return  ret.put("heji",page.getList().get(0));	
	
	}
	public Ret chubeiliangrenwuheji(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Param p = new Param();
		p.put("and a.xzqhdm = ?", "xzqhdm");
		p.put("and a.niandu = ?", "niandu");
		p.put("and c.name = ? ", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,15, "select aa.* ",
				" from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from njpt_chubeiliangjihua_renwu a inner join njpt_diqu b on a.xzqhdm=b.xzqhdm inner join fw_area c on b.areaid=c.id where 1=1 and xingzhi = '122' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
		for(Record record:page.getList()){
			Page<Record> xianji = Db.paginate(1,15, "select aa.* ",
					" from (select isnull(sum(cast(xm as int)),0) as xianjixm,isnull(sum(cast(jd as int)),0) xianjijd,isnull(sum(cast(xd as int)),0) xianjixd,isnull(sum(cast(xiaoji as int)),0) xianjixj from njpt_chubeiliangjihua_renwu a inner join njpt_diqu b on a.xzqhdm=b.xzqhdm inner join fw_area c on b.areaid=c.id where 1=1 and xingzhi = '123' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
			Record xianjirec=xianji.getList().get(0);
			record.set("xianjixm", xianjirec.get("xianjixm"));
			record.set("xianjijd", xianjirec.get("xianjijd"));
			record.set("xianjixd", xianjirec.get("xianjixd"));
			record.set("xianjixj", xianjirec.get("xianjixj"));
		}
		return  ret.put("heji",page.getList().get(0));	
	
	}
	public Ret chubeilianghuizongheji(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Param p = new Param();
		p.put("and quming = ?", "xzqhdm");
		p.put("and ccqy = ?", "qyzzjgdm");
		p.put("and k.kdmc like ?", "kdmc","%%%s%%");
		p.put("and niandu = ?", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))), "select sum(cbljh_xm)/1000 as cbljh_xm,SUM(cbljh_jd)/1000 cbljh_jd,sum(cbljh_xd)/1000 cbljh_xd,sum(cbljh_xj)/1000 cbljh_xj ",
				"from njpt_chubeiliangjihua c LEFT JOIN tz_kudian k on c.ccqy=k.qyzzjgdm and c.cckd=k.kdbm where 1=1 " + s.getSql(), s.getParam().toArray(new Object[0]));
			return  ret.put("heji",page.getList().get(0));	
		
		
	
	}
	@Before(AutoPageInterceptor.class)
	public Page<Record> chubeilianghuizongb(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.queryswkc(map);//查询所有的
		double cbljh_xm=0,cbljh_jd=0,cbljh_xd=0,cbljh_xj=0;
		for(Record qykdRecord:PageRecord.getList()){
			String xzqhdm = qykdRecord.getStr("xzqhdm");
			String qyzzjgdm = qykdRecord.getStr("qyzzjgdm");
			String kdbm = qykdRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("xzqhdm", xzqhdm);
			param1.put("qyzzjgdm", qyzzjgdm);
			param1.put("kdbm", kdbm);
			Record cangaosl = ChuBeiLiangJHDAO.findcangaosl(param1);//查询仓廒数量
			qykdRecord.setColumns(cangaosl);
			if(null!=map.get("niandu")){
				param1.put("niandu", map.get("niandu"));
			}
			if(null!=map.get("xingzhi")){
				param1.put("xingzhi", map.get("xingzhi"));
			}
			Record record = ChuBeiLiangJHDAO.findcbljhkudiankc(param1);//统计库点下储备粮计划分品种数量
			qykdRecord.setColumns(record);
			cbljh_xm+=(qykdRecord.getBigDecimal("cbljh_xm").doubleValue());
			cbljh_jd+=(qykdRecord.getBigDecimal("cbljh_jd").doubleValue());
			cbljh_xd+=(qykdRecord.getBigDecimal("cbljh_xd").doubleValue());
			cbljh_xj+=(qykdRecord.getBigDecimal("cbljh_xj").doubleValue());
		}
		Ret ret =new Ret().put("list", PageRecord).put("cbljh_xm", cbljh_xm).put("cbljh_jd", cbljh_jd).put("cbljh_xd", cbljh_xd).put("cbljh_xj", cbljh_xj); 
		
		return PageRecord;
	}
	@Before(AutoPageInterceptor.class)
	public Ret chakanxiangxich(HashMap<String, Object> map) throws Exception {
		
		List<Record> cangao= ChuBeiLiangJHDAO.findcangaolist(map);//查询仓廒编码
		for(Record record:cangao){
				map.remove("cfbm");
				map.put("cfbm", record.get("cfbm"));
				String role=(String)UserKit.currentUserInfo().getRole().get("code");
				Record cfpzRecord=new Record();
				if("njslsj".equals(role)){
					
					cfpzRecord = ChuBeiLiangJHDAO.findcbljhpzkucun(map);
				}else if("quxian".equals(role)){
					cfpzRecord = ChuBeiLiangJHDAO.findcbljhpzkucunquxian(map);
				}
				if(cfpzRecord!=null){
					
					record.setColumns(cfpzRecord);
				}
		}
		
		Ret ret = Ret.create("list", new Page<>(cangao, 1, 15, 1, cangao.size()));
		return ret;
	}
	@Before(AutoPageInterceptor.class)
	public Ret chakanswkcxiangxich(HashMap<String, Object> map) throws Exception {
		
		List<Record> cangao= ChuBeiLiangJHDAO.findcangaolist(map);//查询仓廒编码
		for(Record record:cangao){
			String[] lspzbm = {"111","1132","1131"};
			String[] lspz = {"xiaomai","jingdao","xiandao"};
			for(int i = 0; i < lspzbm.length; i++){
				map.remove("lspzbm");
				map.remove("lspz");
				map.remove("cfbm");
				map.put("cfbm", record.get("cfbm"));
				map.put("lspzbm", lspzbm[i]);
				map.put("lspz",lspz[i]);
				Record cfpzRecord = ChuBeiLiangJHDAO.findcfpzkucun(map);
				BigDecimal linshi=(BigDecimal)cfpzRecord.get(lspz[i],new BigDecimal(0.0));
				record.set(lspz[i], linshi);
			}
			double xioaji=((BigDecimal)record.get("xiaomai",new BigDecimal(0.0))).doubleValue()+((BigDecimal)record.get("jingdao",new BigDecimal(0.0))).doubleValue()+((BigDecimal)record.get("xiandao",new BigDecimal(0.0))).doubleValue();
			record.set("xiaoji", xioaji);
		}
		
		Ret ret = Ret.create("list", new Page<>(cangao, 1, 15, 1, cangao.size()));
		return ret;
	}
	
	@Before(AutoPageInterceptor.class)
	public Ret queryswkc(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.queryswkc(map);//查询所有的
		for(Record qykdRecord:PageRecord.getList()){
			String qyzzjgdm = qykdRecord.getStr("qyzzjgdm");
			String kdbm = qykdRecord.getStr("kdbm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("qyzzjgdm", qyzzjgdm);
			param1.put("kdbm", kdbm);
			Record cangaosl = ChuBeiLiangJHDAO.findcangaosl(param1);//查询仓廒数量
			qykdRecord.setColumns(cangaosl);
			String[] lspzbm = {"111","1132","1131"};
			String[] lspz = {"xiaomai","jingdao","xiandao"};
			for(int i = 0; i < lspzbm.length; i++){
				param1.remove("lspzbm");
				param1.remove("lspz");
				param1.put("lspzbm", lspzbm[i]);
				param1.put("lspz",lspz[i]);
				Record record = ChuBeiLiangJHDAO.findpzkucun(param1);
				qykdRecord.set(lspz[i], record.get(lspz[i],new BigDecimal(0.0)));
			}
			double xioaji=((BigDecimal)qykdRecord.get("xiaomai",new BigDecimal(0.0))).doubleValue()+((BigDecimal)qykdRecord.get("jingdao",new BigDecimal(0.0))).doubleValue()+((BigDecimal)qykdRecord.get("xiandao",new BigDecimal(0.0))).doubleValue();
			qykdRecord.set("xiaoji", xioaji);
		}
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	public Ret fpzECharts(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Calendar cal = Calendar.getInstance();
		String nian=(String) map.get("year");
		String month=(String) map.get("month");
		if ((nian==null||nian=="")&&(month==null||month=="")) {
			nian=String.valueOf((cal.get(Calendar.YEAR)));
			month=String.valueOf(((cal.get(Calendar.MONTH))+1));
		}
		int cgldmonth=Integer.valueOf(month);//页面传来的月份
		int cgldyear=Integer.valueOf(nian);//页面传来的年份
		int nowyear=cal.get(Calendar.YEAR);
		int nowmonth=(cal.get(Calendar.MONTH))+1;
		if(cgldmonth<nowmonth||cgldyear<nowyear){
			List<Record> shifoucunzai=Db.find("select * from njpt_shiwukucun_qu where year=? and month=?",nian,month);
			if(shifoucunzai.size()>0){
				ret = Ret.create("list",new Page<>(shifoucunzai, 1, 15, 1, 15));
			}else{
				ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
			}
		}else{
			List<Record> shifoucunzai=Db.find("select * from njpt_shiwukucun_fpz where year=? and month=?",nian,month);
			if(shifoucunzai.size()>0){
				ret = Ret.create("list", new Page<>(shifoucunzai, 1, 15, 1, 15));
			}else{
				List<Record> list=new ArrayList<>();
				//分品种储备粮计划
				Record cbljh=new Record();
				cbljh=Db.findFirst("select SUM(cbljh_xm) as xm,sum(cbljh_jd) as jd,SUM(cbljh_xd) as xd,sum(cbljh_xj) as xj FROM njpt_chubeiliangjihua where niandu=?",nian);
				//求实物库存
				Record qsxmRrcord = Db.findFirst(
						"select SUM(k.dmStock) as xm from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%111%' and k.vGrainPropertyCode in(122,123)");
				Record qsjdRrcord = Db.findFirst(
						"select SUM(k.dmStock) as jd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%1132%' and k.vGrainPropertyCode in(122,123)");
				Record qsxdRrcord = Db.findFirst(
						"select SUM(k.dmStock) as xd from  njpt_diqu d INNER JOIN tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN kc_CurrentStock k on q.qyzzjgdm=k.qyzzjgdm  and k.vGrainSubTypeCode like '%1131%' and k.vGrainPropertyCode in(122,123)");
				BigDecimal qsxmSWKC = qsxmRrcord.get("xm",new BigDecimal(0.0));
				BigDecimal qsjdSWKC = qsjdRrcord.get("jd",new BigDecimal(0.0));
				BigDecimal qsxdSWKC = qsxdRrcord.get("xd",new BigDecimal(0.0));
				double qsxjSWKC = qsxmSWKC.doubleValue() + qsjdSWKC.doubleValue() + qsxdSWKC.doubleValue();
				//小麦储备粮计划和实物库存
				Record xmjhs=new Record().set("shuliang", cbljh.get("xm")).set("leixing", "jhs");
				Record xmswkc=new Record().set("shuliang", qsxmSWKC).set("leixing", "swkc");
				//粳稻储备粮计划和实物库存
				Record jdjhs=new Record().set("shuliang", cbljh.get("jd")).set("leixing", "jhs");
				Record jdswkc=new Record().set("shuliang", qsjdSWKC).set("leixing", "swkc");
				//籼稻储备粮计划和实物库存
				Record xdjhs=new Record().set("shuliang", cbljh.get("xd")).set("leixing", "jhs");
				Record xdswkc=new Record().set("shuliang", qsxdSWKC).set("leixing", "swkc");
				//总量储备粮计划和实物库存
				Record xjjhs=new Record().set("shuliang", cbljh.get("xj")).set("leixing", "jhs");
				Record xjswkc=new Record().set("shuliang", qsxjSWKC).set("leixing", "swkc");
				list.add(xjjhs);
				list.add(xjswkc);
				list.add(xdjhs);
				list.add(xdswkc);
				list.add(jdjhs);
				list.add(jdswkc);
				list.add(xmjhs);
				list.add(xmswkc);
				ret = Ret.create("list", new Page<>(list, 1, 15, 1, 15));
			}
		}
		return ret;
	}
	/**
	 * 加载实物库存 分品种图表数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret quECharts(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Calendar cal = Calendar.getInstance();
		String nian=(String) map.get("year");
		String month=(String) map.get("month");
		if (nian==null&&month==null) {
			nian=String.valueOf((cal.get(Calendar.YEAR)));
			month=String.valueOf(((cal.get(Calendar.MONTH))+1));
		}
		int cgldmonth=Integer.valueOf(month);
		int cgldyear=Integer.valueOf(nian);
		int nowyear=cal.get(Calendar.YEAR);
		int nowmonth=(cal.get(Calendar.MONTH))+1;
		if(cgldmonth<nowmonth||cgldyear<nowyear){
			List<Record> shifoucunzai=Db.find("select * from njpt_shiwukucun_qu where year=? and month=?",nian,month);
			if(shifoucunzai.size()>0){
				ret = Ret.create("list",new Page<>(shifoucunzai, 1, 15, 1, 15));
			}else{
				ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
			}
		}else{
			List<Record> shifoucunzai=Db.find("select * from njpt_shiwukucun_qu where year=? and month=?",nian,month);
			if(shifoucunzai.size()>0){
				ret = Ret.create("list", new Page<>(shifoucunzai, 1, 15, 1, 15));
			}else{
			String niandu=String.valueOf(nowyear);
			List<Record> list=new ArrayList<>();
			//南京市
					Record nanjingshi=ChuBeiLiangJHDAO.nanjingshiEChart(niandu);
					Record nanjinshijhs=new Record()
							.set("leixing", "jhs")
							.set("shuliang", nanjingshi.get("jhsl"));
					Record nanjinshiswkc=new Record()
							.set("leixing", "swkc")
							.set("shuliang", nanjingshi.get("swkc"));
					list.add(nanjinshijhs);
					list.add(nanjinshiswkc);
					
			//区
					String[] qu = { "320115", "320116", "320111", "320124" };// 江宁 六合 浦口 溧水
					for(String xzqhdm:qu){
						Record quRecord=ChuBeiLiangJHDAO.quEChart(xzqhdm,niandu);
						Record qujhs=new Record()
								.set("leixing", "jhs")
								.set("shuliang", quRecord.get("jhsl"));
						Record quswkc=new Record()
								.set("leixing", "swkc")
								.set("shuliang", quRecord.get("swkc"));
						list.add(qujhs);
						list.add(quswkc);
					}
			
			//高淳
					Record gaochunRecord=ChuBeiLiangJHDAO.gaochunEChart(niandu);
					Record gaochunjhs=new Record()
							.set("leixing", "jhs")
							.set("shuliang", gaochunRecord.get("jhsl"));
					Record gaochunswkc=new Record()
							.set("leixing", "swkc")
							.set("shuliang", gaochunRecord.get("swkc"));
					list.add(gaochunjhs);
					list.add(gaochunswkc);
			//其他
					
					Record qitaRecord= ChuBeiLiangJHDAO.qitaEchart(niandu);
					Record qitajhs=new Record()
							.set("leixing", "jhs")
							.set("shuliang", qitaRecord.get("jhsl"));
					Record qitaswkc=new Record()
							.set("leixing", "swkc")
							.set("shuliang", qitaRecord.get("swkc"));
					list.add(qitajhs);
					list.add(qitaswkc);
			//南京市属企业实物库存和储备粮计划
					Record shishuqiyeRecord=ChuBeiLiangJHDAO.shishuqiyeEChart(niandu);
					Record shishujhs=new Record()
							.set("leixing", "jhs")
							.set("shuliang", shishuqiyeRecord.get("jhsl"));
					Record shishuswkc=new Record()
							.set("leixing", "swkc")
							.set("shuliang", shishuqiyeRecord.get("swkc"));
					list.add(shishujhs);
					list.add(shishuswkc);
			
			ret=Ret.create("list",new Page<>(list, 1, 15, 1, 15));
			}
		}
		return ret;
	}
	/**
	 * 保存当前月实物库存 作为结算中心每月的统计数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret saveshiwukucun(HashMap<String, Object> map) throws Exception {
		Ret ret=new Ret();
		Calendar cal = Calendar.getInstance();
		//得到当前年度
		int yearInt = cal.get(Calendar.YEAR);
		String year=String.valueOf(yearInt);
		//得到当前month
		int monthInt=cal.get(Calendar.MONTH);
		String month=String.valueOf((monthInt+1));
		HashMap<String, Object> para=new HashMap<>();
		para.put("year", year);
		para.put("month", month);
		boolean succes=Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				List<Record> shifoucunzai=Db.find("select * from njpt_shiwukucun where year=? and month=?",year,month);
				boolean bool1=false;
				if(shifoucunzai.size()>0){
					for(Record record:shifoucunzai){
						int id=record.getInt("id");
						bool1= Db.deleteById("njpt_shiwukucun", id);
					}
					bool1=saveshiwukucunfangfa(para);
				}else{
					bool1=saveshiwukucunfangfa(para);
				}
				
				return bool1;
			}
		});
		ret.put("ret", succes);
		return ret;
	}
	public static boolean saveshiwukucunfangfa(HashMap<String, Object> map){
		String year=(String) map.get("year");
		String month=(String) map.get("month");
		List<Record> list=new ArrayList<>();
		//查询南京市
		List<Record> qunshiList=quanshi(map); //（格式符合）
		for(Record record:qunshiList){
			list.add(record);
		}
		//南京市属企业(小麦 粳稻 籼稻)
		String[] shishuqyzzjgdm = { "134967560", "660696497" };// 铁心桥粮库  下关粮库
		for (String qyzzjgdm : shishuqyzzjgdm) {
			List<Record> shishu = shishuqiye(qyzzjgdm, map);
			for(Record record:shishu){
				list.add(record);
				
			}
		}
		//南京市属企业（粳稻 籼到）
		List<Record> shishujunliang = qita("660696498", map);//军粮供应站 (格式正确)
			for(Record record:shishujunliang){
				list.add(record);
			}
		//区(小麦 粳稻 籼稻)
		String[] qu = { "320115", "320111", "320116" };// 江宁 浦口 六合  （格式符合）
		for (String xzqhdm : qu) {
			List<Record> quList=quswkc(xzqhdm, map);
			for(Record record:quList){
				if ("320115"==xzqhdm) {
					record.set("qymc", "江宁区粮食购销公司");
				}
				if ("320111"==xzqhdm) {
					record.set("qymc", "浦口区粮食购销公司");
				}
				if ("320116"==xzqhdm) {
					record.set("qymc", "六合区粮食购销公司");
				}
				list.add(record);
			}
		}
		//溧水 高淳
		String[] biedequ = {"320124", "320125"};   //溧水 高淳 (格式符合)
		for(String xzqhdm:biedequ){
			List<Record> gaochunList=gaochun(xzqhdm, map);
			for(Record record:gaochunList){
				if("320124"==xzqhdm){
					record.set("qymc", "溧水区粮食购销公司");
				}
				if("320125"==xzqhdm){
					record.set("qymc", "高淳区粮食购销公司");
				}
				list.add(record);
			}
		}
		
		
		//其他(沙塘庵市场--粳稻)
			List<Record> qitashatangyan = shatangqita("736094548", map);//沙塘庵市场 
			for(Record record:qitashatangyan){
				list.add(record);
			}
		//其他(海悦公司--小麦)	
			List<Record> qitahaiyue = haiyueqita("765432125", map);//海悦公司
			for(Record record:qitahaiyue){
				list.add(record);
			}
		//得到当前年度
		boolean succes= Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				boolean bool=false;
				for(Record record:list){
					record.set("year", year);
					record.set("month", month);
					bool=Db.save("njpt_shiwukucun", record);
				}
				return bool;
			}
		});
		return succes;
	}
	/**
	 *加载企业名称
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before (AutoPageInterceptor.class)
	public Ret queryqiye(HashMap<String, Object> map) throws Exception {
		HashMap<String,Object> param = map;
		if("请选择承储企业".equals(param.get("xian"))){
			param.remove("xian");
		}
		Page<Record> userPageRecord = ChuBeiLiangJHDAO.queryqiye(param);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	/**
	 * 查询所有区域
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret selectQY(HashMap<String, Object> map) throws Exception {
		
		Page<Record> list= ChuBeiLiangJHDAO.selectQY(map);
		Ret ret = Ret.create("list", list);
		return ret;
	}
	/**
	 * 查询所有一级企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret selectQiYe(HashMap<String, Object> map) throws Exception {
		Page<Record> list= ChuBeiLiangJHDAO.selectQiYe(map);
		Ret ret = Ret.create("list", list);
		return ret;
	}
	/**
	 * 查询所有企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret selectallQiYe(HashMap<String, Object> map) throws Exception {
		Page<Record> list= ChuBeiLiangJHDAO.selectallQiYe(map);
		Ret ret = Ret.create("list", list.getList());
		return ret;
	}
	/**
	 * 查询所有企业
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret selectQYbydm(HashMap<String, Object> map) throws Exception {
		Page<Record> list= ChuBeiLiangJHDAO.selectQYbydm(map);
		Ret ret = Ret.create("list", list.getList());
		return ret;
	}
	/**
	 * 拉去储备粮任务增删改列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cbljhrenwuzsg(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.cbljhrenwuzsg1(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	/**
	 * 储备粮计划任务（市级储备和县级储备）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cbljhrenwu(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.name = ? ", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.*,a.name,c.diqu as cbldiqu ",
				" from fw_area a LEFT JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua_diqu c on b.xzqhdm=c.xzqhdm  where a.parentid!='root' " + s.getSql() + " ORDER BY c.orderno",
				s.getParam().toArray(new Object[0]));
		for(Record record:page.getList()){
			String qyzzjgdm=record.getStr("qymc");
			String niandu=(String)map.get("niandu");
			Record shijilinshi= Db.findFirst("select aa.* from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from njpt_chubeiliangjihua_renwu a  where 1=1 and a.xingzhi = '122' and a.qyzzjgdm='"+qyzzjgdm+"' and a.niandu='"+niandu+"' ) as aa");
			Record xianjilinshi= Db.findFirst("select aa.* from (select isnull(sum(cast(xm as int)),0) as xianjixm,isnull(sum(cast(jd as int)),0) xianjijd,isnull(sum(cast(xd as int)),0) xianjixd,isnull(sum(cast(xiaoji as int)),0) xianjixj from njpt_chubeiliangjihua_renwu a  where 1=1 and a.xingzhi = '123' and a.qyzzjgdm='"+qyzzjgdm+"' and a.niandu='"+niandu+"' ) as aa");
			record.setColumns(shijilinshi);	
			record.setColumns(xianjilinshi);
		}
		Ret ret = Ret.create("list", page);
		return ret;
	}
	/**
	 * 储备粮计划年度任务（市级储备和县级储备）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> cbljhniandurenwu(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.name = ? ", "diqu");
		p.put("and b.xzqhdm = ? ", "xzqhdm");
		p.put("and c.niandu = ? ", "niandu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.diqu,c.qyzzjgdm,c.qymc ",
				" from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua_diqu c on a.name=c.diqu  where 1=1 " + s.getSql() + " ORDER BY c.orderno,qyzzjgdm ",
				s.getParam().toArray(new Object[0]));
		for(Record record:page.getList()){
			String diqu=record.getStr("diqu");
			String qyzzjgdm=record.getStr("qyzzjgdm");
			String niandu=(String)map.get("niandu");
			Record shijilinshi= Db.findFirst("select aa.* from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from njpt_chubeiliangjihua_renwu a  where 1=1 and a.xingzhi = '122' and a.qyzzjgdm='"+qyzzjgdm+"' and a.niandu='"+niandu+"' ) as aa");
			Record xianjilinshi=new Record();
			/*if("市区".equals(diqu)){
				xianjilinshi.set("xianjixm", "/");
				xianjilinshi.set("xianjijd", "/");
				xianjilinshi.set("xianjixd", "/");
				xianjilinshi.set("xianjixj", "/");
			}else {
				
			}*/
			xianjilinshi= Db.findFirst("select aa.* from (select isnull(sum(cast(xm as int)),0) as xianjixm,isnull(sum(cast(jd as int)),0) xianjijd,isnull(sum(cast(xd as int)),0) xianjixd,isnull(sum(cast(xiaoji as int)),0) xianjixj from njpt_chubeiliangjihua_renwu a  where 1=1 and a.xingzhi = '123' and a.qyzzjgdm='"+qyzzjgdm+"'and a.niandu='"+niandu+"' ) as aa");
			record.setColumns(shijilinshi);	
			record.setColumns(xianjilinshi);
		}
		return page;
	}
	/**
	 * 储备粮计划年度任务（市级储备和县级储备）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public static List<Record> cbljhnianduheji(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and d.niandu = ?", "niandu");
		p.put("and c.niandu = ?", "niandu");
		p.put("and a.name = ? ", "diqu");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(1,15, "select aa.* ",
				" from (select isnull(sum(cast(xm as int)),0) as xm,isnull(sum(cast(jd as int)),0) jd,isnull(sum(cast(xd as int)),0) xd,isnull(sum(cast(xiaoji as int)),0) xj from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua_diqu c on a.name=c.diqu inner join njpt_chubeiliangjihua_renwu d on c.qyzzjgdm=d.qyzzjgdm  where 1=1 and d.xingzhi = '122' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
		for(Record record:page.getList()){
			Page<Record> xianji = Db.paginate(1,15, "select aa.* ",
					" from (select isnull(sum(cast(xm as int)),0) as xianjixm,isnull(sum(cast(jd as int)),0) xianjijd,isnull(sum(cast(xd as int)),0) xianjixd,isnull(sum(cast(xiaoji as int)),0) xianjixj from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid inner join njpt_chubeiliangjihua_diqu c on a.name=c.diqu inner join njpt_chubeiliangjihua_renwu d on c.qyzzjgdm=d.qyzzjgdm where 1=1 and d.xingzhi = '123' "+ s.getSql()+") as aa " , s.getParam().toArray(new Object[0]));
			Record xianjirec=xianji.getList().get(0);
			record.set("xianjixm", xianjirec.get("xianjixm"));
			record.set("xianjijd", xianjirec.get("xianjijd"));
			record.set("xianjixd", xianjirec.get("xianjixd"));
			record.set("xianjixj", xianjirec.get("xianjixj"));
		}
		return  page.getList();	
	}
	public static Ret shiwukucun(HashMap<String, Object> map) throws Exception {
		//TODO  什么鬼？？？你猜
		Ret ret=new Ret();
		Calendar cal = Calendar.getInstance();
		//前台页面传来的年月 year month  当前年月 nian yue
		String year=(String) map.get("year");
		String month=(String) map.get("month");
		int nianInt = cal.get(Calendar.YEAR);
		int yueInt = cal.get(Calendar.MONTH)+1;
		String nian=String.valueOf(nianInt);
		String yue=String.valueOf(yueInt);
		map.put("year", year);
		Object xingzhi=map.get("xingzhi");
		if(xingzhi!=null){
			map.put("xingzhi", xingzhi);
		}else{
			map.put("xingzhi","122");
		}
		// 判断是查询 实时的or上月保存的记录
		if((year.equals(nian)&&month.equals(yue))||(year==null&&month==null)){ //当前年月
			if("122".equals(xingzhi)){  		//市级储备
				List<Record> shijiisex=Db.find("select * from njpt_shiwukucun where year=? and month=?",nian,month);
				if(shijiisex.size()>0){			//如果没当前月记录 查询实时的实物库存
					ret = Ret.create("list", new Page<>(shijiisex, 1, 15, 1, 15));
				}else {
					List<Record> shijibyshishi=shiwukucunbyxingzhi(map);
					if(shijibyshishi.size()>0){
						ret = Ret.create("list", new Page<>(shijibyshishi, 1, 15, 1, 15));
					}else{
						ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
					}
				}
			}else if ("123".equals(xingzhi)) {  //县级储备
				List<Record> xianjiisex=Db.find("select * from njpt_shiwukucun_xianji where year=? and month=?",nian,month);
				if(xianjiisex.size()>0){
					ret = Ret.create("list", new Page<>(xianjiisex, 1, 15, 1, 15));
				}else{
					List<Record> xianjibyshishi=shiwukucunbyxingzhi(map);
					if(xianjibyshishi.size()>0){
						ret = Ret.create("list", new Page<>(xianjibyshishi, 1, 15, 1, 15));
					}else{
						ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
					}
				}
			}
				
		}else{													//不是当前年月
			
			if("122".equals(xingzhi)){  		//市级储备
				List<Record> shijiisex=Db.find("select * from njpt_shiwukucun where year=? and month=?",nian,month);
				if(shijiisex.size()>0){
					ret = Ret.create("list", new Page<>(shijiisex, 1, 15, 1, 15));
				}else{
					ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
				}
			}else if ("123".equals(xingzhi)) {  //县级储备
				List<Record> xianjiisex=Db.find("select * from njpt_shiwukucun_xianji where year=? and month=?",nian,month);
				if(xianjiisex.size()>0){
					ret = Ret.create("list", new Page<>(xianjiisex, 1, 15, 1, 15));
				}else{
					ret = Ret.create("list", new Page<>(new ArrayList<>(), 1, 15, 1, 15));
				}
			}
			
		}
		return ret;
	}
	/**
	 *  根据传来的性质生成实物库存固定样式
	 * @param map
	 * @return
	 */
	public static List<Record> shiwukucunbyxingzhi(HashMap<String, Object> map){
		List<Record> list=new ArrayList<>();
		//得到当前年度
		//查询南京市
		
		
		List<Record> qunshiList=quanshi(map); //
		for(Record record:qunshiList){
			list.add(record);
		}
		//南京市属企业(小麦 粳稻 籼稻)
		String[] shishuqyzzjgdm = { "134967560", "660696497" };// 铁心桥粮库  下关粮库
		for (String qyzzjgdm : shishuqyzzjgdm) {
			List<Record> shishu = shishuqiye(qyzzjgdm, map);
			for(Record record:shishu){
				list.add(record);
				
			}
		}
		//南京市属企业（粳稻 籼到）
		List<Record> shishujunliang = qita("660696498", map);//军粮供应站
			for(Record record:shishujunliang){
				list.add(record);
			}
		//区(小麦 粳稻 籼稻)
		String[] qu = { "320115", "320111", "320116" };// 江宁 浦口 六合  （格式符合）
		for (String xzqhdm : qu) {
			List<Record> quList=quswkc(xzqhdm, map);
			for(Record record:quList){
				if ("320115"==xzqhdm) {
					record.set("qymc", "江宁区粮食购销公司");
				}
				if ("320111"==xzqhdm) {
					record.set("qymc", "浦口区粮食购销公司");
				}
				if ("320116"==xzqhdm) {
					record.set("qymc", "六合区粮食购销公司");
				}
				list.add(record);
			}
		}
		//溧水 高淳(粳稻)
		String[] biedequ = { "320124", "320125"};   //溧水 高淳 (格式符合)
		for(String xzqhdm:biedequ){
			List<Record> gaochunList=gaochun(xzqhdm, map);
			for(Record record:gaochunList){
				if("320124"==xzqhdm){
					record.set("qymc", "溧水区粮食购销公司");
				}
				if("320125"==xzqhdm){
					record.set("qymc", "高淳区粮食购销公司");
				}
				list.add(record);
			}
		}

		//其他(沙塘庵市场--粳稻)
			List<Record> qitashatangyan = shatangqita("736094548", map);//沙塘庵市场 
			for(Record record:qitashatangyan){
				list.add(record);
			}
		//其他(海悦公司--小麦)	
			List<Record> qitahaiyue = haiyueqita("765432125", map);//海悦公司
			for(Record record:qitahaiyue){
				list.add(record);
			}
		return list;
	}
	/**
	 * 生成 其他(沙塘堰) 实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> haiyueqita(String qyzzjgdm,HashMap<String, Object> map){
		List<Record> qitaList=new ArrayList<>();
		Record qitaRecord=ChuBeiLiangJHDAO.haiyueqita(qyzzjgdm, map);
	
		Record jd=new Record().set("quming", "其他")
				.set("qymc",  "海悦公司")
				.set("pz", "小麦")
				.set("cbljh", qitaRecord.get("xmcbljh"))
				.set("swkc", qitaRecord.get("xmswkc"))
				.set("sjkczb", qitaRecord.get("sjkczb"))
				.set("sjkczbbool", qitaRecord.get("sjkczbbool"))
				.set("sjjdzb", qitaRecord.get("sjjdzb"))
				.set("sjjdzbbool", qitaRecord.get("sjjdzbbool"))
				.set("sjxdzb", qitaRecord.get("sjxdzb"))
				.set("sjxdzbbool", qitaRecord.get("sjxdzbbool"))
				.set("sjxmzb", qitaRecord.get("sjxmzb"))
				.set("sjxmzbbool", qitaRecord.get("sjxmzbbool"));
		
		qitaList.add(jd);
		return qitaList;
	}
	/**
	 * 生成 其他(沙塘堰) 实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> shatangqita(String qyzzjgdm,HashMap<String, Object> map){
		List<Record> qitaList=new ArrayList<>();
		Record qitaRecord=ChuBeiLiangJHDAO.shatangqita(qyzzjgdm, map);
	
		Record jd=new Record().set("quming", "其他")
				.set("qymc",  qitaRecord.get("qymc"))
				.set("pz", "粳稻")
				.set("cbljh", qitaRecord.get("jdcbljh"))
				.set("swkc", qitaRecord.get("jdswkc"))
				.set("sjkczb", qitaRecord.get("sjkczb"))
				.set("sjkczbbool", qitaRecord.get("sjkczbbool"))
				.set("sjjdzb", qitaRecord.get("sjjdzb"))
				.set("sjjdzbbool", qitaRecord.get("sjjdzbbool"))
				.set("sjxdzb", qitaRecord.get("sjxdzb"))
				.set("sjxdzbbool", qitaRecord.get("sjxdzbbool"))
				.set("sjxmzb", qitaRecord.get("sjxmzb"))
				.set("sjxmzbbool", qitaRecord.get("sjxmzbbool"));
		
		qitaList.add(jd);
		return qitaList;
	}
	/**
	 * 生成 其他 实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> qita(String qyzzjgdm,HashMap<String, Object> map){
		List<Record> qitaList=new ArrayList<>();
		Record qitaRecord=ChuBeiLiangJHDAO.qitashiwukucun(qyzzjgdm, map);
		Record heji=new Record().set("quming", "市属企业")
				.set("qymc", "军粮供应站")
				.set("pz", "小计")
				.set("cbljh", qitaRecord.get("xjcbljh"))
				.set("swkc", qitaRecord.get("xjswkc"))
				.set("sjkczb", qitaRecord.get("sjkczb"))
				.set("sjkczbbool", qitaRecord.get("sjkczbbool"))
				.set("sjjdzb", qitaRecord.get("sjjdzb"))
				.set("sjjdzbbool", qitaRecord.get("sjjdzbbool"))
				.set("sjxdzb", qitaRecord.get("sjxdzb"))
				.set("sjxdzbbool", qitaRecord.get("sjxdzbbool"))
				.set("sjxmzb", qitaRecord.get("sjxmzb"))
				.set("sjxmzbbool", qitaRecord.get("sjxmzbbool"));
		Record jd=new Record().set("quming", "市属企业")
				.set("qymc",  "军粮供应站")
				.set("pz", "粳稻")
				.set("cbljh", qitaRecord.get("jdcbljh"))
				.set("swkc", qitaRecord.get("jdswkc"))
				.set("sjkczb", qitaRecord.get("sjkczb"))
				.set("sjkczbbool", qitaRecord.get("sjkczbbool"))
				.set("sjjdzb", qitaRecord.get("sjjdzb"))
				.set("sjjdzbbool", qitaRecord.get("sjjdzbbool"))
				.set("sjxdzb", qitaRecord.get("sjxdzb"))
				.set("sjxdzbbool", qitaRecord.get("sjxdzbbool"))
				.set("sjxmzb", qitaRecord.get("sjxmzb"))
				.set("sjxmzbbool", qitaRecord.get("sjxmzbbool"));
		Record xd=new Record().set("quming", "市属企业")
				.set("qymc",  "军粮供应站")
				.set("pz", "籼稻")
				.set("cbljh", qitaRecord.get("xdcbljh"))
				.set("swkc", qitaRecord.get("xdswkc"))
				.set("sjkczb", qitaRecord.get("sjkczb"))
				.set("sjkczbbool", qitaRecord.get("sjkczbbool"))
				.set("sjjdzb", qitaRecord.get("sjjdzb"))
				.set("sjjdzbbool", qitaRecord.get("sjjdzbbool"))
				.set("sjxdzb", qitaRecord.get("sjxdzb"))
				.set("sjxdzbbool", qitaRecord.get("sjxdzbbool"))
				.set("sjxmzb", qitaRecord.get("sjxmzb"))
				.set("sjxmzbbool", qitaRecord.get("sjxmzbbool"));
		qitaList.add(heji);
		qitaList.add(jd);
		qitaList.add(xd);
		return qitaList;
	}
	/**
	 * 生成高淳实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> gaochun(String xzqhdm,HashMap<String, Object> map){
		List<Record> gaochunList=new ArrayList<>();
		Record gaochunRecord=ChuBeiLiangJHDAO.gaochunShiWuKuCun(xzqhdm, map);
		Record jd=new Record().set("quming", gaochunRecord.get("quming"))
				.set("qymc", "")
				.set("pz", "粳稻")
				.set("cbljh", gaochunRecord.get("jdcbljh"))
				.set("swkc", gaochunRecord.get("jdswkc"))
				.set("sjkczb", gaochunRecord.get("sjkczb"))
				.set("sjkczbbool", gaochunRecord.get("sjkczbbool"))
				.set("sjjdzb", gaochunRecord.get("sjjdzb"))
				.set("sjjdzbbool", gaochunRecord.get("sjjdzbbool"))
				.set("sjxdzb", gaochunRecord.get("sjxdzb"))
				.set("sjxdzbbool", gaochunRecord.get("sjxdzbbool"))
				.set("sjxmzb", gaochunRecord.get("sjxmzb"))
				.set("sjxmzbbool", gaochunRecord.get("sjxmzbbool"));
		
		gaochunList.add(jd);
		return gaochunList;
	}
	/**
	 * 生成区实物库存整条信息（储备粮计划，实物库存，占比）（高淳除外）
	 */
	public static List<Record> quswkc(String xzqhdm,HashMap<String, Object> map){
		List<Record> quList=new ArrayList<>();
		Record quRecord=ChuBeiLiangJHDAO.quShiWuKuCun(xzqhdm,map);
		Record heji=new Record().set("quming", quRecord.get("quming"))
				.set("qymc", "")
				.set("pz", "小计")
				.set("cbljh", quRecord.get("xjcbljh"))
				.set("swkc", quRecord.get("xjswkc"))
				.set("sjkczb", quRecord.get("sjkczb"))
				.set("sjkczbbool", quRecord.get("sjkczbbool"))
				.set("sjjdzb", quRecord.get("sjjdzb"))
				.set("sjjdzbbool", quRecord.get("sjjdzbbool"))
				.set("sjxdzb", quRecord.get("sjxdzb"))
				.set("sjxdzbbool", quRecord.get("sjxdzbbool"))
				.set("sjxmzb", quRecord.get("sjxmzb"))
				.set("sjxmzbbool", quRecord.get("sjxmzbbool"));
		Record xm=new Record().set("quming", quRecord.get("quming"))
				.set("qymc", "")
				.set("pz", "小麦")
				.set("cbljh", quRecord.get("xmcbljh"))
				.set("swkc", quRecord.get("xmswkc"))
				.set("sjkczb", quRecord.get("sjkczb"))
				.set("sjkczbbool", quRecord.get("sjkczbbool"))
				.set("sjjdzb", quRecord.get("sjjdzb"))
				.set("sjjdzbbool", quRecord.get("sjjdzbbool"))
				.set("sjxdzb", quRecord.get("sjxdzb"))
				.set("sjxdzbbool", quRecord.get("sjxdzbbool"))
				.set("sjxmzb", quRecord.get("sjxmzb"))
				.set("sjxmzbbool", quRecord.get("sjxmzbbool"));
		Record jd=new Record().set("quming", quRecord.get("quming"))
				.set("qymc", "")
				.set("pz", "粳稻")
				.set("cbljh", quRecord.get("jdcbljh"))
				.set("swkc", quRecord.get("jdswkc"))
				.set("sjkczb", quRecord.get("sjkczb"))
				.set("sjkczbbool", quRecord.get("sjkczbbool"))
				.set("sjjdzb", quRecord.get("sjjdzb"))
				.set("sjjdzbbool", quRecord.get("sjjdzbbool"))
				.set("sjxdzb", quRecord.get("sjxdzb"))
				.set("sjxdzbbool", quRecord.get("sjxdzbbool"))
				.set("sjxmzb", quRecord.get("sjxmzb"))
				.set("sjxmzbbool", quRecord.get("sjxmzbbool"));
		Record xd=new Record().set("quming", quRecord.get("quming"))
				.set("qymc", "")
				.set("pz", "籼稻")
				.set("cbljh", quRecord.get("xdcbljh"))
				.set("swkc", quRecord.get("xdswkc"))
				.set("sjkczb", quRecord.get("sjkczb"))
				.set("sjkczbbool", quRecord.get("sjkczbbool"))
				.set("sjjdzb", quRecord.get("sjjdzb"))
				.set("sjjdzbbool", quRecord.get("sjjdzbbool"))
				.set("sjxdzb", quRecord.get("sjxdzb"))
				.set("sjxdzbbool", quRecord.get("sjxdzbbool"))
				.set("sjxmzb", quRecord.get("sjxmzb"))
				.set("sjxmzbbool", quRecord.get("sjxmzbbool"));
		quList.add(heji);
		quList.add(xm);
		quList.add(jd);
		quList.add(xd);
		return quList;
		
	}
	/**
	 * 生成市属企业实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> shishuqiye(String qyzzjgdm,HashMap<String, Object> map){
		List<Record> shishuList=new ArrayList<>();
		Record shishu=ChuBeiLiangJHDAO.shishuqiyeshiwukucun(qyzzjgdm,map);
		
		Record heji=new Record().set("quming", "市属企业")
				.set("qymc",shishu.get("qymc"))
				.set("pz", "小计")
				.set("cbljh", shishu.get("xjcbljh"))
				.set("swkc", shishu.get("xjswkc"))
				.set("sjkczb", shishu.get("sjkczb"))
				.set("sjkczbbool", shishu.get("sjkczbbool"))
				.set("sjjdzb", shishu.get("sjjdzb"))
				.set("sjjdzbbool", shishu.get("sjjdzbbool"))
				.set("sjxdzb", shishu.get("sjxdzb"))
				.set("sjxdzbbool", shishu.get("sjxdzbbool"))
				.set("sjxmzb", shishu.get("sjxmzb"))
				.set("sjxmzbbool", shishu.get("sjxmzbbool"));
		Record xm=new Record().set("quming", "市属企业")
				.set("qymc", shishu.get("qymc"))
				.set("pz", "小麦")
				.set("cbljh", shishu.get("xmcbljh"))
				.set("swkc", shishu.get("xmswkc"))
				.set("sjkczb", shishu.get("sjkczb"))
				.set("sjkczbbool", shishu.get("sjkczbbool"))
				.set("sjjdzb", shishu.get("sjjdzb"))
				.set("sjjdzbbool", shishu.get("sjjdzbbool"))
				.set("sjxdzb", shishu.get("sjxdzb"))
				.set("sjxdzbbool", shishu.get("sjxdzbbool"))
				.set("sjxmzb", shishu.get("sjxmzb"))
				.set("sjxmzbbool", shishu.get("sjxmzbbool"));
		Record jd=new Record().set("quming", "市属企业")
				.set("qymc", shishu.get("qymc"))
				.set("pz", "粳稻")
				.set("cbljh", shishu.get("jdcbljh"))
				.set("swkc", shishu.get("jdswkc"))
				.set("sjkczb", shishu.get("sjkczb"))
				.set("sjkczbbool", shishu.get("sjkczbbool"))
				.set("sjjdzb", shishu.get("sjjdzb"))
				.set("sjjdzbbool", shishu.get("sjjdzbbool"))
				.set("sjxdzb", shishu.get("sjxdzb"))
				.set("sjxdzbbool", shishu.get("sjxdzbbool"))
				.set("sjxmzb", shishu.get("sjxmzb"))
				.set("sjxmzbbool", shishu.get("sjxmzbbool"));
		Record xd=new Record().set("quming", "市属企业")
				.set("qymc", shishu.get("qymc"))
				.set("pz", "籼稻")
				.set("cbljh", shishu.get("xdcbljh"))
				.set("swkc", shishu.get("xdswkc"))
				.set("sjkczb", shishu.get("sjkczb"))
				.set("sjkczbbool", shishu.get("sjkczbbool"))
				.set("sjjdzb", shishu.get("sjjdzb"))
				.set("sjjdzbbool", shishu.get("sjjdzbbool"))
				.set("sjxdzb", shishu.get("sjxdzb"))
				.set("sjxdzbbool", shishu.get("sjxdzbbool"))
				.set("sjxmzb", shishu.get("sjxmzb"))
				.set("sjxmzbbool", shishu.get("sjxmzbbool"));
		shishuList.add(heji);
		shishuList.add(xm);
		shishuList.add(jd);
		shishuList.add(xd);
		return shishuList;
		
	}
	/**
	 * 生成实物库存整条信息（储备粮计划，实物库存，占比）
	 */
	public static List<Record> quanshi(HashMap<String, Object> map){
		List<Record> quanshiList=new ArrayList<>();
		Record quanshi=ChuBeiLiangJHDAO.quanshi(map);
		
		Record heji=new Record().set("quming", "南京市")
				.set("qymc", "")
				.set("pz", "合计")
				.set("cbljh", quanshi.get("xjcbljh"))
				.set("swkc", quanshi.get("xjswkc"))
				.set("sjkczb", quanshi.get("sjkczb"))
				.set("sjkczbbool", quanshi.get("sjkczbbool"))
				.set("sjjdzb", quanshi.get("sjjdzb"))
				.set("sjjdzbbool", quanshi.get("sjjdzbbool"))
				.set("sjxdzb", quanshi.get("sjxdzb"))
				.set("sjxdzbbool", quanshi.get("sjxdzbbool"))
				.set("sjxmzb", quanshi.get("sjxmzb"))
				.set("sjxmzbbool", quanshi.get("sjxmzbbool"));
		Record xm=new Record().set("quming", "南京市")
				.set("qymc", "")
				.set("pz", "小麦")
				.set("cbljh", quanshi.get("xmcbljh"))
				.set("swkc", quanshi.get("xmswkc"))
				.set("sjkczb", quanshi.get("sjkczb"))
				.set("sjkczbbool", quanshi.get("sjkczbbool"))
				.set("sjjdzb", quanshi.get("sjjdzb"))
				.set("sjjdzbbool", quanshi.get("sjjdzbbool"))
				.set("sjxdzb", quanshi.get("sjxdzb"))
				.set("sjxdzbbool", quanshi.get("sjxdzbbool"))
				.set("sjxmzb", quanshi.get("sjxmzb"))
				.set("sjxmzbbool", quanshi.get("sjxmzbbool"));
		Record jd=new Record().set("quming", "南京市")
				.set("qymc", "")
				.set("pz", "粳稻")
				.set("cbljh", quanshi.get("jdcbljh"))
				.set("swkc", quanshi.get("jdswkc"))
				.set("sjkczb", quanshi.get("sjkczb"))
				.set("sjkczbbool", quanshi.get("sjkczbbool"))
				.set("sjjdzb", quanshi.get("sjjdzb"))
				.set("sjjdzbbool", quanshi.get("sjjdzbbool"))
				.set("sjxdzb", quanshi.get("sjxdzb"))
				.set("sjxdzbbool", quanshi.get("sjxdzbbool"))
				.set("sjxmzb", quanshi.get("sjxmzb"))
				.set("sjxmzbbool", quanshi.get("sjxmzbbool"));
		Record xd=new Record().set("quming", "南京市")
				.set("qymc", "")
				.set("pz", "籼稻")
				.set("cbljh", quanshi.get("xdcbljh"))
				.set("swkc", quanshi.get("xdswkc"))
				.set("sjkczb", quanshi.get("sjkczb"))
				.set("sjkczbbool", quanshi.get("sjkczbbool"))
				.set("sjjdzb", quanshi.get("sjjdzb"))
				.set("sjjdzbbool", quanshi.get("sjjdzbbool"))
				.set("sjxdzb", quanshi.get("sjxdzb"))
				.set("sjxdzbbool", quanshi.get("sjxdzbbool"))
				.set("sjxmzb", quanshi.get("sjxmzb"))
				.set("sjxmzbbool", quanshi.get("sjxmzbbool"));
		quanshiList.add(heji);
		quanshiList.add(xm);
		quanshiList.add(jd);
		quanshiList.add(xd);
		return quanshiList;
		
	}
	
	public boolean findchubeiliangjhRW(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChuBeiLiangJHDAO.findchubeiliangjhRW(map);
		return bool;
	}
	
	public boolean findShiChuQiye(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChuBeiLiangJHDAO.findShiChuQiye(map);
		return bool;
	}
	public boolean findchubeiliangjh(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChuBeiLiangJHDAO.findchubeiliangjh(map);
		return bool;
	}
	public Ret bianji(HashMap<String, Object> map) throws Exception {
		 
		return ChuBeiLiangJHDAO.bianji(map);
	}
	
	public Ret savecbljhpz(HashMap<String, Object> map) throws Exception {
		 
		return ChuBeiLiangJHDAO.savecbljhpz(map);
	}
	public Ret cbljhrwbianji(HashMap<String, Object> map) throws Exception {
		return ChuBeiLiangJHDAO.cbljhrwbianji(map);
	}
	public Ret cbljhrwbj(HashMap<String, Object> map) throws Exception {
		return ChuBeiLiangJHDAO.cbljhrwbj(map);
	}
	/**
	 * 保存储备粮计划驳回理由
	 * @throws Exception
	 */
	public Ret saveBohuicbljh(HashMap<String, Object> param) {
		Ret ret=new Ret();
		int flag=Db.update("update njpt_chubeiliangjihua set status=? ,liyou=?,shenpiren=?,realname=? where id=?","2",param.get("liyou"),UserKit.currentUserInfo().getUser().getString("id"),UserKit.currentUserInfo().getUser().getString("realname"),param.get("id"));
		if(flag>0){
			ret.put("ret", true);
		}else{
			ret.put("ret", false);
		}
		return ret;
	}
	
	/**
	 * 保存市储企业
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret saveshichuqiye(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		Record record=new Record();
		List<Record> linshi= ChuBeiLiangJHDAO.selectorderno(param).getList();
		List<Record> linshiqy= ChuBeiLiangJHDAO.selectqymc(param).getList();
		if(linshi.size()>0){
			record.set("qymc", linshiqy.get(0).get("qymc"));
		}
		record.set("xzqhdm", param.get("xzqhdm"))
			  .set("qyzzjgdm", param.get("qyzzjgdm"))
			  .set("diqu",param.get("diqu"))
			  .set("niandu",param.get("niandu"))
			  .set("orderno",param.get("orderno"));
		boolean bool= Db.save("njpt_chubeiliangjihua_diqu", record);
		if(bool){
			ret.put("ret",bool);
		}else {
			ret.put("ret",bool).put("msg","配置失败！");
		}
		return ret;
	}
	/**
	 * 编辑市储企业
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret bianjishichuqiye(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		Record record=new Record();
		List<Record> linshiqy= ChuBeiLiangJHDAO.selectqymc(param).getList();
		if(linshiqy.size()>0){
			record.set("qymc", linshiqy.get(0).get("qymc"));
		}
		record.set("xzqhdm", param.get("xzqhdm"))
			  .set("qyzzjgdm", param.get("qyzzjgdm"))
			  .set("diqu",param.get("diqu"))
			  .set("niandu",param.get("niandu"))
			  .set("orderno",param.get("orderno"))
			  .set("id", param.get("id"));
		boolean bool= Db.update("njpt_chubeiliangjihua_diqu","id",record);
		if(bool){
			ret.put("ret",bool);
		}else {
			ret.put("ret",bool).put("msg","配置失败！");
		}
		return ret;
	}
	@Before(AutoPageInterceptor.class)
	public Ret selectCBLJHHZ(HashMap<String, Object> map) throws Exception {	
		Page<Record> PageRecord = ChuBeiLiangJHDAO.selectCBLJHHZ(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	@Before(AutoPageInterceptor.class)
	public Ret selectCBLJH(HashMap<String, Object> map) throws Exception {
		Page<Record> PageRecord = ChuBeiLiangJHDAO.selectCBLJH(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	@Before(AutoPageInterceptor.class)
	public Ret chakanCBLJH(HashMap<String, Object> map) throws Exception {
		
		Page<Record> PageRecord = ChuBeiLiangJHDAO.chakanCBLJH(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	@Before(AutoPageInterceptor.class)
	public Ret liebiaoSWKC(HashMap<String, Object> map) throws Exception {
		
		Page<Record> PageRecord = ChuBeiLiangJHDAO.liebiaoSWKC(map);
		Ret ret = Ret.create("list", PageRecord);
		return ret;
	}
	
	public Ret saveCBLJH(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChuBeiLiangJHDAO.saveCBLJH(map);
		Ret ret = Ret.create("ret", bool);
		return ret;
	}
	public Ret saveQYCBLJH(HashMap<String, Object> map) throws Exception {
		
		boolean bool= ChuBeiLiangJHDAO.saveQYCBLJH(map);
		Ret ret = Ret.create("ret", bool);
		return ret;
	}
	
	@Before(AutoPageInterceptor.class)
	public static Page<Record> cbljh(HashMap<String, Object> map) throws Exception {
		
		//将二级企业库点转换成qyzzjgdm和kdbm(市属企业直接查库点未加-但是不影响)
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		/*int year=Integer.parseInt((String)map.get("year"));
		int month=Integer.parseInt((String)map.get("month"));
		//当月结算时间
		String time= ChuBeiLiangJHDAO.getLastDayOfMonth(year,month);
		map.put("month", time);*/
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		pzmap.put("xingzhi", map.get("xingzhi"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		String[] xz= ChuBeiLiangJHDAO.swkccheckxz(pzmap);
		map.put("yearsub", String.valueOf(Integer.valueOf((String)map.get("year"))-1));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		
		String[] cbljhsjmc={"xm","jd","xd"};
		String[] cbljhxjmc={"xianjixm","xianjijd","xianjixd"};
		for(Record recordQu:pageQu.getList()){
			//市属企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				BigDecimal cbljhsj=new BigDecimal(0);
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int j=0;j<2;j++){
					map.put("xingzhi", xz[j]);
					for(int i=0;i<3;i++){
						map.put("pinzhong", cbljhpz[i]);
						//性质-品种
						BigDecimal jihuashiji=new BigDecimal(0);
						jihuashiji=ChuBeiLiangJHDAO.cblswkcshiqu(map);
						if(j==0){
							cbljhsj=cbljhsj.add(jihuashiji);
							recordQu.set(cbljhsjmc[i], jihuashiji);
						}else if(j==1){
							cbljhxj=cbljhxj.add(jihuashiji);
							recordQu.set(cbljhxjmc[i], jihuashiji);
						}
					}
				}
				recordQu.set("xj", cbljhsj);
				recordQu.set("xianjixj", cbljhxj);
				recordQu.set("qyzzjgdm",recordQu.get("qyzzjgdm"));
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				BigDecimal cbljhsj=new BigDecimal(0);
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int j=0;j<2;j++){
					testMap.put("xingzhi", xz[j]);
					for(int i=0;i<3;i++){
						testMap.put("pinzhong", cbljhpz[i]);
						//性质-品种
						BigDecimal jihuashiji=new BigDecimal(0);
						jihuashiji=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
						if(j==0){
							cbljhsj=cbljhsj.add(jihuashiji);
							recordQu.set(cbljhsjmc[i], jihuashiji);
						}else if(j==1){
							cbljhxj=cbljhxj.add(jihuashiji);
							recordQu.set(cbljhxjmc[i], jihuashiji);
						}
					}
				}
				recordQu.set("xj", cbljhsj);
				recordQu.set("xianjixj", cbljhxj);
				recordQu.set("qyzzjgdm",testMap.get("qyzzjgdm"));
			}
			
			//将查询条件放到每一行里
			recordQu.set("niandu", map.get("year"));
			recordQu.set("xzqhdm", recordQu.get("xzqhdm"));
			recordQu.set("kdbm",map.get("kdbm"));
			recordQu.set("cfbm",map.get("cfbm"));
			recordQu.set("month",map.get("month"));
			recordQu.set("pzclass",map.get("pzclass"));
			recordQu.set("xingzhi", pzmap.get("xingzhi"));
			recordQu.set("pinzhong",pzmap.get("pinzhong"));
			
	}
		return pageQu;
		
		
	}
	@Before(AutoPageInterceptor.class)
	public static Page<Record> cblswkc(HashMap<String, Object> map) throws Exception {
		
		//将二级企业库点转换成qyzzjgdm和kdbm(市属企业直接查库点未加-但是不影响)
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)map.get("year"),(String)map.get("month"));
		//int year=Integer.parseInt((String)map.get("year"));
		//int month=Integer.parseInt((String)map.get("month"));
		//当月结算时间
		//String time= ChuBeiLiangJHDAO.getLastDayOfMonth(year,month);
		//map.put("month", time);
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		Calendar c = Calendar.getInstance();
		map.put("yearsub",c.get(Calendar.YEAR));
		map.put("year",c.get(Calendar.YEAR));
		//map.put("yearsub", String.valueOf(Integer.valueOf((String)map.get("year"))-1));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		
		for(Record recordQu:pageQu.getList()){
			//市区固定三家企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				//for循环去对应品种的
				String[] cbljhpinmc={"xm","jd","xd"};
				BigDecimal xj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcshiqu(map);
					xj=xj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", xj);
				//实际库存
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					//返回库存记录数(判断查询定时保存的库存还是实时库存)
					/*if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKc(map);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKc(map);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupz(map);
					if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
						linshiweight=new BigDecimal(0);
					}
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
				//占比  小麦 1/2    粳稻1/4      籼稻1/3 
				HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xj"),recordQu.getBigDecimal("shijixj"),2);
				HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xm"),recordQu.getBigDecimal("shijixm"),2);
				HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("jd"),recordQu.getBigDecimal("shijijd"),4);
				HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xd"),recordQu.getBigDecimal("shijixd"),3);
				recordQu.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
				recordQu.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
				recordQu.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
				recordQu.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
				recordQu.set("qyzzjgdm",recordQu.get("qyzzjgdm"));
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				String[] cbljhpinmc={"xm","jd","xd"};
				BigDecimal xj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
					xj=xj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", xj);
				//实际库存
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					/*if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKcBydiqu(testMap);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcBydiqu(testMap);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwudiqupz(testMap);
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
				HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xj"),recordQu.getBigDecimal("shijixj"),2);
				HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xm"),recordQu.getBigDecimal("shijixm"),2);
				HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("jd"),recordQu.getBigDecimal("shijijd"),4);
				HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xd"),recordQu.getBigDecimal("shijixd"),3);
				recordQu.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
				recordQu.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
				recordQu.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
				recordQu.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
				recordQu.set("qyzzjgdm",testMap.get("qyzzjgdm"));
			}
			
			//将查询条件放到每一行里
			recordQu.set("niandu", map.get("year"));
			recordQu.set("xingzhi", map.get("xingzhi"));
			recordQu.set("xzqhdm", recordQu.get("xzqhdm"));
			recordQu.set("kdbm",map.get("kdbm"));
			recordQu.set("cfbm",map.get("cfbm"));
			recordQu.set("month",map.get("month"));
			recordQu.set("pzclass",map.get("pzclass"));
			recordQu.set("pinzhong",pzmap.get("pinzhong"));
			
	}
		return pageQu;
		
		
	}
	@Before(AutoPageInterceptor.class)
	public static Page<Record> cblswkck(HashMap<String, Object> map) throws Exception {
		
		//将二级企业库点转换成qyzzjgdm和kdbm(市属企业直接查库点未加-但是不影响)
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)map.get("year"),(String)map.get("month"));
		//int year=Integer.parseInt((String)map.get("year"));
		//int month=Integer.parseInt((String)map.get("month"));
		//当月结算时间
		//String time= ChuBeiLiangJHDAO.getLastDayOfMonth(year,month);
		//map.put("month", time);
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		Calendar c = Calendar.getInstance();
		//map.put("yearsub",c.get(Calendar.YEAR));
		map.put("year",c.get(Calendar.YEAR));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		map.put("yearsub", String.valueOf(c.get(Calendar.YEAR)));
		//map.put("yearsub", String.valueOf(Integer.valueOf((String)map.get("year"))-1));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		
		for(Record recordQu:pageQu.getList()){
			//市区固定三家企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				//for循环去对应品种的
				String[] cbljhpinmc={"xm","jd","xd"};
				BigDecimal xj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcshiqu(map);
					xj=xj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", xj);
				//实际库存
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					//返回库存记录数(判断查询定时保存的库存还是实时库存)
				/*	if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKc(map);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhNjptKc(map);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupz(map);
					if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
						linshiweight=new BigDecimal(0);
					}
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
				//占比  小麦 1/2    粳稻1/4      籼稻1/3 
				HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xj"),recordQu.getBigDecimal("shijixj"),2);
				HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xm"),recordQu.getBigDecimal("shijixm"),2);
				HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("jd"),recordQu.getBigDecimal("shijijd"),4);
				HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xd"),recordQu.getBigDecimal("shijixd"),3);
				recordQu.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
				recordQu.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
				recordQu.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
				recordQu.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
				recordQu.set("qyzzjgdm",recordQu.get("qyzzjgdm"));
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				String[] cbljhpinmc={"xm","jd","xd"};
				BigDecimal xj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
					xj=xj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", xj);
				//实际库存
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					/*if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKcBydiqu(testMap);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhNjptKcBydiqu(testMap);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwudiqupz(testMap);
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
				HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xj"),recordQu.getBigDecimal("shijixj"),2);
				HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xm"),recordQu.getBigDecimal("shijixm"),2);
				HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("jd"),recordQu.getBigDecimal("shijijd"),4);
				HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(recordQu.getBigDecimal("xd"),recordQu.getBigDecimal("shijixd"),3);
				recordQu.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
				recordQu.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
				recordQu.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
				recordQu.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
				recordQu.set("qyzzjgdm",testMap.get("qyzzjgdm"));
			}
			
			//将查询条件放到每一行里
			recordQu.set("niandu", map.get("year"));
			recordQu.set("xingzhi", map.get("xingzhi"));
			recordQu.set("xzqhdm", recordQu.get("xzqhdm"));
			recordQu.set("kdbm",map.get("kdbm"));
			recordQu.set("cfbm",map.get("cfbm"));
			recordQu.set("month",map.get("month"));
			recordQu.set("pzclass",map.get("pzclass"));
			recordQu.set("pinzhong",pzmap.get("pinzhong"));
			
	}
		return pageQu;
		
		
	}
	/**
	 * 按年度查询一级企业
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public static Page<Record> findcompany(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and q.xzqhdm=?", "xzqhdm");
		p.put("and c.qyzzjgdm = ?", "shishuqiye");
		p.put("and c.diqu not in (?) ", "name"); 
		p.put("and c.niandu=?", "niandu"); 
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select b.xzqhdm,a.name,c.qymc,c.qyzzjgdm ",
				"from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 "+ s.getSql()+" order by c.orderno,c.qyzzjgdm ",s.getParam().toArray(new Object[0]));
		return page;
	}
	/**
	 * 取所有一级企业
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public static List<Record> findallcompany(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and c.qyzzjgdm = ?", "qyzzjgdm");
		p.put("and c.diqu=?", "diqu"); 
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				1,
				10000,
				"select aa.* ",
				" from (select DISTINCT b.orderno,c.qyzzjgdm,c.qymc from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 "+ s.getSql()+" GROUP BY b.orderno,c.qyzzjgdm,c.qymc   ) as aa ORDER BY aa.orderno ",s.getParam().toArray(new Object[0]));
		//List<Record> list=Db.find("select DISTINCT b.orderno,c.qyzzjgdm,c.qymc from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 GROUP BY b.orderno,c.qyzzjgdm,c.qymc  ORDER BY b.orderno ");
		return page.getList();
	}
	@Before(AutoPageInterceptor.class)
	public static Page<Record> findcompanyy(HashMap<String, Object> param) throws Exception {
		Param p = new Param();
		p.put("and q.xzqhdm=?", "xzqhdm");
		p.put("and c.qyzzjgdm = ?", "shishuqiye");
		p.put("and c.diqu=? ", "name"); 
		p.put("and c.niandu=?", "niandu"); 
		SqlAndParam s = SqlUtil.buildSql(p, param);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(param.get("page"))),
				Integer.valueOf(String.valueOf(param.get("rows"))),
				"select b.xzqhdm,a.name,c.qymc,c.qyzzjgdm ",
				"from fw_area a inner JOIN njpt_diqu b on a.id=b.areaid INNER JOIN njpt_chubeiliangjihua_diqu c on c.diqu=a.name where 1=1 "+ s.getSql()+" order by c.orderno,c.qyzzjgdm ",s.getParam().toArray(new Object[0]));
		return page;
	}
	public Page<Record> shichuqiye(HashMap<String, Object> map) {
		
		
		return ChuBeiLiangJHDAO.shichuqiye(map);
		
	}
	public List<Record> findcckd(HashMap<String, Object> map) {
		String qyzzjgdm = (String) map.get("qyzzjgdm");
		String sql;
		List<Record> list = null;
		if("".equals(qyzzjgdm)||null==qyzzjgdm){
			
		}else{
			sql = "select a.kdbm,a.kdmc from tz_kudian a INNER JOIN njpt_chubeiliangjihua b  on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd where a.qyzzjgdm = ?  GROUP BY a.qyzzjgdm,a.kdbm,a.kdmc";
			list = Db.find(sql, qyzzjgdm);
		}
		return list;
	}
	/**
	 * 查询二级企业或库点
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public static List<Record> finderjiqiyekd(HashMap<String, Object> map) throws Exception {
		Record xzqhdm=ChuBeiLiangJHDAO.findisssqy(map);
		List<Record> qiyekdList=new ArrayList<>();
		if("市区".equals(xzqhdm.getStr("diqu"))){
			Page<Record> ssqy=ChuBeiLiangJHDAO.findssqykd(map);
			return ssqy.getList();
		}else{
			map.put("name", xzqhdm.get("diqu"));
			Page<Record> erjiqy=ChuBeiLiangJHDAO.finddiquqykd(map);
			for(Record record:erjiqy.getList()){
				qiyekdList.add(new Record().set("kdbm", record.get("qyzzjgdm")).set("kdmc", record.get("qymc")));
				map.put("qyzzjgdm", record.get("qyzzjgdm"));
				Page<Record> erjiqykd=ChuBeiLiangJHDAO.finddqqykd(map);
				for(Record kdRecord:erjiqykd.getList()){
					String kdbm=record.getStr("qyzzjgdm")+"-"+kdRecord.getStr("kdbm");
					String kdmc="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+kdRecord.getStr("kdmc");
					qiyekdList.add(new Record().set("kdbm",kdbm).set("kdmc", kdmc));
				}
			}
			
			return qiyekdList;
		}
	}
	public List<Record> selectcfmc(HashMap<String, Object> map) {
		String qyzzjgdm = (String) map.get("qyzzjgdm");
		String kdbm = (String) map.get("kdbm");
		String sql;
		List<Record> list = null;
		if("".equals(qyzzjgdm)&&"".equals(kdbm)){
			
		}else{
			sql = "select a.cfbm,a.cfmc from tz_cangfang a INNER JOIN njpt_chubeiliangjihua b on a.qyzzjgdm=b.ccqy and a.kdbm=b.cckd and a.cfbm=b.ccch where a.qyzzjgdm=? and a.kdbm=? GROUP BY a.qyzzjgdm,a.kdbm,a.cfbm,a.cfmc ";
			list = Db.find(sql, qyzzjgdm,kdbm);
		}
		return list;
	}
	/**
	 * 生成实物库存合计
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public static Record shiwukucunheji(HashMap<String, Object> map) throws Exception {
		//将二级企业库点转换成qyzzjgdm和kdbm
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		//int year=Integer.parseInt((String)map.get("year"));
		//int month=Integer.parseInt((String)map.get("month"));
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)map.get("year"),(String)map.get("month"));
		//String time= ChuBeiLiangJHDAO.getLastDayOfMonth(year,month);
		//map.put("month", time);
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		String[] cbljhpinmc={"xm","jd","xd"};
		String[] pinzhongmc={"shijixm","shijijd","shijixd"};
		Calendar c=Calendar.getInstance();
		map.put("year",c.get(Calendar.YEAR));
		map.put("yearsub", String.valueOf(c.get(Calendar.YEAR)));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		//for循环去对应区的数量（市区只去三家企业）
		BigDecimal xm=new BigDecimal(0);
		BigDecimal jd=new BigDecimal(0);
		BigDecimal xd=new BigDecimal(0);
		BigDecimal xj=new BigDecimal(0);
		BigDecimal shijixm=new BigDecimal(0);
		BigDecimal shijijd=new BigDecimal(0);
		BigDecimal shijixd=new BigDecimal(0);
		BigDecimal shijixj=new BigDecimal(0);
		for(Record recordQu:pageQu.getList()){
			//市区固定三家企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcshiqu(map);
					cbljhxj=cbljhxj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", cbljhxj);
				//实际库存--市区三家企业
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
						linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKc(map);
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupz(map);
					if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
						linshiweight=new BigDecimal(0);
					}
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
					cbljhxj=cbljhxj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", cbljhxj);
				//实际库存--市区三家企业
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
						linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcBydiqu(testMap);
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwudiqupz(testMap);
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
			}
			xm=xm.add(recordQu.get("xm"));
			jd=jd.add(recordQu.get("jd"));
			xd=xd.add(recordQu.get("xd"));
			xj=xj.add(recordQu.get("xj"));
			shijixm=shijixm.add(recordQu.get("shijixm"));
			shijijd=shijijd.add(recordQu.get("shijijd"));
			shijixd=shijixd.add(recordQu.get("shijixd"));
			shijixj=shijixj.add(recordQu.get("shijixj"));
			
	}
		//转换占比和颜色
		HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(xj,shijixj,2);
		HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(xm,shijixm,2);
		HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(jd,shijijd,4);
		HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(xd,shijixd,3);
		Record heji=new Record();
		heji.set("xm", xm);
		heji.set("jd", jd);
		heji.set("xd", xd);
		heji.set("xj", xj);
		heji.set("shijixm", shijixm);
		heji.set("shijijd", shijijd);
		heji.set("shijixd", shijixd);
		heji.set("shijixj", shijixj);
		heji.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
		heji.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
		heji.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
		heji.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
		return heji;
	}
	/**
	 * 生成实物库存合计
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public static Record shiwukucunhejixinxi(HashMap<String, Object> map) throws Exception {
		//将二级企业库点转换成qyzzjgdm和kdbm
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		//int year=Integer.parseInt((String)map.get("year"));
		//int month=Integer.parseInt((String)map.get("month"));
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)map.get("year"),(String)map.get("month"));
		//String time= ChuBeiLiangJHDAO.getLastDayOfMonth(year,month);
		//map.put("month", time);
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		String[] cbljhpinmc={"xm","jd","xd"};
		String[] pinzhongmc={"shijixm","shijijd","shijixd"};
		Calendar c=Calendar.getInstance();
		map.put("year",c.get(Calendar.YEAR));
		//map.put("yearsub", String.valueOf(Integer.valueOf((String)map.get("year"))-1));
		map.put("yearsub", String.valueOf(c.get(Calendar.YEAR)));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		//for循环去对应区的数量（市区只去三家企业）
		BigDecimal xm=new BigDecimal(0);
		BigDecimal jd=new BigDecimal(0);
		BigDecimal xd=new BigDecimal(0);
		BigDecimal xj=new BigDecimal(0);
		BigDecimal shijixm=new BigDecimal(0);
		BigDecimal shijijd=new BigDecimal(0);
		BigDecimal shijixd=new BigDecimal(0);
		BigDecimal shijixj=new BigDecimal(0);
		for(Record recordQu:pageQu.getList()){
			//市区固定三家企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcshiqu(map);
					cbljhxj=cbljhxj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", cbljhxj);
				//实际库存--市区三家企业
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					map.put("pinzhong", cbljhpz[i]);
					/*if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKc(map);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhNjptKc(map);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupz(map);
					if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
						linshiweight=new BigDecimal(0);
					}
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshijihua=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					linshijihua=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
					cbljhxj=cbljhxj.add(linshijihua);
					recordQu.set(cbljhpinmc[i], linshijihua);
				}
				recordQu.set("xj", cbljhxj);
				//实际库存--市区三家企业
				BigDecimal xiaoji=new BigDecimal(0);
				for(int i=0;i<3;i++){
					BigDecimal linshiweight=new BigDecimal(0);
					testMap.put("pinzhong", cbljhpz[i]);
					/*if(bool){
						linshiweight=ChuBeiLiangJHDAO.cbljhPastKcBydiqu(testMap);
					}else{*/
						linshiweight=ChuBeiLiangJHDAO.cbljhNjptKcBydiqu(testMap);
					//}
					//linshiweight=ChuBeiLiangJHDAO.cbljhrenwudiqupz(testMap);
					xiaoji=xiaoji.add(linshiweight);
					recordQu.set(pinzhongmc[i], linshiweight);
				}
				recordQu.set("shijixj", xiaoji);
			}
			xm=xm.add(recordQu.get("xm"));
			jd=jd.add(recordQu.get("jd"));
			xd=xd.add(recordQu.get("xd"));
			xj=xj.add(recordQu.get("xj"));
			shijixm=shijixm.add(recordQu.get("shijixm"));
			shijijd=shijijd.add(recordQu.get("shijijd"));
			shijixd=shijixd.add(recordQu.get("shijixd"));
			shijixj=shijixj.add(recordQu.get("shijixj"));
			
	}
		//转换占比和颜色
		HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(xj,shijixj,2);
		HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(xm,shijixm,2);
		HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(jd,shijijd,4);
		HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(xd,shijixd,3);
		Record heji=new Record();
		heji.set("xm", xm);
		heji.set("jd", jd);
		heji.set("xd", xd);
		heji.set("xj", xj);
		heji.set("shijixm", shijixm);
		heji.set("shijijd", shijijd);
		heji.set("shijixd", shijixd);
		heji.set("shijixj", shijixj);
		heji.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
		heji.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
		heji.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
		heji.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
		return heji;
	}
	/**
	 * 生成储备量计划合计
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public static Record cbljhheji(HashMap<String, Object> map) throws Exception {
		//将二级企业库点转换成qyzzjgdm和kdbm
		String qykdStr=(String)map.get("erjiqiyekd");
		String[] qyandkd={"",""};
		if(qykdStr!=null||"".equals(qykdStr)){
			int con=qykdStr.indexOf("-");
			if(con==-1){
				map.put("qyzzjgdm", qykdStr);
			}else{
				//代表qyzzjgdm和kdbm
				qyandkd=qykdStr.split("-");
				map.put("qyzzjgdm", qyandkd[0]);
				map.put("kdbm", qyandkd[1]);
			}
		}
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", map.get("pinzhong"));
		pzmap.put("xingzhi", map.get("xingzhi"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
		String[] xz= ChuBeiLiangJHDAO.swkccheckxz(pzmap);
		String[] cbljhsjmc={"xm","jd","xd"};
		String[] cbljhxjmc={"xianjixm","xianjijd","xianjixd"};
		map.put("yearsub", String.valueOf(Integer.valueOf((String)map.get("year"))-1));
		HashMap<String, Object> testMap = (HashMap<String, Object>) map.clone();
		//得到所有一级企业
		Page<Record> pageQu=ChuBeiLiangJHDAO.cblswkcyijiqiye(map);
		//for循环去对应区的数量（市属企业）
		BigDecimal xm=new BigDecimal(0);
		BigDecimal jd=new BigDecimal(0);
		BigDecimal xd=new BigDecimal(0);
		BigDecimal xj=new BigDecimal(0);
		BigDecimal xianjixm=new BigDecimal(0);
		BigDecimal xianjijd=new BigDecimal(0);
		BigDecimal xianjixd=new BigDecimal(0);
		BigDecimal xianjixj=new BigDecimal(0);
		for(Record recordQu:pageQu.getList()){
			//市区固定三家企业
			if("210008".equals(recordQu.get("xzqhdm"))){
				//承储计划--市属企业
				map.put("qyzzjgdm", recordQu.get("qyzzjgdm"));
				BigDecimal cbljhsj=new BigDecimal(0);
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int j=0;j<2;j++){
					map.put("xingzhi", xz[j]);
					for(int i=0;i<3;i++){
						map.put("pinzhong", cbljhpz[i]);
						//性质-品种
						BigDecimal jihuashiji=new BigDecimal(0);
						jihuashiji=ChuBeiLiangJHDAO.cblswkcshiqu(map);
						if(j==0){
							cbljhsj=cbljhsj.add(jihuashiji);
							recordQu.set(cbljhsjmc[i], jihuashiji);
						}else if(j==1){
							cbljhxj=cbljhxj.add(jihuashiji);
							recordQu.set(cbljhxjmc[i], jihuashiji);
						}
					}
				}
				recordQu.set("xj", cbljhsj);
				recordQu.set("xianjixj", cbljhxj);
				
			}else {
				//地区
				testMap.put("xzqhdm", recordQu.get("xzqhdm"));
				BigDecimal cbljhsj=new BigDecimal(0);
				BigDecimal cbljhxj=new BigDecimal(0);
				for(int j=0;j<2;j++){
					testMap.put("xingzhi", xz[j]);
					for(int i=0;i<3;i++){
						testMap.put("pinzhong", cbljhpz[i]);
						//性质-品种
						BigDecimal jihuashiji=new BigDecimal(0);
						jihuashiji=ChuBeiLiangJHDAO.cblswkcdiqu(testMap);
						if(j==0){
							cbljhsj=cbljhsj.add(jihuashiji);
							recordQu.set(cbljhsjmc[i], jihuashiji);
						}else if(j==1){
							cbljhxj=cbljhxj.add(jihuashiji);
							recordQu.set(cbljhxjmc[i], jihuashiji);
						}
					}
				}
				recordQu.set("xj", cbljhsj);
				recordQu.set("xianjixj", cbljhxj);
			}
			xm=xm.add(recordQu.get("xm"));
			jd=jd.add(recordQu.get("jd"));
			xd=xd.add(recordQu.get("xd"));
			xj=xj.add(recordQu.get("xj"));
			xianjixm=xianjixm.add(recordQu.get("xianjixm"));
			xianjijd=xianjijd.add(recordQu.get("xianjijd"));
			xianjixd=xianjixd.add(recordQu.get("xianjixd"));
			xianjixj=xianjixj.add(recordQu.get("xianjixj"));
	}
		Record heji=new Record();
		heji.set("xm", xm);
		heji.set("jd", jd);
		heji.set("xd", xd);
		heji.set("xj", xj);
		heji.set("xianjixm", xianjixm);
		heji.set("xianjijd", xianjijd);
		heji.set("xianjixd", xianjixd);
		heji.set("xianjixj", xianjixj);
		return heji;
	}
	private static Object[] String(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 实物库存-点击区域展开二级企业
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakankudian(HashMap<String, Object> queryparam) throws Exception {
		
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", queryparam.get("pinzhong"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap); //品种代码名称
		String[] cbljhpinmc={"xm","jd","xd"};				   //代表储备计划分品种数量
		String[] pinzhongmc={"shijixm","shijijd","shijixd"};	//代表实际库存分品种数量
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
		Calendar c = Calendar.getInstance();
		//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
		queryparam.put("niandusub", String.valueOf(c.get(Calendar.YEAR)));
		//获取有储备粮计划的企业
		Page<Record> cblqiye=ChuBeiLiangJHDAO.cblqiye(queryparam);
		//for循环企业得到实物库存
		for(Record qiyerecord:cblqiye.getList()){
			//储备粮计划数
			queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
			
			BigDecimal cbljhxj=new BigDecimal(0);
			for(int i=0;i<3;i++){
				BigDecimal linshijihua=new BigDecimal(0);
				queryparam.put("pinzhong", cbljhpz[i]);
				linshijihua=ChuBeiLiangJHDAO.cbljhqiye(queryparam);
				cbljhxj=cbljhxj.add(linshijihua);
				qiyerecord.set(cbljhpinmc[i], linshijihua);
			}
			qiyerecord.set("xj", cbljhxj);
			/*Page<Record> cbljhqiye=ChuBeiLiangJHDAO.cbljhqiye(queryparam);
			if(cbljhqiye.getList().size()>0){
				qiyerecord.setColumns(cbljhqiye.getList().get(0));
			}*/
			//实际库存数
			BigDecimal xiaoji=new BigDecimal(0);
			for(int i=0;i<3;i++){
				BigDecimal linshiweight=new BigDecimal(0);
				queryparam.put("pinzhong", cbljhpz[i]);
					//linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcToqiye(queryparam);
					linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcToKuDian(queryparam);
				//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbyqiye(queryparam);
				if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
					linshiweight=new BigDecimal(0);
				}
				xiaoji=xiaoji.add(linshiweight);
				qiyerecord.set(pinzhongmc[i], linshiweight);
			}
			qiyerecord.set("shijixj", xiaoji);
			HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
			HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
			HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
			HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
			qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
			qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
			qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
			qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
			
			//将查询条件放到每一行里
			qiyerecord.set("niandu", queryparam.get("niandu"));
			qiyerecord.set("xingzhi", queryparam.get("xingzhi"));
			qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
			qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
			qiyerecord.set("kdbm",queryparam.get("kdbm"));
			qiyerecord.set("cfbm",queryparam.get("cfbm"));
			qiyerecord.set("pzclass",queryparam.get("pzclass"));
			qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
			qiyerecord.set("month",queryparam.get("month"));
		}
		return cblqiye;
	}
	/**
	 * 实物库存-点击区域展开二级企业
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakankudiankc(HashMap<String, Object> queryparam) throws Exception {
		
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", queryparam.get("pinzhong"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap); //品种代码名称
		String[] cbljhpinmc={"xm","jd","xd"};				   //代表储备计划分品种数量
		String[] pinzhongmc={"shijixm","shijijd","shijixd"};	//代表实际库存分品种数量
		//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
		//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
		Calendar c=Calendar.getInstance();
		queryparam.put("niandusub", String.valueOf(c.get(Calendar.YEAR)));
		//获取有储备粮计划的企业
		Page<Record> cblqiye=ChuBeiLiangJHDAO.cblqiye(queryparam);
		//for循环企业得到实物库存
		for(Record qiyerecord:cblqiye.getList()){
			//储备粮计划数
			queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
			
			BigDecimal cbljhxj=new BigDecimal(0);
			for(int i=0;i<3;i++){
				BigDecimal linshijihua=new BigDecimal(0);
				queryparam.put("pinzhong", cbljhpz[i]);
				linshijihua=ChuBeiLiangJHDAO.cbljhqiye(queryparam);
				cbljhxj=cbljhxj.add(linshijihua);
				qiyerecord.set(cbljhpinmc[i], linshijihua);
			}
			qiyerecord.set("xj", cbljhxj);
			/*Page<Record> cbljhqiye=ChuBeiLiangJHDAO.cbljhqiye(queryparam);
			if(cbljhqiye.getList().size()>0){
				qiyerecord.setColumns(cbljhqiye.getList().get(0));
			}*/
			//实际库存数
			BigDecimal xiaoji=new BigDecimal(0);
			for(int i=0;i<3;i++){
				BigDecimal linshiweight=new BigDecimal(0);
				queryparam.put("pinzhong", cbljhpz[i]);
				/*if(bool){
					//linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToqiye(queryparam);
					linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToKuDian(queryparam);
				}else{*/
					//linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcToqiye(queryparam);
					linshiweight=ChuBeiLiangJHDAO.cbljhNjptToKuDian(queryparam);
				//}
				//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbyqiye(queryparam);
				if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
					linshiweight=new BigDecimal(0);
				}
				xiaoji=xiaoji.add(linshiweight);
				qiyerecord.set(pinzhongmc[i], linshiweight);
			}
			qiyerecord.set("shijixj", xiaoji);
			HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
			HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
			HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
			HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
			qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
			qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
			qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
			qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
			
			//将查询条件放到每一行里
			qiyerecord.set("niandu", queryparam.get("niandu"));
			qiyerecord.set("xingzhi", queryparam.get("xingzhi"));
			qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
			qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
			qiyerecord.set("kdbm",queryparam.get("kdbm"));
			qiyerecord.set("cfbm",queryparam.get("cfbm"));
			qiyerecord.set("pzclass",queryparam.get("pzclass"));
			qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
			qiyerecord.set("month",queryparam.get("month"));
		}
		return cblqiye;
	}
	/**
	 * 储备粮计划-点击区域展开二级企业
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> ckcbljherjiqiye(HashMap<String, Object> queryparam) throws Exception {

		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", queryparam.get("pinzhong"));
		pzmap.put("xingzhi", queryparam.get("xingzhi"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap); //品种代码名称
		String[] xz= ChuBeiLiangJHDAO.swkccheckxz(pzmap);
		String[] cbljhsjmc={"xm","jd","xd"};
		String[] cbljhxjmc={"xianjixm","xianjijd","xianjixd"};
		queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
		//获取有储备粮计划的企业
		Page<Record> cblqiye=ChuBeiLiangJHDAO.cblqiye(queryparam);
		//for循环企业得到实物库存
		for(Record qiyerecord:cblqiye.getList()){
			//储备粮计划数
			queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
			
			BigDecimal cbljhsj=new BigDecimal(0);
			BigDecimal cbljhxj=new BigDecimal(0);
			for(int j=0;j<2;j++){
				queryparam.put("xingzhi", xz[j]);
				for(int i=0;i<3;i++){
					queryparam.put("pinzhong", cbljhpz[i]);
					//性质-品种
					BigDecimal jihuashiji=new BigDecimal(0);
					jihuashiji=ChuBeiLiangJHDAO.cbljhqiye(queryparam);
					if(j==0){
						cbljhsj=cbljhsj.add(jihuashiji);
						qiyerecord.set(cbljhsjmc[i], jihuashiji);
					}else if(j==1){
						cbljhxj=cbljhxj.add(jihuashiji);
						qiyerecord.set(cbljhxjmc[i], jihuashiji);
					}
				}
			}
			qiyerecord.set("xj", cbljhsj);
			qiyerecord.set("xianjixj", cbljhxj);
			
			//将查询条件放到每一行里
			qiyerecord.set("niandu", queryparam.get("niandu"));
			qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
			qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
			qiyerecord.set("kdbm",queryparam.get("kdbm"));
			qiyerecord.set("cfbm",queryparam.get("cfbm"));
			qiyerecord.set("pzclass",queryparam.get("pzclass"));
			qiyerecord.set("xingzhi", pzmap.get("xingzhi"));
			qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
		}
		return cblqiye;
	}
	/**
	 * 实物库存-点击二级企业展开库点
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakankudian2(HashMap<String, Object> queryparam) throws Exception {
				HashMap<String, Object> pzmap=new HashMap<>();
				pzmap.put("pinzhong", queryparam.get("pinzhong"));
				String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);//品种代码-数组
				String[] cbljhpinmc={"xm","jd","xd"};				  //代表储备粮计划数组
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};  //代表实际库存数组
				//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
				Calendar a=Calendar.getInstance();
				queryparam.put("niandusub", String.valueOf(a.get(Calendar.YEAR)));
				//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
				//获取有储备粮计划的企业
				Page<Record> cblqiye=ChuBeiLiangJHDAO.cblkudian(queryparam);
				//for循环企业得到实物库存
				for(Record qiyerecord:cblqiye.getList()){
					queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
					queryparam.put("kdbm",qiyerecord.get("cckd"));
					//储备粮计划数

					BigDecimal cbljhxj=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshijihua=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						linshijihua=ChuBeiLiangJHDAO.cblswkckudian(queryparam);
						cbljhxj=cbljhxj.add(linshijihua);
						qiyerecord.set(cbljhpinmc[i], linshijihua);
					}
					qiyerecord.set("xj", cbljhxj);
					
					//实际库存数
					BigDecimal xiaoji=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshiweight=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						/*if(bool){
							linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToKuDian(queryparam);
						}else{*/
							linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcToKuDian(queryparam);
						//}
						//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbykudian(queryparam);
						if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
							linshiweight=new BigDecimal(0);
						}
						xiaoji=xiaoji.add(linshiweight);
						qiyerecord.set(pinzhongmc[i], linshiweight);
					}
					qiyerecord.set("shijixj", xiaoji);
					HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
					HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
					HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
					HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
					qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
					qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
					qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
					qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
					
					//将查询条件放到每一行里
					qiyerecord.set("niandu", queryparam.get("niandu"));
					qiyerecord.set("month", queryparam.get("month"));
					qiyerecord.set("xingzhi", queryparam.get("xingzhi"));
					qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
					qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
					qiyerecord.set("kdbm",qiyerecord.get("cckd"));
					qiyerecord.set("cfbm",queryparam.get("cfbm"));
					qiyerecord.set("pzclass",queryparam.get("pzclass"));
					qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
				}
				return cblqiye;
	}
	/**
	 * 实物库存-点击二级企业展开库点
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakankudian2kc(HashMap<String, Object> queryparam) throws Exception {
				HashMap<String, Object> pzmap=new HashMap<>();
				pzmap.put("pinzhong", queryparam.get("pinzhong"));
				String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);//品种代码-数组
				String[] cbljhpinmc={"xm","jd","xd"};				  //代表储备粮计划数组
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};  //代表实际库存数组
				//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
				//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
				Calendar c=Calendar.getInstance();
				queryparam.put("niandusub", String.valueOf(c.get(Calendar.YEAR)));
				//获取有储备粮计划的企业
				Page<Record> cblqiye=ChuBeiLiangJHDAO.cblkudian(queryparam);
				//for循环企业得到实物库存
				for(Record qiyerecord:cblqiye.getList()){
					queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
					queryparam.put("kdbm",qiyerecord.get("cckd"));
					//储备粮计划数

					BigDecimal cbljhxj=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshijihua=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						linshijihua=ChuBeiLiangJHDAO.cblswkckudian(queryparam);
						cbljhxj=cbljhxj.add(linshijihua);
						qiyerecord.set(cbljhpinmc[i], linshijihua);
					}
					qiyerecord.set("xj", cbljhxj);
					
					//实际库存数
					BigDecimal xiaoji=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshiweight=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						/*if(bool){
							linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToKuDian(queryparam);
						}else{*/
							linshiweight=ChuBeiLiangJHDAO.cbljhNjptToKuDian(queryparam);
						//}
						//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbykudian(queryparam);
						if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
							linshiweight=new BigDecimal(0);
						}
						xiaoji=xiaoji.add(linshiweight);
						qiyerecord.set(pinzhongmc[i], linshiweight);
					}
					qiyerecord.set("shijixj", xiaoji);
					HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
					HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
					HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
					HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
					qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
					qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
					qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
					qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
					
					//将查询条件放到每一行里
					qiyerecord.set("niandu", queryparam.get("niandu"));
					qiyerecord.set("month", queryparam.get("month"));
					qiyerecord.set("xingzhi", queryparam.get("xingzhi"));
					qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
					qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
					qiyerecord.set("kdbm",qiyerecord.get("cckd"));
					qiyerecord.set("cfbm",queryparam.get("cfbm"));
					qiyerecord.set("pzclass",queryparam.get("pzclass"));
					qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
				}
				return cblqiye;
	}
	public static Page<Record> chakanecbljhkd(HashMap<String, Object> queryparam) throws Exception {
		HashMap<String, Object> pzmap=new HashMap<>();
		pzmap.put("pinzhong", queryparam.get("pinzhong"));
		pzmap.put("xingzhi", queryparam.get("xingzhi"));
		String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);//品种代码-数组
		String[] xz= ChuBeiLiangJHDAO.swkccheckxz(pzmap);
		String[] cbljhsjmc={"xm","jd","xd"};
		String[] cbljhxjmc={"xianjixm","xianjijd","xianjixd"};
		queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
		//获取有储备粮计划的企业
		Page<Record> cblqiye=ChuBeiLiangJHDAO.cblkudian(queryparam);
		//for循环企业得到实物库存
		for(Record qiyerecord:cblqiye.getList()){
			queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
			queryparam.put("kdbm",qiyerecord.get("cckd"));
			//储备粮计划数
			BigDecimal cbljhsj=new BigDecimal(0);
			BigDecimal cbljhxj=new BigDecimal(0);
			for(int j=0;j<2;j++){
				queryparam.put("xingzhi", xz[j]);
				for(int i=0;i<3;i++){
					queryparam.put("pinzhong", cbljhpz[i]);
					//性质-品种
					BigDecimal jihuashiji=new BigDecimal(0);
					jihuashiji=ChuBeiLiangJHDAO.cblswkckudian(queryparam);
					if(j==0){
						cbljhsj=cbljhsj.add(jihuashiji);
						qiyerecord.set(cbljhsjmc[i], jihuashiji);
					}else if(j==1){
						cbljhxj=cbljhxj.add(jihuashiji);
						qiyerecord.set(cbljhxjmc[i], jihuashiji);
					}
				}
			}
			qiyerecord.set("xj", cbljhsj);
			qiyerecord.set("xianjixj", cbljhxj);
			
			//将查询条件放到每一行里
			qiyerecord.set("niandu", queryparam.get("niandu"));
			qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
			qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
			qiyerecord.set("kdbm",qiyerecord.get("cckd"));
			qiyerecord.set("cfbm",queryparam.get("cfbm"));
			qiyerecord.set("pzclass",queryparam.get("pzclass"));
			qiyerecord.set("xingzhi", pzmap.get("xingzhi"));
			qiyerecord.set("pinzhong",pzmap.get("pinzhong"));
		}
		return cblqiye;
}
	/**
	 * 实物库存-点击库点展开仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakancangfang(HashMap<String, Object> queryparam) throws Exception {
				HashMap<String, Object> pzmap=new HashMap<>();
				pzmap.put("pinzhong", queryparam.get("pinzhong"));
				String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
				String[] cbljhpinmc={"xm","jd","xd"};
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
				Calendar a=Calendar.getInstance();
				queryparam.put("niandusub", String.valueOf(a.get(Calendar.YEAR)));
				//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
				//获取有储备粮计划的企业
				Page<Record> cblqiye=ChuBeiLiangJHDAO.cblcangfang(queryparam);
				//for循环企业得到实物库存
				for(Record qiyerecord:cblqiye.getList()){
					queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
					queryparam.put("kdbm",qiyerecord.get("cckd"));
					queryparam.put("cfbm", qiyerecord.get("ccch"));
					//获取年限
					Record ry=Db.findFirst("SELECT year,dmStock from kc_CurrentStock where 1=1 and qyzzjgdm='"+qiyerecord.get("ccqy")+"' and kdbm='"+ qiyerecord.get("cckd")+"' and vWareHouseCode='"+qiyerecord.get("ccch")+"' ");
						
						if(ry==null){
							queryparam.put("year", "");
						}else{
							queryparam.put("year", ry.get("year"));
						}
					
					//储备粮计划数
					BigDecimal cbljhxj=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshijihua=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						linshijihua=ChuBeiLiangJHDAO.cblswkccangfang(queryparam);
						cbljhxj=cbljhxj.add(linshijihua);
						qiyerecord.set(cbljhpinmc[i], linshijihua);
					}
					qiyerecord.set("xj", cbljhxj);
					
					//实际库存数
					String[] pinzhong=cbljhpz;
					BigDecimal xiaoji=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshiweight=new BigDecimal(0);
						queryparam.put("pinzhong", pinzhong[i]);
						/*if(bool){
							linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToKuDian(queryparam);
						}else{*/
							linshiweight=ChuBeiLiangJHDAO.cbljhCurrentKcToKuDian(queryparam);
						//}
						//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbycangfang(queryparam);
						if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
							linshiweight=new BigDecimal(0);
						}
						xiaoji=xiaoji.add(linshiweight);
						qiyerecord.set(pinzhongmc[i], linshiweight);
					}
					qiyerecord.set("shijixj", xiaoji);
					HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
					HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
					HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
					HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
					qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
					qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
					qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
					qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
					
					//添加xzqhdm，方便前台删除相应的tr
					qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
					qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
					qiyerecord.set("kdbm",qiyerecord.get("cckd"));
					qiyerecord.set("cfbm",queryparam.get("cfbm"));
					qiyerecord.set("nianxian", queryparam.get("year"));
				}
				return cblqiye;
	}
	/**
	 * 实物库存-点击库点展开仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakancangfangkc(HashMap<String, Object> queryparam) throws Exception {
				HashMap<String, Object> pzmap=new HashMap<>();
				pzmap.put("pinzhong", queryparam.get("pinzhong"));
				String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
				String[] cbljhpinmc={"xm","jd","xd"};
				String[] pinzhongmc={"shijixm","shijijd","shijixd"};
				//boolean bool= ChuBeiLiangJHBO.judgeSelectKC((String)queryparam.get("niandu"),(String)queryparam.get("month"));
				//queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
				Calendar c=Calendar.getInstance();
				queryparam.put("niandusub", String.valueOf(c.get(Calendar.YEAR)));
				//获取有储备粮计划的企业
				Page<Record> cblqiye=ChuBeiLiangJHDAO.cblcangfang(queryparam);
				//for循环企业得到实物库存
				for(Record qiyerecord:cblqiye.getList()){
					queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
					queryparam.put("kdbm",qiyerecord.get("cckd"));
					queryparam.put("cfbm", qiyerecord.get("ccch"));
					//获取年限
					Record ry=Db.findFirst("SELECT year from kc_CurrentStock where 1=1 and qyzzjgdm='"+qiyerecord.get("ccqy")+"' and kdbm='"+ qiyerecord.get("cckd")+"' and vWareHouseCode='"+qiyerecord.get("ccch")+"' ");
					if(ry==null){
						queryparam.put("year", "");
					}else{
						queryparam.put("year", ry.get("year"));
					}
					//储备粮计划数
					BigDecimal cbljhxj=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshijihua=new BigDecimal(0);
						queryparam.put("pinzhong", cbljhpz[i]);
						linshijihua=ChuBeiLiangJHDAO.cblswkccangfang(queryparam);
						cbljhxj=cbljhxj.add(linshijihua);
						qiyerecord.set(cbljhpinmc[i], linshijihua);
					}
					qiyerecord.set("xj", cbljhxj);
					
					//实际库存数
					String[] pinzhong=cbljhpz;
					BigDecimal xiaoji=new BigDecimal(0);
					for(int i=0;i<3;i++){
						BigDecimal linshiweight=new BigDecimal(0);
						queryparam.put("pinzhong", pinzhong[i]);
						/*if(bool){
							linshiweight=ChuBeiLiangJHDAO.cbljhPastKcToKuDian(queryparam);
						}else{*/
							linshiweight=ChuBeiLiangJHDAO.cbljhNjptToKuDian(queryparam);
						//}
						//linshiweight=ChuBeiLiangJHDAO.cbljhrenwupzbycangfang(queryparam);
						if(linshiweight.compareTo(new BigDecimal(0))== -1 ){
							linshiweight=new BigDecimal(0);
						}
						xiaoji=xiaoji.add(linshiweight);
						qiyerecord.set(pinzhongmc[i], linshiweight);
					}
					qiyerecord.set("shijixj", xiaoji);
					HashMap<String, Object> xjzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xj"),qiyerecord.getBigDecimal("shijixj"),2);
					HashMap<String, Object> xmzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xm"),qiyerecord.getBigDecimal("shijixm"),2);
					HashMap<String, Object> jdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("jd"),qiyerecord.getBigDecimal("shijijd"),4);
					HashMap<String, Object> xdzb=ChuBeiLiangJHDAO.baifenbi(qiyerecord.getBigDecimal("xd"),qiyerecord.getBigDecimal("shijixd"),3);
					qiyerecord.set("xjzb",xjzb.get("zb")).set("xjzbbool", xjzb.get("bool"));
					qiyerecord.set("xmzb",xmzb.get("zb")).set("xmzbbool", xmzb.get("bool"));
					qiyerecord.set("jdzb",jdzb.get("zb")).set("jdzbbool", jdzb.get("bool"));
					qiyerecord.set("xdzb",xdzb.get("zb")).set("xdzbbool", xdzb.get("bool"));
					
					//添加xzqhdm，方便前台删除相应的tr
					qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
					qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
					qiyerecord.set("kdbm",qiyerecord.get("cckd"));
					qiyerecord.set("cfbm",queryparam.get("cfbm"));
					qiyerecord.set("nianxian", queryparam.get("year"));
				}
				return cblqiye;
	}
	/**
	 * 储备粮计划-点击库点展开仓房
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> chakancbljhcf(HashMap<String, Object> queryparam) throws Exception {
				HashMap<String, Object> pzmap=new HashMap<>();
				pzmap.put("pinzhong", queryparam.get("pinzhong"));
				pzmap.put("xingzhi", queryparam.get("xingzhi"));
				String[] cbljhpz= ChuBeiLiangJHDAO.swkccheckpz(pzmap);
				String[] xz= ChuBeiLiangJHDAO.swkccheckxz(pzmap);
				String[] cbljhsjmc={"xm","jd","xd"};
				String[] cbljhxjmc={"xianjixm","xianjijd","xianjixd"};
				queryparam.put("niandusub", String.valueOf(Integer.valueOf((String)queryparam.get("niandu"))-1));
				//获取有储备粮计划的企业
				Page<Record> cblqiye=ChuBeiLiangJHDAO.cblcangfang(queryparam);
				//for循环企业得到实物库存
				for(Record qiyerecord:cblqiye.getList()){
					queryparam.put("qyzzjgdm",qiyerecord.get("ccqy"));
					queryparam.put("kdbm",qiyerecord.get("cckd"));
					queryparam.put("cfbm", qiyerecord.get("ccch"));
					BigDecimal cbljhsj=new BigDecimal(0);
					BigDecimal cbljhxj=new BigDecimal(0);
					for(int j=0;j<2;j++){
						queryparam.put("xingzhi", xz[j]);
						for(int i=0;i<3;i++){
							queryparam.put("pinzhong", cbljhpz[i]);
							//性质-品种
							BigDecimal jihuashiji=new BigDecimal(0);
							jihuashiji=ChuBeiLiangJHDAO.cblswkccangfang(queryparam);
							if(j==0){
								cbljhsj=cbljhsj.add(jihuashiji);
								qiyerecord.set(cbljhsjmc[i], jihuashiji);
							}else if(j==1){
								cbljhxj=cbljhxj.add(jihuashiji);
								qiyerecord.set(cbljhxjmc[i], jihuashiji);
							}
						}
					}
					qiyerecord.set("xj", cbljhsj);
					qiyerecord.set("xianjixj", cbljhxj);
					
					//添加xzqhdm，方便前台删除相应的tr
					qiyerecord.set("xzqhdm", queryparam.get("xzqhdm"));
					qiyerecord.set("qyzzjgdm",qiyerecord.get("ccqy"));
					qiyerecord.set("kdbm",qiyerecord.get("cckd"));
					qiyerecord.set("cfbm",queryparam.get("cfbm"));
				}
				return cblqiye;
	}
	/**
	 * 返回boolean true：查询定时保存的表， false：查询实时库存表
	 * @param string2 
	 * @param string 
	 * @param year
	 * @param month
	 * @return
	 */
	public static boolean judgeSelectKC(String year,String month) {
		boolean bool=true;
		Calendar now = Calendar.getInstance(); 
		Calendar condition = Calendar.getInstance();
		//设置年份
		condition.set(Calendar.YEAR,Integer.parseInt(year));
		//设置月份
		condition.set(Calendar.MONTH, Integer.parseInt(month)-1);
		//格式化日期
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat hour= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat timing= new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date nowDate = null;
		Date condDate = null;
		try {
			nowDate = day.parse(day.format(now.getTime()));
			condDate = day.parse(day.format(condition.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(condDate.compareTo(nowDate)<0){
			bool=true;
		}else if(condDate.compareTo(nowDate)>0){
			bool=false;
		}else{
			try {
				condDate=hour.parse(hour.format(now.getTime()));
				now.set(Calendar.DAY_OF_MONTH, 28);
				nowDate = hour.parse(timing.format(now.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(condDate.compareTo(nowDate)<=0){
				bool=false;
			}else{
				bool=true;
			}
		}
		return bool;
	}
	/**
	 * 获取储备粮计划审批
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public  Ret getcbljhsp(HashMap<String, Object> param) throws Exception {
		Page<Record> userPageRecord=ChuBeiLiangJHDAO.getcbljhsp(param);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 获取储备粮计划申请
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public  Ret getcbljhsq(HashMap<String, Object> param) throws Exception {
		Page<Record> userPageRecord=ChuBeiLiangJHDAO.getcbljhsq(param);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	/**
	 * 查询当前年二级企业--by qyzzjgdm
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public List<Record> chengchuku(HashMap<String, Object> map) throws Exception {
		Record xzqhdm=ChuBeiLiangJHDAO.findisssqy(map);
		List<Record> qiyekdList=new ArrayList<>();
		if("市区".equals(xzqhdm.getStr("diqu"))){
			Page<Record> ssqy=ChuBeiLiangJHDAO.findssqykd(map);
			return ssqy.getList();
		}else{
			map.put("name", xzqhdm.get("diqu"));
			Page<Record> erjiqy=ChuBeiLiangJHDAO.finddiquqykd(map);
			for(Record record:erjiqy.getList()){
				qiyekdList.add(new Record().set("kdbm", record.get("qyzzjgdm")).set("kdmc", record.get("qymc")));
				/*map.put("qyzzjgdm", record.get("qyzzjgdm"));
				Page<Record> erjiqykd=ChuBeiLiangJHDAO.finddqqykd(map);
				for(Record kdRecord:erjiqykd.getList()){
					String kdbm=record.getStr("qyzzjgdm")+"-"+kdRecord.getStr("kdbm");
					String kdmc="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+kdRecord.getStr("kdmc");
					qiyekdList.add(new Record().set("kdbm",kdbm).set("kdmc", kdmc));
				}*/
			}
			
			return qiyekdList;
		}
	}
	/**
	 * 查询当前年二级企业--by qyid
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public List<Record> chengchuku1(HashMap<String, Object> map) throws Exception {
		Record xzqhdm=ChuBeiLiangJHDAO.findisssqy1(map);
		List<Record> qiyekdList=new ArrayList<>();
		if("市区".equals(xzqhdm.getStr("diqu"))){
			Page<Record> ssqy=ChuBeiLiangJHDAO.findssqykd1(map);
			return ssqy.getList();
		}else{
			map.put("name", xzqhdm.get("diqu"));
			Page<Record> erjiqy=ChuBeiLiangJHDAO.finddiquqykd(map);
			for(Record record:erjiqy.getList()){
				qiyekdList.add(new Record().set("kdbm", record.get("qyzzjgdm")).set("kdmc", record.get("qymc")));
				/*map.put("qyzzjgdm", record.get("qyzzjgdm"));
				Page<Record> erjiqykd=ChuBeiLiangJHDAO.finddqqykd(map);
				for(Record kdRecord:erjiqykd.getList()){
					String kdbm=record.getStr("qyzzjgdm")+"-"+kdRecord.getStr("kdbm");
					String kdmc="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+kdRecord.getStr("kdmc");
					qiyekdList.add(new Record().set("kdbm",kdbm).set("kdmc", kdmc));
				}*/
			}
			
			return qiyekdList;
		}
	}
	/**
	 * 查询所有年二级企业--by qyzzjgdm
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public List<Record> allchengchuku(HashMap<String, Object> map) throws Exception {
		Record xzqhdm=ChuBeiLiangJHDAO.findallssqy(map);
		List<Record> qiyekdList=new ArrayList<>();
		if("市区".equals(xzqhdm.getStr("diqu"))){
			Page<Record> ssqy=ChuBeiLiangJHDAO.findallssqykd(map);
			return ssqy.getList();
		}else{
			map.put("name", xzqhdm.get("diqu"));
			Page<Record> erjiqy=ChuBeiLiangJHDAO.finddiquqykd(map);
			for(Record record:erjiqy.getList()){
				qiyekdList.add(new Record().set("kdbm", record.get("qyzzjgdm")).set("kdmc", record.get("qymc")));
				/*map.put("qyzzjgdm", record.get("qyzzjgdm"));
				Page<Record> erjiqykd=ChuBeiLiangJHDAO.finddqqykd(map);
				for(Record kdRecord:erjiqykd.getList()){
					String kdbm=record.getStr("qyzzjgdm")+"-"+kdRecord.getStr("kdbm");
					String kdmc="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+kdRecord.getStr("kdmc");
					qiyekdList.add(new Record().set("kdbm",kdbm).set("kdmc", kdmc));
				}*/
			}
			
			return qiyekdList;
		}
	}
	@Before(AutoPageInterceptor.class)
	public List<Record> chengchudian(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		Page<Record> erjiqykd=ChuBeiLiangJHDAO.finddqqykd(map);
		return erjiqykd.getList();
	}
	
}
