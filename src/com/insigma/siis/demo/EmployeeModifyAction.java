package com.insigma.siis.demo;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.ICommand;
import com.insigma.odin.framework.persistence.TransactionManager;
import com.insigma.siis.demo.entity.Employee;

public class EmployeeModifyAction extends ActionSupport{
	public ActionForward modify(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			final EmployeeForm empForm=(EmployeeForm)actionForm;
			String aae074=(String)TransactionManager.trans(new ICommand(){
				public Object execute() throws AppException{
						return new EmployeeModifyVS().modify(empForm);
					} 
				}
			);
			this.doSuccess(request, "ok", aae074);
		}catch(AppException e){
			this.doError(request,e);
		}
		//return this.formResponse(request, response);
		return this.ajaxResponse(request, response);
	}
	 
	public ActionForward getEmployeeInfo(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String aac001=request.getParameter("aac001");
			String aac003=request.getParameter("aac003");
			//aac003=java.net.URLDecoder.decode(aac003,"utf-8");
//			Enumeration e=request.getHeaderNames();
//			while(e.hasMoreElements()){
//				System.out.println(e.nextElement()+" "+request.getHeader((String)e.nextElement()));
//			}
			EmployeeDTO empDTO=new EmployeeModifyVS().getEmployeeInfo(aac001);
			
			this.doSuccess(request, "ok", empDTO);
		}catch(AppException e){
			this.doError(request,e);
		}
		return this.ajaxResponse(request, response);
	}
}
