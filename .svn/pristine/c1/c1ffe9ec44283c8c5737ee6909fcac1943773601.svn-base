package com.zkzy.framework.lnterceptor;

import java.util.HashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class UserDepCacheInterceptor implements Interceptor {

	@SuppressWarnings("unchecked")
	public void intercept(Invocation inv) {
		HashMap<String, Object> map = (HashMap<String, Object>) inv.getArg(0);
		if (map.containsKey("userid")) {
			String keyid = "dep_userid_" + String.valueOf(map.get("userid"));
			Cache frameworkCache = Redis.use();
			Page<Record> c = frameworkCache.get(keyid);
			Ret ret;
			if (c == null) {
				inv.invoke();
				ret = inv.getReturnValue();
				c = ret.get("list");
				frameworkCache.set(keyid, c);
			} else {
				ret = Ret.create("list", c);
			}
			inv.setReturnValue(ret);
		} else {
			inv.invoke();
		}
	}
}
