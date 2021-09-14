package com.insigma.siis.local.pagemodel.fxyp.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class FABDUtil {
	//区县市机构编码  最外一级
	public static final String QXS_B0111="001.001.004";
	
	
	
	
	/**
	 * 根据b0111获取现状信息。//001.001.004.001.001
	 * @throws AppException 
	 */
	public static List<HashMap<String, Object>> getXZXX(String b0111) throws AppException{
		String sql = "select *" + 
				"  from (select a02.a0200," + 
				"               a01.a0000," + 
				"               a01.a0101," + 
				"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
				"               a02.a0215a," + 
				"               a02.a0248," + 
				"               a02.a0277," + 
				"               a0165," + 
				"               a0192f," + 
				"               a0201b," + 
				"               substr(a0201b,0,15) qx," + 
				"               (select b0131 from b01 where b0111=a0201b)b0131," + 
				"               a02.a0225" + 
				"          from a02, a01" + 
				"         WHERE a01.A0000 = a02.a0000" + 
				"           AND a01.a0163 = '1'" + 
				"           and a01.status = '1'" + 
				"           and a02.a0281 = 'true'" + 
				"           AND a02.a0255 = '1'" + 
				"           and a02.a0201b = '"+b0111+"'" + 
				"        union all" + 
				"        select qpid," + 
				"               ''," + 
				"               ''," + 
				"               ''," + 
				"               gwname," + 
				"               ''," + 
				"               ''," + 
				"               ''," + 
				"               ''," + 
				"               '"+b0111+"'," + 
				"               '"+b0111.substring(0,15)+"'," + 
				"               ''," + 
				"               ''" + 
				"          from BGWQP" + 
				"         where b01id =" + 
				"               (select b01id from b01 where b0111 = '"+b0111+"')) t" + 
				"  order by nvl((select sortid" + 
				"                from gwpxall b" + 
				"               where b0111 = '"+b0111+"'" + 
				"                 and t.a0200 = b.id)," + 
				"              99999)";

		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	
	
	
	public static int getLength(List<List<Map<String, Object>>> list){
		int l = 0;
		for(List ol : list){
			if(ol.size()>l){
				l = ol.size();
			}
		}
		return l;
	}




	public static List<HashMap<String, Object>> getTPXX(String b01id, String b0111, String b0131, String mntp00) throws AppException {
		String sql = "select *"
  +" from (select *"
  +"        from (SELECT a02.a0201b,"
  +"                     a02.a0200,"
  +"                     a01.a0000,"
  +"                   GET_tpbXingming(a01.a0101,"
  +"                                    a01.a0104,"
  +"                                     a01.a0117,"
  +"                                     a01.a0141) a0101,"
  +"    decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,"
  +"                     a01.zgxl,a01.qrzxl,"
  +"                     a01.a0192a,"
  +"                     a02.a0215a,"
  +"                     nvl((select decode(gw.fxyp07, 1, '1', -1, '2', '34')"
  +"                           from hz_mntp_gw gw"
  +"                          where gw.a0200 = a02.a0200"
  +"                            and gw.mntp00 ="
  +"                                '"+mntp00+"'"
 +"                             and fxyp07 = -1),"
  +"                         '34') personStatus,"
  +"                     (select gw.fxyp07"
  +"                        from hz_mntp_gw gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"'"
  +"                         and fxyp07 = -1) fxyp07,"
  +"                     (select gw.fxyp00"
  +"                        from hz_mntp_gw gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"'"
  +"                         and fxyp07 = -1) fxyp00,"
  +"                     (select gw.zwqc00"
  +"                        from hz_mntp_gw gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"'"
  +"                         and fxyp07 = -1) zwqc00,"
  +"                     (select gw.tp0100"
  +"                        from v_mntp_gw_ry gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"'"
  +"                         and fxyp07 = -1) tp0100,"
  +"                     b.a01bzdesc,"
  +"                     b.b01id b01idbz,"
  +"                     b.b0101 b0101bz,"
  +"                     (select b0111 from b01 where b01.b01id = b.b01id) b0111bz,"
  +"                     (select b0131 from b01 where b0111 = a02.a0201b) zrrx,"
   +"                    '"+b01id+"' b01id,"
   +"                    null zwqc01,"
  +"                     c.sortnum,"
  +"                     null ps,"
  +"                     b.rybz,"
  +"                     b.bmd"
 +"                 FROM a02,"
  +"                     a01,"
  +"                     (select *"
  +"                        from HZ_MNTP_BZ"
  +"                       where mntp00 = '"+mntp00+"'"
  +"                         and a01bztype = '1') b,"
  +"                     (select *"
  +"                        from GWSORT"
  +"                       where mntp00 = '"+mntp00+"'"
  +"                         and sorttype = '1') c"
  +"               WHERE a01.A0000 = a02.a0000"
  +"                 AND a02.a0281 = 'true'"
  +"                 AND a02.a0255 = '1'"
  +"                 and b.a01bzid(+) = a02.a0200"
  +"                 and c.SORTID(+) = a02.a0200"
  +"                 and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
  +"                 and a02.a0201b in"
  +"                     (select b0111"
  +"                        from b01 b"
  +"                       where b.b0131 ='"+b0131+"'"
  +"                         and b.b0121 = '"+b0111+"')"
  +"                 and not exists (select 1"
  +"                        from GWZWREF gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"')"
  +"                 and not exists (select gw.fxyp07"
  +"                        from hz_mntp_gw gw"
  +"                       where gw.a0200 = a02.a0200"
  +"                         and gw.mntp00 ="
  +"                             '"+mntp00+"'"
  +"                         and fxyp07 = -1)"
  +"               order by ((select rpad(b0269, 25, '.') ||"
  +"                                 lpad(a0225, 25, '0')"
  +"                            from (select a02.a0000,"
  +"                                         b0269,"
  +"                                         a0225,"
  +"                                         row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn"
  +"                                    from a02, b01"
  +"                                   where a02.a0201b = b01.b0111"
  +"                                     and a0281 = 'true'"
  +"                                     and a0201b like '"+b0111+"%') t"
  +"                           where rn = 1"
  +"                             and t.a0000 = a01.a0000))) a01"
  +"      union all"
  +"      select *"
  +"        from (select '' a0201b,"
  +"                     t.a0200 a0200,"
  +"                     t.a0000 a0000,"
  +"                     GET_tpbXingming(a01.a0101,"
  +"                                     a01.a0104,"
  +"                                     a01.a0117,"
  +"                                     a01.a0141) a0101,"
  +"   decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,"
  +"                     a01.zgxl,a01.qrzxl, "
  +"                     a01.a0192a a0192a,"
  +"                     t.fxyp02 a0215a,"
  +"                     '1' personStatus,"
  +"                     t.fxyp07 fxyp07,"
  +"                     fxyp00,"
  +"                     zwqc00,"
  +"                     tp0100,"
  +"                     b.a01bzdesc,"
  +"                     b.b01id b01idbz,"
  +"                     b.b0101 b0101bz,"
  +"                     (select b0111 from b01 where b01.b01id = b.b01id) b0111bz,"
  +"                     t.fxyp06 zrrx,"
  +"                     '"+b01id+"' b01id,"
  +"                     t.zwqc01,"
  +"                     c.sortnum,"
  +"                     t.sortnum ps,"
  +"                     b.rybz,"
  +"                     b.bmd"
  +"                from v_mntp_gw_ry t,"
  +"                     a01,"
  +"                     (select *"
  +"                        from HZ_MNTP_BZ"
  +"                       where mntp00 = '"+mntp00+"'"
  +"                         and a01bztype = '2') b,"
  +"                     (select *"
  +"                        from GWSORT"
 +"                        where mntp00 = '"+mntp00+"'"
  +"                         and sorttype = '2') c"
  +"               where t.a0000 = a01.a0000(+)"
  +"                 and t.fxyp07 = 1"
  +"                 and t.fxyp06 = '"+b0131+"'"
  +"                 and t.mntp00 = '"+mntp00+"'"
  +"                 and t.b01id = '"+b01id+"'"
  +"                 and b.a01bzid(+) = t.tp0100"
  +"                 and c.SORTID(+) = t.fxyp00"
  +"               order by t.fxyp00, t.sortnum)) a01"
 +" order by sortnum, nvl2(sortnum, ps, null)";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	
	
}
