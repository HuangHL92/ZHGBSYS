package com.insigma.siis.local.pagemodel.cadremgn.comm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hsqldb.lib.StringUtil;

import com.fr.third.org.apache.poi.hssf.record.formula.functions.Request;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.AddRmbPageModel;


public class RedundancyCode {
	public static CommQuery cq = new CommQuery();
	private static GenericCodeItem gc = GenericCodeItem.getGenericCodeItem();
	public void saveForA01(String code,String a0000,HttpServletRequest request) throws AppException, RadowException {
		HBSession sess = HBUtil.getHBSession();
		if("A02".equals(code)){
			this.UpdateA0201c(a0000,sess);
			this.UpdateTitleBtn(a0000,request);
		}else if("A01".equals(code)){
			this.saveDegrees(a0000,request);
		}else if("A05".equals(code)){
			this.saveTrainingInfo(a0000);
			this.saveTrainingInfoA0501Q(a0000);
		}else if("A06".equals(code)){
			this.updateA01(a0000);
		}else if("A08".equals(code)){
			this.printout(a0000,sess,"1");// �������ѧ��ѧλ��� 
			this.printout(a0000,sess,"2");
			this.updateZGQRZXueliXuewei(a0000,sess);
			this.updateZGZZXueliXuewei(a0000,sess);//�����ְѧ��ѧλ��־
			this.updateXueliXuewei(a0000,sess,"1");
			this.updateXueliXuewei(a0000,sess,"2");
			this.updateZGXueliXuewei(a0000,sess);//���ѧ��ѧλ��־
			this.updateA01ZGxuelixuewei(a0000);
		}else if("A14".equals(code)){
			this.saveA14(a0000);
		}
	}
	
	public void saveForA01(String code,List<String> a0000List,HttpServletRequest request) throws AppException, RadowException {
		HBSession sess = HBUtil.getHBSession();
		for(String a0000:a0000List){
			if("A02".equals(code)){
				this.UpdateA0201c(a0000,sess);
				this.UpdateTitleBtn(a0000,request);
			}else if("A39".equals(code)){
				this.saveDegrees(a0000,request);
			}else if("A05".equals(code)){
				this.saveTrainingInfo(a0000);
				this.saveTrainingInfoA0501Q(a0000);
			}else if("A06".equals(code)){
				this.updateA01(a0000);
			}else if("A08".equals(code)){
				this.printout(a0000,sess,"1");// �������ѧ��ѧλ��� 
				this.printout(a0000,sess,"2");
				this.updateZGQRZXueliXuewei(a0000,sess);
				this.updateZGZZXueliXuewei(a0000,sess);//�����ְѧ��ѧλ��־
				this.updateXueliXuewei(a0000,sess,"1");
				this.updateXueliXuewei(a0000,sess,"2");
				this.updateZGXueliXuewei(a0000,sess);//���ѧ��ѧλ��־
				this.updateA01ZGxuelixuewei(a0000);
			}else if("A14".equals(code)){
				this.saveA14(a0000);
			}			
		}
	}
	
