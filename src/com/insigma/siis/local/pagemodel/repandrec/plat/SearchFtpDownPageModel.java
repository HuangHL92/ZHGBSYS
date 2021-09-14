package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class SearchFtpDownPageModel extends PageModel {

	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("closeBtn.onclick")
	@NoRequiredValidate            
	public int closeBtn() throws RadowException{
		this.closeCueWindow("deptWin");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 确定
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("btnAdd.onclick")
	@GridDataRange
	@NoRequiredValidate            
	public int btnAdd() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("Grid").getValueList();
		StringBuffer name = new StringBuffer("");
		StringBuffer id = new StringBuffer("");
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				if(id.length()>0){
					name.append(",").append((map.get("depict")==null?"":map.get("depict").toString()));
					id.append(",").append(map.get("userid")==null?"":map.get("userid").toString());
				}else{
					name.append((map.get("depict")==null?"":map.get("depict").toString()));
					id.append(map.get("userid")==null?"":map.get("userid").toString());
				}
				
			}
		}
		this.createPageElement("searchDeptBtn", ElementType.TEXTWITHICON, true).setValue(name.toString());
		this.createPageElement("ftpid", ElementType.HIDDEN, true).setValue(id.toString());
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
	@NoRequiredValidate           
	public int dogridQuery(int start,int limit) throws RadowException{
		String sql = "select b.b0101 as depict, a.userid, a.homedirectory,  a.enableflag, a.writepermission,  a.userid as opid from ftp_user a,b01 b where a.depict=b.b0111  order by depict asc";		
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
