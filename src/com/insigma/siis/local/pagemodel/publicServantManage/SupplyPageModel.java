package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

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
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


//补充信息集录入
public class SupplyPageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int save()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//获得页面a01的信息
		A01 a01New = new A01();
		this.copyElementsValueToObj(a01New, this);
		
		a01.setA0115a(a01New.getA0115a());			//成长地
		a01.setA0120(a01New.getA0120()); 			//级别
		a01.setA0122(a01New.getA0122()); 			//专业技术类公务员任职资格
		a01.setA2949(a01New.getA2949()); 			//公务员登记时间
		
		try {
			
			
			
			sess.saveOrUpdate(a01);
			this.getPageElement("a0000").setValue(a01.getA0000());
		
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//对页面进行数据加载
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException, JSONException, IOException {
		
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		//对页面赋值
		if(a0000!=null && !"".equals(a0000)){
			
			A01 a01 = (A01)sess.get(A01.class, a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);		//数据赋值到页面
			
			/*String sqlA99Z1 = "from A99Z1 where a0000='" + a0000 + "'";
			List listA99Z1 = sess.createQuery(sqlA99Z1).list();
			if (listA99Z1 != null && listA99Z1.size() > 0) {
				A99Z1 a99Z1 = (A99Z1) listA99Z1.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(a99Z1, this);		//数据赋值到页面
			}*/
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
    
}
