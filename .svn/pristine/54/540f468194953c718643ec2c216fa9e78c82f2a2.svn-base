package com.zkzy.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.ParamUtil;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TianqiController {
	public static void main(String[] args) {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://php.weather.sina.com.cn/search.php?city=%C4%CF%BE%A9&dpc=1").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String title = doc.title();
		String shijian = doc.select(".tab_ctn  span").first().text();
		String wendu = doc.select(".tab_bd .m_left .day .detail span").first().text();
		String tq = doc.select(".tab_bd .m_left .day .icon_weather").attr("title");
		
		String tupian = doc.select(".tab_bd .m_left .day .icon_weather ").attr("style").substring(16, 78);
		System.out.println(tupian);
	}

}
