package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;


public class GbjbqkxwxlSql {

	public GbjbqkxwxlSql() {
		
	}
	
	/**
	 * ��ѯͳ�����ѧ��
	 * @param sb
	 */
	public void returnSqlSelect(StringBuffer sb) {
		
			sb.append(" "
					+ " SELECT  "
			       + " 0 heji,"//�ϼ�
		            +" a.A0221, "//��ǰְλ���
		            + " SUM(CASE "
			             + " WHEN (a.A0801B like '1%' and a.a0834='1') THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			           + " END) yjs,"//�о���
			        + "  SUM(CASE "
			             + " WHEN (a.A0801B like '2%' and a.a0834='1') THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			           + " END) dxbk,"//��ѧ����
			        +" SUM(CASE "
			             +" WHEN (a.A0801B like '3%' and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) dxzz,"//��ѧר��
			        +" SUM(CASE "
			             +" WHEN (a.A0801B like '4%' and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zz,"//��ר
			        +" SUM(CASE "
			             +" WHEN (a.A0801B in('61','71','81','91') and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) gzjyx,"//���м�����
//		           +" SUM(CASE "
//		                   +" WHEN (a.A0901B like '2%' or a.A0901B='1') and a.a0835='1'  THEN "
//		                    +" 1 "
//		                   +" ELSE "
//		                    +" 0 "
//		                 +" END) bs, "//--  ��ʿ
					 +" 0 bs, "//--  ��ʿ
//	              +" SUM(CASE "
//		                +" WHEN (a.A0901B like '3%' and a.a0835='1')  THEN "
//		                 +" 1 "
//		                +" ELSE "
//		                 +" 0 "
//		                 +" END) ss, "//--˶ʿ
					 +" 0 ss, "//--˶ʿ
//	              +" SUM(CASE "
//		                +" WHEN (a.A0901B like '4%' and a.a0835='1')  THEN "
//		                 +" 1 "
//		                +" ELSE "
//		                 +" 0 "
//		                 +" END) xs, "//--ѧʿ
					+" 0 xs, "//--ѧʿ 
//		      //*************** ���^^^ ****************//
//		                 + " SUM(CASE "
//			             + " WHEN (a.A0801B like '1%' and a.A0831='1') THEN "
//			              + " 1 "
//			             + " ELSE "
//			              + " 0 "
//			           + " END) yjsq,"//�о���
					 + " 0 yjsq,"//�о���
//			        + "  SUM(CASE "
//			             + " WHEN (a.A0801B like '2%' and a.A0831='1') THEN "
//			              + " 1 "
//			             + " ELSE "
//			              + " 0 "
//			           + " END) dxbkq,"//��ѧ����
					 + " 0 dxbkq,"//��ѧ����
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B like '3%' and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) dxzzq,"//��ѧר��
					 +" 0 dxzzq,"//��ѧר��
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B like '4%' and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) zzq,"//��ר
					  +" 0 zzq,"//��ר
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B in('61','71','81','91') and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) gzjyxq,"//���м�����
					  +" 0 gzjyxq,"//���м�����
//			           +" SUM(CASE "
//	                   +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0832='1')  THEN "
//	                    +" 1 "
//	                   +" ELSE "
//	                    +" 0 "
//	                 +" END) bsq, "//--  ��ʿ
					   +" 0 bsq, "//--  ��ʿ
//	          +" SUM(CASE "
//	                +" WHEN (a.A0901B like '3%' and a.A0832='1')  THEN "
//	                 +" 1 "
//	                +" ELSE "
//	                 +" 0 "
//	                 +" END) ssq, "//--˶ʿ
					    +" 0 ssq, "//--˶ʿ
//	          +" SUM(CASE "
//	                +" WHEN (a.A0901B like '4%' and a.A0832='1')  THEN "
//	                 +" 1 "
//	                +" ELSE "
//	                 +" 0 "
//	                 +" END) xsq, "//--ѧʿ
					    +" 0 xsq, "//--ѧʿ
//	          //*************** ȫ����^^^ ****************//   
//	                 + " SUM(CASE "
//		             + " WHEN (a.A0801B like '1%' and a.A0838='1') THEN "
//		              + " 1 "
//		             + " ELSE "
//		              + " 0 "
//		           + " END) yjsz,"//�о���
					    + " 0 yjsz,"//�о���
