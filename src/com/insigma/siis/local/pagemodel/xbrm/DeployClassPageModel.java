package com.insigma.siis.local.pagemodel.xbrm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.utils.CommonQueryBS;

public class DeployClassPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
		
		
		this.setNextEventName("initx");
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		
		
		String rbId = this.getPageElement("rbId").getValue();	
		String dc005 = this.getPageElement("dc005").getValue();		//类别标识
		
		String sql = "select a.dc001,a.rb_id, b.rb_name,a.dc003,a.dc004,a.dc006 from deploy_classify a inner join  RECORD_BATCH b  on a.RB_ID = b.RB_ID   and a.RB_ID ='"+rbId+"' "
				+ " and dc005='"+dc005+"'  order by a.dc004 asc";
		if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){
			sql = "select a.dc001,a.rb_id, a.dc003,a.dc004,a.dc006 from deploy_classify a where  dc005='3'  order by a.dc004 asc";
		}
		this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	/**
	 * 智能输入
	 * @return
	 * @throws Exception
	 */
	@PageEvent("jsondata")
	public int jsondata() throws Exception{
		String p = this.request.getParameter("query");
		String sql = "select b0111,b0101 from b01 t where t.b0101 like ?||'%' and rownum<=20 "
				+ " union "
				+ " select b0111,jsdw002 b0101 from JS_DW t where  t.jsdw002 like ?||'%'   and rownum<=20 and JSDW004='0' ";
		HBSession session = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().prepareStatement(sql);
		stmt.setString(1, p);
		stmt.setString(2, p);
		rs = stmt.executeQuery();
		
		Map<String, Object> m = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> ent = new HashMap<String, String>();
		while (rs.next()) {
			ent = new HashMap<String, String>();
			ent.put("key", rs.getString("b0111")==null?"":rs.getString("b0111"));
			ent.put("value", rs.getString("b0101")==null?"":rs.getString("b0101"));
			list.add(ent);
		}
		m.put("data", list);
		this.setSelfDefResData(m);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("initx")
	public int initx() throws Exception{
		String rbId=this.getPageElement("rbId").getValue();
		String sql = "select rb_name from RECORD_BATCH where rb_id = '"+rbId+"'";
		CommQuery cq=new CommQuery();
		List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
		HashMap<String, Object> map=list.get(0);
		String rb_name = (String) map.get("rb_name");	
		this.getPageElement("t1").setValue(rb_name);
	//	setLDCombox();
		
		//初始化3条数据
		initThreeData();
		
		this.setNextEventName("grid1.dogridquery");
		this.setNextEventName("ldqkgrid111.dogridquery");
		return 0 ;
	}
	
	public void initThreeData() throws Exception{
		String rb_id = this.getPageElement("rbId").getValue();
		
		String[] strs={"市级机关","国有企业","市（县）区"};
		CommonQueryBS cq=new CommonQueryBS();
		List<HashMap<String, Object>> listBySQL = cq.getListBySQL("select dc003 from deploy_classify where rb_id='"+rb_id+"'");
		boolean flag1=false;
		boolean flag2=false;
		boolean flag3=false;
		for(HashMap<String, Object> map:listBySQL){
			String dc003=(String) map.get("dc003");
			if("市级机关".equals(dc003)){
				flag1=true;
			}else if("国有企业".equals(dc003)){
				flag2=true;
			}else if("市（县）区".equals(dc003)){
				flag3=true;
			}
		}
		if(!flag1){
			String id = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005,dc006) values(?,?,'市级机关',deploy_classify_dc004.nextval,'1','')",
					new Object[]{id,rb_id});
		}
		if(!flag2){
			String id = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005,dc006) values(?,?,'国有企业',deploy_classify_dc004.nextval,'1','')",
					new Object[]{id,rb_id});
		}
		if(!flag3){
			String id = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005,dc006) values(?,?,'市（县）区',deploy_classify_dc004.nextval,'1','')",
					new Object[]{id,rb_id});
		}
	}
	
	private void setLDCombox() throws AppException, RadowException{
		//设置下拉框
		String ldsql ="select  DC001,DC003,lpad(dc004,10,'0') dc004,dc005 from DEPLOY_CLASSIFY where dc005='3'  order by dc004";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(ldsql);
		HashMap<String, Object> mapCode_1=new LinkedHashMap<String, Object>();
		HashMap<String, Object> mapCode_2=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			mapCode_1.put(listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
			mapCode_2.put(listCode.get(i).get("dc004").toString()+"@@"+listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
		}
		//mapCode_1.put("999@@999", "其他");
		//this.getExecuteSG().addExecuteCode("alert(Ext.getCmp('selectLD_combo').store.getCount());");
		((Combo)this.getPageElement("selectLD")).setValueListForSelect(mapCode_1);
		((Combo)this.getPageElement("gdc003_grid")).setValueListForSelect(mapCode_2);
	}
	
	
	/**
	 * 单击调配类别，显示类别信息
	 */
	@PageEvent("grid1.rowclick")
	@NoRequiredValidate
	public void codeChange() throws Exception{
		String dc001=this.getPageElement("grid1").getValue("dc001",0).toString();
		String rb_id=this.getPageElement("grid1").getValue("rb_id",0).toString();
		String dc003=this.getPageElement("grid1").getValue("dc003",0).toString();
		String rb_name=this.getPageElement("grid1").getValue("rb_name",0).toString();
		String dc006=this.getPageElement("grid1").getValue("dc006",0).toString();
		
		this.getPageElement("dc001").setValue(dc001);	
		//this.getPageElement("t1").setValue(rb_name);
		this.getPageElement("dc003").setValue(dc003);
		this.getPageElement("dc006").setValue(dc006);
		//this.getExecuteSG().addExecuteCode("hiddenbtn1();");

		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
	}
	/**
	 * 类别删除
	 * @throws Exception
	 */
	@PageEvent("tabledelete")
	@NoRequiredValidate
	public void tabledelete() throws Exception{
		
		String dc001 =this.getPageElement("dc001").getValue();
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		String sql="delete from deploy_classify  where  dc001='"+dc001+"'";
		stmt.executeUpdate(sql);
		this.getPageElement("dc003").setValue("");
		this.getPageElement("dc001").setValue("");	
		this.getPageElement("dc006").setValue("");	
		
		String dc005 =this.getPageElement("dc005").getValue();
		if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){//删除该领导管辖的单位记录
			sql="delete from JS_LD_DW  where  dc001='"+dc001+"'";
			stmt.executeUpdate(sql);
			sql="delete from JS_DW j  where not exists "
					+ "(select 1 from JS_LD_DW d where d.B0111=j.b0111)";
			stmt.executeUpdate(sql);
			setLDCombox();
			this.getPageElement("gdc001").setValue("");	
			this.setNextEventName("ldqkgrid111.dogridquery");
		}
		this.toastmessage("删除成功！");
		this.setNextEventName("grid1.dogridquery");
	}
	/**
	 * 类别增加修改
	 * @throws Exception
	 */
	@PageEvent("tablechange")
	@NoRequiredValidate
	public void tablechange() throws Exception{
		
		String dc001 = this.getPageElement("dc001").getValue();
		String rb_id = this.getPageElement("rbId").getValue();
		String dc003 = this.getPageElement("dc003").getValue();
		String dc005 = this.getPageElement("dc005").getValue();		//类别标识
		String dc006 = this.getPageElement("dc006").getValue();		//详情
		if(dc003==null||"".equals(dc003)){
			this.setMainMessage("请输入类别名称！");
			return;
		}
		String id = UUID.randomUUID().toString();
		if(dc001==null||"".equals(dc001)){
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005,dc006) values(?,?,?,deploy_classify_dc004.nextval,?,?)",
					new Object[]{id,rb_id,dc003,dc005,dc006});
			this.toastmessage("新增成功！");
		}else{
			HBUtil.executeUpdate("update deploy_classify set dc003=?,dc006=? where  dc001=?",
					new Object[]{dc003,dc006,dc001});
			if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){//该领导管辖的单位记录
				this.setNextEventName("ldqkgrid111.dogridquery");
			}
			
			this.toastmessage("修改成功！");
		}
		if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){
			setLDCombox();
		}
		this.setNextEventName("grid1.dogridquery");
	}
	/**
	 * 分管领导信息
	 * @throws Exception
	 */
	@PageEvent("saveLD")
	@NoRequiredValidate
	public int saveLD() throws Exception{
		
		String gdc001 = this.getPageElement("gdc001").getValue();//id
		String rb_id = this.getPageElement("rbId").getValue();
		String dc005 = this.getPageElement("dc005").getValue();
		String dc003 = this.getPageElement("gdc003").getValue();//领导名称
		String jsdw002 = this.getPageElement("jsdw002").getValue();		//单位名称
		String b0111 = this.getPageElement("b0111").getValue();		//单位编码
		if(dc003==null||"".equals(dc003)){
			this.setMainMessage("请输入姓名！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(jsdw002==null||"".equals(jsdw002)){
			this.setMainMessage("请输入单位名称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(gdc001==null||"".equals(gdc001)){//领导添加记录
			String id = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005,dc006) values(?,?,?,deploy_classify_dc004.nextval,'3',null)",
					new Object[]{id,rb_id,dc003});
			gdc001 = id;
			//获取下拉框store时 若输入框里的值不在store中 store长度获取为0， 先触发展开一下，store的值就会正常。
			this.getExecuteSG().addExecuteCode("Ext.getCmp('selectLD_combo').onTriggerClick();Ext.getCmp('selectLD_combo').collapse();");
			
			setLDCombox();
			if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){
				this.setNextEventName("grid1.dogridquery");
			}
			
			this.getPageElement("selectLD").setValue(id);
		}
		
		if(b0111==null||"".equals(b0111)){//单位添加记录
			b0111=UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into JS_DW(JSDW002,b0111,JSDW003,JSDW004) values(?,?,deploy_classify_dc004.nextval,'0')",
					new Object[]{jsdw002,b0111});
			this.getExecuteSG().addExecuteCode("document.getElementById('selectdw').value='"+b0111+"'");
			
		}else{
			List list = HBUtil.getHBSession().createSQLQuery("select 1 from JS_DW where b0111='"+b0111+"'").list();
			if(list.size()==0){
				HBUtil.executeUpdate("insert into JS_DW(JSDW002,b0111,JSDW003,JSDW004) values(?,?,deploy_classify_dc004.nextval,'1')",
						new Object[]{jsdw002,b0111});
			}
		}
		HBUtil.executeUpdate("insert into JS_LD_DW(DC001,B0111,JSLDDW003,jslddw000) values(?,?,deploy_classify_dc004.nextval,sys_guid())",
				new Object[]{gdc001,b0111});
		this.toastmessage("添加成功！");
		this.setNextEventName("ldqkgrid111.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/***
	 * 领导列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PageEvent("ldqkgrid111.dogridquery")
	public int ldqkgridquery(int start,int limit) throws Exception{
		
		
		String sql = "select lpad(dc004,10,'0')||'@@'||dc.dc001 gdc003,j.jslddw000,dw.jsdw002  "
				+ " from deploy_classify dc,js_dw dw,js_ld_dw j where "
				+ " dc.dc001=j.dc001 and j.B0111=dw.B0111 order by dc.dc004, j.JSLDDW003";
		this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	/**
	 * 类别排序
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("btnsave.onclick")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("grid1").getStringValueList();
		
		try {			
			int i = 1;
			for(HashMap<String,String> m : list){
				String dc001 = m.get("dc001");
				
				HBUtil.executeUpdate("update deploy_classify set dc004="+i+" where dc001='"+dc001+"' ");				
				i++;				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		String dc005 = this.getPageElement("dc005").getValue();		//类别标识
		if(RMHJ.FEN_GUAN_LING_DAO.equals(dc005)){//该领导管辖的单位记录
			setLDCombox();//排序号更改重新生成下拉框
			this.setNextEventName("ldqkgrid111.dogridquery");
		}			
		this.toastmessage("排序已保存！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 领导列表排序
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("ldqkgridsort")
	@Transaction
	public int ldqkgridsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("ldqkgrid111").getStringValueList();
		
		try {			
			int i = 1;
			for(HashMap<String,String> m : list){
				String jslddw000 = m.get("jslddw000");
				
				HBUtil.executeUpdate("update js_ld_dw set jslddw003="+i+" where jslddw000='"+jslddw000+"' ");				
				i++;				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
					
		this.toastmessage("排序已保存！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除单位列表条目
	 * @param id
	 * @throws Exception
	 */
	@PageEvent("deleteDW")
	@NoRequiredValidate
	public void deleteDW(String id) throws Exception{
		
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		String sql="delete from JS_LD_DW  where  JSLDDW000='"+id+"'";//删除关系表
		stmt.executeUpdate(sql);
		sql="delete from JS_DW j  where not exists "
				+ "(select 1 from JS_LD_DW d where d.B0111=j.b0111)";
		stmt.executeUpdate(sql);//删除单位表，若在关系表中不存在
		this.toastmessage("删除成功！");
		this.setNextEventName("ldqkgrid111.dogridquery");
	}
	
	
}
