package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.Query;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.VerifyProcess;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.ImpmodelThread;

public class OrgDataVerify2PageModel extends PageModel{
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	public static String jiaoyan = "0";//0�Ǵ� 1�ǵ��
	
	@Override
	public int doInit() throws RadowException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
//		
		HBSession sess=HBUtil.getHBSession();
		String params=this.getPageElement("subWinIdBussessId").getValue();
		String bsType="";
		String b0111OrimpID="";
		if(StringUtil.isEmpty(params)){
			String cueUserid=SysUtil.getCacheCurrentUser().getId();
			if(cueUserid.equals("4028810f5f2aed67015f2aefeeee0002")){
				bsType = "4"; //ҵ������ ��bsType���� 1-����У�飨�����¼���������2-����У�� 3��ԱУ��//4����У��
				b0111OrimpID="001.001";
			}else{
				String b0111sql="";
				if(DBType.ORACLE==DBUtil.getDBType()){
					b0111sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc) where ROWNUM =1";
				}else if(DBType.MYSQL==DBUtil.getDBType()){
					b0111sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
				}
				Object b01=sess.createSQLQuery(b0111sql).uniqueResult();
				if(b01==null||"".equals(b01)){
					this.setMainMessage("û�����û���Ȩ��");
					this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(true);
					//this.createPageElement("imgVerify", ElementType.BUTTON, false).setDisabled(true);
					return EventRtnType.FAILD;
				}else{
					bsType = "4"; //ҵ������ ��bsType���� 1-����У�飨�����¼���������2-����У�� 3��ԱУ��//4����У��
					b0111OrimpID=b01.toString();
				}
			}
			

		}else{
			bsType=params.split("@")[0];
			b0111OrimpID=params.split("@")[1];
		}
		this.getPageElement("bsType").setValue(bsType);
		this.getPageElement("b0111OrimpID").setValue(b0111OrimpID);
		String userid=SysUtil.getCacheCurrentUser().getId();
		String vscsql="select vsc001,vsc002 from verify_scheme where vsc003='1' and (vsc007 in ('1','2') or vsc005='"+userid+"') order by vsc007";
		List<Object[]> list=sess.createSQLQuery(vscsql).list();
		HashMap<String,String> map= new HashMap<String,String>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				String vsc001=obj[0].toString();
				String vsc002=obj[1].toString();
				map.put(vsc001, vsc002);
			}
			vscsql = list.get(0)[0].toString();
		}
		((Combo)this.getPageElement("vsc001")).setValueListForSelect(map);
		String vsc001 = "0";
		if(list!=null&&list.size()>0){
			vsc001 = list.get(0)[0].toString();
			this.getPageElement("vsc001").setValue(vsc001);
			vsc001 = countCheckTJ(userid, vsc001);
		}
		this.setNextEventName("ruleGrid.dogridquery");
		
		
		/*if("4".equals(bsType)){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
			this.setNextEventName("errorDetailGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab3');");
			this.setNextEventName("errorDetailGrid2.dogridquery");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab0');");
			
		}*/

		this.getExecuteSG().addExecuteCode(vsc001 + 
				 "odin.ext.getCmp('tab').hideTabStripItem('tab2') ;");
		//this.setNextEventName("errorGrid9.dogridquery");
		//this.setNextEventName("errorGrid.dogridquery");
		//this.setNextEventName("errorDetailGrid.dogridquery");
		return 0;
	}

	private String countCheckTJ(String userid, String vsc001) throws AppException {
		vsc001 = HBUtil.getValueFromTab("count(1)", "CHECK_TJ", "USERID='"+userid+"' and CHECK_VSC001='"+vsc001+"'");
		if("0".equals(vsc001)){
			return "odin.ext.getCmp('DownloadVerificationReport').hide() ;";
		}else{
			String vsc002 = HBUtil.getValueFromTab("count(1)", "aa01", "aaa001='CHECK_WORD' and aaa005='ON'");
			if("0".equals(vsc002)){
				return "odin.ext.getCmp('DownloadVerificationReport').hide() ;";
			}else{
				return "odin.ext.getCmp('DownloadVerificationReport').show() ;"
						+ "document.getElementById('canDownloadVerificationReport').value='"+vsc001+"';";
			}
		}
	}
	
	/**
	 * ����������� �򿪵�����ȷ���Ƿ���������
	 * @return
	 * @throws FANY
	 */
	@PageEvent("ArrangeBtn2.onclick")
	@GridDataRange
	public int ArrangeBtn2()throws RadowException{
		if(DBType.MYSQL==DBUtil.getDBType()){
			
			DBUtil.updateData();//���¿��ַ���Ϊ��
		}
		CommonQueryBS.systemOut("----------��ʼ��������------"+ DateUtil.getTime());
		if(DBType.MYSQL==DBUtil.getDBType()){
			CommonQueryBS.systemOut("--���¿��ַ���Ϊ��start------"+ DateUtil.getTime());
			DBUtil.updateData();//���¿��ַ���Ϊ��
			CommonQueryBS.systemOut("--���¿��ַ���Ϊ��end------"+ DateUtil.getTime());
		}
		CommonQueryBS.systemOut("--�������пյ�2ѡ1��ѡ��Ϊ��start------"+ DateUtil.getTime());
		DBUtil.updateOneYESorNO();//�������пյ�2ѡ1��ѡ��Ϊ��
		CommonQueryBS.systemOut("--�������пյ�2ѡ1��ѡ��Ϊ��end;ȥ���д��ո���ֶ�,ѧλ����ȥ��ѧλ������,�뵳ʱ��Ӣ�����Ÿ�����start------"+ DateUtil.getTime());
		DBUtil.deleteNull();//ȥ���д��ո���ֶ�,ѧλ����ȥ��ѧλ������,�뵳ʱ��Ӣ�����Ÿ�����
		CommonQueryBS.systemOut("--ȥ���д��ո���ֶ�,ѧλ����ȥ��ѧλ������,�뵳ʱ��Ӣ�����Ÿ�����end;����ƴ��start------"+ DateUtil.getTime());
		DBUtil.updatepinyin();//����ƴ��
		CommonQueryBS.systemOut("--����ƴ��end;���»�����Ϣstart------"+ DateUtil.getTime());
		DBUtil.updateB01Data();//����B01��Ϣ
		/*CommonQueryBS.systemOut("--���»�����Ϣend;��ʽ������start------"+ DateUtil.getTime());
		try {
			DBUtil.formatA1701();
			CommonQueryBS.systemOut("--��ʽ������end------"+ DateUtil.getTime());
		} catch (SQLException e) {
			CommonQueryBS.systemOut("��ʽ������ʧ�ܣ�");
			e.printStackTrace();
		}//��ʽ������*/
		CommonQueryBS.systemOut("----------������������------"+ DateUtil.getTime());
		this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����������ɣ�', null,200);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * У��ǰ��֤
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("btnSave.onclick")
	public int saveBefore() throws RadowException{
		//DeletePersonTimer.exec();
		String bsType = this.getPageElement("bsType").getValue();
		String params=this.getPageElement("subWinIdBussessId").getValue();
		String b0111="";
		if(!StringUtil.isEmpty(params)){
			b0111=this.getPageElement("b0111OrimpID").getValue();
		}else{
			b0111=this.getPageElement("searchDeptBtn").getValue();
			if(StringUtil.isEmpty(b0111)){
				b0111=this.getPageElement("b0111OrimpID").getValue();
			}
		}
		String vsc001=this.getPageElement("vsc001").getValue();
		if(StringUtil.isEmpty(vsc001)){
			this.setMainMessage("��ѡ��У�鷽��");
			return EventRtnType.FAILD;
		}
		this.getPageElement("b0111OrimpID").setValue(b0111);
		String userid=SysUtil.getCacheCurrentUser().getId();
		HBSession sess = HBUtil.getHBSession();
		//long startTime1 = System.currentTimeMillis();    //��ȡ��ʼʱ��
		@SuppressWarnings("unchecked")
		List<VerifyProcess> list =  sess.createQuery("from VerifyProcess where batchNum ='"+b0111+"' and bsType = '"+bsType+"' and userid='"+userid+"' and resultFlag='3'").list();
		//long endTime1 = System.currentTimeMillis();    //��ȡ����ʱ��

		//System.out.println("У��ǰ��֤ʱ�䣺" + (endTime1 - startTime1) + "ms");    //�����������ʱ��
		if(list!=null && list.size()>0){//3-����У�������
			this.setMainMessage("�����������ڴ����ݽ���У�飬�Ƿ����У��?");
			this.setMessageType(EventMessageType.CONFIRM);
			this.addNextEvent(NextEventValue.YES,"verifyData");
		}else{
			//this.setNextEventName("verifyData");
			this.getExecuteSG().addExecuteCode("radow.doEvent('verifyData')");
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * У��
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("verifyData")
	@Transaction
	public int save() throws RadowException, AppException {
		this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(true);
		String a0163 = this.getPageElement("a0163").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String bsType = this.getPageElement("bsType").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		String peopleid = this.getPageElement("peopleid").getValue();
		PageElement pe = this.getPageElement("ruleGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String ruleids="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object checked=map.get("checked");
			if(checked!=null&&checked.toString().equals("true")){
				Object vru001=map.get("vru001");
				ruleids+=vru001+",";
			}
		}
		if(StringUtil.isEmpty(ruleids)){
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				ruleids+=map.get("vru001")+",";
			}
		}
		if(!StringUtil.isEmpty(ruleids)){
			ruleids=ruleids.substring(0, ruleids.length()-1);
		}
		HBSession sess = HBUtil.getHBSession();
//<----------------------------------------------------------------------------------------------------------------------------------------------------------------->
		if("2".equals(bsType)){
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, b0111);
			if(imp.getImpstutas().equals("2")){
				this.setMainMessage("�����ѵ��룬����У�顣");
				this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
				return EventRtnType.NORMAL_SUCCESS;
			} else if(imp.getImpstutas().equals("4")){
				this.setMainMessage("���ݽ����У�����У�顣");
				this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
				return EventRtnType.NORMAL_SUCCESS;
			} else if(imp.getImpstutas().equals("3")){
				this.setMainMessage("�����Ѵ�أ�����У�顣");
				this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
//<----------------------------------------------------------------------------------------------------------------------------------------------------------------->
		
		String uuid = UUID.randomUUID().toString();
		if (!StringUtil.isEmpty(ruleids) && !StringUtil.isEmpty(b0111) && !StringUtil.isEmpty(bsType)) {
			sess.createSQLQuery("delete from  Verify_Process where batch_Num ='"+b0111+"' and userid='"+userid+"' and bs_Type = '"+bsType+"'").executeUpdate();
			sess.flush();
			VerifyProcess vp =  new VerifyProcess();
			vp.setProcessId(uuid);
			vp.setBsType(bsType);//ҵ������
			vp.setResultFlag(3L);//������
			vp.setBatchNum(b0111);//���κ�
			vp.setuserId(userid);
			sess.save(vp);
			sess.flush();
			this.getExecuteSG().addExecuteCode("verify('"+vp.getProcessId()+"','"+b0111+"','"+ruleids+"','"+bsType+"','"+a0163+"')");
			
		} else {
			throw new AppException("��֯��Ϣ��У�鷽����ҵ������Ϊ�գ����ܿ�ʼУ�飡");
		}
		this.getPageElement("errorDetailGrid").setValueList(new ArrayList());//���ҳ���쳣��Ϣ
		this.getPageElement("errorDetailGrid2").setValueList(new ArrayList());
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("showgrid")
	public int showgird(String vpid) throws RadowException, AppException{
		String  bsType = this.getPageElement("bsType").getValue();
		VerifyProcess vp=(VerifyProcess) HBUtil.getHBSession().get(VerifyProcess.class, vpid);
		this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
		String userid=SysUtil.getCacheCurrentUser().getId();
		String sql=" select count(1) from special_verify where sv004='"+bsType+"' and sv005='"+userid+"'";
		Object num=HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
		int n=Integer.parseInt(num.toString());
		if(n>0){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').unhideTabStripItem('tab2');");
			this.setNextEventName("errorRuleGrid.dogridquery");
		}
		if("4".equals(bsType)){
			this.setMainMessage(vp.getProcessMsg());
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
			this.setNextEventName("errorDetailGrid.dogridquery");
			this.setNextEventName("errorDetailGrid2.dogridquery");
			
		}

		this.getExecuteSG().addExecuteCode(countCheckTJ(userid, this.getPageElement("vsc001").getValue()));
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("errorRuleGrid.dogridquery")
	public int doerrorRuleGridquery(int start,int limit) throws RadowException{
		String  bsType = this.getPageElement("bsType").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String sql="select sv001,sv002,sv003,sv004,sv005,sv006 from special_verify where sv004='"+bsType+"' and sv005='"+userid+"'";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ��ѯ������
	 * @param processId
	 * @return
	 */
	@PageEvent("queryBar")
	@AutoNoMask
	@Transaction
	public int queryBar(String processIdPageVal){
		String bsType = "";
		String b0111 = "";
		try {
			 bsType = this.getPageElement("bsType").getValue();
			 b0111 = this.getPageElement("b0111OrimpID").getValue();
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HBSession sess = HBUtil.getHBSession();
		//Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, b0111);
		if(StringUtil.isEmpty(processIdPageVal)){
			this.setMainMessage("��ѯ���Ȼ�ȡ�����쳣��");
			this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
			//this.createPageElement("impconfirmBtn", ElementType.BUTTON, false).setDisabled(false);
			//this.createPageElement("rejectBtn", ElementType.BUTTON, false).setDisabled(false);
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] params = processIdPageVal.split("@");
		String processId = params[0];
		String pageValStr = StringUtil.isEmpty(params[1])?"0":params[1];
		long pageVal = Long.parseLong(pageValStr);
		
		sess.getSession().clear();
		VerifyProcess vp = (VerifyProcess) sess.get(VerifyProcess.class, processId);

		if(vp!=null   ){
			if(-1L!=vp.getResultFlag()  ){
				
				long currentNum = vp.getCurrentNum()==null?1L:vp.getCurrentNum();
				long totalNum = vp.getTotalNum()==null?100L:vp.getTotalNum();
				float num= currentNum/(float)totalNum;   
				DecimalFormat df = new DecimalFormat("0");//��ʽ��С��   
				String val = df.format(num*100);
				CommonQueryBS.systemOut("total-"+totalNum+",currentNum-"+currentNum+",num-"+num+",val-"+val);
				if(3L==vp.getResultFlag()||pageVal < Long.parseLong(val)){
					this.getExecuteSG().addExecuteCode("progress("+val+",'"+processId+"')");
				}else{
					this.setMainMessage(vp.getProcessMsg());
					this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
					//this.createPageElement("impconfirmBtn", ElementType.BUTTON, false).setDisabled(false);
					//this.createPageElement("rejectBtn", ElementType.BUTTON, false).setDisabled(false);
					if("2".equals(bsType)){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
						//this.setNextEventName("errorGrid9.dogridquery");
						this.setNextEventName("errorDetailGrid2.dogridquery");
						this.setNextEventName("errorDetailGrid.dogridquery");
					}
					if("3".equals(bsType)){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab3');");
						this.setNextEventName("errorDetailGrid2.dogridquery");
					}
					if("1".equals(bsType)){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
						this.setNextEventName("errorDetailGrid.dogridquery");
					}
					if("4".equals(bsType)){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
						this.setNextEventName("errorDetailGrid.dogridquery");
						this.setNextEventName("errorDetailGrid2.dogridquery");
						
					}
					//this.setNextEventName("errorGrid.dogridquery");
					//this.setNextEventName("errorDetailGrid.dogridquery");
				}
				if(vp.getResultFlag() == 0 || vp.getResultFlag() == 1){
					//CommonQueryBS.systemOut("vp.getResultFlag()----------------"+vp.getResultFlag());
					this.getExecuteSG().addExecuteCode("setprogressLabelnull()");
				}

			}else{
				this.setMainMessage(vp.getProcessMsg());
				this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
				//this.createPageElement("impconfirmBtn", ElementType.BUTTON, false).setDisabled(false);
				//this.createPageElement("rejectBtn", ElementType.BUTTON, false).setDisabled(false);
				if("2".equals(bsType)){
					//this.setNextEventName("errorGrid9.dogridquery");
					this.setNextEventName("errorDetailGrid2.dogridquery");
					this.setNextEventName("errorDetailGrid.dogridquery");
				}
				if("3".equals(bsType)){
					this.setNextEventName("errorDetailGrid2.dogridquery");
				}
				if("1".equals(bsType)){
					this.setNextEventName("errorDetailGrid.dogridquery");
				}
				if("4".equals(bsType)){
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
					this.setNextEventName("errorDetailGrid.dogridquery");
					this.setNextEventName("errorDetailGrid2.dogridquery");
					
				}
				//this.setNextEventName("errorGrid.dogridquery");
				
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����У����
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("btnSaveUpdated.onclick")
	@Transaction
	public int saveUpdated() throws RadowException, AppException {
		String bsType = this.getPageElement("bsType").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		String updated = this.getPageElement("updated").getValue();
		if(StringUtil.isEmpty(updated)){
			throw new AppException("У����Ϊ�գ�");
		}
		if(!"1".equals(bsType)){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if ( !StringUtil.isEmpty(b0111)) {
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery("update b01 a set a.updated = '"+updated+"' where b0111 like '"+b0111+"%'").executeUpdate();
			B01 b01 = (B01) sess.get(B01.class, b0111);
			try {
				new LogUtil().createLog("643", "B01", b0111, b01.getB0101(), null,new ArrayList() );
			} catch (Exception e) {
				e.printStackTrace();
			}
//			@SuppressWarnings("unchecked")
//			List<B01> list = sess.createQuery("from B01 a where b0111 like '"+b0111+"%'").list();
//			
//			for(B01 b01 :list){
//				B01DTO b01Dto = new B01DTO();
//				BeanUtil.copy(b01, b01Dto);
//				b01.setUpdated(updated);
//				sess.update(b01);
//				//��¼��־
//				try {
//						new LogUtil().createLog("643", "B01", b01.getB0111(), b01.getB0101(), null,Map2Temp.getLogInfo(b01Dto,b01) );
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
				
		} else {
			this.setMainMessage("��֯��ϢΪ�գ����ܱ���У���ǣ�");
		}
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˫��������Ϣ����ʾ��ش�����ϸ��Ϣ
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("errorGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorGrid() throws RadowException{
		
		Grid grid = (Grid)this.getPageElement("errorGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		String vel002 = "";
		String vel003 = "";
		//1.ѡ����
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if(cueRowIndex==i){
				vel002 = map.get("vel002").toString();
				vel003 = map.get("vel003").toString();
				break;
			}
		}
		grid.selectRow(cueRowIndex);
		
		//2.��ѯ���ж�Ӧ��У�����
		this.getPageElement("dbClickvel002").setValue(vel002);//ѡ����
		this.getPageElement("dbClickvel003").setValue(vel003);//ѡ����
		jiaoyan = "1";
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �鿴ȫ����������
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("allErrorDetail.onclick")
	public int allErrorDetail() throws RadowException{
		this.getPageElement("dbClickvel002").setValue("");
		this.getPageElement("dbClickvel003").setValue("");
		jiaoyan = "1";
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ������Ϣ(���ǰ�쳣�� vru003 = '9')--���֤�����ظ�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorGrid9.dogridquery")
	@NoRequiredValidate
	public int doErrorGrid9Query(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
		
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("��ȡ����Ϊ�գ�");
		}
		
		Imprecord impRecord = null;
		String imptemptable = "_temp";
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
			imptemptable = impRecord.getImptemptable();
			if("3".equals(impRecord.getImpstutas())){//�����Ѵ��
				bsType = "1";
			}
		}
		
		String a01Name = (!"2".equals(bsType))?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01"+imptemptable;
		String b01Name = (!"2".equals(bsType))?"B01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"B01":"B01"+imptemptable;
		
		
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("SELECT  distinct 1, A.A0101 Vel002_name, A.A0000 Vel002,A.A0184 A0184")
		.append(" FROM A01"+impRecord.getImptemptable()+" A,")
		.append(" (SELECT A01.A0000, A01.A0101, A01.A0184")
		.append(" FROM A01, A02")
		.append(" WHERE A01.A0000 = A02.A0000")
		.append(" AND A02.A0201B LIKE '"+impRecord.getImpdeptid()+"%'")
		.append(" GROUP BY A01.A0000, A01.A0101, A01.A0184) B")
		.append(" WHERE A.A0184 = B.A0184");
		/*if(DBType.MYSQL ==DBUtil.getDBType()){
			sqlbf.append("Select  Vel003    ,           ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select ifnull(A0101, '')")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              limit 0,1)   	    ")
			.append("         Else                      ")
			.append("          (Select ifnull(B0101, '')")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              limit 0,1)    	    ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("          (Select ifnull(A0184, '')")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              limit 0,1)  A0184    ")
			.append("  From Verify_Error_List a         ")
			.append(" Where 1 = 1                       ");
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
			sqlbf.append("Select    Vel003 ,            ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select Nvl(A0101, '')   ")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("         Else                      ")
			.append("          (Select Nvl(B0101, '')   ")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("          (Select Nvl(A0184, '')   ")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("         And Rownum = 1)  A0184    ")
			.append("  From Verify_Error_List a         ")
			.append(" Where 1 = 1                       ");
		}
		if("2".equals(bsType)){//����У��
			sqlbf.append(" and   a.vel004 ='"+impRecord.getImprecordid()+"'    AND a.vel005 = '"+bsType+"'   AND vru003 = '9'");
		}else if("1".equals(bsType)){
			sqlbf.append(" and   a.vel004 like '"+b0111+"%'    AND a.vel005 = '"+bsType+"'   AND vru003 = '9'");
		}else{
			sqlbf.append(" AND a.vel005 = '"+bsType+"'   AND vru003 = '9'");
		}*/
		//sqlbf.append(" and   a.vel004 ='"+b0111+"'    AND a.vel005 = '"+bsType+"'   AND vru003 = '9'");
		//sqlbf.append("   AND a.vel005 = '"+bsType+"'   AND vru003 = '9'");
		sqlbf.append(" order by A.A0101");
		CommonQueryBS.systemOut("--->���֤�����ظ�>>"+sqlbf.toString());
		this.pageQuery(sqlbf.toString(),"SQL", start, limit);
		w.stop();
		CommonQueryBS.systemOut("ִ��ʱ�����֤�����ظ���---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ��ѯ������Ϣ(���������ǰ�쳣�� vru003 != '9')
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorGrid.dogridquery")
	@NoRequiredValidate
	public int doErrorGridQuery(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
    	
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("��ȡ����Ϊ�գ�");
		}

		Imprecord impRecord = null;
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
			if("3".equals(impRecord.getImpstutas())){//�����Ѵ�� ֱ�Ӳ���ʽ��
				bsType = "1";
			}
		}
		
		
		String pel = "";
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		pel = "select vel002,ETYPE vel003,A0101 vel002_name,errnum count_nums from errcount where b0111 like '"+b0111+"%' order by vel002";
		 CommonQueryBS.systemOut(pel);
		this.pageQuery(pel,"SQL", start, limit);
		w.stop();
		CommonQueryBS.systemOut("ִ��ʱ��2222��---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ��ѯ������ϸ��Ϣ(���������ǰ�쳣�� vru003 != '9')
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorDetailGrid.dogridquery")
	@NoRequiredValidate      
	public int doErrorDetailGridQuery(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
		StringBuffer sqlbf = checkSelectB01();
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		this.request.getSession().setAttribute("v3sql", sqlbf.toString());
		
		w.stop();
		CommonQueryBS.systemOut("ִ�����ʱ�䣺---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
	// ��ȡ zxw 2019-03-09 ��ѯ������Ϣ
	private StringBuffer checkSelectB01() throws RadowException {
		String userid=SysUtil.getCacheCurrentUser().getId();
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("��ȡ����Ϊ�գ�");
		}
		String peopleid=this.getPageElement("peopleid").getValue();
		
		Imprecord impRecord = null;
		String imptemptable = "_temp";
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
			imptemptable = impRecord.getImptemptable();
			if("3".equals(impRecord.getImpstutas())){//�����Ѵ��
				bsType = "1";
			}
		}
		
		String a01Name = (!"2".equals(bsType))?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01" +imptemptable;
		String b01Name = (!"2".equals(bsType))?"B01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"B01":"B01" +imptemptable;
		
		//����������û�������ѯ��Ա���������� ---modifyed by lizs likk ��
		String a02Name = "A01".equals(a01Name)?"A02":"A02" +imptemptable;
	
//		String dbClickvel002 = this.getPageElement("dbClickvel002").getValue();
//		String dbClickvel003 = this.getPageElement("dbClickvel003").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
				sqlbf.append("/*"+Math.random()+"*/Select Vel001, ")

			


				.append(" (Select case when b1.b0194 = '2' then (select ifnull(b2.b0101, '') from "+b01Name+" b2 where b1.b0121 = b2.b0111) ")
				.append(" else ifnull(b1.B0101, '') end From "+b01Name+" b1 Where b1.B0111 = Vel002 limit 0,1) vel002_b, ")
				.append("( Select ifnull(B0101, '')  From "+b01Name+"  Where B0111 = Vel002  limit 0,1) vel002_name, ")
				.append(" vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a Where 1 = 1   and vel003='2' ");
				
			
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
				sqlbf.append("/*"+Math.random()+"*/Select Vel001, ")

			


				.append(" (Select case when b1.b0194 = '2' then (select nvl(b2.b0101, '') from "+b01Name+" b2 where b1.b0121 = b2.b0111) ")
				.append(" else nvl(b1.B0101, '') end From "+b01Name+" b1 Where b1.B0111 = Vel002 And Rownum = 1) vel002_b, ")
				.append("( Select Nvl(B0101, '')  From "+b01Name+"  Where B0111 = Vel002  And Rownum = 1) vel002_name, ")
				.append(" vel002,vel003,vel004,vel005,vel006,vel010 From Verify_Error_List a Where 1 = 1   and vel003='2' ");
					
		}
		if("2".equals(bsType)){//����У��
			sqlbf.append(" and   a.vel004 ='"+impRecord.getImprecordid()+"'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"' ");
		}else if("1".equals(bsType)){
			sqlbf.append(" and   a.vel004 like '"+b0111+"%'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"'  ");
		}else if("3".equals(bsType)){
			sqlbf.append(" and   a.vel004 in ("+peopleid+")  AND a.vel005 = '"+bsType+"'   and vel010='"+userid+"'");
		}else if("4".equals(bsType)){
			sqlbf.append(" and   a.vel004 like '"+b0111+"%'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"'  ");
		}else{
			sqlbf.append(" AND a.vel005 = '"+bsType+"'   and vel010='"+userid+"'");
		}
		
		sqlbf.append(" order by Vel002");
		return sqlbf;
	}
	
	/**
	 * ��ѯ������ϸ��Ϣ(���������ǰ�쳣�� vru003 != '9')
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorDetailGrid2.dogridquery")
	@NoRequiredValidate      
	public int doErrorDetailGrid2Query(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
    	StringBuffer sqlbf = checkSelectA01();
		this.request.getSession().setAttribute("v2sql", sqlbf.toString());
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		
		w.stop();
		CommonQueryBS.systemOut("ִ�����ʱ�䣺---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}

	// ��ȡ zxw 2019-03-09 ��ѯУ����Ա��Ϣ
	/**
	*====================================================================================================
	* ��������:TODO<br>
	* ������������:2019��12��17��<br>
	* ����������Ա:lixl<br>
	* ��������޸�����:2019��12��17��<br>
	* ��������޸���Ա:lixl<br>
	* ������������:��ѯУ����Ա��Ϣ������У��У�ˡ�ȫ����������������<br>
	*====================================================================================================
	*/
	private StringBuffer checkSelectA01() throws RadowException {
		String userid=SysUtil.getCacheCurrentUser().getId();
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		String b0111=this.getPageElement("searchDeptBtn").getValue();
		if(StringUtil.isEmpty(b0111)){
			b0111=this.getPageElement("b0111OrimpID").getValue();
		}
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("��ȡ����Ϊ�գ�");
		}
		
		String peopleid=this.getPageElement("peopleid").getValue();
		
		Imprecord impRecord = null;
		String imptemptable = "_temp";
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
			imptemptable = impRecord.getImptemptable();
			if("3".equals(impRecord.getImpstutas())){//�����Ѵ��
				bsType = "1";
			}
		}
		
		String a01Name = (!"2".equals(bsType))?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01" +imptemptable;
		String b01Name = (!"2".equals(bsType))?"B01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"B01":"B01" +imptemptable;
		
		//����������û�������ѯ��Ա���������� ---modifyed by lizs likk ��
		String a02Name = "A01".equals(a01Name)?"A02":"A02" +imptemptable;
		
		String dbClickvel002 = this.getPageElement("dbClickvel002").getValue();
		String dbClickvel003 = this.getPageElement("dbClickvel003").getValue();
		//String b0111 = this.getPageElement("b0111OrimpID").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
			sqlbf.append("/*"+Math.random()+"*/select vel001,(Select ifnull(A0101, '') "
					+ "from "+a01Name+"  Where A0000 = Vel002 limit 0,1) Vel002_name,(Select ifnull(A0184, '') "
							+ "from "+a01Name+"  Where A0000 = Vel002 limit 0,1) vel002_sfz,(Select ifnull(A0192A, '') "
									+ "from "+a01Name+"  Where A0000 = Vel002 limit 0,1) vel002_zw,vel002,vel003,vel004,vel005,vel006,vel010 "
											+ "From Verify_Error_List a ,a01 a01 where 1=1 and a.VEL002 = a01.A0000 and a01.STATUS <> '4' and vel003='1' ");			
			
			
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
			sqlbf.append("/*"+Math.random()+"*/select vel001,(Select Nvl(A0101, '') "
					+ "from "+a01Name+"  Where A0000 = Vel002 And Rownum = 1) Vel002_name,(Select Nvl(A0184, '') "
							+ "from "+a01Name+"  Where A0000 = Vel002 And Rownum = 1) vel002_sfz,(Select Nvl(A0192A, '') "
									+ "from "+a01Name+"  Where A0000 = Vel002 And Rownum = 1) vel002_zw,vel002,vel003,vel004,vel005,vel006,vel010 "
									+ "From Verify_Error_List a ,a01 a01 where 1=1 and a.VEL002 = a01.A0000 and a01.STATUS <> '4' and vel003='1' ");			
				
		}
		if("2".equals(bsType)){//����У��
			sqlbf.append(" and   a.vel004 ='"+impRecord.getImprecordid()+"'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"'  ");
		}else if("1".equals(bsType)){
			sqlbf.append(" and   a.vel004 like '"+b0111+"%'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"'  ");
		}else if("3".equals(bsType)){
			sqlbf.append(" and   a.vel004 in ("+peopleid+")  AND a.vel005 = '"+bsType+"'   and vel010='"+userid+"'");
		}else if("4".equals(bsType)){
			sqlbf.append(" and   a.vel004 = '"+b0111+"'    AND a.vel005 = '"+bsType+"' and vel010='"+userid+"'  ");
		}else{
			sqlbf.append(" AND a.vel005 = '"+bsType+"'   AND vru003 = '9' and vel010='"+userid+"'");
		}
		//sqlbf.append(" and   a.vel004 ='"+b0111+"'    AND a.vel005 = '"+bsType+"' and vel009 is not null  AND vru003 != '9'");
		//sqlbf.append(" AND a.vel005 = '"+bsType+"' and vel009 is not null  AND vru003 != '9'");
		/*if(!StringUtil.isEmpty(dbClickvel002)&&!StringUtil.isEmpty(dbClickvel003)){
			sqlbf.append(" and   a.vel002 ='"+dbClickvel002+"'    AND a.vel003 = '"+dbClickvel003+"'");
		}*/
		String sort= request.getParameter("sort");//Ҫ���������--���趨�壬ext�Զ���
		String dir= request.getParameter("dir");//Ҫ����ķ�ʽ--���趨�壬ext�Զ���
		if(StringUtil.isEmpty(sort)||StringUtil.isEmpty(dir)){
			sqlbf.append(" order by vel004 ");
		}else{
			sqlbf.append(" order by "+sort+" "+dir+"");
		}
		return sqlbf;
	}
	
	/**
	 * ˫��������ϸ��Ϣ����ת�������
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("errorDetailGrid2.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid2() throws RadowException, AppException{
		HBSession sess=HBUtil.getHBSession();
		Grid grid = (Grid)this.getPageElement("errorDetailGrid2");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String a0000=map.get("vel002").toString();
		if(StringUtil.isEmpty(a0000)){
			throw new AppException("�޷���ȡ������������ͣ�");
		}
		String vel006 = map.get("vel006").toString();
		String text = vel006.split(">")[0].trim();
		String title = text.replace(":", "��").split("��")[1].trim();
		String sql = "select functionid from smt_function where title = '"+title+"' and parent = '8280818b722c20fe01722c484cb40006' ";
		List list = sess.createSQLQuery(sql).list();
		String functionid = (String) (list.size()==0?"8ab6d7b07283e1f2017283e39e9c0003":list.get(0));
		A01 ac01 = (A01) sess.get(A01.class, a0000);
		if (null != ac01) {
			this.request.getSession().setAttribute("personIdSet", null);
			this.request.getSession().setAttribute("vsql", null);
			// this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','" + request.getContextPath() + "/rmb/rmb.jsp?a0000=" + a0000 + "','��Ա��Ϣ�޸�',851,630,null," + "{a0000:'" + a0000 + "',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");
			this.getExecuteSG().addExecuteCode("personInfoWin('" + a0000 + "@"+functionid+"');");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			throw new AppException("����Ա����ϵͳ�У�");
		}
		//����У��(ҵ������Ϊ2)��δ������ʽ�⣬�򷵻�
		/*String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		Imprecord impRecord = null;
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		}
		
		String a01Name = "1".equals(bsType)?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01_temp";
		
		if(StringUtil.isEmpty(bsType) || !"A01".equals(a01Name)){
			this.setMainMessage("���������֮��˫����Ա�������飬������Ա��Ϣ�޸Ľ��棡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Grid grid = (Grid)this.getPageElement("errorDetailGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		String vel002 = "";	//����ID
		String vel003 = "";	//�������ͣ�1-��Ա��2-������
		String vel004 = "";	//����ID
		String vel005 = "";	//ҵ������
		String vel007_code = "";//��Ϣ������
		String vel008_code = "";//��Ϣ�� 
		//����list���������Դ�ŷ������Ϣ������
		List firTab = new ArrayList();//��Ӧ��Աҳ���һ��tabҳ����Ϣ��
		List secTab = new ArrayList();//��Ӧ��Աҳ��ڶ���tabҳ����Ϣ��
		List thiTab = new ArrayList();//��Ӧ��Աҳ�������tabҳ����Ϣ��
		//�����ڵ�һ��tabҳ����Ϣ�������ŵ�firTab��
		firTab.add("A57");
		firTab.add("A08");
		firTab.add("B01");
		firTab.add("A06");
		firTab.add("A02");
		firTab.add("A01");
		//�����ڵڶ���tabҳ����Ϣ�������ŵ�secTab��
		secTab.add("A36");
		secTab.add("A15");
		secTab.add("A14");
		//�����ڵ�����tabҳ����Ϣ�������ŵ�thiTab��
		thiTab.add("A37");
		thiTab.add("A53");
		thiTab.add("A31");
		thiTab.add("A30");
		thiTab.add("A29");
		thiTab.add("A11");
		
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if(cueRowIndex==i){
				vel002 = map.get("vel002").toString();
				vel003 = map.get("vel003").toString();
				vel004 = map.get("vel004").toString();
				vel005 = map.get("vel005").toString();
				vel007_code = map.get("vel007_code").toString();//��ȡ��Ϣ������
				vel008_code = map.get("vel008_code").toString();//��ȡ��Ϣ��
				break;
			}
		}
		grid.selectRow(cueRowIndex);
		
		if(StringUtil.isEmpty(vel002)){
			throw new AppException("�޷���ȡ��������ID��");
		}
		
		if(!StringUtil.isEmpty(vel003)){
			if("1".equals(vel003)){
				String tabIndex = "";//��Ϣ������tabҳ
				//�ж�ѡ�е���Ϣ�����������ĸ�tabҳ
				if("A0196".equals(vel008_code.toUpperCase()) || "A0120".equals(vel008_code.toUpperCase()) || "A0192D".equals(vel008_code.toUpperCase())){
					tabIndex = "3";
				}else{
					if(firTab.contains(vel007_code.toUpperCase())){
						tabIndex = "1";
					}else if(secTab.contains(vel007_code.toUpperCase())){
						tabIndex = "2";
					}else{
						tabIndex = "3";
					}
				}
				String errorInfoJsonStr = DataVerifyBS.getErrorInfo(vel002, vel004, vel005);
				String conp = this.request.getContextPath();
				this.getExecuteSG().addExecuteCode("addTab('"+tabIndex+"','','"+vel002+"','"+conp+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false,"+errorInfoJsonStr+")");
			}else if("2".equals(vel003)){
				//��λ������
				//String errorInfoJsonStr = DataVerifyBS.getErrorInfo(vel002, vel004, vel005);
				//String conp = this.request.getContextPath();
				//����ת��ҳ�汣�湦���Ѿ�������ȡ����ҳ����ܲ鿴�����ɱ༭ҳ�棨�û�������鿴�����������пؼ��ɲ��༭��
				//this.getExecuteSG().addExecuteCode("addOrgTab('"+vel002+"','������Ϣά��','402881ef533533800153354ae1c00006','"+conp+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther',false,false,"+errorInfoJsonStr+")");
			}else{
				throw new AppException("��������������쳣�����ǡ���Ա����Ҳ���ǡ���λ����");
			}
		}else{
			throw new AppException("�޷���ȡ������������ͣ�");
		}*/
	}
	
	/**
	 * ˫��������ϸ��Ϣ����ת�������޸�ҳ��
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("errorDetailGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid() throws RadowException, AppException{
		
		Grid grid = (Grid)this.getPageElement("errorDetailGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String b0111=map.get("vel002").toString();
		if(StringUtil.isEmpty(b0111)){
			throw new AppException("�޷���ȡ������������ͣ�");
		}
		String groupid="";
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map_x = gridList.get(i);
			groupid+=map_x.get("vel002")+",";
		}
		if(!StringUtil.isEmpty(groupid)){
			groupid=groupid.substring(0,groupid.length()-1);
		}
		String pardata=b0111+","+groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		//this.request.getSession().setAttribute("tag", "0");
		this.getExecuteSG().addExecuteCode("$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',860,560,'','"+request.getContextPath()+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeWindow")
	public int closeWindow(){
		this.closeCueWindow("dataVerifyWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("vsc001.onchange")
	public int vcc001change() throws AppException, RadowException{
		this.setNextEventName("ruleGrid.dogridquery");
		this.getExecuteSG().addExecuteCode(countCheckTJ(SysUtil.getCacheCurrentUser().getId(), this.getPageElement("vsc001").getValue()));
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ruleGrid.dogridquery")
	public int doruleGridQuery(int start,int limit) throws RadowException{
		String bsType=this.getPageElement("bsType").getValue();
		String filtersql="" ;
		if("1".equals(bsType)){
			filtersql=" and  vru004='B01'";
		}
		if("3".equals(bsType)){
			filtersql=" and  vru004<>'B01'";
		}
		String vsc001=this.getPageElement("vsc001").getValue();
		String sql="select vru001,vru002,vru003, case vru003 when '1' then '����' ELSE '��ʾ' END vru003_name from verify_rule where vsc001='"+vsc001+"' and vru007<>'0' "+filtersql+" order by  vru004,vru005,vru003_name";
		this.pageQuery(sql,"SQL", start, 500); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**************************************************��ɴ���**********************************************/
	/**
	 * ������ȷ����ǰ��ʾ��Ϣ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("impconfirmBtn2")
	@NoRequiredValidate
	public int impmodelOnclickBefore(String str) throws RadowException{
		//����У��(ҵ������Ϊ2) 
		String bsType = this.getPageElement("bsType").getValue();
		String grid9_totalcount = this.getPageElement("grid9_totalcount").getValue();
		if("2".equals(bsType) && grid9_totalcount!=null && !grid9_totalcount.equals("") && !grid9_totalcount.equals("0")){
			this.setMainMessage("����������"+grid9_totalcount+"�����֤������ϵͳ���ظ����Ƿ�������ղ�����ԭ����Ա��Ϣ��");
			this.setMessageType(EventMessageType.CONFIRM); 
//			this.addNextEvent(NextEventValue.YES,"impmodel");
			this.addNextEvent(NextEventValue.YES,"impmodel", str);
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2')");
		}else{
//			this.setNextEventName("impmodel");
			this.impmodelOnclick(str);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ������ȷ����
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("impmodel")
	@NoRequiredValidate
	public int impmodelOnclick(String str)throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			/**
			 * ��ʷ���������
			 */
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
//			
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			//-------------------------------------------------
			String sql1 = "delete from Datarecrejlog where imprecordid = '"+imprecordid+"' ";
			sess.createQuery(sql1).executeUpdate();
			//------------------------------------------------
			ImpmodelThread thr = new ImpmodelThread(imp,str, user,userVo); 
			new Thread(thr).start();
//			this.closeCueWindow();
//			this.setRadow_parent_data(imprecordid);
//			this.openWindow("refreshWin", "pages.dataverify.RefreshOrgRecRej");
			this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin1','pages.dataverify.RefreshOrgRecRej','���մ���',700,445, '"+imprecordid+"','"+request.getContextPath()+"');");
		} catch (Exception e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("impBtn1.onclick")
	@NoRequiredValidate
	public int impmodelOnclick1()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			/**
			 * ��ʷ���������
			 */
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String impdeptid = imp.getImpdeptid();
			String ftype = imp.getFiletype();
			String empdeptid = imp.getEmpdeptid();
			if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
				this.setMainMessage("���Ƚ���У�飬�ٵ�������");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			if(imp.getWrongnumber() != null && !imp.getWrongnumber().equals("") 
//					&& Long.parseLong(imp.getWrongnumber())>0){
//				this.setMainMessage("���ڴ������ݣ����ء�");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
				if(imp.getImpstutas().equals("2")){
					this.setMainMessage("�����ѵ��룬�����ظ����롣");
				} else {
					this.setMainMessage("�����Ѵ�أ����ܵ��롣");
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				String orgs = "select distinct a.a0000 from a02 a where a.a0000=a0000 and a.a0201b in (select b0111 from b01 start with b0111='"+ imp.getImpdeptid() +"' connect by prior b0111= b0121)";
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					orgs = "select mt.a0000 from (select distinct a.a0000 a0000 from a02 a where a.a0201b in (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%')) mt where mt.a0000=a0000";
				}
				String borgs = "select b0111 from b01 start with b0111='"+ imp.getImpdeptid() +"' connect by prior b0111= b0121";
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					borgs = "select mt.b0111 from (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%') mt ";
				}
				String houzhui = imp.getFilename().substring(imp.getFilename().lastIndexOf(".") + 1);

				//a02 ��ɾ��a02���� ----- ���������ݡ��롮��Դ���ݡ� ���Ѵ��ڵ�ͬ����Ա�����֤��ͬ������  ------ ֮�������ʱ������
				//update ����temp��ְ����id
//				sess.createSQLQuery("update A02_temp t set t.a0201b = (select a1 from (select a.b0111 a1,a.b0121 a2,b.b0111 b1 from b01 a,b01_temp b where a.b0101=b.b0101 and a.b0111=b.b0114) d where d.b1=t.a0201b ) where imprecordid='"+ imprecordid + "'  and exists (select a1 from (select a.b0111 a1, a.b0121 a2, b.b0111 b1 from b01 a, b01_temp b where a.b0101 = b.b0101 and a.b0111=b.b0114) d where d.b1 = t.a0201b)").executeUpdate();
				sess.createSQLQuery("update A02_temp t set t.a0201b = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b  and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				//delete 
				//sess.createSQLQuery(" delete from a02 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a02 where exists (select t.a0200 from(select k.a0200 from A02_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0200=a02.a0200) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a02 select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
						"A0209,A0215A,A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,"+
						" A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256,A0256A,"+
						"A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,"+
	       				"A0289,A0295,A0299,A4901,A4904,A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239"+
	       				" from a02_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a06
				//delete 
				//sess.createSQLQuery(" delete from a06 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a06 where exists (select t.a0600 from(select k.a0600 from A06_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0600=a06.a0600) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a06 select A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, t.SORTID, t.UPDATED"+
	       				" from a06_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a08
				//delete 
				//sess.createSQLQuery(" delete from a08 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a08 where exists (select t.a0800 from(select k.a0800 from A08_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0800=a08.a0800) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a08 select  A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827,"+
						" A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898,A0899,A0901A,"+
						" A0901B,A0904,SORTID,t.updated,t.wage_used"+
	       				" from a08_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a11
				//delete 
				//sess.createSQLQuery(" delete from a11 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a11 where exists (select t.a1100 from(select k.a1100 from A11_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1100=a11.a1100) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a11 select  A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
						"a1127 ,a1131 ,a1134 ,a1151 ,t.updated,t.A1108,t.A1107C"+
	       				" from a11_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a14
				//delete 
				//sess.createSQLQuery(" delete from a14 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a14 where exists (select t.a1400 from(select k.a1400 from A14_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1400=a14.a1400) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a14 select a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,"+ 
						"t.sortid ,t.updated "+
	       				" from a14_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a15
				//delete 
//				sess.createSQLQuery(" delete from a15 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a15 where exists (select t.a1500 from(select k.a1500 from A15_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1500=a15.a1500) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a15 select a0000, a1500, a1517, a1521, t.updated, a1527  "+
	       				" from a15_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a29
				//delete 
//				sess.createSQLQuery(" delete from a29 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a29 where exists (select t.a0000 from(select k.a0000 from A29_temp k where k.imprecordid ='"+ imprecordid + 
						"') t where t.a0000=a29.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a29 select  a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2947 ,a2949, t.updated," +
						"t.a2970,t.a2970a,t.a2970b,t.a2970c,t.a2947a,t.a2950,t.a2951,A2921B,A2947B,t.A2921C,t.A2921d "+
	       				" from a29_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a30
				//delete 
//				sess.createSQLQuery(" delete from a30 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a30 where exists (select t.a0000 from(select k.a0000 from A30_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a30.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a30 select a0000,a3001, a3004, a3007a ,a3034 ,t.updated  "+
	       				" from a30_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a31
				//delete 
//				sess.createSQLQuery(" delete from a31 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a31 where exists (select t.a0000 from(select k.a0000 from A31_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a31.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a31 select  a0000,a3101, a3104, a3107, a3117a,a3118, a3137, a3138 ,t.updated,t.a3140,t.a3141,t.a3142  "+
	       				" from a31_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a36
				//delete 
//				sess.createSQLQuery(" delete from a36 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a36 where exists (select t.a3600 from(select k.a3600 from A36_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a3600=a36.a3600) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a36 select  a0000,a3600, a3601, a3604a, a3607, a3611, a3627 ,sortid ,t.updated "+
	       				" from a36_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a37
				//delete 
//				sess.createSQLQuery(" delete from a37 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a37 where exists (select t.a0000 from(select k.a0000 from A37_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a37.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a37 select a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,t.updated "+
	       				" from a37_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a41
				//delete 
//				sess.createSQLQuery(" delete from a41 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a41 where exists (select t.a4100 from(select k.a4100 from A41_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a4100=a41.a4100) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a41 select  a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199"+
	       				" from a41_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a53
				//delete 
//				sess.createSQLQuery(" delete from a53 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a53 where exists (select t.a5300 from(select k.a5300 from A53_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a5300=a53.a5300) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a53 select a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,t.updated"+
	       				" from a53_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a57
				//delete 
//				sess.createSQLQuery(" delete from a57 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a57 where exists (select t.a0000 from(select k.a0000 from A57_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a57.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a57 select a0000,a5714 ,t.updated,PHOTODATA,PHOTONAME,PHOTSTYPE "+
	       				" from a57_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a01
				//update ͳ�ƹ�ϵ���ڵ�λ
				sess.createSQLQuery("update A01_temp t set t.a0195 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0195 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.A0195 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update A01_temp t set t.a0195 ='-1' where imprecordid='"+ imprecordid + "' and t.a0195='XXX'").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from a01 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a01 where exists (select t.a0000 from(select k.a0000 from A01_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a01.a0000) or exists ("+orgs+")").executeUpdate();
//				String str =null;
//				Long.parseLong(str);
				//insert
				sess.createSQLQuery("insert into a01 select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
						"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
						"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
						"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
						"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
						"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
						"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
						"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
						"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122"+
						" from a01_temp t where t.is_qualified='0' and imprecordid='"+ imprecordid + "'").executeUpdate();
				
				//b01
				//update ����temp��ְ����id �ϼ�id ����id
				
				sess.createSQLQuery("update B01_temp t set t.b0121 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and t.b0111<>'"+imp.getEmpdeptid()+"' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update B01_temp t set t.b0111 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from b01 where b0111 in (select a.b0111 from b01 a,b01_temp b where (a.b0111=b.b0114 and a.b0101=b.b0101) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into b01 select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
						"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
						"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
						"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234"+
	       				" from b01_temp t where t.imprecordid='"+ imprecordid + "' and is_qualified='0' and imprecordid='"+ imprecordid + "'").executeUpdate();
				
			} else {
				Connection conn=sess.connection();
				CallableStatement cstmt=conn.prepareCall("{call Imp_From_Temp(?,?,?,?)}");
				cstmt.setString(1,imprecordid);
				cstmt.setString(2,impdeptid);
				cstmt.setString(3,empdeptid);
				cstmt.registerOutParameter(4,Types.INTEGER );
				cstmt.execute();
				int imp_sta = cstmt.getInt(4);
				if (imp_sta!=1) {
					if(cstmt!=null) cstmt.close();	
					throw new RadowException("���ݴ����쳣!");
				}
				if(cstmt!=null) cstmt.close();	
			}
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57", "B01"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" delete from " + tables1[i] + "_temp where imprecordid='"
						+ imprecordid + "'").executeUpdate();
			}
			sess.createSQLQuery(" delete from B01TEMP_B01 where imprecordid='"
					+ imprecordid + "'").executeUpdate();
			imp.setImpstutas("2");
			sess.update(imp);
			sess.flush();
			if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
				new LogUtil().createLog("463", "IMP_RECORD", "", "", "����Ӧ�ÿ�", new ArrayList());
			} else if (ftype.equalsIgnoreCase("zzb3")){
				new LogUtil().createLog("473", "IMP_RECORD", "", "", "����Ӧ�ÿ�", new ArrayList());
			}
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('MGrid').store.reload();");
			this.setMainMessage("���ճɹ���");
		} catch (AppException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
		} catch (SQLException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ���뷴����
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("empBtn.onclick")
	@NoRequiredValidate
	public int expmodelOnclick()throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ȫ���˻�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rejectBtn.onclick")
	@NoRequiredValidate
	public int rejectBtnOnclick()throws RadowException{
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		String bsType = this.getPageElement("bsType").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String imptemptable = imp.getImptemptable();
			String ftype = imp.getFiletype();
			if(imp.getImpstutas().equals("2")){
				this.setMainMessage("�����ѵ��룬���ܴ�ء�");
				return EventRtnType.NORMAL_SUCCESS;
			} else if(imp.getImpstutas().equals("4")){
				this.setMainMessage("���ݽ����У����ܴ�ء�");
				return EventRtnType.NORMAL_SUCCESS;
			} else if(imp.getImpstutas().equals("3")){
				this.setMainMessage("�����Ѵ�أ������ظ���ء�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String str = "";
			//��غ����������ϢVerify_Error_List
			sess.createSQLQuery(" delete from Verify_Error_List where vel004 = '"+b0111+"' and vel005='"+bsType+"'").executeUpdate();
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B_E", "I_E"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
				
			}
			imp.setImpstutas("3");
			sess.update(imp);
			sess.flush();
			sess.getTransaction().commit();
			String filetype=imp.getFiletype();
			if(filetype.equalsIgnoreCase("zip")){
				String p = AppConfig.HZB_PATH + "/temp/upload/" +imprecordid +"/" + imprecordid;
				File file = new File(p);
				File[] subs = file.listFiles();
				File f = subs[0];
				String fName = f.getName();

				PhotosUtil.removDirImpCmd(imprecordid,fName);
			}else{
				PhotosUtil.removDirImpCmd(imprecordid,"");
			}
			
			if(str != null && !str.equals(""))
				this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(str,"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}");
			if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
				new LogUtil().createLog("462", "IMP_RECORD", "", "", "������", new ArrayList());
			} else if (ftype.equalsIgnoreCase("zzb3")){
				new LogUtil().createLog("472", "IMP_RECORD", "", "", "������", new ArrayList());
			}
			this.getExecuteSG().addExecuteCode("realParent.odin.ext.getCmp('MGrid').store.reload();");
			this.setMainMessage("�Ѵ�أ�");
		} catch (UnsupportedEncodingException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (AppException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void exportExcel(List plist, List olist, String excelname,
			String string2) {
		// ��һ��������һ��webbook����Ӧһ��Excel�ļ�  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook();  
        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet sheet = wb.createSheet("��Ա������Ϣ");  
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
        HSSFRow row = sheet.createRow((int) 0);  
        // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("����");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("���֤����");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 2);  
        cell.setCellValue("��Ϣ��");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("��Ϣ��");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 4);  
        cell.setCellValue("������Ϣ");  
        cell.setCellStyle(style);  
         
        // ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���  
        if(plist!=null && plist.size()>0){
        	 for (int i = 0; i < plist.size(); i++)  
             {  
                 row = sheet.createRow((int) i + 1);  
                 // ���Ĳ���������Ԫ�񣬲�����ֵ  
                 row.createCell((short) 0).setCellValue((String) (((Object[]) plist.get(i))[0]));  
                 row.createCell((short) 1).setCellValue((String) (((Object[]) plist.get(i))[1])); 
                 row.createCell((short) 2).setCellValue(CodeManager.getValueByCode("VSL003", (String) (((Object[]) plist.get(i))[5]))); 
                 row.createCell((short) 3).setCellValue(CodeManager.getValueByCode("VSL004", (String) (((Object[]) plist.get(i))[6]))); 
                 row.createCell((short) 4).setCellValue((String) (((Object[]) plist.get(i))[4]));   
             }  
        }
        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet sheet2 = wb.createSheet("����������Ϣ");  
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
        HSSFRow row2 = sheet2.createRow((int) 0);  
  
        HSSFCell cell2 = row2.createCell((short) 0);  
        cell2.setCellValue("��������");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 1);  
        cell2.setCellValue("��������");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 2);  
        cell2.setCellValue("��Ϣ��");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 3);  
        cell2.setCellValue("��Ϣ��");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 4);  
        cell2.setCellValue("������Ϣ");  
        cell2.setCellStyle(style);  
        
        // ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���  
        if(olist!=null && olist.size()>0){
        	for (int i = 0; i < olist.size(); i++)  
            {  
                row = sheet2.createRow((int) i + 1);  
                // ���Ĳ���������Ԫ�񣬲�����ֵ  
                row.createCell((short) 0).setCellValue((String) (((Object[]) olist.get(i))[0]));  
                row.createCell((short) 1).setCellValue((String) (((Object[]) olist.get(i))[1])); 
                row.createCell((short) 2).setCellValue(CodeManager.getValueByCode("VSL003", (String) (((Object[]) olist.get(i))[5]))); 
                row.createCell((short) 3).setCellValue(CodeManager.getValueByCode("VSL004", (String) (((Object[]) olist.get(i))[6]))); 
                row.createCell((short) 4).setCellValue((String) (((Object[]) olist.get(i))[4]));   
            }  
       }
        // �����������ļ��浽ָ��λ��  
        try {  
            FileOutputStream fout = new FileOutputStream(excelname);  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
		
	}
	
	@PageEvent("impconfirmBtn.onclick")
	@NoRequiredValidate

	public int batchPrintBefore() throws RadowException, AppException, SQLException{
		String imprecordid = this.getPageElement("b0111OrimpID").getValue();
		HBSession sess = HBUtil.getHBSession();
		Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
		String b0114 = imp.getB0114();
		Connection conn = sess.connection();
		Statement stmt = conn.createStatement();
		ResultSet rs =  null;
		String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
		rs = stmt.executeQuery(sql5);
		int size = 0;
		while(rs.next()){
			size = rs.getInt(1);
		}
//		String sql3 = "select b0114 from b01 where B0114 = '"+ b0114 +"'";
//		rs = stmt.executeQuery(sql3);
		if((size== 0 && imp.getImpdeptid().equals("001.001")) || size > 0 || imp.getImptype().equals("4")){
			String impdeptid = imp.getImpdeptid();
			String imptype = imp.getImptype();
			String ftype = imp.getFiletype();
//			if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
//				this.setMainMessage("���Ƚ���У�飬�ٵ�������");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
				if(imp.getImpstutas().equals("2")){
					this.setMainMessage("�����ѵ��룬�����ظ����롣");
				} else if(imp.getImpstutas().equals("4")){
					this.setMainMessage("���ݽ����У������ظ����ա�");
				} else {
					this.setMainMessage("�����Ѵ�أ����ܵ��롣");
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.getExecuteSG().addExecuteCode("var grid = odin.ext.getCmp('errorGrid9');document.getElementById('grid9_totalcount').value=grid.getStore().getTotalCount();");
			if(imptype.equals("4")){
				this.getExecuteSG().addExecuteCode("radow.doEvent('impconfirmBtn2','1')");
			} else {
				this.getExecuteSG().addExecuteCode("$h1.confirm4btn('ϵͳ��ʾ','�Ƿ���µ���Ļ�����Ϣ��',200,function(id){" +
						"if(id=='yes'){" +
						"			radow.doEvent('impconfirmBtn2','1');" +
							"}else if(id=='no'){" +
							"			radow.doEvent('impconfirmBtn2','2');" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}
		}else{
			this.setMainMessage("û�ж�Ӧ����,���½��û���!");
		}
		//=======��־�� �򿪺���Ҫ�ر� 2018-12-21======= 
		if (rs != null) rs.close(); 
		//======================================
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Ƭ���
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("imgVerify.onclick")
	public int imgVerify() throws RadowException {
		this.getExecuteSG().addExecuteCode("addTab('��Ƭ���','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����У��������
	 * 3.��ȡ�ܼ�¼���ʹ�����
	 * 		��λ + ��Ա
	 * 1.��ȡ��λ�����ʹ�����
	 * 		��λ������select count(1) from b01 * ��λУ��������
	 * 2.��ȡ��Ա�����ʹ�����
	 * 		��Ա������select count(1) from a01 * ��ԱУ��������
	 * 4.����������ϸ��Ϣ
	 * 		select count(1) from verify_error_list
	 * @return
	 * @throws RadowException
	 * @throws UnsupportedEncodingException 
	 */
	@PageEvent("expSummarize.onclick")
	public int expSummarize() throws RadowException, UnsupportedEncodingException{
		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		String vsc001 = this.getPageElement("vsc001").getValue();
		
		String params = this.getRadow_parent_data();
		if(StringUtil.isEmpty(params)){
			params = this.getPageElement("subWinIdBussessId").getValue();
		}
		if(StringUtil.isEmpty(params)){
			throw new RadowException("��ȡ����Ϊ�գ�");
		}
		String bsType = params.split("@")[0];    //ҵ������ ��bsType���� 1-����У�飨�����¼���������2-����У�� 
		String b0111OrimpID;
		try {
			b0111OrimpID = params.split("@")[1];
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("��ȡУ��ҵ�������쳣���쳣��Ϣ��"+e.getMessage());
		}
		//���ļ�д��һ���ᶨʱɾ�����ļ���
//		String path = AppConfig.HZB_PATH+"/temp/upload/"+UUID.randomUUID() + ".txt";
		String path = AppConfig.HZB_PATH+"/temp/upload/�ۺ���˽��.txt"; 
		
		//ͨ��verify_error_list��ȡ�ϴ�ִ�е�У�����
		List<String[]> list = HBUtil.getHBSession().createSQLQuery("select t.vel009,count(t.vel009) from verify_error_list t where t.vel009 is not null group by  t.vel009").list();
		if(null == list || list.size() == 0){
			this.setMainMessage("û�н��Ҫ������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (Object[] strings : list) {
			map.put(strings[0].toString(), strings[1].toString());
		}
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){ 
            vsc001=HBUtil.getHBSession().createSQLQuery("select vsc001 from verify_rule where vru001 = '"+iter.next()+"'").uniqueResult().toString();
            break;
        }
		//1.��ȡ��λ�����ʹ�����
		int unitNum = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from b01 where b0111 like'"+b0111OrimpID+"%'").uniqueResult().toString());
		int unitNUm_verify = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from verify_rule t where t.vru004 = 'B01' and t.vsc001='"+vsc001+"'").uniqueResult().toString());
		int unit_total = unitNum * unitNUm_verify;
		
		int unit_error = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from verify_error_list t where t.vel003 = '2' and t.vel004 like '"+b0111OrimpID+"%'").uniqueResult().toString());
		
		//2.��ȡ��Ա�����ʹ�����
		int personNum = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from (select distinct a02.a0000 from a01,a02 where a01.a0000 = a02.a0000 and a02.a0201b like '"+b0111OrimpID+"%') a").uniqueResult().toString());
		int personNUm_verify = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from verify_rule t where t.vru004 <> 'B01'  and t.vsc001='"+vsc001+"'").uniqueResult().toString());
		int person_total = personNum * personNUm_verify;
		
		int person_error = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select count(1) from verify_error_list t where t.vel003 = '1' and t.vel004 like '"+b0111OrimpID+"%'").uniqueResult().toString());
		//3.��ȡ�ܼ�¼���ʹ�����
		int total_num = unit_total + person_total;
		int total_error = unit_error + person_error;
		
		//4.����������ϸ��Ϣ
		List<VerifyRule> itr = HBUtil.getHBSession().createQuery("from VerifyRule t where t.vsc001 = '"+vsc001+"'").list();

		try {
			File file = new File(path);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");// ָ�����룬��ֹд��������
			bw = new BufferedWriter(osw);
			if("2".equals(bsType)){
				 unit_error = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select sum(errnum) from errcount where etype='2'").uniqueResult().toString());
				 person_error = Integer.parseInt(HBUtil.getHBSession().createSQLQuery("select sum(errnum) from errcount where etype='1'").uniqueResult().toString());
				bw.write("����У��Ϊ����У��\r\n");
				bw.write("��λ������ɨ�裺������ʾ"+unit_error+"�\r\n");
				bw.write("��Ա������ɨ�裺������ʾ"+person_error+"�\r\n");
				bw.write("�������������£�"+ "\r\n");
				for (VerifyRule rule : itr) {
					if(null != map.get(rule.getVru001())){
						bw.write(rule.getVru002() + " ������Ŀ " + map.get(rule.getVru001())+ "\r\n");				
					}
				}
			}else{
				bw.write("���δ����鹲ɨ���¼"+total_num+"����д���"+total_error+"�ռ��Ϊ"+String.format("%.2f", ((float)total_error/total_num*100)).replace("NaN", "0.00").replace("Infinity", "0.00")+"%��"+ "\r\n");
				bw.write("��λ������ɨ��"+unit_total+"�������ʾ"+unit_error+"�ռ��λ���������Ϊ"+String.format("%.2f",((float)unit_error/unit_total*100)).replace("NaN", "0.00").replace("Infinity", "0.00")+"%��ռ�ܴ��������Ϊ "+String.format("%.2f",((float)unit_error/total_error*100)).replace("NaN", "0.00").replace("Infinity", "0.00")+"%��"+ "\r\n");
				bw.write("��Ա������ɨ��"+person_total+"�������ʾ"+person_error+"�ռ��Ա���������Ϊ"+String.format("%.2f",((float)person_error/person_total)).replace("NaN", "0.00").replace("Infinity", "0.00")+"%,ռ�ܴ��������Ϊ "+String.format("%.2f",((float)person_error/total_error*100)).replace("NaN", "0.00").replace("Infinity", "0.00")+"%��"+ "\r\n");
				bw.write("�������������£�"+ "\r\n");
				for (VerifyRule rule : itr) {
					if(null != map.get(rule.getVru001())){
						bw.write(rule.getVru002() + " ɨ����Ŀ " + (rule.getVru004().equals("B01") ? unitNum : personNum) + " ������Ŀ " + map.get(rule.getVru001())+ "\r\n");				
					}
				}
				
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (bw != null) 
					bw.close();
				if (osw != null) 
					osw.close();
				if (fos != null) 
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//����
		this.getExecuteSG().addExecuteCode("window.location = 'ProblemDownServlet?method=downFile&prid="+URLEncoder.encode(URLEncoder.encode(path,"UTF-8"),"UTF-8")+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����б�
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("savelist.onclick")
	@NoRequiredValidate
	@Transaction
	public int doSaveList() throws RadowException, UnsupportedEncodingException, SerialException, AppException, SQLException{
		PageElement pe = this.getPageElement("errorDetailGrid2");
		List<HashMap<String, Object>> list = pe.getValueList();
		if(list==null||list.size()<1){
			this.setMainMessage("�޿ɱ�����Ա");
			return EventRtnType.FAILD;
		}
		String sql = (String) this.request.getSession().getAttribute("v2sql");
		int from=sql.indexOf("From Verify_Error_List a");//316
		sql=sql.replace(sql.substring(0, from), "select vel002 a0000 ");//��ȡ����
		int order =sql.indexOf("order");//168
		sql=sql.replace(sql.substring(order,sql.length()),"");//��ȡ����
		String saveName = "��ԱУ���б�";
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		CustomQueryBS.saveList(saveName, "", "", loginName,sql,null);
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*//У�˽�����
	@PageEvent("canDownloadVerificationReport")
	public int canDownloadVerificationReport() throws AppException, RadowException{
		CommQuery commQ = new CommQuery();
		String mbpath1 = request.getSession().getServletContext().getRealPath("") + "\\template\\excel\\tj.xlsx";
		
		String ss=File.separatorChar+"";
		mbpath1=mbpath1.replace("\\", ss);
		mbpath1=mbpath1.replace("/", ss);
		String vsc001=this.getPageElement("vsc001").getValue();
		String b0111=this.getPageElement("searchDeptBtn").getValue();
		//���У��ͳ�Ʊ� Check-Collect.xlsx
		Map<String, GwyTongJi> tjmap = new HashMap<String, GwyTongJi>();
		List<HashMap<String,Object>> listBySQL = commQ.getListBySQL("select ct.code_table, ct.check_all, ct.check_null, ct.check_error, ct.check_time,"
				+ " ct.check_vru002, cc.code_name, cc.table_name from check_tj ct left join check_code cc on cc.col_code = ct.col_code "
				+ "where ct.userid = '"+SysManagerUtils.getUserId()+"' and ct.check_vsc001 = '"+vsc001+"' and ct.check_b0111 = '"+b0111+"' order by cc.code_table ");
		String check_all, check_null, check_error, key, check_time = "";GwyTongJi gwyTongJi;Boolean isfrist = true;
		//ѭ������A01&����
		for (HashMap<String, Object> hashMap : listBySQL) {
			check_all = hashMap.get("check_all") + "";
			check_null = hashMap.get("check_null") + "";
			check_error = hashMap.get("check_error") + "";
			if(!"".equals(check_all) && !"".equals(check_null) && !"".equals(check_error)){
				key = hashMap.get("code_table") + "&" + hashMap.get("table_name");
				if (tjmap.containsKey(key)) {
					gwyTongJi = tjmap.get(key);
					gwyTongJi.setTjisnull(Integer.parseInt(check_null) + gwyTongJi.getTjisnull());
					gwyTongJi.setTjismis(Integer.parseInt(check_error) + gwyTongJi.getTjismis());
					gwyTongJi.setNumberOfBars(gwyTongJi.getNumberOfBars()+1);
					tjmap.put(key, gwyTongJi);
				}else{
					tjmap.put(key, new GwyTongJi(key, 
							Integer.parseInt(check_all),
							Integer.parseInt(check_null),
							Integer.parseInt(check_error), hashMap.get("code_name") + "", 1));
				}
				if("��ͥ��Ա��ν".equals(hashMap.get("table_name")) ){
					System.out.println("��ͥ��Ա��ν" + check_null + " + " + check_error + " >> " 
				+ tjmap.get(key).getTjisnull() + "+" + tjmap.get(key).getTjismis());
				} 
				if("����ְ����ʱ��".equals(hashMap.get("table_name")) ){
					System.out.println("����ְ����ʱ��" + check_null + " + " + check_error + " >> " 
							+ tjmap.get(key).getTjisnull() + "+" + tjmap.get(key).getTjismis());
				}  
				if("����".equals(hashMap.get("table_name")) ){
					System.out.println("����-" + check_null + " + " + check_error + " >> " 
							+ tjmap.get(key).getTjisnull() + "+" + tjmap.get(key).getTjismis());
				}
				if(isfrist){ check_time = hashMap.get("check_time") + "";}
				
			}
		}
		
		Set<String> dbColumns = getDBColumns();
		CheckTJExcelAndWord tjExcel = new CheckTJExcelAndWord();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		
		String path = tjExcel.getPath(uuid);
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}

		String format = simpleDateFormat.format(new Date());
		//��ʼ����ͳ������Excel
		tjExcel.startSheet(mbpath1, tjmap, vsc001, dbColumns, check_time, format, path,
				checkSelectB01().toString(),//������Ϣ
				checkSelectA01().toString());//��Ա��Ϣ
		
		//����word�ĵ��������� 
		tjExcel.createWord(mbpath1,tjmap, dbColumns, format,path);
		String fileName = "������������_"+Calendar.getInstance().getTimeInMillis()+".zip";
		com.insigma.siis.local.pagemodel.dataverify.Zip7z.zip7Z(path.substring(0,path.length()-1) ,path+fileName ,null);
		
		this.getExecuteSG().addExecuteCode("window.location.href='"+request.getContextPath()+"/ProblemDownServlet?method=downFileExcelry&prid='+encodeURI(encodeURI('"+path+fileName+"'));");


		return EventRtnType.NORMAL_SUCCESS;
	}*/

	public Set<String> getDBColumns(){
		CommQuery commQuery = new CommQuery();
		List<HashMap<String, Object>> listBySQL;
		Set<String> set = new LinkedHashSet<String>();
		try {
			HashMap<String,Object> hashMap;String table_code,col_code;
			if(DBUtil.getDBType()==DBType.ORACLE){
				table_code = "to_number(orderby)";
			}else{
				table_code = "orderby + 0";
			}
			listBySQL = commQuery.getListBySQL("select CODE_TABLE, TABLE_NAME from check_code where isok='1' order by "+table_code+" ");
			for (int i = 0, num = listBySQL.size(); i < num; i++) {
				hashMap = listBySQL.get(i);
				table_code = hashMap.get("code_table") + "";
				col_code = hashMap.get("table_name") + "";
				set.add(table_code+"&"+col_code);
			}
		} catch (AppException e) {
			e.printStackTrace();
			CommonQueryBS.systemOut("��ȡ�ֶ���Ϣʱ������");
		}
		return set;
	}
	
	//==================lxl================================
	/**
	 * ������԰�ť��ת��ҳ��
	 * @param info
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ignore")
	public int ignore(String a0000) throws RadowException {
		HBSession sess=HBUtil.getHBSession();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String deleteSql = " DELETE from verify_error_newlist where vel010='"+userid+"'";
		sess.createSQLQuery(deleteSql).executeUpdate();
		String sql="insert into verify_error_newlist(vel001,ignore_value,vel010) SELECT vel001,vel006,vel010 FROM verify_error_list_mirror where vel010='"+userid+"' and vel002 ='"+a0000+"' and vel001 not in (select vel001 from (select vel001 from verify_rule_detail where vel010='"+userid+"') a)";
		sess.createSQLQuery(sql).executeUpdate();
		request.getSession().setAttribute("ignoreA0000", a0000);
		//this.openWindow("grantWindow", "pages.sysorg.org.orgdataverify.OrgDataVerifyNew");sss
		this.getExecuteSG().addExecuteCode("showOrgDataVerifyNew();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ȡ��
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("cancelIgnore.onclick")
	@NoRequiredValidate
	@Transaction
	public int cancelIgnore() throws RadowException, UnsupportedEncodingException, SerialException, AppException, SQLException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		HBSession sess=HBUtil.getHBSession();
		PageElement pe = this.getPageElement("errorDetailGrid4");
		List<HashMap<String, Object>> list = pe.getValueList();
		int flagNum=0;
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object checked=map.get("checked");
			if(checked!=null&&checked.toString().equals("true")){
				flagNum++;
				String vel001=map.get("vel001")==null?"":map.get("vel001").toString();
				String a0000=map.get("a0000")==null?"":map.get("a0000").toString();
				String vel009=map.get("vel009")==null?"":map.get("vel009").toString();
				String sql="update verify_error_list set vel006=concat(vel006,'��"+vel001+"') where VEL001='"+a0000+"' and vel010='"+userid+"'";
				HBUtil.executeUpdate(sql);
				HBUtil.executeUpdate("delete from verify_rule_detail where vel009='"+vel009+"' and a0000='"+a0000+"' and vel010='"+userid+"'");
				//HBUtil.executeUpdate("delete from verify_error_list_mirror");
				HBUtil.executeUpdate("delete from verify_error_list where vel010='"+userid+"'");
				String vel001Bf = " sys_guid()  "; 
				if(DBUtil.getDBType() == DBType.MYSQL){
					vel001Bf = " UUID() ";
				}else{
					vel001Bf = "sys_guid()";
				}
				HBUtil.executeUpdate("delete from  verify_error_list where vel010='"+userid+"'");
				String insersql="insert into verify_error_list ( vel001,vel002,vru003,vel003,vel004,vel005,vel006,vel007,vel008,vel009,vel010)  select "+vel001Bf+" vel001,tt.* from "
						+ " (select vel002,vru003,vel003,vel004,vel005,replace(to_char(wm_concat(vel006)),',','��'),vel007,vel008,vel009,vel010 from "
						+ " (select  vel002,'5' vru003,vel003,vel004,vel005,concat((case when vru003='1' then '����:' else '��ʾ:' end ),to_char(wm_concat(vel006))) vel006,'' vel007,''vel008,'' vel009,vel010"
						+ " from verify_error_list_mirror where vel010='"+userid+"' and vel001 not in (SELECT a.vel001 from verify_error_list_mirror a ,verify_rule_detail b where b.VEL002=a.VEL002 and b.VEL009=a.VEL009 and a.vel010=b.vel010 and a.vel010='"+userid+"') group by vel002,vel003,vel004,vel005,vel010,vru003 "
						+ "  ) vel"
						+ " group by vel002,vru003,vel003,vel004,vel005,vel007,vel008,vel009,vel010) tt";
				if(DBUtil.getDBType()==DBType.MYSQL){
					insersql=insersql.replace("to_char(wm_concat(vel006))", "group_concat(vel006  order by vel006 desc)");
				}
				HBUtil.executeUpdate(insersql);
			}
		}
		if(flagNum==0){
			this.setMainMessage("��ѡ����Ҫȡ�����Ե���Ϣ��");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("errorDetailGrid4.dogridquery");
		this.setNextEventName("errorDetailGrid.dogridquery");
		this.setNextEventName("errorDetailGrid2.dogridquery");
		
		this.setMainMessage("ִ�гɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ���Դ�����ϸ��Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorDetailGrid4.dogridquery")
	@NoRequiredValidate      
	public int doIgnoreDetailGridQuery(int start,int limit) throws RadowException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String sql="select a01.a0000,a0101,a0184 a0184,b.vel006,b.vel001,b.vel009,a.detail from a01,verify_rule_detail a,verify_error_list_mirror b where a01.a0000= a.a0000 and a.VEL009=b.VEL009 and a.a0000=b.VEL002  and a.vel010=b.vel010 and a.vel010='"+userid+"' order by a0184";
		this.pageQuery(sql,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
}
