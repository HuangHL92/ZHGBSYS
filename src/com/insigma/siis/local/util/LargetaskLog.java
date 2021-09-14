package com.insigma.siis.local.util;

import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class LargetaskLog {
	/**
	 * 主日志ID
	 */
	public static String mainID;
	/**
	 * 主日志存入数据（DATE类型的可传空串）
	 * 
	 * @param map
	 * 			key:cloumn;
	 * 			value:值
	 */
	public static void MainLog(Map<String, String> map) {
		try {
			if(mainID ==null || "".equals(mainID)) {
				mainID=UUID.randomUUID().toString().replaceAll("-","");
			}
				if(map != null && !map.isEmpty() ) {
					HBSession sess = HBUtil.getHBSession();
					String countNum = sess.createSQLQuery("select count(LARGETASK_LOG_ID) from largetask_log_main where LARGETASK_LOG_ID='"+mainID+"'").uniqueResult().toString();
					if("0".equals(countNum)) {
						insertMainLog(map,sess);
					}else {
						updateMainLog(map,sess);
					}
					
				}else {
					System.out.println("主日志的参数为空");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 详情日志存入数据
	 * @param map
	 * 			key:cloumn;
	 * 			value:值
	 * 		detailID:不为空时：更新
	 */
	public static String DetailLog(Map<String, String> map,String detailID) {
		String id="";
		try {
			if( mainID !=null && !"".equals(mainID)) {
				if(map != null && !map.isEmpty() ) {
					if(detailID==null || "".equals(detailID)) {
						detailID=UUID.randomUUID().toString().replaceAll("-","");
					}
					HBSession sess = HBUtil.getHBSession();
					String countNum = sess.createSQLQuery("select count(LARGETASK_LOG_ITEM_ID) from largetask_log_item_check_da where LARGETASK_LOG_ITEM_ID='"+detailID+"'").uniqueResult().toString();
					if("0".equals(countNum)) {
						id = insertDetailLog(map,sess);
					}else {
						id = updateDetailLog(map,sess,detailID);
					}
				}else {
					System.out.println("详情日志的参数为空");
				}
			}else {
				System.out.println("详情日志的主键为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}
	private static String insertDetailLog(Map<String, String> map, HBSession sess) {
		String detailID = UUID.randomUUID().toString().replaceAll("-","");
		StringBuffer sbf = new StringBuffer("insert into LARGETASK_LOG_ITEM_CHECK_DA ");
		StringBuffer cloumnSbf=new StringBuffer();
		StringBuffer valueSbf=new StringBuffer();
		for (String key:map.keySet()) {
			cloumnSbf.append(key+",");
			String str = map.get(key);
			if(key.equalsIgnoreCase("CHECK_DA_BEGIN_DATE") || key.equalsIgnoreCase("CHECK_DA_END_TIME") || key.equalsIgnoreCase("CHECK_FILE_BEGIN_TIME")) {
				if(DBType.MYSQL ==DBUtil.getDBType())
					str="now()";
				else
					str="sysdate";
				valueSbf.append(str+",");
			}else if(key.equalsIgnoreCase("CHECK_DA_SEQUENCE") || key.equalsIgnoreCase("CHECK_COUNT")  || key.equalsIgnoreCase("CHECK_TOTAL") ){
				valueSbf.append(str+",");
			}else if(key.equalsIgnoreCase("LARGETASK_LOG_ID")){
				continue;
			}else {
				valueSbf.append(str==null?"":"'"+str+"',");
			}
		}
		String sql=sbf+" ("+cloumnSbf.substring(0,cloumnSbf.length()-1)+",LARGETASK_LOG_ID,LARGETASK_LOG_ITEM_ID) values ("+valueSbf.substring(0,valueSbf.length()-1)+",'"+mainID+"','"+detailID+"')";
		sess.createSQLQuery(sql).executeUpdate();
		return detailID;
	}
	private static String updateDetailLog(Map<String, String> map, HBSession sess, String detailID) {
		StringBuffer sbf = new StringBuffer("update  LARGETASK_LOG_ITEM_CHECK_DA set ");
		StringBuffer paramSbf=new StringBuffer();
		for (String key:map.keySet()) {
			if(key.equalsIgnoreCase("CHECK_DA_BEGIN_DATE") || key.equalsIgnoreCase("CHECK_DA_END_TIME") || key.equalsIgnoreCase("CHECK_FILE_BEGIN_TIME")) {
				String str="";
				if(DBType.MYSQL ==DBUtil.getDBType())
					str="now()";
				else
					str="sysdate";
				paramSbf.append(key+"="+str+",");
			}else if(key.equalsIgnoreCase("CHECK_DA_SEQUENCE") || key.equalsIgnoreCase("CHECK_COUNT")  || key.equalsIgnoreCase("CHECK_TOTAL") ){
				paramSbf.append(key+"="+map.get(key)+",");
			}else {
				paramSbf.append(key+"='"+map.get(key)+"',");
			}
		}
		String sql=sbf+" "+paramSbf.substring(0,paramSbf.length()-1)+" where LARGETASK_LOG_ITEM_ID='"+detailID+"'";
		sess.createSQLQuery(sql).executeUpdate();
		return detailID;
		
	}
	private static void updateMainLog(Map<String, String> map, HBSession sess) {
		StringBuffer sbf = new StringBuffer("update LARGETASK_LOG_MAIN set ");
		StringBuffer paramSbf=new StringBuffer();
		for (String key:map.keySet()) {
			if(key.equalsIgnoreCase("TASK_BEGIN_TIME") || key.equalsIgnoreCase("TASK_END_TIME")) {
				String str="";
				if(DBType.MYSQL ==DBUtil.getDBType())
					str="now()";
				else
					str="sysdate";
				paramSbf.append(key+"="+str+",");
			}else if(key.equalsIgnoreCase("LARGETASK_LOG_SEQUENCE") ){
				continue;
			}else if(key.equalsIgnoreCase("EXEC_PERCENT") ){
				paramSbf.append(key+"="+map.get(key)+",");
			}else{
				paramSbf.append(key+"='"+map.get(key)+"',");
			}
		}
		String sql=sbf+" "+paramSbf.substring(0,paramSbf.length()-1)+" where LARGETASK_LOG_ID='"+mainID+"'";
		sess.createSQLQuery(sql).executeUpdate();
		
	}
	private static void insertMainLog(Map<String, String> map, HBSession sess) {
		int Largetask_Log_Sequence= 0;
		if(DBType.MYSQL ==DBUtil.getDBType()){
			Largetask_Log_Sequence=Integer.parseInt(sess.createSQLQuery("select IFNULL(max(LARGETASK_LOG_SEQUENCE),0)+1 from LARGETASK_LOG_MAIN").uniqueResult().toString());
		}else {
			Largetask_Log_Sequence=Integer.parseInt(sess.createSQLQuery("select nvl(max(LARGETASK_LOG_SEQUENCE),0)+1 from LARGETASK_LOG_MAIN").uniqueResult().toString());
		}
		
		StringBuffer sbf = new StringBuffer("insert into LARGETASK_LOG_MAIN ");
		StringBuffer cloumnSbf=new StringBuffer();
		StringBuffer valueSbf=new StringBuffer();
		for (String key:map.keySet()) {
			cloumnSbf.append(key+",");
			String str = map.get(key);
			if(key.equalsIgnoreCase("TASK_BEGIN_TIME") || key.equalsIgnoreCase("TASK_END_TIME")) {
				if(DBType.MYSQL ==DBUtil.getDBType())
					str="now()";
				else
					str="sysdate";
				valueSbf.append(str+",");
			}else if(key.equalsIgnoreCase("LARGETASK_LOG_SEQUENCE") ){
				continue;
			}else {
				valueSbf.append(str==null?"":"'"+str+"',");
			}
		}
		String sql=sbf+" ("+cloumnSbf.substring(0,cloumnSbf.length()-1)+",LARGETASK_LOG_SEQUENCE,LARGETASK_LOG_ID,LARGETASK_LOG_BATCH_ID) values ("+valueSbf.substring(0,valueSbf.length()-1)+","+Largetask_Log_Sequence+",'"+mainID+"','"+UUID.randomUUID().toString().replaceAll("-","")+"')";
		sess.createSQLQuery(sql).executeUpdate();
		
	}
}
