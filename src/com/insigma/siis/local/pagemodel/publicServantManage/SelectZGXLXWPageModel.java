package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class SelectZGXLXWPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	@PageEvent("initX")
	@Transaction
	public int initx() throws RadowException{
		String a0000=this.getRadow_parent_data();
		if(StringUtil.isEmpty(a0000)){
			a0000=this.getPageElement("subWinIdBussessId").getValue();
		}
		/*HBSession sess=HBUtil.getHBSession();
		String sql="select qrzxl,qrzxlxx,qrzxw,qrzxwxx,zzxl,zzxlxx,zzxw,zzxwxx from a01 where a0000='"+a0000+"'";
		Object[] objs=(Object[]) sess.createSQLQuery(sql).uniqueResult();
		if(objs!=null&&objs.length>0){
			String qrzxl=objs[0]==null?"":objs[0].toString();
			String qrzxlxx=objs[1]==null?"":objs[1].toString();
			String qrzxw=objs[2]==null?"":objs[2].toString();
			String qrzxwxx=objs[3]==null?"":objs[3].toString();
			String zzxl=objs[4]==null?"":objs[4].toString();
			String zzxlxx=objs[5]==null?"":objs[5].toString();
			String zzxw=objs[6]==null?"":objs[6].toString();
			String zzxwxx=objs[7]==null?"":objs[7].toString();
			String zgxl=objs[8]==null?"":objs[8].toString();
			String zgxw=objs[9]==null?"":objs[9].toString();
			this.getPageElement("qrzxl").setValue(qrzxl);
			this.getPageElement("qrzxlxx").setValue(qrzxlxx);
			this.getPageElement("qrzxw").setValue(qrzxw);
			this.getPageElement("qrzxwxx").setValue(qrzxwxx);
			this.getPageElement("zzxl").setValue(zzxl);
			this.getPageElement("zzxlxx").setValue(zzxlxx);
			this.getPageElement("zzxw").setValue(zzxw);
			this.getPageElement("zzxwxx").setValue(zzxwxx);	
		}*/
		setzgxlxw(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	public int save() throws RadowException, AppException{
		HBSession sess=HBUtil.getHBSession();
		String a0000=this.getRadow_parent_data();
		if(StringUtil.isEmpty(a0000)){
			a0000=this.getPageElement("subWinIdBussessId").getValue();
		}
		String qrzzgxl=this.getPageElement("qrzzgxl").getValue();
		String qrzzgxw=this.getPageElement("qrzzgxw").getValue();
		String zzzgxl=this.getPageElement("zzzgxl").getValue();
		String zzzgxw=this.getPageElement("zzzgxw").getValue();
		
	/*	if("0".equals(qrzzgxl)&&"0".equals(zzzgxl)){
			updatezgxl(a0000,sess);
		}
		if("0".equals(qrzzgxw)&&"0".equals(zzzgxw)){
			updatezgxw(a0000,sess);
		}
		updateA01zgxuelixuewei(a0000,sess);*/
		
		//this.closeCueWindow("selectzgxlxw");
		this.getExecuteSG().addExecuteCode("window.close();");
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('success')");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	private void updateA01zgxuelixuewei(String a0000, HBSession sess) throws AppException {

		String xl="";
		String xlxx="";
		String sql1 = "select a0814,a0824,a0801a from a08 where a0000='"+a0000+"' and a0834='1' and a0899='true'";  //以学历编码升序排列
         List<Object[]> list1 = sess.createSQLQuery(sql1).list();
				if(list1!=null&&list1.size()>0){
					Object[] xueli = list1.get(0);
					String yx_xl = xueli[0]==null?"":xueli[0].toString();//院系
					String zy_xl = xueli[1]==null?"":xueli[1].toString();//专业
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
					 xl = xueli[2]==null?"":xueli[2].toString();//学历 中文
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
			String sql="select a0814,a0824,a0901a from a08 where a0000='"+a0000+"' and a0835='1' and a0899='true'";
			List<Object[]> list = sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				Object[] xuewei=list.get(0);
				xw=xuewei[2]==null?"":xuewei[2].toString();
				String yx_xw=xuewei[0]==null?"":xuewei[0].toString();
				String zy_xw=xuewei[1]==null?"":xuewei[1].toString();
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
					Object[] xuewei2=list.get(1);
					String xw2=xuewei2[2]==null?"":xuewei2[2].toString();
					String yx_xw2=xuewei2[0]==null?"":xuewei2[0].toString();
					String zy_xw2=xuewei2[1]==null?"":xuewei2[1].toString();
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
		a01.setZgxw(xw);//学位
		a01.setZgxwxx(xwxx);
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
		this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01,"parent.parent"));

		
	}


	private void updatezgxl(String a0000,HBSession sess){
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
	}
	
	private void updatezgxw(String a0000,HBSession sess){
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
	}
	
	@PageEvent("close")
	@Transaction
	public int update() throws RadowException, AppException{
			HBSession sess=HBUtil.getHBSession();
			String a0000=this.getRadow_parent_data();
			if(StringUtil.isEmpty(a0000)){
				a0000=this.getPageElement("subWinIdBussessId").getValue();
			}
			String qrzzgxl=this.getPageElement("qrzzgxl").getValue();
			String qrzzgxw=this.getPageElement("qrzzgxw").getValue();
			String zzzgxl=this.getPageElement("zzzgxl").getValue();
			String zzzgxw=this.getPageElement("zzzgxw").getValue();
			String update ="update a08 set a0834='0',a0835='0'  where a0000='"+a0000+"' and a0899='true'";
			sess.createSQLQuery(update).executeUpdate();
			sess.flush();
			if("1".equals(qrzzgxl)){
				String sql=" update a08 set a0834='1' where a0831='1' and a0837='1' and a0000='"+a0000+"' and a0899='true'";
				sess.createSQLQuery(sql).executeUpdate();
			}
			if("1".equals(qrzzgxw)){
				String sql=" update a08 set a0835='1' where a0832='1' and a0000='"+a0000+"' and a0837='1' and a0899='true'";
				sess.createSQLQuery(sql).executeUpdate();
			}
			if("1".equals(zzzgxl)){
				String sql=" update a08 set a0834='1' where a0838='1' and a0000='"+a0000+"' and a0837='2' and a0899='true'";
				sess.createSQLQuery(sql).executeUpdate();
			}
			if("1".equals(zzzgxw)){
				String sql=" update a08 set a0835='1' where a0839='1' and a0000='"+a0000+"' and a0837='2' and a0899='true'";
				sess.createSQLQuery(sql).executeUpdate();
			}
			if("0".equals(qrzzgxl)&&"0".equals(zzzgxl)){
				updatezgxl(a0000,sess);
			}
			if("0".equals(qrzzgxw)&&"0".equals(zzzgxw)){
				updatezgxw(a0000,sess);
			}
			
		
			updateA01zgxuelixuewei(a0000,sess);
			//this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('degreesgrid.dogridquery')");
			sess.flush(); 
			//this.closeCueWindow("selectzgxlxw");
			//this.getExecuteSG().addExecuteCode("window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public void setzgxlxw(String a0000) throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		String sql="select a0801a,a0814,a0824,a0834 from a08 where a0000='"+a0000+"' and a0831='1' and a0899='true'";
		List<Object[]> list1=sess.createSQLQuery(sql).list();
		if(list1!=null&&list1.size()>0){
			Object[] qrzzgxl=list1.get(0);
			String qrzxl=qrzzgxl[0]==null?"":qrzzgxl[0].toString();
			this.getPageElement("qrzxl").setValue(qrzxl);
			String xuexiao=qrzzgxl[1]==null?"":qrzzgxl[1].toString();
			String zhuanye=qrzzgxl[2]==null?"":qrzzgxl[2].toString();
			this.getPageElement("qrzxlxx").setValue(xuexiao+zhuanye);
			String a0834=qrzzgxl[3]==null?"":qrzzgxl[3].toString();
			if(a0834.equals("1")){
				this.getPageElement("qrzzgxl").setValue("1");	
			}
		}
		
		String sql2="select a0901a,a0814,a0824,a0835 from a08 where a0000='"+a0000+"' and a0832='1' and a0899='true' order by a0901b";
		List<Object[]> list2=sess.createSQLQuery(sql2).list();
		if(list2!=null&&list2.size()>0){
			Object[] qrzzgxw=list2.get(0);
			String qrzxw=qrzzgxw[0]==null?"":qrzzgxw[0].toString();
			String xuexiao=qrzzgxw[1]==null?"":qrzzgxw[1].toString();
			String zhuanye=qrzzgxw[2]==null?"":qrzzgxw[2].toString();
			String a0835=qrzzgxw[3]==null?"":qrzzgxw[3].toString();
			String xxxx=xuexiao+zhuanye;
			if(list2.size()>1){
				Object[] qrzzgxw2=list2.get(1);
				String qrzxw2=qrzzgxw2[0]==null?"":qrzzgxw2[0].toString();
				String xuexiao2=qrzzgxw2[1]==null?"":qrzzgxw2[1].toString();
				String zhuanye2=qrzzgxw2[2]==null?"":qrzzgxw2[2].toString();
				String xxxx2=xuexiao2+zhuanye2;
				qrzxw+=","+qrzxw2;
				xxxx+=","+xxxx2;
				
			}
			this.getPageElement("qrzxw").setValue(qrzxw);
			this.getPageElement("qrzxwxx").setValue(xxxx);
			if(a0835.equals("1")){
				this.getPageElement("qrzzgxw").setValue("1");
			}
		}
		
		String sql3="select a0801a,a0814,a0824,a0834 from a08 where a0000='"+a0000+"' and a0838='1' and a0899='true'";
		List<Object[]> list3=sess.createSQLQuery(sql3).list();
		if(list3!=null&&list3.size()>0){
			Object[] zzzgxl=list3.get(0);
			String zzxl=zzzgxl[0]==null?"":zzzgxl[0].toString();
			this.getPageElement("zzxl").setValue(zzxl);
			String xuexiao=zzzgxl[1]==null?"":zzzgxl[1].toString();
			String zhuanye=zzzgxl[2]==null?"":zzzgxl[2].toString();
			this.getPageElement("zzxlxx").setValue(xuexiao+zhuanye);
			String a0834=zzzgxl[3]==null?"":zzzgxl[3].toString();
			if(a0834.equals("1")){
				this.getPageElement("zzzgxl").setValue("1");	
			}
		}
		
		String sql4="select a0901a,a0814,a0824,a0835 from a08 where a0000='"+a0000+"' and a0839='1' and a0899='true' order by a0901b";
		List<Object[]> list4=sess.createSQLQuery(sql4).list();
		if(list4!=null&&list4.size()>0){
			Object[] zzzgxw=list4.get(0);
			String zzxw=zzzgxw[0]==null?"":zzzgxw[0].toString();
			String xuexiao=zzzgxw[1]==null?"":zzzgxw[1].toString();
			String zhuanye=zzzgxw[2]==null?"":zzzgxw[2].toString();
			String a0835=zzzgxw[3]==null?"":zzzgxw[3].toString();
			String xxxx=xuexiao+zhuanye+"专业";
			if(list4.size()>1){
				Object[] zzzgxw2=list4.get(1);
				String zzxw2=zzzgxw2[0]==null?"":zzzgxw2[0].toString();
				String xuexiao2=zzzgxw2[1]==null?"":zzzgxw2[1].toString();
				String zhuanye2=zzzgxw2[2]==null?"":zzzgxw2[2].toString();
				String xxxx2=xuexiao2+zhuanye2;
				zzxw+=","+zzxw2;
				xxxx+=","+xxxx2;
				
			}
			this.getPageElement("zzxw").setValue(zzxw);
			this.getPageElement("zzxwxx").setValue(xxxx);
			if(a0835.equals("1")){
				this.getPageElement("zzzgxw").setValue("1");
			}
		
		}
	}
}
