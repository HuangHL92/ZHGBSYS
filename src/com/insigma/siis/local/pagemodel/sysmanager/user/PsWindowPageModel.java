package com.insigma.siis.local.pagemodel.sysmanager.user;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.insigma.odin.framework.RSACoder;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PsWindowPageModel extends PageModel {
	/**
	 * ��ʼ�����ڳ�ʼ��ʱ�ж��Ƿ����������
	 */
	@Override
	public int doInit(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ���水ť����ʱ��������¼���������Ϣ��
	 * @return EventRtnType.NORMAL_SUCCESS �¼������ɹ�
	 */
	@PageEvent("savePd.onclick")
	public int savePassword(){
		this.addNextEvent(NextEventValue.YES, "savePasswordEvent");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ���޸����������"); //������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������ȡ����ťʱ��������¼�
	 * @return EventRtnType.NORMAL_SUCCESS �¼������ɹ�
	 */
	@PageEvent("cannelEvent")
	public int cannelEvent(){ 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ȷ����ťʱ��������¼�
	 * @return EventRtnType.NORMAL_SUCCESS �¼������ɹ� EventRtnType.FAILD �¼�����ʧ��
	 * @throws RadowException
	 */
	@PageEvent("savePasswordEvent")
	@Transaction
	public int savePdOnClick() throws RadowException {
		String sql4 = null;
		String userLog = PrivilegeManager.getInstance().getCueLoginUser().getId();
		try {
			String id = PrivilegeManager.getInstance().getCueLoginUser().getId();
			String odpassword = MD5.MD5(this.getPageElement("odpassword").getValue().trim());
			String newpassword = this.getPageElement("newpassword").getValue().trim();
			String checkpassword = this.getPageElement("checkpassword").getValue().trim();
			String oldPassword = PrivilegeManager.getInstance().getCueLoginUser().getPasswd();
			//�ٴ�У���������ȷ�ԣ�û�����⣬�ٱ��浽���ݿ�
			//System.out.println((!oldPassword.equals(odpassword) || !checkPasswd(newpassword) || !newpassword.equals(checkpassword)));
			//System.out.println(!oldPassword.equals(odpassword));
			//System.out.println(!checkPasswd(newpassword));
			//System.out.println(!newpassword.equals(checkpassword));
			if(1==2&&(!oldPassword.equals(odpassword) || !checkPasswd(newpassword) || !newpassword.equals(checkpassword))){
				this.setMainMessage("�޸�����ʧ��,���������룡");
				this.getExecuteSG().addExecuteCode("document.getElementById('odpassword').value=''");
				this.getExecuteSG().addExecuteCode("document.getElementById('newpassword').value=''");
				this.getExecuteSG().addExecuteCode("document.getElementById('checkpassword').value=''");
				return EventRtnType.FAILD;
			}else{
				
				HBSession sess = HBUtil.getHBSession();
				String hql = "From SmtUser S where S.id = '"+id+"'";
				SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
				PrivilegeManager.getInstance().getIUserControl().updatePassword(id, odpassword, newpassword);
				
				try {
					String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJTrQ2uKF1wb6SQmvMqCjhsd0WRvWRK/nrhYj5mZ3D0ZHJ2mqEypXh0Xac8GrgTwbEF1NKKFJyKS7Wl5jHIkoTUCAwEAAQ==";
					user =(SmtUser) sess.createQuery(hql).list().get(0);
					byte[] code2 = RSACoder.encryptByPublicKey(newpassword.getBytes(), Base64.decodeBase64(publicKey.getBytes()));
					user.setSec(new String(Base64.encodeBase64(code2)));
					user.setPasswd(MD5.MD5(newpassword));
					sess.update(user);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List<String> list = new ArrayList<String>();
				try {
					new LogUtil().createLog("67", "SMT_USER",user.getId(), user.getLoginname(), "", list);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				//���޸ĵ����ݱ��浽���ݿ���
				
				sql4 = "update smt_user set pwdedtdate=sysdate where userid='"+userLog+"'";
				sess.createSQLQuery(sql4).executeUpdate();
				
				this.getExecuteSG().addExecuteCode("odin.alert('�����޸ĳɹ�',showResult)");
				
				/*this.setMainMessage("�޸�����ɹ�");
				this.setMessageType(EventMessageType.ALERT);
				NextEvent ne1 = new NextEvent();
				ne1.setNextEventName("print");
				ne1.setNextEventValue(NextEventValue.YES);
				this.addNextEvent(ne1);*/
				return EventRtnType.NORMAL_SUCCESS;

				//this.closeCueWindowByYes("pswin");
				//this.getExecuteSG().addExecuteCode("parent.location.href='LogonDialog.jsp'");
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("�޸�����ʧ�ܣ�"+e.getMessage());
			return EventRtnType.FAILD;
		}
	
	}
	/*@PageEvent("print")
	public int cannelEvent11(){ 
		this.getExecuteSG().addExecuteCode("parent.location.href='LogonDialog.jsp'");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	

	/**
	 * ����ɵ������ʱ��У��ɵ������ǲ�����ȷ
	 * @return EventRtnType.NORMAL_SUCCESS �¼������ɹ�
	 * @throws RadowException   �������ɹ�ʱ���׳���Ϣ������ľ����벻��ȷ
	 */
	@PageEvent("odpassword.onchange")
	@NoRequiredValidate
	public int odPasswordOnClick() throws RadowException{
		String oldPassword = PrivilegeManager.getInstance().getCueLoginUser().getPasswd();
		String odpassword = this.getPageElement("odpassword").getValue();
		if(!oldPassword.equals(MD5.MD5(odpassword))){
			this.getExecuteSG().addExecuteCode("document.getElementById('odpassword').value=''");
			this.getExecuteSG().addExecuteCode("odpasswordMarkFalse()");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("odpasswordMarkTrue()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * У��������
	 */
	@PageEvent("newpassword.onchange")
	@NoRequiredValidate
	public int newPasswordOnClick() throws RadowException{
		String newPassword = this.getPageElement("newpassword").getValue();
		String odpassword = this.getPageElement("odpassword").getValue();
		if(odpassword == null || odpassword.isEmpty()){
			//this.setMainMessage("������������룡");
			this.getExecuteSG().addExecuteCode("document.getElementById('newpassword').value=''");
			this.getExecuteSG().addExecuteCode("newpasswordFalse1()");
			return EventRtnType.FAILD;
		}
		if(newPassword == null || newPassword.isEmpty()){
			//this.setMainMessage("���������룡");
			this.getExecuteSG().addExecuteCode("newpasswordFalse2()");
			return EventRtnType.FAILD;
		}
		if(newPassword.equals(odpassword)&&get3YCheck()) {
			//this.setMainMessage("�������������ͬ��");
			this.getExecuteSG().addExecuteCode("document.getElementById('newpassword').value=''");
			this.getExecuteSG().addExecuteCode("newpasswordFalse3()");
			return EventRtnType.FAILD;
		}
		if(!checkPasswd(newPassword)){
			//this.setMainMessage("���볤�ȱ������8λ�������֣���ĸ�������ַ����");
			this.getExecuteSG().addExecuteCode("document.getElementById('newpassword').value=''");
			this.getExecuteSG().addExecuteCode("newpasswordFalse4()");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("newpasswordTrue()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * У���������ǲ������롣ȷ�ϵ��������ǲ��Ǻ�����������һ��
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("checkpassword.onchange")
	@NoRequiredValidate
	public int checkPasswordOnChange() throws RadowException{
		String newpassword = this.getPageElement("newpassword").getValue().trim();
		String checkpassword = this.getPageElement("checkpassword").getValue().trim();
		if(checkpassword==null || checkpassword.isEmpty()){
			//this.setMainMessage("������������!");
			this.getExecuteSG().addExecuteCode("checkpasswordFalse1()");
			return EventRtnType.FAILD;
		}
		if(newpassword==null || newpassword.isEmpty()){
			//this.setMainMessage("�������������룡");
			this.getExecuteSG().addExecuteCode("document.getElementById('checkpassword').value=''");
			this.getExecuteSG().addExecuteCode("checkpasswordFalse2()");
			return EventRtnType.FAILD;
		}
		if(!newpassword.equals(checkpassword)){
			//this.setMainMessage("��������������벻һ��");
			this.getExecuteSG().addExecuteCode("document.getElementById('checkpassword').value=''");
			this.getExecuteSG().addExecuteCode("checkpasswordFalse3()");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("checkpasswordTrue()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*
	 * ���������У��
	 */
	public static boolean checkPasswd(String newPassword){
		String regex1 = ".*[0-9].*";
		String regex2 = ".*.*[a-zA-Z].*.*";
		String regex3 = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������].*";
		boolean r1 = newPassword.matches(regex1);
		boolean r2 = newPassword.matches(regex2);
		
		boolean r3 = newPassword.matches(regex3);
		
		//�����������š�У��û��ͨ�������ж��Ƿ����-����
		if(!r3){
			int i = newPassword.indexOf("-");
			
			if(i != -1){
				r3 = true;
			}
			
		}
		//������֤����
		String sql3 = "select aaa005 from aa01 where AAA001 = 'IS_VERIFY_PASSWORD'";
		HBSession sess = HBUtil.getHBSession();
		String statu=(String) sess.createSQLQuery(sql3).uniqueResult();
		if(statu == null || statu.equals("")){
			statu="0";
		}
		//boolean r4 = newPassword.length()>=8;
		boolean r4 = newPassword.length()>=10;
		if("1".equals(statu)){
			return r1 && r2 && r3 && r4;
		}else{
			return true;
		}
	}
	//���ϵͳʱ��,��������޸�����ʱ������ݿ�ʱ���ʱ���
	public static boolean getCheck(){
		//���һ���޸�����ʱ��
		String sql1 = null;
		//��ǰ������ʱ��
		String sql2 = null;
		//ʱ�������
		int time = 0;
		HBSession sess = HBUtil.getHBSession();
		String userLog = PrivilegeManager.getInstance().getCueLoginUser().getId();
		//�����ݿ�����Ե��ж�
		sql1 = "select nvl(pwdedtdate,createdate) pwdedtdate from smt_user t WHERE userid = '"+userLog+"'";
		sql2 = "select sysdate from dual";


		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lastTimeDate = null;
		Date nowTimeDate = null;
		try {
			Object last = sess.createSQLQuery(sql1).uniqueResult();
			//�ж��Ƿ��ܲ�ѯ��ʱ��
			if(last == null){
				last=0;
			}
			Object now = sess.createSQLQuery(sql2).uniqueResult();
			if(now == null){
				last=0;
			}
			CommonQueryBS.systemOut("last:"+last+"   "+"now:"+now);
			//���ڸ�ʽ��ת��
			lastTimeDate = sdf.parse(sdf.format(last));
			nowTimeDate = sdf.parse(sdf.format(now));
			//������
			time = (int) ((nowTimeDate.getTime()-lastTimeDate.getTime())/1000/60/60/24);
			
		} catch (ParseException e) {
			CommonQueryBS.systemOut("����У������������ת��ʧ�ܣ�");
			e.printStackTrace();
		}
		//������֤����
		String sql3 = "select aaa005 from aa01 where AAA001 = 'IS_VERIFY_PASSWORD'";
		String statu=(String) sess.createSQLQuery(sql3).uniqueResult();
		if(statu == null || statu.equals("")){
			statu="0";
		}
		return (time>7&&"1".equals(statu));
		
	}
	
	public static boolean get3YCheck(){

		
		//������֤����
		String sql3 = "select aaa005 from aa01 where AAA001 = 'IS_VERIFY_PASSWORD'";
		String statu=(String) HBUtil.getHBSession().createSQLQuery(sql3).uniqueResult();
		if(statu == null || statu.equals("")){
			statu="0";
		}
		return ("1".equals(statu));
		
	}
}
