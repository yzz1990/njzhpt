package com.zkzy.njzhpt.bo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class saveNews {
	
	public void savenews() throws IOException{
		//查找所有网站
		//新浪网
		String name = "新浪网";
		String url = "http://finance.sina.com.cn/futuremarket/";
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element content = doc.select(".part02").first().select(".blk22").first().select(".newslist").get(1).select("ul")
					.first().select("li").first().select(".grey43").first();
			String title = content.text();
			String purl = content.attr("href");
			String uuid = SqlUtil.uuid();
			Record record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//中国粮食信息网
			name = "中国粮食信息网";
			url = "http://www.grain.gov.cn/Grain/Index.aspx";
			doc = Jsoup.connect(url).get();
			content = doc.select(".firstNews").first().select("a").first();
			title = content.text();
			purl = "http://www.grain.gov.cn/Grain/" + content.attr("href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//江苏粮网
			name = "江苏粮网";
			url = "http://www.jsgrain.gov.cn/default.php?mod=article&settype=0&fid=1684";
			doc = Jsoup.connect(url).get();
			content = doc.select("#s2945971_content").first().select("a").first();
			title = content.text();
			purl = content.attr("abs:href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//中国粮食行业网
			name = "中国粮食行业网";
			url = "http://www.chinagrains.org.cn/info/1904/type_4881.html";
			doc = Jsoup.connect(url).get();
			content = doc.select(".li_sali1").first().select("a").first();
			title = content.text();
			purl = content.attr("abs:href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//国家粮食局
			name = "国家粮食局";
			url = "http://www.chinagrain.gov.cn/";
			doc = Jsoup.connect(url).get();
			content = doc.select(".list_act_1").first().select("a").first();
			title = content.text();
			purl = content.attr("abs:href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//中国粮食网
			name = "中国粮食网";
			url = "http://www.cereal.com.cn/";
			doc = Jsoup.connect(url).get();
			content = doc.select(".headline-news").first().select("a").first();
			title = content.text();
			purl = content.attr("abs:href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
			//中国粮油信息网
			name = "中国粮油信息网";
			url = "http://www.chinagrain.cn/";
			doc = Jsoup.connect(url).get();
			content = doc.select("#eq_news_bg").first().select("a").first();
			title = content.text();
			purl = content.attr("abs:href");
			uuid = SqlUtil.uuid();
			record = new Record();
			record.set("id", uuid);
			record.set("webname", name);
			record.set("title", title);
			record.set("url", purl);
			if(title != null && !"".equals(title) && purl != null && !"".equals(purl)){
				String upsql = "update t_newnow set title = ?,url = ? where webname = ?";
				Db.update(upsql,title,purl,name);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		/*
		int temp = 0;
		int flag = 0;
		do{
			//删除t_newnow中的数据
			String delnew = "delete from t_newnow";
			Db.update(delnew);
			temp++;
			//查找所有网站
					//新浪网
					String name = "新浪网";
					String url = "http://finance.sina.com.cn/futuremarket/";
					Document doc;
					try {
						doc = Jsoup.connect(url).get();
						Element content = doc.select(".part02").first().select(".blk22").first().select(".newslist").get(1).select("ul")
								.first().select("li").first().select(".grey43").first();
						String title = content.text();
						String purl = content.attr("href");
						String uuid = SqlUtil.uuid();
						Record record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//中国粮食信息网
						name = "中国粮食信息网";
						url = "http://www.grain.gov.cn/Grain/Index.aspx";
						doc = Jsoup.connect(url).get();
						content = doc.select(".firstNews").first().select("a").first();
						title = content.text();
						purl = "http://www.grain.gov.cn/Grain/" + content.attr("href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//江苏粮网
						name = "江苏粮网";
						url = "http://www.jsgrain.gov.cn/default.php?mod=article&settype=0&fid=1684";
						doc = Jsoup.connect(url).get();
						content = doc.select("#s2945971_content").first().select("a").first();
						title = content.text();
						purl = content.attr("abs:href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//中国粮食行业网
						name = "中国粮食行业网";
						url = "http://www.chinagrains.org.cn/info/1904/type_4881.html";
						doc = Jsoup.connect(url).get();
						content = doc.select(".li_sali1").first().select("a").first();
						title = content.text();
						purl = content.attr("abs:href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//国家粮食局
						name = "国家粮食局";
						url = "http://www.chinagrain.gov.cn/";
						doc = Jsoup.connect(url).get();
						content = doc.select(".list_act_1").first().select("a").first();
						title = content.text();
						purl = content.attr("abs:href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//中国粮食网
						name = "中国粮食网";
						url = "http://www.cereal.com.cn/";
						doc = Jsoup.connect(url).get();
						content = doc.select(".headline-news").first().select("a").first();
						title = content.text();
						purl = content.attr("abs:href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
						//中国粮油信息网
						name = "中国粮油信息网";
						url = "http://www.chinagrain.cn/";
						doc = Jsoup.connect(url).get();
						content = doc.select("#eq_news_bg").first().select("a").first();
						title = content.text();
						purl = content.attr("abs:href");
						uuid = SqlUtil.uuid();
						record = new Record();
						record.set("id", uuid);
						record.set("webname", name);
						record.set("title", title);
						record.set("url", purl);
						Db.save("t_newnow", record);
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String sqlnum = "select count(*) as num from t_newnow ";
					Record nrecord = Db.findFirst(sqlnum);
					flag = nrecord.getInt("num");
					System.out.println(flag);
			
		}while(flag != 7 && temp <= 200);
		*/
	}
	

}
