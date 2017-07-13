package com.zkzy.njzhpt.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.zkzy.njzhpt.bo.CangchuglBo;
import com.zkzy.njzhpt.bo.CommondBo;
import com.zkzy.njzhpt.bo.ExcelBo;
import com.zkzy.njzhpt.config.interfaces.ICommond;
import com.zkzy.njzhpt.dao.CangchuglDao;
import com.zkzy.njzhpt.dao.ExcelDao;
import com.zkzy.njzhpt.validator.addXiaozuValidator;
import com.zkzy.njzhpt.validator.addYaojirukuValidator;

public class CangchuglController extends Controller {
	
	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
	public void index(){
		File file=new File("C:\\Users\\Administrator\\Desktop\\新建文件夹32123\\南京市2014年仓储普查\\upload\\images\\_783830659_002_002_f.jpg");
		LogKit.info(String.valueOf(file.exists()));
		renderFile(file);
	}
	
	public void toHTML() {
		String url = (String) getAttr("_url_");
		render(url);
	}
	
	/**
	 * 
	 */
	@Clear
	public void showshipin(){	
		String userAgent =getRequest().getHeader("user-agent");
		String huashu = getPara("huashu");
		boolean flag = false;
		if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
				for (String item : agent) {
					if (userAgent.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		setAttr("flag", flag);
		setAttr("huashu",huashu);
		setAttr("wmode","opaque");
		render("cangchuguanli/shiping/showshipin.html");
	}
	
	@Clear
	public void showshipin1(){	
		String userAgent =getRequest().getHeader("user-agent");
		
		boolean flag = false;
		if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
				for (String item : agent) {
					if (userAgent.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		setAttr("flag", flag);	
		render("cangchuguanli/shiping/showshipin1.html");
	}
	
	/**
	 * 
	 */
	@Clear
	public void churukuxq(){	
		setAttr("vSwiftNumber", getPara("vSwiftNumber"));
		render("cangchuguanli/yewuguanli/churukuxq.html");
	}
	
	/**
	 * 
	 */
	@Clear
	public void xunzhengxinxi(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		render("cangchuguanli/zuoyeguanli/bqy_xunzhengxinxi.html");
	}
	
	/**
	 * 
	 */
	@Clear
	public void lengquexinxi(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		render("cangchuguanli/zuoyeguanli/bqy_lengquexinxi.html");
	}
	
	/**
	 * 
	 */
	@Clear
	public void tongfengxinxi(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		render("cangchuguanli/zuoyeguanli/bqy_tongfengxinxi.html");
	}
	
	
	/**
	 * 出入库
	 */
	@Clear
	public void bqychuruku(){	
		setAttr("qyzzjgdm", getPara("qyzzjgdm"));
		setAttr("kdbm", getPara("kdbm"));
		render("cangchuguanli/zuoyeguanli/bqy_churuku.html");
	}
	
	
	
	/**
	 * 获取区县信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryDiqu() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryDiqu(queryParam).getList()); 
	}
	
	/**
	 * 获取区县信息
	 * @author yzz
	 * @throws Exception 
	 */
	
	public void findxjsp() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findxjsp(queryParam).getList()); 
	}
	
	

	/**
	 * 获取企业性质信息
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryQiyeXZ() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryQiyeXZ(queryParam).getList()); 
	}
	
	/**
	 * 获取企业经营类型
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryJYLX() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryJYLX(queryParam).getList()); 
	}
	
	
	/**
	 * 获取仓房主要业务
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryZYYW() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryZYYW(queryParam).getList()); 
	}
	/**
	 * 获取人员名字
	 * @author yzz
	 * @throws Exception 
	 */
	public void findUsername() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findUsername(queryParam).getList()); 
	}
	
