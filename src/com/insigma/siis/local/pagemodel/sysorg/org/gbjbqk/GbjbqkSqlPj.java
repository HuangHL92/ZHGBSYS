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
			
			if("4".equals(param_arr_row)){//�ܼ�
				//��������
			}else if( GbjbqkSubPageModel.xioaji.indexOf(param_arr_row)!=-1 ){//������� С��
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
				throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
			}
			
			/*sb.append(" and a01.a0221 is not null "//��Ϊ��
					+ " and a01.a0221 not in('01',"//����Ա//��Ϊ�����
					+ "'01A',"//�ۺϹ�����  //��Ϊ�����  
					+ "'01B',"//רҵ������  //��Ϊ����� 
					+ "'01C',"//����ִ����//��Ϊ�����
					+ "'02',"//���񾯲쾯Աְ������ //��Ϊ�����
					+ "'1',"//
					+ "'2',"//
					+ "'3',"//
					+ "'4',"//
					+ "'5',"//
					+ "'6',"//
					+ "'7',"// ϵͳ�Ѿ�����//��Ϊ�����
					+ "'8',"//���ھ�����ְ��//��Ϊ�����
					+ "'A',"//����������Ԥ��Ա//��Ϊ�����
					+ "'B',"//������������ϢԱ//��Ϊ�����
					+ "'9',"//��ҵ��λ����ȼ�//��Ϊ�����
					+ "'911'"//��ҵ��λ����ȼ� ��������Ա ͳ�Ʊ�񲻴��ڴ���
					+ ") ");
			if(DBType.ORACLE==DBUtil.getDBType()){
				sb.append(" and not regexp_like(a01.a0221,'^[C-G]') ");//����a-g��ͷ
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				sb.append(" and a01.a0221 regexp '^[C-G]' ");//����a-g��ͷ
			}else{
				throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
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
	 * grid �б��ѯ�ֶ�
	 * @param sb
	 * @param lb
	 * @return
	 */
	public StringBuffer returnPjGridSql(StringBuffer sb){
		sb.append(" SELECT "
				  + " a01.a0101, "//����   ��
				  + " a01.a0104, "//�Ա�
				  + " a01.a0184, "//������ݺ���   ��
				  + " a01.a0117a, "//����
				  + " a01.a0117,"
				  + " a01.a0141, "//������ò
				  + " a01.a0149,"//ְ���� ����
				  + " a01.a0192a,"//��ְ�������� ��ְ��ȫ��  ��
				  + " a01.a0221, "//��ǰְ����
				  //+ " a08.a0801b, "//ѧ������   ��
				  //+ " a08.a0901b, "//ѧλ����   ��
				  + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a01.a0107, "//��������
				  + " a01.A0288, "//����ְ����ʱ��   ��
				  + " a01.A0197"
				  
				  );//�Ƿ�����������ϻ��㹤������
		    
		return sb;
	}

}
