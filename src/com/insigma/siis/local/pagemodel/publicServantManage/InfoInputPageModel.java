package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class InfoInputPageModel extends PageModel {

	
	public static String sid = "";
	
	/**
	 * 私有属性 ids 父页传过来的数据
	 */
	public static int type = 0;
	private LogUtil applog = new LogUtil();

	public InfoInputPageModel() {

	}

	// 页面初始化
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		
		sid = this.request.getSession().getId();
		
		this.getExecuteSG().addExecuteCode("getTabletype();");
		HBSession sess = HBUtil.getHBSession();
		//String value = this.getRadow_parent_data();
		String value = this.getPageElement("subWinIdBussessId").getValue();
		String[] values = value.split("@");
		if (values.length > 1) {
			String sql = values[0];
			StringBuffer sb = new StringBuffer();
			sql = sql.replaceAll("\\$", "\\'");
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				for (int i = 0; i < allSelect.size(); i++) {
					//判断是否有删除权限。c.type：机构权限类型(0：浏览，1：维护)
					String a0000 = allSelect.get(i).toString();
					A01 a01 = (A01)sess.get(A01.class, a0000);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
		/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){
						type ="'zz'";//替换垃圾数据
					}
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
						continue;
					}
					if(elist2==null||elist2.size()==0){//维护权限
						if(elist!=null&&elist.size()>0){//有浏览权限
							continue;
						}else{
							//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
								continue;
							}else {
								sb.append("'").append(a0000).append("',");
							}
							
						}
					}else {
						sb.append("'").append(a0000).append("',");
					}
				}
				if (sb.length() == 0) {
					this.setMainMessage("所选人员不可操作！");
					return EventRtnType.FAILD;
				}
				String ids = sb.substring(0, sb.length() - 1);
				this.getPageElement("ids").setValue(ids);
			} else {
				this.setMainMessage("请先进行人员查询！");
				return EventRtnType.FAILD;
			}
		} else {
			String[] id_arr = value.split(",");
			String ids_temp = ""; 
			for(int i = 0; i < id_arr.length; i ++){
				ids_temp += "'" + id_arr[i] + "',";
			}
			String ids = ids_temp.substring(0,ids_temp.length()-1);
			this.getPageElement("ids").setValue(ids);
		}
		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 80; i++) {
			map.put("" + (year - i), year - i);
		}
		// String[] idArray = ids.split(",");
		
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}



	
	@PageEvent("peopleInfoGrid.dogridquery")
	@NoRequiredValidate
	public int queryById(int start,int limit)throws RadowException{
		
		String ids = this.getPageElement("ids").getValue();
		String sql = "select a01.a0000,a01.a0101,a01.a0192a from a01,A01SEARCHTEMP b where a01.a0000 in ("+ids+") and a01.a0000 = b.a0000 and sessionid = '"+sid+"' order by b.sort";
		System.out.println("----------------"+sql);
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
}
