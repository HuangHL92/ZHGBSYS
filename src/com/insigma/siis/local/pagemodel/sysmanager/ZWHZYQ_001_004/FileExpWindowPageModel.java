package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004;

import java.io.*;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;  

import org.dom4j.*;  
import org.dom4j.io.*;
import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.HashMap;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.LogDetail;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;

public class FileExpWindowPageModel extends PageModel {
	public static int isfinish = 0;
	public static Thread currentThread = null;
	public static int cur = 0;
	public static long currentThreadID = -1;
	public static int total = 0;
	public static int flag = 0;//用于分辨是点击的查询按钮，还是点击的用户组树
	
	@Override
	public int doInit() throws RadowException{
		if(isfinish==1){
			this.getExecuteSG().addExecuteCode("doprocess();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("isfinish")
	@AutoNoMask
	public int isfinish() throws RadowException{ 
		if(isfinish==1){
			this.getExecuteSG().addExecuteCode("updateProgress('"+cur+"','"+total+"')");
		}else{
			this.getExecuteSG().addExecuteCode("Ext.Msg.hide();clearInterval(isfinishE);" +
					"window.location.reload();");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	@PageEvent("rebackup.onclick")
	@AutoNoMask
	public int rebackup() throws RadowException{ 
		final String username = this.getPageElement("username").getValue();
		final String type = this.getPageElement("type").getValue();
		final String object = this.getPageElement("object").getValue();
		final String info = this.getPageElement("info").getValue();
		final String start = this.getPageElement("start").getValue();
		final String end = this.getPageElement("end").getValue();
		final String path = this.getPageElement("path").getValue();
		
		
		new Thread(){

			@Override
			public void run() {
				try {
					new FileDownServlet().rebackup(username,type,object,info,start,end,path);
				} catch (Exception e) {
					isfinish = 0;
				}
				
				super.run();
			}
			
		}.start();
		
		this.getExecuteSG().addExecuteCode("doprocess();");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
}