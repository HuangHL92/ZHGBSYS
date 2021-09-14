package com.insigma.siis.local.business.sysorg.org;

import java.beans.IntrospectionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.ExeclUtil;
import com.picCut.servlet.SaveLrmFile;

public class SysOrgBS extends BSSupport{
	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	//����������ѯ
	public static B01 LoadB01(String id){
		try{
			return (B01) HBUtil.getHBSession().get(B01.class, id);
		}catch(Exception e){
			return null;
		}
	}

	public static List<B01DTO> selectB01s(){
		return selectB01s(null);
	}
	//������
	public static List<B01DTO> selectB01s(String nsjg){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//�����������ѯ��ʾ
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? "where t.b0194<>'2' " : " ";
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 "+nsjgsql+" order by t.sortid";
		if(SysRuleBS.issupermanager()){
			sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,'1' userrule,t.b0194 from b01 t";
		}
		/*List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] object=null;
		for(int i=0;i<list.size();i++){
			B01DTO b01dto=new B01DTO();
			object= (Object[]) list.get(i);
			String a = (String)object[0];
			b01dto.setB0111((String)object[0]);
			b01dto.setB0121((String)object[1]);
			b01dto.setB0101((String)object[2]);
			b01dto.setB0117((String)object[3]);
			b01dto.setSortid(((BigDecimal) object[4]).longValue());
			if((String)object[5]==null){
				b01dto.setType("2");
			}else{
				b01dto.setType((String)object[5]);
			}
			b01dto.setB0194((String)object[6]);
			b01dtolist.add(b01dto);
		}*/
		ResultSet rs = null;
		try {
			  rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
			  while(rs.next()){
				  	B01DTO b01dto=new B01DTO();
					b01dto.setB0111(rs.getString(1));
					b01dto.setB0121(rs.getString(2));
					b01dto.setB0101(rs.getString(3));
					b01dto.setB0117(rs.getString(4));
					b01dto.setSortid(Long.valueOf(rs.getObject(5)!=null?rs.getObject(5).toString():"0"));
					if(StringUtil.isEmpty(rs.getString(6))){
						b01dto.setType("2");
					}else{
						b01dto.setType(rs.getString(6));
					}
					b01dto.setB0194(rs.getString(7));
					b01dtolist.add(b01dto);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
			//���ԣ��ݲ��׳��쳣
		}
		
		
		return b01dtolist;
	}
	
	
	//�����ء���Ա��Ϣ��ѯҳ����
	public static List<B01DTO> selectB01sByPeople(){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//�����������ѯ��ʾ
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111 from b01 t,competence_userdept b where t.b0111=b.b0111 and userid='"+user.getId()+"'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sql2="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql2).list().get(0).toString();
		if(str.equals(String.valueOf(list.size()+1))){
			b01dtolist=selectB01s();
		}else{
			HashSet hashSet=new HashSet();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("001.001")&&list.get(i).length()>6){
					getHashSetTree(hashSet,list.get(i));
				}
			}
			hashSet.add("001.001");
			hashSet.add("-1");
			if(hashSet.size()>0){
			    Iterator ir=hashSet.iterator();
			    StringBuffer sb = new StringBuffer();
			    while(ir.hasNext()){
			    	sb.append("'").append(ir.next()).append("',");
			    }
			    String ids = sb.toString();
			    ids=ids.substring(0, ids.length()-1);
			    b01dtolist = selectB01sByPeople(ids,null);
			}else{
				return b01dtolist;
			}
		}
		return b01dtolist;
	}
	
	//�����ء���Ա��Ϣ��ѯҳ����
	public static List<B01DTO> selectB01sByPeopleNsjg(){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//�����������ѯ��ʾ
		List<B01DTO> b01dtolist = new ArrayList();
		String sql="select t.b0111 from b01 t,competence_userdept b where t.b0111=b.b0111 and userid='"+user.getId()+"' and t.b0194<>'2'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sql2="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql2).list().get(0).toString();
		if(str.equals(String.valueOf(list.size()+1))){
			b01dtolist=selectB01s("0");
		}else{
			HashSet hashSet=new HashSet();
			for(int i=0;i<list.size();i++){
				if(!list.get(i).equals("001.001")&&list.get(i).length()>6){
					getHashSetTree(hashSet,list.get(i));
				}
			}
			hashSet.add("001.001");
			hashSet.add("-1");
			if(hashSet.size()>0){
			    Iterator ir=hashSet.iterator();
			    StringBuffer sb = new StringBuffer();
			    while(ir.hasNext()){
			    	sb.append("'").append(ir.next()).append("',");
			    }
			    String ids = sb.toString();
			    ids=ids.substring(0, ids.length()-1);
			    b01dtolist = selectB01sByPeople(ids,"0");
			}else{
				return b01dtolist;
			}
		}
		return b01dtolist;
	}
	
	public static List<B01DTO> selectB01sByPeople(String ids,String nsjg){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//�����������ѯ��ʾ
		List<B01DTO> b01dtolist = new ArrayList();
		String[] a =ids.split(",");
		String sql="";
		String nsjgsql = (nsjg!=null && nsjg.equals("0")) ? " and t.b0194<>'2' " : " ";
		if(a.length>999){
			StringBuffer sqlString = new StringBuffer();
			for (int i = 0; i < a.length; i++) {  
                if (i == (a.length - 1)) {  
                    sqlString.append(a[i]); //SQLƴװ�����һ�����ӡ�,����  
                }else if((i%999)==0 && i>0){  
                    sqlString.append(a[i]).append(") or t.B0111 in ("); //���ORA-01795����  
                }else{  
                    sqlString.append(a[i]).append(",");  
                }  
            } 
			sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 where t.B0111 in ("+sqlString+") "+nsjgsql+" order by t.sortid";
		}else{
			if(ids.length()>0){
				sql="select t.b0111,t.b0121,t.b0101,t.b0107,t.sortid,b.type userrule,t.b0194 from b01 t  left   join (select * from competence_userdept where  userid='"+user.getId()+"') b  on t.b0111=b.b0111 where t.B0111 in ("+ids+")"+nsjgsql+" order by t.sortid";
			}
		}
		ResultSet rs = null;
		try {
			  rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
			  while(rs.next()){
				  	B01DTO b01dto=new B01DTO();
					b01dto.setB0111(rs.getString(1));
					b01dto.setB0121(rs.getString(2));
					b01dto.setB0101(rs.getString(3));
					b01dto.setB0117(rs.getString(4));
					b01dto.setSortid(Long.valueOf(rs.getObject(5)!=null?rs.getObject(5).toString():"0"));
					if(StringUtil.isEmpty(rs.getString(6))){
						b01dto.setType("2");
					}else{
						b01dto.setType(rs.getString(6));
					}
					b01dto.setB0194(rs.getString(7));
					b01dtolist.add(b01dto);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
			//���ԣ��ݲ��׳��쳣
		}
		
		
		return b01dtolist;
	}
	
	public static HashSet getHashSetTree(HashSet hashSet,String str){
		if(str.length()>6&&!str.equals("001.001")){
			hashSet.add(str);
			getHashSetTree(hashSet,str.substring(0, str.length()-4));
		}
		return hashSet;
	}
	
	
	//��ѯB01�������
	public static List<String[]> selectB01SStartWith(String id){
		String sql="select b.B0111 from b01 b where b.b0111 like '"+id+"%'";
		List<String[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		return list;
	}
	
	/**
	 * ɾ���������¼�����
	 * @param id
	 * @return count �Ƿ�ɹ�
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int delB01(String id) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		String userid=SysUtil.getCacheCurrentUser().getId();//��ȡ�û�Ȩ��id
		//����Ȩ���б�ɾ������a0195��ͳ�ƹ�ϵ���ڵ�λ��a0000����Աͳһ��ʶ����
		//a0201b����ְ�������룩
        String sql="delete from b01 where b0111 like '"+id+"%'";
		//String sql="delete from b01 where EXISTS (select b0111 from competence_userdept  where userid='"+userid+"'and b0111 like '"+id+"%' )  ";
		//String sqla02="update a02 t set t.a0201b='-1',t.a0201a='������λ',t.a0201c='' where t.a0201b like '"+id+"%'";
		System.out.println(sql);
		
		//����Ȩ�޸��°�a0195ͳ�ƹ�ϵ���ڵ�λ��a0000��Աͳһ��ʶ��,a0201b��ְ��������  ,b0111��������
		//String sqla01="update a01 t set t.a0195='' where a0000 in (select a0000 from a02 where a02.a0201b like '"+id+"%')";
		String sqla01="update a01 t set t.a0195='"+id+"' where a0000 in "
				+ "(select a0000 from a02,competence_userdept c where a0201b like'"+id+"%'"
						+"and c.userid='"+userid+"' and a0201b=c.b0111)";
//		String sqla01="update a01 t set t.a0195='' where a0000 in "
//		+ "(select a0000 from a02,competence_userdept c where a0201b like'"+id+"%'"
//				+"and c.userid='"+userid+"' and a0201b=c.b0111)";
//		
		
		List list =selectB01SStartWith(id);
		B01 logb01 = new B01();
		//SysOrgBS.getTime();
		for(int i=0 ;i<list.size();i++){
			B01 b01 = LoadB01(list.get(i).toString());
		//TODO ��Ա�޸���־
			try {
				new LogUtil().createLog("23", "B01", b01.getB0111(), b01.getB0101(), "����ɾ��", new Map2Temp().getLogInfo(b01, logb01));
				log.info("��ɾ������id��"+b01.getB0111()
						+"/��ɾ���������ƣ�"+ b01.getB0101()
						+"/ɾ����id��"+PrivilegeManager.getInstance().getCueLoginUser().getId()
						+"/ɾ����"+PrivilegeManager.getInstance().getCueLoginUser().getLoginname()
						+"/ɾ��ʱ�䣺"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//ɾ��Ȩ��,�����û�Ȩ��ɾ��
	   // String sql3 = "delete from COMPETENCE_USERDEPT  where  b0111 like '"+id+"%' ";
		
		String sql3 = "delete from COMPETENCE_USERDEPT where b0111 like '"+id+"%' and userid='"+userid+"'";
		System.out.println(sql3);

		HBUtil.getHBSession().createSQLQuery(sql3).executeUpdate();
		//SysOrgBS.getTime();
		int counta01 =HBUtil.getHBSession().createSQLQuery(sqla01).executeUpdate();
		//int counta02 =HBUtil.getHBSession().createSQLQuery(sqla02).executeUpdate();
		int count =HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		
		//ɾ������У�������
		String sql_verify_error_list=" delete from verify_error_list where vel002 like '"+id+"%' ";
		HBUtil.getHBSession().createSQLQuery(sql_verify_error_list).executeUpdate();
		//SysOrgBS.getTime();
		return count;
	}
	
	//��ѯB01�������
	public static String selectB01Count(){
		String sql="select count(*) from b01";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//��ѯ�Ƿ����¼��ڵ�  falseû�� true��
	public static String hasChildren(String id){
		String sql="from B01 b where B0121='"+id+"' order by sortid";// -1������ְ��Ա
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	
	//����������ѯ�Ƿ�������ݿ���
	public static String selectCountById(String id) throws RadowException{
		String sql="select count(*) from b01 t where B0111='"+id+"'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		if(str.equals("0")){
			throw new RadowException("��ǰ�������������ݿ���!");
		}
		return str;
	}
	
		//����ͳ�ƹ���
		//��ѯ�Ƿ����¼���������
		//�������˵�λ�µ���Ա�б���
		public static String selectCountBySubIdBz_Counts(String sql) throws RadowException{
			String userId = SysUtil.getCacheCurrentUser().getId();
			sql=" select count(*) from b01 t "
					+ " where t.b0194='1' "//�������� 1���˵�λ 2������� 3��������
					+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
					+ " where cu.userid='"+userId+"' "
					+ sql
					+ " and cu.type in ('0','1') "
					//+ " and cu.b0111=t.b0111"
					+ " ) ";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			return str;
		}
		
		//�쵼ְ��ͳ�ƹ���
		//��ѯ�Ƿ����¼���������
		public static String selectCountBySubId_Counts(String sql) throws RadowException{
			String userId = SysUtil.getCacheCurrentUser().getId();
			sql=" select count(*) from b01 t "
					+ " where t.b0194 in ('1','2')"//�������� 1���˵�λ 2������� 3��������
					+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
					+ " where cu.userid='"+userId+"' "
					+ sql
					+ " and cu.type in ('0','1') "
					//+ " and cu.b0111=t.b0111"
					+ " ) ";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			return str;
		}
	
	//����ͳ�ƹ���
	//��ѯ�Ƿ����¼���������
	//�������˵�λ�µ���Ա�б���
	public static String selectCountBySubIdBz(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" select count(*) from b01 t "
				+ " where t.B0121='"+id+"'"//�ϼ���λid
				+ " and t.b0194='1' "//�������� 1���˵�λ 2������� 3��������
				+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
				+ " where cu.userid='"+userId+"' "
				+ " and cu.type in ('0','1') "
				//+ " and cu.b0111=t.b0111"
				+ " ) ";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//�쵼ְ��ͳ�ƹ���
	//��ѯ�Ƿ����¼���������
	public static String selectCountBySubId(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" select count(*) from b01 t "
				+ " where t.B0121='"+id+"'"//�ϼ���λid
				+ " and t.b0194 in ('1','2')"//�������� 1���˵�λ 2������� 3��������
				+ " and t.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
				+ " where cu.userid='"+userId+"' "
				+ " and cu.type in ('0','1') "
				//+ " and cu.b0111=t.b0111"
				+ " ) ";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//����ͳ�ƹ���
	//��ѯ�¼�����
	public static List<B01> selectListBySubIdBz(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" from B01 "
				+ " where B0121='"+id+"' "//�ϼ���λ����  
				+ " and B0194 ='1' "//1-���˵�λ ���¼�����������Ա�Աȣ�����ͳ�Ʒ��˵�λ�����˵�λ�µ���Ա��ӵ�б��ƣ�
				+ " and b0111 in (select cu.b0111 from Competenceuserdept cu "
    						+ " where cu.userid='"+userId+"' "
    						+ " and cu.type in ('0','1') "
			        		//+ " and cu.b0111=b0111"
			        		+ " ) "
				+ " order by sortid ";//�����ֶ�  
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}

	//�쵼ְ��ͳ�ƹ���
	//��ѯ�¼�����
	//���˵�λ����������µ���Աͳ��
	public static List<B01> selectListBySubId(String id) throws RadowException{
		String userId = SysUtil.getCacheCurrentUser().getId();
		String sql=" from B01  "
				+ " where B0121='"+id+"' "//�ϼ���λ����  
				+ " and B0194 !='3' "//3 ��������
				+ " and b0111 in (select cu.b0111 from Competenceuserdept cu "
        						+ " where cu.userid='"+userId+"' "
        						+ " and cu.type in ('0','1') "
				        		//+ " and cu.b0111=b0111"
				        		+ " ) "
				+ " order by sortid ";//�����ֶ�  
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	//��ѯ�¼���������EXECL��
	public static List<B01> selectListByExecl(String id) throws RadowException{
		/**
		 * update by zoul 2017��5��17��
		 * ['1','���˵�λ'],['2','�������'],['3','��������']
		 * ͳ�Ʊ������¼��������
		 */
		String sql="from B01 where (B0121='"+id+"' and b0194 in ('2')) or B0111='"+id+"' order by sortid";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	/**
	 * ��ȡCodeValue����
	 * @param
	 * @param
	 * @return
	 */
	public static CodeValue getCodeValue(String codeType ,String codeValue,String subCodeValue ){
		CodeValue codeValueObj = null;
		String hql = "from CodeValue where codeType=:codeType and codeValue=:codeValue";
		if(!StringUtil.isEmpty(subCodeValue)){
			hql = hql+" and subCodeValue = :subCodeValue";
		}
		HBSession sess = HBUtil.getHBSession();
		Query query =  sess.createQuery(hql).setString("codeType", codeType==null?"":codeType.toUpperCase()).setString("codeValue", codeValue==null?"":codeValue.toUpperCase());
		if(!StringUtil.isEmpty(subCodeValue)){
			query = query.setString("subCodeValue", subCodeValue);
		}
		List<CodeValue> list = query.list();
		if(list!=null && !list.isEmpty()){
			codeValueObj = list.get(0);
		}
		return codeValueObj;
	}
	
	//ҳ���ʼ����ѯ
	public static Object[] queryInit(){
		Object[] area = (Object[]) HBUtil.getHBSession().createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
		return area;
	}
	
	/**
	 * ��ȡCodeValue����
	 * @param
	 * @param
	 * @return
	 */
	public static Map getCodeValue(String codeType){
		CodeValue codeValueObj = null;
		String hql = "from CodeValue where codeType=:codeType ";
		HBSession sess = HBUtil.getHBSession();
		Query query =  sess.createQuery(hql).setString("codeType", codeType==null?"":codeType.toUpperCase());
		List<CodeValue> list = query.list();
		Map map = null;
		if(list!=null && !list.isEmpty()){
			map = new HashMap();
			CodeValue codeValue = new CodeValue();
			for(int i=0;i<list.size();i++){
				codeValue =list.get(i);
				map.put(codeValue.getCodeValue(), codeValue.getCodeName());
			}
		}
		return map;
	}
	public static byte[] wirteExeclLowOrgLeader(InputStream is,List<B01> list,int counts) throws RadowException, AppException{
		try {
			HSSFWorkbook work = new HSSFWorkbook(is);
			HSSFSheet sheet = work.getSheetAt(0);
			if(counts==0){//û���¼����˻������������
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//����6�������ƶ�һ��
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//����6�������ƶ�һ��
			}else if(counts==1){//����һ���¼�����
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);//����6�������ƶ�һ��
	            B01 b01 =list.get(0);
	            HSSFRow row = sheet.getRow(3);//��ȡ������
				HSSFCell[] cells = new HSSFCell[15];//15������
				for(int k = 0;k<15;k++){
					cells[k] = row.getCell(k);//�����е�15������//��ȡ��sheet�����4�е�15��cell���� 
				}
				//��������
				cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());//ÿһ������д������
				
				String temp="";
				temp=(String)getCodeValue("ZB03").get(b01.getB0127());//�������� ZB03   ��
				cells[1].setCellValue(temp==null?"":temp);
				
				temp=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' "); 
				cells[2].setCellValue(temp==null?"":temp);
				
				
				
				long b0183 = b01.getB0183()==null?0:b01.getB0183();
				cells[7].setCellValue(b0183);//��ְ�쵼ְ��
				sheet.getRow(4).getCell(7).setCellValue(b0183);
			
				int rightLeaderCount = Integer.valueOf(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));
				cells[8].setCellValue(rightLeaderCount);//ʵ������
				sheet.getRow(4).getCell(8).setCellValue(rightLeaderCount);
				if(rightLeaderCount-b0183>0){
					cells[9].setCellValue(rightLeaderCount-b0183);
					sheet.getRow(4).getCell(9).setCellValue(rightLeaderCount-b0183);//����=ʵ��-��ְ�쵼ְ��
					cells[10].setCellValue("0");
					sheet.getRow(4).getCell(10).setCellValue("0");
				}else{
					cells[9].setCellValue("0");
					sheet.getRow(4).getCell(9).setCellValue("0");
					cells[10].setCellValue(b0183-rightLeaderCount);
					sheet.getRow(4).getCell(10).setCellValue(b0183-rightLeaderCount);//ȱ��=��ְ�쵼ְ��-ʵ��
				}
				
				long b0185 = b01.getB0185()==null?0:b01.getB0185();
				cells[11].setCellValue(b0185);//��ְ�쵼ְ��
				sheet.getRow(4).getCell(11).setCellValue(b0185);
				
				int viceLeaderCount = Integer.valueOf(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));
				cells[12].setCellValue(viceLeaderCount);//ʵ������
				sheet.getRow(4).getCell(12).setCellValue(viceLeaderCount);
				if(viceLeaderCount-b0185>0){
					cells[13].setCellValue(viceLeaderCount-b0185);
					sheet.getRow(4).getCell(13).setCellValue(viceLeaderCount-b0185);//����=ʵ��-��ְ�쵼ְ��
					cells[14].setCellValue("0");
					sheet.getRow(4).getCell(14).setCellValue("0");
				}else{
					cells[13].setCellValue("0");
					sheet.getRow(4).getCell(13).setCellValue("0");
					cells[14].setCellValue(b0185-viceLeaderCount);
					sheet.getRow(4).getCell(14).setCellValue(b0185-viceLeaderCount);//ȱ��=��ְ�쵼ְ��-ʵ��
				}
			
				//3
				cells[3].setCellValue(b0183+b0185);//��ְ�쵼ְ��+��ְ�쵼ְ��
				sheet.getRow(4).getCell(3).setCellValue(b0183+b0185);
				//4
				cells[4].setCellValue(viceLeaderCount+rightLeaderCount);//��ְ�쵼ʵ������+��ְ�쵼ʵ������
				sheet.getRow(4).getCell(4).setCellValue(viceLeaderCount+rightLeaderCount);
				int temp9 = 0;
				int temp13 = 0;
				int temp10 = 0;
				int temp14 = 0;
				try {
					temp9 = Integer.valueOf(cells[9].getStringCellValue());
				} catch (Exception e) {
					temp9 = 0;
				}
				try {
					temp13 = Integer.valueOf(cells[13].getStringCellValue());
				} catch (Exception e) {
					temp13 = 0;
				}
				try {
					temp10 = Integer.valueOf(cells[10].getStringCellValue());
				} catch (Exception e) {
					temp10 = 0;
				}
				try {
					temp10 = Integer.valueOf(cells[10].getStringCellValue());
				} catch (Exception e) {
					temp10 = 0;
				}
				//5
				cells[5].setCellValue(temp9+temp13);//����
				sheet.getRow(4).getCell(5).setCellValue(temp9+temp13);
				//6
				cells[6].setCellValue(temp10+temp14);//����
				sheet.getRow(4).getCell(6).setCellValue(temp10+temp14);
				/*//5
				cells[5].setCellValue(Integer.valueOf(cells[9].getStringCellValue())+Integer.valueOf(cells[13].getStringCellValue()));//����
				sheet.getRow(4).getCell(5).setCellValue(Integer.valueOf(cells[9].getStringCellValue())+Integer.valueOf(cells[13].getStringCellValue()));
				//6
				cells[6].setCellValue(Integer.valueOf(cells[10].getStringCellValue())+Integer.valueOf(cells[14].getStringCellValue()));//����
				sheet.getRow(4).getCell(6).setCellValue(Integer.valueOf(cells[10].getStringCellValue())+Integer.valueOf(cells[14].getStringCellValue()));*/
			}else{
				int c=0;//��ְ�쵼ְ��-�ܼ�
				int d=0;//ʵ������-�ܼ�
				int e=0;//����=ʵ��-��ְ�쵼ְ�� �ܼ� 
				int f=0;//ȱ��=��ְ�쵼ְ��-ʵ�� �ܼ�
				int g=0;//��ְ�쵼ְ��-�ܼ�
				int h=0;//ʵ������-�ܼ�
				int ii=0;//����=ʵ��-��ְ�쵼ְ�� �ܼ�
				int j=0;//ȱ��=��ְ�쵼ְ��-ʵ�� �ܼ�
//				int kk=0;//��ְ�쵼ְ��+��ְ�쵼ְ�� �ܼ�
//				int l=0;//��ְ�쵼ʵ������+��ְ�쵼ʵ������ �ܼ�
//				int m=0;//���� �ܼ�
//				int n=0;//ȱ��
				
				//HSSFWorkbook hk = new HSSFWorkbook();
				HSSFCellStyle newstyle = work.createCellStyle();
				for(int i=0;i<counts-2;i++){//�����¼����˵�λ�����������������������
					int insertRowNum =3;//������
					sheet.shiftRows(insertRowNum+i, sheet.getLastRowNum(), 1,true,false);//���3������һ��
					HSSFRow fromrow = sheet.getRow(4+i);//������ʽ�ĵ�Ԫ��//����������
					HSSFRow torow = sheet.getRow(insertRowNum+i);//���Ƶ�//����������
					ExeclUtil.copyRow(newstyle,work,fromrow,torow,true);//���Ƶ�Ԫ����ʽ//���Ƶ�4+i�е���ʽ��3+i��
				}
				B01 b01 = new B01();
				for(int i=0;i<list.size();i++){
					b01=list.get(i);
		            HSSFRow row = sheet.getRow(3+i);
					HSSFCell[] cells = new HSSFCell[18];
					for(int k = 0;k<18;k++){
						cells[k] = row.getCell(k);
					}
					//��������
					cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());
					
					String temp="";
					temp=(String)getCodeValue("ZB03").get(b01.getB0127());//�������� ZB03   ��
					cells[1].setCellValue(temp==null?"":temp);
					
					temp=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' ");
					cells[2].setCellValue(temp==null?"":temp);
					
					long b0183 = b01.getB0183()==null?0:b01.getB0183();
					cells[7].setCellValue(b0183);//��ְ�쵼ְ��
					c=(int) (c+b0183);//��ְ�쵼ְ��-�ܼ�
					//ʵ������
					int rightLeaderCount = Integer.valueOf(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));
					d=d+rightLeaderCount;//ʵ������-�ܼ�
					cells[8].setCellValue(rightLeaderCount);
					if(rightLeaderCount-b0183>0){
						cells[9].setCellValue(rightLeaderCount-b0183);//����=ʵ��-��ְ�쵼ְ��
						e=(int) (e+(rightLeaderCount-b0183));//����=ʵ��-��ְ�쵼ְ�� �ܼ� 
					}else{
						if(rightLeaderCount-b0183!=0){
							cells[10].setCellValue(b0183-rightLeaderCount);//ȱ��=��ְ�쵼ְ��-ʵ��
							f=(int) (f+(b0183-rightLeaderCount));//ȱ��=��ְ�쵼ְ��-ʵ�� �ܼ�
						}
					}
					
					long b0185 = b01.getB0185()==null?0:b01.getB0185();
					cells[11].setCellValue(b0185);//��ְ�쵼ְ��
					g=(int) (g+b0185);//��ְ�쵼ְ��-�ܼ�
					//��ְ�쵼
					int viceLeaderCount = Integer.valueOf(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));//ʵ������
					h=h+viceLeaderCount;//ʵ������-�ܼ�
					cells[12].setCellValue(viceLeaderCount);
					if(viceLeaderCount-b0185>0){
						cells[13].setCellValue(viceLeaderCount-b0185);//����=ʵ��-��ְ�쵼ְ��
						ii=(int) (ii+(viceLeaderCount-b0185));//����=ʵ��-��ְ�쵼ְ�� �ܼ�
					}else{
						if(viceLeaderCount-b0185!=0){
							cells[14].setCellValue(b0185-viceLeaderCount);//ȱ��=��ְ�쵼ְ��-ʵ��
							j=(int) (j+(b0185-viceLeaderCount));//ȱ��=��ְ�쵼ְ��-ʵ�� �ܼ�
						}
					}
					
					//3
					cells[3].setCellValue( ((b0183+b0185)==0)?"":((b0183+b0185)+"") );//��ְ�쵼ְ��+��ְ�쵼ְ��
					//4
					cells[4].setCellValue( ((viceLeaderCount+rightLeaderCount)==0)?"":((viceLeaderCount+rightLeaderCount)+"") );//��ְ�쵼ʵ������+��ְ�쵼ʵ������
					//5
					long chaopei=0;
					long qupei=0;
					//��ְ
					if(rightLeaderCount-b0183>0){//����
						chaopei=chaopei+(rightLeaderCount-b0183);
					}else{//ȱ��
						qupei=chaopei+(b0183-rightLeaderCount);
					}
					//��ְ
					if(viceLeaderCount-b0185>0){
						chaopei=chaopei+(viceLeaderCount-b0185);
					}else{
						qupei=qupei+(b0185-viceLeaderCount);
					}
					cells[5].setCellValue( (chaopei==0)?"":(chaopei+"") );//����
					//6
					cells[6].setCellValue( (qupei==0)?"":(qupei+"") );//ȱ��
					
				}
				sheet.getRow(counts+3).getCell(7).setCellValue(c);
				sheet.getRow(counts+3).getCell(8).setCellValue(d);
				sheet.getRow(counts+3).getCell(9).setCellValue(e);
				sheet.getRow(counts+3).getCell(10).setCellValue(f);
				sheet.getRow(counts+3).getCell(11).setCellValue(g);
				sheet.getRow(counts+3).getCell(12).setCellValue(h);
				sheet.getRow(counts+3).getCell(13).setCellValue(ii);
				sheet.getRow(counts+3).getCell(14).setCellValue(j);
				
				sheet.getRow(counts+3).getCell(3).setCellValue( (c+g==0)?"":((c+g)+"") );//kk
				sheet.getRow(counts+3).getCell(4).setCellValue( (d+h)==0?"":((d+h)+"") );
				sheet.getRow(counts+3).getCell(5).setCellValue( (e+ii)==0?"":((e+ii)+"") );//o
				sheet.getRow(counts+3).getCell(6).setCellValue( (f+j)==0?"":((f+j)+"") );
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			work.write(bos);
			bos.close();
			is.close();
			byte[] bytes = null;
			return bytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		} catch (RadowException e) {
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
	}
	
	//������Ա�Ա�
	public static byte[] wirteExeclLowOrgPeople(InputStream is,int counts,List<B01> list){
		try {
			HSSFWorkbook work = new HSSFWorkbook(is);
			HSSFSheet sheet = work.getSheetAt(0);
			if(counts==0){
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
			}else if(counts==1){
	            sheet.shiftRows(4, sheet.getLastRowNum(), -1);
	            B01 b01 =list.get(0);
	            HSSFRow row = sheet.getRow(3);
				HSSFCell[] cells = new HSSFCell[26];
				for(int k = 0;k<26;k++){
					cells[k] = row.getCell(k);
				}
				cells[0].setCellValue(b01.getB0101()==null?"":b01.getB0101());
				cells[1].setCellValue((String)getCodeValue("ZB03").get(b01.getB0127()==null?"":b01.getB0127()));
				
				long b0227 = b01.getB0227()==null?0:b01.getB0227();
				cells[2].setCellValue(b0227);
				//��������
				int civilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));
				cells[3].setCellValue(civilServantCount);
				if(civilServantCount-b0227>0){
					cells[4].setCellValue(civilServantCount-b0227);
					sheet.getRow(4).getCell(4).setCellValue(civilServantCount-b0227);
				}else{
					cells[5].setCellValue(b0227-civilServantCount);
					sheet.getRow(4).getCell(5).setCellValue(b0227-civilServantCount);
				}
				
//				long b0235 = b01.getB0235()==null?0:b01.getB0235();
//				cells[6].setCellValue(b0235);
				//����ר�������
				//int zfzxbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));
				//cells[7].setCellValue(zfzxbzs);
//				if(zfzxbzs-b0235>0){
//					cells[8].setCellValue(zfzxbzs-b0235);
//					sheet.getRow(4).getCell(8).setCellValue(zfzxbzs-b0235);
//				}else{
//					cells[9].setCellValue(b0235-zfzxbzs);
//					sheet.getRow(4).getCell(9).setCellValue(b0235-zfzxbzs);
//				}
				
				long b0232 = b01.getB0232()==null?0:b01.getB0232();
				cells[6].setCellValue(b0232);
				//��ҵ����(�ι�)
				int likeCivilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));
				cells[7].setCellValue(likeCivilServantCount);
				if(likeCivilServantCount-b0232>0){
					cells[8].setCellValue(likeCivilServantCount-b0232);
					sheet.getRow(4).getCell(8).setCellValue(likeCivilServantCount-b0232);
				}else{
					cells[9].setCellValue(b0232-likeCivilServantCount);
					sheet.getRow(4).getCell(9).setCellValue(b0232-likeCivilServantCount);
				}
				
				long b0233 = b01.getB0233()==null?0:b01.getB0233();
				cells[10].setCellValue(b0233);
				//��ҵ����(����)
				int careerServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
				cells[11].setCellValue(careerServantCount);
				if(careerServantCount-b0233>0){
					cells[12].setCellValue(careerServantCount-b0233);
					sheet.getRow(4).getCell(12).setCellValue(careerServantCount-b0233);
				}else{
					cells[13].setCellValue(b0233-careerServantCount);
					sheet.getRow(4).getCell(13).setCellValue(b0233-careerServantCount);
				}
				
				
