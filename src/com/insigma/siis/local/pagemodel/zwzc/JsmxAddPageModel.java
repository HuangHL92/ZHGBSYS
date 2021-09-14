package com.insigma.siis.local.pagemodel.zwzc;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.DBWY;
import com.insigma.siis.local.business.entity.Jsmx;
import com.insigma.siis.local.business.entity.Wpgz;

public class JsmxAddPageModel extends PageModel  {
	@Override
    public int doInit() throws RadowException {
		//this.setNextEventName("loaddata");
        return EventRtnType.NORMAL_SUCCESS;
    }
	
//	 @PageEvent("loaddata")
//	    public int dogridquery() throws RadowException{
//		 	String js00=this.getPageElement("js00").getValue();
//		 	StringBuilder sb = new StringBuilder("select * from jsmx where js00='"+js00+"'");
//		 	 List<Object[]> a=HBUtil.getHBSession().createSQLQuery(sb.toString()).list();
//		 	 Object[] mapdata=null;
//		        if(a.size()>0){
//		            mapdata=a.get(0);
//		            //this.getPageElement("a0000").setValue(mapdata[0].toString());
//		            //this.getPageElement("a0200").setValue(mapdata[18].toString());
//		            this.getPageElement("personId").setValue(mapdata[0].toString());
//		            this.getPageElement("xm").setValue(mapdata[1].toString());
//		            this.getPageElement("xb").setValue(mapdata[2].toString());
//		            this.getPageElement("csny").setValue(mapdata[3].toString());
//		            this.getPageElement("zw").setValue(mapdata[4].toString());
//		            this.getPageElement("zj").setValue(mapdata[5].toString());
//		            this.getPageElement("szcs").setValue(mapdata[6].toString());
//		            this.getPageElement("id").setValue(mapdata[7].toString());
//		            this.getPageElement("rank").setValue(mapdata[8].toString());
//		            this.getPageElement("dbwy").setValue(mapdata[9].toString());
//		            this.getPageElement("xqjb").setValue(mapdata[10].toString());
//		            String aa=mapdata[11].toString();
//		            if(!" ".equals(aa)) {
//		            	this.getPageElement("rzsj").setValue(mapdata[11].toString());
//		            }
//		            String bb=mapdata[12].toString();
//		            if(!" ".equals(bb)) {
//		            	this.getPageElement("mzsj").setValue(mapdata[12].toString());
//		            }
//		            
//		        }
//		 
//		 
//		 	return EventRtnType.NORMAL_SUCCESS;
//	    
//	 }
	
	
	
	
	@PageEvent("freshInfo")
    public int freshInfo() throws RadowException, AppException {
        String personId = this.getPageElement("personId").getValue();
        // 拼接sql，获取信息
        StringBuffer sb = new StringBuffer();
//        sb.append("select a01.A0000 as id, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, a01.A0221 as zw, a01.A0192a as zj, a01.a0192f ,");
//        sb.append(" to_char(wm_concat(a02.A0201A)) as rzjg ");
//        sb.append(" from a01 ");
//        sb.append(" join a02 on a02.a0000 = a01.a0000 ");
//        sb.append(" where 1 = 1 ");
//        sb.append(" and a01.a0000 = '" + personId + "' ");
//        sb.append(" and a02.a0255 = '1' ");
//        sb.append(" and a02.A0201A is not null ");
//        sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E ");
        sb.append("select a01.a0101,a01.a0107,a01.a0192a,a01.a0192f from a01 where a01.a0000='"+personId+"'");
        Object[] result = (Object[]) HBUtil.getHBSession().createSQLQuery(sb.toString()).uniqueResult();
        // 页面赋值
        this.getPageElement("a0101_1").setValue(toString(result[0]));
//        this.getPageElement("xb").setValue(toString(result[2]));
        this.getPageElement("a0107_1").setValue(toString(result[1]));
//        String zw = HBUtil.getCodeName("ZB09", toString(result[4]));
//        this.getPageElement("zw").setValue(zw);
        this.getPageElement("a0192a_1").setValue(toString(result[2]));
        this.getPageElement("a0192f_1").setValue(toString(result[3]));

        return EventRtnType.NORMAL_SUCCESS;
    }
	
	 private String toString(Object obj) {
	        return (obj == null) ? "" : obj.toString();
	 }

	 private String formatDate(String date) {
	        if (date.length() == 6 || date.length() == 8) {
	            return date.substring(0, 4) + "." + date.substring(4, 6);
	        } else {
	            return date;
	        }
	 }
	 
	 @PageEvent("save.onclick")
	 public int save() throws RadowException, AppException {
		 String type= this.getPageElement("type").getValue();
		 PageElement id = this.getPageElement("id");
		 HBSession session = HBUtil.getHBSession();
		 Jsmx jsmx=new Jsmx();
         if ("1".equals(type)) {
            // 新增数据
            //Rztx  record2=new Rztx();
            String uuid=IDUtil.generateUUID();
            jsmx.setJs00(uuid);
            jsmx.setA0000(this.getPageElement("personId").getValue());
            jsmx.setA0101(this.getPageElement("a0101_1").getValue());
            jsmx.setA0107(this.getPageElement("a0107_1").getValue());
            jsmx.setA0192a(this.getPageElement("a0192a_1").getValue());
            jsmx.setA0192f(this.getPageElement("a0192f_1").getValue());
            /*record2.setId(uuid);
            record2.setA0000(uuid);
            record2.setA0101(this.getPageElement("xm").getValue());
            record2.setRztxsj(this.getPageElement("qzsj").getValue());
            record2.setRztxnr(this.getPageElement("yxnr").getValue());
            record2.setState("0");//默认状态未读
            session.save(record2);*/
            session.save(jsmx);
            session.flush();
        } else if("2".equals(type)){
        	 String uuid=IDUtil.generateUUID();
             jsmx.setJs00(uuid);
             jsmx.setA0101(this.getPageElement("a0101_2").getValue());
             jsmx.setA0107(this.getPageElement("a0107_2").getValue());
             jsmx.setA0192a(this.getPageElement("a0192a_2").getValue());
             jsmx.setA0192f(this.getPageElement("a0192f_2").getValue());
            session.save(jsmx);
            session.flush();
        }
        String a0000=this.getPageElement("personId").getValue();
        this.setMainMessage("保存成功");
        this.getExecuteSG().addExecuteCode("realParent.PersonQuery()");
        //this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('wpgzAdd').close()");
        return EventRtnType.NORMAL_SUCCESS;
    }
	 



}
