package com.insigma.siis.local.pagemodel.xbrm2;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;

import net.sf.json.JSONArray;

public class SelectTPRYPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		//����������
		String ynId = this.getPageElement("ynId").getValue();
		String yntype = this.getPageElement("yntype").getValue();
		String uname = SysManagerUtils.getUserloginName();
		String sql="select distinct tp0116 from TPHJ1 t where t.yn_id='"+ynId+"' and t.tp0116!='"+yntype+"'";  
		
		String sql2="select (select yn_name from JS2_YNTP x where x.yn_id=t.yn_id) yn_name, yn_id,info01,GET_gname(info04) gname,info04 from JS2_YNTP_INFO t where t.info02='"+uname+"'";  //���һ�������
		
		String sql3 = "select code_name,code_value from "+ Zsrm.getZjkTableName("code_value") +" where code_type = 'KZTPHJ02'";  //����
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		
		List<HashMap<String, Object>> listCode2=cqbs.getListBySQL(sql2);
		
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			String tp0116 = listCode.get(i).get("tp0116").toString();
			mapCode.put(tp0116, YNTPFileExportBS.TPHJ.get(tp0116));
		}
		for(int i=0;i<listCode2.size();i++){
			String yn_id = listCode2.get(i).get("yn_id").toString();
			String inf01 = listCode2.get(i).get("info01").toString();
			String gname = listCode2.get(i).get("gname")==null?"":listCode2.get(i).get("gname").toString();
			String info04 = listCode2.get(i).get("info04").toString();
			String yn_name = listCode2.get(i).get("yn_name").toString();
			mapCode.put(yn_id+"@@"+inf01, gname+info04+"("+yn_name+" "+YNTPFileExportBS.TPHJ.get(inf01)+")");
		}
		
		//=====================����ʵ��������=================
		System.out.println(sql3);
		List<HashMap<String, Object>> listCode3=cqbs.getListBySQL(sql3);
		StringBuffer buffer = new StringBuffer();
		for (HashMap<String, Object> map:listCode3) {
			buffer.append(",{name:'"+map.get("code_name")+"',code:'"+map.get("code_value")+"'}");
		}
		if (buffer.length()>0) {
			buffer.delete(0, 1);
		}
		this.getExecuteSG().addExecuteCode("resetPiCiSelect(["+buffer.toString()+"]);");
		
		//=================================================
		