	/*
	 * �ѵڶ��������ɴ浽a01����
	 */
	public void saveDegrees(String a0000,HttpServletRequest request) {
		request.getSession().setAttribute("code_name", "code_name");
		HBSession sess =  HBUtil.getHBSession();
		List<HashMap<String, Object>> degreesList = null;
		String sql = "select a0141,a0144,a3921,a3927 from a01 where a0000='"+a0000+"'";
		String a0141 = "";//������ò
		String a0144 = "";//�뵳ʱ��
		String A0140 = "";//�뵳ʱ������
		String a3921 = "";//�ڶ�����
		String a3927 = "";//��������
		try {
			degreesList = cq.getListBySQL(sql);
			if(degreesList.size()==0||degreesList.size()>1){
				return;
			}
			for(HashMap<String,Object> map:degreesList){
				a0141 = map.get("a0141")==null?"":map.get("a0141").toString();//������ò
				a0144 = map.get("a0144")==null?"":map.get("a0144").toString();//�뵳ʱ��
				a3921 = map.get("a3921")==null?"":map.get("a3921").toString();//�ڶ�����
				a3927 = map.get("a3927")==null?"":map.get("a3927").toString();//��������
			}
			String a0141_combo = gc.getDescription("A01.A0141", a0141, request);
			String a3921_combo = gc.getDescription("A01.A3921", a3921, request);
			String a3927_combo = gc.getDescription("A01.A3927", a3927, request);
			if((a0141==null || "".equals(a0141))&& ((a0144!=null && !"".equals(a0144))|| (a3921!=null && !"".equals(a3921))|| (a3927!=null && !"".equals(a3927)))){
				return;
			} else {
				if("02".equals(a0141) || "01".equals(a0141)){
					if((a0144==null || "".equals(a0144))){
						return;
					}	
					String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							return;
						} else {
							A0140 = a0144_sj ;
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  a3921_combo+ "��" + a3927_combo + "(" + a0144_sj +")";
						} else {
							A0140 =  a3921_combo+ "(" + a0144_sj +")";
						}
					}
				} else {
					if("02".equals(a3921) || "01".equals(a3921)){
						if((a0144==null || "".equals(a0144))){
							return;
						}
					}
					if("02".equals(a3927) || "01".equals(a3927)){
						if((a0144==null || "".equals(a0144))){
							return;
						}
					}
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							return;
						} else {
							A0140 = "(" + a0141_combo +")";
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  "(" + a0141_combo+ "��" +a3921_combo+ "��" + a3927_combo +")";
						} else {
							A0140 =  "(" + a0141_combo+ "��" +a3921_combo+ ")";
						}
					}
				}
				
			}
			
			if("()".equals(A0140)){
				A0140="";
			}
			String a0144_time = "";
			if(a0144 != null && !a0144.equals("")){
				a0144_time = a0144.substring(0,4)+"."+a0144.substring(4,6);
			}
			
			
			//����ƴ���뵳ʱ��
			String A0140New = "";
			String date = "";
			
			if(a0141.equals("01") || a0141.equals("02")){
				date = "("+a0144_time+")";
				a0141_combo = "";
			}
			if(a3921.equals("01") || a3921.equals("02")){
				date = "("+a0144_time+")";
				a3921_combo = "";
			}
			if(a3927.equals("01") || a3927.equals("02")){
				date = "("+a0144_time+")";
				a3927_combo = "";
			}
			
			
			
			if(a0141_combo != null && !a0141_combo.equals("")){
				A0140New = A0140New + a0141_combo+ "��";
			}
			if(a3921_combo != null && !a3921_combo.equals("")){
				A0140New = A0140New + a3921_combo+ "��";
			}
			if(a3927_combo != null && !a3927_combo.equals("")){
				A0140New = A0140New + a3927_combo+ "��";
			}
			
			if(A0140New != null && !A0140New.equals("")){
				A0140New = A0140New.substring(0, A0140New.length()-1);
				if(date != null && !date.equals("")){
					A0140New = A0140New + date;
				}
			}else{
				A0140New = A0140New + a0144_time;
			}
			
			
			if("()".equals(A0140New)){
				A0140New="";
			}
			
			//�ж��Ƿ�Ϊ��������û����ƴ������
			if(!"02".equals(a0141) && !"01".equals(a0141)){		//������
				
				if(A0140New != null && !A0140New.equals("")){
					A0140New = "(" +A0140New+ ")";
					a0144="";
				}
				
			}
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0140(A0140New);
			if("".equals(a0144)){
				a01.setA0144(a0144);
			}
			sess.update(a01);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * רҵ����ְ��
	 */
	public void updateA01(String a0000) {
		//��ǰҳ�渳ֵ
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"��");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		
		try {
			CustomQueryBS.setA01(a0000);
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//����A10 a0196 רҵ����ְ���ֶΡ�   a06 a0602
			HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/*
	 * ѧ��ѧλ����
	 */
	public void updateA01ZGxuelixuewei(String a0000) throws AppException, RadowException{
		HBSession sess = HBUtil.getHBSession();
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0834='1' and a0899='true'";  //��ѧ��������������
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//Ժϵ
					String zy_xl = xueli.getA0824();//רҵ
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					
					
					/*if(!"".equals(zy_xl)){
						
						//��רҵ�еġ�רҵ�������
						zy_xl = zy_xl.replace("רҵ", "");
						
//						zy_xl += "רҵ";
					}*/
					 xl = xueli.getA0801a();//ѧ�� ����
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setZgxl(xl);//ѧ��
			a01.setZgxlxx(xlxx);	
			sess.update(a01);
			sess.flush();
			
			
			String xw = "";//ѧλ����
			String xwxx = "";//ѧλ��Ժϵרҵ
			String sql="select * from a08 where a0000='"+a0000+"' and a0835='1' and a0899='true'";
			List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();
			if(list!=null&&list.size()>0){
				A08 xuewei=list.get(0);
				xw=xuewei.getA0901a();
				String yx_xw=xuewei.getA0814();
				String zy_xw=xuewei.getA0824();
				if(yx_xw==null){
					yx_xw = "";
				}
				if(zy_xw==null){
					zy_xw = "";
				}
				/*if(!"".equals(zy_xw)){
					//��רҵ�еġ�רҵ�������
					zy_xw = zy_xw.replace("רҵ", "");
					
//					zy_xw += "רҵ";
				}*/
				xwxx = yx_xw+zy_xw;
				if(xw==null&&"".equals(xw)){
					xwxx=null;
				}
				if(list.size()>1){
					A08 xuewei2=list.get(1);
					String xw2=xuewei2.getA0901a();
					String yx_xw2=xuewei2.getA0814();
					String zy_xw2=xuewei2.getA0824();
					if(yx_xw2==null){
						yx_xw2 = "";
					}
					if(zy_xw2==null){
						zy_xw2 = "";
					}
					/*if(!"".equals(zy_xw2)){
						
						//��רҵ�еġ�רҵ�������
						zy_xw2 = zy_xw2.replace("רҵ", "");
						
//						zy_xw2 += "רҵ";
					}*/
					if(xw2!=null&&!"".equals(xw2)){
						if(xw==null&&"".equals(xw)){
							xw=xw2;
							xwxx= yx_xw2+zy_xw2;
						}else{
							xw=xw+","+xw2;
							if(StringUtil.isEmpty(xwxx)){
								xwxx= yx_xw2+zy_xw2;
							}else{
								xwxx=xwxx+","+yx_xw2+zy_xw2;
							}
							
						}
					}
				}
			}
		try {
			a01.setZgxw(xw);//ѧλ
			a01.setZgxwxx(xwxx);
			sess.update(a01);
			sess.flush();
			CustomQueryBS.setA01(a0000);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//�ж���ߵ�ѧ��ѧλ�����
		public int printout(String a0000,HBSession sess,String a0837) throws RadowException{
			String sql="update a08 set a0899='false' where a0000='"+a0000+"' and a0837='"+a0837+"'";
			sess.createSQLQuery(sql).executeUpdate();
			sess.flush();
			String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='"+a0837+"'";// ����������ְѧ�� 
			List<Object[]> list1 = sess.createSQLQuery(sql1).list();
			if(list1!=null&&list1.size()>0){
				Collections.sort(list1,new Comparator<Object[]>(){//ѧ������
					@Override
					public int compare(Object[] o1, Object[] o2) {
						String a0801b_1 = o1[1]==null?"":o1[1].toString();//ѧ������
						String a0801b_2 = o2[1]==null?"":o2[1].toString();
						if(a0801b_1==null||"".equals(a0801b_1)){
							return 1;
						}
						if(a0801b_2==null||"".equals(a0801b_2)){
							return -1;
						}
						//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
						return a0801b_1.compareTo(a0801b_2);
					}
					
				});
			}
			//���ֻ��һ��ѧ����Ϣ,���ѧ�����벻Ϊ�վ������ѧ��
		/*	if(list1!=null&&list1.size()==1){
				A08 a08=list1.get(0);
				String xuelidaima=a08.getA0801b();
				if(!StringUtil.isEmpty(xuelidaima)){
					a08.setA0899("true");
				}
				sess.update(a08);
			}*/
			//����ж�����¼,��һ����¼ѧ�����벻Ϊ�վ������ѧ��,ʣ��ѧ������������һ��һ��Ҳ������
			if(list1!=null&&list1.size()>0){
				Object[] a08=list1.get(0);
				String xuelidaima=a08[1]==null?"":a08[1].toString();
				if(!StringUtil.isEmpty(xuelidaima)){
					sess.createSQLQuery(" update a08 set a0899='true' where a0800='"+a08[0]+"'").executeUpdate();
					/*for(int i=1;i<list1.size();i++){
						A08 a08_x=list1.get(i);
						String xuelidaima_x=a08_x.getA0801b();
						String duibi=xuelidaima.substring(0,1);
						if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
							a08_x.setA0899("true");
						}else{
							a08_x.setA0899("false");
						}
						sess.update(a08_x);
					}*/
				}
			}
			String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and  length(a0901b)>0 order by a0901b asc";
			List<Object[]> list2 = sess.createSQLQuery(sql2).list();
			//���ֻ��һ��ѧλ��Ϣ,���ѧλ���벻Ϊ�վ������ѧλ
			/*if(list2!=null&& list2.size()==1){
				A08 a08=list2.get(0);
				String xueweidaima=a08.getA0901b();
				if(!StringUtil.isEmpty(xue weidaima)){
					 a08.setA0899("true");
				}
				sess.update(a08);
			}*/
			//����ж�����¼,��һ����¼ѧλ���벻Ϊ�վ������ѧλ,ʣ��ѧλ����������һ���ԱȺ����һ��ҲΪ���ѧλ
			if(list2!=null&&list2.size()>0){
				Object[] a08_1=list2.get(0);
				String xueweidaima=a08_1[1].toString();
				if(!StringUtil.isEmpty(xueweidaima)){
					sess.createSQLQuery(" update a08 set a0899='true' where a0800='"+a08_1[0]+"'").executeUpdate();

					if(xueweidaima.startsWith("1")){
						for(int i=1;i<list2.size();i++){
							Object[] a08_x=list2.get(i);
							String xueweidaima_x=a08_x[1].toString();
							if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
								sess.createSQLQuery(" update a08 set a0899='true' where a0800='"+a08_x[0]+"'").executeUpdate();
							}
							
						}
					}else{
						String reg=xueweidaima.substring(0,1);
							for(int i=1;i<list2.size();i++){
								Object[] a08_x=list2.get(i);
								String xueweidaima_x=a08_x[1].toString();
								if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
									sess.createSQLQuery(" update a08 set a0899='true' where a0800='"+a08_x[0]+"'").executeUpdate();
								}
							}
						
					}
				
				}	
			}
			sess.flush();
			return EventRtnType.NORMAL_SUCCESS;
		}
	
	private void updateZGQRZXueliXuewei(String a0000, HBSession sess) {
		String sql=" update a08 set a0831='0',a0832='0' where a0000='"+a0000+"' and a0899='true' and a0837='1'";
		sess.createSQLQuery(sql).executeUpdate();
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1'";//��������ȫ����ѧ�� 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//ѧ������
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//ѧ������
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//����ж�����¼,��һ����¼ѧ�����벻Ϊ�վ������ѧ��,ʣ��ѧ������������һ��һ��Ҳ������
				if(list1!=null&&list1.size()>0){
					Object[] a08=list1.get(0);
					String xuelidaima=a08[1]==null?"":a08[1].toString();
					if(!StringUtil.isEmpty(xuelidaima)){
						sess.createSQLQuery(" update a08 set a0831='1' where a0800='"+a08[0]+"'").executeUpdate();
					}
				}
				
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
				if(list2!=null&&list2.size()>0){
					Object[] a08_1=list2.get(0);
					String xueweidaima=a08_1[1].toString();
					if(!StringUtil.isEmpty(xueweidaima)){
						sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_1[0]+"'").executeUpdate();
						if(xueweidaima.startsWith("1")){
							for(int i=1;i<list2.size();i++){
								Object[] a08_x=list2.get(i);
								String xueweidaima_x=a08_x[1].toString();
								if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
									sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
								}
							}
						}else{
							String reg=xueweidaima.substring(0,1);
								for(int i=1;i<list2.size();i++){
									Object[] a08_x=list2.get(i);
									String xueweidaima_x=a08_x[1].toString();;
									if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
										sess.createSQLQuery("update a08 set a0832='1' where a0800='"+a08_x[0]+"'").executeUpdate();	
									}
									
								}
							
						}
					
					}
				}
				sess.flush();
		
	}
	private void updateZGZZXueliXuewei(String a0000, HBSession sess) {
		String sql=" update a08 set a0838='0',a0839='0' where a0000='"+a0000+"' and a0899='true' and a0837='2'";
		sess.createSQLQuery(sql).executeUpdate();
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2'";// ����������ְѧ�� 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//ѧ������
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//ѧ������
					String a0801b_2 = o2[1]==null?"":o2[1].toString();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		if(list1!=null&&list1.size()>0){
			Object[] a08=list1.get(0);
			String xuelidaima=a08[1]==null?"":a08[1].toString();
			if(!StringUtil.isEmpty(xuelidaima)){
				sess.createSQLQuery(" update a08 set a0838='1' where a0800='"+a08[0]+"'").executeUpdate();
			
			}
		}
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		if(list2!=null&&list2.size()>0){
			Object[] a08_1=list2.get(0);
			String xueweidaima=a08_1[1].toString();
			if(!StringUtil.isEmpty(xueweidaima)){
				sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_1[0]+"'").executeUpdate();

				if(xueweidaima.startsWith("1")){
					for(int i=1;i<list2.size();i++){
						Object[] a08_x=list2.get(i);
						String xueweidaima_x=a08_x[1].toString();
						if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
							sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
						}
					}
				}else{
					String reg=xueweidaima.substring(0,1);
						for(int i=1;i<list2.size();i++){
							Object[] a08_x=list2.get(i);
							String xueweidaima_x=a08_x[1].toString();
							if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
								sess.createSQLQuery(" update a08 set a0839='1' where a0800='"+a08_x[0]+"'").executeUpdate();
							}
						}
					
				}
			
			}
			
		}
		sess.flush();
	}
	
	private void updateZGXueliXuewei(String a0000, HBSession sess) throws AppException {
		String update ="update a08 set a0834='0',a0835='0'  where a0000='"+a0000+"' and a0899='true'";
		sess.createSQLQuery(update).executeUpdate();
		sess.flush();
		String sql="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='1' and a0831='1' and a0899='true'";
		String sql2="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='2' and a0838='1' and a0899='true'";
		Object[] qrz=(Object[]) sess.createSQLQuery(sql).uniqueResult();
		Object[] zz=(Object[]) sess.createSQLQuery(sql2).uniqueResult();
		if((qrz==null||qrz.length<1)&&(zz!=null&&zz.length>0)){
			Object a0800=zz[0];
			sess.createSQLQuery("update a08 set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if((zz==null||zz.length<1)&&(qrz!=null&&qrz.length>0)){
			Object a0800=qrz[0];
			sess.createSQLQuery("update a08 set a0834='1' where a0800='"+a0800+"'").executeUpdate();
		}
		if(qrz!=null&&zz!=null&&qrz.length>0&&zz.length>0){
			
			String qrzxueli=qrz[1].toString();
			String zzxueli=zz[1].toString();
			String qrzxl=qrzxueli.substring(0,1);
			String zzxl=zzxueli.substring(0, 1);
			if(qrzxl.compareTo(zzxl)==-1){
				sess.createSQLQuery("update a08 set a0834='1' where a0800='"+qrz[0]+"'").executeUpdate();
			}else{
				sess.createSQLQuery("update a08 set a0834='1' where a0800='"+zz[0]+"'").executeUpdate();
			}
			
			
		}
		
		String sql3="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0837='1' and a0832='1' and a0899='true'";
		String sql4="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0837='2' and a0839='1' and a0899='true'";
		List<Object[]> list1=sess.createSQLQuery(sql3).list();
		List<Object[]> list2=sess.createSQLQuery(sql4).list();
		if((list1==null||list1.size()<1)&&(list2!=null&&list2.size()>0)){
			for(int i=0;i<list2.size();i++){
				Object a0800=list2.get(i)[0];
				sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			}
		}
		if((list1!=null&&list1.size()>0)&&(list2==null||list2.size()<1)){
			for(int i=0;i<list1.size();i++){
				Object a0800=list1.get(i)[0];
				sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
			
			}
		}
		if(list1!=null&&list2!=null&&list1.size()>0&&list2.size()>0){

			String qrzxuewei=list1.get(0)[1].toString();
			String zzxuewei=list2.get(0)[1].toString();
			String qrzxw=qrzxuewei.substring(0,1);
			String zzxw=zzxuewei.substring(0, 1);
			if(qrzxw.compareTo(zzxw)==-1){
				for(int i=0;i<list1.size();i++){
					Object a0800=list1.get(i)[0];
					sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				
				}
			}else{
				for(int i=0;i<list2.size();i++){
					Object a0800=list2.get(i)[0];
					sess.createSQLQuery("update a08 set a0835='1' where a0800='"+a0800+"'").executeUpdate();
				
				}
			}
		}
		sess.flush();
		
	}
	
	private void updateXueliXuewei(String a0000, HBSession sess,String a0837) throws AppException {
		checkXL(sess,a0837,a0000);
		checkXW(sess,a0837,a0000);
	}

	private void checkXL(HBSession sess,String a0837,String a0000) throws AppException {//a0837�������
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //��ѧ��������������
				if("1".equals(a0837)){
					sql1+=" and a0831='1'";
				}
				if("2".equals(a0837)){
					sql1+=" and a0838='1'";
				}
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//Ժϵ
					String zy_xl = xueli.getA0824();//רҵ
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					
					
					/*if(!"".equals(zy_xl)){
						
						//��רҵ�еġ�רҵ�������
						zy_xl = zy_xl.replace("רҵ", "");
						
//						zy_xl += "רҵ";
					}*/
					 xl = xueli.getA0801a();//ѧ�� ����
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//����a01 ѧ��ѧλ��Ϣ:ȫ���ƣ�qrzxlѧ�� qrzxwѧλ qrzxlxxԺУϵ��רҵ����ְ zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		if("1".equals(a0837)){//ȫ����
			a01.setQrzxl(xl);//ѧ��
			a01.setQrzxlxx(xlxx);
		}else{//��ְ
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}

	private void checkXW(HBSession sess,String a0837,String a0000) throws AppException{
		String xw = "";//ѧλ����
		String xwxx = "";//ѧλ��Ժϵרҵ
		String sql="select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";
		if("1".equals(a0837)){
			sql+=" and a0832='1' order by a0901b";
		}
		if("2".equals(a0837)){
			sql+=" and a0839='1' order by a0901b";
		}
		List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();
		if(list!=null&&list.size()>0){
			A08 xuewei=list.get(0);
			xw=xuewei.getA0901a();
			String yx_xw=xuewei.getA0814();
			String zy_xw=xuewei.getA0824();
			if(yx_xw==null){
				yx_xw = "";
			}
			if(zy_xw==null){
				zy_xw = "";
			}
			/*if(!"".equals(zy_xw)){
				//��רҵ�еġ�רҵ�������
				zy_xw = zy_xw.replace("רҵ", "");
				
//				zy_xw += "רҵ";
			}*/
			xwxx = yx_xw+zy_xw;
			if(xw==null&&"".equals(xw)){
				xwxx=null;
			}
			if(list.size()>1){
				A08 xuewei2=list.get(1);
				String xw2=xuewei2.getA0901a();
				String yx_xw2=xuewei2.getA0814();
				String zy_xw2=xuewei2.getA0824();
				if(yx_xw2==null){
					yx_xw2 = "";
				}
				if(zy_xw2==null){
					zy_xw2 = "";
				}
				/*if(!"".equals(zy_xw2)){
					
					//��רҵ�еġ�רҵ�������
					zy_xw2 = zy_xw2.replace("רҵ", "");
					
//					zy_xw2 += "רҵ";
				}*/
				if(xw2!=null&&!"".equals(xw2)){
					if(xw==null&&"".equals(xw)){
						xw=xw2;
						xwxx= yx_xw2+zy_xw2;
					}else{
						xw=xw+","+xw2;
						if(StringUtil.isEmpty(xwxx)){
							xwxx= yx_xw2+zy_xw2;
						}else{
							xwxx=xwxx+","+yx_xw2+zy_xw2;
						}
						
					}
				}
			}
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if("1".equals(a0837)){//ȫ����
			a01.setQrzxw(xw);//ѧλ
			a01.setQrzxwxx(xwxx);
		}else{//��ְ
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}
	
	/*
	 * ��ְ����
	 */
	public void saveTrainingInfo(String a0000){
		HBSession sess = HBUtil.getHBSession();
		String sql = "select a0501b,a0504 from a05 where a0524='1' and a0000='"+a0000+"' and a0531='0'";
		try {
			String a0221 = "";
			String a0288 = "";
			List<HashMap<String,Object>> list = cq.getListBySQL(sql);
			if(list.isEmpty()||list.size()>1){
				return;
			}
			for(HashMap<String,Object> map:list){
				a0221 = map.get("a0501b")==null?"":map.get("a0501b").toString();
				a0288 = map.get("a0504")==null?"":map.get("a0504").toString();
			}
			A01 a01 = (A01) sess.get(A01.class, a0000);
			a01.setA0221(a0221);
			a01.setA0288(a0288);
			CustomQueryBS.setA01(a0000);
			sess.saveOrUpdate(a01);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ��ְ��
	 */
	public void saveTrainingInfoA0501Q(String a0000){
		HBSession sess = HBUtil.getHBSession();
		String sql = "select a0501b,a0504 from a05 where a0524='1' and a0000='"+a0000+"' and a0531='1'";
		try {
			String a0192d = "";
			String a0192c = "";
			List<HashMap<String,Object>> list = cq.getListBySQL(sql);
			if(list.isEmpty()||list.size()>1){
				return;
			}
			for(HashMap<String,Object> map:list){
				a0192d = map.get("a0501b")==null?"":map.get("a0501b").toString();
				a0192c = map.get("a0504")==null?"":map.get("a0504").toString();
			}
			A01 a01 = (A01) sess.get(A01.class, a0000);
			a01.setA0192d(a0192d);
			a01.setA0192c(a0192c);
			CustomQueryBS.setA01(a0000);
			sess.saveOrUpdate(a01);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 */
	public void UpdateA0201c(String a0000,HBSession sess) throws AppException {
		String sql = "select a0200,a0201b,a0201a from a02 where a0000='"+a0000+"'";
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list.isEmpty()||list==null){
			return;
		}
		for(HashMap<String,Object> map: list){
			String a0201bb = map.get("a0201b")==null?"":map.get("a0201b").toString();
			String a0200 = map.get("a0200")==null?"":map.get("a0200").toString();
			String a0201a = map.get("a0201a")==null?"":map.get("a0201a").toString();
			if(a0201bb!=null&&!"".equals(a0201bb)){//��ȡ�������
				if("-1".equals(a0201bb)){//������λ
					sess.createSQLQuery("update a02 set a0201c='"+a0201a+"' where a0200='"+a0200+"'").executeUpdate();
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201bb);
					if(b01!=null){
						String a0201c = b01.getB0104()==null?"":b01.getB0104();
						sess.createSQLQuery("update a02 set a0201c='"+a0201c+"' where a0200='"+a0200+"'").executeUpdate();
					}
				}
			}
		}
	}
	/*
	 * ���ι���ְ��
	 */
	public void UpdateTitleBtn(String a0000,HttpServletRequest request) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "select * from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ������λand a0201b!='-1'
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//���� ���
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String yrqm = "";//ԭ��
			String zrjc = "";//���
			String ymjc = "";
			String yrjc = "";//ԭ��
			for(HashMap<String, Object> map : list){
				String a0255 =map.get("a0255")==null?"":map.get("a0255").toString();//��ְ״̬
				String jgbm = map.get("a0201b")==null?"":map.get("a0201b").toString();//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				jgmcList.add(map.get("a0201a")==null?"":map.get("a0201a").toString());
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(map.get("a0201c")==null?"":map.get("a0201c").toString());//�������� ���
				
				String zwmc = map.get("a0215b")==null?"":map.get("a0215b").toString();//ְ������
				String code_value = map.get("a0215a")==null?"":map.get("a0215a").toString();//ְ�����ƴ���
				if("".equals(zwmc)&&!"".equals(code_value)){
					String newCode_Id ="A02.A0215A";
					zwmc = gc.getDescription(newCode_Id, code_value, request);					
				}
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc);
										
										
									}
									//���
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "��" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key ����_$_��������_$_�Ƿ�����
						}else{
							if(!"".equals(zwmc)){
								desc_full.put(key_full, value_full + "��" + zwmc);								
							}
						}
						
						//���
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							if(!"".equals(zwmc)){
								desc_short.put(key_short, value_short  + "��" +  zwmc);								
							}
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else if("0".equals(parm[2])){//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}else{//ԭ��
					if(!"".equals(jgzw)){
						yrqm += jgzw + "��";
					}
				}
			}
			for(String key : desc_short.keySet()){//���
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "��";
					}
				}else if("0".equals(parm[2])){//����
					if(!"".equals(jgzw)){
						ymjc += jgzw + "��";
					}
				}else{//ԭ��
					if(!"".equals(jgzw)){
						yrjc += jgzw + "��";
					}
				}
			}
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				//ymqm = "(ԭ"+ymqm+")";
			}
			if(!"".equals(yrqm)&&"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "(ԭ"+yrqm+")";
			}else if(!"".equals(yrqm)&&!"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "(ԭ"+yrqm+"��"+ymqm+")";
			}
			
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				//ymjc = "(ԭ"+ymjc+")";
			}
			
			if(!"".equals(yrjc)&&"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length()-1);
				yrjc = "(ԭ"+yrjc+")";
			}else if(!"".equals(yrjc)&&!"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length()-1);
				yrjc = "(ԭ"+yrjc+"��"+ymjc+")";
			}
			a01.setA0192a(zrqm+yrqm);
			a01.setA0192(zrjc+yrjc);
			sess.update(a01);	
		}else{
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);	
		}
		sess.flush();
		this.UpdateTimeBtn(a0000);
	}
	
	
	/**
	 * �������ƶ�Ӧ��ʱ��
	 * @throws AppException 
	 * @throws RadowException
	 */
	public void UpdateTimeBtn(String a0000) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "select * from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ������λand a0201b!='-1'
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String yrqm = "";//ԭ��ȫ��
			String zrjc = "";//���
			String ymjc = "";
			String yrjc = "";//ԭ�μ��
			for(HashMap<String, Object> map : list){
				String a0255 =map.get("a0255")==null?"":map.get("a0255").toString();//��ְ״̬
				String jgbm = map.get("a0201b")==null?"":map.get("a0201b").toString();//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				jgmcList.add("");
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(map.get("a0201c")==null?"":map.get("a0201c").toString());//�������� ���
				String zwmc =map.get("a0215b")==null?"":map.get("a0215b").toString();;//ְ������
				String zwrzshj = "";//ְ����ְʱ��
				if(map.get("a0243") != null && map.get("a0243").toString().length() >= 6 && map.get("a0243").toString().length() <= 8){
					zwrzshj = map.get("a0243").toString().substring(0,4) + "." + map.get("a0243").toString().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "��" + zwrzshj);
										
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwrzshj);//key ����_$_��������_$_�Ƿ�����
						}else{
							desc_full.put(key_full, value_full + "��" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else if("0".equals(parm[2])){//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}else{
					if(!"".equals(jgzw)){
						yrqm += jgzw + "��";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				//ymqm = "("+ymqm+")";
			}
			if(!"".equals(yrqm)&&"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "("+yrqm+")";
			}else if(!"".equals(yrqm)&&!"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "("+yrqm+"��"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
		    if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				//ymjc = "("+ymjc+")";
			}
			if(!"".equals(yrjc)&&"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length()-1);
				yrjc = "("+yrjc+")";
			}else if(!"".equals(yrjc)&&!"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length());
				yrjc = "("+yrjc+"��"+ymjc+")";
			}
			
			
			//a01.setA0192f(zrqm+ymqm);
			a01.setA0192f(zrqm+yrqm);
			sess.update(a01);	
		}else{
			
		
			a01.setA0192f(null);
			sess.update(a01);
		}	
		sess.flush();
	}
	
	/*
	 * ���͸���
	 */
	public void saveA14(String a0000){
		HBSession sess = HBUtil.getHBSession();
        String sql = "select a1404b,a1407,a1411a,a1404a from  a14  where a0000='"+a0000+"' order by a1407 desc";
        A01 a01 = (A01) sess.get(A01.class, a0000);
        try {
        	List<HashMap<String, Object>> list = cq.getListBySQL(sql);
            if(list.isEmpty()||list.size()>0){
            	StringBuffer desc = new StringBuffer("");
    			for(HashMap<String, Object> map : list){
    				//�������ƴ���
    				String a1404b = map.get("a1404b")==null?"":map.get("a1404b").toString();
    				//��������
    				String a1404a = map.get("a1404a")==null?"":map.get("a1404a").toString();
    				//��׼����
    				String a1411a =map.get("a1411a")==null?"":map.get("a1411a").toString();
    				
    				//��׼ʱ��
    				String a1407 = map.get("a1407")==null?"":map.get("a1407").toString();
    				
	    				if(a1407!=null&&a1407.length()>=6){
	    					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"��";
	    				}else{
	    					a1407 = "";
	    				}
	    				if(a1404b.startsWith("01") || a1404b.startsWith("1")){//��
	    					
	    					if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
	    						desc.append("��"+a1411a+"��׼��").append("�ٻ�"+a1404a+"��");
	    					}else{
	    						desc.append("��"+a1411a+"��׼��").append(a1404a+"��");
	    					}
	    					
	    				}else{//��
	    					desc.append("��"+a1411a+"��׼��").append("��"+a1404a+"���֡�");
	    				}
	    				if(!"".equals(a1407)){
	    					desc.insert(0, a1407);
	    				}
    			
    				//�б�˳������׼ʱ�䵹�����ɵý������������������Ҫ����׼ʱ������
    				if(desc.toString().endsWith("��")){
    					desc.deleteCharAt(desc.length()-1).append("��");
    				}
    			}
    			a01.setA14z101(desc.toString());
    			sess.update(a01);
    			
			}else{
				String a14z101 = a01.getA14z101();
				if("".equals(a14z101)){
					a01.setA14z101("��");
					sess.update(a01);
					
				}
			}
            sess.flush();
	}catch (Exception e) {
		e.printStackTrace();
	}
	}

	/**
	 * chs 20180629	�Զ���� ������Ϣ����A15�����濼�˽������Ա��Ϣ��A01
	 * @param a0000	��Ա����
	 */
	public void saveA15ToA01(String a0000) {
		if (a0000==null || "".equals(a0000)) {
			return;
		}
		String a1527;
		List<HashMap<String, Object>> a15List = null;
		try {
			//��������Ƿ���¼��
			String sql = "select * from a15 where a0000 = '" + a0000 + "'";		
			a15List = cq.getListBySQL(sql);
			// ��ʾ���˽��������
			a1527 = a15List.get(0).get("a1527") == null ? "0" : a15List.get(0).get("a1527").toString();
			listAssociation(a1527, a0000);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * chs 20180629	�Զ���� ������Ϣ����A15�����濼�˽������Ա��Ϣ��A01
	 * @param a1527	������ʾ����
	 * @param a0000	��Ա����
	 * @throws AppException
	 */
	public void listAssociation(String a1527, String a0000) throws AppException {
		if(a1527==null||"".equals(a1527)){
			return;	
		}
		HBSession sess = HBUtil.getHBSession(); 
		A01 a01 = (A01) sess.get(A01.class, a0000);
		String sql = "from A15 where a0000='" + a0000 + "' order by a1521 asc";
		List<A15> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		if(list!=null && list.size()>0){
			int years = "".equals(a1527) ? list.size() : Integer.valueOf(a1527);
			if(years > list.size()){
				years = list.size();
			}
			StringBuffer desc = new StringBuffer("");
			for(int i = list.size() - years; i < list.size(); i++){
				A15 a15 = list.get(i);
				//�������
				String a1521 = a15.getA1521();
				//���˽������ְ������ȣ�����
				String a1517 = a15.getA1517();
				//���˽������ְ������ȣ�����
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				desc.append(a1521 + "����ȿ���" + a1517Name + "��");
			}
			if(desc.length()>0){
				desc.replace(desc.length() - 1, desc.length(), "��");
			}
			a01.setA15z101(desc.toString());
		}else{
			String description = "��";
			a01.setA15z101(description);
		}
		sess.update(a01);
		CustomQueryBS.setA01(a0000);
		sess.flush();
		return;	
	}
	
}
