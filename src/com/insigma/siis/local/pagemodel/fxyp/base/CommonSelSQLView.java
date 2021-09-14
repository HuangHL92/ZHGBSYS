package com.insigma.siis.local.pagemodel.fxyp.base;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;

public class CommonSelSQLView {
	private PageModel pm;
	public CommonSelSQLView(PageModel pageModel) {
		this.pm = pageModel;
	}
	
	public String getNoticeSetgrid() throws RadowException{
		String mntp00 = this.pm.getPageElement("mntp00").getValue();
		String mntp05 = this.pm.getPageElement("mntp05").getValue();
		String dwmc = this.pm.getPageElement("dwmc").getValue();
		String filter = " and s.b01id in (select b01id from fxyp where mntp00='"+mntp00+"') ";
		
	 
		String sql = "";
		
	  
		  if("2".equals(mntp05)){//区县
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter += " and s.b0101 like '%"+dwmc+"%'";
			  }
			  sql = "select s.b0101 jgmc,fy - fysp fyqp, jcy - jcysp jcyqp, bzdw - bzspdw bzqpdw,s.b01id,"
			  		+ "bzzf - bzspzf bzqpzf, bzrd - bzsprd bzqprd, bzzx - bzspzx bzqpzx,b0111,bz, b0236,"
			  		+ " bzdw,bzzf,bzrd,bzzx,bzspdw,bzspzf,bzsprd,bzspzx,fy+jcy fj,fysp+jcysp fjsp,fy,jcy,fysp,jcysp,"
					  
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1001' and m.mntp00='"+mntp00+"') bzqpdw_yggw,"
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1004' and m.mntp00='"+mntp00+"') bzqpzf_yggw,"
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1003' and m.mntp00='"+mntp00+"') bzqprd_yggw,"
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1005' and m.mntp00='"+mntp00+"') bzqpzx_yggw,"
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1007' and m.mntp00='"+mntp00+"') jcyqp_yggw,"
			  		+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1006' and m.mntp00='"+mntp00+"') fyqp_yggw,"
			  		
			  		+" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1001' and m.mntp00='"+mntp00+"') bzqpdw_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1004' and m.mntp00='"+mntp00+"') bzqpzf_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1003' and m.mntp00='"+mntp00+"') bzqprd_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1005' and m.mntp00='"+mntp00+"') bzqpzx_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1007' and m.mntp00='"+mntp00+"') jcyqp_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1006' and m.mntp00='"+mntp00+"') fyqp_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" QXSLDBZHZB s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }else if("1".equals(mntp05)){//市直
			  if(dwmc!=null&&!"".equals(dwmc)){
				  filter = " and jgmc like '%"+dwmc+"%'";
			  }
			  sql = "select jgmc,zzhd,fzhd,zzsp,fzsp,zshd,zssp,bzzs,s.b0111,s.b01id,bz,b0234,b0235,b0236,"
					  
					+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_yggw,"
					+" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" SZDWHZB s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
			  sql = sql + " union  ";
			  sql += "select jgmc,zzhd,fzhd,zzsp,fzsp,zshd,zssp,bzzs,s.b0111,s.b01id,bz,b0234,b0235,b0236,"
					  +" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_yggw,"
					  +" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" GQGXHZB s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
			  sql = sql + " union  ";
			  sql += "select b0101 jgmc,null zzhd,null fzhd,null zzsp,null fzsp,null zshd,null zssp,null bzzs,  b0111,s.b01id,'' bz, to_char(nvl(bzzz,0)-nvl(bzspzz,0)) b0234, to_char(nvl(bzfz,0)-nvl(bzspfz,0)) b0235 ,bz b0236,"
					  +" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_yggw,"
					  +" (select sum(fxyp07) from hz_mntp_gw m where m.b01id=s.b01id and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_yggw,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='1' and m.mntp00='"+mntp00+"') b0234_ygrx,"
					  +" (select count(1) from hz_mntp_gw m,rxfxyp r where m.b01id=s.b01id and m.fxyp00=r.fxyp00 and m.fxyp06='3' and m.mntp00='"+mntp00+"') b0235_ygrx,"
					  +" (select replace(to_char(wm_concat(m.fxyp02||'@@'||m.fxyp06||'@@'||m.fxyp00)),',','{RN}') from hz_mntp_gw m where m.b01id=s.b01id  and m.mntp00='"+mntp00+"') yxgw  from" 
					  +" QXSPTHZB s where not exists(select 1 from hz_mntp_dwref d where d.b01id=s.b01id and d.mntp00='"+mntp00+"') "+filter;
		  }
		  
		  return sql;
	}
}
