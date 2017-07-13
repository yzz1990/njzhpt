package com.zkzy.njzhpt.dao;

import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ExcelDao {
	
	/**
	 * 分页查询table表参数
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findTableCS(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and liucId = ?", "liucId");
		p.put("and id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select *","from njpt_sc_tablecanshu where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询excel参数
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findExcelCS(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and liucId = ?", "liucId");
		p.put("and id = ?", "id");

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select *","from njpt_sc_excelcanshu where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 分页查询guanlian表
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> findGuanlian(HashMap<String, Object> queryparam) throws Exception{
		Param p = new Param();
		p.put("and liucId = ?", "liucId");
		p.put("and id = ?", "id");

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))), 
				Integer.valueOf(String.valueOf(queryparam.get("rows"))), 
				"select *","from njpt_sc_guanlian where 1=1" + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}
	
	/**
	 * 通过liucId查找excel参数
	 */
	public List<Record> excelCs(String liucId){
		return Db.find("SELECT excelCs from njpt_sc_excelcanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 通过liucId查找table参数
	 */
	public List<Record> tableCs(String liucId){
		return Db.find("SELECT tableCs from njpt_sc_excelcanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 根据流程id查出配置
	 */
	public List<Record> findPeizhi(String liucId){
		return Db.find("SELECT id,tableCs,excelCs FROM njpt_sc_guanlian where liucId = ? ", liucId);
	}
	
	/**
	 * 根据流程id查出table参数
	 */
	public List<Record> findTaCs(String liucId){
		return Db.find("SELECT tableCs FROM njpt_sc_tablecanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 根据流程id查出excel参数
	 */
	public List<Record> findExCs(String liucId){
		return Db.find("SELECT excelCs FROM njpt_sc_excelcanshu WHERE liucId = ? and excelCs NOT LIKE '%设#置%' ", liucId);
	}
	
	/**
	 * 查询需要用的参数，如filePath，biaoName等
	 * @param liucId
	 * @return
	 */
	public List<Record> findCxCanshu(String liucId){
		return Db.find("SELECT * FROM njpt_sc_jxcxcanshu where liucId = ?",liucId);
	}
	
}
