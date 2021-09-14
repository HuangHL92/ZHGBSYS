package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class MultimediaGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX(String record) throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();//String a0000 = this.getRadow_parent_data();
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//人员已审核则锁定
		/*if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}*/
		//this.getExecuteSG().addExecuteCode("setA1700()");
		//this.getPageElement("title").setValue(getDocumentTitle(a1700,a0000));
		this.setNextEventName("TrainInfoGrid.dogridquery");
		this.setNextEventName("TrainAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 保存修改
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String startdate = this.getPageElement("startdate").getValue();
		String enddate = this.getPageElement("enddate").getValue();
		String entrycontent = this.getPageElement("entrycontent").getValue();
		
		String a1700 = this.getPageElement("a1700").getValue();
		if(a1700==null||"".equals(a1700)){
			this.setMainMessage("请先选择一条多媒体信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select a1700 from a17 where a1700 = '"+a1700+"'").uniqueResult();
			if(obj!=null){
				//判断a1700是否已经存在，如果存在，update；如果不存在，insert。
				sess.createSQLQuery("update a17 set a17.start_date = '"+startdate+"',a17.end_date = '"+enddate+"',"
						+ "a17.entry_content = '"+entrycontent+"' where a17.a1700 = '"+a1700+"'").executeUpdate();
			}else{
				sess.createSQLQuery("insert into a17(a0000,a1700,start_date,end_date,entry_content,is_check) values ('"+a0000+"','"+a1700+"','"+startdate+"','"+enddate+"','"+entrycontent+"','1')").executeUpdate();
			}
			sess.flush();
			this.setMainMessage("保存成功!");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainInfoGrid.dogridquery");//刷新
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}
	/**
	 * 多媒体列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a01.a0101, t.a1700,t.start_date,t.end_date,t.entry_content from A17 t,a01  where a01.a0000 = '"+a0000+"' and t.a0000='"+a0000+"' and t.is_check = '1' order by t.start_date,t.end_date";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 多媒体新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1700 = UUID.randomUUID().toString().replaceAll("-", "");
		this.getPageElement("a1700").setValue(a1700);
		this.getPageElement("startdate").setValue("");
		this.getPageElement("enddate").setValue("");
		this.getPageElement("entrycontent").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("TrainInfoGrid").getCueRowIndex();
		String a1700 = this.getPageElement("TrainInfoGrid").getValue("a1700",index).toString();
		
		HBSession sess = HBUtil.getHBSession();
		Object[] objs = (Object[]) sess.createSQLQuery("select t.start_date,t.end_date,t.entry_content from A17 t where t.a1700 = '"+a1700+"'").uniqueResult();
		if(objs!=null){
			this.getPageElement("startdate").setValue(""+objs[0]);
			this.getPageElement("enddate").setValue(""+objs[1]);
			this.getPageElement("entrycontent").setValue(""+objs[2]);
		}
		this.getPageElement("a1700").setValue(a1700);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1700)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			sess.createSQLQuery("delete from a17 where a1700 = '"+a1700+"'").executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainInfoGrid.dogridquery')");
			this.setMainMessage("删除成功！");
			this.getPageElement("a1700").setValue("");
			this.getPageElement("startdate").setValue("");
			this.getPageElement("enddate").setValue("");
			this.getPageElement("entrycontent").setValue("");
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//得到最新的排序号
	public long getsortid(String a0000){
		HBSession session = HBUtil.getHBSession();
		String maxSortid = session.createSQLQuery("select max(sortid) from a36 where A0000='"+a0000+"' ORDER BY SORTID").uniqueResult().toString();
		return Long.parseLong(maxSortid);
	}
	
	//获取网页标题
	/*
	 * @PageEvent("getTitle") public int getDocumentTitle(String a1700,String a0000)
	 * { System.out.println("调用了这个方法"); try { HBSession session =
	 * HBUtil.getHBSession(); Object[] obj = (Object[])session.
	 * createSQLQuery("select a0101,start_date,end_date,entry_content from a01,a17 where a01.a0000='"
	 * +a0000+"' and a1700='"+a1700+"'").uniqueResult(); String name =
	 * (String)obj[0];//获取姓名 StringBuilder start = new
	 * StringBuilder((String)obj[1]);//获取开始时间 StringBuilder end = new
	 * StringBuilder((String)obj[2]);//获取结束时间 String
	 * entfyContent=(String)obj[3];//获取详情内容 //拼接字符串 String str =
	 * name+" "+start.insert(4, ".")+"--"+end.insert(4, ".")+" "+entfyContent;
	 * 
	 * this.getPageElement("title").setValue(str); } catch (RadowException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */
}
