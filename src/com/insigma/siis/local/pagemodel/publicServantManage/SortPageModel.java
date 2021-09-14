package com.insigma.siis.local.pagemodel.publicServantManage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class SortPageModel extends PageModel{
	
	
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String sid = this.request.getSession().getId();
		//查询出当前最大的排序号
		String sql = "select max(sort) from A01SEARCHTEMP where SESSIONID='"+sid+"'";
		
		Object sortObject = sess.createSQLQuery(sql).uniqueResult();
		
		String sortMax = "";
		if(sortObject != null && !sortObject.equals("")){
			sortMax = sortObject.toString();
		}
		
		this.getPageElement("sortMax").setValue(sortMax);
		
		
		this.setNextEventName("gridA01.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	//刷新列表
	@NoRequiredValidate
	@PageEvent("gridA01.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String a0000 = this.getPageElement("a0000").getValue();//人员id
		String sid = this.request.getSession().getId();
		
		String sql2 = "select a01c.A0000, a01c.SESSIONID, a01c.sort, a.A0101,a.A0192A from A01SEARCHTEMP a01c left join a01 a on a01c.A0000 = a.A0000 where SESSIONID='"+sid+"' order by sort";
		
		this.pageQuery(sql2, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	public int saveSort()throws RadowException, AppException{
		
		String sid = this.request.getSession().getId();
		
		String a0000 = this.getPageElement("a0000").getValue();		//当前调整人的id
		int sortSelect = Integer.valueOf(this.getPageElement("sortNow").getValue());		//当前排序
		int sort = Integer.valueOf(this.getPageElement("sort").getValue());					//目标排序
		
		String sql = "";
		if(sortSelect > sort){
			sql = "update A01SEARCHTEMP set sort = sort+1 where sort >= "+sort+" and  sort < "+sortSelect+"";
		}
		if(sortSelect < sort){
			sql = "update A01SEARCHTEMP set sort = sort-1 where sort > "+sortSelect+" and  sort <= "+sort+"";
		}
		
		String sqlSort = "update A01SEARCHTEMP set sort = "+sort+" where a0000 = '"+a0000+"' and  SESSIONID='"+sid+"'";
		
		HBUtil.executeUpdate(sql);
		HBUtil.executeUpdate(sqlSort);
		
		
		this.getExecuteSG().addExecuteCode("backSort()");
		
		this.setNextEventName("gridA01.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//将结果载入列表
	@NoRequiredValidate
	@PageEvent("load.onclick")
	public int load()throws RadowException, AppException{
		
		/*HttpSession session=request.getSession(); 
		session.setAttribute("temporarySort",1); */
		this.request.getSession().setAttribute("temporarySort", "1");
		//this.getExecuteSG().addExecuteCode("realParent.document.getElementById('temporarySort').value = '"+1+"';");
		this.getExecuteSG().addExecuteCode("realParent.load();");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('Sort').close();");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
