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
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.Association;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class AssociationAddPagePageModel extends PageModel{
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("document.getElementById('a0000').value='"+a0000+"';");
		if (a0000 == null || "".equals(a0000)) {//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//人员已审核则锁定
		if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}
		
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from Association where a0000='" + a0000 +"'  ";
			List list = sess.createQuery(sql).list();
			Association association = null;
			if(list != null && list.size()>0){
				association = (Association) list.get(0);
				association.setA0000(a0000);
			}

			if (association != null) {
				PMPropertyCopyUtil.copyObjValueToElement(association, this);
				// 旧值 判断是否修改
				String json = objectToJson(association);
				// this.getExecuteSG().addExecuteCode("parent.A61value="+json+";");
			}
			updateA01(a0000);

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainingInfoGrid.dogridquery");// 信息列表
		this.getExecuteSG().addExecuteCode("setClosingDateDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//数据保存
	@PageEvent("saveAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo() throws RadowException, AppException {
		Association association =new Association();
		this.copyElementsValueToObj(association, this);
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (association.getStname() == null || "".equals(association.getStname())) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','社团名称不能为空！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		association.setA0000(a0000);
		String st00=association.getSt00();
		String stname=association.getStname();
		String stjob=association.getStjob();
		String stnature=association.getStnature();
		String status=association.getStatus();
		String startdate=association.getStartdate();
		String approvaldate=association.getApprovaldate();
		String closingdate=association.getClosingdate();
		String sessionsnum=association.getSessionsnum();
		String salary=association.getSalary();
		
//		String a0500 = a05.getA0500();
//		String a0504 = a05.getA0504();// 
//		String a0517 = a05.getA0517();//
//		String a0524 = a05.getA0524();//获取页面的状态值
//		String a0501b = a05.getA0501b();//职务层
		int start = 0;
		int end = 0;
		if(startdate != null && !"".equals(startdate)){
			start = Integer.valueOf((startdate+"01").substring(0, 8));
			association.setStartdate(startdate);
		}else{
			association.setStartdate(null);
		}
		if(closingdate != null && !"".equals(closingdate)){
			end = Integer.valueOf((closingdate+"01").substring(0, 8));
			association.setClosingdate(closingdate);
		}else{
			association.setClosingdate(null);
		}
		//比较终止时间和批准时间
		if(closingdate.compareTo(startdate)<0 && status.equals("0")) {
			this.setMainMessage("终止时间不能早于起始时间");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
			
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if(st00==null ||"".equals(st00)) {
				sess.save(association);
			}else {
				Association association_old=(Association)sess.get(Association.class, st00);
				PropertyUtils.copyProperties(association_old, association);
				
				sess.update(association_old);
			}	
			sess.flush();
			updateA01(association.getA0000());
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存成功！',null,'220')");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		
		
		this.getPageElement("st00").setValue(association.getSt00());// 保存成功将id返回到页面上。
		// this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//刷新社团列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String st00) throws RadowException, AppException {
/*		Map map = this.getRequestParamer();
		int index = map.get("eventParameter") == null ? null
				: Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();*/
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Association association = (Association) sess.get(Association.class, st00);
			association.setA0000(a0000);
			A01 a01 = (A01) sess.get(A01.class, association.getA0000());
			
			//applog.createLogNew("3A05","现职务层次删除","现职务层次",a0000,a01.getA0101(), new ArrayList<Object[]>());
			
			//记录删除哪个

			sess.delete(association);
			sess.flush();	
			updateA01(association.getA0000());
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			association = new Association();
			PMPropertyCopyUtil.copyObjValueToElement(association, this);
			
			//CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//更新A01中的STJZ字段
	private void updateA01(String a0000 ) throws AppException{
		//当前页面赋值
		List<String> list1 = HBUtil.getHBSession().createSQLQuery("select STname from Association where a0000='"+a0000+"' "
				).list();
		List<String> list2 = HBUtil.getHBSession().createSQLQuery("select STJob from Association where a0000='"+a0000+"' "
				).list();
		List<String> list3 = HBUtil.getHBSession().createSQLQuery("select status from Association where a0000='"+a0000+"' "
				).list();
		StringBuffer STJZs = new StringBuffer();
		for(int i=0;i<list1.size();i++) {
			if(!list3.get(i).equals("0")) {
				STJZs.append(list1.get(i));
				STJZs.append(list2.get(i)+",");
			}	
		}
		if(STJZs.length()>0){
			STJZs.deleteCharAt(STJZs.length()-1);
		}
		
	
		
		//更新A10 a0196 专业技术职务字段。   a06 a0602
		HBUtil.executeUpdate("update a01 set STJZ='"+STJZs.toString()+"' where a0000='"+a0000+"'");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('Association_p').innerHTML='"+(STJZs.toString()==null?"":STJZs.toString())+"';");
	}
	
	/**
	 * 显示社团兼职grid表格
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
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from ASSOCIATION where a0000='" + a0000 + "' ";
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
		// String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Association association = new Association();
		association.setA0000(a0000);
		PMPropertyCopyUtil.copyObjValueToElement(association, this);
		this.getExecuteSG().addExecuteCode("setClosingDateDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int trainingInforGridOnRowClick() throws RadowException {
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String st00 = this.getPageElement("TrainingInfoGrid").getValue("st00", index).toString();
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Association association = (Association) sess.get(Association.class, st00);
			association.setA0000(a0000);
			PMPropertyCopyUtil.copyObjValueToElement(association, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','查询失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setClosingDateDisabled();");
		//this.getExecuteSG().addExecuteCode("a0501bChange();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 将实体POJO转化为JSON
	 * 
	 * @param obj
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static <T> String objectToJson(T obj) throws JSONException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string
		String jsonStr = "{}";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw e;
		}
		return jsonStr;
	}
	
}
