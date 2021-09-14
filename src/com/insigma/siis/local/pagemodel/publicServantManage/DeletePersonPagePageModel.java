package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class DeletePersonPagePageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		//��ȡǰ�˵�tableType
		this.getExecuteSG().addExecuteCode("getTabletype();");
		try {
			HBSession sess = HBUtil.getHBSession();
//			String sql = "from A01 where a0000='" + this.getRadow_parent_data()
//					+ "'";
//			List list = sess.createQuery(sql).list();
//			A01 a01 = (A01) list.get(0);
//			
//			// �˳��������
//			A30 a30 = (A30) sess.get(A30.class, a01.getA0000());
//			if (a30 != null) {
//				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("a3007a();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(strHint); // ������ʾ��Ϣ
	}
	
	//���ȷ���¼�
	@PageEvent("yesBtn.onclick") 
	public int deletePerson() throws RadowException {
		String radio = this.getPageElement("radio").getValue();
		
		String a3001 = this.getPageElement("a3001").getValue();
		if("1".equals(radio)){
			if(a3001 == null || "".equals(a3001)){
				this.setMainMessage("��ѡ���˳�����ʽ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		dialog_set("deleteconfirm1","��ȷ����������ѡ��Ĳ�����");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteconfirm1")
	public int deleteconfirm1()throws AppException, RadowException{
		dialog_set("deleteconfirm","���ٴ�ȷ�����Ĳ�����");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ɾ���¼�
	@PageEvent("deleteconfirm")
	@Transaction
	public int deleteconfirm()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		String radio = this.getPageElement("radio").getValue();
		//��ȡ����ѡ����Ա���
		String sa0000 = "";
		String a0000s = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String[] values = a0000s.split("@");
		if (values.length > 1) {
			StringBuffer sb = new StringBuffer();
			String sql = values[0];
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				for (int i = 0; i < allSelect.size(); i++) {
					// �ж��Ƿ���ɾ��Ȩ�ޡ�
					String a0000 = allSelect.get(i).toString();
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and "
							+ " b.a0201b=c.b0111 and a.a0000='"
							+ a0000
							+ "' and c.userid='"
							+ SysManagerUtils.getUserId()
							+ "' "
							+ " and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and "
							+ " b.a0201b=c.b0111 and a.a0000='"
							+ a0000
							+ "' and c.userid='"
							+ SysManagerUtils.getUserId()
							+ "' "
							+ " and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
					if (elist2 == null || elist2.size() == 0) {
						if (elist != null && elist.size() > 0) {

						} else {
							sb.append("'").append(a0000).append("',");
						}
					} else {
						sb.append("'").append(a0000).append("',");
					}
				}
				if (sb.length() == 0) {
					this.setMainMessage("��ѡ��Ա���ɲ�����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				sa0000 = sb.substring(0, sb.length() - 1);
			} else {
				this.setMainMessage("����ѡ��Ҫɾ������Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else{
			//�������Ķ��Žص�
			sa0000=a0000s.substring(0,a0000s.length()-1);
		}
		//�жϣ������ֵ��ѡ��
		if(!"".equals(sa0000) && sa0000 != null){
			//������ʷ��
			if("1".equals(radio)){
				//�������
				String A0163 = "";
				String Status = "";
				
				A30 a30 = new A30();
				this.copyElementsValueToObj(a30, this);
				String a3001 = a30.getA3001();
				//1��ְ��Ա 2������Ա 3������Ա 4��ȥ�� 5������Ա       0��ȫɾ�� 1���� 2��ʷ�� 3������Ա
				//�˳������� id���ɷ�ʽΪassigned
				if(a3001!=null&&!"".equals(a3001)){
					//������Ա     ��ʷ��
					if(a3001.startsWith("1")||a3001.startsWith("2")){
						A0163="3";
						//if(!"4".equals(a01.getStatus())){
						Status="2";
						//}
						
					}else if("35".equals(a3001)){//����  ��ʾ����ȥ����       ��ѯ����ʷ��Ա
						A0163="4";
						//if(!"4".equals(a01.getStatus())){
						Status="2";
						//}
						
					}else if("31".equals(a3001)){//������ ��ʾ��������Ա��     ��ѯ��������Ա
						A0163="2";
						//if(!"4".equals(a01.getStatus())){
						Status="3";
						//}
						
					}else{//����ѡ���˳��Ǽǡ��������������ˡ�����ȥ��ְ���������� ��ʾ��������Ա��     ��ѯ����ʷ��Ա
						A0163="5";
						//if(!"4".equals(a01.getStatus())){
						Status="2";
						//}
						
					}
				}else{
					//�����ǡ����ˡ���״̬
				}
				//��ȡ���б�ѡ�е���Ա��Ϣ
				List<A01> a01_list = (List<A01>) sess.createQuery("from A01 where a0000 in("+sa0000+")").list();
				//ѭ������
				for(int i=0;i<a01_list.size();i++){
					//			A30 a30 = new A30();			
					//			this.copyElementsValueToObj(a30, this);
					//			type = a30.getA3001();
					//			if("31".equals(type)){
					//				//�޸ĳ�����״̬
					//				a01.setStatus("3");
					//			}else{
					//				//�޸ĳ���ʷ״̬
					//				a01.setStatus("2");
					//			}
					//			a30.setA0000(a0000);
					//			sess.saveOrUpdate(a30);	
					//			sess.saveOrUpdate(a01);
					//			sess.flush();
					//			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete')");
					
					a30.setA0000(a01_list.get(i).getA0000());
					
					A30 a30_old = (A30)sess.get(A30.class, a01_list.get(i).getA0000());
					if(a30_old==null){
						a30_old = new A30();
						new LogUtil("3301", "A30", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "������¼", new Map2Temp().getLogInfo(a30_old,a30)).start();
						
					}else{
						new LogUtil("3302", "A30", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a30_old,a30)).start();
						
					}
					try {
						PropertyUtils.copyProperties(a30_old, a30);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					sess.saveOrUpdate(a30_old);
					
				}
				
				HBUtil.executeUpdate("update a01 set A0163 = '"+A0163+"',status = '"+Status+"' where a0000 in("+sa0000+")");
				//sess.flush();
			}else{
				//a01.setStatus("0");//�޸���ȫɾ��
				//sess.saveOrUpdate(a01);
				deletePerson(sa0000);
				//sess.flush();
				//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete');");
				
			}
		}
		String tableType = this.getPageElement("type").getValue();
		//�б�
		if("1".equals(tableType)){
			this.closeCueWindow("deletePersonWin");
			this.getExecuteSG().addExecuteCode("parent.reloadGrid();");
		}
		//С����
		if("2".equals(tableType)){
			this.closeCueWindow("deletePersonWin");
			this.getExecuteSG().addExecuteCode("parent.datashow();");
		}
		//��Ƭ
		if("3".equals(tableType)){
			this.closeCueWindow("deletePersonWin");
			this.getExecuteSG().addExecuteCode("parent.picshow();");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	private void deletePerson(String a0000) throws AppException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		List<A01> a01_list = (List<A01>) HBUtil.getHBSession().createQuery("from A01 where a0000 in("+a0000+")").list();
		
		List<A57> a57_list = (List<A57>) HBUtil.getHBSession().createQuery("from A57 where a0000 in("+a0000+")").list();
		for(A57 a57 : a57_list){
			if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
				String photourl = a57.getPhotopath();
				File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				if(fileF.isFile()){
					fileF.delete();
				}
				
			}
		}
		Statement stmt=null;
		Connection conn = HBUtil.getHBSession().connection();
		try{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//String a0000 = a01.getA0000();
			stmt.executeUpdate("delete from a02 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a06 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a08 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a14 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a15 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a36 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a11 where a1100 in(select a1100 from a41 where a0000 in ("+a0000+"))");
			stmt.executeUpdate("delete from a41 where a0000 in ("+a0000+")");
			
			stmt.executeUpdate("delete from a29 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a53 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a37 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a31 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a30 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a57 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a60 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a61 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a62 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a63 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a64 where a0000 in("+a0000+")");
			stmt.executeUpdate("delete from a01 where a0000 in("+a0000+")");
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new AppException("���ݿ⴦���쳣��",e);
		}finally{
			if (stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new AppException("���ݿ⴦���쳣��", e);
				}
			}
				
		}
		
		for(int i=0;i<a01_list.size();i++){
			
			new LogUtil("33", "A01", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "ɾ����¼", new ArrayList()).start();
		}
		
	}

	@PageEvent("cancelBtn.onclick")
	public int cancel() throws AppException, RadowException {
		//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').store.reload()");
		//this.createPageElement("Grid", "persongrid", false).reload();
		this.closeCueWindow("deletePersonWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	}

   

    

