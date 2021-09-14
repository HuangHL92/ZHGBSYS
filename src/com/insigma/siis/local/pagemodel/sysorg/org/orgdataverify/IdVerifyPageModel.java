package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

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
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class IdVerifyPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("IDerrorDetailGrid.dogridquery");
		System.out.println("执行了初始化方法");
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
		String deletesql="delete from verify_error_list where  vel005='7' and vel010='"+cueUserid+"'";
		sess.createSQLQuery(deletesql).executeUpdate();
		String vel001Bf = " sys_guid()  "; 
		if(DBUtil.getDBType() == DBType.MYSQL){
			vel001Bf = " UUID() ";
		}
		String sql="(select a0000 vel002,'1' vru003,'1' vel003,a0184 vel004,'7' vel005,'错误:身份证号码重复' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from a01 where upper(a01.a0184) in (select upper(a01.a0184) from a01 where a0000 in(select  a0000 from a02 where a0201b like '"+b01+"%' and a0281='true' group by a0000) and status<>'4' group by upper(a01.a0184) having count(upper(a01.a0184))>1)"
				+ ") tt";
		if(b01.equals("001.001")){
			sql=sql.replace("a0000 in(select  a0000 from a02 where a0201b like '"+b01+"%' and a0281='true' group by a0000)", " 1=1 ");
		}
		String insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		this.getPageElement("bsType").setValue("7");
		this.setNextEventName("IDerrorDetailGrid.dogridquery");
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
		String deletesql="delete from verify_error_list where  vel005='6' and vel010='"+cueUserid+"' ";
		sess.createSQLQuery(deletesql).executeUpdate();
		String vel001Bf = " sys_guid()  "; 
		if(DBUtil.getDBType() == DBType.MYSQL){
			vel001Bf = " UUID() ";
		}
		StringBuffer sql1 = new StringBuffer();
		sql1 .append("     Case 																																																")
		 .append("        When Length(A0184) = 18 Then                                                                      ")
		 .append("         Mod(Substr(A0184, -2, 1), 2)                                                                     ")
		 .append("        When Length(A0184) = 15 Then                                                                      ")
		 .append("         Mod(Substr(A0184, -1), 2)                                                                        ")
		 .append("        Else                                                                                              ")
		 .append("         Null                                                                                             ")
		 .append("      End <> Case                                                                                         ")
		 .append("        When A0104 Is Not Null Then                                                                       ")
		 .append("         Mod(A0104, 2)                                                                                    ")
		 .append("        Else                                                                                              ")
		 .append("         Null                                                                                             ")
		 .append("      END                                                                                                 ");
		 //.append("      OR  (Length(A0184) <> 18 AND   Length(A0184) <> 15)                                                 ");
		
		String reg="and ("+sql1.toString()+")";
		String sql="(select a0000 vel002,'1' vru003,'1' vel003,a0000 vel004,'6' vel005,'错误:身份证号码与性别不符' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from a01 where "
				+ "  a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true') "+reg+" and status<>'4') tt";
		if("001.001".equals(b01)){
			sql=sql.replace("a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true')", " 1=1 ");
		}
		String insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		
		String reg2="  and  (Length(A0184) <> 18 AND   Length(A0184) <> 15)  ";
		sql="(select a0000 vel002,'1' vru003,'1' vel003,a0000 vel004,'6' vel005,'错误:身份证号码不是15或18位' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from a01 where "
				+ "  a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true') "+reg2+" and status<>'4') tt";
		if("001.001".equals(b01)){
			sql=sql.replace("a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true')", " 1=1 ");
		}
		insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		
		String reg3=" and (a0184 is null or a0184=''  or  substr(a0184,1,length(a0184)-1) not regexp '^[[:digit:]]+$')";
		if(DBUtil.getDBType() == DBType.ORACLE){
			reg3="  and (a0184 is null  or not regexp_like(substr(a0184,1,length(a0184)-1),'^[[:digit:]]+$'))";
		}
		sql=" (select a0000 vel002,'1' vru003,'1' vel003,a0000 vel004,'6' vel005,'错误:身份证号码为空或者不是纯数字' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from a01 where "
				+ "  a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true') "+reg3+" and status<>'4') tt";
		if("001.001".equals(b01)){
			sql=sql.replace("a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true')", " 1=1 ");
		}
		
		insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		
		String reg4=" and (ifnull(length(A0184),0)=15)";
		if(DBUtil.getDBType() == DBType.ORACLE){
			reg4="  and (nvl(length(A0184),0)=15)";
		}
		sql=" (select a0000 vel002,'1' vru003,'1' vel003,a0000 vel004,'6' vel005,'提示:身份证号码为15位' vel006,'' vel007,'' vel008,'' vel009,'"+cueUserid+"' vel010  from a01 where "
				+ "  a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true') "+reg4+" and status<>'4') tt";
		if("001.001".equals(b01)){
			sql=sql.replace("a0000 in (select a0000 from a02 where a0201b like '"+b01+"%' and a0281='true')", " 1=1 ");
		}
		
		insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+sql;
		sess.createSQLQuery(insertsql).executeUpdate();
		
		
		String concat=" (select vel002,'5' vru003,vel003,vel004,vel005,replace(wm_concat(vel006), ',', '；') vel006,vel007,vel008,vel009,vel010 from verify_error_list where vel005='6' and vel010='"+cueUserid+"' group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
		if(DBUtil.getDBType() == DBType.MYSQL){
			concat=concat.replace("wm_concat", "group_concat");
		}
		insertsql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "+concat;
		sess.createSQLQuery(insertsql).executeUpdate();
		
		String delete=" delete from verify_error_list where vel005='6' and vel010='"+cueUserid+"' and vru003='1'";
		sess.createSQLQuery(delete).executeUpdate();
		this.getPageElement("bsType").setValue("6");
		this.setNextEventName("IDerrorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("IDerrorDetailGrid.dogridquery")
	public int doerrorDetailGridQuery(int start,int limit) throws RadowException{
		System.out.println("正在执行查询校核");
		String cueUserid=SysUtil.getCacheCurrentUser().getId();
		String bsType=this.getPageElement("bsType").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
			sqlbf.append("/*"+Math.random()+"*/select vel001,(Select ifnull(A0101, '') from a01  Where A0000 = Vel002 limit 0,1) Vel002_name,(Select ifnull(A0184, '') from a01  Where A0000 = Vel002 limit 0,1) vel002_sfz,(Select ifnull(A0192A, '') from a01  Where A0000 = Vel002 limit 0,1) vel002_zw,vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a where 1=1 and vel003='1' and vel010='"+cueUserid+"'");
				
			
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
			sqlbf.append("/*"+Math.random()+"*/select vel001,(Select Nvl(A0101, '') from a01  Where A0000 = Vel002 And Rownum = 1) Vel002_name,(Select Nvl(A0184, '') from a01  Where A0000 = Vel002 And Rownum = 1) vel002_sfz,(Select Nvl(A0192A, '') from a01  Where A0000 = Vel002 And Rownum = 1) vel002_zw,vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a where 1=1 and vel003='1' and  vel010='"+cueUserid+"'");
					
		}
			if(!StringUtil.isEmpty(bsType)){
				sqlbf.append(" and vel005='"+bsType+"'");
			}else{
				sqlbf.append(" and (vel005='7' or vel005='6')");
			}
			
			String sort= request.getParameter("sort");//要排序的列名--无需定义，ext自动后传
			String dir= request.getParameter("dir");//要排序的方式--无需定义，ext自动后传
			if(StringUtil.isEmpty(sort)||StringUtil.isEmpty(dir)){
				sqlbf.append(" order by vel002_sfz ");
			}else{
				sqlbf.append(" order by "+sort+" "+dir+"");
			}
		
		//sqlbf.append(" order by vel002_name");
		this.request.getSession().setAttribute("v3sql", sqlbf.toString());
		CommonQueryBS.systemOut("xx------->"+sqlbf);
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("selectall.onclick")
	public int selectall() throws RadowException{
		this.getPageElement("bsType").setValue("");
		System.out.println("开始执行检验身份证");
		this.getPageElement("bsType").setValue("");
	    this.setNextEventName("IDerrorDetailGrid.dogridquery");
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
	 * 保存列表
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("savelist.onclick")
	@NoRequiredValidate
	@Transaction
	public int doSaveList() throws RadowException, UnsupportedEncodingException, SerialException, AppException, SQLException{
		PageElement pe = this.getPageElement("IDerrorDetailGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		if(list==null||list.size()<1){
			this.setMainMessage("无可保存人员");
			return EventRtnType.FAILD;
		}
		String sql = (String) this.request.getSession().getAttribute("v3sql");
		int from=sql.indexOf("From Verify_Error_List a");
		sql=sql.replace(sql.substring(0, from), "select vel002 a0000 ");
		int order =sql.indexOf("order");
		sql=sql.replace(sql.substring(order,sql.length()),"");
		String saveName = "身份证校核结果列表";
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		CustomQueryBS.saveList(saveName, "", "", loginName,sql,null);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 双击错误详细信息，跳转到任免表
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("IDerrorDetailGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowIDerrorDetailGrid() throws RadowException, AppException{
		Grid grid = (Grid)this.getPageElement("IDerrorDetailGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String a0000=map.get("vel002").toString();
		if(StringUtil.isEmpty(a0000)){
			throw new AppException("无法获取错误主体的类型！");
		}
		this.request.getSession().setAttribute("personIdSet", null);
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
				+ "{a0000:'"+a0000+"',gridName:'IDerrorDetailGrid'},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 将查看全部中的方法体抽出创建新的方法，以供OrgDatVer
	 * @throws RadowException 
	 */
	public void orgDateVerifySelectall() throws RadowException {
		
		this.setNextEventName("IDerrorDetailGrid.dogridquery");
		
	}
	
}
