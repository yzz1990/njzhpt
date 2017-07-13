package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class QiTiJCDAO {

	public static Page<Record> selectqiti(HashMap<String, Object> map) throws Exception{
		Param p = new Param();
		p.put("and w.xzqhdm=?", "xzqhdm");
		p.put("and w.qyzzjgdm=?", "qyzzjgdm");
		p.put("and w.kdbm=?", "kdbm");
		p.put("and w.cfbm=?", "cfbm");
		SqlAndParam s = SqlUtil.buildSql(p, map);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(map.get("page"))),
				Integer.valueOf(String.valueOf(map.get("rows"))),
				"select w.xzqhdm,w.qyzzjgdm,w.kdbm,w.cfbm, w.t_WareHouse_id,d.vCode,d.vName,d.vDeviceType,d.vStatus,d.vDeviceKind,s.fValue,d.vUnit,s.vUptime ",
				" from qt_WareHouseDeviceBridge w INNER JOIN qt_DevicePara d on w.t_DevicePara_id=d.id inner JOIN qt_SensorData s on d.vCode=s.vDeviceCode "+ s.getSql()+"order by s.vUptime desc",s.getParam().toArray(new Object[0]));
		for(Record r:page.getList()){
			Object qyzzjgdm=r.get("qyzzjgdm");
			Object kdbm=r.get("kdbm");
			Object cfbm=r.get("cfbm");
			
		
			Record qymc= Db.findFirst("select qymc,xian,xzqhdm from tz_qiye where qyzzjgdm=?",qyzzjgdm);
			if(qymc!=null){
				r.set("qyzzjgdm", qymc.getStr("qymc"));
				r.set("xzqhdm", qymc.getStr("xian"));
			}

			Record kdmc= Db.findFirst("select kdmc from tz_kudian where kdbm=? and qyzzjgdm=?",kdbm,qyzzjgdm);
			if(kdmc!=null){
				r.set("kdbm", kdmc.getStr("kdmc"));
			}
			Record cfmc= Db.findFirst("select cfmc from tz_cangfang where qyzzjgdm=? and kdbm=? and cfbm=? ",qyzzjgdm,kdbm,cfbm);
			if(cfmc!=null){
				r.set("cfbm", cfmc.getStr("cfmc"));
			}
			
			
		
			
		}
		
		return page;
	//"select c.cfmc,c.cfbm",
	//+" GROUP BY c.cfmc,c.cfbm"
	}
}
