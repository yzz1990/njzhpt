package com.zkzy.njzhpt.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;

public class ExportExcel {
	private final static String UPLOAD_ROOT = "uploadfile";
	protected final static Log log = LogFactory.getLog(ExportExcel.class);

	private Logger logger = Logger.getLogger(ExportExcel.class);

	/**
	 * 企业记录导出工能 修改日期：2016-09-19
	 */
	@SuppressWarnings("unused")
	public static String exportExcel(String[] title, JSONArray list, String text) {
		title = new String[] { "企业名称", "用户名", "密码", "状态" };
		// 准备设置excel工作表的标题
		String fileName = null;
		WritableWorkbook wwb = null;
		OutputStream os = null;
		try {
			// 输出的excel的路径
			String path = ExportExcel.class.getResource("/").toURI().getPath();
			LogKit.info(path);
			File dirPath = new File(getSavePath());
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			// String uuid = UUID.randomUUID().toString();
			// fileName = uuid + ".xls";
			fileName = "库点信息记录.xls";
			// 新建立一个jxl文件,即在e盘下生成testJXL.xls
			os = new FileOutputStream(getSavePath() + fileName);
			// 创建Excel工作薄
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet("导出", 0);
			// 去掉整个sheet中的网格线
			sheet.getSettings().setShowGridLines(false);
			Label label;
			// 设置行高
			sheet.setRowView(0, 600);
			// 合并列
			sheet.mergeCells(0, 0, 4, 0);
			label = new Label(0, 0, "企业信息记录", getOneCellStyle());
			sheet.addCell(label);
			sheet.setRowView(1, 400);
			// sheet.setColumnView(1, 200);
			sheet.mergeCells(0, 1, 5, 1);
			label = new Label(0, 1, text.replaceAll("\\s+", ""),
					getTwotitleCellStyle());
			sheet.addCell(label);
			// 将定义好的单元格添加到工作表中
			for (int i = 0; i < title.length; i++) {
				if (i == 2) {
					sheet.setColumnView(i, 28);
				} else if (i == (title.length - 2)) {
					sheet.setColumnView(i, 190);
				} else {
					sheet.setColumnView(i, 15);
				}
				// 设置第x列宽度
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 2, title[i], getHeaderCellStyle());
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			if (list != null) {
				for (int j = 0; j < list.size(); j++) {
					JSONObject obj = list.getJSONObject(j).getJSONObject(
							"columns");

					label = new Label(0, j + 3, obj.getString("deptname"),
							getContentCellStyle());
					sheet.addCell(label);
					label = new Label(1, j + 3, obj.getString("loginname"),
							getContentCellStyle());
					sheet.addCell(label);
					label = new Label(2, j + 3, obj.getString("password"),
							getContentCellStyle());
					sheet.addCell(label);
					String status = obj.getString("canuse");
					if ("0".equals(status)) {
						label = new Label(3, j + 3, "待审核",
								getContentCellStyle());
						sheet.addCell(label);
					} else if ("1".equals(status)) {
						label = new Label(3, j + 3, "审核通过",
								getContentCellStyle());
						sheet.addCell(label);
					} else if ("2".equals(status)) {
						label = new Label(3, j + 3, "审核不通过",
								getContentCellStyle());
						sheet.addCell(label);
					}
				}
			}
			// 写入数据
			wwb.write();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			// 关闭文件
			try {
				wwb.close();
				os.close();
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return fileName;
	}

	private static String getSavePath() throws URISyntaxException {

		String str = PathKit.class.getClassLoader().getResource("").toURI()
				.getPath()
				+ UPLOAD_ROOT + "/" + "excelfile" + "/";
		log.info(str);
		return str;
	}

	/**
	 * 第一行样式的设定
	 */
	public static WritableCellFormat getOneCellStyle() {
		WritableCellFormat wc = new WritableCellFormat();
		try {
			// 设置居中
			wc.setAlignment(Alignment.CENTRE);
			// 设置边框线
			wc.setBorder(Border.LEFT, BorderLineStyle.THIN);
			// 设置字体
			jxl.write.WritableFont font = new jxl.write.WritableFont(
					WritableFont.createFont("宋体"), 20, WritableFont.BOLD,
					false, UnderlineStyle.NO_UNDERLINE);
			wc.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	/**
	 * 单元格头部样式的设定
	 */
	public static WritableCellFormat getHeaderCellStyle() {
		WritableCellFormat wcc = new WritableCellFormat();
		try {
			// 设置居中
			wcc.setAlignment(Alignment.CENTRE);
			// 设置边框线
			wcc.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置字体
			jxl.write.WritableFont fontc = new jxl.write.WritableFont(
					WritableFont.createFont("宋体"), 12, WritableFont.BOLD,
					false, UnderlineStyle.NO_UNDERLINE);
			wcc.setFont(fontc);
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return wcc;
	}

	/**
	 * 单元格内容样式的设定
	 */
	public static WritableCellFormat getContentCellStyle() {
		WritableCellFormat wcc = new WritableCellFormat();
		try {
			// 设置居中
			wcc.setAlignment(Alignment.LEFT);
			// 设置边框线
			wcc.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 设置字体
			jxl.write.WritableFont fontc = new jxl.write.WritableFont(
					WritableFont.createFont("宋体"), 12);
			wcc.setFont(fontc);
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return wcc;
	}

	/** 
     * 
     */
	public static WritableCellFormat getTwotitleCellStyle() {
		WritableCellFormat wcc = new WritableCellFormat();
		try {
			// 设置居中
			wcc.setAlignment(Alignment.CENTRE);
			// 设置边框线
			// wcc.setBorder(Border.LEFT, BorderLineStyle.THIN);
			// 设置字体
			jxl.write.WritableFont fontc = new jxl.write.WritableFont(
					WritableFont.createFont("宋体"), 11);
			wcc.setFont(fontc);
		} catch (WriteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return wcc;
	}
}
