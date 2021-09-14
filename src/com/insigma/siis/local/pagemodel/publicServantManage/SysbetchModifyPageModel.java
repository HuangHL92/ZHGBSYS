package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class SysbetchModifyPageModel extends PageModel {

	/**
	 * 私有属性 ids 父页传过来的数据
	 */
	public static int type = 0;
	private static String ids = null;
	private LogUtil applog = new LogUtil();

	public SysbetchModifyPageModel() {

	}

	// 页面初始化
	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	//单位基本信息
	@PageEvent("save7.onclick")
	@Transaction
	@Synchronous(true)
	public int save7() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		int count = 0;
		String ids = this.request.getSession().getAttribute("orgids").toString();
		String orgids = ids.replace("|", "'").replace("@", ",");
		String[] b0111s = orgids.split(",");
		try {
			String b0124 = this.getPageElement("b0124").getValue();
			String b0127 = this.getPageElement("b0127").getValue();
			String b0131 = this.getPageElement("b0131").getValue();
			String b0194 = this.getPageElement("b0194").getValue();
			if (!StringUtil.isEmpty(b0124) || !StringUtil.isEmpty(b0127)
					|| !StringUtil.isEmpty(b0131)
					|| !StringUtil.isEmpty(b0194) ) {
				if (b0111s.length > 0) {
					StringBuffer sb = new StringBuffer();
					Map< String, String> map = new LinkedHashMap();
					if(!StringUtil.isEmpty(b0124)){
						map.put("b0124", b0124);
						sb = sb.append("b0124 = ?,");
					}
					if(!StringUtil.isEmpty(b0127)){
						map.put("b0127", b0127);
						sb = sb.append("b0127 = ?,");
					}
					if(!StringUtil.isEmpty(b0131)){
						map.put("b0131", b0131);
						sb = sb.append("b0131 = ?,");
					}
					if(!StringUtil.isEmpty(b0194)){
						map.put("b0194", b0194);
						sb = sb.append("b0194 = ?,");
					}
					String sql1 = sb.substring(0,sb.length()-1);
					PreparedStatement ps = conn
							.prepareStatement("UPDATE b01 set "+sql1+" where b0111 = ?");

					for (int i = 0; i < b0111s.length; i++) {
						int j = 1;
						for(String key : map.keySet()){
							ps.setString(j, map.get(key));
							j++;
						}
						ps.setString(j, b0111s[i]);
						ps.addBatch();
						count++;
						if (count >= 500) {
							ps.executeBatch();
							ps.clearParameters();
//							conn.commit();
							count = 0;
						}
					}
					if (count > 0) {
						ps.executeBatch();
//						conn.commit();
						count = 0;
					}
					if (ps != null) {
						ps.close();
					}
					// 记录日志
					List list = new ArrayList();

					if (!StringUtil.isEmpty(b0124)) {
						String[] arr = { "B0124", "", "", "单位隶属关系" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(b0127)) {
						String[] arr = { "B0127", "", "", "单位级别" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(b0131)) {
						String[] arr = { "B0131", "", "", "单位性质类别" };
						list.add(arr);
					}
					if (!StringUtil.isEmpty(b0194)) {
						String[] arr = { "B0194", "", "", "法人单位标识" };
						list.add(arr);
					}
					applog.createLog("3706", "B01", "", "", "机构基本信息批量修改", list);
				}

			} else {
				this.setMainMessage("没有要修改的项");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！异常信息：" + e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功");
		this.getExecuteSG()
		.addExecuteCode(
				"window.realParent.Ext.getCmp('memberGrid').getStore().reload()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
