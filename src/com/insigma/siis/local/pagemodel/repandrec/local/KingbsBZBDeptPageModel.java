package com.insigma.siis.local.pagemodel.repandrec.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;

public class KingbsBZBDeptPageModel extends PageModel {

	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("closeBtn.onclick")
	@NoRequiredValidate           ///??????
	public int closeBtn() throws RadowException{
		this.closeCueWindow("winFile11");
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
	public int btnAdd() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("Grid").getValueList();
		int countNum = 0;
		String name = "";
		String id = "";
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				name=map.get("b0101")==null?"":map.get("b0101").toString();
				id=map.get("b0111")==null?"":map.get("b0111").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new RadowException("请勾选信息！");
		}else if(countNum>1){
			throw new RadowException("只能勾选一条信息！");
		}
//		this.createPageElement("fabsolutepath", ElementType.TEXTWITHICON, true).setValue(name);
//		this.createPageElement("bzbDeptid", ElementType.HIDDEN, true).setValue(id);
//		this.closeCueWindow("winFile11");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('fabsolutepath').value='"+name+"';");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('bzbDeptid').value='"+id+"';");
		this.closeCueWindow("winFile11");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("Grid.dogridquery")
	public int dogridQuery(int start,int limit) throws RadowException{
		List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
//		Grid grid = (Grid)this.createPageElement("Grid", ElementType.GRID, false);
		try {
			gridList = KingbsGainBS.getB01ListByPage(start,limit);
			int orgsize = KingbsGainBS.getAllB01Size();
			PageQueryData pqd = new PageQueryData();
			pqd.setTotalCount(orgsize);
			pqd.setData(gridList);
	        this.setSelfDefResData(pqd);
		} catch (Exception e) {
			this.setMainMessage("连接标准版数据库失败，请检查配置信息。");
			return EventRtnType.FAILD;
		}
//		if(gridList.size()>0){
//			grid.setValueList(gridList);
//		}else{
//			grid.setValueList(new ArrayList<HashMap<String,Object>>());
//		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}


}
