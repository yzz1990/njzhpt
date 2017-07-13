package com.zkzy.njzhpt.dao;

import java.util.HashMap;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ChengPinLiangDAO {
	public static Page<Record> selectCPLCBJH(HashMap<String, Object> map) throws Exception {
				Param p = new Param();
				p.put("and a.id=?", "id");
				p.put("and a.quming like ?", "quming","%%%s%%");
				p.put("and a.ccqy like ?", "ccqy","%%%s%%");
				p.put("and a.niandu like ?", "niandu","%%%s%%");
				SqlAndParam s = SqlUtil.buildSql(p, map);
				Page<Record> page = Db.paginate(
						Integer.valueOf(String.valueOf(map.get("page"))),
						Integer.valueOf(String.valueOf(map.get("rows"))),
						"select a.* ",
						"from njpt_chengpinliangjihuab a LEFT JOIN njpt_diqu b ON a.quming =b.xzqhdm LEFT JOIN fw_area c ON b.areaid=c.id where 1=1 " + s.getSql()+" order by a.niandu desc ,c.orderno ASC",
						s.getParam().toArray(new Object[0]));
				for(Record r:page.getList()){
					Object qyzzjgdm=r.get("ccqy");
					Record qyr= Db.findFirst("select * from tz_qiye where qyzzjgdm=?",qyzzjgdm);
					if(qyr!=null){
						Object xzqhdm=r.get("quming");
						Record diqu= Db.findFirst("select * from njpt_diqu where xzqhdm=?",xzqhdm);
						String quming = Db.findFirst("select * from fw_area where id=?",diqu.getStr("areaid")).getStr("name");
						String ccqy= Db.findFirst("select * from tz_qiye where qyzzjgdm=?",qyzzjgdm).getStr("qymc");
						r.set("quming", quming);
						r.set("ccqymc", ccqy);
					}else{
						r.set("quming", "");
						r.set("ccqymc", "");
					}
					
				}
				
				return page;
				
			}
	public static boolean saveCPLCBJH(HashMap<String, Object> map) throws Exception {
		String id=(String)map.get("id");
		Record record=new Record().setColumns(map);
		boolean bool=false;
		if(StrKit.isBlank(id)){
			bool=Db.save("njpt_chengpinliangjihuab", record);
		}else {
			record.set("id", id);
			bool=Db.update("njpt_chengpinliangjihuab", record);
		}
		return bool;
		
	}
	public static boolean deleteCPLCBJH(HashMap<String, Object> map) throws Exception {
		boolean bool=false;
		bool=Db.deleteById("njpt_chengpinliangjihuab", map.get("id"));
		return bool;
		
	}
}
