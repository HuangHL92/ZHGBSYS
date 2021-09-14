package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * 学历学位新增修改页面
 * @author Administrator
 *
 */
public class DegreesAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();
//		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//a01中的学位学历
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			this.setNextEventName("degreesgrid.dogridquery");
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	
	@PageEvent("saveA08")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveDegrees()throws RadowException, AppException{
		A08 a08 = new A08();
		this.copyElementsValueToObj(a08, this);
		String a0800 = this.getPageElement("a0800").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
//		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//学历学位不能同时为空
		String a0801b = a08.getA0801b();//学历
		String a0901b = a08.getA0901b();//学位
		if((a0801b==null||"".equals(a0801b))&&(a0901b==null||"".equals(a0901b))){
			this.setMainMessage("学历学位不能同时为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//毕业时间不能早于早于入学时间
				String a0807 = a08.getA0807();//毕业时间
				String a0804 = a08.getA0804();//入学时间
				String a0904 = a08.getA0904();//学位授予时间
				if(a0807!=null&&!"".equals(a0807)&&a0804!=null&&!"".equals(a0804)){
					if(a0807.length()==6){
						a0807 += "00";
					}
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0807.compareTo(a0804)<0){
						this.setMainMessage("毕业时间不能早于入学时间");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0804!=null&&!"".equals(a0804)&&a0904!=null&&!"".equals(a0904)){
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0904.length()==6){
						a0904 += "00";
					}
					if(a0904.compareTo(a0804)<0){
						this.setMainMessage("学位授予时间不能早于入学时间");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
		a08.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//入学时间、毕业时间、学位授予时间可以早于出生年月并保存。
			String a0107 = a01.getA0107();//出生年月
			if(a0107!=null&&!"".equals(a0107)){
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0807!=null&&!"".equals(a0807)){
					if(a0807.length()==6){
						a0807 += "00";
					}
					if(a0807.compareTo(a0107)<0){
						this.setMainMessage("毕业时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0804!=null&&!"".equals(a0804)){
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0804.compareTo(a0107)<0){
						this.setMainMessage("入学时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0904!=null&&!"".equals(a0904)){
					if(a0904.length()==6){
						a0904 += "00";
					}
					if(a0904.compareTo(a0107)<0){
						this.setMainMessage("学位授予时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			
			
			
			if(a0800==null||"".equals(a0800)){
				a08.setA0899("false");//是否输出
				
				
				applog.createLog("3081", "A08", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A08(), a08));
				
				sess.save(a08);	
			}else{
				if(a08.getA0899()==null||"".equals(a08.getA0899())){
					a08.setA0899("false");//是否输出
				}
				
				A08 a08_old = (A08)sess.get(A08.class, a0800);
				applog.createLog("3082", "A08", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a08_old, a08));
				PropertyUtils.copyProperties(a08_old, a08);
				
				sess.update(a08_old);
				//如果修改了重新计算最高学历和输出信息
				/*updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
				updateZGQRZXueliXuewei(a0000,sess);
				updateXueliXuewei(a0000,sess,"1");
				updateXueliXuewei(a0000,sess,"2");
				*/
			}	
			sess.flush();
			
			printout(a0000,sess,"1");// 设置最高学历学位输出 
			printout(a0000,sess,"2");
			String sql="update a08 set a0831='',a0832='',a0834='',a0835='',a0838='',a0839='' where a0000='"+a0000+"' and a0899='false'";
			sess.createSQLQuery(sql).executeUpdate();
			updateZGQRZXueliXuewei(a0000,sess);
			updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
			updateXueliXuewei(a0000,sess,"1");
			updateXueliXuewei(a0000,sess,"2");
			//判断全日制最高学历学位和在职最高学历学位是否相同等级,如果同等,弹窗让用户选择哪个是不分全日制和在职的最高学历
			boolean isSame=isSame(a0000,sess);
			if(isSame){
				this.setRadow_parent_data(a0000);
				this.getPageElement("a0800").setValue(a08.getA0800());//保存成功将id返回到页面上。
				this.getExecuteSG().addExecuteCode("open('"+a0000+"')");
				this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				updateA01ZGxuelixuewei(a0000, sess);
			}
			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getPageElement("a0800").setValue(a08.getA0800());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('degreesgrid').getStore().reload()");//刷新学历学位列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String closeCueWindowEX(){
		return "window.parent.Ext.WindowMgr.getActive().hide();";
	}
	
/*	private void updateZGXueliXuewei(String a0000, HBSession sess) {
		String sql = "from A08 where a0000='"+a0000+"'";//全日制 或 在职 1 2
		List<A08> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
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
			for(int i=0;i<list.size();i++){
				if(i==0){
					list.get(i).setA0834("1");
				}else{
					list.get(i).setA0834("0");
				}
				sess.update(list.get(i));
			}
			Collections.sort(list,new Comparator<A08>(){//学位排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0901b_1 = o1.getA0901b();//学位代码
					String a0901b_2 = o2.getA0901b();
					if(a0901b_1==null||"".equals(a0901b_1)){
						return 1;
					}
					if(a0901b_2==null||"".equals(a0901b_2)){
						return -1;
					}
					return a0901b_1.compareTo(a0901b_2);
				}
				
			});
			for(int i=0;i<list.size();i++){
				if(i==0){
					list.get(i).setA0835("1");
				}else{
					list.get(i).setA0835("0");
				}
				sess.update(list.get(i));
			}
			
		}
	}



	private void updateXueliXuewei(String a0000, HBSession sess,String a0837,boolean checked) throws AppException {
		//全日制学位学历
		A08 xueli = null;
		A08 xuewei = null;
		//学历排序
		String sortsql = "from Codevaluesort where codetype='ZB64'";
		List<Codevaluesort> sortlist = sess.createQuery(sortsql).list();
		final Map<String,String> sortmap = new HashMap<String, String>();
		if(sortlist!=null&&sortlist.size()>0){
			for(Codevaluesort cvs : sortlist){
				sortmap.put(cvs.getCodevalue(), cvs.getSortvalue());
			}
		}
		
		String sql = "from A08 where a0000='"+a0000+"' and a0837='"+a0837+"'";//全日制 或 在职 1 2
		if(checked){
			sql = sql + " and a0899='true'";
		}
		List<A08> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
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
			xueli = list.get(0);
			Collections.sort(list,new Comparator<A08>(){//学位排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0901b_1 = o1.getA0901b();//学位代码
					String a0901b_2 = o2.getA0901b();
					if(a0901b_1==null||"".equals(a0901b_1)){
						return 1;
					}
					if(a0901b_2==null||"".equals(a0901b_2)){
						return -1;
					}
					return a0901b_1.compareTo(a0901b_2);
				}
				
			});
			xuewei = list.get(0);
		}
		//xueli  xuewei不存在 同时为null。 
		//如果为空
		if(xueli==null||xuewei==null){
			xueli = new A08();
			xuewei = new A08();
		}else{
			if(!checked){
				xueli.setA0899("true");//更新输出
				xuewei.setA0899("true");
				sess.update(xueli);
				sess.update(xuewei);
				HBUtil.executeUpdate("update a08 set a0899='false' where a0000='"+a0000+"' and" +
						" a0837='"+a0837+"' and a0800!='"+xueli.getA0800()+"' and a0800!='"+xuewei.getA0800()+"'");
				sess.flush();
			}
		}
		
		//更新a01
		updateA01(xueli,xuewei,sess,a0837,a0000);
	}


	
	
	private void updateA01(A08 xueli, A08 xuewei, HBSession sess,String a0837,String a0000) {//a0837教育类别
				
		
		//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		String yx_xl = xueli.getA0814();//院系
		String zy_xl = xueli.getA0824();//专业
		
		String yx_xw = xuewei.getA0814();//院系
		String zy_xw = xuewei.getA0824();//专业
		if(yx_xl==null){
			yx_xl = "";
		}
		if(zy_xl==null){
			zy_xl = "";
		}
		if(yx_xw==null){
			yx_xw = "";
		}
		if(zy_xw==null){
			zy_xw = "";
		}
		if(!"".equals(zy_xl)){
			zy_xl += "专业";
		}
		if(!"".equals(zy_xw)){
			zy_xw += "专业";
		}
		String xl = xueli.getA0801a();
		String xw = xuewei.getA0901a();
		String xlxx = yx_xl+zy_xl;
		String xwxx = yx_xw+zy_xw;
		if(xl==null||"".equals(xl)){
			xlxx = null;
		}
		if(xw==null||"".equals(xw)){
			xwxx = null;
		}
		if("1".equals(a0837)){//全日制
			a01.setQrzxl(xl);//学历
			a01.setQrzxw(xw);//学位
			a01.setQrzxlxx(xlxx);
			a01.setQrzxwxx(xwxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
		}else{//在职
			a01.setZzxl(xl);
			a01.setZzxw(xw);
			a01.setZzxlxx(xlxx);
			a01.setZzxwxx(xwxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxw').value='"+(a01.getZzxw()==null?"":a01.getZzxw())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxwxx').value='"+(a01.getZzxwxx()==null?"":a01.getZzxwxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}



	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}*/
/***********************************************学位学历(a08)
 * @throws AppException *********************************************************************/
	
	private void updateZGXueliXuewei(String a0000, HBSession sess) throws AppException {
		String update ="update a08 set a0834='0',a0835='0'  where a0000='"+a0000+"' and a0899='true'";
		sess.createSQLQuery(update).executeUpdate();
		sess.flush();
		String sql="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='1' and a0831='1' and a0899='true'";
		String sql2="select a0800,a0801b from a08 where a0000='"+a0000+"' and a0837='2' and a0838='1' and a0899='true'";
		/*List<A08> list3= sess.createSQLQuery(sql).addEntity(A08.class).list();
		List<A08> list4=  sess.createSQLQuery(sql2).addEntity(A08.class).list();*/
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
		/*String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true'";//只显示 输出的最高学历 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
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
				if(list1!=null&&list1.size()==1){
					A08 a08=list1.get(0);
					String xuelidaima=a08.getA0801b();
					if(!StringUtil.isEmpty(xuelidaima)){
						a08.setA0834("1");
					}else{
						a08.setA0834("0");
					}
					sess.update(a08);
				}
				
				//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码不会与第一条一样 ,一样已经在issame中判断了
						if(list1!=null&&list1.size()>1){
							A08 a08=list1.get(0);
							String xuelidaima=a08.getA0801b();
							if(!StringUtil.isEmpty(xuelidaima)){
								a08.setA0834("1");
								sess.update(a08);
								for(int i=1;i<list1.size();i++){
									A08 a08_x=list1.get(i);
									String xuelidaima_x=a08_x.getA0801b();
									String duibi=xuelidaima.substring(0,1);
									if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
										a08_x.setA0834("1");
									}else{
										a08_x.setA0834("0");
									}
									sess.update(a08_x);
								}
								for(int i=1;i<list1.size();i++){
									A08 a08_x=list1.get(i);
									a08_x.setA0834("0");
									sess.update(a08_x);
								}
							}else{
								for(int i=0;i<list1.size();i++){
									A08 a08_x=list1.get(i);
									a08_x.setA0834("0");
									sess.update(a08_x);
								}
							}
						}
			String sql2="select * from a08 where a0000='"+a0000+"' and a0899='true' and length(a0901b)>0 order by a0901b asc";
			List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
						//如果只有一条学位信息,如果学位代码不为空就是最高学位
						if(list2!=null&& list2.size()==1){
							A08 a08=list2.get(0);
							String xueweidaima=a08.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima)){
								 a08.setA0835("1");
							}else{
								a08.setA0835("0");
							}
							sess.update(a08);
						}
						//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码不会与第一条一样,一样的已经在issame中判断了
						if(list2!=null&&list2.size()>1){
							A08 a08_1=list2.get(0);
							String xueweidaima=a08_1.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima)){
								a08_1.setA0835("1");
								sess.update(a08_1);
								

								if(xueweidaima.startsWith("1")){
									for(int i=1;i<list2.size();i++){
										A08 a08_x=list2.get(i);
										String xueweidaima_x=a08_x.getA0901b();
										if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
											a08_x.setA0835("1");
										}else{
											a08_x.setA0835("0");
										}
										sess.update(a08_x);
									}
								}else{
									String reg=xueweidaima.substring(0,1);
										for(int i=1;i<list2.size();i++){
											A08 a08_x=list2.get(i);
											String xueweidaima_x=a08_x.getA0901b();
											if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
												a08_x.setA0835("1");
											}else{
												a08_x.setA0835("0");
											}
											sess.update(a08_x);
										}
									
								}
							
							}else{
								for(int i=0;i<list2.size();i++){
									A08 a08_x=list2.get(i);
									a08_x.setA0835("0");
									sess.update(a08_x);
								}
							}
							
						}*/

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
		/*//如果只有一条学历信息,如果学历代码不为空就是最高学历
		if(list1!=null&&list1.size()==1){
			for(int i=0;i<list1.size();i++){
				if(i==0){
					list1.get(i).setA0838("1"); 
				}else{
					list1.get(i).setA0838("0");
				}
				sess.update(list1.get(i));
			}
			//xl = list1.get(0);
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0838("1");
			}
			sess.update(a08);
		}*/
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
		if(list1!=null&&list1.size()>0){
			Object[] a08=list1.get(0);
			String xuelidaima=a08[1]==null?"":a08[1].toString();
			if(!StringUtil.isEmpty(xuelidaima)){
				sess.createSQLQuery(" update a08 set a0838='1' where a0800='"+a08[0]+"'").executeUpdate();
				/*for(int i=1;i<list1.size();i++){
					A08 a08_x=list1.get(i);
					String xuelidaima_x=a08_x.getA0801b();
					String duibi=xuelidaima.substring(0,1);
					if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
						a08_x.setA0838("1");
					}else{
						a08_x.setA0838("0");
					}
					sess.update(a08_x);
				}*/
			
			}
		}
/*		if(xl!=null){
			if(xl.getA0901a()!=null && !"".equals(xl.getA0901a())){//最高学历，他的学位也最高
				xl.setA0839("1");
				sess.update(xl);
			}else{
				String sql2 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2' order by to_number(a0901b) asc";//输出的最高学位 
				List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
				if(list2!=null && list2.size()>0){
					for(int i=0;i<list2.size();i++){
						if(i==0){
							list2.get(i).setA0839("1");
						}else{
							list2.get(i).setA0839("0");
						}
						sess.update(list2.get(i));
					}
				}
			}
		}*/
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		//如果只有一条学位信息,如果学位代码不为空就是最高学位
	/*	if(list2!=null&& list2.size()==1){
			A08 a08=list2.get(0);
			String xueweidaima=a08.getA0901b();
			if(!StringUtil.isEmpty(xueweidaima)){
				 a08.setA0839("1");
			}
			sess.update(a08);
		}*/
		//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
	/*	//如果只有一条学历信息,如果学历代码不为空就是最高学历
		if(list1!=null&&list1.size()==1){
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0831("1");
			}
			sess.update(a08);
		}
		*/
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也不处理
				if(list1!=null&&list1.size()>0){
					Object[] a08=list1.get(0);
					String xuelidaima=a08[1]==null?"":a08[1].toString();
					if(!StringUtil.isEmpty(xuelidaima)){
						sess.createSQLQuery(" update a08 set a0831='1' where a0800='"+a08[0]+"'").executeUpdate();
						/*for(int i=1;i<list1.size();i++){
							A08 a08_x=list1.get(i);
							String xuelidaima_x=a08_x.getA0801b();
							String duibi=xuelidaima.substring(0,1);
							if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
								a08_x.setA0831("1");
							}else{
								a08_x.setA0831("0");
							}
							sess.update(a08_x);
						}*/
					
					}
				}
				
		String sql2="select a0800,a0901b from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1' and length(a0901b)>0 order by a0901b asc";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
				/*//如果只有一条学位信息,如果学位代码不为空就是最高学位
				if(list2!=null&& list2.size()==1){
					A08 a08=list2.get(0);
					String xueweidaima=a08.getA0901b();
					if(!StringUtil.isEmpty(xueweidaima)){
						 a08.setA0832("1");
					}
					sess.update(a08);
				}*/
				//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
		/*A08 xl = null;
		if(list1!=null&&list1.size()>0){
			for(int i=0;i<list1.size();i++){
				if(i==0){
					list1.get(i).setA0831("1");//
				}else{
					list1.get(i).setA0831("0");
				}
				list1.get(i).setA0832("0");//现将所有查到的学位设置为0,后面判断把最高学位的变成1
				sess.update(list1.get(i));
			}
			xl = list1.get(0);
		}		
		
		if(xl!=null){
			if(xl.getA0901a()!=null && !"".equals(xl.getA0901a())){//最高学历，他的学位也最高
				xl.setA0832("1");
				sess.update(xl);
			}else{
				String sql2 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1' order by to_number(a0901b) asc";//输出的最高全日制学位 
				List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
				if(list2!=null && list2.size()>0){
					for(int i=0;i<list2.size();i++){
						if(i==0){
							list2.get(i).setA0832("1");
						}else{
							list2.get(i).setA0832("0");
						}
						sess.update(list2.get(i));
					}
				}
			}
		}*/
	}
	
	@SuppressWarnings("unchecked")
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
					
					
					if(!"".equals(zy_xl)){
						
						//将专业中的“专业”先清除
						zy_xl = zy_xl.replace("专业", "");
						
						zy_xl += "专业";
					}
					 xl = xueli.getA0801a();//学历 中文
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		
		/*String a0901a_xl = xueli.getA0901b();
		if(a0901a_xl != null && !"".equals(a0901a_xl)){//最高学历 存在 学位，则此学位一定为最高学位
			checkXW(true,xueli,sess,a0837,a0000);
		}else{//最高学历不 存在 学位
			checkXW(false,xueli,sess,a0837,a0000);
		}*/
	
		if("1".equals(a0837)){//全日制
			a01.setQrzxl(xl);//学历
			a01.setQrzxlxx(xlxx);
//			//人员基本信息界面
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
		}else{//在职
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
//			//人员基本信息界面
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
		//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01));
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
			if(!"".equals(zy_xw)){
				//将专业中的“专业”先清除
				zy_xw = zy_xw.replace("专业", "");
				
				zy_xw += "专业";
			}
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
				if(!"".equals(zy_xw2)){
					
					//将专业中的“专业”先清除
					zy_xw2 = zy_xw2.replace("专业", "");
					
					zy_xw2 += "专业";
				}
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
			//人员基本信息界面
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
		}else{//在职
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
			//人员基本信息界面
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxw').value='"+(a01.getZzxw()==null?"":a01.getZzxw())+"'");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('zzxwxx').value='"+(a01.getZzxwxx()==null?"":a01.getZzxwxx())+"'");
		}
		sess.update(a01);
		sess.flush();
		CustomQueryBS.setA01(a0000);
		//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01));
	}


	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}
	
/*********************************************************************************************************************************/	
	
	/**
	 * 学位学历列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.dogridquery")
	@NoRequiredValidate
	public int degreesgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
//		String a0000 = this.getRadow_parent_data();
		String sql = "select * from A08 where a0000='"+a0000+"' order by a0837,a0801b";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 学位学历新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesAddBtn.onclick")
	@NoRequiredValidate
	public int opendegreesWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("a0000").getValue();
//		String a0000 = this.getRadow_parent_data();
		
		String a0800 = this.getPageElement("a0800").getValue();
		String a0837 = this.getPageElement("a0837").getValue();//教育类别
		String a0801b = this.getPageElement("a0801b").getValue();//学历
		String a0901b = this.getPageElement("a0901b").getValue();//学位
		if(a0800==null||"".equals(a0800)){//新增提示是否保存当前信息。
			if(a0837!=null&&!"".equals(a0837)){
				if((a0801b!=null&&!"".equals(a0801b))||(a0901b!=null&&!"".equals(a0901b))){
					this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否保存当前新增信息?',200,function(id){" +
							"if(id=='ok'){" +
								"saveDegree();	" +
								"}else if(id=='cancel'){" +
								"	radow.doEvent('clearCondition');" +
								"}" +
							"});");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
		}
		
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		clearCondition();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空 条件列表
	 * @throws RadowException 
	 */
	@PageEvent("clearCondition")
	@NoRequiredValidate
	public void clearCondition() throws RadowException{
		A08 a08 = new A08();
		PMPropertyCopyUtil.copyObjValueToElement(a08, this);
	}
	
	/**
	 * 修改学位学历修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int degreesgridOnRowClick() throws RadowException{ 
		//this.openWindow("DegreesAddPage", "pages.publicServantManage.DegreesAddPage");
		//获取选中行index
		int index = this.getPageElement("degreesgrid").getCueRowIndex();
		String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出设置	
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgridchecked")
	@Transaction
	@NoRequiredValidate
	public int degreesgridChecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0800 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("").getValue();
//		String a0000 = this.getRadow_parent_data();
		try{
			if(a0800!=null){
				HBSession sess = HBUtil.getHBSession();
				A08 a08 = (A08)sess.get(A08.class, a0800);
				Boolean checked = Boolean.valueOf(a08.getA0899());
				a08.setA0899(String.valueOf(!checked));
				if("false".equals(a08.getA0899())){
					a08.setA0831("");
					a08.setA0832("");
					a08.setA0834("");
					a08.setA0835("");
					a08.setA0838("");
					a08.setA0839("");
				}
				sess.save(a08);
				sess.flush();
				PMPropertyCopyUtil.copyObjValueToElement(a08, this);
				//updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a0000,sess);//最高全日制学历学位标志

				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
				}
				updateXueliXuewei(a0000, sess, a0837);
				//判断全日制最高学历学位和在职最高学历学位是否相同等级,如果同等,弹窗让用户选择哪个是不分全日制和在职的最高学历
				boolean isSame=isSame(a08.getA0000(),sess);
				if(isSame){
					//this.setRadow_parent_data(a08.getA0000());
					this.getExecuteSG().addExecuteCode("open('"+a0000+"')");
					this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");//刷新列表
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					updateZGXueliXuewei(a08.getA0000(),sess);//最高学历学位标志
					updateA01ZGxuelixuewei(a08.getA0000(), sess);
				}
				
				/*//将A01的最高学历（zgxl），最高学位（zgxw）字段更新
				
				String zgxl = "";
				//查询出最高学历（只有一条记录）
				
				List<Object> zgxlObjectList = HBUtil.getHBSession().createSQLQuery("select A0801A from a08 where a0834 = '1' and a0000 = '"+a0000+"'").list();
				
				Object zgxlObject = null;
				if(zgxlObjectList.size() > 0){
					zgxlObject = zgxlObjectList.get(0);
				}
				
				if(zgxlObject != null && !zgxlObject.equals("")){
					zgxl = zgxlObject.toString();
				}
				HBUtil.executeUpdate("update a01 set zgxl='"+ zgxl +"' where a0000='"+a0000+"'");		//更新最高学历：zgxl
				
				
				String zgxw = "";
				//查询出最高学位（可能为两条，双学位）
				String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a0000+"'";
				
				List<Object> zgxwS = sess.createSQLQuery(zgxwSQL).list();
				
				if(zgxwS.size() > 0){
					
					for (int i = 0; i < zgxwS.size(); i++) {
						zgxw = zgxw + zgxwS.get(i).toString()+ ",";
					}
					
					zgxw = zgxw.substring(0,zgxw.length()-1);
				}
				
				HBUtil.executeUpdate("update a01 set zgxw='"+ zgxw +"' where a0000='"+a0000+"'");		//更新最高学位：zgxw
				
				
				//将A01的最高学历学校（zgxlxx），最高学位学校（zgxwxx）字段更新
				String zgxlxx = "";
				
				//查询出最高学历学校（只有一条记录）
				List<Object> zgxlxxObjectList = HBUtil.getHBSession().createSQLQuery("select A0801A from a08 where a0834 = '1' and a0000 = '"+a0000+"'").list();
				
				Object zgxlxxObject = null;
				if(zgxlxxObjectList.size() > 0){
					zgxlxxObject = zgxlxxObjectList.get(0);
				}
				
				if(zgxlxxObject != null && !zgxlxxObject.equals("")){
					zgxlxx = zgxlxxObject.toString();
				}
				
				//查询出最高学历专业名称（只有一条记录）
				List<Object> zgxlzyObjectList = HBUtil.getHBSession().createSQLQuery("select A0824 from a08 where a0834 = '1' and a0000 = '"+a0000+"'").list();
				
				Object zgxlzyObject = null;
				if(zgxlzyObjectList.size() > 0){
					zgxlzyObject = zgxlzyObjectList.get(0);
				}
				
				if(zgxlzyObject != null && !zgxlzyObject.equals("")){
					zgxlxx = zgxlxx + zgxlzyObject.toString();
				}
				
				
				HBUtil.executeUpdate("update a01 set zgxlxx='"+ zgxlxx +"' where a0000='"+a0000+"'");		//更新最高学历学校：zgxlxx
				
				
				String zgxwxx = "";
				//查询出最高学位学校（可能为两条，双学位）
				String zgxwxxSQL = "select A0814,A0824 from A08 where A0835 = '1' and a0000 = '"+a0000+"'";
				
				List<Object[]> zgxwxxS = sess.createSQLQuery(zgxwxxSQL).list();
				
				if(zgxwxxS.size() > 0){
					
					for (int i = 0; i < zgxwxxS.size(); i++) {
						
						if(zgxwxxS.get(i)[0] != null && !zgxwxxS.get(i)[0].equals("") && zgxwxxS.get(i)[1] != null && !zgxwxxS.get(i)[1].equals("")){
							zgxwxx = zgxwxx + zgxwxxS.get(i)[0].toString()+ zgxwxxS.get(i)[1].toString()+",";
						}
						
					}
					
					if(zgxwxx != null && !zgxwxx.equals("")){
						zgxwxx = zgxwxx.substring(0,zgxwxx.length()-1);
					}
					
				}
				
				HBUtil.executeUpdate("update a01 set zgxwxx='"+ zgxwxx +"' where a0000='"+a0000+"'");		//更新最高学位学校：zgxwxx
				*/
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");//刷新列表
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	public int deleteRow(String a0800)throws RadowException, AppException{
		//Map map = this.getRequestParamer();
		//int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		//String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			
			A01 a01 = (A01)sess.get(A01.class, a08.getA0000());
			applog.createLog("3083", "A08", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A08(), new A08()));
			sess.delete(a08);
			sess.flush();
			Boolean checked = Boolean.valueOf(a08.getA0899());				
			//checked 如果为true 数据是被输出的。  修改A01 否则不做更改
			if(checked){
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a08.getA0000(),sess);//最高全日制学历学位标志	
				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a08.getA0000(),sess);//最高在职学历学位标志
				}
				updateXueliXuewei(a08.getA0000(), sess, a0837);
				//判断全日制最高学历学位和在职最高学历学位是否相同等级,如果同等,弹窗让用户选择哪个是不分全日制和在职的最高学历
				boolean isSame=isSame(a08.getA0000(),sess);
				if(isSame){
					//this.setRadow_parent_data(a08.getA0000());
					this.getExecuteSG().addExecuteCode("open('"+a08.getA0000()+"')");
					a08 = new A08();
					PMPropertyCopyUtil.copyObjValueToElement(a08, this);
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					updateZGXueliXuewei(a08.getA0000(),sess);//最高学历学位标志
					updateA01ZGxuelixuewei(a08.getA0000(), sess);
				}
				
				/*//将A01的最高学历（zgxl），最高学位（zgxw）字段更新
				String zgxl = "";
				//查询出最高学历（只取一条记录）
				
				List<Object> zgxlObjectList = HBUtil.getHBSession().createSQLQuery("select A0801A from a08 where a0834 = '1' and a0000 = '"+a08.getA0000()+"'").list();
				
				Object zgxlObject = null;
				if(zgxlObjectList.size() > 0){
					zgxlObject = zgxlObjectList.get(0);
				}
				
				if(zgxlObject != null && !zgxlObject.equals("")){
					zgxl = zgxlObject.toString();
				}
				HBUtil.executeUpdate("update a01 set zgxl='"+ zgxl +"' where a0000='"+a08.getA0000()+"'");		//更新最高学历：zgxl
				
				
				String zgxw = "";
				//查询出最高学位（可能为两条，双学位）
				String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a08.getA0000()+"'";
				
				List<Object> zgxwS = sess.createSQLQuery(zgxwSQL).list();
				
				if(zgxwS.size() > 0){
					
					for (int i = 0; i < zgxwS.size(); i++) {
						zgxw = zgxw + zgxwS.get(i).toString()+ ",";
					}
					
					zgxw = zgxw.substring(0,zgxw.length()-1);
				}
				
				HBUtil.executeUpdate("update a01 set zgxw='"+ zgxw +"' where a0000='"+a08.getA0000()+"'");		//更新最高学位：zgxw
				
				
				//将A01的最高学历学校（zgxlxx），最高学位学校（zgxwxx）字段更新
				String zgxlxx = "";
				
				//查询出最高学历学校（只有一条记录）
				List<Object> zgxlxxObjectList = HBUtil.getHBSession().createSQLQuery("select A0801A from a08 where a0834 = '1' and a0000 = '"+a08.getA0000()+"'").list();
				
				Object zgxlxxObject = null;
				if(zgxlxxObjectList.size() > 0){
					zgxlxxObject = zgxlxxObjectList.get(0);
				}
				
				if(zgxlxxObject != null && !zgxlxxObject.equals("")){
					zgxlxx = zgxlxxObject.toString();
				}
				
				//查询出最高学历专业名称（只有一条记录）
				List<Object> zgxlzyObjectList = HBUtil.getHBSession().createSQLQuery("select A0824 from a08 where a0834 = '1' and a0000 = '"+a08.getA0000()+"'").list();
				
				Object zgxlzyObject = null;
				if(zgxlzyObjectList.size() > 0){
					zgxlzyObject = zgxlzyObjectList.get(0);
				}
				
				if(zgxlzyObject != null && !zgxlzyObject.equals("")){
					zgxlxx = zgxlxx + zgxlzyObject.toString();
				}
				
				
				HBUtil.executeUpdate("update a01 set zgxlxx='"+ zgxlxx +"' where a0000='"+a08.getA0000()+"'");		//更新最高学历学校：zgxlxx
				
				
				String zgxwxx = "";
				//查询出最高学位学校（可能为两条，双学位）
				String zgxwxxSQL = "select A0814,A0824 from A08 where A0835 = '1' and a0000 = '"+a08.getA0000()+"'";
				
				List<Object[]> zgxwxxS = sess.createSQLQuery(zgxwxxSQL).list();
				
				if(zgxwxxS.size() > 0){
					
					for (int i = 0; i < zgxwxxS.size(); i++) {
						
						if(zgxwxxS.get(i)[0] != null && !zgxwxxS.get(i)[0].equals("") && zgxwxxS.get(i)[1] != null && !zgxwxxS.get(i)[1].equals("")){
							zgxwxx = zgxwxx + zgxwxxS.get(i)[0].toString()+ zgxwxxS.get(i)[1].toString()+",";
						}
						
					}
					
					if(zgxwxx != null && !zgxwxx.equals("")){
						zgxwxx = zgxwxx.substring(0,zgxwxx.length()-1);
					}
					
				}
				
				HBUtil.executeUpdate("update a01 set zgxwxx='"+ zgxwxx +"' where a0000='"+a08.getA0000()+"'");		//更新最高学位学校：zgxwxx
				*/
				
				
			}
			this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
			a08 = new A08();
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/*@PageEvent("printoutBtn.onclick")*/
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
	
	public boolean isSame(String a0000,HBSession sess){
		String sql="select a0801b from a08 where a0000='"+a0000+"' and a0837='1' and a0831='1' and a0899='true'";
		String sql2="select a0801b from a08 where a0000='"+a0000+"' and a0837='2' and a0838='1' and a0899='true'";
		Object  qrzxueli=sess.createSQLQuery(sql).uniqueResult();
		Object  zzxueli=sess.createSQLQuery(sql2).uniqueResult();
		if(qrzxueli!=null&&zzxueli!=null){
			String qrzxl=qrzxueli.toString().substring(0, 1);
			String zzxl=zzxueli.toString().substring(0, 1);
			if(qrzxl.equals(zzxl)){
				return true;
			}
		}
		
		
		String sql3="select a0901b from a08 where a0000='"+a0000+"' and a0837='1' and a0832='1' and a0899='true'";
		String sql4="select a0901b from a08 where a0000='"+a0000+"' and a0837='2' and a0839='1' and a0899='true'";
		List<Object> list1=sess.createSQLQuery(sql3).list();
		List<Object> list2=sess.createSQLQuery(sql4).list();
		if(list1!=null&&list1.size()>0&&list2!=null&&list2.size()>0){
			Object qrzxuewei=list1.get(0);
			Object zzxuewei=list2.get(0);
			if(qrzxuewei!=null&&zzxuewei!=null){
				String qrzxw=qrzxuewei.toString().substring(0, 1);
				String zzxw=zzxuewei.toString().substring(0, 1);
				if((qrzxw.equals("1")||qrzxw.equals("2"))&&(zzxw.equals("1")||zzxw.equals("2"))){
						return true;
				}else if(qrzxw.equals(zzxw)){
					return true;
				}
			}
		}
		return false;
	}
	
	public void updateA01ZGxuelixuewei(String a0000,HBSession sess) throws AppException{
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
							
							
							if(!"".equals(zy_xl)){
								
								//将专业中的“专业”先清除
								zy_xl = zy_xl.replace("专业", "");
								
								zy_xl += "专业";
							}
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
						if(!"".equals(zy_xw)){
							//将专业中的“专业”先清除
							zy_xw = zy_xw.replace("专业", "");
							
							zy_xw += "专业";
						}
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
							if(!"".equals(zy_xw2)){
								
								//将专业中的“专业”先清除
								zy_xw2 = zy_xw2.replace("专业", "");
								
								zy_xw2 += "专业";
							}
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
				//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01));
	}
	
	@PageEvent("success")
	public int success(){
		this.setMainMessage("保存成功！");
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sethighest.onclick")
	@NoRequiredValidate
	public int sethighest() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		this.getExecuteSG().addExecuteCode("open('"+a0000+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setA1701.onclick")
	@NoRequiredValidate
	public int setA1701() throws RadowException{
		String kssj="";
		String jssj="";
		HBSession sess = HBUtil.getHBSession();
		List<String> list1=sess.createSQLQuery("select a0000 from a01 where a0184 in (select a0184 from zarw)").list();
			for(String a0000: list1){
			String sql="select a0804,a0807,A0814 from a08 where  a0000='"+a0000+"' and A0837='1'";
			List<Object[]> list=sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				Object[] A08=list.get(0);
				String A0804=A08[0]==null?"":A08[0].toString();
				String A0807=A08[1]==null?"":A08[1].toString();
				String A0814=A08[2]==null?"":A08[2].toString();
				if(A0804.length()==4){
					A0804+="01";
				}
				if(A0807.length()==4){
					A0807+="01";
				}
				if("".equals(A0814)||A0814==null){
					continue;
				}
				A0814=A0814.trim();
				if("".equals(A0804)&&"".equals(A0807)){
					String sql4="select a0134 from a01 where a0000='"+a0000+"'";
					Object a0134=sess.createSQLQuery(sql4).uniqueResult();
					if("".equals(a0134)||a0134==null){
						continue;
					}
					String A0134=a0134.toString();
					if(A0134.length()==4){
						A0134+="01";
					}
					String nian=A0134.substring(0,4);
					int n=Integer.parseInt(nian)-4;
					String yue=A0134.substring(4, 6);
					kssj=n+"."+yue;
					jssj=A0134.substring(0, 4)+"."+A0134.substring(4, 6);
				}else
				if("".equals(A0804)&&!"".equals(A0807)){
					String nian=A0807.substring(0,4);
					int n=Integer.parseInt(nian)-4;
					String yue=A0807.substring(4, 6);
					kssj=n+"."+yue;
					jssj=A0807.substring(0, 4)+"."+A0807.substring(4, 6);
				}else
				if("".equals(A0807)&&!"".equals(A0804)){
					String nian=A0804.substring(0,4);
					int n=Integer.parseInt(nian)+4;
					String yue=A0804.substring(4, 6);
					kssj=A0804.substring(0, 4)+"."+A0804.substring(4, 6);
					jssj=n+"."+yue;
				}else{
					kssj=A0804.substring(0, 4)+"."+A0804.substring(4, 6);
					jssj=A0807.substring(0, 4)+"."+A0807.substring(4, 6);
				}
				
				
				String zjxx=kssj+"--"+jssj+"  "+A0814+"学习\r\n";
				System.out.println(zjxx);
				//String sql2="select A1701 from a01 where A0000='19860417-3522-0082-2201-190004142001'";
				String sql3="update A01 set A1701=concat('"+zjxx+"',A1701) where a0000='"+a0000+"'";
				sess.createSQLQuery(sql3).executeUpdate();
			  }
			}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
