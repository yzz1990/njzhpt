package com.zkzy.njzhpt.bo;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class savegnPrice {
	//定期保存国内期货
		public void savegnprice(){
			String[] pzname = {"豆粕","大豆","豆油","玉米","菜油","强麦"};
			String[] pzcode = {"M0","A0","Y0","C0","OI0","WH0"};
			Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR); 
	        int month = c.get(Calendar.MONTH) + 1; 
	        if(month == 1){
	        	 month = 12;
	        	 year = year - 1;
	        }else{
	        	 month -= 1;
	        }
	        int flag = 0;
	        
	        do{
	        	String sql = "delete from tz_qihuogn where year = ? and month = ?";
		        Db.update(sql,year,month);
		        
		        for(int j = 0; j< pzname.length; j++){
					String pz = pzname[j];
					String code = pzcode[j];
					
					String input = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesDailyKLine?symbol="+code;
					String output = "";
					URL url;
					try {
						url = new URL(input);
						InputStreamReader reader = new InputStreamReader(new BufferedInputStream(url.openStream()));
						 char[] cbuf = new char[1024 * 10];
				         int cnum = -1;
				         while((cnum=reader.read(cbuf))!=-1)
				         {
				             output += new String(cbuf, 0, cnum);
				         }          
				         JSONArray outjson = JSONArray.parseArray(output);
				         //System.out.println(year+","+month+","+c+","+now);
				         for(int i = outjson.size() - 1 ; i > 0; i-- ){
				         	 String[] items = outjson.getString(i).split(",");
					         String uuid = SqlUtil.uuid();
					         String day = items[0].substring(2, 12);
					         String price = items[4].substring(1,items[4].length()-1);
					         int iyear = Integer.parseInt(day.substring(0, 4));
					         int imonth = Integer.parseInt(day.substring(5, 7));
					         if(iyear == year && imonth == month){
					        	 HashMap<String,Object> map = new HashMap<>();
						         map.put("id", uuid);
						         map.put("riqi", day);
						         map.put("price", price);
						         map.put("pinzhong", pz);
						         map.put("year", year);
						         map.put("month", month); 
					        	 System.out.println(map);
					        	 Duang.duang(JiageBO.class).savegnqihuo(map);
					        	 break;
					         }
				         }
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        String sqlnum = "select count(*) as num from tz_qihuogn where year = ? and month = ?";
		        Record nrecord = Db.findFirst(sqlnum,year,month);
		        flag = nrecord.getInt("num");
		        System.out.println(flag);
		        if(flag == 6){
		        	System.out.println("结束");
		        }
		        
	        }while(flag != 6);
		} 		
}
