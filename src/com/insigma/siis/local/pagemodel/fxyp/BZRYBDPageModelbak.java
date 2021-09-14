package com.insigma.siis.local.pagemodel.fxyp;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class BZRYBDPageModelbak extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("pgrid.dogridquery");
		this.setNextEventName("pgrid2.dogridquery");
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
	 

	  String sql = "select 2 from dual";	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 调配前
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid.dogridquery")
	public int pgrid(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//区县领导班子
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "'34' personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "'' fxyp00,"
		  		+ "'' tp0100,"

				+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"
		  		
		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ "'"+b01id+"' b01id FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"'34' personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
       +"'' fxyp00,"
       +"'' tp0100,"
       
		+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"
       
       + " a02.a0201e zrrx, '"+b01id+"' b01id"
       +"   from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
       +"    AND a02.a0255 = '1' and a02.a0201e in ('1','3')"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'";
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  /*String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,tp0100,"
	  		+ " t.fxyp06 zrrx,'"+b01id+"' b01id from v_mntp_gw_ry t, a01 "
	  		+ " where t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 ";	*/
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 调配后
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid2.dogridquery")
	public int pgrid2(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//区县领导班子
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
		  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
		  		
		  		+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"

		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ "'"+b01id+"' b01id FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')"
		  				+ " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
       
       + "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"
       
       + " a02.a0201e zrrx, '"+b01id+"' b01id"
       +"   from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
       +"    AND a02.a0255 = '1' and a02.a0201e in ('1','3')"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'"
       + " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";;
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,tp0100,"
			  
			+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = t.tp0100 and bz.mntp00='"+mntp00+"' and a01bztype='2') a01bzdesc,"
	  		
	  		+ " t.fxyp06 zrrx,'"+b01id+"' b01id from v_mntp_gw_ry t, a01 "
	  		+ " where t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 ";	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
}
