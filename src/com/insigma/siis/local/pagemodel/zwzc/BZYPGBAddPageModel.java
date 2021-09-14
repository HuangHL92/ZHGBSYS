package com.insigma.siis.local.pagemodel.zwzc;

import java.util.HashMap;
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
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.DBWY;
import com.insigma.siis.local.business.entity.Wpgz;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class BZYPGBAddPageModel extends PageModel  {
	@Override
    public int doInit() throws RadowException {
		this.setNextEventName("loaddata");
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	 @PageEvent("loaddata")
	    public int dogridquery() throws RadowException{
		 	String id=this.getPageElement("id").getValue();
		 	StringBuilder sb = new StringBuilder("select a01.A0000, a01.A0101 as xm, a01.A0104 as xb, a01.A0107 as csny, nvl(a01.A0221,' ') as zw, nvl(a01.A0192E,' ') as zj, ")
	                .append(" nvl(to_char(wm_concat(a02.A0201A)),' ') as szcs, g.id, nvl(g.tbfzsj,' '), nvl(g.tbzzsj,' '),g.b0111,g.b0101 ")
	                .append(" from a01 ")
	                .append(" join a02 on a02.a0000 = a01.a0000 ")
	                .append(" join BZYPGB g on g.a0000 = a01.a0000 ")
	                .append(" where 1 = 1 and id='"+id.toString()+"'");
		 	 sb.append(" group by a01.a0000, a01.A0101, a01.A0104, a01.A0107, a01.A0221, a01.A0192E,g.id, g.tbfzsj, g.tbzzsj,g.b0111,g.b0101");
		 	 List<Object[]> a=HBUtil.getHBSession().createSQLQuery(sb.toString()).list();
		 	 Object[] mapdata=null;
		        if(a.size()>0){
		            mapdata=a.get(0);
		            //this.getPageElement("a0000").setValue(mapdata[0].toString());
		            //this.getPageElement("a0200").setValue(mapdata[18].toString());
		            this.getPageElement("a0000").setValue(mapdata[0].toString());
		            this.getPageElement("xm").setValue(mapdata[1].toString());
		            this.getPageElement("xb").setValue(mapdata[2].toString());
		            this.getPageElement("csny").setValue(mapdata[3].toString());
		            this.getPageElement("zw").setValue(mapdata[4].toString());
		            this.getPageElement("zj").setValue(mapdata[5].toString());
		            this.getPageElement("szcs").setValue(mapdata[6].toString());
		            this.getPageElement("id").setValue(mapdata[7].toString());
		            //this.getPageElement("rank").setValue(mapdata[8].toString());
		            this.getPageElement("tbfzsj").setValue(mapdata[8].toString());
		            this.getPageElement("tbzzsj").setValue(mapdata[9].toString());
		            this.getPageElement("b0111").setValue(mapdata[10].toString());
		            this.getPageElement("b0101").setValue(mapdata[11].toString());
		            
		        }
		 
		 
		 	return EventRtnType.NORMAL_SUCCESS;
	    
	 }
	
	
	
	
	@PageEvent("freshInfo")
    public int freshInfo() throws RadowException, AppException {
        String personId = this.getPageElement("a0000").getValue();
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
	        //PageElement id = this.getPageElement("id");
	        String id = this.getPageElement("id").getValue();
	        HBSession session = HBUtil.getHBSession();
	        CommQuery cq = new CommQuery();
	        String b0111 = this.getPageElement("checkedgroupid").getValue();
	        String a0000 = this.getPageElement("a0000").getValue();
	        String xm = this.getPageElement("xm").getValue();
	        String tbfzsj = this.getPageElement("tbfzsj").getValue();
	        String tbzzsj = this.getPageElement("tbzzsj").getValue();
	        try {
	        if (StringUtils.isBlank(id)) {
	            // 新增数据
	            //Rztx  record2=new Rztx();
	        	String uuid=IDUtil.generateUUID();
	        	String sql ="select  b0101,tbrs from BZYPGB where b0111='" + b0111 + "'";
	    		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
	    		String b0101=list.get(0).get("b0101") == null ? "" : list.get(0).get("b0101").toString();
	    		String tbrs=list.get(0).get("tbrs") == null ? "" : list.get(0).get("tbrs").toString();
	    		String tbrs1=String.valueOf(Integer.parseInt(tbrs)+1);
				HBUtil.executeUpdate("insert into BZYPGB(id,b0111,b0101,a0000,tbfzsj,tbzzsj,a0101) "
							+ "values(?,?,?,?,?,?,?)",new Object[]{uuid,b0111,b0101,a0000,tbfzsj,tbzzsj,xm});
	        	
	        	//String sql ="select  count(distinct a0000) count from BZYPGB where b0111='" + b0111 + "'";
	    		//List<HashMap<String, Object>> list = cq.getListBySQL(sql);
	    		//String sum=list.get(0).get("count") == null ? "" : list.get(0).get("count").toString();
	        	
	        	HBUtil.executeUpdate("update BZYPGB set tbrs=? where b0111=?",
						new Object[]{tbrs1,b0111});
	        	
	        } else {
	            // 修改记录
	        	HBUtil.executeUpdate("update BZYPGB set tbfzsj=?,tbzzsj=? where id=?",
	        			new Object[]{tbfzsj,tbzzsj,id});
	        	
	        }
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        this.setMainMessage("保存成功");
	        //this.getExecuteSG().addExecuteCode("alert('保存成功');window.close();");
	        this.getExecuteSG().addExecuteCode("realParent.reload()");
	        this.closeCueWindowByYes("BZYPGBAdd");
	        //this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('BZYPGBAdd').close()");
	        return EventRtnType.NORMAL_SUCCESS;
	    }
	//重写确认后关闭方法
		@Override
		public void closeCueWindowByYes(String arg0) {
			// TODO Auto-generated method stub
			this.setShowMsg(true);
			this.addNextBackFunc(NextEventValue.YES, "parent.odin.ext.getCmp('"+arg0+"').close();");
		}
	


}
