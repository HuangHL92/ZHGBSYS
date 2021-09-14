package com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk;


public class GbjbqkxwxlSql {

	public GbjbqkxwxlSql() {
		
	}
	
	/**
	 * 查询统计最高学历
	 * @param sb
	 */
	public void returnSqlSelect(StringBuffer sb) {
		
			sb.append(" "
					+ " SELECT  "
			       + " 0 heji,"//合计
		            +" a.A0221, "//当前职位层次
		            + " SUM(CASE "
			             + " WHEN (a.A0801B like '1%' and a.a0834='1') THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			           + " END) yjs,"//研究生
			        + "  SUM(CASE "
			             + " WHEN (a.A0801B like '2%' and a.a0834='1') THEN "
			              + " 1 "
			             + " ELSE "
			              + " 0 "
			           + " END) dxbk,"//大学本科
			        +" SUM(CASE "
			             +" WHEN (a.A0801B like '3%' and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) dxzz,"//大学专科
			        +" SUM(CASE "
			             +" WHEN (a.A0801B like '4%' and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) zz,"//中专
			        +" SUM(CASE "
			             +" WHEN (a.A0801B in('61','71','81','91') and a.a0834='1') THEN "
			              +" 1 "
			             +" ELSE "
			              +" 0 "
			           +" END) gzjyx,"//高中及以下
//		           +" SUM(CASE "
//		                   +" WHEN (a.A0901B like '2%' or a.A0901B='1') and a.a0835='1'  THEN "
//		                    +" 1 "
//		                   +" ELSE "
//		                    +" 0 "
//		                 +" END) bs, "//--  博士
					 +" 0 bs, "//--  博士
//	              +" SUM(CASE "
//		                +" WHEN (a.A0901B like '3%' and a.a0835='1')  THEN "
//		                 +" 1 "
//		                +" ELSE "
//		                 +" 0 "
//		                 +" END) ss, "//--硕士
					 +" 0 ss, "//--硕士
//	              +" SUM(CASE "
//		                +" WHEN (a.A0901B like '4%' and a.a0835='1')  THEN "
//		                 +" 1 "
//		                +" ELSE "
//		                 +" 0 "
//		                 +" END) xs, "//--学士
					+" 0 xs, "//--学士 
//		      //*************** 最高^^^ ****************//
//		                 + " SUM(CASE "
//			             + " WHEN (a.A0801B like '1%' and a.A0831='1') THEN "
//			              + " 1 "
//			             + " ELSE "
//			              + " 0 "
//			           + " END) yjsq,"//研究生
					 + " 0 yjsq,"//研究生
//			        + "  SUM(CASE "
//			             + " WHEN (a.A0801B like '2%' and a.A0831='1') THEN "
//			              + " 1 "
//			             + " ELSE "
//			              + " 0 "
//			           + " END) dxbkq,"//大学本科
					 + " 0 dxbkq,"//大学本科
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B like '3%' and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) dxzzq,"//大学专科
					 +" 0 dxzzq,"//大学专科
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B like '4%' and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) zzq,"//中专
					  +" 0 zzq,"//中专
//			        +" SUM(CASE "
//			             +" WHEN (a.A0801B in('61','71','81','91') and a.A0831='1') THEN "
//			              +" 1 "
//			             +" ELSE "
//			              +" 0 "
//			           +" END) gzjyxq,"//高中及以下
					  +" 0 gzjyxq,"//高中及以下
//			           +" SUM(CASE "
//	                   +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0832='1')  THEN "
//	                    +" 1 "
//	                   +" ELSE "
//	                    +" 0 "
//	                 +" END) bsq, "//--  博士
					   +" 0 bsq, "//--  博士
//	          +" SUM(CASE "
//	                +" WHEN (a.A0901B like '3%' and a.A0832='1')  THEN "
//	                 +" 1 "
//	                +" ELSE "
//	                 +" 0 "
//	                 +" END) ssq, "//--硕士
					    +" 0 ssq, "//--硕士
//	          +" SUM(CASE "
//	                +" WHEN (a.A0901B like '4%' and a.A0832='1')  THEN "
//	                 +" 1 "
//	                +" ELSE "
//	                 +" 0 "
//	                 +" END) xsq, "//--学士
					    +" 0 xsq, "//--学士
