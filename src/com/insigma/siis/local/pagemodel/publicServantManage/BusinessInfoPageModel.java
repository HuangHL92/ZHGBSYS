package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A60;
import com.insigma.siis.local.business.entity.A61;
import com.insigma.siis.local.business.entity.A62;
import com.insigma.siis.local.business.entity.A63;
import com.insigma.siis.local.business.entity.A64;
import com.insigma.siis.local.business.entity.A64_1;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class BusinessInfoPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	
	/**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> String objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "{}";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
	@Override
	public int doInit() throws RadowException {
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A01 where a0000='" + this.getRadow_parent_data()
					+ "'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);
			// 考试录用人员信息集
			A60 a60 = (A60) sess.get(A60.class, a01.getA0000());
			if (a60 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a60, this);
				//旧值 判断是否修改
				String json = objectToJson(a60);
				//this.getExecuteSG().addExecuteCode("parent.A60value="+json+";");
			}
			
			// 选调生信息集
			A61 a61 = (A61) sess.get(A61.class, a01.getA0000());
			if (a61 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a61, this);
				//旧值 判断是否修改
				String json = objectToJson(a61);
				//this.getExecuteSG().addExecuteCode("parent.A61value="+json+";");
			}
			
			// 公开遴选信息集
			A62 a62 = (A62) sess.get(A62.class, a01.getA0000());
			if (a62 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a62, this);
				//旧值 判断是否修改
				String json = objectToJson(a62);
				//this.getExecuteSG().addExecuteCode("parent.A62value="+json+";");
			}
			
			// 公开选调人员信息集
			A63 a63 = (A63) sess.get(A63.class, a01.getA0000());
			if (a63 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a63, this);
				//旧值 判断是否修改
				String json = objectToJson(a63);
				//this.getExecuteSG().addExecuteCode("parent.A63value="+json+";");
			}
			
			// 考试信息集
			List<String> a64Str = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A60'").list();
			List<String> a64Str_1 = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A61'").list();
			if(a64Str.size() != 0 && a64Str != null){
				A64 a64 = (A64) sess.get(A64.class, a64Str.get(0).toString());
				if (a64 != null) {
					PMPropertyCopyUtil.copyObjValueToElement(a64, this);
					//旧值 判断是否修改
					String json = objectToJson(a64);
					//this.getExecuteSG().addExecuteCode("parent.A64value="+json+";");
				}
			}
			if(a64Str_1.size() != 0 && a64Str_1 != null){
				A64 a64_2 = (A64) sess.get(A64.class, a64Str_1.get(0).toString());				
			//	PropertyUtils.copyProperties(a64_1, a64_2);

				if (a64_2 != null) {
					//PMPropertyCopyUtil.copyObjValueToElement(a64_1, this);
					if(a64_2.getA6401() != null){
						this.getPageElement("a6401_1").setValue(a64_2.getA6401().toString());
					}
					
					if(a64_2.getA6402() != null){
						this.getPageElement("a6402_1").setValue(a64_2.getA6402().toString());
					}
					
					if(a64_2.getA6403() != null){
						this.getPageElement("a6403_1").setValue(a64_2.getA6403().toString());
					}
					
					if(a64_2.getA6404() != null){
						this.getPageElement("a6404_1").setValue(a64_2.getA6404().toString());
					}
					
					if(a64_2.getA6405() != null){
						this.getPageElement("a6405_1").setValue(a64_2.getA6405().toString());
					}
					
					if(a64_2.getA6406() != null){
						this.getPageElement("a6406_1").setValue(a64_2.getA6406().toString());
					}
					
					if(a64_2.getA6407() != null){
						this.getPageElement("a6407_1").setValue(a64_2.getA6407().toString());
					}
					
					if(a64_2.getA6408() != null){
						this.getPageElement("a6408_1").setValue(a64_2.getA6408().toString());
					}
										
					//旧值 判断是否修改
					String json = objectToJson(a64_2);
					//this.getExecuteSG().addExecuteCode("parent.A64value="+json+";");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainingInfoGrid.dogridquery");//培训信息列表	
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	

	/**
	 * 人员其它信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveOthers(String confirm)throws RadowException, AppException{
		String a0000 = this.getRadow_parent_data();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] confirminfo = confirm.split("--");
		String showmsg = "任免表信息、业务信息保存成功!";
		String msgwidth = "220";
		if(confirminfo.length==2){
			showmsg = "任免表信息、其他信息、业务信息保存成功!";
			msgwidth = "275";
		}
		confirm = confirminfo[0];
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//考试录用人员信息集 id生成方式为assigned
			A60 a60 = new A60();
			this.copyElementsValueToObj(a60, this);
			a60.setA0000(a0000);
			A60 a60_old = (A60)sess.get(A60.class, a0000);
			if(a60_old==null){
				a60_old = new A60();
				applog.createLog("3601", "A60", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a60_old,a60));
				
			}else{
				applog.createLog("3602", "A60", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a60_old,a60));
			}
			PropertyUtils.copyProperties(a60_old, a60);
			sess.saveOrUpdate(a60_old);	
			//新增或更新A64信息集
			List<A64> a64List = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A60'").list();
			A64 a64 = new A64();
			this.copyElementsValueToObj(a64, this);
			a64.setA0000(a0000);
			List<String> a64Str = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A60'").list();
			A64 a64_old = new A64();
			a64.seta64type("A60");//考试录用人员
			if(a64List.size() == 0 || a64List == null){
				//新增A64信息
				applog.createLog("3641", "A64", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a64_old,a64));					
				sess.save(a64);	
			}else{
				//更新A64信息
				a64_old = (A64)sess.get(A64.class, a64List.get(0));
				applog.createLog("3642", "A64", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a64_old,a64));
				PropertyUtils.copyProperties(a64_old, a64);
				a64_old.setA6400(a64Str.get(0).toString());
				sess.update(a64_old);	
			}
			//选调生信息集 id生成方式为assigned
			A61 a61 = new A61();
			this.copyElementsValueToObj(a61, this);
			a61.setA0000(a0000);
			A61 a61_old = (A61)sess.get(A61.class, a0000);
			if(a61_old==null){
				a61_old = new A61();
				applog.createLog("3611", "A61", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a61_old,a61));
				
			}else{
				applog.createLog("3612", "A61", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a61_old,a61));
			}
			PropertyUtils.copyProperties(a61_old, a61);
			sess.saveOrUpdate(a61_old);	
			//新增或更新A64信息集
			List<A64_1> a64_1List = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A61'").list();
			A64_1 a64_1 = new A64_1();//页面上要获取的a64
			A64 a64_2 = new A64();
			this.copyElementsValueToObj(a64_1, this); //页面上获取到输入信息
			 //将获取到的信息拷贝给a64_2
			a64_2.setA0000(a0000);
			a64_2.setA6401(a64_1.getA6401_1());
			a64_2.setA6402(a64_1.getA6402_1());
			a64_2.setA6403(a64_1.getA6403_1());
			a64_2.setA6404(a64_1.getA6404_1());
			a64_2.setA6405(a64_1.getA6405_1());
			a64_2.setA6406(a64_1.getA6406_1());
			a64_2.setA6407(a64_1.getA6407_1());
			a64_2.setA6408(a64_1.getA6408_1());
			
			List<String> a64_1Str = HBUtil.getHBSession().createSQLQuery("Select a6400 from a64 where a0000 = '"+a0000+"'" +" and a64.a64type='A61'").list();
			A64 a64_2_old = new A64();
			a64_2.seta64type("A61");//选调生考试人员
			if(a64_1List.size() == 0 || a64_1List == null){
				//新增A64信息
				applog.createLog("3641", "A64", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a64_2_old,a64_2));					
				sess.save(a64_2);	
			}else{
				//更新A64信息
				a64_2_old = (A64)sess.get(A64.class, a64_1List.get(0));
				applog.createLog("3642", "A64", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a64_2_old,a64_2));
				PropertyUtils.copyProperties(a64_2_old, a64_2);
				a64_2_old.setA6400(a64_1Str.get(0).toString());
				sess.update(a64_2_old);	
			}
			//公开遴选信息集 id生成方式为assigned
			A62 a62 = new A62();
			this.copyElementsValueToObj(a62, this);
			a62.setA0000(a0000);
			A62 a62_old = (A62)sess.get(A62.class, a0000);
			if(a62_old==null){
				a62_old = new A62();
				applog.createLog("3621", "A62", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a62_old,a62));
				
			}else{
				applog.createLog("3622", "A62", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a62_old,a62));
			}
			PropertyUtils.copyProperties(a62_old, a62);
			sess.saveOrUpdate(a62_old);	
			
			//公开选调人员信息集 id生成方式为assigned
			A63 a63 = new A63();
			this.copyElementsValueToObj(a63, this);
			a63.setA0000(a0000);
			A63 a63_old = (A63)sess.get(A63.class, a0000);
			if(a63_old==null){
				a63_old = new A63();
				applog.createLog("3631", "A63", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a63_old,a63));
				
			}else{
				applog.createLog("3632", "A63", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a63_old,a63));
			}
			PropertyUtils.copyProperties(a63_old, a63);
			sess.saveOrUpdate(a63_old);	
			
			//考试信息集 id生成方式为uuid
//			A64 a64 = new A64();
//			this.copyElementsValueToObj(a64, this);
//			a64.setA0000(a0000);
//			A64 a64_old = (A64)sess.get(A64.class, a0000);
//			if(a64_old==null){
//				a64_old = new A64();
//				applog.createLog("3641", "A64", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a64_old,a64));
//				
//			}else{
//				applog.createLog("3642", "A64", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a64_old,a64));
//			}
//			PropertyUtils.copyProperties(a64_old, a64);
//			sess.saveOrUpdate(a64_old);	
			sess.flush();
			this.getExecuteSG().addExecuteCode("if('true'=='"+confirm+"'){" +
					"window.parent.parent.tabs.remove(parent.thisTab.tabid);" +
				"}else{" +
					"window.parent.$h.alert('系统提示','"+showmsg+"',null,"+msgwidth+")" +
				"}" +
			"");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("业务信息保存失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************培训信息A11*********************************************************************/
	
	/**
	 * 培训信息列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int trainingInforGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getRadow_parent_data();
		String sql = "select a1.* from a41 a4,A11 a1 where a4.a1100=a1.a1100 and a4.a0000='"+a0000+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 培训信息新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A11 a11 = new A11();
		PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 培训信息的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int trainingInforGridOnRowClick() throws RadowException{  
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训信息查询失败！',null,'220')");
			return EventRtnType.FAILD;
		}	
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("saveA11.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo()throws RadowException, AppException{
		A11 a11 = new A11();
		this.copyElementsValueToObj(a11, this);
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a11.getA1131()==null||"".equals(a11.getA1131())){
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训名称不能为空！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a11.setA0000(a0000);
		String a1100 = this.getPageElement("a1100").getValue();
		String a1107c = this.getPageElement("a1107c").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		if(a1107c == null || "".equals(a1107c)){
			a11.setA1107c(null);
		}else{
			a11.setA1107c(Double.parseDouble(this.getPageElement("a1107c").getValue()));
		}
		if(a1108 == null || "".equals(a1108)){
			a11.setA1108(null);
		}else{
			a11.setA1108(Double.parseDouble(this.getPageElement("a1108").getValue()));
		}
		
		String a1107 = a11.getA1107();//培训开始时间
		String a1111 = a11.getA1111();//培训结束时间
		if(a1107!=null&&!"".equals(a1107)&&a1111!=null&&!"".equals(a1111)){
			if(a1107.length()==6){
				a1107 += "01";
			}
			if(a1111.length()==6){
				a1111 += "01";
			}
			int start = Integer.valueOf(a1107);
			int end = Integer.valueOf(a1111);
			if(start>end){
				this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训开始时间不能大于结束时间！',null,'220')");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//计算培训有几月几天。
			int days = DateUtil.getDaysBetween(DateUtil.stringToDate(a1107, "yyyymmdd"), DateUtil.stringToDate(a1111, "yyyymmdd"));
			int mounthA1107a = days/31;//月
			int dayA1107b = days%31;//天
			a11.setA1107a((long)mounthA1107a);
			a11.setA1107b((long)dayA1107b);
		}else{
			a11.setA1107a(null);
			a11.setA1107b(null);
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			A41 a41 = new A41();
			A11 a11_old = null;
			if(a1100==null||"".equals(a1100)){
				a11_old = new A11();
				applog.createLog("3111", "A11", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a11_old,a11));
				sess.save(a11);	
				sess.flush();
				a41.setA0000(a0000);
				a41.setA1100(a11.getA1100());
				sess.save(a41);	
			}else{
				a11_old = (A11)sess.get(A11.class, a1100);
				applog.createLog("3112", "A11", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a11_old,a11));
				PropertyUtils.copyProperties(a11_old, a11);
				sess.update(a11_old);	
			}
			sess.flush();			
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训信息保存成功！',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训信息保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1100").setValue(a11.getA1100());//保存成功将id返回到页面上。
		this.getPageElement("a1107a").setValue(a11.getA1107a()==null?"":a11.getA1107a().toString());//月
		this.getPageElement("a1107b").setValue(a11.getA1107b()==null?"":a11.getA1107b().toString());//日
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//刷新专业技术职务列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow()throws RadowException, AppException{
		Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1100 = this.getPageElement("TrainingInfoGrid").getValue("a1100",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A11 a11 = (A11)sess.get(A11.class, a1100);
			A01 a01 = (A01)sess.get(A01.class, a11.getA0000());
			applog.createLog("3113", "A11", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A11(),new A11()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[]{a11.getA1100()});
			sess.delete(a11);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a11 = new A11();
			PMPropertyCopyUtil.copyObjValueToElement(a11, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.parent.$h.alert('系统提示','培训信息删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
