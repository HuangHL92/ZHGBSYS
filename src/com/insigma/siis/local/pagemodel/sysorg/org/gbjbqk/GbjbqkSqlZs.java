package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.RadowException;

public class GbjbqkSqlZs {

	public GbjbqkSqlZs() {
		
	}
	public StringBuffer returnGbjbqkSqlZs21(StringBuffer sb1 ) throws RadowException{
		 sb1.append( " from A01 a01 left join "
	                + " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 asc ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 "//1 最高学位 所在记录 学历学位表 A0801B,A0901B,a0000
	                +" on a01.a0000=a08.a0000  "//任职状态 1 在任
			         + "  "//人员统一标识符 关联 a08 记录可能为空
				         );
		return sb1;
	}
	public StringBuffer returnGbjbqkSqlZs21_sub(StringBuffer sb,boolean xueli,boolean xuewei) throws RadowException{
		 sb.append( " from A01 a01 left join ");
		 
		 if(DBType.ORACLE==DBUtil.getDBType()){
			 if(xueli==true&&xuewei==true){
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' and A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
		     }else{
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
		     }
		 }else if(DBType.MYSQL==DBUtil.getDBType()){
			 if(xueli==true&&xuewei==true){
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' and A0834='1' group by a0000 ) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
		     }else{
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' group by a0000 ) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
		     }
		 }
         sb.append(" on a01.a0000=a08.a0000 "//
        + "  "//人员统一标识符 关联
	         );
		return sb;
	}
	
	public StringBuffer query_tj(StringBuffer sb,String query_tj){
		 if("''".equals(query_tj)||"'',''".equals(query_tj)
				 ||"'','',''".equals(query_tj)||"'','','',''".equals(query_tj)
				  ||"'','','','',''".equals(query_tj)||"'','','','','',''".equals(query_tj)
				  ||"'','','','','','',''".equals(query_tj)
				  ){//选择全部
		 }else{
			 sb.append(" and a01.a0221 in"
					 + " ( select code_value from code_value s where s.sub_code_value "
					 + " in( select t.code_value from code_value t where t.code_name in ( " 
					 + query_tj
					 + " ) and t.code_type='ZB09' and t.code_status='1'"
					 + " ) "
					 + " )");
			 
		 }
		 return sb;
	}
	
