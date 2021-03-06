package com.zkzy.njzhpt.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ggy.common.utils.ParamUtil;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.zkzy.njzhpt.bo.ExcelBo;
import com.zkzy.njzhpt.util.DocUtil;

public class ExcelController extends Controller{
	/**
	 * 粮食局 上传企业信息
	 * 
	 * @throws Exception
	 */
	
	
	/*public void uploadFile() throws IOException {
		UploadFile uploadFile = this.getFile();

		File file = uploadFile.getFile();
		String liucid = "08209f9061c64bf4ab8ac30509148b9d";
		String filepath = file.getPath();
		String filename = file.getName();
		// 更新jxcxcanshu表中的文件地址
		Duang.duang(ExcelBo.class).upCanshu(filepath, filename, liucid);
		// 保存excel
		renderJson(Duang.duang(ExcelBo.class).saveExcel(liucid).getData());
	}
	
	public void uploadqiuFile() throws IOException {
		UploadFile uploadFile = this.getFile();

		File file = uploadFile.getFile();
		String liucid = "3c0e58df7c3e4aff93c84ed3cdc8e1da";
		String filepath = file.getPath();
		String filename = file.getName();
		// 更新jxcxcanshu表中的文件地址
		Duang.duang(ExcelBo.class).upCanshu(filepath, filename, liucid);
		// 保存excel
		renderJson(Duang.duang(ExcelBo.class).saveExcel(liucid).getData());
	}*/
	
	//上传轮换申请行文()
	public void uploadlhsqxwFile() throws IOException {
		UploadFile uploadFile = this.getFile();
		File file = uploadFile.getFile();
		String filepath = file.getPath();
		String filename=file.getName();
		Ret ret= Ret.create("scfilename", filename).put("filepath","/upload"+filepath.split("upload")[1]);
		// 更新njpt_lunhuanshenqingb表中的文件地址
		renderJson(ret.getData());
	}
	//上传轮换批准行文(前台传回id)
		public void uploadlhpzxwFile() throws IOException {
			UploadFile uploadFile = this.getFile();
			File file = uploadFile.getFile();
			String filepath = file.getPath();
			String filename=file.getName();
			// 更新njpt_lunhuanshenqingb表中的文件地址
			Ret ret= Ret.create("scfilename", filename).put("filepath","/upload"+filepath.split("upload")[1]);
			
			renderJson(ret.getData());
		}
	//上传确认申请行文
		public void uploadqrsqxwFile() throws IOException {
			UploadFile uploadFile = this.getFile();
			String id =getPara("id");
			File file = uploadFile.getFile();
			String filepath = file.getPath();
			String filename=file.getName();
			// 更新njpt_lunhuanshenqingb表中的文件地址
			Ret ret= Ret.create("scfilename", filename).put("filepath","/upload/"+filename);
			renderJson(ret.getData());
		}
	//上传确认通知行文
		public void uploadqrtzxwFile() throws IOException {
			UploadFile uploadFile = this.getFile();
			String id =getPara("id");
			File file = uploadFile.getFile();
			String filepath = file.getPath();
			String filename=file.getName();
			// 更新njpt_lunhuanshenqingb表中的文件地址
			Ret ret= Ret.create("scfilename", filename).put("filepath","/upload"+filepath.split("upload")[1]);
			renderJson(ret.getData());
		}
		/**
		 * 预览轮换申请报告
		 * @throws IOException
		 */
		public void yulanlhsq() throws IOException {
			HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
			List list=new ArrayList();
			String ndfpzkclist=(String) param.get("ndfpzkclist");
			String[] fpzkc=ndfpzkclist.split("、");
			for (int i = 0; i < fpzkc.length; i++) {
				if(i==(fpzkc.length-1)){
					list.add(fpzkc[i]);
				}else {
					list.add(fpzkc[i]+"、");
				}
			}
			param.put("ndfpzkclist",list);
			
			DocUtil docUtil=new DocUtil();
			double dou=docUtil.createDoc(param, "lhsq", "D:/upload/");
			renderJson( new Ret().put("ret", true).put("docname",dou+".doc").getData());
			//File file=new File("D:/upload/43.47807070352761.doc");
			//renderFile(file);
		}
		
		/**
		 * 预览轮入申请报告
		 * @throws IOException
		 */
		public void yulanlrsq() throws IOException {
			HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
			List list=new ArrayList();
			String cfbmlrs=(String) param.get("cfbmlrs");
			String[] cfbmlr=cfbmlrs.split(",");
			for (int i = 0; i < cfbmlr.length; i++) {
				list.add(cfbmlr[i]);
			}
			param.put("cfbmlr",list);
			
			DocUtil docUtil=new DocUtil();
			double dou=docUtil.createDoc(param, "lrsq", "D:/upload/");
			renderJson( new Ret().put("ret", true).put("docname",dou+".doc").getData());
			//File file=new File("D:/upload/43.47807070352761.doc");
			//renderFile(file);
		}
		
		/**
		 * 预览易库点轮入申请报告
		 * @throws IOException
		 */
		public void yulanbgkd() throws IOException {
			HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
			List list=new ArrayList();
			String cfbmlrs=(String) param.get("kdmccfsl");
			String[] cfbmlr=cfbmlrs.split(",");
			for (int i = 0; i < cfbmlr.length; i++) {
				list.add(cfbmlr[i]);
			}
			param.put("kdmccfsl",list);
			
			DocUtil docUtil=new DocUtil();
			double dou=docUtil.createDoc(param, "bgkd", "D:/upload/");
			renderJson( new Ret().put("ret", true).put("docname",dou+".doc").getData());
			//File file=new File("D:/upload/43.47807070352761.doc");
			//renderFile(file);
		}
		
		/**
		 * 预览易品种轮入申请报告
		 * @throws IOException
		 */
		public void yulanbgpz() throws IOException {
			HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
			DocUtil docUtil=new DocUtil();
			double dou=docUtil.createDoc(param, "bgpz", "D:/upload/");
			renderJson( new Ret().put("ret", true).put("docname",dou+".doc").getData());
			//File file=new File("D:/upload/43.47807070352761.doc");
			//renderFile(file);
		}
		
		/**
		 * 预览轮换批准报告
		 * @throws IOException
		 */
		public void yulanlhpz() throws IOException {
			HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
			List list=new ArrayList();
			String ndfpzkclist=(String) param.get("ndfpzkclist");
			String[] fpzkc=ndfpzkclist.split("、");
			for (int i = 0; i < fpzkc.length; i++) {
				if(i==(fpzkc.length-1)){
					list.add(fpzkc[i]);
				}else {
					list.add(fpzkc[i]+"、");
				}
			}
			param.put("ndfpzkclist",list);
			DocUtil docUtil=new DocUtil();
			double dou=docUtil.createDoc(param, "lhpz", "D:/upload/");
			renderJson( new Ret().put("ret", true).put("docname",dou+".doc").getData());
			//renderFile("D:/upload/"+dou+".doc");
		}
		
		/**
		 * 下载报告
		 */
		public void getDocFile(){
			   HashMap<String, Object> queryparam = ParamUtil.getParamMap(this.getParaMap());	
			   File file=new File((String)queryparam.get("docpath"));
			   renderFile(file);
		   }
	
}
