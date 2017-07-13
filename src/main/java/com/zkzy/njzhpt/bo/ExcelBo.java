package com.zkzy.njzhpt.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.framework.lnterceptor.AutoPageInterceptor;
import com.zkzy.njzhpt.dao.ExcelDao;

public class ExcelBo {
	
	/**
	 * 查询table表参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findTableCS(HashMap<String, Object> param) throws Exception{
		return ExcelDao.findTableCS(param);
	}
	
	/**
	 * 查询excel参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Before(AutoPageInterceptor.class)
	public Page<Record> findExcelCS(HashMap<String, Object> param) throws Exception{
		return ExcelDao.findExcelCS(param);
	}
	
	/**
	 * 查询关联参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findGuanlian(HashMap<String, Object> param) throws Exception{
		return ExcelDao.findGuanlian(param);
	}
	
	/**
	 * 通过表名查找表字段名
	 * @param biaoName
	 * @return
	 */
	public List<Record> findBiao(String biaoName){
		String sql = "select c.name as column_name,t.name as data_type,(select value from sys.extended_properties as ex where ex.major_id = c.object_id and ex.minor_id = c.column_id) as notes from sys.columns as c inner join sys.tables as ta on c.object_id=ta.object_id inner join (select name,system_type_id from sys.types where name<>'sysname') as t on c.system_type_id=t.system_type_id where ta.name=? order by c.column_id";
		List<Record> record = Db.find(sql, biaoName);
		 for (Record rec : record) {
    		 HashMap<String, Object> map = new HashMap<String, Object>();
    		 map.put("tableCs", rec.get("column_name"));
    	 }
		return record;
		
	}
	
	/**
	 * 保存table表参数
	 * @param param
	 * @return
	 */
	public void saveTableCS (String biaoName,String liucId) {
		String sql = "select c.name as column_name,t.name as data_type,(select value from sys.extended_properties as ex where ex.major_id = c.object_id and ex.minor_id = c.column_id) as notes from sys.columns as c inner join sys.tables as ta on c.object_id=ta.object_id inner join (select name,system_type_id from sys.types where name<>'sysname') as t on c.system_type_id=t.system_type_id where ta.name=? order by c.column_id";
		List<Record> record = Db.find(sql, biaoName);
		 for (Record rec : record) {
			 String id = SqlUtil.uuid();
				Record recd = new Record().set("liucId", liucId).set("tableCs",rec.getStr("column_name")).remove("id");
				Db.save("tablecanshu", recd);
    	 }
	}
	
	/**
	 * 保存excel参数
	 * @param param
	 * @return
	 */
	public boolean saveExcelCS (String a,String liucId) {
		String id = SqlUtil.uuid();
		Record rec = new Record().set("excelCs",a).set("liucId",liucId).remove("id");
		return Db.save("njpt_sc_excelcanshu", rec);
	}
	
	/**
	 * 保存关联参数
	 * @param tableCs
	 * @param excelCs
	 * @param liucId
	 * @return
	 */
	public boolean saveGuanlian (String tableCs,String excelCs,String liucId) {
		String id = SqlUtil.uuid();
		Record rec = new Record().set("id", id).set("tableCs", tableCs).set("excelCs", excelCs).set("liucId", liucId);
		return Db.save("njpt_sc_guanlian", "id", rec);
		
	}
	
