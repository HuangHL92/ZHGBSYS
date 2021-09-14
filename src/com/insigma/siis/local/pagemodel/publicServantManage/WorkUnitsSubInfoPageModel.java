package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * 工作单位及职务新增修改页面
 * @author Administrator
 *
 */
public class WorkUnitsSubInfoPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String a0200 = this.getPageElement("subWinIdBussessId").getValue();//this.getRadow_parent_data();
		if(a0200==null||"".equals(a0200)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//a01中工作单位及职务
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from A02 where a0200='"+a0200+"'",null);
			if(Info_Extends!=null&&Info_Extends.size()>0){//
				Map<String,String> entity = Info_Extends.get(0);
				for(String key : entity.keySet()){
					try {
						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
					} catch (Exception e) {
					}
				}
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("setParentA0194Value();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveWorkUnitsSubInfo()throws RadowException, AppException{
		String a0200 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0200==null||"".equals(a0200)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = CommSQL.getInfoSQL("'A02'");

			List<Object[]> list = sess.createSQLQuery(sql).list();
			Map<String,String> field = new LinkedHashMap<String, String>();
			if(list!=null&&list.size()>0){
				for(Object[] os : list){
					field.put(os[1].toString(), os[2].toString());
				}
			}
			sess.flush();
			if(field.size()>0){
				StringBuffer update_sql = new StringBuffer("update a02 set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						update_sql.append(key+"=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						if(DBType.ORACLE == DBUtil.getDBType()){
							HBUtil.executeUpdate("alter table A02 add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column A02."+key+" is '"+comment+"'");
							//HBUtil.executeUpdate("alter table Info_Extend_Temp add "+key+" varchar2(200)");
							//HBUtil.executeUpdate("comment on column Info_Extend_Temp."+key+" is '"+comment+"'");
						}else{
							HBUtil.executeUpdate("ALTER TABLE A02 add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							//HBUtil.executeUpdate("ALTER TABLE Info_Extend_Temp add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
						}
						
					}catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length()-1).append(" where a0200='"+a0200+"'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	
}

