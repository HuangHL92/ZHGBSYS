package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.epsoft.util.StringUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class UpdateField {
	private static Map<String, List<String>> tableMap = new HashMap<String, List<String>>();
	static{
		List<String> a01 = Arrays.asList(new String[]{"CBDRESULT","A0101","A0104","A0104A","A0107","A0111","A0111A","A0114","A0114A","A0117","A0117A","A0134","A0144","A0144B","A0144C","A0148","A0149","A0151","A0153","A0155","A0157","A0158","A0159","A015A","A0160","A0161","A0162","A0163","A0165","A0184","A0191","A0192","A0192A","A0192B","A0193","A0195","A0196","A0198","A0199","A01K01","A01K02","AGE","CBDW","ISVALID","JSNLSJ","NL","NMZW","NRZW","QRZXL","QRZXLXX","QRZXW","QRZXWXX","RESULTSORTID","RMLY","TBR","TBSJ","USERLOG","XGR","XGSJ","ZZXL","ZZXLXX","ZZXW","ZZXWXX","A3927","A0102","A0128B","A0128","A0140","A0187A","A0148C","A1701","A14Z101","A15Z101","A0141D","A0141","A3921","SORTID","A0180","A0194","A0192D","STATUS","TBRJG","A0120","A0121","A0122"});
		List<String> a29 = Arrays.asList(new String[]{"A2907","A2911","A2921A","A2941","A2944","A2947","A2949","UPDATED","A2947A","A2921B","A2947B","A2921C","A2921D"});
		List<String> a30 = Arrays.asList(new String[]{"A3001","A3004","A3007A","A3034","UPDATED"});
		List<String> a31 = Arrays.asList(new String[]{"A3101","A3104","A3107","A3117A","A3118","A3137","A3138","UPDATED","A3140","A3141","A3142"});
		List<String> a37 = Arrays.asList(new String[]{"A3701","A3707A","A3707C","A3707E","A3707B","A3708","A3711","A3714","UPDATED"});
		List<String> a60 = Arrays.asList(new String[]{"A6001","A6002","A6003","A6004","A6005","A6006","A6007","A6008","A6009","A6010","A6011","A6012","A6013","A6014","A6015","A6016","A6017"});
		List<String> a61 = Arrays.asList(new String[]{"A2970","A2970A","A2970B","A2970C","A6104","A6107","A6108","A6109","A6110","A6111","A6112","A6113","A6114","A6115","A6116"});
		List<String> a62 = Arrays.asList(new String[]{"A2950","A6202","A6203","A6204","A6205"});
		List<String> a63 = Arrays.asList(new String[]{"A2951","A6302","A6303","A6304","A6305","A6306","A6307","A6308","A6309","A6310"});
		
		tableMap.put("a01", a01);
		tableMap.put("a29", a29);
		tableMap.put("a30", a30);
		tableMap.put("a31", a31);
		tableMap.put("a37", a37);
		tableMap.put("a60", a60);
		tableMap.put("a61", a61);
		tableMap.put("a62", a62);
		tableMap.put("a63", a63);
	}
	public static String update(List<Param> params) throws AppException{
		String a0000 = "";
		String table = "";
		String column = "";
		String value = "";
		for (Param param : params){
			//判断是否存在该人员信息
			if (param.getName().equals("A0184") && !"".equals(param.getValue())){
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list==null||list.size()==0){
					return "错误：系统中不存在该人员信息！";
				} else if(list.size()>1){
					return "错误：系统中存在多条该人员信息！";
				} else{
					a0000 = list.get(0).getA0000();					
				}
			}

			if (param.getName().equals("TABLE")){
				if (!tableMap.containsKey(param.getValue().toLowerCase())) {
					return "错误："+param.getName()+"值输入不合法！";
				}
				if(param.getValue() != null && !param.getValue().equals("")){
					table = param.getValue().toLowerCase();
				}
				else {
					return "错误：TABLE值不能为空！";
				}
			}
			if (param.getName().equals("COLUMN")){
				List<String> list = new ArrayList<String>();
				list.addAll(tableMap.get(table));
				if ( !list.contains(param.getValue().toUpperCase())){
					return "错误："+param.getName()+"值输入不合法！";
				}
				if(param.getValue() != null && !param.getValue().equals("")){
					column = param.getValue().toLowerCase();
				}
				else {
					return "错误：COLUMN值不能为空！";
				}
			}
			if (param.getName().equals("VALUE")){
				if(param.getValue() != null && !param.getValue().equals("")){
					if(getCodeValue(column, param.getValue()).equals("")){
						value = param.getValue();
					}else {
						value = getCodeValue(column, param.getValue());
					}
				}else {
					return "错误：VALUE值不能为空！";
				}
			}
			
		}
			//System.out.println("update "+ table +" set "+ column +"='"+value+"' where a0000 = '"+ a0000 +"'");
			try {
				String sql = "update "+ table +" set "+ column +"='"+value+"' where a0000 =?";
				sql = StringUtil.decodeXML(sql);
				HBUtil.executeUpdate(sql,new Object[]{a0000});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return "select 1 from dual";
		
	}
	
	/**
	 * 根据参数获取CodeValue值  机构的代码单独判断
	 * @param param
	 * @return 返回null 等于返回错误信息
	 */
	public static String getCodeValue(String column,String value){

		String codetype = CodeTypeUtil.get(column.toUpperCase());
		if(codetype == null){
			return value;
		}
		List list = HBUtil.getHBSession().createSQLQuery(
				"select code_value from Code_Value where code_type='"+codetype+"'" +
						" and code_status='1' and code_name='"+value+"'").list();
		if (list==null||list.size()==0) {
			return "";
		}
		value = list.get(0).toString();
		return value;
	}
	
	/**
	 * 批量更新预留方法
	 * @param params
	 * @return
	 * @throws AppException
	 */
	public static String update2(List<Param> params,Connection conn) throws AppException{
		String orgid = "";
		String select = ""; //是否包含下级
		String sql2 = "";
		PreparedStatement pst = null;
		for (Param param : params){
			if (param.getName().equals("ORGID")){
				orgid = param.getValue();
			}
			if (param.getName().equals("SELECT")){
				select = param.getValue();
			}
		}
		
				HBSession sess = HBUtil.getHBSession();
				String sql = "select   b0111   from   B01   where   B0111='"+orgid+"'";//查询机构编码
				List<String> list = sess.createSQLQuery(sql).list();
				if(list!=null&&list.size()>0){
					if(list.size() != 1){
						return "错误：机构代码对应多条记录！";
					}

					try {
						String b0111 = list.get(0);
						conn.setAutoCommit(true);
						if(select.equals("1")){
							pst = conn.prepareStatement("update a29 c set c.a2970='03' where c.a0000 = any(select a0000 from b01 t, a02 b where b.a0201b = t.b0111 and t.b0111 like ?)");
							pst.setString(1, b0111+"%");
						}else if (select.equals("0")){
							pst = conn.prepareStatement("update a29 c set c.a2970='03' where c.a0000 = any(select a0000 from b01 t, a02 b where b.a0201b = t.b0111 and t.b0111 = ?)");
							pst.setString(1, b0111);
						}
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
						return "错误";
					}
				}
		
			
	
		return "1";
	}
}