//				long b0236 = b01.getB0236()==null?0:b01.getB0236();
//				cells[14].setCellValue(b0236);
//				//���ڱ���
//				int gqbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"5"));
//				cells[15].setCellValue(gqbzs);
//				if(gqbzs-b0236>0){
//					cells[16].setCellValue(gqbzs-b0236);
//					sheet.getRow(4).getCell(16).setCellValue(gqbzs-b0236);
//				}else{
//					cells[17].setCellValue(b0236-gqbzs);
//					sheet.getRow(4).getCell(17).setCellValue(b0236-gqbzs);
//				}
//
//				long b0234 = b01.getB0234()==null?0:b01.getB0234();
//				cells[18].setCellValue(b0234);
//				//��������
//				int qtbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"9"));
//				cells[19].setCellValue(qtbzs);
//				if(qtbzs-b0234>0){
//					cells[20].setCellValue(qtbzs-b0234);
//					sheet.getRow(4).getCell(20).setCellValue(qtbzs-b0234);
//				}else{
//					cells[21].setCellValue(b0234-qtbzs);
//					sheet.getRow(4).getCell(21).setCellValue(b0234-qtbzs);
//				}
//
//				sheet.getRow(4).getCell(2).setCellValue(b0227);
//				sheet.getRow(4).getCell(3).setCellValue(civilServantCount);
//
////				sheet.getRow(4).getCell(6).setCellValue(b0235);
////				sheet.getRow(4).getCell(7).setCellValue(zfzxbzs);
//
//				sheet.getRow(4).getCell(6).setCellValue(b0232);
//				sheet.getRow(4).getCell(7).setCellValue(likeCivilServantCount);
//
//				sheet.getRow(4).getCell(10).setCellValue(b0233);
//				sheet.getRow(4).getCell(11).setCellValue(careerServantCount);
//
//				sheet.getRow(4).getCell(14).setCellValue(b0236);
//				sheet.getRow(4).getCell(15).setCellValue(gqbzs);
//
//				sheet.getRow(4).getCell(19).setCellValue(b0234);
//				sheet.getRow(4).getCell(19).setCellValue(qtbzs);
//
			}else{
				int c=0,d=0,e=0,f=0,g=0,h=0,ii=0,j=0,kk=0,
				l=0,m=0,n=0,
				//o=0,p=0,q=0,r=0,
				s=0,t=0,u=0,v=0,w=0,x=0,y=0,z=0;
				
				HSSFCellStyle newstyle = work.createCellStyle();
				for(int i=0;i<counts-2;i++){
					int insertRowNum =3;//������
					sheet.shiftRows(insertRowNum+i, sheet.getLastRowNum(), 1,true,false);
					HSSFRow fromrow = sheet.getRow(6+i);//������ʽ�ĵ�Ԫ��
					HSSFRow torow = sheet.getRow(insertRowNum+i);//���Ƶ�
					ExeclUtil.copyRow(newstyle,work,fromrow,torow,true);//���Ƶ�Ԫ����ʽ
				}
				B01 b01 = new B01();
				for(int i=0;i<list.size();i++){
					b01=list.get(i);
		            HSSFRow row = sheet.getRow(3+i);
					HSSFCell[] cells = new HSSFCell[26];
					for(int k = 0;k<26;k++){
						cells[k] = row.getCell(k);
					}
					String temp="";
					temp=b01.getB0101();
					cells[0].setCellValue((temp==null||"0".equals(temp))?"":temp);
					temp=(String)getCodeValue("ZB03").get(b01.getB0127()==null?"":b01.getB0127());
					cells[1].setCellValue((temp==null||"0".equals(temp))?"":temp);
					
					long b0227 = b01.getB0227()==null?0:b01.getB0227();
					cells[2].setCellValue((b0227==0)?"":(b0227+""));
					c=(int) (c+b0227);
					//����������
					int civilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));
					d=d+civilServantCount;
					cells[3].setCellValue((civilServantCount==0)?"":(civilServantCount+""));
					if(civilServantCount-b0227>0){
						cells[4].setCellValue(civilServantCount-b0227);
						e=(int) (e+(civilServantCount-b0227));
					}else{
						if(civilServantCount-b0227!=0){
							cells[5].setCellValue(b0227-civilServantCount);
							f=(int) (f+(b0227-civilServantCount));
						}
					}
					
