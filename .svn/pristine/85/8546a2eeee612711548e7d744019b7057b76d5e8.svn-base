package com.zkzy.njzhpt.controller;

import java.util.HashMap;

import com.ggy.common.utils.ParamUtil;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.zkzy.njzhpt.bo.ZongHeBo;

public class ZongHeController extends Controller{

	
	public void index() {
		Ret ret = null;
		try {
			String method = getAttr("_url_");
			if (StrKit.notBlank(method)) {
				ret = (Ret) ZongHeBo.class.getMethod(method, HashMap.class)
						.invoke(Duang.duang(ZongHeBo.class),
								ParamUtil.getParamMap(this.getParaMap()));
				if (ret.getData().containsKey("list")) {
					renderJson(ret.getData().get("list"));
				} else {
					renderJson(ret.getData());
				}
			} else {
				render("login.html");
			}
		} catch (Exception e) {
			String message = e.getCause().getMessage();
			LogKit.error(message);
			ret = Ret.create("ret", false).put("message", message);
			renderJson(ret.getData());
		}
	}
	

	public void toHTML() {
		String url = (String) getAttr("_url_");
		render(url);
	}
	

	
	
	
	
	/**
	 * 品种等级综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findpzdjfx() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findpzdjfx(param).getData());
	}
	
	/**
	 * 自有仓容综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findzycr() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzycr(param).getData().get("list"));
	}

	/**
	 * 品种性质综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findpzxzfx() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findpzxzfx(param).getData());
	}
	
	/**
	 * 仓房状态综合分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findcfzt() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcfzt(param).getData().get("list"));
	}
	
	/**
	 * 仓房使用情况综合分析
	 * @author yzz
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void findcfsy() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcfsy(param).getData().get("list"));
	}
	
	/**
	 * 仓房建设年代综合分析
	 * @author yzz
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void findcfnd() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcfnd(param).getData().get("list"));
	}
	
	/**
	 * 寻找年度
	 * @throws Exception
	 */
	public void findyear() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findyear(param).getData().get("list"));
	}
	
	/**
	 * 全市仓房类型仓容占比  --列表
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	@Clear
	public void findcflxzcrzb() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcflxzcrzb(param).getData().get("list"));
	}
	
	/**
	 * 仓房储粮技术
	 * @throws Exception
	 */
	public void cfcljs() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).cfcljs(param).getData().get("list"));
	}
	
	/**
	 * 烘干能力统计分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findhgnl() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson((Duang.duang(ZongHeBo.class)).findhgnl(param).getData().get("list"));
	}
	
	/**
	 * 种植面积分析
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findzzmj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson((Duang.duang(ZongHeBo.class)).findzzmj(param).getData().get("list"));
	}
	
	
	/**
	 * 仓房类型统计
	 * @author yzz
	 * @throws Exception 
	 */
	public void cflxtj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).cflxtj(param).getData());
	}
	
	
	
	/**
	 * 仓房利用率
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findcflyl() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzcr(param).getData());
	}
	
	
	

	/**
	 * 库存占比
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findkczb() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findkczb(param).getData());
	}
	
	
	/**
	 * 全市仓房总仓容占比
	 * @author yzz
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public void findcfcrzb() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcfcrzb(param).getData());
	}
	
	
	
	
	

	
	
	
	
	
	
	
	/**
	 * 分区域库存统计
	 * @throws Exception
	 */
	public void findfqck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findfqck(param).getData());
	}
	
	/**
	 * 分区品种统计库存
	 * @throws Exception
	 */
	public void findpzck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findpzck(param).getData());
	}
	
	
	/**
	 * 分区性质库存统计
	 * @throws Exception
	 */
	public void findnxck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findnxck(param).getData());
	}
	
	
	/**
	 * 分区性质库存统计
	 * @throws Exception
	 */
	public void findxzck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findxzck(param).getData());
	}
	
	

	/**
	 * 分区粮食等级库存统计
	 * @throws Exception
	 */
	public void finddjck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		
		renderJson(Duang.duang(ZongHeBo.class).finddjck(param).getData());
	}
	
	/**
	 * 粮食分年限库存统计  
	 * @throws Exception
	 */
	public void findnfck() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findcfnd(param).getData());
	}
	
	/**
	 * 全市库存分析——
	 * @throws Exception
	 */
	public void findzkcfpz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this
				.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzkcfpz(param).getData());
	}
	

	/**
	 * 全市库存分析——
	 * @throws Exception
	 */
	public void findzkcpz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzkcpz(param).getData());
	}
	
	
	/**
	 * 全市库存分析——
	 * @throws Exception
	 */
	public void findzkcxz() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzkcxz(param).getData());
	}
	
	/**
	 * 全市库存分析——
	 * @throws Exception
	 */
	public void findzkcdj() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzkcdj(param).getData());
	}
	
	/**
	 * 全市库存分析——
	 * @throws Exception
	 */
	public void findzkcnx() throws Exception{
		HashMap<String, Object> param = ParamUtil.getParamMap(this.getParaMap());
		renderJson(Duang.duang(ZongHeBo.class).findzkcnx(param).getData());
	}
	
	
}
