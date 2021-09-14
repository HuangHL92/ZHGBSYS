package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.utils.kingbs.KingBSImpUtil;

public class KingbsDataPageModel extends PageModel{
	/**
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("confbtn.onclick")
	public int confbtn() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("Fgrid").getValueList();
		int countNum = 0;
		String fname = "";
		String fabsolutepath = "";
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				fname=map.get("fname")==null?"":map.get("fname").toString();
				fabsolutepath=map.get("fabsolutepath")==null?"":map.get("fabsolutepath").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new RadowException("请勾选信息！");
		}else if(countNum>1){
			throw new RadowException("只能勾选一条信息！");
		}
		this.createPageElement("fabsolutepath", ElementType.TEXTWITHICON, true).setValue(fabsolutepath);
		this.createPageElement("fname", ElementType.HIDDEN, true).setValue(fname);
		this.closeCueWindow("winFile");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(int start,int limit) throws RadowException {
		try {
			Connection conn = KingBSImpUtil.getConnection();			//获取 金仓数据库打开数据库连接
			Statement  stmt = conn.createStatement();					//定义 金仓数据库statement
			ResultSet  rs          = null;								//定义 金仓数据库结果集
			rs = stmt.executeQuery("select b0111,b0101 from dbo.b01 where b0111 <>'XXX' order by b0111 limit " + limit +" OFFSET " +start*limit);
			
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			Grid grid = (Grid)this.createPageElement("Fgrid", ElementType.GRID, false);
			if(rs!=null){
				while (rs.next()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("kbsdeptid", rs.getString(1));
					map.put("kbsdeptname", rs.getString(2));
					gridList.add(map);
				}
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
		} catch (Exception e) {
			throw new RadowException("检索目录失败:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
