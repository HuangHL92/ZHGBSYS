package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.module.common.codeparameter.CodeParameterVS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataVerifyAction extends ActionSupport{
	private File file;
	private String fileName;
	private String fileType;
	public ActionForward addParameter(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String jsonstr = request.getParameter("_ODA_TRANSMIT_OBJECT");
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonstr);
			String aaa100 = jsonObject.getString("aaa100");
			String aaa101 = jsonObject.getString("aaa101");
			new CodeParameterVS().addParameter(aaa100, aaa101);
			Object ret = null;
			this.doSuccess(request, "ok", ret);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
	
	public ActionForward doCheck(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonQueryBS.systemOut("1");
		try {
			String path = request.getParameter("path");
			String isPerson = request.getParameter("isPerson");
			String filename = path.substring((path.lastIndexOf("\\") + 1) , path.lastIndexOf("."));
			String houzhui = path.substring(path.lastIndexOf(".")+1, path.length());
			Map map = new HashMap();
			if(isPerson!=null && isPerson.equals("1")){
				if(houzhui.equalsIgnoreCase("hzb")){
					map.put("valueimp", "");
					map.put("orginfo", "");
				} else {
					map.put("valueimp", "3");
					map.put("orginfo", "只支持HZB格式文件，请重新选择。");
				}
			} else {
				if(houzhui.equalsIgnoreCase("zb3") || houzhui.equalsIgnoreCase("hzb")){
					map.put("valueimp", "");
					map.put("orginfo", "");
				} else {
					map.put("valueimp", "3");
					map.put("orginfo", "不是ZB3或HZB格式请重新选择文件。");
				}
			}
			
			this.doSuccess(request, "ok", map);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
	
	public ActionForward doCheck2(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonQueryBS.systemOut("1");
		try {
			String path = request.getParameter("path");
			String filename = path.substring((path.lastIndexOf("\\") + 1) , path.lastIndexOf("."));
			String houzhui = path.substring(path.lastIndexOf(".")+1, path.length());
			Map map = new HashMap();
			
			if(houzhui.equalsIgnoreCase("7z")||houzhui.equalsIgnoreCase("zip")){
				map.put("valueimp", "");
				map.put("orginfo", "");
			} else {
				map.put("valueimp", "3");
				map.put("orginfo", "不是7Z、ZIP格式请重新选择文件。");
			}
			
			this.doSuccess(request, "ok", map);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}

	public ActionForward doCheck3(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonQueryBS.systemOut("1");
		try {
			String path = request.getParameter("path");
			String isPerson = request.getParameter("isPerson");
			String filename = path.substring((path.lastIndexOf("\\") + 1) , path.lastIndexOf("."));
			String houzhui = path.substring(path.lastIndexOf(".")+1, path.length());
			Map map = new HashMap();
			if(isPerson!=null && isPerson.equals("1")){
				if(houzhui.equalsIgnoreCase("hzb") || houzhui.equalsIgnoreCase("7z") || houzhui.equalsIgnoreCase("zip")){
					map.put("valueimp", "");
					map.put("orginfo", "");
				} else {
					map.put("valueimp", "3");
					map.put("orginfo", "只支持HZB、7z、ZIP格式文件，请重新选择。");
				}
			} 
			
			this.doSuccess(request, "ok", map);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