	/**
	 * 避免save重复，删除旧的几率
	 * @param liucId
	 * @return
	 */
	public int delTableCs(String liucId){
		return Db.use("").update("DELETE FROM njpt_sc_tablecanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 避免save重复，删除旧的几率
	 * 通过update来进行批量删除
	 * @param liucId
	 * @return
	 */
	public int delExcelCs(String liucId){
		return Db.update("DELETE FROM njpt_sc_excelcanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 保存excel
	 * @param param
	 * @param biaoName
	 * @return
	 */
	public boolean saveExcel(HashMap<String, Object> param,String biaoName){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		Record record = new Record().setColumns(param);
		//Record shi =  Db.use("jlreport").findFirst("select * from diqu where shi=?", param.get("shi"));
		//record.set("quyu", shi.getStr("diqu"));
		return Db.save(biaoName, "id", record);
		
	}
	
	/**
	 * 保存查询解析参数
	 * @param liucId
	 * @param filePath
	 * @param lanweiH
	 * @param firstSjh
	 * @return
	 */
	public boolean saveJxcxCanshu (String liucId,String filePath,String biaoName,int qsh,int dah) {
		Record record = new Record().set("liucId", liucId).set("filePath", filePath).set("biaoName", biaoName)
				.set("lanweiH", qsh).set("firstSjh", dah).remove("id");
		return Db.save("njpt_sc_jxcxcanshu", "id", record);
	}
	
	/**
	 * 避免重复，删除重复数据
	 * @param liucId
	 * @return
	 */
	public int delCxcanshu (String liucId) {
		return Db.update("DELETE FROM njpt_sc_jxcxcanshu where liucId = ? ", liucId);
	}
	
	/**
	 * 删除关联的配置
	 * @param param
	 * @return
	 */
	public boolean delPeizhi (String id) {
		return Db.deleteById("njpt_sc_guanlian", "id",id);
	}
	
	public List<Map<Integer, String>> listrec = null;
	
	/**
	 * 解析excel的内容 增加了栏位名行和第一个数据行的判断 适用：xls,xlsx 文件类型：Microsoft Excel 工作表
	 * (.xlsx)----用XSSF... 文件类型：Microsoft Excel 97-2003 工作表 (.xls) ---- 用HSSF...
	 * 
	 * @param filePath
	 *            文件位置
	 * @param qsh
	 *            栏位名行
	 * @param dah
	 *            第一个数据行
	 * @return
	 * @throws IOException
	 */
	/*@SuppressWarnings("resource")
	public List<Map<Integer, String>> dealDataByPath(String filePath, int qsh,
			int dah) throws IOException {
		
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		// 工作簿
		HSSFWorkbook swb = null;
		swb = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		HSSFSheet sheet = swb.getSheetAt(0); // 获取到第一个sheet中数据
		// 根据栏位名行和第一个数据行判断，解析excel
		if (dah - qsh == 1) {// 标题行和数据行差为1
			for (int i = qsh - 1; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
				HSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
				Map<Integer, String> map = new HashMap<Integer, String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell((short) j); // 获取到第j行的数据(单元格)
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					map.put(j, cell.getStringCellValue());
				}
				list.add(map);
			}
		} else {// 标题行和数据行差不为1
			HSSFRow row = sheet.getRow(qsh - 1); // 获取到第i列的行数据(表格行)
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell((short) j); // 获取到第j行的数据(单元格)
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				map.put(j, cell.getStringCellValue());
			}
			list.add(map);

			for (int i = dah - 1; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
				row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
				map = new HashMap<Integer, String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell((short) j); // 获取到第j行的数据(单元格)
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					map.put(j, cell.getStringCellValue());
				}
				list.add(map);
			}

		}
		return list;
	}
	*/
	
	
	/**
	 * 解析excel的内容 增加了栏位名行和第一个数据行的判断 适用：xls,xlsx 文件类型：Microsoft Excel 工作表
	 * (.xlsx)----用XSSF... 文件类型：Microsoft Excel 97-2003 工作表 (.xls) ---- 用HSSF...
	 * 
	 * @param filePath
	 *            文件位置
	 * @param qsh
	 *            栏位名行
	 * @param dah
	 *            第一个数据行
	 * @return
	 * @throws IOException
	 */
	public List<Map<Integer, String>> dealDataByPath(String filePath, String biaoName, int qsh,
			int dah) throws IOException {
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		// 工作簿
		HSSFWorkbook swb = null;
		swb = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		HSSFSheet sheet = swb.getSheetAt(0); // 获取到第一个sheet中数据
		// 根据栏位名行和第一个数据行判断，解析excel
		if (dah - qsh == 1) {// 标题行和数据行差为1
			for (int i = qsh - 1; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
				HSSFRow row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
				Map<Integer, String> map = new HashMap<Integer, String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {		
					HSSFCell cell = row.getCell(j); // 获取到第j行的数据(单元格)
					
					if (cell == null) {
						map.put(j,"null");
					}else{
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						map.put(j, cell.getStringCellValue());
						System.out.print(cell.getStringCellValue()+",");
					}
				}
				System.out.println();
				list.add(map);
			}
		} else {// 标题行和数据行差不为1
			HSSFRow row = sheet.getRow(qsh - 1); // 获取到第i列的行数据(表格行)
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell((short) j); // 获取到第j行的数据(单元格)
				if (cell == null) {
					map.put(j,"null");
				}else{
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					map.put(j, cell.getStringCellValue());
				}
			}
			list.add(map);

			for (int i = dah - 1; i < sheet.getLastRowNum() + 1; i++) {// 第二行开始取值，第一行为标题行
				row = sheet.getRow(i); // 获取到第i列的行数据(表格行)
				map = new HashMap<Integer, String>();
				//map.clear();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell((short) j); // 获取到第j行的数据(单元格)
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					map.put(j, cell.getStringCellValue());
					System.out.print(cell.getStringCellValue()+",");
				}
				System.out.println();
				list.add(map);
			}
		}
		String role = UserKit.currentUserInfo().getRole().getString("name");
		String area = UserKit.currentUserInfo().getArea().getString("name");
		String company = UserKit.currentUserInfo().getDep().getString("name");
		if("企业".equals(role) || ("市属企业").equals(role)){
			if("njpt_qiuliangshougouwuribb".equals(biaoName) || "njpt_xialiangshougouwuribb".equals(biaoName)){
				for(int j = 1; j < list.size(); j++){
					list.get(j).put(0, area);
					list.get(j).put(1, company);
					for(int i = 2; i < list.get(0).size();i++){
						if(!list.get(j).containsKey(i)){
							list.get(j).put(i, "0");
						}
					}
				}
			}
		}
		if("区县粮食局".equals(role)){
			if("njpt_qiuliangshougouwuribb".equals(biaoName) || "njpt_xialiangshougouwuribb".equals(biaoName)){
				for(int j = 1; j < list.size(); j++){
					list.get(j).put(0, area);
					list.get(j).put(1, area+"粮食购销公司");
					for(int i = 2; i < list.get(0).size();i++){
						if(!list.get(j).containsKey(i)){
							list.get(j).put(i, "0");
						}
					}
				}
			}
			
		}
		
		return list;
	}
	
	public int JiexiExcel(String liucid) throws IOException{
		// 查询配置表，查找需要用的配置参数
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucid);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");
		List a = dealDataByPath(filePath, biaoName,qsh, dah);
		Map<Integer, String> tou = (Map<Integer, String>) a.get(0);
		int lie = tou.size();
		/*int hang = a.size();
		for (int i=0;i<hang;i++) {
			Map<Integer, String> tou = (Map<Integer, String>) a.get(0);
			int lie = tou.size();
			for (int j=0;j<lie;j++) {
				jxec.add(tou.get(j));
			}
		}*/
		/*System.out.println(jxec);
		ret.put(Ret.create("biaotou", jxec));
		System.out.println(ret.get("biaotou"));*/
		return lie;
	}
	
	

	/**
	 * 保存excel数据至数据库    企业信息
	 * 
	 * @throws IOException
	 */
	@Before(Tx.class)
	public Ret saveExcel_qiye(String liucid) throws IOException {
		Ret ret = new Ret();
		// 设置uuid，和date
		int count = 0;// 统计上传成功数目
		int uncount = 0;// 统计上传失败数目
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());
		// 查询配置表，查找需要用的配置参数
		String liucId = liucid;
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucId);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");