//					long b0235 = b01.getB0235()==null?0:b01.getB0235();
//					cells[6].setCellValue(b0235);
//					o = (int)(o+b0235);
					//����ר�������
//					int zfzxbzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));
//					p = (int)(p+zfzxbzs);
//					cells[7].setCellValue(zfzxbzs);
//					if(zfzxbzs-b0235>0){
//						cells[8].setCellValue(zfzxbzs-b0235);
//						q = (int)(q+(zfzxbzs-b0235));
//					}else{
//						cells[9].setCellValue(b0235-zfzxbzs);
//						r = (int)(r+(b0235-zfzxbzs));
//					}
					
					long b0232 = b01.getB0232()==null?0:b01.getB0232();
					cells[6].setCellValue((b0232==0)?"":(b0232+""));
					g=(int) (g+b0232);
					//��ҵ���ƣ��ι���
					int likeCivilServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));
					h=h+likeCivilServantCount;
					cells[7].setCellValue((likeCivilServantCount==0)?"":(likeCivilServantCount+""));
					if(likeCivilServantCount-b0232>0){
						cells[8].setCellValue(likeCivilServantCount-b0232);
						ii=(int) (ii+(likeCivilServantCount-b0232));
					}else{
						if(likeCivilServantCount-b0232!=0){
							cells[9].setCellValue(b0232-likeCivilServantCount);
							j=(int) (j+(b0232-likeCivilServantCount));
						}
					}
					
					long b0233 = b01.getB0233()==null?0:b01.getB0233();
					cells[10].setCellValue((b0233==0)?"":(b0233+""));
					kk=(int) (kk+b0233);
					//��ҵ���ƣ�������
					int careerServantCount = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
					l=l+careerServantCount;
					cells[11].setCellValue((careerServantCount==0)?"":(careerServantCount+""));
					if(careerServantCount-b0233>0){
						cells[12].setCellValue(careerServantCount-b0233);
						m=(int) (m+(careerServantCount-b0233));
					}else{
						if(careerServantCount-b0233!=0){
							cells[13].setCellValue(b0233-careerServantCount);
							n=(int) (n+(b0233-careerServantCount));
						}
					}
					
