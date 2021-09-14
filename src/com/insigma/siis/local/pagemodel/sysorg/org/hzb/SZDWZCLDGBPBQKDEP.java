package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class SZDWZCLDGBPBQKDEP {

	public static String expData01(String b){
		String con="";
		if("dwbm".equals(b))
			con="党委部门";
		if("zfbm".equals(b))
			con="政府部门";
		if("fjjg".equals(b))
			con="法检机关";
		if("mzdp".equals(b))
			con="民主党派";
		if("qt".equals(b))
			con="其他市级机关";
		if("ssgx".equals(b))
			con="市属高校";
		if("ssgq".equals(b))
			con="市属国企";
		
		try {
			String sql1  ="select \r\n" + 
					"dep,count(b0111) nu,\r\n" + 
					"sum(b0183) a01,\r\n" + 
					"sum(b0185) a02,\r\n" + 
					"sum(cou1) a03,\r\n" + 
					"sum(cou2) a04,\r\n" + 
					"sum(t01) a05,\r\n" + 
					"sum(t02) a06,\r\n" + 
					"sum(t03) a07,\r\n" + 
					"sum(t04) a08,\r\n" + 
					"sum(t05) a09,\r\n" + 
					"sum(t06) a10,\r\n" + 
					"sum(t07) a11,\r\n" + 
					"sum(t09) a12,\r\n" + 
					"sum(t10) a13,\r\n" + 
					"sum(t11) a14,\r\n" + 
					"sum(t12) a15,\r\n" + 
					"sum(t13) a16,\r\n" + 
					"sum(t14) a17,\r\n" + 
					"sum(t15) a18,\r\n" + 
					"sum(t16) a19,\r\n" + 
					"sum(t17) a20,\r\n" + 
					"sum(t18) a21,\r\n" + 
					"sum(t19) a22,\r\n" + 
					"sum(t20) a23,\r\n" + 
					"sum(t21) a24,\r\n" + 
					"sum(t22) a25,\r\n" + 
					"sum(t23) a26,\r\n" + 
					"sum(t24) a27,\r\n" + 
					"sum(t25) a28,\r\n" + 
					"sum(t26) a29,\r\n" + 
					"sum(t27) a30,\r\n" + 
					"sum(t28) a31,\r\n" + 
					"sum(t29) a32,\r\n" + 
					"sum(t30) a33,\r\n" + 
					"sum(t31) a34,\r\n" + 
					"sum(t32) a35,\r\n" + 
					"sum(t33) a36,\r\n" + 
					"sum(t34) a37,\r\n" + 
					"sum(t35) a38\r\n" + 
					"\r\n" + 
					"from\r\n" + 
					"SZDWZCLDGBPBQKDEP b\r\n" + 
					"where b.dep='"+con+"'\r\n" + 
					"group by b.dep\r\n" + 
					"order by b.dep";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql1.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			return updateunDataStoreObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String expData02(String b){
		String con="";
		if("dwbm".equals(b))
			con="党委部门";
		if("zfbm".equals(b))
			con="政府部门";
		if("fjjg".equals(b))
			con="法检机关";
		if("mzdp".equals(b))
			con="民主党派";
		if("qt".equals(b))
			con="其他市级机关";
		if("ssgx".equals(b))
			con="市属高校";
		if("ssgq".equals(b))
			con="市属国企";
		try {
			String sql1  = "select \r\n" + 
					"dep,b0101,count(b0111) nu,\r\n" + 
					"sum(b0183) a01,\r\n" + 
					"sum(b0185) a02,\r\n" + 
					"sum(cou1) a03,\r\n" + 
					"sum(cou2) a04,\r\n" + 
					"sum(t01) a05,\r\n" + 
					"sum(t02) a06,\r\n" + 
					"sum(t03) a07,\r\n" + 
					"sum(t04) a08,\r\n" + 
					"sum(t05) a09,\r\n" + 
					"sum(t06) a10,\r\n" + 
					"sum(t07) a11,\r\n" + 
					"sum(t09) a12,\r\n" + 
					"sum(t10) a13,\r\n" + 
					"sum(t11) a14,\r\n" + 
					"sum(t12) a15,\r\n" + 
					"sum(t13) a16,\r\n" + 
					"sum(t14) a17,\r\n" + 
					"sum(t15) a18,\r\n" + 
					"sum(t16) a19,\r\n" + 
					"sum(t17) a20,\r\n" + 
					"sum(t18) a21,\r\n" + 
					"sum(t19) a22,\r\n" + 
					"sum(t20) a23,\r\n" + 
					"sum(t21) a24,\r\n" + 
					"sum(t22) a25,\r\n" + 
					"sum(t23) a26,\r\n" + 
					"sum(t24) a27,\r\n" + 
					"sum(t25) a28,\r\n" + 
					"sum(t26) a29,\r\n" + 
					"sum(t27) a30,\r\n" + 
					"sum(t28) a31,\r\n" + 
					"sum(t29) a32,\r\n" + 
					"sum(t30) a33,\r\n" + 
					"sum(t31) a34,\r\n" + 
					"sum(t32) a35,\r\n" + 
					"sum(t33) a36,\r\n" + 
					"sum(t34) a37,\r\n" + 
					"sum(t35) a38\r\n" + 
					"\r\n" + 
					"from\r\n" + 
					"SZDWZCLDGBPBQKDEP b\r\n" + 
					"where b.dep='"+con+"'\r\n" + 
					"group by b.dep,b.b0101,b.so\r\n" + 
					"order by b.so";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql1.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			return updateunDataStoreObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String expData03(String b){
		String con="";
		if("dwbm".equals(b))
			con="党委部门";
		if("zfbm".equals(b))
			con="政府部门";
		if("fjjg".equals(b))
			con="法检机关";
		if("mzdp".equals(b))
			con="民主党派";
		if("qt".equals(b))
			con="其他市级机关";
		if("ssgx".equals(b))
			con="市属高校";
		if("ssgq".equals(b))
			con="市属国企";
		try {
		String sql="select b0101,cou1,cou2,dcou\r\n" + 
				"from\r\n" + 
				"(select b01.b0101,count(c.b0111) dcou,to_number(substr(b01.b0269,13,3)) so, sum(c.b0183) cou1,sum(c.b0185) cou2,\r\n" + 
				"case   when substr(b01.b0131,1,2)='31' or substr(b01.b0131,1,2)='32' then '党委部门'\r\n" + 
				"            when substr(b01.b0131,1,2)='34' then  '政府部门'\r\n" + 
				"      when substr(b01.b0131,1,2)='36' or substr(b01.b0131,1,2)='37' then '法检机关'\r\n" + 
				"      when instr(b01.b0131, '3801') > 0 then '民主党派'\r\n" + 
				"      when instr(b01.b0131, '511') > 0 then  '市属国企'\r\n" + 
				"      when substr(b01.b0131,1,2)='43' then '市属高校'\r\n" + 
				"      else  '其他市级机关' end dep from\r\n" + 
				"(select * from B01 where substr(b0111,1,11) in ('001.001.002','001.001.003') and length(b0111)=15) b01,B01 c\r\n" + 
				"where c.b0111 like b01.b0111||'%' and length(c.b0111)>15\r\n" + 
				"group by b01.b0101,b01.b0269,b01.b0131) e\r\n" + 
				"where e.dep='"+con+"'\r\n" + 
				"order by so";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
		return updateunDataStoreObject.toString();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "[]";
}
}