//	          //*************** 全日制^^^ ****************//   
//	                 + " SUM(CASE "
//		             + " WHEN (a.A0801B like '1%' and a.A0838='1') THEN "
//		              + " 1 "
//		             + " ELSE "
//		              + " 0 "
//		           + " END) yjsz,"//研究生
					    + " 0 yjsz,"//研究生
//		        + "  SUM(CASE "
//		             + " WHEN (a.A0801B like '2%' and a.A0838='1') THEN "
//		              + " 1 "
//		             + " ELSE "
//		              + " 0 "
//		           + " END) dxbkz,"//大学本科
					     + " 0 dxbkz,"//大学本科
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B like '3%' and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) dxzzz,"//大学专科
					      +" 0 dxzzz,"//大学专科
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B like '4%' and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) zzz,"//中专
					      +" 0 zzz,"//中专
//		        +" SUM(CASE "
//		             +" WHEN (a.A0801B in('61','71','81','91') and a.A0838='1') THEN "
//		              +" 1 "
//		             +" ELSE "
//		              +" 0 "
//		           +" END) gzjyxz,"//高中及以下
					      +" 0 gzjyxz,"//高中及以下
//		           +" SUM(CASE "
//	               +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0839='1')  THEN "
//	                +" 1 "
//	               +" ELSE "
//	                +" 0 "
//	             +" END) bsz, "//--  博士
					      +" 0 bsz, "//--  博士
//			      +" SUM(CASE "
//			            +" WHEN (a.A0901B like '3%' and a.A0839='1')  THEN "
//			             +" 1 "
//			            +" ELSE "
//			             +" 0 "
//			             +" END) ssz, "//--硕士
					      +" 0 ssz, "//--硕士
