package com.zkzy.njzhpt.bo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi.ecCVCDSA;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.fabric.xmlrpc.base.Data;
import com.zkzy.njzhpt.dao.ChuBeiLiangJHDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class saveShiwuKucun {
	@Before(AutoPageInterceptor.class)
	public Ret saveshiwukucun() throws Exception {
		Ret ret=new Ret();
		/*HashMap<String, Object> map=new HashMap<>();
		Calendar curr = Calendar.getInstance();
		String year =String.valueOf(curr.get(Calendar.YEAR));
		//map.put("niandu", year);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println("实时库存定时任务开始----------当前时间："+sdf.format(curr.getTime()));
		Page<Record> allqiye=ChuBeiLiangJHDAO.findQYbyJiHua(map);
		System.out.println("今年有储备计划的企业有： "+allqiye.getList().size()+" 家");
		for(Record record:allqiye.getList()){
			Record pzxz=new Record();
			String qyzzjgdm=record.getStr("qyzzjgdm");
			String kdbm=record.getStr("kdbm");
			String cfbm=record.getStr("cfbm");
			HashMap<String, Object> param=new HashMap<>();
			param.put("qyzzjgdm", qyzzjgdm);
			param.put("kdbm", kdbm);
			param.put("cfbm", cfbm);
			Page<Record> crkrecord=ChuBeiLiangJHDAO.findcfcrkjilu(param);
			Page<Record> kucunrecord=ChuBeiLiangJHDAO.findcfkucunjilu(param);
			if(crkrecord.getList().size()>0&&kucunrecord.getList().size()>0){
				
				Record lunhuanjilu=ChuBeiLiangJHDAO.findlunhuanbycf(param);//查询轮换记录
				BigDecimal cfdmstock=new BigDecimal(0);
				//6:待提交  1：已提交  4：轮换申请通过  10：轮换申请不通过  5：提交验收申请  0：完成  20：验收申请不通过
				if(lunhuanjilu!=null){
					String zt=lunhuanjilu.getStr("lhsq_zt");
					if("4".equals(zt)||"20".equals(zt)){	
						// 轮出申请批准通过 或 轮入不通过（查询上次轮入数量-当前轮出量）
						String lhpzsj=lunhuanjilu.getStr("lhpzsj");
						param.put("time", lhpzsj);
					}else if("0".equals(zt)){
						String qrtzsj=lunhuanjilu.getStr("qrtzsj");
						param.put("time", qrtzsj);
					}
					try{
						//品种，性质，年份-库存表取    等级-出入库表取
						pzxz=ChuBeiLiangJHDAO.findpzxzyear(param);
					}catch(Exception e){
						e.printStackTrace();
					}
					try{
						cfdmstock=ChuBeiLiangJHDAO.findRuKubycangfang(param);
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					String nowtime=sdf.format(Calendar.getInstance().getTime());
					param.put("nowtime", nowtime);
					//pzxz=ChuBeiLiangJHDAO.findPZaboutcrk(param);
					//品种，性质，年份-库存表取    等级-出入库表取
					try{
						pzxz=ChuBeiLiangJHDAO.findpzxzyear(param);
					}catch(Exception e){
						e.printStackTrace();
					}
					try{
						cfdmstock=ChuBeiLiangJHDAO.findsskcbycf(param);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				List<Record> kucunlist=Db.find("select  * from njpt_chubeiliangjihua_kucun where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
				record.setColumns(pzxz);
				record.set("dmStock", cfdmstock);
				record.remove("tempcolumn");
				record.remove("temprownumber");
				if(kucunlist.size()>0){
					Record existkucun=kucunlist.get(0);
					record.set("id", existkucun.get("id"));
					try{
						Db.update("njpt_chubeiliangjihua_kucun", record);
						//Db.update("update njpt_chubeiliangjihua_kucun set xzqhdm=?,qyzzjgdm=?,kdbm=?,cfbm=?,dmStock=?,pinzhong=?,xingzhi=?,grade=?,year=? ",record.get("xzqhdm"),record.get("qyzzjgdm"),record.get("kdbm"),record.get("cfbm"),record.get("dmStock"),record.get("pinzhong"),record.get("xingzhi"),record.get("grade"),record.get("year"));
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					try{
						Db.save("njpt_chubeiliangjihua_kucun", record);
						//Db.update("insert into njpt_chubeiliangjihua_kucun (xzqhdm,qyzzjgdm,kdbm,cfbm,dmStock,pinzhong,xingzhi,grade,year)  VALUES (?,?,?,?,?,?,?,?,?)",record.get("xzqhdm"),record.get("qyzzjgdm"),record.get("kdbm"),record.get("cfbm"),record.get("dmStock"),record.get("pinzhong"),record.get("xingzhi"),record.get("grade"),record.get("year"));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("实时库存定时任务结束----------当前时间："+sdf.format(Calendar.getInstance().getTime()));
		*/
		return ret;
		
	}
	public Ret saveEachMonthkucun() throws Exception {
		HashMap<String, Object> map=new HashMap<>();
		Calendar curr = Calendar.getInstance();
		String year =String.valueOf(curr.get(Calendar.YEAR));
		String month=String.valueOf(curr.get(Calendar.MONTH)+1);
		System.out.println("实物库存每月定时保存库存开始--------当前年："+year+" 月："+month);
		Db.update("delete from njpt_chubeiliangjihua_kucun_yue where niandu=? and yuefen=? ",year,month);
		Page<Record> shishikucun=ChuBeiLiangJHDAO.findallchlqukuucn(map);
		for(Record record:shishikucun.getList()){
			Record linshi=new Record();
			linshi.setColumns(record);
			linshi.remove("id")
				.remove("tempcolumn")
				.remove("temprownumber");
			linshi.set("id", SqlUtil.uuid())
					.set("niandu", year)
					.set("yuefen", month);
			try {
				
				Db.save("njpt_chubeiliangjihua_kucun_yue", linshi);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("实物库存每月定时保存库存结束--------当前年："+year+" 月："+month);
		return null;
	}
	/*public Ret saveEachYearJihua() throws Exception {
		HashMap<String, Object> map=new HashMap<>();
		HashMap<String, Object> map1=new HashMap<>();
		Calendar curr = Calendar.getInstance();
		String year =String.valueOf(curr.get(Calendar.YEAR));
		System.out.println("年度计划每年定时保存库存开始--------当前年："+year);
		map.put("niandu", Integer.valueOf(year)-1);
		map1.put("niandu", year);
		Page<Record> shishikucun=ChuBeiLiangJHDAO.findallndjihua(map);
		Page<Record> shishikucun1=ChuBeiLiangJHDAO.findallndjihua(map1);
		if(shishikucun1.getList().size()<=0){
			for(Record record:shishikucun.getList()){
				Record linshi=new Record();
				linshi.setColumns(record);
				linshi.remove("id").remove("tempcolumn")
				.remove("temprownumber");
				linshi.set("id", SqlUtil.uuid())
						.set("niandu", year);
				try {
					Db.save("njpt_chubeiliangjihua_renwu", linshi);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("年度计划每年定时保存库存结束--------当前年："+year);
		return null;
	}*/
	/**
	 * 定时任务--超架空期
	 * @throws Exception
	 */
	public void  updateIsCJKQ() throws Exception{
		System.out.println("定时任务-超架空期------执行开始!");
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 20);
		String time= sdf.format(c.getTime());
		System.out.println(time);
		//小麦
		List<Record> xmList=Db.find("select a.ccku,a.ccdian,a.pz from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '111%' GROUP BY a.ccku,a.ccdian,a.pz ORDER BY a.ccku,a.ccdian,a.pz ");
		for(Record record:xmList){
			double linshi=0;
			Record lrRecord=Db.findFirst("select ISNULL(sum(CONVERT(FLOAT,a.sumlunru)), 0) as zonglr from njpt_lunruhj a where  a.pz like '111%' and a.qyzzjgdm=? and a.kdbm=? ",record.getStr("ccku"),record.get("ccdian"));
			List<Record> lhRecord=new ArrayList<>();
			try {
				lhRecord=Db.find("select CONVERT(FLOAT,a.lhsl) as lhsl,a.id from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '111%' and a.ccku=? and a.ccdian=? and wclrsj<? ORDER BY a.wclrsj ",record.getStr("ccku"),record.get("ccdian"),time);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(lrRecord!=null){
				linshi=lrRecord.getDouble("zonglr");
			}
			
			for(Record lunh:lhRecord){
				try {
					
					linshi=linshi-lunh.getDouble("lhsl");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(linshi<0){
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("iscjkq", 1));
				}else{
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("status", 2));
				}
			}
		}
		//粳稻
		List<Record> jdList=Db.find("select a.ccku,a.ccdian,a.pz from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '1132%' GROUP BY a.ccku,a.ccdian,a.pz ORDER BY a.ccku,a.ccdian,a.pz ");
		for(Record record:jdList){
			double linshi=0;
			Record lrRecord=Db.findFirst("select ISNULL(sum(CONVERT(FLOAT,a.sumlunru)), 0) as zonglr from njpt_lunruhj a where  a.pz like '1132%' and a.qyzzjgdm=? and a.kdbm=? ",record.getStr("ccku"),record.get("ccdian"));
			List<Record> lhRecord=Db.find("select CONVERT(FLOAT,a.lhsl) as lhsl,a.id from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '1132%' and a.ccku=? and a.ccdian=? and wclrsj<? ORDER BY a.wclrsj ",record.getStr("ccku"),record.get("ccdian"),time);
			if(lrRecord!=null){
				linshi=lrRecord.getDouble("zonglr");
			}
			
			for(Record lunh:lhRecord){
				linshi=linshi-lunh.getDouble("lhsl");
				if(linshi<0){
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("iscjkq", 1));
					break;
				}else{
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("status", 2));
				}
			}
		}
		//籼稻
		List<Record> xdList=Db.find("select a.ccku,a.ccdian,a.pz from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '1131%' GROUP BY a.ccku,a.ccdian,a.pz ORDER BY a.ccku,a.ccdian,a.pz ");
		for(Record record:xdList){
			double linshi=0;
			Record lrRecord=Db.findFirst("select ISNULL(sum(CONVERT(FLOAT,a.sumlunru)), 0) as zonglr from njpt_lunruhj a where  a.pz like '1131%' and a.qyzzjgdm=? and a.kdbm=? ",record.getStr("ccku"),record.get("ccdian"));
			List<Record> lhRecord=Db.find("select CONVERT(FLOAT,a.lhsl) as lhsl,a.id from njpt_lunhuanshenqingb a  where a.status>=1 and a.pz like '1131%' and a.ccku=? and a.ccdian=? and wclrsj<? ORDER BY a.wclrsj ",record.getStr("ccku"),record.get("ccdian"),time);
			if(lrRecord!=null){
				linshi=lrRecord.getDouble("zonglr");
			}
			for(Record lunh:lhRecord){
				linshi=linshi-lunh.getDouble("lhsl");
				if(linshi<0){
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("iscjkq", 1));
				}else{
					Db.update("njpt_lunhuanshenqingb", new Record().set("id", lunh.get("id")).set("status", 2));
				}
			}	
		}
		System.out.println("定时任务-超架空期------执行成功");
	}
}
