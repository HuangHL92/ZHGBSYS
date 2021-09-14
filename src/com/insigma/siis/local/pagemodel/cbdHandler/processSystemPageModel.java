package com.insigma.siis.local.pagemodel.cbdHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class processSystemPageModel extends PageModel{
/*	public String personid = "";
	public String cbd_id = "";*/

	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String cbd_name = "";
		String personids = "";
		String cbd_path = "";
		String cbd_flag = "";
		String cbd_id = "";
		String sb_cbd_id = "";
		String value = this.getRadow_parent_data();
		// ����ҳ�洫�ݵ�ֵ��Ϊ��ʱ����ʾ��ָ���ʱ����򿪣�����Ϊ�´�ҳ��
		if("".equals(value) || value ==null){
			this.getPageElement("resource").setValue("New");
		}
		if(!"".equals(value) && value !=null){
			
			cbd_name = value.split("@")[0];
			personids = value.split("@")[1];
			cbd_path = value.split("@")[3];
			//this.createPageElement("addCBD", ElementType.BUTTON, false).setDisabled(true);
			this.getExecuteSG().addExecuteCode("setButton('addCBD');");
			if(cbd_path.equals("2")){
				sb_cbd_id = value.split("@")[2];
				String cbd_personname = sess.createSQLQuery("select cbd_personname from cbd_info where cbd_id = '"+sb_cbd_id+"'").uniqueResult().toString();
				this.getPageElement("cbd_personname").setValue(cbd_personname);
				this.getPageElement("sb_cbd_id").setValue(sb_cbd_id);
				cbd_id = sess.createSQLQuery("select objectno from cbd_info where cbd_id = '"+sb_cbd_id+"'").uniqueResult().toString();
				this.getPageElement("cbd_id").setValue(cbd_id);
				this.getPageElement("sb_cbd_id").setValue(sb_cbd_id);
				this.getPageElement("cbd_path").setValue(cbd_path);
				//this.createPageElement("addUpCBDBtn", ElementType.BUTTON, false).setDisabled(true);
				this.getExecuteSG().addExecuteCode("setButton('addUpCBDBtn');");
			}else{
				cbd_id = value.split("@")[2];
				String cbd_personname = sess.createSQLQuery("select cbd_personname from cbd_info where cbd_id = '"+cbd_id+"'").uniqueResult().toString();
				this.getPageElement("cbd_personname").setValue(cbd_personname);
				List list = sess.createSQLQuery("select cbd_id from cbd_info where objectno = '"+cbd_id+"'").list();
				if(list.size()>0){
					sb_cbd_id = list.get(0).toString();
					this.getPageElement("sb_cbd_id").setValue(sb_cbd_id);
					//this.createPageElement("addUpCBDBtn", ElementType.BUTTON, false).setDisabled(true);
					this.getExecuteSG().addExecuteCode("setButton('addUpCBDBtn');");
				}
				this.getPageElement("cbd_id").setValue(cbd_id);
				this.getPageElement("cbd_path").setValue(cbd_path);
			}
			
		}
		
		if(!cbd_id.isEmpty()){
			
			this.getExecuteSG().addExecuteCode("setcbdname('"+cbd_name+"');");
			this.getPageElement("cbd_id").setValue(cbd_id);
			this.getPageElement("cbd_name").setValue(cbd_name);
			this.getPageElement("cbd_personid").setValue(personids);
			this.request.getSession().setAttribute("cbd_id", cbd_id);
			StringBuffer stepValue=new StringBuffer();
			List<String> list = sess.createSQLQuery("select cbd_status_step from cbd_status where cbd_id = '"+cbd_id+"' order by cbd_status_step").list();
			
			if(list.size()>0){
				//�����еĲ�����װ�����ŵ�ҳ����
				for(int i=0;i<list.size();i++){
					stepValue.append(list.get(i)).append(",");
				}
				stepValue.substring(0, stepValue.length()-1);
				this.getPageElement("step").setValue(stepValue.toString());
				this.getPageElement("nowStep").setValue(list.get(list.size()-1));
				this.getExecuteSG().addExecuteCode("controlStep('"+list.get(list.size()-1)+"');");
				List list2 = sess.createSQLQuery("select case when STATUS is null then '0' else STATUS end as STATUS from cbd_info where cbd_id = '"+cbd_id+"'").list();
				if(list2.size()>0){
					String end = list2.get(0).toString();
					if(!"0".equals(end)){
						this.getPageElement("end").setValue(end);
					}
				}
			}else{
				this.getExecuteSG().addExecuteCode("controlStep('0');");
				this.getPageElement("step").setValue("0");
				this.getPageElement("nowStep").setValue("0");
			}
		}else{
			this.getPageElement("step").setValue("0");
			this.getExecuteSG().addExecuteCode("setNextStep('0');");
		}
		this.setNextEventName("peopleInfoGrid_noAttach.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("reloadGrid")
	public int reloadGrid() throws RadowException{
		String personid = this.getPageElement("cbd_personid").getValue();
		CommonQueryBS.systemOut(personid);
		this.setNextEventName("peopleInfoGrid_noAttach.dogridquery");
		return 0;
		
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String personid = this.getPageElement("cbd_personid").getValue();
		String [] personids = personid.split(",");
		StringBuffer ids = new StringBuffer();
		for(int i=0;i<personids.length;i++){
			ids = ids.append("'"+personids[i]+"',");
		}
		String id = ids.substring(0, ids.length()-1);
		String sql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			 sql = "select a0000,a0101,a0104,a0117,a0141,a0192,a0184, "+
                         "(CASE "+
                         "WHEN LENGTH(A0107) = 6 THEN "+
                         "SUBSTR(A0107, 1, 4) || '.' || SUBSTR(A0107, 5, 2) "+
                         "WHEN LENGTH(A0107) = 8 THEN "+
                         "SUBSTR(A0107, 1, 4) || '.' || SUBSTR(A0107, 5, 2) || '.' || "+
                         "SUBSTR(A0107, 7, 2) "+
                         "ELSE "+
                         "A0107 "+
                         "END) as a0107 "+
                         "FROM A01 "+
                         "where a0000 in ("+id+")";
		}else{
			sql = "select a0000,a0101,a0104,a0117,a0141,a0192,a0184, "+
                     "(CASE "+
                     "WHEN LENGTH(A0107) = 6 THEN "+
	                 "CONCAT_WS('.',SUBSTR(A0107, 1, 4),SUBSTR(A0107, 5, 2)) "+
                     "WHEN LENGTH(A0107) = 8 THEN "+
	                 "CONCAT_WS('.',SUBSTR(A0107, 1, 4),SUBSTR(A0107, 5, 2),SUBSTR(A0107, 7, 2)) "+
                     "ELSE "+
	                 "A0107 "+
                     "END ) "+
                     "as a0107 "+
                     "FROM A01 "+
                     "where a0000 in ("+id+")";
		}
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("peopleInfoGrid_noAttach.dogridquery")
	public int doMemberQuery2(int start,int limit) throws RadowException{
		String personid = this.getPageElement("cbd_personid").getValue();
		String [] personids = personid.split(",");
		StringBuffer ids = new StringBuffer();
		for(int i=0;i<personids.length;i++){
			ids = ids.append("'"+personids[i]+"',");
		}
		String id = ids.substring(0, ids.length()-1);
		String sql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			 sql = "select a0000,a0101,a0104,a0117,a0141,a0192,a0184, "+
                        "(CASE "+
                        "WHEN LENGTH(A0107) = 6 THEN "+
                        "SUBSTR(A0107, 1, 4) || '.' || SUBSTR(A0107, 5, 2) "+
                        "WHEN LENGTH(A0107) = 8 THEN "+
                        "SUBSTR(A0107, 1, 4) || '.' || SUBSTR(A0107, 5, 2) || '.' || "+
                        "SUBSTR(A0107, 7, 2) "+
                        "ELSE "+
                        "A0107 "+
                        "END) as a0107 "+
                        "FROM A01 "+
                        "where a0000 in ("+id+")";
		}else{
			sql = "select a0000,a0101,a0104,a0117,a0141,a0192,a0184, "+
                    "(CASE "+
                    "WHEN LENGTH(A0107) = 6 THEN "+
	                 "CONCAT_WS('.',SUBSTR(A0107, 1, 4),SUBSTR(A0107, 5, 2)) "+
                    "WHEN LENGTH(A0107) = 8 THEN "+
	                 "CONCAT_WS('.',SUBSTR(A0107, 1, 4),SUBSTR(A0107, 5, 2),SUBSTR(A0107, 7, 2)) "+
                    "ELSE "+
	                 "A0107 "+
                    "END ) "+
                    "as a0107 "+
                    "FROM A01 "+
                    "where a0000 in ("+id+")";
		}
		this.pageQuery(sql, "SQL", start, limit);
//		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	
	/**
	 * �ǼǱ����
	 */
//	@PageEvent("djbOperate.onclick")
//	public int djbOperate() throws RadowException{
//		String personid = this.getPageElement("cbd_personid").getValue();
//		this.setRadow_parent_data(personid+"@1");
//		this.openWindow("personWindow", "pages.cbdHandler.PersonAttach");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * ���ɵǼǱ�
	 */
	@PageEvent("checkPer")
	public int checkPer(String value) throws RadowException, AppException{
		String [] values = value.split("@");
		String a0000 = values[0];
		String a0101 = values[1];
		HBSession sess = HBUtil.getHBSession();
		if(a0101.equals("1")){
			a0101 = sess.createSQLQuery("select a0101 from a01 where a0000 = '"+a0000+"'").uniqueResult().toString();
			this.getPageElement("person_id").setValue(a0000);
			this.getPageElement("person_name").setValue(a0101);
		}
		StringBuffer sql2 = new StringBuffer();
		String [] a0000s = a0000.split(",");
		String filename = "";
		if(a0000s.length>3){
			for(int i=0;i<3;i++){
				filename = filename+sess.createSQLQuery("select a0101 from a01 where a0000 = '"+a0000s[i]+"'").uniqueResult().toString()+",";
			}
			filename = filename.substring(0, filename.length() - 1);
		}else{
			for(int i=0;i<a0000s.length;i++){
				filename = filename+sess.createSQLQuery("select a0101 from a01 where a0000 = '"+a0000s[i]+"'").uniqueResult().toString()+",";
			}
			filename = filename.substring(0, filename.length() - 1);
		}
		this.getPageElement("filename").setValue(filename);
		for(int i=0;i<a0000s.length;i++){
			sql2=sql2.append("'"+a0000s[i]+"',");
		}
		String sql3 = sql2.substring(0, sql2.length()-1);
		String sql = "select a0101 from Rydjb where a0000 in ("+sql3+")";
		List list = sess.createQuery(sql).list();
		CommonQueryBS.systemOut(a0000);
		CommonQueryBS.systemOut(a0101);
		if(list.size()>0){
			StringBuffer name = new StringBuffer();
			for(int i=0;i<list.size();i++){
				name=name.append(""+list.get(i)+",");
			}
			String allName = name.substring(0, name.length()-1);
			this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','"+allName+",�����ɵǼǱ��Ƿ��������ɣ�ע������Ա�����ɵǼǱ����ٴ����ɵø���ԭ�������޷��ָ���',200,function(id){" +
					"if(id=='yes'){" +
					"	ml('"+a0000+"','"+allName+"');		" +
						"}else if(id=='no'){" +
						"	expExcelTemp();" +
						"}else if(id=='cancel'){" +
						"	" +
						"}" +
					"});");
		}else{
			this.getExecuteSG().addExecuteCode("ml('"+a0000+"','"+a0101+"');");
		}		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* ���ɱ��Ǽ���Ա���ַ���
	 * @author lxy
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getSheet")
	public int getSheet(String value) throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String djString=null;
		String [] values = value.split("@");
		String a0000 = values[0];
		String a0101 = values[1];
		String [] a0000s = a0000.split(",");
		String sql = "select a0201a from a02 where a0000 = '"+a0000s[0]+"'";
		String dw = sess.createSQLQuery(sql).list().get(0).toString();
		String djs = ""+a0000s.length;
		if(a0000s.length<1){
			this.setMainMessage("��ѡ����Ҫ�Ǽǵ���Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//ѭ���Ǽ��б��������ɵǼ��ַ���
		for (int i=0;i<a0000s.length;i++) {
			djString=djString+"@|"+a0000s[i]+"|";
		}
		
		if(djString!=null){
			djString=djString.substring(djString.indexOf("@")+1,djString.length());
		}
 		this.getPageElement("djgridString").setValue(djString);
 		this.getPageElement("djs").setValue(djs);
 		this.getPageElement("dw").setValue(dw);
		this.getExecuteSG().addExecuteCode("downLoadTmp()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����������
	 */
//	@PageEvent("expbab.onclick")
//	public int expbab() throws RadowException{
//		String personid = this.getPageElement("cbd_personid").getValue();
//		this.setRadow_parent_data(personid+"@2");
//		this.openWindow("personWindow", "pages.cbdHandler.PersonAttach");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * �ϴ���Ա����
	 */
//	@PageEvent("openAttach.onclick")
//	public int processSystem() throws RadowException{
//		String personid = this.getPageElement("cbd_personid").getValue();
//		this.setRadow_parent_data(personid+"@3");
//		this.openWindow("personWindow", "pages.cbdHandler.PersonAttach");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**
	 * �鿴/ɾ������
	 * @return
	 * @throws RadowException 
	 */
//	@PageEvent("modifyAttach")
//	public int modifyAttach(String value) throws RadowException{
//		System.out.println(value);
//		this.setRadow_parent_data(value);
//		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
//	
	/**
	 * ��ɰ�ť
	 */
	@PageEvent("nextStep")
	public int startWork(String value) throws RadowException{
		String cbd_id = this.getPageElement("cbd_id").getValue();
		String sb_cbd_id = this.getPageElement("sb_cbd_id").getValue();
		if(value.equals("1")){
			if(StringUtil.isEmpty(cbd_id)){
				this.setMainMessage("�������ɱ����ʱ�����");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(value.equals("4")){
			if(StringUtil.isEmpty(sb_cbd_id)){
				this.setMainMessage("���������ϱ��ʱ�����");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//cbd_id = (String)this.request.getSession().getAttribute("cbd_id");
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep(value);
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		
		//this.setMainMessage("�������ɹ���");
		//this.reloadPage();
		this.getExecuteSG().addExecuteCode("setNextStep('"+value+"');");
		StringBuffer stepValue=new StringBuffer();
		List<String> list = sess.createSQLQuery("select cbd_status_step from cbd_status where cbd_id = '"+cbd_id+"' order by cbd_status_step").list();
		
		if(list.size()>0){
			//�����еĲ�����װ�����ŵ�ҳ����
			for(int i=0;i<list.size();i++){
				stepValue.append(list.get(i)).append(",");
			}
			stepValue.substring(0, stepValue.length()-1);
			this.getPageElement("step").setValue(stepValue.toString());
			this.getPageElement("nowStep").setValue(list.get(list.size()-1));
			//this.getExecuteSG().addExecuteCode("controlStep('"+stepValue.toString()+"');");
			List list2 = sess.createSQLQuery("select case when STATUS is null then '0' else STATUS end as STATUS from cbd_info where cbd_id = '"+cbd_id+"'").list();
			if(list2.size()>0){
				String end = list2.get(0).toString();
				if(!"0".equals(end)){
					this.getPageElement("end").setValue(end);
				}
			}
		}else{
			this.getExecuteSG().addExecuteCode("controlStep('0');");
			this.getPageElement("step").setValue("0");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����ϱ��ʱ���
	 */
	@PageEvent("addUpCBDBtn.onclick")
	public int addUpCBDBtn() throws RadowException{
		//cbd_id = (String)this.request.getSession().getAttribute("cbd_id");
		String cbd_id = this.getPageElement("cbd_id").getValue();
		//System.out.println(cbd_id);
		if(StringUtil.isEmpty(cbd_id)){
			this.setMainMessage("δ���ɱ����ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data(cbd_id+"@N");
		this.openWindow("newUpCBD", "pages.cbdHandler.AddUPCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸��ϱ��ʱ���
	 */
	@PageEvent("modifyUpCBDBtn.onclick")
	public int modifyUpCBDBtn() throws RadowException{
		String sb_cbd_id = this.getPageElement("sb_cbd_id").getValue();
		String cbd_id = this.getPageElement("cbd_id").getValue();
		if(StringUtil.isEmpty(sb_cbd_id)){
			this.setMainMessage("δ�����ϱ��ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRadow_parent_data(cbd_id+"@U");
		this.openWindow("newUpCBD", "pages.cbdHandler.AddUPCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ύ���ܲ�������
	 */
	@PageEvent("sendUp.onclick")
	public int sendUp() throws RadowException{
		String cbd_id = this.getPageElement("cbd_id").getValue();
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("3");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ȷ���������
	 */
	@PageEvent("makeSure.onclick")
	public int makeSure() throws RadowException{
		String cbd_id = this.getPageElement("cbd_id").getValue();
		String status = this.getPageElement("end").getValue();
		if(StringUtil.isEmpty(status)){
			this.setMainMessage("��ѡ�����������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_id)){
			this.setMainMessage("δ��ѯ���������ļ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("6");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		HBSession sess = HBUtil.getHBSession();
		//��ѯ�ʱ�����Ϣ�����Ը��³ʱ���״̬�ֶ�
		List<CBDInfo> list = sess.createQuery("from CBDInfo where cbd_id = '"+cbd_id+"'").list();
		List<CBDInfo> list1 = sess.createQuery("from CBDInfo where objectno = '"+cbd_id+"'").list();
		CBDInfo cbdinfo = null;
		CBDInfo cbdinfo1 = null;
		if(list.size()>0){
			cbdinfo = list.get(0);
			//������Ա��Ϣ���������
			String personid = cbdinfo.getCbd_personid();
			String[] personids = personid.split(",");
			for(int i=0;i<personids.length;i++){
				A01 a01 = (A01) sess.createQuery("from A01 where a0000='"+personids[i]+"'").list().get(0); 
				if("1".equals(status)){
					a01.setCbdresult(status);
				}else{
					a01.setCbdresult("0");
				}
				sess.saveOrUpdate(a01);
				
			}
			//���óʱ����������״̬
			if("1".equals(status)){
				status = "1";
			}else{
				status = "2";
			}
			cbdinfo.setStatus(status);
			
		}
		if(list1.size()>0){
			cbdinfo1 = list1.get(0);
			cbdinfo1.setStatus(status);
		}
		
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		//���ʱ�����Ϣ����ʱ������
		if(cbdinfo != null){
			sess.saveOrUpdate(cbdinfo);
		}
		if(cbdinfo1 != null){
			sess.saveOrUpdate(cbdinfo1);
		}
		ts.commit();
		reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˢ��
	 */
	@PageEvent("reload")
	public int reload() throws RadowException {
	    this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ʱ����ϱ�
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("repBtn")
	public int reportCBD(String value) throws RadowException{
		String [] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		//ִ�����ݿ����
		HBSession sess = HBUtil.getHBSession();
		String sb_cbd_id = sess.createSQLQuery("select cbd_id from cbd_info where objectno = '"+cbd_id+"'").uniqueResult().toString();
		//��ѯ�ʱ�����Ӧ�ĸ�����Ϣ
		String sql = "select * from Attachment_Info ai where ai.objectid = '"+sb_cbd_id+"'";
		List attach_list =  sess.createSQLQuery(sql).list();
		if(attach_list.size()<=0){
			this.setMainMessage("��û���ϴ��ʱ��������������ϱ��ʱ������ݰ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ȡ�ʱ�����Ӧ��Ա���
		sql = "select ci.CBD_PERSONID from CBD_Info ci where ci.cbd_id = '"+sb_cbd_id+"'";
		List list = sess.createSQLQuery(sql).list();
		String personid = list.get(0).toString();
		//��ȡ��Ա���
		String[] personids = personid.split(",");
				
		//ѭ����ѯ��Ա�ĸ�����Ϣ
		for(int i =0;i<personids.length;i++){
			sql = "select * from Attachment_Info ai where ai.personid = '"+personids[i]+"'";
			List atta = sess.createSQLQuery(sql).list();
			//�ж��Ƿ�����Ա�ĸ�����Ϣ���������Աû���ϴ��������������ϱ�
			if(atta.size()<=0){
				this.setMainMessage("��û���ϴ���Ա�����������ϱ��ʱ������ݰ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//��ȡ�ʱ�����Ӧ��Ա����
		sql = "select CBD_PERSONNAME from cbd_info where cbd_id = '"+sb_cbd_id+"'";
		List list2 = sess.createSQLQuery(sql).list();
		String personname = list2.get(0).toString();
		this.setRadow_parent_data(sb_cbd_id+"@"+cbd_name+"@"+cbd_id+"@"+personname+"@"+personids);
		this.openWindow("ReportCBD", "pages.cbdHandler.ReportCBDFile");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������سʱ������ݰ��ж��Ƿ��и���
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getCBDZip")
	public int getCBDZip(String value) throws RadowException {
		String [] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		//ִ�����ݿ����
		HBSession sess = HBUtil.getHBSession();
		//ͨ��objectno��ѯ�ϱ��ʱ���id
		String sb_cbd_id = sess.createSQLQuery("select cbd_id from cbd_info where objectno = '"+cbd_id+"'").uniqueResult().toString();
		if(sb_cbd_id.isEmpty()){
			this.setMainMessage("����¼���ϱ��ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ѯ�ʱ�����Ӧ�ĸ�����Ϣ
		String sql = "select * from Attachment_Info ai where ai.objectid = '"+sb_cbd_id+"'";
		List attach_list =  sess.createSQLQuery(sql).list();
		if(attach_list.size()<=0){
			this.setMainMessage("��û���ϴ��ʱ����������������سʱ������ݰ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ȡ�ʱ�����Ӧ��Ա���
		sql = "select ci.CBD_PERSONID from CBD_Info ci where ci.cbd_id = '"+sb_cbd_id+"'";
		List list = sess.createSQLQuery(sql).list();
		String personid = list.get(0).toString();
		//��ȡ��Ա���
		String[] personids = personid.split(",");
						
		//ѭ����ѯ��Ա�ĸ�����Ϣ
		for(int i =0;i<personids.length;i++){
			sql = "select * from Attachment_Info ai where ai.personid = '"+personids[i]+"'";
			List atta = sess.createSQLQuery(sql).list();
			//�ж��Ƿ�����Ա�ĸ�����Ϣ���������Աû���ϴ��������������ϱ�
			if(atta.size()<=0){
				this.setMainMessage("��û���ϴ���Ա�������������سʱ������ݰ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//��ȡ�ʱ�����Ӧ��Ա����
		sql = "select CBD_PERSONNAME from cbd_info where cbd_id = '"+sb_cbd_id+"'";
		List list2 = sess.createSQLQuery(sql).list();
		String personname = list2.get(0).toString();
		//���ϱ��ʱ���id���ϱ��ʱ������ơ������ʱ���id����ǰ̨
		this.getExecuteSG().addExecuteCode("createCBDZip('"+sb_cbd_id+"','"+cbd_name+"','"+cbd_id+"','"+personname+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �鿴/ɾ������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int editFile(String value) throws RadowException{
//		System.out.println(value);
		String[] values = value.split("@");
		if(values.length==2&&values[0].equals("")){
			this.setMainMessage("δ��ѯ���ϱ��ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String cbd_id = values[0];
		String cbd_path = values[1];
		if(cbd_path.equals("2")){
			HBSession sess = HBUtil.getHBSession();
			List list = sess.createSQLQuery("select * from cbd_info where objectno = '"+cbd_id+"' and CBD_PATH = '2'").list();
			if(list.size()==0){
				this.setMainMessage("δ�����ϱ��ʱ�����");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ¼��ʱ���ҳ��
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("addCBD.onclick")
	public int cbdAdd() throws RadowException{
		String resource = this.getPageElement("resource").getValue();
		if("New".equals(resource)){
			this.setRadow_parent_data("New");
		}
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸ĳʱ�����Ϣ
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("modifyCBD")
	public int modifyCBD(String value) throws RadowException{
		
		String[] values = value.split("@");
		if("0".equals(values[1])){
			
			this.setRadow_parent_data(values[0]+"@UU@"+values[2]);
			this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
			//this.getExecuteSG().addExecuteCode("onHide('editCBD',\"radow.doEvent('reload')\");");
		}else if("1".equals(values[1])){
			this.setRadow_parent_data(values[0]+"@U");
			this.openWindow("newUpCBD", "pages.cbdHandler.AddUPCBDInfo");
			this.getExecuteSG().addExecuteCode("onHide('editCBD',\"radow.doEvent('reload')\");");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
