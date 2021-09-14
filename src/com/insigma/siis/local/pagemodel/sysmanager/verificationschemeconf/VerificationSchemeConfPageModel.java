package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.VVerifyColVsl006;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifySchemeDTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class VerificationSchemeConfPageModel extends PageModel {
	
	final static boolean TEMP_FLAG = true;					//�����Ƿ�ƴ�ӡ�TEMP_PREFIX��
	public final static String TEMP_PREFIX = "_temp";		//��ʱ���׺
	final static String SPACE = " ";						//�ո�
	final static String SINGLE_QUOTE = "'";					//������
	static String LEN_COMPARE_PREFIX ="nvl(length(";		//���ȱȽ�ǰ׺
	final static String LEN_COMPARE_POSTFIX ="),0)";		//���ȱȽϺ�׺
	final static String AND = " AND ";						//and ����
	public final static String EQUAL = " = ";						//= ����
	final static String FROM = " from ";					//from ����
	public final static String COUNT_SQL = " count(1) "; 			//��������ʾ�Ƿ���ڷ�����������Ϣ
	public final static String A01_PARAM = ":A0000"; 				//A01����
	public final static String B01_PARAM = ":B0111"; 				//B01����
	
	public final static String a01TabName = TEMP_FLAG?"A01"+TEMP_PREFIX:"A01";	//A01��ʱ��
	public final static String a02TabName = TEMP_FLAG?"A02"+TEMP_PREFIX:"A02";	//A02��ʱ��
	public final static String b01TabName = TEMP_FLAG?"B01"+TEMP_PREFIX:"B01";	//B01��ʱ��
	public final static List<String> REVERSE_OPERATION = new ArrayList<String>(){{add("B0127"); add("A0221");add("A0801B");add("B0124");add("A1428");add("A0120");add("A0122");add("A6304");}};
	
	
	static{
		if(DBUtil.getDBType() == DBType.MYSQL){
			LEN_COMPARE_PREFIX ="ifnull(length(";
		}
	}
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public VerificationSchemeConfPageModel() {
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
			}else{
//				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.AAA146,a.AAA005 FROM AA01 a,AA26 b WHERE a.AAA001='AREA_ID' and a.AAA005=b.AAB301 ").uniqueResult();
				Object[] area = null;
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", null == areaname ? "" : areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}
			}
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
			@SuppressWarnings("unchecked")
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			if(groups.isEmpty() || PrivilegeManager.getInstance().getCueLoginUser().getLoginname().equals("kf00")){
				areaInfo.put("manager", "true");
			}else{
				areaInfo.put("manager", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���������������
	 * @author mengl
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("expScheme")
	@NoRequiredValidate
	public int expSchemeOnclick(String vsc001s)throws RadowException{
		if(StringUtil.isEmpty(vsc001s)){
			this.setMainMessage("��ã�δѡ�񷽰����ݣ���ѡ��");
			return EventRtnType.FAILD;
		}
		String[] vsc001=vsc001s.split(",");
		PageElement pe = this.getPageElement("VeriySchemeGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		//int count = 0;
		//StringBuffer ids = new StringBuffer();
		String fileName = "";
		if(vsc001.length==1){
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(vsc001[0].equals((String)map.get("vsc001"))){
					fileName=(String) map.get("vsc002");
				}
			}
		}
		/*for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("mcheck");
			if(logchecked!=null && logchecked.equals(true)){
				count++;
				ids.append((String) map.get("vsc001")).append(",");
				fileName =(String) map.get("vsc002");
			}
		}*/
		/*if(count < 1){
			this.setMainMessage("��ã�δѡ�񷽰����ݣ���ѡ��");
			this.setMessageType(EventMessageType.ALERT);
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		//ids.deleteCharAt(ids.lastIndexOf(","));
		vsc001s=vsc001s.substring(0,vsc001s.length()-1);
		if(vsc001.length > 1){
			this.getExecuteSG().addExecuteCode("expScheme('"+vsc001s+"','')");// ���ļ����������ļ���������ʹ�ö��ļ�ѹ����һ���ļ�����
		}else{
			this.getExecuteSG().addExecuteCode("expScheme('"+vsc001s+"','"+fileName+"')");//�����ļ�
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �����ͣ�á���ť��ͣ��ѡ�񷽰�
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("stop.onclick")
	@NoRequiredValidate
	@Transaction
	public int stopOnclick(String index) throws RadowException, AppException {
		PageElement pe = this.getPageElement("VeriySchemeGrid");
		/*int count = 0 ;  //��ѡ����
		List<String> vsc001s = new ArrayList<String>();*/
		int row=Integer.parseInt(index);
		List<HashMap<String, Object>> list = pe.getValueList();
		/*int  index=pe.getCueRowIndex();*/
		HashMap<String,Object> map=list.get(row);
		/*for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("mcheck");
			if(logchecked!=null && logchecked.equals(true)){
				vsc001s.add((String) map.get("vsc001"));
				count++;
			}
		}*/
	/*	if(count < 1){
			this.setMainMessage("��ã�δѡ�񷽰����ݣ���ѡ��");
			return EventRtnType.FAILD;
		}*/
		String vsc001=map.get("vsc001").toString();
		String vsc002=map.get("vsc002").toString();
		String vsc007=map.get("vsc007").toString();
		try {
			RuleSqlListBS.stopSchemeByVsc001(vsc001);
		} catch (AppException e) {
			e.printStackTrace();
			throw new AppException("ͣ��У�鷽��ʧ�ܣ�"+e.getMessage());
		}
		pe.reload();
		this.getPageElement("dbClickVsc001").setValue(vsc001);
		this.getPageElement("isdefault").setValue(vsc007);
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		/*String flag=this.getPageElement("flag").getValue();
		if(flag!=null&&"true".equals(flag)){
			//flagΪtrueʱ������
			this.getPageElement("flag").setValue("");
		}else{
			this.setMainMessage("ͣ�óɹ���");
		}*/
		this.setMainMessage("ͣ�óɹ���");
		this.getExecuteSG().addExecuteCode("changeh1('"+vsc002+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��������á���ť��У�������Ϣ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("run.onclick")
	@NoRequiredValidate
	public int runOnclick(String index) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
			
		//1.��ѡ��һ������У��
		int row=Integer.parseInt(index);
		PageElement pe = this.getPageElement("VeriySchemeGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		/*int index=pe.getCueRowIndex();*/
		HashMap<String,Object> map=list.get(row);
		/*int count = 0;*/
		String vsc001Str = map.get("vsc001").toString();
		/*for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("mcheck");
			if(logchecked!=null && logchecked.equals(true)){
				count++;
				vsc001Str =(String) map.get("vsc001");
			}
		}
		if(count > 1){
			this.setMainMessage("��ã�����ֻ����ѡ��һ���������ݣ�");
			return EventRtnType.FAILD;
		} else if(count < 1){
			this.setMainMessage("��ã�δѡ�񷽰����ݣ���ѡ��");
			return EventRtnType.FAILD;
		}*/
		VerifyScheme vs = null;
		String vsc003 = "";
		try {
			vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001Str);
			vsc003 = vs.getVsc003();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			this.setMainMessage("���������쳣���쳣��Ϣ����ȡ���������쳣-"+vsc001Str);
			return EventRtnType.FAILD;
		}
		
		//2 У���Ƿ��Ѿ�����
		
		if(!StringUtil.isEmpty(vsc003) && "1".equals(vsc003)){
			this.setMainMessage("�÷����Ѿ����ã�");
			return EventRtnType.FAILD;
		}
		
		
		//3 У���Ƿ���verify_sql_list����
		
		String sqlCount = " from VerifySqlList a  where a.vru001 IN (:vru001s) order by vsl014";
		String sqlParams = "SELECT vru001 FROM verify_rule WHERE vsc001 = :vsc001";
		
		@SuppressWarnings("unchecked")
		List<String> sqlParamsList = sess.createSQLQuery(sqlParams).setString("vsc001", vsc001Str).list();
		
		List<String> vru001s = new ArrayList<String>();
		if(sqlParamsList==null || sqlParamsList.isEmpty() || sqlParamsList.get(0)==null ){
			System.out.println("û��vsl����");
			this.setMainMessage("���������쳣���쳣��Ϣ���÷���û�����У�������Ϣ��");
			return EventRtnType.FAILD;
		}else{
			for(String vsc001 : sqlParamsList){
				if(!StringUtil.isEmpty(vsc001)){
					vru001s.add(vsc001);
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<VerifySqlList> verifySqlList = sess.createQuery(sqlCount).setParameterList("vru001s", vru001s).list();
		if(verifySqlList==null || verifySqlList.isEmpty() || verifySqlList.get(0)==null ){
			/** �ൺ��Ŀ�޸� **/
			String vru001strs="";
			for (String vru001str : vru001s) {
				vru001strs=vru001strs+"'"+vru001str+"',";
			}
			vru001strs = vru001strs.substring(0, vru001strs.length()-1);
			System.out.println("vru001strs11111111111111"+vru001strs);
			String numforvru010 = "";
			try {
				numforvru010 = HBUtil.getValueFromTab("count(1)", "verify_rule", " vru001 IN ("+vru001strs+") and vru010 is null and vsc001='"+vsc001Str+"' ");
				System.out.println("SQL@@@@@@@"+numforvru010);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if("0".equals(numforvru010)){
			/** �ൺ��Ŀ�޸� **/
				System.out.println("�����ൺ��Ŀ�޸��쳣");
				this.setMainMessage("���������쳣���쳣��Ϣ���÷���û�����У����䣡");
				return EventRtnType.FAILD;
			}
		}
		
		VerifySchemeDTO vsDTO = new VerifySchemeDTO();	//��¼��־ʱ��¼��ʼУ�鷽��״̬
		try {
			vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001Str);
			
			BeanUtil.copy(vs, vsDTO);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("���������쳣���쳣��Ϣ����ȡ���������쳣-"+vsc001Str);
			return EventRtnType.FAILD;
		}
		
		vs.setVsc003("1");
		sess.saveOrUpdate(vs);
		sess.flush();
		try {
			if(vs!=null){
				new LogUtil().createLog("635", "VERIFY_SCHEME", vsc001Str, vs.getVsc002(), null,Map2Temp.getLogInfo(vsDTO,vs) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.getExecuteSG().addExecuteCode("radow.doEvent('runEvent','"+vsc001Str+"')");
		this.getExecuteSG().addExecuteCode("radow.doEvent('verifyRuleGrideload','"+vs.getVsc001()+","+vs.getVsc007()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ʵ�ʵ�ƴ��SQL���
	 *   1. Ԥ����
	 *   	1.1 ����ʹ�ñ���
	 *   2. ƴ��SQL���
	 * 	  	2.0 Select����
	 * 	  	2.1 FROM���֣�
	 * 				�����б��ظ���
	 * 				�ڶ��ʱ�����һ����B01��Ϊ����
	 * 				�۵���B01��ʱ����B01Ϊ���������������������
	 * 				�ܶ���д���B01��ʱ���ж��Ƿ���A02����ʹ��Ա��A01���뵥λ��B01��������û�������A02��
	 * 	  	2.2 Where�����Ӳ��֣�
	 *				����From�����漰�ı�ʹ�����������������
	 * 	  	2.3 ���Where�����֤�������֣�
	 * 				����������Ĳ�ͬ���ͣ�У����ȶ����ʵ�����ͺ��������͵Ĳ�ͬ����������֤��䡣
	 *   3.У��SQL��Ч�ԣ����趨У����� �Ƿ���Ч�����ã�
	 *   4.�趨У�鷽�� �Ƿ���Ч�����ã�
	 *   5.ˢ�·����б� 
	 * @return
	 * @author mengl
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("runEvent")
	@NoRequiredValidate
	@Transaction
	public int runEvent(String vsc001)throws RadowException{
		
		//1. Ԥ����
		
			//1.1 ����ʹ�ó���
		
		HBSession sess = HBUtil.getHBSession();
		
		VerifySchemeDTO vsDTO = new VerifySchemeDTO();	//��¼��־ʱ��¼��ʼУ�鷽��״̬
		
		int errorCount = 0;								//�������
		String runResult = null;						//���÷������
		StringBuffer errorInfos = new StringBuffer("");	//У��SQLִ������Ĵ�����Ϣ��
		//String vsc001Str = null ;						//У�鷽������
		VerifyScheme vs = null;							//У�鷽��
		List<VerifySqlList> verifySqlList = null;		//У�������У��SQL�б�(VerifySqlList)
		StringBuffer sqlBf = null;						//sql���
		TreeSet<String> fromTab = null;					//ÿ��У�����(VerifyRule)�漰�ı�,��ȡ��У�������У��SQL�б�(VerifySqlList)�������輰��У���ͱȶԱ�
		String sqlCount = "from VerifySqlList a  where a.vru001 IN (:vru001s) order by vsl014";
		String selectSql = "";							//split sql in two parts,first part (select ... where 1=1)
		String secSql = "";								//the second part (and column_name = 'value')
		boolean flag = false;							//flag = true use union all ,else flag = false 
		boolean bracket = false;						//judge left bracket
		
		PageElement pe = this.getPageElement("VeriySchemeGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		/*for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("mcheck");
			if(logchecked!=null && logchecked.equals(true)){
				vsc001Str =(String) map.get("vsc001");
				break;
			}
		}*/
		try {
			vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
			
			BeanUtil.copy(vs, vsDTO);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("���������쳣���쳣��Ϣ����ȡ���������쳣-"+vsc001);
			return EventRtnType.FAILD;
		}
		
			//1.2  �������Ĺ��ܣ����õ����Ƿ�Ĭ�ϻ���У�鷽��������Ч������Ĭ�ϻ���У�鷽����Ҫ�����һ����Ч��У�鷽��
		
		/*if(vs!=null && !"1".equals(vs.getVsc007())){
			String sqlVS = "UPDATE verify_scheme a SET a.vsc003 = '0' WHERE a.vsc007<>'1' and  a.vsc001 <>   '"+vsc001Str+"'";
			String sqlVR = "UPDATE Verify_Rule b SET b.vru007 = '0' WHERE not exists (select 1 from verify_scheme a where a.vsc001 = b.vsc001 and a.vsc007='1') and   vsc001 <>  '"+vsc001Str+"'";
			try {
				conn.prepareStatement(sqlVS).executeUpdate();
				conn.prepareStatement(sqlVR).executeUpdate();
				sess.flush();
			} catch (SQLException e) {
				e.printStackTrace();
				this.setMainMessage("�����÷���������У�鷽����Ϊ��Чʧ�ܣ��쳣��Ϣ�����÷���ID-"+vsc001Str+";"+e.getMessage());
				return EventRtnType.FAILD;
			}
		}*/
		
		List<VerifyRule> verifyRuleList = sess.createQuery("from VerifyRule  WHERE vsc001 = :vsc001").setString("vsc001", vsc001).list();
		
		//2. ƴ��SQL���
		
		for(VerifyRule vr : verifyRuleList){
			
			fromTab = new TreeSet<String>();//�漰��
			verifySqlList = sess.createQuery(sqlCount).setParameter("vru001s", vr.getVru001()).list();
			String vsl003 = null;		// У���
			String vsl008 = null;		// �ȶԱ�
			String tabPrimary = null;	// ����
			boolean vsl006Flag8 = false;// ������������Ƿ����8�������������֤��Ϣ���Ƿ���ڣ�
			
			//2.0 Select����
			
			sqlBf =  new StringBuffer("select "+COUNT_SQL+FROM);
			
			//2.1 ��ӱ�(�������⣬�����ظ�):FROM����
			
			for(VerifySqlList vsl : verifySqlList){
				 vsl003 =TEMP_FLAG?vsl.getVsl003()+TEMP_PREFIX:vsl.getVsl003();// У���
				 vsl008 =(TEMP_FLAG&&!StringUtil.isEmpty(vsl.getVsl008()))?vsl.getVsl008()+TEMP_PREFIX:vsl.getVsl008();// �ȶԱ�
				if(StringUtil.isEmpty(vsl003)){
					this.setMainMessage("У������б���У�����Ϣȱʧ���쳣��Ϣ��У������б�ID-"+vsl.getVsl001());
					return EventRtnType.FAILD;
				}
				
				//2.1.1 ���У���(ע��  8�������������֤��Ϣ���Ƿ���ڣ���У�����ӵ�From�����)
				
				if(!StringUtil.isEmpty(vsl003) &&  !fromTab.contains(vsl003)  && !"80".equals(vsl.getVsl006()) &&  !"81".equals(vsl.getVsl006()) ){
					fromTab.add(vsl003);
					sqlBf.append(SPACE)
						.append(",")
						.append(SPACE)
						.append(vsl003);
				}
				
				//2.1.2 ��ӱȶԱ�
				
				if(!StringUtil.isEmpty(vsl008) && !fromTab.contains(vsl008)){
					fromTab.add(vsl008);
					sqlBf.append(SPACE)
						.append(",")
						.append(SPACE)
						.append(vsl008);
				}
				
				//�������ж�������������Ƿ����8�������������֤��Ϣ���Ƿ���ڣ�
				if("80".equals(vsl.getVsl006()) ||  "81".equals(vsl.getVsl006())){
					vsl006Flag8 = true;
				}
				
			}
			
				
			if(fromTab.size()==1 && fromTab.contains(b01TabName)){
				
			}else{
					//2.1.3 �����Ա������Ϣ��
				
				/*if(!fromTab.contains(a01TabName)){
					fromTab.add(a01TabName);
					sqlBf.append(SPACE)
					.append(",")
					.append(SPACE)
					.append(a01TabName);
				};*/
					//2.1.4 ������B01�����A02��
				
				if(fromTab.contains(b01TabName) && !fromTab.contains(a02TabName)){
					sqlBf.append(SPACE)
					.append(",")
					.append(SPACE)
					.append(a02TabName);
				};
				
					//��������ѯ��Ϊ�� �� У������а���8�������������֤��Ϣ���Ƿ���ڣ�������� a01 �� ��Ϊ����
				if(fromTab.size()<1 && vsl006Flag8){
					fromTab.add(a01TabName);
					sqlBf.append(SPACE)
					.append(",")
					.append(SPACE)
					.append(a01TabName);
				}
				
				
			}
			sqlBf = new StringBuffer(sqlBf.toString().replaceFirst(",", ""));
			//2.2 ���Where�������Ӳ���
			
			sqlBf.append(" WHERE 1=1  AND ");
			if(fromTab==null || fromTab.size() < 1 ){
				this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧУ����Ϣ����У��������ID-"+vr.getVru001());
				return EventRtnType.FAILD;
			}
				//2.2.1 �趨����
			
			 if(fromTab.contains(b01TabName)&&fromTab.size()==1){
				sqlBf.append(b01TabName)
				.append(".")
				.append("B0111")
				.append(EQUAL)
				.append(B01_PARAM)
				.append(AND);
				
				tabPrimary = b01TabName;
			}else{
				for(String tabName :fromTab){
					if(!b01TabName.equalsIgnoreCase(tabName)){
						sqlBf.append(tabName)
						.append(".")
						.append("A0000")
						.append(EQUAL)
						.append(A01_PARAM)
						.append(AND);
						
						tabPrimary = tabName;
						break;
					}
				}
				
			}
			
			 vr.setVru006(tabPrimary);//ƴ����ɵ�SQL���������
			
				//2.2.2 ���������������
			
			for(String tabName : fromTab){
				if(tabName.equalsIgnoreCase(tabPrimary)){
					continue;
				}
				//�ǻ�����
				if(!tabName.equalsIgnoreCase(b01TabName)){
					sqlBf.append(SPACE) 
					.append(tabPrimary)
					.append(".")
					.append("A0000")
					.append(EQUAL)
					.append(tabName)
					.append(".")
					.append("A0000")
					.append(AND);
				}else{//B01��������Ҫ��A02������Ա����
					
					//��B01����A02����
					sqlBf.append(SPACE) 
					.append(a02TabName)
					.append(".")
					.append("a0201b")
					.append(EQUAL)
					.append(tabName)
					.append(".")
					.append("B0111")
					.append(AND);
					
					//����û��A02��A02���������
					if(!fromTab.contains(a02TabName)){
						sqlBf.append(SPACE) 
						.append(a01TabName)
						.append(".")
						.append("A0000")
						.append(EQUAL)
						.append(a02TabName)
						.append(".")
						.append("A0000")
						.append(AND);
					}
				}
			}
			selectSql = sqlBf.toString();		//store the first part
			//2.3 ���Where�����֤��������
			
			if(DBUtil.getDBType()==DBType.ORACLE){
			sqlBf.append(" ( ");//����֤�����ŵ�������
			}
			CodeValue vsl006CodeValue = null;			//�����CodeValue����
			String vsl006 = null;						//�����ֵ
			//��������ͣ�subCodeValue����1-��С�Ƚϣ�2-����ѡ��3-�Ƿ�Ϊ��;4-���ȱȽϣ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�7-������ʽƥ�������8-�������������֤��Ϣ���Ƿ���ڣ���9-������֤
			String subCodeValue = null;					//���������
			String vsl007 = null;						//�̶�ֵ
			String vsl013 = null;						//ѡ��ֵ
			String vsl009 = null;						//���������� �ֶ�
			String codeName2 = null;					//�������sql�У�
			String codeName3 = "";						//�������׺��sql�У�
			VVerifyColVsl006 vverifyColVsl006Vsl003 = null;//У����ֶ�������Ϣ
			String vsl005 = "";							//У����ֶ�ʵ����������
			String vsl005Should = "";					//У����ֶ������������ͣ�Ӧ�õ��������ͣ�
			String vsl005Should_qd = "";				//У����ֶ�Ϊ�ൺ�趨���ͣ�DΪdata��
			String vsl0052 = "";						//�ȶ���Ϣ��ʵ����������
			
			for(VerifySqlList vsl : verifySqlList){
				
				 vsl003 =TEMP_FLAG?vsl.getVsl003()+TEMP_PREFIX:vsl.getVsl003();// У���
				 vsl008 =(TEMP_FLAG&&!StringUtil.isEmpty(vsl.getVsl008()))?vsl.getVsl008()+TEMP_PREFIX:vsl.getVsl008();// �ȶԱ�
				 vsl006 = vsl.getVsl006();
				if(!StringUtil.isEmpty(vsl006)){
					vsl006CodeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
				}else{
					this.setMainMessage("���������쳣���쳣��Ϣ��У������в��������Ϊ�գ�У�����ID-"+vsl.getVsl001());
					return EventRtnType.FAILD;
				}
				
				//��������ͣ�subCodeValue����1-��С�Ƚϣ�2-����ѡ��3-�Ƿ�Ϊ��;4-���ȱȽϣ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�7-������ʽƥ�������8-�������������֤��Ϣ���Ƿ���ڣ���9-������֤
				 subCodeValue = vsl006CodeValue.getSubCodeValue();
				 vsl007 = vsl.getVsl007();//�̶�ֵ
				 vsl013 = vsl.getVsl013();//ѡ��ֵ
				 vsl009 = vsl.getVsl009();//���������� �ֶ�
				 codeName2 = vsl006CodeValue.getCodeName2()==null?"":vsl006CodeValue.getCodeName2();//�������sql�У�
				 codeName3 = vsl006CodeValue.getCodeName3()==null?"":vsl006CodeValue.getCodeName3();//�������׺
				 vverifyColVsl006Vsl003 = RuleSqlListBS.getVverifyColVsl006(vsl.getVsl003(),vsl.getVsl004());
//				 System.out.println(vsl.getVsl003()+"|"+vsl.getVsl004());
				//��������Ϊ�ջ�����varchar2,number,date���׳��쳣
				 if(vverifyColVsl006Vsl003==null){
					 this.setMainMessage("���������쳣���쳣��Ϣ����"+RuleSqlListBS.getTableName(vsl.getVsl003())+"����"+RuleSqlListBS.getColName(vsl.getVsl003(), vsl.getVsl004())+"����ȡ������ϢΪ�գ�");
					 return EventRtnType.FAILD;
				 }
				 /* �ൺ��Ŀ��Ҫ  -- ��ȡ������������ D - Date*/
//				 vsl005Should_qd = vverifyColVsl006Vsl003.getColDataTypeShould_qd();
				 /* �ൺ��Ŀ��Ҫ  */
				 vsl005 = vverifyColVsl006Vsl003.getColDataType();
				 vsl005Should = vverifyColVsl006Vsl003.getColDataTypeShould();
				 vsl0052 = !StringUtil.isEmpty(vsl.getVsl009()) ? RuleSqlListBS.getVverifyColVsl006(vsl.getVsl008(),vsl.getVsl009()).getColDataType():"";
				if(StringUtil.isEmpty(vsl005)){
					this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧУ����������������ͣ�У�����ID-"+vsl.getVsl001());
					return EventRtnType.FAILD;
				}/*else if(!vsl005.equalsIgnoreCase("varchar2") && !vsl005.equalsIgnoreCase("number") && !vsl005.equalsIgnoreCase("date")){
					this.setMainMessage("���������쳣���쳣��Ϣ��У�������У������������������쳣-"+vsl005+"��У�����ID-"+vsl.getVsl001());
					return EventRtnType.FAILD;
				}*/
				
				if(vsl.getVru001().equals(vr.getVru001())){
					
					sqlBf.append(SPACE);
					
					//2.3.1. ƴ�������� 
				
					for(Long i=0L;null != vsl.getVsl002() && i<vsl.getVsl002();i++) {
						sqlBf.append("(");
/*						if(DBUtil.getDBType()==DBType.MYSQL){
							sqlBf.deleteCharAt(sqlBf.length()-1);  //case when mysql ,delete left bracket set bracket = true
							//��¼��secSql��ֵ
							secSql = sqlBf.substring(sqlBf.lastIndexOf("1=1")+8,sqlBf.length());
						}*/
						bracket = true;
					}
					if("is null".equals(codeName2) && DBUtil.getDBType()==DBType.MYSQL){
						sqlBf.append(" ( ");
					}
					//2.3.2. ƴ��У���ֶ�
					
					//����������ٳ��ȱȽϵĻ�Ҫƴ�� ���ȱȽ�ǰ׺ LEN_COMPARE_PREFIX ��׺; �������������������͵�תΪ���ڸ�ʽ; ��9-����У�顢8-�������������֤��Ϣ���Ƿ���ڣ���7-�������  ��ƴ��У����
					if("9".equalsIgnoreCase(subCodeValue) || "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
						
					}else if(!"4".equalsIgnoreCase(subCodeValue)){
						//ֻ����������ΪDate,ʵ�����Ͳ���Date���ͣ��� Ӧ������رȽ�����ʱ��תΪDate���ͣ�1-��С�Ƚϡ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϡ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�
						if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) && !vsl005.equalsIgnoreCase(vsl005Should) 
								&& ("1".equals(subCodeValue) || "5".equals(subCodeValue) || "6".equals(subCodeValue))){
							sqlBf.append(SPACE)
							.append(RuleSqlListBS.sqlStrToDate(vsl003, vsl.getVsl004()))
							.append(SPACE);
						}else{
							sqlBf.append(SPACE)
							.append(vsl003)
							.append(".")
							.append(vsl.getVsl004())
							.append(SPACE);
						}
						
					}else{
						sqlBf.append(SPACE)
						.append(LEN_COMPARE_PREFIX)
						.append(vsl003)
						.append(".")
						.append(vsl.getVsl004())
						.append(LEN_COMPARE_POSTFIX)
						.append(SPACE);
					}
					
					
					//2.3.3 ƴ�Ӳ��������
					if("1".equals(subCodeValue) && REVERSE_OPERATION.contains(vsl.getVsl004())){
						if(">=".equals(codeName2)){
							codeName2 = "<=";
						}else if(">".equals(codeName2)){
							codeName2 = "<";
						}else if("<=".equals(codeName2)){
							codeName2 = ">=";
						}else if("<".equals(codeName2)){
							codeName2 = ">";
						}
					}
					sqlBf.append(codeName2);
					if("is null".equals(codeName2) && DBUtil.getDBType()==DBType.MYSQL){
						sqlBf.append(" or ");
						// create index
						try {
//							sess.createSQLQuery("ALTER TABLE "+vsl.getVsl003().toString()+" add index idx_"+vsl.getVsl004().toString()+" ("+vsl.getVsl004().toString()+")").executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//����������ٳ��ȱȽϵĻ�Ҫƴ�� ���ȱȽ�ǰ׺ LEN_COMPARE_PREFIX ��׺; �������������������͵�תΪ���ڸ�ʽ; ��9-����У�顢8-�������������֤��Ϣ���Ƿ���ڣ���7-�������  ��ƴ��У����
						if("9".equalsIgnoreCase(subCodeValue) || "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
							
						}else if(!"4".equalsIgnoreCase(subCodeValue)){
							//ֻ����������ΪDate,ʵ�����Ͳ���Date���ͣ��� Ӧ������رȽ�����ʱ��תΪDate���ͣ�1-��С�Ƚϡ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϡ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�
							if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) && !vsl005.equalsIgnoreCase(vsl005Should) 
									&& ("1".equals(subCodeValue) || "5".equals(subCodeValue) || "6".equals(subCodeValue))){
								sqlBf.append(SPACE)
								.append(RuleSqlListBS.sqlStrToDate(vsl003, vsl.getVsl004()))
								.append(SPACE);
							}else{
								sqlBf.append(SPACE)
								.append(vsl003)
								.append(".")
								.append(vsl.getVsl004())
								.append(SPACE);
							}
							
						}else{
							sqlBf.append(SPACE)
							.append(LEN_COMPARE_PREFIX)
							.append(vsl003)
							.append(".")
							.append(vsl.getVsl004())
							.append(LEN_COMPARE_POSTFIX)
							.append(SPACE);
						}
						
						
						
						sqlBf.append(" ='') ");
					}
					//2.3.4 ƴ�ӱȶ�ֵ�����ж������������͡�ʵ���������ͽ�������ת��
					
					//1-��С�Ƚϣ�2-����ѡ��
					if("1".equals(subCodeValue) || "2".equals(subCodeValue)){
						
						//  �ȶԹ̶�ֵ
						if(!StringUtil.isEmpty(vsl007)){
							if(vsl005.equalsIgnoreCase("varchar2") || vsl005.equalsIgnoreCase("number")){
								if(vsl007.trim().matches("\\d+") && vsl005Should.equalsIgnoreCase("date")){
									sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl007.trim()));
								}else if(vsl005.equalsIgnoreCase("varchar2") && "1".equals(subCodeValue)){
									sqlBf.append(SINGLE_QUOTE)
										.append(vsl007.trim())
										.append(SINGLE_QUOTE);
								}else{
									sqlBf.append(vsl007.trim());
								}
								
							}else if(vsl005.equalsIgnoreCase("date")){
								if(vsl007.trim().matches("\\d+")){
									sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl007.trim()));
								}else{
									this.setMainMessage("���������쳣���쳣��Ϣ�����������͵�У����Ϣ��Ƚϣ��̶�ֵӦΪ6λ��8λ�����֣�У�����ID-"+vsl.getVsl001());
									return EventRtnType.FAILD;
								}
							}
							
						// �ȶ�ѡ��ֵ
						}else if(!StringUtil.isEmpty(vsl013)){
							if( vsl005.equalsIgnoreCase("number")){
								sqlBf.append(vsl013);
							}else if(vsl005.equalsIgnoreCase("varchar2") && "1".equals(subCodeValue)){
								sqlBf.append(SINGLE_QUOTE)
									.append(vsl013)
									.append(SINGLE_QUOTE);
							}else if(vsl005.equalsIgnoreCase("date")){
								this.setMainMessage("���������쳣���쳣��Ϣ���ȶ�ѡ��ֵ���������͵�У����Ϣ��ܱȽϣ�У�����ID-"+vsl.getVsl001());
								return EventRtnType.FAILD;
							}
					
						// �������ֶ�ֵ
						}else if(!StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008) ){
							if("1".equals(subCodeValue)){
								//
								if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  !"date".equalsIgnoreCase(vsl0052)  ){
									sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl008, vsl009));
								}else{
									sqlBf.append(vsl008)
										.append(".")
										.append(vsl009);
								}
							}else if("2".equals(subCodeValue)){
								this.setMainMessage("���������쳣���쳣��Ϣ���ַ������Ƚ���������������������ȶ���Ϣ��Ƚϣ�У�����ID-"+vsl.getVsl001());
								return EventRtnType.FAILD;
							}
							
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧ�ȶ�ֵ��У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
						//3-���ӿձȽϷ���������
					}else if("3".equals(subCodeValue)){
						
						//4-���ȱȽϣ�
					}else if("4".equals(subCodeValue)){
						//�̶��ȶ�ֵ
						if(!StringUtil.isEmpty(vsl007)){
							if(vsl005.equalsIgnoreCase("varchar2") || vsl005.equalsIgnoreCase("number")){
								sqlBf.append(vsl007);
							}else {
								this.setMainMessage("���������쳣���쳣��Ϣ���������Ͳ��ܱȽϳ��ȣ�У�����ID-"+vsl.getVsl001());
								return EventRtnType.FAILD;
							}
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ�����ȱȽ�ֻ����̶��ȶ�ֵ�Ƚϣ�У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
						//5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ���������������Ϊnumber �� date ��
					}else if("5".equals(subCodeValue)){
						//  �ȶԹ̶�ֵ
						if(!StringUtil.isEmpty(vsl007) && vsl007.trim().matches("-?\\d+") && !StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008)){
							
							if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  !"date".equalsIgnoreCase(vsl0052) ){
								sqlBf .append(RuleSqlListBS.sqlStrToDateAddMonths(vsl008,vsl009,Long.valueOf(vsl007.trim())));
							}else if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  "date".equalsIgnoreCase(vsl0052)  ){
								
								
								if(DBUtil.getDBType() == DBType.MYSQL){
									sqlBf.append("date_add("+vsl008+"."+vsl009+", INTERVAL "+vsl007.trim()+" MONTH) ");
								}else{
									sqlBf.append("Add_Months("+vsl008+"."+vsl009+", "+vsl007.trim()+")");
								}
								
							}else if("number".equalsIgnoreCase(vsl005Should)||"n".equalsIgnoreCase(vsl005Should_qd)){
								if(DBUtil.getDBType() == DBType.MYSQL){
									sqlBf.append("( CONVERT("+vsl008+"."+vsl009+",DECIMAL) + "+vsl007.trim()+")");
								}else{
									sqlBf.append("( to_number("+vsl008+"."+vsl009+") + "+vsl007.trim()+")");
								}
								
							}else{
								this.setMainMessage("���������쳣���쳣��Ϣ��У��������롾�ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ���ֻ��������͵ģ�У�����ID-"+vsl.getVsl001());
								return EventRtnType.FAILD;
							}
							
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶ���Ϣ���ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
						//6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚ�
					}else if("6".equals(subCodeValue)){
						//�̶�ֵ
						if(!StringUtil.isEmpty(vsl007) && vsl007.trim().matches("-?\\d+") ){
							
							if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd))  ){
								if(DBUtil.getDBType() == DBType.MYSQL){
									sqlBf.append( "date_add(now(), INTERVAL "+vsl007.trim()+" MONTH) ");
								}else{
									sqlBf.append( "Add_Months(sysdate, "+vsl007.trim()+")");
								}
								/*�ൺ��Ŀ����*/
							}else if("varchar2".equalsIgnoreCase(vsl005Should)){
								if(DBUtil.getDBType() == DBType.MYSQL){
									sqlBf.append( "date_add(now(), INTERVAL "+vsl007.trim()+" MONTH) ");
								}else{
									sqlBf.append( "Add_Months(sysdate, "+vsl007.trim()+")");
								}
								/*�ൺ��Ŀ����*/
							}else{
								this.setMainMessage("���������쳣���쳣��Ϣ��У��������롾��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ�������͵ģ�У�����ID-"+vsl.getVsl001());
								return EventRtnType.FAILD;
							}
							
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
						//7-������ʽ������������
					}else if("7".equals(subCodeValue)){
						sqlBf.append(vsl003)
						.append(".")
						.append(vsl.getVsl004());
						//8-�������������֤��Ϣ���Ƿ���ڣ�
					}else if("8".equals(subCodeValue)){
						if("80".equalsIgnoreCase(vsl006CodeValue.getCodeValue()) || "81".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlExiest(vsl003,tabPrimary,vsl006CodeValue.getCodeValue()));
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
					}else if("9".equals(subCodeValue)){
						//���֤У��
						if("95".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlBirthPlace((vsl.getVsl004())));
						}else if("99".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlA0184CompareToA0104(a01TabName));
						}else if("98".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlMoreA0184(a01TabName));
						}else if("97".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlExistPhoto(a01TabName));
						}else if("90".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.append(RuleSqlListBS.sqlA0195CompareToA0201(a01TabName));
						}else if("91".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.delete(0,sqlBf.length());
							sqlBf.append(RuleSqlListBS.sqlA2911Compare(a01TabName));
						}
						else if("96".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
							sqlBf.delete(0,sqlBf.length());
							sqlBf.append(RuleSqlListBS.a0207CompareToA0148(a01TabName));
						}else{
							this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
							return EventRtnType.FAILD;
						}
					}else{
						this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
						return EventRtnType.FAILD;
					}
					
					
					//2.3.4�������׺
					
					sqlBf.append(codeName3).append(SPACE);
					
					//������MYSQL��  ��7-������ʽ�������������������ʹ��REGEXP����
					if("7".equals(subCodeValue) && DBUtil.getDBType() == DBType.MYSQL ){
						sqlBf = new StringBuffer(sqlBf.toString().replace("regexp_like(", "").replace(codeName3, " REGEXP '[a-z]'"));
					}
					
					//2.3.5.����������
					if(!"91".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
					for(Long i=0L;null != vsl.getVsl011() && i<vsl.getVsl011();i++) {
						sqlBf.append(")");
						bracket = false;
					}

					//2.3.6.�����¸�������
					
					if(vsl.getVsl012().equals("1")){
						sqlBf.append(SPACE).append("and");
						flag = false;
					}else if(vsl.getVsl012().equals("2")){
						if(DBUtil.getDBType()==DBType.MYSQL){
							if(!bracket){
								sqlBf.append(SPACE).append("union all").append(SPACE).append(selectSql);
								flag = true;
							}else{
								sqlBf.append(SPACE).append("or"); 
								flag = false;
							}
						}else{
							sqlBf.append(SPACE).append("or");  //oracle 
						}
					}
					}
				}//end if 
				
			}//end foreach verify_sql_list
			String sql = "";
			if(DBUtil.getDBType()==DBType.MYSQL){
				if(flag){
						sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf("union all")) ;					
				}else{
					sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf(SPACE));
				}
			}else{
				sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf(SPACE))+" ) ";
			}
			
			if("91".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
				sql= sqlBf.toString();
			}
		
			sql = sql.replace(a01TabName +".AGE", "GET_BIRTHDAY("+a01TabName+".a0107, '"+DateUtil.getcurdate()+"')");
			CommonQueryBS.systemOut("SQL========>"+sql);
			flag = false;
			//3.У��SQL��Ч�ԣ����趨У����� �Ƿ���Ч�����ã�
			
			String ora_err = RuleSqlListBS.testSql(sql.replace(A01_PARAM, "'1'").replace(B01_PARAM, "'1'"));
			if(!StringUtil.isEmpty(ora_err)){
				errorInfos.append(++errorCount+"��У�����IDΪ��"+vr.getVru001()+"��У�����SQL����쳣��"+ora_err+";\n");
				vr.setVru007("0");//��Ч��� �� 0-��Ч��δ���ã�
			}else{
				vr.setVru007("1");//��Ч��� �� 1-��Ч�����ã�
			}
			
			vr.setVru009(sql);//ƴ����ɵ�sql���
			sess.saveOrUpdate(vr);
			sess.flush();
		}
		
		/*//4. �趨У�鷽����Ч�����ã�
		
		if(!StringUtil.isEmpty(errorInfos.toString())){
			vs.setVsc003("0");//��Ч��� �� 0-��Ч��δ���ã�
			runResult = "����ʧ�ܣ�\n"+errorInfos.toString();
		}else{
			vs.setVsc003("1");//��Ч��ǣ�1-��Ч�����ã�
			runResult = "���óɹ���";
		}
		sess.saveOrUpdate(vs);
		sess.flush();*/
		
		//��¼��־
		try {
			if(vs!=null){
				new LogUtil().createLog("635", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null,Map2Temp.getLogInfo(vsDTO,vs) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		this.setMainMessage("��"+vs.getVsc002()+"��"+runResult);
		
		//5.ˢ�·����б� 
		this.getExecuteSG().addExecuteCode("changeh1('"+vs.getVsc002()+"')");
		this.getExecuteSG().addExecuteCode("radow.doEvent('verifyRuleGrideload','"+vs.getVsc001()+","+vs.getVsc007()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * ����У�鷽��ĳ�����ݣ�
	 * ��ѡ���л�ȡ��
	 * @return
	 * @throws RadowException 
	 */
	/*@PageEvent("VeriySchemeGrid.rowclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int clickRowVerifyScheme() throws RadowException{
		Grid grid = (Grid)this.getPageElement("VeriySchemeGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = null;
		for(int i=0;i<gridList.size();i++){
			 map = gridList.get(i);
			if(cueRowIndex==i){
				Object mcheck = map.get("mcheck");
				if(mcheck==null||StringUtil.isEmpty(mcheck.toString())||"false".equalsIgnoreCase(mcheck.toString())){
					map.put("mcheck", true);
				}else{
					map.put("mcheck", false);
				}
			}
			newList.add(map);
		}
		grid.setValueList(newList);
		grid.selectRow(cueRowIndex);
		
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	
	
	/**
	 * ˫��У�鷽��ĳ�����ݣ�
	 * 1.��ѡ���У�ȡ�������еĹ�ѡ
	 * 2.��ѯ���ж�Ӧ��У�����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("VeriySchemeGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowVerifyScheme() throws RadowException{
		Grid grid = (Grid)this.getPageElement("VeriySchemeGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		//List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String vsc001 = map.get("vsc001").toString();
		String vsc002 = map.get("vsc002").toString();
		String vsc007 = map.get("vsc007").toString();
		String vsc008 = map.get("vsc008").toString();
		/*@SuppressWarnings("unused")
		String vsc007 = null;
		//1.��ѡ���У�ȡ�������еĹ�ѡ  
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if(cueRowIndex==i){
				//map.put("mcheck", true);
				vsc001 = map.get("vsc001").toString();
				vsc007 = map.get("vsc007").toString();
			}else{
				map.put("mcheck", false);
			}
			newList.add(map);
		}
		grid.setValueList(newList);*/
		grid.selectRow(cueRowIndex);
		
		//2.��ѯ���ж�Ӧ��У�����
		this.getPageElement("dbClickVsc001").setValue(vsc001);//ѡ����vsc001
		this.getPageElement("isdefault").setValue(vsc007);
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("changeh1('"+vsc002+"')");
		//3.��ʾtab2
		//this.getExecuteSG().addExecuteCode("getShowTow()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ִ������ת��������vru010��vrutime�ֶ�
	 * @throws AppException 
	 */
	public void updateVru010Vrutime(String vsc001) throws Exception{
		CommQuery cqbs=new CommQuery();
		String sqlvru="select vru001 from verify_rule where vsc001='"+vsc001+"'";
		String sqlvsl="";
		String vru001="";
		List<HashMap<String, Object>> listvru = cqbs.getListBySQL(sqlvru);
		List<HashMap<String, Object>> listvsl = null;
		for (HashMap<String, Object> map : listvru) {
			vru001 = map.get("vru001").toString();
			sqlvsl="select vru001 from verify_rule where vru001='"+vru001+"'";
			listvsl = cqbs.getListBySQL(sqlvsl);
			for (HashMap<String, Object> hashMap : listvsl) {
				hashMap.get("");
			}
		}
	}
	/**
	 * ˫��У�����ĳ�����ݣ��򿪶Ի���鿴�޸�У�����
	 * 
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("VerifyRuleGrid.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	public int dbClickRowVerifyRuleGrid() throws RadowException{
		Grid grid = (Grid)this.getPageElement("VerifyRuleGrid");
		String vsc001 = grid.getValue("vsc001").toString();
		String vru001 = grid.getValue("vru001").toString();
		this.setRadow_parent_data(vsc001+","+vru001);
		try {
			String vsc001Only = HBUtil.getValueFromTab("vsc001", "verify_scheme", "VSC007='1'");
			if(vsc001Only.equals(vsc001)){//ΪĬ��У�ˣ��޸Ĺ���ʧЧ
				this.setMainMessage("����Ĭ�Ϸ����������޸ģ� ��  ");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String vrutime = HBUtil.getValueFromTab("vrutime", "verify_rule", "vru001='"+vru001+"'");
			if("-1".equals(vrutime)){//�ù���Ϊ��Ĭ�Ϸ������뷽�����ƶ����Ĺ����޷��޸�
				this.setMainMessage("�ù���Ϊ��Ĭ�Ϸ����������������ƶ����Ĺ����޷��޸ģ� ��  ");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.openWindow("addwin", "pages.sysmanager.verificationschemeconf.RuleSqlList");//ԭ�������޸�������ҳ��
//		this.openWindow("addwin", "pages.cadremgn.sysmanager.CheckExpression");//��Ҫ��򿪷�ʽ
		this.getExecuteSG().addExecuteCode("checkExpressionWinQD('"+vsc001+","+vru001+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("updateRule")
	public int updateRule(String index) throws RadowException{
		Grid grid = (Grid)this.getPageElement("VerifyRuleGrid");
		List<HashMap<String ,Object>> list=grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String,Object> map=list.get(cueindex);
		String vru001=map.get("vru001").toString();
		String vsc001=this.getPageElement("dbClickVsc001").getValue();
		try {
			String vsc001Only = HBUtil.getValueFromTab("vsc001", "verify_scheme", "VSC007='1'");
			if(vsc001Only.equals(vsc001)){//ΪĬ��У�ˣ��޸Ĺ���ʧЧ
				this.setMainMessage("����Ĭ�Ϸ����������޸ģ� ��  ");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String vrutime = HBUtil.getValueFromTab("vrutime", "verify_rule", "vru001='"+vru001+"'");
			if("-1".equals(vrutime)){//�ù���Ϊ��Ĭ�Ϸ������뷽�����ƶ����Ĺ����޷��޸�
				this.setMainMessage("�ù���Ϊ��Ĭ�Ϸ����������������ƶ����Ĺ����޷��޸ģ� ��  ");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		this.setRadow_parent_data(vsc001+","+vru001);
		//		this.openWindow("addwin", "pages.sysmanager.verificationschemeconf.RuleSqlList");//ԭ������ҳ��
		this.getExecuteSG().addExecuteCode("checkExpressionWinQD('"+vsc001+","+vru001+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ͨ��Grid�ϰ�ť������ҳ���޸�У�鷽����Ϣ
	 * @param vsc001
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("updateScheme.onclick")
	@Transaction
	public int updateScheme(String index) throws AppException, RadowException{
		Grid grid = (Grid)this.getPageElement("VeriySchemeGrid");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String,Object> map=gridList.get(cueindex);
		String vsc001=map.get("vsc001").toString();
		/*int count=0;
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if("true".equals(map.get("mcheck").toString())){
				vsc001=map.get("vsc001").toString();
				count++;
			}
		}
		if(count>1){
			this.setMainMessage("ֻ��ѡ��һ��У�鷽��,�����¹�ѡ");
			return EventRtnType.FAILD;
		}*/
		
		if(StringUtil.isEmpty(vsc001)){
			throw new AppException("��ȡ��У�鷽��IDʧ�ܣ�");
		}
		//String userid = SysUtil.getCacheCurrentUser().getUserVO().getLoginname(); //��ǰ�û���Ϣ
		HBSession sess = HBUtil.getHBSession();
		VerifyScheme vs = (VerifyScheme)sess.get(VerifyScheme.class, vsc001);
		if(vs!=null){
			/*if("1".equals(vs.getVsc007()) &&  !"admin".equalsIgnoreCase(userid)){
				throw new AppException("����Ĭ�Ϸ�������������admin�������޸ģ�");
			}*/
			this.setRadow_parent_data(vsc001);
			this.openWindow("addSchemeWin", "pages.sysmanager.verificationschemeconf.AddScheme");//ԭ������ҳ��
//			this.getExecuteSG().addExecuteCode("checkExpressionWinQD();");
		}else{
			throw new AppException("��ȡ��У�鷽��ʧ�ܣ�����ID-"+vsc001);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Grid��ֱ���޸ĺ󣬵���������޸ġ���ť�޸�У�鷽����Ϣ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveUpdateScheme.onclick")
	@NoRequiredValidate
	@Transaction
	public int saveUpdateSchemeGrid() throws RadowException{
		List<HashMap<String,Object>> schemeGrid = this.getPageElement("VeriySchemeGrid").getValueList();
		String msg = "";
		try {
			msg = RuleSqlListBS.saveUpdateSchemeGrid(schemeGrid, false);
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			this.getPageElement("VeriySchemeGrid").reload();
			this.getPageElement("VerifyRuleGrid").reload();
			return EventRtnType.FAILD;
		}
		this.setMainMessage(msg);
		this.getPageElement("VeriySchemeGrid").reload();
		this.getPageElement("VerifyRuleGrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �򿪵���������У�鷽����Ϣ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addScheme.onclick")
	@NoRequiredValidate
	@AutoNoMask(true)
	public int openAddSchemeWin()throws RadowException{
		this.setRadow_parent_data("");
		
		this.openWindow("addSchemeWin", "pages.sysmanager.verificationschemeconf.AddScheme");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �򿪵�����������ϢУ�������õ������
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("paraSet.onclick")
	@NoRequiredValidate
	@AutoNoMask(true)
	public int openParaSetWin()throws RadowException{
		this.setRadow_parent_data("");
		this.openWindow("paraSetWin", "pages.sysmanager.verificationschemeconf.ParaSet");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * Grid�ϵ��������У�顿,������У��������
	 * @param vsc001
	 * @return
	 * @throws AppException 
	 * @throws RadowException 
	 */
	@PageEvent("addMyscript")
	@Transaction
	public int addVerifyRule() throws AppException, RadowException{
		String vsc001=this.getPageElement("dbClickVsc001").getValue();
		if(StringUtil.isEmpty(vsc001)){
//			throw new AppException("��ȡУ�鷽��IDʧ�ܣ�,����˫��ѡ�񷽰�");
			this.setMainMessage("��ȡУ�鷽��IDʧ�ܣ�,����˫��ѡ�񷽰�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//grid.selectRow(cueRowIndex);
		HBSession sess = HBUtil.getHBSession();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		VerifyScheme vs = (VerifyScheme)sess.get(VerifyScheme.class, vsc001);
		if(vs!=null){
			if(vs.getVsc007()!=null && vs.getVsc007().toString().equals("1")&&!"admin".equals(username)){
				this.setMainMessage("����ʧ�ܣ���"+vs.getVsc002()+"���÷�������Ϊ��Ĭ�ϻ����������������޸ģ���������У�����");
				return EventRtnType.FAILD;
			}
			this.setRadow_parent_data(vsc001);
//			this.openWindow("addwin", "pages.sysmanager.verificationschemeconf.RuleSqlList");//ԭ������ҳ��
			this.getExecuteSG().addExecuteCode("checkExpressionWinQD('"+vsc001+",');");
		}else{
			throw new AppException("��ȡ��У�鷽��ʧ�ܣ�����ID-"+vsc001);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ɾ��
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("delScheme.onclick")
	@NoRequiredValidate
	public int deleteSchemeBefore(String index)throws RadowException{
		PageElement pe = this.getPageElement("VeriySchemeGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String vsc001=map.get("vsc001").toString();
		/*int count = 0;
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("mcheck");
			if(logchecked!=null && logchecked.equals(true)){
				count++;
				Object vsc007 =  map.get("vsc007");
				if(vsc007!=null && vsc007.toString().equals("1")){
					this.setMainMessage("ɾ��ʧ�ܣ���"+map.get("vsc002")+"���÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
					return EventRtnType.FAILD;
				}
			}
		}
		if(count < 1){
			this.setMainMessage("��ã�δѡ�����ݣ���ѡ��");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		this.setMainMessage("��ȷ��Ҫɾ������У�鷽��ô��");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "deleteScheme",vsc001);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ɾ��У�鷽��
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("deleteScheme")
	@NoRequiredValidate
	public int deleteScheme(String vsc001)throws RadowException, AppException{
		HBTransaction trans=null;
		HBSession sess = HBUtil.getHBSession();
		
		try {
			trans=sess.beginTransaction();
			Grid g = (Grid) this.getPageElement("VeriySchemeGrid");
			//List<HashMap<String, Object>> data = g.getValueList();
			//boolean isSelected = false;
			/*for(Iterator<HashMap<String, Object>> it = data.iterator();it.hasNext();){*/
				/*HashMap<String, Object> rowData = it.next();
				if(new Boolean(true).equals(rowData.get("mcheck")) || "1".equals(rowData.get("mcheck"))){
					isSelected = true;
					String vsc001Str = (String)rowData.get("vsc001");
					String vsc007Str = (String)rowData.get("vsc007");
					if(vsc007Str!=null && "1".equals(vsc007Str.trim())){
						throw new AppException("��У�鷽�����趨Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
					}*/
					//VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
					VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
					String delVrSql = "delete from Verify_Rule where vsc001 ='"+vsc001+"'";
					String delVslSql = new String("delete from Verify_Sql_List where vru001 in (select vru001 from Verify_Rule where vsc001 = '"+vsc001+"' )");
					//ɾ�� Verify_Sql_List
					sess.connection().prepareStatement(delVslSql).executeUpdate();
					//ɾ��Verify_Rule
					sess.connection().prepareStatement(delVrSql).executeUpdate();
					//ɾ��Verify_Scheme
					sess.delete(vs);
					
					//��¼��־
					try {
						if(vs!=null){
							new LogUtil().createLog("634", "VERIFY_SCHEME", vsc001, vs.getVsc002(), null,Map2Temp.getLogInfo(vs, new VerifyScheme()) );
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				/*}*/
			/*}*/
		/*	if(!isSelected){
				this.setMainMessage("����Ҫɾ������ǰ��ѡ��");
			}else{
				g.reload();
			}*/
			sess.flush();
			sess.getTransaction().commit();
			g.reload();
		} catch (Exception e) {
			e.printStackTrace();
			if (null != trans){
				try {
					trans.rollback();
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
			throw new AppException("ɾ��ʧ�ܣ�"+e.getMessage());
		}
		//this.getPageElement("VerifyRuleGrid").setValue("");
		this.getExecuteSG().addExecuteCode("changetext()");
		this.getPageElement("dbClickVsc001").setValue("");
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/**
	 * ɾ��У�����ǰ����ʾ
	 * @return
	 */
	@PageEvent("delAll.onclick")
	@NoRequiredValidate
	public int delAllRulesBefore(){
		this.setMainMessage("ȷ��ɾ�����б�������У�����");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "delAll");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ȫ��ɾ��VerifyRuleGrid 
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("delAll")
	@Transaction
	public int delAllRules() throws RadowException, AppException{
		
		Grid grid = (Grid)this.getPageElement("VerifyRuleGrid");
		List<HashMap<String,Object>> list = grid.getValueList();
		String vsc001Str = "";
		HBSession sess = HBUtil.getHBSession();

		//1. ������ݿ�
		if(list!=null && list.size()>0 ){ // original sentence --->&& list.get(0)!=null  modified in 2016/12/8  @author 01322
			vsc001Str = list.get(0).get("vsc001").toString();
			VerifyScheme vs =  (VerifyScheme)sess.get(VerifyScheme.class, vsc001Str);
			if(vs!=null && vs.getVsc007()!=null && vs.getVsc007().toString().equals("1")){
				this.setMainMessage("ɾ��ʧ�ܣ���"+vs.getVsc002()+"���÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
				return EventRtnType.FAILD;
			}
			
			sess.beginTransaction();
			Connection conn = null;
			
			@SuppressWarnings("unchecked")
			List<VerifyRule> rvList = sess.createQuery("from VerifyRule where vsc001='"+vsc001Str+"'").list();
			
			//1.1  ɾ��verify_sql_list 
			String sql1 = "delete from verify_sql_list where vru001 in (select vru001 from verify_rule where vsc001 = ? )";
			//1.2 ɾ��verify_rule
			String sql2 = "delete from verify_rule where vsc001 = ?";
			try {
				conn = sess.connection();
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, vsc001Str);
				pstmt1.executeUpdate();
				
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, vsc001Str);
				pstmt2.executeUpdate();
				
				//��¼��־
				List<String[]> changesList = new ArrayList<String[]>();
				VerifyRule vrNew = new VerifyRule();//�ն����¶���ȶ�Ϊ��ʱʹ��
				for(int i = 0;i<rvList.size();i++){
					changesList.addAll( Map2Temp.getLogInfo(rvList.get(i),vrNew ));
				}
				new LogUtil().createLog("640", "VERIFY_SCHEME", vsc001Str, vs.getVsc002(), "IDΪ��"+vsc001Str+"����У�鷽����"+vs.getVsc002()+"��У�����ȫ��ɾ��",changesList );
				
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if(conn!=null){
					try {
						conn.rollback();
						return EventRtnType.FAILD;
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}else{
			this.setMainMessage("У������б����޿�ɾ�������ݣ�");
		}
		//2. ���ҳ��
		grid.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delRowBefore")
	@NoRequiredValidate
	public int delRuleRowBefore(String index){
		if(StringUtil.isEmpty(index)){
			this.setMainMessage("��ѡ��Ҫɾ����У�����");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("ȷ��ɾ����У�����");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "delRow",index);
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ɾ��ѡ����VerifyRuleGrid 
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("delRow")
	@Transaction
	public int delRuleRow(String index) throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		/*List<String> vru001List = new ArrayList<String>();
		//У��
		
		String vru001Str = vru001Para.substring(0, vru001Para.lastIndexOf(","));
		StringBuffer vru001Where = new StringBuffer();
		String[] vru001s = vru001Str.split(",");
		for(String vru001 : vru001s){
			vru001Where.append("'").append(vru001).append("',");
			vru001List.add(vru001);
		}
		vru001Str = vru001Where.toString().substring(0, vru001Where.lastIndexOf(","));*/
		
	/*	@SuppressWarnings("unchecked")
		List<Object> list =  sess.createSQLQuery("SELECT  vsc007 FROM Verify_Scheme  a , verify_rule b WHERE a.vsc001 = b.vsc001 AND b.vru001 in ("+vru001Str +")").list();
		for(Object vsc007 : list){
			if(vsc007!=null && vsc007.toString().equals("1")){
				this.setMainMessage("ɾ��ʧ�ܣ��÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
				return EventRtnType.FAILD;
			}
		}*/
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		PageElement pe = this.getPageElement("VerifyRuleGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String vru001=map.get("vru001").toString();
		String vsc001=map.get("vsc001").toString();
		Object vsc007=sess.createSQLQuery("select vsc007 from verify_scheme where vsc001='"+vsc001+"'").uniqueResult();
		if(vsc007!=null && vsc007.toString().equals("1")&&!"admin".equals(username)){
			this.setMainMessage("ɾ��ʧ�ܣ��÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
			return EventRtnType.FAILD;
		}
		//��ѯ��ɾ��У���������
		@SuppressWarnings("unchecked")
		List<VerifyRule> rvList = sess.createQuery("from VerifyRule a where a.vru001 = '"+vru001+"'").list();
		
		//ɾ��
		sess.beginTransaction();
		Connection conn = null;
		//1  ɾ��verify_sql_list 
		String sql1 = "delete from verify_sql_list where vru001  = '"+vru001+"'";
		//2 ɾ��verify_rule
		String sql2 = "delete from verify_rule where vru001 = '"+vru001+"'";
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			conn = sess.connection();
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.executeUpdate();
			
			
			//��¼��־
			VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001);
			List<String[]> changesList = new ArrayList<String[]>();
			VerifyRule vrNew = new VerifyRule();//�ն����¶���ȶ�Ϊ��ʱʹ��
			for(int i = 0;i<rvList.size();i++){
				changesList.addAll( Map2Temp.getLogInfo(rvList.get(i),vrNew ));
			}
			new LogUtil().createLog("640", "VERIFY_SCHEME", vs.getVsc001(), vs.getVsc002(), "IDΪ��"+vs.getVsc001()+"����У�鷽����"+vs.getVsc002()+"����У�����IDΪ��"+vru001+"���ı�ɾ��",changesList );
			
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
					return EventRtnType.FAILD;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally{
			try {
				if(pstmt1!=null){
					pstmt1.close();
				}
				if(pstmt2!=null){
					pstmt2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.setNextEventName("VerifyRuleGrid.dogridquery");
			
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ���ò�ѯ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	/*@PageEvent("reset.onclick")
	@NoRequiredValidate  
	@AutoNoMask
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("vsc002").setValue("");
		this.getPageElement("vsc006_start").setValue("");
		this.getPageElement("vsc006_end").setValue("");
		this.getPageElement("vsc005_name").setValue("");
		this.getPageElement("vsc004").setValue("");
		this.getPageElement("comboxArea_vsc004").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	
	/**
	 * ��ѯ -�����ѯ��ť�����в�ѯ
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	public int queryVeriySchemeOnclick()throws RadowException, AppException {
		this.getPageElement("dbClickVsc001").setValue("");
		this.getPageElement("VerifyRuleGrid").setValueList(new ArrayList<HashMap<String,Object>>());
		this.setNextEventName("VeriySchemeGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("changetext()");;
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("VeriySchemeGrid.dogridquery")
	@NoRequiredValidate
	public int doVeriySchemeGridQuery(int start,int limit) throws RadowException{
		String sqlStr = "";
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String userid=user.getId();
		StringBuffer sql = new StringBuffer()
		.append("Select Vsc001, ")
		.append("       Vsc002, ")
		.append("       Vsc003, ")
		.append("       Nvl((Select Name From Smt_Group Where Groupid = Vsc004), '') Vsc004, ")
		.append("       Nvl((Select concat(concat(concat(Nvl(username,''),'('),Nvl(loginname,'')),')') From Smt_User Where Userid = Vsc005), '') Vsc005, ")
		.append("       To_Char(Vsc006, 'yyyy-MM-dd hh24:mi:ss') Vsc006, ")
		.append("       Vsc007, ")
		.append("       Vsc008, ")
		.append("       Vsc009, ")
		.append("       Vsc010, ")
		.append("       Vsc011 ")
		.append("  From Verify_Scheme ")
		.append(" Where 1 = 1 AND (vsc007='1' or vsc005='"+userid+"')");
		
		/*String vsc002 = this.getPageElement("vsc002").getValue();
		String vsc006_start = this.getPageElement("vsc006_start").getValue();
		String vsc006_end = this.getPageElement("vsc006_end").getValue();
		String vsc004 = this.getPageElement("vsc004").getValue();
		String vsc005Name = this.getPageElement("vsc005_name").getValue();
		
		if(!StringUtil.isEmpty(vsc002)){
			sql.append(" and vsc002 like '%" +vsc002 +"%' ");
		}
		if(!StringUtil.isEmpty(vsc006_start)){
			sql.append(" and to_char(vsc006,'yyyymmdd') >='" +vsc006_start+ "' ");
		}
		if(!StringUtil.isEmpty(vsc006_end)){
			sql.append(" and to_char(vsc006,'yyyymmdd') <='" +vsc006_end+ "' ");
		}
		if(!StringUtil.isEmpty(vsc004)){
			sql.append(" and vsc004 ='" +vsc004+ "' ");
		}
		if(!StringUtil.isEmpty(vsc005Name)){
			sql.append(" and vsc005 in (select userid from Smt_User where username like '%" +vsc005Name+ "%') ");
		}*/
		
		//����MySQL
		if(DBUtil.getDBType() == DBType.MYSQL){
			sqlStr =sql.toString().replace("Nvl(", "ifnull(")
					.replace("To_Char(Vsc006, 'yyyy-MM-dd hh24:mi:ss')", "DATE_FORMAT(Vsc006,'%Y%m%d %H:%i:%s')")
					.replace("to_char(vsc006,'yyyymmdd')", "DATE_FORMAT(Vsc006,'%Y%m%d')");
		}else{
			sqlStr = sql.toString();
		}
		
		
		this.pageQuery(sqlStr,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("VerifyRuleGrid.dogridquery")
	@NoRequiredValidate
	public int doVerifyRuleGridQuery(int start,int limit) throws RadowException{
		String dbClickVsc001 = this.getPageElement("dbClickVsc001").getValue();
		String sqlStr = "";
		StringBuffer sql = new StringBuffer( " Select a.Vsc001,   ")
									.append( "        b.Vsc002,   ")
									.append( "        Vru001,   ")
									.append( "        Vru002,   ")
									.append( "        Vru003,   ")
									/* �ൺ��Ŀ���� */
									.append( "       nvl((select  d.table_name from Code_Table d where a.Vru004 = d.table_code and rownum<2),'')  Vru004_name,   ")
									.append( "       nvl((select  c.Col_Name  from  Code_Table_Col c where a.Vru004 = c.Table_Code  And a.Vru005 = c.Col_Code and rownum<2),'')    Vru005_name,   ")
									/* �ൺ��Ŀ���� */
//									.append( "       nvl((select  d.table_name from Code_Table d where a.Vru004 = d.table_code),'')  Vru004_name,   ")
//									.append( "       nvl((select  c.Col_Name  from  Code_Table_Col c where a.Vru004 = c.Table_Code  And a.Vru005 = c.Col_Code ),'')    Vru005_name,   ")
									.append( "        Vru006,   ")
									.append( "        Vru007,   ")
									.append( "        Vru008,   ")
									.append( "        Vru009   ")
									.append( "   From Verify_Rule a, Verify_Scheme b   ")
									.append( "  Where a.Vsc001 = b.Vsc001   ")
									.append( "    And a.Vsc001 = '"+dbClickVsc001+"'   ")
									.append( "  Order By sorts  ");
		sqlStr = sql.toString() ;
		if(DBUtil.getDBType() == DBType.MYSQL){
			sqlStr = sqlStr.replace("nvl(", "ifnull(");
		}
		System.out.println("|-------->ִ����䣺"+sqlStr);
		this.pageQuery(sqlStr,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	/**
	 * ����������ѯ����ʼ��
	 * @return
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		List<GroupVO> list = PrivilegeManager.getInstance().getIGroupControl()
				.findByParentId(node);
		// ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<GroupVO> choose = new ArrayList<GroupVO>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser()
				.getId();
		List<GroupVO> groups = PrivilegeManager.getInstance()
				.getIGroupControl().findGroupByUserId(cueUserid);
		boolean topuser=false; 
		String areaid=areaInfo.get("areaid").toString();
		for (int i = 0; i < groups.size(); i++) {
			GroupVO vo=(GroupVO)groups.get(i);
			String groupid=vo.getId();
			if(groupid.equals(areaid)){
				topuser=true;
			}
			for (int j = 0; j < groups.size(); j++) {
				if (groups.get(j).getId().equals(groups.get(i).getParentid())) {
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		
		
		if (!groups.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				
				for (int j = 0; j < groups.size(); j++) {
					if (groups.get(j).getId().equals(list.get(i).getId())) {
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if (equel) {
			list = choose;
		}
		// ��
		if(topuser==false && node.equals(areaInfo.get("areaid"))){
			list=PrivilegeManager.getInstance()
			.getIGroupControl().findGroupByUserId(cueUserid);
		}
		
		
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (GroupVO group : list) {
				if (i == 0 && last == 1) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				} else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
		
	}

	/**
	 * ��ʾУ��ű�����ɹ�����ˢ�¸÷�����У������б�(VerifyRuleGrid)
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("impSuccess")
	public int impSuccess() throws RadowException{
		this.setMainMessage("����ɹ���");
		this.setNextEventName("VeriySchemeGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ʾУ��ű�����ɹ�����ˢ�¸÷�����У������б�(VerifyRuleGrid)
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("success")
	public int successReload(String vsc001Str) throws RadowException{
		String vsc001 = vsc001Str;
		String msg = "����ɹ���";
		if(!StringUtil.isEmpty(vsc001Str) && vsc001Str.contains(",")){
			String[] infos= vsc001Str.split(",");
			vsc001 = infos[0];
			msg = infos[1];
			/*this.getPageElement("flag").setValue("true");
			this.setNextEventName("stop.onclick");
			this.setNextEventName("run.onclick");*/
			this.setMainMessage(msg);
		}else{
			this.setMainMessage(msg);
		}
		
		this.getPageElement("dbClickVsc001").setValue(!StringUtil.isEmpty(vsc001)?vsc001:"");
		VerifyScheme vs=(VerifyScheme) HBUtil.getHBSession().get(VerifyScheme.class, vsc001);
		this.getPageElement("isdefault").setValue(vs.getVsc007());
		this.setNextEventName("VeriySchemeGrid.dogridquery");
		this.setNextEventName("VerifyRuleGrid.dogridquery");	
		this.getExecuteSG().addExecuteCode("changeh1('"+vs.getVsc002()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ʾУ��ű�����ɹ�����ˢ�¸÷�����У������б�(VerifyRuleGrid)
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("verifyRuleGrideload")
	public int verifyRuleGrideload(String parm) throws RadowException{
		String[] parms=parm.split(",");
		this.getPageElement("dbClickVsc001").setValue(parms[0]);
		this.getPageElement("isdefault").setValue(parms[1]);
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		this.setNextEventName("VeriySchemeGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ҳ�����¼���
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnReload.onclick")
	public int reload() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		this.getPageElement("username").setValue(username);
		this.setNextEventName("VeriySchemeGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("openCopyWin")
	public int openCopyWin(String vru001Para) throws RadowException{
		if(StringUtil.isEmpty(vru001Para)){
			this.setMainMessage("��ѡ��Ҫ���Ƶ�У�����");
			return EventRtnType.FAILD;
		}
		String sql="select count(*) from verify_scheme";
		String  num=HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
		if(Integer.parseInt(num)<=1){
			this.setMainMessage("��ǰû���������ϵ�У�鷽��");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(vru001Para);
		this.openWindow("copyWin", "pages.sysmanager.verificationschemeconf.CopyRule");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("runRule")
	public int runRule(String index) throws RadowException{
		if(StringUtil.isEmpty(index)){
			this.setMainMessage("��ѡ��Ҫ���õ�У�����");
			return EventRtnType.FAILD;
		}
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		HBSession sess=HBUtil.getHBSession();
		PageElement pe = this.getPageElement("VerifyRuleGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String vru001=map.get("vru001").toString();
		String vsc001=this.getPageElement("dbClickVsc001").getValue();
		String s="select vsc007 from Verify_Scheme where vsc001='"+vsc001+"'";
		Object vsc007=sess.createSQLQuery(s).uniqueResult();
		if(vsc007!=null&&vsc007.toString().equals("1")&&!"admin".equals(username)){
			this.setMainMessage("����ʧ�ܣ��÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
			return EventRtnType.FAILD;
		}
		/*String  sql="update Verify_Rule set vru007='1' where vru001='"+vru001+"'";
		   sess.createSQLQuery(sql).executeUpdate();*/
		/* �ൺ��Ŀ */
		String error= "";
		try {
			String vrutime = HBUtil.getValueFromTab("vrutime", "verify_rule","vru001='"+vru001+"'");
			if("-1".equals(vrutime)){
				sess.createSQLQuery("update verify_rule set Vru007 ='1' where vru001='"+vru001+"'").executeUpdate();
			}else{
				error= PJSql(vru001);
			}
		} catch (AppException e) {
			//��ȡ���� -- Ϊ����Ĭ�Ϸ���ʱ������������ƴ�����
			e.printStackTrace();
		}
		/* �ൺ��Ŀ */
//		String error= PJSql(vru001);
		VerifyRule vr=(VerifyRule) sess.get(VerifyRule.class, vru001);
		if("1".equals(vr.getVru007())){
			this.setMainMessage("���óɹ�");
		}else{
			if(!StringUtil.isEmpty(error)){
				this.setMainMessage(error);
			}	
		}
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("stopRule")
	public int stopRule(String index) throws RadowException{
		if(StringUtil.isEmpty(index)){
			this.setMainMessage("��ѡ��Ҫ���õ�У�����");
			return EventRtnType.FAILD;
		}
		HBSession sess = HBUtil.getHBSession();
		/*List<String> vru001List = new ArrayList<String>();
		//У��
		String vru001Str = vru001Para.substring(0, vru001Para.lastIndexOf(","));
		StringBuffer vru001Where = new StringBuffer();
		String[] vru001s = vru001Str.split(",");
		for(String vru001 : vru001s){
			vru001Where.append("'").append(vru001).append("',");
			vru001List.add(vru001);
		}
		vru001Str = vru001Where.toString().substring(0, vru001Where.lastIndexOf(","));*/
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		PageElement pe = this.getPageElement("VerifyRuleGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String vru001=map.get("vru001").toString();
		String vsc001=this.getPageElement("dbClickVsc001").getValue();
		String s="select vsc007 from Verify_Scheme where vsc001='"+vsc001+"'";
		Object vsc007=sess.createSQLQuery(s).uniqueResult();
		if(vsc007!=null&&vsc007.toString().equals("1")&&!"admin".equals(username)){
			this.setMainMessage("ͣ��ʧ�ܣ��÷�������Ϊ��Ĭ�ϻ����������������޸ģ�����ɾ����");
			return EventRtnType.FAILD;
		}

			String  sql="update Verify_Rule set vru007='0' where vru001='"+vru001+"'";
			 sess.createSQLQuery(sql).executeUpdate();
		
		this.setMainMessage("ͣ�óɹ�");
		this.setNextEventName("VerifyRuleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("runOrStopScheme")
	public int rosScheme(String parm){
		String[] parms=parm.split(",");
		String vsc003=parms[0];
		String index=parms[1];
		if("0".equals(vsc003)){
			this.getExecuteSG().addExecuteCode("radow.doEvent('run.onclick','"+index+"')");
		}else{
			this.getExecuteSG().addExecuteCode("radow.doEvent('stop.onclick','"+index+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("runOrStopRule")
	public int rosRule(String parm){
		String[] parms=parm.split(",");
		String vru007=parms[0];
		String index=parms[1];
		if("0".equals(vru007)){
			this.getExecuteSG().addExecuteCode("radow.doEvent('runRule','"+index+"')");
		}else{
			this.getExecuteSG().addExecuteCode("radow.doEvent('stopRule','"+index+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���������sqlƴ��
	 * @param vru001
	 * �ൺ��Ŀ��Ҫ - ʵ���µ��뷽�����ڵ�һ������ʱ����ʾ����vru010��vrutime�ֶ�
	 * ���ڹ���������У��޶�Ӧ�������ʱ����ʾת��ʧ�ܣ�����ֵnull
	 */
	public String PJSql(String vru001){
		int bs = 0;	// ��ʶ 0:�Ѿ�ƴ�Ӻõ�����ǰ��䣻1������ - �ൺ��Ŀ����
		HBSession sess = HBUtil.getHBSession();
		int errorCount = 0;								//�������
		String runResult = null;						//���÷������
		StringBuffer errorInfos = new StringBuffer("");	//У��SQLִ������Ĵ�����Ϣ��
		//String vsc001Str = null ;						//У�鷽������
		VerifyScheme vs = null;							//У�鷽��
		List<VerifySqlList> verifySqlList = null;		//У�������У��SQL�б�(VerifySqlList)
		StringBuffer sqlBf = null;						//sql���
		TreeSet<String> fromTab = null;					//ÿ��У�����(VerifyRule)�漰�ı�,��ȡ��У�������У��SQL�б�(VerifySqlList)�������輰��У���ͱȶԱ�
		String sqlCount = "from VerifySqlList a  where a.vru001 IN (:vru001s) order by vsl014";
		String selectSql = "";							//split sql in two parts,first part (select ... where 1=1)
		String secSql = "";								//the second part (and column_name = 'value')
		boolean flag = false;							//flag = true use union all ,else flag = false 
		boolean bracket = false;						//judge left bracket
		
		VerifyRule vr=(VerifyRule) sess.get(VerifyRule.class, vru001);
		fromTab = new TreeSet<String>();//�漰��
		verifySqlList = sess.createQuery(sqlCount).setParameter("vru001s", vr.getVru001()).list();
		String vsl003 = null;		// У���
		String vsl008 = null;		// �ȶԱ�
		String tabPrimary = null;	// ����
		boolean vsl006Flag8 = false;// ������������Ƿ����8�������������֤��Ϣ���Ƿ���ڣ�
		
		//2.0 Select����
		
		sqlBf =  new StringBuffer("select "+COUNT_SQL+FROM);
		
		//2.1 ��ӱ�(�������⣬�����ظ�):FROM����
		
		for(VerifySqlList vsl : verifySqlList){
			 vsl003 =TEMP_FLAG?vsl.getVsl003()+TEMP_PREFIX:vsl.getVsl003();// У���
			 vsl008 =(TEMP_FLAG&&!StringUtil.isEmpty(vsl.getVsl008()))?vsl.getVsl008()+TEMP_PREFIX:vsl.getVsl008();// �ȶԱ�
			if(StringUtil.isEmpty(vsl003)){
				//this.setMainMessage("У������б���У�����Ϣȱʧ���쳣��Ϣ��У������б�ID-"+vsl.getVsl001());
				return "У������б���У�����Ϣȱʧ���쳣��Ϣ��У������б�ID-"+vsl.getVsl001();
			}
			
			//2.1.1 ���У���(ע��  8�������������֤��Ϣ���Ƿ���ڣ���У�����ӵ�From�����)
			
			if(!StringUtil.isEmpty(vsl003) &&  !fromTab.contains(vsl003)  && !"80".equals(vsl.getVsl006()) &&  !"81".equals(vsl.getVsl006())&&!"96".equals(vsl.getVsl006())&&!"94".equals(vsl.getVsl006()) ){
				fromTab.add(vsl003);
				sqlBf.append(SPACE)
					.append(",")
					.append(SPACE)
					.append(vsl003);
			}
			
			//2.1.2 ��ӱȶԱ�
			
			if(!StringUtil.isEmpty(vsl008) && !fromTab.contains(vsl008)){
				fromTab.add(vsl008);
				sqlBf.append(SPACE)
					.append(",")
					.append(SPACE)
					.append(vsl008);
			}
			
			//�������ж�������������Ƿ����8�������������֤��Ϣ���Ƿ���ڣ�
			if("80".equals(vsl.getVsl006()) ||  "81".equals(vsl.getVsl006())||"96".equals(vsl.getVsl006())||"94".equals(vsl.getVsl006())){
				vsl006Flag8 = true;
			}
			
		}
		
			
		if(fromTab.size()==1 && fromTab.contains(b01TabName)){
			
		}else{
				//2.1.3 �����Ա������Ϣ��
			
			/*if(!fromTab.contains(a01TabName)){
				fromTab.add(a01TabName);
				sqlBf.append(SPACE)
				.append(",")
				.append(SPACE)
				.append(a01TabName);
			};*/
				//2.1.4 ������B01�����A02��
			
			if(fromTab.contains(b01TabName) && !fromTab.contains(a02TabName)){
				sqlBf.append(SPACE)
				.append(",")
				.append(SPACE)
				.append(a02TabName);
			};
			
				//��������ѯ��Ϊ�� �� У������а���8�������������֤��Ϣ���Ƿ���ڣ�������� a01 �� ��Ϊ����
			if(fromTab.size()<1 && vsl006Flag8){
				fromTab.add(a01TabName);
				sqlBf.append(SPACE)
				.append(",")
				.append(SPACE)
				.append(a01TabName);
			}
			
			
		}
		sqlBf = new StringBuffer(sqlBf.toString().replaceFirst(",", ""));
		//2.2 ���Where�������Ӳ���
		
		sqlBf.append(" WHERE 1=1  AND ");
		if(fromTab==null || fromTab.size() < 1 ){
			/** �ൺ��Ŀ�޸� **/
			String numforvru010 = "";
			try {
				numforvru010 = HBUtil.getValueFromTab("count(1)", "verify_rule", " vru001 ='"+vru001+"' and vru010 is null ");
			} catch (AppException e) {
				e.printStackTrace();
			}
			if("0".equals(numforvru010)){
				VerifyRule vrqd=(VerifyRule) sess.get(VerifyRule.class, vru001);
//				if(vrqd.getVru009()!=null&&vrqd.getVru010()!=null&&vrqd.getVru009().replace("_temp", "").contains(vrqd.getVru010())){
//					vrqd.setVru007("1");
//					sess.saveOrUpdate(vrqd);
//					sess.flush();
//					return "";
//				}
//				else {
//					/** �ൺ��Ŀ�޸� **/
//					//this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧУ����Ϣ����У��������ID-"+vr.getVru001());
//					return "���������쳣���쳣��Ϣ��У�����ȱʧУ����Ϣ����У��������ID-"+vr.getVru001();
//				}
//			} else {
//				/** �ൺ��Ŀ�޸� **/
//				//this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧУ����Ϣ����У��������ID-"+vr.getVru001());
//				return "���������쳣���쳣��Ϣ��У�����ȱʧУ����Ϣ����У��������ID-"+vr.getVru001();
//			}
		}
			//2.2.1 �趨����
		
		 if(fromTab.contains(b01TabName)&&fromTab.size()==1){
			sqlBf.append(b01TabName)
			.append(".")
			.append("B0111")
			.append(EQUAL)
			.append(B01_PARAM)
			.append(AND);
			
			tabPrimary = b01TabName;
		}else{
			for(String tabName :fromTab){
				if(!b01TabName.equalsIgnoreCase(tabName)){
					sqlBf.append(tabName)
					.append(".")
					.append("A0000")
					.append(EQUAL)
					.append(A01_PARAM)
					.append(AND);
					
					tabPrimary = tabName;
					break;
				}
			}
			
		}
		
		 vr.setVru006(tabPrimary);//ƴ����ɵ�SQL���������
		
			//2.2.2 ���������������
		
		for(String tabName : fromTab){
			if(tabName.equalsIgnoreCase(tabPrimary)){
				continue;
			}
			//�ǻ�����
			if(!tabName.equalsIgnoreCase(b01TabName)){
				sqlBf.append(SPACE) 
				.append(tabPrimary)
				.append(".")
				.append("A0000")
				.append(EQUAL)
				.append(tabName)
				.append(".")
				.append("A0000")
				.append(AND);
			}else{//B01��������Ҫ��A02������Ա����
				
				//��B01����A02����
				sqlBf.append(SPACE) 
				.append(a02TabName)
				.append(".")
				.append("a0201b")
				.append(EQUAL)
				.append(tabName)
				.append(".")
				.append("B0111")
				.append(AND);
				
				//����û��A02��A02���������
				if(!fromTab.contains(a02TabName)){
					sqlBf.append(SPACE) 
					.append(a01TabName)
					.append(".")
					.append("A0000")
					.append(EQUAL)
					.append(a02TabName)
					.append(".")
					.append("A0000")
					.append(AND);
				}
			}
		}
		selectSql = sqlBf.toString();		//store the first part
		//2.3 ���Where�����֤��������
		
		if(DBUtil.getDBType()==DBType.ORACLE){
		sqlBf.append(" ( ");//����֤�����ŵ�������
		}
		
		String fristStr = "";
		if (bs==0) {
			fristStr = sqlBf.toString();
			bs=1;
		}
		CodeValue vsl006CodeValue = null;			//�����CodeValue����
		String vsl006 = null;						//�����ֵ
		//��������ͣ�subCodeValue����1-��С�Ƚϣ�2-����ѡ��3-�Ƿ�Ϊ��;4-���ȱȽϣ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�7-������ʽƥ�������8-�������������֤��Ϣ���Ƿ���ڣ���9-������֤
		String subCodeValue = null;					//���������
		String vsl007 = null;						//�̶�ֵ
		String vsl013 = null;						//ѡ��ֵ
		String vsl009 = null;						//���������� �ֶ�
		String codeName2 = null;					//�������sql�У�
		String codeName3 = "";						//�������׺��sql�У�
		VVerifyColVsl006 vverifyColVsl006Vsl003 = null;//У����ֶ�������Ϣ
		String vsl005 = "";							//У����ֶ�ʵ����������
		String vsl005Should = "";					//У����ֶ������������ͣ�Ӧ�õ��������ͣ�
		String vsl005Should_qd = "";				//У����ֶ�Ϊ�ൺ�趨���ͣ�DΪdata��
		String vsl0052 = "";						//�ȶ���Ϣ��ʵ����������
		
		for(VerifySqlList vsl : verifySqlList){
			
			 vsl003 =TEMP_FLAG?vsl.getVsl003()+TEMP_PREFIX:vsl.getVsl003();// У���
			 vsl008 =(TEMP_FLAG&&!StringUtil.isEmpty(vsl.getVsl008()))?vsl.getVsl008()+TEMP_PREFIX:vsl.getVsl008();// �ȶԱ�
			 vsl006 = vsl.getVsl006();
			if(!StringUtil.isEmpty(vsl006)){
				vsl006CodeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
			}else{
				//this.setMainMessage("���������쳣���쳣��Ϣ��У������в��������Ϊ�գ�У�����ID-"+vsl.getVsl001());
				return "���������쳣���쳣��Ϣ��У������в��������Ϊ�գ�У�����ID-"+vsl.getVsl001();
			}
			
			//��������ͣ�subCodeValue����1-��С�Ƚϣ�2-����ѡ��3-�Ƿ�Ϊ��;4-���ȱȽϣ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�7-������ʽƥ�������8-�������������֤��Ϣ���Ƿ���ڣ���9-������֤
			 subCodeValue = vsl006CodeValue.getSubCodeValue();
			 vsl007 = vsl.getVsl007();//�̶�ֵ
			 vsl013 = vsl.getVsl013();//ѡ��ֵ
			 vsl009 = vsl.getVsl009();//���������� �ֶ�
			 codeName2 = vsl006CodeValue.getCodeName2()==null?"":vsl006CodeValue.getCodeName2();//�������sql�У�
			 codeName3 = vsl006CodeValue.getCodeName3()==null?"":vsl006CodeValue.getCodeName3();//�������׺
			 vverifyColVsl006Vsl003 = RuleSqlListBS.getVverifyColVsl006(vsl.getVsl003(),vsl.getVsl004());
//			 System.out.println(vsl.getVsl003()+"|"+vsl.getVsl004());
			//��������Ϊ�ջ�����varchar2,number,date���׳��쳣
			 if(vverifyColVsl006Vsl003==null){
				 //this.setMainMessage("���������쳣���쳣��Ϣ����"+RuleSqlListBS.getTableName(vsl.getVsl003())+"����"+RuleSqlListBS.getColName(vsl.getVsl003(), vsl.getVsl004())+"����ȡ������ϢΪ�գ�");
				 return "���������쳣���쳣��Ϣ����"+RuleSqlListBS.getTableName(vsl.getVsl003())+"����"+RuleSqlListBS.getColName(vsl.getVsl003(), vsl.getVsl004())+"����ȡ������ϢΪ�գ�";
			 }
			 /* �ൺ��Ŀ��Ҫ */
//			 vsl005Should_qd = vverifyColVsl006Vsl003.getColDataTypeShould_qd();
			 /* �ൺ��Ŀ��Ҫ */
			 vsl005 = vverifyColVsl006Vsl003.getColDataType();
			 vsl005Should = vverifyColVsl006Vsl003.getColDataTypeShould();
			 vsl0052 = !StringUtil.isEmpty(vsl.getVsl009()) ? RuleSqlListBS.getVverifyColVsl006(vsl.getVsl008(),vsl.getVsl009()).getColDataType():"";
			if(StringUtil.isEmpty(vsl005)){
				//this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧУ����������������ͣ�У�����ID-"+vsl.getVsl001());
				return "���������쳣���쳣��Ϣ��У�����ȱʧУ����������������ͣ�У�����ID-"+vsl.getVsl001();
			}/*else if(!vsl005.equalsIgnoreCase("varchar2") && !vsl005.equalsIgnoreCase("number") && !vsl005.equalsIgnoreCase("date")){
				this.setMainMessage("���������쳣���쳣��Ϣ��У�������У������������������쳣-"+vsl005+"��У�����ID-"+vsl.getVsl001());
				return EventRtnType.FAILD;
			}*/
			
			if(vsl.getVru001().equals(vr.getVru001())){
				
				sqlBf.append(SPACE);
				
				//2.3.1. ƴ�������� 
			
				for(Long i=0L;null != vsl.getVsl002() && i<vsl.getVsl002();i++) {
					sqlBf.append("(");
/*						if(DBUtil.getDBType()==DBType.MYSQL){
						sqlBf.deleteCharAt(sqlBf.length()-1);  //case when mysql ,delete left bracket set bracket = true
						//��¼��secSql��ֵ
						secSql = sqlBf.substring(sqlBf.lastIndexOf("1=1")+8,sqlBf.length());
					}*/
					bracket = true;
				}
				if(("is null".equals(codeName2)||"is not null".equals(codeName2)) && DBUtil.getDBType()==DBType.MYSQL){
					sqlBf.append(" ( ");
				}
				//2.3.2. ƴ��У���ֶ�
				
				//����������ٳ��ȱȽϵĻ�Ҫƴ�� ���ȱȽ�ǰ׺ LEN_COMPARE_PREFIX ��׺; �������������������͵�תΪ���ڸ�ʽ; ��9-����У�顢8-�������������֤��Ϣ���Ƿ���ڣ���7-�������  ��ƴ��У����
				if("9".equalsIgnoreCase(subCodeValue) || "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
					
				}else if(!"4".equalsIgnoreCase(subCodeValue)){
					//ֻ����������ΪDate,ʵ�����Ͳ���Date���ͣ��� Ӧ������رȽ�����ʱ��תΪDate���ͣ�1-��С�Ƚϡ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϡ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�
					if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) && !vsl005.equalsIgnoreCase(vsl005Should) 
							&& ("1".equals(subCodeValue) || "5".equals(subCodeValue) || "6".equals(subCodeValue))){
						sqlBf.append(SPACE)
						.append(RuleSqlListBS.sqlStrToDate(vsl003, vsl.getVsl004()))
						.append(SPACE);
					}else{
						sqlBf.append(SPACE)
						.append(vsl003)
						.append(".")
						.append(vsl.getVsl004())
						.append(SPACE);
					}
					
				}else{
					sqlBf.append(SPACE)
					.append(LEN_COMPARE_PREFIX)
					.append(vsl003)
					.append(".")
					.append(vsl.getVsl004())
					.append(LEN_COMPARE_POSTFIX)
					.append(SPACE);
				}
				
				
				//2.3.3 ƴ�Ӳ��������
				if("1".equals(subCodeValue) && REVERSE_OPERATION.contains(vsl.getVsl004())){
					if(">=".equals(codeName2)){
						codeName2 = "<=";
					}else if(">".equals(codeName2)){
						codeName2 = "<";
					}else if("<=".equals(codeName2)){
						codeName2 = ">=";
					}else if("<".equals(codeName2)){
						codeName2 = ">";
					}
				}
				sqlBf.append(codeName2);
				if(("is null".equals(codeName2)||"is not null".equals(codeName2))  && DBUtil.getDBType()==DBType.MYSQL){
					if("is null".equals(codeName2)){
						sqlBf.append(" or ");
					}else{
						sqlBf.append(" and ");
					}
					
					// create index
					try {
//						sess.createSQLQuery("ALTER TABLE "+vsl.getVsl003().toString()+" add index idx_"+vsl.getVsl004().toString()+" ("+vsl.getVsl004().toString()+")").executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//����������ٳ��ȱȽϵĻ�Ҫƴ�� ���ȱȽ�ǰ׺ LEN_COMPARE_PREFIX ��׺; �������������������͵�תΪ���ڸ�ʽ; ��9-����У�顢8-�������������֤��Ϣ���Ƿ���ڣ���7-�������  ��ƴ��У����
					if("9".equalsIgnoreCase(subCodeValue) || "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
						
					}else if(!"4".equalsIgnoreCase(subCodeValue)){
						//ֻ����������ΪDate,ʵ�����Ͳ���Date���ͣ��� Ӧ������رȽ�����ʱ��תΪDate���ͣ�1-��С�Ƚϡ�5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϡ�6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚϣ�
						if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) && !vsl005.equalsIgnoreCase(vsl005Should) 
								&& ("1".equals(subCodeValue) || "5".equals(subCodeValue) || "6".equals(subCodeValue))){
							sqlBf.append(SPACE)
							.append(RuleSqlListBS.sqlStrToDate(vsl003, vsl.getVsl004()))
							.append(SPACE);
						}else{
							sqlBf.append(SPACE)
							.append(vsl003)
							.append(".")
							.append(vsl.getVsl004())
							.append(SPACE);
						}
						
					}else{
						sqlBf.append(SPACE)
						.append(LEN_COMPARE_PREFIX)
						.append(vsl003)
						.append(".")
						.append(vsl.getVsl004())
						.append(LEN_COMPARE_POSTFIX)
						.append(SPACE);
					}
					
					
					if("is null".equals(codeName2)){
						sqlBf.append(" ='') ");
					}else{
						sqlBf.append(" <>'') ");
					}
					
				}
				System.out.println(sqlBf.toString());
				//2.3.4 ƴ�ӱȶ�ֵ�����ж������������͡�ʵ���������ͽ�������ת��
				
				//1-��С�Ƚϣ�2-����ѡ��
				if("1".equals(subCodeValue) || "2".equals(subCodeValue)){
					
					//  �ȶԹ̶�ֵ
					if(!StringUtil.isEmpty(vsl007)){
						if(vsl005.equalsIgnoreCase("LONGTEXT")||vsl005.equalsIgnoreCase("CLOB")||vsl005.equalsIgnoreCase("varchar2") || vsl005.equalsIgnoreCase("number")){
							if(vsl007.trim().matches("\\d+") && vsl005Should.equalsIgnoreCase("date")){
								sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl007.trim()));
							}else if((vsl005.equalsIgnoreCase("varchar2")||vsl005.equalsIgnoreCase("LONGTEXT") ||vsl005.equalsIgnoreCase("CLOB"))&& "1".equals(subCodeValue)){
								sqlBf.append(SINGLE_QUOTE)
									.append(vsl007.trim())
									.append(SINGLE_QUOTE);
							}else{
								sqlBf.append(vsl007.trim());
							}
							
						}else if(vsl005.equalsIgnoreCase("date")){
							if(vsl007.trim().matches("\\d+")){
								sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl007.trim()));
							}else{
								//this.setMainMessage("���������쳣���쳣��Ϣ�����������͵�У����Ϣ��Ƚϣ��̶�ֵӦΪ6λ��8λ�����֣�У�����ID-"+vsl.getVsl001());
								return "���������쳣���쳣��Ϣ�����������͵�У����Ϣ��Ƚϣ��̶�ֵӦΪ6λ��8λ�����֣�У�����ID-"+vsl.getVsl001();
							}
						}
						
					// �ȶ�ѡ��ֵ
					}else if(!StringUtil.isEmpty(vsl013)){
						if( vsl005.equalsIgnoreCase("number")){
							sqlBf.append(vsl013);
						}else if(vsl005.equalsIgnoreCase("varchar2") && "1".equals(subCodeValue)){
							sqlBf.append(SINGLE_QUOTE)
								.append(vsl013)
								.append(SINGLE_QUOTE);
						}else if(vsl005.equalsIgnoreCase("date")){
							//this.setMainMessage("���������쳣���쳣��Ϣ���ȶ�ѡ��ֵ���������͵�У����Ϣ��ܱȽϣ�У�����ID-"+vsl.getVsl001());
							return "���������쳣���쳣��Ϣ���ȶ�ѡ��ֵ���������͵�У����Ϣ��ܱȽϣ�У�����ID-"+vsl.getVsl001();
						}
				
					// �������ֶ�ֵ
					}else if(!StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008) ){
						if("1".equals(subCodeValue)){
							//
							if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  !"date".equalsIgnoreCase(vsl0052)  ){
								sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl008, vsl009));
							}
//							else if(("varchar2".equalsIgnoreCase(vsl005Should) || "c".equalsIgnoreCase(vsl005Should_qd)) &&"-1".equals(vr.getVrutime())){
//								sqlBf.append(RuleSqlListBS.sqlStrToDate(vsl008, vsl009));
//							}
							else{
								sqlBf.append(vsl008)
									.append(".")
									.append(vsl009);
							}
						}else if("2".equals(subCodeValue)){
							//this.setMainMessage("���������쳣���쳣��Ϣ���ַ������Ƚ���������������������ȶ���Ϣ��Ƚϣ�У�����ID-"+vsl.getVsl001());
							return "���������쳣���쳣��Ϣ���ַ������Ƚ���������������������ȶ���Ϣ��Ƚϣ�У�����ID-"+vsl.getVsl001();
						}
						
					}else{
						//this.setMainMessage("���������쳣���쳣��Ϣ��У�����ȱʧ�ȶ�ֵ��У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ��У�����ȱʧ�ȶ�ֵ��У�����ID-"+vsl.getVsl001();
					}
					//3-���ӿձȽϷ���������
				}else if("3".equals(subCodeValue)){
					
					//4-���ȱȽϣ�
				}else if("4".equals(subCodeValue)){
					//�̶��ȶ�ֵ
					if(!StringUtil.isEmpty(vsl007)){
						if(vsl005.equalsIgnoreCase("LONGTEXT")||vsl005.equalsIgnoreCase("CLOB")||vsl005.equalsIgnoreCase("varchar2") || vsl005.equalsIgnoreCase("number")){
							sqlBf.append(vsl007);
						}else {
							//this.setMainMessage("���������쳣���쳣��Ϣ���������Ͳ��ܱȽϳ��ȣ�У�����ID-"+vsl.getVsl001());
							return "���������쳣���쳣��Ϣ���������Ͳ��ܱȽϳ��ȣ�У�����ID-"+vsl.getVsl001();
						}
					}else{
						//this.setMainMessage("���������쳣���쳣��Ϣ�����ȱȽ�ֻ����̶��ȶ�ֵ�Ƚϣ�У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ�����ȱȽ�ֻ����̶��ȶ�ֵ�Ƚϣ�У�����ID-"+vsl.getVsl001();
					}
					//5-��ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚϣ���������������Ϊnumber �� date ��
				}else if("5".equals(subCodeValue)){
					//  �ȶԹ̶�ֵ
					if(!StringUtil.isEmpty(vsl007) && vsl007.trim().matches("-?\\d+") && !StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008)){
						
						if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  !"date".equalsIgnoreCase(vsl0052) ){
							sqlBf .append(RuleSqlListBS.sqlStrToDateAddMonths(vsl008,vsl009,Long.valueOf(vsl007.trim())));
						}else if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd)) &&  "date".equalsIgnoreCase(vsl0052)  ){
							
							if(DBUtil.getDBType() == DBType.MYSQL){
								sqlBf.append("date_add("+vsl008+"."+vsl009+", INTERVAL "+vsl007.trim()+" MONTH) ");
							}else{
								sqlBf.append("Add_Months("+vsl008+"."+vsl009+", "+vsl007.trim()+")");
							}
							/*�ൺ��Ŀ����*/
						}
//						else if("varchar2".equalsIgnoreCase(vsl005Should)&&"-1".equals(vr.getVrutime())){
//							if(DBUtil.getDBType() == DBType.MYSQL){
//								sqlBf.append("date_add("+vsl008+"."+vsl009+", INTERVAL "+vsl007.trim()+" MONTH) ");
//							}else{
//								sqlBf.append("Add_Months("+vsl008+"."+vsl009+", "+vsl007.trim()+")");
//							}
//							/*�ൺ��Ŀ����*/
//						}
						else{
							//this.setMainMessage("���������쳣���쳣��Ϣ��У��������롾�ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ���ֻ��������͵ģ�У�����ID-"+vsl.getVsl001());
							return "���������쳣���쳣��Ϣ��У��������롾�ȶ���Ϣ��͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ���ֻ��������͵ģ�У�����ID-"+vsl.getVsl001();
						}
						
					}else{
						//this.setMainMessage("���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶ���Ϣ���ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶ���Ϣ���ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001();
					}
					//6-��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚ�
				}else if("6".equals(subCodeValue)){
					//�̶�ֵ
					if(!StringUtil.isEmpty(vsl007) && vsl007.trim().matches("-?\\d+") ){
						
						if(("date".equalsIgnoreCase(vsl005Should) || "t".equalsIgnoreCase(vsl005Should_qd))  ){
							if(DBUtil.getDBType() == DBType.MYSQL){
								sqlBf.append( "date_add(now(), INTERVAL "+vsl007.trim()+" MONTH) ");
							}else{
								sqlBf.append( "Add_Months(sysdate, "+vsl007.trim()+")");
							}
							/*�ൺ��Ŀ����*/
						}
//						else if("varchar2".equalsIgnoreCase(vsl005Should)&&"-1".equals(vr.getVrutime())){
//							if(DBUtil.getDBType() == DBType.MYSQL){
//								sqlBf.append( "date_add(now(), INTERVAL "+vsl007.trim()+" MONTH) ");
//							}
							else{
								sqlBf.append( "Add_Months(sysdate, "+vsl007.trim()+")");
							}
							/*�ൺ��Ŀ����*/
						}else{
							//this.setMainMessage("���������쳣���쳣��Ϣ��У��������롾��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ�������͵ģ�У�����ID-"+vsl.getVsl001());
							return "���������쳣���쳣��Ϣ��У��������롾��ϵͳ���ں͹̶�ֵ�����ֵ�Ƚ�������������ñȶ���Ϣ����������Ϊ�������͵ģ�У�����ID-"+vsl.getVsl001();
						}
						
					}else{
						//this.setMainMessage("���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ��У�������ȱʧ�ȶԹ̶�ֵ��ȶԹ̶�ֵ��Ϊ�����֣�У�����ID-"+vsl.getVsl001();
					}
					//7-������ʽ������������
				}else if("7".equals(subCodeValue)){
					sqlBf.append(vsl003)
					.append(".")
					.append(vsl.getVsl004());
					//8-�������������֤��Ϣ���Ƿ���ڣ�
				}else if("8".equals(subCodeValue)){
					if("80".equalsIgnoreCase(vsl006CodeValue.getCodeValue()) || "81".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlExiest(vsl003,tabPrimary,vsl006CodeValue.getCodeValue()));
					}else{
						//this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001();
					}
				}else if("9".equals(subCodeValue)){
					//���֤У��
					if("95".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlBirthPlace((vsl.getVsl004())));
					}else if("99".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlA0184CompareToA0104(a01TabName));
					}else if("98".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlMoreA0184(a01TabName));
					}else if("97".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlExistPhoto(a01TabName));
					}else if("96".equals(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlA0281Exist(a01TabName));
					}else if("94".equals(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlA0279Exist(a01TabName));
					}else if("90".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.sqlA0195CompareToA0201(a01TabName));
					}else if("91".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.b0114echo(b01TabName));
					}else if("93".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.haveParent());
					}else if("92".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.delete(0,sqlBf.length());
						sqlBf.append(RuleSqlListBS.a0195isThree());
					}else if("901".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.a0160error());
					}else if("902".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.a0837NOONE());
					}else if("903".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.append(RuleSqlListBS.b0150error());
					}
					/*else if("96".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
						sqlBf.delete(0,sqlBf.length());
						sqlBf.append(RuleSqlListBS.a0207CompareToA0148(a01TabName));
					}*/else{
						//this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
						return "���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001();
					}
				}else{
					//this.setMainMessage("���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001());
					return "���������쳣���쳣��Ϣ��У������������δ���ö�Ӧ���У�����ID-"+vsl.getVsl001();
				}
				
				
				//2.3.4�������׺
				
				sqlBf.append(codeName3).append(SPACE);
				
				//������MYSQL��  ��7-������ʽ�������������������ʹ��REGEXP����
				if("7".equals(subCodeValue) && DBUtil.getDBType() == DBType.MYSQL ){
					sqlBf = new StringBuffer(sqlBf.toString().replace("regexp_like(", "").replace(codeName3, " REGEXP BINARY '[a-z]'"));
				}
				
				//2.3.5.����������
				if(!"92".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
				for(Long i=0L;null != vsl.getVsl011() && i<vsl.getVsl011();i++) {
					sqlBf.append(")");
					bracket = false;
				}

				//2.3.6.�����¸�������
				
				if(vsl.getVsl012().equals("1")){
					sqlBf.append(SPACE).append("and");
					flag = false;
				}else if(vsl.getVsl012().equals("2")){
					if(DBUtil.getDBType()==DBType.MYSQL){
						if(!bracket){
							sqlBf.append(SPACE).append("union all").append(SPACE).append(selectSql);
							flag = true;
						}else{
							sqlBf.append(SPACE).append("or"); 
							flag = false;
						}
					}else{
						sqlBf.append(SPACE).append("or");  //oracle 
					}
				}
				}
			}//end if 
			
		}//end foreach verify_sql_list
		String sql = "";
		if(DBUtil.getDBType()==DBType.MYSQL){
			if(flag){
					sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf("union all")) ;					
			}else{
				sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf(SPACE));
			}
		}else{
			sql = sqlBf.toString().substring(0, sqlBf.toString().lastIndexOf(SPACE))+" ) ";
		}
		
//		if("92".equalsIgnoreCase(vsl006CodeValue.getCodeValue())){
//			sql= sqlBf.toString();
//		}
	
		sql = sql.replace(a01TabName +".AGE", "GET_BIRTHDAY("+a01TabName+".a0107, '"+DateUtil.getcurdate()+"')");
		CommonQueryBS.systemOut("SQL========>"+sql);
		//�����г���
//		if (bs==1&&vr.getVrutime()==null&&vr.getVru010()==null) {
//			fristStr = sql.replace(fristStr, "").toUpperCase().replace("_TEMP", "");
//			vr.setVru010(fristStr.substring(0, fristStr.lastIndexOf(")")));
//			bs=0;
//		}
		flag = false;
		//3.У��SQL��Ч�ԣ����趨У����� �Ƿ���Ч�����ã�
		
		String ora_err = RuleSqlListBS.testSql(sql.replace(A01_PARAM, "'1'").replace(B01_PARAM, "'1'"));
		if(!StringUtil.isEmpty(ora_err)){
			errorInfos.append(++errorCount+"��У�����IDΪ��"+vr.getVru001()+"��У�����SQL����쳣��"+ora_err+";\n");
			vr.setVru007("0");//��Ч��� �� 0-��Ч��δ���ã�
		}else{
			vr.setVru007("1");//��Ч��� �� 1-��Ч�����ã�
		}
		
		vr.setVru009(sql);//ƴ����ɵ�sql���
		sess.saveOrUpdate(vr);
		sess.flush();
	
		return errorInfos.toString();
	}

	
	@PageEvent("runAllRule")
	public int allRun() throws RadowException{
		//String vsc001=this.getPageElement("dbClickVsc001").getValue();
		String vsc001=request.getParameter("vsc001");
		if(StringUtil.isEmpty(vsc001)){
			//this.setMainMessage("����˫��У�鷽����ѯ����");
			this.setSelfDefResData("����˫��У�鷽����ѯ����");
			return EventRtnType.XML_SUCCESS;
		}
		String sql="select vru001 from verify_rule where vsc001='"+vsc001+"' and vru007='0'";
		HBSession sess=HBUtil.getHBSession();
		List<Object> list=sess.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			String errorinfo="";
			for(Object obj:list){
				String vru001=obj.toString();
				/* �ൺ��Ŀ */
				String error= "";
				try {
					String vrutime = HBUtil.getValueFromTab("vrutime", "verify_rule","vru001='"+vru001+"'");
					if("-1".equals(vrutime)){
						sess.createSQLQuery("update verify_rule set Vru007 ='1' where vru001='"+vru001+"'").executeUpdate();
					}else{
						error= PJSql(vru001);
					}
				} catch (AppException e) {
					//��ȡ���� -- Ϊ����Ĭ�Ϸ���ʱ������������ƴ�����
					e.printStackTrace();
				}
				/* �ൺ��Ŀ */
//				String error= PJSql(vru001);
				errorinfo+=error;
			}
			if(StringUtil.isEmpty(errorinfo)){
				this.setSelfDefResData("�������");
			}else{
				this.setSelfDefResData("�������,�д�����Ϣ:"+errorinfo);
			}
		}else{
			this.setSelfDefResData("��ǰѡ�з���û��δ���õ�У�����");
			return EventRtnType.XML_SUCCESS;
		}
		//this.setNextEventName("VerifyRuleGrid.dogridquery");
		return EventRtnType.XML_SUCCESS;
	}
}
