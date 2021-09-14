package com.insigma.siis.local.pagemodel.gzgl;

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

public class WpgzPageModel extends PageModel {
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
		StringBuilder sb = new StringBuilder("select a01.A0000, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, a01.A0221 as zw, a01.A0192E as zj, ")
				.append(" wm_concat(a02.A0201A) as rzjg, g.id, g.xpdw, g.gzlx, g.gzrw, g.qzsj, g.khqk, g.dybz, g.grzw,g.kssj,g.jssj ")
				.append(" ,nvl2(g.kssj,substr(g.kssj,0,4),'') nd,g.jsgz ")
				.append(" from a01 ")
				.append(" join a02 on a02.a0000 = a01.a0000 ")
				.append(" join wpgz g on g.a0000 = a01.a0000 ")
				.append(" where 1 = 1 ");
		String codeValue = this.getPageElement("checkedgroupid").getValue();
		// 判断单位是否选择
		if (StringUtils.isNotEmpty(codeValue)) {
			// 是否包含下级
			String isContain = this.getPageElement("isContain").getValue();
			if ("1".equals(isContain)) {
				sb.append(" and (a02.a0201b ='" + codeValue + "' or a02.a0201b like '" + codeValue + "%') ");
			} else {
				sb.append(" and a02.a0201b = '" + codeValue + "' ");
			}
		}
		sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E, g.id, g.xpdw, g.gzlx, g.gzrw, g.qzsj, g.khqk, g.dybz, g.grzw,g.kssj,g.jssj,g.jsgz ");
		sb.append(" order by a01.a0101 ");
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
				String id = item.get("id") == null ? "" : item.get("id").toString();
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

			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode(" $h.openWin('wpgzAdd', 'pages.gzgl.WpgzAdd', '外派挂职 ', 920, 650, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close',jgId:'"+id+"'})" );
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
			String sql = "delete from wpgz where id = ?";
			String sql2 = "delete from WPGZ_bz where gzid = ?";
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			try {
				ps = HBUtil.getHBSession().connection().prepareStatement(sql);
				ps2 = HBUtil.getHBSession().connection().prepareStatement(sql2);
				for (int i = 0; i < selected.size(); i++) {
					ps.setString(1, selected.get(i));
					ps.addBatch();
					ps2.setString(1, selected.get(i));
					ps2.addBatch();
				}
				ps.executeBatch();
				ps2.executeBatch();
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

	@PageEvent("OutExec")
	public int outExec() throws RadowException {
		StringBuilder checkIds = new StringBuilder();
		PageElement pe = this.getPageElement("editgrid");
		if (pe != null) {
			List<HashMap<String, Object>> list = pe.getValueList();
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("checked");
				//获取选中的id值
				if (usercheck.toString().equals("true")) {
					checkIds.append("'" + map.get("id") + "'").append(",");
				}
			}
		}
		if (checkIds.length() == 0) {
			this.setMainMessage("请勾选信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		checkIds.deleteCharAt(checkIds.length() - 1);
		HBSession sess = HBUtil.getHBSession();
		//String sql_person = "select * from jbgz where id in (" + checkIds.toString() + ")";
		StringBuilder sn=new StringBuilder("select a01.A0000,\n" +
				"       a01.A0101 as xm,\n" +
				"       a01.A0104 as xb,\n" +
				"       a01.A0107 as csny,\n" +
				"       (select code_name from code_value where code_type='ZB09' and code_value=a01.A0221) as zw,\n" +
				"       (select code_name from code_value where code_type='ZB148' and code_value=a01.A0192E) as zj,\n" +
				"to_char(nvl(wm_concat(a02.A0201A),'')) as rzjg,"+
				"       g.id,\n" +
				"       g.XPDW,\n" +
				"       g.GZLX,\n" +
				"       g.GZRW,\n" +
				"       g.QZSJ,\n" +
				"       g.KHQK,\n" +
				"       g.DYBZ,\n" +
				"       g.GRZW\n" +
				"  from a01\n" +
				"  join a02\n" +
				"    on a02.a0000 = a01.a0000\n" +
				"  join wpgz g\n" +
				"    on g.a0000 = a01.a0000 \n" +
				" where 1 = 1\n ");
		sn.append(" and g.id in ("+ checkIds.toString() +")");
		sn.append(" group by a01.a0000,\n" +
				"          a01.A0101,\n" +
				"          a01.A0104,\n" +
				"          a01.A0107,\n" +
				"          a01.A0221,\n" +
				"          a01.A0192E,\n" +
				"          g.id,\n" +
				"       g.XPDW,\n" +
				"       g.GZLX,\n" +
				"       g.GZRW,\n" +
				"       g.QZSJ,\n" +
				"       g.KHQK,\n" +
				"       g.DYBZ,\n" +
				"       g.GRZW,g.kssj" +
                " order by g.kssj ");


		List<Map<String, Object>> list_person = sess.createSQLQuery(sn.toString()).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		String path = Exec.wpgz_execl(list_person);
		this.getExecuteSG().addExecuteCode("outExcel('" + path + "')");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("tpbj.onclick")
    public int tpbj() throws RadowException {
    	Grid grid = (Grid) this.getPageElement("editgrid");
        List<HashMap<String, Object>> list = grid.getValueList();
        LinkedHashSet<String> selected = new LinkedHashSet<String>();
     // 从cookie中的获取之前选择的人员id
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("jggl.tpbj.ids".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    String[] ids = cookieValue.split("#");
                    for (String id : ids) {
                    	if(!"".equals(id)){
                        	selected.add(id);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> item = list.get(i);
            if (item.get("checked").equals(true)) {
                String id = item.get("a0000") == null ? "" : item.get("a0000").toString();
                if(!"".equals(id)){
                	selected.add(id);
                }
                
            }
        }
        
        if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
            this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
            return EventRtnType.FAILD;
        } else {
            String json = JSON.toJSONString(selected);
            this.getExecuteSG().addExecuteCode("$h.openWin('tpbjWindow','pages.customquery.GbglTpbj','同屏比较',1500,731,null,ctxPath,null,{" + "maximizable:false,resizable:false,RMRY:'同屏比较',data:" + json + "})");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	
	
	
}
