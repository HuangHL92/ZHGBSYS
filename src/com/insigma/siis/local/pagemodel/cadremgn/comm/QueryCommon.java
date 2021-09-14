package com.insigma.siis.local.pagemodel.cadremgn.comm;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class QueryCommon {
	/*
	 * �Զ���������ѯ  -- ��Ϣ�� 
	 * @return ȫ����Ϣ��
	 */
	public List<HashMap<String, Object>> queryTable() throws AppException{//������grid������
		CommQuery cqbs=new CommQuery();
		String sql="select  table_code table_code ,concat(concat(table_code,'.'),table_name) table_name from code_table where table_code<>'A32' order by  table_name";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * �Զ���������ѯ  -- ָ����  
	 * ��Ӧ��Ϣ������Ϣ��
	 * @return ����ָ����
	 */
	public List<HashMap<String, Object>> queryCode(String tablename) throws AppException{//������1.��Ϣ����
		CommQuery cqbs=new CommQuery();
		String sql="select col_code col_code,concat(concat(col_code,'.'),col_name)  col_name,code_type code_type from code_table_col where table_code='"+tablename+"' "
//				+ " and ISUSE='1' "
				+ " order by  col_name";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * ����У��������� ��ѯ��ӦУ����Ϣ
	 * @return ����У�����
	 */
	public List<HashMap<String, Object>> queryVerifyRule(String vsc001,String vru001) throws AppException{//������1.��Ϣ����
		CommQuery cqbs=new CommQuery();
		String sql="select vru001,vru002,vru004,vru005,VRU010,VRUTIME from verify_rule where vru001='"+vru001+"' and vsc001='"+vsc001+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * ��ѯ���������
	 * @return �������� ��
	 * 
	 */
	public List<HashMap<String, Object>> queryOperator(String code_level) throws AppException{//������ѡ��ֻ��ʾ�������  ����� CODE_VALUE.code_type �ֶΣ�
		CommQuery cqbs=new CommQuery();
		String sql="select concat(concat(t.code_name2,' '),"+CommonSQLUtil.NVL()+"(t.code_name3,'')) as code_value,concat(concat(concat(concat(t.code_name,' ( '),t.code_name2),"+CommonSQLUtil.NVL()+"(t.code_name3,'')),')') as code_name from CODE_VALUE t where t.code_type in ('VSL006','VSL012') and t.code_value in ("+code_level+") order by t.sub_code_value asc";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * �Զ���������ѯ  -- ��ѯĳָ�������к���ʽ
	 */
	public List<HashMap<String, Object>> queryFunction() throws AppException{
		CommQuery cqbs=new CommQuery();
		String sql="select ID as code_value,CHECKNAME as code_name from CHECK_FUNCTION";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * ��ѯ�Զ�����ͼ||��ѯ����Ϣ������Ϣ��
	 */
	public HashMap<String, Object> queryVerifyScheme() throws AppException{//������1.��Ϣ������2.�ֶ�����
		CommQuery cqbs=new CommQuery();
		String o1 = "";
		String o2 = "";
		HashMap<String,Object> map = new HashMap<String,Object>();
		String sql="select v.VSC001,v.vsc002 from verify_scheme v where v.vsc007='0' order by v.VSC006";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		for (int i = 0; i < list.size(); i++) {
			o1 = list.get(i).get("vsc001").toString();
			o2 = list.get(i).get("vsc002").toString();
			map.put(o1,o2);
		}
		return map;
	}
	
	/*
	 * ��ѯ��ϲ�ѯ����������б�
	 */
	public List<HashMap<String, Object>> queryQueryCall(String qvid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String sql="select"
				+ " q.tblname,"
				+ " q.fldcode,"
				+ " q.fldname,"
				+ " q.lable_type wzp_codetype,"
				+ " q.sign,"
				+ " concat(concat(concat(concat(concat(concat(concat(concat(q.fldname,' '),c.code_name),' '),q.valuecode1),q.valuename1),'   '),q.valuecode2),q.valuename2) condition_name,"
				+ " q.valuename1,"
				+ " q.valuename2,"
				+ " q.valuecode1,"
				+ " q.valuecode2"
				+ " from qryuse q left join code_value c on c.code_value=q.sign"
				+ " where qvid='"+qvid+"'"
				+ " order by sort asc";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	/*
	 * ��ѯ���� -- qryviewfld
	 */
	public List<HashMap<String, Object>> queryQryviewfld(String qvid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String sql = "select tblname,fldname,fldnamenote from qryviewfld where qvid = '"+qvid+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		return list;
	}
	
	/*
	 * ��ѯ���� qvid -- qryview
	 */
	public String queryQryview(String userid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String qvids="''";
		String sql = "select qvid from qryview where type='3' and userid='"+userid+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list.size()!=0){
			for (HashMap<String, Object> hashMap : list) {
				qvids = qvids +"'"+ hashMap.get("qvid").toString() + "',";
			}
			qvids = qvids.substring(0,qvids.length()-1);
		}
		return qvids;
	}
	
	/*
	 * ��ѯȨ�� -- ��Ա
	 */
	public String queryPowerPeople(String userid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String peoples="''";
		String sql = "select t.a0000 from Competence_Subperson t where t.userid='"+userid+"' and t.type = '1'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list.size()!=0){
			for (HashMap<String, Object> hashMap : list) {
				peoples = peoples +"'"+ hashMap.get("a0000").toString() + "',";
			}
			peoples = peoples.substring(0,peoples.length()-1);
		}
		return peoples;
	}
	/*
	 * ��ѯȨ�� -- ��Ա�������
	 */
	public String queryPowerCompetence(String userid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String competences="''";
		String sql = "select t.managerid,t.laidoff from COMPETENCE_USERMANAGER t where t.userid = '"+userid+"' and t.type = '1'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list.size()!=0){
			for (HashMap<String, Object> hashMap : list) {
				competences = competences +"'"+ hashMap.get("qvid").toString() + "',";
			}
			competences = competences.substring(0,competences.length()-1);
		}
		return competences;
	}
	/*
	 * ��ѯȨ�� -- ��Ϣ��
	 */
	public String queryPowerInfoSet(String userid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String infoSets="''";
		String sql = "select t.table_code,t.islook,t.isadd,t.ischange,t.isdel from COMPETENCE_USERTABLE t where t.userid = '"+userid+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list.size()!=0){
			for (HashMap<String, Object> hashMap : list) {
				infoSets = infoSets +"'"+ hashMap.get("qvid").toString() + "',";
			}
			infoSets = infoSets.substring(0,infoSets.length()-1);
		}
		return infoSets;
	}
	/*
	 * ��ѯȨ�� -- ��Ϣ��
	 */
	public String queryPowerInfoItem(String userid) throws AppException{
		CommQuery cqbs=new CommQuery();
		String infoItems="''";
		String sql = "select t.table_code,t.col_code,t.islook,t.ischange,t.ischeckout from COMPETENCE_USERTABLECOL t where t.userid in ('"+Utils.getRoleId(userid)+"')";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list.size()!=0){
			for (HashMap<String, Object> hashMap : list) {
				infoItems = infoItems +"'"+ hashMap.get("table_code").toString() + "." + hashMap.get("col_code").toString() + "',";
			}
			infoItems = infoItems.substring(0,infoItems.length()-1);
		}
		return infoItems;
	}
	
	/*
	 * ��ȡ�������ε���Ϣ����Ϣ
	 */
	public String queryInfoSetMax(String qvid) throws AppException{
		HBSession session = HBUtil.getHBSession();
		String infoSetMax="''";
		String sql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){	
			sql = "select tblname from qryviewfld where qvid = '"+qvid+"' and rownum = 1 group by tblname order by count(1) desc";
		}else{			//mysql
			sql = "select tblname from qryviewfld where qvid = '"+qvid+"'  group by tblname order by count(1) desc limit 1";
		}
		
		infoSetMax = (String)session.createSQLQuery(sql).uniqueResult();
		return infoSetMax;
	}
}
