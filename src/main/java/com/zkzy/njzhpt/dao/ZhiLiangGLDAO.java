package com.zkzy.njzhpt.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ZhiLiangGLDAO {
	public static Page<Record> selectZLXQ(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and pu.vSwiftNumber=?", "vSwiftNumber");	
		p.put("and d.xzqhdm=?", "xzqhdm");				//行政区划代码
		p.put("and pu.qyzzjgdm=?", "qyzzjgdm");			//企业组织机构代码
		p.put("and pu.kdbm=?", "kdbm");					//库点编码
		p.put("and pu.vWareHouseCode=?", "vWareHouseCode");	//仓房编码
		p.put("and pu.dtQualityTime like ?", "dtQualityTime","%%%s%%");	//质检时间
		p.put("and pu.vDirection=?", "vDirection");		//出入库标示
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select fw.name ,q.qymc,pu.* ",
				"from crk_Purchase pu INNER JOIN tz_qiye q on pu.qyzzjgdm=q.qyzzjgdm LEFT JOIN njpt_diqu d ON q.xzqhdm= d.xzqhdm LEFT JOIN fw_area fw on d.areaid=fw.id where 1=1 " + s.getSql()+" order by d.orderno,pu.dtRegistrateTime desc",
				s.getParam().toArray(new Object[0]));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Record r:page.getList()){
//			Object xzqhdm=r.get("xzqhdm");
			Object qyzzjgdm=r.get("qyzzjgdm");
			Object kdbm=r.get("kdbm");
			Object cfbm=r.get("vWareHouseCode");
			Object vGrainSubTypeCode=r.get("vGrainSubTypeCode");
			Object vGrainPropertyId=r.get("vGrainPropertyId");
			
			Record  liangshiXZ= Db.findFirst("select vGrainProperties from tz_GrainProperties where vCode=?",vGrainPropertyId);
			if(liangshiXZ!=null){
				r.set("vGrainPropertyId", liangshiXZ.getStr("vGrainProperties"));
			}
			Record  liangshiPZ= Db.findFirst("select vName from tz_GrainType where vCode=?",vGrainSubTypeCode);
			if(liangshiPZ!=null){
				r.set("vGrainSubTypeCode", liangshiPZ.getStr("vName"));
			}
/*			Record quyu= Db.findFirst("select quyu from njpt_diqu where xzqhdm=?",xzqhdm);
			if(quyu!=null){
				r.set("xzqhdm", quyu.getStr("quyu"));
			}*/
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("kdbm", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("vWareHouseCode", cfmc.getStr("cfmc"));
			}
			//将数据库numeric类型为空时转成0
			r.set("dtRegistrateTime", sf.format(r.getDate("dtRegistrateTime")));
			/*if(null!=r.getDate("dtGWTime")){
				r.set("dtGWTime", sf.format(r.getDate("dtGWTime")));
			}
			if(null!=r.getDate("dtPWTime")){
				r.set("dtPWTime", sf.format(r.getDate("dtPWTime")));
			}
			if(null!=r.getDate("dtCheckTime")){
				r.set("dtCheckTime", sf.format(r.getDate("dtCheckTime")));
			}
			if(null!=r.getDate("dtQualityTime")){
				r.set("dtQualityTime", sf.format(r.getDate("dtQualityTime")));
			}*/
			/*r.set("dmPackageNumber", changebigDtoStr(r.get("dmPackageNumber")));
			r.set("dmYellowRiceKernel", changebigDtoStr(r.get("dmYellowRiceKernel")));
			r.set("dmOriginalMoisture", changebigDtoStr(r.get("dmOriginalMoisture")));
			r.set("dmOriginalImpurity", changebigDtoStr(r.get("dmOriginalImpurity")));
			r.set("dmOriginalMineral", changebigDtoStr(r.get("dmOriginalMineral")));
			r.set("dmOriginalGamma", changebigDtoStr(r.get("dmOriginalGamma")));
			r.set("dmOriginalUnsoundKernel", changebigDtoStr(r.get("dmOriginalUnsoundKernel")));
			r.set("dmOriginalYellowRiceKernel", changebigDtoStr(r.get("dmOriginalYellowRiceKernel")));
			r.set("dmOriginalMilledRice", changebigDtoStr(r.get("dmOriginalMilledRice")));
			r.set("dmOriginalBRRatio", changebigDtoStr(r.get("dmOriginalBRRatio")));
			r.set("dmOriginalMildew", changebigDtoStr(r.get("dmOriginalMildew")));
			r.set("dmOriginalVBRRatio", changebigDtoStr(r.get("dmOriginalVBRRatio")));
			r.set("dmOriginalOdor", changebigDtoStr(r.get("dmOriginalOdor")));
			r.set("dmOriginalFr", changebigDtoStr(r.get("dmOriginalFr")));
			r.set("dmOriginalWaterAbsorption", changebigDtoStr(r.get("dmOriginalWaterAbsorption")));
			r.set("dmOriginalMixingRatio",changebigDtoStr(r.get("dmOriginalMixingRatio")));
			r.set("dmOriginalFattyAcid", changebigDtoStr(r.get("dmOriginalFattyAcid")));
			r.set("dmMoisture", changebigDtoStr(r.get("dmMoisture")));
			r.set("dmImpurity", changebigDtoStr(r.get("dmImpurity")));
			r.set("dmGamma",changebigDtoStr(r.get("dmGamma")));
			r.set("dmUnsoundKernel", changebigDtoStr(r.get("dmUnsoundKernel")));
			r.set("dmPrice", changebigDtoStr(r.get("dmPrice")));
			r.set("dmMilledRice", changebigDtoStr(r.get("dmMilledRice")));
			r.set("dmBRRatio", changebigDtoStr(r.get("dmBRRatio")));
			r.set("dmMildew", changebigDtoStr(r.get("dmMildew")));
			r.set("dmVBRRatio", changebigDtoStr(r.get("dmVBRRatio")));
			r.set("dmOdor", changebigDtoStr(r.get("dmOdor")));
			r.set("dmFr", changebigDtoStr(r.get("dmFr")));
			r.set("dmWaterAbsorption",changebigDtoStr(r.get("dmWaterAbsorption")));
			r.set("dmMixingRatio", changebigDtoStr(r.get("dmMixingRatio")));
			r.set("dmFattyAcid", changebigDtoStr(r.get("dmFattyAcid")));
			r.set("dmMineral", changebigDtoStr(r.get("dmMineral")));
			r.set("dmHardness", changebigDtoStr(r.get("dmHardness")));
			r.set("dmGW", changebigDtoStr(r.get("dmGW")));
			r.set("dmPW", changebigDtoStr(r.get("dmPW")));
			r.set("dmNW", changebigDtoStr(r.get("dmNW")));
			r.set("dmCheckNumber", changebigDtoStr(r.get("dmCheckNumber")));
			r.set("dmCheckWeight", changebigDtoStr(r.get("dmCheckWeight")));*/
		}
		return page;
		
	}
	public static Page<Record> selectZLGLXQ(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and pu.vSwiftNumber=?", "vSwiftNumber");	
		p.put("and d.xzqhdm=?", "xzqhdm");				//行政区划代码
		p.put("and pu.qyzzjgdm=?", "qyzzjgdm");			//企业组织机构代码
		p.put("and pu.kdbm=?", "kdbm");					//库点编码
		p.put("and pu.vWareHouseCode=?", "vWareHouseCode");	//仓房编码
		p.put("and pu.dtQualityTime like ?", "dtQualityTime","%%%s%%");	//质检时间
		p.put("and pu.vDirection=?", "vDirection");		//出入库标示
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select fw.name ,q.qymc,pu.* ",
				"from crk_Purchase pu INNER JOIN tz_qiye q on pu.qyzzjgdm=q.qyzzjgdm LEFT JOIN njpt_diqu d ON q.xzqhdm= d.xzqhdm LEFT JOIN fw_area fw on d.areaid=fw.id where 1=1 " + s.getSql()+" order by d.orderno,pu.dtRegistrateTime desc",
				s.getParam().toArray(new Object[0]));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Record r:page.getList()){
//			Object xzqhdm=r.get("xzqhdm");
			Object qyzzjgdm=r.get("qyzzjgdm");
			Object kdbm=r.get("kdbm");
			Object cfbm=r.get("vWareHouseCode");
			Object vGrainSubTypeCode=r.get("vGrainSubTypeCode");
			Object vGrainPropertyId=r.get("vGrainPropertyId");
			
			Record  liangshiXZ= Db.findFirst("select vGrainProperties from tz_GrainProperties where vCode=?",vGrainPropertyId);
			if(liangshiXZ!=null){
				r.set("vGrainPropertyId", liangshiXZ.getStr("vGrainProperties"));
			}
			Record  liangshiPZ= Db.findFirst("select vName from tz_GrainType where vCode=?",vGrainSubTypeCode);
			if(liangshiPZ!=null){
				r.set("vGrainSubTypeCode", liangshiPZ.getStr("vName"));
			}
/*			Record quyu= Db.findFirst("select quyu from njpt_diqu where xzqhdm=?",xzqhdm);
			if(quyu!=null){
				r.set("xzqhdm", quyu.getStr("quyu"));
			}*/
			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("kdbm", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("vWareHouseCode", cfmc.getStr("cfmc"));
			}
			//将数据库numeric类型为空时转成0
			r.set("dtRegistrateTime", sf.format(r.getDate("dtRegistrateTime")));
			if(null!=r.getDate("dtGWTime")){
				r.set("dtGWTime", sf.format(r.getDate("dtGWTime")));
			}
			if(null!=r.getDate("dtPWTime")){
				r.set("dtPWTime", sf.format(r.getDate("dtPWTime")));
			}
			if(null!=r.getDate("dtCheckTime")){
				r.set("dtCheckTime", sf.format(r.getDate("dtCheckTime")));
			}
			if(null!=r.getDate("dtQualityTime")){
				r.set("dtQualityTime", sf.format(r.getDate("dtQualityTime")));
			}
			r.set("dmPackageNumber", changebigDtoStr(r.get("dmPackageNumber")));
			r.set("dmYellowRiceKernel", changebigDtoStr(r.get("dmYellowRiceKernel")));
			r.set("dmOriginalMoisture", changebigDtoStr(r.get("dmOriginalMoisture")));
			r.set("dmOriginalImpurity", changebigDtoStr(r.get("dmOriginalImpurity")));
			r.set("dmOriginalMineral", changebigDtoStr(r.get("dmOriginalMineral")));
			r.set("dmOriginalGamma", changebigDtoStr(r.get("dmOriginalGamma")));
			r.set("dmOriginalUnsoundKernel", changebigDtoStr(r.get("dmOriginalUnsoundKernel")));
			r.set("dmOriginalYellowRiceKernel", changebigDtoStr(r.get("dmOriginalYellowRiceKernel")));
			r.set("dmOriginalMilledRice", changebigDtoStr(r.get("dmOriginalMilledRice")));
			r.set("dmOriginalBRRatio", changebigDtoStr(r.get("dmOriginalBRRatio")));
			r.set("dmOriginalMildew", changebigDtoStr(r.get("dmOriginalMildew")));
			r.set("dmOriginalVBRRatio", changebigDtoStr(r.get("dmOriginalVBRRatio")));
			r.set("dmOriginalOdor", changebigDtoStr(r.get("dmOriginalOdor")));
			r.set("dmOriginalFr", changebigDtoStr(r.get("dmOriginalFr")));
			r.set("dmOriginalWaterAbsorption", changebigDtoStr(r.get("dmOriginalWaterAbsorption")));
			r.set("dmOriginalMixingRatio",changebigDtoStr(r.get("dmOriginalMixingRatio")));
			r.set("dmOriginalFattyAcid", changebigDtoStr(r.get("dmOriginalFattyAcid")));
			r.set("dmMoisture", changebigDtoStr(r.get("dmMoisture")));
			r.set("dmImpurity", changebigDtoStr(r.get("dmImpurity")));
			r.set("dmGamma",changebigDtoStr(r.get("dmGamma")));
			r.set("dmUnsoundKernel", changebigDtoStr(r.get("dmUnsoundKernel")));
			r.set("dmPrice", changebigDtoStr(r.get("dmPrice")));
			r.set("dmMilledRice", changebigDtoStr(r.get("dmMilledRice")));
			r.set("dmBRRatio", changebigDtoStr(r.get("dmBRRatio")));
			r.set("dmMildew", changebigDtoStr(r.get("dmMildew")));
			r.set("dmVBRRatio", changebigDtoStr(r.get("dmVBRRatio")));
			r.set("dmOdor", changebigDtoStr(r.get("dmOdor")));
			r.set("dmFr", changebigDtoStr(r.get("dmFr")));
			r.set("dmWaterAbsorption",changebigDtoStr(r.get("dmWaterAbsorption")));
			r.set("dmMixingRatio", changebigDtoStr(r.get("dmMixingRatio")));
			r.set("dmFattyAcid", changebigDtoStr(r.get("dmFattyAcid")));
			r.set("dmMineral", changebigDtoStr(r.get("dmMineral")));
			r.set("dmHardness", changebigDtoStr(r.get("dmHardness")));
			r.set("dmGW", changebigDtoStr(r.get("dmGW")));
			r.set("dmPW", changebigDtoStr(r.get("dmPW")));
			r.set("dmNW", changebigDtoStr(r.get("dmNW")));
			r.set("dmCheckNumber", changebigDtoStr(r.get("dmCheckNumber")));
			r.set("dmCheckWeight", changebigDtoStr(r.get("dmCheckWeight")));
		}
		return page;
		
	}
	/**
	 * 将数据库numeric转成字符串（为空不显示，保留两位）
	 * @param bigDecimal
	 * @return
	 */
	public static String changebigDtoStr(BigDecimal bigDecimal){
		double linshiDou=0;
		if(null!=bigDecimal){
			linshiDou=bigDecimal.setScale(2,BigDecimal.ROUND_UP).doubleValue();
		}
		if(linshiDou==0){
			return "";
		}else {
			return linshiDou+"";
		}
	}
	public static Page<Record> selectfeiksh(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and pu.vSwiftNumber=?", "vSwiftNumber");	
		p.put("and d.xzqhdm=?", "xzqhdm");				//行政区划代码
		p.put("and pu.qyzzjgdm=?", "qyzzjgdm");			//企业组织机构代码
		p.put("and pu.kdbm=?", "kdbm");					//库点编码
		p.put("and pu.vWareHouseCode=?", "vWareHouseCode");	//仓房编码
		p.put("and pu.vDirection=?", "vDirection");		//出入库标示
		p.put("and pu.dtRegistrateTime > ?", "qssj");	//>质检时间
		p.put("and pu.dtRegistrateTime < ?", "jssj");	//<质检时间
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select q.xzqhdm,fw.name,q.qymc,k.kdmc,c.cfmc,pu.*,gp.vGrainProperties,gt.vName ",
				"from crk_Purchase pu INNER JOIN tz_qiye q on pu.qyzzjgdm=q.qyzzjgdm inner JOIN njpt_diqu d ON q.xzqhdm= d.xzqhdm inner JOIN fw_area fw on d.areaid=fw.id inner JOIN tz_kudian k on k.qyzzjgdm=q.qyzzjgdm and k.kdbm=pu.kdbm inner join tz_cangfang c on c.qyzzjgdm=k.qyzzjgdm and c.kdbm=k.kdbm and c.cfbm=pu.vWareHouseCode INNER JOIN tz_GrainProperties gp on pu.vGrainPropertyId=gp.vCode INNER JOIN tz_GrainType gt on pu.vGrainSubTypeCode=gt.vCode  where 1=1 and k.id not in( select kk.id from tz_kudian kk where kk.isksh = '1' ) "+ s.getSql()+" order by d.orderno,pu.qyzzjgdm,pu.kdbm,pu.vWareHouseCode,pu.dtQualityTime desc",
				s.getParam().toArray(new Object[0]));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Record r:page.getList()){
			r.set("dtRegistrateTime", sf.format(r.getDate("dtRegistrateTime")));
			r.set("dtGWTime", sf.format(r.getDate("dtGWTime")));
			r.set("dtPWTime", sf.format(r.getDate("dtPWTime")));
			r.set("dtCheckTime", sf.format(r.getDate("dtCheckTime")));
			r.set("dtQualityTime", sf.format(r.getDate("dtQualityTime")));
			r.set("dmPackageNumber", changebigDtoStr(r.get("dmPackageNumber")));
			r.set("dmYellowRiceKernel", changebigDtoStr(r.get("dmYellowRiceKernel")));
			r.set("dmOriginalMoisture", changebigDtoStr(r.get("dmOriginalMoisture")));
			r.set("dmOriginalImpurity", changebigDtoStr(r.get("dmOriginalImpurity")));
			r.set("dmOriginalMineral", changebigDtoStr(r.get("dmOriginalMineral")));
			r.set("dmOriginalGamma", changebigDtoStr(r.get("dmOriginalGamma")));
			r.set("dmOriginalUnsoundKernel", changebigDtoStr(r.get("dmOriginalUnsoundKernel")));
			r.set("dmOriginalYellowRiceKernel", changebigDtoStr(r.get("dmOriginalYellowRiceKernel")));
			r.set("dmOriginalMilledRice", changebigDtoStr(r.get("dmOriginalMilledRice")));
			r.set("dmOriginalBRRatio", changebigDtoStr(r.get("dmOriginalBRRatio")));
			r.set("dmOriginalMildew", changebigDtoStr(r.get("dmOriginalMildew")));
			r.set("dmOriginalVBRRatio", changebigDtoStr(r.get("dmOriginalVBRRatio")));
			r.set("dmOriginalOdor", changebigDtoStr(r.get("dmOriginalOdor")));
			r.set("dmOriginalFr", changebigDtoStr(r.get("dmOriginalFr")));
			r.set("dmOriginalWaterAbsorption", changebigDtoStr(r.get("dmOriginalWaterAbsorption")));
			r.set("dmOriginalMixingRatio",changebigDtoStr(r.get("dmOriginalMixingRatio")));
			r.set("dmOriginalFattyAcid", changebigDtoStr(r.get("dmOriginalFattyAcid")));
			r.set("dmMoisture", changebigDtoStr(r.get("dmMoisture")));
			r.set("dmImpurity", changebigDtoStr(r.get("dmImpurity")));
			r.set("dmGamma",changebigDtoStr(r.get("dmGamma")));
			r.set("dmUnsoundKernel", changebigDtoStr(r.get("dmUnsoundKernel")));
			r.set("dmPrice", changebigDtoStr(r.get("dmPrice")));
			r.set("dmMilledRice", changebigDtoStr(r.get("dmMilledRice")));
			r.set("dmBRRatio", changebigDtoStr(r.get("dmBRRatio")));
			r.set("dmMildew", changebigDtoStr(r.get("dmMildew")));
			r.set("dmVBRRatio", changebigDtoStr(r.get("dmVBRRatio")));
			r.set("dmOdor", changebigDtoStr(r.get("dmOdor")));
			r.set("dmFr", changebigDtoStr(r.get("dmFr")));
			r.set("dmWaterAbsorption",changebigDtoStr(r.get("dmWaterAbsorption")));
			r.set("dmMixingRatio", changebigDtoStr(r.get("dmMixingRatio")));
			r.set("dmFattyAcid", changebigDtoStr(r.get("dmFattyAcid")));
			r.set("dmMineral", changebigDtoStr(r.get("dmMineral")));
			r.set("dmHardness", changebigDtoStr(r.get("dmHardness")));
			r.set("dmGW", r.getBigDecimal("dmGW").intValue());
			r.set("dmPW", r.getBigDecimal("dmPW").intValue());
			r.set("dmNW", r.getBigDecimal("dmNW").intValue());
			r.set("dmCheckNumber", changebigDtoStr(r.getBigDecimal("dmCheckNumber")));
			r.set("dmCheckWeight", r.getBigDecimal("dmCheckWeight").intValue());
		}
		return page;
	}
	public static Page<Record> kucunliebiao(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and c.xzqhdm=?", "xzqhdm");
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put(" and e.kdbm=?", "kdbm");
		p.put(" and a.vGrainSubTypeCode like ?", "pinzhong","%%%s");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select c.xzqhdm,d.name,b.qyzzjgdm,b.qymc,e.kdbm,e.kdmc,ISNULL(SUM(a.dmStock), 0) as stock ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN njpt_diqu c on b.xzqhdm=c.xzqhdm INNER JOIN fw_area d on c.areaid=d.id INNER JOIN tz_kudian e on e.qyzzjgdm=a.qyzzjgdm and e.kdbm=a.kdbm where e.kdlxbh<>'06' and e.ID NOT in (select kd.ID from tz_kudian kd where kd.isksh='1' ) "+ s.getSql()+" GROUP BY c.xzqhdm,d.name,c.orderno,b.qyzzjgdm,b.qymc,e.kdbm,e.kdmc ORDER BY c.orderno,b.qyzzjgdm,e.kdbm ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> singlekucun(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put(" and a.kdbm=?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				1,
				10000,
				"select b.xzqhdm,a.* ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm where 1=1 "+ s.getSql()+" order by vWareHouseCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> kucunbypz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put(" and d.xzqhdm=?", "xzqhdm");
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put(" and e.kdbm=?", "kdbm");
		p.put(" and a.vGrainSubTypeCode like ?", "pinzhong","%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select ISNULL(SUM(a.dmStock)/1000, 0) as stock ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN njpt_diqu c on b.xzqhdm=c.xzqhdm INNER JOIN fw_area d on c.areaid=d.id INNER JOIN tz_kudian e on e.qyzzjgdm=a.qyzzjgdm and e.kdbm=a.kdbm where e.kdlxbh<>'06' "+ s.getSql()+"  ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Record zongkucun(HashMap<String, Object> map) throws Exception {
		Record record=new Record();
		Param p = new Param();
		p.put(" and d.xzqhdm=?", "xzqhdm");
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put(" and e.kdbm=?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select ISNULL(SUM(a.dmStock)/1000, 0) as zongkc ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN njpt_diqu c on b.xzqhdm=c.xzqhdm INNER JOIN fw_area d on c.areaid=d.id INNER JOIN tz_kudian e on e.qyzzjgdm=a.qyzzjgdm and e.kdbm=a.kdbm where e.kdlxbh<>'06' "+ s.getSql()+"  ",
				s.getParam().toArray(new Object[0]));
		if(page.getList().size()>0){
			record.setColumns(page.getList().get(0));
		}else{
			record.set("zongkc", 0);
		}
		return record;
	}
	public static Page<Record> selectQuYu(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and d.xzqhdm=?", "xzqhdm");
		p.put("and fw.name=?", "name");
		p.put("and fw.id=?", "diquid");
		p.put("and c.id=?", "qiyeid");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select d.xzqhdm,fw.name ",
				"from njpt_diqu d  inner JOIN fw_area fw on d.areaid=fw.id inner join tz_qiye c on c.xzqhdm=d.xzqhdm "+ s.getSql()+" GROUP BY d.xzqhdm,fw.name,d.orderno order by d.orderno ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static String checkBigDecimal(String str) {
		String lastStr="0";
		if("".equals(str)||null==str){
			return lastStr;
			
		}else{
			return str;
		}
		
	}
	/**
	 * 新增非可视化库点-出入库数据
	 * @param param
	 * @return
	 */
	public static Ret cbljhrwbianji(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		Record r=new Record().setColumns(param);
		r.remove("xzqhdm");
		String vSwiftNumber=r.getStr("vSwiftNumber");
		String qyzzjgdm=r.getStr("qyzzjgdm");
		String kdbm=r.getStr("kdbm");
		//  new BigDecimal(r.getStr("dmOriginalGamma"))
		r.set("dmPackageNumber", new BigDecimal(checkBigDecimal(r.getStr("dmPackageNumber"))));
		r.set("dmYellowRiceKernel", new BigDecimal(checkBigDecimal(r.getStr("dmYellowRiceKernel"))));
		r.set("dmOriginalMoisture", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalMoisture"))));
		r.set("dmOriginalImpurity",new BigDecimal(checkBigDecimal(r.getStr("dmOriginalImpurity"))));
		r.set("dmOriginalMineral",new BigDecimal(checkBigDecimal(r.getStr("dmOriginalMineral"))));
		r.set("dmOriginalGamma", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalGamma"))));
		r.set("dmOriginalUnsoundKernel", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalUnsoundKernel"))));
		r.set("dmOriginalYellowRiceKernel", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalYellowRiceKernel"))));
		r.set("dmOriginalMilledRice", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalMilledRice"))));
		r.set("dmOriginalBRRatio", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalBRRatio"))));
		r.set("dmOriginalMildew", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalMildew"))));
		r.set("dmOriginalVBRRatio", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalVBRRatio"))));
		r.set("dmOriginalOdor", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalOdor"))));
		r.set("dmOriginalFr", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalFr"))));
		r.set("dmOriginalWaterAbsorption", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalWaterAbsorption"))));
		r.set("dmOriginalMixingRatio", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalMixingRatio"))));
		r.set("dmOriginalFattyAcid", new BigDecimal(checkBigDecimal(r.getStr("dmOriginalFattyAcid"))));
		r.set("dmMoisture", new BigDecimal(checkBigDecimal(r.getStr("dmMoisture"))));
		r.set("dmImpurity", new BigDecimal(checkBigDecimal(r.getStr("dmImpurity"))));
		r.set("dmGamma", new BigDecimal(checkBigDecimal(r.getStr("dmGamma"))));
		r.set("dmUnsoundKernel", new BigDecimal(checkBigDecimal(r.getStr("dmUnsoundKernel"))));
		r.set("dmMilledRice", new BigDecimal(checkBigDecimal(r.getStr("dmMilledRice"))));
		r.set("dmBRRatio",new BigDecimal(checkBigDecimal(r.getStr("dmBRRatio"))));
		r.set("dmMildew", new BigDecimal(checkBigDecimal(r.getStr("dmMildew"))));
		r.set("dmVBRRatio", new BigDecimal(checkBigDecimal(r.getStr("dmVBRRatio"))));
		r.set("dmOdor", new BigDecimal(checkBigDecimal(r.getStr("dmOdor"))));
		r.set("dmFr", new BigDecimal(checkBigDecimal(r.getStr("dmFr"))));
		r.set("dmWaterAbsorption", new BigDecimal(checkBigDecimal(r.getStr("dmWaterAbsorption"))));
		r.set("dmMixingRatio", new BigDecimal(checkBigDecimal(r.getStr("dmMixingRatio"))));
		r.set("dmFattyAcid", new BigDecimal(checkBigDecimal(r.getStr("dmFattyAcid"))));
		r.set("dmPrice", new BigDecimal(checkBigDecimal(r.getStr("dmPrice"))));
		r.set("bCheckState", r.getStr("bCheckState"));
		r.set("bIsUseMulitViechle", r.getStr("bIsUseMulitViechle"));
		r.set("bIsMultiWeight", r.getStr("bIsMultiWeight"));
		r.set("bIsStandardProgress", r.getStr("bIsStandardProgress"));
		r.set("bIsRegistrateFinished", r.getStr("bIsRegistrateFinished"));
		r.set("bIsQualityFinished", r.getStr("bIsQualityFinished"));
		r.set("bIsGwFinished", r.getStr("bIsGwFinished"));
		r.set("bEnsureStocked", r.getStr("bEnsureStocked"));
		
		r.set("dmMineral", new BigDecimal(checkBigDecimal(r.getStr("dmMineral"))));
		r.set("dmHardness", new BigDecimal(checkBigDecimal(r.getStr("dmHardness"))));
		r.set("dmGW", new BigDecimal(checkBigDecimal(r.getStr("dmGW"))));
		r.set("dmPW", new BigDecimal(checkBigDecimal(r.getStr("dmPW"))));
		r.set("dmNW", new BigDecimal(checkBigDecimal(r.getStr("dmNW"))));
		r.set("dmCheckWeight", new BigDecimal(checkBigDecimal(r.getStr("dmCheckWeight"))));
		r.set("dmCheckNumber", new BigDecimal(checkBigDecimal(r.getStr("dmCheckNumber"))));
		List<Record> sq=Db.find("select * from crk_Purchase where vSwiftNumber=? and qyzzjgdm=? and kdbm=? ",vSwiftNumber,qyzzjgdm,kdbm);
		if(sq.size()>0){
			return new Ret().put("ret",Db.update("crk_Purchase","vSwiftNumber,qyzzjgdm,kdbm",new Record().setColumns(r)));
		}else{
			return new Ret().put("ret",Db.save("crk_Purchase", r));
		}
	}

	
	public static Page<Record> selectfeikshKDMC(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and q.qyzzjgdm=?", "qyzzjgdm");
		p.put("and fw.name=?", "name");
		p.put("and q.qymc=?", "qymc"); 
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select q.qyzzjgdm as qyzzjgdm,k.kdbm as kdbm,k.kdmc as kdmc,fw.orderno as orderno,k.ID as ID,q.xzqhdm as xzqhdm ",
				"from njpt_diqu d inner join fw_area fw on d.areaid=fw.id inner JOIN  tz_qiye q on d.xzqhdm=q.xzqhdm inner JOIN tz_kudian k on q.qyzzjgdm=k.qyzzjgdm where 1=1 and k.kdlxbh<>'06' and k.id not in( select kk.id from tz_kudian kk where kk.isksh = '1' ) "+ s.getSql()+" GROUP BY fw.orderno,q.qyzzjgdm,k.kdbm,k.kdmc,k.ID,q.xzqhdm ORDER BY fw.orderno ",s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> selectallpz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		p.put("and a.vCode=?", "pzcoed");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select a.vCode,a.vName ",
				"from tz_GrainType a where 1=1 "+ s.getSql()+" order by a.ID  ",s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> selectallxz(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select vCode,vGrainProperties ",
				"from tz_GrainProperties  where 1=1 "+ s.getSql()+" order by id ",s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> selectalldj(HashMap<String, Object> map) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select vCode,vLevelName ",
				"from tz_GrainLevel  where 1=1 "+ s.getSql()+" order by vLevelId ",s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> kucunheji(HashMap<String, Object> map) {
		Param p = new Param();
		p.put(" and c.xzqhdm=?", "xzqhdm");
		p.put("and d.id=?", "diquid");
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put("and b.id=?", "qiyeid");
		p.put(" and e.kdbm=?", "kdbm");
		p.put(" and a.vGrainSubTypeCode like ?", "pinzhong","%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select ISNULL(SUM(a.dmStock), 0)/1000 as stock ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN njpt_diqu c on b.xzqhdm=c.xzqhdm INNER JOIN fw_area d on c.areaid=d.id INNER JOIN tz_kudian e on e.qyzzjgdm=a.qyzzjgdm and e.kdbm=a.kdbm where e.kdlxbh<>'06' and e.ID NOT in (select kd.ID from tz_kudian kd where kd.isksh='1' ) "+ s.getSql()+"  ",
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Record zongkucunheji(HashMap<String, Object> map) {
		Record record=new Record();
		Param p = new Param();
		p.put(" and c.xzqhdm=?", "xzqhdm");
		p.put("and d.id=?", "diquid");
		p.put(" and a.qyzzjgdm=?", "qyzzjgdm");
		p.put("and b.id=?", "qiyeid");
		p.put(" and e.kdbm=?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select ISNULL(SUM(a.dmStock), 0)/1000 as zongkc ",
				"from kc_CurrentStock a INNER JOIN tz_qiye b on a.qyzzjgdm=b.qyzzjgdm INNER JOIN njpt_diqu c on b.xzqhdm=c.xzqhdm INNER JOIN fw_area d on c.areaid=d.id INNER JOIN tz_kudian e on e.qyzzjgdm=a.qyzzjgdm and e.kdbm=a.kdbm where e.kdlxbh<>'06' and e.ID NOT in (select kd.ID from tz_kudian kd where kd.isksh='1' ) "+ s.getSql()+"  ",
				s.getParam().toArray(new Object[0]));
		if(page.getList().size()>0){
			record.setColumns(page.getList().get(0));
		}else{
			record.set("zongkc", 0);
		}
		return record;
	}
}
