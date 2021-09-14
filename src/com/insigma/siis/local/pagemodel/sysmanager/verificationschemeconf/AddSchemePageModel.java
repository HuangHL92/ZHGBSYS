package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.sql.SQLException;
import java.util.Date;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifySchemeDTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddSchemePageModel extends PageModel {

	@PageEvent("btnClose.onclick")
	@NoRequiredValidate
	public int btn1Onclick() {
		this.closeCueWindow("addSchemeWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	@Transaction
	public int btnSaveOnclick() throws AppException, RadowException {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		String vsc001 = this.getPageElement("vsc001").getValue();// 主键
		try {
			Object groupid = sess.createSQLQuery(
					"SELECT groupid from Smt_Usergroupref WHERE userid = '"
							+ user.getId() + "'").uniqueResult();
			//1. 主键为空，新增
			if (StringUtil.isEmpty(vsc001)) {
				VerifyScheme vs = new VerifyScheme();
				this.copyElementsValueToObj(vs, this);
				vs.setVsc003("0");				// 1-有效（启用）；0-无效（未启用）
				vs.setVsc004(groupid != null ? groupid.toString() : "");// 创建人所属机构编码
				vs.setVsc005(user.getId());		// 创建人ID
				vs.setVsc006(new Date());		// 创建时间
				vs.setVsc007("0");				// 默认基础方案标识
				vs.setVsc008("0");				// 导入标记0-不是
				vs.setVsc010("");				// 方案导出人员ID
				vs.setVsc011("");				// 方案导出人员所在机构
				sess.save(vs);
				vsc001 = vs.getVsc001();
				
				//记录日志
				try {
					if(vs!=null){
						new LogUtil().createLog("632", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null, null);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				

			} else {
			//2. 修改
				VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class,
						vsc001);
				VerifySchemeDTO verifySchemeDTO = new VerifySchemeDTO();
				BeanUtil.copy(vs, verifySchemeDTO);
				this.copyElementsValueToObj(vs, this);
				sess.update(vs);
				
				//记录日志
				try {
					if(vs!=null){
						new LogUtil().createLog("633", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null, new Map2Temp().getLogInfo(verifySchemeDTO, vs));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
			}
			sess.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new AppException("校验方案处理出错：" + e1.getMessage());
		}
//		this.createPageElement("VeriySchemeGrid", "grid", true).reload();
		this.closeCueWindow("addSchemeWin");
		this.getExecuteSG().addExecuteCode(
				"parent.radow.doEvent('success','" + vsc001 + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		String userid = SysUtil.getCacheCurrentUser().getUserVO()
				.getLoginname();
		try {
			if (userid == null || userid.equals("")) {
				throw new AppException("没有用户信息！");
			}
			String id = this.getRadow_parent_data();
			if (id != null && !id.equals("")) {
				VerifyScheme vs = (VerifyScheme) HBUtil.getHBSession().get(
						VerifyScheme.class, id);
				copyObjValueToElement(vs, this);
			}

			// TODO 只允许admin用户修改【是否默认校验方案】
			if (!userid.equalsIgnoreCase("admin")) {
				this.getPageElement("vsc007").setDisabled(true);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
