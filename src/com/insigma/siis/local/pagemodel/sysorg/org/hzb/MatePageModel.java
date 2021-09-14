package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;

public class MatePageModel extends PageModel{ 

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  查询未匹配人员信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b0101 = this.getPageElement("b0101").getValue();
	  String query_type = this.getPageElement("query_type").getValue();
	  String colIndex = this.getPageElement("colIndex").getValue();
	  
	  String sql = "";
	  
	  if("SZDWHZB".equals(query_type)){
		  String a0201e = "1";
		  if("4".equals(colIndex)){
			  a0201e = "1";
		  }else if("6".equals(colIndex)){
			  a0201e = "3";
		  }else if("8".equals(colIndex)){
			  a0201e = "31";
		  }
		  sql = "select a01.a0000,a0101,a0192a  FROM a02, a01 WHERE a01.A0000 = a02.a0000 AND "
		  		+ " a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e='"+a0201e+"' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b='"+b0111+"'";
		  if("9".equals(colIndex)){//不占职数
			  sql = "SELECT a01.a0000,a01.a0101,a01.a0192a FROM a02, a01 "
			  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
			  		+ " and  a02.a0248 = '1' and a02.a0201b='"+b0111+"'";
		  }
	  }else if("QXZSDWNQGBPBQK".equals(query_type)){//区、县（市）直属单位年轻干部配备情况
		  String A0165 = "";
			 
		  if("4".equals(colIndex)){
			  A0165 = " ";
		  }else if("5".equals(colIndex)){
			  A0165 = " and a01.a0000 in (select m.a0000 from QXZSDWNQGBPBQK m,a02 n,b01 k where m.a0000=n.a0000 and n.a0201b=k.b0111 and n.a0281='true' "
	      		+ "		 and length(b0111)=23 and substr(b0124,1,1)<>'8' and b0194='1' and b0101 not like '%选调生%' "
	      		+ "      and (substr(k.b0131,1,2) in ('31','32','34') or k.b0131 in ('1001','1002','1004')))";
		  }else if("7".equals(colIndex)){
			  A0165 = " and qx.age < 37 ";
		  }else if("9".equals(colIndex)){
			  A0165 = " and qx.age < 37 and (qx.A0165 LIKE '%07%' OR qx.A0165 LIKE '%09%')";
		  }else if("10".equals(colIndex)){
			  A0165 = " and qx.age < 32 and (qx.A0165 LIKE '%08%' OR qx.A0165 LIKE '%13%')";
		  }
		  sql = " with QXZSDWNQGBPBQK as (\n" +
	                " select distinct a01.a0000," + 
	                "                  a01.a0101," + 
	                "                  substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6)," + 
	                "                         1," + 
	                "                         2) age," + 
	                "                  substr(a02.a0201b, 1, 15) xqdm," + 
	                "                  (select b.b0101" + 
	                "                     from b01 b" + 
	                "                    where b.b0111 = substr(a02.a0201b, 1, 15)) ssxq," + 
	                "                  A0165\r\n" + 
	                "    from A01 a01, A02 a02, B01 b01" + 
	                "   WHERE a01.a0000 = a02.a0000" + 
	                "     and a02.a0201b = b01.b0111" + 
	                "     and b01.b0111 like '001.001.004%'" + 
	                "     and length(b0111) = 23" + 
	                "     and substr(b0124, 1, 1) <> '8'" + 
	                "     and b0101 not like '%选调生%'" + 
	                "     and a02.a0281 = 'true'" + 
	                "     and a02.a0255 = '1'" + 
	                "     and (A0165 LIKE '%07%' OR A0165 LIKE '%08%' OR A0165 LIKE '%09%' OR" + 
	                "         A0165 LIKE '%13%')" + 
	                "     and a02.a0201d = '1'" + 
	                "     and a01.A0163 = '1'" + 
	                "     and a01.status = '1') \n" +
	                "   select a01.a0000,a01.a0101,a01.a0192a from QXZSDWNQGBPBQK  qx , A01 a01\n" +
	                "   where 1=1  and a01.a0000 = qx.a0000\n" + A0165+
		  			"  and xqdm like '"+b0111+"'||'%'"+""+"\n";
		  
	  }else if("SZDWZCNQGB".equals(query_type)){//区、县（市）直属单位年轻干部配备情况
		  String A0165 = "";
			 
		  if("1".equals(colIndex)){
			  A0165 = " and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or\n" +
	                    "               a01.a0165 like '%07%' or a01.a0165 like '%08%' or\n" +
	                    "               a01.a0165 like '%09%' or a01.a0165 like '%13%' or\n" +
	                    "               a01.a0165 like '%14%' or a01.a0165 like '%15%' or\n" +
	                    "               a01.a0165 like '%51%') \n" +
	                    "   ";
		  }else if("3".equals(colIndex)){
			  A0165 = "and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or\n" +
	                    "   a01.a0165 like '%07%' or a01.a0165 like '%08%' or\n" +
	                    "   a01.a0165 like '%09%' or a01.a0165 like '%13%' or\n" +
	                    "   a01.a0165 like '%14%' or a01.a0165 like '%15%' or\n" +
	                    "   a01.a0165 like '%51%') \n" +
	                    "   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<41";
		  }else if("2".equals(colIndex)){
			  A0165 = "   and (a01.a0165 like '%12%' or a01.a0165 like '%07%' or\n" +
	                    "               a01.a0165 like '%09%' or a01.a0165 like '%14%' or  a01.a0165 like '%51%')\n" +
	                    "   ";
		  }else if("5".equals(colIndex)){
			  A0165 = " and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or" + 
			  		"               a01.a0165 like '%07%' or a01.a0165 like '%08%' or" + 
			  		"               a01.a0165 like '%09%' or a01.a0165 like '%13%' or" + 
			  		"               a01.a0165 like '%14%' or a01.a0165 like '%15%' or" + 
			  		"               a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<37 ";
		  }else if("7".equals(colIndex)){
			  A0165 = " and (a01.a0165 like '%12%' or a01.a0165 like '%07%' or" + 
			  		"  a01.a0165 like '%09%' or a01.a0165 like '%14%' or  a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<37 ";
		  }else if("8".equals(colIndex)){
			  A0165 = " and (a01.a0165 like '%50%' or a01.a0165 like '%08%' or" + 
			  		"  a01.a0165 like '%13%' or a01.a0165 like '%15%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<32 ";
		  }
		  sql = " select distinct a01.a0000,a01.a0101,a01.a0192a from a01 a01,a02 b,b01  where \n" +
                  "   a01.a0000=b.a0000 \n" +
                  "   and a0163='1'\n" +
                  A0165+
                  "   and a0281='true' and a0201b like b01.b0111||'%'\n" +
                  "   and b0111= '"+b0111+"'";
		  
	  }else if("QXSLDBZHZB".equals(query_type)){//区、县（市）领导班子职数统计表
		  String b0131 = "";
		 
		  if("7".equals(colIndex)){
			  b0131 = "1001";
		  }else if("8".equals(colIndex)){
			  b0131 = "1004";
		  }else if("9".equals(colIndex)){
			  b0131 = "1003";
		  }else if("10".equals(colIndex)){
			  b0131 = "1005";
		  }else if("12".equals(colIndex)){
			  b0131 = "1006','1007";
		  }
		 
		  sql = "SELECT a01.a0000,a01.a0101,a01.a0192a FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('"+b0131+"') and b.b0111 like '"+b0111+"'||'.%')";
	  }else if("QXSPTHZB".equals(query_type)){//区、县（市）平台领导班子职数统计表
		  String a0201e = "1";
		  if("3".equals(colIndex)){
			  a0201e = "1";
		  }else if("4".equals(colIndex)){
			  a0201e = "3";
		  }
		  sql = "select a01.a0000,a0101,a0192a from a01,a02 where a01.A0000 = a02.a0000 AND "
		  		+ " a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e='"+a0201e+"' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b='"+b0111+"'";
		 
	  }else if("QXDZLDGBB".equals(query_type)){//区、县（市）党政领导干部情况统计表
		  String condition = "";
		  if("1".equals(colIndex)){
			  condition = " ";
		  }else if("2".equals(colIndex)){
			  condition = " and age<43 ";
		  }else if("4".equals(colIndex)){
			  condition = " and age<38 ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM QXDZLDGBB where xqdm ='"+b0111+"' "+condition+" )";
		 
	  }else if("QXSTLDBZJGB".equals(query_type)){//区、县（市）四套领导班子结构性干部情况统计表
		  String condition = "";
		  if("3".equals(colIndex)){
			  condition = " and a0104='2' ";
		  }else if("6".equals(colIndex)){
			  condition = " and A0141 not in ('01', '02', '03') ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in(SELECT a01.a0000 FROM a02, a01 WHERE a01.A0000 = a02.a0000 AND "
			  		+ " a02.a0281 = 'true' AND a02.a0255 = '1'"
			  		+ " and a02.a0201e in ('1', '3') "
			  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
			  		+ " and a02.a0201b like '"+b0111+"'||'%' ) "+condition;
	  }else if("SGGBTJB".equals(query_type)||"SGGBTJB2".equals(query_type)||"SGGBTJB3".equals(query_type)
			  ||"SGGBTJB4".equals(query_type)||"SGGBTJB5".equals(query_type)||"SGGBTJB6".equals(query_type)
			  ||"SGGBTJB7".equals(query_type)||"SGGBTJB8".equals(query_type)||"SGGBTJB9".equals(query_type)){//全市市管干部情况统计表、区县（市）市管干部情况统计表（市管部分）。。。
		  String[] arr= {"1=1","1=1","a0107='1960'","a0107='1961'","a0107='1962'","a0107='1963'","a0107='1964'","a0107='1965'","a0107='1966'"
				  ,"a0107='1967'","a0107='1968'","a0107='1969'","a0107='1970'","a0107='1971'","a0107='1972'","a0107='1973'","a0107='1974'"
				  ,"a0107='1975'","a0107='1976'","a0107='1977'","a0107='1978'","a0107='1979'","a0107>='1980'","a0107='1980'","a0107='1981'"
				  ,"a0107='1982'","a0107='1983'","a0107='1984'","a0107>='1985'","age<'47'","1=1","age<'42'","1=1","age<'37'","a0104='2'"
				  ,"a0141 not in ('01', '02', '03')","zgxl='大专及以下'"
				  ," zgxl='大学'","zgxw='大学硕士'"
				  ,"zgxl='研究生'","zgxw='研究生博士'"
				  ,"(qrzxl='大学' or qrzxl='研究生')","qrzxl='研究生'"};
		  String condition=arr[Integer.valueOf(colIndex)-1];
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM "+query_type+" where a0165mc ='"+b0111+"' and "+condition+"  )";
	  }else if("SGGBTJB21".equals(query_type)){//区县（市）市管干部情况统计表（党政部分）
		  String[] arr= {"1=1","1=1","a0107='1960'","a0107='1961'","a0107='1962'","a0107='1963'","a0107='1964'","a0107='1965'","a0107='1966'"
				  ,"a0107='1967'","a0107='1968'","a0107='1969'","a0107='1970'","a0107='1971'","a0107='1972'","a0107='1973'","a0107='1974'"
				  ,"a0107='1975'","a0107='1976'","a0107='1977'","a0107='1978'","a0107='1979'","a0107>='1980'","a0107='1980'","a0107='1981'"
				  ,"a0107='1982'","a0107='1983'","a0107='1984'","a0107>='1985'","age<'47'","1=1","age<'42'","1=1","age<'37'","a0104='2'"
				  ,"a0141 not in ('01', '02', '03')","zgxl='大专及以下'"
				  ," zgxl='大学'","zgxw='大学硕士'"
				  ,"zgxl='研究生'","zgxw='研究生博士'"
				  ,"(qrzxl='大学' or qrzxl='研究生')","qrzxl='研究生'"};
		  String condition=arr[Integer.valueOf(colIndex)-1];
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM "+query_type+" where a0201e ='"+b0111+"' and "+condition+"  )";
	  }else if("QSXZDZZZPBB".equals(query_type)){//全市乡镇（街道）党政正职配备情况统计表
		  String condition = "";
		  if("2".equals(colIndex)){
			  condition = " ";
		  }else if("3".equals(colIndex)){
			  condition = " and age<38 ";
		  }else if("5".equals(colIndex)){
			  condition = " and a0132=1 ";
		  }else if("6".equals(colIndex)) {
			  condition =" and a0132=1 and age<38 ";
		  }else if("8".equals(colIndex)) {
			  condition =" and a0133=1 ";
		  }else if("9".equals(colIndex)) {
			  condition =" and a0133=1 and age<38 ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM QSXZDZZZPBB where xqdm ='"+b0111+"' "+condition+" )";
	  }else if("XZLDBZNQGBPBQK".equals(query_type)){//乡镇领导班子年轻干部配备情况（2021.06）
		  String condition = "";
		  if("2".equals(colIndex)){
			  condition = " and (a0132='1' or a0133='1')";
		  }else if("5".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and a0107>='198511' ";
		  }else if("6".equals(colIndex)) {
			  condition =" and a0132='1' and a0107>='198511' ";
		  }else if("10".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and a0107>='199011'  ";
		  }else if("12".equals(colIndex)) {
			  condition =" and  a0107>='198511'  ";
		  }else if("15".equals(colIndex)) {
			  condition =" and  a0107>='199011'";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM XZLDBZNQGBPBQK where xqdm ='"+b0111+"' "+condition+" )";
	  }else if("XZLDBZNQGBPBQKK".equals(query_type)){//乡镇领导班子年轻干部配备情况
		  String condition = "";
		  if("3".equals(colIndex)){
			  condition = " and  b0124  in('81','82')";
		  }else if("4".equals(colIndex)) {
			  condition =" and b0124  in('81','82') and (a0132='1' or a0133='1') ";
		  }else if("7".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and  b0124  in('81','82') and age<36 ";
		  }else if("8".equals(colIndex)) {
			  condition =" and a0132='1' and  b0124  in('81','82') and  age<36";
		  }else if("12".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and b0124  in('81','82')  and age<31 ";
		  }else if("14".equals(colIndex)) {
			  condition =" and  b0124  in('81','82')  and age<36 ";
		  }else if("17".equals(colIndex)) {
			  condition =" and  b0124  in('81','82')  and age<31  ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM XZLDBZNQGBPBQKK where xqdm ='"+b0111+"' "+condition+" )";
	  }else if("SGGBTJB15".equals(query_type)){//市直单位市管干部情况统计表
		  String condition = "";
		  if("1".equals(colIndex)){
			  condition = " ";
		  }else if("2".equals(colIndex)) {
			  condition =" and a0165 like '%10%'";
		  }else if("3".equals(colIndex)) {
			  condition =" and a0165 like '%11%'";
		  }else if("4".equals(colIndex)) {
			  condition =" and (a0165 like '%18%' or a0165 like '%19%')";
		  }else if("5".equals(colIndex)) {
			  condition =" and  a0107>='197407' ";
		  }else if("7".equals(colIndex)) {
			  condition =" and  a0107>='197907' ";
		  }else if("9".equals(colIndex)) {
			  condition =" and  a0107>='198407' ";
		  }else if("10".equals(colIndex)) {
			  condition =" and  a0104='2' ";
		  }else if("11".equals(colIndex)) {
			  condition =" and a0141 not in ('01', '02', '03') ";
		  }else if("12".equals(colIndex)) {
			  condition =" and  (qrzxl='大学' or qrzxl='研究生') ";
		  }else if("13".equals(colIndex)) {
			  condition =" and  qrzxl='研究生'";
		  }
		  
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SGGBTJB15 where b0101 ='"+b0101+"' "+condition+" )";
	  }else if("XZJDLDBZNQGBPZQK".equals(query_type)){//乡镇（街道）领导班子年轻干部配备情况
		  String condition = "";
		  if("3".equals(colIndex)){
			  condition = " ";
		  }else if("4".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') ";
		  }else if("7".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and age<37 ";
		  }else if("8".equals(colIndex)) {
			  condition =" and a0132='1' and  age<37";
		  }else if("13".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and age<36 ";
		  }else if("14".equals(colIndex)) {
			  condition =" and a0132='1' and  age<36";
		  }else if("18".equals(colIndex)) {
			  condition =" and (a0132='1' or a0133='1') and b0124  in('81','82','83')  and age<31 ";
		  }else if("20".equals(colIndex)) {
			  condition =" and b0124  in('81','82','83')  and age<36 ";
		  }else if("23".equals(colIndex)) {
			  condition =" and  b0124  in('81','82','83')  and age<31 ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM XZJDLDBZNQGBPZQK where xqdm ='"+b0111+"' "+condition+" )";
	  }else if("SZDWZCLDGBPBQKK".equals(query_type)) {
		  
		  String sqlcondi[]=new String[32];
		  
		  sqlcondi[0]=" and a0107='1960' ";
		  sqlcondi[1]=" and a0107='1961' ";
		  sqlcondi[2]=" and a0107='1962' ";
		  sqlcondi[3]=" and a0107='1963' ";
		  sqlcondi[4]=" and a0107='1964' ";
		  sqlcondi[5]=" and a0107='1965' ";
		  sqlcondi[6]=" and a0107>'1979' ";
		  sqlcondi[7]=" and a0107='1980' ";
		  sqlcondi[8]=" and a0107='1981' ";
		  sqlcondi[9]=" and a0107='1982' ";
		  sqlcondi[10]=" and a0107='1983' ";
		  sqlcondi[11]=" and a0107='1984' ";
		  sqlcondi[12]=" and a0107='1985' ";
		  sqlcondi[13]=" and a0107='1986' ";
		  sqlcondi[14]=" and a0107='1987' ";
		  sqlcondi[15]=" and a0107>'1987' ";
		  sqlcondi[16]=" and a0107>'1979' and a0165mc='市直正处' ";
		  sqlcondi[17]=" and a0107='1980' and a0165mc='市直正处' ";
		  sqlcondi[18]=" and a0107='1981' and a0165mc='市直正处' ";
		  sqlcondi[19]=" and a0107='1982' and a0165mc='市直正处' ";
		  sqlcondi[20]=" and a0107='1983' and a0165mc='市直正处' ";
		  sqlcondi[21]=" and a0107='1984' and a0165mc='市直正处' ";
		  sqlcondi[22]=" and a0107>'1984' and a0165mc='市直正处' ";
		  sqlcondi[23]=" and a0104='2' ";
		  sqlcondi[24]=" and a0104='2' and a0165mc='市直正处' ";
		  sqlcondi[25]=" and a0141 not in ('01', '02', '03') ";
		  sqlcondi[26]=" and a0141 not in ('01', '02', '03') and a0165mc='市直正处' ";
		  sqlcondi[27]=" and a0801b  in ('22', '2C', '2D','2F','41','44','47','6','7','8','9') ";
		  sqlcondi[28]=" and a0801b  in ('21', '23', '24','2A','2B','2E') ";
		  sqlcondi[29]=" and substr(a0801b,1,1)='1' ";
		  sqlcondi[30]=" and a0801b  in ('21', '23', '24','2A','2B','2E') and a0837='1' ";
		  sqlcondi[31]=" and substr(a0801b,1,1)='1' and a0837='1' ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col<=17 && col>=11) {
			  String condition = sqlcondi[col-11];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where dep='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=19) {
			  String condition = sqlcondi[col-12];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where dep='"+b0111 +"'  "+condition+"  )";
		  }
		  
		  if(col==5)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where dep='"+b0111 +"')";
		  if(col==6)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where dep='"+b0111 +"' and a0201e='1')";
		  if(col==7)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where dep='"+b0111 +"' and a0201e='3')";
	  }else if("XZLDGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==2) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  and b0124 in ('81','82') )";
		  }else  if(col<=38) {
			  String condition = sqlcondi[col-5];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+" and b0124 in ('81','82') )";
		  }
		  if(col==39) {
			  String condition = sqlcondi[col-6];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+" and b0124 in ('81','82') )";
		  }
		  if(col==41) {
			  String condition = sqlcondi[col-7];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+" and b0124 in ('81','82') )";
		  }
		  if(col>=43) {
			  String condition = sqlcondi[col-8];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+" and b0124 in ('81','82') )";
		  }
		  System.out.println(sql);
	  }else if("XZJDLDGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==2) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  )";
		  }else  if(col<=38) {
			  String condition = sqlcondi[col-5];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }  
		  if(col==39) {
			  String condition = sqlcondi[col-6];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col==41) {
			  String condition = sqlcondi[col-7];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=43) {
			  String condition = sqlcondi[col-8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM xzjdldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  System.out.println(sql);
	  }else if("QXCJGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==1) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxcjgbpb where a0201e='"+b0111 +"' )";
		  }else if(col<=36) {
			  String condition = sqlcondi[col-3];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxcjgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }  
		  if(col==37) {
			  String condition = sqlcondi[col-4];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxcjgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col==39) {
			  String condition = sqlcondi[col-5];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxcjgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=41) {
			  String condition = sqlcondi[col-6];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxcjgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  System.out.println(sql);
	  }else if("QXSZSDWGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==1) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxszsdwgbpb where a0201e='"+b0111 +"'  )";
		  }else if(col<=36) {
			  String condition = sqlcondi[col-3];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxszsdwgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }  
		  if(col==37) {
			  String condition = sqlcondi[col-4];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxszsdwgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col==39) {
			  String condition = sqlcondi[col-5];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxszsdwgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=41) {
			  String condition = sqlcondi[col-6];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxszsdwgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  System.out.println(sql);
	  }else if("QXSDZBMGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==1) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxsdzldgbpb where a0201e='"+b0111 +"' )";
		  }else if(col<=36) {
			  String condition = sqlcondi[col-3];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxsdzldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }  
		  if(col==37) {
			  String condition = sqlcondi[col-4];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxsdzldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col==39) {
			  String condition = sqlcondi[col-5];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxsdzldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=41) {
			  String condition = sqlcondi[col-6];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM qxsdzldgbpb where a0201e='"+b0111 +"'  "+condition+"  )";
		  }
		  System.out.println(sql);
	  }else if("SZDWCJZCGBPB".equals(query_type)) {
		  
		  String sqlcondi[]=new String[44];
		  
		  sqlcondi[0]=" and a0107='1961' ";
		  sqlcondi[1]=" and a0107='1962' ";
		  sqlcondi[2]=" and a0107='1963' ";
		  sqlcondi[3]=" and a0107='1964' ";
		  sqlcondi[4]=" and a0107='1965' ";
		  sqlcondi[5]=" and a0107='1966' ";
		  sqlcondi[6]=" and a0107='1967' ";
		  sqlcondi[7]=" and a0107='1968' ";
		  sqlcondi[8]=" and a0107='1969' ";
		  sqlcondi[9]=" and a0107='1970' ";
		  sqlcondi[10]=" and a0107='1971' ";
		  sqlcondi[11]=" and a0107='1972' ";
		  sqlcondi[12]=" and a0107='1973' ";
		  sqlcondi[13]=" and a0107='1974' ";
		  sqlcondi[14]=" and a0107='1975' ";
		  sqlcondi[15]=" and a0107='1976' ";
		  sqlcondi[16]=" and a0107='1977'  ";
		  sqlcondi[17]=" and a0107='1978'  ";
		  sqlcondi[18]=" and a0107='1979'  ";
		  sqlcondi[19]=" and a0107>='1980' ";
		  sqlcondi[20]=" and a0107='1980' ";
		  sqlcondi[21]=" and a0107='1981'  ";
		  sqlcondi[22]=" and a0107='1982'  ";
		  sqlcondi[23]=" and a0107='1983' ";
		  sqlcondi[24]=" and a0107='1984' ";
		  sqlcondi[25]=" and a0107='1985'  ";
		  sqlcondi[26]=" and a0107='1986' ";
		  sqlcondi[27]=" and a0107='1987' ";
		  sqlcondi[28]=" and a0107='1988' ";
		  sqlcondi[29]=" and a0107='1989' ";
		  sqlcondi[30]=" and a0107='1990' ";
		  sqlcondi[31]=" and a0107>='1991' ";
		  sqlcondi[32]=" and age<42 ";
		  sqlcondi[33]=" and  age<37 ";
		  sqlcondi[34]=" and age<32 ";
		  sqlcondi[35]=" and a0104=2 ";
		  sqlcondi[36]=" and a0141 not in ('01', '02', '03')";
		  sqlcondi[37]=" and zgxl='大专及以下' ";
		  sqlcondi[38]=" and zgxl='大学' ";
		  sqlcondi[39]=" and zgxw='大学硕士'  ";
		  sqlcondi[40]=" and zgxl='研究生'  ";
		  sqlcondi[41]=" and  zgxw='研究生博士'  ";
		  sqlcondi[42]=" and (rzxl='大学' or qrzxl='研究生')  ";
		  sqlcondi[43]=" and qrzxl='研究生'   ";
		  
		  int col=Integer.parseInt(colIndex);
		  
		  if(col==1) {
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM szdwcjzcgbpb where substr(a0201e,'2',length(a0201e))='"+b0111 +"'  )";
		  }else if(col<=36) {
			  String condition = sqlcondi[col-3];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM szdwcjzcgbpb where substr(a0201e,'2',length(a0201e))='"+b0111 +"'  "+condition+"  )";
		  }  
		  if(col==37) {
			  String condition = sqlcondi[col-4];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM szdwcjzcgbpb where substr(a0201e,'2',length(a0201e))='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col==38) {
			  String condition = sqlcondi[col-5];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM szdwcjzcgbpb where substr(a0201e,'2',length(a0201e))='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=41) {
			  String condition = sqlcondi[col-6];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM szdwcjzcgbpb where substr(a0201e,'2',length(a0201e))='"+b0111 +"'  "+condition+"  )";
		  }
		  System.out.println(sql);
	  }else if("SZDWZCLDGBPBQKDEP".equals(query_type)) {
		  int col=Integer.parseInt(colIndex);
		  String sqlcondi[]=new String[34];
		  
		  sqlcondi[0]=" and a0107='1960' ";
		  sqlcondi[1]=" and a0107='1961' ";
		  sqlcondi[2]=" and a0107='1962' ";
		  sqlcondi[3]=" and a0107='1963' ";
		  sqlcondi[4]=" and a0107='1964' ";
		  sqlcondi[5]=" and a0107='1965' ";
		  sqlcondi[6]=" and a0107>'1979' ";
		  sqlcondi[7]=" and a0107='1980' ";
		  sqlcondi[8]=" and a0107='1981' ";
		  sqlcondi[9]=" and a0107='1982' ";
		  sqlcondi[10]=" and a0107='1983' ";
		  sqlcondi[11]=" and a0107='1984' ";
		  sqlcondi[12]=" and a0107='1985' ";
		  sqlcondi[13]=" and a0107='1986' ";
		  sqlcondi[14]=" and a0107='1987' ";
		  sqlcondi[15]=" and a0107>'1987' ";
		  sqlcondi[16]=" and a0107>'1979' and a0165mc='市直正处' ";
		  sqlcondi[17]=" and a0107='1980' and a0165mc='市直正处' ";
		  sqlcondi[18]=" and a0107='1981' and a0165mc='市直正处' ";
		  sqlcondi[19]=" and a0107='1982' and a0165mc='市直正处' ";
		  sqlcondi[20]=" and a0107='1983' and a0165mc='市直正处' ";
		  sqlcondi[21]=" and a0107='1984' and a0165mc='市直正处' ";
		  sqlcondi[22]=" and a0107>'1984' and a0165mc='市直正处' ";
		  sqlcondi[23]=" and a0104='2' ";
		  sqlcondi[24]=" and a0104='2' and a0165mc='市直正处' ";
		  sqlcondi[25]=" and a0141 not in ('01', '02', '03') ";
		  sqlcondi[26]=" and a0141 not in ('01', '02', '03') and a0165mc='市直正处' ";
		  sqlcondi[27]=" and zgxl='大专及以下' ";
		  sqlcondi[28]=" and zgxl='大学' ";
		  sqlcondi[29]=" and zgxw='大学硕士' ";
		  sqlcondi[30]=" and zgxl='研究生' ";
		  sqlcondi[31]=" and zgxw='研究生博士' ";
		  sqlcondi[32]=" and (qrzxl='大学' or qrzxl='研究生') ";
		  sqlcondi[33]=" and qrzxl='研究生' ";
		  
		  
		  if(col<=17 && col>=11) {
			  String condition = sqlcondi[col-11];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where b0101='"+b0111 +"'  "+condition+"  )";
		  }
		  if(col>=19) {
			  String condition = sqlcondi[col-12];
			  //String comp[]=new String[8];
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where b0101='"+b0111 +"'  "+condition+"  )";
		  }
		  
		  if(col==5)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where b0101='"+b0111 +"'  )";
		  if(col==6)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where b0101='"+b0111 +"'  and a0201e='1')";
		  if(col==7)
			  sql="select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM SZDWZCLDGBPBQK where b0101='"+b0111 +"'  and a0201e='3')";
		  System.out.println(sql);
	  }else if("XSQDZLDBZ".equals(query_type)) {
		  String condition="";
		  if("1".equals(colIndex)){
			  condition = " ";
		  }else if("2".equals(colIndex)){
			  condition = " and age<=42 ";
		  }else if("3".equals(colIndex)){
			  condition = " and age<40 ";
		  }else if("4".equals(colIndex)) {
			  condition =" and age>=41 and age<=45 ";
		  }else if("5".equals(colIndex)) {
			  condition =" and age>=46 and age<=50  ";
		  }else if("6".equals(colIndex)) {
			  condition =" and age>=51 and age<=55 ";
		  }else if("7".equals(colIndex)) {
			  condition =" and age>=56 and age<=60  ";
		  }else if("9".equals(colIndex)) {
			  condition =" and a0107>=1970 ";
		  }else if("10".equals(colIndex)) {
			  condition =" and a0107>=1975 ";
		  }else if("11".equals(colIndex)) {
			  condition =" and a0107>=1980 ";
		  }else if("12".equals(colIndex)) {
			  condition =" and age<=42 and a0201e='1' ";
		  }else if("13".equals(colIndex)) {
			  condition =" and zgxl='研究生' ";
		  }else if("14".equals(colIndex)) {
			  condition =" and zgxl='研究生' and age<=42 ";
		  }else if("15".equals(colIndex)) {
			  condition =" and zgxl='本科' ";
		  }else if("16".equals(colIndex)) {
			  condition =" and zgxl='本科' and age<=42 ";
		  }else if("17".equals(colIndex)) {
			  condition =" and zgxl='大专及以下' ";
		  }else if("18".equals(colIndex)) {
			  condition =" and zgxl='大专及以下'  and age<=42";
		  }else if("19".equals(colIndex)) {
			  condition =" and qrzxl='博士'  ";
		  }else if("20".equals(colIndex)) {
			  condition =" and qrzxl='博士'  and age<=42 ";
		  }else if("21".equals(colIndex)) {
			  condition =" and qrzxl='硕士' ";
		  }else if("22".equals(colIndex)) {
			  condition =" and qrzxl='硕士'  and age<=42 ";
		  }else if("23".equals(colIndex)) {
			  condition =" and qrzxl='本科' ";
		  }else if("24".equals(colIndex)) {
			  condition =" and qrzxl='本科'  and age<=42 ";
		  }else if("25".equals(colIndex)) {
			  condition =" and qrzxl='大专及以下'";
		  }else if("26".equals(colIndex)) {
			  condition =" and qrzxl='大专及以下'  and age<=42 ";
		  }else if("27".equals(colIndex)) {
			  condition =" and a0104='女' ";
		  }else if("28".equals(colIndex)) {
			  condition =" and a0104='女' and age<=42";
		  }else if("29".equals(colIndex)) {
			  condition =" and a0104='女' and a0201e='1' ";
		  }else if("30".equals(colIndex)) {
			  condition =" and a0141='党外干部' ";
		  }else if("31".equals(colIndex)) {
			  condition =" and a0141='党外干部' and age<=42  ";
		  }else if("32".equals(colIndex)) {
			  condition =" and a0141='党外干部' and a0201e='1'";
		  }
		  System.out.println(b0111+"sdadsadsssssssssssssssssssss");
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM XSQDZLDBZ where xqdm ='"+b0111+"' "+condition+" )";
		  
	  }else if("QXSCJLDGBYGQKTJ".equals(query_type)) {
		  String condition="";
		  if("1".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%')";
		  }else if("2".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("3".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%'or a01.a0165 like '%07%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("4".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%08%' or a01.a0165 like '%50%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("7".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl like '%研究生%' and qrzxw like '%博士%')";
		  }else if("8".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl like '%研究生%' and qrzxw like '%硕士%')";
		  }else if("9".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and qrzxl like '%大学%' ";
		  }else if("10".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl not like '%大学%' and  qrzxl not like '%研究生%') ";
		  }
		    
		  sql = "select a0000,a0101,a0192a from A01 a01 where  1 = 1 and a0163 = '1' and a01.STATUS = '1' "+condition;
	  }else if("QXSCJLDGBYGQKTJ".equals(query_type)) {
		  String condition="";
		  if("1".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%')";
		  }else if("2".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("3".equals(colIndex)){
			  condition = "and (a01.a0165 like '%12%'or a01.a0165 like '%07%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("4".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%08%' or a01.a0165 like '%50%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43";
		  }else if("7".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl like '%研究生%' and qrzxw like '%博士%')";
		  }else if("8".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl like '%研究生%' and qrzxw like '%硕士%')";
		  }else if("9".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and qrzxl like '%大学%' ";
		  }else if("10".equals(colIndex)) {
			  condition ="and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or a01.a0165 like '%07%' or a01.a0165 like '%08%' or a01.a0165 like '%51%') and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)  <43 and (qrzxl not like '%大学%' and  qrzxl not like '%研究生%') ";
		  }
		    
		  sql = "select a0000,a0101,a0192a from A01 a01 where  1 = 1 and a0163 = '1' and a01.STATUS = '1' "+condition;
	  }else if("QGGBDWQK".equals(query_type)) {
		  String condition="";
		  if("4".equals(colIndex)){
			  condition = " and (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  ";
		  }else if("6".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.age<=40 ";
		  }else if("7".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.age<=45 and  b.age>=41 ";
		  }else if("8".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.age<=50 and  b.age>=46  ";
		  }else if("9".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.age<=55 and  b.age>=51 ";
		  }else if("10".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and  b.age>=56  ";
		  }else if("11".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and  b.a0160='01' ";
		  }else if("12".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and  b.a0160='02'  ";
		  }else if("13".equals(colIndex)) {
			  condition=" and (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and  b.a0160='03' ";
		  }else if("14".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and  b.a0160='05' ";
		  }else if("15".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.workage>=30  ";
		  }else if("16".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '07') <> 0 or instr(b.a0165, '09') <> 0) and b.a0219='1'  and b.workage>=20 and  b.workage<30 and b.age>=55   ";
		  }else if("18".equals(colIndex)) {
			  condition=" and (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'     ";
		  }else if("20".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.age<=40    ";
		  }else if("21".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.age<=45 and  b.age>=41   ";
		  }else if("22".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.age<=50 and  b.age>=46    ";
		  }else if("23".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.age<=55 and  b.age>=51    ";
		  }else if("24".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and  b.age>=56    ";
		  }else if("25".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and  b.a0160='01'   ";
		  }else if("26".equals(colIndex)) {
			  condition="  and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and  b.a0160='02'  ";
		  }else if("27".equals(colIndex)) {
			  condition="  and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and  b.a0160='03'  ";
		  }else if("28".equals(colIndex)) {
			  condition="  and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and  b.a0160='05'  ";
		  }else if("29".equals(colIndex)) {
			  condition="  and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.workage>=30  ";
		  }else if("30".equals(colIndex)) {
			  condition=" and  (instr(b.a0165, '08') <> 0 or instr(b.a0165, '13') <> 0) and b.a0219='1'  and b.workage>=20 and  b.workage<30 and b.age>=55   ";
		  }else if("32".equals(colIndex)) {
			  condition="  and   b.a0219='2' and b.a0192e='1A13'   ";
		  }else if("34".equals(colIndex)) {
			  condition="  and   b.a0219='2' and b.a0192e='1A14'   ";
		  }else if("36".equals(colIndex)) {
			  condition="  and   b.a0219='2' and b.a0192e='1A15' ";
		  }else if("38".equals(colIndex)) {
			  condition="  and   b.a0219='2' and b.a0192e='1A16'   ";
		  }
		  
		  System.out.println(b0111+"/////////////////////////////////");
		  sql = "select a0000,a0101,a0192a from A01 a01 where  1 = 1 and a0163 = '1' and a01.STATUS = '1' and a01.a0000 in( select a0000 from QGGBDWQK b where  b.b0111 = '"+b0111+"' "+condition+")";
		  System.out.println(sql);
	  }else if("CJNQGBTJ1".equals(query_type)){//
		  String condition = "";
		  if("1".equals(colIndex)){
			  condition = "  (instr(a0165, '10') <> 0 or instr(a0165, '11') <> 0 or " + 
			  		"       instr(a0165, '18') <> 0)  ";
		  }else if("2".equals(colIndex)){
			  condition = "  instr(a0165, '10') <> 0  ";
		  }else if("3".equals(colIndex)){
			  condition = "  instr(a0165, '10') <> 0 and a0107>197505 ";
		  }else if("4".equals(colIndex)) {
			  condition ="  instr(a0165, '10') <> 0 and a0107>198003  ";
		  }else if("5".equals(colIndex)) {
			  condition ="  instr(a0165, '10') <> 0 and a0104='2'  ";
		  }else if("6".equals(colIndex)) {
			  condition ="  instr(a0165, '10') <> 0 and a0141 not in ('01', '02', '03')  ";
		  }else if("7".equals(colIndex)){
			  condition = "  ( instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0)  ";
		  }else if("8".equals(colIndex)){
			  condition = "  ( instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0) and a0107>198003 ";
		  }else if("9".equals(colIndex)) {
			  condition ="  ( instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0) and a0107>198503  ";
		  }else if("10".equals(colIndex)) {
			  condition ="  ( instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0) and a0104='2'  ";
		  }else if("11".equals(colIndex)) {
			  condition ="  ( instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0) and a0141 not in ('01', '02', '03')  ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM CJNQGBTJ where "+condition+" )";
	  }else if("CJNQGBTJ2".equals(query_type)){//
		  String condition = "";
		  if("1".equals(colIndex)){
			  condition = "  (instr(a0165, '07') <> 0 or instr(a0165, '08') <> 0 or " + 
			  		"       instr(a0165, '12') <> 0 or instr(a0165, '14') <> 0 or instr(a0165, '15') <> 0 " + 
			  		"       or instr(a0165, '50') <> 0 or instr(a0165, '51') <> 0)  ";
		  }else if("2".equals(colIndex)){
			  condition = " (instr(a0165, '07') <> 0 or instr(a0165, '12') <> 0 " + 
			  		"    or instr(a0165, '14') <> 0  or instr(a0165, '51') <> 0)   ";
		  }else if("3".equals(colIndex)){
			  condition = "  (instr(a0165, '07') <> 0 or instr(a0165, '12') <> 0 " + 
			  		"    or instr(a0165, '14') <> 0  or instr(a0165, '51') <> 0) and a0107>197505 ";
		  }else if("4".equals(colIndex)) {
			  condition =" (instr(a0165, '07') <> 0 or instr(a0165, '12') <> 0 " + 
			  		"    or instr(a0165, '14') <> 0  or instr(a0165, '51') <> 0) and a0107>198003 ";
		  }else if("5".equals(colIndex)) {
			  condition ="  (instr(a0165, '07') <> 0 or instr(a0165, '12') <> 0 " + 
			  		"    or instr(a0165, '14') <> 0  or instr(a0165, '51') <> 0) and a0104='2'  ";
		  }else if("6".equals(colIndex)) {
			  condition ="  (instr(a0165, '07') <> 0 or instr(a0165, '12') <> 0 " + 
			  		"    or instr(a0165, '14') <> 0  or instr(a0165, '51') <> 0) and a0141 not in ('01', '02', '03')  ";
		  }else if("7".equals(colIndex)){
			  condition = "  ( instr(a0165, '8') <> 0 or instr(a0165, '15') <> 0 or instr(a0165, '50') <> 0)  ";
		  }else if("8".equals(colIndex)){
			  condition = "  ( instr(a0165, '8') <> 0 or instr(a0165, '15') <> 0 or instr(a0165, '50') <> 0)  and a0107>198003 ";
		  }else if("9".equals(colIndex)) {
			  condition ="  ( instr(a0165, '8') <> 0 or instr(a0165, '15') <> 0 or instr(a0165, '50') <> 0)  and a0107>198503  ";
		  }else if("10".equals(colIndex)) {
			  condition ="  ( instr(a0165, '8') <> 0 or instr(a0165, '15') <> 0 or instr(a0165, '50') <> 0)  and a0104='2'  ";
		  }else if("11".equals(colIndex)) {
			  condition ="  ( instr(a0165, '8') <> 0 or instr(a0165, '15') <> 0 or instr(a0165, '50') <> 0)  and a0141 not in ('01', '02', '03')   ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM CJNQGBTJ where "+condition+" )";
	  }else if("XSQLDBZTJ".equals(query_type)){//
		  String condition = "";
		  if("1".equals(colIndex)){
			  condition = "  bz='党政' ";
		  }else if("2".equals(colIndex)){
			  condition = " bz='党政'and a0107>198003 ";
		  }else if("3".equals(colIndex)){
			  condition = " bz='党政'and a0107>198503 ";
		  }else if("4".equals(colIndex)) {
			  condition =" bz='党政'and a0188='1' ";
		  }else if("6".equals(colIndex)){
			  condition = " bz='党政'and b0114 like '%0' and a0104='2'  ";
		  }else if("7".equals(colIndex)){
			  condition = "  bz='党政'and b0114 like '%0' and a0104='2'and a0201e='1' ";
		  }else if("8".equals(colIndex)) {
			  condition ="   bz='党政'and b0114 like '%1' and a0104='2'  ";
		  }else if("9".equals(colIndex)) {
			  condition ="  bz='党政'and b0114 like '%1' and a0104='2'and a0201e='1'  ";
		  }else if("10".equals(colIndex)) {
			  condition ="  bz='党政'and a0107>197505 and a0201e='1'   ";
		  }else if("11".equals(colIndex)){
			  condition = "  bz='党政'and a0107>198003 and a0201e='1'  ";
		  }else if("12".equals(colIndex)){
			  condition = "  bz='政协' ";
		  }else if("13".equals(colIndex)) {
			  condition ="  bz='政协'and  a0141 not in ('01', '02', '03')  and a0201e='3'  ";
		  }else if("14".equals(colIndex)) {
			  condition ="  bz='政协'and a0107>196305  ";
		  }
		  sql = "select a0000,a0101,a0192a from a01 where a0000 in (SELECT a0000 FROM XSQLDBZTJ where b0111 ='"+b0111+"' and  "+condition+" )";
	  }else if("HZSFNFZGHTJ".equals(query_type)) {
		  if("1".equals(colIndex)&&"001.002".equals(b0111)){
			  sql = "select distinct a01.a0000,a0101,a0192a\r\n" + 
			  		"from ZHGBSYS.A01 a01 \r\n" + 
			  		"left join ZHGBSYS.A02 a02 on a01.a0000=a02.a0000 \r\n" + 
			  		"left join ZHGBSYS.B01 b01 on a02.a0201b=b01.b0111\r\n" + 
			  		"where a01.A0163='1' and a01.nl<'46'and a01.status='1' and (A01.A0165 like '%03%' or A01.A0165 like '%05%') \r\n" + 
			  		"and  a02.a0255='1' and a02.a0281='true' and a02.a0201b like '001.001.002%'";
		  }else if("2".equals(colIndex)&&"001.002".equals(b0111)) {
			  sql = "select distinct a01.a0000,a0101,a0192a\r\n" + 
				  		"from ZHGBSYS.A01 a01 \r\n" + 
				  		"left join ZHGBSYS.A02 a02 on a01.a0000=a02.a0000 \r\n" + 
				  		"left join ZHGBSYS.B01 b01 on a02.a0201b=b01.b0111\r\n" + 
				  		"where a01.A0163='1' and a0104='2' and a01.nl<'46'and a01.status='1' and (A01.A0165 like '%03%' or A01.A0165 like '%05%') \r\n" + 
				  		"and  a02.a0255='1' and a02.a0281='true' and a02.a0201b like '001.001.002%'";
		  }else if("1".equals(colIndex)&&"001.003".equals(b0111)) {
			  sql="select distinct a01.a0000,a0101,a0192a\r\n" + 
			  		"from ZHGBSYS.A01 a01 \r\n" + 
			  		"where  1 = 1\r\n" + 
			  		"and a0163 = '1'\r\n" + 
			  		"and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or\r\n" + 
			  		"a01.a0165 like '%07%' or a01.a0165 like '%08%' or\r\n" + 
			  		"a01.a0165 like '%51%')\r\n" + 
			  		"and a01.STATUS = '1'\r\n" + 
			  		"and a01.nl<'36'";
		  }else if("2".equals(colIndex)&&"001.003".equals(b0111)) {
			  sql="select distinct a01.a0000,a0101,a0192a\r\n" + 
				  		"from ZHGBSYS.A01 a01 \r\n" + 
				  		"where  1 = 1\r\n" + 
				  		"and a0163 = '1'\r\n" + 
				  		"and (a01.a0165 like '%12%' or a01.a0165 like '%50%' or\r\n" + 
				  		"a01.a0165 like '%07%' or a01.a0165 like '%08%' or\r\n" + 
				  		"a01.a0165 like '%51%')\r\n" + 
				  		"and a01.STATUS = '1'\r\n" + 
				  		"and a01.nl<'36' and a01.a0104='2'";
		  }else if("1".equals(colIndex)&&"001.004".equals(b0111)) {
			  sql="select  distinct a01.a0000,a0101,a0192a\r\n" + 
			  		"from ZHGBSYS.A01 a01 where exists (select 1 from ZHGBSYS.A02 a02 where a02.a0281 = 'true' \r\n" + 
			  		"and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') \r\n" + 
			  		"and a02.a0255 = '1' and a01.A0000 = a02.A0000 and a02.a0201b like '001.001%' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')) \r\n" + 
			  		"and A01.A0163='1' and A01.status='1'and (A01.A0165 like '%03%' or A01.A0165 like '%05%')";
		  }else if("2".equals(colIndex)&&"001.004".equals(b0111)) {
			  sql="select  distinct a01.a0000,a0101,a0192a\r\n" + 
				  		"from ZHGBSYS.A01 a01 where exists (select 1 from ZHGBSYS.A02 a02 where a02.a0281 = 'true' \r\n" + 
				  		"and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') \r\n" + 
				  		"and a02.a0255 = '1' and a0104='2' and a01.A0000 = a02.A0000 and a02.a0201b like '001.001%' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')) \r\n" + 
				  		"and A01.A0163='1' and A01.status='1'and (A01.A0165 like '%03%' or A01.A0165 like '%05%')";
		  }else if("1".equals(colIndex)&&"001.001.004".equals(b0111.substring(0, 11))) {
			  sql="select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
				  		"from ZHGBSYS.A01 a01,\r\n" + 
				  		"(select\r\n" + 
				  		"a0000,area,b01.b0101 from\r\n" + 
				  		"(select a.* from\r\n" + 
				  		"(select distinct a01.a0000,substr(a02.a0201b,1,15) area,a0165,a0201b,b0131\r\n" + 
				  		"from\r\n" + 
				  		"ZHGBSYS.A01 a01 \r\n" + 
				  		"left join ZHGBSYS.A02 a02 on  a01.a0000=a02.a0000\r\n" + 
				  		"left join ZHGBSYS.B01 b01 on  a02.a0201b=b01.b0111\r\n" + 
				  		"where A01.A0163='1' and A01.status='1' and  a02.a0201d='1' and a02.a0255='1' \r\n" + 
				  		"and a02.a0281='true' and a02.a0201b like '001.001%') a\r\n" + 
				  		"where a0201b like '001.001.004%'and b0131='1003') a\r\n" + 
				  		"left join ZHGBSYS.B01 b01 on  b01.b0111=a.area) a\r\n" + 
				  		"where a.a0000=a01.a0000";
		  }else if("2".equals(colIndex)&&"001.001.004".equals(b0111.substring(0, 11))) {
			  sql="select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
			  		"from ZHGBSYS.A01 a01,\r\n" + 
			  		"(select\r\n" + 
			  		"a0000,area,b01.b0101 from\r\n" + 
			  		"(select a.* from\r\n" + 
			  		"(select distinct a01.a0000,substr(a02.a0201b,1,15) area,a0165,a0201b,b0131\r\n" + 
			  		"from\r\n" + 
			  		"ZHGBSYS.A01 a01 \r\n" + 
			  		"left join ZHGBSYS.A02 a02 on  a01.a0000=a02.a0000\r\n" + 
			  		"left join ZHGBSYS.B01 b01 on  a02.a0201b=b01.b0111\r\n" + 
			  		"where A01.A0163='1' and A01.status='1' and  a02.a0201d='1'  and a0104='2' and a02.a0255='1' \r\n" + 
			  		"and a02.a0281='true' and a02.a0201b like '001.001%') a\r\n" + 
			  		"where a0201b like '001.001.004%'and b0131='1003') a\r\n" + 
			  		"left join ZHGBSYS.B01 b01 on  b01.b0111=a.area) a\r\n" + 
			  		"where a.a0000=a01.a0000";
		  }else if("1".equals(colIndex)&&b0111.indexOf("X")!=-1&&"001.001.002".equals(b0111.substring(0, 11))) {
			  sql="  select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
					  "from A01 a01 \r\n" + 
					  "left join A02 a02 on a01.a0000=a02.a0000 \r\n" + 
					  "left join B01 b01 on a02.a0201b=b01.b0111 \r\n" + 
					  "where a0163 = '1'\r\n" + 
					  "and a02.a0201e = '1'\r\n" + 
					  "and a02.a0281 = 'true'\r\n" + 
					  "and a01.STATUS = '1'\r\n" + 
					  "and b0111 like '001.001.002%' and length(b0111)=15 and  substr(b01.b0131,1,2)='34'";
	  }else if("2".equals(colIndex)&&b0111.indexOf("X")!=-1&&"001.001.002".equals(b0111.substring(0, 11))) {
		  sql="  select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
				  "  from A01 a01 \r\n" + 
				  "  left join A02 a02 on a01.a0000=a02.a0000 \r\n" + 
				  "  left join B01 b01 on a02.a0201b=b01.b0111 \r\n" + 
				  "   where a0163 = '1'\r\n" + 
				  "   and a02.a0201e = '1'\r\n" + 
				  "   and a02.a0281 = 'true'\r\n" + 
				  "   and a01.STATUS = '1'\r\n" + 
				  "   and b0111 like '001.001.002%' and length(b0111)=15 and  substr(b01.b0131,1,2)='34' and a01.a0104='2'";
	  }else if("1".equals(colIndex)&&b0111.indexOf("X")==-1&&"001.001.002".equals(b0111.substring(0, 11))) {
		  sql="  select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
				  "  from A01 a01 \r\n" + 
				  "  left join A02 a02 on a01.a0000=a02.a0000 \r\n" + 
				  "  left join B01 b01 on a02.a0201b=b01.b0111 \r\n" + 
				  "   where a0163 = '1'\r\n" + 
				  "   and a02.a0281 = 'true'\r\n" + 
				  "   and a01.STATUS = '1'\r\n" + 
				  "   and b0111 like '"+b0111+"%' and length(b0111)=15 and  substr(b01.b0131,1,2)='34'";
	  }else if("2".equals(colIndex)&&b0111.indexOf("X")==-1&&"001.001.002".equals(b0111.substring(0, 11))) {
		  sql="  select distinct a01.a0000,a0101,a0192a,b0101\r\n" + 
				  "  from A01 a01 \r\n" + 
				  "  left join A02 a02 on a01.a0000=a02.a0000 \r\n" + 
				  "  left join B01 b01 on a02.a0201b=b01.b0111 \r\n" + 
				  "   where a0163 = '1'\r\n" + 
				  "   and a02.a0281 = 'true'\r\n" + 
				  "   and a01.STATUS = '1'\r\n" + 
				  "   and b0111 like '"+b0111+"%' and length(b0111)=15 and  substr(b01.b0131,1,2)='34' and a01.a0104='2'";
	  }
/*		  System.out.println("/////////////////////////////////");
		  System.out.println(sql);*/
	  }
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  sql += " order by " + ordersql;	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("noticeSetgrid").getValue("a0000",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
}
