package com.insigma.odin.framework.sys.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.comm.CodeTypeConvert;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.commform.PrintUtil;
import com.insigma.odin.framework.util.commform.SysConst;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

/**
 * 查询的通用bs类
 * @author jinwei
 * @date 2008-5-19
 */
public class CommQueryBS extends BSSupport {
	
	
	/**
	 * 带有HeadName的查询
	 * 
	 * @param querySQL
	 * @param headNames
	 * @param start
	 * @param limit
	 * @return
	 * @throws AppException
	 */
	public PageQueryData queryByHeadNames(String querySQL, String headNames, int start, int limit) throws AppException {
		PageQueryData pqd = new PageQueryData();
		HBSession hbsess = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			hbsess = HBUtil.getHBSession();			
			ResultSetMetaData rsmd = null;
			List<HashMap<Object, Object>> queryDataList = new ArrayList<HashMap<Object, Object>>();
			
			String sql = "";			
			CodeTypeConvert convert = null;
			
			if (start >= 0) {
				if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
					sql = "select * from ("+querySQL+") t limit "+start+","+limit;
				else
					sql = "select * from (select rownum as numrow,c.* from (" + querySQL + ") c) where numrow>=" + (start + 1) + " and numrow<=" + (start + limit);
			} else {
				sql = querySQL;
			}
			
			if (headNames != null && !headNames.equals("")) { // 指定列头
				if (headNames.indexOf("{") != -1) {// json格式					
					JSONObject headNameJsonObject = JSONObject.fromObject(headNames);
					Iterator<String> it = headNameJsonObject.keys();
					
					String colName = null;
					String[] realCol = null;					
					convert = new CodeTypeConvert();
					
					while (it.hasNext()) {
						colName = it.next();
						realCol=colName.split("_");						
						colName=realCol[0];
						
						if(colName.length() != 0){
							convert.addCodetype(colName.toUpperCase());
						}						
					}
				}
			}
			

			stmt = hbsess.connection().createStatement();
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			
			int cols = rsmd.getColumnCount();
			String[] colArray = new String[cols];
			
			for (int i = 1; i <= cols; i++) {
				colArray[i - 1] = rsmd.getColumnName(i).toLowerCase();						
			}

			String tmp = null;					
			HashMap<Object, Object> hm = null;
			
			while (rs.next()) {

				hm = new HashMap<Object, Object>();

				for (int j = 1; j <= cols; j++) {
					if (!colArray[j - 1].equals("numrow")) {
						tmp = rs.getString(j);	
						
						if (tmp != null && tmp.length() >= 3) {
							if (tmp.startsWith("~1~")) { // rowsToCols行变成列的特殊处理
								if (tmp.length() > 3) {
									tmp = tmp.substring(3);
									String[] rowArray = tmp.split("~");

									for (int row = 0; row < rowArray.length; row++) {
										int index = rowArray[row].indexOf("^");
										if (index != -1) {
											hm.put(rowArray[row].substring(0, index), (index == rowArray[row].length() ? "" : rowArray[row].substring(index + 1)));
										}
									}
								}
								
								continue; // 特殊处理完后跳转到处理下一个
							}
						}
						
						if(convert != null && convert.containsKey(colArray[j-1].toUpperCase())){
							hm.put( colArray[j - 1], convert.convertCodeValue(colArray[j - 1].toUpperCase(), tmp));
						}else{
							hm.put( colArray[j - 1], tmp);
						}
						
					}					
				}

				queryDataList.add(hm);
			}
			
			if (rs != null){
				rs.close();
			}
			
			pqd.setData(queryDataList);
			
			if (start >= 0) {
				rs = stmt.executeQuery("select count(*) from (" + querySQL + ")");
				rs.next();				
				pqd.setTotalCount(rs.getInt(1));				
			} else {
				pqd.setTotalCount(queryDataList.size());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new AppException("分页查询失败", e);
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		return pqd;
	}

	static Map<String,Object> pqdSizeMap = new HashMap<String,Object>();
	/**
	 * 查询的共用bs，当start为-1时表示 查处符合条件的所有记录，即不分页
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @return
	 */
	public PageQueryData query(String querySQL, String sqlType, int start, int limit) throws AppException {
		PageQueryData pqd = new PageQueryData();
		Object msql = pqdSizeMap.get("sql");
		boolean isCache = false;
		Object totalSize = 0;
		if(querySQL!=null&&msql!=null&&querySQL.equals(msql.toString())){
			isCache = true;
			totalSize = pqdSizeMap.get("totalSize");
		}else{
			pqdSizeMap.put("sql", querySQL);
		}
		
		//PhotosUtil.saveLog("执行的sql------:"+querySQL);
		try {
			if (sqlType == null || querySQL == null) {
				pqd.setTotalCount(0);
				pqdSizeMap.put("totalSize", 0);
				pqd.setData(new ArrayList<Object>());
			} else {
				HBSession hbsess = HBUtil.getHBSession();
				if (sqlType.equalsIgnoreCase("HQL")) {
					// 取数据
					Query q = hbsess.createQuery(querySQL);
					if (start >= 0) { // 分页时做如下操作
						q.setFirstResult(start);
						q.setMaxResults(limit);
					}
					List<?> queryDataList = q.list();
					pqd.setData(queryDataList);
					if (start >= 0) {
						// 取记录总数
						
						int totalCount = 0;
						if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							Iterator<?> it = hbsess.createQuery("select count(*) " + querySQL).iterate();
							totalCount = ((Long) it.next()).intValue();
							pqdSizeMap.put("totalSize", totalCount);
						}
						pqd.setTotalCount(totalCount);
					} else {
						if (queryDataList != null && queryDataList.size() >= 0) {
							pqd.setTotalCount(queryDataList.size());
							pqdSizeMap.put("totalSize", queryDataList.size());
						}
					}
				} else if (sqlType.equalsIgnoreCase("SQL")) {
					Statement stmt = null;
					ResultSet rs = null;
					ResultSetMetaData rsmd = null;
					List<HashMap<Object, Object>> queryDataList = new ArrayList<HashMap<Object, Object>>();
					int cols;
					stmt = hbsess.connection().createStatement();
					String sql = "";
					if (start >= 0) {
						if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
							sql = "select * from ("+querySQL+") t limit "+start+","+limit;
						else
							sql = "select * from (select rownum as numrow,c.* from (" + querySQL + ") c  where rownum<=" + (start + limit)+") where numrow>=" + (start + 1) ;
					} else {
						sql = querySQL;
					}
					if(SysConst.getServerShowSql()){
						PrintUtil.sysprint(sql);
					}
					StopWatch w2 = new StopWatch();
					w2.start();
					rs = stmt.executeQuery(sql);
					w2.stop();
					PhotosUtil.saveLog("grid列表分页数据查询sql耗时："+w2.elapsedTime());
					StopWatch w = new StopWatch();
					w.start();
					rsmd = rs.getMetaData();
					cols = rsmd.getColumnCount();
					String[] colArray = new String[cols];
					
					for (int i = 1; i <= cols; i++) {
						colArray[i - 1] = rsmd.getColumnName(i).toLowerCase();						
					}

					String tmp = null;					
					int i=0;
					while (rs.next()) {
						i++;
						HashMap<Object, Object> hm = new HashMap<Object, Object>();

						for (int j = 1; j <= cols; j++) {

							if (!colArray[j - 1].equals("numrow")) {

								tmp = rs.getString(j);								;

								if (tmp != null && tmp.length() >= 3) {
									if (tmp.startsWith("~1~")) { // rowsToCols行变成列的特殊处理
										if (tmp.length() > 3) {
											tmp = tmp.substring(3);
											String[] rowArray = tmp.split("~");

											for (int row = 0; row < rowArray.length; row++) {
												int index = rowArray[row].indexOf("^");
												if (index != -1) {
													hm.put(rowArray[row].substring(0, index), (index == rowArray[row].length() ? "" : rowArray[row].substring(index + 1)));
												}
											}
										}
										continue; // 特殊处理完后跳转到处理下一个
									}
								}
								
								hm.put((rsmd.getColumnName(j)).toLowerCase(), tmp);
							}
							
						}

						queryDataList.add(hm);
						if(start >= 0&&i==limit)break;
					}
					w.stop();
					PhotosUtil.saveLog("grid列表数据对象封装耗时："+w.elapsedTime()+"\r\n列表数据量:"+i);
					if (rs != null)
						rs.close();
					pqd.setData(queryDataList);
					if (start >= 0) {
						StopWatch w3 = new StopWatch();
						w3.start();
						int totalCount = 0;
						if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							rs = stmt.executeQuery("select count(*) from (" + querySQL + ") t");
							rs.next();
							totalCount = rs.getInt(1);
							rs.close();
							pqdSizeMap.put("totalSize", totalCount);
						}
						pqd.setTotalCount(totalCount);
						w3.stop();
						PhotosUtil.saveLog("grid列表数据总量查询sql耗时："+w3.elapsedTime()+"\r\n列表总数:"+totalCount);
					} else {
						pqd.setTotalCount(queryDataList.size());
						pqdSizeMap.put("totalSize", queryDataList.size());
					}
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				} else {
					pqd.setTotalCount(0);
					pqdSizeMap.put("totalSize", 0);
					pqd.setData(new ArrayList<Object>());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new AppException("分页查询失败", e);
		}
		return pqd;
	}
	
	/**
	 * 查询的共用bs，当start为-1时表示 查处符合条件的所有记录，即不分页
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @return
	 */
	public PageQueryData query(String querySQL, String sqlType, int start, int limit,HashMap<String,List<String>> mapData) throws AppException {
		PageQueryData pqd = new PageQueryData();
		Object msql = pqdSizeMap.get("sql");
		boolean isCache = false;
		Object totalSize = 0;
		if(querySQL!=null&&msql!=null&&querySQL.equals(msql.toString())){
			isCache = true;
			totalSize = pqdSizeMap.get("totalSize");
		}else{
			pqdSizeMap.put("sql", querySQL);
		}
		
		//PhotosUtil.saveLog("执行的sql------:"+querySQL);
		try {
			if (sqlType == null || querySQL == null) {
				pqd.setTotalCount(0);
				pqdSizeMap.put("totalSize", 0);
				pqd.setData(new ArrayList<Object>());
			} else {
				HBSession hbsess = HBUtil.getHBSession();
				if (sqlType.equalsIgnoreCase("HQL")) {
					// 取数据
					Query q = hbsess.createQuery(querySQL);
					if (start >= 0) { // 分页时做如下操作
						q.setFirstResult(start);
						q.setMaxResults(limit);
					}
					List<?> queryDataList = q.list();
					pqd.setData(queryDataList);
					if (start >= 0) {
						// 取记录总数
						
						int totalCount = 0;
						if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							Iterator<?> it = hbsess.createQuery("select count(*) " + querySQL).iterate();
							totalCount = ((Long) it.next()).intValue();
							pqdSizeMap.put("totalSize", totalCount);
						}
						pqd.setTotalCount(totalCount);
					} else {
						if (queryDataList != null && queryDataList.size() >= 0) {
							pqd.setTotalCount(queryDataList.size());
							pqdSizeMap.put("totalSize", queryDataList.size());
						}
					}
				} else if (sqlType.equalsIgnoreCase("SQL")) {
					Statement stmt = null;
					ResultSet rs = null;
					ResultSetMetaData rsmd = null;
					List<HashMap<Object, Object>> queryDataList = new ArrayList<HashMap<Object, Object>>();
					int cols;
					stmt = hbsess.connection().createStatement();
					String sql = "";
					if (start >= 0) {
						if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
							sql = "select * from ("+querySQL+") t limit "+start+","+limit;
						else
							sql = "select * from (select rownum as numrow,c.* from (" + querySQL + ") c  where rownum<=" + (start + limit)+") where numrow>=" + (start + 1) ;
					} else {
						sql = querySQL;
					}
					if(SysConst.getServerShowSql()){
						PrintUtil.sysprint(sql);
					}
					StopWatch w2 = new StopWatch();
					w2.start();
					rs = stmt.executeQuery(sql);
					w2.stop();
					PhotosUtil.saveLog("grid列表分页数据查询sql耗时："+w2.elapsedTime());
					StopWatch w = new StopWatch();
					w.start();
					rsmd = rs.getMetaData();
					cols = rsmd.getColumnCount();
					String[] colArray = new String[cols];
					
					for (int i = 1; i <= cols; i++) {
						colArray[i - 1] = rsmd.getColumnName(i).toLowerCase();						
					}

					String tmp = null;					
					int i=0;
					int k=0;
					while (rs.next()) {
						i++;
						HashMap<Object, Object> hm = new HashMap<Object, Object>();

						for (int j = 1; j <= cols; j++) {

							if (!colArray[j - 1].equals("numrow")) {

								tmp = rs.getString(j);								;

								if (tmp != null && tmp.length() >= 3) {
									if (tmp.startsWith("~1~")) { // rowsToCols行变成列的特殊处理
										if (tmp.length() > 3) {
											tmp = tmp.substring(3);
											String[] rowArray = tmp.split("~");

											for (int row = 0; row < rowArray.length; row++) {
												int index = rowArray[row].indexOf("^");
												if (index != -1) {
													hm.put(rowArray[row].substring(0, index), (index == rowArray[row].length() ? "" : rowArray[row].substring(index + 1)));
												}
											}
										}
										continue; // 特殊处理完后跳转到处理下一个
									}
								}
								
								hm.put((rsmd.getColumnName(j)).toLowerCase(), tmp);
							}
							
						}

						Iterator it=mapData.keySet().iterator(); 
						while(it.hasNext()){ 
						String key=it.next().toString(); 
						//通过key拿到value 
						List<String> str1= (List<String>) mapData.get(key); 
							hm.put(key.toLowerCase(), str1.get(k));
						}
						queryDataList.add(hm);
						k++;
						if(start >= 0&&i==limit)break;
					}
					w.stop();
					PhotosUtil.saveLog("grid列表数据对象封装耗时："+w.elapsedTime()+"\r\n列表数据量:"+i);
					if (rs != null)
						rs.close();
					pqd.setData(queryDataList);
					if (start >= 0) {
						StopWatch w3 = new StopWatch();
						w3.start();
						int totalCount = 0;
						if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							rs = stmt.executeQuery("select count(*) from (" + querySQL + ") t");
							rs.next();
							totalCount = rs.getInt(1);
							rs.close();
							pqdSizeMap.put("totalSize", totalCount);
						}
						pqd.setTotalCount(totalCount);
						w3.stop();
						PhotosUtil.saveLog("grid列表数据总量查询sql耗时："+w3.elapsedTime()+"\r\n列表总数:"+totalCount);
					} else {
						pqd.setTotalCount(queryDataList.size());
						pqdSizeMap.put("totalSize", queryDataList.size());
					}
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				} else {
					pqd.setTotalCount(0);
					pqdSizeMap.put("totalSize", 0);
					pqd.setData(new ArrayList<Object>());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new AppException("分页查询失败", e);
		}
		return pqd;
	}
	
	/**
	 * 查询的共用bs，当start为-1时表示 查处符合条件的所有记录，即不分页
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @return
	 */
	public PageQueryData queryNoCount(String querySQL, String sqlType, int start, int limit,int totalCount) throws AppException {
		PageQueryData pqd = new PageQueryData();
		Object msql = pqdSizeMap.get("sql");
		boolean isCache = false;
		Object totalSize = 0;
		if(querySQL!=null&&msql!=null&&querySQL.equals(msql.toString())){
			isCache = true;
			totalSize = pqdSizeMap.get("totalSize");
		}else{
			pqdSizeMap.put("sql", querySQL);
		}
		
		//PhotosUtil.saveLog("执行的sql------:"+querySQL);
		try {
			if (sqlType == null || querySQL == null) {
				pqd.setTotalCount(0);
				pqdSizeMap.put("totalSize", 0);
				pqd.setData(new ArrayList<Object>());
			} else {
				HBSession hbsess = HBUtil.getHBSession();
				if (sqlType.equalsIgnoreCase("HQL")) {
					// 取数据
					Query q = hbsess.createQuery(querySQL);
					if (start >= 0) { // 分页时做如下操作
						q.setFirstResult(start);
						q.setMaxResults(limit);
					}
					List<?> queryDataList = q.list();
					pqd.setData(queryDataList);
					if (start >= 0) {
						// 取记录总数
						
						//int totalCount = 0;
						/*if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							Iterator<?> it = hbsess.createQuery("select count(*) " + querySQL).iterate();
							totalCount = ((Long) it.next()).intValue();
							pqdSizeMap.put("totalSize", totalCount);
						}*/
						pqdSizeMap.put("totalSize", totalCount);
						pqd.setTotalCount(totalCount);
					} else {
						if (queryDataList != null && queryDataList.size() >= 0) {
							pqd.setTotalCount(queryDataList.size());
							pqdSizeMap.put("totalSize", queryDataList.size());
						}
					}
				} else if (sqlType.equalsIgnoreCase("SQL")) {
					Statement stmt = null;
					ResultSet rs = null;
					ResultSetMetaData rsmd = null;
					List<HashMap<Object, Object>> queryDataList = new ArrayList<HashMap<Object, Object>>();
					int cols;
					stmt = hbsess.connection().createStatement();
					String sql = "";
					if (start >= 0) {
						if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
							sql = "select * from ("+querySQL+") t limit "+start+","+limit;
						else
							sql = "select * from (select rownum as numrow,c.* from (" + querySQL + ") c  where rownum<=" + (start + limit)+") where numrow>=" + (start + 1) ;
					} else {
						sql = querySQL;
					}
					if(SysConst.getServerShowSql()){
						PrintUtil.sysprint(sql);
					}
					StopWatch w2 = new StopWatch();
					w2.start();
					rs = stmt.executeQuery(sql);
					w2.stop();
					PhotosUtil.saveLog("grid列表分页数据查询sql耗时："+w2.elapsedTime());
					StopWatch w = new StopWatch();
					w.start();
					rsmd = rs.getMetaData();
					cols = rsmd.getColumnCount();
					String[] colArray = new String[cols];
					
					for (int i = 1; i <= cols; i++) {
						colArray[i - 1] = rsmd.getColumnName(i).toLowerCase();						
					}

					String tmp = null;					
					int i=0;
					while (rs.next()) {
						i++;
						HashMap<Object, Object> hm = new HashMap<Object, Object>();

						for (int j = 1; j <= cols; j++) {

							if (!colArray[j - 1].equals("numrow")) {

								tmp = rs.getString(j);								;

								if (tmp != null && tmp.length() >= 3) {
									if (tmp.startsWith("~1~")) { // rowsToCols行变成列的特殊处理
										if (tmp.length() > 3) {
											tmp = tmp.substring(3);
											String[] rowArray = tmp.split("~");

											for (int row = 0; row < rowArray.length; row++) {
												int index = rowArray[row].indexOf("^");
												if (index != -1) {
													hm.put(rowArray[row].substring(0, index), (index == rowArray[row].length() ? "" : rowArray[row].substring(index + 1)));
												}
											}
										}
										continue; // 特殊处理完后跳转到处理下一个
									}
								}
								
								hm.put((rsmd.getColumnName(j)).toLowerCase(), tmp);
							}
							
						}

						queryDataList.add(hm);
						if(start >= 0&&i==limit)break;
					}
					w.stop();
					PhotosUtil.saveLog("grid列表数据对象封装耗时："+w.elapsedTime()+"\r\n列表数据量:"+i);
					if (rs != null)
						rs.close();
					pqd.setData(queryDataList);
					if (start >= 0) {
						StopWatch w3 = new StopWatch();
						w3.start();
						//int totalCount = 0;
						/*if(isCache){
							totalCount = (Integer)totalSize;
						}else{
							rs = stmt.executeQuery("select count(*) from (" + querySQL + ") t");
							rs.next();
							totalCount = rs.getInt(1);
							rs.close();
							pqdSizeMap.put("totalSize", totalCount);
						}*/
						pqdSizeMap.put("totalSize", totalCount);
						pqd.setTotalCount(totalCount);
						w3.stop();
						PhotosUtil.saveLog("grid列表数据总量查询sql耗时："+w3.elapsedTime()+"\r\n列表总数:"+totalCount);
					} else {
						pqd.setTotalCount(queryDataList.size());
						pqdSizeMap.put("totalSize", queryDataList.size());
					}
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				} else {
					pqd.setTotalCount(0);
					pqdSizeMap.put("totalSize", 0);
					pqd.setData(new ArrayList<Object>());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new AppException("分页查询失败", e);
		}
		return pqd;
	}
	
	/**
	 * 使用异步方法同时进行本页数据和总数据的查询，使查询提速
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @return
	 */
	public PageQueryData queryByAsynchronous(String querySQL, String sqlType, int start, int limit) throws AppException {
		PageQueryData pqd = new PageQueryData();
		
		try {
			if (sqlType == null || querySQL == null) {
				pqd.setTotalCount(0);
				pqd.setData(new ArrayList<Object>());
			} else {
				if (sqlType.equalsIgnoreCase("HQL")) {
					
					List<Future<Object>> tasks = new ArrayList<Future<Object>>();
					ExecutorService executor = Executors.newCachedThreadPool();
					
					tasks.add(executor.submit(new Task(querySQL, sqlType, start, limit,1)));	
					tasks.add(executor.submit(new Task(querySQL, sqlType, start, limit,0)));
					
					executor.shutdown();
					List queryDataList=new ArrayList();
					int totalCount=0;
					for (int i = 0; i < tasks.size(); i++) {
						Future<Object> task = tasks.get(i);
						try {
							Object obj = task.get();
							if(obj instanceof Integer) {
								totalCount = (Integer) obj;
							}
							else if(obj instanceof List<?>) {
								queryDataList =  (ArrayList)obj;
							}
							System.out.println("线程"+i+"执行完毕");
						} catch (Exception e) {
							System.out.println("线程" + i + "发生异常");
							e.printStackTrace();
							throw new AppException("分页查询失败", e);
						}
					}
					pqd.setData(queryDataList);
					
					
					
					if (start >= 0) {
						pqd.setTotalCount(totalCount);
					} else {
						if (queryDataList != null && queryDataList.size() >= 0) {
							pqd.setTotalCount(queryDataList.size());
						}
					}
				} else if (sqlType.equalsIgnoreCase("SQL")) {
					List<Future<Object>> tasks = new ArrayList<Future<Object>>();
					ExecutorService executor = Executors.newCachedThreadPool();
					
					tasks.add(executor.submit(new Task(querySQL, sqlType, start, limit,1)));	
					tasks.add(executor.submit(new Task(querySQL, sqlType, start, limit,0)));
					
					executor.shutdown();
					List queryDataList=new ArrayList();
					int totalCount=0;
					for (int i = 0; i < tasks.size(); i++) {
						Future<Object> task = tasks.get(i);
						try {
							Object obj = task.get();
							if(obj instanceof Integer) {
								totalCount = (Integer) obj;
							}
							else if(obj instanceof List<?>) {
								queryDataList =  (ArrayList)obj;
							}
							System.out.println("线程"+i+"执行完毕");
						} catch (Exception e) {
							System.out.println("线程" + i + "发生异常");
							e.printStackTrace();
							throw new AppException("分页查询失败", e);
						}
					}
					pqd.setData(queryDataList);
					if (start >= 0) {
						pqd.setTotalCount(totalCount);
					} else {
						pqd.setTotalCount(queryDataList.size());
					}
				} else {
					pqd.setTotalCount(0);
					pqd.setData(new ArrayList<Object>());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new AppException("分页查询失败", e);
		}
		return pqd;
	}
	
	
	
	/**
	 * 查询的共用bs，当start为-1时表示 查处符合条件的所有记录，即不分页
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，用于excel，目前只支持“SQL”
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @return
	 */
	public PageQueryData queryForExpExcel(String querySQL,String sqlType,int start,int limit) throws AppException{
		PageQueryData pqd=new PageQueryData();
		try{
		if(sqlType==null||querySQL==null){
			pqd.setTotalCount(0);
			pqd.setData(new ArrayList<Object>());
		}else{
			HBSession hbsess=HBUtil.getHBSession();
			if(sqlType.equalsIgnoreCase("SQL")){
				Statement stmt=null;
				ResultSet rs=null;
				ResultSetMetaData rsmd=null;
				List<List<String>> queryDataList=new ArrayList<List<String>>();
				int cols;
				stmt=hbsess.connection().createStatement();
				String sql  = "";
				if(start>=0){
					if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
						sql = "select * from ("+querySQL+") t limit "+start+","+limit;
					else
						sql = "select * from (select rownum as numrow,c.* from ("+querySQL+") c) where numrow>="+(start+1)+" and numrow<="+(start+limit);
				}else{
					sql = querySQL;
				}
				rs=stmt.executeQuery(sql);
				rsmd=rs.getMetaData();
				cols=rsmd.getColumnCount();				
				while(rs.next()){
					List<String>  rowData=new ArrayList<String>();
					for(int j=1;j<=cols;j++){
						if(!(rsmd.getColumnName(j)).equalsIgnoreCase("NUMROW")){
							rowData.add(rs.getString(j));
						}
					}
					queryDataList.add(rowData);
				}
				if(rs!=null) rs.close();
				pqd.setData(queryDataList);	
				if(start>=0){
					rs=stmt.executeQuery("select count(*) from ("+querySQL+")");
					rs.next();
					int totalCount=rs.getInt(1);
					rs.close();
					pqd.setTotalCount(totalCount);
				}else{
					pqd.setTotalCount(queryDataList.size());
				}
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
			}else{
				pqd.setTotalCount(0);
				pqd.setData(new ArrayList<Object>());
			}
		}
		}catch(Exception e){
			System.out.println(e);
			throw new AppException("分页查询失败", e);
		}
		return pqd;
	}
	
	
	
	

	
	class Task implements Callable<Object> {
		
		private String querySQL;
		private int start;
		private int limit;
		private String sqlType;
		private int flag;//1 表示查询总记录数，0表示查询查询当前页的数据
		private HBSession hbsess;
		
		public Task(String querySQL, String sqlType, int start, int limit,int flag) {
			this.querySQL = querySQL;
			this.querySQL = querySQL;
			this.start = start;
			this.limit = limit;
			this.flag = flag;
			//this.hbsess = hbsess;
			
			this.sqlType = sqlType;
		}

		public Object call() throws Exception {
			hbsess = HBUtil.getHBSession();
			if(flag == 0) {
				
				if (sqlType.equalsIgnoreCase("HQL")) {
					List<?> list =new ArrayList();
					// 取数据
					Query q = hbsess.createQuery(querySQL);
					if (start >= 0) { // 分页时做如下操作
						q.setFirstResult(start);
						q.setMaxResults(limit);
					}
					list = q.list();
					hbsess.closeSession();
					return list;
				}
				
				if (sqlType.equalsIgnoreCase("SQL")) {

					Statement stmt = null;
					ResultSet rs = null;
					ResultSetMetaData rsmd = null;
					List<HashMap<Object, Object>> queryDataList = new ArrayList<HashMap<Object, Object>>();
					int cols;
					Connection conn = hbsess.connection();
					stmt = conn.createStatement();
					String sql = "";
					if (start >= 0) {
						if(DBUtil.getDBType(hbsess.getSession()).equals(DBType.MYSQL))
							sql = "select * from ("+querySQL+") t limit "+start+","+limit;
						else
							sql = "select * from (select rownum as numrow,c.* from (" + querySQL + ") c) where numrow>=" + (start + 1) + " and numrow<=" + (start + limit);
					} else {
						sql = querySQL;
					}
					if(SysConst.getServerShowSql()){
						PrintUtil.sysprint(sql);
					}
					rs = stmt.executeQuery(sql);
					rsmd = rs.getMetaData();
					cols = rsmd.getColumnCount();
					String[] colArray = new String[cols];
					
					for (int i = 1; i <= cols; i++) {
						colArray[i - 1] = rsmd.getColumnName(i).toLowerCase();						
					}

					String tmp = null;					

					while (rs.next()) {

						HashMap<Object, Object> hm = new HashMap<Object, Object>();

						for (int j = 1; j <= cols; j++) {

							if (!colArray[j - 1].equals("numrow")) {

								tmp = rs.getString(j);								;

								if (tmp != null && tmp.length() >= 3) {
									if (tmp.startsWith("~1~")) { // rowsToCols行变成列的特殊处理
										if (tmp.length() > 3) {
											tmp = tmp.substring(3);
											String[] rowArray = tmp.split("~");

											for (int row = 0; row < rowArray.length; row++) {
												int index = rowArray[row].indexOf("^");
												if (index != -1) {
													hm.put(rowArray[row].substring(0, index), (index == rowArray[row].length() ? "" : rowArray[row].substring(index + 1)));
												}
											}
										}
										continue; // 特殊处理完后跳转到处理下一个
									}
								}
								
								hm.put((rsmd.getColumnName(j)).toLowerCase(), tmp);
							}
							
						}

						queryDataList.add(hm);
						
						
					}
					if (rs != null){
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (conn!=null) {
						conn.close();
					}
					hbsess.closeSession();
					return queryDataList;
					
					
				}
				hbsess.closeSession();
				return new ArrayList();
			}
			else {//查询总记录数
				Integer total =0;
				if (sqlType.equalsIgnoreCase("HQL")) {
					if (start >= 0) {
						// 取记录总数
						Iterator<?> it = hbsess.createQuery("select count(*) " + querySQL).iterate();
						int totalCount = ((Long) it.next()).intValue();
						hbsess.closeSession();
						return totalCount;
					} else {
						
					}
				}
				
				if (sqlType.equalsIgnoreCase("SQL")) {
						Statement stmt = null;
						ResultSet rs = null;
						ResultSetMetaData rsmd = null;
						Connection conn = hbsess.connection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery("select count(*) from (" + querySQL + ") c");
						rs.next();
						int totalCount = rs.getInt(1);
						rs.close();

					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (conn!=null) {
						conn.close();
					}
					hbsess.closeSession();
					return totalCount;
				}
				hbsess.closeSession();
				return total;
			}
			
			
		}
	}
}
