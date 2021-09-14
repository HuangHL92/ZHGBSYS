package com.insigma.siis.local.pagemodel.cadremgn.sysmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class CheckExpressionPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	public int initX() throws RadowException {
		
		String vscvru = this.getPageElement("subWinIdBussessId").getValue();
		String[] vscvrustr = vscvru.split(",");
		this.getPageElement("vsc001").setValue(vscvrustr[0].toString());
		QueryCommon qcom = new QueryCommon();
		String code_level = "0,1,2,01,02,03,04,05,06,11,12,13,14,141,36";//����̶����������
		try {
			if (vscvrustr.length==2) {//����vru001��Ϊ�޸�
				this.getPageElement("vru001").setValue(vscvrustr[1].toString());
				List<HashMap<String,Object>> list = qcom.queryVerifyRule(vscvrustr[0].toString(),vscvrustr[1].toString());
				if(list.size()>0){
					this.getPageElement("vru001").setValue(list.get(0).get("vru001").toString());
					this.getPageElement("vru000").setValue(list.get(0).get("vru010")==null?"":list.get(0).get("vru010").toString());
					this.getPageElement("vru002").setValue(list.get(0).get("vru002").toString());
					this.getPageElement("A1KC192").setValue(list.get(0).get("vrutime").toString());
				}
			} else {//����vru001��Ϊ����
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
				this.getPageElement("A1KC192").setValue(df.format(new Date()));
				this.createPageElement("btnReload", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("upRow", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("downRow", ElementType.BUTTON, false).setDisabled(true);
			}
//			if (!sysuserid.equals(userid)) {
//				String sqlTable = "select t.table_code,t.islook,t.isadd,t.ischange,t.isdel from COMPETENCE_USERTABLE t where t.userid = '"+userid+"' where t.islook='1'";
//				String sqlCode = "select t.table_code,t.col_code,t.islook,t.ischange,t.ischeckout from COMPETENCE_USERTABLECOL t where t.userid = '"+userid+" and table_code='"+"'";
//			}
			String vsc002 = HBUtil.getValueFromTab("vsc002", "verify_scheme", "vsc001='"+vscvrustr[0]+"'");
			List<HashMap<String, Object>> table_list = qcom.queryTable();
			List<HashMap<String, Object>> function_list = qcom.queryFunction();
			List<HashMap<String, Object>> operator_list = qcom.queryOperator(code_level);
			this.getPageElement("tableListGrid").setValueList(table_list);
			this.getPageElement("personListGrid121").setValueList(function_list);
			this.getPageElement("personListGrid3123123").setValueList(operator_list);
			this.getPageElement("vsc002").setValue(vsc002);
			this.getPageElement("Flag").setValue("no");
			rownumChange("-1");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯ���ݿ�ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*
	 * �����Ϣ������ʾ��Ӧ��ָ����
	 */
	@PageEvent("tableListGrid.rowclick")
	@NoRequiredValidate
	public int codeChange() throws Exception {
		QueryCommon qcom = new QueryCommon();
		// ��ȡ�������Ϣ
		String table_name = this.getPageElement("tableListGrid").getValue("table_name", 0).toString();
		String[] arr = table_name.split("\\.");
		if (arr.length < 1) {
			this.setMainMessage("��ѯ���ݿ�ʧ��");
		}
		this.getPageElement("table_code").setValue(arr[0]);
		List<HashMap<String, Object>> list = qcom.queryCode(arr[0]);
		this.getPageElement("codeListGrid").setValueList(list);
		this.getPageElement("Flag").setValue("no");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * �����Ϣ�� -- ���ҳ����Ϣ
	 */
	@PageEvent("codeListGrid.rowclick")
	@NoRequiredValidate
	public int signChange() throws Exception {
		String table_code = this.getPageElement("table_code").getValue();
		String col_name = this.getPageElement("codeListGrid").getValue("col_name", 0).toString();
		String code_type = this.getPageElement("codeListGrid").getValue("code_type", 0).toString();
		String[] arr = col_name.split("\\.");
		if (arr.length < 1) {
			this.setMainMessage("��ѯ���ݿ�ʧ��");
		}
		this.getPageElement("vru002").setValue(this.getPageElement("vru002").getValue()+arr[1]);
		this.getPageElement("codetype").setValue(code_type);
		this.getPageElement("col_code").setValue(table_code+"."+arr[0]);
		this.getPageElement("nowString").setValue(table_code+"."+arr[0]);
		this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		this.getExecuteSG().addExecuteCode("orgTreeJsonData();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * ��������
	 */
	@PageEvent("personListGrid3123123.rowclick")
	@NoRequiredValidate
	public int signWrite() throws Exception {
		// ��ȡ�������Ϣ
		String col_name = this.getPageElement("personListGrid3123123").getValue("code_name", 0).toString();
		String[] arr = col_name.split("\\(");
		if (arr.length < 1) {
			this.setMainMessage("��ѯ���ݿ�ʧ��");
		}
		this.getPageElement("vru002").setValue(this.getPageElement("vru002").getValue()+arr[0]);
		this.getPageElement("nowString").setValue(arr[1].replace(")", ""));
		this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*
	 * ����Ѷ��幫ʽ
	 */
	@PageEvent("personListGrid121.rowclick")
	@NoRequiredValidate
	public int formulaWrite() throws Exception {
		// ��ȡ�������Ϣ
		String col_name = this.getPageElement("personListGrid121").getValue("code_name", 0).toString();
		this.getPageElement("nowString").setValue(col_name);
		this.getPageElement("Flag").setValue("no");
		this.getExecuteSG().addExecuteCode("rowDbClick1();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��֤
	@PageEvent("verifiClick")
	public int verifiClick() throws RadowException, AppException {
		String flag="no";
		String fullSQL ="";
		if(this.getPageElement("vru000")!=null&&!"".equals(this.getPageElement("vru000"))&&this.getPageElement("vru000").getValue().trim().length()>1){
			String sql=this.getPageElement("vru000").getValue().toString().trim();
			String jygzsm=this.getPageElement("vru002").getValue().toString().trim();
			if(sql.length()<1){
				this.setMainMessage("������У��������");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(sql.length()>250){
				this.setMainMessage("У�����������ַ����ƣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(jygzsm.length()>80){
				this.setMainMessage("У������˵�������ַ����ƣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			LinkedList<Object> list = getTableCode(sql);
			CommQuery cqbs=new CommQuery();
			try {
				this.getPageElement("vru010").setValue(sql);
				keepCheck();
				fullSQL = getFullSQL(sql,list);
				cqbs.getListBySQL(fullSQL+" and 1=2");//ʵ��У�飬�޷���ֵ
				flag="yes";
				this.setMainMessage("���ʽУ��ϸ�\n���뼰ʱ���桿");
				this.getPageElement("Flag").setValue(flag);
				this.getPageElement("vru009").setValue(fullSQL);
			} catch (Exception e) {
				this.setMainMessage("���ɵ�����﷨����������ӵ�����! "+e.getMessage());
	        	this.getPageElement("Flag").setValue(flag);
			} 
		}else{
			this.setMainMessage("������У����ʽ��");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ƴ������SQL���
	public String getFullSQL(String sql,LinkedList<Object> list) {
		HashMap<String,String> map = (HashMap<String, String>)list.get(0);
		TreeSet<String> tableSet = (TreeSet<String>)list.get(1);
		String selAfter = "select ";
		String fromAfter = " from ";
		String selAndFrom = " count(1) ";
		String onJoin = "";
		String fullSQL = "";
		for (String code : tableSet) {
			fromAfter=fromAfter+code+"_temp,";
		}
		if (fromAfter.length()>0) {
			fromAfter=fromAfter.substring(0, fromAfter.length()-1);//ȥ����󶺺�
		}
		//�����ֶι���  -- �Ժ���
		if (tableSet.size()==1) {
			//����Ҫ����
		} else {
			if (tableSet.contains("B01")){
				if (tableSet.contains("A01")) {
					onJoin = " A01_temp.A0000=A02_temp.A0000 and A02_temp.A0201B=B01_temp.B0111 and ";
					if (!tableSet.contains("A02")) {
						fromAfter = fromAfter+ ",A02_temp ";
					}
				}
			}else{
				if (tableSet.contains("A01")) {
					onJoin = " ";
					for (String table : tableSet) {
						onJoin = onJoin + " A01_temp.A0000="+table+"_temp.A0000 and ";
					}
				}
			}
		}
		//����sql����ĳ���ʱ����_Ŀ��Ϊ�˺�ԭϵͳ����
		for (String code : map.keySet()){
			sql=sql.replace(map.get(code)+"."+code, map.get(code)+"_temp."+code);
		}
		fullSQL = selAfter + selAndFrom + fromAfter + " where 1=1 and " + onJoin + " (" + sql + ") ";
		CommonQueryBS.systemOut("--------->>>"+DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss")+":"+fullSQL);
		return fullSQL;
	}
	
	//����
	@PageEvent("keepCheck")
	@NoRequiredValidate
	public int keepCheck() throws RadowException, AppException {
		String vsc001 = this.getPageElement("vsc001").getValue();//У�鷽������
		String vru001 = this.getPageElement("vru001").getValue();//У���������
		String vru002 = this.getPageElement("vru002").getValue();//У�������ʾ��Ϣ
		String vru004 = this.getPageElement("vru004").getValue();//У�������ʾ��Ϣ
		String vru005 = this.getPageElement("vru005").getValue();//У�������ʾ��Ϣ
		String vru006 = this.getPageElement("vru006").getValue();//У�������ʾ��Ϣ
		String vru009 = this.getPageElement("vru009").getValue();//У���������SQL���
		String vru010 = this.getPageElement("vru010").getValue();//У�������SQL��䲿��
		String shijian = this.getPageElement("A1KC192").getValue();//У�����ʱ��
		String Flag = this.getPageElement("Flag").getValue();//��ȡУ����
		HBSession session = HBUtil.getHBSession();
		if (vru001==null||vru001.trim().equals("")) {//��Ϊ����
			if ("no".equals(this.getPageElement("Flag").getValue())) {
				this.setMainMessage("����ȷ��У��ʽ�ϸ�");
			}

			VerifyRule vr = new VerifyRule();
			vr.setVsc001(vsc001);
			vr.setVru002(("".equals(vru002)?"��������ʾ��Ϣ":vru002).replace("'", "\""));
			vr.setVru003("1");
			vr.setVru004(vru004);
			vr.setVru005(vru005);
			vr.setVru006(vru006);
			if("yes".equals(Flag)){
				vr.setVru007("1");
			}else{
				vr.setVru007("0");
			}
			vr.setVru008(("".equals(vru002)?"��������ʾ��Ϣ":vru002).replace("'", "\""));
			vr.setVru009(vru009);
//			vr.setVru010(vru010);
//			vr.setVrutime(shijian);
			session.save(vr);
			//��ȡ��������ֵ����ǰҳ��
			vru001 = vr.getVru001();
			this.getPageElement("vru001").setValue(vru001);
			session.flush();
		} else {//��Ϊ�޸�
			VerifyRule vr=(VerifyRule)session.get(VerifyRule.class,vru001);
			vr.setVru002(("".equals(vru002)?"��������ʾ��Ϣ":vru002).replace("'", "\""));
			vr.setVru004(vru004);
			vr.setVru005(vru005);
			vr.setVru006(vru006);
			if("yes".equals(Flag)){
				vr.setVru007("1");
			}else{
				vr.setVru007("0");
			}
			if("B01".equalsIgnoreCase(vru004)){
				vru009 = vru009.replace(" 1=1 ", " 1=1 and B01_temp.B0111 = :B0111 ");
			}else{
				vru009 = vru009.replace(" 1=1 ", " 1=1 and "+vru006+".A0000 = :A0000 ");
			}
			vr.setVru008(("".equals(vru002)?"��������ʾ��Ϣ":vru002).replace("'", "\""));
			vr.setVru009(vru009);
//			vr.setVru010(vru010);
//			vr.setVrutime(shijian);
			session.update(vr);
			session.flush();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		this.getPageElement("A1KC192").setValue(df.format(new Date()));
		if ("1".equals(this.getPageElement("keepCheckBZ").getValue())) {//����ʱ�����ѣ�
			this.setMainMessage("����ɹ���");
			this.getExecuteSG().addExecuteCode("btnClose();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//��һ��&��һ��//num��ʾ��ǰ������
	@PageEvent("rownumChange")
	public int rownumChange(String num) throws RadowException, AppException {
		QueryCommon comm = new QueryCommon();
		String vsc001 = this.getPageElement("vsc001").getValue();
		String vru001 = this.getPageElement("vru001").getValue();
		LinkedList<String> selectOnChange = selectOnChange(vsc001);
		if("-1".equals(num)){
			if(selectOnChange.size()==1||selectOnChange.size()==0){
				this.createPageElement("downRow", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("upRow", ElementType.BUTTON, false).setDisabled(true);
			}
		}else{
			if (selectOnChange.size()==0) {
				this.getPageElement("vru000").setValue("");
				this.getPageElement("vru001").setValue("");
				this.getPageElement("vru002").setValue("");
				this.getPageElement("A1KC192").setValue("");
				this.setMainMessage("��У�˷�����У�����");
			} else if(selectOnChange.size()==1){
				this.createPageElement("downRow", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("upRow", ElementType.BUTTON, false).setDisabled(true);
			}else {
				int i  = selectOnChange.indexOf(vru001);;
				i="0".equals(num)?i+1:i-1;
				if (i==selectOnChange.size()) {//�ӣ�������
					i=0;
				} else if (i==-1){//��������
					i=selectOnChange.size()-1;
				}
				List<HashMap<String,Object>> list = comm.queryVerifyRule(vsc001, selectOnChange.get(i));
				if("-1".equals(list.get(0).get("vrutime"))){
					this.getPageElement("vru001").setValue(selectOnChange.get(i));
					rownumChange(num);
				}else{
					this.getPageElement("vru001").setValue(selectOnChange.get(i));
					this.getPageElement("vru002").setValue(list.get(0).get("vru002").toString());
					this.getPageElement("vru000").setValue(list.get(0).get("vru010").toString());
					this.getPageElement("A1KC192").setValue(list.get(0).get("vrutime").toString());
				}
			}
		}
		return  EventRtnType.NORMAL_SUCCESS;
	}
	
	//��ȡ��ǰ�������еġ��Ǹ���Ĭ�Ϲ����еĹ��򡿹�������
	@PageEvent("selectOnChange")
	public LinkedList<String> selectOnChange(String vsc001) throws RadowException, AppException {
		LinkedList<String> linkedList = new LinkedList<String>();
		CommQuery cqbs=new CommQuery();
		List<HashMap<String,Object>> list = cqbs.getListBySQL("select vru001 from verify_rule where vsc001='"+vsc001+"' and VRUTIME<>'-1' ");
		for (HashMap<String, Object> map : list) {
			linkedList.add(map.get("vru001").toString());
		}
		return  linkedList;
	}
	
	//ɾ������
	@PageEvent("deletcClick")
	public int deletcClick() throws RadowException, AppException {
		this.setMainMessage("��ɾ���ɹ�����Ϊ��������һ����");
		String vru001 = this.getPageElement("vru001").getValue();
		if (vru001==null||"".equals(vru001.trim())) {
			this.getExecuteSG().addExecuteCode("window.opener.location.reload();");
		} else {
			HBSession session = HBUtil.getHBSession();
			session.delete(session.get(VerifyRule.class,vru001));
			session.flush();
		}
		rownumChange("0");//ɾ����ɽ�������һ��
		return  EventRtnType.NORMAL_SUCCESS;
	}
	
	//��ʼ�����ṹ
	@PageEvent("orgTreeJsonData")
	public int orgTreeJsonData() throws RadowException, AppException {
		String node=request.getParameter("node");
		String codeType=this.request.getParameter("codetype");
		String sql="select * from code_value where code_type='"+codeType+"' and sub_code_value='"+node+"'";
		if("ZB01".equals(codeType)){
			//��ȡ��λ����code_value
			sql = sql + " and REGEXP_LIKE (code_value,'^[0-9]+[0-9]$')";
		}
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		List<ExtTreeNodeStr> listTree=new ArrayList<ExtTreeNodeStr>();
		if(list!=null&&list.size()>0){
			for(HashMap<String, Object> info : list){
				ExtTreeNodeStr nodestr =new ExtTreeNodeStr();
				nodestr.setId(info.get("code_value").toString());
				//�ж��Ƿ����ӽڵ�
				sql = "select * from code_value where code_type='"+codeType+"' and sub_code_value='"+info.get("code_value")+"'";
				List<HashMap<String, Object>> nextlist=cqbs.getListBySQL(sql);
				if(nextlist.size()>0){
					nodestr.setLeaf(false);
				}else{
					nodestr.setLeaf(true);
				}
				nodestr.setText(info.get("code_name").toString());
				listTree.add(nodestr);
			}
		}
		String json = com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil.toJSONString(listTree);
		this.setSelfDefResData(json);
		return EventRtnType.XML_SUCCESS;
		
	}
	//����������
	@PageEvent("clickCodeValue")
	public int clickCodeValue(String codevalue) throws RadowException, AppException {
		if("-1".equals(codevalue)){//��ʾ���ڵ�
			this.getPageElement("codevalue").setValue(codevalue);
		}else{
			String codeType=this.getPageElement("codetype").getValue();
			String sql="select code_value,code_name from code_value where code_type='"+codeType+"' and code_value='"+codevalue+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			HashMap<String, Object> map=list.get(0);
			String allStr2 = this.getPageElement("vru002").getValue();
			this.getPageElement("nowString").setValue("'"+map.get("code_value")+"'");
			this.getPageElement("vru002").setValue(allStr2+" "+map.get("code_name"));
			this.getExecuteSG().addExecuteCode("rowDbClick1();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��ȡ�ַ����б����ֶΣ�ͬʱΪ��У���ֵ
	 * @param sqlterm
	 * @return ���� List���� 
	 * ��һλ��HashMap<String code, String table>
	 * �ڶ�λ�� TreeSet<String table>
	 * ����λ�� TreeSet<String code>
	 */
	public LinkedList<Object> getTableCode(String sqlterm) {
		HashMap<String,String> map = new HashMap<String, String>();
		TreeSet<String> tableSet = new TreeSet<String>();
		TreeSet<String> codeSet = new TreeSet<String>();
		LinkedList<Object> list = new LinkedList<Object>();
		String regex = "([A-Za-z]([A-Za-z0-9]+)\\.[A-Za-z]([A-Za-z0-9]+))";
		Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);//��ʾ���п���
		Matcher m = pattern.matcher(sqlterm);
		String[] split = null;
		int biaoji = 1;
		while (m.find()) {
			split = m.group(1).split("\\.");
			tableSet.add(split[0]);
			codeSet.add(split[1]);
			map.put(split[1], split[0]);
			try { //��ȡ��У����Ϣ������У����Ϣ��
				/* �߼�˵����
				 * 	����A01��B01ʱ��B01Ϊ��У��� A01Ϊ�ο���ͬʱ��Ҫ����м��A02
				 *  ����A01��������ʱ��������Ϊ��У��� A01Ϊ�ο���
				 *  ֻ����A01ʱ��A01Ϊ��У����ο���
				 *  ������A01ʱ��������Ϊ��У����ο���
				 */
				if("B01".equals(split[0])&&(biaoji==2||biaoji==1)){
					this.getPageElement("vru004").setValue("B01");
					this.getPageElement("vru005").setValue(split[1]);
					this.getPageElement("vru006").setValue("B01_temp");
					biaoji=3;
				} else if (!"A01".equals(split[0])&&biaoji==1){
					this.getPageElement("vru004").setValue(split[0]);
					this.getPageElement("vru005").setValue(split[1]);
					this.getPageElement("vru006").setValue(split[0]+"_temp");
					biaoji=2;
				} else if (biaoji!=2&&biaoji!=3){
					this.getPageElement("vru004").setValue(split[0]);
					this.getPageElement("vru005").setValue(split[1]);
					biaoji=1;
				}
			} catch (RadowException e) {
				e.printStackTrace();
			}
		}
		list.add(map);//�����Ϣ������Ϣ���
		list.add(tableSet);//������ظ���Ϣ��
		list.add(codeSet);//������ظ���Ϣ��
		try {
			if (tableSet.contains("B01")) {
				if (tableSet.contains("A01")) {
					this.getPageElement("vru006").setValue("A01_temp");
				}
			} else if (tableSet.contains("A01")){
				this.getPageElement("vru006").setValue("A01_temp");
			}
		} catch (RadowException e) {
			e.printStackTrace();
		}
		return list;
	}
}
