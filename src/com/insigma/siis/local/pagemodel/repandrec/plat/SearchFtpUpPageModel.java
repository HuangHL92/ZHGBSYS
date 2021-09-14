package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SearchFtpUpPageModel extends PageModel {

	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("closeBtn.onclick")
	@NoRequiredValidate           ///??????
	public int closeBtn() throws RadowException{
		this.closeCueWindow("deptWin");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("btnAdd.onclick")
	@GridDataRange
	@NoRequiredValidate           ///??????
	public int btnAdd() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("Grid").getValueList();
		int countNum = 0;
		String name = "";
		String id = "";
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				name=map.get("name")==null?"":map.get("name").toString();
				id=map.get("id")==null?"":map.get("id").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new RadowException("请勾选信息！");
		}else if(countNum>1){
			throw new RadowException("只能勾选一条信息！");
		}
		this.createPageElement("searchDeptBtn", ElementType.TEXTWITHICON, true).setValue(name);
		this.createPageElement("ftpid", ElementType.HIDDEN, true).setValue(id);
		this.closeCueWindow("deptWin");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("Grid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String sql = "select id,name,hostname,port,username,status from trans_config t  where t.type='0'";
		this.setSelfDefResData(this.pageQuery(sql,"SQL", start,limit)); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
