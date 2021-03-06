package com.zkzy.njzhpt.config.interfaces;

import java.util.HashMap;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public interface AppICommond {
	
	/**
	 * 权限
	 * 企业备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiyebeian(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 熏蒸备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 药剂备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYaoji(HashMap<String, Object> param) throws Exception;
	/**
	 * 熏蒸作业
	 */
	public Page<Record> findxzzy(HashMap<String, Object> queryparam) throws Exception;
}
