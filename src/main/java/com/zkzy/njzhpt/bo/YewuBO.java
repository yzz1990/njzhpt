package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.JibenDAO;
import com.zkzy.njzhpt.dao.JibenxinxiDao;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;
import com.zkzy.njzhpt.model.pt_Cangrong;
import com.zkzy.njzhpt.model.pt_Liangqing;
import com.zkzy.njzhpt.model.pt_Qiti;
import com.zkzy.njzhpt.model.pt_Qitilx;
import com.zkzy.njzhpt.model.pt_Shipin;

public class YewuBO {

	/**
	 * 同步对接的库存
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public void queryTongBu() throws Exception {
		// syncCurrentStock();//同步库存
		syncPurchase();// 同步出入库
	}

	/**
	 * 同步对接的库存
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public void syncCurrentStock() throws Exception {
		Boolean bool = false;
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		queryparam.put("page", "1");
		queryparam.put("rows", 10000);
		Page<Record> page = JibenDAO.queryOnStorage(queryparam);
		if (page.getList().size() > 0) {
			for (Record record : page.getList()) {
				Record CurrentStock = new Record();
				if (!StrKit.isBlank(record.getStr("status")) && !record.getStr("status").equals("0")) { // 判断该仓廒不是空仓
					CurrentStock.set("vWareHouseCode", record.getStr("house"));
					CurrentStock.set("dmStock", Double.valueOf(record.getStr("quantity")));
					CurrentStock.set("vGrainSubTypeCode", record.getStr("grainkind"));
					CurrentStock.set("vGrainPropertyCode", record.getStr("grainattribute"));
					CurrentStock.set("kdbm", record.getStr("orgcode"));
					CurrentStock.set("qyzzjgdm", record.getStr("entcode"));
					CurrentStock.set("status", record.getStr("status"));
					CurrentStock.set("grade", record.getStr("grade"));
					CurrentStock.set("owner", record.getStr("owner"));
					CurrentStock.set("year", record.getStr("year"));
					CurrentStock.set("houseStoreID", record.getStr("housestoreid"));
					CurrentStock.set("DBarCode", record.getStr("dbarcode"));
					CurrentStock.set("housecode", record.getStr("housecode"));
					CurrentStock.set("inBeginTime", record.getStr("inbegintime"));
					CurrentStock.set("storeBeginTime", record.getStr("storebegintime"));
					// 判断该厫间的库存是否已经存在
					Record RCount = Db.use("njpt").findFirst(
							"select *  from kc_CurrentStock where qyzzjgdm=? and kdbm=? and vWareHouseCode=?",
							record.getStr("entcode"), record.getStr("orgcode"), record.getStr("house"));
					if (RCount != null) {// 执行更新操作
						CurrentStock.set("id", RCount.getLong("id"));
						bool = Db.use("njpt").update("kc_CurrentStock",
								CurrentStock);
						if (bool) {
							LogKit.info("企业组织机构代码：" + record.getStr("entcode")
									+ "库点编码：" + record.getStr("orgcode")
									+ "仓房编码：" + record.getStr("house")
									+ "==库存更新成功！！！");
						} else {
							LogKit.info("企业组织机构代码：" + record.getStr("entcode")
									+ "库点编码：" + record.getStr("orgcode")
									+ "仓房编码：" + record.getStr("house")
									+ "==库存更新失败！！！");
						}
					} else {// 执行新增操作
						bool = Db.use("njpt").save("kc_CurrentStock",
								CurrentStock);
						if (bool) {
							LogKit.info("企业组织机构代码：" + record.getStr("entcode")
									+ "库点编码：" + record.getStr("orgcode")
									+ "仓房编码：" + record.getStr("house")
									+ "==库存新增成功！！！");
						} else {
							LogKit.info("企业组织机构代码：" + record.getStr("entcode")
									+ "库点编码：" + record.getStr("orgcode")
									+ "仓房编码：" + record.getStr("house")
									+ "==库存新增失败！！！");
						}
					}
				}
			}
		}

	}

	public void syncPurchase()throws Exception {
		int flag=0;
		HashMap<String, Object> queryparam=new HashMap<String, Object>();
		queryparam.put("page", "1");
		queryparam.put("rows", 10000);
		Page<Record> page=JibenDAO.querySainout(queryparam);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(page.getList().size()>0){
			for(Record record:page.getList()){
				if(record.getStr("grainattribute")!=null&&record.getStr("unitprice")!=null){
					Record Purchase=new Record();
					Purchase.set("vSwiftNumber", record.getStr("inoutid"));
					Purchase.set("dtRegistrateTime",dealDate(record.getStr("date")));
					Purchase.set("vWareHouseCode", record.getStr("house"));
					Purchase.set("vGrainSubTypeCode", record.getStr("grainkind"));
					Purchase.set("vViechelNumber", record.getStr("platenumber"));
					Purchase.set("vCustomerIdentificationId", record.getStr("idcardno"));
					Purchase.set("vGrainPropertyId", record.getStr("grainattribute"));				
					Purchase.set("vGrainLevel", record.getStr("grade"));
					Purchase.set("vDispatchCompany", record.getStr("customername"));
					Purchase.set("dmPrice", BigDecimal.valueOf(Double.valueOf(record.getStr("unitprice"))) );
					Purchase.set("dmGW",BigDecimal.valueOf(Double.valueOf(record.getStr("grossweight"))) );
					Purchase.set("dmPW",BigDecimal.valueOf(Double.valueOf(record.getStr("tareweight"))));
					Purchase.set("dmNW",BigDecimal.valueOf(Double.valueOf(record.getStr("netweight"))));
					Purchase.set("dmCheckWeight", BigDecimal.valueOf(Double.valueOf(record.getStr("settleweight"))));
					Purchase.set("dmCheckNumber", BigDecimal.valueOf(Double.valueOf(record.getStr("settlemoney"))));
					Purchase.set("shipinUrl",record.getStr("url"));
					Purchase.set("qyzzjgdm",record.getStr("entcode"));
					Purchase.set("kdbm",record.getStr("orgcode"));
					if(record.getStr("inouttype").equals("00")){
						Purchase.set("vDirection", "销售");
					}else if(record.getStr("inouttype").equals("01")){
						Purchase.set("vDirection", "收购");
					}else if(record.getStr("inouttype").equals("02")){
						Purchase.set("vDirection", "调入");
					}else if(record.getStr("inouttype").equals("03")){
						Purchase.set("vDirection", "调出");
					}else if(record.getStr("inouttype").equals("04")){
						Purchase.set("vDirection", "调增");
					}else if(record.getStr("inouttype").equals("05")){
						Purchase.set("vDirection", "调减");
					}else if(record.getStr("inouttype").equals("06")){
						Purchase.set("vDirection", "溢余");
					}else if(record.getStr("inouttype").equals("07")){
						Purchase.set("vDirection", "损耗");
					}else if(record.getStr("inouttype").equals("08")){
						Purchase.set("vDirection", "收成品");
					}else if(record.getStr("inouttype").equals("09")){
						Purchase.set("vDirection", "加工出");
					}else if(record.getStr("inouttype").equals("10")){
						Purchase.set("vDirection", "库调入");
					}else if(record.getStr("inouttype").equals("11")){
						Purchase.set("vDirection", "库调出");
					}
					//扣量
					JSONArray jsonArrry = JSONArray.parseArray(record.getStr("items"));
					if(jsonArrry.size()>0){
						for(int i=0;i<jsonArrry.size();i++){
							String item=jsonArrry.getJSONObject(i).getString("item");
							String value=jsonArrry.getJSONObject(i).getString("value");
							if(item.equals("020101")){  //水分
								Purchase.set("dmMoisture",value);
							}else if(item.equals("01010301")){//杂质
								Purchase.set("dmImpurity",value);	
							}else if(item.equals("01010701")){//容重
								Purchase.set("dmGamma",value);		
							}else if(item.equals("01010401")){//不完善粒
								Purchase.set("dmUnsoundKernel",value);		
							}else if(item.equals("01012102")){//黄粒米
								Purchase.set("dmYellowRiceKernel",value);		
							}else if(item.equals("01012104")){//整粳米率  
								Purchase.set("dmMilledRice",value);		
							}else if(item.equals("01012101")){//出糙率
								Purchase.set("dmBRRatio",value);		
							}else if(item.equals("01010405")){//霉变
								Purchase.set("dmMildew",value);		
							}else if(item.equals("01012103")){//谷外糙米
								Purchase.set("dmVBRRatio",value);		
							}else if(item.equals("010101")){//色泽气味
								Purchase.set("dmOdor",value);		
							}else if(item.equals("040103")){//面筋吸水率
								Purchase.set("dmWaterAbsorption",value);		
							}else if(item.equals("01010201")){//互混率
								Purchase.set("dmMixingRatio",value);		
							}else if(item.equals("02010306")){//脂肪酸
								Purchase.set("dmFattyAcid",value);		
							}
						}
					}
					//LogKit.info("售粮人："+Purchase.getStr("vDispatchCompany")+"企业组织机构代码："+record.getStr("entcode")+"库点编码："+record.getStr("orgcode")+"仓房编码："+record.getStr("house")+"流水号："+record.getStr("inoutid")+"==测试记录！！！");
					Record crkPurchase=   Db.use("njpt").findFirst("select * from crk_Purchase where qyzzjgdm=? and kdbm=? and vWareHouseCode=? and vSwiftNumber=? ",record.getStr("entcode"),record.getStr("orgcode"),record.getStr("house"),record.getStr("inoutid"));
					if(crkPurchase!=null){
						flag=Db.use("njpt").update("update crk_Purchase set dtRegistrateTime=cast(? as datetime),"
								+ "vGrainSubTypeCode=?,vViechelNumber=?,vCustomerIdentificationId=?,"
								+ "vDirection=?,vGrainPropertyId=?,vDispatchCompany=?,dmMoisture=?,dmImpurity=?,dmGamma=?,"
								+ "dmUnsoundKernel=?,dmYellowRiceKernel=?,dmMilledRice=?,dmBRRatio=?,dmMildew=?,dmVBRRatio=?,"
								+ "dmOdor=?,dmFr=?,dmWaterAbsorption=?,dmMixingRatio=?,dmFattyAcid=?,vGrainLevel=?,"
								+ "dmPrice=?,dmGW=?,dmPW=?,dmNW=?,dmCheckWeight=?,dmCheckNumber=?,shipinUrl=? where qyzzjgdm=? and kdbm=? and vWareHouseCode=? and vSwiftNumber=? ",
								dealDate(record.getStr("date")),record.getStr("grainkind"),
								record.getStr("platenumber")!=null?record.getStr("platenumber"):null,record.getStr("idcardno"), Purchase.getStr("vDirection"), 
								record.getStr("grainattribute"),record.getStr("customername"),
								Purchase.getStr("dmMoisture")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmMoisture"))):0,
								Purchase.getStr("dmImpurity")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmImpurity"))):0,
								Purchase.getStr("dmGamma")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmGamma"))):0,
								Purchase.getStr("dmUnsoundKernel")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmUnsoundKernel"))):0,
								Purchase.getStr("dmYellowRiceKernel")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmYellowRiceKernel"))):0,
								Purchase.getStr("dmMilledRice")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmMilledRice"))):0,
								Purchase.getStr("dmBRRatio")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmBRRatio"))):0,
								Purchase.getStr("dmMildew")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmMildew"))):0,
								Purchase.getStr("dmVBRRatio")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmVBRRatio"))):0,
								Purchase.getStr("dmOdor")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmOdor"))):0,
								Purchase.getStr("dmFr")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmFr"))):0,
								Purchase.getStr("dmWaterAbsorption")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmWaterAbsorption"))):0,
								Purchase.getStr("dmMixingRatio")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmMixingRatio"))):0,
								Purchase.getStr("dmFattyAcid")!=null?BigDecimal.valueOf(Double.valueOf(Purchase.getStr("dmFattyAcid"))):0,
								record.getStr("grade"),
								BigDecimal.valueOf(Double.valueOf(record.getStr("unitprice"))),
								BigDecimal.valueOf(Double.valueOf(record.getStr("grossweight"))),
								BigDecimal.valueOf(Double.valueOf(record.getStr("tareweight"))),
								BigDecimal.valueOf(Double.valueOf(record.getStr("netweight"))),
								BigDecimal.valueOf(Double.valueOf(record.getStr("settleweight"))),
								BigDecimal.valueOf(Double.valueOf(record.getStr("settlemoney"))),
								record.getStr("url"),
								record.getStr("entcode"),record.getStr("orgcode"),record.getStr("house"),record.getStr("inoutid"));
						if(flag>0){
							LogKit.info("企业组织机构代码："+record.getStr("entcode")+"库点编码："+record.getStr("orgcode")+"仓房编码："+record.getStr("house")+"流水号："+record.getStr("inoutid")+"==出入库记录更新成功！！！");
						}else{
							LogKit.info("企业组织机构代码："+record.getStr("entcode")+"库点编码："+record.getStr("orgcode")+"仓房编码："+record.getStr("house")+"流水号："+record.getStr("inoutid")+"==出入库记录更新失败！！！");						
						}
					}else{
						boolean bool=Db.use("njpt").save("crk_Purchase", Purchase);
						if(bool){
							LogKit.info("企业组织机构代码："+record.getStr("entcode")+"库点编码："+record.getStr("orgcode")+"仓房编码："+record.getStr("house")+"流水号："+record.getStr("inoutid")+"==库存新增成功！！！");
						}else{
							LogKit.info("企业组织机构代码："+record.getStr("entcode")+"库点编码："+record.getStr("orgcode")+"仓房编码："+record.getStr("house")+"流水号："+record.getStr("inoutid")+"==库存新增失败！！！");						
						}
					}
				}else{
					continue;
				}
				
			}
		}
	}

	public String dealDate(String datestr) {
		return datestr.substring(0, 4) + "-" + (datestr.substring(4, 6).length()>1?datestr.substring(4, 6):"0"+datestr.substring(4, 6))+ "-"
				+ (datestr.substring(6, 8).length()>1?datestr.substring(6, 8):"0"+datestr.substring(6, 8))+ " " + (datestr.substring(8, 10).length()>1?datestr.substring(8, 10):"0"+datestr.substring(8, 10)) + ":"
				+ (datestr.substring(10, 12).length()>1?datestr.substring(10, 12):"0"+datestr.substring(10, 12)) + ":" + (datestr.substring(12, 14).length()>1?datestr.substring(12, 14):"0"+datestr.substring(12, 14));
	}

	// region 仓容/粮情/气体

	/**
	 * 查询气体
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryCangfangQT(HashMap<String, Object> queryparam)
			throws Exception {
		List<pt_Qiti> pt_Qitis = new ArrayList<pt_Qiti>();
		List<Record> qtList = Db
				.use("grainplat")
				.find("select storehouse,house,max(time) as time from gas where orgcode = ? and entcode = ? group by storehouse,house",
						queryparam.get("kdbm"), queryparam.get("qyzzjgdm"));

		for (Record record : qtList) {
			pt_Qiti pt_Qiti = new pt_Qiti();
			// 获取仓房名称
			Record tRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from house where cfbm=? and ajbh = ? and qyzzjgdm = ? and kdbm = ?",
							record.getStr("storehouse"),record.getStr("house"), queryparam.get("qyzzjgdm"),
							queryparam.get("kdbm"));
			pt_Qiti.setCangming(tRecord.getStr("ajmc"));
			String[] pointsStrings = tRecord.getStr("points").split(",");
			pt_Qiti.setPointx(pointsStrings[0]);
			pt_Qiti.setPointy(pointsStrings[1]);
			// 获取气体数值
			Record gas = Db
					.use("grainplat")
					.findFirst(
							"select * from gas where orgcode = ? and entcode = ? and house = ? and storehouse=? and time = ?",
							queryparam.get("kdbm"), queryparam.get("qyzzjgdm"),
							record.getStr("house"), record.getStr("storehouse"),record.getStr("time"));
			JSONArray array = JSON.parseArray(gas.getStr("points"));
			JSONObject obj = array.getJSONObject(0);
			pt_Qiti.setYangqi(obj.getString("o2"));
			pt_Qiti.setEryanghuatan(obj.getString("co2"));
			pt_Qiti.setLinhuaqin(obj.getString("h3p"));
			pt_Qiti.setDanqi(obj.getString("n2"));
			pt_Qiti.setShijian(gas.getStr("time"));
			pt_Qitis.add(pt_Qiti);
		}

		return Ret.create("list", pt_Qitis);
	}
	/**
	 * 查询气体
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryCangfangJcQT(HashMap<String, Object> queryparam)
			throws Exception {
		List<Record> pt_Qitis = new ArrayList<Record>();
		List<Record> qtList = Db
				.use("grainplat")
				.find("select house,max(time) as time from gas where orgcode = ? and entcode = ? group by house",
						queryparam.get("kdbm"), queryparam.get("qyzzjgdm"));

		for (Record record : qtList) {
			Record pt_Qiti = new Record();
			// 获取仓房名称
			Record tRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from house where ajbh = ? and qyzzjgdm = ? and kdbm = ?",
							record.getStr("house"), queryparam.get("qyzzjgdm"),
							queryparam.get("kdbm"));
			pt_Qiti.set("cangming",tRecord.getStr("ajmc"));
			String[] pointsStrings = tRecord.getStr("points").split(",");
			pt_Qiti.set("pointx",pointsStrings[0]);
			pt_Qiti.set("pointy",pointsStrings[1]);
			// 获取气体数值
			Record gas = Db
					.use("grainplat")
					.findFirst(
							"select * from gas where orgcode = ? and entcode = ? and house = ? and time = ?",
							queryparam.get("kdbm"), queryparam.get("qyzzjgdm"),
							record.getStr("house"), record.getStr("time"));
			JSONArray array = JSON.parseArray(gas.getStr("points"));
			JSONObject obj = array.getJSONObject(0);
			pt_Qiti.set("yangqi",obj.getString("o2"));
			pt_Qiti.set("eryanghuatan",obj.getString("co2"));
			pt_Qiti.set("linhuaqin",obj.getString("h3p"));
			pt_Qiti.set("danqi",obj.getString("n2"));
			
				String datetimeString = gas.getStr("time");
				datetimeString = datetimeString.substring(0, 4) + "-"
						+ datetimeString.substring(4, 6) + "-"
						+ datetimeString.substring(6, 8) + " "
						+ datetimeString.substring(8, 10) + ":"
						+ datetimeString.substring(10, 12) + ":"
						+ datetimeString.substring(12, 14);
			pt_Qiti.set("shijian",datetimeString);
			pt_Qitis.add(pt_Qiti);
		}
		Page<Record> page=new Page<Record>(pt_Qitis, 1, 1000, 1, pt_Qitis.size());
		return page;
	}
	/**
	 * 查询视频
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryShiPin(HashMap<String, Object> queryparam) throws Exception {
		List<pt_Shipin> pt_Shipins = new ArrayList<pt_Shipin>();
		Record nvr = Db.use("grainplat").findFirst(
				"select * from nvr where orgcode = ? and entcode = ?",
				queryparam.get("kdbm"), queryparam.get("qyzzjgdm"));
		JSONArray array = JSONArray.parseArray(nvr.getStr("points"));
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			pt_Shipin shipin = new pt_Shipin();
			shipin.setName(object.getString("name"));
			String[] pointsStrings = object.getString("points").split(",");
			shipin.setPointx(pointsStrings[0]);
			shipin.setPointy(pointsStrings[1]);
			JSONObject infoJsonObject = object.getJSONObject("info");
			shipin.setStatus(object.getString("status"));
			shipin.setInfo(infoJsonObject);
			pt_Shipins.add(shipin);
		}
		return Ret.create("list", pt_Shipins);
	}
	/**
	 * 查询仓容,库存
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryCangRongCangFangInfo(HashMap<String, Object> queryparam)
			throws Exception {
		Ret r = new Ret();
		List<pt_Cangrong> crList = new ArrayList<pt_Cangrong>();
		List<Record> WareHouseList = JibenDAO.queryHouse(queryparam);
		for (Record record : WareHouseList) {// 循环仓房
			LogKit.info(record.getStr("ajmc"));
			pt_Cangrong cr = new pt_Cangrong();
			cr.setCangming(record.getStr("ajmc"));
			cr.setAjbh(record.getStr("ajbh"));
			// 获取相对平面图的位置
			String[] pointsStrings = record.getStr("points").split(",");
			cr.setPointx(pointsStrings[0]);
			cr.setPointy(pointsStrings[1]);

			// 获取在仓房的总库存
			queryparam.put("vWareHouseCode", record.getStr("ajbh"));
			queryparam.put("storehouse", record.getStr("cfbm"));
			Page<Record> SumOnStorage = JibenDAO.querySumOnStorage(queryparam);
			Record onStorageRecord = null;
			if (SumOnStorage.getTotalRow() == 0) {
				onStorageRecord = new Record().set("status", "0");
			} else {
				onStorageRecord = SumOnStorage.getList().get(0);
			}
			if(onStorageRecord.getStr("year")!=null){
				cr.setNiandu(onStorageRecord.getStr("year"));
			}else{
				cr.setNiandu("--");
			}
			
			String sumString = "";
			BigDecimal sumBigDecimal=new BigDecimal(0);
			if ("0".equals(onStorageRecord.getStr("status"))) {
				sumString = "0";
				cr.setXingzhi("--");
				cr.setPinzhong("--");
				cr.setDengji("--");

			} else {
				
					sumString = onStorageRecord.getStr("quantity");
					 sumBigDecimal = new BigDecimal(sumString);
					 cr.setShuliang(sumString);
						
					// 品种
					 if(!"".equals(onStorageRecord.getStr("grainkind"))&&onStorageRecord.getStr("grainkind")!=null){
						 String graintypeString = Db.findById("tz_GrainType", "vCode",
								 onStorageRecord.getStr("grainkind")).getStr("vName");
						 cr.setPinzhong(graintypeString);
					 }else{
						 cr.setPinzhong("--");
					 }
					
					// 等级
					 if(!"".equals(onStorageRecord.getStr("grade"))&&onStorageRecord.getStr("grade")!=null){
						 String grainlevelString = Db.findById("tz_GrainLevel", "vCode",
									onStorageRecord.getStr("grade")).getStr("vLevelName");
						 cr.setDengji(grainlevelString);
					 }else{
						 cr.setDengji("--");
					 }
					
					// 性质
					 if(!"".equals(onStorageRecord.getStr("grainattribute"))&&onStorageRecord.getStr("grainattribute")!=null){
						 String grainpropertiesString = Db.findById(
									"tz_GrainProperties", "vCode",
									onStorageRecord.getStr("grainattribute")).getStr(
									"vGrainProperties");
						 cr.setXingzhi(grainpropertiesString);
					 }else{
						 cr.setXingzhi("--");
					 }
			}
		/**
		 *  获取总仓容
		 *  //TODO 此处的仓房总仓容要和普查数据中的总仓容保持一致
		 */
			//String zcrString = record.getStr("capacity");
			String zcrString="0";
			HashMap<String, Object>  param=new HashMap<String, Object>();
			param.put("qyzzjgdm", record.getStr("qyzzjgdm"));
			param.put("kdbm", record.getStr("kdbm"));
			param.put("cfbm", record.getStr("cfbm"));
			param.put("ajbh", record.getStr("ajbh"));
			param.put("page", 1);
			param.put("rows", 10000);
			
