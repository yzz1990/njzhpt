package com.zkzy.njzhpt.bo;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


public class savePrice {
	//定期保存外盘期货
	public void saveprice() throws IOException{
		Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR); 
        int month = c.get(Calendar.MONTH) + 1; 
        
        if(month == 1){
       	 month = 12;
       	 year = year - 1;
        }else{
       	 month -= 1;
        }
        
        String[] pzname = {"玉米","黄豆","小麦","豆油"};
		String[] pzcode = {"C","S","W","BO"};
		
		int flag = 0;
		do{
			String sql = "delete from tz_qihuogw where year = ? and month = ?";
	        Db.update(sql,year,month);
	        
			 for(int j = 0; j < pzname.length; j++){
		        	
		        	String pz = pzname[j];
		        	String pc = pzcode[j];
		        	String url = "http://vip.stock.finance.sina.com.cn/q/view/vFutures_History.php?jys=CBOT&pz="+pc+"&hy=&breed="+pc+"&type=global&start=&end=";
		            //System.out.println(pz);
		    		Document doc = Jsoup.connect(url).get();

		    		Element content = doc.select(".historyList").first().select("tbody").first();
		    		Elements links = content.select("tr");
		    		for (int i = 1; i < links.size(); i++) {
		    			Elements ctds = links.get(i).select("td");
		    			String sriqi = ctds.get(0).select("div").text().trim();
		    			
		    			String sprice = ctds.get(1).select("div").text().trim();
		    			
		    			double price = Double.parseDouble(sprice);
		    			
		    			int iyear = Integer.parseInt(sriqi.substring(0, 4));
		    	        int imonth = Integer.parseInt(sriqi.substring(5, 7));

		    	        
		    	        if(iyear == year && imonth == month){
		    	        	System.out.println(price+","+sriqi);
		 
		    	        	String uuid = SqlUtil.uuid();
		    	        	
		    	        	 HashMap<String,Object> map = new HashMap<>();
		    		         map.put("id", uuid);
		    		         map.put("riqi", sriqi);
		    		         map.put("price", price);
		    		         map.put("pinzhong", pz);
		    		         map.put("year", year);
		    		         map.put("month", month); 
		    	        	 System.out.println(map);
		    	        	 Duang.duang(JiageBO.class).savewpqihuo(map);
		    	        	 break;
		    	        }	 
		    		}
		    		
		    		
		        	
		        }
			 	String sqlnum = "select count(*) as num from tz_qihuogw where year = ? and month = ?";
		        Record nrecord = Db.findFirst(sqlnum,year,month);
		        flag = nrecord.getInt("num");
		        System.out.println(flag);
		        if(flag == 4){
		        	System.out.println("结束");
		        }
			
			
		}while(flag != 4);
			
       
	}
	
	
}
