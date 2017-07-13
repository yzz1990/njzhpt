package com.zkzy.framework.controller;

import java.util.HashMap;

import com.jfinal.aop.Duang;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.bo.FrameworkBO;

public class FrameworkTag {

	// 获取导航
	public static Ret queryAmsByRoot(String root) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", root);
		map.put("page", 1);
		map.put("rows", 1000);
		FrameworkBO bo = Duang.duang(FrameworkBO.class);

		Ret ret = bo.queryFwAms(map);
		Page<Record> userPageRecord = ret.get("list");
		for (int i = 0; i < userPageRecord.getList().size(); i++) {
			Record ams = userPageRecord.getList().get(i);
			int childcount = ams.get("childcount");
			if (childcount > 0) {
				Ret childret = queryAmsByRoot(ams.get("id"));
				ams.set("children", childret.get("list"));
			}
		}
		return ret;
	}

	// 获取菜单树形
	public static String queryAmsTree(String root) throws Exception {
		String strAms = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", root);
		map.put("page", 1);
		map.put("rows", 1000);
		FrameworkBO bo = Duang.duang(FrameworkBO.class);
		Ret ret = bo.queryFwAms(map);
		Page<Record> amsRecords = ret.get("list");
		for (int i = 0; i < amsRecords.getList().size(); i++) {
			Record ams = amsRecords.getList().get(i);
			int childcount = ams.get("childcount");
			if (childcount > 0) {
				strAms += "<li><span id=\""
						+ ams.getStr("id")
						+ "\"><i class=\"glyphicon glyphicon-minus-sign\"></i> "
						+ ams.getStr("name") + "</span>";
				strAms += "<ul>";
				strAms += queryAmsTree(ams.get("id"));
				strAms += "</ul></li>";
			} else {
				strAms += "<li><span id=\"" + ams.getStr("id")
						+ "\"><i class=\"glyphicon glyphicon-leaf\"></i> "
						+ ams.getStr("name") + "</span></li>";
			}
		}
		return strAms;
	}

	// 获取菜单树形
	public static String queryAmsTreeCheckBox(String root) throws Exception {
		String strAms = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", root);
		map.put("page", 1);
		map.put("rows", 1000);
		FrameworkBO bo = Duang.duang(FrameworkBO.class);
		Ret ret = bo.queryFwAms(map);
		Page<Record> amsRecords = ret.get("list");
		for (int i = 0; i < amsRecords.getList().size(); i++) {
			Record ams = amsRecords.getList().get(i);
			int childcount = ams.getInt("childcount");
			if (childcount > 0) {
				strAms += "<li><input type=\"checkbox\" name=\""
						+ ams.getStr("id")
						+ "\" class=\"chklist\"/><label class=\"chkbox\"><span class=\"check-image\"></span><span class=\"radiobox-content\">"
						+ ams.getStr("name") + "</span></label>";
				strAms += "<ul>";
				strAms += queryAmsTreeCheckBox(ams.get("id"));
				strAms += "</ul></li>";
			} else {
				strAms += "<li><input type=\"checkbox\" name=\""
						+ ams.getStr("id")
						+ "\" class=\"chklist\"/><label class=\"chkbox\"><span class=\"check-image\"></span><span class=\"radiobox-content\">"
						+ ams.getStr("name") + "</span></label></li>";
			}
		}
		return strAms;
	}

	// 获取部门树形
	public static String queryDepTree(String root) throws Exception {
		String strAms = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", root);
		map.put("page", 1);
		map.put("rows", 1000);
		FrameworkBO bo = Duang.duang(FrameworkBO.class);
		Ret ret = bo.queryFwDep(map);
		Page<Record> amsRecords = ret.get("list");
		for (int i = 0; i < amsRecords.getList().size(); i++) {
			Record ams = amsRecords.getList().get(i);
			int childcount = ams.get("childcount");
			if (childcount > 0) {
				strAms += "<li><span id=\""
						+ ams.getStr("id")
						+ "\"><i class=\"glyphicon glyphicon-minus-sign\"></i> "
						+ ams.getStr("name") + "</span>";
				strAms += "<ul>";
				strAms += queryDepTree(ams.get("id"));
				strAms += "</ul></li>";
			} else {
				strAms += "<li><span id=\"" + ams.getStr("id")
						+ "\"><i class=\"glyphicon glyphicon-leaf\"></i> "
						+ ams.getStr("name") + "</span></li>";
			}
		}
		return strAms;
	} // 获取部门树形

	public static String queryDepTreeCheckBox(String root) throws Exception {
		String strAms = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", root);
		map.put("page", 1);
		map.put("rows", 1000);
		FrameworkBO bo = Duang.duang(FrameworkBO.class);
		Ret ret = bo.queryFwDep(map);
		Page<Record> amsRecords = ret.get("list");
		for (int i = 0; i < amsRecords.getList().size(); i++) {
			Record ams = amsRecords.getList().get(i);
			int childcount = ams.get("childcount");
			if (childcount > 0) {
				strAms += "<li><input type=\"checkbox\" name=\""
						+ ams.getStr("id")
						+ "\" class=\"chklist\"/><label class=\"chkbox\"><span class=\"check-image\"></span><span class=\"radiobox-content\">"
						+ ams.getStr("name") + "</span></label>";
				strAms += "<ul>";
				strAms += queryDepTreeCheckBox(ams.get("id"));
				strAms += "</ul></li>";
			} else {
				strAms += "<li><input type=\"checkbox\" name=\""
						+ ams.getStr("id")
						+ "\" class=\"chklist\"/><label class=\"chkbox\"><span class=\"check-image\"></span><span class=\"radiobox-content\">"
						+ ams.getStr("name") + "</span></label></li>";
			}
		}
		return strAms;
	}
}
