package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang.StringUtils;

import com.fr.report.core.A.e;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.AttrLrzw;
import com.insigma.siis.local.business.entity.PJManagement;
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 专业类型
 * 
 * @author zhubo
 *
 */
public class A0196TagsAddPagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
		if (extratags != null) {
			this.getPageElement("a0196z").setValue(extratags.getA0196z());
		//	this.getPageElement("a0196c").setValue(extratags.getA0196c());
			String a0196s = extratags.getA1401d();
			//if (a0196s != null && !"".equals(a0196s)) {
				List<AttrLrzw> list = sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").list();
				AttrLrzw attribute = new AttrLrzw();
				if(list.size()!=0){
					attribute = list.get(0);
				}else{
					attribute.setA0000(a0000);
					sess.saveOrUpdate(attribute);
					sess.flush();
				}
				//this.getPageElement("attr2101").getValue(null);
				PMPropertyCopyUtil.copyObjValueToElement(attribute, this);  //将值赋值到页面
				this.getPageElement("a0196s").setValue(a0196s);
			//}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//将Clob类型转成String
	  public String oracleClobToString(Clob clob){
	      try {
	        Reader characterStream = clob.getCharacterStream();
	        char[] c=new char[(int)clob.length()];
	        characterStream.read(c);
	        return new String(c);
	      } catch (Exception e) {
	          //e.printStackTrace();
	           
	      }
	      return null;
	  }
	@SuppressWarnings("unchecked")
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0196zInfo() throws RadowException, AppException, SerialException, SQLException {
		String a0000 = this.getPageElement("a0000").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0196z = this.getPageElement("a0196z").getValue();
	//	String a0196c = this.getPageElement("a0196c").getValue();//专业类型备注
		String a0196s = this.getPageElement("a0196s").getValue();
		String a98 = this.getPageElement("a98").getValue();
//		String a99 = this.getPageElement("a99").getValue();
		String a1401b = this.getPageElement("a1401b").getValue();
		String a1401c = this.getPageElement("a1401c").getValue();
		String a1401d = this.getPageElement("a1401d").getValue();
		String a1401f = this.getPageElement("a1401f").getValue();
		String a1401p = this.getPageElement("a1401p").getValue();
		String a1401g = this.getPageElement("a1401g").getValue();
		String a1401h = this.getPageElement("a1401h").getValue();
		String a1401k = this.getPageElement("a1401k").getValue();
		String a99z1304 = this.getPageElement("a99z").getValue();
		String a1401l = this.getPageElement("a1401l").getValue();
		String a1401m = this.getPageElement("a1401m").getValue();
		String a1401n = this.getPageElement("a1401n").getValue();
		
	
		
		
		try {
			HBSession sess = HBUtil.getHBSession();
			ExtraTags extratags = (ExtraTags) sess.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
			}
			extratags.setA0196z(a0196z);
		//	extratags.setA0196c(a0196c);
			extratags.setA1401d(a0196s);
			extratags.setA1401a(a98);
			//extratags.setA1401h(a99);
		
			
			
			
			AttrLrzw attribute = (AttrLrzw) sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").uniqueResult();
			
			A01 a01 = (A01) sess.get(A01.class, a0000);
			boolean isAdd = true;
			AttrLrzw attribute_old = null;
			if(a01==null){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(attribute==null){//增加日志
				attribute = new AttrLrzw();
				attribute.setA0000(a0000);
			}else{//修改日志
				isAdd = false;
				attribute_old = attribute.clone();
			}
			this.copyElementsValueToObj(attribute, this);
						
			attribute.setA0000(a0000);
			sess.saveOrUpdate(attribute);
			
			
			

			// 人员基本信息界面
//			this.getExecuteSG().addExecuteCode(
//					"window.realParent.document.getElementById('a0196z').value='" + extratags.getA0196z() + "'");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('A0196zcode').value='"
//					+ extratags.getA0196zcode() + "'");
			
			
			/**
			 * 评价相关
			 */
			PJManagement pjManagement = new PJManagement();
			pjManagement.setId(a0000);
			pjManagement.setA1401b(a1401b);
			pjManagement.setA1401c(a1401c);
			pjManagement.setA1401d(a1401d);
			pjManagement.setA1401f(a1401f);
			pjManagement.setA1401p(a1401p);
			pjManagement.setA1401g(a1401g);
			pjManagement.setA1401h(a1401h);
			pjManagement.setA1401k(a1401k);
			pjManagement.setA1401l(a1401l);
			pjManagement.setA1401m(a1401m);
			pjManagement.setA1401n(a1401n);
			/**
			 * 主要不足
			 */
			A99Z1 a99z1 = new A99Z1();
			a99z1.setA0000(a0000);
			a99z1.setA99z1304(a99z1304);
			if (StringUtils.isEmpty(a0000)) {
				a0000 = UUID.randomUUID().toString();
				HBUtil.executeUpdate(
						"insert into a99Z1(a0000,a99z1304) values(?,?)",
						new Object[] {a0000,a99z1304 });
			} else {
				HBUtil.executeUpdate("update a99Z1 set a99z1304=? where a0000=?", new Object[] { a99z1304, a0000 });
			}
			
			//String []arrStrings= {a1401b,a1401c,a1401d,a1401f,a1401g};
			//Clob clob = new javax.sql.rowset.serial.SerialClob(pjManagement.toString().toCharArray());
			
			//sess.saveOrUpdate(a99z1);
			sess.saveOrUpdate(pjManagement);
			sess.saveOrUpdate(extratags);
			sess.flush();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}

