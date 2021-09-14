package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

import net.sf.json.JSONArray;

public class SelectPByNamePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String cid = this.getPageElement("subWinIdBussessId").getValue();
		String sql2="select  a01.a0000, a0101, a0104,a0192a,a0163,a0184 from A01 a01  where a01.a0000  in (select a02.a0000 from a02 where a02.a0281='true' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"')) "
				+ " and a01.a0000 not in (select a0000 from CHECKREGPERSON where CHECKREGID in ('"+cid+"') and a0000 is not null) ";
		
		String unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163,a0184 from A01 a01  where "
				+ " not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' and a01.a0000 not in (select a0000 from CHECKREGPERSON where CHECKREGID in ('"+cid+"') and a0000 is not null) ";
		
		//��������������֤
		String name = this.getPageElement("queryName").getValue();
		//��ò�ѯ���ͣ���������c��֤
		String tpye = this.getPageElement("tpye").getValue();
		
		String col1 = "a0101";
		String col2 = "a0102";
		
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
					unionsql = unionsql + " and (" + sb.toString() + " or " + pingyin.toString()+")";
				}else{ 
				//���������������ģ����ѯ
					name = name.replaceAll("\\s", "");
					/*sql2 = sql2 +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";
					unionsql = unionsql +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";*/
					
					
					sql2 = sql2 +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
					unionsql = unionsql +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
				}
		}
		this.getPageElement("sql").setValue(sql2+" union all " + unionsql);
		String count_sql = "select count(*) from ("+sql2+") a ";
		Object number = sess.createSQLQuery(count_sql).uniqueResult();
		int count = 0;
		if(number != null && !"".equals(number.toString())){
			count = Integer.parseInt(number.toString());
		}
		//�����ѯ�������1000�����ڴ�ѡ�б���չʾ(��ֻ��һ�����Զ����Ƶ�����б�  jsp:190)
		if(count < 1000){ 
			this.getPageElement("mark").setValue("1");
			this.setNextEventName("gridcq.dogridquery");
		
		//�������1000�������ѯչʾ����ʾ	
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
	//���
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//ѡ����Ա�б�
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
 		this.getPageElement("selectName").setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ�����
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//ѡ����Ա�б�
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
 		this.getPageElement("selectName").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�Ƴ�
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck2")!=null&&!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				addlist.add(hm);
			}
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȫ���Ƴ�
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//��ѯ��Ա�б�
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//ȥ����ѯ�б�ȥ��������
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//��������ӵ��Ǽ���Ա�б�
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//�ر�
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		/*List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
		//ȥ����ѯ�б�ȥ��������
		pe.setValue(JSONArray.fromObject(addlist).toString());*/
		//this.closeCueWindow("findById");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ȷ�ϱ���
	@PageEvent("saveSelect")
	public int saveSelect() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String cid = this.getPageElement("subWinIdBussessId").getValue();
		String cyk = this.getPageElement("cyk").getValue();
		//���ѡ�����ԱID
		StringBuffer sb = new StringBuffer("");
		try {
			PageElement pe = this.getPageElement("selectName");//ѡ����Ա�б�
			List<HashMap<String, Object>> listSelect = pe.getValueList();
			if(listSelect.size()<1){
				this.setMainMessage("��ѡ����Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(sortid1) from CHECKREGPERSON where checkregid ='"+cid+"'");
			int num = 1;
			if(rs.next()) {
				num = rs.getInt(1) +1;
			}
			for (int i = 0; i < listSelect.size(); i++) {
				String crp008 = UUID.randomUUID().toString();
				HashMap<String, Object> hm = listSelect.get(i);
				hm.get("a0000");
				String sql1 = "insert into CHECKREGPERSON select sys_guid(),'"+hm.get("a0000")+"',null,a0101,"
						+ "'������',A0107,a0192a,(select code_name from code_value where code_type='GB4762' " + 
						"and code_value=A0141),"+(num+i)+",0,a0184,'1','"+cid+"','"+crp008+"'��case when a0104='1' "
						+ " then '��' else 'Ů' end,'' ,(select code_name from code_value where code_type='ZB148' and code_value=A0192e),"
						+ "(select code_name from code_value where code_type='ZB125' and code_value=A0160),'','','','','','' from a01 where a0000='"+hm.get("a0000")+"'";
				String sql2 = "insert into CHECKREGPERSON select c1,c2,c3,c4,c5,c6,c7,c8,c9, rownum c10,c11,c12,c13,c14,"
						+ "c15,c16,c17,c18,c19,c20,c21,c22,c23,'' from (select sys_guid() c1,'"+hm.get("a0000")+"' c2,a3600 c3,A3601 c4,"
						+ "case when A3604A IN ('�ɷ�','����') then '��ż' when A3604A IN ('��ϱ','Ů��') then '��Ů����ż' else '��Ů' end c5,A3607 c6,A3611 c7,A3627 c8,"+(num+i)+" c9,"
						+ "0 c10,A3684 c11,'2' c12,'"+cid+"' c13,'"+crp008+"' c14,case when "
						+ "A3604A in ('��Ů','��Ů','��Ů','Ů��','����','����Ů��','��Ů','��Ů','��Ů','��Ů','��ϱ') then 'Ů' else '��' end c15,'' c16,'' c17,'' c18,'' c19,'' c20,'' c21,'' c22,'' c23  from a36 where a0000='"+hm.get("a0000")+"'"
						+ " and A3604A in ('��Ů','����','��Ů','����','����','��Ů','����','Ů��','����','����Ů��','��Ů','����','��Ů','����','��Ů','����','��Ů','����','�ɷ�','��ϱ','Ů��') order by sortid)";
				if(cyk!=null && "cyk".equals(cyk)) {
					sql2 = "insert into CHECKREGPERSON select c1,c2,c3,c4,c5,c6,c7,c8,c9, rownum c10,c11,c12,c13,c14,"
							+ "c15,c16,c17,c18,c19,c20,c21,c22,c23,'' from (select sys_guid() c1,'' c2,'' c3,JTCYK003 c4,"
							+ " case when JTCYK004 IN ('�ɷ�','����') then '��ż' when JTCYK004 IN ('��ϱ','Ů��') then '��Ů����ż' when JTCYK004 IN ('Ů��','����') then '��Ů' else JTCYK004 end c5,"
							+ "'' c6,JTCYK011 c7,'' c8,"+(num+i)+" c9, 0 c10,JTCYK008 c11,'2' c12,'"+
							cid+"' c13,'"+crp008+"' c14, '' c15,'' c16,'' c17,'' c18,'' c19,'' c20,'' c21,''"
							+ " c22,'' c23  from CHECKREGJTCYK where JTCYK002='"+hm.get("a0184")+"' order by sortid)";
					
				}
				System.out.println(sql1);
				System.out.println(sql2);
				stmt.addBatch(sql1);
				stmt.addBatch(sql2);
				if(i!=0 && i%5000==0) {
					stmt.executeBatch();
				}
			}
			stmt.executeBatch();
			conn.commit();
			this.getExecuteSG().addExecuteCode("window.realParent.radow.doEvent('gridcq.dogridquery');");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
			/*for (HashMap<String, Object> hm : listSelect) {
				sb.append("'").append(hm.get("a0000")+"").append("',");
				String sql
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn!=null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if(stmt!=null) {
					stmt.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if(conn!=null) {
					conn.close();;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		/*if("������Ա".equals(RMRY)){
			this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+addlist+"');");
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById321').close();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("realParent.document.getElementById('orgjson').value=''");
		this.getExecuteSG().addExecuteCode("window.realParent.queryByNameAndIDS('"+addlist+"');");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//�������б�
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
		String s = "��.��.                    ��";
		System.out.println(s.replace(" ", ","));
	}
	
	

}
