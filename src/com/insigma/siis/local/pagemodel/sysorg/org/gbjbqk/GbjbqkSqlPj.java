package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.sysorg.org.GbjbqkSubPageModel;

public class GbjbqkSqlPj {

	public GbjbqkSqlPj() {
	}
	
	public void sqlPj(String param_arr_row,StringBuffer sb){
		try{
			
			if("4".equals(param_arr_row)){//总计
				//不加条件
			}else if( GbjbqkSubPageModel.xioaji.indexOf(param_arr_row)!=-1 ){//其他类别 小计
				sb.append(" and a01.a0221 like '"+GbjbqkSubPageModel.row[Integer.valueOf(param_arr_row)]+"%' ");
			}else if(Integer.parseInt(param_arr_row)>4&&Integer.parseInt(param_arr_row)<=(GbjbqkSubPageModel.row.length-1)){
				sb.append(" and a01.a0221='"+GbjbqkSubPageModel.row[Integer.valueOf(param_arr_row)]+"' ");
			}else{
				sb.append(" and 1=0 ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sqlPjA01(StringBuffer sb) throws RadowException {
		try{
			if(DBType.ORACLE==DBUtil.getDBType()){
				sb.append(""
						+ " and a01.a0221 in( select code_value from code_value "
						+ " where code_type='ZB09' "
						+ " and code_status='1' "
						+ " and code_leaf='1'"
						+ " and code_value!='911'"
						+ " and not regexp_like(code_value,'^[C-G]') ) "
						+ "");
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				sb.append(""
						+ " and a01.a0221 in( select code_value from code_value "
						+ " where code_type='ZB09' "
						+ " and code_status='1' "
						+ " and code_leaf='1'"
						+ " and code_value!='911'"
						+ " and a01.a0221 regexp '^[C-G]' ) "
						+ "");
			}else{
				throw new RadowException("发现未知数据源，请联系系统管理员!");
			}
			
			/*sb.append(" and a01.a0221 is not null "//不为空
					+ " and a01.a0221 not in('01',"//公务员//不为大类别
					+ "'01A',"//综合管理类  //不为大类别  
					+ "'01B',"//专业技术类  //不为大类别 
					+ "'01C',"//行政执法类//不为大类别
					+ "'02',"//人民警察警员职务序列 //不为大类别
					+ "'1',"//
					+ "'2',"//
					+ "'3',"//
					+ "'4',"//
					+ "'5',"//
					+ "'6',"//
					+ "'7',"// 系统已经不用//不为大类别
					+ "'8',"//深圳警务技术职务//不为大类别
					+ "'A',"//深圳市气象预报员//不为大类别
					+ "'B',"//深圳市气象信息员//不为大类别
					+ "'9',"//事业单位管理等级//不为大类别
					+ "'911'"//事业单位管理等级 试用期人员 统计表格不存在此行
					+ ") ");
			if(DBType.ORACLE==DBUtil.getDBType()){
				sb.append(" and not regexp_like(a01.a0221,'^[C-G]') ");//不以a-g开头
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				sb.append(" and a01.a0221 regexp '^[C-G]' ");//不以a-g开头
			}else{
				throw new RadowException("发现未知数据源，请联系系统管理员!");
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sqlPjExists(StringBuffer sb,String groupid){
		if(groupid!=null&&groupid.length()>26){
			
		}else{
			return ;
		}
		sb.append(" ,(select  a02.a0000 "
                      + " from a02, competence_userdept cu "
                     + " where a02.a0255 = '1' "
                     + groupid.substring(0, groupid.length()-26) 
                    // + " and (1 = 2 or substr(a02.a0201b, 1, 7) = '001.001') "
                       + " and a02.A0201B = cu.b0111 "
                       + " and cu.userid = '"+SysUtil.getCacheCurrentUser().getId()+"' "
                       + " group by a02.a0000"
                       + " ) a02 where a01.a0163 = '1'   and a02.a0000=a01.a0000  "
                       );
		
//		sb.append(""
//	      		  + " and exists  ( select 1 from a02,competence_userdept cu "
//	      		  + " where a02.a0255 = '1' "
//	      		  + groupid.substring(0, groupid.length()-26) 
//	      		  + "  and a02.a0000=a01.a0000 "
//	      		  + " and a02.A0201B = cu.b0111 "
//	      		  + " and cu.userid = '"+SysUtil.getCacheCurrentUser().getId()+"' "
//	      		+" )"
//		         );
//		sb.append(""
//      		  + " and exists  ( "
//      		+ "select 1 from  ( "
//			         + " select a0000 from a02 "
//			         + " where a02.a0255 = '1' "
//			         + groupid.substring(0, groupid.length()-26)
//			         + " and a02.A0201B in "
//                     + " (select cu.b0111 "
//                        + " from competence_userdept cu "
//                       + " where cu.userid = '"+SysUtil.getCacheCurrentUser().getId()+"') "
////			         + " and a01.a0000=a02.a0000 "
//			         + ") ci where a01.a0000 = ci.a0000)"
//	         );
	}
	

	/**
	 * grid 列表查询字段
	 * @param sb
	 * @param lb
	 * @return
	 */
	public StringBuffer returnPjGridSql(StringBuffer sb){
		sb.append(" SELECT "
				  + " a01.a0101, "//姓名   等
				  + " a01.a0104, "//性别
				  + " a01.a0184, "//公民身份号码   等
				  + " a01.a0117a, "//民族
				  + " a01.a0117,"
				  + " a01.a0141, "//政治面貌
				  + " a01.a0149,"//职务层次 名称
				  + " a01.a0192a,"//任职机构名称 及职务全称  等
				  + " a01.a0221, "//当前职务层次
				  //+ " a08.a0801b, "//学历代码   等
				  //+ " a08.a0901b, "//学位代码   等
				  + " a01.a0000, "//人员统一标识符
				  + " a01.a0107, "//出生日期
				  + " a01.A0288, "//任现职务层次时间   多
				  + " a01.A0197"
				  
				  );//是否具有两年以上基层工作经历
		    
		return sb;
	}

}
