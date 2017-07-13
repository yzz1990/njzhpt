package com.zkzy.njzhpt.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zkzy.framework.kit.UserKit;
import com.zkzy.njzhpt.dao.FindPerson;
import com.zkzy.njzhpt.dao.RemoteDAO;
import com.zkzy.njzhpt.interceptor.AutoPageInterceptor;

public class SaveMailBO{
/**
 * 获取草稿箱
 */
	@Before(AutoPageInterceptor.class)
public List<Record> findcgx(Record search) {
	//UserInfo userInfo = com.zkzy.oa.common.CommonUtil.getCurrentUser();
	String sql = "select *from oa_Mailinfo a where a.fjrid = ? and a.hassend = '0' and a.isdel = '0'";
	List<Object> queryparam = new ArrayList<Object>();
	queryparam.add(UserKit.currentUserInfo().getUser().getString("id"));
	String param = "";
	String orderby = " order by createtime desc";
	if (search!=null) {
		String title = search.getStr("title");
		String quanwen = search.getStr("content");
		String d1 = search.getStr("sendtime");
		String d2 = search.getStr("sendendtime");

		if (title!=null) {
			param += " and a.title like ?";
			queryparam.add("%" + title + "%");
		}
		if (quanwen!=null) {
			param += " and a.content like ?";
			queryparam.add("%" + quanwen + "%");
		}
		if (d1!=null) {
			param += " and a.sendtime > ?";
			queryparam.add(d1);
		}
		if (d2!=null) {
			param += " and a.sendtime < ?";
			queryparam.add(d2);
		}

	}
	
	List<Record> list = Db.find(sql + param + orderby, queryparam.toArray());
	for (Record i : list) {
		List<Record> l = Db.find(
				"select *from oa_MailRecieve where mail_id = ?",
				i.getStr("id"));
		String ids = "", names = "";
		for (Record r : l) {
			ids += r.getStr("sjrid") + ",";
			names += FindPerson.findPerson(r.getStr("sjrid"))
					.get(0).getStr("realname") + ",";
			
			/*ids += r.getReceivepid() + ",";
			names += userInfobo.findUserById(r.getReceivepid())
					.getRealname() + ",";*/
		}
		i.set("sjrid", ids);
		i.set("realname",names);
	}
	return list;
}
}
