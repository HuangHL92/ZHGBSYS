package com.insigma.siis.local.pagemodel.publicServantManage;

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
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;


/**
 * 年度考核情况新增修改页面
 * @author Administrator
 *
 */
public class BZZXKHAddPagePageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String b0111 = this.getPageElement("subWinIdBussessId").getValue();
//		this.getExecuteSG().addExecuteCode("alert('"+b0111+"')");
		if(b0111==null||"".equals(b0111)){//
			this.setMainMessage("请先保存机构基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("BZZXKHGrid.dogridquery");//专项考核情况列表	
		try {
			HBSession sess = HBUtil.getHBSession();
			BZZXKH bzzxkh = (BZZXKH)sess.get(BZZXKH.class, b0111);
			
			String sqlCount = "select count(1) from BZZXKH where b0111 = '"+b0111+"'";
			Object o1 = sess.createSQLQuery(sqlCount).uniqueResult();
			Integer n = Integer.parseInt(o1.toString());
			
			listAssociation();
		}catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 专项考核情况列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("BZZXKHGrid.dogridquery")
	@NoRequiredValidate
	public int BZZXKHGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String b0111 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from BZZXKH where b0111='"+b0111+"' order by ZXKHSJ desc";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 专项考核情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("BZZXKHAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String b0111 = this.getPageElement("subWinIdBussessId").getValue();
		if(b0111==null||"".equals(b0111)){//
			this.setMainMessage("请先保存机构基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		BZZXKH bzzxkh=new BZZXKH();
//		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(bzzxkh, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 专项考核情况的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("BZZXKHGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int BZZXKHGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("BZZXKHGrid").getCueRowIndex();
		String bz00 = this.getPageElement("BZZXKHGrid").getValue("bz00",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			BZZXKH bzzxkh=(BZZXKH) sess.get(BZZXKH.class, bz00);
//			A15 a15 = (A15)sess.get(A15.class, a1500);
			PMPropertyCopyUtil.copyObjValueToElement(bzzxkh, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	/**
	 * 考核情况描述
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("listAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int listAssociation()throws RadowException, AppException{

		String b0111 = this.getPageElement("subWinIdBussessId").getValue();

		@SuppressWarnings("unchecked")
		List<String> list1 = HBUtil.getHBSession().createSQLQuery("select ZXKHSJ from BZZXKH where b0111='"+b0111+"' order by ZXKHSJ asc").list();
		@SuppressWarnings("unchecked")
		List<String> list2 = HBUtil.getHBSession().createSQLQuery("select ZXKHM from BZZXKH where b0111='"+b0111+"' order by ZXKHSJ asc").list();
		@SuppressWarnings("unchecked")
		List<String> list3 = HBUtil.getHBSession().createSQLQuery("select ZXKHJG from BZZXKH where b0111='"+b0111+"' order by ZXKHSJ asc").list();
		//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();

		StringBuffer desc = new StringBuffer("");
		for(int i=0;i<list1.size();i++) {
			String ZXKHSJ=list1.get(i);
			String ZXKHM=list2.get(i);
			String ZXKHJG=list3.get(i);
			String JG="";
			if("1".equals(ZXKHJG)) {
				JG="优秀";
			}else if("2".equals(ZXKHJG)) {
				JG="良好";
			}else if("3".equals(ZXKHJG)) {
				JG="一般";
			}else if("4".equals(ZXKHJG)) {
				JG="较差";
			}
			desc.append(ZXKHSJ.substring(0,4)+"."+ZXKHSJ.substring(4,6)+ZXKHM+JG+";");	
		}
		
		
		if(desc.length()>0){
			desc.replace(desc.length()-1, desc.length(), "。");
		}
		this.getPageElement("description").setValue(desc.toString());
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zxkh').value='"+desc.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	
	
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String bz00)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			BZZXKH bzzxkh=(BZZXKH)sess.get(BZZXKH.class, bz00);
//			A15 a15 = (A15)sess.get(A15.class, a1500);
//			A01 a01= (A01)sess.get(A01.class, a15.getA0000());
//			String a1527 = a15.getA1527();
			
			//applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			//记录了删除哪条
//			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(a15, new A15()));
			
			sess.delete(bzzxkh);
			this.getExecuteSG().addExecuteCode("radow.doEvent('BZZXKHGrid.dogridquery')");
			this.getExecuteSG().addExecuteCode("radow.doEvent('listAssociation.onclick')");
			bzzxkh = new BZZXKH();
			PMPropertyCopyUtil.copyObjValueToElement(bzzxkh, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("删除成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
//		String a1521s = this.getPageElement("a1521").getValue();
		String zxkhsj = this.getPageElement("zxkhsj").getValue();
//		String [] a1521sNum = a1521s.split(",");
		BZZXKH bzzxkh= new BZZXKH();
		this.copyElementsValueToObj(bzzxkh, this);
		//String a0000 = this.getRadow_parent_data();
		String b0111 = this.getPageElement("subWinIdBussessId").getValue();
		if(b0111==null||"".equals(b0111)){
			this.setMainMessage("请先保存结构基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		bzzxkh.setB0111(b0111);
		String bz00 = this.getPageElement("bz00").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();	
			@SuppressWarnings("unchecked")
			List<String> list1 = HBUtil.getHBSession().createSQLQuery("select BZ00 from BZZXKH where ZXKHM='"+bzzxkh.getZxkhm()+"' and ZXKHSJ='"+bzzxkh.getZxkhsj()+"'").list();	
			if(bz00!=null &&  !"".equals(bz00)){
				sess.update(bzzxkh);
			}else {
				if(list1.size()>0) {
					sess.update(bzzxkh);
				}else {
					sess.save(bzzxkh);
				}
			}		
			sess.flush();
			listAssociation();
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
			
		this.getExecuteSG().addExecuteCode("radow.doEvent('BZZXKHGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
