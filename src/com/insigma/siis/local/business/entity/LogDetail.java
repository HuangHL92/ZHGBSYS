package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class LogDetail implements Serializable {
	private java.lang.String systemlogdetailid;
	private java.lang.String systemlogid;
	private java.util.Date changedatetime;
	private java.lang.String dataname;
	private java.lang.String oldvalue;
	private java.lang.String newvalue;
	private java.lang.String tableName;
	public java.lang.String getTableName() {
		return tableName;
	}
	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}
	public java.lang.String getSystemlogdetailid() {
		return systemlogdetailid;
	}
	public void setSystemlogdetailid(java.lang.String systemlogdetailid) {
		this.systemlogdetailid = systemlogdetailid;
	}
	public java.lang.String getSystemlogid() {
		return systemlogid;
	}
	public void setSystemlogid(java.lang.String systemlogid) {
		this.systemlogid = systemlogid;
	}
	public java.util.Date getChangedatetime() {
		return changedatetime;
	}
	public void setChangedatetime(java.util.Date changedatetime) {
		this.changedatetime = changedatetime;
	}
	public java.lang.String getDataname() {
		return dataname;
	}
	public void setDataname(java.lang.String dataname) {
		this.dataname = dataname;
	}
	public java.lang.String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(java.lang.String oldvalue) {
		if(oldvalue != null){
			this.oldvalue = oldvalue.replaceAll("\r\n|\r|\n", "");
		}
		//this.oldvalue = oldvalue;
	}
	public java.lang.String getNewvalue() {
		return newvalue;
	}
	public void setNewvalue(java.lang.String newvalue) {
		if(newvalue != null){
			this.newvalue = newvalue.replaceAll("\r\n|\r|\n", "");
		}
		
		//this.newvalue = newvalue;
	}
	public LogDetail() {
    }
	
	public static String escapeAttributeEntities(String text)
    {
		StringBuffer buffer = new StringBuffer();
        char block[] = null;
        int last = 0;
        int size = text.length();
        int i;
        for(i = 0; i < size; i++)
        {
            String entity = null;
            char c = text.charAt(i);
            switch(c)
            {
            case 60: // '<'
                entity = "&lt;";
                break;

            case 62: // '>'
                entity = "&gt;";
                break;


            case 38: // '&'
                entity = "&amp;";
                break;
            case 10: // '\n'
            	 entity = "\n";
                 break;
            case 6: // ''
           	 entity = " ";
                break;
            default:
//            	if(c < ' ' || shouldEncodeChar(c))
                if(c < ' ')
                    entity = "&#" + (int)c + ";";
                break;

            case 9: // '\t'
//            case 10: // '\n'
            case 13: // '\r'
                break;
            }
            if(entity == null)
                continue;
            if(block == null)
                block = text.toCharArray();
            buffer.append(block, last, i - last);
            buffer.append(entity);
            last = i + 1;
        }

        if(last == 0)
            return text;
        if(last < size)
        {
            if(block == null)
                block = text.toCharArray();
            buffer.append(block, last, i - last);
        }
        String answer = buffer.toString();
        buffer.setLength(0);
        return answer;
    }

  
}