//		        + "  SUM(CASE "
//		             + " WHEN (a.A0801B like '2%' and a.A0838='1') THEN "
//		              + " 1 "
//		             + " ELSE "
//		              + " 0 "
//		           + " END) dxbkz,"//��ѧ����
					     + " 0 dxbkz,"//��ѧ����
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B like '3%' and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) dxzzz,"//��ѧר��
					      +" 0 dxzzz,"//��ѧר��
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B like '4%' and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) zzz,"//��ר
					      +" 0 zzz,"//��ר
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B in('61','71','81','91') and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) gzjyxz,"//���м�����
					      +" 0 gzjyxz,"//���м�����
//		           +" SUM(CASE "
//	               +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0839='1')  THEN "
//	                +" 1 "
//	               +" ELSE "
//	                +" 0 "
//	             +" END) bsz, "//--  ��ʿ
					      +" 0 bsz, "//--  ��ʿ
//			      +" SUM(CASE "
//			            +" WHEN (a.A0901B like '3%' and a.A0839='1')  THEN "
//			             +" 1 "
//			            +" ELSE "
//			             +" 0 "
//			             +" END) ssz, "//--˶ʿ
					      +" 0 ssz, "//--˶ʿ
//			      +" SUM(CASE "
//			            +" WHEN (a.A0901B like '4%' and a.A0839='1')  THEN "
//			             +" 1 "
//			            +" ELSE "
//			             +" 0 "
//			             +" END) xsz "
						+" 0 xsz "
			             );//--ѧʿ
			 sb.append(" FROM (SELECT "
			 		+ " a01.a0000, "//��Աͳһ��ʶ��
		  			  //+ " a08.a0839, "//�����ְѧλ
					  //+ " a08.a0838, "//�����ְѧ��
					  //+ " a08.a0832, "//���ȫ����ѧλ
					  //+ " a08.a0831, "//���ȫ����ѧ��
					  + " a08.A0834, "//���ѧ��
					  //+ " a08.A0835, "//���ѧ��
					  + " a01.A0221, "//��ǰְ����
					  + " a08.A0801B "//ѧ������   ��
					  //+ " a08.A0901B "//ѧλ����   ��
		          +" FROM A01 a01 left join  "//��Ա������Ϣ��
		       //   + " a08 a08 "// ѧ��ѧλ��
		       //  +" on a01.a0000=a08.a0000 WHERE a01.status = '1' "//��ְ״̬ 1 ����//��Աͳһ��ʶ�� ���� a08 ��¼����Ϊ��
		         + "  ");
		
	}
	
	/**
	 * ��ѯͳ�����ѧλ
	 * @param sb
	 */
	public void returnSqlSelect_zgxw(StringBuffer sb) {
		
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//��ǰְλ���
	           +" SUM(CASE "
	                   +" WHEN (a.A0901B like '2%' or a.A0901B='1') and a.a0835='1'  THEN "
	                    +" 1 "
	                   +" ELSE "
	                    +" 0 "
	                 +" END) bs, "//--  ��ʿ
              +" SUM(CASE "
	                +" WHEN (a.A0901B like '3%' and a.a0835='1')  THEN "
	                 +" 1 "
	                +" ELSE "
	                 +" 0 "
	                 +" END) ss, "//--˶ʿ
              +" SUM(CASE "
	                +" WHEN (a.A0901B like '4%' and a.a0835='1')  THEN "
	                 +" 1 "
	                +" ELSE "
	                 +" 0 "
	                 +" END) xs "//--ѧʿ
		             );//--ѧʿ
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a08.A0835, "//���ѧλ
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0901B "//ѧλ����   ��
	          +" FROM A01 a01 left join  "//��Ա������Ϣ��
	         + "  ");
		
	}
	
	/**
	 * ��ѯͳ�����ȫ����ѧ��
	 * @param sb
	 */
	public void returnSqlSelect_qrzxl(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//��ǰְλ���
	      //*************** ���ȫ����ѧ�� ****************//
	                 + " SUM(CASE "
		             + " WHEN (a.A0801B like '1%' and a.A0831='1') THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjsq,"//�о���
		        + "  SUM(CASE "
		             + " WHEN (a.A0801B like '2%' and a.A0831='1') THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbkq,"//��ѧ����
		        +" SUM(CASE "
		             +" WHEN (a.A0801B like '3%' and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzzq,"//��ѧר��
		        +" SUM(CASE "
		             +" WHEN (a.A0801B like '4%' and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zzq,"//��ר
		        +" SUM(CASE "
		             +" WHEN (a.A0801B in('61','71','81','91') and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) gzjyxq "//���м�����
		             );//--ѧʿ
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a08.a0831, "//���ȫ����ѧ��
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0801B "//ѧ������   ��
	          +" FROM A01 a01 left join  "//��Ա������Ϣ��
	         + "  ");
	}
	
	/**
	 * ��ѯͳ�����ȫ����ѧλ
	 * @param sb
	 */
	public void returnSqlSelect_qrzxw(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//��ǰְλ���
		           +" SUM(CASE "
                   +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0832='1')  THEN "
                    +" 1 "
                   +" ELSE "
                    +" 0 "
                 +" END) bsq, "//--  ��ʿ
          +" SUM(CASE "
                +" WHEN (a.A0901B like '3%' and a.A0832='1')  THEN "
                 +" 1 "
                +" ELSE "
                 +" 0 "
                 +" END) ssq, "//--˶ʿ
          +" SUM(CASE "
                +" WHEN (a.A0901B like '4%' and a.A0832='1')  THEN "
                 +" 1 "
                +" ELSE "
                 +" 0 "
                 +" END) xsq "//--ѧʿ
		             );//--ѧʿ
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a08.a0832, "//���ȫ����ѧλ
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0901B "//ѧλ����   ��
	          +" FROM A01 a01 left join  "//��Ա������Ϣ��
	         + "  ");
	}
	
	/**
	 * ��ѯͳ�������ְѧ��
	 * @param sb
	 */
	public void returnSqlSelect_zzxl(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//��ǰְλ���
                 + " SUM(CASE "
	             + " WHEN (a.A0801B like '1%' and a.A0838='1') THEN "
	              + " 1 "
	             + " ELSE "
	              + " 0 "
	           + " END) yjsz,"//�о���
	        + "  SUM(CASE "
	             + " WHEN (a.A0801B like '2%' and a.A0838='1') THEN "
	              + " 1 "
	             + " ELSE "
	              + " 0 "
	           + " END) dxbkz,"//��ѧ����
	        +" SUM(CASE "
	             +" WHEN (a.A0801B like '3%' and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) dxzzz,"//��ѧר��
	        +" SUM(CASE "
	             +" WHEN (a.A0801B like '4%' and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) zzz,"//��ר
	        +" SUM(CASE "
	             +" WHEN (a.A0801B in('61','71','81','91') and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) gzjyxz "//���м�����
		             );//--ѧʿ
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//��Աͳһ��ʶ��
				  + " a08.a0838, "//�����ְѧ��
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0801B "//ѧ������   ��
	          +" FROM A01 a01 left join  "//��Ա������Ϣ��
	         + "  ");
	}
	
	/**
	 * ��ѯͳ�������ְѧλ
	 * @param sb
	 */
	public void returnSqlSelect_zzxw(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//��ǰְλ���
	           +" SUM(CASE "
               +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0839='1')  THEN "
                +" 1 "
               +" ELSE "
                +" 0 "
             +" END) bsz, "//--  ��ʿ
		      +" SUM(CASE "
		            +" WHEN (a.A0901B like '3%' and a.A0839='1')  THEN "
		             +" 1 "
		            +" ELSE "
		             +" 0 "
		             +" END) ssz, "//--˶ʿ
		      +" SUM(CASE "
		            +" WHEN (a.A0901B like '4%' and a.A0839='1')  THEN "
		             +" 1 "
		            +" ELSE "
		             +" 0 "
		             +" END) xsz "
		             );//--ѧʿ
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//��Աͳһ��ʶ��
	  			  + " a08.a0839, "//�����ְѧλ
				  + " a01.A0221, "//��ǰְ����
				  + " a08.A0901B "//ѧλ����   ��
	          +" FROM A01 a01 left join  "//��Ա������Ϣ��
	         + "  ");
	}

}
