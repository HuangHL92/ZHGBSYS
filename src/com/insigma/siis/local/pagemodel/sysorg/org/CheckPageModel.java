package com.insigma.siis.local.pagemodel.sysorg.org;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.entity.YearCheck;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CheckPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
     
	@PageEvent("initx")
	@NoRequiredValidate
	public int initx() throws Exception{
		String groupid = this.getPageElement("subWinIdBussessId").getValue();				
		String[] str = groupid.split("@@@");
		String Id = null;
		String orgId = null;
		if(str.length==2){
			Id = str[0];
			orgId = str[1];
			 this.getPageElement("id").setValue(Id);
			 this.getPageElement("b0111").setValue(orgId);
		}else{
			orgId = groupid;
			this.getPageElement("b0111").setValue(orgId);
		}
		this.getPageElement("xfMainOid").setValue(UUID.randomUUID().toString());		
        if(Id==null||"".equals(Id)){
			
		}else{
			HBSession sess = HBUtil.getHBSession();
			
			YearCheck yearcheck = (YearCheck)sess.get(YearCheck.class, Id);
			this.getPageElement("xfMainOid").setValue(yearcheck.getCheckfile());
			PMPropertyCopyUtil.copyObjValueToElement(yearcheck, this);  //��ֵ��ֵ��ҳ��
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ر�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	/*@PageEvent("close")
	public int close() throws RadowException, Exception {
				
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		this.closeCueWindow("Check");
		return EventRtnType.NORMAL_SUCCESS;		
	}*/
	
	
	/**
	 * ����
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		YearCheck yearcheck =  new YearCheck();
		String orifileid = this.getPageElement("orifileid").getValue();
		String id = this.getPageElement("id").getValue();
        if(id==null||"".equals(id)){
        	this.copyElementsValueToObj(yearcheck, this);
        	yearcheck.setId(UUID.randomUUID().toString());     	
		}else{
			yearcheck = (YearCheck)sess.get(YearCheck.class, id);
			this.copyElementsValueToObj(yearcheck, this);
			
		}
      //�������ԭ��
		if(!orifileid.equals("1")){
			yearcheck.setCheckfile(orifileid);
		}
		sess.saveOrUpdate(yearcheck);		
		sess.flush();
		//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('grid1.dogridquery')");
		//this.setMainMessage("����ɹ���");
		
		this.getExecuteSG().addExecuteCode("scfj()");
		this.getExecuteSG().addExecuteCode("saveCallBack('����ɹ�');");
		//reloadCustomQuery();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*public void reloadCustomQuery() {


		this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('grid1').getStore().reload()");// ˢ��
		//this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('grid1.dogridquery')");

		//this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('persongrid').getStore().reload()");// ˢ����Ա�б�


		
	}*/
}
