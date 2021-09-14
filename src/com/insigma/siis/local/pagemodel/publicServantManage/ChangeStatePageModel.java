package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ChangeStatePageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		
		this.getExecuteSG().addExecuteCode("$h.dateDisable('xgsj');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	//ȡ����ť���رյ���
	@PageEvent("cancelBtn.onclick")
	public int cancel() throws AppException, RadowException {
		//this.closeCueWindow("changeStateWin");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//״̬���
	@PageEvent("changeState")
	@Transaction
	public int changeState()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		
		//��ȡ����ѡ����Ա���
		HttpSession session=request.getSession(); 
		String a0000s = session.getAttribute("a0000s").toString(); 	
		
		String a0163Odl = this.getPageElement("a0163Odl").getValue();   //ԭ��Ա����״̬
		//�������Ķ��Žص�
		a0000s = a0000s.substring(0,a0000s.length()-1);
		String a0163 = this.getPageElement("a0163").getValue();		//��Ա����״̬
		
		String xgsj = this.getPageElement("xgsj").getValue();		//�仯ʱ��
		
		
		HBSession sess = HBUtil.getHBSession();
		String[] values = a0000s.split(",");
		
		A01 a01 = new A01();
		
		//ѭ������ÿ���û�
		for (String a0000 : values) {
			
			//A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//����A01��a0163��xgsj
			A01 a01_old = (A01)sess.get(A01.class, a0000);
			try {
				a01 = a01_old.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			a01_old.setA0163(a0163);   					//��Ա����״̬
			/*if(a01_old==null){
				a01_old = new A01();
				//applog.createLog("3301", "A01", a01_old.getA0000(), a01_old.getA0101(), "������¼", new Map2Temp().getLogInfo(new A01(),a01_old));
				
				applog.createLogNew("3A0163","״̬���","��Ա����״̬",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01_old));
			}else{
				//applog.createLog("3302", "A01", a01_old.getA0000(), a01_old.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a01_old,a01_old));
				
				applog.createLogNew("3A0163","״̬���","��Ա����״̬",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01_old));
			}*/
			
			applog.createLogNew("3A0163","״̬���","��Ա����״̬",a0000,a01_old.getA0101(), new Map2Temp().getLogInfo(a01_old,a01));
			
			
			
			if(!a0163Odl.equals(a0163) && !a0163.equals("1")){
				
				UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
				//a01_old.setXgsj(Long.valueOf(xgsj));  		//�仯ʱ��
				Date date = new Date();//���ϵͳʱ��.
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String nowTime = sdf.format(date);//��ʱ���ʽת���ɷ���TimestampҪ��ĸ�ʽ.
		        Timestamp dates =Timestamp.valueOf(nowTime);//��ʱ��ת��
				
				
				a01_old.setXgr(user.getId());			//�޸���(��id)
				a01_old.setXgsj(dates.getTime());		    //�޸�ʱ��
			}
			
			sess.saveOrUpdate(a01_old);	
			sess.flush();
			
			//�����Ϊ����ְ��Ա��21,22,23,29��������Ա���е�ְ����Ϣ��A02��ȫ������Ϊ"����",��ְʱ����д
			
			
			if(a0163.equals("21") || a0163.equals("22") || a0163.equals("23") || a0163.equals("29")){
				
				HBUtil.executeUpdate("update a02 set A0265 = '"+xgsj+"' where a0000 = '"+a0000+"' and A0255 = '1'");
				HBUtil.executeUpdate("update a02 set A0255 = '"+0+"' where a0000 = '"+a0000+"'");
				
				sess.flush();
			}
			
			//������Ϊ��ְ��Ա��1��������Ա����ְ����Ҫ��Ϊ����,������ְʱ�����
			if(a0163.equals("1")){
				HBUtil.executeUpdate("update a02 set A0255 = '"+1+"',A0265 = '' where a0000 = '"+a0000+"' and a0279 = '"+1+"'");
				sess.flush();
			}
			
		}
		
		
		String tableType = this.getPageElement("type").getValue();
		//String tableType = session.getAttribute("type").toString(); 	//��session��ȡ���б�����
		//�б�
		if("1".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.reloadGrid();");		//ˢ����Ա��Ϣ�б�ҳ��
		}
		//С����
		if("2".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.datashow();");		//ˢ��С����ҳ��
		}
		//��Ƭ
		if("3".equals(tableType)){
			//this.closeCueWindow("changeStateWin");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('changeStateWin').close();");
			this.getExecuteSG().addExecuteCode("window.realParent.picshow();");			//ˢ����Ƭҳ��
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	
	//ƴ��confirm��ʾ
	@PageEvent("changeStateYY")
	@Transaction
	public int changeStateYY()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		
		//ƴ����ʾ�ֶ�
		HttpSession session=request.getSession(); 
		String a0000s = session.getAttribute("a0000s").toString(); 	
		//�������Ķ��Žص�
		a0000s = a0000s.substring(0,a0000s.length()-1);
		String[] values = a0000s.split(",");

		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, values[0]);		//��ѯ��һ������Ϣ 
		String name = "";
		if(a01 != null){
			name = a01.getA0101();			//������� 
		}

		String msg = "ȷ�Ͻ�" + name;

		if(values.length > 1){		//�ж��޸ĸ���״̬����Ա�����Ƿ����1
			msg += "��"+ values.length + "��";
		}
		msg += "����״̬���Ϊ��";
		
		String a0163_combo = this.getPageElement("a0163_combo").getValue();
		
		if(msg != null){			//׷��msg��Ϣ����Ҫ�ı����Ա״̬׷������ʾ 
			msg = msg + a0163_combo+"?";
		}
		
		this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ��','"+msg+"',290,function(id) { if('ok'==id){radow.doEvent('changeState');}else{return false;}});");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
}

   

    

