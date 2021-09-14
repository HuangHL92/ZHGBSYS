package com.insigma.siis.local.pagemodel.fxyp.base;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;

public class CommonSelSQL {
	private PageModel pm;
	public CommonSelSQL(PageModel pageModel) throws RadowException {
		this.pm = pageModel;
		this.mntp00 = this.pm.getPageElement("mntp00").getValue();
		//区县市时，单位到区一级
		this.b01id = this.pm.getPageElement("b01idkq").getValue();
		this.fxyp06 = this.pm.getPageElement("fxyp06").getValue();
		this.mntp05 = this.pm.getPageElement("mntp05").getValue();
		this.dwmc = this.pm.getPageElement("dwmc").getValue();
	}
	private String mntp00;
	private String b01id;
	private String fxyp06;
	private String mntp05;
	private String dwmc;
	public CommonSelSQL(PageModel pageModel,String mntp00, String b01id, String fxyp06) throws RadowException {
		super();
		this.pm = pageModel;
		this.mntp00 = mntp00;
		this.b01id = b01id;
		this.fxyp06 = fxyp06;
		this.mntp05 = this.pm.getPageElement("mntp05").getValue();
		this.dwmc = this.pm.getPageElement("dwmc").getValue();
	}


	public String getNoticeSetgrid() throws RadowException{
		//String mntp00 = this.pm.getPageElement("mntp00").getValue();
		//String mntp05 = this.pm.getPageElement("mntp05").getValue();
		//String dwmc = this.pm.getPageElement("dwmc").getValue();
		String filter = "";
		
	 
		String sql = "";
		
	  
		  if("2".equals(mntp05)){//区县
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter = " and s.b0101 like '%"+dwmc+"%'";
			  }
			  sql = "select s.b0101 jgmc,fy - fysp fyqp, jcy - jcysp jcyqp, bzdw - bzspdw bzqpdw,s.b01id,"
			  		+ "bzzf - bzspzf bzqpzf, bzrd - bzsprd bzqprd, bzzx - bzspzx bzqpzx,b0111,bz, b0236,"
			  		+ " bzdw,bzzf,bzrd,bzzx,bzspdw,bzspzf,bzsprd,bzspzx,fy+jcy fj,fysp+jcysp fjsp,fy,jcy,fysp,jcysp,"
					  
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1001' and m.mntp00='"+mntp00+"') bzqpdw_yggw,"
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1004' and m.mntp00='"+mntp00+"') bzqpzf_yggw,"
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1003' and m.mntp00='"+mntp00+"') bzqprd_yggw,"
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1005' and m.mntp00='"+mntp00+"') bzqpzx_yggw,"
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1007' and m.mntp00='"+mntp00+"') jcyqp_yggw,"
			  		+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='1006' and m.mntp00='"+mntp00+"') fyqp_yggw,"
			  		
