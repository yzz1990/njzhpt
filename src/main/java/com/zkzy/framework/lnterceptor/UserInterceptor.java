package com.zkzy.framework.lnterceptor;

import com.ggy.common.utils.UserInfo;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.zkzy.framework.kit.UserKit;

public class UserInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		UserInfo userinfo = controller.getSessionAttr("userinfo");
		if (userinfo != null) {
			UserKit.buildUser(userinfo);
			controller.setAttr("userinfo", userinfo);
		}
		inv.invoke();
	}
}
