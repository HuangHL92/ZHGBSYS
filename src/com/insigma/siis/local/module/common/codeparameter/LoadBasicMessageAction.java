/*-----------------------------------------------/
 *Generated By ODA Version 1.0.5 Alpha
 *Generated On 2008-05-20 09:02:40
 *Action Path: /com/insigma/siis/local/module/common/codeparameter/LoadBasicMessageAction
 *Location : /com/insigma/siis/local/module/common/codeparameter/LoadBasicMessageAction.do?method=loadBasicMessage
 *VS Class : CodeParameterVS
 *Method Called : loadBasicMessage
 *Transaction Mode: None
 *Action Mode: Ajax Normal Mode
 *Method Called : loadBasicMessage
 *Module ID : 9AB0A896245CD2CD8622A8D56703AB6B
 *----------------------------------------------*/
package com.insigma.siis.local.module.common.codeparameter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;

public class LoadBasicMessageAction extends ActionSupport {
	public ActionForward loadBasicMessage(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String jsonstr = request.getParameter("_ODA_TRANSMIT_OBJECT");
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonstr);
			Object ret = new CodeParameterVS().loadBasicMessage();
			this.doSuccess(request, "ok", ret);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
}