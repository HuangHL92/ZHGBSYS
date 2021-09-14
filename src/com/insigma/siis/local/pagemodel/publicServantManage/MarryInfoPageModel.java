package com.insigma.siis.local.pagemodel.publicServantManage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.transform.Transformers;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.rxl.control.CountWordsSimilarity;

/**
   * 年度考核重复人员匹配
 */
public class MarryInfoPageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	// 页面初始化
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("marryGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("marryGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		
		
		String a0101=this.getPageElement("a0101").getValue();
		String sql="select a0000,a0101,a0192a from a01 where a0101='"+a0101+"' and a0163='1'";
		HBSession sess=HBUtil.getHBSession();
		List<Object[]> lists= sess.createSQLQuery(sql).list();
		
		
		CountWordsSimilarity cws = new CountWordsSimilarity();
		String a0192=this.getPageElement("a0192").getValue();
			HashMap<String, List<String>> mapReturn=new HashMap();
			List<String> list=new ArrayList<String>();
			List<Double> list2 = new ArrayList<Double>();
			String a0000 ="";
			String a0192a ="";
			for(Object[] obj:lists){
					System.out.println(obj[0].toString()+"---"+obj[1].toString());
					a0000=obj[0].toString();
					a0101=obj[1].toString();
					a0192a=obj[2].toString();
					//调用匹配算法计算相似度
					Double xsd=cws.countSimilarityBySegmentation(a0192,a0192a);
			        DecimalFormat df = new DecimalFormat("0.0%");//转换成百分数保留一位小数
			        System.out.println(xsd);
							 
							
					list.add(df.format(xsd));
//					list2.add(xsd);
			}
			mapReturn.put("xsd",list);//添加相似度
			
//			double max =list2.get(0);
//			for(int i=1;i<list2.size();i++) {
//				if(list2.get(i)>max) {
//					max=list2.get(i);
//				}
//			}
//			System.out.println("最大值为"+max);
		this.pageQuery(sql, "SQL", start,limit,mapReturn);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	   * 匹配人员信息
	 * 
	 */
	@PageEvent("marryInfo")
	public int marryInfo(String parameters) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		try {
		String[] strs=parameters.split("#@#");
		String id=strs[0];//失败id
		String a0000=strs[1];//人员主键
		String a1517=strs[2];//考核等次
		String a1521=strs[3];//年份
		String name=strs[4];
		List<String>  list2= sess.createSQLQuery("select a1500 from a15 where a0000='"+a0000+"' and a1521='"+a1521+"'").list();
		if(list2.size()>0) {
		String a1500=list2.get(0);
		A15 a15=(A15) sess.get(A15.class, a1500);
		A15 a15_old = new A15();
		PropertyUtils.copyProperties(a15_old, a15);
		a15.setA1517(a1517);
		//修改日志记录
		applog.createLog("3152", "A15", a0000, name, "修改记录", new Map2Temp().getLogInfo(a15_old, a15));
		sess.update(a15);
		}else {
			A15 a15=new A15();
			a15.setA0000(a0000);
			a15.setA1500(UUID.randomUUID().toString());
			a15.setA1521(a1521);
			a15.setA1517(a1517);
			a15.setA1527("3");
			sess.save(a15);
			//新增日志记录
			applog.createLog("3151", "A15", a0000, name, "新增记录", new Map2Temp().getLogInfo(new A15(), a15));
			
		}
		
		
		String sql="delete from a15report where id='"+id+"' ";
		HBUtil.executeUpdate(sql);
		} catch (Exception e) {
			sess.getTransaction().rollback();
			this.setMainMessage("匹配失败:"+e);
		}
		
		this.getExecuteSG().addExecuteCode("realParentDoEvent('memberGrid.dogridquery');");
		this.getExecuteSG().addExecuteCode("alert('匹配成功') ;collapseGroupWin();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("marryGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("marryGrid").getValue("a0000",this.getPageElement("marryGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			String rmbs=this.getPageElement("rmbs").getValue();
//			if(rmbs.contains(a0000)) {
//				this.setMainMessage("已经打开了");
//				return EventRtnType.FAILD;
//			}
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("onpenRmb('"+a0000+"')");
//			this.getExecuteSG().addExecuteCode("openZHGBRMB('"+a0000+"')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
}
