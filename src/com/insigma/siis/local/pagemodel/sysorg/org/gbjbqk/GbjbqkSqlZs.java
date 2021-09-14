package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.RadowException;

public class GbjbqkSqlZs {

	public GbjbqkSqlZs() {
		
	}
	public StringBuffer returnGbjbqkSqlZs21(StringBuffer sb1 ) throws RadowException{
		 sb1.append( " from A01 a01 left join "
	                + " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 asc ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 "//1 ���ѧλ ���ڼ�¼ ѧ��ѧλ�� A0801B,A0901B,a0000
	                +" on a01.a0000=a08.a0000  "//��ְ״̬ 1 ����
			         + "  "//��Աͳһ��ʶ�� ���� a08 ��¼����Ϊ��
				         );
		return sb1;
	}
	public StringBuffer returnGbjbqkSqlZs21_sub(StringBuffer sb,boolean xueli,boolean xuewei) throws RadowException{
		 sb.append( " from A01 a01 left join ");
		 
		 if(DBType.ORACLE==DBUtil.getDBType()){
			 if(xueli==true&&xuewei==true){
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' and A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
		     }else{
		    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
		     }
		 }else if(DBType.MYSQL==DBUtil.getDBType()){
			 if(xueli==true&&xuewei==true){
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' and A0834='1' group by a0000 ) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
		     }else{
		    	 sb.append( " ( select A0801B,a0000,A0901B from a08 where A0835='1' group by a0000 ) a08 ");
		    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
		     }
		 }
         sb.append(" on a01.a0000=a08.a0000 "//
        + "  "//��Աͳһ��ʶ�� ����
	         );
		return sb;
	}
	
