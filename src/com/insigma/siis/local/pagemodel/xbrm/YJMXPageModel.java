package com.insigma.siis.local.pagemodel.xbrm;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

/**
 * Ԥ����ϸ
 * @author a
 *
 */
public class YJMXPageModel extends PageModel{

	/**
	 * ҳ���ʼ��
	 */
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridYjmx.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * Ԥ����ϸ�б��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridYjmx.dogridquery")
	public int doYJMXQuery(int start,int limit) throws RadowException{
		String js0100 = this.getPageElement("js0100").getValue();
		String type = this.getPageElement("type").getValue();
		
		if(StringUtils.isNotEmpty(js0100)){
			String sql = "select js0100,chinesename,yjtype from YJMX where js0100='"+js0100+"'";
			
			if("1".equals(type)){
				sql += " and YJTYPE = '4'";
			} else if ("-1".equals(type)){
				sql += " and YJTYPE != '4'";
			}
			
			this.pageQuery(sql, "SQL", start, limit);
		} else{
			//dosomething
		}
		return EventRtnType.SPE_SUCCESS;
	}

}
