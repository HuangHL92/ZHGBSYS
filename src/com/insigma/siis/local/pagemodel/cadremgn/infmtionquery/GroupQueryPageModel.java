package com.insigma.siis.local.pagemodel.cadremgn.infmtionquery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;
import com.insigma.siis.local.business.entity.Qryviewfld;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class GroupQueryPageModel extends PageModel {

	/**
	 * ����ɱ༭�İ�ť
	 */
	public String btnn1init[] = { "btnn1", "btnn2", "btnn4" };// 1.ѡ������ 2.���� 3.��
	public String btnn1[] = { "btnn5", "btnn6" };// 1.���� 2.����
	public String btnn2[] = { "btnn4", "btnn1", "btnn2" };// 1.�� 2.ѡ������ 3.��
	public String btnn3[] = { "btnn5", "btnn6" };// 1.���� 2.����
	public String btnn4[] = { "btnn1", "btnn2" };// 1.ѡ������ 2.��
	public String btnn5[] = { "btnn1", "btnn2" };// 1.ѡ������ 2.��
	public String btnn6[] = { "btnn1", "btnn2" };// 1.ѡ������ 2.��

	public HashMap<String, String[]> mapBtn = new HashMap<String, String[]>();

	@Override
	public int doInit() throws RadowException {
		
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);// ��
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);// ����
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);// ����
		this.setNextEventName("viewListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ���ѡ�������� ���ض�Ӧ��ť 1.ѡ������ 2.�� 3.�� 4.�� ��ʾ��Ӧ��ť 5.���� 6.����
	 */
	@PageEvent("setDisSelect")
	public int setDisSelect(String arrid) throws RadowException {
		try {
			String id = "";
			String arrstr[] = arrid.split(",");
			id = arrstr[0];
			int leftBrakets = Integer.parseInt(arrstr[1]);
			int rightBrakets = Integer.parseInt(arrstr[2]);
			id = arrstr[0];
			if (mapBtn == null || mapBtn.size() == 0) {
				mapBtn.put("btnn1", btnn1);
				mapBtn.put("btnn2", btnn2);
				mapBtn.put("btnn3", btnn3);
				mapBtn.put("btnn4", btnn4);
				mapBtn.put("btnn5", btnn5);
				mapBtn.put("btnn6", btnn6);
			}
			String arr[] = mapBtn.get(id);
			for (int i = 0; i < arr.length; i++) {
				this.createPageElement(arr[i], ElementType.BUTTON, false).setDisabled(false);
			}
			if ("btnn1".equals(id)) {
				if (leftBrakets == rightBrakets) {
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				}
				if (leftBrakets > rightBrakets) {
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false); 
				}
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true); 
			} else if ("btnn2".equals(id)) {
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true); 
			} else if ("btnn3".equals(id)) {
				if (leftBrakets > rightBrakets) {
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false); 
				}
				if (leftBrakets == rightBrakets) {
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				}
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true); 
			} else if ("btnn4".equals(id)) {
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true); 
			} else if ("btnn5".equals(id)) {
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true); 
			} else if ("btnn6".equals(id)) {
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true); 
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true); 
			}
			setAllDis();// ����ȫ��ɾ����ť�����ɱ༭
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ����ȫ��ɾ����ť�����ɱ༭
	 */
	@PageEvent("setAllDis")
	public int setAllDis() {
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����ѯ
	 */
	@PageEvent("previewFunc")
	public int previewFunc() throws RadowException, SQLException {
		String userid=SysUtil.getCacheCurrentUser().getId();
		String gs = this.getPageElement("conditionName9").getValue();
		if (gs==null&&"".equals(gs)) {//����Ҫ��༭����
			this.setMainMessage("����������༭��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if (listCode1==null||listCode1.size()<1) {//��ʾδѡ����Ϣ���Ĭ��ԭ��ͼ˳��
			this.setMainMessage("��ѡ��Ԥ����Ϣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(getLSSQL(userid)!=-10){
			return 0;
		}
		this.getExecuteSG().addExecuteCode("udfbt5func();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 5��->num=4;
	 */
	//ʵ��ѡ��Ԥѡ��
	@PageEvent("checkClickCode")
	public int checkClickCode(String num) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//δѡ��
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0";}//�����grid ��һ�з���null
		int rownum = Integer.parseInt(num);
		HashMap<String,Object> hashMap1 = listCode.get(rownum);
		hashMap1.put("weizhi", num);
		listCode.remove(Integer.parseInt(num));//δѡ�� -- �Ƴ�
		listCode1.add(hashMap1);//Ԥѡ�� -- ����
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ʵ���Ƴ�Ԥѡ��
	@PageEvent("delThisOne")
	public int delThisOne(String num) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//δѡ��
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0";}//�����grid ��һ�з���null
		int rownum = Integer.parseInt(num);
		HashMap<String,Object> hashMap1 = listCode1.get(rownum);
		String weizhi = (String)hashMap1.get("weizhi");
		listCode1.remove(Integer.parseInt(num));//Ԥѡ��  -- �Ƴ�
		listCode.add(hashMap1);//δѡ��-- ���������
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ʵ������Ԥѡ��
	@PageEvent("upThisOne")
	public int upThisOne(String num) throws RadowException{
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){
			this.setMainMessage("��������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int rownum = Integer.parseInt(num);
		int replaceNum1 = rownum;
        int replaceNum2 = rownum-1;
        listCode1.add(replaceNum1, listCode1.get(replaceNum2));
        listCode1.add(replaceNum2+1, listCode1.get(replaceNum1+1));
        listCode1.remove(replaceNum1+2);
        listCode1.remove(replaceNum2);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ʵ������Ԥѡ��
	@PageEvent("downThisOne")
	public int downThisOne(String num) throws RadowException{
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0"; }
		int rownum = Integer.parseInt(num);
		if(listCode1.size()==rownum+1){
			this.setMainMessage("�������һ�");
		}else{
			int replaceNum1 = rownum;
            int replaceNum2 = rownum+1;
            listCode1.add(replaceNum1, listCode1.get(replaceNum2));
            listCode1.add(replaceNum2+1, listCode1.get(replaceNum1+1));
            listCode1.remove(replaceNum1+1);
            listCode1.remove(replaceNum2+1);
			this.getPageElement("codeList2Grid1").setValueList(listCode1);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	public TreeSet<String> getTables() throws RadowException {
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		TreeSet<String> treeSet = new TreeSet<String>();
		String tempTable = "";
		for (int i = 0; i < listCode.size(); i++) {
			tempTable = (String) listCode.get(i).get("table_code");
			treeSet.add(tempTable);
		}
		return treeSet;
	}

	/**
	 * ����������� ����  sql ��ʵ��д��һ����ʱ�������ݿ�
	 */
	public int getLSSQL(String userid) throws RadowException, SQLException {
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		TreeSet<String> listTable = getTables();//��ȡ���е���Ϣ��
		boolean flag = false;
		String viewnametb="",temp = "",sqlSelect = "select ",tempTable = "",tempCode = "",tempName = "",qvidold = "",qrysqlold = "";
		int numrow = 0;//��ʶ�Ƿ�ѡ�������Ϣ�δѡ��Ĭ��ѡ��ȫ��
		//ƴ�Ӳ�ѯ�ֶ�
		for (int i = 0; i < listCode.size()&&listCode.size()>0; i++) {
			tempTable = (String) listCode.get(i).get("table_code");
			tempCode = (String) listCode.get(i).get("col_code");
			sqlSelect=sqlSelect + " " + tempTable + "." + tempCode + " "+ tempTable + tempCode +",";
		}
		sqlSelect = sqlSelect.toLowerCase();
		if(sqlSelect.indexOf("a01a0000")<0){//û��ѡ��a0000��Ϣ��
			sqlSelect=sqlSelect+" "+"a01"+"."+"a0000"+" a01a0000,";
		}
		if(sqlSelect.indexOf("a01a0101")<0){//û��ѡ��a0000��Ϣ��
			sqlSelect=sqlSelect+" "+"a01"+"."+"a0101"+" a01a0101,";
		}
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		if(sqlSelect.toLowerCase().contains("a01.a0195")){
			sqlSelect = sqlSelect.toLowerCase().replace("a01.a0195", " (select b0101 from b01 where b0111=a01.A0195) ");
		}
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		sqlSelect = sqlSelect.substring(0, sqlSelect.length() - 1) + " from ";

		String tabletemp = ""; 
		for (String string : listTable) {
			tabletemp = tabletemp + " " + string.toLowerCase() + ",";
		}
		String arr[] = null;
		if (listTable == null || listTable.size() == 0) {
			String parenttablename = this.getPageElement("parenttablename").getValue(); 
			arr = parenttablename.split("@");
			parenttablename = arr[1]; 
			parenttablename = parenttablename.replace("\'", "");
			tabletemp = tabletemp + " " + parenttablename.toLowerCase() + "  ";
		}
		tabletemp = tabletemp.substring(0, tabletemp.length() - 1);
		if (tabletemp != null && tabletemp.indexOf("b01") != -1) {// ѡ��b01�����ѡ��a02��a01��ǰ̨��ѡ����sql�Զ����
			if (tabletemp.indexOf("a01") == -1) {
				tabletemp = tabletemp + ",a01";
			}
			if (tabletemp.indexOf("a02") == -1) {
				tabletemp = tabletemp + ",a02";
			}
		}
		sqlSelect = sqlSelect + tabletemp;// ƴ�ӱ���
		sqlSelect = sqlSelect + " where 1=1 ";
		String tableid = "";// �����
		if (tabletemp != null && !tabletemp.equals("null") && tabletemp.trim().length() > 0) {
			for (String string : listTable) {
				if (!"a01".equals(string.toLowerCase()) && !"b01".equals(string.toLowerCase())) {
					tableid = tableid + " and a01.a0000=" + string.toLowerCase() + ".a0000 ";
				} else if ("b01".equals(string.toLowerCase())) {
					tableid = tableid + " and a02.a0201b=" + string.toLowerCase() + ".b0111 ";
				}
			}

			String arr1[] = null;
			if (arr != null) {
				temp = arr[1];
				temp = temp.replace("\\'", "");
				arr1 = temp.split(",");

			}
			for (int i = 0; arr1 != null && i < arr1.length; i++) {
				temp = arr1[i];
				if (!"a01".equals(temp.toLowerCase()) && !"b01".equals(temp.toLowerCase())) {
					tableid = tableid + " and a01.a0000=" + temp.toLowerCase() + ".a0000 ";
				} else if ("b01".equals(temp.toLowerCase())) { 
					tableid = tableid + " and a02.a0201b=" + temp.toLowerCase() + ".b0111 ";
				}
			}
		}
		sqlSelect = sqlSelect + tableid;
		sqlSelect = sqlSelect + " and a01.status!='4' " ;//ȥ����������
		try {
			//���Ȩ��
			QueryCommon common = new QueryCommon();
			String userid_system = HBUtil.getValueFromTab("userid", "smt_user", "loginname='system'");
			//String peoples = common.queryPowerPeople(userid);
			qvidold = this.getPageElement("qrysqlcc").getValue();
			String infoSetMax = common.queryInfoSetMax(qvidold);
			infoSetMax =" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid='"+userid+"' and t.type = '1')";
			//if(userid_system.equals(userid)){
				infoSetMax = " ";
			//}
			sqlSelect = sqlSelect + infoSetMax;
		}catch(Exception e){
			e.printStackTrace();
			this.setMainMessage("�޷���ȡȨ�ޣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			viewnametb = HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='"+qvidold+"'");
			if(!StringUtils.isEmpty(viewnametb.trim())){
				
				
				if(DBUtil.getDBType()==DBType.ORACLE){	
					qrysqlold = HBUtil.getValueFromTab("text", "user_views", "view_name='"+viewnametb.toUpperCase()+"'");
					if(!StringUtils.isEmpty(qrysqlold.trim())){
						qrysqlold = qrysqlold.substring(qrysqlold.indexOf("a01.status!='4'")+15);
					}
				}else{			//mysql
					//qrysqlold = HBUtil.getValueFromTab("VIEW_DEFINITION", "information_schema.views", "table_name='"+viewnametb.toUpperCase()+"'");
				}
				
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setMainMessage("��ȡ��ͼʧ�ܣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String whereConditionStr = this.getPageElement("conditionStr").getValue(); 
		String qrysql = "";
		if (whereConditionStr != null && !whereConditionStr.trim().equals("") && !whereConditionStr.trim().equals("null")) {
			qrysql = sqlSelect + " and (" + whereConditionStr + ") "; //Ϊ�����������
		} else {
			qrysql = sqlSelect;
		}
		// �滻�����е������
		qrysql = qrysql.replace("{v}", "").replace("{%v}", "").replace("{v%}", "").replace("{%v%}", "");
		// �滻�ظ��������ֶ�
		qrysql = qrysql.toLowerCase();
		qrysql = qrysql + " " + qrysqlold;//ƴ��ԭ����
		//����ƴ������Ƿ���ȷ
		HBSession hbsess1 = HBUtil.getHBSession();	
		Connection connection1 = hbsess1.connection();
		Statement stmt1 = connection1.createStatement();
		try{
			String tmepsql=qrysql+ " and 1=2 ";
			stmt1.executeQuery(tmepsql);
			stmt1.close();
		}catch(SQLException e){
			e.printStackTrace();
			this.setMainMessage("���ɵ�����﷨����������ӵ�����! "+e.getMessage());
			return EventRtnType.NORMAL_SUCCESS;
		}finally{
			try{
				if(stmt1!=null){
					stmt1.close();
				}
				if(connection1!=null){
					connection1.close();
				}
			}catch(Exception e2){
				
			}
		}
		this.getPageElement("querysql").setValue(qrysql);
		HBSession hbSession = HBUtil.getHBSession();
		//ָ����ϲ�ѯǰ��ɾ����ǰ�û�֮ǰ���������ͼ
		
		
		try {
			String qvid = HBUtil.getValueFromTab("qvid", "qryview", "userid='"+userid+"' and type='2'");
			//�����в�ѯɾ������
//			String delZHQryview = "delete from qryview where userid='"+userid+"' and type='2'";
//			hbSession.createSQLQuery(delZHQryview).executeUpdate();
			viewnametb=HUtil.getSequence("sq_qryview");
			viewnametb="V_qv"+viewnametb;
			Qryview qryview = new Qryview();
			qryview.setType("2");
			qryview.setViewnametb(viewnametb);
			qryview.setUserid(userid);
			qryview.setQrysql(qrysql);
			hbSession.save(qryview);
			CommonQueryBS.systemOut("--------->>>"+DateUtil.dateToString(new Date(), "yyyyMMdd hh:mm:ss")+":"+qrysql);
			this.getPageElement("qrysqlcc").setValue(qryview.getQvid());
			hbSession.flush();
			String delSqlForqryf = "delete from qryviewfld where qvid = '"+qvid+"'";
			hbSession.createSQLQuery(delSqlForqryf).executeUpdate();
			//������Ӧ����Ϣ
			Qryviewfld qryviewfld = null;
			for (int i = 0; i < listCode.size(); i++) {
				qryviewfld = new Qryviewfld();
				tempTable = (String) listCode.get(i).get("table_code");
				tempCode = (String) listCode.get(i).get("col_code");
				tempName = (String) listCode.get(i).get("col_name");
				qryviewfld.setQvid(qryview.getQvid());
				qryviewfld.setTblname(tempTable);
				qryviewfld.setFldname(tempCode);
				qryviewfld.setFldnum(i+"");
				qryviewfld.setFldnamenote(tempName);
				hbSession.save(qryviewfld);
				hbSession.flush();
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		//����sql��Ӧ����ͼ
		HBSession hbsess = HBUtil.getHBSession();	
		Connection connection = hbsess.connection();
		Statement stmt = connection.createStatement();
		String viewSql = "create or replace view "+ viewnametb +" as "+qrysql;
		stmt.execute(viewSql);
		stmt.close();
		connection.close();
		return -10;
	}

	/**
	 * ���ҳ���ֵ
	 */
	@PageEvent("clearValue")
	public int clearValue() throws RadowException, AppException {
		this.getPageElement("conditionName9").setValue("");
		this.getPageElement("conditionName71").setValue("");
		this.getPageElement("conditionName7").setValue("");
		this.getPageElement("conditionName61").setValue("");
		this.getPageElement("conditionName611").setValue("");
		this.getPageElement("conditionName6").setValue("");
		this.getPageElement("conditionName5").setValue("");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		((Combo) this.getPageElement("conditionName5")).setValueListForSelect(map);
		this.getPageElement("conditionName4").setValue("");
		CommQuery commQuery = new CommQuery();
		List<HashMap<String, Object>> list = commQuery.getListBySQL(" select * from dual where 1=2 ");
		this.getPageElement("codeList2Grid").setValueList(list);
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �������� ���ض�Ӧ��ť 1.�� 2.���� 3.���� ��ʾ��Ӧ��ť 4.ѡ������ 5.�� 6.��
	 */
	@PageEvent("refreshDis")
	public int refreshDis() {
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true); 
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true); 
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true); 
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false); 
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false); 
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false); 
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ѯ��ͼ�б�
	 */
	@PageEvent("viewListGrid.dogridquery")
	@NoRequiredValidate
	public int doGridQuery(int start, int limit) throws RadowException,AppException {
		String userid=SysUtil.getCacheCurrentUser().getId();
		String userid_system = HBUtil.getValueFromTab("userid", "smt_user", "loginname='system'");
		String qvids= " and qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1')";
		//if(userid_system.equals(userid)){
			qvids = " ";
		//}
		String sql = "select qvid, q.conditions, chinesename from qryview q where type='1'" + qvids;
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * ˫��ָ���� copy��ҳ������ָ����
	 */
	@PageEvent("rowFldeDbClick")
	public int rowFldeDbClick(String qvfid) throws RadowException {
		try {
			HBSession session = HBUtil.getHBSession();
			Qryviewfld qryviewfld = (Qryviewfld) session.get(Qryviewfld.class, qvfid);
			this.getPageElement("conditionName4").setValue(qryviewfld.getFldname());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ѡ����ͼ ��ѯָ���� & �Լ���������
	 */
	@PageEvent("selqvid")
	public int selqvid(String qvid) throws AppException, RadowException {
		CommQuery commQuery = new CommQuery();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String userid_system = HBUtil.getValueFromTab("userid", "smt_user", "loginname='system'");
		String items= " and concat(concat(c.table_code,'.'),c.col_code)  in (select concat(concat(t.table_code,'.'),t.col_code) from COMPETENCE_USERTABLECOL t where t.userid in ('"+Utils.getRoleId(userid)+"') and t.islook='1')";
		//if(userid_system.equals(userid)){
			items = " ";
		//}
		String sql = "select concat(concat(concat(concat(c.table_code, '.'), c.col_code), ' '), c.col_name) col_name1, "
				+ "c.col_name, "
				+ "c.code_type, "
				+ "c.col_data_type, "
				+ "c.col_data_type_should, "
				+ "c.col_code,'' caozuo, "
				+ "c.table_code from qryviewfld q left join code_table_col c "
				+ "on q.fldname = c.col_code and q.tblname=c.table_code "
				+ "where 1=1 and q.qvid ='"
				+ qvid
				+ "' "
				/*+ " and ISUSE = '1' "*/
				/*+ " and ISLOOK = '1' "*/
				+ items
				+ "order by q.fldnum asc";
		System.out.println("ִ�еĲ�ѯ��ͼʹ�õ���Ϣ��|-------->"+sql);
		List<HashMap<String, Object>> list1 = commQuery.getListBySQL(sql);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		this.getPageElement("codeList2Grid").setValueList(list);
		this.getPageElement("codeList2Grid1").setValueList(list1);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ����,��ղ�ѯ������Ϣ
	 */
	@PageEvent("clearqvfid")
	public int clearqvfid() {
		this.clearDivData("area44");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ���������Ϣ
	 */
	@PageEvent("createqvfid")
	public int createqvfid() {
		this.clearDivData("area44");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������������ѡ����ֵ
	 */
	@PageEvent("selectValueList")
	public int selectValueList() throws AppException, RadowException {
		try {
			conditionclear();// �������
			String col_name1 = this.getPageElement("col_name_quan").getValue();
			this.getPageElement("conditionName4").setValue(col_name1);
			CommQuery commQuery = new CommQuery();
			String sql = "select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where code_value not like '%like%' "
					+ " and sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			List<HashMap<String, Object>> list2 = commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for (int i = 0; i < list2.size(); i++) {
				map.put(list2.get(i).get("code_value").toString(), list2.get(i).get("code_name"));
			}
			((Combo) this.getPageElement("conditionName5")).setValueListForSelect(map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������������ѡ����ֵ
	 */
	@PageEvent("selectValueListLike")
	public int selectValueListLike() throws AppException, RadowException {
		try {
			conditionclear(); 
			String col_name1 = this.getPageElement("col_name_quan").getValue();
			this.getPageElement("conditionName4").setValue(col_name1);
			CommQuery commQuery = new CommQuery();
			String sql = "select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where sub_code_value!='11' "
					+ " and t.code_type='OPERATOR'";
			List<HashMap<String, Object>> list2 = commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for (int i = 0; i < list2.size(); i++) {
				map.put(list2.get(i).get("code_value").toString(), list2.get(i).get("code_name"));
			}
			((Combo) this.getPageElement("conditionName5")).setValueListForSelect(map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ѡ�����������ֶΣ�������ѡ�ؼ���ֵ
	 */
	@PageEvent("code_type_value1")
	public int code_type_value1(String code_type) throws AppException,RadowException {
		conditionclear();//�������
		String col_name1 = this.getPageElement("col_name_quan").getValue();
		this.getPageElement("conditionName4").setValue(col_name1);
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where  "
				+ " sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		selectValue1List(code_type);//ֵһ
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �������
	 */
	@PageEvent("conditionclear")
	public int conditionclear() throws RadowException {
		this.getPageElement("conditionName4").setValue("");//����ָ����
		this.getPageElement("conditionName5").setValue("");//��������
		this.getPageElement("conditionName6").setValue("");//ֵһ
		this.getPageElement("conditionName6_combotree").setValue("");//ֵһ
		this.getPageElement("conditionName61").setValue("");//ֵһ
		this.getPageElement("conditionName611").setValue("");//ֵһ
		this.getPageElement("conditionName6111").setValue("");//ֵһ
		this.getPageElement("conditionName7").setValue("");//ֵ��
		this.getPageElement("conditionName71").setValue("");//ֵ��
		this.getPageElement("conditionName71_combotree").setValue("");//ֵ��
		this.getPageElement("conditionName711").setValue("");//ֵ��
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ֵ1 ����ѡ����ֵ
	 */
	@PageEvent("selectValue1List")
	public int selectValue1List(String code_type) throws RadowException {
		try{
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static String replaceSpecial(String str){
		String temp=str
		.replace("\"", "")  
		.replace("\'", "")  
		.replace("\\", "")
		.replace("/", "")
		.replace("\n", "")  
		.replace("\r", "")
		;
		return temp;
	}
	/**
	 * ��ӵ��б�
	 */
	@PageEvent("addtolistfunc")
	public int addtolistfunc() throws RadowException, AppException {
		String col_data_type_should = this.getPageElement("col_data_type_should").getValue();//varchar2
		String code_type = this.getPageElement("code_type").getValue();//ZB01
		String col_data_type=this.getPageElement("col_data_type").getValue();//C  --��ʾ�Ŀؼ�����--�ൺ
		if (col_data_type_should == null || col_data_type_should.trim().length() == 0) {
			this.setMainMessage("��˫��ָ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		col_data_type_should = col_data_type_should.toLowerCase();
		String conditionName2 = this.getPageElement("table_code").getValue();//A01
		String conditionName3 = this.getPageElement("col_code").getValue();//A0104
		String conditionName4 = this.getPageElement("col_name").getValue();//�Ա�
		String conditionName5 = this.getPageElement("conditionName5").getValue();//={v}
		String conditionName51 = HUtil.getValueFromTab("code_name","code_value", "code_type='OPERATOR' and code_value='" + conditionName5 + "' ");//����
		String value1 = "";
		String value11 = "";
		String value2 = "";
		String value21 = "";
		if (code_type != null && !code_type.trim().equals("") && !code_type.equals("null")) {
			value1 = this.getPageElement("conditionName6").getValue();//1
			value2 = this.getPageElement("conditionName71").getValue();//1
			value11 = HUtil.getValueFromTab("code_name","code_value","code_type='" + code_type.toUpperCase() + "' and code_value='" + value1 + "'");//����
			//��ֹ����null 
			value1=value1==null||value1=="null"?"":value1;
			value11=value11==null||value11=="null"?"":value11;
			value21 = HUtil.getValueFromTab("code_name","code_value","code_type='" + code_type.toUpperCase() + "' and code_value='" + value2 + "'");//����
			value2=value2==null||value2=="null"?"":value2;
			value21=value21==null||value21=="null"?"":value21;
		} else if (col_data_type_should == null || col_data_type_should.equals("clob") || col_data_type_should.equals("varchar2") || col_data_type_should.equals("null") || col_data_type_should.trim().equals("")) {
			value1 = this.getPageElement("conditionName611").getValue();
			value2 = this.getPageElement("conditionName711").getValue();
		} else if ("date".equals(col_data_type_should.toLowerCase())) {
			value1 = this.getPageElement("conditionName61").getValue();
			value2 = this.getPageElement("conditionName7").getValue();
		}
		if (code_type != null && !code_type.trim().equals("") && !code_type.equals("null")) {
			if(conditionName5.contains("between")){
				this.getPageElement("conditoionlist").setValue(conditionName4 + "  �� " + value11 + " �� " + value21 + " ֮��");
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4 + "  " + conditionName51 + "  " + value11);
			}
		} else {
			if(conditionName5.contains("between")){
				this.getPageElement("conditoionlist").setValue(conditionName4 + "  " + conditionName51 + "  " + value11 + "  " + value21);
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4 + "  " + conditionName51 + "  " + value1);
			}
		}
		this.getPageElement("for_table_qryuse").setValue(conditionName4+","+conditionName3+","+conditionName2+","+value11+","+value1+","+col_data_type+","+value21+","+conditionName5+"@");
		this.getExecuteSG().addExecuteCode("textareadd();");// ��ӵ��б�
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ѡ��is null ���� is not null
	@PageEvent("setValue1And2Disable")
	public int setValue1And2Disable() throws RadowException{
		clearValue1And2();//���ֵ һ  ֵ��
		//this.createPageElement("conditionName6", ElementType.SELECT, false).setDisabled(true);
		//this.createPageElement("conditionName61", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName6111", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//���ֵ һ  ֵ��
	public void clearValue1And2() throws RadowException{
		this.getPageElement("conditionName6").setValue("");
		this.getPageElement("conditionName6_combotree").setValue("");
		this.getPageElement("conditionName61").setValue("");
		this.getPageElement("conditionName611").setValue("");
		this.getPageElement("conditionName6111").setValue("");
		this.getPageElement("conditionName7").setValue("");
		this.getPageElement("conditionName71").setValue("");
		this.getPageElement("conditionName71_combotree").setValue("");
		this.getPageElement("conditionName711").setValue("");
	}
	/**
	 * ����ֵ2 date
	 */
	@PageEvent("setValue2Disable")
	public int setValue2Disable(){
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);//����
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ֵ2 number
	 */
	@PageEvent("setValue211Disable")
	public int setValue211Disable(){
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);//����
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����ֵ2 select
	 */
	@PageEvent("setValue21Disable")
	public int setValue21Disable(String code_type) throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
		this.getExecuteSG().addExecuteCode("setTree2('"+code_type+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����  
	 */
	@PageEvent("selectValueListLikeBt")
	public int selectValueListLikeBt() throws RadowException, AppException{
		conditionclear();// �������
		String col_name1 = this.getPageElement("col_name_quan").getValue();
		this.getPageElement("conditionName4").setValue(col_name1);
		//�������
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where  "
				+ " sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 *�ı�����ֵ  ��like ��between and
	 */
	@PageEvent("selectValueListNobt")
	public int selectValueListNobt() throws AppException, RadowException{
		conditionclear();// �������
		String col_name1 = this.getPageElement("col_name_quan").getValue();
		this.getPageElement("conditionName4").setValue(col_name1);
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where code_value not like '%between%' "
				+ " and sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}