//		((Combo)this.getPageElement("tplb")).setValueListForSelect(mapCode);
		 
		this.getPageElement("mark").setValue("1");
		//this.setNextEventName("gridcq.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryFromDatainit")
	public int queryFromDatainit() throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/****
	 * ��ѯ���ܵ�����
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("queryFromYnId")
	public int queryFromYnId() throws RadowException{
		this.getPageElement(change("gridcq")).setValue("[]");
		this.getPageElement(change("selectName")).setValue("[]");
		
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String uname = SysManagerUtils.getUserloginName();
		String sql="select count(1) from JS2_YNTP_INFO t where t.info02='"+uname+"'";
		Object number = sess.createSQLQuery(sql).list().size();
		int count = 0;
		if(number != null && !"".equals(number.toString())){
			count = Integer.parseInt(number.toString());
		}
		
		String sql2="select (select yn_name from JS2_YNTP x where x.yn_id=t.yn_id) as a0101 ,"+
				" yn_id as a0000,"+
				" info01 as a0101,"+
				" (select tb_unit from JS2_YNTP x where x.yn_id=t.yn_id) as a0104,"+
				" '' as a0163,'' as a0192a "+
				" from JS2_YNTP_INFO t where t.info02='"+uname+"'";
		
		//this.getPageElement("sql2").setValue(sql2);
		
		//�����ѯ�������1000�����ڴ�ѡ�б���չʾ(��ֻ��һ�����Զ����Ƶ�����б�  jsp:190)
		if(count < 1000){ 
			//this.getPageElement("mark").setValue("1");
			this.setNextEventName("gridcq2.dogridquery");
		
		//�������1000�������ѯչʾ����ʾ	
		}else{
			this.setMainMessage("��ѯ������࣬����С��ѯ��Χ�����²�ѯ");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridcq2.dogridquery")
	public int doMemberQuery2(int start,int limit) throws RadowException{
		try {
			String uname = SysManagerUtils.getUserloginName();
			String sql="select (select x.yn_name from JS2_YNTP x where x.yn_id=t.yn_id)   a0101 ,"+
					" yn_id   a0000,"+
					" (select tb_unit from JS2_YNTP x where x.yn_id=t.yn_id) as a0104,"+
					" ''   a0163,''   a0192a "+
					" from JS2_YNTP_INFO t where t.info02='"+uname+"'";
			if(limit<1000) {
				limit=1000;
			}
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		String sql2=" ";
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String selectByInputYnIdHidden = this.getPageElement("selectByInputYnIdHidden").getValue();

			//��������������֤
			String name = this.getPageElement("queryName").getValue();
			//��ò�ѯ���ͣ���������c��֤
			String tpye = this.getPageElement("tpye").getValue();
			
			String col1 = "a0101";
			String col2 = "a0102";
			
			boolean multiUnit = false;//�Ƿ�����¼���λ
			
			//��־�� 2018-08-22
			String selectUnitId = this.getPageElement("selectUnitId").getValue();
			selectUnitId=selectUnitId.toString().equals("")?"{}":selectUnitId;
			JSONObject objUnitId = new JSONObject(selectUnitId);
			StringBuffer sqlBuffer = new StringBuffer();

			multiUnit = objUnitId.keySet().size()>1;		
			for (Object oKey:objUnitId.keySet()) {
				String unitId = (String)oKey;
				String array[] = objUnitId.get(unitId).toString().split(":");
				if ("true".equals(array[1]) && "true".equals(array[2])) {
					sqlBuffer.append(" OR a0201b like '"+unitId+"%' ");
					multiUnit = true;
				}else if (!"true".equals(array[1]) && "true".equals(array[2])) {
					sqlBuffer.append(" OR a0201b = '"+unitId+"' ");
				}
				 
			} 
			sqlBuffer.delete(0, 3);

		if (selectByInputYnIdHidden != null && !"".equals(selectByInputYnIdHidden)) {
			//���յ�������ѡ��
			sql2="select  a01.a0000, a0101, a0104,a0192a,a0163 from "+Zsrm.getZjkTableName("a01")+" where a0000 in (select a0000 from "+Zsrm.getZjkTableName("TPHJPERSON")+" where TPZZ02='"+selectByInputYnIdHidden+"' )";
			this.getPageElement("sql").setValue(sql2);
		}else {
			//===========================���°��յ�λ��Աѡ��


			String appointment = this.getPageElement("appointment").getValue();

//			String unionsql=" ";
			if ("1".equals(appointment)) {
				//��ʽ����
				sql2="select  distinct a01.TORGID,a01.torder,a01.a0225,a01.a0000, a0101, a0104,a0192a,a0163 from a01_tphj a01  where A0163='1' and status <> '4' and remoteserver = remoteserver() and a01.a0000  in (select a02.a0000 from a02_tphj a02 where a02.A0255='1' and a02.a0281='true' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"'))  ";

//				unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163 from a01_tphj a01  where remoteserver = remoteserver() and "
//						+ " not exists (select 1 from a02_tphj a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
//							+ " and a01.status!='4' ";
			} else {
				//���е�λ��Աѡ��
				String mult=multiUnit?"":"a01.a0225,";  //ѡ������λȥ����������
				sql2="select distinct a01.TORGID,a01.torder,"+mult+" a01.a0000, a0101, a0104,a0192a,a0163 from a01_tphj a01  where A0163='1' and status <> '4' and a01.a0000  in (select a02.a0000 from a02_tphj a02 where a02.a0281='true' and a02.A0255='1' "  +
						"and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"'))  ";

//				unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163 from a01_tphj a01  where "
//						+ " not exists (select 1 from a02_tphj a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
//							+ " and a01.status!='4' ";
			}
			//�������ӵ�λȨ�޵Ŀ���
			
			if(tpye != null && tpye.equals("2")){
				col1 = "a0184";
				col2 = "a0184";
			}
			
			if(name!=null && !name.trim().equals("")){
				name = StringEscapeUtils.escapeSql(name.trim());
				name = name.replaceAll("\\s+"," ");
				name = name.replaceAll(" ", ",");
				name = name.replace(".", ",");
				name = name.replace("&", ",");
				name = name.replace("#", ",");
				name = name.replaceAll("[\\t\\n\\r]", ",");
				String[] names = name.trim().split(",|��");
				//������������о�ȷ��ѯ
				if(names.length>1){
					
					/*	StringBuffer sb = new StringBuffer(" a01.a0101 in(");
						StringBuffer pingyin = new StringBuffer(" a01.a0102 in(");*/
						
						StringBuffer sb = new StringBuffer(" a01."+col1+" in(");
						StringBuffer pingyin = new StringBuffer(" a01."+col2+" in(");
						for(int i=0;i<names.length;i++){
							name = names[i].replaceAll("\\s", "");
							sb.append("'"+name+"',");
							pingyin.append("'"+name.toUpperCase()+"',");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append(")");
						pingyin.deleteCharAt(pingyin.length()-1);
						pingyin.append(")");
						sql2 = sql2 + " and (" + sb.toString() + " or " + pingyin.toString()+")";
//						unionsql = unionsql + " and (" + sb.toString() + " or " + pingyin.toString()+")";
					}else{ 
					//���������������ģ����ѯ
						name = name.replaceAll("\\s", "");
						/*sql2 = sql2 +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";
						unionsql = unionsql +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";*/
						 
						sql2 = sql2 +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
//						unionsql = unionsql +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
					}
			} else {
				if (sqlBuffer.length()>0) {
					sql2+=" and a01.a0000 in (select a0000 from a02_tphj where A0255='1' and a0281='true' and " + sqlBuffer.toString() +  ") ";
				sql2+=	"and  A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"' and "+ sqlBuffer.toString() +  " )";
				}
			}
			
			String tElementSQL = "";
			if (multiUnit) {
				tElementSQL = sql2 + " Order by TORGID, torder ";
			} else {
				tElementSQL = sql2 + " Order by a0225 ";
			}
			System.out.println(tElementSQL);
			this.getPageElement("sql").setValue(sql2); //this.getPageElement("sql").setValue(sql2+" union all " + unionsql);
		}
		
		int DISPLAY_PERSON = 100;  //��ʾ��Ա����
		
		String count_sql = "select count(*) from ("+sql2+") a where rownum<"+(DISPLAY_PERSON+1)+" ";
		System.out.println(count_sql);
		
		Object number = sess.createSQLQuery(count_sql).list().size();
		
		int count = 0;
		if(number != null && !"".equals(number.toString())){
			count = Integer.parseInt(number.toString());
		}
		//�����ѯ�������DISPLAY_PERSON�����ڴ�ѡ�б���չʾ(��ֻ��һ�����Զ����Ƶ�����б�  jsp:190)
		if(count < DISPLAY_PERSON){ 
			this.getPageElement("mark").setValue("1");
			this.setNextEventName("gridcq.dogridquery");
		
		//�������DISPLAY_PERSON�������ѯչʾ����ʾ	
		}else{
			this.setMainMessage("��ѯ������࣬����С��ѯ��Χ�����²�ѯ");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String sql = this.getPageElement("sql").getValue();
			if(limit<1000) {
				limit=1000;
			}
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name=name+"2";
		}
		return name;
	}
	
	//���
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement(change("gridcq"));
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement(change("selectName")).getValueList();//ѡ����Ա�б�
		for (HashMap<String, Object> hm : list) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(addlist);
		String gridcqdata = JSONArray.fromObject(list).toString();
		
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//��������ӵ��Ǽ���Ա�б�
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement(change("selectName")).setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ�����
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement(change("gridcq"));
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement(change("selectName")).getValueList();//ѡ����Ա�б�
		for (HashMap<String, Object> hm : list) {
			addlist.add(hm);
		}
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(list).toString());
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//��������ӵ��Ǽ���Ա�б�
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement(change("selectName")).setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�Ƴ�
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement(change("gridcq")).getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement(change("gridcq")).setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ���Ƴ�
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement(change("gridcq")).getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement(change("gridcq")).setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�ر�
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		/*List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		//ȥ����ѯ�б�ȥ��������
		pe.setValue(JSONArray.fromObject(addlist).toString());*/
		//this.closeCueWindow("findById");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ת���ݿ�
	@PageEvent("doAppointment")
	public int doAppointment() throws RadowException, AppException{
		//���ѡ�����ԱID
		//List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		StringBuffer conditionBuffer = new StringBuffer();
		for (HashMap<String, Object> hm : listSelect) {
			//addlist.add(hm.get("a0000")+"");
			conditionBuffer.append(",'"+hm.get("a0000")+"'");
		} 
		if(conditionBuffer.length()<=0 ){
			this.setMainMessage("��ѡ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}		
		
		conditionBuffer.delete(0, 1);
		//��ʼ����Ǩ��
		Zsrm objZsrm = new Zsrm(); 
		String condition = conditionBuffer.toString();
		int result = objZsrm.movePersonDB(condition);
		if (result == EventRtnType.NORMAL_SUCCESS) {
			this.setMainMessage("��Աת�뵽��ʽ����ϣ�");
			this.getPageElement(change("selectName")).setValue("[]");
		}else {
			this.setMainMessage("��Աת�뵽��ʽ��ʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	//ȷ�ϱ���
	@PageEvent("saveSelect")
	public int saveSelect() throws RadowException, AppException{
		
//		String tplb = this.getPageElement("tplb").getValue();
//		if(tplb!=null&&!"".equals(tplb)){
//			this.getExecuteSG().addExecuteCode("window.realParent.doAddPerson.queryByNameAndIDS('"+tplb+"');");
//			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('seltpry').close();");
//			this.getPageElement(change("selectName")).setValue("[]");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		
		//���ѡ�����ԱID
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm.get("a0000")+"");
		}
		
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			addlist.add("TPHJ");
		}
		
		if(addlist.size()<1){
			this.setMainMessage("��ѡ����Ա��ѡ��������ͣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String jw=this.getPageElement("jw").getValue();
		if("1".equals(jw)) {
			this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+addlist+"');");
		}else {
			this.getExecuteSG().addExecuteCode("window.realParent.doAddPerson.queryByNameAndIDS('"+addlist+"');");
		}
		
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('seltpry').close();");
		this.getPageElement(change("selectName")).setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//�������б�
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement(change("gridcq")).setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
		String s = "��.��.                    ��";
		System.out.println(s.replace(" ", ","));
	}
	
	

}
