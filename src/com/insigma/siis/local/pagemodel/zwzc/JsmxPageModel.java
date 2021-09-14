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
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import org.hibernate.criterion.CriteriaSpecification;

public class JsmxPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("editgrid.dogridquery");
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
		StringBuilder sb = new StringBuilder("select t.*,t.srzw || '【' || t.a0192a || '】' xrzw from jsmx t where 1=1 ");
		String b0111 = this.getPageElement("checkedgroupid").getValue();
		// 判断单位是否选择
		if (StringUtils.isNotEmpty(b0111) && "0".equals(searching)) {
			// 是否包含下级
			String isContain = this.getPageElement("isContain").getValue();
			if ("1".equals(isContain)) {
				sb.append(" and t.a0000 in (select a02.a0000 from a02 " + 
						" where a02.a0201b like '%"+b0111+"%') ");
			} else {
				sb.append(" and t.a0000 in (select a02.a0000 from a02 " + 
						" where a02.a0201b ='"+b0111+"') ");
			}
		}
		if("1".equals(searching)) {
			String a0101=this.getPageElement("a0101").getValue();
			String a0107=this.getPageElement("a0107").getValue();
			String a0192a=this.getPageElement("a0192a").getValue();
			String a0192f=this.getPageElement("a0192f").getValue();
			if(a0101.length()>0) {
				sb.append(" and t.a0101 like '%"+a0101+"%' ");
			}
			if(a0107.length()>0) {
				sb.append(" and t.a0107 like '%"+a0107+"%'" );
			}
			if(a0192a.length()>0) {
				sb.append(" and t.a0192a like '%"+a0192a+"%'" );
			}
			if(a0192f.length()>0) {
				sb.append(" and t.a0192f like '%"+a0192f+"%' " );
			}
		}
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
				String js00 = item.get("js00") == null ? "" : item.get("js00").toString();
				selected.add(js00);
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
//	@PageEvent("editgrid.rowdbclick")
//	@GridDataRange
//	public int OnRowDbClick() throws RadowException, AppException { // 打开窗口的实例
//		String js00 = this.getPageElement("editgrid").getValue("js00", this.getPageElement("editgrid").getCueRowIndex()).toString();
//
//		if (js00 != null) {
//
//			this.request.getSession().setAttribute("personIdSet", null);
//
//			this.request.getSession().setAttribute("personIdSet", null);
//			this.getExecuteSG().addExecuteCode(" $h.openWin('JsmxAdd', 'pages.zwzc.JsmxAdd', '纪实明细详情 ', 1200, 800, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close',js00:'"+js00+"'})" );
//			//this.getExecuteSG().addExecuteCode("$h.openWin('cjqkupdate','pages.gbjd.cjqkupdate','惩戒情况',750,405,ctxPath,null,{maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"'})");
//
//			// initA0000Map(a0000);
//			// this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
//			// this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
//			return EventRtnType.NORMAL_SUCCESS;
//		} else {
//			throw new AppException("请选择一条记录！");
//
//		}
//	}
	@Transaction
	@PageEvent("doDeleteGbjy")
	public int doDeleteGbjy() throws RadowException {
		Grid grid = (Grid) this.getPageElement("editgrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<String> selected = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> item = list.get(i);
			if (item.get("checked").equals(true)) {
				String js00 = item.get("js00") == null ? "" : item.get("js00").toString();
				selected.add(js00);
			}
		}
		if (selected.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.FAILD;
		} else {
			String sql = "delete from jsmx where js00 = ?";
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
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

	
	@PageEvent("editgrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("editgrid");
		String js00 = grid6.getValue("js00") +"";
		String a0107 = grid6.getValue("a0107") +"";
		String srzw = grid6.getValue("srzw") +"";
		String a0192a = grid6.getValue("a0192a") +"";
		String a0192f = grid6.getValue("a0192f") +"";
		String js01 = grid6.getValue("js01") +"";
		String js02a = grid6.getValue("js02a") +"";
		String js02b = grid6.getValue("js02b") +"";
		String js02c = grid6.getValue("js02c") +"";
		String js03a = grid6.getValue("js03a") +"";
		String js03b = grid6.getValue("js03b") +"";
		String js03c = grid6.getValue("js03c") +"";
		String js04a = grid6.getValue("js04a") +"";
		String js04b = grid6.getValue("js04b") +"";
		String js05a = grid6.getValue("js05a") +"";
		String js05b = grid6.getValue("js05b") +"";
		String js05c = grid6.getValue("js05c") +"";
		String js06a = grid6.getValue("js06a") +"";
		String js06b = grid6.getValue("js06b") +"";
		String js07 = grid6.getValue("js07") +"";
		String js08 = grid6.getValue("js08") +"";
		String js09a = grid6.getValue("js09a") +"";
		String js09b = grid6.getValue("js09b") +"";
		String js10 = grid6.getValue("js10") +"";
		String js11 = grid6.getValue("js11") +"";
		String js12 = grid6.getValue("js12") +"";
		String js13 = grid6.getValue("js13") +"";
		String js14 = grid6.getValue("js14") +"";
		String js15 = grid6.getValue("js15") +"";
		String js16 = grid6.getValue("js16") +"";
		String js17 = grid6.getValue("js17") +"";
		String js18 = grid6.getValue("js18") +"";
		String js19a = grid6.getValue("js19a") +"";
		String js19b = grid6.getValue("js19b") +"";
		String js20 = grid6.getValue("js20") +"";
		String js21 = grid6.getValue("js21") +"";
		String js22 = grid6.getValue("js22") +"";
		HBSession sess = HBUtil.getHBSession();
		
		sess.createSQLQuery("update jsmx set a0107=?,srzw=?,a0192a =?,a0192f =?,js01 =?,js02a =?,js02b =?,js02c =?,js03a =?,\r\n" + 
				"js03b =?,js03c =?,js04a =?,js04b =?,js05a =?,js05b =?,js05c =?,js06a =?,js06b =?,js07 =?,js08 =?,\r\n" + 
				"js09a =?,js09b =?,js10 =?,js11 =?,js12 =?,js13 =?,js14 =?,js15 =?,js16 =?,js17 =?,js18 =?,js19a =?,\r\n" + 
				"js19b =?,js20 =?,js21 =?,js22 =? where js00=?")
		.setString(0, a0107).setString(1, srzw).setString(2, a0192a).setString(3, a0192f).setString(4, js01).setString(5, js02a).setString(6, js02b).setString(7, js02c)
		.setString(8, js03a).setString(9, js03b).setString(10, js03c).setString(11, js04a).setString(12, js04b).setString(13, js05a).setString(14, js05b).setString(15, js05c)
		.setString(16, js06a).setString(17, js06b).setString(18, js07).setString(19, js08).setString(20, js09a).setString(21, js09b).setString(22, js10).setString(23, js11)
		.setString(24, js12).setString(25, js13).setString(26, js14).setString(27, js15).setString(28, js16).setString(29, js17).setString(30, js18).setString(31, js19a)
		.setString(32, js19b).setString(33, js20).setString(34, js21).setString(35, js22).setString(36, js00).executeUpdate();
		sess.flush();
		//this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
