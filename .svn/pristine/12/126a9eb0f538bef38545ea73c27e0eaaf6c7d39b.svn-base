package com.zkzy.framework.lnterceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AuthorityInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Object userinfo = controller.getSessionAttr("userinfo");
		if (userinfo != null)
			inv.invoke();
		else
			controller.redirect("/login");
	}
}
