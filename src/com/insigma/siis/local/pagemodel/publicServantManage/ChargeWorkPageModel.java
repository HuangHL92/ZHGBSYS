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

import com.fr.stable.core.UUID;
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
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A86;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.CommonQueryBS;


/**
 * 分工情况历史
 * @author manpj
 *
 */
public class ChargeWorkPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("document.getElementById('a0200').value='"+a0200+"';");
		if (a0200 == null || "".equals(a0200)) {
			this.setMainMessage("无工作单位及职位信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CommonQueryBS cq=new CommonQueryBS();
		try {
			HashMap<String, Object> a8600map = cq.getMapBySQL("select a8600 from A86 where A0200='"+a0200+"' and A8602='1'");
			String a8600="";
			if(a8600map!=null){
				a8600=a8600map.get("a8600")==null?"":a8600map.get("a8600").toString();
			}
			//A86 a86=new A86();
			//a86.setA8600(a8600);
			HBSession hbSession = HBUtil.getHBSession();
			A86 getA86 = (A86)hbSession.get(A86.class, a8600);
			if(getA86!=null){
				this.copyObjValueToElement(getA86, this);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 显示grid
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int trainingInforGridQuery(int start, int limit) throws RadowException {
		// String a0000 = this.getPageElement("a0000").getValue();
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A86 where a0200='" + a0200 + "'";
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 
	 * 点击新增按钮事件
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id) throws RadowException {
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		if (a0200 == null || "".equals(a0200)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('无工作单位及职位信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a8600=UUID.randomUUID().toString().replaceAll("-", "");
		A86 a86=new A86();
		a86.setA0200(a0200);
		a86.setA8600(a8600);
		boolean copyObjValueToElement = PMPropertyCopyUtil.copyObjValueToElement(a86, this);
		//this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	public int TrainingInfoAddBtnRowclick() throws RadowException{
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		int index=this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String a8600=this.getPageElement("TrainingInfoGrid").getValue("a8600", index).toString();
		String a8601=this.getPageElement("TrainingInfoGrid").getValue("a8601",index).toString();
		String a0299=this.getPageElement("TrainingInfoGrid").getValue("a0299",index).toString();
		A86 a86=new A86();
		a86.setA0200(a0200);
		a86.setA8600(a8600);
		a86.setA8601(a8601);
		a86.setA0299(a0299);
		this.copyObjValueToElement(a86, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveAll")
	@NoRequiredValidate
	@Transaction
	public int saveAll() throws RadowException {
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		
		A86 a86=new A86();
		a86.setA8602("1");
		
		HBSession hbSession = HBUtil.getHBSession();
		String a8600=this.getPageElement("a8600").getValue();
		this.copyElementsValueToObj(a86, this);
		CommonQueryBS cq=new CommonQueryBS();
		//HBSession hbSession = HBUtil.getHBSession();
		try {
			HashMap<String, Object> a8600map = cq.getMapBySQL("select a8600 from A86 where A0200='"+a0200+"' and a8600='"+a8600+"'");
			if(a8600map==null){
				String newA8600=UUID.randomUUID().toString().replaceAll("-", "");
				a86.setA8600(newA8600);
				hbSession.save(a86);
				hbSession.flush();
			}else{
				hbSession.update(a86);
				hbSession.flush();
			}
			HBUtil.executeUpdate("update A86 set A8602='0' where A0200='"+a0200+"' and A8600!='"+a86.getA8600()+"'");
			this.getPageElement("a8600").setValue(a86.getA8600());
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0229').value='"+a86.getA0299()+"';radow.doEvent('TrainingInfoGrid.dogridquery');window.$h.alert('系统提示','保存成功',null,'200');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("savecheck")
	public int savecheck(String a8600) throws RadowException{
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		HBSession hbSession = HBUtil.getHBSession();
		A86 a86=(A86)hbSession.get(A86.class, a8600);
		a86.setA8602("1");
		try {
			HBUtil.executeUpdate("update A86 set A8602='0' where A0200='"+a0200+"' and A8600!='"+a86.getA8600()+"'");
			this.getPageElement("a8600").setValue(a86.getA8600());
		} catch (AppException e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0229').value='"+a86.getA0299()+"';radow.doEvent('TrainingInfoGrid.dogridquery');window.$h.alert('系统提示','保存成功',null,'200');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("deleteRow")
	@Transaction
	public int deleteRow(String a8600) throws RadowException{
		String a0200=this.getPageElement("subWinIdBussessId").getValue();
		A86 a86=new A86();
		a86.setA0200(a0200);
		a86.setA8600(a8600);
		HBSession hbSession = HBUtil.getHBSession();
		hbSession.delete(a86);
		hbSession.flush();
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery');$h.alert('系统提示','删除成功',null,'200');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}

