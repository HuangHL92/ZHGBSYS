package com.insigma.siis.local.pagemodel.templateconf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.NaturalStructure;
import com.insigma.siis.local.business.entity.WholeStatus;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.lbs.cp.util.SysManagerUtil;

public class TemplateInferPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������������ͼ������
	 * @param gid
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("selectduty")
	@Transaction
	public	int  selectduty(String gid) throws  RadowException, AppException{
		HBSession sess =HBUtil. getHBSession ();
		
		//���ж��Ƿ��� ������Ա
		String reset = this.getPageElement("reset").getValue();
		if(reset!=null&&"reset".equals(reset)){
			sess.createSQLQuery("delete from CODE_A0215A_C c where c.userid = '"+SysManagerUtils.getUserId()+"' and c.b0111 = '"+gid+"'").executeUpdate();
			this.getPageElement("reset").setValue("");
		}
		
		
		//����id,��������,Ӧ��, ʵ�� ,(ȱ�䡢����)      ��Աid,��Ա����,��Ա������λ��ְ��
		String b0111 = "";
		String b0101 = "";
		int yp = 0;//Ӧ��
		int sp = 0;//ʵ��
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String , List<String>>();
		
		//�ж��м���Ƿ�������ݣ���ȡ��ǰ�û���CODE_A0215A_C�еĵ�ǰ�������ݣ������У���ֱ��ȡ��������ݣ���û�У��Ȳ������ݣ���ȡ
		String codeSql = "select id from CODE_A0215A_C c where c.userid = '"+SysManagerUtils.getUserId()+"' and c.b0111 = '"+gid+"'";
		List<String> list = sess.createSQLQuery(codeSql).list();
		boolean sign = false;
		if(list==null||list.size()==0){
			sign = true;
		}
		
		String jg = "";
		//ʵ��
		String sql = "select f.b0111,f.b0101,f.gwnum,f.gwcode,f.gwname from Jggwconf f where b0111='" + gid + "' order by gwcode";
		List<Object[]> obj = sess.createSQLQuery(sql).list();
		if(obj!=null&&obj.size()>0){
			for(Object[] objs : obj){
				b0111 = ""+objs[0];
				b0101 = ""+objs[1];
				Object gwnum = objs[2];
				if(gwnum==null||"null".equals(gwnum)||"".equals(gwnum)){
					gwnum = "0";
				}
				yp = yp + Integer.parseInt(""+gwnum);
				/*Object countnum = objs[3];
				if(countnum==null||"null".equals(countnum)||"".equals(countnum)){
					countnum = "0";
				}
				sp = sp + Integer.parseInt(""+countnum);*/
				
				Object gwcode = objs[3];
				Object gwname = objs[4];
				
				if(sign){
					String ssql = "insert into code_a0215a_c select a01.a0000, a01.a0101, a01.a0192a, a02.a0201b, a02.A0215A_c, sys_guid(), '"+SysManagerUtils.getUserId()+"' from a01, a02 "
							+ "where a01.a0000 = a02.a0000 and a0201b = '"+gid+"' and A0215A_c = '"+gwcode+"'";
					sess.createSQLQuery(ssql).executeUpdate();
				}
				
				String sssql = "select a01.a0000, a01.a0101, a01.a0192a from code_a0215a_c a01 where a01.userid = '"+SysManagerUtils.getUserId()+"' and a01.b0111 = '"+gid+"' and a01.a0215a_c = '"+gwcode+"'";
				
				List<Object[]> persons = sess.createSQLQuery(sssql).list();
				
				List<String> strs = new ArrayList<String>();
				if(persons!=null&&persons.size()>0){
					for(Object[] person : persons){
						sp++;
						String a0000 = ""+person[0];
						String a0101 = ""+person[1];
						String a0192a = ""+person[2];
						String str = a0000 + "@" + a0101 + "@" + a0192a;
						strs.add(str);
					}
				}
				map.put(gwcode+"@"+gwname+"@"+gwnum, strs);
			}
			jg = b0111 + "@" + b0101 + "@" + yp + "@" + sp;
		}
		/*String personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
				+ "where a0255 = '1' and a0201b = '"+gid+"' and A0215A_c is not null) a "
				+ "where f.gwcode = a.A0215A_c and b0111 = '"+gid+"'";//ʵ����ԱSQL
*/		
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
	    this.getExecuteSG().addExecuteCode("znzsvalue('"+jg+"','"+jsonObject+"');");
       //ְ��end================================================================================================================  
	   
	   
	   //��ѯ�û����µ���Ա�б�===================================================================================================
	   String 	sqlperson = "select a01.a0000,a01.a0101,a01.a0192A  from A01 a01 where 1 = 1 and fkly is null and a01.status != '4' "
	   		+ "and a0163 like '1%' and a01.a0000 in (select a02.a0000 from a02 where a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+SysManagerUtils.getUserId()+"') and a0281 = 'true' and a02.a0201b = '"+gid+"')";
	   List<Object[]> listp = sess.createSQLQuery(sqlperson).list();
	   if(listp != null && listp.size() > 0){
			String pall="";
			for (int i = 0; i < listp.size(); i++) {
				String parem="";
				Object[] objects = listp.get(i);
		        for (Object object : objects) {
					parem+=object+",";
				}
		        parem= parem.substring(0,parem.length()-1);
		        pall+=parem+"&"; 
			}
			pall=pall.substring(0,pall.length()-1);
			this.getExecuteSG().addExecuteCode("ptable('"+pall+"');");
		}else{
			this.getExecuteSG().addExecuteCode("ptable('null');");
		}
		
	    //TroupeDetailPageModel td = new TroupeDetailPageModel();
	    this.setNextEventName("dataTb");
	    
	    return EventRtnType.NORMAL_SUCCESS;
	     
	}
	
	@PageEvent("selcctPerson")
	public int selcctPerson(String code) throws RadowException{
		String b0111 = this.getPageElement("gid").getValue();
		HBSession sess =HBUtil. getHBSession ();
		String sql = "select t.ageup, t.agedown, t.a0104, t.a0801b,  t.a0827, t.a0141, t.know, t.a0221, t.a0221n, t.a0148,  t.a0148n "
				+ " from JGGWCONF j,KNOW_FIELD t where j.jggwconfid = t.jggwconfid and j.b0111 = '"+b0111+"' and j.gwcode = '"+code+"'";
		List<Object[]> objs = sess.createSQLQuery(sql).list();
		String querySql = "select a01.a0000,a01.a0101,a01.a0192A  from A01 a01 where 1=2";
		if(objs!=null&&objs.size()>0){
			querySql = "select a01.a0000,a01.a0101,a01.a0192A  from A01 a01 "
					+ "where 1 = 1 "
					+ "and fkly is null "
					+ "and a01.status != '4' "
			   		+ "and a0163 like '1%' "
			   		+ "and a01.a0000 in (select a02.a0000 from a02 where a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+SysManagerUtils.getUserId()+"') and a0281 = 'true') ";
			Object[] obj = objs.get(0);//��������ֻ��Ψһһ��
			String ageup = ""+obj[0];
			if(ageup!=null&&!"".equals(ageup)&&!"null".equals(ageup)){
				querySql = querySql + " and GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"') >= "+ageup+" ";
			}
			
			String agedown = ""+obj[1];
			if(agedown!=null&&!"".equals(agedown)&&!"null".equals(agedown)){
				querySql = querySql + " and GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"') <= "+agedown+" ";
			}
			
			String a0104 = ""+obj[2];
			if(a0104!=null&&!"".equals(a0104)&&!"null".equals(a0104)){
				querySql = querySql + " and a0104 = "+a0104+" ";
			}
			
			String a0801b = ""+obj[3];
			if(a0801b!=null&&!"".equals(a0801b)&&!"null".equals(a0801b)){
				querySql = querySql + "and a01.a0000 in (select a08.a0000 from a08 where a08.a0801b = '"+a0801b+"') ";
			}
			
			String a0827 = ""+obj[4];
			if(a0827!=null&&!"".equals(a0827)&&!"null".equals(a0827)){
				querySql = querySql + "and a01.a0000 in (select a08.a0000 from a08 where a08.a0827 = '"+a0827+"') ";
			}
			
			String a0141 = ""+obj[5];
			if(a0141!=null&&!"".equals(a0141)&&!"null".equals(a0141)){
				querySql = querySql + " and a0141 = "+a0141+" ";
			}
			
			String know = ""+obj[6];
			if(know!=null&&!"".equals(know)&&!"null".equals(know)){
				querySql = querySql + "and a01.a0000 in (select a0000 from a0194_tag where a0194 = '"+know+"') ";
			}
			
			String a0221 = ""+obj[7];
			if(a0221!=null&&!"".equals(a0221)&&!"null".equals(a0221)){
				querySql = querySql + " and a0221 = "+a0221+" ";
				
				String a0221n = ""+obj[8];//����ְ���ε�����£����ж�����Ƿ���д
				if(a0221n!=null&&!"".equals(a0221n)&&!"null".equals(a0221n)){
					querySql = querySql + " and GET_BIRTHDAY( a01.a0288,'"+DateUtil.getcurdate()+"') >= "+a0221n+" ";
				}
			}
			
			String a0148 = ""+obj[9];
			if(a0148!=null&&!"".equals(a0148)&&!"null".equals(a0148)){
				querySql = querySql + " and a0192e = "+a0148+" ";
				
				String a0148n = ""+obj[10];//����ְ���ε�����£����ж�����Ƿ���д
				if(a0148n!=null&&!"".equals(a0148n)&&!"null".equals(a0148n)){
					querySql = querySql + " and GET_BIRTHDAY( a01.a0192c,'"+DateUtil.getcurdate()+"') >= "+a0148n+" ";
				}
			}
	   }
	   List<Object[]> listp = sess.createSQLQuery(querySql).list();
	   if(listp != null && listp.size() > 0){
			String pall="";
			for (int i = 0; i < listp.size(); i++) {
				String parem="";
				Object[] objects = listp.get(i);
		        for (Object object : objects) {
					parem+=object+",";
				}
		        parem= parem.substring(0,parem.length()-1);
		        pall+=parem+"&"; 
			}
			pall=pall.substring(0,pall.length()-1);
			this.getExecuteSG().addExecuteCode("ptable('"+pall+"');");
		}else{
			this.getExecuteSG().addExecuteCode("ptable('null');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
    public void showView(String b0111) throws RadowException{
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String, String> scoreMap = null;
		Map<String, String> colMap = null;
		HBSession sess = HBUtil.getHBSession();
		
		//���� ��ֵ����
		List<Object[]> scores = sess.createSQLQuery("select s.*,t.redf,t.greenf from wholestatus s left join troupe_score t on s.unit = t.b0111 where s.unit = '"+b0111+"'").list();
		if(scores!=null&&scores.size()>0){
			for(Object[] objs : scores){
				String unit = ""+objs[0];
				String statustype = ""+objs[1];
				String bzf = ""+objs[2];
				String xbf = ""+objs[3];
				String dpf = ""+objs[4];
				String mzf = ""+objs[5];
				String nlf = ""+objs[6];
				String xlf = ""+objs[7];
				String zyf = ""+objs[8];
				String dyf = ""+objs[9];
				String knowf = ""+objs[10];
				String jlf = ""+objs[11];
				String score = ""+objs[12];
				String redf = ""+objs[13];
				String greenf = ""+objs[14];
				String max = getMax(bzf,xbf,dpf,mzf,nlf,xlf,zyf,dyf,knowf,jlf);
				
				scoreMap = new HashMap<String, String>();
				scoreMap.put("statustype", isStrNULL(statustype));
				scoreMap.put("bzf", isStrNULL(isFUYI(bzf)));
				scoreMap.put("xbf", isStrNULL(isFUYI(xbf)));
				scoreMap.put("dpf", isStrNULL(isFUYI(dpf)));
				scoreMap.put("mzf", isStrNULL(isFUYI(mzf)));
				scoreMap.put("nlf", isStrNULL(isFUYI(nlf)));
				scoreMap.put("xlf", isStrNULL(isFUYI(xlf)));
				scoreMap.put("zyf", isStrNULL(isFUYI(zyf)));
				scoreMap.put("dyf", isStrNULL(isFUYI(dyf)));
				scoreMap.put("knowf", isStrNULL(isFUYI(knowf)));
				scoreMap.put("jlf", isStrNULL(isFUYI(jlf)));
				scoreMap.put("score", isStrNULL(isFUYI(score)));
				scoreMap.put("redf", isStrNULL(isFUYI(redf)));
				scoreMap.put("greenf", isStrNULL(isFUYI(greenf)));
				scoreMap.put("max", max);
			}
		}
		JSONObject jsonObjectScore = JSONObject.fromObject(scoreMap);
		
		this.getExecuteSG().addExecuteCode("appendScore('"+jsonObjectScore+"')");
		
		//���� ��Ȼ�ṹ
		List<Object[]> list = sess.createSQLQuery("select t.category, t.project, t.quantity, t.one_ticket_veto,t.count, t.id  from NATURAL_STRUCTURE t where t.unit = '"+b0111+"' order by t.category").list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = list.get(i);
				String category = ""+objs[0];
				String project = ""+objs[1];
				String quantity = ""+objs[2];
				String one_ticket_veto = ""+objs[3];
				String count = ""+objs[4];
				String id = ""+objs[5];
				
				colMap = new HashMap<String, String>();
				colMap.put("category", isStrNULL(changeCategory(category)));
				colMap.put("project", isStrNULL(changeProject(category,project)));
				colMap.put("quantity", isStrNULL(quantity.replaceAll("\\&", "")));
				colMap.put("one_ticket_veto", isStrNULL(changeOne_ticket_veto(one_ticket_veto)));
				colMap.put("count", isStrNULL(count));
				colMap.put("id", isStrNULL(id));
				
				map.put("tr"+i, colMap);
				
			}
			JSONObject jsonObject = JSONObject.fromObject(map);
			
			this.getExecuteSG().addExecuteCode("appendTable('"+list.size()+"','"+jsonObject+"')");
		}else{
			this.getExecuteSG().addExecuteCode("appendNullTable()");
		}
	}
	
	private String getMax(String bzf, String xbf, String dpf, String mzf,
			String nlf, String xlf, String zyf, String dyf, String knowf,
			String jlf) {
		//��ʼ������
        List<Integer> nums = new ArrayList<Integer>();
        nums.add(Integer.parseInt(isStrNULL(bzf)));
        nums.add(Integer.parseInt(isStrNULL(xbf)));
        nums.add(Integer.parseInt(isStrNULL(dpf)));
        nums.add(Integer.parseInt(isStrNULL(mzf)));
        nums.add(Integer.parseInt(isStrNULL(nlf)));
        nums.add(Integer.parseInt(isStrNULL(xlf)));
        nums.add(Integer.parseInt(isStrNULL(zyf)));
        nums.add(Integer.parseInt(isStrNULL(dyf)));
        nums.add(Integer.parseInt(isStrNULL(knowf)));
        nums.add(Integer.parseInt(isStrNULL(jlf)));
        //�������ֵMax
        int Max = Collections.max(nums);
		return ""+Max;
	}
	private String isFUYI(String val){
		if("-1".equals(val)){
			val = "0";
		}
		return val;
	}
	
	private String changeOne_ticket_veto(String one_ticket_veto){
		if("1".equals(one_ticket_veto)){
			one_ticket_veto = "��";
		}else if("0".equals(one_ticket_veto)){
			one_ticket_veto = "��";
		}
		return one_ticket_veto;
	}
	
	private String changeCategory(String category){
		if("banzi".equals(category)){
			category = "�����������";
		}else if("xb".equals(category)){
			category = "�Ա�";
		}else if("dp".equals(category)){
			category = "����";
		}else if("mz".equals(category)){
			category = "����";
		}else if("nl".equals(category)){
			category = "����";
		}else if("xl".equals(category)){
			category = "ѧ��";
		}else if("zy".equals(category)){
			category = "רҵ";
		}else if("dy".equals(category)){
			category = "����";
		}else if("sxly".equals(category)){
			category = "��Ϥ����";
		}else if("jl".equals(category)){
			category = "����";
		}
		return category;
	}
	
	private String changeProject(String category,String project){
		HBSession sess = HBUtil.getHBSession();
		String codeType = "";
		if("banzi".equals(category)){

		}else if("xb".equals(category)){
			codeType = "GB2261";
		}else if("dp".equals(category)){
			codeType = "GB4762";
		}else if("mz".equals(category)){
			codeType = "GB3304";
		}else if("nl".equals(category)){
			
		}else if("xl".equals(category)){
			codeType = "ZB64";
		}else if("zy".equals(category)){
			codeType = "GB16835";
		}else if("dy".equals(category)){
			
		}else if("sxly".equals(category)){
			codeType = "SXLY";
		}else if("jl".equals(category)){
			codeType = "TAGZB131";
		}
		
		if(!"".equals(codeType)){
			Object obj = sess.createSQLQuery("select c.code_name from code_value c where  c.code_type = '"+codeType+"' and c.code_status = '1' and c.code_value = '"+project+"'").uniqueResult();
			if(obj!=null&&!"".equals(obj)){
				project = ""+obj;
			}
		}
		return project;
	}
	
	private String isStrNULL(String str){
		if(str==null||"".equals(str)||"null".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	@PageEvent("deleteOne")
	@Transaction
	public int deleteOne(String value) throws RadowException{
		String[] val = value.split("\\$");
		String gid = val[0];
		String a0000 = val[1];
		String code = val[2];
		
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("delete from code_a0215a_c where userid = '"+SysManagerUtils.getUserId()+"'"
				+ " and a0000 = '"+a0000+"' and b0111 = '"+gid+"' and A0215A_C = '"+code+"'").executeUpdate();
		
		String sql = "select f.b0111,f.b0101,f.gwnum,f.gwcode,f.gwname from Jggwconf f where b0111='" + gid + "' order by gwcode";
		String b0111 = "";
		String b0101 = "";
		int yp = 0;//Ӧ��
		int sp = 0;//ʵ��
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String , List<String>>();
		
		String jg = "";
		List<Object[]> obj = sess.createSQLQuery(sql).list();
		if(obj!=null&&obj.size()>0){
			for(Object[] objs : obj){
				b0111 = ""+objs[0];
				b0101 = ""+objs[1];
				Object gwnum = objs[2];
				if(gwnum==null||"null".equals(gwnum)||"".equals(gwnum)){
					gwnum = "0";
				}
				yp = yp + Integer.parseInt(""+gwnum);
				/*Object countnum = objs[3];
				if(countnum==null||"null".equals(countnum)||"".equals(countnum)){
					countnum = "0";
				}
				sp = sp + Integer.parseInt(""+countnum);*/
				
				Object gwcode = objs[3];
				Object gwname = objs[4];
				
				String ssql = "select a01.a0000, a01.a0101, a01.a0192a from code_a0215a_c a01 where a01.userid = '"+SysManagerUtils.getUserId()+"' and a01.b0111 = '"+gid+"' and a01.a0215a_c = '"+gwcode+"'";
				
				List<Object[]> persons = sess.createSQLQuery(ssql).list();
				
				List<String> strs = new ArrayList<String>();
				if(persons!=null&&persons.size()>0){
					for(Object[] person : persons){
						sp++;
						String a0000n = ""+person[0];
						String a0101 = ""+person[1];
						String a0192a = ""+person[2];
						String str = a0000n + "@" + a0101 + "@" + a0192a;
						strs.add(str);
					}
				}
				map.put(gwcode+"@"+gwname+"@"+gwnum, strs);
			}
			jg = b0111 + "@" + b0101 + "@" + yp + "@" + sp;
		}
		/*String personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
				+ "where a0255 = '1' and a0201b = '"+gid+"' and A0215A_c is not null) a "
				+ "where f.gwcode = a.A0215A_c and b0111 = '"+gid+"'";//ʵ����ԱSQL
*/		
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
	    this.getExecuteSG().addExecuteCode("znzsvalue('"+jg+"','"+jsonObject+"');");
	    
	    //�쵼����ͼˢ��
	    this.getPageElement("a0215a_c_sign").setValue("1");//�Ƿ��ȡcode_a0215a_c���
	    this.setNextEventName("dataTb");
	    
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dataTb")
	@Transaction
	public int dataTb() throws RadowException{
		HBSession sess =HBUtil. getHBSession ();
		String gid = this.getPageElement("gid").getValue();
		//list  redһƱΪ��   red��ֵ����   yellow  green
		
		//�Ȳ�ѯtroupe_score�У�����Щ��������������   ���������������Բ�� ���� ��  δ�� �Ļ���
		List<Object[]> list = sess.createSQLQuery("select * from TROUPE_SCORE t where t.b0111 = '"+gid+"'").list();
		
		if(list!=null&&list.size()>0){
			Object[] objs = list.get(0);
			String b0111 = ""+objs[1];//����ID
			String bzf = ""+objs[2];
			String xbf = ""+objs[3];
			String dpf = ""+objs[4];
			String mzf = ""+objs[5];
			String nlf = ""+objs[6];
			String xlf = ""+objs[7];
			String zyf = ""+objs[8];
			String dyf = ""+objs[9];
			String knowf = ""+objs[10];
			String jlf = ""+objs[11];

			String remarks = ""+objs[12];

			String redf = ""+objs[13];
			String greenf = ""+objs[14];
			
			//�������ֱ�������Щ��(sql)
			String personSql = "";
			String sign = this.getPageElement("a0215a_c_sign").getValue();//�Ƿ��ȡcode_a0215a_c���
			if("1".equals(sign)){
				personSql = "select a01.a0000 from code_a0215a_c a01 where a01.userid = '"+SysManagerUtils.getUserId()+"' and a01.b0111 = '"+gid+"'";
			}else{
				personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
						+ "where a0255 = '1' and a0201b = '"+b0111+"' and A0215A_c is not null) a "
						+ "where f.gwcode = a.A0215A_c and b0111 = '"+b0111+"'";//ʵ����ԱSQL
			}
			this.getPageElement("a0215a_c_sign").setValue("");//��������Ϊ""
			
			// ����ǹ�������
			zrjg(b0111,sess,personSql,bzf,xbf,dpf,mzf,nlf,xlf,zyf,dyf,knowf,jlf,redf,greenf);
			// for 	���ݻ���ID ���� NATURAL_STRUCTURE����ѯ�����õ���Ȼ���� ,������֮ǰ���㷨����� ���� �������������ˣ�
					//�Ա� ʵ����Ա  ��   ��Ȼ����  ��������Ա���ж��Ƿ����õ� ����Ȼ���� �ķ�ֵ��ͬʱ���� һƱ�����
	
			//�����������з�ֵ��ӣ��Ա� ��λ troupe_score ��redf��greenf �����жϸû�����״̬
			
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public int zrjg(String gid,HBSession sess,String sql,String bzf,String xbf,String dpf,String mzf,
			String nlf,String xlf,String zyf,String dyf,String knowf,String jlf,String redf,String greenf) throws RadowException{
		String sql20="select t.category, t.quantity, t.project, t.one_ticket_veto, t.id  from natural_structure t where  t.unit='"+gid+"'";
		List<Object[]> list = sess.createSQLQuery(sql20).list();
		NaturalStructure ns = null;
		WholeStatus ws = (WholeStatus)sess.get(WholeStatus.class, gid);
		if(ws==null){
			ws = new WholeStatus();
		}
		ws.setUnit(gid);
		//�ȳ�ʼ���������������ö���治������ٱ�0
		ws.setBzf(bzf);ws.setXbf(xbf);ws.setDpf(dpf);ws.setMzf(mzf);ws.setNlf(nlf);
		ws.setXlf(xlf);ws.setZyf(zyf);ws.setDyf(dyf);ws.setKnowf(knowf);ws.setJlf(jlf);

		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Object[] objects = list.get(i);
				String category = ""+objects[0];
				String quantity = ""+objects[1];
				String project = ""+objects[2];
				String one_ticket_veto = ""+objects[3];
				String id = ""+objects[4];
				//	xb	        >=&1	  1	        1
				//	nl		    >=&1    56�꼰����	1
				//	xl		    >=&1	 21         0
				//	banzi		>=&2	����                1
				String count="";
				String sql1="";
				String fc = "";
				
				if("nl".equals(category)){//����
					if("51-55��".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"') a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  51 and 55 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  51 and 55 ";
					}else if("35�꼰����".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111<=35 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 <= 35 ";
					}else if("36-40��".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  36 and 40 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  36 and 40 ";
					}else if("41-45��".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  41 and 45 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  41 and 45 ";
					}else if("46-50��".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  46 and 50 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  46 and 50 ";
					}else if("56�꼰����".equals(project)){
						sql1="select count(1) from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111<=56 ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 <= 56 ";
					}/*else if("ƽ������".equals(object)){
						//[nl, >=&2, 35�꼰����, 0]
						 String pjnl = (String)objects[1];
						// String regEx = "[^0-9]";
						 Pattern pattern = Pattern.compile("\\d+");
						 Matcher matcher = pattern.matcher(pjnl);
						 
						 //String subpj = pjnl.substring(pjnl.length()-1);
						 //String subpj="";
						 ArrayList<String> arrayList = new ArrayList<String>();
						 while(matcher.find()){
							 arrayList.add(matcher.group());
						}
						sql1="select count(1) from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111='"+arrayList.get(0)+"' ";	
						BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
						count = cou.toString();
						fc = "select * from (select distinct a01.a0000,GET_BIRTHDAY( a01.a0107,'"+DateUtil.getcurdate()+"')a0111,a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111='"+arrayList.get(0)+"'";
					}*/
					
					String score = judgeScore(bzf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  bzf���ж��Ƿ����õ���ֵ
					ws.setNlf(score);
					
				}else if("xl".equals(category)){//ѧ��
					sql1="select count(distinct a08.a0000) from a08,("+sql+") bb where bb.a0000 = a08.a0000 and a08.a0801b <= '"+project+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 where a01.a0000 in("
							+ "select distinct a08.a0000 from a08,("+sql+") bb where bb.a0000 = a08.a0000 and a08.a0801b <= '"+project+"')";
					
					String score = judgeScore(xlf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setXlf(score);
				}else if("zy".equals(category)){//רҵ
					sql1="select count(distinct a08.a0000) from a08,("+sql+") bb where bb.a0000 = a08.a0000 and a08.a0827 = '"+project+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 where a01.a0000 in("
							+ "select distinct a08.a0000 from a08,("+sql+") bb where bb.a0000 = a08.a0000 and a08.a0827 = '"+project+"')";
					
					String score = judgeScore(zyf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setZyf(score);
					
				}else if("xb".equals(category)){//�Ա�
					sql1="select count(*) from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0104 = '"+project+"'";	
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					//fc = "select * from (select a01.a0000, a01.a0141 , a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000) aa where aa.a0111 BETWEEN  46 and 50 ";
					fc = "select  a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0104 = '"+project+"'";
				
					String score = judgeScore(xbf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setXbf(score);
				}else if("dp".equals(category)){//����
					sql1="select count(*) from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0141 = '"+project+"'";	
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select  a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0141 = '"+project+"'";
				
					String score = judgeScore(dpf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setDpf(score);
				}else if("mz".equals(category)){//����
					sql1="select count(*) from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0117 = '"+project+"'";	
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select  a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0117 = '"+project+"'";
				
					String score = judgeScore(mzf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setMzf(score);
				}else if("sxly".equals(category)){//��Ϥ����
					sql1="select count(distinct tag.a0000) from a0194_tag tag,("+sql+") bb where bb.a0000 = tag.a0000 and tag.a0194 = '"+project+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 where a01.a0000 in("
							+ "select distinct tag.a0000 from a0194_tag tag,("+sql+") bb where bb.a0000 = tag.a0000 and tag.a0194 = '"+project+"')";
				
					String score = judgeScore(knowf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setKnowf(score);
				}else if("dy".equals(category)){//����   ��������ɳ���һ�� ����
					sql1="select count(*) from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0115a = a01.a0114a";	
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select  a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000 and a01.a0115a = a01.a0114a";
				
					String score = judgeScore(dyf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setDyf(score);
				}else if("jl".equals(objects[0])){//����
					sql1="select count(distinct tag.a0000) from a0193_tag tag,("+sql+") bb where bb.a0000 = tag.a0000 and tag.a0193 = '"+project+"'";
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 where a01.a0000 in("
							+ "select distinct tag.a0000 from a0193_tag tag,("+sql+") bb where bb.a0000 = tag.a0000 and tag.a0193 = '"+project+"')";
				
					String score = judgeScore(jlf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setJlf(score);
				}else if("banzi".equals(objects[0])){//��������
					sql1="select count(*) from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000";	
					BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
					count = cou.toString();
					fc = "select  a01.a0000, a01.a0101,(CASE a01.a0104 WHEN '1' THEN '��' else 'Ů' end)a0104,(substr (a01.a0107,1,4)||'.'||substr (a01.a0107,-4,2))a0107, a01.a0114a,a01.a0111a,a01.a0192 from a01 inner join ("+sql+") cc on a01.a0000=cc.a0000";
				
					String score = judgeScore(bzf,quantity,count,one_ticket_veto);//����ֵΪ0  ����  ָ����ֵ���ж��Ƿ����õ���ֵ
					ws.setBzf(score);
				}
			
				ns = (NaturalStructure)sess.get(NaturalStructure.class, id);
				ns.setQuerysql(fc);//���淴���SQL
				ns.setCount(count);//������������������
				sess.saveOrUpdate(ns);
				sess.flush();
			}
			
			sess.saveOrUpdate(ws);
			sess.flush();
			
			//���ܷ�,��ɫ ����
			calculateResult(ws,sess,redf,greenf);
			
			showView(gid);
		}else{
			//this.getExecuteSG().addExecuteCode("zrjgk();");	
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void calculateResult(WholeStatus ws, HBSession sess, String redf, String greenf) {
		//ȡ��  ws ÿһ��ķ�ֵ,�����з�ֵ��ӣ��� redf��greenf �Ƚϡ�
		//                    ����ж��Ƿ�Ϊ -1 һƱ���,��һ�� -1 ������ 0  
		if(ws!=null){
			String bzf = ws.getBzf();
			String xbf = ws.getXbf();
			String dpf = ws.getDpf();
			String mzf = ws.getMzf();
			String nlf = ws.getNlf();
			String xlf = ws.getXlf();
			String zyf = ws.getZyf();
			String dyf = ws.getDyf();
			String knowf = ws.getKnowf();
			String jlf = ws.getJlf();
			
			int score = Integer.parseInt(isFUYI(bzf)) + Integer.parseInt(isFUYI(xbf)) + Integer.parseInt(isFUYI(dpf)) + Integer.parseInt(isFUYI(mzf)) + Integer.parseInt(isFUYI(nlf))
					  + Integer.parseInt(isFUYI(xlf)) + Integer.parseInt(isFUYI(zyf)) + Integer.parseInt(isFUYI(dyf)) + Integer.parseInt(isFUYI(knowf)) + Integer.parseInt(isFUYI(jlf));
			ws.setScore(""+score);
			
			int red = Integer.parseInt(redf);
			int green = Integer.parseInt(greenf);
			
			if(score<red){
				ws.setStatustype("1");
			}
			if(red<=score&&score<green){
				ws.setStatustype("2");
			}
			if(score>=green){
				ws.setStatustype("3");
			}
			
			//����ж��Ƿ�Ϊ -1 һƱ���,��һ�� -1 ������ 0  
			if("-1".equals(bzf)){
				ws.setStatustype("0");
			}
			if("-1".equals(xbf)){
				ws.setStatustype("0");
			}
			if("-1".equals(xlf)){
				ws.setStatustype("0");
			}
			if("-1".equals(dpf)){
				ws.setStatustype("0");
			}
			if("-1".equals(mzf)){
				ws.setStatustype("0");
			}
			if("-1".equals(nlf)){
				ws.setStatustype("0");
			}
			if("-1".equals(zyf)){
				ws.setStatustype("0");
			}
			if("-1".equals(dyf)){
				ws.setStatustype("0");
			}
			if("-1".equals(knowf)){
				ws.setStatustype("0");
			}
			if("-1".equals(jlf)){
				ws.setStatustype("0");
			}
			
			sess.saveOrUpdate(ws);
			sess.flush();
		}
	}

	private String judgeScore(String fs, String quantity, String count, String one_ticket_veto) {
		int score = Integer.parseInt(fs);//��ֵ
		int cou = Integer.parseInt(count);//Ŀǰ����
		
		String[] vals = quantity.split("\\&");
		String fh = vals[0];
		String num = vals[1];
		int n = Integer.parseInt(num);//Ҫ������
		
		if(">=".equals(fh)){
			if(cou>=n){
				
			}else{
				score = 0;
				if("0".equals(one_ticket_veto)){//�����һƱ���   0  ��ʾ��
					score = -1;
				}
			}
		}
		if("<=".equals(fh)){
			if(cou<=n){
				
			}else{
				score = 0;
				if("0".equals(one_ticket_veto)){//�����һƱ���   0  ��ʾ��
					score = -1;
				}
			}
		}
		// TODO Auto-generated method stub
		return ""+score;
	}
}

