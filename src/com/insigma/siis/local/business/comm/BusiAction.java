package com.insigma.siis.local.business.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;

public class BusiAction extends ActionSupport {

	public ActionForward zhgbrmb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String a0000 = request.getParameter("a0000");
		request.getRequestDispatcher("rmb/ZHGBrmb.jsp?FromModules=1&a0000="+a0000).forward(request, response);
		return null;
	}

}