	public StringBuffer returnGbjbqkSqlZs2(StringBuffer sb1) throws RadowException{
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb1.append(  " select  "
	                + "a.A0221,"
	                +" SUM(CASE "
			                +" WHEN nvl(a.A0901B,0) like '2%' or nvl(a.A0901B,0)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  博士
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--硕士
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs "//--学士
	                +" FROM"
	                + " (SELECT "
	                + " a01.a0221,"
					  + " a08.A0901B ");//学位代码   等
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb1.append(  " select  "
	                + "a.A0221,"
	                +" SUM(CASE "
			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  博士
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--硕士
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs "//--学士
	                +" FROM"
	                + " (SELECT "
	                + " a01.a0221,"
					  + " a08.A0901B ");//学位代码   等
		}else{
			throw new RadowException("数据源错误，请联系系统管理员检查原因!");
		}
		return sb1;
	}
	
	/**
	 * 关联a08表 ，但是仅仅统计最高学历信息  A0834='1' 的记录
	 * @param sb
	 * @return
	 * @throws RadowException
	 */
	public StringBuffer returnGbjbqkSqlZs11(StringBuffer sb) throws RadowException{
		sb.append(" FROM (SELECT a01.a0000, "//人员统一标识符
				  + " a01.a0104, "//性别
				  + " a01.a0117, "//民族
				  + " a01.a0141, "//政治面貌
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0801B, "//学历代码   等
				  + " a08.A0901B, "//学位代码   等
				  + " a01.a0107, "//出生日期
				  + " a01.A0288, "//任现职务层次时间   多
				  + " a01.A0197 "//是否具有两年以上基层工作经历
	          +" FROM A01 a01 "
	          + " left join  "//人员基本信息表
	          + " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 asc) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 "//1 最高学历 所在记录 学历学位表 A0801B,A0901B,a0000
	         +" on a01.a0000=a08.a0000 "
	         + "  ");//人员统一标识符 关联 a08 记录可能为空
		return sb;
	}
	
	/**
	 * 关联a08表
	 * @param sb
	 * @return
	 * @throws RadowException
	 */
	public StringBuffer returnGbjbqkSqlZs11_sub(StringBuffer sb,boolean xueli,boolean xuewei) throws RadowException{
		sb.append(" FROM (SELECT a01.a0000, "//人员统一标识符
				  + " a01.a0104, "//性别
				  + " a01.a0117, "//民族
				  + " a01.a0141, "//政治面貌
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0801B, "//学历代码   等
				  + " a08.A0901B, "//学位代码   等
				  + " a01.a0107, "//出生日期
				  + " a01.A0288, "//任现职务层次时间   多
				  + " a01.A0197 "//是否具有两年以上基层工作经历
	          +" FROM A01 a01 "
	          + " left join  ");//人员基本信息表
		if(DBType.ORACLE==DBUtil.getDBType()){
			if(xueli==true&&xuewei==true){
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' and A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		     }else if(xueli==true){
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		     }
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			if(xueli==true&&xuewei==true){
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' and A0834='1' group by a0000 ) a08 ");
		     }else if(xueli==true){
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0834='1' group by a0000 ) a08 ");
		     }
		}
        sb.append(" on a01.a0000=a08.a0000 "
        + "  ");//人员统一标识符 关联 a08 记录可能为空
		return sb;
	}
	
	public StringBuffer returnGbjbqkSqlZs1(StringBuffer sb,String lb) throws RadowException{
		
		if(DBType.ORACLE==DBUtil.getDBType()){
			if("".equals(lb)){//干部基本情况统计 
				sb.append(" SELECT  "
				+ " SUM(CASE "
		             +" WHEN nvl(a.a0104,0) = 2 THEN "
		             +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) nv, "//性别 2 女
		       +" SUM(CASE "
		             +" WHEN nvl(a.a0117,0) = 01 THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) sm, "//少数名族  !=01
		       +" SUM(CASE "
		             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zd, "//中共党员
		        +" SUM(CASE "
		             +" WHEN (a.a0141=01 or a.a0141=02) THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) fzd, "//非中共党员   为空则不是中共党员（统计分析计算逻辑需要）
		              );
			}else if("nv".equals(lb)){//干部基本情况统计 (女)
					sb.append(" SELECT  "
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//少数名族  !=01
			       +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//中共党员
			        +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) fzd, "//非中共党员   为空则不是中共党员（统计分析计算逻辑需要）
			              );
			}else if("shao".equals(lb)){//干部基本情况统计 (少)
					sb.append(" SELECT  "
					+ " SUM(CASE "
			             +" WHEN nvl(a.a0104,0) = 2 THEN "
			             +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) nv, "//性别 2 女
			       +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//中共党员
			        +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) fzd, "//非中共党员   为空则不是中共党员（统计分析计算逻辑需要）
			              );
			}else if("fei".equals(lb)){//干部基本情况统计 (非)
					sb.append(" SELECT  "
					+ " SUM(CASE "
			             +" WHEN nvl(a.a0104,0) = 2 THEN "
			             +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) nv, "//性别 2 女
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//少数名族  !=01 
		           +" SUM(CASE "
		             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) heji, "//非中共党员   为空则不是中共党员（统计分析计算逻辑需要）
			              );
			}else{
				throw new RadowException("参数错误!请联系系统管理员!");
			}
			sb.append(" "
	            +" a.A0221, "//当前职位层次
	            + " SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '1%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjs,"//研究生
		        + "  SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '2%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbk,"//大学本科
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '3%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzz,"//大学专科
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '4%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zz,"//中专
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) in('61','71','81','91') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) gzjyx,"//高中及以下
	           +" 0 bs, "//--  博士
              +" 0 ss, "//--硕士
              +" 0 xs, "//--学士
              + " sum(CASE "
             + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(add_months(sysdate,-455),'yyyy-mm')),'yyyy-mm'))/12)<=35  THEN "
              + " 1 "
             + " ELSE "
              + " 0 "
           + " END) xydy35, "
           + " sum( CASE "
             + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=40 and floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=36 THEN "
              + " 1 "
             + " ELSE "
              + " 0 "
           + " END) dy36xy40, "
            + " sum( CASE "
             + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=45 and floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=41  THEN "
              + " 1 "
             + " ELSE "
              + " 0 "
           + " END) dy41xy45, "
            + " sum( CASE "
             + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=50 and floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=46  THEN "
              + " 1 "
             + " ELSE "
              + " 0 "
           + " END) dy46xy50, "
           + " sum( CASE "
	           + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=54 and floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=51  THEN "
	            + " 1 "
	           + " ELSE "
	            + " 0 "
	         + " END) dy51xy54, "
	         + " sum( CASE "
	           + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=59 and floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=55  THEN "
	            + " 1 "
	           + " ELSE "
	            + " 0 "
	         + " END) dy55xy59, "
           + " sum( CASE "
             + " WHEN floor(months_between(SYSDATE,to_date(nvl(substr(a.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=60  THEN "
              + " 1 "
             + " ELSE "
              + " 0 "
           + " END) dy60, "
           + " round( "
           + " sum( " 
                   + " floor( "
                         + "  months_between( SYSDATE,  to_date( nvl(substr(a.a0107,1,6),to_char(sysdate,'yyyy-mm') ) , 'yyyy-mm' )  ) /12 "
                         + " ) "
                         + " )  /  sum("
                         + " case when a.a0107 is not null then 1 else 0 end "
                         + ") "
                         + " ,2) pjnl, "//平均年龄
           +" sum( case when add_months(to_date((nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-13),'yyyy-mm-dd') "
           		+" ) ),'yyyy-mm-dd'),12)>trunc(sysdate) then 1 else 0 end ) zhccxy1, "//不满1年
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-37),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),12)<=trunc(sysdate) then 1 else 0 end) zhccxy3, "//1至不满3年
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-61),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-35),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)<=trunc(sysdate) then 1 else 0 end) zhccxy5, "
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-121),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),120)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)<=trunc(sysdate) then 1 else 0 end) zhccxy10, "
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-119),'yyyy-mm-dd') "
          	   +" ),'yyyy-mm-dd'),120)<=trunc(sysdate) then 1 else 0 end ) zhccxy11, "
          	   + "  sum( case when nvl(a.A0197,0) = 1 then 1 else 0 end ) A0197 ");//是否具有两年以上基层工作经历
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append("SELECT  "
			+ "	SUM(     "
			+ "		CASE "
			+ "		WHEN a.a0104 = 2 THEN  "
			+ "			1 " 
			+ "		ELSE  " 
			+ "			0 " 
			+ "		END   " 
			+ "	) nv,     " //女
			+ "	SUM(      " 
			+ "		CASE  " 
			+ "		WHEN a.a0117 = 01 THEN "
			+ "			0 "
			+ "		ELSE  "
			+ "			1 "
			+ "		END "
			+ "	) sm,   "//少
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) zd,     "//党员
			+ "	SUM(      "
			+ "		CASE  "
			+ "		WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			+ "			0  "
			+ "		ELSE   "
			+ "			1  "
			+ "		END  "
			+ "	) fzd,   "//非党员 为空则不是中共党员（统计分析计算逻辑需要）
			+ "	a.A0221, "
			+ "	SUM(     "
			+ "		CASE "
			+ "		WHEN a.A0801B LIKE '1%' THEN   "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END  "
			+ "	) yjs,   "//研究生
			+ "	SUM(     "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '2%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END  "
			+ "	) dxbk,  "//大学本科
			+ "	SUM(     "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '3%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END "
			+ "	) dxzz, "//大学专科
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '4%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END "
			+ "	) zz,   "//中专
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN a.A0801B IN ('61', '71', '81', '91') THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) gzjyx,  "//高中及以下
			+ "	0 bs, "//博士
			+ "	0 ss, "//硕士
			+ "	0 xs, "//学士
			+ "	sum(  "
			+ "		CASE  "
			+ "		WHEN floor(  "
			+ "			timestampdiff (month,     "
			+ "				 "
			+ "					if (   "
			+ "						a.a0107 is null,   "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(	CURDATE(),INTERVAL 455 MONTH),  "
			+ "							'%y%m%d' "
			+ "						),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 35 THEN  "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) xydy35, "//35岁及以下
			+ "	sum(      "
			+ "		CASE  "
			+ "		WHEN floor(   "
			+ "			timestampdiff (month, "
			+ "				 "
			+ "					if (     "
			+ "						a.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
			+ "					) ,DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12     "
			+ "		) <= 40        "
			+ "		AND floor(     "
			+ "			timestampdiff (month,     "
			+ "				 "
			+ "					if (    "
			+ "						a.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d')"
			+ "			) / 12     "
			+ "		)>= 36 THEN    "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) dy36xy40, "//36岁至40岁
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN floor(    "
			+ "			timestampdiff (month,   "
			+ "					 "
			+ "					if (     "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d')"
			+ "			) / 12    "
			+ "		) <= 45       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,     "
			+ "					 "
			+ "					if (     "
			+ "						a.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107) "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 41 THEN  "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) dy41xy45, "//41岁至45岁
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN floor(   "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (   "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 50       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (  "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 46 THEN  "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) dy46xy50, "//46岁至50岁
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN floor(   "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (   "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 54       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (  "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 51 THEN  "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) dy51xy54, "//51岁至54岁
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN floor(   "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (   "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 59       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			+ "				 "
			+ "					if (  "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 55 THEN  "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) dy55xy59, "//55岁至59岁
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN floor(   "
			+ "			timestampdiff (month,    "
			+ "					 "
			+ "					if (     "
			+ "						a.a0107 is null,  "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)   "
			+ "					), DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 60 THEN  "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) dy60,   "//60岁及以上
			+ "	round(    "
			+ "		sum(  "
			+ "			floor(    "
			+ "			timestampdiff (month,   "
			+ "					if (            "
			+ "						a.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a.a0107)=6,CONCAT(a.a0107,\"01\"),a.a0107)  "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d')  "
			+ "			) / 12 "
			+ "		)          "
			+ "		) / sum(   "
			+ "			CASE   "
			+ "			WHEN a.a0107 IS NOT NULL THEN "
			+ "				1 "
			+ "			ELSE  "
			+ "				0 "
			+ "			END   "
			+ "		)         "
			+ "	,2) pjnl,       "//平均年龄
			+ "sum(           "
			+ "		CASE      "
			+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 13 month) ,  "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 12 month) > CURDATE() THEN   "
			+ "			1  "
			+ "		ELSE   "
			+ "			0  "
			+ "		END    "
			+ "	) zhccxy1, "//不满1年
			+ "	sum(       "
			+ "		CASE   "
			+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 37 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 36 month) > CURDATE()  "
			+ "		AND DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 11 month) ,  "
			+ "							'%Y%m%d'  "
			+ "						),            "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
			+ "					),'%Y%m%d'),INTERVAL 12 month) <= CURDATE() THEN  "
			+ "			1  "
			+ "		ELSE   "
			+ "			0  "
			+ "		END    "
			+ "	) zhccxy3, "//1年至不满3年
			+ "	sum(       "
			+ "		CASE   "
			+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 61 month) ,  "
			+ "							'%Y%m%d'  "
			+ "						),            "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
			+ "					),'%Y%m%d'),INTERVAL 60 month) > CURDATE() "
			+ "		AND DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,     "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(CURDATE(),INTERVAL 35 month) ,  "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 36 month) <= CURDATE() THEN  "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) zhccxy5,"//3年至不满5年
			+ "	sum(      "
			+ "		CASE  "
			+ "		WHEN DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 121 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 120 month) > CURDATE()  "
			+ "		AND DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,     "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(CURDATE(),INTERVAL 59 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 60 month) <= CURDATE() THEN "
			+ "			1   "
			+ "		ELSE    "
			+ "			0   "
			+ "		END     "
			+ "	) zhccxy10, "//5年至不满10年
			+ "	sum(        "
			+ "		CASE    "
			+ "		WHEN DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 119 month) ,"
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 120 month) <= CURDATE() THEN "
			+ "			1  "
			+ "		ELSE   "
			+ "			0  "
			+ "		END    "
			+ "	) zhccxy11,"//10年及以上
			+ "	sum(       "
			+ "		CASE   "
			+ "		WHEN if (a.A0197='', 0,a.A0197) = 1 THEN "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) A0197 "//具有2年以上基层工作经历
			);
		}else{
			throw new RadowException("数据源错误(发现未知数据源)，请联系系统管理员检查原因!");
		}
		
		return sb;
	}

}
