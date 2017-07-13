package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

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
import com.zkzy.njzhpt.dao.JibenxinxiDao;
import com.zkzy.njzhpt.dao.ShouchukeshihuaDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;


public class ShouchukeshihuaBO {

	/**
	 *获取夏粮五日分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryxialiangshougouwuri(HashMap<String, Object> map) throws Exception {
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxialiangshougouwuri(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	
	
	/**
	 *获取秋粮五日分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryqiuliangshougouwuri(HashMap<String, Object> map) throws Exception {
		
		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangshougouwuri(map);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
		
		/**
		 *获取夏粮五日分期上报
		 * @param map
		 * @return
		 * @throws Exception
		 */
	 	
		public Ret querywuri(HashMap<String, Object> map) throws Exception {
			Date dt = new Date();
		    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
		    String today = matter1.format(dt);
		    String shijian=today.split("-")[2];
		    int day=Integer.valueOf(shijian)+5;
		    String riqi=today.split("-")[0]+"-"+today.split("-")[1]+"-"+day;
		    map.put("today", riqi);
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querywuri(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *获取分期
		 * @param map
		 * @return
		 * @throws Exception
		 */
	 	
		public Ret queryfenqi(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryfenqi(map);
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
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxialiangyuce(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 获取夏粮预测年度数据
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryxialiangyucend(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxialiangyucend(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 获取秋粮预测年度数据
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryqiuliangyucend(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangyucend(map);
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
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangyuce(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 加载粮食性质
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Ret querylsxz(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querylsxz(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 加载粮食品种
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Ret querylspz(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querylspz(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		/**
		 * 加载粮食等级
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Ret querylsdj(HashMap<String, Object> map) throws Exception {
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querylsdj(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *获取库点实时收购情况
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryshishi(HashMap<String, Object> map) throws Exception {
			
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
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryshishi(param);
			
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
		    	sql = "select starttime , endtime "
			    		+ "from njpt_fenqishangbao "
			    		+ "where convert(date,endtime) <= ? order by endtime desc ";
		    	recordt = Db.findFirst(sql,today);
		    	if(recordt == null){
		    		starttime = "2100-01-01";
			    	endtime = "2000-01-01";	
		    	}else{
		    		starttime = recordt.getStr("starttime");
				    endtime = recordt.getStr("endtime");
		    	}
		    }else{
		    	starttime = recordt.getStr("starttime");
			    endtime = recordt.getStr("endtime");
		    }
		    
		    if(StrKit.isBlank(starttime) || starttime.length() != 10){
		    	
		    }
			//=====================================================
			for(Record userRecord:userPageRecord.getList()){
				String qiye = userRecord.getStr("qyzzjgdm");
				String kdbm = userRecord.getStr("kdbm");
				HashMap<String, Object> param1 = new HashMap<>();
				param1.put("qyzzjgdm", qiye);
				param1.put("kdbm", kdbm);
				param1.put("page", 1);
				param1.put("rows", 1);
				Page<Record> pzkc = JibenxinxiDao.queryCfZkc(param1);
				//Record kdkc = new Record();
				if(pzkc.getTotalRow() == 0){
					userRecord.set("dmStock", 0);
				}else{
					userRecord.set("dmStock", pzkc.getList().get(0).get("dmStock"));
				}
				userRecord.set("seshi11111", 1111111);
				
				
//				if(JibenxinxiDao.queryCfZkc(param1) == null){
//					pzkc.set("dmStock", 0);
//				}else{
//					pzkc = JibenxinxiDao.queryCfZkc(param1).getPageNumber();
//				}
				
				/*
				Record kdcr = new Record();
				if(JibenxinxiDao.queryCfZcr(param1).getList().get(0) == null){
					kdcr.set("dmStock", 0);
				}else{
					kdcr = JibenxinxiDao.queryCfZcr(param1).getList().get(0);
				}
				*/
				
				//System.out.println("pzkc:"+pzkc);
				//Record pzkc = ShouchukeshihuaDAO.findzongkucun(param1);
				//userRecord.setColumns(pzkc);
				//userRecord.setColumns(kdkc);
				HashMap<String, Object> param2 = new HashMap<>();
				param2.put("qiye", qiye);
				param2.put("kdbm", kdbm);
				param2.put("starttime", starttime);
				param2.put("endtime", endtime);
				Record pzrk = ShouchukeshihuaDAO.findzongrk(param2);
				if(pzrk == null){
					pzrk = new Record();
					pzrk.set("sunNW", 0);
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
			}
			
			if("1".equals(param.get("isksh"))){
				System.out.println("11111111111111111111111111111111111111111");
			}
			
			Ret ret = Ret.create("list", userPageRecord);
			
			return ret;
		}
		
		/**
		 *获取库点明细情况
		 * @param map
		 * @return
		 * @throws Exception
		 */
	 	
		public Ret querymingxi(HashMap<String, Object> map) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querymingxi(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		@Before(AutoPageInterceptor.class)
		public Ret minxiheji(HashMap<String, Object> map) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.minxiheji(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		/**
		 * 获取五日报表年份
		 * @param map
		 * @return
		 */
		public List<Record>  findnian(HashMap<String, Object> map){
			String sql = "select distinct niandu from njpt_fenqishangbao order by niandu";
			List<Record> list = Db.find(sql);
			return list;
		}
		/**
		 * 获取预测报表年份
		 * @param map
		 * @return
		 */
		public List<Record>  findnianyuce(HashMap<String, Object> map){
			String sql = "select distinct niandu from njpt_shougouyuce order by niandu";
			List<Record> list = Db.find(sql);
			return list;
		}
		
		public Ret findheji(HashMap<String, Object> map) throws Exception{
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
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findheji(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
			
		}
		
		/**
		 * 找平均杂质和水分
		 * @param map
		 * @return
		 * @throws Exception 
		 */
		public Ret findshuiza(HashMap<String, Object> map) throws Exception{
			HashMap<String, Object> param = map;
			
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			if("请选择".equals(param.get("qyzzjgdm"))){
				param.remove("qyzzjgdm");
			}
			if("请选择".equals(param.get("kdmc"))){
				param.remove("kdmc");
			}
			if("请选择".equals(param.get("kdbm"))){
				param.remove("kdbm");
			}
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findshuiza(param);
			BigDecimal sumshuifen = new BigDecimal(0);
			BigDecimal sumzazhi = new BigDecimal(0);
			BigDecimal sumnwrk = new BigDecimal(0);
			BigDecimal sumnwck = new BigDecimal(0);
			BigDecimal avgshuifen = new BigDecimal(0);
			BigDecimal avgzazhi = new BigDecimal(0);
			for(Record userRecord:userPageRecord.getList()){
				String direction = userRecord.getStr("vDirection");
				
				if("入库".equals(direction) || "出库".equals(direction)){
					BigDecimal shuifen = userRecord.get("dmMoisture");
					BigDecimal zazhi = userRecord.get("dmImpurity");
					BigDecimal nw = userRecord.get("dmNW");
					
					
					
					if("入库".equals(direction)){
						sumnwrk = sumnwrk.add(nw);
					}else{
						//nw = nw.multiply(new BigDecimal(-1));
						sumnwck = sumnwck.add(nw);
					}
					sumshuifen = sumshuifen.add(shuifen.multiply(nw));
					sumzazhi =  sumzazhi.add(zazhi.multiply(nw));
					
				}
			}
			if(sumnwrk.add(sumnwck).compareTo(new BigDecimal(0)) != 0 ){
				avgshuifen = sumshuifen.divide((sumnwrk.add(sumnwck)),2,BigDecimal.ROUND_HALF_UP);
				avgzazhi = sumzazhi.divide((sumnwrk.add(sumnwck)),2,BigDecimal.ROUND_HALF_UP);
			}
			
			/*
			if(avgshuifen.compareTo(new BigDecimal(0))!=1){
				avgshuifen = new BigDecimal(0);
			}
			if(avgzazhi.compareTo(new BigDecimal(0))!=1){
				avgzazhi = new BigDecimal(0);
			}
			if(sumnwck.compareTo(new BigDecimal(0))!=1){
				sumnwck = new BigDecimal(0);
			}
			if(sumnwrk.compareTo(new BigDecimal(0))!=1){
				sumnwrk = new BigDecimal(0);
			}*/
			
			
			
			HashMap<String, Object> purchase = new HashMap<>();
			purchase.put("avgshuifen", avgshuifen);
			purchase.put("avgzazhi", avgzazhi);
			purchase.put("sumnwck", sumnwck);
			purchase.put("sumnwrk", sumnwrk);
		
			Ret ret = Ret.create("list", purchase);
			return ret;
			
		}
		
		
		/**
		 * 查找视频
		 * @param map
		 * @return
		 */
		public List<Record> readshiping(HashMap<String, Object> map){
			Record record = ShouchukeshihuaDAO.readshiping(map);
			
			String kdmc = record.getStr("kdmc");
		
			List<Record> records = null;
			String sql = "select * from njpt_shiping where kdmc = ?";
			records = Db.find(sql, kdmc);
			return records;
		}

		public Ret queryfengquzongliang(HashMap<String, Object> param) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryfengquzongliang(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *获取实时汇总情况
		 * @param map
		 * @return
		 * @throws Exception
		 */
		//@Before(AutoPageInterceptor.class)
		public Ret queryhuizong(HashMap<String, Object> map) throws Exception {
			HashMap<String, Object> param = map;
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			if("请选择".equals(param.get("qyzzjgdm"))){
				param.remove("qyzzjgdm");
			}
			if("请选择".equals(param.get("kdmc"))){
				param.remove("kdmc");
			}
			if("请选择".equals(param.get("kdbm"))){
				param.remove("kdbm");
			}
			if("请选择".equals(param.get("vGrainProperties"))){
				param.remove("vGrainProperties");
			}
			if("请选择".equals(param.get("vName"))){
				param.remove("vName");
			}
			if("请选择".equals(param.get("vDirection"))){
				param.remove("vDirection");
			}
			if("请选择".equals(param.get("vGrainLevel"))){
				param.remove("vGrainLevel");
			}
			if("".equals(param.get("dtRegistrateTime"))){
				param.remove("dtRegistrateTime");
			}
			if("".equals(param.get("vEndTime"))){
				param.remove("vEndTime");
			}

			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryhuizong(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		public Ret queryliangqing(HashMap<String, Object> map) throws Exception{
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryliangqing(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		public Ret queryqiuliangshebei(HashMap<String, Object> map) throws Exception{
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiuliangshebei(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 实时汇总明细
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret findzhj(HashMap<String, Object> map) throws Exception {
			HashMap<String, Object> param = map;
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findzhj(param);
			String sql="SELECT vLevelName FROM tz_GrainLevel WHERE vCode=?";
			
			for (Record re : userPageRecord.getList()) {
				if(re.get("grade")!=null&&!re.get("grade").equals("")){
	 				List<Record> list=Db.find(sql, re.getStr("grade"));
	 				re.set("level",list.get(0).get("vLevelName"));	
	 			}else{
	 				re.set("level","");
	 			}
			}
			
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 全市汇总明细
		 * @param param
		 * @return
		 * @throws Exception 
		 */
		public Ret findqszhj(HashMap<String, Object> map) throws Exception {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = map;
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findqszhj(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 全市汇总明细年度
		 * @param param
		 * @return
		 * @throws Exception 
		 */
		public Ret findqszhjyear(HashMap<String, Object> map) throws Exception {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = map;
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findqszhjyear(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 * 全市汇总大类
		 * @param param
		 * @return
		 */
		public Ret findqszhjdl(HashMap<String, Object> map) {
			HashMap<String, Object> param = map;
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findqszhjdl(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}

		/**
		 * 夏粮五日报表修改
		 * @param param
		 * @return
		 */
		public Ret upxialiangwuri(HashMap<String, Object> param) {
			Record record = new Record().setColumns(param);
			boolean result = Db.update("njpt_xialiangshougouwuribb", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","修改成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","修改失败"));
				return msg;
			}
		}

		/**
		 * 夏粮五日报表增加
		 * @param param
		 * @return
		 */
		public Ret addxialiangwuri(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_xialiangshougouwuribb", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}
		
		/**
		 * 秋粮五日报表修改
		 * @param param
		 * @return
		 */
		public Ret upqiuliangwuri(HashMap<String, Object> param) {
			Record record = new Record().setColumns(param);
			boolean result = Db.update("njpt_qiuliangshougouwuribb", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","修改成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","修改失败"));
				return msg;
			}
		}
		
		/**
		 * 秋粮五日新增
		 * @param param
		 * @return
		 */
		public Ret addqiuliangwuri(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_qiuliangshougouwuribb", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}
		
		/**
		 * 修改秋季预测收购表
		 * @param param
		 * @return
		 */
		public Ret upqiujiyuce(HashMap<String, Object> param) {
			Record record = new Record().setColumns(param);
			boolean result = Db.update("njpt_qiuliangyuce", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","修改成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","修改失败"));
				return msg;
			}
		}
		
		
		/**
		 * 增加秋季预测收购表
		 * @param param
		 * @return
		 */
		public Ret addqiujiyuce(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_qiuliangyuce", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}
		
		/**
		 * 修改夏季预测收购表
		 * @param param
		 * @return
		 */
		public Ret upxiajiyuce(HashMap<String, Object> param) {
			Record record = new Record().setColumns(param);
			boolean result = Db.update("njpt_xialiangyuce", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","修改成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","修改失败"));
				return msg;
			}
		}

		/**
		 * 增加夏季预测收购表
		 * @param param
		 * @return
		 */
		public Ret addxiajiyuce(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_xialiangyuce", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}
		
		public Ret addqiujishebei(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_qiuliangshebei", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}

		public Ret upqiujishebei(HashMap<String, Object> param) {
			Record record = new Record().setColumns(param);
			boolean result = Db.update("njpt_qiuliangshebei", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","修改成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","修改失败"));
				return msg;
			}
		}
			

		/**
		 * 报表删除
		 * @param param
		 * @return
		 */
		public Ret deletebaobiao(HashMap<String, Object> param) {
			String temp = (String) param.get("temp");
			String id = (String) param.get("id");
			param.remove("isedit");
			boolean result = false;
			System.out.println(temp);
			System.out.println(id);
			//删除夏粮五日报表
			if("0".equals(temp)){
				result = Db.deleteById("njpt_xialiangshougouwuribb", "id", param.get("id"));
			}
			//删除秋粮五日报表
			if("1".equals(temp)){
				result = Db.deleteById("njpt_qiuliangshougouwuribb", "id", id);
				System.out.println("进入方法");
			}
			//删除秋粮预测表
			if("2".equals(temp)){
				result = Db.deleteById("njpt_qiuliangyuce", "id", param.get("id"));
			}
			//删除夏粮预测表
			if("3".equals(temp)){
				result = Db.deleteById("njpt_xialiangyuce", "id", param.get("id"));
			}
			//删除企业设备表
			if("4".equals(temp)){
				result = Db.deleteById("njpt_qiuliangshebei", "id", param.get("id"));
			}
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","删除成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","删除失败"));
				return msg;
			}
		}

		public Ret savefenqi(HashMap<String, Object> param) {
			String uuid = SqlUtil.uuid();
			Record record = new Record().setColumns(param).set("id",uuid);
			boolean result = Db.save("njpt_fenqishangbao", "id", record);
			if (result) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","新增成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}

		/**
		 *找到库点仓房库存信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
	 	@Before (AutoPageInterceptor.class)
		public Page<Record>  findkcxinxi(HashMap<String, Object> map) throws Exception {
	 		Page<Record> userPageRecord = ShouchukeshihuaDAO.findkcxinxi(map);
			
	 		for(Record record:userPageRecord.getList()){
	 			HashMap<String, Object>  param=new HashMap<String, Object>();
	 			param.put("qyzzjgdm", record.getStr("qyzzjgdm"));
	 			param.put("kdbm", record.getStr("kdbm"));
	 			param.put("vWareHouseCode", record.getStr("cfbm"));
	 			Record nx=  ShouchukeshihuaDAO.queryNx(param).getList().get(0);
	 			record.set("vHarvestPeriod",nx.getStr("year"));
	 			String sql="SELECT vLevelName FROM tz_GrainLevel WHERE vCode=?";
	 			if(nx.getStr("grade")!=null){
	 				List<Record> list=Db.find(sql, nx.getStr("grade"));
	 				if(list.size()>0){	 					
	 					record.set("vGrainLevel",list.get(0).get("vLevelName"));	
	 				}else{
	 					record.set("vGrainLevel","三等");
	 				}
	 			}else{
	 				record.set("vGrainLevel","三等");
	 			}
	 			
	 			
	 		}
	 		Ret ret = Ret.create("list", userPageRecord);
	 		return userPageRecord;
		}
	 	
	 	/**
		 *找到库点基本信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret findkudian(HashMap<String, Object> map) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.findkudian(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *找到各性质粮食库存
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryxingzhi(HashMap<String, Object> map) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryxingzhi(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
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
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiye(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *加载企业名称
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before (AutoPageInterceptor.class)
		public Ret queryqiye1(HashMap<String, Object> map) throws Exception {
			HashMap<String,Object> param = map;
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			if(map.containsKey("isksh")){
				if(Integer.parseInt(map.get("isksh").toString()) != 1){
					map.remove("isksh");
				}
			}
			//判断权限
//			List<String> auth = UserKit.currentUserInfo().getAuth();
//			if(auth.contains("auth_select_part")){
//				String xian = UserKit.currentUserInfo().getArea().getString("name");
//				map.put("xian", xian);
//			}
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryqiye1(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		/**
		 *加载库点名称
		 * @param map
		 * @return
		 * @throws Exception
		 */
		
		@Before (AutoPageInterceptor.class)
		public Ret querykud(HashMap<String, Object> map) throws Exception {
			HashMap<String,Object> param = map;
			if("请选择".equals(param.get("qymc"))){
				param.remove("qymc");
			}

			//判断权限
//			List<String> auth = UserKit.currentUserInfo().getAuth();
//			if(auth.contains("auth_select_part")){
//				String xian = UserKit.currentUserInfo().getArea().getString("name");
//				map.put("xian", xian);
//			}

			Page<Record> userPageRecord = ShouchukeshihuaDAO.querykud(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}

	/**
	 *加载应急加工库点名称
	 * @param map
	 * @return
	 * @throws Exception
	 */

	@Before (AutoPageInterceptor.class)
	public Ret queryyjjgkud(HashMap<String, Object> map) throws Exception {
		HashMap<String,Object> param = map;

		//判断权限
//			List<String> auth = UserKit.currentUserInfo().getAuth();
//			if(auth.contains("auth_select_part")){
//				String xian = UserKit.currentUserInfo().getArea().getString("name");
//				map.put("xian", xian);
//			}

		Page<Record> userPageRecord = ShouchukeshihuaDAO.queryyjjgkud(param);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

		/**
		 *寻找区县明细
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryquxianmingxi(HashMap<String, Object> map) throws Exception {
			HashMap<String,Object> param = map;
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryquxianmingxi(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		/**
		 *遍历区县基本信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Ret queryquxiancontent(HashMap<String, Object> map) throws Exception {
			Page<Record> userPageRecord = ShouchukeshihuaDAO.queryquxiancontent(map);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		
		
		/**
		 * 根据区县编码查询图片
		 * @param qyzzjgdm
		 * @param kdbm
		 * @return
		 */
		public List<Record> findquxiantp(String xzqhdm){
			return Db.find("select qumappath,qushiyipath from njpt_diqu where xzqhdm = ?",xzqhdm);
		}
		
		/**
		 *找到库点基本信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Page<Record> findcangfang(HashMap<String, Object> map) throws Exception {
		
			return ShouchukeshihuaDAO.findcangfang(map);
		}
		/**
		 *找到库点基本信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public Page<Record> findmysqlcf(HashMap<String, Object> map) throws Exception {
		
			return ShouchukeshihuaDAO.findmysqlcf(map);
		}
		
		public Page<Record> querylqtime(HashMap<String, Object> map) throws Exception {
			Page<Record> page=ShouchukeshihuaDAO.querylqtime(map);
			for (Record r : page.getList()) {
				String datetimeString = r.getStr("time");
				datetimeString = datetimeString.substring(0, 4) + "-"
						+ datetimeString.substring(4, 6) + "-"
						+ datetimeString.substring(6, 8) + " "
						+ datetimeString.substring(8, 10) + ":"
						+ datetimeString.substring(10, 12) + ":"
						+ datetimeString.substring(12, 14);
				r.set("datetime", datetimeString);
			}
			return page;
		}
		
		
		/**
		 *遍历库点信息
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Ret querykudian(HashMap<String, Object> map) throws Exception {
			HashMap<String,Object> param = map;
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			if("请选择".equals(param.get("qyzzjgdm"))){
				param.remove("qyzzjgdm");
			}
			if(map.containsKey("isksh")){
				if(Integer.parseInt(map.get("isksh").toString()) != 1){
					map.remove("isksh");
				}
			}
			
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querykudian(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		/**
		 * 企业用户下，只显示该企业下的库点
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Ret querykudianbyqiyeid(HashMap<String, Object> map) throws Exception {
			HashMap<String,Object> param = map;
			if("请选择".equals(param.get("xian"))){
				param.remove("xian");
			}
			if("请选择".equals(param.get("qyzzjgdm"))){
				param.remove("qyzzjgdm");
			}
			Page<Record> userPageRecord = ShouchukeshihuaDAO.querykudianbyqiyeid(param);
			Ret ret = Ret.create("list", userPageRecord);
			return ret;
		}
		/**
		 * 查找应急小组负责人
		 * @param map
		 * @return
		 */
		public List<Record> findfuzeren(HashMap<String, Object> map) {
			String danwei_id = (String) map.get("danwei_id");
			String sql;
			List<Record> list = null;
			if("请选择企业".equals(danwei_id)){
				sql = "select fuzeren from njpt_yingjixiaozu";
				list = Db.find(sql);
			}else{
				sql = "select fuzeren from njpt_yingjixiaozu where danwei_id = ?";
				list = Db.find(sql, danwei_id);
			}
			return list;
		}
		/**
		 * 查找应急小组成员
		 * @param map
		 * @return
		 */
		public List<Record> findchenyuan(HashMap<String, Object> map) {
			String fuzeren = (String) map.get("fuzeren");
			String sql;
			List<Record> list = null;
			if("请选择负责人".equals(fuzeren)){
				sql = "select chenyuan from njpt_yingjixiaozu";
				list = Db.find(sql);
			}else{
				sql = "select chenyuan from njpt_yingjixiaozu where fuzeren = ?";
				list = Db.find(sql, fuzeren);
			}
			return list;
		}
		
		
		/**
		 *区县下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryquxian(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.queryquxian(map);
		}
		/**
		 *区县下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryquxianhk(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.queryquxianhk(map);
		}
		/**
		 *区县下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> findyaokuxinxi(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.findyaokuxinxi(map);
		}
		
		/**
		 *区县下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> findcfkudian(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.findcfkudian(map);
		}
		
		/**
		 *数据表t_property 属性名下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> querypropname(HashMap<String, Object> map) throws Exception {
			return  ShouchukeshihuaDAO.querypropname(map);
		}
		/**
		 *数据表t_property 属性值下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> querypropvalue(HashMap<String, Object> map) throws Exception {
			return  ShouchukeshihuaDAO.querypropvalue(map);
		}
		/**
		 *应急小组负责人下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryfuzeren(HashMap<String, Object> map) throws Exception {
			return  ShouchukeshihuaDAO.queryfuzeren(map);
		}
		/**
		 *应急小组成员下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> querychenyuan(HashMap<String, Object> map) throws Exception {
			return  ShouchukeshihuaDAO.querychenyuan(map);
		}
		/**
		 *企业名称下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryqymc
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.queryqymc(map);
		}
		/**
		 *企业名称下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> findcaidanname
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.findcaidanname(map);
		}
		
		/**
		 *模块下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryrate
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.queryrate(map);
		}
		/**
		 *密码信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> querymima
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.querymima(map);
		}
		/**
		 *用户下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> finduserid
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.finduserid(map);
		}
		@Before(AutoPageInterceptor.class)
		public Page<Record> findrenyuanid
		(HashMap<String, Object> map) throws Exception {
			List<Record> list = new ArrayList<Record>();
			Page<Record> page=ShouchukeshihuaDAO.findrenyuanid(map);
			for (Record record : page.getList()) {
				List<Record> re=Db.find("SELECT * FROM fw_dep WHERE id not in (SELECT convert(varchar,id) from tz_qiye) and parentid=?",record.getStr("id"));
				for (Record record2 : re) {
					String sql="SELECT a.* FROM (SELECT * FROM fw_user WHERE id not in (SELECT id from tz_qiye)) as a JOIN fw_user_dep b ON a.id=b.userid WHERE b.depid=? ORDER BY a.orderno DESC";
					List<Record> record3=Db.find(sql,record2.getStr("id"));
					for (Record record4 : record3) {
						Record rec=new Record();
						rec.set("id", record4.get("id"));
						rec.set("realname", record4.get("realname"));
						list.add(rec);
					}
				}
			}
			Page<Record> page2=new Page<Record>(list, page.getPageNumber(), page.getPageSize(), page.getTotalPage(), 10000);
			return page2;
		}
		/**
		 *库点名称下拉信息获取
		 * @param map
		 * @return
		 * @throws Exception
		 */
		@Before(AutoPageInterceptor.class)
		public Page<Record> queryKDmc
		(HashMap<String, Object> map) throws Exception {
			
			return  ShouchukeshihuaDAO.queryKDmc(map);
		}

		/**
		 * 删除分期
		 * @param param
		 * @return
		 */
		public Ret delfenqi(HashMap<String, Object> param) {
			
			String sql = "delete from njpt_fenqishangbao where starttime = ? ";
			
			int result = Db.update(sql, param.get("starttime"));
			if(result > 0){
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","删除成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","新增失败"));
				return msg;
			}
		}

		public Ret deletewuribaobiao(HashMap<String, Object> param) {
			// TODO Auto-generated method stub
			String biaoji = (String) param.get("biaoji");
			String[] bj = biaoji.split(",");
			String temp = (String) param.get("temp");
			int result = 0;
			if(UserKit.currentUserInfo().getAuth().contains("auth_county")){
				//删除夏粮五日报表
				if("0".equals(temp)){
					String sql = "delete from njpt_xialiangshougouwuribb where year = ? and qishu = ? and dizhi = ? ";
					result = Db.update(sql, bj[0],bj[1],bj[2]);
				}
				//删除秋粮五日报表
				if("1".equals(temp)){
					String sql = "delete from njpt_qiuliangshougouwuribb where nianfen = ? and qishu = ? and quming = ? ";
					result = Db.update(sql, bj[0],bj[1],bj[2]);
				}
			}else{
				//删除夏粮五日报表
				if("0".equals(temp)){
					String sql = "delete from njpt_xialiangshougouwuribb where year = ? and qishu = ? and qiye = ? ";
					result = Db.update(sql, bj[0],bj[1],bj[2]);
				}
				//删除秋粮五日报表
				if("1".equals(temp)){
					String sql = "delete from njpt_qiuliangshougouwuribb where nianfen = ? and qishu = ? and qiye = ? ";
					result = Db.update(sql, bj[0],bj[1],bj[2]);
				}
			}
			if (result > 0) {
				Ret msg = Ret.create("ret", "true");
				msg.put(Ret.create("message","删除成功"));
				return msg;
			}else{
				Ret msg = Ret.create("ret", "false");
				msg.put(Ret.create("message","删除失败"));
				return msg;
			}
			
			
		}



		

		

		
		

		
}
