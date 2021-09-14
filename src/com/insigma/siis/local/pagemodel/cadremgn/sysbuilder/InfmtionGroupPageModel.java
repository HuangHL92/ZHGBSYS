package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class InfmtionGroupPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		loadInformation();
//		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
//		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		String sql="select * from code_value where code_type='TC04' order by code_value";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=new LinkedHashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).get("code_value").toString(), list.get(i).get("code_name"));
		}
		this.getPageElement("t3").setValue(null);
		((Combo)this.getPageElement("t3")).setValueListForSelect(map); 
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 单击信息群，显示信息群信息
	 */
	@PageEvent("grid1.rowclick")
	@NoRequiredValidate
	public void codeChange() throws Exception{
		String table_name=this.getPageElement("grid1").getValue("rowsname",0).toString();
		String secretLevel=this.getPageElement("grid1").getValue("secretlevel",0).toString();
		String[] arr=table_name.split("\\.");
		arr[1]=arr[1].replaceAll("&acute;","'").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replace("&middot;", ".");
		this.getPageElement("t1").setValue(arr[0]);
		this.getPageElement("t2").setValue(arr[1]);
		this.getPageElement("t3").setValue(secretLevel);
		this.getExecuteSG().addExecuteCode("hiddenbtn1();");
//		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
//		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(false);
//		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
	}
	
	/**
	 * 修改群代码
	 */
	@PageEvent("changeValue")
	@NoRequiredValidate
	public void changeValue() throws Exception{
		String value=this.getPageElement("t1").getValue();
		if(value!=null&&!"".equals(value.trim())){
			value=value.trim();
			String strExp="^[D-Zd-z]$";
			if(value.matches(strExp)){
				this.getPageElement("t1").setValue(value.toUpperCase());
			}else{
				this.setMainMessage("群代码只能输D-Z之间的字符串");
				this.getPageElement("t1").setValue("");
			}
		}
	}
	
	/**
	 * 新增信息群
	 */
	@PageEvent("addtolist")
	@NoRequiredValidate
	public void addtolist() throws Exception{
		String value=this.getPageElement("t1").getValue();
		if(value!=null&&!"".equals(value)){
			String name=this.getPageElement("t2").getValue().trim();
			if(name!=null&&!"".equals(name)){
				name=name.replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replace(".", "&middot;");
				String secretLevel=this.getPageElement("t3").getValue();
				String sql="select * from GRPDFN t  where t.\"GrpCod\"='"+value+"'";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					this.setMainMessage("群代码与库中已有信息群代码重复，请重填");
				}else{
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					sql="insert into GRPDFN values('"+value+"','"+name+"','0','"+secretLevel+"')";
					stmt.executeUpdate(sql);
					loadInformation();
					this.getPageElement("t1").setValue("");
					this.getPageElement("t2").setValue("");
					this.getPageElement("t3").setValue("");
				}
			}else{
				this.setMainMessage("群名称不能为空");
			}
		}else{
			this.setMainMessage("群代码不能为空");
		}
	}
	
	/**
	 * 代码集名称和描述校验
	 */
	@PageEvent("specialValue")
	@NoRequiredValidate
	public void specialValue() throws Exception {
		String t2Value = this.getPageElement("t2").getValue();
		if (t2Value != null && !"".equals(t2Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (t2Value.matches(regex)) {
				this.getPageElement("t2").setValue(t2Value);
			} else {
				this.setMainMessage("群名称只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("t2").setValue("");
			}
		}
	}
	
	
	/**
	 * 修改信息群
	 */
	@PageEvent("changetolist")
	@NoRequiredValidate
	public void changetolist() throws Exception{
		String value=this.getPageElement("t1").getValue();
		String name=this.getPageElement("t2").getValue().trim();
		String secretLevel=this.getPageElement("t3").getValue();
		if(name!=null&&!"".equals(name.trim())){
			name=name.replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replace(".", "&middot;");
			HBSession hbsess = HBUtil.getHBSession();	
			Statement  stmt = hbsess.connection().createStatement();
			String sql="update GRPDFN set \"GrpCpt\"='"+name+"',secretLevel='"+secretLevel+"' where  \"GrpCod\"='"+value+"'";
			stmt.executeUpdate(sql);
			loadInformation();
			this.getPageElement("t1").setValue("");
			this.getPageElement("t2").setValue("");
			this.getPageElement("t3").setValue("");
			this.getExecuteSG().addExecuteCode("showbtn1();");
//			this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
//			this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
//			this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
		}else{
			this.setMainMessage("群名称不能为空");
		}
	}
	
	/**
	 * 删除信息群
	 */
	@PageEvent("deletetolist")
	@NoRequiredValidate
	public void deletetolist() throws Exception{
		String value=this.getPageElement("t1").getValue();
		String name=this.getPageElement("t2").getValue();
		if("A".equals(value)||"B".equals(value)
		  ||"C".equals(value)||"D".equals(value)){
			this.setMainMessage("非自定义信息群不能删除");
		}else{
			CommQuery cqbs=new CommQuery();
			String infoSql="select * from code_table where TABLE_GROUP='"+value+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(infoSql);
			if(list!=null&&list.size()>0){
				this.setMainMessage("该信息群下已定义了信息集，不能删除");
			}else{
				HBSession hbsess = HBUtil.getHBSession();	
				Statement  stmt = hbsess.connection().createStatement();
				String sql="delete from GRPDFN  where  \"GrpCod\"='"+value+"'";
				stmt.executeUpdate(sql);
				loadInformation();
				this.getPageElement("t1").setValue("");
				this.getPageElement("t2").setValue("");
				this.getPageElement("t3").setValue("");
				this.getExecuteSG().addExecuteCode("showbtn1();");
//				this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
//				this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
//				this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
			}
		}
	}
	
	/**
	 * 加载信息群
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	public void loadInformation() throws RadowException, AppException{
		CommQuery cqbs=new CommQuery();
		String infoSql="select  t.secretLevel secretlevel ,t.\"GrpCod\"||'.'|| t.\"GrpCpt\" rowsname from GRPDFN t order by t.\"GrpCod\"";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(infoSql);
		this.getPageElement("grid1").setValueList(list);
	}
	
}
