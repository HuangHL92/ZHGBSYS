package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;

public class CreateTemplatePageModel extends PageModel{
	
	public static String tpid = "";
	public CreateTemplatePageModel(){

	}
	
	@Override
	public int doInit() throws RadowException {
//		String tpname = this.request.getSession().getAttribute("tpname").toString();
		String tpname = (String)this.request.getSession().getAttribute("tpname");
		this.getPageElement("isedit").setValue((this.request.getSession().getAttribute("isedit")).toString());
//		System.out.println((this.request.getSession().getAttribute("temtype")).toString());
		this.getPageElement("temtype").setValue((this.request.getSession().getAttribute("temtype")).toString());
		if(!"1".equals((this.request.getSession().getAttribute("temtype")).toString())){
			String sql = "select tpid from listoutput2 where tpname='"+tpname+"' group by tpid";
			try{
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
				while(rs.next()){
					this.getPageElement("tpname").setValue(rs.getString(1));
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else{
			this.getPageElement("tpname").setValue(tpname);
		}
		this.getPageElement("tpkind").setValue((this.request.getSession().getAttribute("tpkind")).toString());
		this.getExecuteSG().addExecuteCode("pageinit();");
		this.request.getSession().removeAttribute("isphoto");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//������������
	/*	@PageEvent("openDataVerifyWin")
		public void  openDataVerifyWin(String xmlDoc) throws RadowException{
			System.out.println(xmlDoc);
			//����һ���µ��ַ���
	        StringReader read = new StringReader(xmlDoc);
	        //�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
	        InputSource source = new InputSource(read);
	        //����һ���µ�SAXBuilder
	        SAXBuilder sb = new SAXBuilder();
	        try {
	            //ͨ������Դ����һ��Document
	            Document doc = sb.build(source);
	            //ȡ�ĸ�Ԫ��
	            Element root = doc.getRootElement();
	            System.out.println(root.getName());//�����Ԫ�ص����ƣ����ԣ�
	            //�õ���Ԫ��������Ԫ�صļ���
	            List jiedian = root.getChildren();
	            Namespace ns = root.getNamespace();
	            System.out.println(jiedian);
	            Element et = null;
//	            for(int i=0;i<jiedian.size();i++){
//	                et = (Element) jiedian.get(i);//ѭ�����εõ���Ԫ��
//	               System.out.println(et);
	//
//	            }
	           //���Worksheet�ڵ�
	            et = (Element) jiedian.get(1);
	            //���Worksheet�ڵ��µ��ӽڵ�Table�ڵ�
	            List Table = et.getChildren();
	            //��ȡTable�ڵ�
	            et = (Element) Table.get(0);
	            System.out.println(et.getAttributeValue("Index", ns));
	            //��ȡTable�ڵ��µ��ӽڵ�row�ڵ�
	            List row = et.getChildren();
	            //���row>0��˵��������
	            if(row.size()>0){
	            	for(int i =0;i<row.size();i++){}
	            	//��ȡrow�ڵ�
	            	et = (Element) row.get(0);
	            	System.out.println(et.getAttributeValue("Index", ns)+"�б�");
	            	//��ȡrow�ڵ�������ӽڵ�
	            	List cell = et.getChildren();
	            	System.out.println(cell.size());
	            	for(int j=0;j<cell.size();j++){
	            		//��ȡcell�ڵ�
	            		et = (Element) cell.get(j);
	            		System.out.println(et.getAttributeValue("Index", ns)+"�б�");
	            		System.out.println(et.getChild("Data",ns).getText()+"����");
	            		//��ȡcell�ڵ�������ӽڵ�Data
	            		List Data = et.getChildren();
	            		//��ȡData�ڵ�
//	            		et = (Element) Data.get(0);
//	            		System.out.println(et.getChild("Data",ns).getText());
	            	}
	            	
	            }else{
	            	System.out.println("������");
	            }
	            
	            
	            
	        } catch (JDOMException e) {
	            // TODO �Զ����� catch ��
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO �Զ����� catch ��
	            e.printStackTrace();
	        }
			
			
			
			
		}*/
	
	/**
	 * ��ģ������������yyyy��MM��
	 * @throws RadowException
	 */
	public String addCurDate() throws RadowException{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��");
		String date = sdf.format(cal.getTime());
		return date;
	}
	/*
	 * ���н���
	 * xmlDoc �����xml�ַ�������
	 * TPname   ģ������
	 * TPtype  ģ������
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String xmlDoc) throws RadowException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String time = sf.format(date);
        time = time.substring(0,time.length()-1);
//		System.out.println(xmlDoc);
		//����һ���µ��ַ���
		StringReader read = new StringReader(xmlDoc);
		//�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
		InputSource source = new InputSource(read);
		//����һ���µ�SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		//һ������������mapȷ����k��1�У�2�У�3���ݣ�v���б꣬�б����ݣ�
		//Map<String, String> map = new HashMap<String, String>();
		try {
			//ͨ������Դ����һ��Document
			Document doc = sb.build(source);
			//ȡ�ĸ�Ԫ��
			Element root = doc.getRootElement();
			//�����Ԫ�ص����ƣ����ԣ�
//			System.out.println(root.getName());
			//�õ���Ԫ��������Ԫ�صļ���
			List jiedian = root.getChildren();
			Namespace ns = root.getNamespace();
			int n = 0;
			n = jiedian.size();
			String uuid = UUID.randomUUID().toString();
			int p = 1;
			int a = 0;//����ɾ���Ĵ�������jiexi��������
			String TPname = this.getPageElement("tname").getValue();
			String houzhui = (String)request.getSession().getAttribute("namehouzhui");
			if(houzhui != null && (!TPname.contains("�����")&&!TPname.contains("����׼���᡿")&&!TPname.contains("�������᡿"))){
				TPname += houzhui;
				request.getSession().removeAttribute("namehouzhui");
			}
			if(houzhui != null && "�������᡿".equals(houzhui)){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��");
				String date1 = sdf.format(cal.getTime());
				TPname = date1+TPname;
			}
			String TPname1 = this.getPageElement("tpname").getValue();
			String isedit = this.getPageElement("isedit").getValue();
			String tpkind = this.getPageElement("tpkind").getValue();
			if("1".equals(isedit)){
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput t where t.tpname='"+TPname1+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("ģ�����ظ��������������");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput2 t where t.tpname='"+TPname+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("ģ�����ظ��������������");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			for(int m = 0;m < jiedian.size()-1;m++ ){
				//list������map
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				n--;
				Element et = null;
				//���Worksheet�ڵ�
				et = (Element) jiedian.get(n);
				 //��ȡҳ��
	            String page = et.getAttributeValue("Name", ns);
	            String PageNu = n+"";
				//���Worksheet�ڵ��µ��ӽڵ�Table�ڵ�
				List Table = et.getChildren();
				//��ȡTable�ڵ�
				et = (Element) Table.get(0);
				//��ȡTable�ڵ��µ��ӽڵ�row�ڵ�
				List row = et.getChildren();
				//������������ƴ���ַ���
				String messageE = "";
				String MessageA = "";
				//���row>0��˵��������
				if (row.size() > 0) {
					//ѭ������row��ȡÿһ��row
					for (int i = 0; i < row.size(); i++) {
						//��ȡrow�ڵ�
						et = (Element) row.get(i);
						//��ȡ��ǩ���������
						String row1 = et.getAttributeValue("Index", ns);
						//1��ʾ��
						//��ȡrow�ڵ�������ӽڵ�
						List cell = et.getChildren();
						for (int j = 0; j < cell.size(); j++) {
							//��ȡcell�ڵ�
							et = (Element) cell.get(j);
							//��ȡ��ǩ���������
							String cell1 = et.getAttributeValue("Index", ns);
							//2��ʾ��
							//��ȡ������Ϣ
							String message = et.getChild("Data", ns).getText();
							//�ж������ǲ�����Ҫ�ı�������ݣ�<>�ڵ�������Ҫ���浽���ݿ�ģ�
							if (message.contains("<")) {
								//��ȡ������ȥ������ >
								String Message1 = message.substring(message.indexOf("<"), message.lastIndexOf(">"));
								//��>���н�ȡ
								String[] split1 = Message1.replace("<", "").split(">");
								for(int q = 0; q < split1.length ; q++){
									//���������ȡ���ݽ���ƴ���ַ���
									MessageA += split1[q]+",";
									//��ȡÿһ����Ϣ��
									String Message = split1[q];
									//ÿһ����Ϣ���붨�����Ϣ�н���ƥ�䣬��ȡȥ��Ϣ��Ӣ��֮��ƴ��Ӣ����Ϣ��
									
/*									if("����".equals(Message)){
										messageE += "a01.a0101,"; 
									}else if("���壨����".equals(Message)){
										messageE += "mzm,";
									}else if("�Ա�����".equals(Message)){
										messageE += "xbm,";
									}else if("��Ա״̬".equals(Message)){
										messageE += "ryzt,";
									}else if("��Ա���".equals(Message)){
										messageE += "a01.a0160,";
									}else if("���֤��".equals(Message)){
										messageE += "a01.a0184,";
									}else if("�Ա�".equals(Message)){
										messageE += "a01.a0104a,";
									}else if("��������".equals(Message)){//??0k
										messageE += "csny,";
									}else if("��������".equals(Message)){
										messageE += "a01.a0107,";
									}else if("����".equals(Message)){//??ok
										messageE += "nl,";
									}else if("��Ƭ".equals(Message)){//??ok
										messageE += "zp,";
									}else if("����".equals(Message)){   
										messageE += "a01.a0117a,";
									}else if("����".equals(Message)){ 
										messageE += "a01.a0111a,";
									}else if("������".equals(Message)){
										messageE += "a01.a0114a,";
									}else if("��һ����".equals(Message)){
										messageE += "a01.a0141,";
									}else if("�뵳ʱ��".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("�ڶ�����".equals(Message)){
										messageE += "dedp,";
									}else if("��������".equals(Message)){
										messageE += "dsdp,";
									}else if("�μӹ���ʱ��".equals(Message)){
										messageE += "a01.a0134,";
									}else if("����״��".equals(Message)){
										messageE += "a01.a0128,";
									}else if("רҵ����ְ��".equals(Message)){
										messageE += "a01.a0196,";
									}else if("��Ϥרҵ�к�ר��".equals(Message)){
										messageE += "a01.a0187a,";
									}else if("ѧ��".equals(Message)){//-----------------
										messageE += "zgxl,";
									}else if("��ҵѧУ��ѧ����".equals(Message)){
										messageE += "zgxlbyxx,";
									}else if("��ѧרҵ��ѧ����".equals(Message)){
										messageE += "zgxlsxzy,";
									}else if("��ѧʱ�䣨ѧ����".equals(Message)){
										messageE += "zgxlrxsj,";
									}else if("��ҵʱ�䣨ѧ����".equals(Message)){
										messageE += "zgxlbisj,";
									}else if("ѧλ".equals(Message)){
										messageE += "zgxw,";
									}else if("��ҵѧУ��ѧλ��".equals(Message)){
										messageE += "zgxwbyxx,";
									}else if("��ѧרҵ��ѧλ��".equals(Message)){
										messageE += "zgxwsxzy,";
									}else if("��ѧʱ�䣨ѧλ��".equals(Message)){
										messageE += "zgxwrxsj,";
									}else if("��ҵʱ�䣨ѧλ��".equals(Message)){
										messageE += "zgxwbisj,";
									}else if("ѧ����ȫ���ƣ�".equals(Message)){//------OK
										messageE += "xlqrz,";
									}else if("��ѧʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "rxsjqrz,";
									}else if("��ҵʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "bysjqrz,";
									}else if("ѧλ��ȫ���ƣ�".equals(Message)){//OK
										messageE += "xwqrz,";
									}else if("��ҵѧУ��ȫ����ѧ����".equals(Message)){//ok
										messageE += "xxjyxql,";
									}else if("��ҵѧУ��ȫ����ѧλ��".equals(Message)){//ok
										messageE +="xxjyxqw,";
									}else if("��ѧרҵ��ȫ����ѧ����".equals(Message)){//??ok
										messageE +="sxzyql,";
									}else if("��ѧרҵ��ȫ����ѧλ��".equals(Message)){//??ok
										messageE +="sxzyqw,";
									}else if("ѧ������ְ��".equals(Message)){//ok
										messageE +="xlzz,";
									}else if("��ѧʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="rxsjzz,";
									}else if("��ҵʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="bysjzz,";
									}else if("ѧλ����ְ��".equals(Message)){//ok
										messageE +="xwzz,";
									}else if("��ҵѧУ����ְѧ����".equals(Message)){//??ok
										messageE +="xxjyxzl,";
									}else if("��ҵѧУ����ְѧλ��".equals(Message)){//??ok
										messageE +="xxjyxzw,";
									}else if("��ѧרҵ����ְѧ����".equals(Message)){//??ok
										messageE +="sxzyzl,";
									}else if("��ѧרҵ����ְѧλ��".equals(Message)){//??ok
										messageE +="sxzyzw,";
									}else if("ȫ����ѧ���������".equals(Message)){//??ok
										messageE += "qrzxlrb,";
									}else if("ȫ����ѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "qrzxlxxrb,";
									}else if("ȫ����ѧλ�������".equals(Message)){//??ok
										messageE += "qrzxwrb,";
									}else if("ȫ����ѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "qrzxwxxrb,";
									}else if("��ְѧ���������".equals(Message)){//ok
										messageE += "zzxlrb,";
									}else if("��ְѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "zzxixxrb,";
									}else if("��ְѧλ�������".equals(Message)){//ok
										messageE += "zzxwrb,";
									}else if("��ְѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "zzxwxxrb,";
									}else if("������λ��ְ��ȫ��".equals(Message)){
										messageE += "a01.a0192a,";
									}else if("������λ��ְ�񣨼�".equals(Message)){
										messageE += "a01.a0192,";
									}else if("ͳ�ƹ�ϵ���ڵ�λ".equals(Message)){
										messageE += "a01.a0195,";//
									}else if("������ְ��".equals(Message)){//??""
										messageE += "jgwpx,";//
									}else if("ѡ�����÷�ʽ".equals(Message)){
										messageE += "a02.a0247,";
									}else if("��ְ�ĺ�".equals(Message)){
										messageE += "a02.a0245,";
									}else if("�Ƿ��쵼ְ��".equals(Message)){
										messageE += "a02.a0219,";//
									}else if("��������".equals(Message)){
										messageE += "a02.a0201a,";
									}else if("ְ������".equals(Message)){
										messageE += "a02.a0216a,";
									}else if("��ְ����".equals(Message)){//?? o k
										messageE += "zwcc,";
									}else if("��ְʱ��".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("����ְ����ʱ��".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("���ʱ�����Ŵ���".equals(Message)){//??ok
										messageE += "ccsjkhcl,";//-------/
									}else if("ְ������".equals(Message)){
										messageE += "a02.a0251,";
									}else if("��ְʱ��".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("���㹤����������".equals(Message)){
										messageE += "jcnx,";
									}else if("����".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("�������".equals(Message)){
										messageE += "a01.a14z101,";
									}else if("��ȿ��˽��".equals(Message)){ 
										messageE += "a01.a15z101,";
									}else if("��ע".equals(Message)){
										messageE += "a01.a0180,";
									}else if("��ν����ͥ��Ա��".equals(Message)){//??ok
										messageE += "cw,";
									}else if("��������ͥ��Ա��".equals(Message)){//??ok
										messageE += "xm,";
									}else if("�������£���ͥ��Ա��".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("���䣨��ͥ��Ա��".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("������ò����ͥ��Ա��".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("������λ��ְ�񣨼�ͥ��Ա��".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("���뱾��λ��ʽ".equals(Message)){
										messageE += "a29.a2911,";
									}else if("���뱾��λ����".equals(Message)){
										messageE += "a29.a2907,";
									}else if("ԭ��λ����".equals(Message)){
										messageE += "a29.a2921a,";
									}else if("��ԭ��λְ��".equals(Message)){
										messageE += "a29.a2941,";
									}else if("��ԭ��λְ����".equals(Message)){
										messageE += "a29.a2944,";
									}else if("���빫��Ա����ʱ��".equals(Message)){
										messageE += "a29.a2947,";
									}else if("����Ա�Ǽ�ʱ��".equals(Message)){
										messageE += "a29.a2949,";
									}else if("����ְ��".equals(Message)){
										messageE += "a53.a5304,";
									}else if("����ְ��".equals(Message)){
										messageE += "a53.a5315,";
									}else if("��������".equals(Message)){
										messageE += "a53.a5317,";
									}else if("�ʱ���λ".equals(Message)){
										messageE += "a53.a5319,";
									}else if("���ʱ��".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("�����".equals(Message)){
										messageE += "a53.a5327,";
									}else if("��������ʱ��".equals(Message)){
										messageE += "a53.a5321,";
									}else if("�˳�����ʽ".equals(Message)){
										messageE += "a30.a3001,";
									}else if("������λ".equals(Message)){
										messageE += "a30.a3007a,";
									}else if("�˳�����ʱ��".equals(Message)){
										messageE += "a30.a3004,";
									}else if("�˳���λ".equals(Message)){
										messageE += "a01.orgid,";
									}else if("�޸���".equals(Message)){
										messageE += "a01.xgr,";
									}else if("�޸�����".equals(Message)){
										messageE += "a01.xgsj,";
									}else if("��ǰ����".equals(Message)){//??ok
										messageE += "dqsj,";//----
									}else if("��ǰ�û���".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
*/								
									if("����".equals(Message)){
										messageE += "a0101-a01.a0101,"; 
									}else if("��Ա״̬".equals(Message)){
										messageE += "a0163-select code_name from code_value where  code_type = 'ZB126' and code_value = (select a0163 from a01 where a0000 = 'id' ) ',";
									}else if("��Ա���".equals(Message)){
										messageE += "a0160-select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='id',";
									}else if("���֤��".equals(Message)){
										messageE += "a0184-a01.a0184,";
									}else if("�Ա�".equals(Message)){
										messageE += "a0104-select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='id' and cv.code_value=a.a0104,";
									}else if("��������".equals(Message)){//??0k
										messageE += "a0107-substr(a01.a0107,1,4)||'.'||substr(a01.a0107,5,2),";
									}else if("����".equals(Message)){//??ok
										messageE += "nl,";
									}else if("��Ƭ".equals(Message)){//??ok
										messageE += "zp,";
									}else if("����".equals(Message)){   
										messageE += "a0117-select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='id' and cv.code_value=a.a0117,";
									}else if("����".equals(Message)){ 
										messageE += "a0111a-a01.a0111a,";
									}else if("������".equals(Message)){
										messageE += "a0114a-a01.a0114a,";
									}else if("��һ����".equals(Message)){
										messageE += "a0141-select cv.code_name from a01 a01,code_value cv where a01.a0000 ='id' and cv.code_type='GB4762' and cv.code_value=a01.a0141,";
									}else if("�뵳ʱ��".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("�ڶ�����".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("��������".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("�μӹ���ʱ��".equals(Message)){
										messageE += "a0134-substr(a01.a0134,1,4)||'.'||substr(a01.a0134,5,2),";
									}else if("����״��".equals(Message)){
										messageE += "a0128-a01.a0128,";
									}else if("רҵ����ְ��".equals(Message)){
										messageE += "a0196-a01.a0196,";
									}else if("��Ϥרҵ�к�ר��".equals(Message)){
										messageE += "a0187a-a01.a0187a,";
									}else if("ѧ��".equals(Message)){//-----------------
										messageE += "a0801a-select a08.a0801a from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ҵѧУ��ѧ����".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ѧרҵ��ѧ����".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ѧʱ�䣨ѧ����".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ҵʱ�䣨ѧ����".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("ѧλ".equals(Message)){
										messageE += "a0901a-select a08.a0901a from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ҵѧУ��ѧλ��".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ѧרҵ��ѧλ��".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ѧʱ�䣨ѧλ��".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ҵʱ�䣨ѧλ��".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("ѧ����ȫ���ƣ�".equals(Message)){//------OK
										messageE += "QRZXL-a01.QRZXL,";
									}else if("��ѧʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '' group by a0804,";
									}else if("��ҵʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = 'id' group by a0807,";
									}else if("ѧλ��ȫ���ƣ�".equals(Message)){//OK
										messageE += "QRZXW-a01.QRZXW,";
									}else if("��ҵѧУ��ȫ����ѧ����".equals(Message)){//ok
										messageE += "a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ҵѧУ��ȫ����ѧλ��".equals(Message)){//ok
										messageE +="a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ��ȫ����ѧ����".equals(Message)){//??ok
										messageE +="a0824-select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ��ȫ����ѧλ��".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("ѧ������ְ��".equals(Message)){//ok
										messageE +="ZZXL-a01.ZZXL,";
									}else if("��ѧʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = 'id' group by a0804,";
									}else if("��ҵʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = 'id' group by a0807,";
									}else if("ѧλ����ְ��".equals(Message)){//ok
										messageE +="ZZXW-a01.ZZXW,";
									}else if("��ҵѧУ����ְѧ����".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ҵѧУ����ְѧλ��".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ����ְѧ����".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ����ְѧλ��".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("ȫ����ѧ���������".equals(Message)){//??ok
										messageE += "QRZXL-a01.QRZXL,";
									}else if("ȫ����ѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "QRZXLXX-a01.QRZXLXX,";
									}else if("ȫ����ѧλ�������".equals(Message)){//??ok
										messageE += "QRZXW-a01.QRZXW,";
									}else if("ȫ����ѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "QRZXWXX-a01.QRZXWXX,";
									}else if("��ְѧ���������".equals(Message)){//ok
										messageE += "ZZXL-a01.ZZXL,";
									}else if("��ְѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "ZZXLXX-a01.ZZXLXX,";
									}else if("��ְѧλ�������".equals(Message)){//ok
										messageE += "ZZXW-a01.ZZXW,";
									}else if("��ְѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "ZZXWXX-a01.ZZXWXX,";
									}else if("������λ��ְ��ȫ��".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("������λ��ְ�񣨼�".equals(Message)){
										messageE += "a0192-a01.a0192,";
									}else if("�ǼǺ�����ְ��".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("�ǼǺ���������".equals(Message)){
										messageE += "a0120-a01.a0120,";
									}else if("ͳ�ƹ�ϵ���ڵ�λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a01.a0000= a02.a0000 ) a on  a.a0195 = a02.a0201b  where a02.a0000 = 'id',";//
									}else if("ѡ�����÷�ʽ".equals(Message)){
										messageE += "a0247-select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = 'id',";
									}else if("��ְ�ĺ�".equals(Message)){
										messageE += "a0245-a02.a0245,";
									}else if("�Ƿ��쵼ְ��".equals(Message)){
										messageE += "a0219-a02.a0219,";//
									}else if("��������".equals(Message)){
										messageE += "a0201a-select a02.a0201a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("ְ������".equals(Message)){
										messageE += "a0216a-select a02.a0216a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("��ְ����".equals(Message)){//?? o k=========================================
										messageE += "a0221-select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = 'id' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09',";
									}else if("��ְʱ��".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("����ְ����ʱ��".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("ְ������".equals(Message)){
										messageE += "a0251-a02.a0251,";
									}else if("��ְʱ��".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("���㹤����������".equals(Message)){
										messageE += "a0197-select (case when a0197 = '1' then '��' else '��' end ) from a01 where a0000 = 'id',";
									}else if("����".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("�������".equals(Message)){
										messageE += "a14z101-a01.a14z101,";
									}else if("��ȿ��˽��".equals(Message)){ 
										messageE += "a15z101-a01.a15z101,";
									}else if("��ע".equals(Message)){
										messageE += "a0180-a01.a0180,";
									}else if("��ν����ͥ��Ա��".equals(Message)){//??ok
										messageE += "cw,";
									}else if("��������ͥ��Ա��".equals(Message)){//??ok
										messageE += "xm,";
									}else if("�������£���ͥ��Ա��".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("���䣨��ͥ��Ա��".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("������ò����ͥ��Ա��".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("������λ��ְ�񣨼�ͥ��Ա��".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("���뱾��λ��ʽ".equals(Message)){
										messageE += "a2911-select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = 'id',";
									}else if("���뱾��λ����".equals(Message)){
										messageE += "a2907-a29.a2907,";
									}else if("ԭ��λ����".equals(Message)){
										messageE += "a2921a-a29.a2921a,";
									}else if("��ԭ��λְ��".equals(Message)){
										messageE += "a2941-a29.a2941,";
									}else if("��ԭ��λְ����".equals(Message)){
										messageE += "a2944-a29.a2944,";
									}else if("���빫��Ա����ʱ��".equals(Message)){
										messageE += "a2947-a29.a2947,";
									}else if("����Ա�Ǽ�ʱ��".equals(Message)){
										messageE += "a2949-a29.a2949,";
									}else if("����ְ��".equals(Message)){
										messageE += "a5304-a53.a5304,";
									}else if("����ְ��".equals(Message)){
										messageE += "a5315-a53.a5315,";
									}else if("��������".equals(Message)){
										messageE += "a5317-a53.a5317,";
									}else if("�ʱ���λ".equals(Message)){
										messageE += "a5319-a53.a5319,";
									}else if("���ʱ��".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("�����".equals(Message)){
										messageE += "a5327-a53.a5327,";
									}else if("�˳�����ʽ".equals(Message)){
										messageE += "a3001-select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = 'id',";
									}else if("������λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = 'id' group by a02.a0201a,";
									}else if("�˳�����ʱ��".equals(Message)){
										messageE += "a3004-a30.a3004,";
									}else if("�˳���λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = 'id' group by a02.a0201a,";
									}else if("�޸���".equals(Message)){
										messageE += "xgr-a01.xgr,";
									}else if("�޸�����".equals(Message)){
										messageE += "xgsj,";
									}else if("��ǰ����".equals(Message)){//??ok
										messageE += "dqsj,";//----?????
									}else if("��ǰ�û���".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
									 								
								}
								//��ȡ�������ݰ�ǰ��<>ȥ��
							//	String Message = message.substring(1, message.length() - 1);
								//3��ʾ����
								Map<String, String> map = new HashMap<String, String>();
								//1��ʾ��
								map.put("1", row1);
								//2��ʾ��
								map.put("2", cell1);
								//3��ʾ����
								MessageA =	MessageA.substring(0, MessageA.length()-1);
								map.put("3", MessageA);
								//Ӣ��������
//									System.out.println("44------------>"+messageE);
									if(messageE == null || "".equals(messageE)){
										this.setMainMessage("����ʧ�ܣ�");
										return EventRtnType.NORMAL_SUCCESS;
									}else {
										messageE =	messageE.substring(0, messageE.length()-1);
									}
								
								map.put("4", messageE);
								list.add(map);
								 messageE = "";
								 MessageA = "";
								List Data = et.getChildren();
								//��ȡData�ڵ�
								//et = (Element) Data.get(0);
								//System.out.println(et.getChild("Data",ns).getText());
							}
						}
					}
					if("1".equals(isedit)){
						jiexi(list,TPname1,uuid,PageNu,"2",tpkind,"1",a);
						a++;
					}else{
						jiexi(list,TPname,uuid,PageNu,"2",tpkind,"2",a);
						a++;
					}
				} else {
					System.out.println("������");
				}
				p++;
			}
			this.getPageElement("savescript").setValue(uuid);
			tpid = uuid;
			this.getExecuteSG().addExecuteCode("tishi()");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.remove(thistab.tabid);");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (JDOMException e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		} catch (IOException e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		} catch (SQLException e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
	}
	
	

	public void jiexi(List list,String TPname,String TPID,String PageNu,String TPType,String TPKind,String type,int a ) {
		int tn = 0;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String sql = "";
		PreparedStatement Stemt = null;
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String tpname = "";
		try {
		if("1".equals(type)){
			String tptype = "";
			String endtime = "";
			String tpkind = "";
//			System.out.println("---->"+TPname);
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpname,tptype,endtime,tpkind from listoutput2 where tpid='"+TPname+"'").executeQuery();
			while(res.next()){
				tpname = res.getString(1);
				tptype = res.getString(2);
				endtime = res.getString(3);
				tpkind = res.getString(4);
			}
			try {
				if(a == 0){
					HBUtil.executeUpdate("delete from listoutput where tpid='"+TPname+"'");
				}
				
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,PageNu,MessageE,TPName,TPID,TPType,EndTime,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, PageNu);
				Stemt.setString(5, MessageE);
				Stemt.setString(6, tpname);
				Stemt.setString(7, TPname);
				Stemt.setString(8, tptype);
				Stemt.setString(9, endtime);
				Stemt.setString(10, tpkind);
				Stemt.setString(11, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}else{
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,TPName,TPID,PageNu,TPType,MessageE,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, TPname);
				Stemt.setString(5, TPID);
				Stemt.setString(6, PageNu);
				Stemt.setString(7, TPType);
				Stemt.setString(8, MessageE);
				Stemt.setString(9, TPKind);
				Stemt.setString(10, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template(user_template_id,tpid,userid) values('"+uuid+"','"+TPID+"','"+userid+"')";
			HBUtil.getHBSession().connection().prepareStatement(sql1).executeUpdate();
			if (tn % 200 != 0) {
				Stemt.executeBatch();
				Stemt.clearBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (Stemt != null) {
					Stemt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e) {
			}

		}
//		System.out.println("�������");
	}

	@PageEvent("uploadfile")
	public int uploadfile() throws RadowException{
		String savePath = "";
		if(this.request.getSession().getAttribute("tpid")!=null&&!"0".equals(this.request.getSession().getAttribute("tpid").toString())){
			String tpid = this.request.getSession().getAttribute("tpid").toString().replace("|", "").replace("|", "");
//			System.out.println("----->"+tpid);
			savePath = request.getSession().getServletContext().getRealPath("/template")+"/"+tpid+".cll";
		}else{
			savePath = request.getSession().getServletContext().getRealPath("/template")+"/"+tpid+".cll";
		}
		try {
//			System.out.println("save----->"+savePath);
			File file = new File(savePath);
			file.createNewFile();
			ServletInputStream in = request.getInputStream();
			FileOutputStream out = new FileOutputStream(savePath);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("success");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