//					long b0236 = b01.getB0236()==null?0:b01.getB0236();
//					cells[14].setCellValue((b0236==0)?"":(b0236+""));
//					s=(int) (s+b0236);
//					//���ڱ���
//					int qtsybzs = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
//					t=t+qtsybzs;
//					cells[15].setCellValue((qtsybzs==0)?"":(qtsybzs+""));
//					if(qtsybzs-b0236>0){
//						cells[16].setCellValue(qtsybzs-b0236);
//						u=(int) (u+(qtsybzs-b0236));
//					}else{
//						if(qtsybzs-b0236!=0){
//							cells[17].setCellValue(b0236-qtsybzs);
//							v=(int) (v+(b0236-qtsybzs));
//						}
//					}
//
//					long b0234 = b01.getB0234()==null?0:b01.getB0234();
//					cells[18].setCellValue((b0234==0)?"":(b0234+""));
//					w=(int) (w+b0234);
//					//��������
//					int qtbz = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));
//					x=x+qtbz;
//					cells[19].setCellValue((qtbz==0)?"":(qtbz+""));
//					if(qtbz-b0234>0){
//						cells[20].setCellValue(qtbz-b0234);
//						y=(int) (y+(qtbz-b0234));
//					}else{
//						if(qtbz-b0234!=0){
//							cells[21].setCellValue(b0234-qtbz);
//							z=(int) (z+(b0234-qtbz));
//						}
//					}
//
					
					
					
				}
				
				sheet.getRow(counts+3).getCell(2).setCellValue((c==0)?"":(c+""));
				sheet.getRow(counts+3).getCell(3).setCellValue((d==0)?"":(d+""));
				sheet.getRow(counts+3).getCell(4).setCellValue((e==0)?"":(e+""));
				sheet.getRow(counts+3).getCell(5).setCellValue((f==0)?"":(f+""));
				
