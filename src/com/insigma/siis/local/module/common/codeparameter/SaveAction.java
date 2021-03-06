/*-----------------------------------------------/
 *Generated By ODA Version 1.0.5 Alpha
 *Generated On 2008-05-21 09:23:59
 *Action Path: /com/insigma/siis/local/module/common/codeparameter/SaveAction
 *Location : /com/insigma/siis/local/module/common/codeparameter/SaveAction.do?method=save
 *VS Class : CodeParameterVS
 *Method Called : save
 *Transaction Mode: OPEN
 *Action Mode: Ajax Form Mode
 *Method Called : save
 *Module ID : 9AB0A896245CD2CD8622A8D56703AB6B
 *----------------------------------------------*/
package com.insigma.siis.local.module.common.codeparameter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.BeanUtil;

public class SaveAction extends ActionSupport {
	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = null;
		try {
			CodeParameterFormDTO codeParameterFormDTO = new CodeParameterFormDTO();
			BeanUtil.copyFormBeanToDTO(actionForm, codeParameterFormDTO);
			trans = sess.beginTransaction();
			new CodeParameterVS().save(codeParameterFormDTO);
			this.doSuccess(request, "ok", null);
			trans.commit();
		} catch (Exception e) {
			if (null != trans)
				trans.rollback();
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
}