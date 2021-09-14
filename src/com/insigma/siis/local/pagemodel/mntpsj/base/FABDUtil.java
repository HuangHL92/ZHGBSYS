package com.insigma.siis.local.pagemodel.mntpsj.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class FABDUtil {
	//区县市机构编码  最外一级
	public static final String QXS_B0111="001.001.004";
	
	
	
	
	
	
	
	
	
	public static int getLength(List<List<Map<String, Object>>> list){
		int l = 0;
		for(List ol : list){
			if(ol.size()>l){
				l = ol.size();
			}
		}
		return l;
	}




	public static List<HashMap<String, Object>> getTPXX(String b0111, String famx00) throws AppException {
		
		String liurenTable = ",(select tpdesc,tpdesc2,infoid tpyjid,a0200 from HZ_MNTP_SJFA_INFO where famx00f='"+famx00+"') info ";
		String liurenWhere = " and info.a0200(+)=t.fxyp00 ";
		
		String sql = "select b0111,b0131,t.zwqc00,famx00,fxyp02 a0215a,fxyp00,fxyp00_ry,info.tpyjid,info.tpdesc,info.tpdesc2,"
				+ " (select code_name2 from code_value t where t.code_type='KZ01' and code_value=t.bzgw) a0215a_bz,t.bzgw, "
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,"
				+ " (select b0104 from b01 where b01.b0111 = a02.a0201b and b01.b0111!='"+b0111+"') laiyuan, "
				+ " (select b0104 from b01 where b01.b0111 = '"+b0111+"') jgjc, "
				+ " t.a0201e rya0201e,a02.a0201e a02a0201e,t.gwa0200,t.rya0200,t.gwmc,a02.a0201b,"
				+ "count(1) over(partition by fxyp00)  gwcount,"
				+ "rank() over(partition by fxyp00 order by b0111_ry,  sortnum)  rk,"
				+ " GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a0101 xingming,"
 +" decode(a01.a0107,null,null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,"
 +" t.a0000,a01.a0192a,a01.zgxl,a01.qrzxl,a01.a0104,a01.a0141,"
 + "decode(a01.a0288,null,null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288 "
 + " from v_mntp_sj_gw_ry t,a01,a02 " + liurenTable
 +" where t.a0000 = a01.a0000(+) and t.rya0200=a02.a0200(+) "+liurenWhere
 + " and t.famx00='"+famx00+"' and t.b0111='"+b0111+"'  "
 +" order by fxyp01,b0111_ry,sortnum";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}

	/**
	 * 获取除区县市现状的sql
	 * @param b0111
	 * @return
	 */
	public static String getXZXXSZSQL(String b0111){
		return getXZXXSZSQL(b0111, null,null);
	}

	/**
	 * 获取现状的sql
	 * @param b0111
	 * @return
	 */
	public static String getXZXXSZSQL(String b0111,String fabd00,String famx00, String selfamx00){
		/*String sel = "";*/
		String sel2 = "";
		String subSel = "";
		String subSelCol = "";
		String subSelWhere = "";
		
		
		String liurenTable = "";
		String liurenWhere = "";
		String liurenWhere2 = "";
		
		//区县条件
		String qxwhere = "";
		//if(b0111.startsWith("001.001.004")&&b0111.length()==19){
			qxwhere = " and a02.a0201e in ('1','3','31')  ";//全部
		//}
		
		if(!StringUtils.isEmpty(fabd00)){
			
			if(StringUtils.isEmpty(selfamx00)||"全部".equals(selfamx00)){
				selfamx00 = "";
			}else{
				selfamx00 = " and ry.famx00 = '"+selfamx00+"'  ";
			}
			sel2 = " null liuren,null quxianga0201e, null quxiang,tpdesc,tpyjid, ";
			subSel = " ,(select "
					+ " rya0200,"
					+"max(case"+
                    "     when ry.gwa0200 = ry.rya0200 then"
                    + "   1  "
                    + "   when b0111 = '"+b0111+"'  then"+
                    "      2 "+
                    "     else"+
                    "      0 "+
                    "   end) liuren, min(a0201e) quxianga0201e,"
                    +" to_char(wm_concat((select b0104||'￥'||b0111||'￥'"+
                    "                   from b01"+
                    "                  where b01.b0111 = ry.b0111 and b0111 != '"+b0111+"'))) quxiang"+
                    " from v_mntp_sj_gw_ry ry"+
                    " where ry.fabd00 = '"+fabd00+"' and ry.rya0200 is not null " + selfamx00 +
                    " group by ry.rya0200"+
                    " ) ry ";
			subSelWhere = " and ry.rya0200(+) = a02.a0200 ";
			subSelCol = " liuren, quxianga0201e,quxiang,tpdesc,tpyjid,  ";//liuren, quxianga0201e, 
			
			
			liurenTable = ",(select tpdesc,infoid tpyjid,a0200 from HZ_MNTP_SJFA_INFO where fabd00='"+fabd00+"' and famx00f='"+famx00+"') info ";
			liurenWhere = " and info.a0200(+)=a02.a0200 ";
			liurenWhere2 = " and info.a0200(+)=BGWQP.qpid ";
		}
		
		String sql = "select *" + 
				"  from (select a02.a0200," + 
				" nvl((select sortid from gwpxall b where b0111 = '"+b0111+"' and a02.a0200 = b.id), 99999) sortid, "+
				" (select bzgw from gwpxall b where b0111 = '"+b0111+"' and a02.a0200 = b.id)  bzgw, "+
				"               a01.a0000," + 
				"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
				"               decode(a01.a0107,null,null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
				"               a02.a0215a," + 
				"               a02.a0248," + 
				"               a02.a0277,a01.qrzxl,a01.zgxl," + 
								subSelCol		+
				"               a0165," + 
				"               decode(a01.a0192f,null,null,substr(a01.a0192f, 0, 4) || '.' || substr(a01.a0192f, 5, 2)) a0192f," + 
				"               a0201b," + 
				"               '"+b0111+"' qx," + 
				"               '' b0131," + 
				"               a02.a0225 ,a02.a0201e,"
				+ "				decode(a01.a0288,null,null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288,"
				+ "				GET_TPBXUELI2(a01.qrzxl,a01.zzxl,a01.qrzxw,a01.zzxw) tp0105,a0192a" + 
				"          from a02, a01" + subSel + liurenTable +
				"         WHERE a01.A0000 = a02.a0000  " + subSelWhere + liurenWhere + qxwhere +
				"           AND a01.a0163 = '1'" + 
				"           and a01.status = '1'" + 
				"           and a02.a0281 = 'true'" + 
				//"           AND a02.a0255 = '1'" + 
				"           and a02.a0201b = '"+b0111+"'" + 
				"        union all" + 
				"        select qpid a0200," + 
				" nvl((select sortid from gwpxall b where b0111 = '"+b0111+"' and qpid = b.id), 99999) sortid, "+
				"  (select bzgw from gwpxall b where b0111 = '"+b0111+"' and qpid = b.id)  bzgw, "+
				"               '' a0000," + 
				"               '' a0101," + 
				"               '' a0107," + 
				"               gwname a0215a," + 
				"               '' a0248," + 
				"               '' a0277,'' qrzxl,'' zgxl," + 
								sel2		+
				"               '' a0165," + 
				"               '' a0192f," + 
				"               '"+b0111+"' a0201b," + 
				"               '"+b0111+"' qx," + 
				"               '' b0131," + 
				"               '' a0225,gwzf a0201e,'' a0288,'' tp0105,'' a0192a" + 
				"          from BGWQP" +  liurenTable +
				"         where b01id =" + 
				"               (select b01id from b01 where b0111 = '"+b0111+"') " + liurenWhere2
						+ ") t" + 
				"  order by sortid,lpad(a0225, 25, '0')";
		return sql;
	}
	/**
	 * 现状市直
	 * @param b0111
	 * @param selfamx00 选择的模拟方案
	 * @return
	 * @throws AppException
	 */
	public static List<HashMap<String, Object>> getXZXXSZ(String b0111,String fabd00,String famx00, String selfamx00) throws AppException {
		
		String sql = getXZXXSZSQL(b0111, fabd00,famx00, selfamx00);
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	private static String getXZXXSZSQL(String b0111, String fabd00, String famx00) {
		return getXZXXSZSQL(b0111, fabd00,famx00, null);
	}




	public static String[] getMatcher(String regex, String source) {
		String result = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			result += matcher.group(0);
			source = source.replace(matcher.group(0), "");
		}
		return new String[]{result,source};
	}
	
	public static void main(String[] args) {
		String url = "市交投集团￥001.001.003.006￥,市委改革办￥001.001.002.01T￥";
		String regex = "￥[0-9a-zA-Z\\.]{0,}￥";
		// String regex = "(\\d{1,3}\\.){1,3}(\\d{1,3})";
		System.out.println(Arrays.toString(getMatcher(regex, url)));
	}
	
	
	
	public static boolean editable(String userid,String fabd00){
		String sql = "select fabd00 from HZ_MNTP_SJFA y "
				+ " where (fabd04 = '"+userid+"'  and fabd00='"+fabd00+"') "
				+ " or (exists (select 1 from HZ_MNTP_SJFA_USERREF u "
				+ " where mnur01 = '"+userid+"' and u.fabd00 = '"+fabd00+"' "
				+ " ))";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	
	
	
	
	
	
}
