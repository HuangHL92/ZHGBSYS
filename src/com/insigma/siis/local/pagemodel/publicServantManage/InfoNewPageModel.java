package com.insigma.siis.local.pagemodel.publicServantManage;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.hsqldb.lib.StringUtil;

import com.fr.function.INT;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
/**
 * ��Ϣ¼��ҳ��
 * @author fujun
 *
 */
public class InfoNewPageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	/**
	 * ��Ż�����Ϣ
	 */
	static Map<String,String> B01ALL = null;
	//ҳ���ʼ��
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ҳ�����ݳ�ʼ��
		@PageEvent("initX")
		@NoRequiredValidate
		public int initX() throws RadowException {

			getStaticB01Map();
			Map<String, String> map = RuleSqlListBS.getAllB01Map();
			StringBuffer dataarrayjs = getComboArray(map);
			
			List<String> list = (List<String>) request.getSession().getAttribute("order");
			int num = list.indexOf("ͳ�ƹ�ϵ���ڵ�λ")+2;
			String js = "$h.changeComboStore({gridid:'a01Grid',colIndex:"+num+",dataArray:" + dataarrayjs + "});";
			this.getExecuteSG()
			.addExecuteCode(js);
			getStaticB01Map();
			int a02A0201b=3;
			List<String> a02List = (List<String>) request.getSession().getAttribute("a02Order");
			for(int i =0 ;i<a02List.size() ;i++){
				if(a02List.get(i).equals("��ְ����")){
					a02A0201b = i+3;
					break ;
				}
			}
			// ��ְ����
			this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'a02Grid',colIndex:"+a02A0201b+",dataArray:" + dataarrayjs + "});");
			// �������
			Calendar c = new GregorianCalendar();
			int year = c.get(Calendar.YEAR);
			Map<String, String> mapYear = new LinkedHashMap<String, String>();
			for (int i = 0; i < 80; i++) {
				mapYear.put("" + (year - i), "" + (year - i));
			}
			StringBuffer dataArrayYear = getComboArray(mapYear);
			this.getExecuteSG()
					.addExecuteCode("$h.changeComboStore({gridid:'a15Grid',colIndex:2,dataArray:" + dataArrayYear + "});");
			
			return EventRtnType.NORMAL_SUCCESS;
		}
	
	
	/**
	 * ��ͥ��Ա��Ϣ����
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author Sucf
	 */
	@PageEvent("a36Save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	
	public int a36Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		if(a0000.equals(null)||a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String,String>> list = this.getPageElement("a36Grid").getStringValueList();
		int  num= 0;
		//����a0000�ֶ��ҳ�����Ա�ļ�ͥ��Ա��a3600
		String sql = "select * from a36 where a0000="+"'"+a0000+"'";
		List<A36> listA36 = sess.createSQLQuery(sql).addEntity(A36.class).list();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String, String> m :list) {
			HashMap<String,Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
		    }
			boolean flag = false;//�����������ѭ��
			if(m.get("A3601").equals("")||m.get("A3601").equals(null)) {//���������ж��Ƿ�Ϊ��
				listNew.add(tempmap);
				num++;
				continue;
			}
			String a3600 = m.get("A3600");
			for(A36 a36 : listA36) {
				if(a36.getA3600().equals(a3600)) {//�����²���
					//String a3600 =  listA36.get(0).getA3600();
					String a3601 = m.get("A3601");
					String a3604A = m.get("A3604A");
					String a3607 = m.get("A3607");
					String a3627 = m.get("A3627");
					String a3611 = m.get("A3611");
					//a36.setA0000(a0000);
					//a36.setA3600(a3600);
					a36.setA3601(a3601);
					a36.setA3604a(a3604A);
					a36.setA3607(a3607);
					a36.setA3627(a3627);
					a36.setA3611(a3611);
					sess.update(a36);
					sbUpdata.append(a3601+"#%");
					tempmap.put("A3600", a3600);
					listNew.add(tempmap);
					flag = true;
				}
			}
			if(flag==true) {
				continue;//��������
			}
			//���������
			A36 a36 = new A36();
			String a3601 = m.get("A3601");
			String a3604A = m.get("A3604A");
			String a3607 = m.get("A3607");
			String a3627 = m.get("A3627");
			String a3611 = m.get("A3611");
			a36.setA0000(a0000);
			//a36.setA3600(a3600);
			a36.setA3601(a3601);
			a36.setA3604a(a3604A);
			a36.setA3607(a3607);
			a36.setA3627(a3627);
			a36.setA3611(a3611);
			
			String sqlsortid = "SELECT MAX(SORTID) FROM a36 WHERE a0000 = '"+a0000+"'";
			List<Integer> listtemp  = sess.createSQLQuery(sqlsortid).list();
			if(listtemp.get(0)==null) {//��һ����
				a36.setSortid(new BigDecimal(1));
				sess.save(a36);
				sess.flush();
				//�滻a3600��ֵ
				String sqlcount = "SELECT * FROM a36 WHERE a0000 = '"+a0000+"' AND SORTID='"+1+"'";
				List<A36> la3600DB  = sess.createSQLQuery(sqlcount).addEntity(A36.class).list();
				String a3600DB = la3600DB.get(0).getA3600();//�õ����е�a3600
				//this.setNextEventName("a01Grid.dogridquery");
				tempmap.put("A3600", a3600DB);
				listNew.add(tempmap);
				sbSave.append(a3601+"#%");//#%��Ϊ�ָ�
			}else {
				int sortid = listtemp.get(0)+1;
				a36.setSortid(new BigDecimal(sortid));
				sess.save(a36);
				sess.flush();
				//�滻a3600��ֵ
				String sqlcount = "SELECT * FROM a36 WHERE a0000 = '"+a0000+"' AND SORTID='"+sortid+"'";
				List<A36> la3600DB  = sess.createSQLQuery(sqlcount).addEntity(A36.class).list();
				String a3600DB = la3600DB.get(0).getA3600();//�õ����е�a3600
				//this.setNextEventName("a01Grid.dogridquery");
				tempmap.put("A3600", a3600DB);
				listNew.add(tempmap);
				sbSave.append(a3601+"#%");//#%��Ϊ�ָ�
			}
		}
		if(num==list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String updata = "";
		String savedata = "";*/
		String message = "";
		/*if(!sbUpdata.toString().equals("")) {
			updata = "���£�"+sbUpdata.toString().substring(0,(sbUpdata.toString().length()-2)).replace("#%", ",");
		}
		if(!sbSave.toString().equals("")) {
			savedata = "������"+sbSave.toString().substring(0,(sbSave.toString().length()-2)).replace("#%", ",");
		}*/
		message = message(sbSave.toString(),sbUpdata.toString());
		this.getExecuteSG().addExecuteCode("$h.alert('���α��������',"+"'"+message+"'"+",null,500)");
		//this.getExecuteSG().addExecuteCode("hint("+"'������:"+updata+"������:"+savedata+"')");
		this.getPageElement("a36Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//��ʾ������Ϣ
	public String message(String savedata,String updata) {
		String savedatar = "";
		String updatar = "";
		if(!savedata.equals("")) {
			int savenum = savedata.split("#%").length;
			savedatar = "����"+savenum+"������";
		}
		if(!updata.equals("")) {
			int updatanum = updata.split("#%").length;
			updatar = "����"+updatanum+"������";
		}
		return savedatar+"<br>"+updatar;
	}
	/**
	 * רҵ����ְ��
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author Sucf
	 */
	@PageEvent("a06Save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int a06Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		if(a0000.equals(null)||a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String,String>> list = this.getPageElement("a06Grid").getStringValueList();
		int  num= 0;
		//����a0000�ֶ��ҳ�����Ա��רҵ����ְ���
		String sql = "select * from a06 where a0000="+"'"+a0000+"'";
		List<A06> listA06 = sess.createSQLQuery(sql).addEntity(A06.class).list();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String, String> m :list) {
			HashMap<String,Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
		    }
			boolean flag = false;//�����������ѭ��
			if(m.get("A0601").equals("")||m.get("A0601").equals(null)) {//���������ж��Ƿ�Ϊ�գ�ʲô�����ԣ�
				listNew.add(tempmap);
				num++;
				continue;
			}
			String a0600 = m.get("A0600");
			for(A06 a06 : listA06) {
				if(a06.getA0600().equals(a0600)) {//�����²���
					String a0699 = m.get("A0699");//�Ƿ����
					String a0601 = m.get("A0601");
					String a0602 = m.get("A0602");
					String a0604 = m.get("A0604");
					String a0607 = m.get("A0607");
					String a0611 = m.get("A0611");
					a06.setA0699(a0699);
					a06.setA0601(a0601);
					a06.setA0602(a0602);
					a06.setA0604(a0604);
					a06.setA0607(a0607);
					a06.setA0611(a0611);
					sess.update(a06);
					sbUpdata.append(a0601+"#%");
					tempmap.put("A0600", a0600);
					listNew.add(tempmap);
					flag = true;
				}
			}
			if(flag==true) {
				continue;//��������
			}
			//���������
			A06 a06 = new A06();
			String a0699 = m.get("A0699");//�Ƿ����
			String a0601 = m.get("A0601");
			String a0602 = m.get("A0602");
			String a0604 = m.get("A0604");
			String a0607 = m.get("A0607");
			String a0611 = m.get("A0611");
			a06.setA0000(a0000);
			a06.setA0699(a0699);
			a06.setA0601(a0601);
			a06.setA0602(a0602);
			a06.setA0604(a0604);
			a06.setA0607(a0607);
			a06.setA0611(a0611);
			
			String sqlsortid = "SELECT MAX(SORTID) FROM a06 WHERE a0000 = '"+a0000+"'";
			List<Integer> listtemp  = sess.createSQLQuery(sqlsortid).list();
			if(listtemp.get(0)==null) {//��һ����
				a06.setSortid((long) 1);
				sess.save(a06);
				sess.flush();
				//�滻a0600��ֵ
				String sqlcount = "SELECT * FROM a06 WHERE a0000 = '"+a0000+"' AND SORTID='"+1+"'";
				List<A06> la0600DB  = sess.createSQLQuery(sqlcount).addEntity(A06.class).list();
				String a0600DB = la0600DB.get(0).getA0600();//�õ����е�a0600
				//this.setNextEventName("a01Grid.dogridquery");
				tempmap.put("A0600", a0600DB);
				listNew.add(tempmap);
				sbSave.append(a0601+"#%");//#%��Ϊ�ָ�
			}else {
				int sortid = listtemp.get(0)+1;
				a06.setSortid((long) sortid);
				sess.save(a06);
				sess.flush();
				//�滻a0600��ֵ
				String sqlcount = "SELECT * FROM a06 WHERE a0000 = '"+a0000+"' AND SORTID='"+sortid+"'";
				List<A06> la0600DB  = sess.createSQLQuery(sqlcount).addEntity(A06.class).list();
				String a0600DB = la0600DB.get(0).getA0600();//�õ����е�a0600
				//this.setNextEventName("a01Grid.dogridquery");
				tempmap.put("A0600", a0600DB);
				listNew.add(tempmap);
				sbSave.append(a0601+"#%");//#%��Ϊ�ָ�
			}
			
		}
		if(num==list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String updata = "";
		String savedata = "";*/
		String message = "";
		/*if(!sbUpdata.toString().equals("")) {
			updata = "���£�"+sbUpdata.toString().substring(0,(sbUpdata.toString().length()-2)).replace("#%", ",");
		}
		if(!sbSave.toString().equals("")) {
			savedata = "������"+sbSave.toString().substring(0,(sbSave.toString().length()-2)).replace("#%", ",");
		}*/
		message = message(sbSave.toString(),sbUpdata.toString());
		this.getExecuteSG().addExecuteCode("$h.alert('���α��������',"+"'"+message+"'"+",null,500)");
		//this.getExecuteSG().addExecuteCode("hint("+"'������:"+updata+"������:"+savedata+"')");
		this.getPageElement("a06Grid").setValueList(listNew);
		//���a0196
		
		String sqla0196 = "select A0602 from a06 where a0000='"+a0000+"' and a0699='true'";
		List<String> lista0196s = sess.createSQLQuery(sqla0196).list();
		StringBuilder sba0196 = new StringBuilder();
		for(String a0196:lista0196s) {
			sba0196.append(a0196+",");
		}
		String a0196 = sba0196.toString().substring(0,(sba0196.toString().length()-1));
		HBUtil.executeUpdate("update a01 set a0196='"+a0196+"' where a0000='"+a0000+"'");
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��Ա������Ϣ����
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author Sucf
	 */
	@PageEvent("a01Save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int a01Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		HBSession sess = HBUtil.getHBSession();
		List<HashMap<String,String>> list = this.getPageElement("a01Grid").getStringValueList();
		int  num= 0;
		for(HashMap<String,String> m : list){
			String A0000 = m.get("A0000");
			if(A0000==null) {
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��',A0000����������,null,500);");
				return EventRtnType.FAILD;
			}
			if(m.get("A0101").equals("")||m.get("A0101").equals(null)) {//���������ж��Ƿ�Ϊ��
				num++;
				continue;
			}
			A01 a01 = (A01)sess.get(A01.class, A0000);
			if(a01==null) {//�������ݿ���û�и���Ա,ִ�б���
				a01= new A01();
				String A0101 = m.get("A0101");
				String A0184 = m.get("A0184");
				String A0104 = m.get("A0104");
				String A0107 = m.get("A0107");
				String A0117 = m.get("A0117");
				String A0111A = m.get("A0111A");
				String A0114A = m.get("A0114A");
				String A0140 = m.get("A0140");
				String A0134 = m.get("A0134");
				String A0128 = m.get("A0128");
				String A0187A = m.get("A0187A");
				String A0160 = m.get("A0160");
				String A0165 = m.get("A0165");
				String A0121 = m.get("A0121");
				
				String a0141 = m.get("A0141");
				String a3921 = m.get("A3921");
				String a3927 = m.get("A3927");
				String a0195 = m.get("A0195");
				String A2949 = m.get("A2949");
				
				String a0120 = m.get("A0120");
				String a0115A = m.get("A0115A");
				String a0122 = m.get("A0122");
				String a0180 = m.get("A0180");
				
				//���֤�ظ�����
				if(A0184!=null){
					A0184 = A0184.toUpperCase();
				}
				/*String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
				Object c = sess.createSQLQuery(sql).uniqueResult();*/
				
				String sql = "select A0101,A0192A from A01 where  a0000!='"+A0000+"' and a0184='"+A0184+"'";
				List<Object[]>  c = sess.createSQLQuery(sql).list();
				
				if(c.size() > 0){		//���ظ����֤
					//ƴ���ظ���Ա��Ϣ
					String msgCard = "ϵͳ���Ѵ���������ͬ���֤������Ա,���޸ģ�<br/>";
					
					for (int i = 0; i < c.size(); i++) {
						
						Object[] info = c.get(i);
						Object a0101msg = info[0];		//����
						Object a0192amsg = info[1];		//������λ��ְ��
						
						msgCard = msgCard + "<br/>" + a0101msg +"      "+ a0192amsg;
					}
					
					
					this.setMainMessage(msgCard);
					
					//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','�������Ϣ\n����ɹ�!',null,220);");
					return EventRtnType.FAILD;
					
				}
				
				
				//ƴ���뵳ʱ��
				String sqla0141_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a0141+"'";
				String a0141_combo = sess.createSQLQuery(sqla0141_combo).list().get(0).toString();
				String a3921_combo;
				if(a3921==null||a3921.equals("")) {
					a3921_combo="";
				}else {
					String sqla3921_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a3921+"'";
					a3921_combo = sess.createSQLQuery(sqla3921_combo).list().get(0).toString();
				}
				String a3927_combo;
				
				if(a3927==null||a3927.equals("")) {
					a3927_combo="";
				}else{
					String sqla3927_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a3927+"'";
					a3927_combo = sess.createSQLQuery(sqla3927_combo).list().get(0).toString();
				}
				String A0140New = "";
				String date = "";
				String a0144 =A0140;
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
					return EventRtnType.FAILD;
				} else {
					if("02".equals(a0141) || "01".equals(a0141)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("������뵳ʱ�䣡");
							return EventRtnType.FAILD;
						}
						//String a0107 = this.getPageElement("a0107").getValue();//��������
						if(!StringUtil.isEmpty(a0144)){
							if(a0144.length()==6){
								a0144+="01";
							}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							String now=sdf.format(new Date());
							if(Integer.valueOf(a0144)>Integer.valueOf(now)){
								this.setMainMessage("�뵳ʱ�䲻�����ڵ�ǰʱ��");
								return EventRtnType.FAILD;
							}
						}	
						if(A0107!=null&&!"".equals(A0107)){
							if (A0107.length() == 6) {
								A0107 += "01";
							}
							if (a0144.length() == 6) {
								a0144 += "01";
							}
							if(A0107!=null&&!"".equals(A0107)&&a0144!=null&&!"".equals(a0144)){
								int start = Integer.valueOf(A0107);      	//��������
								int end = Integer.valueOf(a0144);			//�뵳ʱ��
								
								//����18���������
								/*String a010718 = a0107.substring(0,4);
								int year18 = Integer.valueOf(a010718) + 18;
								a010718 = String.valueOf(year18) + a0107.substring(4,8);
								start = Integer.valueOf(a010718);*/
								
								
								if (start >= end) {
									this.setMainMessage("�뵳ʱ�䲻�����ڵ��ڳ�������");
									//this.setMainMessage("�뵳ʱ�䲻��С��18��");
									return EventRtnType.FAILD;
								}
								
								A0107 = A0107.replace(".", "").substring(0, 6);
								a0144 = a0144.replace(".", "").substring(0, 6);
								
							}
						}
						String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
						if(a3921==null || "".equals(a3921)){
							if((a3927!=null && !"".equals(a3927))){
								this.setMainMessage("����ӵڶ����ɣ�");
								return EventRtnType.FAILD;
							} else {
								A0140 = a0144_sj ;
							}
						} else {
							if(a3927!=null && !"".equals(a3927)){
								A0140 =  a3921_combo+ "��" + a3927_combo + "(" + a0144_sj +")";
							} else {
								A0140 =  a3921_combo+ "(" + a0144_sj +")";
							}
						}
					} else {
						if("02".equals(a3921) || "01".equals(a3921)){
							if((a0144==null || "".equals(a0144))){
								this.setMainMessage("������뵳ʱ�䣡");
								return EventRtnType.FAILD;
							}
						}
						if("02".equals(a3927) || "01".equals(a3927)){
							if((a0144==null || "".equals(a0144))){
								this.setMainMessage("������뵳ʱ�䣡");
								return EventRtnType.FAILD;
							}
						}
						if(a3921==null || "".equals(a3921)){
							if((a3927!=null && !"".equals(a3927))){
								this.setMainMessage("����ӵڶ����ɣ�");
								return EventRtnType.FAILD;
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
				
				if(a0141.equals("01") || a0141.equals("02")){
					date = "("+a0144_time+")";
					a0141_combo = "";
				}
				if(a3921.equals("01") || a3921.equals("02")){
					date = "("+a0144_time+")";
					a3921_combo = "";
				}
				if(a3927.equals("01") || a3927.equals("02")){
					date = "("+a0144_time+")";
					a3927_combo = "";
				}
				
				
				
				if(a0141_combo != null && !a0141_combo.equals("")){
					A0140New = A0140New + a0141_combo+ "��";
				}
				if(a3921_combo != null && !a3921_combo.equals("")){
					A0140New = A0140New + a3921_combo+ "��";
				}
				if(a3927_combo != null && !a3927_combo.equals("")){
					A0140New = A0140New + a3927_combo+ "��";
				}
				
				if(A0140New != null && !A0140New.equals("")){
					A0140New = A0140New.substring(0, A0140New.length()-1);
					if(date != null && !date.equals("")){
						A0140New = A0140New + date;
					}
				}else{
					A0140New = A0140New + a0144_time;
				}
				
				
				if("()".equals(A0140New)){
					A0140New="";
				}
				
				//�ж��Ƿ�Ϊ��������û����ƴ������
				if(a0144 == null || a0144.equals("")){		//������
					
					if(A0140New != null && !A0140New.equals("")){
						A0140New = "(" +A0140New+ ")";
					}
					
				}
				
				//String[] times = getTimes(tempdata,a0141,a0141_combo,a3921,a3921_combo,a3927,a3927_combo);
				
				
				a01.setStatus("1");
				a01.setA0163("1");
				a01.setA0000(A0000);
				a01.setA0101(A0101);
				a01.setA0184(A0184);
				a01.setA0104(A0104);
				a01.setA0107(A0107);
				a01.setA0117(A0117);
				a01.setComboxArea_a0111(A0111A);
				a01.setComboxArea_a0114(A0114A);
				a01.setA0140(A0140New);
				a01.setA0144(a0144);
				a01.setA0134(A0134);
				a01.setA0128(A0128);
				a01.setA0187a(A0187A);
				a01.setA0160(A0160);
				a01.setA0165(A0165);
				a01.setA0121(A0121);
				
				a01.setA0141(a0141);
				a01.setA3921(a3921);
				a01.setA3927(a3927);
				a01.setA0195(a0195);
				a01.setA2949(A2949);
				
				a01.setA0120(a0120);
				a01.setA0115a(a0115A);
				a01.setA0122(a0122);
				a01.setA0180(a0180);
				a01.setA0102(new ChineseSpelling().getPYString(A0101));//ƴ�����
				
				if(A0187A != null || "".equals(A0187A)){
					if(A0187A.length() > 60){
						this.setMainMessage("ר�����ܳ���60�֣�");
						return EventRtnType.FAILD;
					}
				}
				sess.save(a01);
				sess.flush();
				sbSave.append(A0101+"#%");//#%��Ϊ�ָ�
			}else {//ִ�и���
				A01 a01_old = new A01();
				try {
					a01_old = a01.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				String A0101 = m.get("A0101");
				String A0184 = m.get("A0184");
				String A0104 = m.get("A0104");
				String A0107 = m.get("A0107");
				String A0117 = m.get("A0117");
				String A0111A = m.get("A0111A");
				String A0114A = m.get("A0114A");
				String A0140 = m.get("A0140");
				String A0134 = m.get("A0134");
				String A0128 = m.get("A0128");
				String A0187A = m.get("A0187A");
				String A0160 = m.get("A0160");
				String A0165 = m.get("A0165");
				String A0121 = m.get("A0121");
				String a0141 = m.get("A0141");
				String a3921 = m.get("A3921");
				String a3927 = m.get("A3927");
				String a0195 = m.get("A0195");
				String A2949 = m.get("A2949");
				String a0120 = m.get("A0120");
				String a0115A = m.get("A0115A");
				String a0122 = m.get("A0122");
				String a0180 = m.get("A0180");
				//���֤�ظ�����
				if(A0184!=null){
					A0184 = A0184.toUpperCase();
				}
				/*String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
				Object c = sess.createSQLQuery(sql).uniqueResult();*/
				
				String sql = "select A0101,A0192A from A01 where  a0000!='"+A0000+"' and a0184='"+A0184+"'";
				List<Object[]>  c = sess.createSQLQuery(sql).list();
				
				if(c.size() > 0){		//���ظ����֤
					//ƴ���ظ���Ա��Ϣ
					String msgCard = "ϵͳ���Ѵ���������ͬ���֤������Ա,���޸ģ�<br/>";
					
					for (int i = 0; i < c.size(); i++) {
						
						Object[] info = c.get(i);
						Object a0101msg = info[0];		//����
						Object a0192amsg = info[1];		//������λ��ְ��
						
						msgCard = msgCard + "<br/>" + a0101msg +"      "+ a0192amsg;
					}
					
					
					this.setMainMessage(msgCard);
					
					//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','�������Ϣ\n����ɹ�!',null,220);");
					return EventRtnType.FAILD;
					
				}
				
				
				//ƴ���뵳ʱ��
				String sqla0141_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a0141+"'";
				String a0141_combo = sess.createSQLQuery(sqla0141_combo).list().get(0).toString();
				String a3921_combo;
				if(a3921==null||a3921.equals("")) {
					a3921_combo="";
				}else {
					String sqla3921_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a3921+"'";
					a3921_combo = sess.createSQLQuery(sqla3921_combo).list().get(0).toString();
				}
				String a3927_combo;
				
				if(a3927==null||a3927.equals("")) {
					a3927_combo="";
				}else{
					String sqla3927_combo = "select code_name from code_value where CODE_TYPE='GB4762' and CODE_VALUE='"+a3927+"'";
					a3927_combo = sess.createSQLQuery(sqla3927_combo).list().get(0).toString();
				}
				String A0140New = "";
				String date = "";
				String a0144 =A0140;
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
					return EventRtnType.FAILD;
				} else {
					if("02".equals(a0141) || "01".equals(a0141)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("������뵳ʱ�䣡");
							return EventRtnType.FAILD;
						}
						//String a0107 = this.getPageElement("a0107").getValue();//��������
						if(!StringUtil.isEmpty(a0144)){
							if(a0144.length()==6){
								a0144+="01";
							}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							String now=sdf.format(new Date());
							if(Integer.valueOf(a0144)>Integer.valueOf(now)){
								this.setMainMessage("�뵳ʱ�䲻�����ڵ�ǰʱ��");
								return EventRtnType.FAILD;
							}
						}	
						if(A0107!=null&&!"".equals(A0107)){
							if (A0107.length() == 6) {
								A0107 += "01";
							}
							if (a0144.length() == 6) {
								a0144 += "01";
							}
							if(A0107!=null&&!"".equals(A0107)&&a0144!=null&&!"".equals(a0144)){
								int start = Integer.valueOf(A0107);      	//��������
								int end = Integer.valueOf(a0144);			//�뵳ʱ��
								
								//����18���������
								/*String a010718 = a0107.substring(0,4);
								int year18 = Integer.valueOf(a010718) + 18;
								a010718 = String.valueOf(year18) + a0107.substring(4,8);
								start = Integer.valueOf(a010718);*/
								
								
								if (start >= end) {
									this.setMainMessage("�뵳ʱ�䲻�����ڵ��ڳ�������");
									//this.setMainMessage("�뵳ʱ�䲻��С��18��");
									return EventRtnType.FAILD;
								}
								
								A0107 = A0107.replace(".", "").substring(0, 6);
								a0144 = a0144.replace(".", "").substring(0, 6);
								
							}
						}
						String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
						if(a3921==null || "".equals(a3921)){
							if((a3927!=null && !"".equals(a3927))){
								this.setMainMessage("����ӵڶ����ɣ�");
								return EventRtnType.FAILD;
							} else {
								A0140 = a0144_sj ;
							}
						} else {
							if(a3927!=null && !"".equals(a3927)){
								A0140 =  a3921_combo+ "��" + a3927_combo + "(" + a0144_sj +")";
							} else {
								A0140 =  a3921_combo+ "(" + a0144_sj +")";
							}
						}
					} else {
						if("02".equals(a3921) || "01".equals(a3921)){
							if((a0144==null || "".equals(a0144))){
								this.setMainMessage("������뵳ʱ�䣡");
								return EventRtnType.FAILD;
							}
						}
						if("02".equals(a3927) || "01".equals(a3927)){
							if((a0144==null || "".equals(a0144))){
								this.setMainMessage("������뵳ʱ�䣡");
								return EventRtnType.FAILD;
							}
						}
						if(a3921==null || "".equals(a3921)){
							if((a3927!=null && !"".equals(a3927))){
								this.setMainMessage("����ӵڶ����ɣ�");
								return EventRtnType.FAILD;
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
				
				if(a0141.equals("01") || a0141.equals("02")){
					date = "("+a0144_time+")";
					a0141_combo = "";
				}
				if(a3921.equals("01") || a3921.equals("02")){
					date = "("+a0144_time+")";
					a3921_combo = "";
				}
				if(a3927.equals("01") || a3927.equals("02")){
					date = "("+a0144_time+")";
					a3927_combo = "";
				}
				
				
				
				if(a0141_combo != null && !a0141_combo.equals("")){
					A0140New = A0140New + a0141_combo+ "��";
				}
				if(a3921_combo != null && !a3921_combo.equals("")){
					A0140New = A0140New + a3921_combo+ "��";
				}
				if(a3927_combo != null && !a3927_combo.equals("")){
					A0140New = A0140New + a3927_combo+ "��";
				}
				
				if(A0140New != null && !A0140New.equals("")){
					A0140New = A0140New.substring(0, A0140New.length()-1);
					if(date != null && !date.equals("")){
						A0140New = A0140New + date;
					}
				}else{
					A0140New = A0140New + a0144_time;
				}
				
				
				if("()".equals(A0140New)){
					A0140New="";
				}
				
				//�ж��Ƿ�Ϊ��������û����ƴ������
				if(a0144 == null || a0144.equals("")){		//������
					
					if(A0140New != null && !A0140New.equals("")){
						A0140New = "(" +A0140New+ ")";
					}
					
				}
				
				a01.setStatus("1");
				a01.setA0163("1");
				a01.setA0101(A0101);
				a01.setA0184(A0184);
				a01.setA0104(A0104);
				a01.setA0107(A0107);
				a01.setA0117(A0117);
				a01.setComboxArea_a0111(A0111A);
				a01.setComboxArea_a0114(A0114A);
				a01.setA0140(A0140New);
				a01.setA0144(a0144);
				a01.setA0134(A0134);
				a01.setA0128(A0128);
				a01.setA0187a(A0187A);
				a01.setA0160(A0160);
				a01.setA0165(A0165);
				a01.setA0121(A0121);
				a01.setA0141(a0141);
				a01.setA3921(a3921);
				a01.setA3927(a3927);
				a01.setA0195(a0195);
				a01.setA2949(A2949);
				a01.setA0120(a0120);
				a01.setA0115a(a0115A);
				a01.setA0122(a0122);
				a01.setA0180(a0180);
				a01.setA0102(new ChineseSpelling().getPYString(A0101));//ƴ�����
				if(A0187A != null || "".equals(A0187A)){
					if(A0187A.length() > 60){
						this.setMainMessage("ר�����ܳ���60�֣�");
						return EventRtnType.FAILD;
					}
				}
				List<String[]> listww = Map2Temp.getLogInfo(a01,a01_old);
				if(listww.size()!=0) {//û�иı䣬ֱ�ӹر�ҳ��
					sess.update(a01);
				}
				sbUpdata.append(A0101+"#%");
			}
		}
		if(num==list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String updata = "";
		String savedata = "";
		String message = "";
		if(!sbUpdata.toString().equals("")) {
			updata = "���£�"+sbUpdata.toString().substring(0,(sbUpdata.toString().length()-2)).replace("#%", ",");
		}
		if(!sbSave.toString().equals("")) {
			savedata = "������"+sbSave.toString().substring(0,(sbSave.toString().length()-2)).replace("#%", ",");
		}
		message =  "'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + updata + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + savedata +"<br>"+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����¼���Ӽ�"+ "'";
		this.getExecuteSG().addExecuteCode("$h.alert('���α��������',"+message+",null,500)");
		//this.getExecuteSG().addExecuteCode("hint("+"'������:"+updata+"������:"+savedata+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ְ���α���
	 * @return
	 * @throws RadowException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AppException
	 * @author lixl1
	 */
	@PageEvent("a05Save")
	@Synchronous(true)
	@NoRequiredValidate
	public int a05Save() throws RadowException, IntrospectionException, IllegalAccessException,
			InvocationTargetException, AppException {
		List<HashMap<String, String>> list = this.getPageElement("a05Grid").getStringValueList();
		List<String> lists = new ArrayList<String>();
		Set<String> s = new HashSet<String>();
		boolean a05UpdateFlag=true;//�����޸�a5��ʱ������a01��ְ����
		boolean a05SaveFlag=true;//���Ʊ���a5��ʱ������a01��ְ����
		for (HashMap<String, String> m : list) {
			lists.add(m.get("a0501b"));
		}
		for (String str : lists) {
			boolean b = s.add(str);
			if (!b) {
				this.setMainMessage("ְ���β����ظ���д");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		int saveNum=0;
		int updateNum=0;
		StringBuilder sbUpdata = new StringBuilder();// ���µ�ֵ
		StringBuilder sbSave = new StringBuilder();// �������
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		if (a0000.equals(null) || a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡"); 
			return EventRtnType.NORMAL_SUCCESS; 
		}
		
		int num = 0;
		// ����a0000�ֶ��ҳ�����Ա�ļ�ͥ��Ա��a0500
		String sql = "select * from a05 where a0000=" + "'" + a0000 + "'";
		List<A05> listA05 = sess.createSQLQuery(sql).addEntity(A05.class).list();
		List<HashMap<String, Object>> listNew = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, String> m : list) {
			HashMap<String, Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
			}
			boolean flag = false;// �����������ѭ��
			if (m.get("a0501b").equals("") || m.get("a0501b").equals(null)) {// ��ְ�������ж��Ƿ�Ϊ��
				listNew.add(tempmap);
				num++;
				continue;
			}
			String a0500 = m.get("a0500");
			for (A05 a05 : listA05) {
				if (a05.getA0500().equals(a0500)) {// �����²���
					String a0501b = m.get("a0501b");
					String a0524 = m.get("a0524");
					String a0504 = m.get("a0504");
					String a0517 = m.get("a0517");
					a05.setA0500(a0500);
					a05.setA0000(a0000);
					a05.setA0501b(a0501b);
					a05.setA0524(a0524);
					a05.setA0525(a0524);//������a0525==a0524
					a05.setA0504(a0504);
					a05.setA0517(a0517);
					a05.setA0531("0");
					sess.update(a05);
					sess.flush();
					sbUpdata.append(a0501b + "#%");
					updateNum++;
					tempmap.put("a0500", a0500);
					listNew.add(tempmap);
					flag = true;
				}
			}
			if (flag == true) {
				continue;// ��������
			}
			// ���������
			A05 a05 = new A05();

			String a0501b = m.get("a0501b");
			String a0524 = m.get("a0524");
			String a0504 = m.get("a0504");
			String a0517 = m.get("a0517");
			a05.setA0000(a0000);
			a05.setA0501b(a0501b);
			a05.setA0524(a0524);
			a05.setA0525(a0524);//������a0525==a0524
			a05.setA0504(a0504);
			a05.setA0517(a0517);
			a05.setA0531("0");
			sess.save(a05);
			sess.flush();
			String a0500DB = a05.getA0500();// �õ����е�a0500
			saveNum++;
			tempmap.put("a0500", a0500DB);
			listNew.add(tempmap);
			sbSave.append(a0501b + "#%");// #%��Ϊ�ָ�
		}
		if (num == list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String updata = "";
		String savedata = "";
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
		setA0221Value();
		// this.getExecuteSG().addExecuteCode("hint("+"'������:"+updata+"������:"+savedata+"')");
		this.getPageElement("a05Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ְ����ɾ��
	 * @param delA0500
	 * @return
	 * @throws RadowException  
	 * @author lixl1
	 */
	@PageEvent("a05Delete")
	@Synchronous(true)
	@NoRequiredValidate
	public int a05Delete(String delA0500) throws RadowException{
		String[] splitA0500 = delA0500.split("@");
		HBSession sess = null;
		for (String a0500 : splitA0500) {
			try {
				sess = HBUtil.getHBSession();
				A05 a05 = (A05)sess.get(A05.class, a0500);
				sess.delete(a05);
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		setA0221Value();
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			����a01����ְ����
	 * @return
	 * @throws RadowException
	 */
	public int setA0221Value() throws RadowException{
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from a05 where a0000='" + a0000 + "' and A0531 ='0'";
		HBSession sess = HBUtil.getHBSession();
		List<A05> listA05 = sess.createSQLQuery(sql).addEntity(A05.class).list();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if(listA05.size() > 0){
			A05 a05 = listA05.get(0);
			a01.setA0221(a05.getA0501b()); //���ݿ����� ������Ϣ�����ְ���� ��ǰ��ѡ��ְ����
			a01.setA0288(a05.getA0504());
			sess.update(a01);
			sess.flush();
		}else{
			a01.setA0221(null); //���ݿ����� ������Ϣ�����ְ���� ��ǰ��ѡ��ְ����
			a01.setA0288(null);
			sess.update(a01);
			sess.flush();
		}
		return EventRtnType.NORMAL_SUCCESS;
	};
	@PageEvent("a05Save2")
	@Synchronous(true)
	@NoRequiredValidate
	public int a05Save2() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		List<HashMap<String, String>> list = this.getPageElement("a05Grid2").getStringValueList();
		List<String> lists = new ArrayList<String>();
		Set<String> s = new HashSet<String>();
		int saveNum=0;
		int updateNum=0;
		for (HashMap<String, String> m : list) {
			lists.add(m.get("a0501b2"));
		}
		for (String str : lists) {
			boolean b = s.add(str);
			if (!b) {
				this.setMainMessage("ְ�������ظ���д");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		if (a0000.equals(null) || a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡"); 
			return EventRtnType.NORMAL_SUCCESS; }
		
		int num = 0;
		// ����a0000�ֶ��ҳ�����Ա�ļ�ͥ��Ա��a0500
		String sql = "select * from a05 where a0000=" + "'" + a0000 + "'";
		List<A05> listA05 = sess.createSQLQuery(sql).addEntity(A05.class).list();
		List<HashMap<String, Object>> listNew = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, String> m : list) {
			HashMap<String, Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
			}
			boolean flag = false;// �����������ѭ��
			if (m.get("a0501b2").equals("") || m.get("a0501b2").equals(null)) {// ��ְ�������ж��Ƿ�Ϊ��
				listNew.add(tempmap);
				num++;
				continue;
			}
			String a0500 = m.get("a0500");
			for (A05 a05 : listA05) {
				if (a05.getA0500().equals(a0500)) {// �����²���
					String a0501b2 = m.get("a0501b2");
					String a0524 = m.get("a0524");
					String a0504 = m.get("a0504");
					String a0517 = m.get("a0517");
					a05.setA0500(a0500);
					a05.setA0000(a0000);
					a05.setA0501b(a0501b2);
					a05.setA0524(a0524);
					a05.setA0525(a0524);
					a05.setA0504(a0504);
					a05.setA0517(a0517);
					a05.setA0531("1");
					sess.update(a05);
					updateNum++;
					tempmap.put("a0500", a0500);
					listNew.add(tempmap);
					flag = true;
				}
			}
			if (flag == true) {
				continue;// ��������
			}
			// ���������
			A05 a05 = new A05();

			String a0501b2 = m.get("a0501b2");
			String a0524 = m.get("a0524");
			String a0504 = m.get("a0504");
			String a0517 = m.get("a0517");
			a05.setA0000(a0000);
			a05.setA0501b(a0501b2);
			a05.setA0524(a0524);
			a05.setA0525(a0524);
			a05.setA0504(a0504);
			a05.setA0517(a0517);
			a05.setA0531("1");
			sess.save(a05);
			sess.flush();

			String a0500DB = a05.getA0500();// �õ����е�a0500
			tempmap.put("a0500", a0500DB);
			listNew.add(tempmap);
			saveNum++;
		}
		if (num == list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		setA0192eValue(a0000);
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
		this.getPageElement("a05Grid2").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��ְ��ɾ��
	 * @param delA0500
	 * @return
	 * @throws RadowException  
	 * @author lixl1
	 */
	@PageEvent("a05Delete2")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int a05Delete2(String delA0500) throws RadowException{
		String[] splitA0500 = delA0500.split("@");
		
		String a0000 = this.getPageElement("personA0000").getValue();
		for (String a0500 : splitA0500) {
			HBSession sess = null;
			try {
				sess = HBUtil.getHBSession();
				A05 a05 = (A05)sess.get(A05.class, a0500);
				sess.delete(a05);
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		setA0192eValue(a0000);
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			����a01��ְ��
	 * @param a0000
	 * @return
	 */
	public int setA0192eValue(String a0000){
		String sql = "select * from a05 where a0000=" + "'" + a0000 + "' and A0531 ='1'";
		HBSession sess = HBUtil.getHBSession();
		List<A05> listA05 = sess.createSQLQuery(sql).addEntity(A05.class).list();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if(listA05.size() > 0){
			A05 a05 = listA05.get(0);
			a01.setA0192e(a05.getA0501b()); //���ݿ����� ������Ϣ�����ְ��
			a01.setA0192c(a05.getA0504());//��ְ��ʱ��
			sess.update(a01);
			sess.flush();
		}else{
			a01.setA0192e(null); //���ݿ����� ������Ϣ�����ְ��
			a01.setA0192c(null);//��ְ��ʱ��
			sess.update(a01);
			sess.flush();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("a08Save")
	@Synchronous(true)
	@NoRequiredValidate
	public int a08Save() throws RadowException, IntrospectionException, IllegalAccessException,
			InvocationTargetException, AppException {
		List<HashMap<String, String>> list1 = this.getPageElement("a08Grid").getStringValueList();
		List<String> lists = new ArrayList<String>();
		Set<String> s = new HashSet<String>();
		for (HashMap<String, String> m : list1) {
			lists.add(m.get("a0837"));
		}
		for (String str : lists) {
			boolean b = s.add(str);
			if (!b) {
				                          
				this.setMainMessage("����������ظ���д");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		StringBuilder sbUpdata = new StringBuilder();// ���µ�ֵ
		StringBuilder sbSave = new StringBuilder();// �������
		int saveNum=0;
		int updateNum=0;
		HBSession sess = HBUtil.getHBSession();		
		String a0000 = this.getPageElement("personA0000").getValue();
		if (a0000.equals(null) || a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡"); 
			return EventRtnType.NORMAL_SUCCESS; 
			}
		
		List<HashMap<String, String>> list = this.getPageElement("a08Grid").getStringValueList();
		String sql = "select * from a08 where a0000=" + "'" + a0000 + "'";
		List<A08> listA08 = sess.createSQLQuery(sql).addEntity(A08.class).list();
		List<HashMap<String, Object>> listNew = new ArrayList<HashMap<String, Object>>();
		for (HashMap<String, String> m : list) {
			HashMap<String, Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
			}
			boolean flag = false;// �����������ѭ��
			String a0800 = m.get("a0800");
			//��ҵʱ�䲻������������ѧʱ��
			String a0807 = m.get("a0807");//��ҵʱ��
			String a0804 = m.get("a0804");//��ѧʱ��
			String a0904 = m.get("a0904");//ѧλ����ʱ��
			if(a0807!=null&&!"".equals(a0807)&&a0804!=null&&!"".equals(a0804)){
				if(a0807.length()==6){
					a0807 += "00";
				}
				if(a0804.length()==6){
					a0804 += "00";
				}
				if(a0807.compareTo(a0804)<0){
					
					this.setMainMessage("��ҵʱ�䲻��������ѧʱ��");
					return EventRtnType.FAILD;
				}
			}
			if(a0804!=null&&!"".equals(a0804)&&a0904!=null&&!"".equals(a0904)){
				if(a0804.length()==6){
					a0804 += "00";
				}
				if(a0904.length()==6){
					a0904 += "00";
				}
				if(a0904.compareTo(a0804)<0){
					this.setMainMessage("ѧλ����ʱ�䲻��������ѧʱ��");
					return EventRtnType.FAILD;
				}
			}
			for (A08 a08 : listA08) {
				if (a08.getA0800().equals(a0800)) {// �����²���
					
					String a0837 = m.get("a0837");
					String a0801b = m.get("a0801b");
					String a0801a = m.get("a0801a");
					String a0811 = m.get("a0811");
					String a0901b = m.get("a0901b");
					String a0901a = m.get("a0901a");
					String a0814 = m.get("a0814");
					String a0827 = m.get("a0827");
					String a0824 = m.get("a0824");
					a0804 = m.get("a0804");
					a0807 = m.get("a0807");
					a0904 = m.get("a0904");
					// a08.setA0000(a0000);
					// a08.setA0800(a0800);
					a08.setA0837(a0837);
					a08.setA0801b(a0801b);
					a08.setA0801a(a0801a);
					a08.setA0811(a0811);
					a08.setA0901b(a0901b);
					a08.setA0901a(a0901a);
					a08.setA0814(a0814);
					a08.setA0827(a0827);
					a08.setA0824(a0824);
					a08.setA0804(a0804);
					a08.setA0807(a0807);
					a08.setA0904(a0904);
					sess.update(a08); // ����
					sbUpdata.append(a0901a + "#%");
					tempmap.put("a0800", a0800);
					listNew.add(tempmap);
					updateNum++;
					flag = true;
				}
			}
			if (flag == true) {
				continue;// ��������
			}

			// ���������
			A08 a08 = new A08();
			String a0837 = m.get("a0837");
			String a0801b = m.get("a0801b");
			String a0801a = m.get("a0801a");
			String a0811 = m.get("a0811");
			String a0901b = m.get("a0901b");
			String a0901a = m.get("a0901a");
			String a0814 = m.get("a0814");
			String a0827 = m.get("a0827");
			String a0824 = m.get("a0824");
			a0804 = m.get("a0804");
			a0807 = m.get("a0807");
			a0904 = m.get("a0904");
			a08.setA0000(a0000);
			// a08.setA0800(a0800);
			a08.setA0837(a0837);
			a08.setA0801b(a0801b);
			a08.setA0801a(a0801a);
			a08.setA0811(a0811);
			a08.setA0901b(a0901b);
			a08.setA0901a(a0901a);
			a08.setA0814(a0814);
			a08.setA0827(a0827);
			a08.setA0824(a0824);
			a08.setA0804(a0804);
			a08.setA0807(a0807);
			a08.setA0904(a0904);
			if(a0801b != null && !"".equals(a0801b)){
				if(a0801b.length() > 8){
					this.setMainMessage("ѧ������ ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0801a != null && !"".equals(a0801a)){
				if(a0801a.length() > 120){
					this.setMainMessage("ѧ������ ���ܳ���120�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0811 != null && !"".equals(a0811)){
				if(a0811.length() > 8){
					this.setMainMessage("ѧ������  ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0901b != null && !"".equals(a0901b)){
				if(a0901b.length() > 8){
					this.setMainMessage("ѧλ����  ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0901a != null && !"".equals(a0901a)){
				if(a0901a.length() > 40){
					this.setMainMessage("ѧλ����  ���ܳ���40�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0814 != null && !"".equals(a0814)){
				if(a0814.length() > 120){
					this.setMainMessage("ѧУ����λ������  ���ܳ���120�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0827 != null && !"".equals(a0827)){
				if(a0827.length() > 8){
					this.setMainMessage("��ѧרҵ���    ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0824 != null && !"".equals(a0824)){
				if(a0824.length() > 40){
					this.setMainMessage("��ѧרҵ����    ���ܳ���40�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0804 != null && !"".equals(a0804)){
				if(a0804.length() > 8){
					this.setMainMessage("��ѧ����    ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0807 != null && !"".equals(a0807)){
				if(a0807.length() > 8){
					this.setMainMessage("�ϣ��ޣ�ҵ����    ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			if(a0904 != null && !"".equals(a0904)){
				if(a0904.length() > 8){
					this.setMainMessage("ѧλ��������    ���ܳ���8�֣�");
					return EventRtnType.FAILD;
				}
			}
			
			sess.save(a08);
			sess.flush();
			String a0800DB = a08.getA0800();// �õ����е�a0800
			tempmap.put("a0800", a0800DB);
			listNew.add(tempmap);
			saveNum++;
			sbSave.append(a0901a + "#%");// #%��Ϊ�ָ�
		}
		String updata = "";
		String savedata = "";
		if (!sbUpdata.toString().equals("")) {
			updata = sbUpdata.toString().substring(0, (sbUpdata.toString().length() - 2)).replace("#%", ",");
		}
		if (!sbSave.toString().equals("")) {
			savedata = sbSave.toString().substring(0, (sbSave.toString().length() - 2)).replace("#%", ",");
		}
		System.out.println("hint(" + "������:" + updata + "������:" + savedata + ")");
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		DegreesAddPagePageModel addPagePageModel=new DegreesAddPagePageModel();
		addPagePageModel.printout(a0000, sess, "1");
		addPagePageModel.printout(a0000, sess, "2");
		updateZGQRZXueliXuewei(a0000,sess);
		updateZGZZXueliXuewei(a0000,sess);//�����ְѧ��ѧλ��־
		updateXueliXuewei(a0000,sess,"1");
		updateXueliXuewei(a0000,sess,"2");
		//�ж�ȫ�������ѧ��ѧλ����ְ���ѧ��ѧλ�Ƿ���ͬ�ȼ�,���ͬ��,�������û�ѡ���ĸ��ǲ���ȫ���ƺ���ְ�����ѧ��
		boolean isSame=addPagePageModel.isSame(a0000,sess);
		if(isSame){
			this.setRadow_parent_data(a0000);
			this.getExecuteSG().addExecuteCode("open('"+a0000+"')");
			this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
			this.getPageElement("a08Grid").setValueList(listNew);
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			updateZGXueliXuewei(a0000,sess);//���ѧ��ѧλ��־
			updateA01ZGxuelixuewei(a0000, sess);
			this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
			this.getPageElement("a08Grid").setValueList(listNew);
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	private void updateZGQRZXueliXuewei(String a0000, HBSession sess) {
		String sql=" update a08 set a0831='0',a0832='0' where a0000='"+a0000+"' and a0899='true' and a0837='1'";
		sess.createSQLQuery(sql).executeUpdate();
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1'";//��������ȫ����ѧ�� 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//ѧ������
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//ѧ������
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//����ж�����¼,��һ����¼ѧ�����벻Ϊ�վ������ѧ��,ʣ��ѧ������������һ��һ��Ҳ������
				if(list1!=null&&list1.size()>0){
					Object[] a08=list1.get(0);
					String xuelidaima=a08[1]==null?"":a08[1].toString();
					if(!StringUtil.isEmpty(xuelidaima)){
						sess.createSQLQuery(" update a08 set a0831='1' where a0800='"+a08[0]+"'").executeUpdate();
					}
				}
				
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
				//����ж�����¼,��һ����¼ѧλ���벻Ϊ�վ������ѧλ,ʣ��ѧλ����������һ���ԱȺ����һ��ҲΪ���ѧλ
				if(list2!=null&&list2.size()>0){
					Object[] a08_1=list2.get(0);
					String xueweidaima=a08_1[1].toString();
					if(!StringUtil.isEmpty(xueweidaima)){
						sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_1[0]+"'").executeUpdate();
						if(xueweidaima.startsWith("1")){
							for(int i=1;i<list2.size();i++){
								Object[] a08_x=list2.get(i);
								String xueweidaima_x=a08_x[1].toString();
								if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
									sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
								}
							}
						}else{
							String reg=xueweidaima.substring(0,1);
								for(int i=1;i<list2.size();i++){
									Object[] a08_x=list2.get(i);
									String xueweidaima_x=a08_x[1].toString();;
									if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
										sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
									}
									
								}
						}
					
					}
					
				}
				sess.flush();
	}
	private void updateZGZZXueliXuewei(String a0000, HBSession sess) {
		String sql=" update a08 set a0838='0',a0839='0' where a0000='"+a0000+"' and a0899='true' and a0837='2'";
		sess.createSQLQuery(sql).executeUpdate();
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2'";// ����������ְѧ�� 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//ѧ������
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//ѧ������
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//����ж�����¼,��һ����¼ѧ�����벻Ϊ�վ������ѧ��,ʣ��ѧ������������һ��һ��Ҳ������
		if(list1!=null&&list1.size()>0){
			Object[] a08=list1.get(0);
			String xuelidaima=a08[1]==null?"":a08[1].toString();
			if(!StringUtil.isEmpty(xuelidaima)){
				sess.createSQLQuery(" update a08 set a0838='1' where a0800='"+a08[0]+"'").executeUpdate();
			}
		}
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		//����ж�����¼,��һ����¼ѧλ���벻Ϊ�վ������ѧλ,ʣ��ѧλ����������һ���ԱȺ����һ��ҲΪ���ѧλ
		if(list2!=null&&list2.size()>0){
			Object[] a08_1=list2.get(0);
			String xueweidaima=a08_1[1].toString();
			if(!StringUtil.isEmpty(xueweidaima)){
				sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_1[0]+"'").executeUpdate();

				if(xueweidaima.startsWith("1")){
					for(int i=1;i<list2.size();i++){
						Object[] a08_x=list2.get(i);
						String xueweidaima_x=a08_x[1].toString();
						if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
							sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
						}
					}
				}else{
					String reg=xueweidaima.substring(0,1);
						for(int i=1;i<list2.size();i++){
							Object[] a08_x=list2.get(i);
							String xueweidaima_x=a08_x[1].toString();
							if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
								sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
							}
						}
				}
			}
		}
		sess.flush();
	}
	private void updateXueliXuewei(String a0000, HBSession sess,String a0837) throws AppException {
		checkXL(sess,a0837,a0000);
		checkXW(sess,a0837,a0000);
	}
	private void checkXL(HBSession sess,String a0837,String a0000) throws AppException {//a0837�������
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //��ѧ��������������
				if("1".equals(a0837)){
					sql1+=" and a0831='1'";
				}
				if("2".equals(a0837)){
					sql1+=" and a0838='1'";
				}
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//Ժϵ
					String zy_xl = xueli.getA0824();//רҵ
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					
					if(!"".equals(zy_xl)){
						
						//��רҵ�еġ�רҵ�������
						zy_xl = zy_xl.replace("רҵ", "");
						
						zy_xl += "רҵ";
					}
					 xl = xueli.getA0801a();//ѧ�� ����
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//����a01 ѧ��ѧλ��Ϣ:ȫ���ƣ�qrzxlѧ�� qrzxwѧλ qrzxlxxԺУϵ��רҵ����ְ zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		if("1".equals(a0837)){//ȫ����
			a01.setQrzxl(xl);//ѧ��
			a01.setQrzxlxx(xlxx);
		}else{//��ְ
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}

	private void checkXW(HBSession sess,String a0837,String a0000) throws AppException{
		String xw = "";//ѧλ����
		String xwxx = "";//ѧλ��Ժϵרҵ
		String sql="select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";
		if("1".equals(a0837)){
			sql+=" and a0832='1' order by a0901b";
		}
		if("2".equals(a0837)){
			sql+=" and a0839='1' order by a0901b";
		}
		List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();
		if(list!=null&&list.size()>0){
			A08 xuewei=list.get(0);
			xw=xuewei.getA0901a();
			String yx_xw=xuewei.getA0814();
			String zy_xw=xuewei.getA0824();
			if(yx_xw==null){
				yx_xw = "";
			}
			if(zy_xw==null){
				zy_xw = "";
			}
			if(!"".equals(zy_xw)){
				//��רҵ�еġ�רҵ�������
				zy_xw = zy_xw.replace("רҵ", "");
				
				zy_xw += "רҵ";
			}
			xwxx = yx_xw+zy_xw;
			if(xw==null&&"".equals(xw)){
				xwxx=null;
			}
			if(list.size()>1){
				A08 xuewei2=list.get(1);
				String xw2=xuewei2.getA0901a();
				String yx_xw2=xuewei2.getA0814();
				String zy_xw2=xuewei2.getA0824();
				if(yx_xw2==null){
					yx_xw2 = "";
				}
				if(zy_xw2==null){
					zy_xw2 = "";
				}
				if(!"".equals(zy_xw2)){
					
					//��רҵ�еġ�רҵ�������
					zy_xw2 = zy_xw2.replace("רҵ", "");
					
					zy_xw2 += "רҵ";
				}
				if(xw2!=null&&!"".equals(xw2)){
					if(xw==null&&"".equals(xw)){
						xw=xw2;
						xwxx= yx_xw2+zy_xw2;
					}else{
						xw=xw+","+xw2;
						if(StringUtil.isEmpty(xwxx)){
							xwxx= yx_xw2+zy_xw2;
						}else{
							xwxx=xwxx+","+yx_xw2+zy_xw2;
						}
						
					}
				}
			}
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if("1".equals(a0837)){//ȫ����
			a01.setQrzxw(xw);//ѧλ
			a01.setQrzxwxx(xwxx);
		}else{//��ְ
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}
	private void updateZGXueliXuewei(String a0000, HBSession sess) throws AppException {
		String update ="update a08 set a0834='0',a0835='0'  where a0000='"+a0000+"' and a0899='true'";
		sess.createSQLQuery(update).executeUpdate();
		sess.flush();
		String sql="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='1' and a0831='1' and a0899='true'";
		String sql2="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='2' and a0838='1' and a0899='true'";
		Object[] qrz=(Object[]) sess.createSQLQuery(sql).uniqueResult();
		Object[] zz=(Object[]) sess.createSQLQuery(sql2).uniqueResult();
		if((qrz==null||qrz.length<1)&&(zz!=null&&zz.length>0)){
			Object a0800=zz[0];
			sess.createSQLQuery("update a08 set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if((zz==null||zz.length<1)&&(qrz!=null&&qrz.length>0)){
			Object a0800=qrz[0];
			sess.createSQLQuery("update a08 set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if(qrz!=null&&zz!=null&&qrz.length>0&&zz.length>0){
			String qrzxueli=qrz[1].toString();
			String zzxueli=zz[1].toString();
			String qrzxl=qrzxueli.substring(0,1);
			String zzxl=zzxueli.substring(0, 1);
			if(qrzxl.compareTo(zzxl)==-1){
				sess.createSQLQuery("update a08 set a0834='1' where a0800='"+qrz[0]+"'").executeUpdate();
			}else{
				sess.createSQLQuery("update a08 set a0834='1' where a0800='"+zz[0]+"'").executeUpdate();
			}
		}
		String sql3="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0837='1' and a0832='1' and a0899='true'";
		String sql4="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0837='2' and a0839='1' and a0899='true'";
		List<Object[]> list1=sess.createSQLQuery(sql3).list();
		List<Object[]> list2=sess.createSQLQuery(sql4).list();
		if((list1==null||list1.size()<1)&&(list2!=null&&list2.size()>0)){
			for(int i=0;i<list2.size();i++){
				Object a0800=list2.get(i)[0];
				sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			}
		}
		if((list1!=null&&list1.size()>0)&&(list2==null||list2.size()<1)){
			for(int i=0;i<list1.size();i++){
				Object a0800=list1.get(i)[0];
				sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			}
		}
		if(list1!=null&&list2!=null&&list1.size()>0&&list2.size()>0){
			String qrzxuewei=list1.get(0)[1].toString();
			String zzxuewei=list2.get(0)[1].toString();
			String qrzxw=qrzxuewei.substring(0,1);
			String zzxw=zzxuewei.substring(0, 1);
			if(qrzxw.compareTo(zzxw)==-1){
				for(int i=0;i<list1.size();i++){
					Object a0800=list1.get(i)[0];
					sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				}
			}else{
				for(int i=0;i<list2.size();i++){
					Object a0800=list2.get(i)[0];
					sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				}
			}
		}
		sess.flush();
	}
	public void updateA01ZGxuelixuewei(String a0000,HBSession sess) throws AppException{
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0834='1' and a0899='true'";  //��ѧ��������������
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//Ժϵ
					String zy_xl = xueli.getA0824();//רҵ
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					if(!"".equals(zy_xl)){
						
						//��רҵ�еġ�רҵ�������
						zy_xl = zy_xl.replace("רҵ", "");
						
						zy_xl += "רҵ";
					}
					 xl = xueli.getA0801a();//ѧ�� ����
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setZgxl(xl);//ѧ��
			a01.setZgxlxx(xlxx);	
			sess.update(a01);
			sess.flush();
			String xw = "";//ѧλ����
			String xwxx = "";//ѧλ��Ժϵרҵ
			String sql="select * from a08 where a0000='"+a0000+"' and a0835='1' and a0899='true'";
			List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();
			if(list!=null&&list.size()>0){
				A08 xuewei=list.get(0);
				xw=xuewei.getA0901a();
				String yx_xw=xuewei.getA0814();
				String zy_xw=xuewei.getA0824();
				if(yx_xw==null){
					yx_xw = "";
				}
				if(zy_xw==null){
					zy_xw = "";
				}
				if(!"".equals(zy_xw)){
					//��רҵ�еġ�רҵ�������
					zy_xw = zy_xw.replace("רҵ", "");
					
					zy_xw += "רҵ";
				}
				xwxx = yx_xw+zy_xw;
				if(xw==null&&"".equals(xw)){
					xwxx=null;
				}
				if(list.size()>1){
					A08 xuewei2=list.get(1);
					String xw2=xuewei2.getA0901a();
					String yx_xw2=xuewei2.getA0814();
					String zy_xw2=xuewei2.getA0824();
					if(yx_xw2==null){
						yx_xw2 = "";
					}
					if(zy_xw2==null){
						zy_xw2 = "";
					}
					if(!"".equals(zy_xw2)){
						
						//��רҵ�еġ�רҵ�������
						zy_xw2 = zy_xw2.replace("רҵ", "");
						
						zy_xw2 += "רҵ";
					}
					if(xw2!=null&&!"".equals(xw2)){
						if(xw==null&&"".equals(xw)){
							xw=xw2;
							xwxx= yx_xw2+zy_xw2;
						}else{
							xw=xw+","+xw2;
							if(StringUtil.isEmpty(xwxx)){
								xwxx= yx_xw2+zy_xw2;
							}else{
								xwxx=xwxx+","+yx_xw2+zy_xw2;
							}
							
						}
					}
				}
			}
		a01.setZgxw(xw);//ѧλ
		a01.setZgxwxx(xwxx);
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
}
	/**
	 * ѧ��ѧλɾ��
	 * @param delA0800
	 * @return
	 * @throws RadowException  
	 * @author lixl1
	 */
	@PageEvent("a08Delete")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int a08Delete(String delA0800) throws RadowException{
		String[] splitA0800 = delA0800.split("@");
		for (String a0800 : splitA0800) {
			HBSession sess = null;
			try {
				sess = HBUtil.getHBSession();
				A08 a08 = (A08)sess.get(A08.class, a0800);
				sess.delete(a08);
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	// ==========================================end====>lixl1==============================================

	/**
	 * ������λ��ְ�񱣴�
	 * 
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author wangs2
	 */
	@PageEvent("a02Save")
	@NoRequiredValidate
	public int a02Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		String a0000 = this.getPageElement("personA0000").getValue();
		if(a0000==null) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��',A0000����������,null,500);");
			return EventRtnType.FAILD;
		}
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		List<HashMap<String,String>> list = this.getPageElement("a02Grid").getStringValueList();
		int saveNum=0;
		int updateNum=0;
		int isNullNum=0;
		boolean a0279flag=false;//�Ƿ���ְ��ı�ʶ��
		for(HashMap<String,String> m : list){
			String a0200 = m.get("a0200");
			if(m.get("a0201b").equals("")||m.get("a0201b").equals(null)) {//����ְ���� ���ж��Ƿ�Ϊ��
				isNullNum++;
				continue;
			}
			A02 a02 = (A02) sess.get(A02.class, a0200);
			if(a02==null){//�������ݿ���û�и���Ա,ִ�б���
				a02=new A02();
				String a0201a = m.get("a0201a");
				String a0201b = m.get("a0201b");
				String a0201d = m.get("a0201d");
				String a0215a = m.get("a0215a");
				String a0243 = getStringTime8L(m.get("a0243"));
				String a0245 = m.get("a0245");
				String a0265 = getStringTime8L(m.get("a0265"));
				String a0267 = m.get("a0267");
				String a0272 = m.get("a0272");
				String a0279 = m.get("a0279");
				if(a0201b!=null && !"".equals(a0201b)){
					if(a0201b.length()>199){
						this.setMainMessage("��ְ�������벻�ܳ���199�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a0201a!=null && !"".equals(a0201a)){
					if(a0201a.length()>200){
						this.setMainMessage("��ְ�������Ʋ��ܳ���200�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a0215a!=null && !"".equals(a0215a)){
					if(a0215a.length()>100){
						this.setMainMessage("ְ�����Ʋ��ܳ���100�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0245!=null && !"".equals(a0245)){
					if(a0245.length()>1500){
						this.setMainMessage("��ְ�ĺŲ��ܳ���1500�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0267!=null && !"".equals(a0267)){
					if(a0267.length()>1500){
						this.setMainMessage("��ְ�ĺŲ��ܳ���1500�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0272!=null && !"".equals(a0272)){
					if(a0272.length()>1000){
						this.setMainMessage("ְ��䶯ԭ���������ܳ���100�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if("1".equals(a0279) && !a0279flag){//��ְ��ֻ����һ��
					a0279flag = true;
				}else{
					a0279="0";
				}
				a02.setA0000(a0000);
				a02.setA0200(m.get("a0200"));
				a02.setA0281(m.get("a0281"));
				a02.setA0201a(a0201a);
				a02.setA0201b(a0201b);
				a02.setA0215a(a0215a);
				a02.setA0255(m.get("a0255"));
				a02.setA0279(a0279);
				a02.setA0219(m.get("a0219"));
				a02.setA0201e(m.get("a0201e"));
				a02.setA0201d(a0201d);
				a02.setA0251b(m.get("a0251b"));
				a02.setA0247(m.get("a0247"));
				a02.setA0243(a0243);
				a02.setA0245(a0245);
				a02.setA0265(a0265);
				a02.setA0267(a0267);
				a02.setA0272(a0272);
				if("-1".equals(a0201b)){//������λ
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201b);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				sess.save(a02);
				sess.flush();
				saveNum++;
				sbSave.append(a02.toString());
			}else{
				A02 a02_old = new A02();
				a02_old = a02;
				String a0201a = m.get("a0201a");
				String a0201b = m.get("a0201b");
				String a0201d = m.get("a0201d");
				String a0215a = m.get("a0215a");
				String a0243 = getStringTime8L(m.get("a0243"));
				String a0245 = m.get("a0245");
				String a0265 = getStringTime8L(m.get("a0265"));
				String a0267 = m.get("a0267");
				String a0272 = m.get("a0272");
				String a0279 = m.get("a0279");
				if(a0201b!=null && !"".equals(a0201b)){
					if(a0201b.length()>199){
						this.setMainMessage("��ְ�������벻�ܳ���199�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a0201a!=null && !"".equals(a0201a)){
					if(a0201a.length()>200){
						this.setMainMessage("��ְ�������Ʋ��ܳ���200�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a0215a!=null && !"".equals(a0215a)){
					if(a0215a.length()>100){
						this.setMainMessage("ְ�����Ʋ��ܳ���100�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0245!=null && !"".equals(a0245)){
					if(a0245.length()>1500){
						this.setMainMessage("��ְ�ĺŲ��ܳ���1500�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0267!=null && !"".equals(a0267)){
					if(a0267.length()>1500){
						this.setMainMessage("��ְ�ĺŲ��ܳ���1500�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a0272!=null && !"".equals(a0272)){
					if(a0272.length()>1000){
						this.setMainMessage("ְ��䶯ԭ���������ܳ���100�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if("1".equals(a0279) && !a0279flag){//��ְ��ֻ����һ��
					a0279flag = true;
				}else{
					a0279="0";
				}
				a02.setA0000(a0000);
				a02.setA0200(m.get("a0200"));
				a02.setA0281(m.get("a0281"));
				a02.setA0201a(a0201a);
				a02.setA0201b(a0201b);
				a02.setA0215a(a0215a);
				a02.setA0255(m.get("a0255"));
				a02.setA0279(a0279);
				a02.setA0219(m.get("a0219"));
				a02.setA0201e(m.get("a0201e"));
				a02.setA0201d(a0201d);
				a02.setA0251b(m.get("a0251b"));
				a02.setA0247(m.get("a0247"));
				a02.setA0243(a0243);
				a02.setA0245(a0245);
				a02.setA0265(a0265);
				a02.setA0267(a0267);
				a02.setA0272(a0272);
				if("-1".equals(a0201b)){//������λ
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201b);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				a02.setA0201c(a0272);
				List<String[]> listww = Map2Temp.getLogInfo(a02,a02_old);
				if(listww.size()!=0) {//û�иı䣬ֱ�ӹر�ҳ��
					sess.update(a02);
				}
				updateNum++;
				sbUpdata.append(a02.toString());
			}
		}
		if(isNullNum == list.size()){
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.FAILD;
		}
		System.out.println("hint("+"������:"+sbUpdata+"������:"+sbSave+")");
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
		setA0279aValue(a0000);
		setA0281Value(a0000);
		setA0192aValue(a0000);
		jspGridQuery("a02Grid@a02Savecond");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 
	 * @param a0201b
	 * @return
	 */
	@PageEvent("isA0201b")
	public int isA0201b(String a0201b){
		String b0194 = B01ALL.get(a0201b);
		if("".equals(b0194) || b0194 == null || "3".equals(b0194)){
			this.setMainMessage("����ѡ��������鵥λ��");
			this.getExecuteSG().addExecuteCode("clearA0201bValue();");
			return EventRtnType.FAILD;
		}else{
			this.getExecuteSG().addExecuteCode("setA0201aValue();");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	/**
	 * ������λ��ְ��ɾ��
	 * @param delA0200
	 * @return
	 * @throws RadowException  
	 * @author wangs2 
	 */
	@PageEvent("a02Delete")
	@Synchronous(true)
	@NoRequiredValidate
	public int a02Delete(String delA0200) throws RadowException{
		String[] splitA0200 = delA0200.split("@");
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from A02 a where a0000='"+a0000+"'";
		boolean notDelalert = notDelalert(sql, splitA0200.length);
		if(!notDelalert) {
			this.setMainMessage("���뱣��һ����Ч��Ϣ��");
			jspGridQuery("a02Grid@a02Savecond");//��ֹҳ�澲̬ɾ����������
			return EventRtnType.FAILD;
		}
		String getA0200Num="";
		for (String a0200Num : splitA0200) {
			getA0200Num += "'"+a0200Num+"',";
		}
		String sqlGetA0279Num="select * from A02 a where a0000='"+a0000+"' and a0200 in ("+getA0200Num.substring(0,getA0200Num.length()-1)+") and a0279='1'";
		PageQueryData pageQuery = this.pageQuery(sqlGetA0279Num,"SQL", -1, 9999);
		if(pageQuery.getTotalCount() > 0){
			this.setMainMessage("���뱣��һ����ְ����Ϣ��");
			jspGridQuery("a02Grid@a02Savecond");//��ֹҳ�澲̬ɾ����������
			return EventRtnType.FAILD;
		};
		for (String a0200 : splitA0200) {
			HBSession sess = null;
			try {
				sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				sess.delete(a02);
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		setA0279aValue(a0000);//����ȷ��ʣ����Ϣ������һ����ְ��
		setA0281Value(a0000);
		setA0192aValue(a0000);
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			ɾ���򱣴���Ϣ�������ְ�񣬱��������ְ��
	 * @param a0000
	 * @return
	 */
	private String setA0281Value(String a0000) {
		String queryTotal="select * from A02 a where a0000='"+a0000+"' and A0281='true'";
		try {
			PageQueryData pageQuery = this.pageQuery(queryTotal,"SQL", -1, 9999);
			if(pageQuery.getTotalCount() < 1){//û�в鵽�����ְ��ģ��������һ����ϢΪ���ְ��
				String queryA0281="select a0200 from A02 a where a0000='"+a0000+"'  order by A0243 desc";
				PageQueryData pageQueryA0281 = this.pageQuery(queryA0281,"SQL", -1, 9999);
				List a0281List = (List)pageQueryA0281.getData();
				Map map = (Map)a0281List.get(0);
				String a0200=map.get("a0200")+"";
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02) sess.get(A02.class, a0200);
				a02.setA0281("true");
				sess.update(a02);
				sess.flush();
			}
		} catch (RadowException e) {
			System.out.println("ɾ����Ϣ��������ְ����Ϣʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS+"";
	}

	/**
	 * @author wangs2
	 *      ɾ���򱣴���Ϣ������ְ�񣬱�������ְ����Ϣ
	 * @param a0000
	 * @return
	 */
	private String setA0279aValue(String a0000) {
		String queryTotal="select * from A02 a where a0000='"+a0000+"' and a0279='1'";
		try {
			PageQueryData pageQuery = this.pageQuery(queryTotal,"SQL", -1, 9999);
			if(pageQuery.getTotalCount() < 1){//û�в鵽����ְ������ģ��������һ����ϢΪ��ְ��
				String queryA0279="select a0200 from A02 a where a0000='"+a0000+"'  order by A0243 desc";
				PageQueryData pageQueryA0279 = this.pageQuery(queryA0279,"SQL", -1, 9999);
				List a0279List = (List)pageQueryA0279.getData();
				Map map = (Map)a0279List.get(0);
				String a0200=map.get("a0200")+"";
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02) sess.get(A02.class, a0200);
				a02.setA0279("1");
				sess.update(a02);
				sess.flush();
			}
		} catch (RadowException e) {
			System.out.println("ɾ����Ϣ��������ְ����Ϣʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS+"";
	}
	/**
	 * ����a01ȫ�ƺͼ��
	 * @author wangs2
	 * @param a0000
	 * @return
	 */
	private String setA0192aValue(String a0000) {
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ������λand a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//���� ���
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String zrjc = "";//���
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//��ְ״̬
				String jgbm = a02.getA0201b();//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//�������� ���
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//ְ������
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc);
									}
									//���
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "��" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key ����_$_��������_$_�Ƿ�����
						}else{
							desc_full.put(key_full, value_full + "��" + zwmc);
						}
						//���
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "��" +  zwmc);
						}
					}
				}
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else{//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}
			}
			for(String key : desc_short.keySet()){//���
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "��";
					}
				}else{//����
					if(!"".equals(jgzw)){
						ymjc += jgzw + "��";
					}
				}
			}
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(ԭ"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(ԭ"+ymjc+")";
			}
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			sess.flush();
		}else{
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			sess.flush();
		}
		return EventRtnType.NORMAL_SUCCESS+"";
	}
	//�������
	@PageEvent("a02OrderSave")
	public int a02OrderSave(String data) throws RadowException {
		data = data.substring(0, data.length()-1);
		String[] datas = data.split(",");
		List<String> list = new ArrayList<String>();
		for(int i=0;i<datas.length;i++) {
			list.add(datas[i]);
		}
		request.getSession().setAttribute("a02Order", list);
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ������������
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author wangs2
	 */
	@PageEvent("a14Save")
	@Synchronous(true)
	@NoRequiredValidate
	public int a14Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		List<HashMap<String,String>> list = this.getPageElement("a14Grid").getStringValueList();
		int saveNum=0;
		int updateNum=0;
		int isNullNum = 0;
		for(HashMap<String,String> m : list){
			if(a0000==null) {
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��',A0000����������,null,500);");
				return EventRtnType.FAILD;
			}
			String a1400 = m.get("a1400");
			if(m.get("a1404b").equals("")||m.get("a1404b").equals(null)) {//�ý��ʹ������ж��Ƿ�Ϊ��
				isNullNum ++;
				continue;
			}
			A14 a14 = (A14) sess.get(A14.class, a1400);
			if(a14==null){//�������ݿ���û�и���Ա,ִ�б���
				a14=new A14();
				String a1404b = m.get("a1404b");
				String a1404a = m.get("a1404a");
				String a1415  = m.get("a1415");
				String a1414  = m.get("a1414");
				String a1428  = m.get("a1428");
				String a1411a = m.get("a1411a");
				String a1407  = getStringTime8L(m.get("a1407"));
				String a1424  = getStringTime8L(m.get("a1424"));
				if(a1404a !=null && !"".equals(a1404a)){
					if(a1404a.length()>40){
						this.setMainMessage("�������Ʋ��ܳ���40�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a1411a !=null && !"".equals(a1411a)){
					if(a1411a.length()>60){
						this.setMainMessage("������׼�������Ʋ��ܳ���60�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				a14.setA0000(a0000);
				a14.setA1400 (a1400);
				a14.setA1404b(a1404b);
				a14.setA1404a(a1404a);
				a14.setA1415 (a1415);
				a14.setA1414 (a1414);
				a14.setA1428 (a1428);
				a14.setA1411a(a1411a);
				a14.setA1407 (a1407);
				a14.setA1424 (a1424);
				sess.save(a14);
				sess.flush();
				saveNum++;
				sbSave.append(a14.toString());
			}else{
				A14 a14_old = new A14();
				a14_old = a14;
				String a1404b = m.get("a1404b");
				String a1404a = m.get("a1404a");
				String a1415  = m.get("a1415");
				String a1414  = m.get("a1414");
				String a1428  = m.get("a1428");
				String a1411a = m.get("a1411a");
				String a1407  = getStringTime8L(m.get("a1407"));
				String a1424  = getStringTime8L(m.get("a1424"));
				if(a1404a !=null && !"".equals(a1404a)){
					if(a1404a.length()>40){
						this.setMainMessage("�������Ʋ��ܳ���40�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a1411a !=null && !"".equals(a1411a)){
					if(a1411a.length()>60){
						this.setMainMessage("������׼�������Ʋ��ܳ���60�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				a14.setA1404b(a1404b);
				a14.setA1404a(a1404a);
				a14.setA1415 (a1415);
				a14.setA1414 (a1414);
				a14.setA1428 (a1428);
				a14.setA1411a(a1411a);
				a14.setA1407 (a1407);
				a14.setA1424 (a1424);
				List<String[]> listww = Map2Temp.getLogInfo(a14,a14_old);
				if(listww.size()!=0) {//û�иı䣬ֱ�ӹر�ҳ��
					sess.update(a14);
				}
				updateNum++;
				sbUpdata.append(a14.toString());
			}
		}
		if(isNullNum == list.size()){
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.FAILD;
		}
		setA14z101Value(1,2,a0000);
		System.out.println("hint("+"������:"+sbUpdata+"������:"+sbSave+")");
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��������ɾ��
	 * @param delA1400
	 * @return
	 * @throws RadowException  
	 * @author wangs2 
	 */
	@PageEvent("a14Delete")
	@Synchronous(true)
	@NoRequiredValidate
	public int a14Delete(String delA1400) throws RadowException{
		String[] splitA1400 = delA1400.split("@");
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from A14 a where a0000='"+a0000+"'";
		int totalCount = this.pageQuery(sql,"SQL", -1, 9999).getTotalCount();//��ȡ�ܼ�¼��
		for (String a1400 : splitA1400) {
			HBSession sess = null;
			try {
				sess = HBUtil.getHBSession();
				A14 a14 = (A14)sess.get(A14.class, a1400);
				sess.delete(a14);
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		setA14z101Value(splitA1400.length,totalCount,a0000);
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			a01�����������
	 * @param length
	 * @param totalCount
	 * @param a0000
	 * @return String
	 */
	private String setA14z101Value(int length, int totalCount, String a0000) {
		String A14Z101="";//a01��ȿ������
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(length == totalCount){
			a01.setA14z101("��");
			sess.update(a01);
			sess.flush();
		}else{
			String a14Sql = "select A1407,A1404A from A14 a where a0000='"+a0000+"' order by A1407";
			PageQueryData pageQuery = null;
			try {
				pageQuery = this.pageQuery(a14Sql,"SQL", -1, 999);
				List a14List = (List)pageQuery.getData();
				for (int i = 0; i < pageQuery.getTotalCount() && i<3; i++) {
					Map map = (Map)a14List.get(i);
					String a1407 = map.get("a1407")+"";
					a1407 = a1407.substring(0,4)+"."+a1407.substring(4)+",";
					String a1404a =map.get("a1404a")+"";
					A14Z101 += a1407+a1404a+";";
				}
				a01.setA14z101(A14Z101.substring(0,A14Z101.length()-1)+"��");
				sess.update(a01);
				sess.flush();
				jspGridQuery("a14Grid@a14Savecond@order by A1407 desc");
			} catch (RadowException e) {
			} 
		}
		return a01.getA0000();
	}
	/**
	 * ������Ϣ����
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @author wangs2
	 */
	@PageEvent("a15Save")
	@Synchronous(true)
	@NoRequiredValidate
	public int a15Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String,String>> list = this.getPageElement("a15Grid").getStringValueList();
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		String a1527 = "3";//Ĭ��ѡ��3���������
		int saveNum=0;
		int updateNum=0;
		int isNullNum = 0;
		for(HashMap<String,String> m : list){
			if(a0000==null) {
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��',A0000����������,null,500);");
				return EventRtnType.FAILD;
			};
			String a1500 = m.get("a1500");
			if(m.get("a1521").equals("")||m.get("a1521").equals(null)) {//�ÿ���������ж��Ƿ�Ϊ��
				isNullNum ++;
				continue;
			}
			A15 a15 = (A15) sess.get(A15.class, a1500);
			if(a15==null){//�������ݿ���û�и���Ա,ִ�б���
				a15=new A15();
				String a1521 = m.get("a1521");
				String a1517 = m.get("a1517");
				if(a1521!=null && !a1521.equals("")){
					if(a1521.length()>4){
						this.setMainMessage("������Ȳ��ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a1517!=null && !a1517.equals("")){
					if(a1517.length()>4){
						this.setMainMessage("���˽��۲��ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				
				if(a1527!=null && !a1527.equals("")){
					if(a1527.length()>8){
						this.setMainMessage("��ȸ������ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				a15.setA0000(a0000);
				a15.setA1517(a1517);
				a15.setA1521(a1521);
				a15.setA1527(a1527);
				sess.save(a15);
				sess.flush();
				saveNum++;
				sbSave.append(a15.toString());
			}else{
				A15 a15_old = new A15();
				a15_old = a15;
				
				String a1521 = m.get("a1521");
				String a1517 = m.get("a1517");
				if(a1521!=null && !a1521.equals("")){
					if(a1521.length()>4){
						this.setMainMessage("������Ȳ��ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a1517!=null && !a1517.equals("")){
					if(a1517.length()>4){
						this.setMainMessage("���˽��۲��ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				if(a1527!=null && !a1527.equals("")){
					if(a1527.length()>8){
						this.setMainMessage("��ȸ������ܳ���4�ֽڣ�");
						return EventRtnType.FAILD;
					}
				}
				a15.setA1517(a1517);
				a15.setA1521(a1521);
				a15.setA1527(a1527);
				
				List<String[]> listww = Map2Temp.getLogInfo(a15,a15_old);
				if(listww.size()!=0) {//û�иı䣬ֱ�ӹر�ҳ��
					sess.update(a15);
				}
				updateNum++;
				sbUpdata.append(a15.toString());
			}
			
		}
		if(isNullNum == list.size()){
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.FAILD;
		}
		setA15z101Value(1,2,a0000);
		System.out.println("hint("+"������:"+sbUpdata+"������:"+sbSave+")");
		String alertMsg="";
		if(updateNum == 0){
			alertMsg="'���α��������',"+"'������:"+saveNum+"����Ϣ��'";
		}
		if(saveNum == 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��'";
		}
		if(updateNum != 0 && saveNum != 0){
			alertMsg="'���α��������',"+"'������:"+updateNum+"����Ϣ��������:"+saveNum+"����Ϣ��'";
		}
		this.getExecuteSG().addExecuteCode("$h.alert("+alertMsg+",null,500)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ������Ϣɾ��
	 * @param delA1500
	 * @return
	 * @throws RadowException  
	 * @author wangs2 
	 */
	@PageEvent("a15Delete")
	@Synchronous(true)
	@NoRequiredValidate
	public int a15Delete(String delA1500) throws RadowException{
		String[] splitA1500 = delA1500.split("@");
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from A15 a where a0000='"+a0000+"'";
		int totalCount = this.pageQuery(sql,"SQL", -1, 9999).getTotalCount();//��ȡ�ܼ�¼��
		HBSession sess = HBUtil.getHBSession();
		for (String a1500 : splitA1500) {
			try {
				A15 a15 = (A15)sess.get(A15.class, a1500);
				sess.delete(a15);
				sess.flush();
			} catch (Exception e) {
				this.setMainMessage("ɾ��ʧ�ܣ�");
				return EventRtnType.FAILD;
			}
		}
		setA15z101Value(splitA1500.length,totalCount,a0000);
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			����a01��ȿ������
	 * @param length
	 * @param totalCount
	 * @param a0000
	 * @return String
	 */
	private String setA15z101Value(int length, int totalCount, String a0000) {
		String a15z101="";//a01��ȿ������
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(length == totalCount){
			a01.setA15z101("��");
			a01.setA0191("0");//Ĭ�ϲ�����
			sess.update(a01);
			sess.flush();
		}else{
			String a15Sql = "select A1521,A1517 from A15 a where a0000='"+a0000+"' order by A1521 desc";
			PageQueryData pageQuery = null;
			try {
				pageQuery = this.pageQuery(a15Sql,"SQL", -1, 999);
				List a15List = (List)pageQuery.getData();
				for (int i = pageQuery.getTotalCount()-1; i > pageQuery.getTotalCount()-4 && i>-1; i--) {
					Map map = (Map)a15List.get(i);
					String a1521 = map.get("a1521")+"";
					String a1517 =getCodeName("ZB18", map.get("a1517")+"");
					a15z101 += a1521+a1517+",";
				}
				a01.setA15z101(a15z101.substring(0,a15z101.length()-1)+"��");
				a01.setA0191("1");//Ĭ�Ϲ���
				sess.update(a01);
				sess.flush();
			} catch (RadowException e) {
			} 
		}
		try {
			jspGridQuery("a15Grid@a15Savecond@order by A1521 desc");
		} catch (RadowException e) {
		}
		return a01.getA0000();
	}
	//������Ϣ���ı���
	@PageEvent("a99Z1Save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int a99Z1Save() throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		StringBuilder sbUpdata = new StringBuilder();//���µ�ֵ
		StringBuilder sbSave = new StringBuilder();//�������
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("personA0000").getValue();
		if(a0000.equals(null)||a0000.equals("")) {
			this.setMainMessage("������Ա������Ϣ�����ݣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null) {//����û������ˣ���������Ա������Ϣû�б��棬����ʾ
			this.setMainMessage("�������Ա������Ϣ������,�����棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String,String>> list = this.getPageElement("a99Z1Grid").getStringValueList();
		int  num= 0;
		//����a0000�ֶ��ҳ�����Ա�ļ�ͥ��Ա��a99Z100
		String sql = "select * from a99Z1 where a0000="+"'"+a0000+"'";
		List<A99Z1> listA99Z1= sess.createSQLQuery(sql).addEntity(A99Z1.class).list();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		for(HashMap<String, String> m :list) {
			HashMap<String,Object> tempmap = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : m.entrySet()) {
				tempmap.put(entry.getKey(), entry.getValue());
		    }
			boolean flag = false;//�����������ѭ��
			if(m.get("A99Z101").equals("")||m.get("A99Z101").equals(null)) {//���������ж��Ƿ�Ϊ��
				listNew.add(tempmap);
				num++;
				continue;
			}
			String a99Z100 = m.get("A99Z100");
			for(A99Z1 a99Z1 : listA99Z1) {
				if(a99Z1.getA99Z100().equals(a99Z100)) {//�����²���
					String a99Z101 = m.get("A99Z101");
					String a99Z102 = m.get("A99Z102");
					String a99Z103 = m.get("A99Z103");
					String a99Z104 = m.get("A99Z104");
					a99Z1.setA99z101(a99Z101);
					a99Z1.setA99z102(a99Z102);
					a99Z1.setA99z103(a99Z103);
					a99Z1.setA99z104(a99Z104);
					sess.update(a99Z1);
					sbUpdata.append(a99Z101+"#%");
					tempmap.put("A99Z100", a99Z100);
					listNew.add(tempmap);
					flag = true;
				}
			}
			if(flag==true) {
				continue;//��������
			}
			//���������
			A99Z1 a99Z1 = new A99Z1();
			String a99Z101 = m.get("A99Z101");
			String a99Z102 = m.get("A99Z102");
			String a99Z103 = m.get("A99Z103");
			String a99Z104 = m.get("A99Z104");
			a99Z1.setA0000(a0000);
			a99Z1.setA99z101(a99Z101);
			a99Z1.setA99z102(a99Z102);
			a99Z1.setA99z103(a99Z103);
			a99Z1.setA99z104(a99Z104);
			sess.save(a99Z1);
			sess.flush();
			String a99Z100DB = a99Z1.getA99Z100();
			tempmap.put("A99Z100", a99Z100DB);
			listNew.add(tempmap);
			sbSave.append(a99Z101+"#%");//#%��Ϊ�ָ�
		}
		if(num==list.size()) {
			this.setMainMessage("���ȫ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String updata = "";
		String savedata = "";*/
		String message = "";
		/*if(!sbUpdata.toString().equals("")) {
			updata = "���£�"+sbUpdata.toString().substring(0,(sbUpdata.toString().length()-2)).replace("#%", ",");
		}
		if(!sbSave.toString().equals("")) {
			savedata = "������"+sbSave.toString().substring(0,(sbSave.toString().length()-2)).replace("#%", ",");
		}*/
		message = message(sbSave.toString(),sbUpdata.toString());
		this.getExecuteSG().addExecuteCode("$h.alert('���α��������',"+"'"+message+"'"+",null,500)");
		//this.getExecuteSG().addExecuteCode("hint("+"'������:"+updata+"������:"+savedata+"')");
		this.getPageElement("a99Z1Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//tabҳ���л�
	@PageEvent("tab.tabchange")
	public int tabchange() throws RadowException {
		/*Grid A01 = (Grid)this.getPageElement("a01Grid");
		List<HashMap<String,String>> list = this.getPageElement("a01Grid").getStringValueList();
		int row=A01.getCueRowIndex();
		HashMap<String,String> map=list.get(row);
		String A0000 = map.get("A0000");
		String A0101 = map.get("A0101");
		String A0184 = map.get("A0184");
		this.getPageElement("A0000").setValue(A0000);
		this.getPageElement("A0101").setValue(A0101);
		this.getPageElement("A0184").setValue(A0184);*/
		String a0000 = this.getPageElement("personA0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||a0000.equals("")) {
			
		}else {
			if(a01==null) {//��û����
				this.setMainMessage("���ȱ�����Ա������Ϣ��");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		
		this.getExecuteSG().addExecuteCode("select();");
		/*
		List<HashMap<String,String>> list = this.getPageElement("a01Grid").getStringValueList();
		HashMap<String,String> map=list.get(0);
		String A0000 = map.get("A0000");
		String A0101 = map.get("A0101");
		String A0184 = map.get("A0184");*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * <strong>���ݻ���</strong>
	 * @author wangs2
	 * @param gridIDAndInitGridJSNameAndEndsql	  grid����ID+��ʼ�����ĺ�����+sqlƴ�����<em>����andƴ��</em>
	 * 			<ul><li>
	 * 					<ul><em>��������淶��gridID+"@"+InitGridJSName</em><li>gridID��grid���ID<em>   ǰ�����ַ�����Ϊ���ݿ����</em></li><li>InitGridJSName����ʼ�����ĺ�����</li><li>Endsql:����sql��where����֮�� sql������ƴ��<em>   ��������ַ�����ƴ��</em></li></ul></li>
	 * 			<li>jsp��grid��ID�����淶�����ݿ����+��Grid��+����</li>
	 * 			<li>js�г�ʼ�����ĺ����������淶��InitGridJSName+��();��</li></ul>
	 * @throws RadowException
	 */
	@PageEvent("jspGogridquery")
	@NoRequiredValidate
	public int jspGridQuery(String gridIDAndInitGridJSNameAndEndsql) throws RadowException{
		if(gridIDAndInitGridJSNameAndEndsql == null || "".equals(gridIDAndInitGridJSNameAndEndsql)) {
			this.setMainMessage("���ݻ��Ե���β���Ϊ�գ�");
			return EventRtnType.FAILD;
		}
		String[] split = gridIDAndInitGridJSNameAndEndsql.split("@"); //��ν����и�
		if(split.length < 2) {//��θ�ʽ����Ϊ:���ݿ����+"@"+��ʼ������js������
			this.setMainMessage("���ݻ��Ե���θ�ʽ������������淶�����ݿ����(��Ϊgrid���ID)+\"@\"+��ʼ�����ĺ�������");
			return EventRtnType.FAILD;
		}
		String gridID=split[0];
		String tableName=gridID.substring(0,3);		//���ݿ����<���Զ�ת�ɴ�д>
		String InitGridJSName=split[1];	//��ʼ������js������
		String endSql="";	//and ��ߵ�sqlƴ�����
		if(split.length == 3){
			endSql = split[2];	
		}
		
		if(( tableName == null || "".equals(tableName) ) || ( InitGridJSName == null || "".equals(InitGridJSName) )) {
			this.setMainMessage("���ݻ��Ե���β���Ϊ�գ�");
			return EventRtnType.FAILD;
		}
		String a0000 = this.getPageElement("personA0000").getValue();//��ȡҳ�������
		String sql = "select * from "+tableName.toUpperCase()+" where a0000='"+a0000+"'"+( "".equals(endSql)?"":" "+endSql );//ƴ��sql
		PageQueryData pageQuery = this.pageQuery(sql,"SQL", -1, 999); 
		List list= (List) pageQuery.getData();
		if(list.size()>0){	//���ݲ�Ϊ��
			this.getPageElement(gridID).setValueList(list);//����
		}else{	//����Ϊ��
			this.getExecuteSG().addExecuteCode(InitGridJSName+"();");//ִ�г�ʼ�����ĺ���
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * <strong>���ݻ���<em>��ְ��</em></strong>
	 * @author wangs2
	 * @param gridIDAndInitGridJSNameAndEndsql	  grid����ID+��ʼ�����ĺ�����+sqlƴ�����<em>����andƴ��</em>
	 * 			<ul><li>
	 * 					<ul><em>��������淶��gridID+"@"+InitGridJSName</em><li>gridID��grid���ID<em>   ǰ�����ַ�����Ϊ���ݿ����</em></li><li>InitGridJSName����ʼ�����ĺ�����</li><li>Endsql:����sql��where����֮�� sql������ƴ��<em>   ��������ַ�����ƴ��</em></li></ul></li>
	 * 			<li>jsp��grid��ID�����淶�����ݿ����+��Grid��+����</li>
	 * 			<li>js�г�ʼ�����ĺ����������淶��InitGridJSName+��();��</li></ul>
	 * @throws RadowException
	 */
	@PageEvent("a05JspGogridquery")
	@NoRequiredValidate
	public int a05JspGogridquery(String gridIDAndInitGridJSNameAndEndsql) throws RadowException{
		String[] split = gridIDAndInitGridJSNameAndEndsql.split("@"); 
		String gridID=split[0];
		String tableName=gridID.substring(0,3);		//���ݿ����<���Զ�ת�ɴ�д>
		String InitGridJSName=split[1];	//��ʼ������js������
		String endSql=split[2];	//and ��ߵ�sqlƴ�����
		String a0000 = this.getPageElement("personA0000").getValue();//��ȡҳ�������
		String sql = "select a0500,a0501b a0501b2,a0524,a0504,a0517 from A05 where a0000='"+a0000+"' "+endSql;//ƴ��sql
		PageQueryData pageQuery = this.pageQuery(sql,"SQL", -1, 999); 
		List list= (List) pageQuery.getData();
		if(list.size()>0){	//���ݲ�Ϊ��
			this.getPageElement(gridID).setValueList(list);//����
		}else{	//����Ϊ��
			this.getExecuteSG().addExecuteCode(InitGridJSName+"();");//ִ�г�ʼ�����ĺ���
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			���ݿ��б��뱣��һ����Ч��Ϣ����ֹ�û�ɾ�����ݿ��е��Ѵ��ڵ�������Ϣ
	 * @param sql		sql��䣬���ڲ�ѯ�ܼ�¼��
	 * @param length    ��������ĳ���	
	 * @return boolean  true:����ɾ����false:��ֹɾ��<em><��ֹɾ�������Ա����ϢΪ��></em>
	 * @throws RadowException
	 */
	private boolean notDelalert(String sql, int length) throws RadowException {
		int totalCount = this.pageQuery(sql,"SQL", -1, 9999).getTotalCount();//��ȡ�ܼ�¼��
		if(totalCount == 1 || totalCount == length) {	//����ܼ�¼����һ���������ܼ�¼����Ԥɾ��������һ�£���ֹɾ��
			return false;
		}
		return true;
	}
	/**
	 * �����ַ�����ȡ8λ
	 * @param string
	 * @return string
	 */
	private String getStringTime8L(String time){
		String strTime = time == null ? "" : time;
		if(strTime.length()>8)
			strTime=strTime.substring(0,8);
		return strTime;
	}
	private String getCodeName(String code_type,String code_value){
		String sql = "select code_name from code_value where code_type='"+code_type+"' and code_value='"+code_value+"'";
		PageQueryData pageQuery = null;
		try {
			pageQuery = this.pageQuery(sql,"SQL", -1, 999);
		} catch (RadowException e) {
			return "";
		} 
		List listCode_name= (List) pageQuery.getData();
		Map map = (Map)listCode_name.get(0);
		return map.get("code_name").toString();
	}
	/*
	 * @PageEvent("a01Grid.rowclick")
	 * 
	 * @GridDataRange(GridData.allrow) public int girdclick() throws
	 * RadowException { Grid A01 = (Grid)this.getPageElement("a01Grid"); int
	 * row=A01.getCueRowIndex(); List<HashMap<String,String>> list =
	 * this.getPageElement("a01Grid").getStringValueList();
	 * HashMap<String,String> map=list.get(row); String A0000 =
	 * map.get("A0000"); String A0101 = map.get("A0101"); String A0184 =
	 * map.get("A0184"); this.getPageElement("A0000").setValue(A0000);
	 * this.getPageElement("A0101").setValue(A0101);
	 * this.getPageElement("A0184").setValue(A0184);
	 * this.getExecuteSG().addExecuteCode("personm();"); return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */
	@PageEvent("a36Grid.dogridquery")
	public int a36Data(int start, int limit) throws RadowException {
		// System.out.println(11);
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select A3600,A3601,A3604A,A3607,A3627,A3611 from A36 where A0000='"+a0000+"'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("a36Check")
	public int a36Check() throws RadowException {
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from a36 where a0000='"+a0000+"'";
		HBSession sess = HBUtil.getHBSession();
		List<A36> listA36 = sess.createSQLQuery(sql).addEntity(A36.class).list();
		List<HashMap<String,String>> list = this.getPageElement("a36Grid").getStringValueList();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		if(listA36.size()==0) {//û���˻��߻�����Ա��Ϣû���棬�����ñ������uuid����
			for(int i=0;i<5;i++) {//��ʼ��5��
				HashMap<String,Object> m = new HashMap<String, Object>();
				m.put("A3601", "");
				m.put("A3604A", "");
				m.put("A3607", "");
				m.put("A3627", "");
				m.put("A3611", "");
				String uuid = UUID.randomUUID().toString();
				m.put("A3600", uuid);
				listNew.add(m);
			}
			/*for(HashMap<String,String> m:list) {
				HashMap<String,Object> tempmap = new HashMap<String, Object>();
				for (Map.Entry<String, String> entry : m.entrySet()) {
					tempmap.put(entry.getKey(), "");
			    }
				String uuid = UUID.randomUUID().toString();
				tempmap.put("A3600", uuid);
				listNew.add(tempmap);
			}*/
			this.getPageElement("a36Grid").setValueList(listNew);
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			/*this.setNextEventName("a36Grid.dogridquery");*/
			//��ֵ��ҳ��
			for(int i=0;i<listA36.size();i++) {
				HashMap<String,Object> m = new HashMap<String, Object>();
				m.put("A3600", listA36.get(i).getA3600());
				m.put("A3601", listA36.get(i).getA3601());
				m.put("A3604A", listA36.get(i).getA3604a());
				m.put("A3607", listA36.get(i).getA3607());
				m.put("A3627", listA36.get(i).getA3627());
				m.put("A3611", listA36.get(i).getA3611());
				listNew.add(m);
			}
		}
		this.getPageElement("a36Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("a06Check")
	public int a06Check() throws RadowException {
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from a06 where a0000='"+a0000+"'";
		HBSession sess = HBUtil.getHBSession();
		List<A06> listA06 = sess.createSQLQuery(sql).addEntity(A06.class).list();
		List<HashMap<String,String>> list = this.getPageElement("a06Grid").getStringValueList();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		if(listA06.size()==0) {//û���˻��߻�����Ա��Ϣû���棬�����ñ������uuid����
			for(int i=0;i<5;i++) {//��ʼ��5��
				HashMap<String,Object> m = new HashMap<String, Object>();
				m.put("A0699", "");
				m.put("A0601", "");
				m.put("A0602", "");
				m.put("A0604", "");
				m.put("A0607", "");
				m.put("A0611", "");
				String uuid = UUID.randomUUID().toString();
				m.put("A0600", uuid);
				listNew.add(m);
			}
			this.getPageElement("a06Grid").setValueList(listNew);
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			/*this.setNextEventName("a36Grid.dogridquery");*/
			//��ֵ��ҳ��
			for(int i=0;i<listA06.size();i++) {
				HashMap<String,Object> m = new HashMap<String, Object>();
				m.put("A0600", listA06.get(i).getA0600());
				m.put("A0699", listA06.get(i).getA0699());
				m.put("A0601", listA06.get(i).getA0601());
				m.put("A0602", listA06.get(i).getA0602());
				m.put("A0604", listA06.get(i).getA0604());
				m.put("A0607", listA06.get(i).getA0607());
				m.put("A0611", listA06.get(i).getA0611());
				listNew.add(m);
			}
		}
		this.getPageElement("a06Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("a99Z1Check")
	public int a99Z1Check() throws RadowException {
		String a0000 = this.getPageElement("personA0000").getValue();
		String sql = "select * from a99Z1 where a0000='"+a0000+"'";
		HBSession sess = HBUtil.getHBSession();
		List<A99Z1> listA99Z1 = sess.createSQLQuery(sql).addEntity(A99Z1.class).list();
		List<HashMap<String,String>> list = this.getPageElement("a99Z1Grid").getStringValueList();
		List<HashMap<String,Object>> listNew = new ArrayList<HashMap<String,Object>>();
		if(listA99Z1.size()==0) {//û���˻��߻�����Ա��Ϣû���棬�����ñ������uuid����
			HashMap<String,Object> m = new HashMap<String, Object>();
			m.put("A99Z101", "");
			m.put("A99Z102", "");
			m.put("A99Z103", "");
			m.put("A99Z104", "");
			String uuid = UUID.randomUUID().toString();
			m.put("A99Z100", uuid);
			listNew.add(m);
			this.getPageElement("a99Z1Grid").setValueList(listNew);
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			/*this.setNextEventName("a36Grid.dogridquery");*/
			//��ֵ��ҳ��
			for(int i=0;i<listA99Z1.size();i++) {
				HashMap<String,Object> m = new HashMap<String, Object>();
				m.put("A99Z100", listA99Z1.get(i).getA99Z100());
				m.put("A99Z101", listA99Z1.get(i).getA99z101());
				m.put("A99Z102", listA99Z1.get(i).getA99z102());
				m.put("A99Z103", listA99Z1.get(i).getA99z103());
				m.put("A99Z104", listA99Z1.get(i).getA99z104());
				listNew.add(m);
			}
		}
		this.getPageElement("a99Z1Grid").setValueList(listNew);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*----------------------------------------ɾ������----------------------------------------*/
	//������Ϣ��ɾ��
	@PageEvent("A99Z1Delete")
	public int a99Z1Delete(String delA99Z100) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "a99Z1del",delA99Z100);
		this.addNextEvent(NextEventValue.CANNEL,"no");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("ȷ��ɾ��ѡ�е����ݣ�");
		return EventRtnType.NORMAL_SUCCESS;
		
	}

	@PageEvent("a99Z1del")
	public int a99Z1del(String delA99Z100) throws RadowException {
		this.getExecuteSG().addExecuteCode("delData('a99Z1Grid');");
		HBSession sess = HBUtil.getHBSession();
		String[] a99Z100s = delA99Z100.split("@");
		for(int i=0;i<a99Z100s.length;i++) {
			String sql = "select * from a99Z1 where a99Z100= '"+a99Z100s[i]+"'";
			List<A99Z1> listA99Z1 = sess.createSQLQuery(sql).addEntity(A99Z1.class).list();
			if(listA99Z1.size()!=0) {
				String delsql = "DELETE FROM a99Z1 WHERE A99Z100 = '"+a99Z100s[i]+"'";
				sess.createSQLQuery(delsql).executeUpdate();
			}
		}
		this.setMainMessage("ɾ���ɹ���");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ͥ��Ա��ɾ��
	@PageEvent("a36Delete")
	public int a36Delete(String delA3600) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "a36del",delA3600);
		this.addNextEvent(NextEventValue.CANNEL,"no");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("ȷ��ɾ��ѡ�е����ݣ�");
		/*HBSession sess = HBUtil.getHBSession();
		String[] a3600s = delA3600.split("@");
		for(int i=0;i<a3600s.length;i++) {
			String sql = "select * from a36 where a3600= '"+a3600s[i]+"'";
			List<A36> listA36 = sess.createSQLQuery(sql).addEntity(A36.class).list();
			if(listA36.size()==0) {
				A36 a36 = new A36();
				a36.setA3600(a3600s[i]);
				sess.delete(a36);
			}
		}*/
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("a36del")
	public int a36del(String delA3600) throws RadowException {
		this.getExecuteSG().addExecuteCode("delData('a36Grid');");
		HBSession sess = HBUtil.getHBSession();
		String[] a3600s = delA3600.split("@");
		for(int i=0;i<a3600s.length;i++) {
			String sql = "select * from a36 where a3600= '"+a3600s[i]+"'";
			List<A36> listA36 = sess.createSQLQuery(sql).addEntity(A36.class).list();
			if(listA36.size()!=0) {
				String delsql = "DELETE FROM a36 WHERE A3600 = '"+a3600s[i]+"'";
				sess.createSQLQuery(delsql).executeUpdate();
			}
		}
		this.setMainMessage("ɾ���ɹ���");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//רҵ����ְ���ɾ��
	@PageEvent("a06Delete")
	public int a06Delete(String delA0600) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "a06del",delA0600);
		this.addNextEvent(NextEventValue.CANNEL,"no");
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("ȷ��ɾ��ѡ�е����ݣ�");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("a06del")
	public int a06del(String delA0600) throws RadowException {
		this.getExecuteSG().addExecuteCode("delData('a06Grid');");
		HBSession sess = HBUtil.getHBSession();
		String[] a0600s = delA0600.split("@");
		for(int i=0;i<a0600s.length;i++) {
			String sql = "select * from a06 where a0600= '"+a0600s[i]+"'";
			List<A06> list036 = sess.createSQLQuery(sql).addEntity(A06.class).list();
			if(list036.size()!=0) {
				String delsql = "DELETE FROM a06 WHERE A0600 = '"+a0600s[i]+"'";
				sess.createSQLQuery(delsql).executeUpdate();
			}
		}
		this.setMainMessage("ɾ���ɹ���");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private StringBuffer getComboArray(Map<String, String> map){
		StringBuffer dataarrayjs = new StringBuffer("[");
		Set<String> tablecodes = map.keySet();
		//[ 'exists', '����' ]
		for(String tablecode : tablecodes){
			dataarrayjs.append("['"+tablecode+"','"+map.get(tablecode)+"'],");
		}
		if(map.size()>0){
			dataarrayjs.deleteCharAt(dataarrayjs.length()-1);
		}
		dataarrayjs.append("]");
		return dataarrayjs;
	}
	//�������
	@PageEvent("a01OrderSave")
	public int a01OrderSave(String data) throws RadowException {
		/*data = data.substring(0, data.length()-1);
		String[] datas = data.split(",");
		Queue<String> queue = new LinkedList<String>();
		for(int i=0;i<datas.length;i++) {
			queue.offer(datas[i]);
		}
		HttpSession sess =  request.getSession();
		//�������
		sess.setAttribute("order",queue);*/
		data = data.substring(0, data.length()-1);
		String[] datas = data.split(",");
		//List<String> list = (List<String>) request.getSession().getAttribute("order");
		List<String> list = new ArrayList<String>();
		for(int i=1;i<datas.length;i++) {
			list.add(datas[i]);
		}
		
		request.getSession().setAttribute("order", list);
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @author wangs2
	 * 			 ��Map��ʽ���ػ�������͵�λ�ı�ʶ
	 * @param	getStaticB01Map
	 * @return
	 */
	public void getStaticB01Map(){
		B01ALL = new HashMap<String,String>();
		List<B01> list = HBUtil.getHBSession().createQuery("from B01 order by b0111").list();
		if(list!=null && list.size()>0){
			for(B01 b01 : list){
				B01ALL.put(b01.getB0101(), b01.getB0194());
			}
		}
	}
	public static void main(String[] args) {
	}
}
