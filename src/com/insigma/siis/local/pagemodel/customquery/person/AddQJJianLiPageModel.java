package com.insigma.siis.local.pagemodel.customquery.person;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.siis.local.business.entity.DBWY;
import com.insigma.siis.local.pagemodel.publicServantManage.AddJianLiAddPageBS;


public class AddQJJianLiPageModel extends PageModel{
	private HBSession session = HBUtil.getHBSession(); //数据库会话对象
	
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		HBSession sess=HBUtil.getHBSession();
		String a1700=this.getPageElement("a1700").getValue();
		try {
			A17 a17 = (A17) sess.get(A17.class, a1700);
			if(a17!=null) {	
				this.copyObjValueToElement(a17,this);
			}else{
				return EventRtnType.NORMAL_SUCCESS;
			}
			String a1705=a17.getA1705();
			if(a1705!=null && !"".equals(a1705)) {
				@SuppressWarnings("unchecked")
				List<String> a1705name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1705+"'  and code_type='JL02'").list();
				if(a1705name.size()>0 && a1705name.get(0)!=null) {
					this.getPageElement("a1705_combotree").setValue(a1705name.get(0));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("请求参数解析异常！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	 @PageEvent("save.onclick")
	 public int save() throws RadowException, AppException {
		 String a1700=this.getPageElement("a1700").getValue();
		 String a0000=this.getPageElement("a0000").getValue();
		 String a1700parent=this.getPageElement("a1700parent").getValue(); 
		 A17 a17;
		 HBSession sess=HBUtil.getHBSession();
		 try {
        	 a17 = (A17) sess.get(A17.class, a1700);
        	 if(a17==null) {
 				a17=new A17();
 	    		 a17.setBelongToA1700(a1700parent);
 			}
        	 String a1701= this.getPageElement("a1701").getValue();
     		 String a1702= this.getPageElement("a1702").getValue();
     		 String a1703= this.getPageElement("a1703").getValue();
     		 String a1704= this.getPageElement("a1704").getValue();
    		 String a1705= this.getPageElement("a1705").getValue();
    		 String a1706= this.getPageElement("a1706").getValue();
    		 String a1707= this.getPageElement("a1707").getValue();
    		 String a1708= this.getPageElement("a1708").getValue();
    		 a17.setA0000(a0000);
    		 a17.setA1700(a1700);
    		 a17.setA1701(a1701);
    		 a17.setA1702(a1702);
    		 a17.setA1703(a1703);
    		 a17.setA1704(a1704);
    		 a17.setA1705(a1705);
    		 a17.setA1706(a1706);
    		 a17.setA1707(a1707);
    		 a17.setA1708(a1708);
    		 a17.setA1709("3");
    		 session.saveOrUpdate(a17);
    		 session.flush();
			 this.setMainMessage("保存成功");
	         this.getExecuteSG().addExecuteCode("realParent.doQuery()");
	         this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('AddQJJianLi').close()");
		 }catch (Exception e) {
				this.setMainMessage("请求参数解析异常！");
				e.printStackTrace();
				return EventRtnType.NORMAL_SUCCESS;
		 }
         return EventRtnType.NORMAL_SUCCESS;
    }
	
		@PageEvent("changeA1704name")
		@NoRequiredValidate
		@Transaction
		public int changeA1704name()throws RadowException {
			HBSession session = HBUtil.getHBSession();
			String a1704=this.getPageElement("a1704").getValue();
			@SuppressWarnings("unchecked")
			List<String> a1704name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1704+"'  and code_type='JL02'").list();
			if(a1704name.size()>0 && a1704name.get(0)!=null) {
				this.getPageElement("a1704_combo").setValue(a1704name.get(0));
			}
			return EventRtnType.NORMAL_SUCCESS;
		}

}
