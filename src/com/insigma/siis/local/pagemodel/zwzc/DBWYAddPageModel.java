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
import com.insigma.siis.local.business.entity.Wpgz;

public class DBWYAddPageModel extends PageModel  {
	@Override
    public int doInit() throws RadowException {
		this.setNextEventName("loaddata");
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	 @PageEvent("loaddata")
	    public int dogridquery() throws RadowException{
		 	String id=this.getPageElement("id").getValue();
		 	StringBuilder sb = new StringBuilder("select a01.A0000, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, nvl(a01.A0221,' ') as zw, nvl(a01.A0192E,' ') as zj, ")
	                .append(" nvl(to_char(wm_concat(a02.A0201A)),' ') as szcs, g.id, nvl(g.rank,' '), nvl(g.dbwy,' '), nvl(g.xqjb,' '), nvl(g.rzsj,' '), nvl(g.mzsj,' ') ")
	                .append(" from a01 ")
	                .append(" join a02 on a02.a0000 = a01.a0000 ")
	                .append(" join dbwy g on g.a0000 = a01.a0000 ")
	                .append(" where 1 = 1 and id='"+id.toString()+"'");
		 	 sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E,g.id, g.rank, g.dbwy, g.xqjb, g.rzsj, g.mzsj");
		 	 List<Object[]> a=HBUtil.getHBSession().createSQLQuery(sb.toString()).list();
		 	 Object[] mapdata=null;
		        if(a.size()>0){
		            mapdata=a.get(0);
		            //this.getPageElement("a0000").setValue(mapdata[0].toString());
		            //this.getPageElement("a0200").setValue(mapdata[18].toString());
		            this.getPageElement("personId").setValue(mapdata[0].toString());
		            this.getPageElement("xm").setValue(mapdata[1].toString());
		            this.getPageElement("xb").setValue(mapdata[2].toString());
		            this.getPageElement("csny").setValue(mapdata[3].toString());
		            this.getPageElement("zw").setValue(mapdata[4].toString());
		            this.getPageElement("zj").setValue(mapdata[5].toString());
		            this.getPageElement("szcs").setValue(mapdata[6].toString());
		            this.getPageElement("id").setValue(mapdata[7].toString());
		            this.getPageElement("rank").setValue(mapdata[8].toString());
		            this.getPageElement("dbwy").setValue(mapdata[9].toString());
		            this.getPageElement("xqjb").setValue(mapdata[10].toString());
		            String aa=mapdata[11].toString();
		            if(!" ".equals(aa)) {
		            	this.getPageElement("rzsj").setValue(mapdata[11].toString());
		            }
		            String bb=mapdata[12].toString();
		            if(!" ".equals(bb)) {
		            	this.getPageElement("mzsj").setValue(mapdata[12].toString());
		            }
		            
		        }
		 
		 
		 	return EventRtnType.NORMAL_SUCCESS;
	    
	 }
	
	
	
	
	@PageEvent("freshInfo")
    public int freshInfo() throws RadowException, AppException {
        String personId = this.getPageElement("personId").getValue();
        // 拼接sql，获取信息
        StringBuffer sb = new StringBuffer();
        sb.append("select a01.A0000 as id, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, a01.A0221 as zw, a01.A0192E as zj, ");
        sb.append(" to_char(wm_concat(a02.A0201A)) as rzjg ");
        sb.append(" from a01 ");
        sb.append(" join a02 on a02.a0000 = a01.a0000 ");
        sb.append(" where 1 = 1 ");
        sb.append(" and a01.a0000 = '" + personId + "' ");
        sb.append(" and a02.a0255 = '1' ");
        sb.append(" and a02.A0201A is not null ");
        sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E ");
        Object[] result = (Object[]) HBUtil.getHBSession().createSQLQuery(sb.toString()).uniqueResult();
        // 页面赋值
        this.getPageElement("xm").setValue(toString(result[1]));
        this.getPageElement("xb").setValue(toString(result[2]));
        this.getPageElement("csny").setValue(formatDate(toString(result[3])));
        String zw = HBUtil.getCodeName("ZB09", toString(result[4]));
        this.getPageElement("zw").setValue(zw);
        this.getPageElement("zj").setValue(toString(result[5]));
        this.getPageElement("szcs").setValue(toString(result[6]));

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
	        PageElement id = this.getPageElement("id");
	        HBSession session = HBUtil.getHBSession();
	        if (StringUtils.isBlank(id.getValue())) {
	            // 新增数据
	            //Rztx  record2=new Rztx();
	            DBWY record = new DBWY();
	            String uuid=IDUtil.generateUUID();
	            record.setId(uuid);
	            record.setA0000(this.getPageElement("personId").getValue());
	            record.setRank(this.getPageElement("rank").getValue());
	            record.setDbwy(this.getPageElement("dbwy").getValue());
	            record.setXqjb(this.getPageElement("xqjb").getValue());
	            record.setRzsj(this.getPageElement("rzsj").getValue());
	            record.setMzsj(this.getPageElement("mzsj").getValue());
	            /*record2.setId(uuid);
	            record2.setA0000(uuid);
	            record2.setA0101(this.getPageElement("xm").getValue());
	            record2.setRztxsj(this.getPageElement("qzsj").getValue());
	            record2.setRztxnr(this.getPageElement("yxnr").getValue());
	            record2.setState("0");//默认状态未读
	            session.save(record2);*/
	            session.save(record);
	            session.flush();
	            id.setValue(record.getId());
	        } else {
	            // 修改记录
	            //Rztx record2 = (Rztx) session.get(Rztx.class,id.getValue());
	            DBWY record = (DBWY) session.load(DBWY.class, id.getValue());
	            record.setA0000(this.getPageElement("personId").getValue());
	            record.setRank(this.getPageElement("rank").getValue());
	            record.setDbwy(this.getPageElement("dbwy").getValue());
	            record.setXqjb(this.getPageElement("xqjb").getValue());
	            record.setRzsj(this.getPageElement("rzsj").getValue());
	            record.setMzsj(this.getPageElement("mzsj").getValue());
	            /*if(record2!=null) {
	                record2.setA0101(this.getPageElement("xm").getValue());
	                record2.setRztxsj(this.getPageElement("qzsj").getValue());
	                record2.setRztxnr(this.getPageElement("yxnr").getValue());
	                session.update(record2);
	            }*/
	            session.update(record);
	            session.flush();
	        }
	        String a0000=this.getPageElement("personId").getValue();
	        updateA01(a0000);
	        this.setMainMessage("保存成功");
	        this.getExecuteSG().addExecuteCode("realParent.PersonQuery()");
	        //this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('wpgzAdd').close()");
	        return EventRtnType.NORMAL_SUCCESS;
	    }
	 
	//更新A01中的STJZ字段
		private void updateA01(String a0000 ) throws AppException{
			//当前页面赋值
			@SuppressWarnings("unchecked")
			List<String> list1 = HBUtil.getHBSession().createSQLQuery("select rank from DBWY where a0000='"+a0000+"' and mzsj is null "
			+" order by dbwy "		).list();
			
			@SuppressWarnings("unchecked")
			List<String> list2 = HBUtil.getHBSession().createSQLQuery("select dbwy from DBWY where a0000='"+a0000+"' and mzsj is null "
			+" order by dbwy "		).list();
			StringBuffer dbwystr = new StringBuffer();
			for(int i=0;i<list1.size();i++) {
				if("1".equals(list1.get(i))) {
					dbwystr.append("中央");
				}else if("2".equals(list1.get(i))) {
					dbwystr.append("省");
				}else if("3".equals(list1.get(i))) {
					dbwystr.append("市");
				}else if("4".equals(list1.get(i))) {
					dbwystr.append("区县市");
				}
				if("1".equals(list2.get(i))) {
					dbwystr.append("市委委员");
				}else if("2".equals(list2.get(i))) {
					dbwystr.append("人大常委");
				}else if("3".equals(list2.get(i))) {
					dbwystr.append("人大代表");
				}else if("4".equals(list2.get(i))) {
					dbwystr.append("政协委员");
				}else if("5".equals(list2.get(i))) {
					dbwystr.append("党代表");
				}else if("6".equals(list2.get(i))) {
					dbwystr.append("纪委委员");
				}
				dbwystr.append("，");
			}
			if(dbwystr.length()>0){
				dbwystr.deleteCharAt(dbwystr.length()-1);
			}
			
		
			
			//更新A10 a0196 专业技术职务字段。   a06 a0602
			HBUtil.executeUpdate("update a01 set DBWY='"+dbwystr.toString()+"' where a0000='"+a0000+"'");
			
		}


}
