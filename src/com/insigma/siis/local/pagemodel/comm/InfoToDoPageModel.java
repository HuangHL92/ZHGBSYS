package com.insigma.siis.local.pagemodel.comm;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;

import com.insigma.odin.framework.RSACoder;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Infotdrecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InfoToDoPageModel extends PageModel{
	/**
	 * 初始化
	 */
	@Override
	public int doInit() throws RadowException {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public static void saveToDoInfo(String type, String resBusiId, String resBusiname,
			String dealDeptId, String dealUserId, String tBusiId, String tBusiname,
			String remarks) {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		Session sess = null; 
		try {
			sess = HBUtil.getHBSessionFactory().openSession();
			Infotdrecord info = new Infotdrecord();
			info.setItdr001(user.getId());
			info.setItdr002(user.getName());
			info.setItdr003(DateUtil.getTimestamp());
			info.setItdr004(type);
			info.setItdr005(resBusiId);
			info.setItdr006(resBusiname);
			info.setItdr007(dealDeptId);
			info.setItdr008("");
			info.setItdr009(dealUserId);
			info.setItdr010("");
			info.setItdr011(remarks);
			
			info.setItdr013(tBusiId);
			info.setItdr014(tBusiname);
			sess.save(info);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sess.close();
		}
	}
	
	@PageEvent("send.onclick")
	@NoRequiredValidate
	public int word()throws RadowException{
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		try {
			String itdr008 = this.getPageElement("itdr007_combo").getValue();
			Infotdrecord info = new Infotdrecord();
			this.copyElementsValueToObj(info, this);
			info.setItdr001(user.getId());
			info.setItdr002(user.getName());
			info.setItdr003(DateUtil.getTimestamp());
			info.setItdr008(itdr008);
			sess.save(info);
			sess.flush();
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '发送成功！', function(e){window.close();});");;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//点击树查询事件
	@PageEvent("getDBmsgs")
	@NoRequiredValidate
	@AutoNoMask
	public int getDBmsgs(String id) throws RadowException {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String deptid = user.getDept();
		try {
			//String sql = "select * from (select itdc002,itdc003,itdc004,itdc005,itdc008,ITDR013,ITDR014"
			String sql = "select * from (select itdc002,itdc003,ITDR000,to_char(ITDR003,'yyyy\"年\"mm\"月\"dd\"日\"') rq,"
					+ "to_char(ITDR003,'day') xq from infotodorecord r,infotodocfg c where r.ITDR004=c.itdc001 "
					+ " and ITDR007='"+deptid+"' order by ITDR003 desc) where rownum <10";
					//+ " order by ITDR003 desc) where rownum <10";
			List<HashMap> list = CommonQueryBS.getQueryInfoByManulSQL(sql);
			this.setSelfDefResData(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.XML_SUCCESS;
	}
	
	//点击树查询事件
	@PageEvent("getDbById")
	@NoRequiredValidate
	@AutoNoMask
	public int getDbById(String id) throws RadowException {
		String rid = this.request.getParameter("rid");
		try {
			String sql = " select itdc002,itdc003,itdc004,itdc005,itdc008,ITDR013,ITDR014"
					+ " from infotodorecord r,infotodocfg c where r.ITDR004=c.itdc001 "
					+ " and r.ITDR000='"+rid+"' ";
			List<HashMap> list = CommonQueryBS.getQueryInfoByManulSQL(sql);
			this.setSelfDefResData(JSONObject.fromObject(list.get(0)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.XML_SUCCESS;
	}
	
}
