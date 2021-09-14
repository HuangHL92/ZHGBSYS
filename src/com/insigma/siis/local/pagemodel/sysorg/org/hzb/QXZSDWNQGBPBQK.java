package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class QXZSDWNQGBPBQK {
	public static String expData(){
	    try {
//	      String sql = " with QXZSDWNQGBPBQK as (\n" +
//	                " select  distinct a01.a0000,a01.a0101,substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2) age,substr(a02.a0201b,1,15) xqdm,(select b.b0101 from b01 b where b.b0111= substr(a02.a0201b,1,15)) ssxq,A0165\n" +
//	                "  from A01 a01, A02 a02, B01 b01\n" +
//	                " WHERE a01.a0000 = a02.a0000\n" +
//	                "   and a02.a0201b = b01.b0111\n" +
//	                "   and b01.b0111 like '001.001.004%'\n" +
//	                "   and a02.a0281='true' and a02.a0255='1'\n" +
//	                "   and (A0165 LIKE '%07%' OR A0165 LIKE '%08%' OR A0165 LIKE '%09%' OR A0165 LIKE '%13%')\n" +
//	                "   and a02.a0201d = '1'\n" +
//	                "   \n" +
//	                "   and a01.A0163 = '1'\n" +
//	                "   and a01.status = '1') \n" +
//	                "   select rownum,bb.ssxq,bb.b0111,' ' zsdwzs, ' ' dzgzzs, ' ' zsdwzs, ' ' dzgzbz, ' ' age35yp,' ' age35zb, ' ' age35dz\n" +
//	                "   ,bb.dzzs,bb.age37 age35sp,bb.age35zz,bb.age30fz \n" +
//	                "   from (\n" +
//	                " select a.ssxq,a.xqdm b0111,\n" +
//	                "       (select count(1) from QXZSDWNQGBPBQK b where a.ssxq = b.ssxq) dzzs,\n" +
//	                "       (select count(1)\n" +
//	                "          from QXZSDWNQGBPBQK b\n" +
//	                "         where a.ssxq = b.ssxq\n" +
//	                "           and b.age < 37) age37,\n" +
//	                "       (select count(1)\n" +
//	                "          from QXZSDWNQGBPBQK b\n" +
//	                "         where a.ssxq = b.ssxq\n" +
//	                "           and b.age < 37 and (A0165 LIKE '%07%' OR A0165 LIKE '%09%') ) age35zz,\n" +
//	                "           (select count(1)\n" +
//	                "          from QXZSDWNQGBPBQK b\n" +
//	                "         where a.ssxq = b.ssxq\n" +
//	                "           and b.age < 32 and (A0165 LIKE '%08%' OR A0165 LIKE '%13%') ) age30fz \n" +
//	                "  from (select xqdm, ssxq from QXZSDWNQGBPBQK group by xqdm, ssxq) a,b01 t where a.xqdm=t.b0111 order by t.b0269) bb";

	      String sql="with QXZSDWNQGBPBQK as ("
	      		+ " select  distinct a01.a0000,a01.a0101,substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2) age,substr(a02.a0201b,1,15) xqdm,(select b.b0101 from b01 b where b.b0111= substr(a02.a0201b,1,15)) ssxq,A0165"
	      		+ "  from A01 a01, A02 a02, B01 b01"
	      		+ " WHERE a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and b01.b0111 like '001.001.004%'"
	      		+ "   and length(b0111)=23 and substr(b0124,1,1)<>'8' and b0101 not like '%选调生%' and a02.a0281='true' and a02.a0255='1'"
	      		+ "   and (A0165 LIKE '%07%' OR A0165 LIKE '%08%' OR A0165 LIKE '%09%' OR A0165 LIKE '%13%')"
	      		+ "   and a02.a0201d = '1' and a01.A0163 = '1' and a01.status = '1') "
	      		+ "   select rownum,bb.ssxq,bb.b0111,"
	      		+ "		decode(bb.ssxq,'上城区',62,'拱墅区',48,'西湖区',61,'滨江区',48,'萧山区',70,'余杭区',66,'临平区',61,'钱塘区',51,'富阳区',66,'临安区',71,'桐庐县',64,'淳安县',66,'建德市',69,0) zsdwzs,"
	      		+ "     decode(bb.ssxq,'上城区',35,'拱墅区',33,'西湖区',34,'滨江区',27,'萧山区',37,'余杭区',38,'临平区',36,'钱塘区',28,'富阳区',37,'临安区',37,'桐庐县',37,'淳安县',35,'建德市',37,0) dzgzzs"
	      		+ "	  , dzzs zsdwzsry, "
	      		+ "  (select count(distinct m.a0000) from QXZSDWNQGBPBQK m,a02 n,b01 k where m.a0000=n.a0000 and n.a0201b=k.b0111 and n.a0281='true' "
	      		+ "		 and k.b0111 like bb.b0111||'%' and length(b0111)=23 and substr(b0124,1,1)<>'8' and b0194='1' and b0101 not like '%选调生%' "
	      		+ "      and (substr(k.b0131,1,2) in ('31','32','34') or k.b0131 in ('1001','1002','1004')))  dzgzzsry,"
	      		+ " ceil(dzzs*0.15) age35yp,age37 age35sb"
	      		+ "   , to_char( age37*100/dzzs,'fm990.0')||'%' age35db , age35zz,age30fz,"
	      		+ "   (select count(1) from b01 k where b0111 in (select n.a0201b from QXZSDWNQGBPBQK m,a02 n where m.a0000=n.a0000 and m.age<37 and n.a0281='true') "
	      		+ "		 and k.b0111 like bb.b0111||'%' and length(b0111)=23 and substr(b0124,1,1)<>'8' and b0194='1' and b0101 not like '%选调生%' "
	      		+ "		 and (substr(k.b0131,1,2) in ('31','32','34') or k.b0131 in ('1001','1002','1004'))) age35dz"
	      		+ "   from ( select a.ssxq,a.xqdm b0111,  count(1) dzzs,"
	      		+ "       sum(case when a.age < 37 then 1 else 0 end) age37,"
	      		+ "       sum(case when a.age < 37 and (A0165 LIKE '%07%' OR A0165 LIKE '%09%') then 1 else 0 end) age35zz,"
	      		+ "       sum(case when a.age < 32 and (A0165 LIKE '%08%' OR A0165 LIKE '%13%') then 1 else 0 end) age30fz "
	      		+ "  from QXZSDWNQGBPBQK a,b01 t where a.xqdm=t.b0111 group by ssxq,xqdm,b0269 order by t.b0269) bb ";
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
