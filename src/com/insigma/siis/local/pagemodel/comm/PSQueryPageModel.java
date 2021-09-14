package com.insigma.siis.local.pagemodel.comm;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.PageModelEngine;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.ObjectUtil;
import com.insigma.odin.framework.radow.util.PmHListUtil;
import com.insigma.odin.framework.radow.util.SpellUtil;

public class PSQueryPageModel extends PageModel {
	/**
	 * 最小搜索字符长度
	 */
	private final static int MIN_SEARCH_LEN = 6;

	/**
	 * 中文最小搜索字符长度
	 */
	private final static int MIN_SEARCH_LEN_CN = 2;

	/**
	 * 查询的sql
	 */ 
	private final static String sql = "select aaz157,aaz001,aae135,aac003,aac004,eac001,eac070,aac008,aab004,aab001,aab001 cpquery,aac001,aab301,aab023,eab014"
			+ " from sbdv_ac20 a " + " where 1=1 /*1*/ ";
	/**
	 * 初始化界面（init事件使用）
	 * 
	 * @throws AppException
	 */
	@Override
	public int doInit() throws RadowException {
		String searchText = this.getRadow_parent_data();
		if (SpellUtil.existsChinese(searchText)) {
			// 存在中文字符的情况下，按照人员姓名进行查询
			// div_1.set("aac003_a", searchText);
			this.getPageElement("div_1.aac003_a").setValue(searchText);
		}else if (searchText.length() <= 8) {
			//div_1.set("eac001_a", searchText);
			this.getPageElement("div_1.eac001_a").setValue(searchText);
		} else {
			//div_1.set("aae135_a", upaae135(searchText));
			this.getPageElement("div_1.aae135_a").setValue(searchText);
		}
		this.setNextEventName("div_2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("div_2.dogridquery")
	@EventDataRange("div_1")
	public int div2dogridquery(int start,int limit) throws RadowException {
		StringBuffer sqlbuff = new StringBuffer(sql);
		String searchText = null;
		if((searchText=getPageElement("div_1.aac003_a").getValue())==null || searchText.trim().length()==0 ){
			if((searchText=getPageElement("div_1.eac001_a").getValue())==null || searchText.trim().length()==0 ){
				if((searchText=getPageElement("div_1.aae135_a").getValue())==null || searchText.trim().length()==0 ) {
					searchText = "";
				}
			}
		}
		
		if (searchText.length() == 0) {
			this.setMainMessage("未输入搜索字符");
			this.isShowMsg = true;
			return EventRtnType.FAILD;
		}
		boolean existsChinese = SpellUtil.existsChinese(searchText);
		// 如果小于最小搜索长度，则直接出错
		if (existsChinese && searchText.length() < MIN_SEARCH_LEN_CN) {
			this.setMainMessage("搜索字符串（中文）不能少于" + MIN_SEARCH_LEN_CN + "位！");
			this.isShowMsg = true;
			return EventRtnType.FAILD;
		} else if (!existsChinese && searchText.length() < MIN_SEARCH_LEN) {
			this.setMainMessage("搜索字符串（非中文）不能少于" + MIN_SEARCH_LEN + "位！");
			this.isShowMsg = true;
			return EventRtnType.FAILD;
		}

		// 判断是否存在中文字符
		if (existsChinese) {
			// 存在中文字符的情况下，按照人员姓名进行查询
			sqlbuff.append(" and a.aac003 like '%"+searchText+"%' ");
		} else {
			// 宁波
			// 不存在中文字符的情况下，再分两种情况进行查询
			// 在长度小于等于8的情况下，按个人编码搜索
			// 在长度大于8位的情况下，按身份证号查询
			if (searchText.length() <= 8) {
				sqlbuff.append(" and a.eac001 like '%"+searchText+"%'");
			} else {
				sqlbuff.append(" and a.aae135 like '%"+searchText+"%'");
			}
		}
		
		PageQueryData pqd = PmHListUtil.pageQuery(this, sqlbuff.toString(), "sql", start, limit);
		if(pqd.getTotalCount()==1) {
			this.closeCueWindow("psqueryWindow");
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("div_2.rowdbclick")
	public int div2dbonclick() throws RadowException {
		PageElement pe = getPageElement("div_2");
		this.closeCueWindow("psqueryWindow");
		createPageElement("aac004", ElementType.SELECT, true).setValue(pe.getStringValue("aac004"));
		createPageElement("aac003", ElementType.TEXT, true).setValue(pe.getStringValue("aac003"));
		createPageElement("aac006", ElementType.DATE, true).setValue(pe.getStringValue("aac006"));
		createPageElement("aac010", ElementType.TEXT, true).setValue(pe.getStringValue("aac010"));
		createPageElement("aae004", ElementType.TEXT, true).setValue(pe.getStringValue("aae004"));
		createPageElement("aae005", ElementType.TEXT, true).setValue(pe.getStringValue("aae005"));
		createPageElement("aae006", ElementType.TEXT, true).setValue(pe.getStringValue("aae006"));
		createPageElement("aae007", ElementType.TEXT, true).setValue(pe.getStringValue("aae007"));
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static String upaae135(String aae135) {
		// 身份证加"19"
		if (aae135.length() >= 8 && aae135.length() <= 16
				&& !ObjectUtil.equals(aae135.substring(6, 8), "19")) {
			aae135 = aae135.substring(0, 6) + "19" + aae135.substring(6);
		}
		return aae135;
	}



	public static void main(String[] arg) {
		CommonQueryBS.systemOut(upaae135("330219780102"));
	}

}