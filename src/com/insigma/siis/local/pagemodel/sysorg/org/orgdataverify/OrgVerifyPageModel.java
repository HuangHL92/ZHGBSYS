package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.util.HashMap;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class OrgVerifyPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("errorDetailGrid.dogridquery");
		return 0;
	}
	
	
	@PageEvent("echo.onclick")
	public int  echo() throws RadowException{
		String cueUserid=SysUtil.getCacheCurrentUser().getId();
		String b01=haveb01(cueUserid);
		if(StringUtil.isEmpty(b01)){
			if("4028810f5f2aed67015f2aefeeee0002".equals(cueUserid)){
				b01="001.001";
			}else{
				this.setMainMessage("没有设置机构权限");
				return EventRtnType.FAILD;
			}
		}
		HBSession sess=HBUtil.getHBSession();
		String deletesql="delete from verify_error_list where  vel005='9' and vel010='"+cueUserid+"'";
		sess.createSQLQuery(deletesql).executeUpdate();
		String vel001Bf = " sys_guid()  "; 
		if(DBUtil.getDBType() == DBType.MYSQL){
			vel001Bf = " UUID() ";
		}
		String sql="(select b0111 vel002,'1' vru003,'2' vel003,b0111 vel004,'9' vel005,'错误:机构编码重复' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from b01 where b0114 in(select b0114 from b01 where b0111 like '"+b01+"%' group by b0114 having count(b0114)>1)) tt";
		String insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		this.getPageElement("bsType").setValue("9");
		this.setNextEventName("errorDetailGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	@PageEvent("validity.onclick")
	public int validity() throws RadowException{
		String cueUserid=SysUtil.getCacheCurrentUser().getId();
		String b01=haveb01(cueUserid);
		if(StringUtil.isEmpty(b01)){
			if("4028810f5f2aed67015f2aefeeee0002".equals(cueUserid)){
				b01="001.001";
			}else{
				this.setMainMessage("没有设置机构权限");
				return EventRtnType.FAILD;
			}
		}
		HBSession sess=HBUtil.getHBSession();
		String deletesql="delete from verify_error_list where  vel005='8' and vel010='"+cueUserid+"'";
		sess.createSQLQuery(deletesql).executeUpdate();
		String vel001Bf = " sys_guid()  "; 
		if(DBUtil.getDBType() == DBType.MYSQL){
			vel001Bf = " UUID() ";
		}
		String reg="select b0111 from b01 where  (b0114  not regexp  '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$' or b0114 is null or b0114='') and b0111 like '"+b01+"%'";
		if(DBUtil.getDBType() == DBType.ORACLE){
			reg="select b0111 from b01 where  (not regexp_like(b0114, '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$') or b0114 is null) and b0111 like '"+b01+"%'";
		}
		String sql="(select b0111 vel002,'1' vru003,'2' vel003,b0111 vel004,'8' vel005,'错误:机构编码错误' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from b01 where b0111 in("+reg+")) tt";
		String insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		this.getPageElement("bsType").setValue("8");
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("errorDetailGrid.dogridquery")
	public int doerrorDetailGridQuery(int start,int limit) throws RadowException{
		String cueUserid=SysUtil.getCacheCurrentUser().getId();
		String bsType=this.getPageElement("bsType").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
				sqlbf.append("/*"+Math.random()+"*/Select Vel001,  ")
				.append(" (Select case when b1.b0194 = '2' then (select ifnull(b2.b0101, '') from b01 b2 where b1.b0121 = b2.b0111) ")
				.append(" else ifnull(b1.B0101, '') end From b01 b1 Where b1.B0111 = Vel002 limit 0,1) vel002_b, ")
				.append("( Select ifnull(B0101, '')  From b01  Where B0111 = Vel002  limit 0,1) vel002_name, ")
				.append(" vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a Where 1 = 1   and vel003='2' and vel010='"+cueUserid+"' ");
				
			
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
				sqlbf.append("/*"+Math.random()+"*/Select Vel001,                ")
				.append(" (Select case when b1.b0194 = '2' then (select nvl(b2.b0101, '') from b01 b2 where b1.b0121 = b2.b0111) ")
				.append(" else nvl(b1.B0101, '') end From b01 b1 Where b1.B0111 = Vel002 And Rownum = 1) vel002_b, ")
				.append("( Select Nvl(B0101, '')  From b01  Where B0111 = Vel002  And Rownum = 1) vel002_name, ")
				.append(" vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a Where 1 = 1   and vel003='2'  and vel010='"+cueUserid+"' ");
				
					
		}
			if(!StringUtil.isEmpty(bsType)){
				sqlbf.append(" and vel005='"+bsType+"'");
			}else{
				sqlbf.append(" and (vel005='9' or vel005='8')");
			}
			
		
		
		sqlbf.append(" order by Vel002_name");
		CommonQueryBS.systemOut("xx------->"+sqlbf);
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("selectall.onclick")
	public int selectall() throws RadowException{
		this.getPageElement("bsType").setValue("");
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String haveb01(String cueUserid){
		HBSession sess=HBUtil.getHBSession();
		//String cueUserid=SysUtil.getCacheCurrentUser().getId();
		String b0111sql="";
		if(DBType.ORACLE==DBUtil.getDBType()){
			b0111sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc) where ROWNUM =1";
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			b0111sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
		}
		Object b01=sess.createSQLQuery(b0111sql).uniqueResult();
		if(b01==null||"".equals(b01)){
			return "";
		}else{
			return b01.toString();
		}
	}
	
	
	/**
	 * 双击错误详细信息，跳转到机构修改页面
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("errorDetailGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid() throws RadowException, AppException{
		Grid grid = (Grid)this.getPageElement("errorDetailGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String b0111=map.get("vel002").toString();
		if(StringUtil.isEmpty(b0111)){
			throw new AppException("无法获取错误主体的类型！");
		}
		String groupid="";
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map_x = gridList.get(i);
			groupid+=map_x.get("vel002")+",";
		}
		if(!StringUtil.isEmpty(groupid)){
			groupid=groupid.substring(0,groupid.length()-1);
		}
		String pardata=b0111+","+groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		//this.request.getSession().setAttribute("tag", "0");
		this.getExecuteSG().addExecuteCode("$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',860,510,'','"+request.getContextPath()+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
