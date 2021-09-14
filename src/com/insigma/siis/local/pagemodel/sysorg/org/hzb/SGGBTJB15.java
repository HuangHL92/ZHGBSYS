package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class SGGBTJB15 {
	public static String expData(){
	    try {
	      String sql = "select b0101,count(1) cnt,\r\n" + 
	      		"sum(case when a0165 like '%10%' then 1 else 0 end) zz,\r\n" + 
	      		"sum(case when a0165 like '%11%' then 1 else 0 end) fz,\r\n" + 
	      		"sum(case when a0165 like '%18%' or a0165 like '%19%' then 1 else 0 end) qt,\r\n" + 
	      		"sum(case when a0107>='197407' then 1 else 0 end) afn74,\r\n" + 
	      		"to_char(sum(case when a0107>='197407' then 1 else 0 end)*100/count(1),'fm990.0')||'%' afc74,\r\n" + 
	      		"sum(case when a0107>='197907' then 1 else 0 end) afn79,\r\n" + 
	      		"to_char(sum(case when a0107>='197907' then 1 else 0 end)*100/count(1),'fm990.0')||'%' afc79,\r\n" + 
	      		"sum(case when a0107>='198407' then 1 else 0 end) afn84,\r\n" + 
	      		"sum(case when a0104='2' then 1 else 0 end) ngb,\r\n" + 
	      		"sum(case when a0141 not in ('01', '02', '03') then 1 else 0 end) dwgb,\r\n" + 
	      		"sum(case when qrzxl='大学' or qrzxl='研究生' then 1 else 0 end) qrzdx,\r\n" + 
	      		"sum(case when qrzxl='研究生' then 1 else 0 end)qrzyjs\r\n" + 
	      		"  from \r\n" + 
	      		"(select a.*,row_number() over(partition by a.a0201b order by a0101 ) row_number                                                                                     \r\n" + 
	      		"   from (select t.*,c.b0101,c.b0269 from (select a0101,a0192a,substr(a0201b,1,15) a0201b,a0107,a0104,a0141,a0165,qrzxl,qrzxw \r\n" + 
	      		"     from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and a0281='true' and a0279='1' and a0201b like '001.001.002%' \r\n" + 
	      		" and (a0165 like '%10%' or a0165 like '%11%' or a0165 like '%18%' or a0165 like '%19%')) t,b01 c \r\n" + 
	      		"   where t.a0201b=c.b0111) a ) t group by b0101,b0269 order by b0269 ";
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
