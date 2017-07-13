package com.zkzy.framework.config;

import java.util.HashMap;
import java.util.Map;

import org.beetl.ext.jfinal.BeetlRenderFactory;
import org.beetl.ext.tag.TrimHtml;

import com.ggy.common.utils.SqlUtil;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.RedisPlugin;
import com.zkzy.framework.controller.FrameworkController;
import com.zkzy.framework.controller.IndexController;
import com.zkzy.framework.lnterceptor.JfinalSkipHandler;
import com.zkzy.framework.lnterceptor.UserInterceptor;

public class FrameworkConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants arg0) {
		// 设置页面类型为beetl,注册trim函数
		BeetlRenderFactory beetl = new BeetlRenderFactory();
		BeetlRenderFactory.groupTemplate.registerTag("trim", TrimHtml.class);
		arg0.setMainRenderFactory(beetl);
		// 设置主配置文件
		PropKit.use("frameworklocal.txt");
		// 设置调试模式
		arg0.setDevMode(PropKit.getBoolean("devMode"));

		arg0.setBaseUploadPath("D:/files/upload");

		// 10g
		arg0.setMaxPostSize(1000 * Const.DEFAULT_MAX_POST_SIZE);
	}

	@Override
	public void configHandler(Handlers arg0) {
		arg0.add(new JfinalSkipHandler());
	}

	@Override
	public void configInterceptor(Interceptors arg0) {
		arg0.add(new UserInterceptor());
	}

	@Override
	public void configPlugin(Plugins arg0) {
		SqlUtil.initDb(arg0);
		RedisPlugin bbsRedis = new RedisPlugin("njpt", "192.168.30.131");
		arg0.add(bbsRedis);
	}

	@Override
	public void configRoute(Routes arg0) {
		arg0.add("/", IndexController.class);
		arg0.add("/framework", FrameworkController.class,
				"/WEB-INF/web/framework");
		arg0.add("/ui", FrameworkController.class, "/WEB-INF/web/ui");
	}

	@Override
	public void afterJFinalStart() {
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		// 获取所有配置放到共享里面
		// sharedVars.putAll(new CourseBO().getTProperty().getColumns());
		BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
	}
}
