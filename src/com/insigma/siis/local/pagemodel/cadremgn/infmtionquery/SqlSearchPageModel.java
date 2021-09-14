package com.insigma.siis.local.pagemodel.cadremgn.infmtionquery;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class SqlSearchPageModel extends PageModel {
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	public static String jiaoyan = "0";//0是打开 1是点击
	
	@Override
	public int doInit() throws RadowException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public void initX() throws RadowException, AppException{
		QueryCommon qcom = new QueryCommon();
		String code_level = "0,1,2,01,02,03,04,05,06,11,12,13,14,141,36";
		List<HashMap<String, Object>> table_list = qcom.queryTable();
		List<HashMap<String, Object>> function_list = qcom.queryFunction();
		List<HashMap<String, Object>> operator_list = qcom.queryOperator(code_level);
		this.getPageElement("tableListGrid").setValueList(table_list);
		this.getPageElement("personListGrid121").setValueList(function_list);
		this.getPageElement("personListGrid3123123").setValueList(operator_list);
	}

	/*
	 * 点击信息集，显示对应的指标项
	 */
	@PageEvent("tableListGrid.rowclick")
	@NoRequiredValidate
	public int codeChange() throws Exception {
		QueryCommon qcom = new QueryCommon();
		// 获取点击行信息
		String table_name = this.getPageElement("tableListGrid").getValue("table_name", 0).toString();
		String[] arr = table_name.split("\\.");
		if (arr.length < 1) {
			this.setMainMessage("查询数据库失败");
		}
		this.getPageElement("table_code").setValue(arr[0]);
		List<HashMap<String, Object>> list = qcom.queryCode(arr[0]);
		this.getPageElement("codeListGrid").setValueList(list);
		//this.getPageElement("Flag").setValue("no");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*
	 * 点击信息项 -- 填充页面信息
	 */
	@PageEvent("codeListGrid.rowclick")
	@NoRequiredValidate
	public int signChange() throws Exception {
		String table_code = this.getPageElement("table_code").getValue();
		String col_name = this.getPageElement("codeListGrid").getValue("col_name", 0).toString();
		String code_type = this.getPageElement("codeListGrid").getValue("code_type", 0).toString();
		String[] arr = col_name.split("\\.");
		if (arr.length < 1) {
			this.setMainMessage("查询数据库失败");
		}
		this.getPageElement("codetype").setValue(code_type);
		this.getPageElement("col_code").setValue(table_code+"."+arr[0]);
		this.getPageElement("nowString").setValue(table_code+"."+arr[0]);
		//this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		this.getExecuteSG().addExecuteCode("orgTreeJsonData();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*
	 * 点击运算符
	 */
	@PageEvent("personListGrid3123123.rowclick")
	@NoRequiredValidate
	public int signWrite() throws Exception {
		// 获取点击行信息
		String col_name = this.getPageElement("personListGrid3123123").getValue("code_name", 0).toString();
		String[] arr = col_name.split("\\(");
		if (arr.length < 1) {
			this.setMainMessage("查询数据库失败");
		}
		//this.getPageElement("vru000").setValue(this.getPageElement("vru000").getValue()+arr[0]);
		this.getPageElement("nowString").setValue(arr[1].replace(")", ""));
		//this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * 点击已定义公式
	 */
	@PageEvent("personListGrid121.rowclick")
	@NoRequiredValidate
	public int formulaWrite() throws Exception {
		// 获取点击行信息
		String col_name = this.getPageElement("personListGrid121").getValue("code_name", 0).toString();
		this.getPageElement("nowString").setValue(col_name);
		//this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//树机构单击
	@PageEvent("clickCodeValue")
	public int clickCodeValue(String codevalue) throws RadowException, AppException {
		if("-1".equals(codevalue)){//表示根节点
			this.getPageElement("codevalue").setValue(codevalue);
		}else{
			String codeType=this.getPageElement("codetype").getValue();
			String sql="select code_value,code_name from code_value where code_type='"+codeType+"' and code_value='"+codevalue+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			HashMap<String, Object> map=list.get(0);
			this.getPageElement("nowString").setValue("'"+map.get("code_value")+"'");
			this.getExecuteSG().addExecuteCode("rowDbClick1();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setBtnQuery")
	public int querySqlStaement() throws RadowException, AppException{
//		try{
//			if(this.getPageElement("vru000")!=null&&!"".equals(this.getPageElement("vru000"))){
//				String sql=this.getPageElement("vru000").getValue().toString().trim();
//				if(sql.length()>5){
//					String sqltop=sql.substring(0,6).toLowerCase();
//					if("select".equals(sqltop)){
//						CommQuery cq=new CommQuery();
//						cq.getListBySQL(sql);
//					}else{
//						this.setMainMessage("sql语句必须是select的查询语句");
//					}
//				}else{
//					this.setMainMessage("sql语句必须是select的查询语句");
//				}
//			}else{
//				this.setMainMessage("sql语句不能为空");
//			}
//		}catch(AppException e){
//			String[] err =e.toString().split(":");
//        	String exc = err[1];
//        	this.getPageElement("vru005").setValue(exc);
//			this.getPageElement("updateFlag").setValue(e.getMessage());
//			this.getExecuteSG().addExecuteCode("setValue();");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
		String updateFlag="no";
		if(this.getPageElement("vru000")!=null&&!"".equals(this.getPageElement("vru000"))){
			String sql=this.getPageElement("vru000").getValue().toString().trim().replace("‘", "'").replace("“", "\"").replace("，", ",").replace(";", "").replace("；", "");
			this.getPageElement("vru005").setValue("");
			if(sql.length()>5){
				String sqltop=sql.substring(0,6).toLowerCase();
				if("select".equals(sqltop)){
					if(sql.contains(";")){
						this.setMainMessage("语句中不能出现“;”");
					}else{
						CommQuery cqbs=new CommQuery();
						try {
							this.getPageElement("qvid").setValue("");
							cqbs.getListBySQL(sql);
							updateFlag="yes";
							String qvid = setInfoToDB(sql);
							if("".equals(qvid)){
								throw new Exception();
							}
							this.getPageElement("qvid").setValue(qvid);
						} catch (Exception e) {
							String exceptionMessage=e.toString().replace("查询失败,", "");
				        	this.getPageElement("vru005").setValue(exceptionMessage);
				        	this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2');");
						}finally{
							this.getPageElement("updateFlag").setValue(updateFlag);
							this.getExecuteSG().addExecuteCode("setValue();");
						}
					}
				}else{
					this.setMainMessage("sql语句必须是select的查询语句");
				}
			}else{
				this.setMainMessage("sql语句必须是select的查询语句");
			}
		}else{
			this.setMainMessage("sql语句不能为空");
		}
//		this.getPageElement("updateFlag").setValue(updateFlag);
//		this.getExecuteSG().addExecuteCode("setValue();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String setInfoToDB(String sql){
		HBSession hbSession = HBUtil.getHBSession();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String qvid = "";
		try {
			String delSqlForqryf = "delete from qryview where TYPE = '5' and userid='"+userid+"'";
			hbSession.createSQLQuery(delSqlForqryf).executeUpdate();
			Qryview qryview = new Qryview();
			qryview.setType("5");
			qryview.setCreatetime(DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
			qryview.setViewname("sqlSearch");
			qryview.setChinesename("SQL查询");
			qryview.setUses("临时存储SQL查询的SQL语句");
			qryview.setFuncdesc("临时存储SQL查询的SQL语句");
			qryview.setUserid(userid);
			qryview.setQrysql(sql);
			hbSession.save(qryview);
			CommonQueryBS.systemOut("--------->>>"+DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss")+":"+sql);
			hbSession.flush();
			qvid = qryview.getQvid();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return qvid;
	}
	
	public List<String> querySqlCode(String qvid) throws RadowException, SQLException{
		ResultSet rs=null;
	    Statement  stmt =null;
	    Connection connection =null;
	    List<String> list = new ArrayList<String>();
	    HBSession session = HBUtil.getHBSession();
	    Qryview qryview = (Qryview) session.get(Qryview.class, qvid);
	    String column = "";
	    String columnName = "";
	    String columnType = "null";
	    String columnTypeForSelOrTex = "text";
	    try{
			HBSession hbsess = HBUtil.getHBSession();	
			connection = hbsess.connection();
			stmt =connection.createStatement();
			rs = stmt.executeQuery(qryview.getQrysql());
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount(); // 返回此 ResultSet 对象中的列数
			list = new ArrayList<String>();
			//Map<String, String> rowData = new HashMap<String, String>();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					column = rsmd.getColumnName(i);
					/* 这里无法获取表名，所以只能默认获取首行为该字段的数据（默认A0000为主键，各表中该字段相同） */
					if(DBUtil.getDBType()==DBType.ORACLE){
						columnName = HBUtil.getValueFromTab("COL_NAME", "CODE_TABLE_COL", "COL_CODE='"+column+"' and rownum=1 order by COL_CODE");//获取字段名称
						columnType = HBUtil.getValueFromTab("CODE_TYPE", "CODE_TABLE_COL", "COL_CODE='"+column+"' and rownum=1 order by COL_CODE");//获取字段是否存在二级代码
					}else{
						columnName = HBUtil.getValueFromTab("COL_NAME", "CODE_TABLE_COL", "COL_CODE='"+column+"'  order by COL_CODE limit 1 ");//获取字段名称
						columnType = HBUtil.getValueFromTab("CODE_TYPE", "CODE_TABLE_COL", "COL_CODE='"+column+"'  order by COL_CODE limit 1");//获取字段是否存在二级代码
					}
					
					if(!"".equals(columnType)&&!"null".equals(columnType)&&null!=columnType){
						columnTypeForSelOrTex = "select";
					}
					/* 统计关系所在单位A0195 的二级代码-未处理 */
//					if("A0195".equals(column.toUpperCase())){
//						column = "(select b0101 from b01 where b0111=a01.A0195) A0195";
//						columnTypeForSelOrTex = "text";
//					}
					list.add(column+"@"+columnName+"@"+columnType+"@"+columnTypeForSelOrTex);//a0101@姓名@ZB01@SELECT/TEXT
					columnType = "null";
					columnTypeForSelOrTex = "text";
				}
				break;
			}
	    }catch(Exception e){
	        e.printStackTrace();
	        try{
	          if(rs!=null){
	        	  rs.close();
	          }
	          if(stmt!=null){
	        	  stmt.close();
	          }
	          if(connection!=null){
	        	  connection.close();
	          }
	        }catch(SQLException ex){
	        }
	        throw new RadowException(e.getMessage());
	    }
	    return list;
		
	}
	
	public List<HashMap<String, Object>> querySqlValue(String str) throws RadowException, SQLException, AppException{
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(str);
		return list;
	}
	
	
}