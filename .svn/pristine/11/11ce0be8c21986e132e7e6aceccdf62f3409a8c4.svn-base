package com.zkzy.njzhpt.bo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
public class updateDdns {
	public void uodateDdnsip(){
		List<Record>  records=	Db.use("grainplat").find("select * from nvr_copy");
		for(Record record:records){
			if(!StrKit.isBlank(record.getStr("ddns"))){
				String ip=	resultUrl(record.getStr("ddns"));
						ip=regexUrl(ip);
						
				if(!(ip.contains("www.hik-online.com")||ip.equals(""))){
					ip=ip.split("//")[1];
						String points=record.getStr("points");
						Pattern p=Pattern.compile("\"ip\":\".*?\"");
						Pattern p1=Pattern.compile("\"rtsp\":\"(.*?)\"");
						Matcher point=p.matcher(points);
						Matcher point1=p1.matcher(points);
						//替换rtsp之后的ip地址
						if(point1.find()){
							if(!points.contains(ip)){
								points=points.replaceAll("\\@(.*?)\\/", "@"+ip+":");
							}
						}
						//替换ip后面的ip地址
						if(point.find()){
							points=points.replaceAll("\"ip\":\".*?\"", "\"ip\":\""+ip+"\"");
						}
						int flag=Db.use("grainplat").update("update nvr_copy  set points=? where entcode=? and orgcode=? ",points,record.getStr("entcode"),record.getStr("orgcode"));
						if(flag>0){
							//LogKit.info(record.getStr("orgcode")+":"+record.getStr("entcode")+":DDNS地址:"+record.getStr("ddns")+"==替换成功！");
						}else{
							//LogKit.info(record.getStr("orgcode")+":"+record.getStr("entcode")+":DDNS地址:"+record.getStr("ddns")+"==替换失败！");				
						}
				}
			}
		}
		
	}
	
	public void test(){
		
		String point="rtsp://admin:12345@222.94.220.163:81/cam/realmonitor?channel=6&subtype=0";
		String ip="222.94.220.163:81";
		if(point.contains(ip)){
			LogKit.info("ssfsf");
		}
	}
	
	
	/**
     * 获取url返回内容
     * var redirectUrl =  'http://121.237.228.69:50002';
     */
    public static String resultUrl(String path){
    	StringBuilder sbR = new StringBuilder();
    	try {
    		URL url = new URL(path);
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		connection.setInstanceFollowRedirects(false);
    		connection.setConnectTimeout(5000);
    		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    		String line = br.readLine();
    		while (line != null) {
    			sbR.append(line);
    			line = br.readLine();
    		}
    		String a=sbR.toString();
    		if(a!=null){
    		}
		} catch (Exception e) {
			return path+"连接超时";
		}
        return sbR.toString();
    }
    
    /**
     * 匹配正则
     */
    public static String regexUrl(String str){
    	Pattern p=Pattern.compile("var redirectUrl \\=  \'(.*?)\'");
    	Matcher m=p.matcher(str);
    	String string=""; 
    	while(m.find()) {
    		//System.out.println(m.group(1));//m.group(1)不包括这两个字符
    		string=m.group(1);
        }
    	return string;
    }

}
