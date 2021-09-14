package com.insigma.siis.local.pagemodel.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class EditFilePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		//获取父页面传递的参数
		String value=this.getRadow_parent_data();
		String[] values = value.split("@");
		this.getPageElement("uuid").setValue(values[0]);
		this.getPageElement("flag").setValue(values[1]);
		if("".equals(values[1])){
			
		}
		HBSession sess = HBUtil.getHBSession();
		String hql = "From A01 S where S.a0000 = '"+value.split("@")[0]+"'";
		A01 a01 =(A01) sess.createQuery(hql).list().get(0);
		this.getPageElement("peisonname").setValue(a01.getA0101());
		this.getPageElement("personid").setValue(a01.getA0000());
		this.getPageElement("idcard").setValue(a01.getA0184());
		
		this.setNextEventName("FileGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//根据机构树中选择的机构，查询机构下的人员信息
	@PageEvent("orgTreeGridJsonData")
	public int getOrgTreeGridJsonData() throws PrivilegeException, AppException, RadowException {
		//登录用户的用户编码
		String uuid =this.getParameter("uuid");
		String flag =this.getParameter("flag");
		String uname =this.getParameter("uname");
		CommonQueryBS.systemOut(uuid+"/"+flag+"/"+uname);
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//定义返回信息的json串
		StringBuffer jsonStr = new StringBuffer();
		//sql语句
		String sql = "";
		//如果选中机构树中的机构时，查询相关的人员信息
		if(uuid!=null){
			//sql语句
			sql = "select ai.id,ai.filename"+
			" from attachment_info ai"+
			" where ai.objectid = '"+uuid+"'";
				
			CommonQueryBS query_person = new CommonQueryBS();
			query_person.setConnection(HBUtil.getHBSession().connection());
			query_person.setQuerySQL(sql);
			Vector<?> vector_attach = query_person.query();
			Iterator<?> iterator_attach = vector_attach.iterator();
			int i = 0;
			//开始组装json字符串
			jsonStr.append("[");
			
			while(iterator_attach.hasNext()){
				HashMap attach_hashmap = (HashMap) iterator_attach.next();
				//定义变量，用以判断该人员是否已被选中
				String check="indeterminate";
				
				if(i==0){
					jsonStr.append("{task:'"+attach_hashmap.get("filename")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+attach_hashmap.get("id")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}else{
					jsonStr.append(",{task:'"+attach_hashmap.get("filename")+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" "+check+" id=\""+attach_hashmap.get("id")+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				i++;
				
			}
			//组装结尾
			jsonStr.append("]");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	@PageEvent("FileGrid.dogridquery")
	public int queryAttachmentInfo(int start, int limit) throws RadowException{
		
		String sValue = this.getRadow_parent_data();//获取参数
		String[] values = sValue.split("@");
		String sSql = "";
		if("1".equals(values[1])){
			sSql = "select ai.id,ai.personid,ai.personname,ai.uploaddate,ai.filepath," +
					"ai.filetype,ai.filename,ai.userid,ai.username,ai.attribute1,ai.attribute2," +
					"ai.attribute3,ai.attribute4,ai.objectid " +
					" from attachment_info ai" +
					" where ai.personid = '"+values[0]+"'";
		}else if("0".equals(values[1])){
			sSql = "select ai.id,ai.personid,ai.personname,ai.uploaddate,ai.filepath," +
			"ai.filetype,ai.filename,ai.userid,ai.username,ai.attribute1,ai.attribute2," +
			"ai.attribute3,ai.attribute4,ai.objectid " +
			" from attachment_info ai" +
			" where ai.objectid = '"+values[0]+"'";
		}
		this.pageQuery(sSql, "SQL", start, limit);
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("uploadFile")
	public int uploadFile() throws RadowException{
		
		String sValue = this.getRadow_parent_data();//获取参数
		String[] values = sValue.split("@");
		String Files=this.getPageElement("rowLength").getValue();
		CommonQueryBS.systemOut("------"+Files);
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getFileList")
	public int getFileList(){
		CommonQueryBS.systemOut("getFileList");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