			  		+" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1001' and m.mntp00='"+mntp00+"') bzqpdw_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1004' and m.mntp00='"+mntp00+"') bzqpzf_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1003' and m.mntp00='"+mntp00+"') bzqprd_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1005' and m.mntp00='"+mntp00+"') bzqpzx_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1007' and m.mntp00='"+mntp00+"') jcyqp_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='1006' and m.mntp00='"+mntp00+"') fyqp_ygrx,"
					  
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='Z01' and m.mntp00='"+mntp00+"') zjqp_ygrx,"
					  
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" "+get_v_hz_mntp_qxsdw(mntp00)+" s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }else if("1".equals(mntp05)){//市直
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter = " and jgmc like '%"+dwmc+"%'";
			  }
			  sql = "select jgmc,zzhd,fzhd,zzsp,fzsp,zshd,zssp,bzzs,s.b0111,s.b01id,bz,b0234,b0235,b0236,"
					  
					+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_yggw,"
					+" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='Z01' and m.mntp00='"+mntp00+"') zjqp_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" "+get_v_hz_mntp_sz(mntp00)+" s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }else if("4".equals(mntp05)){//国企
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter = " and jgmc like '%"+dwmc+"%'";
			  }
			  sql = "select jgmc,zzhd,fzhd,zzsp,fzsp,zshd,zssp,bzzs,s.b0111,s.b01id,bz,b0234,b0235,b0236,"
					  +" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_yggw,"
					  +" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='Z01' and m.mntp00='"+mntp00+"') zjqp_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" "+get_v_hz_mntp_gqgx(mntp00)+" s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }else if("3".equals(mntp05)){//平台
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter = " and jgmc like '%"+dwmc+"%'";
			  }
			  sql = " select b0101 jgmc, b0111, s.b01id, nvl(bzzz,0)-nvl(bzspzz,0) b0234, nvl(bzfz,0)-nvl(bzspfz,0) b0235 ,bz b0236,"
			  		+ " bzzz zzhd,bzfz fzhd,bzspzz zzsp,bzspfz fzsp,"
					  +" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_yggw,"
					  +" (select sum(zwqc01) from V_MNTP_GW_RY_INNERJOIN m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='101' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='301' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					 +" (select count(1) from v_mntp_gw_ry m where m.b01id=s.b01id and m.fxyp06='Z01' and m.mntp00='"+mntp00+"') zjqp_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" "+get_v_hz_mntp_qxspt(mntp00)+" s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }
		  
		  return sql;
	}
	
	
	//调配前岗位加调配后
	public String getTPQSQL() throws RadowException, AppException{
		  //String mntp00 = this.pm.getPageElement("mntp00").getValue();
		  
		  //String b01id = this.pm.getPageElement("b01idkq").getValue();
		  //String fxyp06 = this.pm.getPageElement("fxyp06").getValue();
		  String b0111 = HBUtil.getValueFromTab("b0111", "b01", "b01id='"+b01id+"'");
		  /*String b01id = this.getPageElement("b01id").getValue();
		  String sql = "select a.a0000,a.a0101,a.a0192a,m.fxyp02 yxgw,r.tp0100 from a01 a,hz_mntp_gw m,rxfxyp r "
		  		+ " where m.b01id='"+b01id+"' and m.fxyp00=r.fxyp00 and m.mntp00='"+mntp00+"' and a.a0000=r.a0000";*/
		  
		  //String mntp05 = this.pm.getPageElement("mntp05").getValue();
			
		  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";
		
		  
		  
		  
		  
		  String sql = "";
			
		  
		  if("2".equals(mntp05)){//区县领导班子
		  
			  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000, a0101,a01.a0192a,a02.a0215a,"
			  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
			  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
			  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
			  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
			  		
			  		+ "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"

			  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
			  		+ " '"+b01id+"' b01id ,null zwqc01,c.sortnum,null ps,null zwqc00"
			  				+ " ,null a0101nm,a02.a0225,a02.a0201e,null a0501b"
			  		+ " FROM a02, a01,"
			  		+ " (select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b, "
			  		+ " (select * from GWSORT where mntp00 = '"+mntp00+"' and sorttype = '1') c"
			  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and b.a01bzid(+) = a02.a0200 "
			  		
			  		+ "  and  c.SORTID(+) = a02.a0200"
			  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
			  		+ " and a02.a0201b in "
			  		+ " (select b0111 from b01 b where b.b0131 in('"+fxyp06+"') "
			  		+ " and b.b0111 like '"+b0111+".%')"
				       + " and not exists (select 1 from GWZWREF gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"')";
			  
			  
		  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台
			  sql = "select a02.a0201b,a02.a0200,a01.a0000,a0101,a01.a0192a,a02.a0215a,"
		   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
	       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
	       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
	       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
	       
	       + "b.a01bzdesc,b.b01id b01idbz,b.b0101 b0101bz,(select b0111 from b01 where b01.b01id=b.b01id) b0111bz,"
	       
	       + " nvl(a02.a0201e,'Z')||'01' zrrx, '"+b01id+"' b01id"
	       + " ,null zwqc01,c.sortnum,null ps,null zwqc00,null a0101nm,a02.a0225,a02.a0201e,null a0501b"
	       +"   from a01, a02 ,"
	       + " (select * from HZ_MNTP_BZ where mntp00='"+mntp00+"' and a01bztype='1') b,"
	       + " (select * from GWSORT where mntp00 = '"+mntp00+"' and sorttype = '1') c"
	       + " where a01.A0000 = a02.a0000 AND a02.a0281 = 'true' and b.a01bzid(+) = a02.a0200"
	       +"    AND a02.a0255 = '1' "
	       //+" and a02.a0201e in ('"+fxyp06+"')"
	       + "  and  c.SORTID(+) = a02.a0200"
	       + " and a01.a0163='1'"
	       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
	       +"    and a02.a0201b = '"+b0111+"'"
	       + " and not exists (select 1 from GWZWREF gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"')";
		  }
		  
		  
		  sql += " order by " + ordersql;
		  sql = "select * from ("+sql+") a01 ";
		  
		  String unionSql = "select null a0201b,null a0200, null a0000,"+
		  "(select listagg(a01.a0101, ',') within group(order by r.sortnum)   from rxfxyp r,a01 where r.zwqc00=t.zwqc00 and a01.a0000=r.a0000) a0101,"+
		  "                       t.a0192a,t.fxyp02 a0215a,'1' personStatus,1 fxyp07,fxyp00,null tp0100,"+
		  "                       null a01bzdesc,null b01idbz,null b0101bz,null b0111bz,null zrrx,'"+b01id+"' b01id,"+
		  "                       t.zwqc01, c.sortnum,null ps,t.zwqc00,"
		  + "(select a0101 from a01 m,GWZWREF n where m.a0000=n.a0000 and t.fxyp00=n.fxyp00 and t.zwqc00=n.zwqc00 and n.mntp00 = t.mntp00) a0101nm,"
		  + " null a0225 ,t.a0201e,t.a0501b"+
		  "                  from hz_mntp_gw t,"+
		  "                       (select *"+
		  "                          from GWSORT"+
		  "                         where mntp00 = '"+mntp00+"'"+
		  "                           and sorttype = '2') c"+
		  "                 where "+
		  "                   t.fxyp07 = 1"+
		  "                   and t.mntp00 = '"+mntp00+"'"+
		  "                   and t.b01id = '"+b01id+"'"+
		  "                   and t.fxyp06 = '"+fxyp06+"'"+
		  "                   and c.SORTID(+) = t.fxyp00"+
		  "                 order by t.fxyp00, c.sortnum";
		  unionSql = " select * from ("+unionSql+") ";
		  sql = "select * from ("+sql+" union all "+unionSql+") a01 order by sortnum";
		  
		  
		  
		  return sql;
	}
	
	
	
	private String get_v_hz_mntp_qxsdw(String mntp00){
		String sql = " (select b0101,b0111,b01id,bzdw,bzzf,bzrd,bzzx,bzspdw,bzspzf,bzsprd,bzspzx,fy,jcy,fysp,jcysp,bz,b0236,'' b0104,'1' bztype ,'' b01idfrom from QXSLDBZHZB t"+
					" union all"+
					"   select b0101,b0111,b01id,bzdw,bzzf,bzrd,bzzx,bzspdw,bzspzf,bzsprd,bzspzx,fy,jcy,fysp,jcysp,bz,b0236, b0104, bztype , b01idfrom from HZ_MNTP_ORG_QXS t where mntp00='"+mntp00+"') ";
		return sql;
	}
	
	private String get_v_hz_mntp_sz(String mntp00){
		String sql = " (select jgmc,zzhd,zzsp,fzhd,fzsp,zshd,zssp,bzzs,b0111,b01id,bz,b0234,b0235,b0236 ,'' b0104,'1' bztype ,'' b01idfrom from SZDWHZB"
					+"  union all"
					+"  select jgmc,zzhd,zzsp,fzhd,fzsp,zshd,zssp,bzzs,b0111,b01id,bz,b0234,b0235,b0236 , b0104, bztype , b01idfrom from HZ_MNTP_ORG_QT where bztype='1' and mntp00='"+mntp00+"') ";
		return sql;
	}
	
	private String get_v_hz_mntp_gqgx(String mntp00){
		String sql = " (select jgmc,zzhd,zzsp,fzhd,fzsp,zshd,zssp,bzzs,b0111,b01id,bz,b0234,b0235,b0236 ,'' b0104,'1' bztype ,'' b01idfrom from GQGXHZB"
					+"  union all"
					+"  select jgmc,zzhd,zzsp,fzhd,fzsp,zshd,zssp,bzzs,b0111,b01id,bz,b0234,b0235,b0236 , b0104, bztype , b01idfrom from HZ_MNTP_ORG_QT where bztype='4' and mntp00='"+mntp00+"') ";
		return sql;
	}
	
	private String get_v_hz_mntp_qxspt(String mntp00){
		String sql = " (select b0101,b0111,b01id,bzzz,bzfz,bzspzz,bzspfz,bz,'' b0104,'1' bztype ,'' b01idfrom from QXSPTHZB"
					+"  union all"
					+"  select jgmc b0101,b0111,b01id,zzhd bzzz,fzhd bzfz,zzsp bzspzz,fzsp bzspfz,bz, b0104, bztype , b01idfrom from HZ_MNTP_ORG_QT where bztype='3' and mntp00='"+mntp00+"') ";
		return sql;
	}
	
	
	public static String getMNTPDW(){
		String sql = " select b0101,b0104,b0111,b0121,b01id,null b0131,b01id qxb01id,b0269   from b01 t where ((t.b0121 = '001.001.002' "
				+ " and t.b01id not in ('6C759252379B4E01BFA614D2B06D31FA','B9E46D6110134E77B7273E01EACF21A3','6B48873119494B34A80F0E314549813D') "
				+ " ) or t.b01id in (  'F0E0EA716C0442328DE41B549BC73C9C','0B3931450F264D36895440B7EAB46B81','24A2EF2597174AE8BF1E65D7EE34DD24', "
				+ " '70BF51BDAD28458DA98B7092B049AECF','BC923D3D6F034B8584C4DDF60071C895','96D6674BC36C428B84166728D0A21455', "
				+ " '72C8D307601E4D15B4DED1B82B41E8DE','A6F4AE51884D4231B7AF3C8623A56884')) "
				+ " or ( t.b0111 like '001.001.003.%' and t.b01id not in "
				+ " ( '36E7A2F90629493AA4FCAB4345AF6F77','6C5E5C831D8443D48EBF42E89CDE7055','DBAD9B3F2C2E4BAE935FC8F4C245555D',"
				+ " '3601E75C9C6F4EF4AD7274C9D6CA09B5','B9E46D6110134E77B7273E01EACF21A3',"
				+ " '6C759252379B4E01BFA614D2B06D31FA','D563DF546904420B8D042038B927549A',"
				+ " '7D174AA620804C608774111467F3F111') and t.b0111 not like '001.001.002.02N%' "
				+ " and t.b0114 not like '%X09') "
				+ " or (t.b0121 like '001.001.004.%' and length(b0111)=19 and b0131='3480') "
				+ " union all select b0101,b0104,b0111,b0121,b01id,b0131,"
				+ " (select b01id from b01 b where b.b0111=substr(t.b0111,0,15)) qxb01id,b0269   from b01 t where (t.b0121 like '001.001.004%' "
				+ " and b0131 in('1001','1003','1004','1005','1006','1007')) "
				+ " order by b0269";
		return sql;
	}
	
	
	//设置岗位初始化排序
	public void setGWSort() throws RadowException, AppException{
		//String mntp00 = this.pm.getPageElement("mntp00").getValue();
		  
		//String b01id = this.pm.getPageElement("b01idkq").getValue();
		//String fxyp06 = this.pm.getPageElement("fxyp06").getValue();
		String b0111 = HBUtil.getValueFromTab("b0111", "b01", "b01id='"+b01id+"'");
		String sql = "";
		if(b0111!=null&&b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
			sql = "select * from gwsort where mntp00='"+mntp00+"' and  zrrx='"+fxyp06+"'  and b01id = '"+b01id+"'";
		}else {
			sql = "select * from gwsort where mntp00='"+mntp00+"' and b01id = '"+b01id+"'";
		}	
		List<Object[]> l = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(l.size()==0){
			String selsql = getTPQSQL();
			selsql = " select '"+mntp00+"','1',a0200,'"+b01id+"',a0225,zrrx from("+selsql+") x ";
			HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) "
					+ selsql);
		}
	}
	
}
