package com.zkzy.framework.kit;

import com.ggy.common.utils.UserInfo;

public class UserKit {

	public static final ThreadLocal<UserInfo> session = new ThreadLocal<UserInfo>();

	public static UserInfo currentUserInfo() {
		UserInfo s = (UserInfo) session.get();
		return s;
	}
	public static void buildUser(UserInfo currentUser) {
		session.set(currentUser);	
	}
}