//			      +" SUM(CASE "
//			            +" WHEN (a.A0901B like '4%' and a.A0839='1')  THEN "
//			             +" 1 "
//			            +" ELSE "
//			             +" 0 "
//			             +" END) xsz "
						+" 0 xsz "
			             );//--学士
			 sb.append(" FROM (SELECT "
			 		+ " a01.a0000, "//人员统一标识符
		  			  //+ " a08.a0839, "//最高在职学位
					  //+ " a08.a0838, "//最高在职学历
					  //+ " a08.a0832, "//最高全日制学位
					  //+ " a08.a0831, "//最高全日制学历
					  + " a08.A0834, "//最高学历
					  //+ " a08.A0835, "//最高学历
					  + " a01.A0221, "//当前职务层次
					  + " a08.A0801B "//学历代码   等
					  //+ " a08.A0901B "//学位代码   等
		          +" FROM A01 a01 left join  "//人员基本信息表
		       //   + " a08 a08 "// 学历学位表
		       //  +" on a01.a0000=a08.a0000 WHERE a01.status = '1' "//任职状态 1 在任//人员统一标识符 关联 a08 记录可能为空
		         + "  ");
		
	}
	
	/**
	 * 查询统计最高学位
	 * @param sb
	 */
	public void returnSqlSelect_zgxw(StringBuffer sb) {
		
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//当前职位层次
	           +" SUM(CASE "
	                   +" WHEN (a.A0901B like '2%' or a.A0901B='1') and a.a0835='1'  THEN "
	                    +" 1 "
	                   +" ELSE "
	                    +" 0 "
	                 +" END) bs, "//--  博士
              +" SUM(CASE "
	                +" WHEN (a.A0901B like '3%' and a.a0835='1')  THEN "
	                 +" 1 "
	                +" ELSE "
	                 +" 0 "
	                 +" END) ss, "//--硕士
              +" SUM(CASE "
	                +" WHEN (a.A0901B like '4%' and a.a0835='1')  THEN "
	                 +" 1 "
	                +" ELSE "
	                 +" 0 "
	                 +" END) xs "//--学士
		             );//--学士
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//人员统一标识符
				  + " a08.A0835, "//最高学位
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0901B "//学位代码   等
	          +" FROM A01 a01 left join  "//人员基本信息表
	         + "  ");
		
	}
	
	/**
	 * 查询统计最高全日制学历
	 * @param sb
	 */
	public void returnSqlSelect_qrzxl(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//当前职位层次
	      //*************** 最高全日制学历 ****************//
	                 + " SUM(CASE "
		             + " WHEN (a.A0801B like '1%' and a.A0831='1') THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) yjsq,"//研究生
		        + "  SUM(CASE "
		             + " WHEN (a.A0801B like '2%' and a.A0831='1') THEN "
		              + " 1 "
		             + " ELSE "
		              + " 0 "
		           + " END) dxbkq,"//大学本科
		        +" SUM(CASE "
		             +" WHEN (a.A0801B like '3%' and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) dxzzq,"//大学专科
		        +" SUM(CASE "
		             +" WHEN (a.A0801B like '4%' and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) zzq,"//中专
		        +" SUM(CASE "
		             +" WHEN (a.A0801B in('61','71','81','91') and a.A0831='1') THEN "
		              +" 1 "
		             +" ELSE "
		              +" 0 "
		           +" END) gzjyxq "//高中及以下
		             );//--学士
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//人员统一标识符
				  + " a08.a0831, "//最高全日制学历
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0801B "//学历代码   等
	          +" FROM A01 a01 left join  "//人员基本信息表
	         + "  ");
	}
	
	/**
	 * 查询统计最高全日制学位
	 * @param sb
	 */
	public void returnSqlSelect_qrzxw(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//当前职位层次
		           +" SUM(CASE "
                   +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0832='1')  THEN "
                    +" 1 "
                   +" ELSE "
                    +" 0 "
                 +" END) bsq, "//--  博士
          +" SUM(CASE "
                +" WHEN (a.A0901B like '3%' and a.A0832='1')  THEN "
                 +" 1 "
                +" ELSE "
                 +" 0 "
                 +" END) ssq, "//--硕士
          +" SUM(CASE "
                +" WHEN (a.A0901B like '4%' and a.A0832='1')  THEN "
                 +" 1 "
                +" ELSE "
                 +" 0 "
                 +" END) xsq "//--学士
		             );//--学士
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//人员统一标识符
				  + " a08.a0832, "//最高全日制学位
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0901B "//学位代码   等
	          +" FROM A01 a01 left join  "//人员基本信息表
	         + "  ");
	}
	
	/**
	 * 查询统计最高在职学历
	 * @param sb
	 */
	public void returnSqlSelect_zzxl(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//当前职位层次
                 + " SUM(CASE "
	             + " WHEN (a.A0801B like '1%' and a.A0838='1') THEN "
	              + " 1 "
	             + " ELSE "
	              + " 0 "
	           + " END) yjsz,"//研究生
	        + "  SUM(CASE "
	             + " WHEN (a.A0801B like '2%' and a.A0838='1') THEN "
	              + " 1 "
	             + " ELSE "
	              + " 0 "
	           + " END) dxbkz,"//大学本科
	        +" SUM(CASE "
	             +" WHEN (a.A0801B like '3%' and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) dxzzz,"//大学专科
	        +" SUM(CASE "
	             +" WHEN (a.A0801B like '4%' and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) zzz,"//中专
	        +" SUM(CASE "
	             +" WHEN (a.A0801B in('61','71','81','91') and a.A0838='1') THEN "
	              +" 1 "
	             +" ELSE "
	              +" 0 "
	           +" END) gzjyxz "//高中及以下
		             );//--学士
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//人员统一标识符
				  + " a08.a0838, "//最高在职学历
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0801B "//学历代码   等
	          +" FROM A01 a01 left join  "//人员基本信息表
	         + "  ");
	}
	
	/**
	 * 查询统计最高在职学位
	 * @param sb
	 */
	public void returnSqlSelect_zzxw(StringBuffer sb) {
		sb.append(" "
				+ " SELECT  "
	            +" a.A0221, "//当前职位层次
	           +" SUM(CASE "
               +" WHEN ((a.A0901B like '2%' or a.A0901B=1) and a.A0839='1')  THEN "
                +" 1 "
               +" ELSE "
                +" 0 "
             +" END) bsz, "//--  博士
		      +" SUM(CASE "
		            +" WHEN (a.A0901B like '3%' and a.A0839='1')  THEN "
		             +" 1 "
		            +" ELSE "
		             +" 0 "
		             +" END) ssz, "//--硕士
		      +" SUM(CASE "
		            +" WHEN (a.A0901B like '4%' and a.A0839='1')  THEN "
		             +" 1 "
		            +" ELSE "
		             +" 0 "
		             +" END) xsz "
		             );//--学士
		 sb.append(" FROM (SELECT "
				 + " a01.a0000, "//人员统一标识符
	  			  + " a08.a0839, "//最高在职学位
				  + " a01.A0221, "//当前职务层次
				  + " a08.A0901B "//学位代码   等
	          +" FROM A01 a01 left join  "//人员基本信息表
	         + "  ");
	}

}
