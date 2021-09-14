package com.insigma.siis.local.pagemodel.edu;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.EduXrdx;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

public class xrdxAddPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String xrdx00 = this.getPageElement("subWinIdBussessId2").getValue();
		try {
			Calendar  c = new  GregorianCalendar();
			int year = c.get(Calendar.YEAR);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<100;i++){
				map.put(""+(year-i), year-i);
				if((year-i)==2015) {
					break;
				}
			}
			((Combo)this.getPageElement("year")).setValueListForSelect(map); 
			
			
			if(xrdx00!=null&&!"".equals(xrdx00)){
				HBSession sess = HBUtil.getHBSession();
				EduXrdx edu=(EduXrdx)sess.get(EduXrdx.class, xrdx00);
				PMPropertyCopyUtil.copyObjValueToElement(edu, this);
				 this.getPageElement("xrdx05").setValue(formatLongToDate(edu.getXrdx05())); //开始日期
	             this.getPageElement("xrdx06").setValue(formatLongToDate(edu.getXrdx06())); //结束日期
	             this.getPageElement("xrdx00").setValue(xrdx00);
	             if(!"".equals(edu.getXrdx02())&& edu.getXrdx02()!=null) {
	            	@SuppressWarnings("unchecked")
	     			List<String> PXLXname= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+edu.getXrdx02()+"'  and code_type='PXLX'").list();
	     			if(PXLXname.size()>0 && PXLXname.get(0)!=null) {
	     				this.getPageElement("xrdx02_combotree").setValue(PXLXname.get(0));
	     			}
	             }
	             String xrdx02=edu.getXrdx02();
	             if("0203".equals(xrdx02)) {
	            	 this.getExecuteSG().addExecuteCode(" $('#xrdx09').removeAttr('readonly');");
	             }
//	             if("1".equals(edu.getType())) {
//	            	 this.getExecuteSG().addExecuteCode("$('#xrdx02').attr('readonly','true')");
//	             }
			}else {
				String now=String.valueOf(year);
				this.getPageElement("year").setValue(now); 
			}
			
			//
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 将Long形式的yyyyMMdd转换为String的yyyy-MM-dd<br/>
	 * 或yyyyMM转换为yyyy-MM
	 * @param obj yyyyMMdd或yyyyMM
	 * @return yyyy-MM-dd或yyyy-MM
	 */
	public static String formatLongToDate(Object obj){
		String result;
		String str=String.valueOf(obj);
		int length=str.length();
		switch (length) {
		case 8:
			result=str.substring(0, 4)+"-"+str.substring(4, 6)+"-"+str.substring(6);
			break;
		case 6:
			result=str.substring(0, 4)+"-"+str.substring(4);
			break;
		default:
			result="";
			break;
		}
		return result;
	}
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String xrdx00 = this.getPageElement("xrdx00").getValue();
		String xrdx01 = this.getPageElement("xrdx01").getValue();
		String type = this.getPageElement("type").getValue();
		String year = this.getPageElement("year").getValue();
		if(StringUtils.isEmpty(xrdx01) || StringUtils.isEmpty(type) || StringUtils.isEmpty(year) ){
			this.setMainMessage("请填写选调类型，班级名称，年份必要信息");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String xrdx02 = this.getPageElement("xrdx02").getValue();
		String xrdx03 = this.getPageElement("xrdx03").getValue();
//		String xrdx04 = this.getPageElement("xrdx04").getValue();
		String xrdx05 = this.getPageElement("xrdx05").getValue();
		String xrdx06 = this.getPageElement("xrdx06").getValue();
		String xrdx07 = this.getPageElement("xrdx07").getValue();
		String xrdx08 = this.getPageElement("xrdx08").getValue();
		String xrdx09 = this.getPageElement("xrdx09").getValue();
		String xrdx10 = this.getPageElement("xrdx10").getValue();
		String xrdx11 = this.getPageElement("xrdx11").getValue();
		String xrdx12 = this.getPageElement("xrdx12").getValue();
		String remark = this.getPageElement("remark").getValue();
		EduXrdx edu=new EduXrdx();
		edu.setType(type);
		edu.setYear(year);
		edu.setXrdx01(xrdx01);
		edu.setXrdx02(xrdx02);
		edu.setXrdx03(xrdx03);
//		edu.setXrdx04(xrdx04);
		edu.setXrdx05(xrdx05.replace("-", ""));
		edu.setXrdx06(xrdx06.replace("-", ""));
		edu.setXrdx07(xrdx07);
		edu.setXrdx08(xrdx08);
		edu.setXrdx09(xrdx09);
		edu.setXrdx10(xrdx10);
		edu.setXrdx11(xrdx11);
		edu.setXrdx12(xrdx12);
		edu.setRemark(remark);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String now = sf.format(System.currentTimeMillis()).replace("-", "");
		int time=Integer.parseInt(now);
		int starttime=0;
		int endtime=0;
		if(xrdx05!=null&&!"".equals(xrdx05)){
			starttime=Integer.parseInt(xrdx05.replace("-", ""));
		}
		if(xrdx06!=null&&!"".equals(xrdx06)){
			endtime=Integer.parseInt(xrdx06.replace("-", ""));
		}
		
		if(time<starttime && starttime!=0) {
			edu.setStatus("1");
		}else if(time>=starttime &&time<=endtime && starttime!=0 && endtime!=0) {
			edu.setStatus("2");
		}else if(time>endtime && endtime!=0) {
			edu.setStatus("3");
		}
		if(StringUtils.isEmpty(xrdx00)){
			edu.setXrdx00(UUID.randomUUID().toString().replaceAll("-", ""));
			
        	@SuppressWarnings("unchecked")
 			List<String> bc= HBUtil.getHBSession().createSQLQuery("select 1 from edu_xrdx where year='"+year+"' and xrdx01='"+xrdx01+"' ").list();
 			if(bc.size()>0 && bc.get(0)!=null) {
 				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','该年份已存在本班次！',null,'220')");
 				return EventRtnType.NORMAL_SUCCESS;
 			}

			sess.save(edu);
			sess.flush();
			this.getPageElement("xrdx00").setValue(edu.getXrdx00());
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}else{
			edu.setXrdx00(xrdx00);
			sess.update(edu);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//切换年份
	@PageEvent("typechange")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int typechange() throws RadowException, AppException {
		String type=this.getPageElement("type").getValue();
		try {
			if("1".equals(type)) {
				this.getPageElement("xrdx02").setValue("");
				this.getPageElement("xrdx02_combotree").setValue("");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','类型切换失败！',null,'220')");
			return EventRtnType.FAILD;
		}


		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public static void main(String[] args) {
		String test="abc,efg,hij";
		int one = test.lastIndexOf(",");
		System.out.println(test.substring((one+1),test.length()));
	}
}