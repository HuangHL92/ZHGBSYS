package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class TwoDStatisticsPageModel extends PageModel{
	public static Map<String, String> ewdata= new LinkedHashMap<String, String>();
	private static ArrayList<String> VERT_VALUE_LIST = new ArrayList<String>();
	private static ArrayList<String> TRAN_VALUE_LIST = new ArrayList<String>();
	private static String ID = null;
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		ewdata.clear();
		VERT_VALUE_LIST.clear();
		TRAN_VALUE_LIST.clear();
		String  sql = "select ts001,ts002 from TWODSTATISTICSINFO_SET ";	
		this.getPageElement("tran_length").setValue("3"); 	//���ú����ʼ����
		this.getPageElement("vert_length").setValue("3");	//���������ʼ����
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] oo=null;
		if(list != null && ! list.isEmpty()){
			for(int i=0;i<list.size();i++){
				oo=list.get(i);
				map.put(""+oo[0], oo[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		((Combo)this.getPageElement("transverse")).setValueListForSelect(map); 
		((Combo)this.getPageElement("vertical")).setValueListForSelect(map);
		
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		if(param.contains("#")){
			this.setNextEventName("ewedit");
		}
		this.getExecuteSG().addExecuteCode("first_load()");
	 	return EventRtnType.NORMAL_SUCCESS;
	}
	
	//������Ϣ���ı��¼�
	@PageEvent("tran_set_change")
	public int tran_change() throws RadowException{
		Integer tran_length = Integer.parseInt(this.getPageElement("tran_length").getValue());
		for(int i = 0; i < tran_length; i++){	
			((Combo)this.getPageElement("tran"+i)).setValue("");
		}
		
		//��ú�����Ϣ��id
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		//���������Ϣ��id
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		if(transverseStatisticsID.equals(verticalStatisticsID)){
			this.setMainMessage("������Ϣ�������ظ�,������ѡ��");
			((Combo)this.getPageElement("transverse")).setValue("");
			for(int i = 0 ;i < tran_length; i++){	
				((Combo)this.getPageElement("tran"+i)).setValue("");
				((Combo)this.getPageElement("tran"+i)).setValueListForSelect(new LinkedHashMap<String, Object>());
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		String tran_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			tran_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t";
		}else{
			tran_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t ";
		}
		Map<String, Object> tran_map = new LinkedHashMap<String, Object>();
		List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_sql).list();
		Object[] oo_tran=null;
		tran_map.put("delete", "ɾ������");
		if(list_tran != null && ! list_tran.isEmpty()){
			for(int i=0;i<list_tran.size();i++){
				oo_tran=list_tran.get(i);
				tran_map.put(""+oo_tran[0], oo_tran[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		for(int i = 0; i < tran_length; i++){	
			((Combo)this.getPageElement("tran"+i)).setValueListForSelect(tran_map); 
		}
		this.getExecuteSG().addExecuteCode("recovery_tran()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//������Ϣ���ı��¼�
	@PageEvent("vert_set_change")
	public int vert_change() throws RadowException{
		Integer vert_length = Integer.parseInt(this.getPageElement("vert_length").getValue());
		for(int i = 0; i < vert_length; i++){	
			((Combo)this.getPageElement("vert"+i)).setValue("");
		}
		//��ú�����Ϣ��id
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		//���������Ϣ��id
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		if(transverseStatisticsID.equals(verticalStatisticsID)){
			this.setMainMessage("������Ϣ�������ظ�,������ѡ��");
			((Combo)this.getPageElement("vertical")).setValue("");
			for(int i = 0; i < vert_length; i++){	
				((Combo)this.getPageElement("vert"+i)).setValue("");
				((Combo)this.getPageElement("vert"+i)).setValueListForSelect(new LinkedHashMap<String, Object>());
			}
			
			return EventRtnType.NORMAL_SUCCESS;
		}
		String vert_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			vert_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t";
		}else{
			vert_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t ";
		}
		Map<String, Object> vert_map = new LinkedHashMap<String, Object>();
		List<Object[]>   list_vert = HBUtil.getHBSession().createSQLQuery(vert_sql).list();
		Object[] oo_vert=null;
		vert_map.put("delete", "ɾ������");
		if(list_vert != null && ! list_vert.isEmpty()){
			for(int i=0;i<list_vert.size();i++){
				oo_vert=list_vert.get(i);
				vert_map.put(""+oo_vert[0], oo_vert[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		for(int i = 0; i < vert_length; i++){	
			((Combo)this.getPageElement("vert"+i)).setValueListForSelect(vert_map);
		}
		this.getExecuteSG().addExecuteCode("recovery_vert()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//������Ϣ��ı��¼�
	@PageEvent("tran_item_change")
	public int tran_item_change(String num) throws RadowException{
		TRAN_VALUE_LIST.clear();
		Integer tran_length = Integer.parseInt(this.getPageElement("tran_length").getValue());
		String tran_num = this.getPageElement("tran"+num).getValue();
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		String tran_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			tran_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t";
		}else{
			tran_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t ";
		}
		Map<String, Object> tran_map = new LinkedHashMap<String, Object>();
		List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_sql).list();
		Object[] oo_tran=null;
		tran_map.put("delete", "ɾ������");
		if(list_tran != null && ! list_tran.isEmpty()){
			for(int i=0;i<list_tran.size();i++){
				oo_tran=list_tran.get(i);
				tran_map.put(""+oo_tran[0], oo_tran[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		if("delete".equals(tran_num)){
			if(tran_length > 3){
				if(Integer.parseInt(num) == tran_length - 1 && tran_length < 15){
					this.getPageElement("tran"+num).setValue("");
					this.setMainMessage("ɾ����Ч,β������ɾ��");
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					this.getExecuteSG().addExecuteCode("tran_delete_message('"+num+"');");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}else{
				this.getPageElement("tran"+num).setValue("");
				this.setMainMessage("ɾ����Ч,����������������");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		for(int i = 0; i < tran_length; i++){
			if(i == Integer.parseInt(num)){
				continue;
			}
			String tran = this.getPageElement("tran"+i).getValue();
			
			if(tran.equals(tran_num)){
				this.setMainMessage("ͳ����Ϣ����ظ�,������ѡ��");
				((Combo)this.getPageElement("tran"+num)).setValue("");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(Integer.parseInt(num) == tran_length - 1 && tran_length < 15){
			Integer new_tran_length = tran_length + 1;
			this.getPageElement("tran_length").setValue(new_tran_length.toString());
			this.getPageElement("tran"+tran_length).setValue("");
			((Combo)this.getPageElement("tran"+tran_length)).setValueListForSelect(tran_map); 
			this.getExecuteSG().addExecuteCode("tran_item_add()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//������Ϣ��ı��¼�
	@PageEvent("vert_item_change")
	public int vert_item_change(String num) throws RadowException{
		VERT_VALUE_LIST.clear();
		Integer vert_length = Integer.parseInt(this.getPageElement("vert_length").getValue());
		String vert_num = this.getPageElement("vert"+num).getValue();
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		String vert_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			vert_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t";
		}else{
			vert_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t ";
		}
		Map<String, Object> vert_map = new LinkedHashMap<String, Object>();
		List<Object[]>   list_vert = HBUtil.getHBSession().createSQLQuery(vert_sql).list();
		Object[] oo_vert=null;
		vert_map.put("delete", "ɾ������");
		if(list_vert != null && ! list_vert.isEmpty()){
			for(int i=0;i<list_vert.size();i++){
				oo_vert=list_vert.get(i);
				vert_map.put(""+oo_vert[0], oo_vert[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		if("delete".equals(vert_num)){
			if(vert_length > 3){
				if(Integer.parseInt(num) == vert_length - 1 && vert_length < 15){
					this.getPageElement("vert"+num).setValue("");
					this.setMainMessage("ɾ����Ч,β������ɾ��");
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					this.getExecuteSG().addExecuteCode("vert_delete_message('"+num+"');");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}else{
				this.getPageElement("vert"+num).setValue("");
				this.setMainMessage("ɾ����Ч,����������������");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		for(int i = 0; i < vert_length; i++){
			if(i == Integer.parseInt(num)){
				continue;
			}
			String vert = this.getPageElement("vert"+i).getValue();
			
			if(vert.equals(vert_num)){
				this.setMainMessage("ͳ����Ϣ����ظ�,������ѡ��");
				((Combo)this.getPageElement("vert"+num)).setValue("");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(Integer.parseInt(num) == vert_length - 1 && vert_length < 15){
			Integer new_vert_length = vert_length + 1;
			this.getPageElement("vert_length").setValue(new_vert_length.toString());
			this.getPageElement("vert"+vert_length).setValue("");
			((Combo)this.getPageElement("vert"+vert_length)).setValueListForSelect(vert_map); 
			this.getExecuteSG().addExecuteCode("vert_item_add()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//������Ϣ��ɾ���¼�
	@PageEvent("tran_item_delete")
	public int tran_item_delete(String num) throws RadowException{
		TRAN_VALUE_LIST.clear();
		Integer tran_length = Integer.parseInt(this.getPageElement("tran_length").getValue());
		String tran_num = this.getPageElement("tran"+num).getValue();
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		String tran_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			tran_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t";
		}else{
			tran_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+transverseStatisticsID+"' order by t ";
		}
		Map<String, Object> tran_map = new LinkedHashMap<String, Object>();
		List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_sql).list();
		Object[] oo_tran=null;
		tran_map.put("delete", "ɾ������");
		if(list_tran != null && ! list_tran.isEmpty()){
			for(int i=0;i<list_tran.size();i++){
				oo_tran=list_tran.get(i);
				tran_map.put(""+oo_tran[0], oo_tran[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		for(int i = 0; i < tran_length; i++){
			if(i == Integer.parseInt(num)){
				continue;
			}
			String tran = this.getPageElement("tran"+i).getValue();
			TRAN_VALUE_LIST.add(tran);
		}
		Integer new_tran_length = tran_length - 1;
		for(int i = 0; i < new_tran_length; i++){
			String tran_key = TRAN_VALUE_LIST.get(i);
			if("".equals(tran_key)){
				this.getPageElement("tran"+i).setValue("");
			}else{
				this.getPageElement("tran"+i).setValue(tran_key);
			}
		}
		((Combo)this.getPageElement("tran"+new_tran_length)).setValueListForSelect(new LinkedHashMap<String, Object>());
		if(tran_length == 15 && this.getPageElement("tran"+new_tran_length).getValue() != null && this.getPageElement("tran"+new_tran_length).getValue().length() > 0){
			this.getPageElement("tran"+new_tran_length).setValue("");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("tran_length").setValue(new_tran_length.toString());
		this.getExecuteSG().addExecuteCode("tran_item_delete()");
		return EventRtnType.NORMAL_SUCCESS;
	}	
	//������Ϣ��ɾ���¼�
	@PageEvent("vert_item_delete")
	public int vert_item_delete(String num) throws RadowException{
		VERT_VALUE_LIST.clear();
		Integer vert_length = Integer.parseInt(this.getPageElement("vert_length").getValue());
		String vert_num = this.getPageElement("vert"+num).getValue();
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		String vert_sql = null ;
		if(DBUtil.getDBType()==DBType.ORACLE){
			vert_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t";
		}else{
			vert_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+verticalStatisticsID+"' order by t ";
		}
		Map<String, Object> vert_map = new LinkedHashMap<String, Object>();
		List<Object[]>   list_vert = HBUtil.getHBSession().createSQLQuery(vert_sql).list();
		Object[] oo_vert=null;
		vert_map.put("delete", "ɾ������");
		if(list_vert != null && ! list_vert.isEmpty()){
			for(int i=0;i<list_vert.size();i++){
				oo_vert=list_vert.get(i);
				vert_map.put(""+oo_vert[0], oo_vert[1]);
			}
		}else{
			this.setMainMessage("�����쳣");
		}
		
		for(int i = 0; i < vert_length; i++){
			if(i == Integer.parseInt(num)){
				continue;
			}
			String vert = this.getPageElement("vert"+i).getValue();
			VERT_VALUE_LIST.add(vert);
		}
		Integer new_vert_length = vert_length - 1;
		for(int i = 0; i < new_vert_length; i++){
			String vert_key = VERT_VALUE_LIST.get(i);
			if("".equals(vert_key)){
				this.getPageElement("vert"+i).setValue("");
			}else{
				this.getPageElement("vert"+i).setValue(vert_key);
			}
		}
		((Combo)this.getPageElement("vert"+new_vert_length)).setValueListForSelect(new LinkedHashMap<String, Object>());
		if(vert_length == 15 && this.getPageElement("vert"+new_vert_length).getValue() != null && this.getPageElement("vert"+new_vert_length).getValue().length() > 0){
			this.getPageElement("vert"+new_vert_length).setValue("");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("vert_length").setValue(new_vert_length.toString());
		this.getExecuteSG().addExecuteCode("vert_item_delete()");
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	//������水ť
	@PageEvent("save.onclick")
	public int save() throws RadowException{
		ewdata.clear();
		ID = UUID.randomUUID().toString();
		//��ú�����Ϣ��id
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		//���������Ϣ��id
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		ewdata.put("transverseStatisticsID", transverseStatisticsID);
		ewdata.put("verticalStatisticsID", verticalStatisticsID);
		Integer vert_length = Integer.parseInt(this.getPageElement("vert_length").getValue());
		Integer tran_length = Integer.parseInt(this.getPageElement("tran_length").getValue());
		if("".equals(transverseStatisticsID) || "".equals(verticalStatisticsID)){
			this.setMainMessage("����ѡ��ͳ����Ϣ������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int temp = 0;
		for(int i = 0; i <  vert_length; i++){
			String vert = this.getPageElement("vert"+i).getValue();
			ewdata.put("vert"+i, vert);
			if("".equals(vert)){
				temp++;
			}
			if(temp == vert_length){
				this.setMainMessage("����ѡ������ͳ����Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;	
			}
		}
		temp = 0;
		for(int i = 0; i < tran_length; i++){
			String tran = this.getPageElement("tran"+i).getValue();
			ewdata.put("tran"+i, tran);
			if("".equals(tran)){
				temp++;
			}
			if(temp == tran_length){
				this.setMainMessage("����ѡ�����ͳ����Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;	
			}
		}
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		if(param.contains("#")){
			String qc001 = param.split("#")[3];
			HBSession sess = HBUtil.getHBSession();
			Map<String,String> map = TwoDStatisticsPageModel.ewdata;
			String QC003_tran = transverseStatisticsID+"@";
			String QC003_vert = verticalStatisticsID+"@";
			Integer tran_value_length = 0;
			Integer vert_value_length = 0;
			for(int i = 0; i < tran_length; i++){
				String tran_i = map.get("tran"+i);
				if(tran_i !=null && tran_i.length()>0){
					QC003_tran += tran_i+"@";
					tran_value_length++;
				}
			}
			QC003_tran = QC003_tran.substring(0,QC003_tran.length()-1);
			for(int i = 0; i < vert_length; i++){
				String vert_i = map.get("vert"+i);
				if(vert_i !=null && vert_i.length()>0){
					QC003_vert += vert_i+"@";
					vert_value_length++;
				}
			}
			QC003_vert = QC003_vert.substring(0,QC003_vert.length()-1);
			String QC003 = QC003_vert + "|" + QC003_tran + "|" + vert_value_length + "|" + tran_value_length; 
			String sql1 = "UPDATE query_condition SET  qc003 = '"+QC003+"' WHERE qc001 = '"+qc001+"'";
			sess.createSQLQuery(sql1).executeUpdate();
			this.setMainMessage("����ɹ�");
			this.closeCueWindowByYes("TwoDStatistics");
		}else{
			this.getExecuteSG().addExecuteCode("save('2@"+vert_length+"@"+tran_length+"@"+ID+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//���ͳ�ư�ť
	@PageEvent("statistics.onclick")
	public int statistics() throws RadowException{
		String SQ002 = null;
		HBSession sess = HBUtil.getHBSession();
		//��ú�����Ϣ��id
		String transverseStatisticsID = this.getPageElement("transverse").getValue();
		//���������Ϣ��id
		String verticalStatisticsID = this.getPageElement("vertical").getValue();
		if("".equals(transverseStatisticsID) || "".equals(verticalStatisticsID)){
			this.setMainMessage("ͳ����Ϣ������Ϊ�գ���ѡ��ͳ����Ϣ����ͳ����Ϣ��");
			return 0;
		}
		Integer vert_length = Integer.parseInt(this.getPageElement("vert_length").getValue());
		Integer tran_length = Integer.parseInt(this.getPageElement("tran_length").getValue());
		int temp = 0;
		for(int i = 0; i <  vert_length; i++){
			String vert = this.getPageElement("vert"+i).getValue();
			
			if("".equals(vert)){
				temp++;
			}
			if(temp == vert_length){
				this.setMainMessage("������ѡ��һ������ͳ����Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;	
			}
		}
		temp = 0;
		for(int i = 0; i < tran_length; i++){
			String tran = this.getPageElement("tran"+i).getValue();
			if("".equals(tran)){
				temp++;
			}
			if(temp == tran_length){
				this.setMainMessage("������ѡ��һ�����ͳ����Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;	
			}
		}
		String groupid = "";
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		if(param.contains("#")){
			String[] param_arr = param.split("#");
			groupid = param_arr[2].replace("|", "'");
			SQ002 = param_arr[4];
		}else{
			groupid = this.getPageElement("subWinIdBussessId2").getValue().replace("|", "'");
		}
		String querysql = "" ;
		boolean mark = false ;
		if("8".equals(transverseStatisticsID) || "8".equals(verticalStatisticsID)){
			mark = true;
			groupid = groupid.split("group")[0];
			querysql = "select count(1) from ( select a.*,b.a0801b from ( SELECT a01.a0000,a01.a0104,"
					+ "a01.a0160,a01.a0117,a01.a0141,a01.a0148,a01.a0192D,a02.a0219 "
					+ "FROM a01,a02,competence_userdept cs WHERE a01. STATUS = '1' "
					+ "AND a01.a0000 = a02.a0000 AND a02.a0255 = '1' "
					+ "AND cs.userid = '"+SysManagerUtils.getUserId()+"' "
					+ "AND NOT EXISTS ( SELECT 1 FROM COMPETENCE_USERPERSON cu WHERE cu.a0000 = a01.a0000 "
					+ "AND cu.userid = '"+SysManagerUtils.getUserId()+"' ) "
					+ "AND a02.A0201B = cs.B0111 groupid ) a "
					+ "left join a08 b on a.a0000 = b.a0000  AND b.a0834 = '1') d "
					+ "where 1=1 ";
			querysql = querysql.replace("groupid",groupid);
		}else{
			querysql = "SELECT a01.a0000 FROM a01,a02,a08,competence_userdept cu "
					+ "WHERE a01.a0000 = a02.a0000 "
					+ "and not exists (select 1 from COMPETENCE_USERPERSON cu "
					+ "				   where cu.a0000 = a01.a0000 "
					+ "				   and cu.userid = '"+SysManagerUtils.getUserId()+"' ) "
					+ "and a02.A0201B=cu.B0111 "
					+ "and cu.userid = '"+SysManagerUtils.getUserId()+"' "
					+ "and a01.a0000=a08.a0000 "
					+ "and a02.a0255 = '1'  "
					+ "and a01. STATUS = '1' ";
		}
		Integer vert_num = 0;
		Integer tran_num = 0;
		String vert_name = "";
		String tran_name = "";
		String data = "";
		String querysql_do = null;
		String querysql_con = null;
		Integer maxvalue = 0;
		for(int i = 0; i < vert_length; i++){
			String vert_i = this.getPageElement("vert"+i).getValue();
			if(vert_i !=null && vert_i.length()>0){
				String vert_i_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+vert_i+"' ";
				List<Object[]>  list_vert = HBUtil.getHBSession().createSQLQuery(vert_i_sql).list();
				Object[] oo_vert=null;
				if(list_vert != null && ! list_vert.isEmpty()){
					oo_vert = list_vert.get(0);
					if(mark){
						String sql_v = oo_vert[1].toString().replaceAll("\\w\\w\\w\\.", "d.");
						querysql_con = querysql + sql_v;
					}else{
						querysql_con = querysql + oo_vert[1]+" ";
					}
					vert_name += oo_vert[0]+"@";
					vert_num += 1;
				}else{
					this.setMainMessage("�����쳣");
				}
				for(int j = 0; j < tran_length; j++){
					String tran_j = this.getPageElement("tran"+j).getValue();
					if(tran_j !=null && tran_j.length()>0){
						String tran_j_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+tran_j+"' ";
						List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_j_sql).list();
						Object[] oo_tran=null;
						if(list_tran != null && ! list_tran.isEmpty()){
							oo_tran = list_tran.get(0);
							if(mark){
								String sql_t = oo_tran[1].toString().replaceAll("\\w\\w\\w\\.", "d.");
								querysql_do = querysql_con + sql_t;
							}else{
								querysql_do = querysql_con + oo_tran[1];
							}
							if(vert_num==1){
								tran_name += oo_tran[0]+"@";
								tran_num += 1;
							}
						}else{
							this.setMainMessage("�����쳣");
							}
						String number = "";
						if(mark){
							number = sess.createSQLQuery(querysql_do).uniqueResult().toString();
						}else if(DBUtil.getDBType()==DBType.ORACLE){
							//number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
							number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+")").uniqueResult().toString();
						}else{
							//number = sess.createSQLQuery("select count(1) from ( "+tj+" ) as t").uniqueResult().toString();
							number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+") as t").uniqueResult().toString();
						}
						data += number + "@";
						if(Integer.parseInt(number)>maxvalue){
							maxvalue = Integer.parseInt(number);
						}
					}
				}
				if(data.length()>0 && data.substring(data.length()-1).equals("@")){
					data = data.substring(0,data.length()-1);
					data += "#";
				}
			}
		}	
		/*String pass1 = "tran_name:'"+tran_name.substring(0,tran_name.length()-1)+"','tran_num':'"+tran_num+"',"
					+ "'vert_name':'"+vert_name.substring(0,vert_name.length()-1)+"','vert_num':'"+vert_num+"',"
					+ "'data':'"+data.substring(0,data.length()-1)+""; */
		if(param.contains("#")){
			String pass = tran_name.substring(0,tran_name.length()-1)+"$"+tran_num+"$"
					+vert_name.substring(0,vert_name.length()-1)+"$"+vert_num+"$"
					+data.substring(0,data.length()-1)+"$"+maxvalue+"&"+SQ002;
			this.getExecuteSG().addExecuteCode("tjfx2('"+pass+"');");
		}else{
			String pass = tran_name.substring(0,tran_name.length()-1)+"$"+tran_num+"$"
					+vert_name.substring(0,vert_name.length()-1)+"$"+vert_num+"$"
					+data.substring(0,data.length()-1)+"$"+maxvalue;
			this.getExecuteSG().addExecuteCode("tjfx('"+pass+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����رհ�ť
	@PageEvent("close.onclick")
	public int close() throws RadowException{
		this.closeCueWindow("TwoDStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��д�رշ���
	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");
	}
	//��дȷ�Ϻ�رշ���
	@Override
	public void closeCueWindowByYes(String arg0) {
		// TODO Auto-generated method stub
		this.setShowMsg(true);
		this.addNextBackFunc(NextEventValue.YES, "parent.odin.ext.getCmp('"+arg0+"').close();");
	}
	
	
	//��ά�༭�¼�
	@PageEvent("ewedit")
	public int ewedit() throws RadowException{
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		HBSession sess = HBUtil.getHBSession();
		String[] param_arr = param.split("#");
		String name = param_arr[0];
		String data = param_arr[1];
		String[] data_arr = data.split("\\|");
		String vert_data = data_arr[0];
		String tran_data = data_arr[1];
		String[] vert_data_arr = vert_data.split("@");
		String[] tran_data_arr = tran_data.split("@");
		Integer vert_length = vert_data_arr.length;
		Integer tran_length = tran_data_arr.length;
		if(vert_length < 3){
			vert_length = 3;
		}
		if(tran_length < 3){
			tran_length = 3;
		}
		this.getPageElement("tran_length").setValue(tran_length.toString()); 	//���ú����ʼ����
		this.getPageElement("vert_length").setValue(vert_length.toString());	//���������ʼ����
		//����
		for(int i = 0; i < vert_data_arr.length; i++){
			if(i==0){
				String vert_set = vert_data_arr[i];
				String vert_sql = null ;
				if(DBUtil.getDBType()==DBType.ORACLE){
					vert_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+vert_set+"' order by t";
				}else{
					vert_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+vert_set+"' order by t ";
				}
				//String vert_ts_sql = "select ts002 from TWODSTATISTICSINFO_SET where ts001 = '"+vert_set+"' ";
				//String vert_set_name = sess.createSQLQuery(vert_ts_sql).uniqueResult().toString();
				((Combo)this.getPageElement("vertical")).setValue(vert_set);
				Map<String, Object> vert_map = new LinkedHashMap<String, Object>();
				List<Object[]>   list_vert = HBUtil.getHBSession().createSQLQuery(vert_sql).list();
				Object[] oo_vert=null;
				vert_map.put("delete", "ɾ������");
				if(list_vert != null && ! list_vert.isEmpty()){
					for(int j=0;j<list_vert.size();j++){
						oo_vert=list_vert.get(j);
						vert_map.put(""+oo_vert[0], oo_vert[1]);
					}
				}else{
					this.setMainMessage("�����쳣");
				}
				for(int j = 0; j < vert_data_arr.length; j++){
					((Combo)this.getPageElement("vert"+j)).setValueListForSelect(vert_map);
				}
				
			}else{
				String vert_name = vert_data_arr[i];
				((Combo)this.getPageElement("vert"+(i-1))).setValue(vert_name);
			}
		}
		//����
		for(int i=0;i<tran_data_arr.length;i++){
			if(i==0){
				String tran_set = tran_data_arr[i];
				String tran_sql = null ;
				if(DBUtil.getDBType()==DBType.ORACLE){
					tran_sql = "select to_number(ti001) t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+tran_set+"' order by t";
				}else{
					tran_sql = "select cast(ti001 as  unsigned int)  t,ti002 from TWODSTATISTICSINFO_ITEM where ts001 = '"+tran_set+"' order by t ";
				}
				//String tran_ts_sql = "select ts002 from TWODSTATISTICSINFO_set where ts001 = '"+tran_set+"' ";
				//String tran_set_name = sess.createSQLQuery(tran_ts_sql).uniqueResult().toString();
				((Combo)this.getPageElement("transverse")).setValue(tran_set);
				Map<String, Object> tran_map = new LinkedHashMap<String, Object>();
				List<Object[]>   list_tran = HBUtil.getHBSession().createSQLQuery(tran_sql).list();
				Object[] oo_tran=null;
				tran_map.put("delete", "ɾ������");
				if(list_tran != null && ! list_tran.isEmpty()){
					for(int j=0;j<list_tran.size();j++){
						oo_tran=list_tran.get(j);
						tran_map.put(""+oo_tran[0], oo_tran[1]);
					}
				}else{
					this.setMainMessage("�����쳣");
				}
				for(int j = 0; j < tran_data_arr.length; j++){
					((Combo)this.getPageElement("tran"+j)).setValueListForSelect(tran_map);
				}
			}else{
				String tran_name = tran_data_arr[i];
				((Combo)this.getPageElement("tran"+(i-1))).setValue(tran_name);
			}
		}
		this.getExecuteSG().addExecuteCode("editor_load();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
