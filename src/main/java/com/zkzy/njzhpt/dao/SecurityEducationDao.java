package com.zkzy.njzhpt.dao;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator
 * Date: 2017/5/4
 * Time: 14:09
 * Description:
 */
public class SecurityEducationDao {
	public static Page<Record> findFile(HashMap<String, Object> queryparam) throws Exception {
		String secondSql;
		//sql injection
		if(queryparam.containsKey("originName")){
			secondSql=" from  njpt_knowledgeStructFile a where 1=1 and a.originName like '%"+queryparam.get("originName")+"%' ";
		}else{
			secondSql=" from  njpt_knowledgeStructFile a where 1=1";
		}
		Param p = new Param();
		p.put("and a.type = ?", "type");
		p.put("and a.uploadTime = ?", "uploadTime");
		p.put("and a.knowLedgeStructId = ?", "knowLedgeStructId");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.* ",
				secondSql + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findknowledgePoint(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		String secondSql=" from njpt_knowledgePoint a where 1=1 ";
		if(queryparam.containsKey("knowledgePointName")){
			secondSql=" from njpt_knowledgePoint a where 1=1 and a.knowledgePointName like '%"+queryparam.get("knowledgePointName")+"%' ";
		}
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.* ",
				secondSql + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findpaperTest(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.paperCreateTime = ?", "paperCreateTime");
		p.put("and b.realname = ?", "realname");
		p.put("and c.userId = ?", "userId");
		p.put("and b.realname = ?", "anserPerson");
		p.put("and c.istest = ?", "istest");
		p.put("and a.averageScore > ?", "averageScore");
		p.put("and c.score > ?", "scoreHighThan");
		p.put("and c.isstandard = ?", "isstandard");
		p.put("and a.id = ?", "id");
		String sql=" from njpt_paperTest a left join fw_user b on b.id=a.examiner left join njpt_user_batch c on c.paperId=a.id where 1=1 ";
		if(queryparam.containsKey("paperName"))
		{
			sql=" from njpt_paperTest a left join fw_user b on b.id=a.examiner left join njpt_user_batch c on c.paperId=a.id where 1=1 and a.paperName like '%"+queryparam.get("paperName")+"%' ";
		}
		if(!queryparam.containsKey("userId")&&queryparam.containsKey("istest")) {
			sql=" from njpt_user_batch c left join fw_user b on b.id=c.userId right join njpt_paperTest a on c.paperId=a.id where 1=1 ";
			if(queryparam.containsKey("paperName"))
			{
				sql=" from njpt_user_batch c left join fw_user b on b.id=c.userId right join njpt_paperTest a on c.paperId=a.id where 1=1 and a.paperName like '%"+queryparam.get("paperName")+"%' ";
			}
		}

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname,c.id as batchId,c.score as userScore,c.isstandard ",
				sql + s.getSql()+"GROUP BY b.realname,a.id,a.paperName,a.paperCreateTime,a.singleNums,a.singleScore,a.multiNums,a.multiScore,a.judgeNums,a.judgeScore,\n" +
						"a.score,a.passScore,a.examiner,a.testedNums,c.id,a.averageScore,c.score,c.isstandard",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findPaperBatch	(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.batch like ?", "batch","%%%s%%");
		p.put("and a.releaseTime = ?", "releaseTime");
		p.put("and a.paperId = ?", "paperId");
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.* ",
				" from njpt_paper_batch a where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findsubjects(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.type = ?", "type");
		p.put("and a.id = ?", "id");
		String sql=" from njpt_subjects a where 1=1 ";
		if(queryparam.containsKey("knowledgePoint"))
		{
			if(queryparam.get("knowledgePoint") instanceof List)
			{
				List<String> lists= ((List<String>)( queryparam.get("knowledgePoint")));
				sql=" from njpt_subjects a where (";
				for(int i=0;i<lists.size();i++){
					sql+="a.knowledgePoint='"+lists.get(i)+"'";
					if(i!=lists.size()-1)
					{
						sql+=" or ";
					}
				}
				sql+=")";
			}else{
				p.put("and a.knowledgePoint = ?", "knowledgePoint");
			}
		}
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.* ",
				sql + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> findPaperBatchUsers(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and a.paperBatchId = ?", "batchId");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname ",
				" from njpt_user_batch a left join fw_user b on b.id=a.userId where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	public static Page<Record> findpaperTestData(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		String secondSql=" from njpt_paperTest a left join fw_user b on b.id=a.examiner where 1=1 ";
		if(queryparam.containsKey("paperName")){
			secondSql=" from njpt_paperTest a left join fw_user b on b.id=a.examiner where 1=1 and a.paperName like '%"+queryparam.get("paperName")+"%' ";
		}
		p.put("and a.paperCreateTime = ?", "paperCreateTime");
		p.put("and b.realname = ?", "realname");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
						.valueOf(queryparam.get("page"))), Integer.valueOf(String
						.valueOf(queryparam.get("rows"))),
				"select a.*,b.realname ",
				secondSql + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
}
