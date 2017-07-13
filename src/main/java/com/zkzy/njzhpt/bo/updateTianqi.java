package com.zkzy.njzhpt.bo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ggy.common.utils.DateUtil;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.njzhpt.interceptor.JfinalSkipHandler;

public class updateTianqi {

	public static void update() {
		
		// 更新最新的天气情况
		Record record = Db
				.findFirst("select  wendu,tq,tianqitupian FROM tianqi ORDER BY sj DESC");
		if (record != null) {
			JfinalSkipHandler.tianqi.put("record", record);
		} else {
			record = new Record().set("tq", "").set("tq", "--")
					.set("wendu", "--").set("tianqitupian", "#");
			JfinalSkipHandler.tianqi.put("record", "--");

		}
		updatenew();
	}

	public static void updatenew() {
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
