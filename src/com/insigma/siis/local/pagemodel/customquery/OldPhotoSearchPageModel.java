package com.insigma.siis.local.pagemodel.customquery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class OldPhotoSearchPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {   
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException, UnsupportedEncodingException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		CommQuery cq = new CommQuery();
		String a57his = "select * from a57_history where a0000='"+a0000+"' order by photodate";
		List<HashMap<String,Object>> a57hisList = cq.getListBySQL(a57his);
		if(a57hisList.isEmpty()||a57hisList==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("Num").setValue(a57hisList.size()+"");
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		for(int i=0;i<a57hisList.size();i++){
			String photodate = a57hisList.get(i).get("photodate")==null?"":a57hisList.get(i).get("photodate").toString();
			String photoname = a57hisList.get(i).get("photoname")==null?"":a57hisList.get(i).get("photoname").toString();
			String photopath = a57hisList.get(i).get("photopath")==null?"":a57hisList.get(i).get("photopath").toString();
			String realPath = PhotosUtil.PHOTO_PATH+photopath+photoname;
		
			sb.append("<li id=\"sjjg_"+i+"\" style=\"width:25%;\" class=\"buttonzdyhover\">");
			sb.append("<a>");
			sb.append("<div style=\"text-align:center;\">");
			sb.append("<img style=\"width:98%;height:200xp;\" src=\""+request.getContextPath()+"/servlet/DownloadUserHeadImage?path="+URLEncoder.encode(URLEncoder.encode(realPath,"UTF-8"),"UTF-8")+"\" />");
			sb.append("<p>"+photodate+"</p>");
			sb.append("</div>");
			sb.append("</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");
		this.getExecuteSG().addExecuteCode("$('#photos').html('')");
		this.getExecuteSG().addExecuteCode("$('#photos').append('"+sb.toString()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
