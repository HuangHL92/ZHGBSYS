package com.insigma.siis.local.pagemodel.publicServantManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class AddPartyTimeAddPagePageModel extends PageModel{
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveDegrees()throws RadowException, AppException{
		
		
		
		String a0141 = this.getPageElement("a0141").getValue();
		String a0144 = this.getPageElement("a0144").getValue();
		String a3921 = this.getPageElement("a3921").getValue();
		String a3927 = this.getPageElement("a3927").getValue();
		
		String a0144All = a0144;//���й���Աʱ��
		String a0141_combo = this.getPageElement("a0141_combo").getValue();//������ò
		String a3921_combo = this.getPageElement("a3921_combo").getValue();//�ڶ�����
		String a3927_combo = this.getPageElement("a3927_combo").getValue();//��������
		String A0140 = "";
//		String a0000 = this.getRadow_parent_data();
//		if(a0000==null||"".equals(a0000)){
//			this.setMainMessage("���ȱ�����Ա������Ϣ��");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(a0141_combo==null || "".equals(a0141_combo) || "����ѡ��...".equals(a0141_combo)){
			a0141="";
		}
		if(a3921_combo==null || "".equals(a3921_combo)|| "����ѡ��...".equals(a3921_combo)){
			a3921="";
		}
		if(a3927_combo==null || "".equals(a3927_combo) || "����ѡ��...".equals(a3927_combo)){
			a3927="";
		}
		if((a0141==null || "".equals(a0141))&& ((a0144!=null && !"".equals(a0144))|| (a3921!=null && !"".equals(a3921))|| (a3927!=null && !"".equals(a3927)))){
			this.setMainMessage("����ѡ��Ϊ������ò��");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			if("02".equals(a0141) || "01".equals(a0141)){
				if((a0144==null || "".equals(a0144))){
					this.setMainMessage("������뵳ʱ�䣡");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String a0107 = this.getPageElement("a0107").getValue();//��������
				if(!StringUtil.isEmpty(a0144)){
					if(a0144.length()==6){
						a0144+="01";
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String now=sdf.format(new Date());
					if(Integer.valueOf(a0144)>Integer.valueOf(now)){
						this.setMainMessage("�뵳ʱ�䲻�����ڵ�ǰʱ��");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}	
				if(a0107!=null&&!"".equals(a0107)){
					if (a0107.length() == 6) {
						a0107 += "01";
					}
					if (a0144.length() == 6) {
						a0144 += "01";
					}
					if(a0107!=null&&!"".equals(a0107)&&a0144!=null&&!"".equals(a0144)){
						int start = Integer.valueOf(a0107);      	//��������
						int end = Integer.valueOf(a0144);			//�뵳ʱ��
						
						//����18���������
						/*String a010718 = a0107.substring(0,4);
						int year18 = Integer.valueOf(a010718) + 18;
						a010718 = String.valueOf(year18) + a0107.substring(4,8);
						start = Integer.valueOf(a010718);*/
						
						
						if (start >= end) {
							this.setMainMessage("�뵳ʱ�䲻�����ڵ��ڳ�������");
							//this.setMainMessage("�뵳ʱ�䲻��С��18��");
							return EventRtnType.NORMAL_SUCCESS;
						}
						
						a0107 = a0107.replace(".", "").substring(0, 6);
						a0144 = a0144.replace(".", "").substring(0, 6);
						
					}
				}
				String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
				if(a3921==null || "".equals(a3921)){
					if((a3927!=null && !"".equals(a3927))){
						this.setMainMessage("����ӵڶ����ɣ�");
						return EventRtnType.NORMAL_SUCCESS;
					} else {
						A0140 = a0144_sj ;
					}
				} else {
					if(a3927!=null && !"".equals(a3927)){
						A0140 = "�й�"+"\n"+a0144_sj+"\n"+a3921_combo+"\n"+a3927_combo;
						//A0140 =  a3921_combo+ "��" + a3927_combo + "(" + a0144_sj +")";
					} else {
						A0140 = "�й�"+"\n"+a0144_sj+"\n"+a3921_combo;
					}
				}
			} else {
				if("02".equals(a3921) || "01".equals(a3921)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("������뵳ʱ�䣡");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if("02".equals(a3927) || "01".equals(a3927)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("������뵳ʱ�䣡");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a3921==null || "".equals(a3921)){
					if((a3927!=null && !"".equals(a3927))){
						this.setMainMessage("����ӵڶ����ɣ�");
						return EventRtnType.NORMAL_SUCCESS;
					} else {
						A0140 = "(" + a0141_combo +")";
					}
				} else {
					if(a3927!=null && !"".equals(a3927)){
						A0140 =  "(" + a0141_combo+ "��" +a3921_combo+ "��" + a3927_combo +")";
					} else {
						A0140 =  "(" + a0141_combo+ "��" +a3921_combo+ ")";
					}
				}
			}
			
		}
		
		if("()".equals(A0140)){
			A0140="";
		}
		
		String a0144_time = "";
		if(a0144 != null && !a0144.equals("")){
			a0144_time = a0144.substring(0,4)+"."+a0144.substring(4,6);
		}
		
		
		
		String first = "";
		String date = "";
		String second = "";
		String third ="";
		date =a0144_time;
		if((a0141.equals("01") || a0141.equals("02"))&&!StringUtil.isEmpty(a3921))//�൳�ɰ����й�
		{			
			first = "�й�";
			second=a3921_combo;
			third=a3927_combo;
		}
		else if(a3921.equals("01") || a3921.equals("02"))//�൳�ɰ����й�
		{
		
			first = "�й�";
			second=a0141_combo;
			third=a3927_combo;
		}else if(a3927.equals("01") || a3927.equals("02"))//�൳�ɰ����й�
		{
			
			first = "�й�";
			second=a0141_combo;
			third=a3921_combo;
		}else if((a0141.equals("01") || a0141.equals("02"))&&StringUtil.isEmpty(a3921)) //�������й�
		{
			
		}
		else//����ߵ����ɲ������й� 
		{
			first = a0141_combo;
			second=a3921_combo;
			third=a3927_combo;
		}
		
		//����ƴ���뵳ʱ��
		StringBuilder A0140New = new StringBuilder();
		if(!StringUtil.isEmpty(first))
		{
			A0140New.append(first);
		}	
		if(!StringUtil.isEmpty(date))
		{   
			if(A0140New.length()!=0)
			A0140New.append("\n");
			A0140New.append(date);
		}
		if(!StringUtil.isEmpty(second))
		{
			if(A0140New.length()!=0)
			A0140New.append("\n");
			A0140New.append(second);
		}
		if(!StringUtil.isEmpty(third))
		{
			if(A0140New.length()!=0)
			A0140New.append("\n");
			A0140New.append(third);
		}
		
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0140').value='"+A0140New.toString()+"'");		//�뵳ʱ��
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0141').value='"+a0141+"'");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a3921').value='"+a3921+"'");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a3927').value='"+a3927+"'");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0144').value='"+a0144All+"'");
		this.getExecuteSG().addExecuteCode("window.close();window.realParent.wrapdiv_a0140onclick()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		
		//this.getExecuteSG().addExecuteCode("lockHTML()");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("v_test('1');");
		this.getExecuteSG().addExecuteCode("v_test('2');");
		this.getExecuteSG().addExecuteCode("v_test('3');");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
