package com.insigma.siis.local.business.sysorg.org;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis.TestForTjPageModel;


public class StatisticsWinBS extends BSSupport{
	
	//�������
	public static Object[] agearr(List<Object> list){
		
		Object[] o=new Object[8];
		int[] arr=new int[8];
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date date=new Date();
		String now=sdf.format(date);;
		try {
			 date=sdf.parse(now);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for(Object obj:list){
			if(obj!=null){
				
			
			String bir=obj.toString();
			Date d=null;
			if(bir.length()==6){
				bir+="01";
			}
			try {
				 d=sdf.parse(bir);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long birth=d.getTime();
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.YEAR, -31);
			Date under30=c.getTime();
			if(birth>under30.getTime()){
				arr[0]+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under35=c.getTime();
			if(birth>under35.getTime()&&birth<=under30.getTime()){
				arr[1]+=1;
				
			}
			c.add(Calendar.YEAR, -5);
			Date under40=c.getTime();
			if(birth>under40.getTime()&&birth<=under35.getTime()){
				arr[2]+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under45=c.getTime();
			if(birth>under45.getTime()&&birth<=under40.getTime()){
				arr[3]+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under50=c.getTime();
			if(birth>under50.getTime()&&birth<=under45.getTime()){
				arr[4]+=1;
			}
			c.add(Calendar.YEAR, -4);
			Date under54=c.getTime();
			if(birth>under54.getTime()&&birth<=under50.getTime()){
				arr[5]+=1;
			}
			c.add(Calendar.YEAR, -5);
			Date under59=c.getTime();
			if(birth>under59.getTime()&&birth<=under54.getTime()){
				arr[6]+=1;
			}
			if(birth<=under59.getTime()){
				arr[7]+=1;
			}
			
		 }
		}
		for(int i=0;i<o.length;i++){
			o[i]=arr[i];
		}
		return o;
	}
	
	//�ٷֱȼ���
	public static String percent(int num,int total){
		double baifenbi=(double)num/(double)total*100;
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(baifenbi);
	} 
	
	
	
	//�����ֵ
	public static String max(List list){
		Integer max=Integer.valueOf(list.get(0).toString());
		int temp;
		for(int i=1;i<list.size()-1;i++){
			temp = Integer.valueOf(list.get(i).toString());
			if(temp>max){
				max = temp;
			}	
		}		
		return max.toString();
	}
	//��ѯͳ��
	public static Object[] querySQ(String queryid)
			throws RadowException {
		String sql = "select SQ002,SQ003,SQ004 from statistics_query where SQ001='"+queryid+"'";
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			Object[] oo=null;
			if(list != null && ! list.isEmpty()){
				oo = list.get(0);
				return oo;
			}else{
				throw new RadowException("���ݿ��쳣");
			}
	}
	
	//��ѯ�Զ���ͳ������
	public static List<Object[]> queryQC(String queryid)
				throws RadowException {
		String sql = "select QC002,QC003 from query_condition where SQ001='"+queryid+"' order by QC001";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] oo=null;
		if(list != null && ! list.isEmpty()){
			return list;
		}else{
			throw new RadowException("���ݿ��쳣");
		}
	}
	//��ѯ����ͳ������
	public static Object[] queryCYQC(String queryid)
				throws RadowException {
		String sql = "select QC002,QC003 from query_condition where SQ001='"+queryid+"' order by QC001";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] oo=null;
		if(list != null && ! list.isEmpty()){
			return list.get(0);
		}else{
			throw new RadowException("���ݿ��쳣");
		}
	}
	//����ͳ��SQL����ͳ��
	public static Object[] querySQL(String sql,String groupid)
				throws RadowException {
		sql=sql.replaceAll("groupid", groupid.replace("|", "'").split("group")[0]);
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] oo=null;
		if(list != null && ! list.isEmpty()){
			return list.get(0);
		}else{
			throw new RadowException("���ݿ��쳣");
		}
	}
	//ͳ�Ƶ�λ����
	public static Object[] querySQL10(String sql)
				throws RadowException {
		String groupid = TestForTjPageModel.b0111;
		sql=sql.replaceAll("groupid", groupid.replace("|", "'"));
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Object[] oo=null;
		if(list != null && ! list.isEmpty()){
			return list.get(0);
		}else{
			throw new RadowException("���ݿ��쳣");
		}
	}
	
	//��ѯ��������
	public static String queryName(String groupid) throws RadowException
			 {
		String sql = "select b0101 from b01 where 1=1 and B0111='"+groupid+"'";
		String str = ""; 
		try{
			str =  (String) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return str;
	}
	//�ж��Ƿ���ڸû���Ů�ٷ�����
	public static boolean live(String groupid)
			throws RadowException {
		String sql = "select SS001 from STATISTICS_SEX where 1=1 and B0111='"+groupid+"'";
		List list= HBUtil.getHBSession().createSQLQuery(sql).list();
		try{
			if(list != null && list.size() > 0){
				return true;
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return false;
		
	}
	public static boolean live2(String groupid)
			throws RadowException {
		String sql = "select SE001 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		List list= HBUtil.getHBSession().createSQLQuery(sql).list();
		try{
			if(list != null && list.size() > 0){
				return true;
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return false;
		
	}
	
	//������
	public static Integer selectZrsCount(String groupid)
			throws RadowException {
		String sql = "select totalnumber from STATISTICS_SEX where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//�Ա���������ͳ��
	//Ů������
	public static Integer selectNvCount(String groupid)
			throws RadowException {
		String sql = "select SS002 from STATISTICS_SEX where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//������������
	public static Integer selectSmCount(String groupid)
			throws RadowException {
		String sql = "select SS003 from STATISTICS_SEX where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	//�й���Ա����
	public static Integer selectZgdyCount(String groupid)
			throws RadowException {
		String sql = "select SS004 from STATISTICS_SEX where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//ѧ��ͳ��
	//�о�������
	public static Integer selectYjsCount(String groupid)
				throws RadowException {
		String sql = "select SE002 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//��ѧ��������
	public static Integer selectDbCount(String groupid)
			throws RadowException {
		String sql = "select SE003 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//��ѧ��ר����
	public static Integer selectDzCount(String groupid)
			throws RadowException {
		String sql = "select SE004 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//��ר����
	public static Integer selectZzCount(String groupid)
			throws RadowException {
		String sql = "select SE005 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	
	//���м���������
	public static Integer selectGzCount(String groupid)
			throws RadowException {
		String sql = "select SE006 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
		Integer i = 0; 
		Object o ;
		try{
			o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			if(o!=null){
				i = Integer.parseInt(o.toString());
			}
		}catch(Exception e){
			throw new RadowException("���ݿ��쳣");
		}
		return i;
	}
	//δ��дѧ��
		public static Integer selectWtxCount(String groupid)
				throws RadowException {
			String sql = "select SE007 from STATISTICS_EDUCATIONLEVEL where 1=1 and B0111='"+groupid+"'";
			Integer i = 0; 
			Object o ;
			try{
				o =  (Object) HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
				if(o!=null){
					i = Integer.parseInt(o.toString());
				}
			}catch(Exception e){
				throw new RadowException("���ݿ��쳣");
			}
			return i;
		}
		
		
	//����SS������
	public static void insertSS(Integer nv,Integer sm,Integer zgdy,Integer zrs,String tjsj,String groupid)
			throws RadowException {
		String uuid=UUID.randomUUID().toString();
		String sql = "insert into STATISTICS_SEX (SS001,SS002,SS003,SS004,TOTALNUMBER,STIME,B0111) values ('"+uuid+"',"+nv+","+sm+","+zgdy+","+zrs+","+tjsj+",'"+groupid+"')" ;
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//����SS������
	public static void updateSS(Integer nv,Integer sm,Integer zgdy,Integer zrs,String tjsj,String groupid)
			throws RadowException {
		String sql = "update STATISTICS_SEX set SS002="+nv+",SS003="+sm+",SS004="+zgdy+",TOTALNUMBER="+zrs+",STIME="+tjsj+" where 1=1 and B0111='"+groupid+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//����SE������
	public static void insertSE(Integer yjs,Integer db,Integer dz,Integer zz,Integer gz,Integer wtx,Integer zrs,String tjsj,String groupid)
			throws RadowException {
		String uuid=UUID.randomUUID().toString();
		String sql = "insert into STATISTICS_EDUCATIONLEVEL (SE001,SE002,SE003,SE004,SE005,SE006,SE007,TOTALNUMBER,STIME,B0111) values ('"+uuid+"',"+yjs+","+db+","+dz+","+zz+","+gz+","+wtx+","+zrs+","+tjsj+",'"+groupid+"')" ;
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//����SE������
	public static void updateSE(Integer yjs,Integer db,Integer dz,Integer zz,Integer gz,Integer wtx,Integer zrs,String tjsj,String groupid)
			throws RadowException {
		String sql = "update STATISTICS_EDUCATIONLEVEL set SE002="+yjs+",SE003="+db+",SE004="+dz+",SE005="+zz+",SE006="+gz+",SE007="+wtx+",TOTALNUMBER="+zrs+",STIME="+tjsj+" where 1=1 and B0111='"+groupid+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//��ѯͳ��SS��
	public static Object[] doQuerySS(String groupid) throws RadowException{
		String sql = 
				"SELECT SUM(CASE WHEN a.a0104 = 2 THEN 1 ELSE 0 END) nv,"+
				   "SUM(CASE WHEN a.a0117 = 01 THEN 0 ELSE 1 END) sm,"+
				   "SUM(CASE WHEN a.a0141 = 01 THEN 1 ELSE 0 END) zgdy,"+
                   "count(a.a0000) zrs "+
             " FROM (SELECT a0000, a0104, a0117, a0141  FROM A01 a01  WHERE a01. STATUS = '1' AND a01.a0000 IN "+
                   "(SELECT a02.a0000 FROM a02 WHERE a02.a0255 = '1' AND a02.a0201b like '"+groupid+"%')"+
				   ") a";
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			Object[] oo=null;
			if(list != null && ! list.isEmpty()){
					oo = list.get(0);
					return oo;
			}else{
				throw new RadowException("���ݿ��쳣");
			}
	}
	
	//��ѯSE��
	public static Object[] doQuerySE(String groupid) throws RadowException{
		String sql = 
				"SELECT SUM(CASE WHEN a.a0801b LIKE '1_' THEN 1 ELSE 0 END) �о���,"+
                       "SUM(CASE WHEN a.a0801b LIKE '2_' THEN 1 ELSE 0 END) ��ѧ����,"+
                       "SUM(CASE WHEN a.a0801b LIKE '3_' THEN 1 ELSE 0 END) ��ѧ��ר,"+
				       "SUM(CASE WHEN a.a0801b = '41' THEN 1 ELSE 0 END) ��ר,"+
				       "SUM(CASE WHEN a.a0801b = '61' OR a.a0801b = '71' OR a.a0801b = '81' THEN 1 ELSE 0 END) ���м�����,"+
		               "SUM(CASE WHEN a.a0801b IS NULL THEN 1 ELSE 0 END) δ��д,"+
		               "count(b.a0000) ������ "+
                "FROM (SELECT * FROM A08 WHERE a0834 = '1') a"+
            " RIGHT OUTER JOIN (SELECT *"+
                    " FROM (SELECT a0000 FROM A01 a01 WHERE a01.STATUS = '1' AND a01.a0000 IN "+
                                  "(SELECT a02.a0000 FROM a02 WHERE a02.a0255 = '1' AND a02.a0201b like '"+groupid+"%')) a00 "+
                    ") b "+
                  " ON a.a0000 = b.a0000";	
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			Object[] oo=null;
			if(list != null && ! list.isEmpty()){
					oo = list.get(0);
					return oo;
			}else{
				throw new RadowException("���ݿ��쳣");
			}
	}

}
