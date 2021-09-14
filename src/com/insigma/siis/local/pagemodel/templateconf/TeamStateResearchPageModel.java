package com.insigma.siis.local.pagemodel.templateconf;

import java.math.BigDecimal;
import java.util.List;

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

public class TeamStateResearchPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX(){
		/*String sql = "select count(1)  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='1'";
		sql1 = "select count(1)  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='2'";
		String sql2 = "select count(1)  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='3'";
		String sql3 = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='1'";
		String sql4 = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='2'";
		String sql5 = "select b0111, b0101  from b01 inner join wholestatus on b01.b0111=wholestatus.unit where wholestatus.statustype='3'";
		BigDecimal cou = (java.math.BigDecimal) sess.createSQLQuery(sql).uniqueResult();
		BigDecimal cou1 = (java.math.BigDecimal) sess.createSQLQuery(sql1).uniqueResult();
		BigDecimal cou2 = (java.math.BigDecimal) sess.createSQLQuery(sql2).uniqueResult();
		String count = cou.toString();
		String count1 = cou1.toString();
		String count2 = cou2.toString();
		String sumcont = count+","+count1+","+count2;
		String sqlAll=sql3+"$"+sql4+"$"+sql5;
		this.getExecuteSG().addExecuteCode("comprevalue('"+sumcont+"','"+sqlAll+"');");*/
		HBSession sess =HBUtil. getHBSession ();
		
		String sql = "select count(*) from WHOLESTATUS t where t.statustype = '0'";//һƱ�����
		String sql1 = "select count(*) from WHOLESTATUS t where t.statustype = '1'";//��
		String sql2 = "select count(*) from WHOLESTATUS t where t.statustype = '2'";//��
		String sql3 = "select count(*) from WHOLESTATUS t where t.statustype = '3'";//��
		String sql4 = "select count(*) from b01 where b01.b0194 = '1' and b01.b0111 != '-1' and not exists (select 1 from WHOLESTATUS t where t.unit = b01.b0111)";//δ����
		
		Object cou = sess.createSQLQuery(sql).uniqueResult();
		Object cou1 = sess.createSQLQuery(sql1).uniqueResult();
		Object cou2 = sess.createSQLQuery(sql2).uniqueResult();
		Object cou3 = sess.createSQLQuery(sql3).uniqueResult();
		Object cou4 = sess.createSQLQuery(sql4).uniqueResult();

		String sumcont = cou+","+cou1+","+cou2+","+cou3+","+cou4;
		this.getExecuteSG().addExecuteCode("comprevalue('"+sumcont+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dataTb")
	@Transaction
	public int dataTb(){
		HBSession sess =HBUtil. getHBSession ();
		
		//list  redһƱΪ��   red��ֵ����   yellow  green
		
		//�Ȳ�ѯtroupe_score�У�����Щ��������������   ���������������Բ�� ���� ��  δ�� �Ļ���
		List<Object[]> list = sess.createSQLQuery("select * from TROUPE_SCORE t").list();
		
		if(list!=null&&list.size()>0){
			for(Object[] objs : list){
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
				
				//for ���� ���� �������ֱ�������Щ��(sql)
				String personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
						+ "where a0255 = '1' and a0201b = '"+b0111+"' and A0215A_c is not null) a "
						+ "where f.gwcode = a.A0215A_c and b0111 = '"+b0111+"'";//ʵ����ԱSQL
				
				// ����ǹ�������
				zrjg(b0111,sess,personSql,bzf,xbf,dpf,mzf,nlf,xlf,zyf,dyf,knowf,jlf,redf,greenf);
				// for 	���ݻ���ID ���� NATURAL_STRUCTURE����ѯ�����õ���Ȼ���� ,������֮ǰ���㷨����� ���� �������������ˣ�
						//�Ա� ʵ����Ա  ��   ��Ȼ����  ��������Ա���ж��Ƿ����õ� ����Ȼ���� �ķ�ֵ��ͬʱ���� һƱ�����
		
				//�����������з�ֵ��ӣ��Ա� ��λ troupe_score ��redf��greenf �����жϸû�����״̬
				
			}
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public int zrjg(String gid,HBSession sess,String sql,String bzf,String xbf,String dpf,String mzf,
			String nlf,String xlf,String zyf,String dyf,String knowf,String jlf,String redf,String greenf){
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
			
			this.setMainMessage("����ͬ���ɹ���");
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
	
	private String isFUYI(String val){
		if("-1".equals(val)){
			val = "0";
		}
		return val;
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
