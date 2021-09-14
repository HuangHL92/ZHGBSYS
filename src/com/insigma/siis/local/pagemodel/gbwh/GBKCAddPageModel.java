package com.insigma.siis.local.pagemodel.gbwh;

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
import com.insigma.siis.local.business.entity.Gbkc;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.entity.YearCheck;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GBKCAddPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
     
	@PageEvent("initx")
	@NoRequiredValidate
	public int initx() throws Exception{
		String a0000s = this.getPageElement("subWinIdBussessId").getValue();				
		String[] str = a0000s.split("@@@");
		String Id = null;
		String a0000 = null;
		if(str.length==2){
			Id = str[0];
			a0000 = str[1];
			 this.getPageElement("id").setValue(Id);
			 this.getPageElement("a0000").setValue(a0000);
		}else{
			a0000 = a0000s;
			this.getPageElement("a0000").setValue(a0000);
		}
		this.getPageElement("xfMainOid").setValue(UUID.randomUUID().toString());		
        if(Id==null||"".equals(Id)){
			
		}else{
			HBSession sess = HBUtil.getHBSession();
			
			Gbkc gbkc=(Gbkc)sess.get(Gbkc.class, Id);
//			YearCheck yearcheck = (YearCheck)sess.get(YearCheck.class, Id);
			this.getPageElement("xfMainOid").setValue(gbkc.getCheckfile());
			PMPropertyCopyUtil.copyObjValueToElement(gbkc, this);  //将值赋值到页面
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 关闭
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
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		Gbkc gbkc=new Gbkc();
//		YearCheck yearcheck =  new YearCheck();
		String orifileid = this.getPageElement("orifileid").getValue();
		String id = this.getPageElement("id").getValue();
        if(id==null||"".equals(id)){
        	this.copyElementsValueToObj(gbkc, this);
        	gbkc.setId(UUID.randomUUID().toString());     	
		}else{
			gbkc = (Gbkc)sess.get(Gbkc.class, id);
			this.copyElementsValueToObj(gbkc, this);
			
		}
      //添加来文原件
		if(!orifileid.equals("1")){
			gbkc.setCheckfile(orifileid);
		}
		sess.saveOrUpdate(gbkc);		
		sess.flush();
		//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('grid1.dogridquery')");
		//this.setMainMessage("保存成功！");
		
		this.getExecuteSG().addExecuteCode("scfj()");
		this.getExecuteSG().addExecuteCode("saveCallBack('保存成功');");
		//reloadCustomQuery();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*public void reloadCustomQuery() {


		this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('grid1').getStore().reload()");// 刷新
		//this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('grid1.dogridquery')");

		//this.getExecuteSG().addExecuteCode("realParent.Ext.getCmp('persongrid').getStore().reload()");// 刷新人员列表


		
	}*/
}
