package com.insigma.siis.local.pagemodel.yngwyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
/**
 * 
 * @author zoulei 2018��4��12��
 *
 */
public class CodeValueUtilPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ���������ݼ���
	 * @return
	 * @throws PrivilegeException
	 * @throws RadowException
	 */
	@PageEvent("codeValueJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException, RadowException {
		String node = this.getParameter("node");
		String codetype = this.getParameter("codetype");
		String code_name = this.getParameter("codename");
		String property = this.getParameter("formproperty");
		if(code_name==null||"".equals(code_name)){
			code_name = "code_name";
		}
		if(node==null){
			node="-1";
		}
		String jsonStr="";
		if("B01".equals(codetype)){
			jsonStr = getCodeTypeJSForB01(codetype, code_name, node,property);
		}else if("USER".equals(codetype)){
			jsonStr = getCodeTypeJSForUSER(codetype, code_name, node,property);
		}else if("historymd".equals(codetype)){
			jsonStr = getCodeTypeJSForHIS(codetype, code_name, node,property);
		}else{
			jsonStr = getCodeTypeJS(codetype, code_name, node,property);			
		}
		if("personq".equals(property)&&"-1".equals(node)&&"ZB126".equals(codetype))
		{
			jsonStr = jsonStr.substring(0, jsonStr.length()-1);
			jsonStr += ",{\"text\" :\"3ȫԱ������\" ,\"selectText\" :\"ȫԱ������\" ,\"id\" :\"4\" ,leaf:true,code_leaf:'1',formproperty:'personq',\"href\":\"javascript: \"}";
			jsonStr += "]";
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	public static String getCodeTypeJSForHIS(String codetype,String code_name,String cv, String property) {
		HBSession sess = HBUtil.getHBSession();
		//������
		String userid=SysManagerUtils.getUserId();
		String sql="select 'his',mdid,mdmc,0 c,'1' code_leaf from "
				+ " (select mdid,mdmc,createdate from historymd y where userid = '"+userid+"'"
				+ "   union all"
				+ "	select mdid,mdmc,createdate   from historymd y where exists "
				+ "       (select 1  from hz_LSMD_userref u  where mnur01 = '"+userid+"' and u.mdid = y.mdid))"
				+ "   order by createdate desc";
		
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				jsonStr.append("{\"text\" :\"" + o[2].toString()
				+ "\" ,\"selectText\" :\"" +o[2].toString()		
				+ "\" ,\"id\" :\"" +o[1].toString()
				//+ "\" ,leaf:"+(((BigDecimal)o[3]).intValue()==0?"true":"false")+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				+ "\" ,leaf:"+((Integer.parseInt(o[3].toString()))==0?"true":"false")+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return	"{}";
	}
	
	public static String getCodeTypeJSForUSER(String codetype,String code_name,String cv, String property) {
		HBSession sess = HBUtil.getHBSession();
		if("-1".equals(cv)){//��һ�μ���
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
			
			String groupSql ="select sm.id,sm.usergroupname from smt_user s,smt_usergroup sm where s.useful = '1' and s.dept = sm.id and s.userid = '40288103556cc97701556d629135000f' order by sm.sortid asc " ;//��ȡ����Ա���ڵĲ���
			Object[] group = (Object[]) sess.createSQLQuery(groupSql).uniqueResult();
			if(group==null){
				return	"{}";
			}
			String groupid = ""+group[0];
			cv = groupid;
		}
		//������
		
		String sql2=" select '', id,name, type,'1' "
				+ " from (select s.userid id, s.username name , 'user' type,s.sortid from smt_user s where s.dept = '"+cv+"' and s.useful = '1' "
				+ " union all "
				+ " select smt.id, smt.usergroupname name ,'group' type ,to_number(smt.sortid) sortid from smt_usergroup smt where smt.sid = '"+cv+"' "
						+ " ) order by sortid asc "
				+ " ";
		
		
		List<Object[]> list = sess.createSQLQuery(sql2).list();
		
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				String type = ""+o[3];
				String leaf="false";
				if(!"group".equals(type)){
					leaf = "true";
				}
				jsonStr.append("{\"text\" :\"" + o[2].toString()
				+ "\" ,\"selectText\" :\"" +o[2].toString()		
				+ "\" ,\"id\" :\"" +o[1].toString()
				+ "\" ,\"ntype\" :\"" +o[3].toString()
				//+ "\" ,leaf:"+(((BigDecimal)o[3]).intValue()==0?"true":"false")+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				+ "\" ,leaf:"+leaf+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return	"{}";
	}
	
	public static String getCodeTypeJS(String codetype,String code_name,String cv, String property) {
		HBSession sess = HBUtil.getHBSession();
		//������
		String sql="select code_type,code_value,"+code_name+",(select count(1) from code_value s where s.sub_code_value=t.code_value and code_type = '"+codetype+"') c,code_leaf from code_value t where code_type='"+codetype+"' and sub_code_value='"+cv+"' and code_status='1'  order by ININO,code_value";
		
		
		
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				jsonStr.append("{\"text\" :\"" + o[1].toString() + " " + o[2].toString()
				+ "\" ,\"selectText\" :\"" +o[2].toString()		
				+ "\" ,\"id\" :\"" +o[1].toString()
				//+ "\" ,leaf:"+(((BigDecimal)o[3]).intValue()==0?"true":"false")+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				+ "\" ,leaf:"+((Integer.parseInt(o[3].toString()))==0?"true":"false")+",code_leaf:'"+o[4].toString()+"',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return	"{}";
	}
	
	/**
	 * �������������ݼ���
	 * @return
	 * @throws PrivilegeException
	 * @throws RadowException
	 */
	@PageEvent("searchCodeValueJsonData")
	public int searchCodeValueJsonData() throws PrivilegeException, RadowException {
		String node = this.getParameter("node");
		String codetype = this.getParameter("codetype");
		String code_name = this.getParameter("codename");
		String property = this.getParameter("formproperty");
		String svalue = this.getParameter("svalue");
		if(code_name==null||"".equals(code_name)){
			code_name = "code_name";
		}
		if(node==null){
			node="-1";
		}
		String jsonStr="";
		if("B01".equals(codetype)){
			jsonStr = getCodeTypeJS2ForB01(codetype, code_name, node,property,svalue);
		}else if("USER".equals(codetype)){
			jsonStr = getCodeTypeJS2ForUSER(codetype, code_name, node,property,svalue);
		}else if("historymd".equals(codetype)){
			jsonStr = getCodeTypeJS2ForHIS(codetype, code_name, node,property,svalue);
		}else{
			jsonStr = getCodeTypeJS2(codetype, code_name, node,property,svalue);			
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	public static String getCodeTypeJS2ForHIS(String codetype,String code_name,String cv, String property,String svalue) {
		if(svalue==null||"".equals(svalue)){
			return	"{}";
		}
		List<Object[]> list;
		String userid=SysManagerUtils.getUserId();
		if(DBUtil.getDBType()==DBType.ORACLE){
			String parentDescSql = "select '<span style=''color:#27158b;''>('||listagg("+code_name+",'>') within GROUP (order by code_value)||')</span>' from "+
					" (select code_type,code_value,"+code_name+",decode(t.sub_code_value,'-1',null,t.sub_code_value) sub_code_value from code_value t  where code_type='"+codetype+"') tt "
					+ "where tt.code_value!=t.code_value"+
					" start with tt.code_value=t.code_value CONNECT BY  tt.code_value= PRIOR tt.sub_code_value";
			
			
			HBSession sess = HBUtil.getHBSession();
			//������
			String sql="select 'his',mdid,mdmc,'' codename2 from "
					+ " (select mdid,mdmc,createdate from historymd y where userid = '"+userid+"'"
					+ "   union all"
					+ "	select mdid,mdmc,createdate   from historymd y where exists "
					+ "       (select 1  from hz_LSMD_userref u  where mnur01 = '"+userid+"' and u.mdid = y.mdid))"
					+ " t where  mdmc like '%"+svalue+"%'  order by createdate desc";
			list = sess.createSQLQuery(sql).list();
			
		}else {
			list = getData(codetype, svalue);
		}
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				jsonStr.append("{\"text\" :\"" +o[2].toString()
				+ "\" ,\"selectText\" :\"" +o[2].toString()		
				+ "\" ,\"id\" :\"" +o[1].toString()
				+ "\" ,leaf:true,code_leaf:'1',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return	"{}";
	}
	
	public static String getCodeTypeJS2ForUSER(String codetype,String code_name,String cv, String property,String svalue) {
		if(svalue==null||"".equals(svalue)){
			return	"{}";
		}
		List<Object[]> list;
			String parentDescSql = "select '<span style=''color:#27158b;''>('||listagg(tt.name,'>') "+
" within GROUP (order by tt.id)||')</span>' from "+
" (select t.id,t.name,decode(t.pid,'-1',null,t.pid) pid from userinfo t) tt "+
" where tt.id!=t.id "+
" start with tt.id=t.id CONNECT BY  tt.id= PRIOR tt.pid";
			
			HBSession sess = HBUtil.getHBSession();
			//������
			String sql="select t.id,t.name,("+parentDescSql+") codename2,type from userinfo t where (name like '%"+svalue+"%') and rownum<=20  order by sortid";
			list = sess.createSQLQuery(sql).list();
			
			if(list!=null&&list.size()>0){
				StringBuffer jsonStr = new StringBuffer("[");
				for(Object[] o : list){
					jsonStr.append("{\"text\" :\"" +" "+ ("code_name".equals(code_name)?(o[1].toString()+o[2].toString()):o[1].toString())
					+ "\" ,\"selectText\" :\"" +o[1].toString()		
					+ "\" ,\"id\" :\"" +o[0].toString()
					+ "\" ,\"ntype\" :\"" +o[3].toString()
					+ "\" ,leaf:true,code_leaf:'1',formproperty:'"+property+"',");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript: \"");
					jsonStr.append("},");
					
				}
				jsonStr.deleteCharAt(jsonStr.length()-1);
				jsonStr.append("]");
				return jsonStr.toString();
			}
		return	"{}";
	}
	
	public static String getCodeTypeJS2(String codetype,String code_name,String cv, String property,String svalue) {
		if(svalue==null||"".equals(svalue)){
			return	"{}";
		}
		List<Object[]> list;
		if(DBUtil.getDBType()==DBType.ORACLE){
			String parentDescSql = "select '<span style=''color:#27158b;''>('||listagg("+code_name+",'>') within GROUP (order by code_value)||')</span>' from "+
					" (select code_type,code_value,"+code_name+",decode(t.sub_code_value,'-1',null,t.sub_code_value) sub_code_value from code_value t  where code_type='"+codetype+"') tt "
					+ "where tt.code_value!=t.code_value"+
					" start with tt.code_value=t.code_value CONNECT BY  tt.code_value= PRIOR tt.sub_code_value";
			
			
			HBSession sess = HBUtil.getHBSession();
			//������
			String sql="select code_type,code_value,"+code_name+",("+parentDescSql+") codename2 from code_value t where code_type='"+codetype+"' and ("+code_name+" like '%"+svalue+"%' or code_value like '%"+svalue+"%')  and code_status='1'  and rownum<=200  order by ININO,code_value";
			list = sess.createSQLQuery(sql).list();
			
		}else {
			list = getData(codetype, svalue);
		}
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				jsonStr.append("{\"text\" :\"" + o[1].toString()+" "+(("code_name".equals(code_name)?(o[2].toString()+o[3].toString()):o[2].toString()).replace("()", ""))
				+ "\" ,\"selectText\" :\"" +o[2].toString()		
				+ "\" ,\"id\" :\"" +o[1].toString()
				+ "\" ,leaf:true,code_leaf:'1',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return	"{}";
	}
	
	
	
	
	
	
	
	/**
	 * ������ؼ�ֱ�Ӳ�� ��ͨ��V_aa10
	 * @param codetype
	 * @param filter
	 * @return
	 */
	public static String getCodeTypeJS(String codetype,String filter,String codename) {
		if(filter==null){
			filter = "1=1";
		}
		if(codename==null||"".equals(codename)){
			codename = "code_name";
		}
		HBSession sess = HBUtil.getHBSession();
		//������
		String sql="select code_type,code_value,"+codename+" from code_value t where code_type='"+codetype+"'" +
				" and " + filter + " order by inino, code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String, List<Map<String, String>>> codemap = new HashMap<String, List<Map<String,String>>>();
			//List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
			//[['1','��',''],['2','Ů',''],['9','δ˵�����Ա�','']] �����������﷨
			StringBuffer sb = new StringBuffer("");
			//sb.append("[");
			for(Object[] codevalue : list){
				if(codevalue[2]!=null) {
					sb.append("\r\n['"+codevalue[1].toString()+"','"+codevalue[2].toString()+"',''],");
				}
			}
			sb.deleteCharAt(sb.length()-1);
			//sb.append("]");
			return sb.toString();
		}else{
			return "[]";
		}
	}
	
	/**
	 * �����������ʱʹ��
	 * @param codetyp
	 * @return
	 */
	public static String getCodeTypeJS(String codetyp) {
		HBSession sess = HBUtil.getHBSession();
		//������
		String sql="select code_type,code_value,code_name from code_value t where code_type='"+codetyp+"' order by ININO,code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		if(list!=null&&list.size()>0){
			//[['1','��',''],['2','Ů',''],['9','δ˵�����Ա�','']] �����������﷨
			StringBuffer sb = new StringBuffer("");
		
			sb.append("{");
			for(Object[] codevalue : list){
				sb.append("'"+codevalue[1].toString()+"':'"+codevalue[2].toString()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("},");
			
			sb.deleteCharAt(sb.length()-1);
			//sb.append("};");
			return sb.toString();
		}
		return	"{}";
	}
	/**
	 * B01ר��������
	 * @param codetype
	 * @param code_name
	 * @param cv
	 * @param property
	 * @return
	 */
	public static String getCodeTypeJSForB01(String codetype,String code_name,String cv, String property) {
		HBSession sess = HBUtil.getHBSession();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		
		
		
		if(cv.equals("-1")){//�״η���
			String sql="select min(b0111) b0111 from competence_userdept t where userid='"+cueUserid+"'";
			String node = cv;
			try {
				List<HashMap> listau = CommonQueryBS.getQueryInfoByManulSQL(sql);
				if(listau!=null&&listau.size()>0){
					node=(String)listau.get(0).get("b0111");//��ȡ�û�Ȩ�޷�Χ�ڵ���߼���λ��id
				}
				if(node==null||node.length()<=7){
					node="-1";
				}else{
					node=node.substring(0, node.length()-4);//��ȡ�û�Ȩ�޷�Χ�ڵ���߼���λ����һ����λid
				}
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cv = node;
		}
		
		
		
		
		//������
		//String sql="select code_type,code_value,"+code_name+",(select count(1) from code_value s where s.sub_code_value=t.code_value and code_type = '"+codetype+"') c,code_leaf from code_value t where code_type='"+codetype+"' and sub_code_value='"+cv+"'  order by ININO,code_value";
		String sql1="select s.b0111,b0101,(select count(1) from b01 b where b.b0121=s.b0111 ) c from "
				+ " b01 s,(select b0111 from competence_userdept t where userid='"+cueUserid+"') t where t.b0111=s.b0111 and b0121 ='"+cv+"' order by b0269";
		
		
		
		List<Object[]> list = sess.createSQLQuery(sql1).list();
		if(list!=null&&list.size()>0){
			StringBuffer jsonStr = new StringBuffer("[");
			for(Object[] o : list){
				jsonStr.append("{\"text\" :\"" +o[1].toString()
				+ "\" ,\"selectText\" :\"" +o[1].toString()		
				+ "\" ,\"id\" :\"" +o[0].toString()
				+ "\" ,leaf:"+((Integer.parseInt(o[2].toString()))==0?"true":"false")+",code_leaf:'1',formproperty:'"+property+"',");
				jsonStr.append("\"href\":");
				jsonStr.append("\"javascript: \"");
				jsonStr.append("},");
				
			}
			jsonStr.deleteCharAt(jsonStr.length()-1);
			jsonStr.append("]");
			return jsonStr.toString();
		}
		return "{}";
	}
	
	/*
	 * b01ר����������ѯ
	 */
	public static String getCodeTypeJS2ForB01(String codetype,String code_name,String cv, String property,String svalue) {
		if(svalue==null||"".equals(svalue)){
			return	"{}";
		}
		List<Object[]> list;
		if(DBUtil.getDBType()==DBType.ORACLE) {
			String parentDescSql = "select '<span style=''color:#27158b;''>('||listagg(b0101,'>') within GROUP (order by b0111)||')</span>' from "+
					" (select b0111,b0101,decode(t.b0121,'-1',null,t.b0121) b0121 from b01 t) tt "
					+ "where tt.b0111!=t.b0111"+
					" start with tt.b0111=t.b0111 CONNECT BY  tt.b0111= PRIOR tt.b0121";
			
			
			HBSession sess = HBUtil.getHBSession();
			//������
			String sql="select b0111,b0101,("+parentDescSql+") codename2 from b01 t where (b0101 like '%"+svalue+"%' or b0111 like '%"+svalue+"%') and rownum<=20  order by b0269";
			
			list = sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				StringBuffer jsonStr = new StringBuffer("[");
				for(Object[] o : list){
					jsonStr.append("{\"text\" :\"" +" "+ ("code_name".equals(code_name)?(o[1].toString()+o[2].toString()):o[1].toString())
					+ "\" ,\"selectText\" :\"" +o[1].toString()		
					+ "\" ,\"id\" :\"" +o[0].toString()
					+ "\" ,leaf:true,code_leaf:'1',formproperty:'"+property+"',");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript: \"");
					jsonStr.append("},");
					
				}
				jsonStr.deleteCharAt(jsonStr.length()-1);
				jsonStr.append("]");
				return jsonStr.toString();
			}
		}else {
			
		}
		
		return	"{}";
	}
	//----------------------------------------����mysql��ȡ������(B01)-----------------------------------------
	public static List<Object[]> getDataB(String codetype,String code_name){
		//��һ�β�ѯ
		HBSession sess = HUtil.getHBSession();
		List<Object[]> list= sess.createSQLQuery("select code_type,code_value,code_name from code_value where code_type='"+codetype+"' and code_name like '%"+code_name+"%' limit 0,20 ").list();
		for(int i=0;i<list.size();i++) {
			namelist.clear();
			String codename = getCodename( getListCodename(list.get(i)[1].toString(),list.get(i)[0].toString()));
			Object[] newdata = new Object[] {list.get(i)[0],list.get(i)[1],list.get(i)[2],codename};
			list.set(i, newdata);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//----------------------------------------����mysql��ȡ������(��B01)-----------------------------------------
	public static List<Object[]> getData(String codetype,String code_name){
		//��һ�β�ѯ
		HBSession sess = HUtil.getHBSession();
		List<Object[]> list= sess.createSQLQuery("select code_type,code_value,code_name from code_value where code_type='"+codetype+"' and code_name like '%"+code_name+"%' limit 0,20 ").list();
		for(int i=0;i<list.size();i++) {
			namelistB.clear();
			String codename = getCodename( getListCodename(list.get(i)[1].toString(),list.get(i)[0].toString()));
			Object[] newdata = new Object[] {list.get(i)[0],list.get(i)[1],list.get(i)[2],codename};
			list.set(i, newdata);
		}
		return list;
		
	}
	public static List<String> namelist = new ArrayList<String>();
	public static List<String> namelistB = new ArrayList<String>();
	
	//�ݹ�õ�codenameȫ��,��������Ϊlist<String>
	public static List<String> getListCodename(String codevalue,String codetype){
		HBSession sess = HUtil.getHBSession();
		List<Object[]> list= sess.createSQLQuery("select sub_code_value,code_name from code_value where code_type='"+codetype+"'and code_value ='"+codevalue+"'").list();
		String sub_code_value = list.get(0)[0].toString();
		if(sub_code_value.equals("-1")||sub_code_value.equals("")){
			namelist.add(list.get(0)[1].toString());
			return namelist;
		}
		namelist.add(list.get(0)[1].toString());
		return getListCodename(sub_code_value, codetype);
	}
	
	//�õ�������codename����  ep:<span style='color:#27158b;'>(������ʡ>˫Ѽɽ��>��Ͻ��)</span>
	public static String getCodename(List<String> list) {
		//����list
		Collections.reverse(list);
		if(list.size()==1) {
			return "<span style='color:#27158b;'>("+list.get(0)+")</span>";
		}
		StringBuilder sb = new StringBuilder();
		for(String codename:list) {
			sb.append(codename+">");
		}
		sb.deleteCharAt(sb.length()-1);
		return "<span style='color:#27158b;'>("+sb.toString()+")</span>";
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("�ķ�̨��");
		/*list.add("��Ͻ��");
		list.add("˫Ѽɽ��");
		list.add("������ʡ");*/
		String s= getCodename(list);
		System.out.println(s);
	}
}



