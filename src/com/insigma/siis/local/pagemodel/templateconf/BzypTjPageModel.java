package com.insigma.siis.local.pagemodel.templateconf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.WholeStatus;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class BzypTjPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("showtype")
	public int showtype(String gid){
		HBSession sess =HBUtil. getHBSession ();
		String sql="select (select B0101  from b01 where B0111='"+gid+"') unit,table_type, team_responsibility,general_evaluation,optimize_direction,id from comprehensive_set where unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			Object[]  obj = list.get(0);
			String parem = "";
			for (Object object : obj) {
				parem+=object+",";
			}
			parem= parem.substring(0,parem.length()-1);
			this.getExecuteSG().addExecuteCode("window.comprevalue('"+parem+"');");
		}else{
			this.getExecuteSG().addExecuteCode("window.comprevalue('error');");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 遍历职能职数
	 * @throws RadowException 
	 * */
	@PageEvent("Znzs")
	public int Znzs(String gid) throws RadowException{
		String shy = "\"";
		HBSession sess =HBUtil. getHBSession ();
		//String sql="select PROJECT,QUANTITY , unit,duty_category from duty_num where  unit='"+gid+"'";
		String sql="select PROJECT,QUANTITY , unit,duty_category,duty_rank,one_ticket_veto from duty_num where  unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list != null && list.size() > 0){
			String parall="";
			for (int i = 0; i < list.size(); i++) {
				String parem="";
				Object[] objects = list.get(i);
		        for (Object object : objects) {
					parem+=object+",";
					
				}
		        parem= parem.substring(0,parem.length()-1);
		        parall+=parem+"&";
			}
			parall=parall.substring(0,parall.length()-1);
			String[] str=parall.split("&");
			String person = "";
			for (String string : str) {
				String[] split = string.split(",");
				String nll = split[1];
				String zwlb = split[3];//ZB42
				//String zhw1 = "select code_name from code_value where code_type='ZB42'and code_value='"+split[3]+"'";
				//String zwlb = (String) sess.createSQLQuery(zhw1).uniqueResult();
				//String zhw = "select code_name from code_value where code_type='ZB03'and code_value='"+split[4]+"'";
				String zwzj = split[4];//ZB03
				//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"' and a02.a0219q='"+zwlb+"' and a02.a0221='"+zwzj+"') aa where  aa.a0107='"+nll+"'";
				String sql1="select count(1) from (select distinct a01.a0000 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"' and a02.a0219q='"+zwlb+"' and a02.a0221='"+zwzj+"' and a0279='1') aa ";
				String fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"# and a02.a0219q=#"+zwlb+"# and a02.a0221=#"+zwzj+"# and a0279=#1#) aa";
				//String sql1="select count(1) from (select distinct a01.a0000,a01.a0101 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where aa.a0101='熊涛'";
				 //count =  (java.math.BigDecimal)sess.createSQLQuery(sql1).uniqueResult();
				BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
				String count = cou.toString();
				person+=string+","+count+"@@@"+shy+fc+shy+"&";
			}
			person=person.substring(0,person.length()-1);
			/*String sql2="select creattime from duty_num where unit='"+gid+"'";
		    String creattime=(String) sess.createSQLQuery(sql2).uniqueResult();     */
			String sql2="select  min(to_number(creattime)) from duty_num where unit='"+gid+"'";
			BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
		    String creattime = cou.toString();
			this.getExecuteSG().addExecuteCode("znzsvalue('"+person+"','"+creattime+"');");
		}else{
			 this.getPageElement("znzsvv").setValue("asd");
			 this.getExecuteSG().addExecuteCode("znzsvalue('没有数据');");
		}
		  
				
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 遍历职位说明
	 * */
	@PageEvent("Zwsm")
	public int Zwsm(String gid){
		HBSession sess =HBUtil. getHBSession ();
		//String sql="select POSITION_DIVISION_OF_LABOR,POSITION_NAME , QUALIFICATION_CONDITION,EQUIP_STATE from POSITION_EXPLAIN where  unit='"+gid+"'";
		//List<Object[]> list = sess.createSQLQuery(sql).list();
		String sql="select POSITION_DIVISION_OF_LABOR,POSITION_NAME , QUALIFICATION_CONDITION,EQUIP_STATE, AGE,GENDER,EDUCATION,MAJOR,JOB_NAMED,POLITICAL_PARTY,TERRITORY,WORK_ON,JOB_LEVEL,CLASSES,ABILITY_REQUIRED,CONDITION_SCREEN from POSITION_EXPLAIN where  unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list != null && list.size() > 0){
			String parall="";
			for (int i = 0; i < list.size(); i++) {
				String parem="";
				Object[] objects = list.get(i);
				for (Object object : objects) {
					parem+=object+"@";
					
				}
				parem= parem.substring(0,parem.length()-1);
				parall+=parem+"$";
			}
			parall=parall.substring(0,parall.length()-1);//职位分工@职位名称@资格条件@武杰@>=&20@1@31@011@职称@01@01@从事分管&4@级别层次&3#0#职务层次&2@类别&1@02@1#2#3#4#0#6#0#0#0$44555@3333@555@武杰@>=&11@1@31@null@null@null@null@&@&#0#&@&@null@1#3#2#0#0#0#0#0#0
			
			
			
			
			
			
			
			String sls="";
			String countsum="";
			String rytjqk="";
			for (int i = 0; i < list.size(); i++) {
				Object[] objects = list.get(i);
				 
				String count="";
				String stj="";
					String pbqk = (String) objects[3];
					String agenl = (String) objects[4];
					String[] nianl =agenl.split("&");
					String nl =nianl[0]+nianl[1];//年龄
					String xb=(String) objects[5];
					String xl=(String) objects[6];
					String zy=(String) objects[7];
					String zc=(String) objects[8];
					String dp=(String) objects[9];
					String dy=(String) objects[10];
					String csfg=(String) objects[11];
					String jborzw=(String) objects[12];
					String lb=(String) objects[13];
					String nlyq=(String) objects[14];
					String sxtj=(String) objects[15];//筛选条件
					String gzjl=csfg+"@"+jborzw+"@"+lb;//工作经历
					String[]sxtj1=sxtj.split("#");
					for (String string2 : sxtj1) {
						if("1".equals(string2)){
							string2="GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107"+"$" +"aa.a0107"+nl;
						}else if("2".equals(string2)){
							string2="a01.a0104"+"$"+"aa.a0104="+"'"+xb+"'";
						}else if("3".equals(string2)){
							string2=xl;
							String sql1="select code_name from code_value where code_type='ZB64' and code_value='"+xl+"'";
							String a=(String) sess.createSQLQuery(sql1).uniqueResult();
							string2="a01.qrzxl,a01.zzxl"+"$"+"("+"aa.qrzxl="+"'"+a+"'"+"or"+" "+"aa.zzxl="+"'"+a+"'"+")";
						}else if("4".equals(string2)){
							string2=zy;
							String sql1="select code_name from code_value where code_type='GB8561' and code_value='"+zy+"'";
							String a=(String) sess.createSQLQuery(sql1).uniqueResult();
							string2="a01.a0196"+"$"+"aa.a0196="+"'"+a+"'";
						}else if("5".equals(string2)){
							string2=zc;
							string2="modelconfig.expertise"+"$"+"aa.expertise="+"'"+zc+"'";
						}else if("6".equals(string2)){
							string2=nlyq;
							string2="modelconfig.ability"+"$"+"aa.ability="+"'"+nlyq+"'";
						}else if("7".equals(string2)){
							string2=dp;
							string2="a01.a0141"+"$"+"aa.a0104="+"'"+dp+"'";
						}else if("8".equals(string2)){
							string2=dy;
							string2="modelconfig.region"+"$"+"aa.region="+"'"+dy+"'";
						}else if("0".equals(string2)){
							string2="";
						}
						if(!"".equals(string2)){
							stj+=string2+"@";
						}
						
					}
					stj=stj.substring(0,stj.length()-1);
					int sumCount=0;//实际查询次数
					int counttj=0;
					String[] sqlc =stj.split("@");
					String keyzd ="";
					String keyvalue="";
					for (String string3 : sqlc) {
						String[] keyAll=string3.split("\\$");
						String keyzd1 = keyAll[0];
						String keyvalue1 = keyAll[1];
						 keyzd+=keyzd1+",";//字段
						 keyvalue+=keyvalue1+" "+"and"+" ";//查询条件
					}
					
					keyzd= keyzd.substring(0,keyzd.length()-1);
					keyvalue=keyvalue.trim();
					keyvalue= keyvalue.substring(0,keyvalue.length()-3);
					String sqlcount="select count(1) from (select distinct a01.a0000,"+keyzd+" from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue+" ";
					//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sqlcount).uniqueResult();
				    count = cou.toString();
				    sumCount++;
				    int len=0;//应该查询次数
				    len = sqlc.length;
				    String count1="";
				    String sjname="";
				    if(!"0".equals(count)){
				    	String tjyr1="select * from (select distinct a01.a0000,a01.a0101,"+keyzd+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue+" ";
				    	List<Object[]>  listname1 = sess.createSQLQuery(tjyr1).list();
				    	for (Object[] object1 : listname1) {
							Object sjname1 = object1[1];
							
							sjname+=sjname1+",";
						}
				    	sjname= sjname.substring(0,sjname.length()-1);
				    }
				    if("0".equals(count)&&(len-1)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-1; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count1 = cou1.toString();
					    if(!"0".equals(count1)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count1;
					    }
				    }
				    
				    String count2="";
				    if("0".equals(count1)&&(len-2)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-2; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count2 = cou1.toString();
					    if(!"0".equals(count2)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count2;
					    }
				    }
				    String count3="";
				    if("0".equals(count2)&&(len-3)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-3; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count3 = cou1.toString();
					    if(!"0".equals(count3)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count3;
					    }
				    }
				    String count4="";
				    if("0".equals(count3)&&(len-4)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-4; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count4 = cou1.toString();
					    if(!"0".equals(count4)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count4;
					    }
				    }
				    String count5="";
				    if("0".equals(count4)&&(len-5)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-5; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count5 = cou1.toString();
					    if(!"0".equals(count5)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count5;
					    }
				    }
				    String count6="";
				    if("0".equals(count5)&&(len-6)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-6; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count6 = cou1.toString();
					    if(!"0".equals(count6)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count6;
					    }
				    }
				    String count7="";
				    if("0".equals(count6)&&(len-7)>=1){
				    	sumCount++;
				    	String keyzd2 ="";
				    	String keyvalue2="";
				    	String[]sqlc1=stj.split("@");
				    	List list1=new ArrayList();
				    	for (int j = 0; j < sqlc1.length-7; j++) {
							list1.add(sqlc1[j]);
						}
				    	Object[] array = list1.toArray();
				    	for (Object object1 : array) {
				    		String str1=(String)object1;
				    		String[] keyAll = str1.split("\\$");
				    		String keyzd1 = keyAll[0];
				    		String keyvalue1 = keyAll[1];
				    		 keyzd2+=keyzd1+",";//字段
							 keyvalue2+=keyvalue1+" "+"and"+" ";//查询条件
				    		
						}
				    	
				    	//String[]newsqlc=sqlc1.
				    	keyzd2= keyzd2.substring(0,keyzd2.length()-1);
						keyvalue2=keyvalue2.trim();
						keyvalue2= keyvalue2.substring(0,keyvalue2.length()-3);
						String sqlcount1="select count(1) from (select distinct a01.a0000,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
						//String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0107 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='001.001.002.001.004') aa where  aa.a0107='"+nll+"'";
						BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sqlcount1).uniqueResult();
					    count7 = cou1.toString();
					    if(!"0".equals(count7)){
					    	String tjyr="select * from (select distinct a01.a0000,a01.a0101,"+keyzd2+" from a01 inner join a02 on a01.a0000=a02.a0000  and a02.a0201b='"+gid+"' left join MODELCONFIG on a01.a0000=MODELCONFIG.id ) aa where "+keyvalue2+" ";
					    	List<Object[]>  listname = sess.createSQLQuery(tjyr).list();
					    	for (Object[] object1 : listname) {
								Object sjname1 = object1[1];
								
								sjname+=sjname1+",";
							}
					    	sjname= sjname.substring(0,sjname.length()-1);
					    	count=count7;
					    }
					    
				}
				    counttj=(100/len)*(len-sumCount+1);
				    rytjqk=sjname;
				   
				   // countsum+=counttj+"%"+","+rytjqk+"@";
				    countsum+=rytjqk+"("+counttj+"%"+")"+"@";
				
			}
			sls=countsum.substring(0,countsum.length()-1);
			this.getExecuteSG().addExecuteCode("zwsmvalue('"+parall+"','"+sls+"');");		
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 遍历自然结构tab1
	 * */
	@PageEvent("zrjg")
	public int zrjg(String gid){
		HBSession sess =HBUtil. getHBSession ();
		String sql="select category, quantity,project,one_ticket_veto  from natural_structure where  unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		String shy = "\"";
		String day = "\\'";
		if(list != null && list.size() > 0){
			String nl = "";//年龄结构
			String xl = "";
			String zy = "";
			String xb = "";
			String dp = "";
			String mz = "";
			String ly = "";
			String zc = "";
			String dy = "";
			String nel = "";//能力结构
			String jy = "";
			String jl = "";
			String rqz = "";
			String parall="";
			for (int i = 0; i < list.size(); i++) {
				String parem="";
				Object[] objects = list.get(i);
				if("nl".equals(objects[0])){//年龄
					String count="";
					String fc = "";
					for (Object object : objects) {
						if("51-55岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  51 and 55 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							 count = cou.toString();
							 fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111 BETWEEN  51 and 55 ";
							 
						}else if("35岁及以下".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111<=35 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111<=35 ";
						}else if("36-40岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  36 and 40 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111 BETWEEN  36 and 40 ";
						}else if("41-45岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  41 and 45 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111 BETWEEN  41 and 45 ";
						}else if("46-50岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  46 and 50 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111 BETWEEN  46 and 50 ";
						}else if("56岁及以下".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111<=56 ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,#"+DateUtil.getcurdate()+"#)a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111 BETWEEN  46 and 50 ";
						}
						nl+=object+",";
					}
					//nl=nl.substring(0,nl.length()-1);
					nl=nl+count+"@&&&"+shy+fc+shy+"$";
				}else if("xl".equals(objects[0])){//学历
					String fc = "";
					String sqlxl="select code_name from code_value where code_type='ZB64' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sqlxl).uniqueResult();
					objects[2]=a;
					String count="";
					for (Object object : objects) {
						String sql1="select count(1) from (select distinct a01.a0000,a01.qrzxl,a01.zzxl from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.qrzxl='"+objects[2]+"'or zzxl='"+objects[2]+"'";
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						xl+=object+",";
						fc = "select * from (select distinct a01.a0000,a01.qrzxl,a01.zzxl , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.qrzxl=#"+objects[2]+"# or zzxl=#"+objects[2]+"#";
					}
					//xl=xl.substring(0,xl.length()-1);
					xl=xl+count+"@&&&"+shy+fc+shy+"$";
				}else if("zy".equals(objects[0])){//专业
					
					String fc = "";
					String sql1="select code_name from code_value where code_type='GB8561' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0196 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0196='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					for (Object object : objects) {
						
						zy+=object+",";
						/*String fc1="select code_value from code_value where code_type='GB8561' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();*/
						fc = "select * from (select distinct a01.a0000,a01.a0196 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192  from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0196=#"+objects[2]+"#";
					}
					//zy=zy.substring(0,zy.length()-1);
					zy=zy+count+"@&&&"+shy+fc+shy+"$";
				}else if("xb".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='GB2261' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					String count="";
					String fc = "";
					for (Object object : objects) {
						String sql2="";
						if("男性".equals(objects[2])){
							 sql2="select count(1) from (select distinct a01.a0000,a01.a0104 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0104='1'";
						     fc = "select * from (select distinct a01.a0000,a01.a0104 a0111,a0101,(select code_name from code_value where code_type=#GB2261# and code_value=#1#)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111=#1#";
						}else{
							sql2="select count(1) from (select distinct a01.a0000,a01.a0104 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0104='2'";
							fc = "select * from (select distinct a01.a0000,a01.a0104 a0111,a0101,(select code_name from code_value where code_type=#GB2261# and code_value=#2#)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0111=#2#";
						}
						
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
						count = cou.toString();
						xb+=object+",";
					}
					//xb=xb.substring(0,xb.length()-1);
					xb=xb+count+"@&&&"+shy+fc+shy+"$";
				}else if("dp".equals(objects[0])){//党派
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0141 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0141='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc = "";
					String sql1="select code_name from code_value where code_type='GB4762' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						dp+=object+",";
						String fc1="select code_value from code_value where code_type='GB4762' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
						fc = "select * from (select distinct a01.a0000,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0141=#"+fc2+"#";
					}
					//dp=dp.substring(0,dp.length()-1);
					dp=dp+count+"@&&&"+shy+fc+shy+"$";
				}else if("mz".equals(objects[0])){//民族
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0117 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0117='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc = "";
					String sql1="select code_name from code_value where code_type='GB3304' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					for (Object object : objects) {
						
						mz+=object+",";
						String fc1="select code_value from code_value where code_type='GB3304' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
						fc = "select * from (select distinct a01.a0000,a01.a0117, a01.a0101,(CASE a01.a0104 WHEN #1# THEN #男# else #女# end)a0104,(substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b=#"+gid+"#) aa where aa.a0117=#"+fc2+"#";
					}
					//mz=mz.substring(0,mz.length()-1);
					mz=mz+count+"@&&&"+shy+fc+shy+"$";
				}else if("ly".equals(objects[0])){//来源
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.source from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.source='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc = "";
					String sql1="select code_name from code_value where code_type='LYJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						ly+=object+",";
						String fc1="select code_value from code_value where code_type='LYJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.source from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.source = #"+fc2+"#";
					}
					//ly=ly.substring(0,ly.length()-1);
					ly=ly+count+"@&&&"+shy+fc+shy+"$";
				}else if("zc".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.expertise from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.expertise='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc = "";
					String sql1="select code_name from code_value where code_type='ZCJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						zc+=object+",";
						String fc1="select code_value from code_value where code_type='ZCJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.expertise from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.expertise = #"+fc2+"#";
					}
					zc=zc+count+"@&&&"+shy+fc+shy+"$";
				}else if("dy".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.region from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.region='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc = "";
					String sql1="select code_name from code_value where code_type='DYJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						dy+=object+",";
						String fc1="select code_value from code_value where code_type='DYJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.region from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.region = #"+fc2+"#";
					}
					dy=dy+count+"@&&&"+shy+fc+shy+"$";
				}else if("nel".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.ability from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.ability='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc="";
					String sql1="select code_name from code_value where code_type='NLJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						nel+=object+",";
						String fc1="select code_value from code_value where code_type='NLJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.ability from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.ability = #"+fc2+"#";
					}
					nel=nel+count+"@&&&"+shy+fc+shy+"$";
				}else if("jy".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.experience from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.experience='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc="";
					String sql1="select code_name from code_value where code_type='JYJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						jy+=object+",";
						String fc1="select code_value from code_value where code_type='JYJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.experience from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.experience = #"+fc2+"#";
					}
					jy=jy+count+"@&&&"+shy+fc+shy+"$";
				}else if("jl".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.undergo from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.undergo='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc="";
					String sql1="select code_name from code_value where code_type='JLJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						jl+=object+",";
						String fc1="select code_value from code_value where code_type='JLJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.undergo from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.undergo = #"+fc2+"#";
					}
					jl=jl+count+"@&&&"+shy+fc+shy+"$";
				}else if("rqz".equals(objects[0])){//专长
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.tenuresystem from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.tenuresystem='"+objects[2]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					String count = cou.toString();
					String fc="";
					String sql1="select code_name from code_value where code_type='RQZJG' and code_value='"+objects[2]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[2]=a;
					//String count="";
					for (Object object : objects) {
						
						rqz+=object+",";
						String fc1="select code_value from code_value where code_type='RQZJG' and code_name='"+objects[2]+"'";
						String fc2=(String) sess.createSQLQuery(fc1).uniqueResult();
				        fc = "select * from (select distinct a01.a0000,a01.a0101,(CASE  a01.a0104 WHEN #1# THEN #男# else #女# end)a0104, (substr (a01.a0107,1,4)||#.#||substr (a01.a0107,-4,2))a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.tenuresystem from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = #"+gid+"#) asd where asd.tenuresystem = #"+fc2+"#";
				       // fc = " a01.a0000 = MODELCONFIG.id and a02.a0201b = "+gid+") asd.tenuresystem = "+fc2+"";
					}
					rqz=rqz+count+"@&&&"+shy+fc+shy+"$";
				}
			
			}
			if(!"".equals(nl)){
				nl=nl.substring(0,nl.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+nl+"');");	
			}
			if(!"".equals(xl)){
				xl=xl.substring(0,xl.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+xl+"');");	
			}
			if(!"".equals(zy)){
				zy=zy.substring(0,zy.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+zy+"');");	
			}
			if(!"".equals(xb)){
				xb=xb.substring(0,xb.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+xb+"');");	
			}
			if(!"".equals(dp)){
				dp=dp.substring(0,dp.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+dp+"');");	
			}
			if(!"".equals(mz)){
				mz=mz.substring(0,mz.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+mz+"');");	
			}
			if(!"".equals(ly)){
				ly=ly.substring(0,ly.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+ly+"');");	
			}
			if(!"".equals(zc)){
				zc=zc.substring(0,zc.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+zc+"');");	
			}
			if(!"".equals(dy)){
				dy=dy.substring(0,dy.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+dy+"');");	
			}
			if(!"".equals(nel)){
				nel=nel.substring(0,nel.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+nel+"');");	
			}
			if(!"".equals(jy)){
				jy=jy.substring(0,jy.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+jy+"');");	
			}
			if(!"".equals(jl)){
				jl=jl.substring(0,jl.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+jl+"');");	
			}
			if(!"".equals(rqz)){
				rqz=rqz.substring(0,rqz.length()-1);
				this.getExecuteSG().addExecuteCode("zrjg('"+rqz+"');");	
			}
		}else{
			this.getExecuteSG().addExecuteCode("zrjgk();");	
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		PageElement pageElement = this.getPageElement("gid");
		String gid=pageElement.getValue();
		HBSession sess =HBUtil. getHBSession();
		String sql = "  select distinct A01.A0101,A01.A0192A,(SUBSTR(a01.a0107, 1, 4)) || '.' ||(SUBSTR(a01.a0107, -4, 2)) A0107, A01.a0111A, A01.QRZXL, A01.QRZXLXX, A01.ZZXL, A01.ZZXLXX, A01.A0196,(SUBSTR(a01.a0134, 1, 4)) || '.' || （SUBSTR(a01.a0134, -4, 2))A0134, (SUBSTR(a01.a0288, 1, 4)) || '.' || （SUBSTR(a01.a0288, -4, 2))A0288,(SUBSTR(a01.a0192c, 1, 4)) || '.' || （SUBSTR(a01.a0192c, -4, 2)) A0192C from a01 inner join a02 on a01.a0000 = a02.a0000 and a02.a0201b = '"+gid+"' and a0201d='1'";
		//String sql="select distinct A01.A0101 A0101,A01.A0192A,(SUBSTR(a01.a0107, 1, 4)) || '.' ||(SUBSTR(a01.a0107, -4, 2)) A0107,(SUBSTR(a01.a0192c, 1, 4)) || '.' || （SUBSTR(a01.a0192c, -4, 2)) A0192C from a01 inner join a02 on a01.a0000 = a02.a0000 and a02.a0201b = '"+gid+"'";
		 //String sql="select a0101,a0192c from a01";
		//List list = sess.createQuery(sql).list();
	     //this.setSelfDefResData( this .getPageQueryData(list, start, limit));
	     this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("hmc")
	public int hmc(String gid){
		HBSession sess =HBUtil. getHBSession ();
		String sql="select B0101  from b01 where B0111='"+gid+"'";
		String jgname =(String) sess.createSQLQuery(sql).uniqueResult();
		this.getExecuteSG().addExecuteCode("dogrid('"+jgname+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 领导班子成员结构要素分布名册
	 * */
	@PageEvent("jgysmc")
	public int jgysmc(String gid){
		HBSession sess =HBUtil. getHBSession ();
		String sql="select category,project , quantity from natural_structure where  unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		if(list != null && list.size() > 0){
			String nl ="";
			String xl ="";
			String zy ="";
			String xb = "";
			String dp = "";
			String mz = "";
			String ly = "";
			String zc = "";
			String dy = "";
			String nel = "";//能力结构
			String jy = "";
			String jl = "";
			String rqz = "";
			String parall="";
			for (int i = 0; i < list.size(); i++) {
				String parem="";
				Object[] objects = list.get(i);
				if("nl".equals(objects[0])){
					for (Object object : objects) {
						nl+=object+",";
					}
					nl=nl.substring(0,nl.length()-1);
					nl=nl+"$";
				}else if("xl".equals(objects[0])){
					String sqlxl="select code_name from code_value where code_type='ZB64' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sqlxl).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						xl+=object+",";
					}
					xl=xl.substring(0,xl.length()-1);
					xl=xl+"$";
				}else if("zy".equals(objects[0])){
					String sql1="select code_name from code_value where code_type='GB8561' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						zy+=object+",";
					}
					zy=zy.substring(0,zy.length()-1);
					zy=zy+"$";
				}else if("xb".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='GB2261' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						xb+=object+",";
					}
					xb=xb.substring(0,xb.length()-1);
					xb=xb+"$";
				}else if("dp".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='GB4762' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						dp+=object+",";
					}
					dp=dp.substring(0,dp.length()-1);
					dp=dp+"$";
				}else if("mz".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='GB3304' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						mz+=object+",";
					}
					mz=mz.substring(0,mz.length()-1);
					mz=mz+"$";
				}else if("ly".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='LYJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						ly+=object+",";
					}
					ly=ly.substring(0,ly.length()-1);
					ly=ly+"$";
				}else if("zc".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='ZCJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						zc+=object+",";
					}
					zc=zc.substring(0,zc.length()-1);
					zc=zc+"$";
				}else if("dy".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='DYJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						dy+=object+",";
					}
					dy=dy.substring(0,dy.length()-1);
					dy=dy+"$";
				}else if("nel".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='NLJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						nel+=object+",";
					}
					nel=nel.substring(0,nel.length()-1);
					nel=nel+"$";
				}else if("jy".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='JYJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						jy+=object+",";
					}
					jy=jy.substring(0,jy.length()-1);
					jy=jy+"$";
				}else if("jl".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='JLJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						jl+=object+",";
					}
					jl=jl.substring(0,jl.length()-1);
					jl=jl+"$";
				}else if("rqz".equals(objects[0])){//性别
					String sql1="select code_name from code_value where code_type='RQZJG' and code_value='"+objects[1]+"'";
					String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					objects[1]=a;
					for (Object object : objects) {
						
						rqz+=object+",";
					}
					rqz=rqz.substring(0,rqz.length()-1);
					rqz=rqz+"$";
				}
			}
			String ryname = "select distinct a01.a0000,a01.a0101 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"'";
			List<Object[]> list2 = sess.createSQLQuery(ryname).list();
			String name ="";
			if(list2 != null && list2.size() > 0){
			for (int i = 0; i < list2.size(); i++) {
				String name1="";
				Object[]  obj = list2.get(i);
				//String parem = "";
				/*for (Object object : obj) {
					name+=object+",";
				}*/
				String nl1="";
				String xl1="";
				String zy1 ="";
				String xb1 = "";
				String dp1 = "";
				String mz1 = "";
				String ly1 = "";
				String zc1 = "";
				String dy1 = "";
				String nel1 = "";//能力结构
				String jy1 = "";
				String jl1 = "";
				String rqz1 = "";
				String pson="";
				String sqlx="select category, quantity,project,one_ticket_veto  from natural_structure where  unit='"+gid+"'";
				List<Object[]> listx = sess.createSQLQuery(sqlx).list();
				for (int j = 0; j < list.size(); j++) {
					Object[] objects = list.get(j);
				if("nl".equals(objects[0])){//年龄
					for (Object object : objects) {
						String count="";
						if("51-55岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  51 and 55 and aa.a0000='"+obj[0]+"'";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							 count = cou.toString();
							 if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}else if("35岁及以下".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111<=35 and aa.a0000='"+obj[0]+"' ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}else if("36-40岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  36 and 40 and aa.a0000='"+obj[0]+"'";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}else if("41-45岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  41 and 45 and aa.a0000='"+obj[0]+"' ";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}else if("46-50岁".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111 BETWEEN  46 and 50 and aa.a0000='"+obj[0]+"'";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}else if("56岁及以下".equals(object)){
							String sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0111<=56 and aa.a0000='"+obj[0]+"'";	
							BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
							count = cou.toString();
							if(!"0".equals(count)){
								 count="Y";
							 }else{
								 count="N";
							 }
						}
						if(!"".equals(count)){
							nl1+=count+",";
						}
						
					}
					//nl=nl.substring(0,nl.length()-1);
				}else if("xl".equals(objects[0])){
					//学历
					//String sqlxl="(select code_value from code_value where code_type='ZB64' and code_name='"+objects[1]+"')";
					//String a=(String) sess.createSQLQuery(sqlxl).uniqueResult();
					//objects[1]=a;
						String count1="";
						String sql1="select count(1) from (select distinct a01.a0000,a01.qrzxl,a01.zzxl from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.qrzxl='"+objects[1]+"' or zzxl='"+objects[1]+"' and aa.a0000='"+obj[0]+"'";
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count1 = cou.toString();
						if(!"0".equals(count1)){
							 count1="Y";
						 }else{
							 count1="N";
						 }
						if(!"".equals(count1)){
							xl1+=count1+",";
						}
				}else if("zy".equals(objects[0])){//专业
					
					//String sql1="(select code_value from code_value where code_type='GB8561' and code_name='"+objects[1]+"')";
					//String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					//objects[1]=a;
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0196 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0196=(select code_value from code_value where code_type='GB8561' and code_name='"+objects[1]+"') and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						zy1+=count+",";
					}
				}else if("xb".equals(objects[0])){//性别
					//String sql1="select code_name from code_value where code_type='GB2261' and code_value='"+objects[1]+"'";
					//String sql1="select code_value from code_value where code_type='GB2261' and code_name='"+objects[1]+"'";
					//String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					//objects[1]=a;
					//for (Object object : objects) {
						String count="";
						String sql2="";
						sql2="select count(1) from (select distinct a01.a0000,a01.a0104 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0104=(select code_value from code_value where code_type='GB2261' and code_name='"+objects[1]+"') and aa.a0000='"+obj[0]+"'";
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
						count = cou.toString();
						if(!"0".equals(count)){
							 count="Y";
						 }else{
							 count="N";
						 }
						if(!"".equals(count)){
							xb1+=count+",";
						}
					//}
				}else if("dp".equals(objects[0])){//党派GB4762
					//String sql1="(select code_value from code_value where code_type='GB4762' and code_name='"+objects[1]+"')";
					//String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					//objects[1]=a;
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0141 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0141=(select code_value from code_value where code_type='GB4762' and code_name='"+objects[1]+"') and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
				    count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						dp1+=count+",";
					}
					
				}else if("mz".equals(objects[0])){//民族GB3304
					//String sql1="(select code_value from code_value where code_type='GB3304' and code_name='"+objects[1]+"')";
					//String a=(String) sess.createSQLQuery(sql1).uniqueResult();
					//objects[1]=a;
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,a01.a0117 from a01 inner join a02 on a01.a0000=a02.a0000 and a02.a0201b='"+gid+"') aa where aa.a0117=(select code_value from code_value where code_type='GB3304' and code_name='"+objects[1]+"') and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					 count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						dp1+=count+",";
					}
					
				}else if("ly".equals(objects[0])){//来源
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.source from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.source='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						ly1+=count+",";
					}
				}else if("zc".equals(objects[0])){//专长
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.expertise from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.expertise='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						zc1+=count+",";
					}
				}else if("dy".equals(objects[0])){//专长
					String count = "";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.region from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.region='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						dy1+=count+",";
					}
				}else if("nel".equals(objects[0])){//专长
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.ability from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.ability='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						nel1+=count+",";
					}
				}else if("jy".equals(objects[0])){//专长
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.experience from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.experience='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						jy1+=count+",";
					}
				}else if("jl".equals(objects[0])){//专长
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.undergo from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.undergo='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						jl1+=count+",";
					}
				}else if("rqz".equals(objects[0])){//专长
					String count="";
					String sql2="select count(1) from (select distinct a01.a0000,modelconfig.tenuresystem from a01 inner join a02 on a01.a0000=a02.a0000 left join MODELCONFIG on a01.a0000=MODELCONFIG.id and a02.a0201b='"+gid+"') aa where  aa.tenuresystem='"+objects[2]+"' and aa.a0000='"+obj[0]+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
					count = cou.toString();
					if(!"0".equals(count)){
						 count="Y";
					 }else{
						 count="N";
					 }
					if(!"".equals(count)){
						rqz1+=count+",";
					}
				}
				
				}
				
				pson=nl1+xl1+zy1+xb1+dp1+mz1+ly1+zc1+dy1+nel1+jy1+jl1+rqz1;
				pson=pson.substring(0,pson.length()-1);
				name+=obj[1]+","+pson+"$";
			}
			
			name= name.substring(0,name.length()-1);
			if(!"".equals(nl)){
				nl=nl.substring(0,nl.length()-1);
			}
			if(!"".equals(xl)){
				xl=xl.substring(0,xl.length()-1);
			}
			if(!"".equals(zy)){
				zy=zy.substring(0,zy.length()-1);
			}
			if(!"".equals(xb)){
				xb=xb.substring(0,xb.length()-1);
			}
			if(!"".equals(dp)){
				dp=dp.substring(0,dp.length()-1);
			}
			if(!"".equals(mz)){
				mz=mz.substring(0,mz.length()-1);
			}
			if(!"".equals(ly)){
				ly=ly.substring(0,ly.length()-1);
			}
			if(!"".equals(ly)){
				ly=ly.substring(0,ly.length()-1);
			}
			if(!"".equals(zc)){
				zc=zc.substring(0,zc.length()-1);
			}
			if(!"".equals(dy)){
				dy=dy.substring(0,dy.length()-1);
			}
			if(!"".equals(nel)){
				nel=nel.substring(0,nel.length()-1);
			}
			if(!"".equals(jy)){
				jy=jy.substring(0,jy.length()-1);
			}
			if(!"".equals(jl)){
				jl=jl.substring(0,jl.length()-1);
			}
			if(!"".equals(rqz)){
				rqz=rqz.substring(0,rqz.length()-1);
			}
			
			int cont1=0;
			int cont2=0;
			int cont3=0;
			int cont4=0;
			int cont5=0;
			int cont6=0;
			int cont7=0;
			int cont8=0;
			int cont9=0;
			int cont10=0;
			int cont11=0;
			int cont12=0;
			int cont13=0;
			
			if(nl.length()!=0){
				 cont1=nl.split("\\$").length;
			}else{
				 cont1=0;
			}
			if(xl.length()!=0){
				 cont2=xl.split("\\$").length;
			}else{
				 cont2=0;
			}
			if(zy.length()!=0){
				 cont3=zy.split("\\$").length;
			}else{
				 cont3=0;
			}
			if(xb.length()!=0){
				 cont4=xb.split("\\$").length;
			}else{
				 cont4=0;
			}
			if(dp.length()!=0){
				 cont5=dp.split("\\$").length;
			}else{
				 cont5=0;
			}
			if(mz.length()!=0){
				 cont6=mz.split("\\$").length;
			}else{
				 cont6=0;
			}
			if(ly.length()!=0){
				 cont7=ly.split("\\$").length;
			}else{
				 cont7=0;
			}
			if(zc.length()!=0){
				 cont8=zc.split("\\$").length;
			}else{
				 cont8=0;
			}
			if(dy.length()!=0){
				 cont9=dy.split("\\$").length;
			}else{
				 cont9=0;
			}
			if(nel.length()!=0){
				 cont10=nel.split("\\$").length;
			}else{
				 cont10=0;
			}
			if(jy.length()!=0){
				 cont11=jy.split("\\$").length;
			}else{
				 cont11=0;
			}
			if(jl.length()!=0){
				 cont12=jl.split("\\$").length;
			}else{
				 cont12=0;
			}
			if(rqz.length()!=0){
				 cont13=rqz.split("\\$").length;
			}else{
				 cont13=0;
			}
			
			//int cont=nl.split("\\$").length+xl.split("\\$").length+zy.split("\\$").length+xb.split("\\$").length/*+dp.split("\\$").length+mz.split("\\$").length+ly.split("\\$").length*/;
			int cont=cont1+cont2+cont3+cont4+cont5+cont6+cont7+cont8+cont9+cont10+cont11+cont12+cont13;
			//String cont = contx+"";
			//this.getPageElement("ryname").setValue(name);
			//this.getPageElement("count").setValue(cont);
			if(!"".equals(nl)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+nl+"','a');");	
			}
			if(!"".equals(xl)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+xl+"','a');");	
			}	
			if(!"".equals(zy)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+zy+"','a');");	
			}
			if(!"".equals(xb)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+xb+"','a');");	
			}
			if(!"".equals(dp)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+dp+"','a');");	
			}
			if(!"".equals(mz)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+mz+"','a');");
			}	
			if(!"".equals(ly)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+ly+"','a');");
			}	
			if(!"".equals(zc)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+zc+"','a');");
			}	
			if(!"".equals(dy)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+dy+"','a');");	
			}	
			if(!"".equals(nel)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+nel+"','a');");	
			}	
			if(!"".equals(jy)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+jy+"','a');");	
			}	
			if(!"".equals(jl)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+jl+"','a');");
			}
			if(!"".equals(rqz)){
				this.getExecuteSG().addExecuteCode("zrjg1('"+rqz+"','a');");	
			}	
			this.getExecuteSG().addExecuteCode("rynn('"+name+"','"+cont+"');");
				
			}else{
				/*for (int i = 0; i < list2.size(); i++) {
					Object[]  obj = list2.get(i);
					//String parem = "";
					for (Object object : obj) {
						name+=object+",";
					}
					name+=obj[1]+",";
				}
				
				name= name.substring(0,name.length()-1);*/
				if(!"".equals(nl)){
					nl=nl.substring(0,nl.length()-1);
				}
				if(!"".equals(xl)){
					xl=xl.substring(0,xl.length()-1);
				}
				if(!"".equals(zy)){
					zy=zy.substring(0,zy.length()-1);
				}
				if(!"".equals(xb)){
					xb=xb.substring(0,xb.length()-1);
				}
				if(!"".equals(dp)){
					dp=dp.substring(0,dp.length()-1);
				}
				if(!"".equals(mz)){
					mz=mz.substring(0,mz.length()-1);
				}
				if(!"".equals(ly)){
					ly=ly.substring(0,ly.length()-1);
				}
				if(!"".equals(zc)){
					zc=zc.substring(0,zc.length()-1);
				}
				if(!"".equals(dy)){
					dy=dy.substring(0,dy.length()-1);
				}
				if(!"".equals(nel)){
					nel=nel.substring(0,nel.length()-1);
				}
				if(!"".equals(jy)){
					jy=jy.substring(0,jy.length()-1);
				}
				if(!"".equals(jl)){
					jl=jl.substring(0,jl.length()-1);
				}
				if(!"".equals(rqz)){
					rqz=rqz.substring(0,rqz.length()-1);
				}
				this.getExecuteSG().addExecuteCode("zrjg1('"+nl+"','a','0');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+xl+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+zy+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+xb+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+dp+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+mz+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+ly+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+zc+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+dy+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+nel+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+jy+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+jl+"','a');");	
				this.getExecuteSG().addExecuteCode("zrjg1('"+rqz+"','a');");	
				
			}
		}else{
			this.getExecuteSG().addExecuteCode("zrjgk();");	
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 整体状况
	 * */
	/*@PageEvent("ztzk")
	public int ztzk(String gs){
		HBSession sess =HBUtil. getHBSession ();
		String uid = UUID.randomUUID().toString();
		String[] gs1 = gs.split(",");
		String unit = gs1[0];//单位
		String statustype = gs1[1];//单位状况类型
		String sql1 = "select * from duty_num where unit='"+unit+"'";
		List<Object[]> list = sess.createSQLQuery(sql1).list();
		if(list.size()>=1&&list!=null){
			String sql = "select id from wholestatus where unit='"+unit+"'";
			String id = (String) sess.createSQLQuery(sql).uniqueResult();
				WholeStatus ws = new WholeStatus();
				if(id==null||"".equals(id)){
					ws.setId(uid);
				}else{
					ws.setId(id);
				}
				      
				ws.setUnit(unit);
				ws.setStatustype(statustype);
				sess.saveOrUpdate(ws);
			
			sess.flush(); 
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
	}*/
	

}
