package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class TableColInterface {
	/**
	 * 描述：得到所有没有修改权限的字段名       任免表主页专用！！！
	 * @return String ep:a0101,a0184...
	 */
	public static String getAllUpdateData(String sign){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		
		String sql = "";
		if("look".equals(sign)){
			sql = "select col_code from code_table_col";
		}else{
			String[] userTemp=Utils.getRoleId(userid).split(",");
			sql = "select col_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  ischange='0' group by col_code having count(1)>"+(userTemp.length-1);
		}
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	/**
	 * 描述：得到所有没有修改权限的字段名
	 * @return String ep:a0101,a0184...
	 */
	public static String getAllUpdateData(){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		
		String sql = "select col_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  ischange='0'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	/**
	 * 描述：得到所有没有查看权限的字段名
	 * @return String ep:a0101,a0184...
	 */
	public static String getAllSelectData(){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		String[] userTemp=Utils.getRoleId(userid).split(",");
		String sql = "select col_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  islook='0' and col_code not in ('A15Z101','A14Z101','A3611','A3607','A3601','A3604A','A3627') group by col_code having count(1)>"+(userTemp.length-1);
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	
	/**
	 * 描述：得到所有没有查看权限的字段名
	 * @return String ep:a0101,a0184...
	 */
	public static String getAllSelectData_in(){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		String[] userTemp=Utils.getRoleId(userid).split(",");
		String sql = "select col_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  islook='0' and col_code in ('A15Z101','A14Z101','A3611','A3607','A3601','A3604A','A3627') group by col_code having count(1)>"+(userTemp.length-1);
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	/**
	 * 描述：根据table得到没有修改权限的字段名      任免表主页专用
	 * @return String ep:a0101,a0184...
	 */
	public static String getUpdateDataByTable(String table,String sign){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		
		String sql = "";
		if("look".equals(sign)){
			sql = "select col_code,table_code from code_table_col where table_code='"+table+"'";
		}else{
			sql = "select col_code,table_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  ischange='0' and table_code='"+table+"'";
		}
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		//List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(Object[] o : list){
				sb.append("'"+o[1].toString()+"_"+o[0].toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	/**
	 * 描述：根据table得到没有修改权限的字段名
	 * @return String ep:a0101,a0184...
	 */
	public static String getUpdateDataByTable(String table){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		
		String sql = "select col_code,table_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  ischange='0' and table_code='"+table+"'";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		//List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(Object[] o : list){
				sb.append("'"+o[1].toString()+"_"+o[0].toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	/**
	 * 描述:根据table得到没有查看权限的字段名
	 * @param table
	 * @return
	 */
	public static String getSelectDataByTable(String table){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		
		String sql = "select col_code,table_code from competence_usertablecol where userid in ('"+Utils.getRoleId(userid)+"') and  islook='0' and table_code='"+table+"'";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		//List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(Object[] o : list){
				sb.append("'"+o[1].toString()+"_"+o[0].toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	 //判断是否是干部处室和组织部领导
    public boolean isA0194Tags() throws AppException{
    	CommQuery cqbs=new CommQuery();
    	boolean flag = false;
        String userid = PrivilegeManager.getInstance().getCueLoginUser().getId();
        String sql = "select userid from SMT_USER t where userid ='"+userid+"'" +
        		" and dept in ('9cf7df4b9c76453a826ee27e6c4f577e')"; 
        List<HashMap<String, Object>> list=cqbs.getListBySQL( sql );
        if (list.size() > 0) {
       	   flag = true;
       }
       return flag;
   } 
}
