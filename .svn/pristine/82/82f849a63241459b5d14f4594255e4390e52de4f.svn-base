package com.zkzy.njzhpt.interceptor;

import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AutoPageInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		if (inv.getArgs().length == 1 && inv.getArg(0) instanceof Map) {
			Map map = (Map) inv.getArg(0);
			if (!map.containsKey("page") && !map.containsKey("rows")) {
				map.put("page", "1");
				map.put("rows", 10000);
			}else if("-1".equals(map.get("rows"))){
				map.put("page", "1");
				map.put("rows", 10000);
			}
			inv.invoke();
		}
	}
}
