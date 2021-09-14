package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;




import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddPersonPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	/**
	 * ��Ա������Ϣ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int savePerson(String confirm)throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		
		
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		
		//����Ա������λ��ְ����Ϣ�����жϣ�У��;��Ĭ������Ϊ��ְ��Ա��
		//��ְ��Ա��������һ��������ְ��
		//����ְ��Ա����һ����ְ��
		//���ԣ�����Ҫ��һ�����ְ��
		
		String sqlOne = null;
		String msg = null;
		if(a01.getA0163().equals("1")){					//��Ա����״̬Ϊ1����ְ��Ա
			msg = "��ְ��Ա������һ��������ְ��";
			sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//���Ρ���ְ���ְ��
		}else{			//����ְ��Ա
			msg = "����ְ��Ա������һ����ְ��";
			sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//��ְ���ְ��
		}
		
		
		String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //���ְ��
		List<A02> listOut = sess.createQuery(sqlOut).list();
		if(listOut.size() == 0){
			this.setMainMessage("����Ҫ��һ�����ְ��");
			return EventRtnType.FAILD;
		}
		
		
		List<A02> list = sess.createQuery(sqlOne).list();
		if(list.size() == 0){
			this.setMainMessage(msg);
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//�μӹ���ʱ�䲻��С�ڳ�������
		String a0134 = a01.getA0134();//�μӹ���ʱ��
		String a0107 = a01.getA0107();//��������
		if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a0107.length()==6){
				a0107 += "00";
			}
			if(a0134.compareTo(a0107)<=0){
				this.setMainMessage("�μӹ���ʱ�䲻��С�ڵ��ڳ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//ר��a0187a
		String a0187a = this.getPageElement("a0187a").getValue();
		if(a0187a != null || "".equals(a0187a)){
			if(a0187a.length() > 60){
				this.setMainMessage("ר�����ܳ���60�֣�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		String tbsj = this.getPageElement("tbsj").getValue();
		/*if(IdCardManageUtil.trueOrFalseIdCard(a01.getA0184()))
			a01.setAge(Long.valueOf(IdCardManageUtil.getAge(a01.getA0184())));*/
		//HBSession sess = null;
		A01 a01_old = null;
		JSONObject jsonbject = null;
		try {
			
			
			String idcard = a01.getA0184();//���֤�� ����У��//�����֤�����һλxת��Ϊ��д�ַ� add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
						this.setMainMessage("ϵͳ���Ѵ��ھ�����ͬ���֤������Ա,���޸ģ�");
						return EventRtnType.NORMAL_SUCCESS;
			}
			
			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//ƴ�����
			
			//�Կ��ܴ��ڿո����ݵ��ı�����ȥ�մ���
			a01.setA0101(this.getPageElement("a0101").getValue().replaceAll("\\s*", ""));
			a01.setComboxArea_a0114(this.getPageElement("comboxArea_a0114").getValue().replaceAll("\\s*", ""));
			a01.setA0114(this.getPageElement("a0114").getValue().replaceAll("\\s*", ""));
			a01.setQrzxlxx(this.getPageElement("qrzxlxx").getValue().replaceAll("\\s*", ""));
			a01.setQrzxwxx(this.getPageElement("qrzxwxx").getValue().replaceAll("\\s*", ""));
			a01.setZzxlxx(this.getPageElement("zzxlxx").getValue().replaceAll("\\s*", ""));
			a01.setZzxwxx(this.getPageElement("zzxwxx").getValue().replaceAll("\\s*", ""));
			
			if(a0000==null||"".equals(a0000)){
				//ְ��Ϊ��  1��ְ��Ա2������Ա3������Ա4��ȥ��5������Ա
				a01.setA0163("1");
				this.getPageElement("a0163").setValue("1");
				sess.save(a01);	
			}else{
				a01_old = (A01)sess.get(A01.class, a0000);
				if("4".equals(a01.getStatus())){//�������ʱ���ݣ�����ʱ״̬��Ϊ��ְ��Ա  ��־��Ϊ����
					a01.setStatus("1");
					String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
					List<String> list2 = sess.createSQLQuery(sql2).list();
					for(int i=0;i<list2.size();i++){
						CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
					}
					this.getPageElement("status").setValue(a01.getStatus());
					//new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A01(), a01)).start();
					
					applog.createLog("31", "A01", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A01(), a01));
				}else{
					a01.setXgr(null);
					a01.setXgsj(null);
					a01_old.setXgr(null);
					a01_old.setXgsj(null);
					//new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a01_old, a01)).start();
					
					applog.createLog("32", "A01", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a01_old, a01));
					//a01.setXgr(SysManagerUtils.getUserId());
					//a01.setXgsj(DateUtil.getTimestamp().getTime());
				}
				
				
				//������Ϣ��Ҫ���
				a01.setA0115a(a01_old.getA0115a());			//�ɳ���
				a01.setA0122(a01_old.getA0122()); 			//רҵ�����๫��Ա��ְ�ʸ�
				a01.setA0120(a01_old.getA0120());  			//����
				a01.setA2949(a01_old.getA2949()); 			//����Ա�Ǽ�ʱ��
				a01.setA0197(a01_old.getA0197()); 			//�����������ϻ��㹤������
				
				PropertyUtils.copyProperties(a01_old, a01);
				//������ʽ��
				StringBuffer originaljl = new StringBuffer("");
				String jianli = formatJL(a01_old.getA1701(),originaljl);
				jsonbject = objectToJson(a01_old);
				//a01_old.setA1701(originaljl.toString());
				this.getPageElement("a1701").setValue(jianli);
				sess.update(a01_old);	
				//this.getExecuteSG().addExecuteCode("document.getElementById('xgsj').value="+a01_old.getXgsj()+";");
			}
			
			
			
			//ͨ��a0000��ѯ����������Ϣ����A99Z1����
			A99Z1 a99Z1 = new A99Z1();
			this.copyElementsValueToObj(a99Z1, this);
			
			if(a99Z1.getA99z101() == null || a99Z1.getA99z101().equals("")){		//"�Ƿ�¼"���Ϊ�գ�������Ϊ0����
				a99Z1.setA99z101("0");
			}
			if(a99Z1.getA99z103() == null || a99Z1.getA99z103().equals("")){		//"�Ƿ�ѡ����"���Ϊ�գ�������Ϊ0����
				a99Z1.setA99z103("0");
			}
			
			//�ж�¼��ʱ�䣺��������ڽ��бȽϣ�һ��Ӧ����18���ꡣ
			//String a0107 = a01.getA0107();//��������
			String a99z102 = a99Z1.getA99z102();//¼��ʱ��
			if(a0107!=null&&!"".equals(a0107)&&a99z102!=null&&!"".equals(a99z102)){
				int age = getAgeNew(a99z102,a0107);
				if(age<18){
					this.setMainMessage("¼��ʱ����������ڽ��бȽϣ�Ӧ����18���꣡");
					return EventRtnType.FAILD;
				}
			}
			
			//�жϽ���ѡ����ʱ�䣺��������ڽ��бȽϣ�һ��Ӧ����18���ꡣ
			
			String a99z104 = a99Z1.getA99z104();//����ѡ����ʱ��
			if(a0107!=null&&!"".equals(a0107)&&a99z104!=null&&!"".equals(a99z104)){
				int age = getAgeNew(a99z104,a0107);
				if(age<18){
					this.setMainMessage("����ѡ����ʱ����������ڽ��бȽϣ�Ӧ����18���꣡");
					return EventRtnType.FAILD;
				}
			}
			
			
			a99Z1.setA0000(a0000);
			A99Z1 a99Z1_old = null;
			if("".equals(a99Z1.getA99Z100())){
				a99Z1.setA99Z100(null);
				a99Z1_old = new A99Z1();
				/*applog.createLog("3531", "A99Z1", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));*/
				applog.createLogNew("3A99Z1","ѡ��������¼��Ϣ����","ѡ��������¼��Ϣ��",a0000,a01.getA0101(),new Map2Temp().getLogInfo(new A99Z1(),a99Z1));
				
			}else{
				a99Z1_old = (A99Z1)sess.get(A99Z1.class, a99Z1.getA99Z100());
				/*applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));*/
				applog.createLogNew("3A99Z1","ѡ��������¼��Ϣ�޸�","ѡ��������¼��Ϣ��",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a99Z1_old,a99Z1));
			}
			PropertyUtils.copyProperties(a99Z1_old, a99Z1);
			
			sess.saveOrUpdate(a99Z1_old);
			this.getPageElement("a99Z100").setValue(a99Z1_old.getA99Z100());
			
			
			
			
			//�����޸ı���ʱ��ҳ��������Ա�������������title
			this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");
			a0000 = a01.getA0000();
			Integer rowLength = Integer.valueOf(this.getPageElement("rowLength").getValue());//����
			StringBuffer sb = new StringBuffer("{");
			for(int i=1;i<=rowLength;i++){
				A36 a36 = new A36();
				String a3600 = this.getPageElement("a3600_"+i).getValue();
				String a3604a = this.getPageElement("a3604a_"+i+"_combo").getValue();		//��ν����
				String a3601 = this.getPageElement("a3601_"+i).getValue().replaceAll("\\s*", "");//��ͥ��Ա����ȥ�� 
				String a3607 = this.getPageElement("a3607_"+i).getValue();
				String a3627 = this.getPageElement("a3627_"+i+"_combo").getValue();			//������òid
				String a3611 = this.getPageElement("a3611_"+i).getValue().replaceAll("\\s*", "");//��ͥ��Ա����ȥ�� 
				
				//if(a3600 != null && !a3600.equals("")){
				if((a3604a != null && !a3604a.equals("")) || (a3601 != null && !a3601.equals("")) || (a3607 != null && !a3607.equals(""))
						|| (a3627 != null && !a3627.equals("")) || (a3611 != null && !a3611.equals(""))){
					
					//��Ϣ��֤
					if(a3604a == null || "".equals(a3604a)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա��ν����Ϊ�գ�',null,220);");
						//this.setMainMessage("��ͥ��Ա��ν����Ϊ�գ�");
						return EventRtnType.NORMAL_SUCCESS;
					}
					if(a3601 == null || "".equals(a3601)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա��������Ϊ�գ�',null,220);");
						//this.setMainMessage("��ͥ��Ա��������Ϊ�գ�");
						return EventRtnType.NORMAL_SUCCESS;
					}
					/*if(a3607 == null || "".equals(a3607)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա�������²���Ϊ�գ�',null,220);");
						//this.setMainMessage("��ͥ��Ա�������²���Ϊ�գ�");
						return EventRtnType.NORMAL_SUCCESS;
					}*/
					if(a3627 == null || "".equals(a3627)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա������ò����Ϊ�գ�',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					if(a3611 == null || "".equals(a3611)){
						this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա������λ��ְ����Ϊ�գ�',null,220);");
						//this.setMainMessage("��ͥ��Ա������λ��ְ����Ϊ�գ�");
						return EventRtnType.NORMAL_SUCCESS;
					}
					
				}
				
				sb
				.append("a3604a_"+i+":'"+a3604a+"',")
				.append("a3601_"+i+":'"+a3601+"',")
				.append("a3607_"+i+":'"+a3607+"',")
				.append("a3627_"+i+":'"+a3627+"',")
				.append("a3611_"+i+":'"+a3611+"',");
				a36.setA0000(a0000);
				
				
				//ȫ����Ϣȥ������ɾ��
				if((a3604a==null||"".equals(a3604a))&&
						(a3601==null||"".equals(a3601))&&
						(a3607==null||"".equals(a3607))&&
						(a3627==null||"".equals(a3627))&&
										(a3611==null||"".equals(a3611))){
					sb.append("a3600_"+i+":'',");
					this.getPageElement("a3600_"+i).setValue("");
					if(a3600==null||"".equals(a3600)){
						
						continue;
					}else{
						a36.setA3600(a3600);
						//new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A36(), new A36())).start();
						applog.createLog("3363", "A36", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A36(), new A36()));
						sess.delete(a36);
						continue;
					}
					
				}
				
				a36.setA3604a(a3604a);
				a36.setA3601(a3601);
				a36.setA3607(a3607);
				a36.setA3627(a3627);
				a36.setA3611(a3611);
				a36.setSortid(BigDecimal.valueOf((long)i));
				if(a3600==null||"".equals(a3600)){
					a36.setA3600(null);
					sess.save(a36);
					sess.flush();
					//new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A36(), a36)).start();
					applog.createLog("3361", "A36", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A36(), a36));
					this.getPageElement("a3600_"+i).setValue(a36.getA3600());
					
				}else{
					a36.setA3600(a3600);
					A36 a36_old = (A36)sess.get(A36.class, a3600);
					//new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a36_old, a36)).start();
					applog.createLog("3362", "A36", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a36_old, a36));
					PropertyUtils.copyProperties(a36_old, a36);
					sess.update(a36_old);
				}
				sb.append("a3600_"+i+":'"+a36.getA3600()+"',");
			}
			for(int i=rowLength+1;i<=30;i++){
				sb.append("a3600_"+i+":'',")
				.append("a3604a_"+i+":'',")
				.append("a3601_"+i+":'',")
				.append("a3607_"+i+":'',")
				.append("a3627_"+i+":'',")
				.append("a3611_"+i+":'',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");
			sess.flush();			
			//��ֵ �ж��Ƿ��޸�
			
			
			// ��ͥ��Ա
			String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
			List lista36 = sess.createQuery(sqla36).list();
			Integer rowLength2 = lista36.size();//����
			
			//��ֵ��ҳ��
			this.getPageElement("total").setValue(rowLength2.toString());
			
			
			String json = jsonbject.toString();
			this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");
			//this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("�������Ϣ����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0000").setValue(a01_old.getA0000());//����ɹ���id���ص�ҳ���ϡ�
		this.getPageElement("age").setValue(a01_old.getAge()+"");
		//this.getPageElement("xgr").setValue(a01_old.getXgr());
		//this.getExecuteSG().addExecuteCode("window.parent.parent.Ext.getCmp('persongrid').getStore().reload();");//ˢ����Ա�б�
		this.setRadow_parent_data(a01_old.getA0000());
		//�жϸ�ҳ���Ƿ�����Ա��Ϣά���б�ҳ�棬�� �����¼����б�
		this.getExecuteSG().addExecuteCode("try{" +
				"if(parent.document.getElementById('I'+thisTab.initialConfig.personListTabId)){" +
				"	var personListWindow = parent.document.getElementById('I'+thisTab.initialConfig.personListTabId).contentWindow;" +
				"	personListWindow.Ext.getCmp('persongrid').store.reload();}" +
				"}catch(e){} "
				);
		
		this.getExecuteSG().addExecuteCode(" if(document.getElementById('Iorthers')){"+
					"var orthersWindow = document.getElementById('Iorthers').contentWindow;"+
					"if(orthersWindow){"+
					"	orthersWindow.radow.doEvent('saveOthers.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('ϵͳ��ʾ','����ɹ�!');" +
						"}" +
					"" +
					"}"+
				"}else if(document.getElementById('IBusinessInfo')){"+
					"var BusinessInfoWindow = document.getElementById('IBusinessInfo').contentWindow;"+
					"if(BusinessInfoWindow){"+
					"	BusinessInfoWindow.radow.doEvent('save.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('ϵͳ��ʾ','����ɹ�!');" +
						"}" +
					"" +
					"}"+
				"}else{" +
				"	if('true'=='"+confirm+"'){" +
					"window.parent.tabs.remove(thisTab.tabid);" +
					"}else{" +
						"parent.$h.alert('ϵͳ��ʾ','�������Ϣ����ɹ�!',null,220);" +
					"}" +
				"}");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("printFalse")
	public int printFalse(String a0000)throws AppException, RadowException, IOException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			this.setMainMessage("���ȱ�����Ա��Ϣ");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		pdfView2(a0000+",false");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * ��PDFԤ������
	 * 
	 * @param a0000AndFlag a0000 ����ԱID���� flag���Ƿ��ӡ��������Ϣ��ƴ�ӵĲ������ö��ŷָ�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @throws IOException 
	 * @date 2016-06-03
	 */
	@PageEvent("printView")
	public int pdfView2(String a0000AndFlag) throws RadowException, AppException, IOException{
		String[] params = a0000AndFlag.split(",");
		String a0000 = params[0]; 										//��ԱID
		Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
		String pdfPath = "";  											//pdf�ļ�·��
		
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add(a0000);
		List<String> pdfPaths = null;
		try {
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2, params);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
		QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.getPageElement("pdfPath").setValue(newPDFPath);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,1,'"+ctxPath+"')");
		/*List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		
		//this.setRadow_parent_data(pdfPath);
		//this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");
		this.getPageElement("pdfPath").setValue(pdfPath);
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,'"+a0000+"',ctxPath)");
		//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+pdfPath+"')");
	*/	return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��Ա������Ϣ���� ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveOthers.onclick")
	@Transaction
	@Synchronous(true)
	public int saveOthers()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//��������� id���ɷ�ʽΪassigned
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			sess.saveOrUpdate(a29);	
			
			//�����Ᵽ��	id���ɷ�ʽΪuuid �� ��������� ��id����Ϊnull
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
			}
			sess.saveOrUpdate(a53);
			this.getPageElement("a5300").setValue(a53.getA5300());
			
			//סַͨѶ���� id���ɷ�ʽΪassigned
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			sess.saveOrUpdate(a37);	
			
			//���˱��� id���ɷ�ʽΪassigned
			A31 a31 = new A31();
			this.copyElementsValueToObj(a31, this);
			sess.saveOrUpdate(a31);	
			
			//�˳������� id���ɷ�ʽΪassigned
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			sess.saveOrUpdate(a30);	
			sess.flush();
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('persongrid').getStore().reload()");//ˢ����Ա�б�
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * a0184���֤����֤
	 * 
	 */
	//@PageEvent("a0184.onblur")
	@NoRequiredValidate
	public int a0184onblur(String v)throws RadowException, AppException{
		String idcardno = this.getPageElement("a0184").getValue().toUpperCase();//�����֤�����һλxת��Ϊ��д�ַ� add by lizs 20161110
		if(idcardno==null || idcardno.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!IdCardManageUtil.trueOrFalseIdCard(idcardno)){
			this.setMainMessage("���֤��ʽ����");
		}else{
			this.getPageElement("a0107").setValue(IdCardManageUtil.getBirthdayFromIdCard(idcardno));//��������
			this.getPageElement("a0104").setValue(IdCardManageUtil.getSexFromIdCard(idcardno));//�Ա�
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//�ܳ�
		//25����һ�С�
		int oneline = 100000000;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//���� 50���ֽ�����ƫ�ƣ�ֱ���㹻Ϊֹ��
    		boolean hass = false;
    		while((end+offset)<llength){//25����һ�У����з��ָ�
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//25����һ�е�����50���ֽ� ������
    				loffset++;
    				if((end+offset+loffset)>llength){//�����ܳ��� �˳�ѭ��
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//���ܻ����һ��51���ֽڣ���ǰ��һ��
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				//jlsb.append(l);
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				//jlsb.append("                  ").append(l);
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			//jlsb.append(line2);
    			jlsb.append(line2).append("\r\n");
    		}else{
    			//jlsb.append("                  ").append(line2);
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	@Override
	@Transaction
	public int doInit() throws RadowException {
		
		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		if(a0000==null||"".equals(a0000)||"add".equals(a0000)){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//ͨ��"nowNumber"�Ƿ���ֵ�����ж� ��Ա���·�ҳ ��ť�Ƿ���ʾ
		Object obj = this.request.getSession().getAttribute("nowNumber");
		if(obj==null || "".equals(obj.toString())){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('lastp').hide();odin.ext.getCmp('nextp').hide();");
		}
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			String sql = "from A01 where a0000='"+a0000+"'";
			List list = sess.createQuery(sql).list();
			A01 a01 = (A01) list.get(0);	
			
			//�ж��Ƿ����޸�Ȩ�ޡ�c.type������Ȩ������(0�������1��ά��)
			String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' ";
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' ";
			List elist = sess.createSQLQuery(editableSQL).list();		//�Ƿ������Ȩ�ޣ���ֵ���ʾ�������Ȩ��(Ŀǰ����Ȩ�޵���ƣ����Ȩ���ж������壬���³��򽫲����������жϣ�2017-9-20)
			List elist2 = sess.createSQLQuery(editableSQL2).list();		//�Ƿ���ά��Ȩ�ޣ���ֵ���ʾ����ά��Ȩ��
/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
			String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
			if(type == null || !type.contains("'")){			//���typeΪ�գ����ʾ��ǰ�û��У��������ά��Ȩ��
				type ="'zz'";//�滻�������ݣ��������elist3��ѯ�޽������ʾ���û��е�ǰ������Ա�Ĺ������ά��Ȩ��
			}
			List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
			if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
				this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
						+ "document.getElementById('isUpdate').value = '2'"
						);
			}
			if(elist2==null||elist2.size()==0){//��ά��Ȩ��
				/*if(elist!=null&&elist.size()>0){//�����Ȩ��
					this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
							+ "document.getElementById('isUpdate').value = '2'"
							);
				}else{
					//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
					if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
						this.getExecuteSG().addExecuteCode("buttonDisabled=true;$h.setDisabled($h.disabledButtons.a01);"
								+ "document.getElementById('isUpdate').value = '2'"
								);
					}
					
				}*/
			}
			
			
				
			//���������ʽ
			String a1701 = a01.getA1701();//����
			this.getPageElement("jlnr").setValue(a1701);
			a01.setA1701(formatJL(a1701,new StringBuffer("")));
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			JSONObject jsonbject = objectToJson(a01);
			a01.setA1701(a1701);
			sess.update(a01);
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//�������� ���ġ�
				}
			}
			
			this.getPageElement("comboxArea_a0114").setValue(a01.getComboxArea_a0114());
			//��Ƭ
			this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+a0000+"'");
			A57 a57 = (A57)sess.get(A57.class, a0000);
			if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
				//Blob img = a57.getPhotodata();
				//InputStream inStream = img.getBinaryStream();
				String photourl = a57.getPhotopath();
				File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
				if(fileF.isFile()){
					long nLen = fileF.length();
					String imageSize = nLen/1024 + "K";
					this.getExecuteSG().addExecuteCode("document.getElementById('personImg').alt='��Ƭ("+imageSize+")'");
				}
			}
			
			//A99Z1�е���Ϣ��
			String sqlA99Z1 = "from A99Z1 where a0000='" + a0000 + "'";
			List listA99Z1 = sess.createQuery(sqlA99Z1).list();
			if (listA99Z1 != null && listA99Z1.size() > 0) {
				A99Z1 a99Z1 = (A99Z1) listA99Z1.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(a99Z1, this);		//���ݸ�ֵ��ҳ��
			}
			
			
			// ��ͥ��Ա
			String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
			List lista36 = sess.createQuery(sqla36).list();
			
			Integer rowLength = lista36.size();//����
			
			
			//��ֵ��ҳ��
			//this.getExecuteSG().addExecuteCode("document.getElementById('Total').alt='��Ƭ("+imageSize+")'");
			this.getPageElement("total").setValue(rowLength.toString());
			
			if(rowLength>10){
				for(int i=0;i<rowLength-10;i++){
					this.getExecuteSG().addExecuteCode("addA36row();");
				}
			}
			StringBuffer sb = new StringBuffer("{");
			if(lista36!=null&&rowLength>0){
				for(int i=1;i<=rowLength;i++){
					A36 a36 = (A36) lista36.get(i-1);		
					this.getPageElement("a3600_"+i).setValue(a36.getA3600());
					this.getPageElement("a3604a_"+i).setValue(a36.getA3604a());
					this.getPageElement("a3601_"+i).setValue(a36.getA3601());
					this.getPageElement("a3607_"+i).setValue(a36.getA3607());
					
					//������ʾʱ��
					String showTime = a36.getA3607();
					if(showTime != null && !showTime.equals("") && showTime.length() > 4){
						showTime = a36.getA3607().substring(0,4) + "." + a36.getA3607().substring(4,6);
					}
					this.getPageElement("a3607_"+i+"F").setValue(showTime);
					
					this.getPageElement("a3627_"+i).setValue(a36.getA3627());
					this.getPageElement("a3611_"+i).setValue(a36.getA3611());
					//this.getExecuteSG().addExecuteCode("a3607_"+i+"onblur();");
					sb.append("a3600_"+i+":'"+a36.getA3600()+"',")
					.append("a3604a_"+i+":'"+a36.getA3604a()+"',")
					.append("a3601_"+i+":'"+a36.getA3601()+"',")
					.append("a3607_"+i+":'"+a36.getA3607()+"',")
					.append("a3607_"+i+"F:'"+a36.getA3607()+"',")
					.append("a3627_"+i+":'"+a36.getA3627()+"',")
					.append("a3611_"+i+":'"+a36.getA3611()+"',");
				}
			}
			
			for(int i=rowLength+1;i<=30;i++){
			
				this.getPageElement("a3600_"+i).setValue("");
				this.getPageElement("a3604a_"+i).setValue("");
				this.getPageElement("a3601_"+i).setValue("");
				this.getPageElement("a3607_"+i).setValue("");
				this.getPageElement("a3607_"+i+"F").setValue("");
				this.getPageElement("a3627_"+i).setValue("");
				this.getPageElement("a3611_"+i).setValue("");
				
				
				sb.append("a3600_"+i+":'',")
				.append("a3604a_"+i+":'',")
				.append("a3601_"+i+":'',")
				.append("a3607_"+i+":'',")
				.append("a3607_"+i+"F:'',")
				.append("a3627_"+i+":'',")
				.append("a3611_"+i+":'',");
			}
			
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");
			genResume();
			
			//��ֵ �ж��Ƿ��޸�
			
			String json = jsonbject.toString();
			this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");
			this.getExecuteSG().addExecuteCode("document.getElementById('a0163Divid').innerHTML='"+(HBUtil.getCodeName("ZB126", (a01.getA0163()==null?"":a01.getA0163())))+"';" );
			
			//������־��¼
			LogMain logmain = new LogMain();
			logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//����id
			logmain.setUserlog(SysManagerUtils.getUserName()); 	//�����û����˴�Ϊ�û���¼������
			logmain.setSystemoperatedate(new Date()); 			//ϵͳ����ʱ��
			logmain.setEventtype("������Ա��Ϣ¼��ҳ��"); 		//����
			logmain.setEventobject("���������Ϣ"); 				//����������
			logmain.setObjectid(a0000); 						//�����漰�������
			logmain.setObjectname(a01.getA0101());   			//��ǰ�������漰���������
			sess.save(logmain);
			sess.flush();
			
			
			//���ͳ�����ڵ�λΪ-1����ҳ�治��ʾ
			if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		
		
		this.getExecuteSG().addExecuteCode("bhua();");
		
		this.setRadow_parent_data(a0000);
		this.getExecuteSG().addExecuteCode("selectchange();" +
				"var orthersWindow = document.getElementById('Iorthers');"
				+ "setVisiable();" +
				"if(orthersWindow){" +
					"orthersWindow.contentWindow.radow.doEvent('doInit');" +
				"}");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("compose")
	@NoRequiredValidate
	public int compose() throws RadowException{
		String a1701 = this.getPageElement("a1701").getValue();
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��|-]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}"); 
				Matcher matcher = pattern.matcher(jl);
				if(matcher.find()){
					if(i==0){
						jlsb.append(jl);
					}else{
						jlsb.append("\r\n").append(jl);
					}
				}else if(i==jianli.length-1){
					jlsb.append("\r\n").append(jl);
				}else{
					jlsb.append(jl);
				} 
			}
			this.getPageElement("a1701").setValue(jlsb.toString());
		}
			
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��|-]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}[ ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//�����ڿ�ͷ  (һ��)
		        		jlsb.append(line1);
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	//originaljl.append(jl);
			        	originaljl.append(jl).append("\r\n");//һ�μ�������ƴ�ϻس�
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			if(jlsb.lastIndexOf("\r\n")==jlsb.length()-2 ){
				jlsb.delete(jlsb.length()-2, jlsb.length());
        	}
			return jlsb.toString();
			
		}
		return a1701;
	}



	/**
	 * ���ɼ���
	 */
	@PageEvent("genResume")
	@NoRequiredValidate
	public int genResume() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		//�Զ����ɼ���
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlA02 = "from A02 where a0000='"+a0000+"'";
			List<A02> listA02 = sess.createQuery(sqlA02).list();
			Collections.sort(listA02, new Comparator<A02>(){
				@Override
				public int compare(A02 o1, A02 o2) {
					String o1sj = o1.getA0243()==null?"":o1.getA0243();//��ְʱ��
					String o2sj = o2.getA0243()==null?"":o2.getA0243();//��ְʱ��
					if("".equals(o1sj)){
						return -1;
					}else if("".equals(o2sj)){
						return 1;
					}else{
						if(o1sj.length()>=6){
							String d1 = o1sj.substring(0,6);
							String d2 = o2sj.substring(0,6);
							return d1.compareTo(d2);
						}
						
					}
					return 0;
				}
			});
			StringBuffer sb = new StringBuffer("");
			if(listA02!=null&&listA02.size()>0){
				
				for(int i=0;i<listA02.size();i++){
					A02 a02 = listA02.get(i);
					A02 a02Next = new A02();
					if(listA02.size()>i+1){
						a02Next = listA02.get(i+1);
					}
					String a0201a = a02.getA0201a()==null?"":a02.getA0201a();//��ְ����
					//String a03015 = a02.getA0216a()==null?"":a02.getA0216a();//ְ������
					String a03015 = a02.getA0215a()==null?"":a02.getA0215a();	//ְ������
					
					String a0203 = a02.getA0243()==null?"":a02.getA0243();//��ְʱ��
					String a0255 = a02.getA0255()==null?"":a02.getA0255();//��ְ״̬
					String a0265 = a02.getA0265()==null?"":a02.getA0265();//��ְʱ��
					
					B01 b01 = null;
					if(a02.getA0201b()!=null){
						b01 = (B01)sess.get(B01.class, a02.getA0201b());
					}
					
					if(b01 != null){//����ƴ�ӹ��� �� ������λ��ְ��ȫ�� ���� ƴ��һ��
						String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
						if("2".equals(b0194)){//2���������
							while(true){
								b01 = (B01)sess.get(B01.class, b01.getB0121());
								if(b01==null){
									break;
								}else{
									b0194 = b01.getB0194();
									if("2".equals(b0194)){//2���������
										a0201a = b01.getB0101()+a0201a;
									}else if("3".equals(b0194)){//3����������
										continue;
									}else if("1".equals(b0194)){//1�����˵�λ
										a0201a = b01.getB0101()+a0201a;
										break;
									}else{
										break;
									}
								}
							}
						}
					}
					
					
					
					String a0203Next = null;//a02Next.getA0243()==null?"":a02Next.getA0243();//��ְʱ��
					if("1".equals(a0255)){//����
						a0203Next = "";
					}else{
						a0203Next = a0265;
					}
					if("".equals(a0203)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203.length()>=6){
							String year = a0203.substring(0,4);
							String month = a0203.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("--");
					if("".equals(a0203Next)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203Next.length()>=6){
							String year = a0203Next.substring(0,4);
							String month = a0203Next.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("&nbsp;&nbsp;"+a0201a+a03015+"<br/>");
				}
			}
			
			this.getExecuteSG().addExecuteCode("document.getElementById('contenttext2').innerHTML='"+sb.toString()+"'");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	 /**
     * ��ʵ��POJOת��ΪJSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    
	@PageEvent("codetype2js.onclick")
	@Transaction
	@Synchronous(true)
	public int codetype2js()throws RadowException, AppException{
		CodeType2js.getCodeTypeJS();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * רҵ����ְ�񴰿�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	//@PageEvent("a0196.onclick")
	@NoRequiredValidate
	public int a0196ondbclick()throws RadowException, AppException{
		this.setRadow_parent_data(this.getPageElement("a0000").getValue());
		this.openWindow("professSkill", "pages.publicServantManage.ProfessSkillAddPage");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ��������ʱ������Ա���롣
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("tabClick")
	@NoRequiredValidate
	public int tabClick(String id) throws RadowException {
		String tabid = id;
		String a0000check = (String)this.request.getSession().getAttribute("a0000");//��һ�δ���Ա��Ϣҳ��
		String a0000 = id;
		
		try {
			HBSession sess = HBUtil.getHBSession();
			if(a0000check==null||"".equals(a0000check)){
				//deletePerson(sess);
			}
			if(a0000==null||"".equals(a0000)||"add".equals(a0000)){
				this.setMainMessage("��ѯʧ�ܣ�");
				return EventRtnType.FAILD;
			}
			A01 a01 = null;
			if(a0000.indexOf("addTab-")!=-1){//������ҳ�棬����Ƿ�����Ա���룬����������������������޸ġ�
				//this.getExecuteSG().addExecuteCode("window.parent.win_addwin.setTitle('��Ա��������');");
				//this.getExecuteSG().addExecuteCode("window.parent.Ext.getCmp('"+a0000+"').setTitle('��Ա��������');");
				a0000 = a0000.split("addTab-")[1];
				a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					a01 = new A01();
					a01.setA0000(a0000);
					a01.setA0163("1");//Ĭ����ְ��Ա
					//a01.setA0104("1");//Ĭ����
					a01.setA14z101("��");//��������
					a01.setStatus("4");
					a01.setA0197("0");//���㹤������ʱ����������
					addUserInfo(a01);
					sess.save(a01);
					sess.flush();
					this.request.getSession().setAttribute("a0000", a0000);
					this.getExecuteSG().addExecuteCode("thisTab.initialConfig.personid='"+a0000+"';");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//���Ĵ�������updateWin.setTitle(title);
			if(a01==null){
				a01 = (A01)sess.get(A01.class, a0000);
			}
			//���� �Ա� ����
			String a0101 = a01.getA0101()==null?"":a01.getA0101();//����
			//String a0184 = a01.getA0184().toUpperCase();//���֤��//�����֤�����һλxת��Ϊ��д�ַ� add by lizs 20161110
			String a0107 = a01.getA0107();//��������
			String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
			String age = "";
			int agei = 0;
			/*if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
				age = IdCardManageUtil.getAge(a0184)+"";
			}*/
			if((agei = IdCardManageUtil.getAgefrombirth(a0107))!=-1){
				age = agei + "";
			}
			String title = a0101 + "��" + sex + "��" + age+"��";
			this.getExecuteSG().addExecuteCode("window.parent.tabs.getItem(thisTab.initialConfig.tabid).setTitle('"+title.replaceAll("<", "&lt;").replaceAll("'", "&acute;")+"');");
		} catch (Exception e) {
			this.setRadow_parent_data("");
			this.request.getSession().setAttribute("a0000", "");
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(a0000);
		//this.createPageElement("a0000n", ElementType.NORMAL, true).setValue(a0000);
		this.request.getSession().setAttribute("a0000", a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void addUserInfo(A01 a01) {
		a01.setTbr(SysManagerUtils.getUserId());
		//a01.setTbrjg(SysManagerUtils.getUserOrgid());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("����");
	}

	private void deletePerson(HBSession sess) throws Exception {
		String userid = SysManagerUtils.getUserId();
		HBUtil.executeUpdate("delete from a02  where exists (select a0000 from a01 t where t.a0000=a02.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a06  where exists (select a0000 from a01 t where t.a0000=a06.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a08  where exists (select a0000 from a01 t where t.a0000=a08.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a14  where exists (select a0000 from a01 t where t.a0000=a14.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a15  where exists (select a0000 from a01 t where t.a0000=a15.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a36  where exists (select a0000 from a01 t where t.a0000=a36.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		
		HBUtil.executeUpdate("delete from a11  where exists (select a1100 from a41 t where a11.a1100=t.a1100 and exists (select a0000 from a01 t1 where t1.a0000=t.a0000 and t1.status='4' and t1.tbr=? )) ",new Object[]{userid});
		HBUtil.executeUpdate("delete from a41  where exists (select a0000 from a01 t where t.a0000=a41.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		
		
		
		HBUtil.executeUpdate("delete from a29  where exists (select a0000 from a01 t where t.a0000=a29.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a53  where exists (select a0000 from a01 t where t.a0000=a53.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a37  where exists (select a0000 from a01 t where t.a0000=a37.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a31  where exists (select a0000 from a01 t where t.a0000=a31.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a30  where exists (select a0000 from a01 t where t.a0000=a30.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});
		HBUtil.executeUpdate("delete from a57  where exists (select a0000 from a01 t where t.a0000=a57.a0000 and t.status='4' and t.tbr=? )",new Object[]{userid});

		HBUtil.executeUpdate("delete from a01 where status='4' and tbr='"+userid+"' ");
		sess.flush();
	}
	
	
	//��ʱ����
	@PageEvent("isChange")
	@NoRequiredValidate
	public int isChange(String id) throws RadowException {
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			//this.setMainMessage("��ǰ��Ϣ�Ѿ��޸�,�Ƿ���Ҫ����");
			//this.setMessageType(EventMessageType.QUESTION);
			//addNextBackFunc(NextEventValue.YES, "alert();");
			this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','��ǰ��Ϣ�Ѿ��޸�,�Ƿ���Ҫ����',200,function(id){" +
				"if(id=='yes'){" +
					"	" +
					"}else if(id=='no'){" +
					"	window.parent.tabs.remove('"+id+"');" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		} catch (Exception e) {
			e.printStackTrace();
			//this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��PDFԤ������
	 * 
	 * @param a0000AndFlag a0000 ����ԱID���� flag���Ƿ��ӡ��������Ϣ��ƴ�ӵĲ������ö��ŷָ�
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @date 2016-06-03
	 */
	@PageEvent("exportLrmBtnNrm.onclick")
	@NoRequiredValidate
	public int pdfViewNrmBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("���ȱ�����Ա��Ϣ��");
		}
		pdfView("true");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("exportLrmBtn.onclick")
	@NoRequiredValidate
	public int pdfViewBefore() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("���ȱ�����Ա��Ϣ��");
		}
		pdfView("false");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("printView")
	public int pdfView(String nrmFlag) throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		Boolean flag = nrmFlag.equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
		
		String pdfPath = "";  											//pdf�ļ�·��
		
		List<String> list = new ArrayList<String>();
		list.add(a0000);
		
		List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
		
		pdfPath = pdfPaths.get(0);
		pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
		pdfPath = "/hzb"+pdfPath;
//		pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//		String contextStr = this.request.getContextPath().replace("/", "\\");
//		pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
		this.getPageElement("pdfPath").setValue(pdfPath);
		
		this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����','','',document.getElementById('a0000').value,ctxPath)");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("nameCheck")
	@NoRequiredValidate
	@AutoNoMask
	public int nameCheck(String name) throws RadowException, AppException, UnsupportedEncodingException{
		String a0000 = this.getPageElement("a0000").getValue();
		String size = HBUtil.getValueFromTab("count(1)", "A01", " a0101='"+name+"' and status!='4' and a0000!='"+a0000+"'");
		int sz = Integer.valueOf(size);
		if(sz>0){
			this.getExecuteSG().addExecuteCode("");
			String n = URLEncoder.encode(URLEncoder.encode(name, "utf8"), "utf8");
			//this.openWindow("nameCheck", "pages.publicServantManage.NameCheck&namecheck="+n);
			this.getExecuteSG().addExecuteCode("$h.openWin('nameCheck','pages.publicServantManage.NameCheck&namecheck="+ n +"','��ӡ�����',850,450,document.getElementById('a0000').value,ctxPath)");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setA0221Value")
	@NoRequiredValidate
	public int setA0221Value(String text) throws RadowException{
		 ((Combo)this.getPageElement("a0221")).setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0192eValue")
	@NoRequiredValidate
	public int setA0192eValue(String text) throws RadowException{
		 ((Combo)this.getPageElement("a0192e")).setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setA0195Value")
	@NoRequiredValidate
	public int setA0195Value(String text) throws RadowException{
		String[] s = text.split("\\|");
		if(s != null){
			String key = s[0];
			String value = s[1];
			((Combo)this.getPageElement("a0195")).setValue(value);
			this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='"+key+"';");
		}
		 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//���ͳ�ƹ�ϵ���ڵ�λ�Ƿ�Ϊ�����������
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		if(B0194 != null && B0194.equals("2")){
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('ϵͳ��ʾ','����ѡ�����������λ��');document.getElementById('a0195').value='';");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setA0288Value")
	@NoRequiredValidate
	public int setA0288Value(String text) throws RadowException{
		this.getPageElement("a0288").setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0192cValue")
	@NoRequiredValidate
	public int setA0192cValue(String text) throws RadowException{
		this.getPageElement("a0192c").setValue(text);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setA0163Value")
	@NoRequiredValidate
	public int setA0163Value(String a0163) throws AppException{
		this.getExecuteSG().addExecuteCode("document.getElementById('a0163Divid').innerHTML='"+(HBUtil.getCodeName("ZB126", (a0163==null?"":a0163)))+"';" );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("lastp.onclick")
	@NoRequiredValidate
	public int lastp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000Map");
		Object o = this.request.getSession().getAttribute("nowNumber");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumber");
		}
		if(num-1<0){
			//throw new RadowException("�Ѿ��ǵ�һλ��Ա��");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�Ѿ��ǵ�һλ��Ա��',null,170);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String nextA0000 = (String)map.get(num-1);
		this.request.getSession().setAttribute("nowNumber",num-1);//����num
		this.request.getSession().setAttribute("nowNumber2",num-1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("nextp.onclick")
	@NoRequiredValidate
	public int nextp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000Map");
		Object o = this.request.getSession().getAttribute("nowNumber");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumber");
		}
		Integer bigNumber = (Integer) this.request.getSession().getAttribute("bigNumber");
		if(num + 1 >= bigNumber){
			//throw new RadowException("�Ѿ������һλ��Ա��");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�Ѿ������һλ��Ա��',null,170);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String nextA0000 = (String)map.get(num+1);
		this.request.getSession().setAttribute("nowNumber",num+1);//����num
		this.request.getSession().setAttribute("nowNumber2",num+1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveA36F")
	@NoRequiredValidate
	public int saveA36F() throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		
		
		Integer rowLength = Integer.valueOf(this.getPageElement("rowLength").getValue());//����
		StringBuffer sb = new StringBuffer("{");
		for(int i=1;i<=rowLength;i++){
			A36 a36 = new A36();
			String a3600 = this.getPageElement("a3600_"+i).getValue();
			String a3604a = this.getPageElement("a3604a_"+i).getValue();
			String a3601 = this.getPageElement("a3601_"+i).getValue().replaceAll("\\s*", "");//��ͥ��Ա����ȥ�� 
			String a3607 = this.getPageElement("a3607_"+i).getValue();
			String a3627 = this.getPageElement("a3627_"+i).getValue();
			String a3611 = this.getPageElement("a3611_"+i).getValue().replaceAll("\\s*", "");//��ͥ��Ա����ȥ�� 
			
			if(a3600 != null && !a3600.equals("")){
				//��Ϣ��֤
				if(a3604a == null || "".equals(a3604a)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա��ν����Ϊ�գ�',null,220);");
					//this.setMainMessage("��ͥ��Ա��ν����Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a3601 == null || "".equals(a3601)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա��������Ϊ�գ�',null,220);");
					//this.setMainMessage("��ͥ��Ա��������Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/*if(a3607 == null || "".equals(a3607)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա�������²���Ϊ�գ�',null,220);");
					//this.setMainMessage("��ͥ��Ա�������²���Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}*/
				if(a3627 == null || "".equals(a3627)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա������ò����Ϊ�գ�',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a3611 == null || "".equals(a3611)){
					this.getExecuteSG().addExecuteCode("parent.$h.alert('ϵͳ��ʾ','��ͥ��Ա������λ��ְ����Ϊ�գ�',null,220);");
					//this.setMainMessage("��ͥ��Ա������λ��ְ����Ϊ�գ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
			}
			
			sb
			.append("a3604a_"+i+":'"+a3604a+"',")
			.append("a3601_"+i+":'"+a3601+"',")
			.append("a3607_"+i+":'"+a3607+"',")
			.append("a3627_"+i+":'"+a3627+"',")
			.append("a3611_"+i+":'"+a3611+"',");
			a36.setA0000(a0000);
			if((a3604a==null||"".equals(a3604a))&&
					(a3601==null||"".equals(a3601))&&
					(a3607==null||"".equals(a3607))&&
					(a3627==null||"".equals(a3627))&&
									(a3611==null||"".equals(a3611))){
				sb.append("a3600_"+i+":'',");
				this.getPageElement("a3600_"+i).setValue("");
				if(a3600==null||"".equals(a3600)){
					
					continue;
				}else{
					a36.setA3600(a3600);
					new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A36(), new A36())).start();
					sess.delete(a36);
					continue;
				}
				
			}
			a36.setA3604a(a3604a);
			a36.setA3601(a3601);
			a36.setA3607(a3607);
			a36.setA3627(a3627);
			a36.setA3611(a3611);
			a36.setSortid(BigDecimal.valueOf((long)i));
			if(a3600==null||"".equals(a3600)){
				a36.setA3600(null);
				sess.save(a36);
				sess.flush();
				new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A36(), a36)).start();
				
				this.getPageElement("a3600_"+i).setValue(a36.getA3600());
				
			}else{
				a36.setA3600(a3600);
				A36 a36_old = (A36)sess.get(A36.class, a3600);
				new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a36_old, a36)).start();
				PropertyUtils.copyProperties(a36_old, a36);
				sess.update(a36_old);
			}
			sb.append("a3600_"+i+":'"+a36.getA3600()+"',");
		}
		for(int i=rowLength+1;i<=30;i++){
			sb.append("a3600_"+i+":'',")
			.append("a3604a_"+i+":'',")
			.append("a3601_"+i+":'',")
			.append("a3607_"+i+":'',")
			.append("a3627_"+i+":'',")
			.append("a3611_"+i+":'',");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		sess.flush();			
		//��ֵ �ж��Ƿ��޸�
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public int getAgeNew(String entryDate,String birth) {
		int returnAge;

		String birthYear = birth.substring(0, 4);
		String birthMonth = birth.substring(4, 6);
		String birthDay = "";
		if(birth.length()==6){
			birthDay = "01";
		}
		if(birth.length()==8){
			birthDay = birth.substring(6, 8);
		}
		
		String nowYear = entryDate.substring(0, 4);
		String nowMonth = entryDate.substring(4, 6);
		String nowDay = "";
		if(entryDate.length()==6){
			nowDay = "01";
		}
		if(entryDate.length()==8){
			nowDay = entryDate.substring(6, 8);
		}
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // ͬ�귵��0��
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // ��ֻ��
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// ��֮��
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// ��֮��
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// �������ڴ��� ���ڽ���
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "��)";
		int msg = returnAge ;
		return msg;
	}
	
	
	
	@PageEvent("deleteA36F")
	@NoRequiredValidate
	public int deleteA36F(String a3600) throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException{
		HBSession session = HBUtil.getHBSession();
		
		//ɾ����ͥ��Ա
		if(a3600 != null && !"".equals(a3600)){
			A36 policy = (A36) session.get(A36.class, a3600);
			if(policy!=null && !"".equals(policy)){
				session.delete(policy);
				session.flush();
			}
		}
		
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