			Page<Record>  exchange= JibenxinxiDao.findExchange(param);
			if(exchange.getList().size()>0){
				HashMap<String, Object>  param1=new HashMap<String, Object>();
				param1.put("qyzzjgdm", record.getStr("qyzzjgdm"));
				param1.put("kdbm", record.getStr("kdbm"));
				param1.put("cfbm", exchange.getList().get(0).getStr("cfbm2"));
				param1.put("page", 1);
				param1.put("rows", 10000);
				Page<Record>  page=JibenxinxiDao.findCangfang(param1);
				if(page.getList().size()>0){
					zcrString=page.getList().get(0).getStr("shjcr");
				}
				
			}
			BigDecimal zcrBigDecimal = new BigDecimal(zcrString);
			cr.setCongrong(zcrString);
			// 百分比
			double bfb = sumBigDecimal.divide(new BigDecimal(1000))
					.divide(zcrBigDecimal, 4, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			//bfb = bfb > 100 ? 100 : bfb;
			bfb = bfb < 0 ? 0 : bfb;
			cr.setBaifenbi(String.valueOf(bfb));
			crList.add(cr);
		}
		r.put("list", crList);
		return r;
	}
	/**
	 * 查询仓库/库容/粮食信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryCangKuInfo(HashMap<String, Object> queryparam)
			throws Exception {
		Ret r = new Ret();
		Record record = Db
				.findFirst(
						"select e.vCapacity,a.dmStock,b.vName,c.vGrainProperties from t_WareHouse e left join kc_CurrentStock a on e.vWareHouseCode = a.vWareHouseCode and e.qyzzjgdm = a.qyzzjgdm and e.kdbm = a.kdbm left join tz_GrainType b on b.vCode = a.vGrainSubTypeCode left join tz_GrainProperties c on a.vGrainPropertyCode = c.vCode where e.qyzzjgdm = ? and e.kdbm = ? and e.vWareHouseCode = ?",
						queryparam.get("qyzzjgdm"), queryparam.get("kdbm"),
						queryparam.get("vcode"));
		r.put("kcl", record.get("dmStock", "-"));
		r.put("pz", record.get("vName", "-"));
		r.put("xz", record.get("vGrainProperties", "-"));
		r.put("cr", record.get("vCapacity", "-"));

		Record jiance = Db
				.findFirst(
						"select * from t_specificQualityResultPerWareHouse where qyzzjgdm = ? and kdbm = ? and vWareHouseCode = ?",
						queryparam.get("qyzzjgdm"), queryparam.get("kdbm"),
						queryparam.get("vcode"));
		if (jiance != null) {
			r.put("shnd", jiance.get("shengchanniandu", "-"));
			r.put("lsdj", jiance.get("vLevelCode", "-"));
			r.put("sf", jiance.get("dmMoisture", "-"));
			r.put("zz", jiance.get("dmImpurity", "-"));
			r.put("cz",
					String.valueOf(jiance.getBigDecimal("dmVBRRatio")
							.stripTrailingZeros().doubleValue()));
			r.put("rz",
					String.valueOf(jiance.getBigDecimal("dmGamma")
							.stripTrailingZeros().doubleValue()));
			r.put("hl",
					String.valueOf(jiance.getBigDecimal("dmYellowRiceKernel")
							.stripTrailingZeros().doubleValue()));
			r.put("bws",
					String.valueOf(jiance.getBigDecimal("dmUnsoundKernel")
							.stripTrailingZeros().doubleValue()));
			r.put("szqw", "正常");
			r.put("jcrq", jiance.get("dtQualityTime", "-"));
		}
		return r;
	}

	// #region

	// region 三维粮情点位信息
	/**
	 * 三维粮情的点位信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryLiangqingInfo(HashMap<String, Object> queryparam)throws Exception {
		List<pt_Liangqing> pt_Liangqings = new ArrayList<pt_Liangqing>();
		Ret r = new Ret();
		List<Record> WareHouseList = JibenDAO.queryHouse(queryparam);
		for (Record wareHouse : WareHouseList) {
			queryparam.put("vCode", wareHouse.getStr("ajbh"));
			// 获取这个仓粮情最近时间
			LogKit.info(wareHouse.getStr("cfbm"));
			Record graintempRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from graintemp where storehouse=?and house = ? and entcode = ? and orgcode = ? ORDER BY time DESC limit 0,1",
							wareHouse.getStr("cfbm"), queryparam.get("vCode"),
							queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
			if (graintempRecord != null) {
				pt_Liangqing ptLiangqing = new pt_Liangqing();
				ptLiangqing.setCangming(wareHouse.getStr("ajmc"));
				ptLiangqing.setCanghao(Db.use("exchange").findFirst("select * from exchange where qyzzjgdm=? and kdbm=? and cfbm=? and ajbh=? ",queryparam.get("qyzzjgdm"), queryparam.get("kdbm"),wareHouse.getStr("cfbm"), queryparam.get("vCode")).getStr("cfbm2"));

				ptLiangqing.setCfbm(graintempRecord.getStr("storehouse"));
				ptLiangqing.setAjbm(graintempRecord.getStr("house"));
				ptLiangqing.setCangwen(graintempRecord.getStr("intemp"));
				ptLiangqing.setCangshi(graintempRecord.getStr("inh"));
				ptLiangqing.setQiwen(graintempRecord.getStr("outtemp"));
				ptLiangqing.setQishi(graintempRecord.getStr("outh"));
				ptLiangqing.setShijian(graintempRecord.getStr("time"));
				String[] pointsStrings = wareHouse.getStr("points").split(",");
				ptLiangqing.setPointx(pointsStrings[0]);
				ptLiangqing.setPointy(pointsStrings[1]);
				Record record= Db
						.use("grainplat")
						.findFirst(
								"select * from onstorage where storehouse=? and house = ? and entcode = ? and orgcode = ?",
								wareHouse.getStr("cfbm"),queryparam.get("vCode"), queryparam.get("qyzzjgdm"),
								queryparam.get("kdbm"));
				 if (record==null||"0".equals(record.getStr("status"))) {
						ptLiangqing.setKucun("0");
						ptLiangqing.setPinzhong("--");
					} else {
						if(record.getStr("quantity")!=null||record.getStr("grainkind")!=null){
							ptLiangqing.setKucun(record.getStr("quantity"));
							String graintypeString = Db.findById("tz_GrainType", "vCode",
									record.getStr("grainkind")).getStr("vName");
							ptLiangqing.setPinzhong(graintypeString);
						}
						
					}
				
				pt_Liangqings.add(ptLiangqing);
			}
		}
		r.put("list", pt_Liangqings);
		return r;
	}


	/**
	 * 收储可视化业务管理三粮情检测信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryLiangqingJcInfo(HashMap<String, Object> queryparam)throws Exception {
		List<Record> pt_Liangqings = new ArrayList<Record>();
		Ret r = new Ret();
		List<Record> WareHouseList = JibenDAO.queryHouse(queryparam);
		for (Record wareHouse : WareHouseList) {
			queryparam.put("vCode", wareHouse.getStr("ajbh"));
			// 获取这个仓粮情最近时间
			Record graintempRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from graintemp where house = ? and entcode = ? and orgcode = ? ORDER BY time DESC limit 0,1",
							queryparam.get("vCode"),
							queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
			if (graintempRecord != null) {
				Record ptLiangqing = new Record();
				ptLiangqing.set("cangming",wareHouse.getStr("ajmc"));
				ptLiangqing.set("canghao",graintempRecord.getStr("house"));
				ptLiangqing.set("cangwen",graintempRecord.getStr("intemp"));
				ptLiangqing.set("cangshi",graintempRecord.getStr("inh"));
				ptLiangqing.set("qiwen",graintempRecord.getStr("outtemp"));
				ptLiangqing.set("qishi",graintempRecord.getStr("outh"));
				ptLiangqing.set("shijian",graintempRecord.getStr("time"));
				String[] pointsStrings = wareHouse.getStr("points").split(",");
				ptLiangqing.set("pointx",pointsStrings[0]);
				ptLiangqing.set("pointx",pointsStrings[1]);
				
				Record record = Db
						.use("grainplat")
						.findFirst(
								"select * from onstorage where house = ? and entcode = ? and orgcode = ?",
								queryparam.get("vCode"), queryparam.get("qyzzjgdm"),
								queryparam.get("kdbm"));
				if ("0".equals(record.getStr("status"))) {
					ptLiangqing.set("kucun","0");
					ptLiangqing.set("pinzhong","--");
				} else {
					if(record.getStr("quantity")!=null||record.getStr("grainkind")!=null){
						ptLiangqing.set("kucun",record.getStr("quantity"));
						String graintypeString = Db.findById("tz_GrainType", "vCode",
								record.getStr("grainkind")).getStr("vName");
						ptLiangqing.set("pinzhong",graintypeString);
					}
					
				}
				pt_Liangqings.add(ptLiangqing);
			}
		}
		//r.put("list", pt_Liangqings);
		
		Page<Record> page=new Page<Record>(pt_Liangqings, 1, 1000, 1, pt_Liangqings.size());
		return page;
	}

	// #region

	// region 三维粮情气泡信息
	/**
	 * 三维粮情的气泡信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret queryLqqipaoInfo(HashMap<String, Object> queryparam) throws Exception {
		String timeString = String.valueOf(queryparam.get("datetime"))
				.replace(" ", "").replace("-", "").replace(":", "");
		pt_Liangqing ptLiangqing = new pt_Liangqing();
		Record tempRecord = Db
				.use("grainplat")
				.findFirst(
						"select * from graintemp where time = ? and storehouse=? and  house = ? and entcode = ? and orgcode = ? limit 0,1",
						timeString, queryparam.get("cfbm"),queryparam.get("ajbm"),
						queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
		Record tRecord = Db
				.use("grainplat")
				.findFirst(
						"select * from house where cfbm=? and ajbh = ? and qyzzjgdm = ? and kdbm = ?",
						queryparam.get("cfbm"), queryparam.get("ajbm"),queryparam.get("qyzzjgdm"),
						queryparam.get("kdbm"));
		ptLiangqing.setCangming(tRecord.getStr("ajmc"));
		ptLiangqing.setCanghao(tempRecord.getStr("house"));
		ptLiangqing.setCangwen(tempRecord.getStr("intemp"));
		ptLiangqing.setCangshi(tempRecord.getStr("inh"));
		ptLiangqing.setQiwen(tempRecord.getStr("outtemp"));
		ptLiangqing.setQishi(tempRecord.getStr("outh"));
		ptLiangqing.setPingjunwen(tempRecord.getStr("averagetemp"));
		ptLiangqing.setZuigaowen(tempRecord.getStr("maxtemp"));
		ptLiangqing.setZuidiwen(tempRecord.getStr("mintemp"));
		ptLiangqing.setShijian(tempRecord.getStr("time"));
		String layerString = tempRecord.getStr("layers");
		if (StrKit.notBlank(layerString)) {
			ptLiangqing.setLayers(JSON.parseArray(layerString));
		}
		// 库存和品种
		Record record = Db
				.use("grainplat")
				.findFirst(
						"select * from onstorage where storehouse=? and house = ? and entcode = ? and orgcode = ?",
						queryparam.get("cfbm"),queryparam.get("ajbm"), queryparam.get("qyzzjgdm"),
						queryparam.get("kdbm"));
		if ("0".equals(record.getStr("status"))) {
			ptLiangqing.setKucun("0");
			ptLiangqing.setPinzhong("--");
		} else {
			ptLiangqing.setKucun(record.getStr("quantity"));
			String graintypeString = Db.findById("tz_GrainType", "vCode",
					record.getStr("grainkind")).getStr("vName");
			ptLiangqing.setPinzhong(graintypeString);
		}
		JSONArray array = JSON.parseArray(tempRecord.getStr("points"));
		JSONObject object = array.getJSONObject(array.size() - 1);
		ptLiangqing
				.setX(String.valueOf(Integer.valueOf(object.getString("x")) + 1));
		ptLiangqing
				.setY(String.valueOf(Integer.valueOf(object.getString("y")) + 1));
		try{
			ptLiangqing
			.setZ(String.valueOf(Integer.valueOf(object.getString("z")) + 1));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String liangwenString = "";
		for (int i = 0; i < array.size(); i++) {
			liangwenString += array.getJSONObject(i).getString("temp") + ",";
		}
		ptLiangqing.setLiangwen(liangwenString);
		return Ret.create("list", ptLiangqing);
	}
	/**
	 * 收储可视化业务管理粮情信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> queryLqqipaoJcInfo(HashMap<String, Object> queryparam) throws Exception {
		if(queryparam.get("datetime")=="undefined"||queryparam.get("datetime")==""||queryparam.get("datetime")==null){
			
		}else{
			String timeString = String.valueOf(queryparam.get("datetime"))
					.replace(" ", "").replace("-", "").replace(":", "");
			queryparam.put("time", timeString);
		}
		Page<Record> page=JibenDAO.queryLqqipaoJcInfo(queryparam);
		
		for (Record record : page.getList()) {
			Record tRecord = Db.use("grainplat").findFirst("select * from house where ajbh = ? and qyzzjgdm = ? and kdbm = ?",record.get("house"), record.get("entcode"),record.get("orgcode"));
			record.set("cangming", tRecord.getStr("ajmc"));
			String layerString=record.getStr("layers");
			if (StrKit.notBlank(layerString)) {
				record.set("layers",JSON.parseArray(layerString));
			}
			// 库存和品种
			Record pzRecord = Db.use("grainplat").findFirst("select * from onstorage where house = ? and entcode = ? and orgcode = ?",record.get("house"), record.get("entcode"),record.get("orgcode"));
			
			
			if ("0".equals(pzRecord.getStr("status"))) {
				record.set("kucun","0");
				record.set("pinzhong","--");
			} else {
				record.set("kucun",pzRecord.getStr("quantity"));
				String graintypeString = Db.findById("tz_GrainType", "vCode",
						pzRecord.getStr("grainkind")).getStr("vName");
				record.set("pinzhong",graintypeString);
			}
			JSONArray array = JSON.parseArray(record.getStr("points"));
			JSONObject object = array.getJSONObject(array.size() - 1);
			record.set("x",String.valueOf(Integer.valueOf(object.getString("x")) + 1));
			record.set("y",String.valueOf(Integer.valueOf(object.getString("y")) + 1));
			try{
				record.set("z",String.valueOf(Integer.valueOf(object.getString("z")) + 1));
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String liangwenString = "";
			for (int i = 0; i < array.size(); i++) {
				liangwenString += array.getJSONObject(i).getString("temp") + ",";
			}
			record.set("liangwen",liangwenString);
			
				String datetimeString =record.get("time");
				datetimeString = datetimeString.substring(0, 4) + "-"
						+ datetimeString.substring(4, 6) + "-"
						+ datetimeString.substring(6, 8) + " "
						+ datetimeString.substring(8, 10) + ":"
						+ datetimeString.substring(10, 12) + ":"
						+ datetimeString.substring(12, 14);
				record.set("shijian", datetimeString);
			record.remove("points");
		}
		return page;
		/*pt_Liangqing ptLiangqing = new pt_Liangqing();
		Record tempRecord;
		if(queryparam.get("datetime")=="undefined"||queryparam.get("datetime")==""||queryparam.get("datetime")==null){
			 tempRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from graintemp where  house = ? and entcode = ? and orgcode = ? limit 0,1",
							 queryparam.get("vCode"),
							queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
		}else{
			String timeString = String.valueOf(queryparam.get("datetime"))
					.replace(" ", "").replace("-", "").replace(":", "");
			
			 tempRecord = Db
					.use("grainplat")
					.findFirst(
							"select * from graintemp where time = ? and house = ? and entcode = ? and orgcode = ? limit 0,1",
							timeString, queryparam.get("vCode"),
							queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
		}
		
		Record tRecord = Db
				.use("grainplat")
				.findFirst(
						"select * from house where ajbh = ? and qyzzjgdm = ? and kdbm = ?",
						queryparam.get("vCode"), queryparam.get("qyzzjgdm"),
						queryparam.get("kdbm"));
		ptLiangqing.setCangming(tRecord.getStr("ajmc"));
		ptLiangqing.setCanghao(tempRecord.getStr("house"));
		ptLiangqing.setCangwen(tempRecord.getStr("intemp"));
		ptLiangqing.setCangshi(tempRecord.getStr("inh"));
		ptLiangqing.setQiwen(tempRecord.getStr("outtemp"));
		ptLiangqing.setQishi(tempRecord.getStr("outh"));
		ptLiangqing.setPingjunwen(tempRecord.getStr("averagetemp"));
		ptLiangqing.setZuigaowen(tempRecord.getStr("maxtemp"));
		ptLiangqing.setZuidiwen(tempRecord.getStr("mintemp"));
		ptLiangqing.setShijian(tempRecord.getStr("time"));
		String layerString = tempRecord.getStr("layers");
		if (StrKit.notBlank(layerString)) {
			ptLiangqing.setLayers(JSON.parseArray(layerString));
		}
		// 库存和品种
		Record record = Db
				.use("grainplat")
				.findFirst(
						"select * from onstorage where house = ? and entcode = ? and orgcode = ?",
						queryparam.get("vCode"), queryparam.get("qyzzjgdm"),
						queryparam.get("kdbm"));
		if ("0".equals(record.getStr("status"))) {
			ptLiangqing.setKucun("0");
			ptLiangqing.setPinzhong("--");
		} else {
			ptLiangqing.setKucun(record.getStr("quantity"));
			String graintypeString = Db.findById("tz_GrainType", "vCode",
					record.getStr("grainkind")).getStr("vName");
			ptLiangqing.setPinzhong(graintypeString);
		}
		JSONArray array = JSON.parseArray(tempRecord.getStr("points"));
		JSONObject object = array.getJSONObject(array.size() - 1);
		ptLiangqing
				.setX(String.valueOf(Integer.valueOf(object.getString("x")) + 1));
		ptLiangqing
				.setY(String.valueOf(Integer.valueOf(object.getString("y")) + 1));
		try{
			ptLiangqing
			.setZ(String.valueOf(Integer.valueOf(object.getString("z")) + 1));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String liangwenString = "";
		for (int i = 0; i < array.size(); i++) {
			liangwenString += array.getJSONObject(i).getString("temp") + ",";
		}
		ptLiangqing.setLiangwen(liangwenString);
		return Ret.create("list", ptLiangqing);*/
	}
	// #region

	// region 三维粮情根据年月日获取时分秒列表--yzz
	@Before(AutoPageInterceptor.class)
	public Ret queryhs(HashMap<String, Object> queryparam) throws Exception {
		List<Record> timeList = Db
				.use("grainplat")
				.find("select time as datetime from graintemp where storehouse=? and house = ? and entcode = ? and orgcode = ? order by time desc",
						queryparam.get("cfbm"),queryparam.get("ajbm"), queryparam.get("qyzzjgdm"),
						queryparam.get("kdbm"));
		for (Record r : timeList) {
			String datetimeString = r.getStr("datetime");
			datetimeString = datetimeString.substring(0, 4) + "-"
					+ datetimeString.substring(4, 6) + "-"
					+ datetimeString.substring(6, 8) + " "
					+ datetimeString.substring(8, 10) + ":"
					+ datetimeString.substring(10, 12) + ":"
					+ datetimeString.substring(12, 14);
			r.set("datetime", datetimeString);
		}
		return Ret.create("list", timeList);

	}
	// #region

	// region 气体泄露气泡内容--yzz
	@Before(AutoPageInterceptor.class)
	public Ret queryQtxlInfo(HashMap<String, Object> queryparam)
			throws Exception {
		Ret ret = new Ret();

		List<pt_Qitilx> qtxls = new ArrayList<pt_Qitilx>();
		Record tempRecord = Db.use("grainplat").findFirst(
				"select * from gasleak where entcode = ? and orgcode = ?",
				queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
		if(tempRecord!=null){
			JSONArray array = JSONArray.parseArray(tempRecord.getStr("points"));
			for (int i = 0; i < array.size(); i++) {
				pt_Qitilx qtxl = new pt_Qitilx();
				qtxl.setName(array.getJSONObject(i).getString("name"));
				String[] pointsStrings = array.getJSONObject(i).getString("points")
						.split(",");
				qtxl.setPointx(pointsStrings[0]);
				qtxl.setPointy(pointsStrings[1]);
				qtxl.setTime(array.getJSONObject(i).getString("time"));
				qtxl.setValue(array.getJSONObject(i).getString("value"));
				qtxls.add(qtxl);
			}
		}
		
		ret.put("list", qtxls);
		return ret;

	}

	// #region

	@Before(AutoPageInterceptor.class)
	public List<Record> queryPlan(HashMap<String, Object> queryparam)
			throws Exception {
		Page<Record> vDwProperty = JibenDAO.queryPlan(queryparam);
		return vDwProperty.getList();
	}
}
