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

public class FileImpWindowPageModel extends PageModel {
	@PageEvent("Imp")
	public int impBtnOnClick(String file) throws RadowException, ParseException{
		File f = new File(file);
		if(f.exists()){
			this.setMainMessage("正在导入，请稍后。。。");
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<LogMain> logmain = new ArrayList<LogMain>();
				List<LogDetail> detail = new ArrayList<LogDetail>();
				HashMap map= new HashMap(); 
				List list = new ArrayList();
				SAXReader reader = new SAXReader(); 
				Document doc = reader.read(f);
				Element root = doc.getRootElement();
				for (Iterator i = root.elementIterator("mainInform"); i.hasNext();) {
					LogMain lm = new LogMain();
					Element mainInform = (Element) i.next();
					lm.setSystemoperatedate(sdf.parse(mainInform.attributeValue("SYSTEM_OPERATE_DATE")));
					lm.setUserlog(mainInform.attributeValue("USERLOG"));
					lm.setEventobject(mainInform.attributeValue("EVENTOBJECT"));
					lm.setEventtype(mainInform.attributeValue("EVENTTYPE"));
					lm.setObjectid(mainInform.attributeValue("OBJECTID"));
					lm.setObjectname(mainInform.attributeValue("OBJECTNAME"));
					lm.setOperatecomments(mainInform.attributeValue("OPERATE_COMMENTS"));
					logmain.add(lm);
					int a = logmain.size()-1;
					list.add(a);
					for (Iterator j = mainInform.elementIterator("item"); j.hasNext();){
						LogDetail ld = new LogDetail();
						Element item = (Element) j.next();
						ld.setDataname(item.attributeValue("DATANAME"));
						ld.setOldvalue(item.attributeValue("OLDVALUE"));
						ld.setNewvalue(item.attributeValue("NEWVALUE"));
						ld.setSystemlogid(""+a);
						detail.add(ld);
					}
					
					
				}
				HBSession sess = HBUtil.getHBSession();
				for(int i=0;i<logmain.size();i++){
					Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
					sess.save(logmain.get(i));
					ts.commit();
					for(int j=0;j<list.size();j++){
						int a = (Integer) list.get(j);
						if(i==a){
							map.put(""+a, logmain.get(i).getSystemlogid());
						}
					}
				}
				for(int i=0;i<detail.size();i++){
					Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
						detail.get(i).setSystemlogid((String)map.get(detail.get(i).getSystemlogid()));
					sess.save(detail.get(i));
					ts.commit();
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		this.setMainMessage("导入成功");
		this.closeCueWindowByYes("fileImpWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}