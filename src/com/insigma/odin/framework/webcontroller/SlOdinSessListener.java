package com.insigma.odin.framework.webcontroller;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.safe.IValidateContExecute;
import com.insigma.odin.framework.safe.SafeControlCenter;
import com.insigma.odin.framework.safe.util.SafeConst;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.LogHelper;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

public class SlOdinSessListener implements HttpSessionListener {
	private static Logger _$3 = Logger.getLogger(SlOdinSessListener.class);
	private IValidateContExecute _$2 = SafeControlCenter.getInstance()
			.getIValidateContExecute();
	private SafeControlCenter _$1 = null;

	public void sessionCreated(HttpSessionEvent paramHttpSessionEvent) {
		this._$2.setSessCount(this._$2.getSessCount().intValue() + 1);
		_$3.info(this._$2.getSessCount());
		this._$1 = SafeControlCenter.getInstance();
		this._$1.setCueProduct(SafeConst.PDT_INSIIS);
		this._$1.safeValidate(SafeConst.VT_SESSIONS,
				SafeConst.PDT_INSIIS_COMP_ODIN);
	}

	public void sessionDestroyed(HttpSessionEvent paramHttpSessionEvent) {
		/*String sessionToken = (String) paramHttpSessionEvent.getSession().getAttribute("token");
		if( sessionToken != null ){
			try {
				HBUtil.executeUpdate("delete from USER_AUTHENTICATION where token=?", new Object[]{sessionToken});
			} catch (Exception e) {
			}
		}*/
		//删除查询表结果。
		//new CustomQueryPageModel().deleteResult(paramHttpSessionEvent.getSession().getId());
		try {
			HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='"+paramHttpSessionEvent.getSession().getId()+"'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this._$2.setSessCount(this._$2.getSessCount().intValue() - 1);
		LogHelper.getLogger(SlOdinSessListener.class).doLogoffLog(
				(CurrentUser) paramHttpSessionEvent.getSession().getAttribute(
						GlobalNames.CURRENT_USER));
		_$3.info(this._$2.getSessCount());
	}
}