	/**
	 * 获取仓廒类型
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryCALX() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryCALX(queryParam).getList()); 
	}
	/**
	 * 获取仓廒状态名称
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryCAZTMC() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryCAZTMC(queryParam).getList()); 
	}
	/**
	 * 获取仓廒使用情况
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryCASYQK() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryCASYQK(queryParam).getList()); 
	}
	/**
	 * 获取仓廒结构名称
	 * @author yzz
	 * @throws Exception 
	 */
	public void queryCAJGMC() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryCAJGMC(queryParam).getList()); 
	}
	
	

	/**
	 * 获取库点类型
	 * @author yzz
	 * @throws Exception 
	 */
	
	public void queryKdlx() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryKdlx(queryParam).getList()); 
	}
	
	/**
	 * 上传文件，上传的同时保存jxcxcanshu
	 * @throws IOException
	 */
	public void uploadFile() throws IOException {
		UploadFile uploadFile = this.getFile();
		
		File file = uploadFile.getFile();
		String filepath = file.getPath();
		String filename = file.getName();
		/*String liucid = "cd6096c03a9c42258c3d9b16b6a1e49d";*/
		String liucid = getPara("liucid");
		Duang.duang(ExcelBo.class).upCanshu(filepath, filename,liucid);
		/*Ret msg = new Ret();
		if (file != null) {
			msg.put("ret", "文件上传成功");
		}else{
			msg.put("ret","文件上传失败");
		}*/
		int lie = Duang.duang(ExcelBo.class).JiexiExcel(liucid);
		renderJson(lie);
	}
	
	/**
	 * 上传企业示意图
	 */
	public void scqysyt() throws IOException {
		UploadFile uploadFile = this.getFile();
		
		File file = uploadFile.getFile();
		String filepath = file.getPath();
		String filename = file.getName();
		filepath = filepath.replaceAll("\\\\", "/");
		/*Duang.duang(CangchuglBo.class).savetupian(filepath);
		List<Record> list = Duang.duang(CangchuglBo.class).findtupian(filepath);*/
		renderJson(filename);
	}
	
	/**
	 * 根据qyzzjgdm，kdbm获取库点示意图
	 */
	public void kudiansyt(){
		String qyzzjgdm = getPara("qyzzjgdm");
		String kdbm = getPara("kdbm");
		/*String qyzzjgdm = "135654781";
		String kdbm = "003";*/
		String kdsypath = Duang.duang(CangchuglBo.class).findkdtp(qyzzjgdm, kdbm).get(0).getStr("kdsypath");
		File file1;
		File file;
		if(kdsypath == null || "".equals(kdsypath)){
			kdsypath = "img/nokudian.png";
			file=new File(getSession().getServletContext().getRealPath("/")+"\\"+kdsypath);
		}else{
			file1 = new File(getSession().getServletContext().getRealPath("/")+"\\"+kdsypath);
			if(!file1.exists()){
				kdsypath = "img/nokudian.png";
			}
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+kdsypath);
		}
		LogKit.info(String.valueOf(file.exists()));
		renderFile(file);
	}
	
	
	/**
	 * 根据qyzzjgdm，kdbm获取库点全貌图
	 */
	public void kudianqmt(){
		String qyzzjgdm = getPara("qyzzjgdm");
		String kdbm = getPara("kdbm");
		/*String qyzzjgdm = "135654781";
		String kdbm = "003";*/
		String kdqmpath = Duang.duang(CangchuglBo.class).findkdtp(qyzzjgdm, kdbm).get(0).getStr("kdqmpath");
		File file1;
		File file;
		if(kdqmpath == null || "".equals(kdqmpath)){
			kdqmpath = "img/nokudian.png";
			file=new File(getSession().getServletContext().getRealPath("/")+"\\"+kdqmpath);
		}else{
			file1 = new File(getSession().getServletContext().getRealPath("/")+"\\"+kdqmpath);
			if(!file1.exists()){
				kdqmpath = "img/nokudian.png";
			}
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+kdqmpath);
		}
		LogKit.info(String.valueOf(file.exists()));
		renderFile(file);
	}
	
	
	/**
	 * 根据qyzzjgdm获取企业示意图
	 */
	public void qiyesyt(){
		String qyzzjgdm = getPara("qyzzjgdm");
		/*String qyzzjgdm = "737069133";*/
		String qysypath = Duang.duang(CangchuglBo.class).findqytp(qyzzjgdm).get(0).getStr("qysypath");
		File file1;
		File file;
		if(qysypath == null || "".equals(qysypath)){
			qysypath = "img/nopictureqiye.png";
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+qysypath);
		}else{
			file1 = new File(getSession().getServletContext().getRealPath("/")+"\\"+qysypath);
			if(!file1.exists()){
				qysypath = "img/nopictureqiye.png";
			}
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+qysypath);
		}
		LogKit.info(String.valueOf(file.exists()));
		renderFile(file);
	}
	
	
	/**
	 * 根据qyzzjgdm获取企业全貌图
	 */
	public void qiyeqmt(){
		String qyzzjgdm = getPara("qyzzjgdm");
		/*String qyzzjgdm = "716290701";*/
		String qyqmpath = Duang.duang(CangchuglBo.class).findqytp(qyzzjgdm).get(0).getStr("qyqmpath");
		File file1;
		File file;
		if(qyqmpath == null || "".equals(qyqmpath)){
			qyqmpath = "img/nopictureqiye.png";
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+qyqmpath);
		}else{
			file1 = new File(getSession().getServletContext().getRealPath("/")+"\\"+qyqmpath);
			if(!file1.exists()){
				qyqmpath = "img/nopictureqiye.png";
			}
			file = new File(getSession().getServletContext().getRealPath("/")+"\\"+qyqmpath);
		}
		LogKit.info(String.valueOf(file.exists()));
		renderFile(file);
	}
	
	/**
	 * 上传企业全貌图
	 */
	/*public void scqyqmt() throws IOException {
		UploadFile uploadFile = this.getFile();
		
		File file = uploadFile.getFile();
		String filepath = file.getPath();
		String filename = file.getName();
		String qydm = getPara("qydm");
		System.out.println(qydm);
		Duang.duang(CangchuglBo.class).upQyqmt(filepath, qydm);
	}*/
	
	
	/**
	 * 获得解析的excel数据
	 * @throws IOException
	 */
	public void JiexiExcel() throws IOException{
		String liucid = getPara("liucid");
		// 查询配置表，查找需要用的配置参数
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucid);
		String biaoName = cxcs.get(0).getStr("biaoName");
		String filePath = cxcs.get(0).getStr("filePath");
		String filename = cxcs.get(0).getStr("excelname");
		int qsh = cxcs.get(0).get("lanweiH");
		int dah = cxcs.get(0).get("firstSjh");
		List a = Duang.duang(ExcelBo.class).dealDataByPath(filePath, biaoName, qsh, dah);
		/*JSONArray jsonArray = JSONArray.fromObject(a); */
		setAttr("filename", filename);
		renderJson(a);
	}
	
	public void Hqfilename(){
		String liucid = getPara("liucid");
		List<Record> cxcs = Duang.duang(ExcelDao.class).findCxCanshu(liucid);
		String filename = cxcs.get(0).getStr("excelname");
		renderJson(filename);
	}
	
	/**
	 * 导入excel数据  qiye
	 * @throws IOException
	 */
	public void Daorushuju_qiye() throws IOException{
		String liucid = getPara("liucid");
		Ret ret = Duang.duang(ExcelBo.class).saveExcel_qiye(liucid);
		System.out.println(ret.getData());
		renderJson(ret.getData());
	
	}
	
	
	/**
	 * 导入excel数据  quxian
	 * @throws IOException
	 */
	public void Daorushuju_quxian() throws IOException{
		String liucid = getPara("liucid");
		Ret ret = Duang.duang(ExcelBo.class).saveExcel_quxian(liucid);
		System.out.println(ret.getData());
		renderJson(ret.getData());
	}
	
	
	/**
	 * 导入excel数据  kudian
	 * @throws IOException
	 */
	public void Daorushuju_kudian() throws IOException{
		String liucid = getPara("liucid");
		Ret ret = Duang.duang(ExcelBo.class).saveExcel_kudian(liucid);
		System.out.println(ret.getData());
		renderJson(ret.getData());
	}
	

	/**
	 * 药库分页查询
	 * @throws Exception
	 */
	public void findYaoku() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findYaoku(queryparam));
	}
	
	/**
	 * 备案号分页查询
	 * @throws Exception
	 */
	public void querybeian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).querybeian(queryparam));
	}
	
	/**
	 * 熏蒸作业查询
	 * @throws Exception
	 */
	public void findxzzy() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		//renderJson(Duang.duang(CangchuglBo.class).findxzzy(queryparam));
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findxzzy(queryparam));
	}
	
	
	
	/**
	 * 熏蒸作业明细查询
	 * @throws Exception
	 */
	public void findxzzymx() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findxzzymx(queryparam));
	}
	
	/**
	 * 空瓶管理
	 * @throws Exception
	 */
	public void findkongping() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		//renderJson(Duang.duang(CangchuglBo.class).findkongping(queryparam));
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findkongping(queryparam));
	}
	
	
	
	public void delYaoku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYaoku(param).getData());
	}
	
	/**
	 * 删除空瓶
	 */
	public void delyjkp(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delyjkp(param).getData());
	}
	
	/**
	 * 删除熏蒸作业
	 */
	public void delxzzy(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delxzzy(param).getData());
	}
	
	public void addYaoku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYaoku(param).getData());
	}
	
	public void addxzzy(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addxzzy(param).getData());
	}
	
	public void addkongping(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addkongping(param).getData());
	}
	
	public void upYaoku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYaoku(param).getData());
	}
	
	public void upkongping(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upkongping(param).getData());
	}
	
	/**
	 * 修改熏蒸作业
	 */
	public void upxzzy(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upxzzy(param).getData());
	}
	
	/**
	 * 企业备案分页查询
	 * @throws Exception
	 */
	public void findQiyebeian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findQiyebeian(queryparam));
	}
	
	/**
	 * 熏蒸库点管理
	 * @throws Exception
	 */
	public void queryxzkd() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryxzkd(queryparam));
	}
	
	/**
	 * 寻找熏蒸仓号
	 * @throws Exception
	 */
	public void selectXzch() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).selectXzch(queryparam));
	}
	
	/**
	 * 寻找备案企业
	 * @throws Exception
	 */
	public void querybaqy() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).querybaqy(queryparam));
	}
	
	public void findqyba() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findqyba(queryparam));
	}
	
	
	public void delQiyebeian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delQiyebeian(param).getData());
	}
	
	public void addQiyebeian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addQiyebeian(param).getData());
	}
	
	public void upQiyebeian(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upQiyebeian(param).getData());
	}
	
	
	/**
	 * 出入库分页查询
	 * @throws Exception
	 */
	@Clear
	public void findChuruku() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		/*String qyzzjgdm = getPara("qyzzjgdm");
		queryparam.put("qyzzjgdm", qyzzjgdm);*/
		renderJson(Duang.duang(CangchuglBo.class).findChuruku(queryparam));
	}
	
	public void delChuruku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delChuruku(param).getData());
	}
	
	public void addChuruku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addChuruku(param).getData());
	}
	
	public void upChuruku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upChuruku(param).getData());
	}
	
	/**
	 * 粮库作业分页查询
	 * @throws Exception
	 */
	public void findlkzuoyexinxi() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findlkzuoyexinxi(queryparam));
	}
	
	public void dellkzuoyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).dellkzuoyexinxi(param).getData());
	}
	
	public void addlkzuoyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addlkzuoyexinxi(param).getData());
	}
	
	public void uplkzuoyexinxi(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).uplkzuoyexinxi(param).getData());
	}
	
	
	
	/**
	 * 通风分页查询
	 * @throws Exception
	 */
	@Clear
	public void findTongfeng() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findTongfeng(queryparam));
	}
	
	public void delTongfeng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delTongfeng(param).getData());
	}
	
	public void addTongfeng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addTongfeng(param).getData());
	}
	
	public void upTongfeng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upTongfeng(param).getData());
	}
	
	
	/**
	 * 熏蒸分页查询
	 * @throws Exception
	 */
	@Clear
	public void findXunzheng() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findXunzheng(queryparam));
	}
	
	public void delXunzheng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delXunzheng(param).getData());
	}
	
	public void addXunzheng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addXunzheng(param).getData());
	}
	
	public void upXunzheng(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upXunzheng(param).getData());
	}
	
	
	/**
	 * 冷却分页查询
	 * @throws Exception
	 */
	@Clear
	public void findLengque() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findLengque(queryparam));
	}
	
	public void delLengque(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delLengque(param).getData());
	}
	
	public void addLengque(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addLengque(param).getData());
	}
	
	public void upLengque(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upLengque(param).getData());
	}
	
	
	/**
	 * 实时粮情查询
	 * @throws Exception
	 */
	public void findShisliangq() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findShisliangq(queryparam));
	}
	
	public void delShisliangq(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delShisliangq(param).getData());
	}
	
	public void addShisliangq(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addShisliangq(param).getData());
	}
	
	public void upShisliangq(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upShisliangq(param).getData());
	}
	
	/**
	 * 粮食质量分页查询
	 * @throws Exception
	 */
	public void findLiangshizl() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findLiangshizl(queryparam));
	}
	
	public void delLiangshizl(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delLiangshizl(param).getData());
	}
	
	public void addLiangshizl(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addLiangshizl(param).getData());
	}
	
	public void upLiangshizl(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upLiangshizl(param).getData());
	}
	
	/**
	 * 药剂追踪分页查询
	 * @throws Exception
	 */
	public void findYaojizz() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYaojizz(queryparam));
	}
	
	
	/**
	 * 查找药剂库存
	 * @throws Exception
	 */
	public void findyjkc() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		//renderJson(Duang.duang(CangchuglBo.class).findyjkc(queryparam));
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findyjkc(queryparam));
	}
	/**
	 * 查找药剂库存
	 * @throws Exception
	 */
	public void findYjckxx() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYjckxx(queryparam));
	}
	/**
	 * 查找药剂库存
	 * @throws Exception
	 */
	public void findYjckxinxi() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYjckxinxi(queryparam));
	}
	
	public void delYaojizz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYaojizz(param).getData());
	}
	
	public void addYaojizz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYaojizz(param).getData());
	}
	
	public void upYaojizz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYaojizz(param).getData());
	}
	
	
	/**
	 * 药剂出库
	 * @throws Exception
	 */
	public void findYjck() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findYjck(queryparam));
	}
	
	public void delYjck(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYjck(param).getData());
	}
	
	public void addYjck(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYjck(param).getData());
	}
	
	public void upYjck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYjck(param).getData());
	}
	
	
	/**
	 * 药剂入库
	 * @throws Exception
	 */
	public void findYjrk() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findYjrk(queryparam));
	}
	
	public void delYjrk() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYjrk(param).getData());
	}
	
	@Before(addYaojirukuValidator.class)
	public void addYjrk() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYjrk(param).getData());
	}
	
	public void upYjrk() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYjrk(param).getData());
	}
	
	
	/**
	 * 药剂损耗 --- 查删增改
	 * @throws Exception
	 */
	public void findYjsh() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYjsh(queryparam));
	}
	
	public void delYjsh(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYjsh(param).getData());
	}
	
	public void addYjsh(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYjsh(param).getData());
	}
	
	public void upYjsh(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYjsh(param).getData());
	}
	
	/**
	 * 邮件的删除
	 */
	public void delemail(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delemail(param).getData());
	}
	
	
	/**
	 * 通过qiyeid查企业备案信息
	 * 这里 先给个死的qiyeid
	 * @throws Exception 
	 */
	public void findbeianqiye() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		Page<Record> qybasq = Duang.duang(CangchuglBo.class).findbeianqiye(queryparam);
		System.out.println(qybasq);
		renderJson(qybasq);
	}
	
	/**
	 * 分页查询企业安全统计表
	 * @throws Exception
	 */
	public void findanquantj() throws Exception {
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.findanquantj(param));

	}
	
	public void delanquantj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delanquantj(param).getData());
	}
	
	public void addanquantj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addanquantj(param).getData());
	}
	
	public void upanquantj(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upanquantj(param).getData());
	}

	/**
	 * 安全生产等级
	 */
	public void queryanquandj() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryanquandj(queryparam).getList());
	}

	/**
	 * 安全生产情况
	 */
	public void queryanquanqk() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryanquanqk(queryparam).getList());
	}

	/**
	 * 分页查询安全教育视频
	 * @throws Exception
	 */
	public void findzhisjijg() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findzhisjijg(queryparam));
	}
	
	public void delzhisjijg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delzhisjijg(param).getData());
	}
	
	public void addzhisjijg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addzhisjijg(param).getData());
	}
	
	public void upzhisjijg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upzhisjijg(param).getData());
	}
	
	
	/**
	 * 分页查询安全教育试题
	 * @throws Exception
	 */
	public void findjiaoyutm() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findjiaoyutm(queryparam));
	}
	
	public void deljiaoyutm(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).deljiaoyutm(param).getData());
	}
	
	public void addjiaoyutm(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addjiaoyutm(param).getData());
	}
	
	public void upjiaoyutm(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upjiaoyutm(param).getData());
	}
	
	
	/**
	 * 分页查询企业信息
	 * @throws Exception
	 */
	public void findQiye() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=	CommondBo.getCommond();
		renderJson(icommond.findQiye(queryparam));
		
	}
	
	
	
	/**
	 * 分页查询药剂库存
	 * @throws Exception
	 */
	public void findYjkucun() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		String kdbm = getPara("kdbm");
		String xzqydm = getPara("xzqydm");
		if(kdbm != null){
			queryparam.put("kdbm", getPara("kdbm"));
		}
		if(xzqydm != null){
			queryparam.put("xzqydm", getPara("xzqydm"));
		}
		
		renderJson(Duang.duang(CangchuglBo.class).findYjkucun(queryparam));
		
	}
	
	
	
	public void delYjkucun(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYjkucun(param).getData());
	}
	
	public void addYjkucun(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		/*int a = Duang.duang(CangchuglBo.class).findrksl().get(0).get("sl");
		int b = Duang.duang(CangchuglBo.class).findcksl().get(0).get("lysl");
		int c = Duang.duang(CangchuglBo.class).findshsl().get(0).get("bssl");
		int kcsl = a-b-c;
		param.put("kcsl", kcsl);*/
		renderJson(Duang.duang(CangchuglBo.class).addYjkucun(param).getData());
	}
	
	public void upYjkucun(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYjkucun(param).getData());
	}
	
	
	/**
	 * 分页查询药剂台账
	 * @throws Exception
	 */
	public void findYjtaiz() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYjtaiz(queryparam));
	}
	
	public void delYjtaiz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delYjtaiz(param).getData());
	}
	
	public void addYjtaiz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYjtaiz(param).getData());
	}
	
	public void upYjtaiz(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upYjtaiz(param).getData());
	}
	
	
	/**
	 * 分页查询仓储设备
	 * @throws Exception
	 */
	public void findCcsb() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findCcsb(queryparam));
	}
	
	public void delCcsb(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delCcsb(param).getData());
	}
	
	public void addCcsb(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addCcsb(param).getData());
	}
	
	public void upCcsb(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upCcsb(param).getData());
	}
	
	/**
	 * 分页查询专职保管
	 * @throws Exception
	 */
	public void findZzbg() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findZzbg(queryparam));
	}
	
	public void delZzbg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delZzbg(param).getData());
	}
	
	public void addZzbg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addZzbg(param).getData());
	}
	
	public void upZzbg(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upZzbg(param).getData());
	}
	
	
	
	/**
	 * 分页查询库区情况
	 * @throws Exception
	 */
	public void findKqqk() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findKqqk(queryparam));
	}
	
	public void delkuqqk(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delkuqqk(param).getData());
	}
	
	public void addkuqqk(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addkuqqk(param).getData());
	}
	
	public void upkuqqk(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upkuqqk(param).getData());
	}
	
	
	/**
	 * 企业简介    查删增改
	 * @throws Exception
	 */
	public void findqiyejianjie() throws Exception {
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findqiyejianjie(queryparam));
	}
	
	public void delqiyejianjie(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delqiyejianjie(param).getData());
	}
	
	public void addqiyejianjie(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addqiyejianjie(param).getData());
	}
	
	public void upqiyejianjie(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upqiyejianjie(param).getData());
	}
	
	
	/**
	 * 查询企业名称
	 */
	public void findqiyemc(){
		HashMap<String, Object> param = new HashMap<>();
		param.put("xian", getPara("xian"));
		renderJson(Duang.duang(CangchuglBo.class).findqiyemc(param));
	}
	
	/**
	 * 查询库点名称
	 */
	public void findkudianmc(){
		HashMap<String, Object> param = new HashMap<>();
		param.put("qyzzjgdm", getPara("qyzzjgdm"));
		renderJson(Duang.duang(CangchuglBo.class).findkudianmc(param));
	}
	
	
	/**
	 * 查找粮食类别
	 */
	@Clear
	public void findlslb(){
		renderJson(Duang.duang(CangchuglBo.class).findlslb());
	}
	
	/**
	 * 查找粮食性质
	 */
	@Clear
	public void findlsxz(){
		renderJson(Duang.duang(CangchuglBo.class).findlsxz());
	}
	
	
	/**
	 * 查找地区
	 */
	@Clear
	public void finddiqu(){
		renderJson(Duang.duang(CangchuglBo.class).finddiqu());
	}
	
	
	/**
	 * 查找药库
	 */
	public void findyaoku() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findyaoku(queryparam));
	}
	
	
	/**
	 * 查询药剂名称和品牌
	 */
	public void findyjmcpp(){
		renderJson(Duang.duang(CangchuglBo.class).findyjmcpp());
	}
	
	
	/**
	 * 查找达标单位
	 */
	public void finddbdw(){
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).finddbdw(queryparam));
	}
	
	/**
	 * 查找视频（带参）
	 * @throws Exception 
	 */
	@Clear
	public void findshipin() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findShipin(queryparam));
	}
	

	/**
	 * 查找视频（带参）
	 * @throws Exception 
	 */
	@Clear
	public void findshipinTree() throws Exception{
		
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findShipin(queryparam).getList());
	}
	/**
	 * 查找放心粮油库点信息
	 */
	public void findFangxinly() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findFangxinly(queryparam));
	}
	/**
	 * 查找应急供应点信息
	 */
	public void findYingjidian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYingjidian(queryparam));
	}
	/**
	 * 查找应急加工点信息
	 */
	public void findYingjijiagongdian() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findYingjijiagongdian(queryparam));
	}
	/**
	 * 查找视频
	 */
	public void findShipin() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findShipin(queryparam));
	}
	/**
	 * 查找用户
	 */
	public void findUserxinxi() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findUserxinxi(queryparam));
	}
	/**
	 * 查找监测信息
	 */
	public void findfangfacaozuo() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findfangfacaozuo(queryparam));
	}
	/**
	 * 查找首页使用频率
	 */
	public void findindexweihu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findindexweihu(queryparam));
	}
	/**
	 * 查找页面方法的使用
	 */
	public void findcaidanfangfaweihu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findcaidanfangfaweihu(queryparam));
	}
	
	/**
	 * 库点下拉获取
	 */
	public void findKudian() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryKudian(param).getList());
	}
	/**
	 * 
	 */
	public void queryjylx(){
		String name = getPara("name");
		//HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).queryjylx(name).getData());
	}
	/**
	 * 增加应急储备计划信息
	 */
	public void addchenpinliangCBJH(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addchenpinliangCBJH(param).getData());
	}
	/**
	 * 删除模块使用信息
	 */
	public void delchengpinliangCBJH(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delchengpinliangCBJH(param).getData());
	}
	/**
	 * 增加模块信息
	 */
	public void addIndexweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addIndexweihu(param).getData());
	}
	
	/**
	 * 增加菜单方法使用信息
	 */
	public void addCaidanweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addCaidanweihu(param).getData());
	}
	/**
	 * 删除模块使用信息
	 */
	public void delIndexweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delIndexweihu(param).getData());
	}
	/**
	 * 删除菜单方法使用信息
	 */
	public void delCaidanweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delCaidanweihu(param).getData());
	}
	
	/**
	 * 修改模块使用信息
	 */
	public void upIndexweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upIndexweihu(param).getData());
	}
	/**
	 * 修改菜单方法使用信息
	 */
	public void upCaidanweihu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upCaidanweihu(param).getData());
	}
	
	/**
	 * 修改模块使用信息
	 */
	public void upchenpinliangCBJH(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upchenpinliangCBJH(param).getData());
	}
	
	/**
	 * 增加视频
	 */
	public void addShipin(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addShipin(param).getData());
	}


	/*public void addYaoku(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addYaoku(param).getData());
	}*/
	/**
	 * 删除视频
	 */
	public void delShipin(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delShipin(param).getData());
	}
	/**
	 * 删除放心粮油
	 */
	public void delFangxinliangyou(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delFangxinliangyou(param).getData());
	}
	/**
	 * 删除方法操作
	 */
	public void delfangfacaozuo(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delfangfacaozuo(param).getData());
	}
	/**
	 * 清空方法操作
	 */
	public void qingkongfacaozuo(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).qingkongfacaozuo(param).getData());
	}
	
	
	/**
	 * 修改视频
	 */
	public void upShipin(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upShipin(param).getData());
	}
	/**
	 * 修改密码
	 */
	public void upmima(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upmima(param).getData());
	}
	
	/**
	 * 应急小组的增删改查
	 */
	public void findxiaozu() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		ICommond icommond=CommondBo.getCommond();
		renderJson(icommond.findxiaozu(queryparam));
	}
	@Before(addXiaozuValidator.class)
	public void addxiaozu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addxiaozu(param).getData());
	}
	public void delxiaozu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delxiaozu(param).getData());
	}
	@Before(addXiaozuValidator.class)
	public void upxiaozu(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upxiaozu(param).getData());
	}
	/**
	 * 数据库表t_property的 增删改查
	 */
	public void findprop() throws Exception{
		HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).findprop(queryparam));
	}
	public void addprop(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addprop(param).getData());
	}
	public void delprop(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).delprop(param).getData());
	}
	public void upprop(){
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).upprop(param).getData());
	}
	
	/**
	 * 查找库点
	 */
	@Clear
	public void findkudian(){
		String xzqhdm = getPara("xzqhdm");
		renderJson(Duang.duang(CangchuglDao.class).findkudian(xzqhdm));
	}
	
	/**
	 * 查找企業簡介
	 */
	public void findjianjie(){
		String qyzzjgdm = getPara("qyzzjgdm");
		renderJson(Duang.duang(CangchuglBo.class).findjianjie(qyzzjgdm));
	}
	
	
	public void findQiyemc(){
		renderJson(Duang.duang(CangchuglBo.class).findQiyemc());
	}
	
	public void findKudianmc(){
		renderJson(Duang.duang(CangchuglBo.class).findKudianmc());
	}
	
	public void findBeianqiye(){
		renderJson(Duang.duang(CangchuglBo.class).findBeianqiye());
	}


	/**
	 * * author:dx
	 * date:2017/5/23
	 * time:13:36
	 * description:获取三维信息
	 * @throws Exception
	 */
	public void querySanwei() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).querySanwei(queryParam));
	}

	public void addOrUpdateSanwei() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).addOrUpdateSanwei(queryParam));
	}
	public void deleteSanweiInfo() throws Exception{
		HashMap<String, Object>   queryParam =ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(CangchuglBo.class).deleteSanweiInfo(queryParam));
	}

}
