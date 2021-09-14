package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;

public class PersonAddTabPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('人员新增窗口');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('人员新增窗口');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//更改窗口名称updateWin.setTitle(title);
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//姓名 性别 年龄
			String a0101 = a01.getA0101();//姓名
			String a0184 = a01.getA0184();//身份证号
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}
			String title = a0101 + "，" + sex + "，" + age+"岁";
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('"+title+"');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('"+title+"');");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getActiveTab().setTitle('"+title+"');");
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * 新增保存时设置人员内码。
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("tabClick")
	public int tabClick(String id) throws RadowException {
		//Map map = this.getRequestParamer();
		//String a0000 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		String a0000 = id;
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('人员新增窗口');");
			//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('人员新增窗口');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//更改窗口名称updateWin.setTitle(title);
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//姓名 性别 年龄
			String a0101 = a01.getA0101();//姓名
			String a0184 = a01.getA0184();//身份证号
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}
			String title = a0101 + "，" + sex + "，" + age+"岁";
			//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('"+title+"');");
			//this.getExecuteSG().addExecuteCode("var newperson = window.parent.Ext.getCmp('"+a0000+"');" +
			//		"if(!newperson){newperson=window.parent.Ext.getCmp('add');}newperson.setTitle('"+title+"');");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getActiveTab().setTitle('"+title+"');");
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
