package com.zkzy.njzhpt.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FindPerson {
	public static List<Record>  findDepsById(String id){
		List<Record> listrecord = Db.find("SELECT a.* FROM (SELECT * FROM fw_user WHERE id not in (SELECT id from tz_qiye)) as a JOIN fw_user_dep b ON a.id=b.userid WHERE b.depid=? ORDER BY a.orderno DESC", id);
		return listrecord;
	}
	
	public static List<Record>  findDepById(String id,int isend){
		List<Record> listrecord = Db.find("SELECT * FROM oa_treelist WHERE leixing='txl' and shangji=? and isend=? ORDER BY orderno", id,isend);
		return listrecord;
	}
	public static List<Record>  findRenyuanById(String id,int isend){
		List<Record> listrecord = Db.find("SELECT * FROM oa_treelist WHERE leixing='txl' and zhanghao!='' and shangji=? and isend=? ORDER BY orderno", id,isend);
		return listrecord;
	}
	
	public static List<Record> findUserByDepId(String id){
		String sql="SELECT a.* FROM fw_user a JOIN fw_user_dep b ON a.id=b.userid WHERE b.depid=?";
		List<Record> list=Db.find(sql,id);
		return list;
	}
	public static List<Record> findPerson(String id) {
		String sql="SELECT *FROM fw_user where id=?";
		List<Record> list=Db.find(sql,id);	
		return list;
	}
}