	public StringBuffer query_tj(StringBuffer sb,String query_tj){
		 if("''".equals(query_tj)||"'',''".equals(query_tj)
				 ||"'','',''".equals(query_tj)||"'','','',''".equals(query_tj)
				  ||"'','','','',''".equals(query_tj)||"'','','','','',''".equals(query_tj)
				  ||"'','','','','','',''".equals(query_tj)
				  ){//ѡ��ȫ��
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
			              +" END) bs, "//--  ��ʿ
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--˶ʿ
			       +" SUM(CASE "
			             +" WHEN nvl(a.A0901B,0) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs "//--ѧʿ
	                +" FROM"
	                + " (SELECT "
	                + " a01.a0221,"
					  + " a08.A0901B ");//ѧλ����   ��
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb1.append(  " select  "
	                + "a.A0221,"
	                +" SUM(CASE "
			                +" WHEN if(a.A0901B='',0,a.A0901B) like '2%' or if(a.A0901B='',0,a.A0901B)=1  THEN "
			                 +" 1 "
			                +" ELSE "
			                 +" 0 "
			              +" END) bs, "//--  ��ʿ
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '3%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) ss, "//--˶ʿ
			       +" SUM(CASE "
			             +" WHEN if(a.A0901B='',0,a.A0901B) like '4%'  THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			              +" END) xs "//--ѧʿ
	                +" FROM"
	                + " (SELECT "
	                + " a01.a0221,"
					  + " a08.A0901B ");//ѧλ����   ��
		}else{
			throw new RadowException("����Դ��������ϵϵͳ����Ա���ԭ��!");
		}
		return sb1;
	}
	
	/**
	 * ����a08�� �����ǽ���ͳ�����ѧ����Ϣ  A0834='1' �ļ�¼
	 * @param sb
	 * @return
	 * @throws RadowException
	 */
	public StringBuffer returnGbjbqkSqlZs11(StringBuffer sb) throws RadowException{
		sb.append(" FROM (SELECT a01.a0000, "//��Աͳһ��ʶ��
				  + " a01.a0104, "//�Ա�
				  + " a01.a0117, "//����
				  + " a01.a0141, "//������ò
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0801B, "//ѧ������   ��
				  + " a08.A0901B, "//ѧλ����   ��
				  + " a01.a0107, "//��������
				  + " a01.A0288, "//����ְ����ʱ��   ��
				  + " a01.A0197 "//�Ƿ�����������ϻ��㹤������
	          +" FROM A01 a01 "
	          + " left join  "//��Ա������Ϣ��
	          + " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 asc) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 "//1 ���ѧ�� ���ڼ�¼ ѧ��ѧλ�� A0801B,A0901B,a0000
	         +" on a01.a0000=a08.a0000 "
	         + "  ");//��Աͳһ��ʶ�� ���� a08 ��¼����Ϊ��
		return sb;
	}
	
	/**
	 * ����a08��
	 * @param sb
	 * @return
	 * @throws RadowException
	 */
	public StringBuffer returnGbjbqkSqlZs11_sub(StringBuffer sb,boolean xueli,boolean xuewei) throws RadowException{
		sb.append(" FROM (SELECT a01.a0000, "//��Աͳһ��ʶ��
				  + " a01.a0104, "//�Ա�
				  + " a01.a0117, "//����
				  + " a01.a0141, "//������ò
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0801B, "//ѧ������   ��
				  + " a08.A0901B, "//ѧλ����   ��
				  + " a01.a0107, "//��������
				  + " a01.A0288, "//����ְ����ʱ��   ��
				  + " a01.A0197 "//�Ƿ�����������ϻ��㹤������
	          +" FROM A01 a01 "
	          + " left join  ");//��Ա������Ϣ��
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
        + "  ");//��Աͳһ��ʶ�� ���� a08 ��¼����Ϊ��
		return sb;
	}
	
	public StringBuffer returnGbjbqkSqlZs1(StringBuffer sb,String lb) throws RadowException{
		
		if(DBType.ORACLE==DBUtil.getDBType()){
			if("".equals(lb)){//�ɲ��������ͳ�� 
				sb.append(" SELECT  "
				+ " SUM(CASE "
		             +" WHEN nvl(a.a0104,0) = 2 THEN "
		             +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) nv, "//�Ա� 2 Ů
		       +" SUM(CASE "
		             +" WHEN nvl(a.a0117,0) = 01 THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) sm, "//��������  !=01
		       +" SUM(CASE "
		             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zd, "//�й���Ա
		        +" SUM(CASE "
		             +" WHEN (a.a0141=01 or a.a0141=02) THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) fzd, "//���й���Ա   Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
		              );
			}else if("nv".equals(lb)){//�ɲ��������ͳ�� (Ů)
					sb.append(" SELECT  "
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//��������  !=01
			       +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//�й���Ա
			        +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) fzd, "//���й���Ա   Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
			              );
			}else if("shao".equals(lb)){//�ɲ��������ͳ�� (��)
					sb.append(" SELECT  "
					+ " SUM(CASE "
			             +" WHEN nvl(a.a0104,0) = 2 THEN "
			             +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) nv, "//�Ա� 2 Ů
			       +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zd, "//�й���Ա
			        +" SUM(CASE "
			             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) fzd, "//���й���Ա   Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
			              );
			}else if("fei".equals(lb)){//�ɲ��������ͳ�� (��)
					sb.append(" SELECT  "
					+ " SUM(CASE "
			             +" WHEN nvl(a.a0104,0) = 2 THEN "
			             +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) nv, "//�Ա� 2 Ů
			       +" SUM(CASE "
			             +" WHEN nvl(a.a0117,0) = 01 THEN "
			              +" 0 "
			             +" ELSE "
			              +" 1 "
			           +" END) sm, "//��������  !=01 
		           +" SUM(CASE "
		             +" WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
		              +" 0 "
		             +" ELSE "
		              +" 1 "
		           +" END) heji, "//���й���Ա   Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
			              );
			}else{
				throw new RadowException("��������!����ϵϵͳ����Ա!");
			}
			sb.append(" "
	            +" a.A0221, "//��ǰְλ���
	            + " SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '1%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjs,"//�о���
		        + "  SUM(CASE "
		             + " WHEN nvl(a.A0801B,0) like '2%' THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbk,"//��ѧ����
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '3%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzz,"//��ѧר��
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) like '4%' THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zz,"//��ר
		        +" SUM(CASE "
		             +" WHEN nvl(a.A0801B,0) in('61','71','81','91') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) gzjyx,"//���м�����
	           +" 0 bs, "//--  ��ʿ
              +" 0 ss, "//--˶ʿ
              +" 0 xs, "//--ѧʿ
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
                         + " ,2) pjnl, "//ƽ������
           +" sum( case when add_months(to_date((nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-13),'yyyy-mm-dd') "
           		+" ) ),'yyyy-mm-dd'),12)>trunc(sysdate) then 1 else 0 end ) zhccxy1, "//����1��
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-37),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),12)<=trunc(sysdate) then 1 else 0 end) zhccxy3, "//1������3��
           +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-61),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-35),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),36)<=trunc(sysdate) then 1 else 0 end) zhccxy5, "
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-121),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),120)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-59),'yyyy-mm-dd') "
	           +" ),'yyyy-mm-dd'),60)<=trunc(sysdate) then 1 else 0 end) zhccxy10, "
	       +" sum( case when add_months(to_date(nvl(decode(length(A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-119),'yyyy-mm-dd') "
          	   +" ),'yyyy-mm-dd'),120)<=trunc(sysdate) then 1 else 0 end ) zhccxy11, "
          	   + "  sum( case when nvl(a.A0197,0) = 1 then 1 else 0 end ) A0197 ");//�Ƿ�����������ϻ��㹤������
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			sb.append("SELECT  "
			+ "	SUM(     "
			+ "		CASE "
			+ "		WHEN a.a0104 = 2 THEN  "
			+ "			1 " 
			+ "		ELSE  " 
			+ "			0 " 
			+ "		END   " 
			+ "	) nv,     " //Ů
			+ "	SUM(      " 
			+ "		CASE  " 
			+ "		WHEN a.a0117 = 01 THEN "
			+ "			0 "
			+ "		ELSE  "
			+ "			1 "
			+ "		END "
			+ "	) sm,   "//��
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) zd,     "//��Ա
			+ "	SUM(      "
			+ "		CASE  "
			+ "		WHEN (a.a0141 = 01 or a.a0141 = 02) THEN "
			+ "			0  "
			+ "		ELSE   "
			+ "			1  "
			+ "		END  "
			+ "	) fzd,   "//�ǵ�Ա Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
			+ "	a.A0221, "
			+ "	SUM(     "
			+ "		CASE "
			+ "		WHEN a.A0801B LIKE '1%' THEN   "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END  "
			+ "	) yjs,   "//�о���
			+ "	SUM(     "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '2%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END  "
			+ "	) dxbk,  "//��ѧ����
			+ "	SUM(     "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '3%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END "
			+ "	) dxzz, "//��ѧר��
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN a.A0801B LIKE '4%' THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END "
			+ "	) zz,   "//��ר
			+ "	SUM(    "
			+ "		CASE  "
			+ "		WHEN a.A0801B IN ('61', '71', '81', '91') THEN    "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) gzjyx,  "//���м�����
			+ "	0 bs, "//��ʿ
			+ "	0 ss, "//˶ʿ
			+ "	0 xs, "//ѧʿ
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
			+ "	) xydy35, "//35�꼰����
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
			+ "	) dy36xy40, "//36����40��
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
			+ "	) dy41xy45, "//41����45��
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
			+ "	) dy46xy50, "//46����50��
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
			+ "	) dy51xy54, "//51����54��
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
			+ "	) dy55xy59, "//55����59��
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
			+ "	) dy60,   "//60�꼰����
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
			+ "	,2) pjnl,       "//ƽ������
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
			+ "	) zhccxy1, "//����1��
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
			+ "	) zhccxy3, "//1��������3��
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
			+ "	) zhccxy5,"//3��������5��
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
			+ "	) zhccxy10, "//5��������10��
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
			+ "	) zhccxy11,"//10�꼰����
			+ "	sum(       "
			+ "		CASE   "
			+ "		WHEN if (a.A0197='', 0,a.A0197) = 1 THEN "
			+ "			1 "
			+ "		ELSE  "
			+ "			0 "
			+ "		END   "
			+ "	) A0197 "//����2�����ϻ��㹤������
			);
		}else{
			throw new RadowException("����Դ����(����δ֪����Դ)������ϵϵͳ����Ա���ԭ��!");
		}
		
		return sb;
	}

}
