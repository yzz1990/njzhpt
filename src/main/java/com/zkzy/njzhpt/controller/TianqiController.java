package com.zkzy.njzhpt.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ggy.common.utils.DateUtil;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.config.Plugins;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TianqiController extends Controller {

	public static void main(String[] args) {
		PropKit.use("njzhpt.txt");
		Plugins p = new Plugins();
		SqlUtil.initDb(p);
		for (IPlugin plugin : p.getPluginList()) {
			plugin.start();
		}
		new TianqiController().save();
	}

	public void save() {
		Document doc = null;
		try {
			doc = Jsoup
					.connect(
							"http://php.weather.sina.com.cn/search.php?city=%C4%CF%BE%A9&dpc=1")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String wendu = doc.select(".tab_bd .m_left .day .detail span").first()
				.text();
		String tq = doc.select(".tab_bd .m_left .day .icon_weather").attr(
				"title");
		String shijian = doc.select(".tab_ctn span").first().text();
		String tupian = doc.select(".tab_bd .m_left .day .icon_weather")
				.attr("style").split("url[(]")[1].split("[)]")[0];
		Record record = new Record().set("wendu", wendu).set("tq", tq)
				.set("shijian", shijian).set("sj", DateUtil.toFullString())
				.set("id", SqlUtil.uuid()).set("tianqitupian", tupian);
		Db.save("tianqi", record);
	}
}
