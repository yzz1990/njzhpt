package com.zkzy.njzhpt.dao;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import com.ggy.common.utils.Param;
import com.ggy.common.utils.SqlAndParam;
import com.ggy.common.utils.SqlUtil;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class JibenDAO {

	public static Page<Record> queryDwProperty(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from dw_Property where 1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));

		return page;
	}

	public static Page<Record> queryPlan(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and entcode = ?", "qyzzjgdm");
		p.put("and orgcode = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from plan where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));

		return page;
	}
	public static Page<Record> queryLqqipaoJcInfo(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and entcode = ?", "qyzzjgdm");
		p.put("and orgcode = ?", "kdbm");
		p.put("and house = ?", "vCode");
		p.put("and time = ?", "time");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from graintemp where 1=1 " + s.getSql()+" order by house ASC, time desc",
				s.getParam().toArray(new Object[0]));

		return page;
	}
	

	// 获取已经绑定设备
	public static Page<Record> queryBindSheBei(
			HashMap<String, Object> queryparam) throws Exception {

		Param p = new Param();
		p.put("and a.id = ?", "id");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select c.id,c.vCode,c.vName",
						"from t_WareHouse a "
								+ "inner join t_wareHouseDeviceBridge b on a.id = b.t_WareHouse_id "
								+ "inner join t_DevicePara c on b.t_DevicePara_id = c.id "
								+ s.getSql(),
						s.getParam().toArray(new Object[0]));

		return page;
	}

	public static Page<Record> queryAllSheBei(HashMap<String, Object> queryparam)
			throws Exception {

		Param p = new Param();

		SqlAndParam s = SqlUtil.buildSql(p, queryparam);

		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select *", "from t_DevicePara where 1=1" + s.getSql()
								+ "order by vCode",
						s.getParam().toArray(new Object[0]));

		return page;
	}

	public static Page<Record> queryTGrainSituation(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from t_GrainSituation where 1=1" + s.getSql()
						+ "order by id", s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryLQGrainSituation(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and vWareHouseCodeAccess = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", " from lq_GrainSituation where 1=1 " + s.getSql()
						+ " order by id", s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryLQWareHouseMeasure(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and WareHouseCode = ?", "WareHouseCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from lq_WareHouseMeasure where 1=1" + s.getSql()
						+ "order by id", s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryShipinDAO(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and name like ?", "name", "%%%s%%");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from t_ShiPing where 1=1" + s.getSql()
						+ "order by id", s.getParam().toArray(new Object[0]));

		return page;
	}

	/**
	 * 性质
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTGrainProperties(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and vCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.use("szlk")
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select *",
						"from t_GrainProperties where 1=1" + s.getSql()
								+ "order by vCode",
						s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 仓房
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTWareHouse(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and vWareHouseCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", " from t_WareHouse where 1=1 " + s.getSql()
						+ " order by vWareHouseCode ",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryKcCurrentStock(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		p.put("and vWareHouseCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from kc_CurrentStock where 1=1" + s.getSql()
						+ "order by vWareHouseCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 类别
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTGrainType(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select *", "from t_GrainType where 1=1" + s.getSql()
								+ "order by vCode",
						s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 品种
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTGrainSubType(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and vCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_GrainSubType where 1=1" + s.getSql()
						+ "order by vGrainTypeCode,vCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 包装
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTPackage(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_Package where 1=1" + s.getSql() + "order by vCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 等级
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTGrainLevel(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and vCode = ?", "vCode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_GrainLevel where 1=1" + s.getSql()
						+ "order by vLevelId",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 人员
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTPersonInfo(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_PersonInfo where 1=1" + s.getSql() + "order by vCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	public static Page<Record> queryTGrainPoint(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_GrainPoint where 1=1" + s.getSql() + "order by vCode",
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 视频
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryShipin(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and id = ?", "id");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from sp_shiping where 1=1" + s.getSql()
						+ "order by id", s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 仓容,目前容量
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static Page<Record> queryCangRongInfo( HashMap<String, Object>
	 * queryparam) throws Exception { Param p = new Param();
	 * p.put("and ck.kdmc = ?", "kdmc"); p.put("and ck.kdbm = ?", "kdbm");
	 * p.put("and ck.qyzzjgdm = ?", "qyzzjgdm"); SqlAndParam s =
	 * SqlUtil.buildSql(p, queryparam); Page<Record> page = Db .paginate(
	 * Integer.valueOf(String.valueOf(queryparam.get("page"))),
	 * Integer.valueOf(String.valueOf(queryparam.get("rows"))),
	 * "SELECT ck.vWareHouseCode AS code,ck.vWareHouseName AS name,SUM(cr.dmStock) AS sum ,ck.vCapacity AS zcr "
	 * , "FROM t_WareHouse ck " +
	 * "LEFT JOIN kc_CurrentStock cr ON cr.vWareHouseCode=ck.vWareHouseCode and  ck.qyzzjgdm=cr.qyzzjgdm and ck.kdbm=cr.kdbm  where 1=1"
	 * + s.getSql() +
	 * "GROUP BY ck.vWareHouseCode,ck.vCapacity,ck.vWareHouseName " +
	 * "ORDER BY ck.vWareHouseCode", s.getParam().toArray(new Object[0]));
	 * return page; }
	 */

	/**
	 * 新 仓容,目前容量
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryOnStorage(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and house = ?", "house");
		p.put("and orgcode = ?", "kdbm");
		p.put("and entcode = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select * ", " FROM onstorage " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	
	/**
	 * 新 出入库记录
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querySainout(HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and house = ?", "vWareHouseCode");
		p.put("and orgcode = ?", "kdbm");
		p.put("and entcode = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", " from sainout	e where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}
	
	

	/**
	 * 新 库存和
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> querySumOnStorage(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and storehouse = ?", "storehouse");
		p.put("and house = ?", "vWareHouseCode");
		p.put("and orgcode = ?", "kdbm");
		p.put("and entcode = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("grainplat").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select * ", "from onstorage where 1=1 " + s.getSql(),
				s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 新 获取仓房信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryWareHouse(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and vWareHouseCode = ?", "vWareHouseCode");
		p.put("and kdbm = ?", "kdbm");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select * ",
				"from t_WareHouse where 1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		return page;
	}

	public static List<Record> queryHouse(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and kdbm = ?", "kdbm");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and ajbh = ?", "vcode");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		List<Record> page = Db
				.use("grainplat")
				.find("select * from house where 1=1 " + s.getSql()
						+ " order by ajbh", s.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 仓库气体
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static List<Record> queryCangKuQT(Object code, Object qyzzjgdm,
			Object kdbm) throws Exception {
		List<Record> list = Db
				.find("select c.vDeviceKind AS qt,d.fValue AS value,c.vUnit AS unit,c.warn as bjz "
						+ "from t_WareHouse a ,qt_wareHouseDeviceBridge b,qt_DevicePara c,qt_SensorData d "
						+ " where b.t_WareHouse_id = RIGHT(a.vWareHouseCode,2)and a.kdbm=b.kdbm  and a.qyzzjgdm=b.qyzzjgdm  and c.vCode = b.t_DevicePara_id and d.vDeviceCode = c.vCode and a.vWareHouseCode = ? and a.qyzzjgdm = ?  and a.kdbm = ? ",
						code, qyzzjgdm, kdbm);
		return list;
	}

	/**
	 * 仓容/仓容/粮食信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Record queryCangKuInfo(HashMap<String, Object> queryparam,
			String code) throws Exception {
		String sql = "select * from kc_CurrentStock e "
				+ "left join tz_GrainProperties b on e.vGrainPropertyCode = b.vCode "
				+ "left join tz_GrainType c on e.vGrainSubTypeCode = c.vCode "
				// + "left join tz_GrainLevel d on d.vCode = e.vLevelCode "
				+ "where e.vWareHouseCode = ? and e.qyzzjgdm = ? and e.kdbm = ?";
		Record record = Db.findFirst(sql, code, queryparam.get("qyzzjgdm")
				.toString(), queryparam.get("kdbm").toString());
		return record;
	}

	/**
	 * 查询三维粮情的基本信息
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */

	public static Page<Record> queryLiangqingInfo(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put(" and a.qyzzjgdm = ?", "qyzzjgdm");
		p.put(" and a.kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"SELECT a.* ",
						"from t_WareHouse a,dw_Property b where a.qyzzjgdm=b.qyzzjgdm and a.kdbm=b.kdbm and b.propname='cfpoint_'+a.vWareHouseCode "
								+ s.getSql(),
						s.getParam().toArray(new Object[0]));
		List<Record> records = page.getList();
		if (records.size() > 0) {
			for (Record record : records) {
				Record propertys = Db
						.findFirst(
								"select * from dw_Property where propname = ? and qyzzjgdm = ? and kdbm = ?",
								"cfpoint_" + record.get("vWareHouseCode"),
								queryparam.get("qyzzjgdm"),
								queryparam.get("kdbm"));
				if (propertys != null) {
					record.set("propvalue", propertys.getStr("propvalue"));
				} else {

					record.set("propvalue", "0px,0px");
				}
			}
		}
		return page;
	}

	/**
	 * 
	 * @param queryparam
	 * @throws Exception
	 */
	public static List<Record> queryliangqing(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", "from t_WareHouse order by id",
				s.getParam().toArray(new Object[0]));

		List<Record> records = page.getList();
		if (records.size() > 0) {
			for (Record record : records) {
				record.set("linhuaqing", "");
				record.set("eryanghuatan", "");
				record.set("yangqi", "");
				record.set("wendu", "");
				record.set("shidu", "");
				record.set("uptime", "");
				String wareHouseCode = record.getStr("vWareHouseCode");
				List<Record> devices = Db.use("szlk").find(
						"select * from t_wareHouseDeviceBridge where t_WareHouse_id="
								+ record.getInt("id"));

				record.set("vName", record.getStr("vWareHouseName"));
				for (Record device : devices) {
					// 读取出设备的数值
					Integer devivePare_id = device.getInt("t_DevicePara_id");
					Record devivePares = Db.use("szlk").findById(
							"t_DevicePara", "id", devivePare_id);
					String vCode = devivePares.getStr("vCode"); // 设备code
					String deviceKind = devivePares.getStr("vDeviceKind");// 气体类型

					Record deviceValue = Db.use("szlk").findById(
							"t_SensorData", "vDeviceCode", vCode);

					if (deviceValue != null) {
						record.set("uptime", deviceValue.getDate("vUptime"));
						if (!StrKit.isBlank(deviceKind)) {
							if (deviceKind.equals("磷化氢")) {
								record.set("linhuaqing",
										deviceValue.getDouble("fValue"));
							} else if (deviceKind.equals("二氧化碳")) {
								record.set("eryanghuatan",
										deviceValue.getDouble("fValue"));
							} else if (deviceKind.equals("氧气")) {
								record.set("yangqi",
										deviceValue.getDouble("fValue"));
							}
						}
					}
				}
				// 读取出最新的仓内温度和二氧化碳
				// 首先查找在粮情软件中的仓号
				Record guanlian = Db
						.use("szlk")
						.findFirst(
								"select * from t_GrainSituation where vWareHouseCodeAccess = ?",
								wareHouseCode);
				if (guanlian != null) {
					// 根据粮情软件中的仓号获取最新温湿度数据
					Record wenshidu = Db
							.use("szlk")
							.findFirst(
									"select * from t_WareHouseMeasure where WareHouseCode = ?",
									guanlian.getStr("vWareHouseCode"));
					if (wenshidu != null) {
						record.set("wendu",
								wenshidu.get("WareHouseTemperature")).set(
								"shidu", wenshidu.get("WareHouseHumidity"));
					} else {
						record.set("wendu", "").set("shidu", "");
					}
				} else {
					record.set("wendu", "").set("shidu", "");
				}
			}
		}

		return records;
	}

	/**
	 * 三维粮情获取点位数据--yzz
	 * 
	 * @param queryparam
	 * @throws Exception
	 */
	public static Page<Record> queryLiangqingpoint(
			HashMap<String, Object> queryparam) throws Exception {

		if (!StrKit.isBlank((String) queryparam.get("datetime"))) {
			queryparam.put(
					"datetime",
					URLDecoder
							.decode((String) queryparam.get("datetime"),
									"utf-8").replace(".0000000", "")
							.substring(0, 19));
		}
		Param p = new Param();
		p.put("and vWareHouseCode = ?", "orderno");
		p.put("and datetime = ?", "datetime");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				" from lq_WareHouseLayerDetail where 1=1" + s.getSql(), s
						.getParam().toArray(new Object[0]));
		return page;
	}

	/**
	 * 处理时间方法 去除月日中的0
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getDate(String dateStr) {
		String[] datearr = dateStr.split("-");
		if (datearr[1].startsWith("0")) {
			datearr[1] = datearr[1].replace("0", "");
		} else if (datearr[2].startsWith("0")) {
			datearr[2] = datearr[2].replace("0", "");
		}
		return datearr[0] + "-" + datearr[1] + "-" + datearr[2];
	}

	/**
	 * region 三维粮情获取点位数据--yzz
	 * 
	 * @param queryparam
	 * @throws Exception
	 */
	@Before(Tx.class)
	public static List<Record> querywenshi(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and b.vWareHouseCodeAccess = ?", "vCode");
		p.put("and CONVERT(varchar, a.datetime, 120 ) = ?", "time");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select a.*,b.iRows as iRows,b.iCols as iCols,b.iFloors as iFloors,b.bSort as bSort",
						" from lq_WareHouseMeasure a inner join lq_GrainSituation b  on a.WareHouseCode=b.vWareHouseCode where 1=1 "
								+ s.getSql() + " ORDER BY datetime DESC", s
								.getParam().toArray(new Object[0]));
		List<Record> records = page.getList();
		for (Record record : records) {
			Integer rows = record.getInt("iRows");// 行数
			Integer cols = record.getInt("iCols");// 列数
			Integer floors = record.getInt("iFloors");// 层数
			// Integer sorts = record.getInt("bSort");// 排列方式
			record.set("rows", rows);
			record.set("cols", cols);
			record.set("floors", floors);
			// record.set("sorts", sorts);

		}
		return records;
	}

	public static String getspcj() {
		Record r = Db.use("szlk").findById("t_Property", "propname", "spcj");
		String changj = r.getStr("propvalue");
		return changj;
	}

	/**
	 * 三维粮情根据年月日获取时分秒列表--yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryhs(HashMap<String, Object> queryparam)
			throws Exception {
		Record vCodes;
		String vWareHouseCode;
		if (!StrKit.isBlank(queryparam.get("vCode").toString())) {
			vCodes = Db
					.findFirst(
							"select * from  lq_GrainSituation where vWareHouseCodeAccess=? and qyzzjgdm=? and kdbm=?",
							queryparam.get("vCode"),
							queryparam.get("qyzzjgdm"), queryparam.get("kdbm"));
			if (vCodes != null) {
				vWareHouseCode = vCodes.getStr("vWareHouseCode");
				queryparam.put("vCode", vWareHouseCode);
			}
		}
		Param p = new Param();
		p.put("and WareHouseCode = ?", "vCode");
		p.put("and datetime like ?", "datetime", "%s%%");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *", " from lq_WareHouseMeasure where 1=1" + s.getSql()
						+ " order BY datetime DESC",
				s.getParam().toArray(new Object[0]));
		List<Record> records = page.getList();
		if (records.size() > 0) {
			for (Record record : records) {
				String datetime = record.getDate("datetime").toString()
						.substring(0, 19);
				record.set("datemms", datetime.trim());
			}
		}
		return page;
	}

	/**
	 * 气体熏蒸泄露--yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */

	public static Page<Record> qitixielou(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from qt_DevicePara where vIn_Out = '仓外气体' " + s.getSql(), s
						.getParam().toArray(new Object[0]));
		List<Record> records = page.getList();
		for (Record record : records) {
			String vCode = record.getStr("vCode");
			Record Property = getLkTProperty(vCode, queryparam.get("qyzzjgdm")
					.toString(), queryparam.get("kdbm").toString());
			if (Property != null) {
				record.set("point", Property.get("propvalue"));
			} else {
				record.set("point", "0,0");
			}
		}

		return page;

	}

	public static Record getLkTProperty(String propname, String qyzzjgdm,
			String kdbm) {
		Record property = Db
				.findFirst(
						"select * from dw_Property where propname = ? and qyzzjgdm = ? and kdbm = ?",
						propname, qyzzjgdm, kdbm);
		return property;
	}

	/**
	 * 气体泄露气泡内容--yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryQtxlInfo(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		p.put("and vCode = ?", "vCode");
		p.put("and qyzzjgdm = ?", "qyzzjgdm");
		p.put("and kdbm = ?", "kdbm");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select *",
				"from qt_DevicePara where 1=1 " + s.getSql(), s.getParam()
						.toArray(new Object[0]));
		List<Record> records = page.getList();
		if (records.size() > 0) {
			for (Record record : records) {
				String vCode = record.getStr("vCode");
				Record DeviceCode = Db.findById("qt_SensorData", "vDeviceCode",
						vCode);
				if (DeviceCode != null) {
					if (DeviceCode.getDouble("fValue") != null) {
						record.set("fValue", DeviceCode.getDouble("fValue"));
					}
					if (DeviceCode.getDate("vUptime") != null) {
						record.set("vUptime", DeviceCode.getDate("vUptime"));
					}
				} else {
					record.set("fValue", 0.0);
					record.set("vUptime", "");
				}

			}
		}

		return page;
	}

	/**
	 * 智能仓窗控制-yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */

	public static Page<Record> queryCc(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select c.id,c.vWareHouseCode,max(c.vWareHouseName) as vWareHouseName ",
						"from t_DevicePara a,t_WareHouseDeviceBridge b,t_WareHouse c where a.vDeviceKind = '通风窗' and a.id = b.t_DevicePara_id and b.t_WareHouse_id = c.id"
								+ s.getSql()
								+ " group by c.id,c.vWareHouseCode", s
								.getParam().toArray(new Object[0]));

		List<Record> records = page.getList();
		for (Record record : records) {
			String vCode = "cfpoint_" + record.getStr("vWareHouseCode");
			Record Property = Db.use("szlk").findById("t_Property", "propname",
					vCode);
			if (Property != null) {
				record.set("point", Property.get("propvalue"));
			} else {
				record.set("point", "0,0");
			}
		}

		return page;
	}

	/**
	 * 智能通风控制-yzz
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryTf(HashMap<String, Object> queryparam)
			throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db
				.paginate(
						Integer.valueOf(String.valueOf(queryparam.get("page"))),
						Integer.valueOf(String.valueOf(queryparam.get("rows"))),
						"select c.id,c.vWareHouseCode,max(c.vWareHouseName) as vWareHouseName ",
						"from t_DevicePara a,t_WareHouseDeviceBridge b,t_WareHouse c where a.vDeviceKind = '气泵' and a.id = b.t_DevicePara_id and b.t_WareHouse_id = c.id"
								+ s.getSql()
								+ " group by c.id,c.vWareHouseCode", s
								.getParam().toArray(new Object[0]));

		List<Record> records = page.getList();
		for (Record record : records) {
			String vCode = "cfpoint_" + record.getStr("vWareHouseCode");
			Record Property = Db.use("szlk").findById("t_Property", "propname",
					vCode);
			if (Property != null) {
				record.set("point", Property.get("propvalue"));
			} else {
				record.set("point", "0,0");
			}
		}

		return page;
	}

	/**
	 * 查询智能化设备中的视频
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Page<Record> queryCangHaoShiPin(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.paginate(Integer.valueOf(String
				.valueOf(queryparam.get("page"))), Integer.valueOf(String
				.valueOf(queryparam.get("rows"))), "select * ",
				"from t_WareHouse " + s.getSql(),
				s.getParam().toArray(new Object[0]));

		return page;
	}

	public static Record findWareHouseByCode(String canghao) {

		return Db.findFirst("select * from t_canghao_shipin where canghao=?",
				canghao);
	}

	/**
	 * 视频
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */
	public static Record querySp(Integer id) throws Exception {

		return Db.use("szlk").findFirst("select * from t_ShiPing where id=?",
				id);
	}

	public static Record querySpByID(HashMap<String, Object> queryparam)
			throws Exception {

		return Db.use("szlk").findFirst("select * from t_ShiPing where id=?",
				queryparam.get("id"));
	}

	/**
	 * 保存与智能仓窗对应的视频
	 * 
	 * @param queryparam
	 * @return
	 * @throws Exception
	 */

	public static boolean saveSpCh(HashMap<String, Object> queryparam) {
		boolean bool;
		Record spch = Db.use("szlk").findFirst(
				"select * from t_canghao_shipin where canghao=?",
				queryparam.get("canghao"));
		Record record = new Record();
		record.set("shipinid", queryparam.get("shipinid"));
		record.set("canghao", queryparam.get("canghao"));
		if (spch != null) {
			record.set("id", spch.getStr("id"));
			bool = Db.use("szlk").update("t_canghao_shipin", record);
		} else {
			record.set("id", SqlUtil.uuid());
			bool = Db.use("szlk").save("t_canghao_shipin", record);
		}
		return bool;

	}

	public static Record querySpCh(HashMap<String, Object> queryparam) {

		return Db.findFirst("select * from t_canghao_shipin where canghao=?",
				queryparam.get("canghao"));
	}

	public static List<Record> queryLiuheyi(String code) throws Exception {

		return Db.use("szlk").find(
				"select * from t_wareHouseDeviceBridge where t_WareHouse_id=?",
				code);
	}

	public static Record queryByCode(HashMap<String, Object> queryparam) {
		return Db
				.findFirst(
						"select vWareHouseName from t_WareHouse where vWareHouseCode=?",
						queryparam.get("vWareHouseCode"));
	}

	public static Record queryById(String id) {
		return Db.use("szlk").findFirst(
				"select vWareHouseName from t_WareHouse where id=?", id);
	}

	public static Page<Record> szlkLiangyouhytongcangList(
			HashMap<String, Object> queryparam) throws Exception {
		Param p = new Param();
		p.put("and vWareHouseCode = ?", "cf_id");
		p.put("and shengchanniandu = ?", "shengchanniandu");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlk").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from t_specificQualityResultPerWareHouse where 1=1"
						+ s.getSql(), s.getParam().toArray(new Object[0]));

		return page;
	}

	public static boolean saveSzlkLiangyouhytongcang(
			HashMap<String, Object> queryparam) {
		boolean bool = false;
		Record spch = Db
				.use("szlk")
				.findFirst(
						"select * from t_specificQualityResultPerWareHouse where vWareHouseCode=?",
						queryparam.get("cfname"));
		Record record = new Record();
		record.set("vWareHouseCode", queryparam.get("cf_id"));
		record.set("vGrainSubCode", queryparam.get("pz_id"));
		record.set("dmMoisture", queryparam.get("shuifen"));
		record.set("dmImpurity", queryparam.get("zazhi"));
		// record.set("dmMineral", queryparam.get("cf_id"));
		record.set("dmUnsoundKernel", queryparam.get("buwanshanli"));
		// record.set("dmVBRRatio", queryparam.get("cf_id"));
		record.set("dmYellowRiceKernel", queryparam.get("huangli"));
		// record.set("dmFattyAcid", queryparam.get("cf_id"));
		record.set("dmMilledRice", queryparam.get("guwaicaomi"));
		// record.set("dmMixingRatio", queryparam.get("cf_id"));
		record.set("dmBRRatio", queryparam.get("chucao"));
		record.set("dmGamma", queryparam.get("rongzhong"));
		record.set("vLevelCode", queryparam.get("dengji"));
		record.set("vOrdor", queryparam.get("sezeqiwei"));
		record.set("vMemo", queryparam.get("beizhu"));
		record.set("dtQualityTime", queryparam.get("jianyanriqi"));
		record.set("shengchanniandu", queryparam.get("shengchanniandu"));
		record.set("vGrainProperty", queryparam.get("xzhi_id"));
		if (spch != null) {
			record.set("ID", spch.getStr("ID"));
			bool = Db.use("szlk").update("t_specificQualityResultPerWareHouse",
					record);
		} else {
			bool = Db.use("szlk").save("t_specificQualityResultPerWareHouse",
					record);
		}
		return bool;
	}

	public static Page<Record> szlkLiangyouchukujiluInfoList(
			HashMap<String, Object> queryparam) throws Exception {

		Param p = new Param();
		p.put("and cf_id = ?", "cf_id");
		p.put("and shenchangniandu = ?", "shenchangniandu");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlkOA").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from szlk_liangyouchukujilu_info where 1=1" + s.getSql()
						+ " ORDER BY jianyanriqi desc",
				s.getParam().toArray(new Object[0]));

		return page;
	}

	public static Page<Record> szlkZhiliangjianchajiluInfoList(
			HashMap<String, Object> queryparam) throws Exception {

		Param p = new Param();
		p.put("and cf_id = ?", "cf_id");
		p.put("and shouhuoniandu = ?", "shouhuoniandu");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlkOA").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from szlk_zhiliangjianchajilu_info where 1=1" + s.getSql()
						+ " ORDER BY jianceriqi desc",
				s.getParam().toArray(new Object[0]));

		return page;
	}

	public static Page<Record> shouzhangkaList(
			HashMap<String, Object> queryparam) throws Exception {

		Param p = new Param();
		p.put("and cf_id = ?", "cf_id");
		p.put("and tiankariqi>= ?", "tiankariqi");
		p.put("and tiankariqi<= ?", "tiankariqi1");
		SqlAndParam s = SqlUtil.buildSql(p, queryparam);
		Page<Record> page = Db.use("szlkOA").paginate(
				Integer.valueOf(String.valueOf(queryparam.get("page"))),
				Integer.valueOf(String.valueOf(queryparam.get("rows"))),
				"select *",
				"from szlk_cangtuanka_info where 1=1" + s.getSql()
						+ " ORDER BY tiankariqi desc",
				s.getParam().toArray(new Object[0]));

		return page;
	}
}
