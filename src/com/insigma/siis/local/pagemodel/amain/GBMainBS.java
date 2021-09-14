package com.insigma.siis.local.pagemodel.amain;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GBMainBS {
	String countFiled = " count(*) sggbcount ";
	CommQuery cqbs=new CommQuery();
	public String searchdata;
	//市管干部sql
	public String gettjSQL(String countFiled,String filer1,String filer2,String filer3){
		String sql = "select  "+countFiled+" from "
				+ " (select c.*,decode(ISDATE(c.a0107),0,0,1,"
				+ " trunc(months_between(sysdate,to_date(decode(length(c.a0107),8,c.a0107,6,"
				+ " concat(c.a0107, '01')),'yyyymmdd')) / 12,0)) agex from A01 c) a01 "
				+ " where 1=1 "
				//+ " concat(a01.a0000, '') in "
				//+ " (select a02.a0000 from A02 a02 where a02.a0281 = 'true' "+filer1+" )"
				+ filer2
				//+ " and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%')"
				+ " and a0163 = '1' "
				+ " and exists (select 1 from (select * from (select a.a0000,a0201b, row_number() over(partition  "
				+ " by a.A0000 order by a0279 desc) row_number from a02 a where a0281 = 'true'  "
				+ " "+filer3+") t where t.row_number = 1) a02 where a01.a0000 = a02.a0000 "
				+ "  "+filer1+"  )";
		return sql;
	}
	
	//市管干部统计
	public Object getCountInfo(String countFiled,String filer1) throws AppException {
		
		List<HashMap<String, Object>> list = cqbs.getListBySQL(this.getFinalSQL(countFiled, filer1));
		return list.get(0).get("sggbcount");
	}
	//市管干部一次统计所有情况
	public HashMap<String, Object> getCountInfoAll() throws AppException {
		
		List<HashMap<String, Object>> list = cqbs.getListBySQL(this.getTJSGGBSQL());
		return list.get(0);
	}
	//年龄 学历 性别 党派一次统计所有情况
	public HashMap<String, Object> getCountAgeInfoAll() throws AppException {
		
		List<HashMap<String, Object>> list = cqbs.getListBySQL(this.getTJAGESQL());
		return list.get(0);
	}
	//经历业绩统计
	public HashMap<String, Object> getCountJLYJ() throws AppException {
		
		List<HashMap<String, Object>> list = cqbs.getListBySQL(this.getJLYJSQL());
		return list.get(0);
	}
	//获取市管干部某一情况统计sql
	public String getFinalSQL(String countFiled,String filer1){
		String f1 = "";
		String f3 = " and a0201b not like '001.001.001%' ";
		String f2 = " and (a01.a0165 like '%10%' or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') ";
		if("SGGB".equals(filer1)){//市管干部
			//f3 = "";
			f1 = "";
		}else if("SZDW".equals(filer1)){//市直单位
			f1 = "and substr(a02.a0201b, 1, 11) = '001.001.002' ";
		}else if("QXS".equals(filer1)){//区县市
			f1 = "and substr(a02.a0201b, 1, 11) = '001.001.004' ";
		}else if("GQGX".equals(filer1)){//国企高校
			f1 = "and substr(a02.a0201b, 1, 11) = '001.001.003' ";
		}else if("SGZZ".equals(filer1)){//市管正职
			f2 = " and a01.a0165 like '%10%' ";
			//f3 = "";
		}else if("SGFZ".equals(filer1)){//市管副职
			f2 = " and a01.a0165 like '%11%' ";
			//f3 = "";
		}else if("SGQT".equals(filer1)){//市管其他
			f2 = " and (a01.a0165 like '%18%' or a01.a0165 like '%19%') ";
			//f3 = "";
		}else if("a56".equals(filer1)) {
			f1 = " and agex  >=56 ";
		}else if("a5155".equals(filer1)) {
			f1 = " and  agex  >50  and  agex<=55 ";
		}else if("a4650".equals(filer1)) {
			f1 = " and  agex  >45  and  agex<=50 ";
		}else if("a4145".equals(filer1)) {
			f1 = " and  agex  >40  and  agex<=45 ";
		}else if("a3540".equals(filer1)) {
			f1 = " and  agex  >35  and  agex<=40 ";
		}else if("a35".equals(filer1)) {
			f1 = " and  agex  <=35  ";
		}else if("fml".equals(filer1)) {//女
			f1 = " and  a0104  =2  ";
		}else if("ml".equals(filer1)) {//男
			f1 = " and  a0104  =1  ";
		}else if("zgss".equals(filer1)) {//搜索
			return "select  "+countFiled+" from a01 where a0000 in("+
					"select a0000 from ZGQUERY where data like '%"+searchdata+"%')";
		}else if("qrzbos".equals(filer1)) {//研究生
			f1 = " and instr(qrzxl,'研究生')>0  ";
		}else if("qrzshuos".equals(filer1)) {//大学
			f1 = " and  instr(qrzxl,'大学')>0  ";
		}else if("qrzxues".equals(filer1)) {//大专
			f1 = " and  instr(qrzxl,'大专')>0  ";
		}else if("qrzqt".equals(filer1)) {//其他
			f1 = " and ((instr(qrzxl,'研究生')=0 and instr(qrzxl,'大学')=0 and instr(qrzxl,'大专')=0) or qrzxl is null)  ";
		}else if("zzbos".equals(filer1)) {//研究生
			f1 = " and  instr(zgxl,'研究生')>0  ";
		}else if("zzshuos".equals(filer1)) {//大学
			f1 = " and  instr(zgxl,'大学')>0  ";
		}else if("zzxues".equals(filer1)) {//大专
			f1 = " and  instr(zgxl,'大专')>0  ";
		}else if("zzqt".equals(filer1)) {//其他
			f1 = " and ((instr(zgxl,'研究生')=0 and instr(zgxl,'大学')=0 and instr(zgxl,'大专')=0) or zgxl is null)  ";
		}else if("minge".equals(filer1)) {
			f1 = " and a0141='04' ";
		}else if("minmeng".equals(filer1)) {
			f1 = " and a0141='05' ";
		}else if("minjian".equals(filer1)) {
			f1 = " and a0141='06' ";
		}else if("minjin".equals(filer1)) {
			f1 = " and a0141='07' ";
		}else if("nonggongdang".equals(filer1)) {
			f1 = " and a0141='08' ";
		}else if("zhigongdang".equals(filer1)) {
			f1 = " and a0141='09' ";
		}else if("jiusanxueshe".equals(filer1)) {
			f1 = " and a0141='10' ";
		}else if("wudangpai".equals(filer1)) {
			f1 = " and a0141='12' ";
		}else if("qunzhong".equals(filer1)) {
			f1 = " and a0141='13' ";
		}else if("zgdy".equals(filer1)) {
			f1 = " and a0141 in('01','02') ";
		}else if("feizgdy".equals(filer1)) {
			f1 = " and a0141!='01' ";
		}else if("sgzzmc".equals(filer1)) {//2020可提任市管正职名册
			f1=" and exists (select 1" + 
					"          FROM Extra_Tags b" + 
					"         where a01.a0000 = b.a0000" + 
					"           and b.A1401H= '近期可提任市管正职' )" + 
					"   and (instr(a0165, '11') <> 0 or instr(a0165, '18') <> 0 or instr(a0165, '19') <> 0) ";
		}else if("sgfzmc".equals(filer1)) {//2020可提任市管副职名册
			f2="";
			f1="and exists (select 1" + 
					"          FROM Extra_Tags b" + 
					"         where a01.a0000 = b.a0000" + 
					"           and b.A1401H = '近期可提任市管副职')" + 
					"   and instr(a0165, '10') = 0" + 
					"   and instr(a0165, '11') = 0" + 
					"   and instr(a0165, '18') = 0" + 
					"   and instr(a0165, '19') = 0 ";
		}else if("szbmzz".equals(filer1)) {//市直部门正职名单
			f2="";
			f1="   and (1 = 1 and (1 = 2 or a01.a0165 like '%10%')) and concat(a01.a0000, '') in " + 
					"               (select a02.a0000 " + 
					"                  from A02 a02 " + 
					"                 where  a02.a0281 = 'true' and a02.A0201E = '1' " + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))";
		}else if("szbmsf".equals(filer1)) {//市直部门双副名单
			f2="";
			f1=" and concat(a01.a0000, '') in " + 
					"               (select a02.a0000 " + 
					"                  from A02 a02 " + 
					"                 where  a02.a0281 = 'true' " + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))  and (1 = 1 and (1 = 2 or a01.a0165 like '%21%')) ";
		}else if("szbmfsj".equals(filer1)) {//市直部门副书记名单
			f2="";
			f1=" and concat(a01.a0000, '') in " + 
					"               (select a02.a0000 " + 
					"                  from A02 a02 " + 
					"                 where  a02.a0281 = 'true' " + 
					"                   and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))  and (1 = 1 and (1 = 2 or a01.a0165 like '%20%')) ";
		}else if("jl0507".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45  and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0507') ";
		}else if("jl0509".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0509') ";
		}else if("jl0510".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0510') ";
		}else if("jl0801".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0801') ";
		}else if("jl0805".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0805') ";
		}else if("jl0901".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0901') ";
		}else if("jl0902".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705='0902') ";
		}else if("jl0903".equals(filer1)) {
			f1 = " and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 and exists (select 1 from hz_a17 t where t.a0000=a01.a0000 and a1705 in ('0903','0904','0905')) ";
		}else if("YJTXSQLType1".equals(filer1)) {//本月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today FROM ZHGBSYS.a01  b WHERE b.a0163 = '1' and (b.A0165 like '%10%' or b.A0165 like '%11%' or b.A0165 like '%18%'  or b.A0165 like '%19%') AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01  WHERE 720 = Aa.age AND aa.a0000 = a01.a0000) ";
		}else if("qxcount1".equals(filer1)) {//区县本月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today FROM ZHGBSYS.a01  b WHERE b.a0163 = '1' and (b.A0165 like '%10%' or b.A0165 like '%11%' or b.A0165 like '%18%'  or b.A0165 like '%19%') AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01  WHERE 720 = Aa.age AND aa.a0000 = a01.a0000    and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.004%')  ";
		}else if("szcount1".equals(filer1)) {//市直本月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today FROM ZHGBSYS.a01  b WHERE b.a0163 = '1' and (b.A0165 like '%10%' or b.A0165 like '%11%' or b.A0165 like '%18%'  or b.A0165 like '%19%') AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01  WHERE 720 = Aa.age AND aa.a0000 = a01.a0000    and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.002%')  ";
		}else if("gqgxcount1".equals(filer1)) {//国企高校本月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today FROM ZHGBSYS.a01  b WHERE b.a0163 = '1' and (b.A0165 like '%10%' or b.A0165 like '%11%' or b.A0165 like '%18%'  or b.A0165 like '%19%') AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01  WHERE 720 = Aa.age AND aa.a0000 = a01.a0000    and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.003%')  ";
		}else if("YJTXSQLType2".equals(filer1)) {//下月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'yyyyMM')  FROM DUAL)),'yyyyMM') Today FROM ZHGBSYS.a01 b WHERE b.a0163 = '1' and (b.A0165 like '%10%'  or b.A0165 like '%11%' or b.A0165 like '%18%' or b.A0165 like '%19%') AND (LENGTH(b.A0107)  = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01 WHERE 720 = Aa.age AND aa.a0000 = a01.a0000)";
		}else if("qxcount2".equals(filer1)) {//国企高校下月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'yyyyMM')  FROM DUAL)),'yyyyMM') Today FROM ZHGBSYS.a01 b WHERE b.a0163 = '1' and (b.A0165 like '%10%'  or b.A0165 like '%11%' or b.A0165 like '%18%' or b.A0165 like '%19%') AND (LENGTH(b.A0107)  = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01 WHERE 720 = Aa.age AND aa.a0000 = a01.a0000 and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.004%')";
		}else if("szcount2".equals(filer1)) {//市直下月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'yyyyMM')  FROM DUAL)),'yyyyMM') Today FROM ZHGBSYS.a01 b WHERE b.a0163 = '1' and (b.A0165 like '%10%'  or b.A0165 like '%11%' or b.A0165 like '%18%' or b.A0165 like '%19%') AND (LENGTH(b.A0107)  = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01 WHERE 720 = Aa.age AND aa.a0000 = a01.a0000 and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.002%')";
		}else if("gqgxcount2".equals(filer1)) {//国企高校下月到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (SELECT  a01.a0000 FROM (SELECT T.newA0107 Birthday, T.Today, MONTHS_BETWEEN(T.Today, T.newA0107) age,  T.a0104 sex, T.a0000 FROM (SELECT b.a0104, b.A0000, to_date((SUBSTR(RPAD(b.A0107, 8, '01'),  0, 6)), 'yyyyMM') newA0107, to_date(TO_CHAR((SELECT TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'yyyyMM')  FROM DUAL)),'yyyyMM') Today FROM ZHGBSYS.a01 b WHERE b.a0163 = '1' and (b.A0165 like '%10%'  or b.A0165 like '%11%' or b.A0165 like '%18%' or b.A0165 like '%19%') AND (LENGTH(b.A0107)  = 6 OR LENGTH(b.A0107) = 8)) T) Aa, ZHGBSYS.a01 WHERE 720 = Aa.age AND aa.a0000 = a01.a0000 and (select a0201b from a02 where a02.a0000 = aa.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.003%')";
		}else if("YJTXSQLType3".equals(filer1)) {//本年度到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 a01 where get_birthday(A01.A0107,  (select to_char(sysdate, 'yyyy') || '1231' from dual)) = 60 and (A01.A0165 like '%10%' or A01.A0165  like '%11%' or A01.A0165 like '%18%' or A01.A0165 like '%19%') and a01.a0163 = '1' and exists (select a0201b from a02 where a02.a0000=a01.a0000 and a0279='1' and a0281='true') )";
		}else if("qxcount3".equals(filer1)) {//区县本年度到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 a01 where get_birthday(A01.A0107,  (select to_char(sysdate, 'yyyy') || '1231' from dual)) = 60 and (A01.A0165 like '%10%' or A01.A0165  like '%11%' or A01.A0165 like '%18%' or A01.A0165 like '%19%') and a01.a0163 = '1' and (select a0201b from a02 where a02.a0000 = a01.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.004%')";
		}else if("szcount3".equals(filer1)) {//市直本年度到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 a01 where get_birthday(A01.A0107,  (select to_char(sysdate, 'yyyy') || '1231' from dual)) = 60 and (A01.A0165 like '%10%' or A01.A0165  like '%11%' or A01.A0165 like '%18%' or A01.A0165 like '%19%') and a01.a0163 = '1' and (select a0201b from a02 where a02.a0000 = a01.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.002%')";
		}else if("gqgxcount3".equals(filer1)) {//国企高校本年度到龄
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 a01 where get_birthday(A01.A0107,  (select to_char(sysdate, 'yyyy') || '1231' from dual)) = 60 and (A01.A0165 like '%10%' or A01.A0165  like '%11%' or A01.A0165 like '%18%' or A01.A0165 like '%19%') and a01.a0163 = '1' and (select a0201b from a02 where a02.a0000 = a01.a0000 and a0281 = 'true'  and a0279 = '1') like '001.001.003%')";
		}else if("fz6ytc".equals(filer1)) {//六月可办理副职
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
					"       (select a01.a0000" + 
					"  from a01,a02,b01" + 
					"  where get_birthday(A01.A0107," + 
					"                   '20201231') >= 57" + 
					"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
					"   and A01.A0163 = '1'" + 
					"   and A01.status = '1'" + 
					"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
					"   and A01.A0000 in (select distinct (A02.A0000)" + 
					"                       from ZHGBSYS.A02" + 
					"                      where A02.A0201E in ('1','3') and a0279='1')" + 
					"  and substr(a0201b,1,11)<>'001.001.003'  and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  )";
		}else if("zz12ytc".equals(filer1)) {//十二月可办理正职
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			" (select a01.a0000" + 
			"          from a01,a02,b01" + 
			"         where ((get_birthday(A01.A0107," + 
			"                            to_char(sysdate, 'yyyy') || '1231') >= 58 and b0131<>'1003' and b0131<>'1005')" + 
			"                  or" + 
			"                  (get_birthday(A01.A0107," + 
			"                            to_char(sysdate, 'yyyy') || '1231') >= 59 and (b0131='1003' or b0131='1005')))          " + 
			"           and A01.A0165 like '%10%'" + 
			"           and A01.A0163 = '1'" + 
			"           and A01.status = '1'" + 
			"           and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
			"           and A01.A0000 in (select  A02.A0000" + 
			"                               from ZHGBSYS.A02" + 
			"                              where A02.A0201E in ('1','3') and a0279='1'" + 
			"                               )" + 
			"    and substr(a0201b,1,11)<>'001.001.003'     and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C' ) ";
		}else if("fz12ytc".equals(filer1)) {//十二月可办理副职
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			" (select a01.a0000" + 
			"  from a01,a02,b01" + 
			" where ((get_birthday(A01.A0107," + 
			"                    to_char(sysdate, 'yyyy') || '1231') >= 57 and b0131<>'1003' and b0131<>'1005' and get_birthday(A01.A0107,  '20201231') < 57)" + 
			"        or" + 
			"        (get_birthday(A01.A0107," + 
			"                    to_char(sysdate, 'yyyy') || '1231') >= 59 and (b0131='1003' or b0131='1005')))" + 
			"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
			"   and A01.A0163 = '1'" + 
			"   and A01.status = '1'" + 
			"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
			"   and A01.A0000 in (select distinct (A02.A0000)" + 
			"                       from ZHGBSYS.A02" + 
			"                      where A02.A0201E in ('1','3') and a0279='1')" + 
			"  and substr(a0201b,1,11)<>'001.001.003'  and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C' ) ";
		}else if("zzmytc".equals(filer1)) {//某月可办理正职
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			" (select a01.a0000" + 
			"          from a01,a02,b01" + 
			"         where ((get_birthday(A01.A0107," + 
			"                            '"+searchdata+"') >= 58 and b0131<>'1003' and b0131<>'1005')" + 
			"                  or" + 
			"                  (get_birthday(A01.A0107," + 
			"                            '"+searchdata+"') >= 59 and (b0131='1003' or b0131='1005')))          " + 
			"           and A01.A0165 like '%10%'" + 
			"           and A01.A0163 = '1'" + 
			"           and A01.status = '1'" + 
			"           and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
			"           and A01.A0000 in (select  A02.A0000" + 
			"                               from ZHGBSYS.A02" + 
			"                              where A02.A0201E in ('1','3') and a0279='1'" + 
			"                               )" + 
			"   and substr(a0201b,1,11)<>'001.001.003'      and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'  )" ;
		}else if("fzmytc".equals(filer1)) {//某月可办理副职
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			" (select a01.a0000" + 
			"  from a01,a02,b01" + 
			" where ((get_birthday(A01.A0107," + 
			"                    '"+searchdata+"') >= 57 and b0131<>'1003' and b0131<>'1005')" + 
			"        or" + 
			"        (get_birthday(A01.A0107," + 
			"                    '"+searchdata+"') >= 59 and (b0131='1003' or b0131='1005')))" + 
			"   and (A01.A0165 like '%11%' or A01.A0165 like '%21%')" + 
			"   and A01.A0163 = '1'" + 
			"   and A01.status = '1'" + 
			"   and a0281='true' and a0279='1' and a02.a0000=a01.a0000 and a02.a0201b=b01.b0111" + 
			"   and A01.A0000 in (select distinct (A02.A0000)" + 
			"                       from ZHGBSYS.A02" + 
			"                      where A02.A0201E in ('1','3') and a0279='1')" + 
			"  and substr(a0201b,1,11)<>'001.001.003'  and a0201b <>'001.001.002.02O.003'  and a0201b<>'001.001.002.00C'   )";
		}else if("bysyqjm".equals(filer1)) {//本月试用期届满
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			"(select a01.a0000 from a01 where a0192a like '%试用期%' and a0165 like '%11%'" + 
			" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))>=12)";
		}else if("xysyqjm".equals(filer1)) {//下月试用期届满
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			"(select a01.a0000 from a01 where a0192a like '%试用期%' and a0165 like '%11%'" + 
			" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))>=11 "+
			" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))<12)";
		}else if("syqry".equals(filer1)) {//试用期人员
			return "select "+countFiled+" from a01 where a01.a0000 in" + 
			"(select a01.a0000 from a01 where a0192a like '%试用期%' and a0165 like '%11%')" ;
		}else if("YJTXSQLType4".equals(filer1)) {//（一巡 今年满57）
			return "select "+countFiled+" from a01  where a01.a0165 like '%03%' and concat(a01.a0000, '') in (select a02.a0000 from ZHGBSYS.A02  a02 where a02.a0281 = 'true' and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))  and a0160 like '01%'  and  a0163 = '1' and a0101 <> '谢国建' and a01.a0107 < '19641231'    and exists (select 1 from a05 where a05.a0000 = a01.a0000  and a0501b in ('0122', '012201', '012202') and substr(a0504, 0, 6) <= '201312') and nvl(a01.a0192e,'@') not in ( '11', '1A11')";
		}else if("YJTXSQLType5".equals(filer1)) {//（二巡 今年满57）
			return "select "+countFiled+" from a01  where a01.a0000  not in (select a0000 from ZHGBSYS.attr_lrzw where ATTR2407 = '1') and concat(a01.a0000,  '') in (select a02.a0000 from ZHGBSYS.A02 a02 where a02.a0281 = 'true' and a02.a0201b not in  (select b0111 from ZHGBSYS.b01 where b0131 in ('36','37','1006','1007','3409') ) and ( substr(a02.a0201b, 1, 11) = '001.001.002'))  and a0160 like '01%'  and a0163 = '1' and a01.a0107 < '19641231'    and exists (select 1 from a05 where a05.a0000 = a01.a0000  and a0501b in ('013102', '013103', '013104')  and substr(a0504, 0, 6)<= '201112') and nvl(a01.a0192e,'@')  not in ( '11','1A11','12','1A12')";
		}else if("YJTXSQLType6".equals(filer1)) {//（一巡 今年不满57）
			return "select "+countFiled+" from a01  where a01.a0165 like '%03%' and concat(a01.a0000, '') in (select a02.a0000 from ZHGBSYS.A02  a02 where a02.a0281 = 'true' and (1 = 2 or substr(a02.a0201b, 1, 11) = '001.001.002'))  and a0160 like '01%'  and  a0163 = '1' and a0101 <> '谢国建' and a01.a0107 >= '19641231'  and exists (select 1 from a05 where a05.a0000 = a01.a0000  and a0501b in ('0122', '012201', '012202') and substr(a0504, 0, 6) <= '201312') and nvl(a01.a0192e,'@') not in ( '11', '1A11')";
		}else if("YJTXSQLType7".equals(filer1)) {//（二巡 今年不满57）
			return "select "+countFiled+" from a01  where a01.a0000  not in (select a0000 from ZHGBSYS.attr_lrzw where ATTR2407 = '1') and concat(a01.a0000,  '') in (select a02.a0000 from ZHGBSYS.A02 a02 where a02.a0281 = 'true' and a02.a0201b not in  (select b0111 from ZHGBSYS.b01 where b0131 in ('36','37','1006','1007','3409') ) and ( substr(a02.a0201b, 1, 11) = '001.001.002'))  and a0160 like '01%'  and a0163 = '1' and a01.a0107 >= '19641231'    and exists (select 1 from a05 where a05.a0000 = a01.a0000  and a0501b in ('013102', '013103', '013104')  and substr(a0504, 0, 6)<= '201112') and nvl(a01.a0192e,'@')  not in ( '11','1A11','12','1A12')";
		}else if("YJTXSQLType8".equals(filer1)) {//（二巡 副师军转）
			return "select "+countFiled+" from a01  where a01.a0163 = '1'   and a0160 like '01%'  and exists (select 1 from ZJS_A0195_TAG t where t.a0000=a01.a0000 and a0195='1823')  and nvl(a01.a0192e,'@')  not in ( '11','1A11','12','1A12')";
		}else if("YJTXSQLType11".equals(filer1)) {//任现职近5年
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 left join ZHGBSYS.A02 a02 on a01.a0000 = a02.a0000  where a01.a0163 = '1' and a01.status = '1' and (a01.a0165 like '%10%'  or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') and a02.a0255 =  '1' and a02.a0281 = 'true' and a02.a0201b like '001.001%' and a02.a0279 = '1' and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) <= 7.5 and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) >= 4.5 )";
		}else if("YJTXSQLType12".equals(filer1)) {//任现职近8年
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 left join ZHGBSYS.A02 a02 on a01.a0000 = a02.a0000  where a01.a0163 = '1' and a01.status = '1' and (a01.a0165 like '%10%'  or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') and a02.a0255 =  '1' and a02.a0281 = 'true' and a02.a0201b like '001.001%' and a02.a0279 = '1' and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) <= 9.5 and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) >= 7.5 )";
		}else if("YJTXSQLType13".equals(filer1)) {//任现职近10年
			return "select "+countFiled+" from a01 where a01.a0000 in (select a01.a0000 from  ZHGBSYS.A01 left join ZHGBSYS.A02 a02 on a01.a0000 = a02.a0000 where a01.a0163 = '1' and a01.status = '1' and (a01.a0165 like '%10%'  or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') and a02.a0255 =  '1' and a02.a0281 = 'true' and a02.a0201b like '001.001%' and a02.a0279 = '1' and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) < 10 and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) >= 9.5) ";
		}else if("YJTXSQLType14".equals(filer1)) {//任现职10年及以上
			return "select "+countFiled+" from a01 where a0000 in (select a01.a0000 from  ZHGBSYS.A01 left join ZHGBSYS.A02 a02 on a01.a0000 = a02.a0000  where a01.a0163 = '1' and a01.status = '1' and (a01.a0165 like '%10%'  or a01.a0165 like '%11%' or a01.a0165 like '%18%' or a01.a0165 like '%19%') and a02.a0255 =  '1' and a02.a0281 = 'true' and a02.a0201b like '001.001%' and a02.a0279 = '1' and TRUNC(((to_char(sysdate,  'yyyyMM')) - to_char(substr(a01.a0192f, 0, 6))) / 100,1) >= 10 )";
		}else if("ngkhyxgb".equals(filer1)) {//年度考核优秀干部
			return "select "+countFiled+" from a01 where a0000 in (select a0000 from a15 where a15.a0000=a01.a0000 and a1521='2020' and a1517='1') and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or A01.A0165 like '%21%')  " ;
		}else if("ngkhybgb".equals(filer1)) {//年度考核一般干部
			return "select "+countFiled+" from a01 where a0000 in (select a0000 from a15_ybgb)  and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or A01.A0165 like '%21%') " ;
		}else if(filer1!=null&&filer1.contains("zdgw")) {//重点岗位预览
		      String id = filer1.replace("zdgw##", "");
		      try {
		        String sql= HBUtil.getValueFromTab("sql", "zdgw_way", "wayid='"+id+"'");
		        if(sql.contains("order by")) {
		        	sql=sql+",(select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from (select a02.a0000,b0269,a0225"
							+ ",row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn "
							+ " from a02, b01 where a02.a0201b = b01.b0111  and a0281 = 'true'  and a0201b like '001.001%') t "
							+ "	 where rn = 1 and t.a0000 = a01.a0000) ";
		        }else {
		        	sql=sql+" order by (select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from (select a02.a0000,b0269,a0225"
							+ ",row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn "
							+ " from a02, b01 where a02.a0201b = b01.b0111  and a0281 = 'true'  and a0201b like '001.001%') t "
							+ "	 where rn = 1 and t.a0000 = a01.a0000) ";
		        }
		        return "select "+countFiled +sql.substring(16)+"";

		      } catch (AppException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		      return "select 1 from dual";
		}else if(filer1!=null&&filer1.contains("gztcymd")) {//工作台常用名单
			String id = filer1.replace("gztcymd##", "");
			if("1_1".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='01' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("1_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='03' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("1_3".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='02' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("1_4".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("2_1_1".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype in ('04','05') and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b in (select b0111 from b01 where b0131 in ('1001','1004') and length(b0111)=19 and b0111 like '001.001.004%')) " ;
			}else if("2_1_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b in (select b0111 from b01 where b0131 in ('1001','1004') and length(b0111)=19 and b0111 like '001.001.004%')) " ;
			}else if("2_1_3".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype in ('04','05') and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b like '001.001.002%') " ;
			}else if("2_1_4".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b like '001.001.002%') " ;
			}else if("2_1_5".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype in ('04','05') and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b like '001.001.003%') " ;
			}else if("2_1_6".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' "
						+ " and a0000 in (select a0000 from a02 where a0281='true' and a0201b like '001.001.003%') " ;
			}else if("2_2_1".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='04' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("2_2_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='05' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("2_2_3".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='08' and a.mdid =b.mdid) and a0163='1' " ;
			}else if("2_2_4".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%')" ;
			}else if("2_2_5".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select b.a0000 from historymd a,historyper b where bqtype='09' and a.mdid =b.mdid) and a0163='1' and substr(to_char(sysdate, 'yyyymm')-substr(a0107, 1, 6), 1, 2)<=45 " ;
			}else if("2_3_1".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%') and a0107>='198001' " ;
			}else if("2_3_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and a0107>='198501' and a0000 in (select a0000 from hz_a17 where a1705 in ('0818','0901','0902'))" ;
			}else if("2_3_3".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and a0107>='199001' and a0000 in (select a0000 from hz_a17 where a1705 in ('0818','0901','0902','0903','0904'))" ;
			}else if("2_3_4".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%') and substr(to_char(sysdate, 'yyyymm')-substr(a0107, 1, 6), 1, 2)<47 " ;
			}else if("2_3_5".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and (a01.a0165 like '%12%' or a01.a0165 like '%07%' or a01.a0165 like '%09%' or a01.a0165 like '%14%' or  a01.a0165 like '%51%') and substr(to_char(sysdate, 'yyyymm')-substr(a0107, 1, 6), 1, 2)<42 " ;
			}else if("2_3_6".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and (a01.a0165 like '%50%' or a01.a0165 like '%08%' or a01.a0165 like '%13%' or a01.a0165 like '%15%') and substr(to_char(sysdate, 'yyyymm')-substr(a0107, 1, 6), 1, 2)<42 " ;
			}else if("3_1_1".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and a0132='1' " ;
			}else if("3_1_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0163='1' and a0133='1' " ;
			}else if("3_1_3".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct (A02.A0000) from A02 where a0281='true' and A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001') and A02.A0201E = '3' and (a02.a0248 = '0' or a02.a0248 is null))"
						+ " and A0192A like '%副书记%'  and A0165 like '%03%'  and A0163='1' " ;
			}else if("3_1_4".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where a0281='true' and A0000 in (select distinct(A0000) from A02 where a0281='true' and A02.A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001') and A0201E='3')and A02.A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1004'))"
						+ " and A0165 like '%11%'  and A0163='1' " ;
			}else if("3_1_5".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001')and A02.A0201E='3' and (a02.a0248 = '0' or a02.a0248 is null)) "
						+ " and ( A01.A0192A like '%纪委书记%' or  A01.A0192A like '%纪（工）委书记%') and A0163='1' " ;
			}else if("3_1_6".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001')) and A01.A0192A like '%组织部部长%'  and A0163='1' " ;
			}else if("3_1_7".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001')) and A01.A0192A like '%统战部部长%'  and A0163='1' " ;
			}else if("3_1_8".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131='1001')) and A01.A0192A like '%宣传部部长%'  and A0163='1' " ;
			}else if("3_1_9".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131 in ('1001','1004'))) and A01.A0192A like '%公安分局局长%'  and A0163='1' " ;
			}else if("3_1_10".equals(id)) {
				return "select "+countFiled+" from a01 where A0000 in (select distinct(A02.A0000) from A02 where A0201B in (select B0111 from B01 where b0111 like '001.001.004%' and length(b0111)=19 and b0131 in ('1006','1007'))) and a01.a0165 like '%11%' and A0163='1' " ;
			}else if("3_2_2".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select a02.a0000  from A02 a02  where  a02.a0281 = 'true'  and substr(a02.a0201b, 1, 11) = '001.001.002') and a01.a0165 like '%21%' and A0163='1' " ;
			}else if("3_2_3".equals(id)) {
				return "select "+countFiled+" from a01 where a0000 in (select a02.a0000  from A02 a02  where  a02.a0281 = 'true'  and substr(a02.a0201b, 1, 11) = '001.001.002') and a0163 = '1' and a01.a0165 like '%20%'" ;
			}
			
		}
		
		else {//专业
			f1 = " and a0000 in (select a0000 from "
					+ "(select distinct a0000, ((SELECT code_remark "
					+ " from code_value b where code_TYPE = 'GB6864' and a.A0901B = b.code_value)) zwrs from ZHGBSYS.a08 a) where zwrs='"+filer1+"') ";
		}
		return this.gettjSQL(countFiled,f1,f2,f3);
	}
	
	
	//市管干部统计所有情况sql
	public String getTJSGGBSQL(){
		String sql = " select "
			+" sum(1) SGGB, "
			+" sum(case when a10=1 then 1 else 0 end) SGZZ, "
			+" sum(case when a11=1 then 1 else 0 end) SGFZ, "
			+" sum(case when a18=1 or a19=1 then 1 else 0 end) SGQT, "
			+" sum(case when substr(a0201b, 1, 11) = '001.001.002' and (a10=1 or a11=1 or a18=1 or a19=1) then 1 else 0 end) SZDW, "
			+" sum(case when substr(a0201b, 1, 11) = '001.001.004' and (a10=1 or a11=1 or a18=1 or a19=1) then 1 else 0 end) QXS, "
			+" sum(case when substr(a0201b, 1, 11) = '001.001.003' and (a10=1 or a11=1 or a18=1 or a19=1) then 1 else 0 end) GQGX "
			+" from "
			+" (select  "
			+" case when instr(a01.a0165,'10')>0 then 1 else 0 end a10, "
			+" case when instr(a01.a0165,'11')>0 then 1 else 0 end a11, "
			+" case when instr(a01.a0165,'18')>0 then 1 else 0 end a18, "
			+" case when instr(a01.a0165,'19')>0 then 1 else 0 end a19,a02.* "
			+"   from A01 a01,(select * "
			+"                   from (select a.a0000, "
			+"                                a0201b, "
			+"                                row_number() over(partition by a.A0000 order by a0279 desc) row_number "
			+"                           from a02 a "
			+"                          where a0281 = 'true' and a0201b not like '001.001.001%') t "
			+"                  where t.row_number = 1) a02 "
			+"  where a0163 = '1' "
			+ "  and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or "
			+"        a0165 like '%19%')"
			+ " and a01.a0000 = a02.a0000) tj ";
		return sql;
	}
	//年龄统计所有情况sql
	public String getTJAGESQL(){
		String sql = "select "
				+" sum(case when age<=35 then 1 else 0 end) a35, "
				+" sum(case when age>35 and age<=40 then 1 else 0 end) a3540, "
				+" sum(case when age>40 and age<=45 then 1 else 0 end) a4145, "
				+" sum(case when age>45 and age<=50 then 1 else 0 end) a4650, "
				+" sum(case when age>50 and age<=55 then 1 else 0 end) a5155, "
				+" sum(case when age>=56 then 1 else 0 end) a56, "
				+" sum(case when a0104=1 then 1 else 0 end) ml, "
				+" sum(case when a0104=2 then 1 else 0 end) fml, "
				
				
				+" sum(case when instr(zgxl,'研究生')>0 then 1 else 0 end) zzbos, "
				+" sum(case when instr(zgxl,'大学')>0 then 1 else 0 end) zzshuos, "
				+" sum(case when instr(zgxl,'大专')>0 then 1 else 0 end) zzxues, "
				+" sum(case when (instr(zgxl,'研究生')=0 and instr(zgxl,'大学')=0 and instr(zgxl,'大专')=0) or zgxl is null then 1 else 0 end) zzqt, "
				+" sum(case when instr(qrzxl,'研究生')>0 then 1 else 0 end) qrzbos, "
				+" sum(case when instr(qrzxl,'大学')>0 then 1 else 0 end) qrzshuos, "
				+" sum(case when instr(qrzxl,'大专')>0 then 1 else 0 end) qrzxues, "
				+" sum(case when (instr(qrzxl,'研究生')=0 and instr(qrzxl,'大学')=0 and instr(qrzxl,'大专')=0 ) or qrzxl is null then 1 else 0 end) qrzqt, "
				
				+" sum(case when a0141='01' then 1 else 0 end) zgdy, "
				+" sum(case when a0141='02' then 1 else 0 end) ybdy, "
				
				+" sum(case when a0141!='01' and a0141!='02' then 1 else 0 end) feizgdy, "
				
				+" sum(case when a0141='04' then 1 else 0 end) minge, "
				+" sum(case when a0141='05' then 1 else 0 end) minmeng, "
				+" sum(case when a0141='06' then 1 else 0 end) minjian, "
				+" sum(case when a0141='07' then 1 else 0 end) minjin, "
				+" sum(case when a0141='08' then 1 else 0 end) nonggongdang, "
				+" sum(case when a0141='09' then 1 else 0 end) zhigongdang, "
				+" sum(case when a0141='10' then 1 else 0 end) jiusanxueshe, "
				+" sum(case when a0141='12' then 1 else 0 end) wudangpai, "
				+" sum(case when a0141='13' then 1 else 0 end) qunzhong, "
				+" sum(1) sggb "
				+" from ( "
				+" select a01.a0000,a0104,zzxl,qrzxl,zgxl,a0141, decode(ISDATE(a01.a0107),0,0,1, "
				+" trunc(months_between(sysdate, "
				+" to_date(decode(length(a01.a0107),8,a01.a0107,6,concat(a01.a0107, '01')),'yyyymmdd')) / 12,0)) age "
				+"   from A01 a01 "
				+"  where exists "
				+"  (select 1 FROM a02 a02 "
				+"          where a01.a0000 = a02.a0000 "
				+"            and substr(a02.A0201B, 1, 7) = '001.001' "
				+"            and a02.a0281 = 'true') "
				+"    and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or "
				+"        a0165 like '%19%') "
				+"    and a0163 = '1') tj ";
		return sql;
	}

	//专业统计
	public List<HashMap<String, String>> getCountZhuanYeInfoAll() throws AppException {
		String sql = "select zwrs, count(1) rs from (select distinct a0000, ((SELECT code_remark "
				+ " from ZHGBSYS.code_value b where code_TYPE = 'GB6864' and a.A0901B = b.code_value)) zwrs "
				+ " from ZHGBSYS.a08 a where a0000 in (select a01.a0000 from ZHGBSYS.a01 where A01.A0163 = '1' "
				+ " and A01.status = '1' and (A01.A0165 like '%10%' or A01.A0165 like '%11%' "
				+ " or A01.A0165 like '%18%' or A01.A0165 like '%19%'))) t group by zwrs "
				+ "having zwrs is not null";
		List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
		return list;
	}
	
	public String getJLYJSQL(){
		String sql = "select (select count(distinct a0000) from hz_a17 where a1705='0507' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1'" + 
				"                   and A01.status = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45 " + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0507," + 
				" (select count(distinct a0000) from hz_a17 where a1705='0509' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0509," + 
				" (select count(distinct a0000) from hz_a17 where a1705='0510' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0510," + 
				" (select count(distinct a0000) from hz_a17 where a1705 ='0801' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0801," + 
				" (select count(distinct a0000) from hz_a17 where a1705='0805' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0805," + 
				" (select count(distinct a0000) from hz_a17 where a1705='0901' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0901," + 
				" (select count(distinct a0000) from hz_a17 where a1705='0902' and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0902,                      " + 
				" (select count(distinct a0000) from hz_a17 where a1705 in ('0903','0904','0905') and a0000 in (select a01.a0000" + 
				"                  from ZHGBSYS.a01" + 
				"                 where A01.A0163 = '1' and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=45" + 
				"                   and A01.status = '1'" + 
				"                   and (A01.A0165 like '%10%' or A01.A0165 like '%11%' or" + 
				"                       A01.A0165 like '%18%' or A01.A0165 like '%19%'))) jl0903" + 
				" from a01 where  rownum=1 ";
		return sql;
	}
}
