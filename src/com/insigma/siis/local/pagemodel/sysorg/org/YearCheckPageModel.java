package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class YearCheckPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
						
		String orgId = this.getPageElement("subWinIdBussessId").getValue();				

		String sql = "select a.id,a.b0111, a.checkyear,a.checktime,a.checkfile,b.filename,b.fileurl from yearcheck a left join yearcheckfile b on a.checkfile = b.id where b0111='"+orgId+"'";
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("��ѯsql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	/**
	 * ˢ��
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("refresh")
	public int refresh() throws RadowException, Exception {
		this.setNextEventName("grid1.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	/**
	 *�޸Ŀ���
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String orgId = this.getPageElement("subWinIdBussessId").getValue();									
		String ctxPath = request.getContextPath();
		
		this.getExecuteSG().addExecuteCode("$h.openWin('Check','pages.sysorg.org.Check','�½�����ҳ��',600,300,'"+orgId+"','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 *�޸Ŀ���
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("update")
	public int update() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		String id="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				id=(String)map.get("id");
			}
		}				
		if(num>1){
			this.setMainMessage("����ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String orgId = this.getPageElement("subWinIdBussessId").getValue();	
		String groupid = id+"@@@"+orgId;
		System.out.println("+++++++++++++="+groupid);
		String ctxPath = request.getContextPath();
		//this.setRadow_parent_data(groupid);
		this.getExecuteSG().addExecuteCode("$h.openWin('Check','pages.sysorg.org.Check','�½�����ҳ��',600,300,'"+groupid+"','"+ctxPath+"');");
		
		//this.openWindow("Check", "pages.sysorg.org.Check");
								
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ɾ��
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("deleteBtn")
	public int delete() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		//String groupid="";
		StringBuffer groupid = new StringBuffer();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				//groupid=groupid+",'"+(String)map.get("id")+"'";
				groupid.append("'").append(map.get("id") == null ? "" : map.get("id").toString()).append("',");// ����ѡ����Ա�����װ���á������ָ�
				//groupid=(String)map.get("id");
			}
		}				
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('ϵͳ��ʾ','��ȷ��Ҫɾ����'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");
		//this.addNextEvent(NextEventValue.YES, "doDelete", groupid);
		//this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
		
		
		//this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ������(confirm���ʹ���)
		//this.setMainMessage("ȷ��Ҫɾ����");
		//this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deletesure")
	public int deleteconfirm() throws AppException, RadowException {
		CommQuery query = new CommQuery();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> listfile = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		HashMap<String,Object> mapfile = new HashMap<String,Object>();
		String ids = this.getPageElement("ids").getValue();	
		String checkfile = null;
		String fileurl = null;
		String sql = "select checkfile from yearcheck where id in (" +ids+ ")";
		list = query.getListBySQL(sql);
		for (int i = 0; i < list.size(); i++) {
			map = list.get(i);
			checkfile=map.get("checkfile").toString();
			
			String sqlfile = "select fileurl from yearcheckfile where id = '"+checkfile+"'";	
			listfile = query.getListBySQL(sqlfile);
			for (int j = 0; j < listfile.size(); j++) {
				mapfile = listfile.get(j);
				fileurl = mapfile.get("fileurl").toString();
				File file = new File(fileurl);
				String parentPath = file.getParent();
				File parenfile = new File(parentPath);
				deleteFile(parenfile);
			}
			
			
			HBUtil.executeUpdate("delete from yearcheckfile where id = '"+checkfile+"'");			
		}
		
		HBUtil.executeUpdate("delete from yearcheck where id in (" +ids+ ")");
						
		this.setMainMessage("ɾ���ɹ���");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private void deleteFile(File file) {  
		if (file.exists()) {//�ж��ļ��Ƿ����  
			 if (file.isFile()) {//�ж��Ƿ����ļ�  
				 file.delete();//ɾ���ļ�  
			 } else if (file.isDirectory()) {//�����������һ��Ŀ¼  
				 File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
				 for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
				       this.deleteFile(files[i]);//��ÿ���ļ�������������е���  
				      } 
				 file.delete();//ɾ���ļ���  
			 }
		} else {  
		     System.out.println("��ɾ�����ļ�������");  
			
		}
	}

}
