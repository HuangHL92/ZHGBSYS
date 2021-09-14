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
			this.printout(a0000,sess,"1");// 设置最高学历学位输出 
			this.printout(a0000,sess,"2");
			this.updateZGQRZXueliXuewei(a0000,sess);
			this.updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
			this.updateXueliXuewei(a0000,sess,"1");
			this.updateXueliXuewei(a0000,sess,"2");
			this.updateZGXueliXuewei(a0000,sess);//最高学历学位标志
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
				this.printout(a0000,sess,"1");// 设置最高学历学位输出 
				this.printout(a0000,sess,"2");
				this.updateZGQRZXueliXuewei(a0000,sess);
				this.updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
				this.updateXueliXuewei(a0000,sess,"1");
				this.updateXueliXuewei(a0000,sess,"2");
				this.updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				this.updateA01ZGxuelixuewei(a0000);
			}else if("A14".equals(code)){
				this.saveA14(a0000);
			}			
		}
	}
	
	/*
	 * 把第二第三党派存到a01表里
	 */
	public void saveDegrees(String a0000,HttpServletRequest request) {
		request.getSession().setAttribute("code_name", "code_name");
		HBSession sess =  HBUtil.getHBSession();
		List<HashMap<String, Object>> degreesList = null;
		String sql = "select a0141,a0144,a3921,a3927 from a01 where a0000='"+a0000+"'";
		String a0141 = "";//政治面貌
		String a0144 = "";//入党时间
		String A0140 = "";//入党时间文字
		String a3921 = "";//第二党派
		String a3927 = "";//第三党派
		try {
			degreesList = cq.getListBySQL(sql);
			if(degreesList.size()==0||degreesList.size()>1){
				return;
			}
			for(HashMap<String,Object> map:degreesList){
				a0141 = map.get("a0141")==null?"":map.get("a0141").toString();//政治面貌
				a0144 = map.get("a0144")==null?"":map.get("a0144").toString();//入党时间
				a3921 = map.get("a3921")==null?"":map.get("a3921").toString();//第二党派
				a3927 = map.get("a3927")==null?"":map.get("a3927").toString();//第三党派
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
							A0140 =  a3921_combo+ "、" + a3927_combo + "(" + a0144_sj +")";
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
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ "、" + a3927_combo +")";
						} else {
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ ")";
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
			
			
			//重新拼接入党时间
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
				A0140New = A0140New + a0141_combo+ "、";
			}
			if(a3921_combo != null && !a3921_combo.equals("")){
				A0140New = A0140New + a3921_combo+ "、";
			}
			if(a3927_combo != null && !a3927_combo.equals("")){
				A0140New = A0140New + a3927_combo+ "、";
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
			
			//判断是否为共产党，没有则拼接括号
			if(!"02".equals(a0141) && !"01".equals(a0141)){		//加括号
				
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
	 * 专业技术职务
	 */
	public void updateA01(String a0000) {
		//当前页面赋值
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"，");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		
		try {
			CustomQueryBS.setA01(a0000);
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//更新A10 a0196 专业技术职务字段。   a06 a0602
			HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/*
	 * 学历学位更新
	 */
	public void updateA01ZGxuelixuewei(String a0000) throws AppException, RadowException{
		HBSession sess = HBUtil.getHBSession();
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0834='1' and a0899='true'";  //以学历编码升序排列
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//院系
					String zy_xl = xueli.getA0824();//专业
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					
					
					/*if(!"".equals(zy_xl)){
						
						//将专业中的“专业”先清除
						zy_xl = zy_xl.replace("专业", "");
						
//						zy_xl += "专业";
					}*/
					 xl = xueli.getA0801a();//学历 中文
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setZgxl(xl);//学历
			a01.setZgxlxx(xlxx);	
			sess.update(a01);
			sess.flush();
			
			
			String xw = "";//学位中文
			String xwxx = "";//学位的院系专业
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
					//将专业中的“专业”先清除
					zy_xw = zy_xw.replace("专业", "");
					
//					zy_xw += "专业";
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
						
						//将专业中的“专业”先清除
						zy_xw2 = zy_xw2.replace("专业", "");
						
//						zy_xw2 += "专业";
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
			a01.setZgxw(xw);//学位
			a01.setZgxwxx(xwxx);
			sess.update(a01);
			sess.flush();
			CustomQueryBS.setA01(a0000);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//判断最高的学历学位并输出
		public int printout(String a0000,HBSession sess,String a0837) throws RadowException{
			String sql="update a08 set a0899='false' where a0000='"+a0000+"' and a0837='"+a0837+"'";
			sess.createSQLQuery(sql).executeUpdate();
			sess.flush();
			String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='"+a0837+"'";// 输出的最高在职学历 
			List<Object[]> list1 = sess.createSQLQuery(sql1).list();
			if(list1!=null&&list1.size()>0){
				Collections.sort(list1,new Comparator<Object[]>(){//学历排序
					@Override
					public int compare(Object[] o1, Object[] o2) {
						String a0801b_1 = o1[1]==null?"":o1[1].toString();//学历代码
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
			//如果只有一条学历信息,如果学历代码不为空就是最高学历
		/*	if(list1!=null&&list1.size()==1){
				A08 a08=list1.get(0);
				String xuelidaima=a08.getA0801b();
				if(!StringUtil.isEmpty(xuelidaima)){
					a08.setA0899("true");
				}
				sess.update(a08);
			}*/
			//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
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
			//如果只有一条学位信息,如果学位代码不为空就是最高学位
			/*if(list2!=null&& list2.size()==1){
				A08 a08=list2.get(0);
				String xueweidaima=a08.getA0901b();
				if(!StringUtil.isEmpty(xue weidaima)){
					 a08.setA0899("true");
				}
				sess.update(a08);
			}*/
			//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1'";//输出的最高全日制学历 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//学历排序
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//学历代码
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
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
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
		String sql1 = "select a0800,a0801b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2'";// 输出的最高在职学历 
		List<Object[]> list1 = sess.createSQLQuery(sql1).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<Object[]>(){//学历排序
				@Override
				public int compare(Object[] o1, Object[] o2) {
					String a0801b_1 = o1[1]==null?"":o1[1].toString();//学历代码
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

	private void checkXL(HBSession sess,String a0837,String a0000) throws AppException {//a0837教育类别
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //以学历编码升序排列
				if("1".equals(a0837)){
					sql1+=" and a0831='1'";
				}
				if("2".equals(a0837)){
					sql1+=" and a0838='1'";
				}
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//院系
					String zy_xl = xueli.getA0824();//专业
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					
					
					/*if(!"".equals(zy_xl)){
						
						//将专业中的“专业”先清除
						zy_xl = zy_xl.replace("专业", "");
						
//						zy_xl += "专业";
					}*/
					 xl = xueli.getA0801a();//学历 中文
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		if("1".equals(a0837)){//全日制
			a01.setQrzxl(xl);//学历
			a01.setQrzxlxx(xlxx);
		}else{//在职
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}

	private void checkXW(HBSession sess,String a0837,String a0000) throws AppException{
		String xw = "";//学位中文
		String xwxx = "";//学位的院系专业
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
				//将专业中的“专业”先清除
				zy_xw = zy_xw.replace("专业", "");
				
//				zy_xw += "专业";
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
					
					//将专业中的“专业”先清除
					zy_xw2 = zy_xw2.replace("专业", "");
					
//					zy_xw2 += "专业";
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
		if("1".equals(a0837)){//全日制
			a01.setQrzxw(xw);//学位
			a01.setQrzxwxx(xwxx);
		}else{//在职
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
	}
	
	/*
	 * 现职务层次
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
	 * 现职级
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
			if(a0201bb!=null&&!"".equals(a0201bb)){//获取机构简称
				if("-1".equals(a0201bb)){//其它单位
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
	 * 现任工作职务
	 */
	public void UpdateTitleBtn(String a0000,HttpServletRequest request) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "select * from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String yrqm = "";//原任
			String zrjc = "";//简称
			String ymjc = "";
			String yrjc = "";//原任
			for(HashMap<String, Object> map : list){
				String a0255 =map.get("a0255")==null?"":map.get("a0255").toString();//任职状态
				String jgbm = map.get("a0201b")==null?"":map.get("a0201b").toString();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
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
				jgmc_shortList.add(map.get("a0201c")==null?"":map.get("a0201c").toString());//机构名称 简称
				
				String zwmc = map.get("a0215b")==null?"":map.get("a0215b").toString();//职务名称
				String code_value = map.get("a0215a")==null?"":map.get("a0215a").toString();//职务名称代码
				if("".equals(zwmc)&&!"".equals(code_value)){
					String newCode_Id ="A02.A0215A";
					zwmc = gc.getDescription(newCode_Id, code_value, request);					
				}
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
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
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							if(!"".equals(zwmc)){
								desc_full.put(key_full, value_full + "、" + zwmc);								
							}
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							if(!"".equals(zwmc)){
								desc_short.put(key_short, value_short  + "、" +  zwmc);								
							}
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else if("0".equals(parm[2])){//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "；";
					}
				}else{//原任
					if(!"".equals(jgzw)){
						yrqm += jgzw + "，";
					}
				}
			}
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else if("0".equals(parm[2])){//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "；";
					}
				}else{//原任
					if(!"".equals(jgzw)){
						yrjc += jgzw + "，";
					}
				}
			}
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				//ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(yrqm)&&"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "(原"+yrqm+")";
			}else if(!"".equals(yrqm)&&!"".equals(ymqm)){
				yrqm = yrqm.substring(0, yrqm.length()-1);
				yrqm = "(原"+yrqm+"；"+ymqm+")";
			}
			
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				//ymjc = "(原"+ymjc+")";
			}
			
			if(!"".equals(yrjc)&&"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length()-1);
				yrjc = "(原"+yrjc+")";
			}else if(!"".equals(yrjc)&&!"".equals(ymjc)){
				yrjc = yrjc.substring(0, yrjc.length()-1);
				yrjc = "(原"+yrjc+"；"+ymjc+")";
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
	 * 更新名称对应的时间
	 * @throws AppException 
	 * @throws RadowException
	 */
	public void UpdateTimeBtn(String a0000) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "select * from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String yrqm = "";//原任全称
			String zrjc = "";//简称
			String ymjc = "";
			String yrjc = "";//原任简称
			for(HashMap<String, Object> map : list){
				String a0255 =map.get("a0255")==null?"":map.get("a0255").toString();//任职状态
				String jgbm = map.get("a0201b")==null?"":map.get("a0201b").toString();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
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
				jgmc_shortList.add(map.get("a0201c")==null?"":map.get("a0201c").toString());//机构名称 简称
				String zwmc =map.get("a0215b")==null?"":map.get("a0215b").toString();;//职务名称
				String zwrzshj = "";//职务任职时间
				if(map.get("a0243") != null && map.get("a0243").toString().length() >= 6 && map.get("a0243").toString().length() <= 8){
					zwrzshj = map.get("a0243").toString().substring(0,4) + "." + map.get("a0243").toString().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "、" + zwrzshj);
										
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwrzshj);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else if("0".equals(parm[2])){//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "；";
					}
				}else{
					if(!"".equals(jgzw)){
						yrqm += jgzw + "，";
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
				yrqm = "("+yrqm+"；"+ymqm+")";
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
				yrjc = "("+yrjc+"；"+ymjc+")";
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
	 * 奖惩更新
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
    				//奖惩名称代码
    				String a1404b = map.get("a1404b")==null?"":map.get("a1404b").toString();
    				//奖惩名称
    				String a1404a = map.get("a1404a")==null?"":map.get("a1404a").toString();
    				//批准机关
    				String a1411a =map.get("a1411a")==null?"":map.get("a1411a").toString();
    				
    				//批准时间
    				String a1407 = map.get("a1407")==null?"":map.get("a1407").toString();
    				
	    				if(a1407!=null&&a1407.length()>=6){
	    					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
	    				}else{
	    					a1407 = "";
	    				}
	    				if(a1404b.startsWith("01") || a1404b.startsWith("1")){//奖
	    					
	    					if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
	    						desc.append("经"+a1411a+"批准，").append("荣获"+a1404a+"。");
	    					}else{
	    						desc.append("经"+a1411a+"批准，").append(a1404a+"。");
	    					}
	    					
	    				}else{//惩
	    					desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
	    				}
	    				if(!"".equals(a1407)){
	    					desc.insert(0, a1407);
	    				}
    			
    				//列表顺序是批准时间倒序，生成得奖惩情况的文字描述需要是批准时间正序
    				if(desc.toString().endsWith("；")){
    					desc.deleteCharAt(desc.length()-1).append("。");
    				}
    			}
    			a01.setA14z101(desc.toString());
    			sess.update(a01);
    			
			}else{
				String a14z101 = a01.getA14z101();
				if("".equals(a14z101)){
					a01.setA14z101("无");
					sess.update(a01);
					
				}
			}
            sess.flush();
	}catch (Exception e) {
		e.printStackTrace();
	}
	}

	/**
	 * chs 20180629	自定义表单 考核信息集（A15）保存考核结果至人员信息集A01
	 * @param a0000	人员主键
	 */
	public void saveA15ToA01(String a0000) {
		if (a0000==null || "".equals(a0000)) {
			return;
		}
		String a1527;
		List<HashMap<String, Object>> a15List = null;
		try {
			//检查该年度是否已录入
			String sql = "select * from a15 where a0000 = '" + a0000 + "'";		
			a15List = cq.getListBySQL(sql);
			// 显示考核结果的年数
			a1527 = a15List.get(0).get("a1527") == null ? "0" : a15List.get(0).get("a1527").toString();
			listAssociation(a1527, a0000);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * chs 20180629	自定义表单 考核信息集（A15）保存考核结果至人员信息集A01
	 * @param a1527	考核显示年数
	 * @param a0000	人员主键
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
				//考核年度
				String a1521 = a15.getA1521();
				//考核结果（称职、优秀等）代码
				String a1517 = a15.getA1517();
				//考核结果（称职、优秀等）名称
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				desc.append(a1521 + "年年度考核" + a1517Name + "；");
			}
			if(desc.length()>0){
				desc.replace(desc.length() - 1, desc.length(), "。");
			}
			a01.setA15z101(desc.toString());
		}else{
			String description = "无";
			a01.setA15z101(description);
		}
		sess.update(a01);
		CustomQueryBS.setA01(a0000);
		sess.flush();
		return;	
	}
	
}
