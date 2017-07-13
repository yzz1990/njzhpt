package com.zkzy.njzhpt.config.interfaces;

import java.util.HashMap;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public interface ICommond {
	/**
	 * 权限
	 * 数据中心中获取企业信息接口
	 * yzz  2017-02-25
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiyexinxi (HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 数据中心中获取区县信息接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public  Page<Record> findDiqu (HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 数据中心中获取区县年度信息接口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findDiquniandupx(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 储备粮计划数据信息接口
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> chubeilianghuizongb(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 储备粮计划数据合计
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Ret chubeilianghuizongheji(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 轮换管理数据合计
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> queryLunHuanShenQing(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 权限
	 * 收储可视化库点汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryshishi(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 收储可视化库点汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryxingzhi(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 权限
	 * 收储可视化库点库存汇总
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret querykchz(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 权限
	 * 业务管理获取企业信息
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiye(HashMap<String, Object> queryparam) throws Exception;
	/**
	 * 权限
	 * 成品粮计划数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> selectCPLCBJH(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 质量管理数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> selectZLXQ(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 轮换统计数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> selectlunhuanHZ(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 企业信息提交审核
	 * yzz  2017-03-01
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Ret qyTjSh(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 企业信息提交审核——区县粮食局审核不通过
	 * yzz  2017-03-01
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Ret qyTjShBtg(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 企业备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findQiyebeian(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 熏蒸备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findXunzheng(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 药剂备案数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYaoji(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 药库数据
	 * njl  2017-02-28
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findYaoku (HashMap<String, Object> param) throws Exception;
	
	/**
	 * 权限
	 * 遍历库点质量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret querykudian(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 权限
	 * 遍历夏粮预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryxialiangyuce(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 权限
	 * 遍历秋粮预测分期上报
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret queryqiuliangyuce(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 安全生产初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findanquantj(HashMap<String, Object> param) throws Exception;
	
	/**
	 * 权限
	 * 遍历储备粮计划
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Ret shengchenghuizongb(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 人口信息初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> queryrenkou(HashMap<String, Object> map) throws Exception;
	/**
	 * 权限
	 * 流程信息初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findshenpiliucheng(HashMap<String, Object> param) throws Exception;
	/**
	 * 权限
	 * 应急小组初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Page<Record> findxiaozu(HashMap<String, Object> param) throws Exception;
	public Ret chakanxiangxich(HashMap<String, Object> queryparam)  throws Exception;
	public Ret findzongjihua(HashMap<String, Object> queryparam) throws Exception;
	public Ret findzongkucun(HashMap<String, Object> queryparam) throws Exception;
	public Ret findzongjihuaxian(HashMap<String, Object> queryparam) throws Exception;
	public Ret findzongkucunxian(HashMap<String, Object> queryparam) throws Exception;
	public Ret fenxingzhi(HashMap<String, Object> map) throws Exception;
	public Ret cbljhrenwu(HashMap<String, Object> queryparam) throws Exception;
	public Ret chubeiliangrenwuheji(HashMap<String, Object> queryparam) throws Exception;
	public Ret cblswkc(HashMap<String, Object> queryparam) throws Exception;
	public Ret cblswkck(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakankudian(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakankudiankc(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakankudian2(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakankudian2kc(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakancangfang(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakancangfangkc(HashMap<String, Object> queryparam) throws Exception;
	public Page<Record> findYjck(HashMap<String, Object> param) throws Exception;
	public Ret shiwukucunheji(HashMap<String, Object> queryparam) throws Exception;
	public Ret shiwukucunhejixinxi(HashMap<String, Object> queryparam) throws Exception;
	public Page<Record> findyjkc(HashMap<String, Object> param) throws Exception;
	public Page<Record> findkongping(HashMap<String, Object> queryparam) throws Exception;
	public Page<Record> findYjrk(HashMap<String, Object> param) throws Exception;
	public Page<Record> selectfeiksh(HashMap<String, Object> param) throws Exception;
	public Ret cbljh(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakanecbljhkd(HashMap<String, Object> queryparam) throws Exception;
	public Ret chakancbljhcf(HashMap<String, Object> queryparam) throws Exception;
	public Ret cbljhheji(HashMap<String, Object> queryparam) throws Exception;
	public Ret ckcbljherjiqiye(HashMap<String, Object> queryparam) throws Exception;
	public Ret findcompany(HashMap<String, Object> queryparam) throws Exception;
	public Ret findcompanyy(HashMap<String, Object> queryparam) throws Exception;
	public Ret cblglzxShenHe(HashMap<String, Object> queryparam) throws Exception;
	public Ret finderjiqiyekd(HashMap<String, Object> queryparam) throws Exception;
	public Ret cbljhniandurenwu(HashMap<String, Object> queryparam) throws Exception;
	public Ret cbljhnianduheji(HashMap<String, Object> queryparam) throws Exception;
	public Page<Record> selectQuYu(HashMap<String, Object> param) throws Exception;
	public Page<Record> kucunliebiao(HashMap<String, Object> param) throws Exception;
	public Ret kucunheji(HashMap<String, Object> param) throws Exception;
	public Page<Record> findxzzy(HashMap<String, Object> queryparam);
	public Page<Record> findlcpw(HashMap<String, Object> param)throws Exception;
	public Ret lcpwheji(HashMap<String, Object> param)throws Exception;
	public Ret findallcompany(HashMap<String, Object> queryparam) throws Exception;

	
}
