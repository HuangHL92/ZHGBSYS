package com.insigma.siis.local.pagemodel.publicServantManage;


import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

//人员信息列表页面，机构树右键“集体内排序”
public class A0225WinPageModel extends PageModel{
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridA01.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//加载列表
	@PageEvent("gridA01.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String a0201b = this.getPageElement("a0201b").getValue();
		String a0000 = this.getPageElement("a0000").getValue();//人员id
		String a0101 = this.getPageElement("a0101").getValue();//姓名
		String a0225 = this.getPageElement("a0225").getValue();//集体内排序
		if(a0225==null||"".equals(a0225)){
			a0225 = "0";
		}
		String authoritySQL = "select t.b0111 b from COMPETENCE_USERDEPT t where t.userid='"+SysManagerUtils.getUserId()+"'";
		
		String sql="from B01 where B0111='"+a0201b+"' ";
		List alist = HBUtil.getHBSession().createQuery(sql).list();
		if(alist!=null && alist.size()>0){
			String sql2="select t2.a0225 a0225,t.a0000 a0000,  t.a0101 a0101, t.A0192A a0192a from a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and exists  ("+authoritySQL+" and t.b0111=b.a0201b)) " +
					" union " +
					" select '"+a0225+"' a0225,t.a0000 a0000,A0192A,'"+a0101+"' a0101 from a01 t where a0000='"+a0000+"' and not exists " +
							"(select 1 from  a01 t,(select distinct a0000,a0225  from a02 where a0201b='"+a0201b+"') t2 where t.a0000='"+a0000+"' and t.a0000=t2.a0000 and status='1' and exists (select a0000 from a02 b where t.a0000=b.a0000 and a0255='1' and a0201b='"+a0201b+"' and  a0201b in("+authoritySQL+")))" +
					"order by a0225";
			this.pageQuery(sql2, "SQL", start, limit); 
			return EventRtnType.SPE_SUCCESS;
		}else{
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	//保存设置好的集体内排序
	@PageEvent("saveBtn.onclick")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("gridA01").getStringValueList();
		String a0201b = this.getPageElement("a0201b").getValue();	
		
		try {
			int i = 1;
			for(HashMap<String,String> m : list){
				String a0000 = m.get("a0000");
				HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				i++;
			}
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.realParent.reloadGrid();");		//刷新人员信息列表页面
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