		String tab = null;// 关联表的tableCS
		String exc = null;// 关联表的excelCs
		String lwcs = null;// 关联表lanwei

		Map<Integer, String> map = null;
		HashMap<Object, Object> param = new HashMap<Object, Object>();
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		listrec = dealDataByPath(filePath,biaoName, qsh, dah);
		List<Record> tableabc = Duang.duang(ExcelDao.class).findTaCs(liucId);
		// 先查出配置
		List<Record> te = Duang.duang(ExcelDao.class).findPeizhi(liucId);
		for (int i = 0; i < te.size(); i++) {
			// 获取guanlian表一对关联的参数
			tab = te.get(i).getStr("tableCs");
			exc = te.get(i).getStr("excelCs");

			if (exc.equals("$设#置时间$")) {
				param.put("$设#置时间$", tab);
			} else if (exc.equals("$设#置ID$")) {
				param.put("$设#置ID$", tab);
			} else {

				// 获取excel栏位行参数
				map = listrec.get(0);
				for (int j = 0; j < map.size(); j++) {
					lwcs = map.get(j);
					// 如果关联表exc参数和lwcs相等
					if (exc.equals(lwcs)) {
						param.put(j, tab);
					}
				}
			}
		}
		boolean rets = false;
		int lie = listrec.size();
		ret.put("lieshu", lie);
		// 遍历解析的excel数据，配对，调用保存excel方法
		for (int i = 1; i < listrec.size(); i++) {
			// 判断重复不添加
			List<Record> listdep = findqyshuju(listrec.get(i).get(1));
			if(StrKit.isBlank(listrec.get(i).get(0))||listrec.get(i).get(0) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业名称不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：企业名称不能为空！");*/
				continue;
			}
			if (listdep.size() != 0) {
				//导入失败后提示信息
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业信息已存在！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业信息已存在！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(1))||listrec.get(i).get(1) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业组织机构代码不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：组织机构代码不能为空！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(18))||listrec.get(i).get(18) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：法定代表人不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：法定代表人不能为空！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(5))||listrec.get(i).get(5) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：区域不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：县不能为空！");*/
				continue;
			}
			ret.put("ret",true);

			// 判断是否需要额外加DATE和UUID
			if ((String) param.get("$设#置时间$") != null) {
				queryparam.put((String) param.get("$设#置时间$"), date + ":" + i);
			}
			if ((String) param.get("$设#置ID$") != null) {
				String uuid = SqlUtil.uuid();
				queryparam.put((String) param.get("$设#置ID$"), uuid);
			}

			for (int j = 0; j < listrec.get(i).size(); j++) {
				if (param.get(j) != null) {
					queryparam
							.put((String) param.get(j), listrec.get(i).get(j));
				} else {
					System.out.println();
				}
			}

			// 给配置中没有的表字段添加null值
			for (int x = 0; x < tableabc.size(); x++) {
				String a = tableabc.get(x).getStr("tableCs");
				if (queryparam.get(a) == null) {
					queryparam.put(a, null);
				}

			}
			// 保存 excel
			rets = Duang.duang(ExcelBo.class).saveExcel(queryparam, biaoName);
			count += 1;

		}
		uncount = listrec.size() - count - 1;
		int allcount = listrec.size() - 1;
		/*
		 * sclist.add(new Record().set("count", "成功上传"+count+"条"));
		 * sclist.add(new Record().set("uncount","失败上传"+uncount+"条"));
		 */
		ret.put("allcount", "本次导入共含" + allcount + "条数据");
		ret.put("count", "成功导入" + count + "条数据,失败"+ uncount + "条数据");
		/*ret.put("uncount", "失败" + uncount + "条数据");*/
		return ret;
	}
	
	/**
	 * 保存excel数据至数据库    区县信息
	 * 
	 * @throws IOException
	 */
	@Before(Tx.class)
	public Ret saveExcel_quxian(String liucid) throws IOException {
		Ret ret = new Ret();
		// 设置uuid，和date
		int count = 0;// 统计上传成功数目
		int uncount = 0;// 统计上传失败数目
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());
		// 查询配置表，查找需要用的配置参数
		String liucId = liucid;
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucId);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");

		String tab = null;// 关联表的tableCS
		String exc = null;// 关联表的excelCs
		String lwcs = null;// 关联表lanwei

		Map<Integer, String> map = null;
		HashMap<Object, Object> param = new HashMap<Object, Object>();
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		listrec = dealDataByPath(filePath,biaoName, qsh, dah);
		List<Record> tableabc = Duang.duang(ExcelDao.class).findTaCs(liucId);
		// 先查出配置
		List<Record> te = Duang.duang(ExcelDao.class).findPeizhi(liucId);
		for (int i = 0; i < te.size(); i++) {
			// 获取guanlian表一对关联的参数
			tab = te.get(i).getStr("tableCs");
			exc = te.get(i).getStr("excelCs");

			if (exc.equals("$设#置时间$")) {
				param.put("$设#置时间$", tab);
			} else if (exc.equals("$设#置ID$")) {
				param.put("$设#置ID$", tab);
			} else {

				// 获取excel栏位行参数
				map = listrec.get(0);
				for (int j = 0; j < map.size(); j++) {
					lwcs = map.get(j);
					// 如果关联表exc参数和lwcs相等
					if (exc.equals(lwcs)) {
						param.put(j, tab);
					}
				}
			}
		}
		boolean rets = false;
		int lie = listrec.size();
		ret.put("lieshu", lie);
		// 遍历解析的excel数据，配对，调用保存excel方法
		for (int i = 1; i < listrec.size(); i++) {
			// 判断重复不添加
			List<Record> listdep = finddqshuju(listrec.get(i).get(1));
			if(StrKit.isBlank(listrec.get(i).get(0))||listrec.get(i).get(0) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：区域名称不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：企业名称不能为空！");*/
				continue;
			}
			if (listdep.size() != 0) {
				//导入失败后提示信息
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：区域信息已存在！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业信息已存在！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(1))||listrec.get(i).get(1) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：行政区域代码不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：组织机构代码不能为空！");*/
				continue;
			}
			ret.put("ret",true);

			// 判断是否需要额外加DATE和UUID
			if ((String) param.get("$设#置时间$") != null) {
				queryparam.put((String) param.get("$设#置时间$"), date + ":" + i);
			}
			if ((String) param.get("$设#置ID$") != null) {
				String uuid = SqlUtil.uuid();
				queryparam.put((String) param.get("$设#置ID$"), uuid);
			}
			for (int j = 0; j < listrec.get(i).size(); j++) {
				if (param.get(j) != null) {
					queryparam
							.put((String) param.get(j), listrec.get(i).get(j));
				} else {
					System.out.println();
				}
			}
			// 给配置中没有的表字段添加null值
			for (int x = 0; x < tableabc.size(); x++) {
				String a = tableabc.get(x).getStr("tableCs");
				if (queryparam.get(a) == null) {
					queryparam.put(a, null);
				}
			}
			// 保存 excel
			rets = Duang.duang(ExcelBo.class).saveExcel(queryparam, biaoName);
			count += 1;
		}
		uncount = listrec.size() - count - 1;
		int allcount = listrec.size() - 1;
		/*
		 * sclist.add(new Record().set("count", "成功上传"+count+"条"));
		 * sclist.add(new Record().set("uncount","失败上传"+uncount+"条"));
		 */
		ret.put("allcount", "本次导入共含" + allcount + "条数据");
		ret.put("count", "成功导入" + count + "条数据,失败"+ uncount + "条数据");
		/*ret.put("uncount", "失败" + uncount + "条数据");*/
		return ret;
	}
	
	
	
	/**
	 * 保存excel数据至数据库    库点信息
	 * 
	 * @throws IOException
	 */
	@Before(Tx.class)
	public Ret saveExcel_kudian(String liucid) throws IOException {
		Ret ret = new Ret();
		// 设置uuid，和date
		int count = 0;// 统计上传成功数目
		int uncount = 0;// 统计上传失败数目
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());
		// 查询配置表，查找需要用的配置参数
		String liucId = liucid;
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucId);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");

		String tab = null;// 关联表的tableCS
		String exc = null;// 关联表的excelCs
		String lwcs = null;// 关联表lanwei

		Map<Integer, String> map = null;
		HashMap<Object, Object> param = new HashMap<Object, Object>();
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		listrec = dealDataByPath(filePath,biaoName, qsh, dah);
		List<Record> tableabc = Duang.duang(ExcelDao.class).findTaCs(liucId);
		// 先查出配置
		List<Record> te = Duang.duang(ExcelDao.class).findPeizhi(liucId);
		for (int i = 0; i < te.size(); i++) {
			// 获取guanlian表一对关联的参数
			tab = te.get(i).getStr("tableCs");
			exc = te.get(i).getStr("excelCs");

			if (exc.equals("$设#置时间$")) {
				param.put("$设#置时间$", tab);
			} else if (exc.equals("$设#置ID$")) {
				param.put("$设#置ID$", tab);
			} else {

				// 获取excel栏位行参数
				map = listrec.get(0);
				for (int j = 0; j < map.size(); j++) {
					lwcs = map.get(j);
					// 如果关联表exc参数和lwcs相等
					if (exc.equals(lwcs)) {
						param.put(j, tab);
					}
				}
			}
		}
		boolean rets = false;
		int lie = listrec.size();
		ret.put("lieshu", lie);
		// 遍历解析的excel数据，配对，调用保存excel方法
		for (int i = 1; i < listrec.size(); i++) {
			// 判断重复不添加
			List<Record> listdep = findkdshuju(listrec.get(i).get(1));
			if(StrKit.isBlank(listrec.get(i).get(0))||listrec.get(i).get(0) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：库点名称不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：企业名称不能为空！");*/
				continue;
			}
			if (listdep.size() != 0) {
				//导入失败后提示信息
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：库点信息已存在！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业信息已存在！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(1))||listrec.get(i).get(1) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：企业组织机构代码不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：组织机构代码不能为空！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(2))||listrec.get(i).get(2) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：库点编码不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：县不能为空！");*/
				continue;
			}
			if(StrKit.isBlank(listrec.get(i).get(7))||listrec.get(i).get(7) == "null"){
				ret.put("ret",rets);
				ret.put("msg","第"+(i + 1) +"行&nbsp;&nbsp;失败：区域不能为空！");
				/*ret.put("msg"+i+"","第"+(i + 1) +"行：县不能为空！");*/
				continue;
			}
			ret.put("ret",true);

			// 判断是否需要额外加DATE和UUID
			if ((String) param.get("$设#置时间$") != null) {
				queryparam.put((String) param.get("$设#置时间$"), date + ":" + i);
			}
			if ((String) param.get("$设#置ID$") != null) {
				String uuid = SqlUtil.uuid();
				queryparam.put((String) param.get("$设#置ID$"), uuid);
			}

			for (int j = 0; j < listrec.get(i).size(); j++) {
				if (param.get(j) != null) {
					queryparam
							.put((String) param.get(j), listrec.get(i).get(j));
				} else {
					System.out.println();
				}
			}

			// 给配置中没有的表字段添加null值
			for (int x = 0; x < tableabc.size(); x++) {
				String a = tableabc.get(x).getStr("tableCs");
				if (queryparam.get(a) == null) {
					queryparam.put(a, null);
				}

			}
			// 保存 excel
			rets = Duang.duang(ExcelBo.class).saveExcel(queryparam, biaoName);
			count += 1;
		}
		
		uncount = listrec.size() - count - 1;
		int allcount = listrec.size() - 1;
		/*
		 * sclist.add(new Record().set("count", "成功上传"+count+"条"));
		 * sclist.add(new Record().set("uncount","失败上传"+uncount+"条"));
		 */
		ret.put("allcount", "本次导入共含" + allcount + "条数据");
		ret.put("count", "成功导入" + count + "条数据,失败"+ uncount + "条数据");
		/*ret.put("uncount", "失败" + uncount + "条数据");*/
		return ret;
	}
	
	/**
	 * 保存excel数据至数据库    秋粮五日报表
	 * 
	 * @throws IOException
	 */
	@Before(Tx.class)
	public Ret saveExcel_ql(String liucid) throws IOException {
		Ret ret = new Ret();
		// 设置uuid，和date
		int count = 0;// 统计上传成功数目
		int uncount = 0;// 统计上传失败数目
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());

		// 查询配置表，查找需要用的配置参数
		String liucId = liucid;
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucId);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");

		String tab = null;// 关联表的tableCS
		String exc = null;// 关联表的excelCs
		String lwcs = null;// 关联表lanwei

		Map<Integer, String> map = null;
		HashMap<Object, Object> param = new HashMap<Object, Object>();
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		listrec = dealDataByPath(filePath, biaoName, qsh, dah);
		List<Record> tableabc = Duang.duang(ExcelDao.class).findTaCs(liucId);
		// 先查出配置
		List<Record> te = Duang.duang(ExcelDao.class).findPeizhi(liucId);
		for (int i = 0; i < te.size(); i++) {
			// 获取guanlian表一对关联的参数
			tab = te.get(i).getStr("tableCs");
			exc = te.get(i).getStr("excelCs");

			if (exc.equals("$设#置时间$")) {
				param.put("$设#置时间$", tab);
			} else if (exc.equals("$设#置ID$")) {
				param.put("$设#置ID$", tab);
			} else {

				// 获取excel栏位行参数
				map = listrec.get(0);
				for (int j = 0; j < map.size(); j++) {
					lwcs = map.get(j);
					// 如果关联表exc参数和lwcs相等
					if (exc.equals(lwcs)) {
						param.put(j, tab);
					}
				}
			}
		}
		boolean rets = false;
		int lie = listrec.size();
		ret.put("lieshu", lie);
		// 遍历解析的excel数据，配对，调用保存excel方法
		for (int i = 1; i < listrec.size(); i++) {
			
			
			
			ret.put("ret",true);
			
			// 判断是否需要额外加DATE和UUID
			if ((String) param.get("$设#置时间$") != null) {
				queryparam.put((String) param.get("$设#置时间$"), date + ":" + i);
			}
			if ((String) param.get("$设#置ID$") != null) {
				String uuid = SqlUtil.uuid();
				queryparam.put((String) param.get("$设#置ID$"), uuid);
			}
			
			for (int j = 0; j < listrec.get(i).size(); j++) {
				if (param.get(j) != null) {
					queryparam
							.put((String) param.get(j), listrec.get(i).get(j));
				} else {
					System.out.println();
				}
			}

			// 给配置中没有的表字段添加null值
			for (int x = 0; x < tableabc.size(); x++) {
				String a = tableabc.get(x).getStr("tableCs");
				if (queryparam.get(a) == null || "null".equals(queryparam.get(a)) || "".equals(queryparam.get(a))) {
					queryparam.put(a, 0);
				}

			}

			
			// 保存 excel
			rets = Duang.duang(ExcelBo.class).saveExcel(queryparam, biaoName);
			count += 1;

		}
		uncount = listrec.size() - count - 1;
		int allcount = listrec.size() - 1;
		/*
		 * sclist.add(new Record().set("count", "成功上传"+count+"条"));
		 * sclist.add(new Record().set("uncount","失败上传"+uncount+"条"));
		 */
		ret.put("allcount", "本次导入共含" + allcount + "条数据");
		ret.put("count", "成功导入" + count + "条数据,失败"+ uncount + "条数据");
		/*ret.put("uncount", "失败" + uncount + "条数据");*/
		return ret;
	}
	
	/**
	 * 保存excel数据至数据库    夏粮五日报表
	 * 
	 * @throws IOException
	 */
	@Before(Tx.class)
	public Ret saveExcel_xl(String liucid) throws IOException {
		Ret ret = new Ret();
		// 设置uuid，和date
		int count = 0;// 统计上传成功数目
		int uncount = 0;// 统计上传失败数目
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());

		// 查询配置表，查找需要用的配置参数
		String liucId = liucid;
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucId);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");

		String tab = null;// 关联表的tableCS
		String exc = null;// 关联表的excelCs
		String lwcs = null;// 关联表lanwei

		Map<Integer, String> map = null;
		HashMap<Object, Object> param = new HashMap<Object, Object>();
		HashMap<String, Object> queryparam = new HashMap<String, Object>();
		listrec = dealDataByPath(filePath, biaoName, qsh, dah);
		List<Record> tableabc = Duang.duang(ExcelDao.class).findTaCs(liucId);
		// 先查出配置
		List<Record> te = Duang.duang(ExcelDao.class).findPeizhi(liucId);
		for (int i = 0; i < te.size(); i++) {
			// 获取guanlian表一对关联的参数
			tab = te.get(i).getStr("tableCs");
			exc = te.get(i).getStr("excelCs");

			if (exc.equals("$设#置时间$")) {
				param.put("$设#置时间$", tab);
			} else if (exc.equals("$设#置ID$")) {
				param.put("$设#置ID$", tab);
			} else {
				// 获取excel栏位行参数
				map = listrec.get(0);
				for (int j = 0; j < map.size(); j++) {
					lwcs = map.get(j);
					// 如果关联表exc参数和lwcs相等
					if (exc.equals(lwcs)) {
						param.put(j, tab);
					}
				}
			}
		}
		boolean rets = false;
		int lie = listrec.size();
		ret.put("lieshu", lie);
		// 遍历解析的excel数据，配对，调用保存excel方法
		for (int i = 1; i < listrec.size(); i++) {
			
			
			queryparam.clear();
			ret.put("ret",true);
			
			// 判断是否需要额外加DATE和UUID
			if ((String) param.get("$设#置时间$") != null) {
				queryparam.put((String) param.get("$设#置时间$"), date + ":" + i);
			}
			if ((String) param.get("$设#置ID$") != null) {
				String uuid = SqlUtil.uuid();
				queryparam.put((String) param.get("$设#置ID$"), uuid);
			}
			
			for (int j = 0; j < listrec.get(i).size(); j++) {
				if (param.get(j) != null) {
					queryparam
							.put((String) param.get(j), listrec.get(i).get(j));
				} else {
					System.out.println();
				}
			}

			// 给配置中没有的表字段添加null值
			for (int x = 0; x < tableabc.size(); x++) {
				String a = tableabc.get(x).getStr("tableCs");
				if (queryparam.get(a) == null || 
						"null".equals(queryparam.get(a)) || 
						"".equals(queryparam.get(a))) {
					queryparam.put(a, 0);
				}
			}

			
			// 保存 excel
			rets = Duang.duang(ExcelBo.class).saveExcel(queryparam, biaoName);
			count += 1;

		}
		uncount = listrec.size() - count - 1;
		int allcount = listrec.size() - 1;
		/*
		 * sclist.add(new Record().set("count", "成功上传"+count+"条"));
		 * sclist.add(new Record().set("uncount","失败上传"+uncount+"条"));
		 */
		ret.put("allcount", "本次导入共含" + allcount + "条数据");
		ret.put("count", "成功导入" + count + "条数据,失败"+ uncount + "条数据");
		/*ret.put("uncount", "失败" + uncount + "条数据");*/
		return ret;
	}
	
	
	/**
	 * 保存查询参数
	 * @param filepath
	 * @param filename
	 * @param liucId
	 * @return
	 */
	public int upCanshu(String filepath,String filename, String liucId) {
		return Db.update("UPDATE njpt_sc_jxcxcanshu SET filePath = ?,excelname = ? WHERE liucId = ?",
				filepath, filename, liucId);
	}

	public Ret upLHSQxw(String filename, String id) {
		Record record =new Record();
		record.set("id", id);
		record.set("lhsq_xw", filename);
		record.set("lhsq_zt", 6);
		boolean bool=Db.save("njpt_lunhuanshenqingb", record);
		Ret ret= Ret.create("ret", bool);
		ret.put("id",id);
		return ret;
	}
	public Ret upLHTZxw(String filename, String id) {
		Record record =new Record();
		record.set("id", id);
		record.set("lhpz_xw", filename);
		boolean bool=Db.update("njpt_lunhuanshenqingb", record);
		Ret ret= Ret.create("ret", bool);
		return ret;
	}
	public Ret upQRSQxw(String filename, String id) {
		Record record =new Record();
		record.set("id", id);
		record.set("qrsq_xw", filename);
		boolean bool=Db.update("njpt_lunhuanshenqingb", record);
		Ret ret= Ret.create("ret", bool);
		return ret;
	}
	public Ret upQRTZxw(String filename, String id) {
		Record record =new Record();
		record.set("id", id);
		record.set("qrtz_xw", filename);
		boolean bool=Db.update("njpt_lunhuanshenqingb", record);
		Ret ret= Ret.create("ret", bool);
		return ret;
	}
	/**
	 * 通过qyzzjgdm查 企业表
	 * @param zzjgdm
	 * @return
	 */
	public List<Record> findqyshuju(String qyzzjgdm) {
		return Db.find("select * from tz_qiye where qyzzjgdm = ?", qyzzjgdm);
	}
	/**
	 * 通过xzqhdm查 地区表
	 * @param zzjgdm
	 * @return
	 */
	public List<Record> finddqshuju(String xzqhdm) {
		return Db.find("select * from njpt_diqu where xzqhdm = ?", xzqhdm);
	}
	/**
	 * 通过qyzzjgdm查 库点表
	 * @param zzjgdm
	 * @return
	 */
	public List<Record> findkdshuju(String qyzzjgdm) {
		return Db.find("select * from tz_kudian where qyzzjgdm = ?", qyzzjgdm);
	}
	
}