//				sheet.getRow(counts+3).getCell(6).setCellValue(o);
//				sheet.getRow(counts+3).getCell(7).setCellValue(p);
//				sheet.getRow(counts+3).getCell(8).setCellValue(q);
//				sheet.getRow(counts+3).getCell(9).setCellValue(r);
				
				sheet.getRow(counts+3).getCell(6).setCellValue((g==0)?"":(g+""));
				sheet.getRow(counts+3).getCell(7).setCellValue((h==0)?"":(h+""));
				sheet.getRow(counts+3).getCell(8).setCellValue((ii==0)?"":(ii+""));
				sheet.getRow(counts+3).getCell(9).setCellValue((j==0)?"":(j+""));
				sheet.getRow(counts+3).getCell(10).setCellValue((kk==0)?"":(kk+""));
				sheet.getRow(counts+3).getCell(11).setCellValue((l==0)?"":(l+""));
				sheet.getRow(counts+3).getCell(12).setCellValue((m==0)?"":(m+""));
				sheet.getRow(counts+3).getCell(13).setCellValue((n==0)?"":(n+""));
				
				sheet.getRow(counts+3).getCell(14).setCellValue((s==0)?"":(s+""));
				sheet.getRow(counts+3).getCell(15).setCellValue((t==0)?"":(t+""));
				sheet.getRow(counts+3).getCell(16).setCellValue((u==0)?"":(u+""));
				sheet.getRow(counts+3).getCell(17).setCellValue((v==0)?"":(v+""));
				sheet.getRow(counts+3).getCell(18).setCellValue((w==0)?"":(w+""));
				sheet.getRow(counts+3).getCell(19).setCellValue((x==0)?"":(x+""));
				sheet.getRow(counts+3).getCell(20).setCellValue((y==0)?"":(y+""));
				sheet.getRow(counts+3).getCell(21).setCellValue((z==0)?"":(z+""));
				
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			work.write(bos);
			bos.close();
			is.close();
			byte[] bytes = null;
			return bytes = bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void getTime(){
        Calendar nowtime = new GregorianCalendar();  
        String strDateTime="["+String.format("%04d", nowtime.get(Calendar.YEAR))+"/"+  
                String.format("%02d", nowtime.get(Calendar.MONTH))+"/" +  
                String.format("%02d", nowtime.get(Calendar.DATE))+" " +  
                String.format("%02d", nowtime.get(Calendar.HOUR))+":" +  
                String.format("%02d", nowtime.get(Calendar.MINUTE))+":" +  
                String.format("%02d", nowtime.get(Calendar.SECOND))+"." +  
                String.format("%03d", nowtime.get(Calendar.MILLISECOND))+"]";  
        CommonQueryBS.systemOut("=========="+strDateTime); 
	}
	/**
	 * ����ɾ��ǰ��ѡ�еĻ�����Ϣ���뵽CPB01��¥���棬���������õĴ���
	 * @param id
	 */
	public static void selectOut(String id,CurrentUser user){
		//String sq1 = "insert into CPB01 select * from b01 where b0111 like  '"+id+"%'";
		//HBUtil.getHBSession().createSQLQuery(sq1).executeUpdate();
		//String sq2 = "insert into COMPETENCE_USERDEPTcpb01 select sys_guid(),"+user.getId()+",b0111,type from COMPETENCE_USERDEPT where b0111 like  '"+id+"%'";
		//HBUtil.getHBSession().createSQLQuery(sq2).executeUpdate();
	}
	/**
	 * ����Ҫά����SQLд�뵽list��
	 * @param groupid ����ID
	 * @return �Ƿ�ά�����
	 */
	public static boolean maintenanceB01(String groupid){
		List<String> list = new ArrayList<String>();
		list.add("update smt_user s set s.otherinfo='X001' where s.otherinfo='"+groupid+"'");//�޸�smt_user
		list.add("delete from statistics_age where b0111 like '"+groupid+"%'");//���º�۷�����--lzy update
		list.add("delete from statistics_educationlevel where b0111 like '"+groupid+"%'");
		list.add("delete from statistics_highestpostlevel where b0111 like '"+groupid+"%'");
		list.add("delete from statistics_sex where b0111 like '"+groupid+"%'");
		list.add("update a01 set a0195 = '-1' where a0195 like '"+groupid+"%'");//ά��a01�е�ͳ�ƹ�ϵ���ڵ�λ
		boolean whetherSuccess = true;
		int count = list.size();
		int countTemp = 0;
		for(String sql:list){
			try {
				HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
				countTemp++;
			} catch (Exception e) {
				e.printStackTrace();
				whetherSuccess=false;
			}
		}
		if(whetherSuccess&&countTemp==count){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �жϸû������Ƿ�����Ա ��ְ��Ա
	 * @param groupid ����ID
	 * @return ��Ա����
	 */
	public static int whetherPersonOn1(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//�жϻ������Ƿ�����Ա�����Ӹû�������ְ״̬
			String sql = "select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.a0163='1' "
					 + " and a.status!='4' "//�����Ա����ʱ��û�е�����棬ϵͳ��������������
					 + " and b.a0281='true' "//�����־
					+ " and b.a0201b like '"+groupid+"%' and b.a0255='1'";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	/**
	 * �жϸû������Ƿ�����Ա ����ְ��Ա
	 * @param groupid ����ID   a0163��Ա����״̬    a0281ְ�������ʶ
	 * @return ��Ա����
	 */
	public static int whetherPersonOn2(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "SELECT count(a01.a0000) FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.a0163 IN ('2', '3') AND A02.A0201B LIKE '"+groupid+"%' AND EXISTS (SELECT a30.a0000 FROM a30 WHERE a30.a0000 = a01.a0000 AND a30.a3001 IN ('31', '32'))";
			String sql = "SELECT count(a01.a0000) "
					+ " FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 "
					+ " WHERE a01.a0163 IN ('21', '22','23','29') "
					+ " and a02.a0281='true' "//�����־
					+ " AND A02.A0201B LIKE '"+groupid+"%' ";
			String str2 = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	/**
	 * �жϸû������Ƿ�����Ա
	 * @param groupid ����ID ��a0000����,status״̬ 0��ȫɾ�� 1���� 2��ʷ��3������Ա4��ʱ����,
	 * b0201b��ְ��������   ��      a0255   ��ְ״̬   ��
	 * @return ��Ա����
	 */
	public static int whetherPersonOn(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//�жϻ������Ƿ�����Ա�����Ӹû�������ְ״̬
			String sql = "select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b like '"+groupid+"%' and b.a0255='1'";
			String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			sql = "SELECT count(a01.a0000) FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.status IN ('2', '3') AND A02.A0201B LIKE '"+groupid+"%' AND EXISTS (SELECT a30.a0000 FROM a30 WHERE a30.a0000 = a01.a0000 AND a30.a3001 IN ('31', '32'))";
			String str2 = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			count_temp = Integer.parseInt(str)+Integer.parseInt(str2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	/**
	 * �жϸû����Ƿ����¼�
	 * @param groupid ����ID
	 * @return ��Ա����
	 */
	public static int whetherHasChile(String groupid) {
		int count_temp = 0;
		try {
			//String sql = "Select Count(1) From a02 t where t.a0201b like '"+groupid+"%' and a0255='1' ";//�жϻ������Ƿ�����Ա�����Ӹû�������ְ״̬
			String sql = "select count(1) from b01 where b0121=:groupid";
			String str = HBUtil.getHBSession().createSQLQuery(sql).setString("groupid", groupid).uniqueResult().toString();
			count_temp=Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return count_temp;
	}
	
	
	
} 