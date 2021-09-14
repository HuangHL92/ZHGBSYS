package com.insigma.siis.local.pagemodel.zwzc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.alibaba.fastjson.JSON;
import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import org.hibernate.criterion.CriteriaSpecification;

public class BZYPGBPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 挂职管理查询
	 * 
	 */
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate
	public int editGridQuery(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String searching=this.getPageElement("searching").getValue();
		StringBuilder sb = new StringBuilder("select distinct * from (select d.*,t.a0117,t.a0104,t.a0107,t.a0192a,t.a0192e from A01 t,BZYPGB d where t.a0000=d.a0000 ");
		String codeValue = this.getPageElement("checkedgroupid").getValue();
		// 判断单位是否选择
		if (StringUtils.isNotEmpty(codeValue)) {
			// 是否包含下级
			//String isContain = this.getPageElement("isContain").getValue();
			//if ("1".equals(isContain)) {
				//sb.append(" and (a02.a0201b ='" + codeValue + "' or a02.a0201b like '" + codeValue + "%') ");
			//} else {
				sb.append(" and d.b0111 = '" + codeValue + "' ");
			//}
		}
		if("1".equals(searching)) {
			String a0101=this.getPageElement("a0101").getValue();
			String tbfzsj=this.getPageElement("tbfzsj").getValue();
			String tbzzsj=this.getPageElement("tbzzsj").getValue();
			if(a0101.length()>0) {
				sb.append(" and t.a0101 like '%"+a0101+"%' ");
			}
			if(tbfzsj.length()>0) {
				sb.append(" and d.tbfzsj='"+tbfzsj+"' " );
			}
			if(tbzzsj.length()>0) {
				sb.append(" and d.tbzzsj='"+tbzzsj+"' " );
			}
		}
		sb.append("order by (((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  g where rn=1 and g.a0000=d.a0000))))");
		this.pageQuery(sb.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("gbjyDelete.onclick")
	public int gbjyDelete() throws RadowException {
		
		Grid grid = (Grid) this.getPageElement("editgrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<String> selected = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> item = list.get(i);
			if (item.get("checked").equals(true)) {
				String id = item.get("a0000") == null ? "" : item.get("a0000").toString();
				selected.add(id);
			}
		}
		if (selected.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setMessageType(EventMessageType.CONFIRM);
		this.setMainMessage("确定删除所选中的数据吗？");

		NextEvent yes = new NextEvent();
		yes.setNextEventValue(NextEventValue.YES);
		yes.setNextEventName("doDeleteGbjy");
		this.addNextEvent(yes);
		NextEvent cannel = new NextEvent();
		cannel.setNextEventValue(NextEventValue.CANNEL);
		this.addNextEvent(cannel);

		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("editgrid.rowdbclick")
	@GridDataRange
	public int OnRowDbClick() throws RadowException, AppException { // 打开窗口的实例
		String id = this.getPageElement("editgrid").getValue("id", this.getPageElement("editgrid").getCueRowIndex()).toString();
		if (id != null) {

			this.request.getSession().setAttribute("personIdSet", null);

			this.getExecuteSG().addExecuteCode(" $h.openWin('BZYPGBAdd', 'pages.zwzc.BZYPGBAdd', '代表委员详情 ', 750, 250, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close',jgId:'"+id+"'})" );
			//this.getExecuteSG().addExecuteCode("$h.openWin('cjqkupdate','pages.gbjd.cjqkupdate','惩戒情况',750,405,ctxPath,null,{maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"'})");

			// initA0000Map(a0000);
			// this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			// this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			throw new AppException("请选择一条记录！");

		}
	}
	@Transaction
	@PageEvent("doDeleteGbjy")
	public int doDeleteGbjy() throws RadowException {
		Grid grid = (Grid) this.getPageElement("editgrid");
		String b0111 = (String)this.getPageElement("editgrid").getValueList().get(this.getPageElement("editgrid").getCueRowIndex()).get("b0111");
		String tbrs = (String)this.getPageElement("editgrid").getValueList().get(this.getPageElement("editgrid").getCueRowIndex()).get("tbrs");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<String> selected = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> item = list.get(i);
			if (item.get("checked").equals(true)) {
				String id = item.get("id") == null ? "" : item.get("id").toString();
				selected.add(id);
			}
		}
		if (selected.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.FAILD;
		} else {
			String sql = "delete from BZYPGB where id = ?";
			
			try {
				if(Integer.parseInt(tbrs)>=1) {
					String tbrs1=String.valueOf(Integer.parseInt(tbrs)-1);
					
					HBUtil.executeUpdate("update BZYPGB set tbrs=? where b0111=?",
						new Object[]{tbrs1,b0111});
				}else {
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PreparedStatement ps = null;
			try {
				
				ps = HBUtil.getHBSession().connection().prepareStatement(sql);
				for (int i = 0; i < selected.size(); i++) {
					ps.setString(1, selected.get(i));
					ps.addBatch();
				}
				ps.executeBatch();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','删除成功！',null,150);");
				grid.reload();
			} catch (SQLException e) {
				e.printStackTrace();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','删除失败！',null,150);");
				return EventRtnType.FAILD;
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	
	
	
	
}
