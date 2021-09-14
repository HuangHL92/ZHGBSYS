package com.insigma.siis.local.pagemodel.customquery;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.Query;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
/**
 * @author lixy
 *
 */
public class ListSaveWinPageModel extends PageModel {
	private CustomQueryBS ctcBs=new CustomQueryBS();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		// TODO Auto-generated method stub
		//this.setNextEventName("queryList");
		this.getPageElement("perName").setValue(CustomQueryPageModel.LISTADDNAME);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("resetPreName")
	public int resetPreName() throws RadowException {
		CustomQueryPageModel.LISTADDCCQLI="-1";
		CustomQueryPageModel.QUERYLISTFLAG=false;
		CustomQueryPageModel.LISTADDNAME="无";
		this.getPageElement("perName").setValue(CustomQueryPageModel.LISTADDNAME);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* 设定选中行号到页面上
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("listGrid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException{
		int cueRowIndex = this.getPageElement("listGrid").getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryList")
	public int queryList() throws RadowException{
		   String data= JSONArray.fromObject(ctcBs.getQueryList(SysUtil.getCacheCurrentUser().getLoginname())).toString();
		   if(data!=null&&!"".equals(data))
   		this.getPageElement("listGrid").setValue(data
   				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("addList.onclick")
	public int addList() {
		String listName="";
		String perName="";
		String perID=CustomQueryPageModel.LISTADDCCQLI;
		try {
			perName=this.getPageElement("perName").getValue();
		} catch (RadowException e) {
			this.setMainMessage("获取页面<上级名称>失败！");
			return EventRtnType.FAILD;
		}
		/*try {
			perID=this.getPageElement("perID").getValue();
		} catch (RadowException e) {
			this.setMainMessage("获取页面<父节点ID>失败！");
			return EventRtnType.FAILD;
		}
		if(perID == null || "".equals(perID)){
			perID="-1";
		}*/
		try {
			listName = this.getPageElement("listName").getValue();
		} catch (RadowException e) {
			this.setMainMessage("获取页面<名称>失败！");
			return EventRtnType.FAILD;
		}
		if(listName == null || "".equals(listName)){
			this.setMainMessage("名称不能为空！");
			return EventRtnType.FAILD;
		}
    	this.getExecuteSG().addExecuteCode("realParent.document.getElementById('saveName').value = '"+listName+"'");
    	
    	String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		try {
			ctcBs.saveListNew(listName,loginName,sql,"",perID);
			this.getPageElement("listName").setValue("");
		}catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('win3').close();");
		//this.getExecuteSG().addExecuteCode("initTree();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("overWriteList.onclick")
	public int overWriteList() throws RadowException,AppException{
		String listName="";
		String curID="";
		try {
			curID=this.getPageElement("perID").getValue();
		} catch (RadowException e) {
			this.setMainMessage("请选择需要覆盖的列表！");
			return EventRtnType.FAILD;
		}
		if(curID == null || "".equals(curID)){
			this.setMainMessage("请选择需要覆盖的列表！");
			return EventRtnType.FAILD;
		}
		try {
			listName = this.getPageElement("listName").getValue();
		} catch (RadowException e) {
			this.setMainMessage("获取页面<名称>失败！");
			return EventRtnType.FAILD;
		}
		if(listName == null || "".equals(listName)){
			this.setMainMessage("名称不能为空！");
			return EventRtnType.FAILD;
		}
    	this.getExecuteSG().addExecuteCode("realParent.document.getElementById('saveName').value = '"+listName+"'");
    	String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		try {
			ctcBs.updateList(listName,"","",loginName,sql,curID);
		}catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		this.getPageElement("listName").setValue("");
		//this.getExecuteSG().addExecuteCode("initTree();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/* 删除列表
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteList")
	@NoRequiredValidate
	public int deleteList() throws RadowException, AppException{
		String curID="";
		try {
			curID=this.getPageElement("perID").getValue();
		} catch (RadowException e) {
			this.setMainMessage("请选择需要删除的列表！");
			return EventRtnType.FAILD;
		}
		if(curID == null || "".equals(curID)){
			this.setMainMessage("请选择需要删除的列表！");
			return EventRtnType.FAILD;
		}
		ctcBs.delClNew(curID);
		this.getPageElement("perName").setValue("无");
		this.getPageElement("perID").setValue("-1");
		this.getPageElement("listName").setValue("");
		//this.getExecuteSG().addExecuteCode("initTree();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/* 删除列表
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteEvent")
	@NoRequiredValidate
	public int deleteEvent(String rowIndex) throws RadowException, AppException{
		//String ri=this.getPageElement("cueRowIndex").getValue();
		if(rowIndex==null||"".equals(rowIndex))
			throw new AppException("请选中要删除的列表");
		int rowIdx= Integer.parseInt(rowIndex);
		PageElement pe = this.getPageElement("listGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		HashMap<String,Object> map=list.get(rowIdx);
		ctcBs.delCl(map.get("cqli").toString());
		this.getPageElement("cueRowIndex").setValue("");//清空选择行数
		this.setNextEventName("queryList");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 
	 * @author wangs2
	 * 			保存列表的树结构
	 * @throws PrivilegeException
	 * @throws AppException
	 * @throws RadowException 
	 */
	@PageEvent("PersionTreeJsonData")
	public int getPersionTreeJsonData() throws RadowException {
		String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		return getCodeValueTree(loginName);
	}
	private int getCodeValueTree(String loginName) {
		String jsonStr =CodeType2js.getCusQueryList(loginName);
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	@PageEvent("querybyid")
	public int getCurName(String id) throws RadowException{
		String sql="SELECT CQLI,LISTNAME  FROM CUSTOMQUERYLIST WHERE CQLI='"+id+"' ORDER BY LISTTIME DESC";
		try {
			PageQueryData pageQuery = this.pageQuery(sql,"SQL", -1, 999); 	
			List list= (List) pageQuery.getData();
			if(list.size()>0){	//数据不为空
				Map map = (Map)list.get(0);
				this.getPageElement("perName").setValue(map.get("listname")+"");
				this.getPageElement("perID").setValue(map.get("cqli")+"");
				this.getPageElement("listName").setValue("");
			}else{	//数据为空
				this.getPageElement("perName").setValue("无");
				this.getPageElement("perID").setValue("-1");
				this.getPageElement("listName").setValue("");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
