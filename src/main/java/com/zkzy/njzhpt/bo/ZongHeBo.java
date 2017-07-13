package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.dao.JibenxinxiDao;
import com.zkzy.njzhpt.dao.ZongHeDao;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class ZongHeBo {	
	/**
	 * 仓房利用率
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public  Ret findzcr(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		List<List> zcrList=new ArrayList();
		List zcrList1=new ArrayList();
		List zcrList2=new ArrayList();
		List crlist=new ArrayList();
		crlist.add(1);
		crlist.add("全市");
		crlist.add(String.format("%.0f",JibenxinxiDao.queryZhCfZcr(param).getList().get(0).getDouble("zcr")));
		crlist.add(String.format("%.0f",JibenxinxiDao.queryCfZycr(param).getList().get(0).getDouble("zcr")));
		zcrList.add(crlist);
		for(int i=0;i<quyu.size();i++){
			List crlist1=new ArrayList();
			crlist1.add(i+2);
			HashMap<String, Object> queryparam=new HashMap<String, Object>();
			queryparam.put("page", 1);
			queryparam.put("rows", 1);
			queryparam.put("xzqhdm", quyu.get(i).getStr("xzqhdm"));
			crlist1.add(quyu.get(i).getStr("name"));
			Page<Record>  zcr=JibenxinxiDao.queryZhCfZcr(queryparam);
			if(zcr.getList().size()>0){
				crlist1.add(String.format("%.0f",zcr.getList().get(0).getDouble("zcr")));
				zcrList1.add(String.format("%.0f",zcr.getList().get(0).getDouble("zcr")));
			}else{
				crlist1.add(0);								
				zcrList1.add(0);								
			}
			
			Page<Record>  zycr=JibenxinxiDao.queryCfZycr(queryparam);
			if(zycr.getList().size()>0){
				crlist1.add( String.format("%.0f",zycr.getList().get(0).getDouble("zcr")));				
				zcrList2.add( String.format("%.0f",zycr.getList().get(0).getDouble("zcr")));				
			}else{
				crlist1.add(0);								
				zcrList2.add(0);								
			}
			zcrList.add(crlist1);			
		}
			
		
		
		ret.put("crlist", zcrList);
		ret.put("crlist1", zcrList1);
		ret.put("crlist2", zcrList2);
		return ret;
	}
	
	/**
	 * 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findyear(HashMap<String, Object> param) throws Exception{
		List<Record> userPageRecord = ZongHeDao.findyear(param);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	
	/**
	 * 仓房类型统计
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cflxtj(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		Page<Record> cflist=  ZongHeDao.finCf(param);
		Page<Record> cflx=  ZongHeDao.finCflx(param);
		List<HashMap<String, Object>> param1 = new ArrayList<HashMap<String, Object>>();
		for(Record lx:cflx.getList()){
			List list=new ArrayList();
			HashMap<String, Object>  hashmap1=new HashMap<String, Object>();
			for(Record qy:quyu){
				Record numbers=Db.findFirst("select count(*) num from  tz_cangfang a where a.qyzzjgdm in (select b.qyzzjgdm from tz_qiye b where b.xzqhdm=? GROUP BY b.qyzzjgdm) and a.cflxbh=? ",qy.getStr("xzqhdm"),lx.getStr("BH"));
				list.add(numbers.getInt("num"));
			}
			//hashmap1.put(lx.getStr("MC"), list);
			hashmap1.put("name", lx.getStr("MC"));
			hashmap1.put("data",list);
			param1.add(hashmap1);
		}
		List quxianlist=new ArrayList();
		for(Record qy:quyu){
			quxianlist.add(qy.getStr("name"));
		}
		ret.put("quyu", quxianlist);
		ret.put("list", param1);
		return ret;
	}



	/**
	 * 全市仓房总仓容占比
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findcfcrzb(HashMap<String, Object> param) throws Exception {
		List<Record> quyu =	ZongHeDao.findQuyu(param);
		List param1 = new ArrayList();
		Ret ret =new Ret();
		for(Record record:quyu){
			HashMap<String, Object> queryparam=new HashMap<String, Object>();
			queryparam.put("xzqhdm", record.getStr("xzqhdm"));
			queryparam.put("page", 1);
			queryparam.put("rows", 1000);
			
			Page<Record>  page=ZongHeDao.findcflyl(queryparam);
			if(page.getList().size()>0){
				param1.add(String.format("%.0f",ZongHeDao.findcflyl(queryparam).getList().get(0).getDouble("zcr")));				
			}else{
				param1.add(0);	
			}
		}
		ret.put("quyu", quyu);
		ret.put("list", param1);
		return ret;
	}

	


	


	/**
	 * 库存占比
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findkczb(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		Page<Record> page= JibenxinxiDao.queryCfZycr(param);//自有仓容
		Page<Record> zkucun= JibenxinxiDao.queryCfZkc(param);//总库存
		Page<Record> zcr= JibenxinxiDao.queryCfZcr(param);//总仓容
		Page<Record> duijiezcr= JibenxinxiDao.duijiezcr(param);//已对接系统的企业总仓容
		if(page.getList().size()>0){
			ret.put("zyzcr", String.format("%.0f",page.getList().get(0).getDouble("zcr")));
		}else{
			ret.put("zyzcr", 0);
		}
		if(zcr.getList().size()>0){
			ret.put("zcr", String.format("%.0f",zcr.getList().get(0).getDouble("zcr")));
		}else{
			ret.put("zcr", 0);
		}
		if(zkucun.getList().size()>0){
			ret.put("dmStock", String.format("%.0f",zkucun.getList().get(0).getDouble("dmStock")) );
		}else{
			ret.put("dmStock", 0);
		}
		
		if(duijiezcr.getList().size()>0){
			ret.put("duijiezcr", String.format("%.0f",duijiezcr.getList().get(0).getDouble("duijiezcr")) );
		}else{
			ret.put("duijiezcr", 0);
		}
		
		return ret;
	}

	
	/**
	 * 分区域库存统计
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findfqck(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		List qylist=new ArrayList();
		List cklist=new ArrayList();	
		Ret ret=new Ret();
		for(int i=0;i<quyu.size();i++){
			qylist.add(quyu.get(i).getStr("name"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("xian", quyu.get(i).getStr("name"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.findzcrk(queryParam);
			  if(page.getList().size()>0){		  
				  cklist.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			  }else{
				  cklist.add(0);
			  }
		}
		ret.put("qylist", qylist);
		ret.put("cklist", cklist);
		return ret;
	}



	/**
	 * 分区品种统计库存
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findpzck(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		//List<Record> quyu1=	ZongHeDao.findlspz();
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));
		List pzlist=new ArrayList();
		//TODO  已经写死
		pzlist.add(0, "小麦");
		pzlist.add(1, "稻谷");
		pzlist.add(2, "玉米");
		pzlist.add(3, "大豆");
		pzlist.add(4, "其他");
		List btlist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List hjzck=new ArrayList();
		double hj=0;
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("区域");
		btlist.add("合计");
		hjzck.add(1);
		hjzck.add("全市");
		for(int i=0;i<quyu.size();i++){
			List pzcklist=new ArrayList();
			pzcklist.add(i+2);
			pzcklist.add(quyu.get(i).getStr("name"));
			//按区域获取库存总和
			HashMap<String,Object> Param=new HashMap<String, Object>();
			  Param.put("xian", quyu.get(i).getStr("name"));
			  Param.put("page", 1);
			  Param.put("rows", 1);
			  Page<Record> page1=  ZongHeDao.findzcrk(Param);
			  if(page1.getList().size()>0){
			    	pzcklist.add(String.format("%.0f",page1.getList().get(0).getDouble("dmStock")));
			    }else{
			    	pzcklist.add(0);
			    }
			  
			for(Record record:lspz){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("xian", quyu.get(i).getStr("name"));
				  queryParam.put("vCode",record.getStr("code"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 1);
				  Page<Record> page=  ZongHeDao.findzcrk1(queryParam);
				  
				  if(page.getList().size()>0){
				    	hj+=page.getList().get(0).getDouble("dmStock");
				    	pzcklist.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
				    }else{
				    	pzcklist.add(0);
				    }	    
			}
			pzzcklist.add(pzcklist);
		}
		hjzck.add(String.format("%.0f", hj));
		for(Record record: lspz){
			btlist.add(record.getStr("name"));
			//pzlist.add(record.getStr("name"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode",record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.findzcrk1(queryParam);
			  pzckMap.put("name", record.getStr("name"));
			 
			  if(page.getList().size()>0){
				  pzckMap.put("value",  String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
				  hjzck.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			  }else{
				  pzckMap.put("value",0);
				  hjzck.add(0);
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//品种
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表品种库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分品种合计
		return ret;
	}


	/**
	 * 分区性质库存统计
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findxzck(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		//List<Record>  lsxz=  ZongHeDao.findlsxz();
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0,new Record().set("code", "a").set("name", "央储"));  //1或者以11开头
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));//121
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));//122
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));//123
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));//32 33 34
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮")); //以2开头
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));//31
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));//129 99
		List pzlist=new ArrayList();
		pzlist.add(0, "央储");
		pzlist.add(1, "省储");
		pzlist.add(2, "市储");
		pzlist.add(3, "县储");
		pzlist.add(4, "临储");
		pzlist.add(5, "商品粮");
		pzlist.add(6, "最低价收购粮");
		pzlist.add(7, "其他储备粮");
		List btlist=new ArrayList();
		List qylist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List pzzcklist1=new ArrayList();
		List hjzck=new ArrayList();
		double hj=0;
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("区域");
		btlist.add("合计");
		hjzck.add(1);
		hjzck.add("全市");
		for(int i=0;i<quyu.size();i++){
			List pzcklist=new ArrayList();
			List pzcklist1=new ArrayList();
			qylist.add(quyu.get(i).getStr("name"));
			pzcklist.add(i+2);
			pzcklist.add(quyu.get(i).getStr("name"));
			
			HashMap<String,Object> Param=new HashMap<String, Object>();
			  Param.put("xian", quyu.get(i).getStr("name"));
			  Param.put("page", 1);
			  Param.put("rows", 1);
			  Page<Record> page1=  ZongHeDao.findzcrk(Param);
			  if(page1.getList().size()>0){
			    	pzcklist.add(String.format("%.0f",page1.getList().get(0).getDouble("dmStock")));				    	
			    }else{
			    	pzcklist.add(0);
			    }
			
			
			for(Record record:lsxz){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("xian", quyu.get(i).getStr("name"));
				  queryParam.put("vGrainPropertyCode",record.getStr("code"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 1);
				  Page<Record> page=  ZongHeDao.findzcrk2(queryParam);
				    if(page.getList().size()>0){
				    	hj+=page.getList().get(0).getDouble("dmStock");
				    	pzcklist.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    	pzcklist1.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    }else{
				    	pzcklist.add(0);    	
				    	pzcklist1.add(0);    	
				    }	    
			}
			pzzcklist.add(pzcklist);
			pzzcklist1.add(pzcklist1);
		}
		hjzck.add(String.format("%.0f", hj));
		for(Record record: lsxz){
			btlist.add(record.getStr("name"));
			//pzlist.add(record.getStr("name"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode",record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.findzcrk2(queryParam);
			  pzckMap.put("name", record.getStr("name"));
			  if(page.getList().size()>0){
				  pzckMap.put("value",  String.format("%.0f",Double.valueOf(String.format("%.0f",(page.getList().get(0).getDouble("dmStock"))))));
				  hjzck.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			  }else{
				  pzckMap.put("value",0);
				  hjzck.add(0);
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//性质
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表性质库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分性质合计
		ret.put("pzzcklist1", pzzcklist1);//折线图分性质合计
		ret.put("qylist", qylist);//折线图分性质合计
		
		return ret;
	}



	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret finddjck(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		//Page<Record>  lsdj=  ZongHeDao.findlsdj(param);//粮食等级
		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		List pzlist=new ArrayList();
		List btlist=new ArrayList();
		List qylist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List pzzcklist1=new ArrayList();
		List hjzck=new ArrayList();
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		DecimalFormat df = new DecimalFormat("0");
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("区域");
		hjzck.add(1);
		hjzck.add("全市");
		double hj=0;
		for(int i=0;i<quyu.size();i++){
			List pzcklist=new ArrayList();
			List pzcklist1=new ArrayList();
			qylist.add(quyu.get(i).getStr("name"));
			pzcklist.add(i+2);
			pzcklist.add(quyu.get(i).getStr("name"));
			HashMap<String,Object> Param=new HashMap<String, Object>();
			  Param.put("xian", quyu.get(i).getStr("name"));
			  Param.put("page", 1);
			  Param.put("rows", 1);
			  Page<Record> page1=  ZongHeDao.finddjck(Param);
			  if(page1.getList().size()>0){
			    	pzcklist.add(String.format("%.0f",page1.getList().get(0).getDouble("dmStock")));				    	
			    }else{
			    	pzcklist.add(0);
			    }
			 
			
			for(Record record:lsdj){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("xian", quyu.get(i).getStr("name"));
				  queryParam.put("vCode",record.getStr("vCode"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 10000);
				  Page<Record> page=  ZongHeDao.finddjck1(queryParam);
				    if(page.getList().size()>0){
				    	double dmStock=0;
				    	for(Record record1:page.getList()){
				    		hj+=record1.getDouble("dmStock");
				    		dmStock+=record1.getDouble("dmStock");
				    	}
				    	pzcklist.add(String.format("%.0f",dmStock)); 	
				    	pzcklist1.add(String.format("%.0f",dmStock)); 	
				    }else{
				    	pzcklist.add(0);    	
				    	pzcklist1.add(0);    	
				    }	    
			}
			pzzcklist.add(pzcklist);
			pzzcklist1.add(pzcklist1);
		}
		 hjzck.add(String.format("%.0f",hj));
		for(Record record: lsdj){
			btlist.add(record.getStr("vLevelName"));
			pzlist.add(record.getStr("vLevelName"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode",record.getStr("vCode"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record>  page= ZongHeDao.finddjck1(queryParam);
			  pzckMap.put("name", record.getStr("vLevelName"));
			  if(page.getList().size()>0){
				  double dmStock=0;
				  for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock").doubleValue();
			    	}
				  pzckMap.put("value", String.format("%.1f",dmStock));
				  hjzck.add(String.format("%.0f",dmStock));
			  }else{
				  pzckMap.put("value",0);
				  hjzck.add(0);
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//性质
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表性质库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分性质合计
		ret.put("pzzcklist1", pzzcklist1);//折线图分性质合计
		ret.put("qylist", qylist);//折线图分性质合计

		return ret;
	}

	/**
	 * 分区粮食年限库存统计
	 * @time  2017-04-21
	 * @author yzz
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findnxck(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		List<Record>  lsnf=  new ArrayList<Record>();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
				
			}
		}
		List pzlist=new ArrayList();
		List btlist=new ArrayList();
		List qylist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List pzzcklist1=new ArrayList();
		List hjzck=new ArrayList();
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		DecimalFormat df = new DecimalFormat("0");
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("区域");
		hjzck.add(1);
		hjzck.add("全市");
		double hj=0;
		for(int i=0;i<quyu.size();i++){
			List pzcklist=new ArrayList();
			List pzcklist1=new ArrayList();
			qylist.add(quyu.get(i).getStr("name"));
			pzcklist.add(i+2);
			pzcklist.add(quyu.get(i).getStr("name"));
			HashMap<String,Object> Param=new HashMap<String, Object>();
			  Param.put("xian", quyu.get(i).getStr("name"));
			  Param.put("page", 1);
			  Param.put("rows", 1);
			  Page<Record> page1=  ZongHeDao.findnxck(Param);
			  if(page1.getList().size()>0){
			  		hj+=page1.getList().get(0).getDouble("dmStock");
			    	pzcklist.add(String.format("%.0f",page1.getList().get(0).getDouble("dmStock")));				    	
			    }else{
			    	pzcklist.add(0);
			    }
			
			
			for(Record record:lsnf){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("xian", quyu.get(i).getStr("name"));
				  queryParam.put("year",record.getStr("year"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 10000);
				  Page<Record> page=  ZongHeDao.findnxck1(queryParam);
				    if(page.getList().size()>0){
				    	double dmStock=0;
				    	for(Record record1:page.getList()){
				    		dmStock+=record1.getDouble("dmStock");
				    	}
				    	
				    	pzcklist.add((dmStock)<0?0:String.format("%.0f",dmStock)); 	
				    	pzcklist1.add(String.format("%.0f",dmStock));	 	
				    }else{
				    	pzcklist.add(0);    	
				    	pzcklist1.add(0);    	
				    }	    
			}
			pzzcklist.add(pzcklist);
			pzzcklist1.add(pzcklist1);
		}
		hjzck.add(String.format("%.0f",hj));
		for(Record record: lsnf){
			btlist.add(record.getStr("year"));
			pzlist.add(record.getStr("year"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			 queryParam.put("year",record.getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record>  page= ZongHeDao.findnxck1(queryParam);
			  pzckMap.put("name", record.getStr("year"));
			  if(page.getList().size()>0){
				  double dmStock=0;
				  for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock").doubleValue();
			    	}
				  pzckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",dmStock))));
				  hjzck.add(String.format("%.0f",dmStock));
			  }else{
				  pzckMap.put("value",0);
				  hjzck.add(0);
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//性质
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表性质库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分性质合计
		ret.put("pzzcklist1", pzzcklist1);//折线图分性质合计
		ret.put("qylist", qylist);//折线图分性质合计

		return ret;
	}

	
	
	/**
	 * 品种性质综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findpzxzfx(HashMap<String, Object> param) throws Exception {
		
		List<Record>  lspz=  ZongHeDao.findlspz();
		List<Record>  lsxz=  ZongHeDao.findlsxz();
		List pzlist=new ArrayList();
		List btlist=new ArrayList();
		List qylist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List pzzcklist1=new ArrayList();
		List hjzck=new ArrayList();
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("品种");
		hjzck.add(1);
		hjzck.add("全市");
		for(int i=0;i<lspz.size();i++){
			List pzcklist=new ArrayList();
			List pzcklist1=new ArrayList();
			pzlist.add(lspz.get(i).getStr("name"));
			qylist.add(lspz.get(i).getStr("name"));
			pzcklist.add(i+2);
			pzcklist.add(lspz.get(i).getStr("name"));
			for(Record record:lsxz){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("vGrainSubTypeCode", lspz.get(i).getStr("code"));
				  queryParam.put("vGrainPropertyCode",record.getStr("code"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 1);
				  Page<Record> page=  ZongHeDao.findzcrk(queryParam);
				    if(page.getList().size()>0){
				    	pzcklist.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    	pzcklist1.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    }else{
				    	pzcklist.add(String.format("%.0f",0.00));    	
				    	pzcklist1.add(String.format("%.0f",0.00));    	
				    }	    
			}
			pzzcklist.add(pzcklist);
			pzzcklist1.add(pzcklist1);
		}
		
		for(Record record:lspz){
			HashMap<String,Object> xzckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainSubTypeCode", record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzcrk(queryParam);
			  xzckMap.put("name", record.getStr("name"));
			    if(page.getList().size()>0){
			    		xzckMap.put("value",  String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			    	}else{
			    		xzckMap.put("value",String.format("%.0f",0.00));     	
			    }
			    xzzck.add(xzckMap); 
		}
		
		
		for(Record record: lsxz){
			btlist.add(record.getStr("name"));
			pzlist.add(record.getStr("name"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode",record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.findzcrk(queryParam);
			  pzckMap.put("name", record.getStr("name"));
			  if(page.getList().size()>0){
				  pzckMap.put("value",  String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
				  hjzck.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			  }else{
				  pzckMap.put("value",String.format("%.0f",0.00));
				  hjzck.add(String.format("%.0f",0.00));
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//性质
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表性质库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分性质合计
		ret.put("pzzcklist1", pzzcklist1);//折线图分性质合计
		ret.put("qylist", qylist);//折线图分性质合计
		ret.put("xzzck", xzzck);//折线图分性质合计
		
		return ret;
	}


	/**
	 * 品种等级综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findpzdjfx(HashMap<String, Object> param) throws Exception {
		List<Record>  lspz=  ZongHeDao.findlspz();
		Page<Record>  lsdj=  ZongHeDao.findlsdj(param);//粮食等级
		List pzlist=new ArrayList();
		List btlist=new ArrayList();
		List qylist=new ArrayList();
		List listhj=new ArrayList();//合计
		List pzzcklist=new ArrayList();
		List pzzcklist1=new ArrayList();
		List hjzck=new ArrayList();
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		btlist.add("编号");
		btlist.add("品种");
		hjzck.add(1);
		hjzck.add("全市");
		for(int i=0;i<lspz.size();i++){
			List pzcklist=new ArrayList();
			List pzcklist1=new ArrayList();
			pzlist.add(lspz.get(i).getStr("name"));
			qylist.add(lspz.get(i).getStr("name"));
			pzcklist.add(i+2);
			pzcklist.add(lspz.get(i).getStr("name"));
			for(Record record:lsdj.getList()){
				HashMap<String,Object> queryParam=new HashMap<String, Object>();
				  queryParam.put("vGrainSubTypeCode", lspz.get(i).getStr("code"));
				  queryParam.put("vCode",record.getStr("vCode"));
				  queryParam.put("page", 1);
				  queryParam.put("rows", 1);
				  Page<Record> page=  ZongHeDao.finddjck(queryParam);
				    if(page.getList().size()>0){
				    	pzcklist.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    	pzcklist1.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))); 	
				    }else{
				    	pzcklist.add(String.format("%.0f",0.00));    	
				    	pzcklist1.add(String.format("%.0f",0.00));    	
				    }	    
			}
			pzzcklist.add(pzcklist);
			pzzcklist1.add(pzcklist1);
		}
		
		for(Record record:lspz){
			HashMap<String,Object> xzckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainSubTypeCode", record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.finddjck(queryParam);
			  xzckMap.put("name", record.getStr("name"));
			    if(page.getList().size()>0){
			    		xzckMap.put("value",  String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			    	}else{
			    		xzckMap.put("value",String.format("%.0f",0.00));     	
			    }
			    xzzck.add(xzckMap); 
		}
		
		
		for(Record record: lsdj.getList()){
			btlist.add(record.getStr("vLevelName"));
			pzlist.add(record.getStr("vLevelName"));
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode",record.getStr("vCode"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.finddjck(queryParam);
			  pzckMap.put("name", record.getStr("vLevelName"));
			  if(page.getList().size()>0){
				  pzckMap.put("value",  String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
				  hjzck.add(String.format("%.0f",page.getList().get(0).getDouble("dmStock")));
			  }else{
				  pzckMap.put("value",String.format("%.0f",0.00));
				  hjzck.add(String.format("%.0f",0.00));
			  }
			  pzzck.add(pzckMap);
		 }
		
		ret.put("pzlist", pzlist);//性质
		ret.put("btlist", btlist);//表头
		ret.put("pzzcklist", pzzcklist);//列表性质库存
		ret.put("hjzck", hjzck);//列表合计
		ret.put("pzzck", pzzck);//饼状图分性质合计
		ret.put("pzzcklist1", pzzcklist1);//折线图分性质合计
		ret.put("qylist", qylist);//折线图分性质合计
		ret.put("xzzck", xzzck);//折线图分性质合计
		
		return ret;
	}


	
	
	/**
	 * 查询仓房状态明细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findcfzt(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		String[] cfzt = {"完好可用","需大修","待报废","待拆除","死角仓","其他"};
		String[] cfztmc = {"whky","xdx","dbf","dcc","sjk","qt"};
		float[] heji = {0,0,0,0,0,0};
		float sumheji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			float sumlx = 0;
			Record lxsum = new Record();
			for(int i = 0; i < cfzt.length; i++){
				param1.put("xzqhdm", xzqhdm);
				param1.put("cfzt", cfzt[i]);
				param1.put("cfztmc", cfztmc[i]);
				Record fqcfzt = new Record();
				if(i == (cfzt.length - 1)){
					fqcfzt = ZongHeDao.findfqcfztqt(param1);
				}else{
					fqcfzt = ZongHeDao.findfqcfzt(param1);
				}
				if(fqcfzt == null){
					fqcfzt = new Record();
					fqcfzt.set(cfztmc[i], 0);
				}else if(fqcfzt.get(cfztmc[i]) == null){
					fqcfzt.set(cfztmc[i], 0);
				}
				float kc = Float.parseFloat(fqcfzt.get(cfztmc[i]).toString());
				heji[i] += kc;
				sumlx += kc;
				sumheji += kc;
				quyu.get(j).setColumns(fqcfzt);
			}
			lxsum.set("sumlx", sumlx);
			quyu.get(j).setColumns(lxsum);
			
		}
		for(int i = 0; i < cfztmc.length; i++){
			r.set(cfztmc[i], heji[i]);
		}
		r.set("sumlx", sumheji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}


	/**
	 * 寻找分区仓房使用情况
	 * @param param
	 * @return
	 */
	public Ret findcfsy(HashMap<String, Object> param) {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		String[] cfsy = {"已装粮","闲置","季节性暂时空置","占地作用"};
		String[] cfsymc = {"yzl","xz","jjxzskz","zdzy"};
		float[] heji = {0,0,0,0};
		float sumheji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			float sumlx = 0;
			Record lxsum = new Record();
			for(int i = 0; i < cfsy.length; i++){
				param1.put("xzqhdm", xzqhdm);
				param1.put("cfsy", cfsy[i]);
				param1.put("cfsymc", cfsymc[i]);
				Record fqcfsy = ZongHeDao.findfqcfsy(param1);
				if(fqcfsy == null){
					fqcfsy = new Record();
					fqcfsy.set(cfsymc[i], 0);
				}else if(fqcfsy.get(cfsymc[i]) == null){
					fqcfsy.set(cfsymc[i], 0);
				}
				
				float kc = Float.parseFloat(fqcfsy.get(cfsymc[i]).toString());
				heji[i] += kc;
				sumlx += kc;
				sumheji += kc;
				quyu.get(j).setColumns(fqcfsy);
			}
			lxsum.set("sumlx", sumlx);
			quyu.get(j).setColumns(lxsum);
			
		}
		for(int i = 0; i < cfsymc.length; i++){
			r.set(cfsymc[i], heji[i]);
		}
		r.set("sumlx", sumheji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
		
		
	}


	/**
	 * 寻找仓房年度使用情况
	 * @param param
	 * @return
	 */
	public Ret findcfnd(HashMap<String, Object> param) {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		String[] cfnd = {"1999","2000","2014"};
		String[] cfndmc = {"eight","nine","zero"};
		float[] heji = {0,0,0,0,0};
		float sumheji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			float sumlx = 0;
			Record lxsum = new Record();
			for(int i = 0; i < cfnd.length; i++){
				param1.put("xzqhdm", xzqhdm);
				param1.put("cfnd", cfnd[i]);
				param1.put("cfndmc", cfndmc[i]);
				Record fqcfnd = ZongHeDao.findfqcfnd(param1);
				if(fqcfnd == null){
					fqcfnd = new Record();
					fqcfnd.set(cfndmc[i], 0);
				}else if(fqcfnd.get(cfndmc[i]) == null){
					fqcfnd.set(cfndmc[i], 0);
				}
				
				float kc = Float.parseFloat(fqcfnd.get(cfndmc[i]).toString());
				heji[i] += kc;
				sumlx += kc;
				sumheji += kc;
				quyu.get(j).setColumns(fqcfnd);
			}
			lxsum.set("sumlx", sumlx);
			quyu.get(j).setColumns(lxsum);
		}
		
		
		for(int i = 0; i < cfndmc.length; i++){
			r.set(cfndmc[i], heji[i]);
		}
		r.set("sumlx", sumheji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
		
	}
	
	
	/**
	 * 全市仓房类型仓容占比
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findcflxzcrzb(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		
		Record r = new Record();
		r.set("name", "全市");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "全市");
		String[] cflx = {"('平房仓')","('浅圆仓')","('立筒仓')","('楼房仓')","('储粮罩棚')","('仓间罩棚')","('铁路罩棚')","('其他简易仓')","('油罐','异形仓')"};
		String[] cflxmc = {"pfc","qyc","ltc","lfc","clzp","cjzp","tlzp","qtjyc","youguan"};
		DecimalFormat df = new DecimalFormat("0");
		float[] heji = {0,0,0,0,0,0,0,0,0};
		float sumheji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			float sumlx = 0;
			Record lxsum = new Record();
			for(int i = 0; i < cflx.length; i++){
				param1.put("xzqhdm", xzqhdm);
				param1.put("cflx", cflx[i]);
				param1.put("cflxmc", cflxmc[i]);
				Record fqcflx = new Record();
				if(i == (cflx.length - 1)){
					fqcflx = ZongHeDao.findcfcrqt(param1);
				}else{
					fqcflx = ZongHeDao.findcfcr(param1);
				}
				if(fqcflx == null){
					fqcflx = new Record();
					fqcflx.set(cflxmc[i], 0);
				}else if(fqcflx.get(cflxmc[i]) == null){
					fqcflx.set(cflxmc[i], 0);
				}
				float kc = Float.parseFloat(fqcflx.get(cflxmc[i]).toString());
				sumlx += kc;
				sumheji += kc;
				heji[i] += kc;
				fqcflx.set(cflxmc[i], df.format(kc));
				quyu.get(j).setColumns(fqcflx);
			}
			lxsum.set("sumlx", sumlx);
			quyu.get(j).setColumns(lxsum);
			
		}
		for(int i = 0; i < cflxmc.length; i++){
			
			r.set(cflxmc[i], df.format(heji[i]));
		}
		r.set("sumlx", sumheji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
		

	}
	
	
	/**
	 * 仓房储粮技术
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Ret cfcljs(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		DecimalFormat df = new DecimalFormat("0");
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		String[] cfcl = {"计算机测温","机械通风","环流熏蒸","低温储藏","气调储粮","其他技术"};
		String[] cfclmc = {"jsjcw","jxtf","hlxz","dwcl","qtcl","qtjs"};
		float[] heji = {0,0,0,0,0,0};
		float sumheji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			float sumlx = 0;
			Record lxsum = new Record();
			for(int i = 0; i < cfcl.length; i++){
				param1.put("xzqhdm", xzqhdm);
				param1.put("cfcl", cfcl[i]);
				param1.put("cfclmc", cfclmc[i]);
				Record fqcfcl = ZongHeDao.cfcljs(param1);
				if(fqcfcl == null){
					fqcfcl = new Record();
					fqcfcl.set(cfclmc[i], 0);
				}else if(fqcfcl.get(cfclmc[i]) == null){
					fqcfcl.set(cfclmc[i], 0);
				}
				float kc = Float.parseFloat(df.format(Float.parseFloat(fqcfcl.get(cfclmc[i]).toString())));
				
				sumlx += kc;
				sumheji += kc;
				heji[i] += kc;
				fqcfcl.set(cfclmc[i], df.format(kc));
				quyu.get(j).setColumns(fqcfcl);
			}
			lxsum.set("sumlx", sumlx);
			quyu.get(j).setColumns(lxsum);
			
		}
		for(int i = 0; i < cfclmc.length; i++){
			r.set(cfclmc[i],df.format(heji[i]));
		}
		r.set("sumlx", sumheji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
		
	}
	
	
	
	/**
	 * 烘干能力统计分析
	 * @author yzz
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findhgnl(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		float heji = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
		
			param1.put("xzqhdm", xzqhdm);
			Record fqcfhg = ZongHeDao.findhgnl(param1);
			if(fqcfhg == null){
				fqcfhg = new Record();
				fqcfhg.set("hgnl", 0);
			}else if(fqcfhg.get("hgnl") == null){
				fqcfhg.set("hgnl", 0);
			}
			float kc = Float.parseFloat(fqcfhg.get("hgnl").toString());
			heji += kc;
			quyu.get(j).setColumns(fqcfhg);
			
			
		}
		r.set("hgnl", heji);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}

	/**
	 * 查询种植面积
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Before(AutoPageInterceptor.class)
	public Ret findzzmj(HashMap<String, Object> param) throws Exception {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		Record r = new Record();
		r.set("name", "合计");
		r.set("orderno", 0);
		r.set("childcount", 0);
		r.set("parentid", "83794");
		r.set("canuse", 1);
		r.set("id", "7923hdoiwju98eu21ue");
		r.set("xzqhdm", "合计");
		int czrk = 0;
		int gztd = 0;
		int bztd = 0;
		int zwtd = 0;
		int xmzl = 0;
		int dgzl = 0;
		for(int j = 0; j < quyu.size(); j++){
			String xzqhdm = quyu.get(j).getStr("xzqhdm");
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("xzqhdm", xzqhdm);
			if(param.containsKey("niandu"))
			{
				param1.put("niandu", param.get("niandu"));
			}
			Record fqzzmj = ZongHeDao.findzzmj(param1);
			if(fqzzmj == null){
				fqzzmj = new Record();
				fqzzmj.set("czrk", 0);
				fqzzmj.set("zc_liangshi", 0);
				fqzzmj.set("qnlsxfl", 0);
				fqzzmj.set("mj_liangshi", 0);
				fqzzmj.set("zc_daogu", 0);
				fqzzmj.set("zc_xiaomai", 0);
			}else{
				int czrkkc;
				int gztdkc;
				int bztdkc;
				int zwtdkc;
				int xmzlkc;
				int dgzlkc;
				if(StrKit.isBlank(fqzzmj.get("czrk"))){
					fqzzmj.set("czrk", 0);
					czrkkc = 0;
				}else{
					czrkkc =Integer.parseInt(fqzzmj.get("czrk"));
				}
				if(StrKit.isBlank(fqzzmj.get("zc_liangshi"))){
					fqzzmj.set("zc_liangshi", 0);
					gztdkc = 0;
				}else{
					gztdkc = Integer.parseInt(fqzzmj.get("zc_liangshi"));
				}
				if(StrKit.isBlank(fqzzmj.get("qnlsxfl"))){
					fqzzmj.set("qnlsxfl", 0);
					bztdkc = 0;
				}else{
					bztdkc =Integer.parseInt(fqzzmj.get("qnlsxfl"));
				}
				if(StrKit.isBlank(fqzzmj.getStr("kllsxfl"))){
					fqzzmj.set("kllsxfl", 0);
					zwtdkc = 0;
				}else{
					zwtdkc = Integer.parseInt(fqzzmj.get("kllsxfl"));
				}
				if(StrKit.isBlank(fqzzmj.getStr("zc_xiaomai"))){
					fqzzmj.set("zc_xiaomai", 0);
					xmzlkc = 0;
				}else{
					xmzlkc = Integer.parseInt(fqzzmj.get("zc_xiaomai"));
				}
				if(StrKit.isBlank(fqzzmj.getStr("zc_daogu"))){
					fqzzmj.set("zc_daogu", 0);
					dgzlkc = 0;
				}else{
					dgzlkc = Integer.parseInt(fqzzmj.get("zc_daogu"));
				}
				czrk += czrkkc;
				gztd += gztdkc;
				bztd += bztdkc;
				zwtd += zwtdkc;
				dgzl += dgzlkc;
				xmzl += xmzlkc;
			}
			quyu.get(j).setColumns(fqzzmj);
		}
		r.set("czrk", czrk);
		r.set("zc_liangshi", gztd);
		r.set("qnlsxfl", bztd);
		r.set("kllsxfl", zwtd);
		r.set("zc_daogu", dgzl);
		r.set("zc_xiaomai", xmzl);
		quyu.add(0, r);
		Page<Record> userPageRecord = new Page<>(quyu, 1, 7, 1, 7);
		Ret ret = Ret.create("list", userPageRecord);
		return ret;
	}
	/**
	 * 弹框
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	@Before(AutoPageInterceptor.class)
	public Ret findzkcfpz(HashMap<String, Object> param) throws Exception {
		Ret ret=new Ret();
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> djzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> nxzck=new ArrayList<HashMap<String,Object>>();
		
		
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));
		for(Record record: lspz){
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			 HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode",record.getStr("code"));
			  queryParam.put("xian",quyu.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record>  page= ZongHeDao.findzcrk1(queryParam);
			  pzckMap.put("name", record.getStr("name"));
			 
			  if(page.getList().size()>0){
				  pzckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock")))));
			  }else{
				  pzckMap.put("value",0);
			  }
			  pzzck.add(pzckMap);
		 }
		
		
		
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0,new Record().set("code", "a").set("name", "央储"));  //1或者以11开头
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));//121
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));//122
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));//123
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));//32 33 34
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮")); //以2开头
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));//31
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));//129 99
		for(Record record:lsxz){
			HashMap<String,Object> xzckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("xian", quyu.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
			  queryParam.put("vGrainPropertyCode",record.getStr("code"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzcrk2(queryParam);
			  xzckMap.put("name", record.getStr("name"));
			    if(page.getList().size()>0){
			    	xzckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock")))));		
			    }else{   	
			    	xzckMap.put("value",0);    	
			    }	
			    xzzck.add(xzckMap);
		}
		
		
		
		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		for(Record record:lsdj){
			HashMap<String,Object> djckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("xian", quyu.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
			  queryParam.put("vCode",record.getStr("vCode"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.finddjck1(queryParam);
			  djckMap.put("name", record.getStr("vLevelName"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	djckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock")))));		
					 
			    }else{
			    	djckMap.put("value",0);   	
			    }	
			    djzck.add(djckMap); 
		}
			
		List<Record>  lsnf=  new ArrayList<Record>();

		List btlist=new ArrayList();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
				
			}
		}
		for(Record record:lsnf){
			btlist.add(record.getStr("year"));
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("xian", quyu.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
			  queryParam.put("year",record.getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findnxck1(queryParam);
			  nxckMap.put("name", record.getStr("year"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	   	
			    	nxckMap.put("value",0);    	
			    }
			    nxzck.add(nxckMap);
		}
		
		
		ret.put("pzzck", pzzck);
		ret.put("xzzck", xzzck);
		ret.put("djzck", djzck);
		ret.put("nxzck", nxzck);
		ret.put("btlist", btlist);
		ret.put("quyu", quyu.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
		return ret;
	}
	
	
	@Before(AutoPageInterceptor.class)
	public Ret findzkcpz(HashMap<String, Object> param) throws Exception {
		
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> djzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> nxzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		//品种
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));
		
		//性质
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0,new Record().set("code", "a").set("name", "央储"));
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮"));
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));
		
		for(Record record:lsxz){
			HashMap<String,Object> xzckMap=new HashMap<String, Object>();
			xzckMap.put("name",record.getStr("name"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode", lspz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//品种
			  queryParam.put("vGrainPropertyCode",record.getStr("code"));//性质
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz(queryParam);
			  if(page.getList().size()>0){
				  xzckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
				  
			    }else{
			    	xzckMap.put("value",0);
			    }	
			  xzzck.add(xzckMap);
		}
		

		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		for(Record record:lsdj){
			HashMap<String,Object> djckMap=new HashMap<String, Object>();
			djckMap.put("name",record.getStr("vLevelName"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode", lspz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//品种
			  queryParam.put("grade",record.getStr("vCode"));//等级
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz1(queryParam);
			  if(page.getList().size()>0){
				    djckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
				  
			    }else{
			    	djckMap.put("value",0);
			    }	
			  djzck.add(djckMap);
		}
		
		List<Record>  lsnf=  new ArrayList<Record>();

		List btlist=new ArrayList();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
				
			}
		}
		for(Record record:lsnf){
			btlist.add(record.getStr("year"));
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode", lspz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//品种
			  queryParam.put("year",record.getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz2(queryParam);
			  nxckMap.put("name", record.getStr("year"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    nxzck.add(nxckMap);
		}
		ret.put("xzzck", xzzck);
		ret.put("djzck", djzck);
		ret.put("nxzck", nxzck);
		ret.put("btlist", btlist);
		ret.put("pz", lspz.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
		
		return ret;
	}
	
	
	@Before(AutoPageInterceptor.class)
	public Ret findzkcxz(HashMap<String, Object> param) throws Exception {	
		Ret ret=new Ret();

		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> djzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> nxzck=new ArrayList<HashMap<String,Object>>();
		//性质
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0,new Record().set("code", "a").set("name", "央储"));
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮"));
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));
		
		//品种
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));
		
		for(Record record:lspz){
			HashMap<String,Object> pzckMap=new HashMap<String, Object>();
			pzckMap.put("name",record.getStr("name"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode", lsxz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//品种
			  queryParam.put("vCode",record.getStr("code"));//性质
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz(queryParam);
			  if(page.getList().size()>0){
				  pzckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 		  
			    }else{
			    	pzckMap.put("value",0);
			    }	
			  pzzck.add(pzckMap);
		}
		
		
		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		for(Record record:lsdj){
			HashMap<String,Object> djckMap=new HashMap<String, Object>();
			djckMap.put("name",record.getStr("vLevelName"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode", lsxz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//
			  queryParam.put("grade",record.getStr("vCode"));//等级
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz3(queryParam);
			  if(page.getList().size()>0){
				    djckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
				  
			    }else{
			    	djckMap.put("value",0);
			    }	
			  djzck.add(djckMap);
		}
		
		List<Record>  lsnf=  new ArrayList<Record>();

		List btlist=new ArrayList();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
			}
		}
		for(Record record:lsnf){
			btlist.add(record.getStr("year"));
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode", lsxz.get(Integer.valueOf((String)param.get("index"))).getStr("code"));//品种
			  queryParam.put("year",record.getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz4(queryParam);
			  nxckMap.put("name", record.getStr("year"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    nxzck.add(nxckMap);
		}
		
		
		ret.put("pzzck", pzzck);
		ret.put("djzck", djzck);
		ret.put("nxzck", nxzck);
		ret.put("btlist", btlist);
		ret.put("xz", lsxz.get(Integer.valueOf((String)param.get("index"))).getStr("name"));
		return ret;
	}
	
	
	@Before(AutoPageInterceptor.class)
	public Ret findzkcdj(HashMap<String, Object> param) throws Exception {
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> nxzck=new ArrayList<HashMap<String,Object>>();
		Ret ret=new Ret();
		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		
			
		//品种
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));	
		
		for(Record record:lspz){
			HashMap<String,Object> djckMap=new HashMap<String, Object>();
			djckMap.put("name",record.getStr("name"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("grade", lsdj.get(Integer.valueOf((String)param.get("index"))).getStr("vCode"));//等级
			  queryParam.put("vCode",record.getStr("code"));//品种
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz1(queryParam);
			  if(page.getList().size()>0){
				    djckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 		  
			    }else{
			    	djckMap.put("value",0);
			    }	
			  pzzck.add(djckMap);
		}
		
		//性质
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0, new Record().set("code", "a").set("name", "央储"));
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮"));
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));
		
		for(Record record:lsxz){
			HashMap<String,Object> djckMap=new HashMap<String, Object>();
			djckMap.put("name",record.getStr("name"));
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode", record.getStr("code"));//性质
			  queryParam.put("grade", lsdj.get(Integer.valueOf((String)param.get("index"))).getStr("vCode"));//等级
			  queryParam.put("page", 1);
			  queryParam.put("rows", 1);
			  Page<Record> page=  ZongHeDao.findzkcpz3(queryParam);
			  if(page.getList().size()>0){
				    djckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.2f",page.getList().get(0).getDouble("dmStock"))))); 	
				  
			    }else{
			    	djckMap.put("value",0);
			    }	
			  xzzck.add(djckMap);
		}
		
		
		List<Record>  lsnf=  new ArrayList<Record>();

		List btlist=new ArrayList();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
			}
		}
		for(Record record:lsnf){
			btlist.add(record.getStr("year"));
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("grade", lsdj.get(Integer.valueOf((String)param.get("index"))).getStr("vCode"));//品种
			  queryParam.put("year",record.getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz5(queryParam);
			  nxckMap.put("name", record.getStr("year"));
			  
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    nxzck.add(nxckMap);
		}
		
		ret.put("pzzck", pzzck);
		ret.put("xzzck", xzzck);
		ret.put("nxzck", nxzck);
		ret.put("btlist", btlist);
		ret.put("dj", lsdj.get(Integer.valueOf((String)param.get("index"))).getStr("vLevelName"));
		
		return ret;
	}
	
	@Before(AutoPageInterceptor.class)
	public Ret findzkcnx(HashMap<String, Object> param) throws Exception {		
		Ret ret=new Ret();
		List<HashMap<String,Object>> pzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> xzzck=new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> nxzck=new ArrayList<HashMap<String,Object>>();
		List<Record>  lsnf=  new ArrayList<Record>();
		List btlist=new ArrayList();
		Record yearMax=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] DESC");
		Record yearMin=  Db.use("njpt").findFirst("select [year] from kc_CurrentStock  where [year] is not NULL  and [year]!='' and LEN([year])=4 GROUP BY [year]  ORDER BY [year] ASC");
		Integer maxyear=Integer.valueOf(yearMax.getStr("year"));
		Integer minyear=Integer.valueOf(yearMin.getStr("year"));
		int j=0;
		for(int i=minyear;i<=maxyear;i++){
			btlist.add(String.valueOf(i));
			lsnf.add(j,new Record().set("year", String.valueOf(i)));
			j++;
			if(i==maxyear){
				btlist.add("其他");
				lsnf.add(j,new Record().set("year", "其他"));//其他年份
			}
		}
		
		//品种
		List<Record>  lspz=  new ArrayList<Record>();
		lspz.add(0,new Record().set("code", "111").set("name", "小麦"));
		lspz.add(1, new Record().set("code", "113").set("name", "稻谷"));
		lspz.add(2, new Record().set("code", "112").set("name", "玉米"));
		lspz.add(3, new Record().set("code", "1411001").set("name", "大豆"));
		lspz.add(4, new Record().set("code", "888").set("name", "其他"));
		
		for(Record record:lspz){
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vCode",record.getStr("code") );//品种
			  queryParam.put("year",lsnf.get(Integer.valueOf((String)param.get("index"))).getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz2(queryParam);
			  nxckMap.put("name", record.getStr("name"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.2f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    pzzck.add(nxckMap);
		}
		
		//性质
		List<Record>  lsxz=  new ArrayList<Record>();
		lsxz.add(0, new Record().set("code", "a").set("name", "央储"));
		lsxz.add(1, new Record().set("code", "b").set("name", "省储"));
		lsxz.add(2, new Record().set("code", "c").set("name", "市储"));
		lsxz.add(3, new Record().set("code", "d").set("name", "县储"));
		lsxz.add(4, new Record().set("code", "e").set("name", "临储"));
		lsxz.add(5, new Record().set("code", "f").set("name", "商品粮"));
		lsxz.add(6, new Record().set("code", "g").set("name", "最低价收购粮"));
		lsxz.add(7, new Record().set("code", "h").set("name", "其他储备粮"));
		
		for(Record record:lsxz){
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("vGrainPropertyCode",record.getStr("code"));//品种
			  queryParam.put("year",lsnf.get(Integer.valueOf((String)param.get("index"))).getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz4(queryParam);
			  nxckMap.put("name", record.getStr("name"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    xzzck.add(nxckMap);
		}
		
		List<Record>  lsdj=  new ArrayList<Record>();
		lsdj.add(0,new Record().set("vCode", "01").set("vLevelName", "一等"));
		lsdj.add(1,new Record().set("vCode", "02").set("vLevelName", "二等"));
		lsdj.add(2,new Record().set("vCode", "03").set("vLevelName", "三等"));
		lsdj.add(3,new Record().set("vCode", "04").set("vLevelName", "四等"));
		lsdj.add(4,new Record().set("vCode", "05").set("vLevelName", "五等"));
		lsdj.add(5,new Record().set("vCode", "06").set("vLevelName", "等外级"));
		
		for(Record record:lsdj){
			HashMap<String,Object> nxckMap=new HashMap<String, Object>();
			HashMap<String,Object> queryParam=new HashMap<String, Object>();
			  queryParam.put("grade", record.getStr("vCode"));//品种
			  queryParam.put("year",lsnf.get(Integer.valueOf((String)param.get("index"))).getStr("year"));
			  queryParam.put("page", 1);
			  queryParam.put("rows", 10000);
			  Page<Record> page=  ZongHeDao.findzkcpz5(queryParam);
			  nxckMap.put("name", record.getStr("vLevelName"));
			    if(page.getList().size()>0){
			    	double dmStock=0;
			    	for(Record record1:page.getList()){
			    		dmStock+=record1.getDouble("dmStock");
			    	}
			    	nxckMap.put("value", String.format("%.0f",Double.valueOf(String.format("%.0f",page.getList().get(0).getDouble("dmStock"))))); 	
			    }else{
			    	nxckMap.put("value",0);    	
			    }
			    nxzck.add(nxckMap);
		}
		
		
		ret.put("btlist", btlist);
		ret.put("pzzck", pzzck);
		ret.put("xzzck", xzzck);
		ret.put("nxzck", nxzck);
		ret.put("nx", lsnf.get(Integer.valueOf((String)param.get("index"))).getStr("year"));
		
		return ret;
	}
	

	/**
	 * 寻找自有库存
	 * @param param
	 * @return
	 */
	public Ret findzycr(HashMap<String, Object> param) {
		List<Record> quyu=	ZongHeDao.findQuyu(param);
		
		List<Record> kucun = new ArrayList<>();
		List<Record> zcr = new ArrayList<>();
		List<Record> qt = new ArrayList<>();
		List<Record> kongc = new ArrayList<>();
		
		
		Ret ret = new Ret();
		
		for(int i = 0; i < quyu.size(); i++){
			String xian = quyu.get(i).getStr("name");
			param.put("xian", xian);
			Record kc = new Record();
			Record zc = new Record();
			Record q = new Record();
			if(ZongHeDao.findzkc(param).size() != 0){
				if(ZongHeDao.findzkc(param).get(0).get("SUM") != null){
					kc = ZongHeDao.findzkc(param).get(0);
				}else{
					kc.set("xian", xian);
					kc.set("SUM", new BigDecimal(0));
				}
			}else{
				kc.set("xian", xian);
				kc.set("SUM", new BigDecimal(0));
			}
			if(ZongHeDao.findzcr(param).size() != 0){
				zc = ZongHeDao.findzcr(param).get(0);
			}else{
				zc.set("xian", xian);
				zc.set("shjcr", 0.00);
			}
			if(ZongHeDao.findqt(param).size() != 0){
				q = ZongHeDao.findqt(param).get(0);
			}else{
				q.set("xian", xian);
				q.set("shjcr", 0.00);
			}
			kucun.add(kc);
			zcr.add(zc);
			qt.add(q);
		}
		
		double sumkucun = 0;
		double sumzcr = 0;
		double sumqt = 0;
		double sumkongc = 0;
		for(int i = 0; i < kucun.size(); i++){
			sumkucun += kucun.get(i).getBigDecimal("SUM").doubleValue();
			
			sumzcr += zcr.get(i).getDouble("shjcr");
			
			sumqt += qt.get(i).getDouble("shjcr");
			Record r = new Record();
			r.set("kongc", (zcr.get(i).getDouble("shjcr") - kucun.get(i).getBigDecimal("SUM").doubleValue()));
			kongc.add(r);	
		}
		
		Record kc = new Record();
		Record zc = new Record();
		Record q = new Record();
		Record ko = new Record();
		kc.set("SUM", sumkucun);
		kc.set("xian", "全市");
		zc.set("shjcr", sumzcr);
		q.set("shjcr", sumqt);
		ko.set("kongc", sumzcr - sumkucun);
		
		kucun.add(0, kc);
		zcr.add(0, zc);
		qt.add(0, q);
		kongc.add(0, ko);
		
		List<Record> pagelist = new ArrayList<>();
		for(int i = 0; i < kucun.size(); i++){
			Record r = new Record();
			r.set("xian", kucun.get(i).get("xian"));
			r.set("sum", kucun.get(i).get("SUM"));
			r.set("orderno", kucun.get(i).get("orderno"));
			r.set("zcr", zcr.get(i).get("shjcr"));
			r.set("kongc", kongc.get(i).get("kongc"));
			r.set("qt", qt.get(i).get("shjcr"));
			pagelist.add(r);
		}
		

		Page<Record> pageRecord = new Page<>(pagelist, 1, 7, 1, 7);
		ret.put("kucun", kucun);
		ret.put("zcr",zcr);
		ret.put("qt",qt);
		ret.put("kongc", kongc);
		ret.put("list",pageRecord);
		return ret;
		
	}

	
	
	
	
}