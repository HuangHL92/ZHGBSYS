package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;  
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;   
import java.util.HashMap;
import java.util.List;    
import java.util.Map;    
      
import org.hsqldb.lib.StringUtil;
     
               
                              

import com.fr.report.core.A.t;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01CheckTime;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
    
public class OrgPersonImgVerifyPageModel extends PageModel{
	
	public static int syDayCount = 0;
	public static int birthDaycount = 0;
	public static String params;
	public static int queryType = 0;//0未检测  1检测结果中查询  2开始检测  3点击树查询
	public static String status = "";
	public static String querysql = "";
	public static String checktime = "";
	
	public OrgPersonImgVerifyPageModel(){
	}
	
	  
	@Override   
	public int doInit() throws RadowException {
		syDayCount = 0;
		birthDaycount = 0;
		queryType = 0;//0未检测  1检测结果中查询  2开始检测  3点击树查询
		status = "";
		querysql = "";
		checktime = "";
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 刷新
	 */         
	@PageEvent("reload.onclick")
	public int reload() throws RadowException {
		syDayCount = 0;
		birthDaycount = 0;
		queryType = 0;//0未检测  1检测结果中查询  2开始检测  3点击树查询
		status = "";
		querysql = "";
		checktime = "";
	    this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 开始检测
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btn1.onclick")
	public int btn1() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择要校验的机构!");
		}
		if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("您无此权限!");
		}
		queryType = 2;
		this.setNextEventName("persongrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}  
	/**
	 * 查询
	 * @return
	 */          
	@PageEvent("doquery.onclick")
	public int doquery() throws RadowException{
		if (queryType == 0) {
			this.setMainMessage("请先进行检测或点击机构树查询！");
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			queryType = 1;
			this.setNextEventName("persongrid2.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	//点击树查询事件
		@PageEvent("querybyid")
		@NoRequiredValidate
		public int query(String id) throws RadowException, AppException {
			this.getPageElement("checkedgroupid").setValue(id);
			Object name=HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+id+"'").uniqueResult();
			this.getPageElement("checkedname").setValue(name+"");
			this.getPageElement("checktime").setValue("尚未检测");
			try {
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select checktime from b01_checktime where b0111='"+id+"'").executeQuery();
				while(res.next()){
					this.getPageElement("checktime").setValue(res.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			queryType = 3;
			this.setNextEventName("persongrid2.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}	
	
	@PageEvent("persongrid2.dogridquery")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate         
	public int dogridQuery2(int start,int limit) throws RadowException, AppException{
		if (queryType==2) {
			String nowdata = DateUtil.getcurdate();
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = format.format(date);
			String b0111OrimpID = this.getPageElement("checkedgroupid").getValue();
			String isContain = this.getPageElement("isContain").getValue();
			HBSession sess = HBUtil.getHBSession();
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String userid = user.getId();
			if ("1".equals(isContain)) {
				//照片读取
				try {
					//包含下级 添加修改机构检测时间
					ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select b.b0111 from b01 b where b.b0111 like '"+b0111OrimpID+"%'").executeQuery();
					int x = 0;
					Map<Integer, String> newmap = new HashMap<Integer, String>();
					while (res.next()) {
						String b0111 = res.getString(1);
						newmap.put(x, b0111);
						x++;
					}
					for (int y = 0; y < newmap.size(); y++) {
						String b0111 = newmap.get(y);
						if(DBUtil.getDBType() == DBType.MYSQL){
							HBUtil.executeUpdate("replace into b01_checktime(b0111,checktime) values('"+b0111+"','"+dateString+"')");
						}else{
							sess.createSQLQuery("MERGE INTO B01_CHECKTIME b USING b01 a ON (b.b0111=a.b0111) WHEN MATCHED THEN UPDATE SET b.checktime='"+dateString+"' WHERE a.b0111='"+b0111+"' WHEN NOT MATCHED THEN INSERT VALUES('"+b0111+"','"+dateString+"') WHERE a.b0111='"+b0111+"'").executeUpdate();
						}
					}
					//ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("select a.a0000 from a57 a,a02 b,competence_userdept t where a.a0000=b.a0000 and b.a0201b like '"+b0111OrimpID+"%' and b.a0201b=t.b0111 and t.type='1' and t.userid='"+userid+"'").executeQuery();
					ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("SELECT a01.a0000 FROM A01 a01 WHERE 1 = 1 AND a01. STATUS != '4' AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN ( SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"') AND a02.a0201b LIKE '"+b0111OrimpID+"%')").executeQuery();
					int i = 0;
					List<Map<Integer, String> > list = new ArrayList();
					while (rs.next()) {
						String a0000 = rs.getString(1);
						Map<Integer, String> map = new HashMap();
						map.put(i, a0000);
						list.add(map);
						i++;
					}
					
					for (int j = 0; j < list.size(); j++) {
						Map<Integer, String> map1 = new HashMap();
						map1 = list.get(j);
						String a0000 = map1.get(j);
						A57 a57 = (A57)sess.get(A57.class, a0000);
						if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
							
							String photourl = a57.getPhotopath();
							File file = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
							 if (file.exists()) {//判断文件目录的存在
								 HBUtil.executeUpdate("update a57 set a57.picstatus='1' where a57.a0000='"+a0000+"'");    
							 }else{
								 HBUtil.executeUpdate("update a57 set a57.picstatus='0' where a57.a0000='"+a0000+"'");
							 }
						}
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				String sql = "SELECT a.a0000,a.a0101,a.a0104,GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a"
						+ "  WHERE  not EXISTS (select a57.a0000 from a57 where  a57.a0000 = a.a0000) AND a. STATUS != '4'"
						+ "  AND  EXISTS (SELECT a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111)"
						+ " AND a02.a0201b LIKE '"+b0111OrimpID+"%' and a.a0000=a02.a0000)"
						+ " union"
						+ " SELECT a.a0000, a.a0101, a.a0104, GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a , a57 b "
						+ " WHERE a.a0000 = b.a0000 AND a. STATUS != '4' AND b.picstatus = '0'"
						+ " and a.a0000 in ( SELECT  a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111) AND a02.a0201b LIKE '"+b0111OrimpID+"%') ";
				querysql = sql;
				//System.out.println(sql);
				CommonQueryBS.systemOut("检测(包含下级)------->"+sql);
				this.request.getSession().setAttribute("imgSelect",sql);
				this.pageQuery(sql, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
			}else {
			//照片读取
			try {
				if(DBUtil.getDBType() == DBType.MYSQL){
					HBUtil.executeUpdate("replace into b01_checktime(b0111,checktime) values('"+b0111OrimpID+"','"+dateString+"')");
				}else{
					sess.createSQLQuery("MERGE INTO B01_CHECKTIME b USING b01 a ON (b.b0111=a.b0111) WHEN MATCHED THEN UPDATE SET b.checktime='"+dateString+"' WHERE a.b0111='"+b0111OrimpID+"' WHEN NOT MATCHED THEN INSERT VALUES('"+b0111OrimpID+"','"+dateString+"') WHERE a.b0111='"+b0111OrimpID+"'").executeUpdate();
				}
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("SELECT a01.a0000 FROM A01 a01 WHERE 1 = 1 AND a01. STATUS != '4' AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN ( SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"') AND a02.a0201b = '"+b0111OrimpID+"')").executeQuery();
				int i = 0;
				List<Map<Integer, String> > list = new ArrayList();
				while (rs.next()) {
					String a0000 = rs.getString(1);
					Map<Integer, String> map = new HashMap();
					map.put(i, a0000);
					list.add(map);
					i++;
				}
				
				for (int j = 0; j < list.size(); j++) {
					Map<Integer, String> map1 = new HashMap();
					map1 = list.get(j);
					String a0000 = map1.get(j);
					A57 a57 = (A57)sess.get(A57.class, a0000);
					if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
						String photourl = a57.getPhotopath();
						File file = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
						 if (file.exists()) {//判断文件目录的存在
							 HBUtil.executeUpdate("update a57 set a57.picstatus='1' where a57.a0000='"+a0000+"'");    
						 }else{
							 HBUtil.executeUpdate("update a57 set a57.picstatus='0' where a57.a0000='"+a0000+"'");
						 }
					} 
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sql = "SELECT a.a0000,a.a0101,a.a0104,GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a"
					+ "  WHERE  not EXISTS (select a57.a0000 from a57 where  a57.a0000 = a.a0000) AND a. STATUS != '4'"
					+ "  AND  EXISTS (SELECT a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111)"
					+ " AND a02.a0201b = '"+b0111OrimpID+"' and a.a0000=a02.a0000)"
					+ " union"
					+ " SELECT a.a0000, a.a0101, a.a0104, GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a , a57 b "
					+ " WHERE a.a0000 = b.a0000 AND a. STATUS != '4' AND b.picstatus = '0'"
					+ " and a.a0000 in ( SELECT  a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111) AND a02.a0201b = '"+b0111OrimpID+"') ";
			querysql = sql;
			CommonQueryBS.systemOut("检测------->"+sql);
			this.request.getSession().setAttribute("imgSelect",sql);
			this.pageQuery(sql, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
			}	
		}else if(queryType==1){
			String sql = querysql;
			String nowdata = DateUtil.getcurdate();
			String name = this.getPageElement("a0101A").getValue();
			if (!"".equals(name)) {
				sql = "select a.a0000,a.a0101,a.a0104,GET_BIRTHDAY(a.a0107,'"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 from ("+sql+") a where a.a0101 like '%"+name+"%'";
			}
			this.request.getSession().setAttribute("imgSelect",sql);
			this.pageQuery(sql, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
		}else if (queryType == 3 ) {
			String nowdata = DateUtil.getcurdate();
			String b0111OrimpID = this.getPageElement("checkedgroupid").getValue();
			String isContain = this.getPageElement("isContain").getValue();
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String userid = user.getId();
			String a0201b="";
			if("1".equals(isContain)){
				a0201b=" like '"+b0111OrimpID+"%'";
			}else{
				a0201b="  ='"+b0111OrimpID+"'";
			}
			String sql = "SELECT a.a0000,a.a0101,a.a0104,GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a"
					+ "  WHERE  not EXISTS (select a57.a0000 from a57 where  a57.a0000 = a.a0000) AND a. STATUS != '4'"
					+ "  AND  EXISTS (SELECT a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111)"
					+ " AND a02.a0201b "+a0201b+" and a.a0000=a02.a0000)"
					+ " union"
					+ " SELECT a.a0000, a.a0101, a.a0104, GET_BIRTHDAY (a.a0107, '"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 FROM a01 a , a57 b "
					+ " WHERE a.a0000 = b.a0000 AND a. STATUS != '4' AND b.picstatus = '0'"
					+ " and a.a0000 in ( SELECT  a02.a0000 FROM a02 WHERE exists (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"' and a02.A0201B = cu.b0111) AND a02.a0201b "+a0201b+") ";
			/*sql="SELECT a.a0000,a.a0101,a.a0104,GET_BIRTHDAY (a.a0107, '20171113') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165 "
					+ "FROM A01 a,A57 b WHERE a.a0000 = b.a0000 AND b.picstatus = '0' AND a.a0000 IN "
					+ "(SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM competence_userdept cu WHERE cu.userid = '"+userid+"') AND a0281 = 'true' "+a0201b+" )"
					+ " GROUP BY a.a0000,a.a0101,a.a0104,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165";
		*//*	if ("1".equals(isContain)) {
				sql = "select a.a0000,a.a0101,a.a0104,GET_BIRTHDAY(a.a0107,'"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165,a.status,a.orgid"
						+ " from A01 a,A57 b,A02 c,B01 d where a.a0000=b.a0000 and a.a0000=c.a0000"
						+ " and c.a0201b=d.b0111 and b.picstatus='0' and d.b0111 like '"+b0111OrimpID+"%'";
			}else {
				sql = "select a.a0000,a.a0101,a.a0104,GET_BIRTHDAY(a.a0107,'"+nowdata+"') age,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165,a.status,a.orgid"
						+ " from A01 a,A57 b,A02 c,B01 d where a.a0000=b.a0000 and a.a0000=c.a0000"
						+ " and c.a0201b=d.b0111 and b.picstatus='0' and d.b0111='"+b0111OrimpID+"'";
			}
			String groupbysql = " group by a.a0000,a.a0101,a.a0104,a.a0117,a.a0141,a.a0107,a.a0193,a.a0192a,a.a0148,a.a0165,a.status,a.orgid ";
			 String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
		        //用户只能查询自己有权限的机构下的信
		        CurrentUser user1 = SysUtil.getCacheCurrentUser();
		        if(!user1.getLoginname().equals("admin")){
		        	String authoritySQL = "select t.b0111 from COMPETENCE_USERDEPT t where t.userid='"+SysManagerUtils.getUserId()+"'  ";//and (t.type='1' or t.type='0')
		        	//非离退历史人员 职务机构权限控制  
		        	sql=sql+" and (exists (select 1 from a02 where a02.a0000=a.a0000 and  a02.A0201B in ("+authoritySQL+") and a.status='1')   " +
		        	//离退历史人员机构权限控制
		        			"     or ( (a.status='3' or a.status='2') and a.orgid in("+authoritySQL+"))  )";//or isHistory=1
		        	//
		        	sql=sql+" and ( ( (a01.status='3' or a01.status='2') and a01.orgid in("+authoritySQL+")) or" +
						"(a01.status='1')  )";
		        }
		        sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";
		        sql += groupbysql;*/
				CommonQueryBS.systemOut("双击------->"+sql);
		        //System.out.println("---->"+sql);
			querysql = sql;
			this.request.getSession().setAttribute("imgSelect",sql);
			this.pageQuery(sql, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
		}
		return 0;
	}
	
	/**
	 * 修改人员信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("persongrid2.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick(String a0000) throws RadowException, AppException{  //打开窗口的实例
		//String conp = this.request.getContextPath();
		String id = this.getPageElement("persongrid2").getValue("a0000",this.getPageElement("persongrid2").getCueRowIndex()).toString();
		//this.getExecuteSG().addExecuteCode("addTab('','"+id+"','"+conp+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.request.getSession().setAttribute("personIdSet", null);
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+id+"','人员信息修改',1009,630,null,"
				+ "{a0000:'"+id+"',gridName:'persongrid2'},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		this.setRadow_parent_data(id);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	


}
