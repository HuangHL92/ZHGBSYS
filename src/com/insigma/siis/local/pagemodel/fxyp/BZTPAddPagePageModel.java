package com.insigma.siis.local.pagemodel.fxyp;

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
import com.insigma.siis.local.business.entity.BZZXKH;
import com.insigma.siis.local.business.entity.QXSBZNDKH;
import com.insigma.siis.local.business.entity.QXSBZTP;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;



public class BZTPAddPagePageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化单位名和核定职数
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		String tp00=this.getPageElement("subWinIdBussessId").getValue();
		this.getPageElement("tp00").setValue(tp00);
		if(tp00!=null && !"".equals(tp00)) {
			HBSession sess = null;
			try {
				sess = HBUtil.getHBSession();
				if(tp00!=null && !"".equals(tp00)) {
					QXSBZTP qxsbztp = (QXSBZTP)sess.get(QXSBZTP.class, tp00);
					PMPropertyCopyUtil.copyObjValueToElement(qxsbztp, this);
				}
			}catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("查询失败！");
				return EventRtnType.FAILD;
			}
		}
//		String b0111=this.getPageElement("b0111").getValue();
//		this.getExecuteSG().addExecuteCode("alert('"+b0111+"')");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
//		String a1521s = this.getPageElement("a1521").getValue();
		String b0111=this.getPageElement("b0111").getValue();
		String tp00=this.getPageElement("subWinIdBussessId").getValue();
//		String [] a1521sNum = a1521s.split(",");
		QXSBZTP qxsbztp = new QXSBZTP();
		this.copyElementsValueToObj(qxsbztp, this);
		//String a0000 = this.getRadow_parent_data();
		if(b0111==null||"".equals(b0111)){
			this.setMainMessage("请先保存结构基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		qxsbztp.setB0111(b0111);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();	
			if(!"".equals(tp00) && tp00!=null) {
				qxsbztp.setTp00(tp00);
				sess.update(qxsbztp);
			}else {
				sess.save(qxsbztp);
			}
//			if(bz00!=null &&  !"".equals(bz00)){
//				sess.update(bzzxkh);
//			}else {
//				if(list1.size()>0) {
//					sess.update(bzzxkh);
//				}else {
//					sess.save(bzzxkh);
//				}
//			}		
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
			
		this.getExecuteSG().addExecuteCode("window.realParent.radow.doEvent('BZTPGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

}
