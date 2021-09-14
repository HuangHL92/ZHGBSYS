package com.insigma.siis.local.pagemodel.sysorg.org;


import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.sysorg.org.util.YearCheckServlet;

public class CreateSysOrgPageModel extends PageModel {
	
	/**
	 * ϵͳ������Ϣ
	 */

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String LegalEntityTypeBtn ="0";//��ť1�������� 2���� 3ȷ�� 4ȡ��
	public static String b0194Type="0";//��λ����1��λ 2������� 3����
	public static String opType="0";//1����2�޸�
	public static String tag= "0";//�޸Ķ���0δִ��1��ִ��
	public static String b0101stauts="0";//0δ�ı�1�ı�
	public static String b0104stauts="0";//0δ�ı�1�ı�
	public CreateSysOrgPageModel() throws RadowException{
	}
	
	//ҳ���ʼ��
	@Override
	public int doInit() throws RadowException {
		//this.getExecuteSG().addExecuteCode("getParentUnitId();");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		
		String ids = this.getRadow_parent_data();
		String parentid = "1,";
		if(ids ==null || ids.trim().equals("")) {
			parentid=parentid+"-1";
		}else {
			parentid=parentid+ids.trim();
		}
	
		String parentids[] = parentid.split(",");
		
		B01 b01 =CreateSysOrgBS.LoadB01(parentids[1]);
		if(parentids[0].equals("1")){
			this.opType="1";//1����2�޸�
			this.b0194Type =b01.getB0194();//�������� 1���˵�λ 2������� 3��������
			this.getPageElement("optionGroup").setValue(b01.getB0101());//�ϼ���������
			this.getPageElement("parentb0114").setValue(b01.getB0114());//�ϼ���������
			this.getPageElement("b0121").setValue(parentids[1]);//�ϼ���λ����
			this.getExecuteSG().addExecuteCode("myfunction("+b0194Type+");");

			if("1".equals(b0194Type)){
				this.getExecuteSG().addExecuteCode("Check1();");
				this.getExecuteSG().addExecuteCode("$('#b0194a').attr('checked',true);");
			}else if("2".equals(b0194Type)){
				this.getExecuteSG().addExecuteCode("Check2();");
				this.getExecuteSG().addExecuteCode("$('#b0194b').attr('checked',true);");
			}else{
				this.getExecuteSG().addExecuteCode("Check1();");
				this.getExecuteSG().addExecuteCode("$('#b0194a').attr('checked',true);");
			}
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��������
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntityContinueAddBtn.onclick")
	public int LegalEntityContinueAdd() throws AppException, RadowException {
		String radio = this.getPageElement("b0194").getValue();
		if(radio.equals("LegalEntity")){//����
			this.b0194Type="1";
		}else if(radio.equals("InnerOrg")){//����
			this.b0194Type="2";
		}else{//����
			this.b0194Type="3";
		}
		this.LegalEntityTypeBtn="1";//��ť1�������� 2���� 3ȷ�� 4ȡ��
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntitySaveBtn.onclick")
	public int LegalEntitySave() throws AppException, RadowException {
		String radio = this.getPageElement("b0194").getValue();
		if(radio.equals("LegalEntity")){//���˵�λ
			this.b0194Type="1";
		}else if(radio.equals("InnerOrg")){//�������
			this.b0194Type="2";
		}else{//��������
			this.b0194Type="3";
		}
		this.LegalEntityTypeBtn="2";//��ť1�������� 2���� 3ȷ�� 4ȡ��
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �ر�
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntityCancelBtn.onclick")
	public int LegalEntityCancel() throws AppException, RadowException {
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		this.closeCueWindow("addOrgWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * ��ʼ����
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@PageEvent("sysorg.save")
	public int sysorgsave() throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		HBSession sess = HBUtil.getHBSession();
		sess.beginTransaction();
		//ҳ����Զ�α���,
		String b0111 = this.getPageElement("b0111").getValue().trim();//����id
		//CurrentUser user = SysUtil.getCacheCurrentUser();
		String b0101 = this.getPageElement("b0101").getValue().trim();//��������
		String b0104 = this.getPageElement("b0104").getValue().trim();//���
		String parentid = this.getRadow_parent_data();
		String parentids[] = parentid.split(",");
	
		String b0111new = "";
		try {
			/* 1�������� 2���� 3ȷ�� 4ȡ��*/
			if(!b0111.equals("")){//�ڶ��α�����ڻ���id
				this.opType="2";//2�޸�
			}
			if(!b0111.equals("")&&LegalEntityTypeBtn.equals("1")){//�������֮�����ٵ����������
				this.opType="1";//1����
				this.setNextEventName("reset.onclick");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(opType.equals("1")){//1����
				B01DTO b01 = new B01DTO();
				PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
				//this.getExecuteSG().addExecuteCode("scfj1()");    //�ϴ�����
				
				String historyfile = UUID.randomUUID().toString();
				this.getPageElement("orifileid").setValue(historyfile);
				
				String schemefile = UUID.randomUUID().toString();
				this.getPageElement("orifileid2").setValue(schemefile);
				
				String leaderfile = UUID.randomUUID().toString();
				this.getPageElement("orifileid3").setValue(leaderfile);
				
				
				
				//String historyfile = this.getPageElement("orifileid").getValue();
				b01.setB0242(historyfile);
				b01.setB0243(schemefile);
				b01.setB0244(leaderfile);
				String radio = this.getPageElement("b0194").getValue();
				if("InnerOrg".equals(radio)){//�������
					/*���˵�λ������������ְ�븱ְΪ��ͬ�ֶ�*/
					String b0183_1=this.getPageElement("b0183_1").getValue();//��ְ
					String b0185_1=this.getPageElement("b0185_1").getValue();//��ְ
					
					if(b0183_1!=null&&b0183_1.length()!=0){
						b01.setB0183(Long.parseLong(b0183_1));//��ְ�쵼ְ��
					}
					if(b0185_1!=null&&b0185_1.length()!=0){
						b01.setB0185(Long.parseLong(b0185_1));//��ְ�쵼ְ��
					}
				}
				CreateSysOrgBS.selectSubListByName(b01.getB0101(),b01.getB0121());//У��ͬһ�����£����ܴ���ͬ������
				CreateSysOrgBS.saveOrUpdateB01(b01,b0194Type);
				
				this.getExecuteSG().addExecuteCode("scfj1()");    //�ϴ�����   ��ʷ�ظ�
				//this.getExecuteSG().addExecuteCode("scfj2()");    //��������
				//this.getExecuteSG().addExecuteCode("scfj3()");    //�����쵼
			
				
				saveInfo_Extend(sess,b01.getB0111());
				SysRuleBS.saveUserDept(b01.getB0111());//��ȡ�������ϼ����������л���Ȩ��
				if(LegalEntityTypeBtn.equals("2")){//����
					this.getPageElement("b0101old").setValue(b01.getB0101());
					this.getPageElement("b0104old").setValue(b01.getB0104());
					this.getPageElement("b0111").setValue(b01.getB0111());
				}
				this.request.getSession().setAttribute("tag", "1");
				b0111new = b01.getB0111();
				
			}else if(opType.equals("2")){//2�޸�
				String b0194Type="";
				String radio = this.getPageElement("b0194").getValue();
				B01DTO b01dto = new B01DTO();
				
				try {
					if(radio==null){
						this.setMainMessage("�����쳣!");
						return EventRtnType.NORMAL_SUCCESS;
					}
					if(radio.equals("LegalEntity")){//���˵�λ
						b0194Type="1";
					}else if(radio.equals("InnerOrg")){//�������
						b0194Type="2";
					}else if(radio.equals("GroupOrg")){//��������
						b0194Type="3";
					}else{
						this.setMainMessage("�����쳣!");
						return EventRtnType.NORMAL_SUCCESS;
					}
					//����������Լ�������ʾ����������
					B01 b01 = CreateSysOrgBS.LoadB01(b0111);
					
					CreateSysOrgBS.selectListByNameForUpdate(b0101,b0111);////У��ͬһ�����£����ܴ���ͬ������
					PropertyUtils.copyProperties(b01dto, b01);
					PMPropertyCopyUtil.copyElementsValueToObj(b01dto, this);
					if(radio.equals("InnerOrg")){//�������
						/*���˵�λ������������ְ�븱ְΪ��ͬ�ֶ�*/
						String b0183_1=this.getPageElement("b0183_1").getValue();//��ְ
						String b0185_1=this.getPageElement("b0185_1").getValue();//��ְ
						
						if(b0183_1!=null&&b0183_1.length()!=0){
							b01dto.setB0183(Long.parseLong(b0183_1));//��ְ�쵼ְ��
						}
						if(b0185_1!=null&&b0185_1.length()!=0){
							b01dto.setB0185(Long.parseLong(b0185_1));//��ְ�쵼ְ��
						}
					}
					String b0101old = this.getPageElement("b0101old").getValue();
					String b0104old = this.getPageElement("b0104old").getValue();
					String isstauts = this.getPageElement("isstauts").getValue();
					if(LegalEntityTypeBtn.equals("2")){//����
						this.getPageElement("b0101old").setValue(b01.getB0101());
						this.getPageElement("b0104old").setValue(b01.getB0104());
						this.getPageElement("b0111").setValue(b01.getB0111());
					}
					if(b0101old.equals(b0101)){//�������ƣ�Ϊ�ı�
						this.b0101stauts="0";
					}else{
						this.b0101stauts="1";
					}
					if(b0104old.equals(b0104)){//��������Ϊ�ı�
						this.b0104stauts="0";
					}else{
						this.b0104stauts="1";
					}
					if(this.b0101stauts.equals("0")&&this.b0104stauts.equals("0")||isstauts.equals("1")||b0194Type.equals("3")){
						CreateSysOrgBS.saveOrUpdateB01(b01dto,b0194Type);
						saveInfo_Extend(sess,b01.getB0111());
						this.getPageElement("isstauts").setValue("0");
						this.getPageElement("b0101old").setValue(b01.getB0101());
						this.getPageElement("b0104old").setValue(b01.getB0104());
					}else{
						sess.connection().rollback();
						this.openWindow("updateNameWin", "pages.sysorg.org.SysOrgUpdateName");
						String parent_data= b01.getB0111()+","+b0101stauts+","+b0104stauts+","+b01dto.getB0101()+","+b01dto.getB0104();
						this.setRadow_parent_data(parent_data);
						return EventRtnType.NORMAL_SUCCESS;
					}

				} catch (Exception e) {
					try{
						if(sess!=null){
							sess.connection().rollback();
						}
					}catch(Exception e1){
						e1.printStackTrace();
					}
					throw new RadowException(e.toString());
				}		

			}else{
				this.setMainMessage("�����쳣!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(LegalEntityTypeBtn.equals("1")){
				this.setNextEventName("reset.onclick");
				this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			}else if(LegalEntityTypeBtn.equals("2")){
				this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			}else{
				//�����½�������������
				if(parentids.length==3 && parentids[2].equals("imp_create")){
					this.closeCueWindowByYes("addOrgImpWin");
					this.createPageElement("b0111new", ElementType.HIDDEN, true).setValue(b0111new);
					this.createPageElement("info", ElementType.TEXTAREA, true).setValue("�½�ƥ�������Ϣ"
							+"\nƥ�������" + b0101 +"\n�������룺" + this.getPageElement("b0114").getValue());
					this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
				} else {
					this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
					this.closeCueWindowByYes("addOrgWin");
					this.closeCueWindowByYes("updateOrgWin");
				}
			}
			sess.connection().commit();
			//ˢ�¸�ҳ���б�
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('orgInfoGrid.dogridquery')");
			this.setMainMessage("����ɹ�");
		} catch (Exception e) {
			try{
				if(sess!=null){
					sess.connection().rollback();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			throw new RadowException(e.toString());
		}		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//���ð�ť
	@PageEvent("reset.onclick")
	@NoRequiredValidate  
	@Transaction
	@OpLog
	public int resetonclick()throws RadowException, AppException {
		//this.getPageElement("b0111").setValue("");//��������
		this.getPageElement("b0114").setValue("");//���˵�λ����(�����������)
		this.getPageElement("b0101").setValue("");//��������
		this.getPageElement("b0104").setValue("");//�������
		this.getPageElement("b0117").setValue("");//������������ ZB01
		this.getPageElement("b0124").setValue("");//��λ������ϵ ZB87
		this.getPageElement("b0131").setValue("");//����������� ZB04 
		this.getPageElement("b0127").setValue("");//�������� ZB03   ��
		
		this.getPageElement("b0183").setValue("0");//��ְ�쵼ְ��
		this.getPageElement("b0185").setValue("0");//��ְ�쵼ְ��
		//this.getPageElement("b0227").setValue("0");//����������   ��
		//this.getPageElement("b0232").setValue("0");//��ҵ������(�ι�)
		//this.getPageElement("b0233").setValue("0");//��ҵ������(����)
		//this.getPageElement("b0236").setValue("0");//���ڱ�����
		//this.getPageElement("b0234").setValue("0");//����������
		
		this.getPageElement("b0240").setValue("");//��������
		this.getPageElement("b0241").setValue("");//ʵ������
		//this.getPageElement("b0242").setValue("");//��ʷ�ظ�
		//this.getPageElement("b0243").setValue("");//�������� 
		//this.getPageElement("b0244").setValue("");//�����쵼
		this.getPageElement("b0246").setValue("0");
		this.getPageElement("b0256").setValue("0");
		this.getPageElement("b0247").setValue("0");
		this.getPageElement("b0257").setValue("0");
		this.getPageElement("b0248").setValue("0");
		this.getPageElement("b0258").setValue("0");
		this.getPageElement("b0249").setValue("0");
		this.getPageElement("b0259").setValue("0");
		//this.getPageElement("b0250").setValue("0");
		//this.getPageElement("b0260").setValue("0");
		
		this.getPageElement("b0150").setValue("0");//�����쵼ְ��
		this.getPageElement("b0183_1").setValue("0");//��ְ�쵼ְ��
		this.getPageElement("b0185_1").setValue("0");//��ְ�쵼ְ��
		//bo238�Զ����ǩ������2��ֵ
		this.getPageElement("b0238").setValue("");//���չ���Ա����������ʱ��
		this.getPageElement("b0238_1").setValue("");//���չ���Ա����������ʱ��
		this.getPageElement("b0239").setValue("");//���չ���Ա�����������ĺ�
		this.getPageElement("b0180").setValue("");//��ע
		
		/*�����ֶ�*/
		this.getPageElement("b0101old").setValue("");
		this.getPageElement("b0104old").setValue("");
		this.getPageElement("b0111").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//Ч��
	@PageEvent("validation.result")
	public int validation() throws RadowException{
		String b0111 =this.getPageElement("b0111").getValue();
		String b0121 =this.getPageElement("b0121").getValue();//�ϼ���λ����
		String b0114 =this.getPageElement("b0114").getValue();//���˵�λ���������飩����
		if(b0194Type.equals("3")){
			//ͨ��B0111���Ƿ�����Ա���������Ա��ʾת��
			String str = CreateSysOrgBS.validationGroup(b0111);
			if(!"0".equals(str)){
				this.setMainMessage("���������²���������Ա������ת����Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(b0114.trim().equals("")){
			this.setMainMessage("������������룡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//122.a23.aaa.11a ......
		if(!java.util.regex.Pattern.matches("^[0-9a-zA-Z]{3}(\\.{1}[0-9a-zA-Z]{3}){0,}$",b0114)){
			this.setMainMessage("�������벻�Ϸ�,���������룡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!b0194Type.equals("2")){
		}else{
			List list = HBUtil.getHBSession().createSQLQuery("select t.b0194 from B01 t where t.b0121 = '"+b0111+"'").list();
			for(int i=0;i<list.size();i++){
				String b0194 = (String) list.get(i);
				if(b0194.equals("1")||b0194.equals("3")){
					this.setMainMessage("��������²������½����˵�λ��������飡");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		if(!b0121.equals("-1")){
			B01 b01 = CreateSysOrgBS.LoadB01(this.getPageElement("b0121").getValue());
			if(b01!=null){
				String b0194 = b01.getB0194();//��λ��ʶ
				if(b0194.equals("2")){
					if(b0194Type.equals("1")||b0194Type.equals("3")){
						this.setMainMessage("��������²������½����˵�λ��������飡");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
		}/*else if(b0121.equals("-1")){//�ϼ�����Ϊ���صĸ����������½��ĵ�һ���������������������
			if(b0194Type.equals("2")){//�����������½��������
				this.setMainMessage("�������������Ϊ������!");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}*/
		B01 b01 =new B01();
		PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
		//���չ���Ա�����������ĺ� ��������
		if(b01.getB0239()!=null&&b01.getB0239().length()>24){
			this.setMainMessage("���չ���Ա�����������ĺų��ȹ���(������ҪС��25)��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(b01.getB0101().equals("")){
			this.setMainMessage("������������ƣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(b01.getB0104().equals("")){
//			this.setMainMessage("�������ƣ�");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(b0194Type.equals("1")){//���˵�λ
			if(b01.getB0114().trim().equals("")){
				this.setMainMessage("������������룡");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0117())){//��������
				this.setMainMessage("��ѡ������������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0124())){//������ϵ
				this.setMainMessage("��ѡ��������ϵ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0131())){//�������
				this.setMainMessage("��ѡ��������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0127())){//��������
				this.setMainMessage("��ѡ���������");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else if(b0194Type.equals("2")){//�������
			if(b01.getB0114().trim().equals("")){
				this.setMainMessage("����������������룡");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String b0183_1=this.getPageElement("b0183_1").getValue();
			String b0185_1=this.getPageElement("b0185_1").getValue();
			if(b0185_1==null||b0185_1.length()==0){
				b0185_1="0";
			}
			if(b0183_1==null||b0183_1.length()==0){
				b0183_1="0";
			}
			if(b01.getB0150()==null){
				b01.setB0150(0l);
			}
			if(b01.getB0150()<(Long.parseLong(b0185_1)+Long.parseLong(b0183_1))){
				this.setMainMessage("�쵼ְ��Ӧ�ô��ڵ�����ְ�븱ְ֮��!");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else{
			if(this.getPageElement("b0114").getValue().equals("")&&b0121.length()<7){
				this.setMainMessage("���������������룡");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		this.setNextEventName("sysorg.save");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	private void readInfo_Extend(String b0111) throws AppException {
//		List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from B01_EXT where b0111='"+b0111+"'",null);
//			if(Info_Extends!=null&&Info_Extends.size()>0){//
//				Map<String,String> entity = Info_Extends.get(0);
//				for(String key : entity.keySet()){
//					try {
//						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
//					} catch (Exception e) {
//					}
//				}
//			}else{
//				DBType cueDBType = DBUtil.getDBType();
//				List<String> list =null;
//				if(cueDBType==DBType.MYSQL){
//					list = HBUtil.getHBSession().createSQLQuery("select COLUMN_NAME from information_schema.COLUMNS where table_name = 'b01_ext' and  column_name!='B0111'").list();
//				}else{
//					list = HBUtil.getHBSession().createSQLQuery("SELECT column_name FROM all_tab_cols WHERE  table_name = UPPER('b01_ext')  and  column_name!='B0111'").list();
//				}
//				for(int i=0;i<list.size();i++){
//					try {
//						this.getPageElement(list.get(i).toLowerCase()).setValue("");
//					} catch (Exception e) {
//					}
//				}
//			}
//	}
	
	private void saveInfo_Extend(HBSession sess, String b0111) throws Exception {

		List<Object[]> list = CreateSysOrgBS.getB01ExtSQL();
		Map<String,String> field = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for(Object[] os : list){
				field.put(os[1].toString(), os[2].toString());
			}
		}
		sess.flush();
		
		List<Object> Info_Extends = sess.createSQLQuery("select b0111 from b01_ext where b0111='"+b0111+"'").list();
		
		if(field.size()>0){//����չ�ֶ�
			if(Info_Extends==null||Info_Extends.size()==0){//����
				StringBuffer insert_sql = new StringBuffer("insert into b01_ext(b0111,");
				StringBuffer values = new StringBuffer(" values('"+b0111+"',");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						insert_sql.append(key+",");
						values.append("?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add "+key+" varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add "+key+" varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext."+key+" is '"+comment+"'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							} catch (Exception e1) {
							}
						}
					}catch (Exception e) {
					}
				}
				insert_sql.deleteCharAt(insert_sql.length()-1).append(")");
				values.deleteCharAt(values.length()-1).append(")");
				insert_sql.append(values);
				HBUtil.executeUpdate(insert_sql.toString(), args);
			}else{//�޸�
				StringBuffer update_sql = new StringBuffer("update b01_ext set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						update_sql.append(key+"=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add "+key+" varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add "+key+" varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext."+key+" is '"+comment+"'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							} catch (Exception e1) {
							}
						}
						
					}catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length()-1).append(" where b0111='"+b0111+"'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}
			
		}
	}
	

}

