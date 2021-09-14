package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class PublishCitePageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String publishid = this.getPageElement("publishid").getValue();
		String meetingid = this.getPageElement("meetingid").getValue();
		try {
			if(publishid!=null&&!"".equals(publishid)){
				HBSession sess = HBUtil.getHBSession();
				Publish p = (Publish)sess.get(Publish.class, publishid);
				PMPropertyCopyUtil.copyObjValueToElement(p, this);
			}
			String sql = "select p.meetingid,p.meetingname from MEETINGTHEME p where meetingid<>'"+meetingid+"' order by time desc";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("meetingid").toString();
				mapCode.put(tp0116, listCode.get(i).get("meetingname").toString());
			}
			((Combo)this.getPageElement("xmeetingname")).setValueListForSelect(mapCode);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
//		String sql="select a.agendaname,b.meetingname from publish a,meetingtheme b where a.meetingid=b.meetingid and a.publishid='"+publishid+"'";
//		CommQuery cqbs=new CommQuery();
//		try {
//			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
//			this.getPageElement("agendaname").setValue(list.get(0).get("agendaname").toString());
//			this.getPageElement("meetingname").setValue(list.get(0).get("meetingname").toString());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解压路径
        return upload_file;
    }
	
	public final static String disk = getPath();
	
	/**
	 * 保存
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String meetingid = this.getPageElement("meetingid").getValue();
		String publishid = this.getPageElement("publishid").getValue();
		String xmeetingname = this.getPageElement("xmeetingname").getValue();
		try {
			String sql="select 1 from publishcite where meetingid_old='"+meetingid+"' and meetingid_new='"+xmeetingname+"' and publishid='"+publishid+"' ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list==null||list.size()==0) {
				HBSession sess = HBUtil.getHBSession();
				Statement stmt = sess.connection().createStatement();
				sql="insert into publishcite (meetingid_old,meetingid_new,publishid) values ('"+meetingid+"','"+xmeetingname+"','"+publishid+"')";
				stmt.executeUpdate(sql);
				stmt.close();
			}
			
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public String isnull (Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;	
	}
	
}
