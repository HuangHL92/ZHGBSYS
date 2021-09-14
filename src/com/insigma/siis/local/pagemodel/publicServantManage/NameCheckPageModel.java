package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * 年度考核情况新增修改页面
 * @author Administrator
 *
 */
public class NameCheckPageModel extends PageModel {	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		this.getExecuteSG().addExecuteCode("radow.doEvent('persongrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//刷新列表
	@PageEvent("persongrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, UnsupportedEncodingException{
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String namecheck = URLDecoder.decode(URLDecoder.decode(this.getPageElement("namecheck").getValue(), "utf8"), "utf8");
		String sql5 = "select t.a0000,t.a0101,t.a0104,t.a0192a,t.a0107 from a01 t where  a0101='"+namecheck+"' and status!='4' and a0000!='"+a0000+"' ";
		CommonQueryBS.systemOut(sql5);
		this.pageQuery(sql5, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("loadSelectedPerson")
	public int loadSelectedPerson(String id) throws RadowException, AppException{
		this.request.getSession().setAttribute("a0000",id);
		HBSession sess = HBUtil.getHBSession();	
		A01	a01 = (A01)sess.get(A01.class, id);
	
		//姓名 性别 年龄
		String a0101 = a01.getA0101()==null?"":a01.getA0101();//姓名
		String a0184 = a01.getA0184();//身份证号
		String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
		String age = "";
		if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
			age = IdCardManageUtil.getAge(a0184)+"";
		}
		String title = a0101 + "，" + sex + "，" + age+"岁";
		this.getExecuteSG().addExecuteCode("window.realParent.realParent.tabs.getItem(realParent.thisTab.initialConfig.tabid).setTitle('"+title+"');" +
				"realParent.thisTab.initialConfig.personid='"+id+"';");
			
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('doInit');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
