package com.insigma.siis.local.pagemodel.meeting;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.cadremgn.sysbuilder.CreateDefinePageModel;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONObject;
    
public class MeetingPowerPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String publish_id = this.getPageElement("publish_id").getValue();
		try {
			if(publish_id!=null&&!"".equals(publish_id)){
				HBSession sess = HBUtil.getHBSession();
				Publish p = (Publish)sess.get(Publish.class, publish_id);
				PMPropertyCopyUtil.copyObjValueToElement(p, this);
			}
			this.getExecuteSG().addExecuteCode("updateTree();updateTree2();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 保存
	 */         
	@PageEvent("save")
	@Transaction
	public int save(String jsons) throws RadowException {
		String publish_id = this.getPageElement("publish_id").getValue();
		String titlename = this.getPageElement("titlename").getValue();
		String[] arr=jsons.split("######");
		String json="";
		String json2="";
		if(arr.length==2) {
			json=arr[0];
			json2=arr[1];
		}else if(arr.length==1) {
			json=arr[0];
		}
		try{
			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			String userid = SysManagerUtils.getUserId();
			String sql="delete from publishuser where publishid='"+publish_id+"'";
			stmt.executeQuery(sql);
			sql="insert into publishuser (publishid,userid) select '"+publish_id+"',userid from smt_user a where dept in ('9cf7df4b9c76453a826ee27e6c4f577e','824751e5191344de859d79be5a50d193') and useful='1' and userid<>'"+userid+"'";
			stmt.executeQuery(sql);
			JSONObject o=JSONObject.fromObject(json);
			Iterator<?> i=o.keys();
			String key="";
			String value="";
			String table_code="";
			
			while(i.hasNext()){
				key=i.next().toString();
				value=o.getString(key);
			    table_code=key.substring(0,key.length()-value.length());
				if("-1".equals(table_code)){
					continue;
				}else{
					sql="update publishuser set islook='1' where  publishid='"+publish_id+"' and userid='"+value+"'";
					stmt.executeQuery(sql);
				}
			}
			
			JSONObject o2=JSONObject.fromObject(json2);
			Iterator<?> i2=o2.keys();
			
			while(i2.hasNext()){
				key=i2.next().toString();
				value=o2.getString(key);
			    table_code=key.substring(0,key.length()-value.length());
				if("-1".equals(table_code)){
					continue;
				}else{
					sql="update publishuser set ischange='1' where  publishid='"+publish_id+"' and userid='"+value+"'";
					stmt.executeQuery(sql);
				}
			}
			
			stmt.close();
			
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"议题授权","publishuser",user.getId(),titlename, new ArrayList());
			
			this.getExecuteSG().addExecuteCode("alert('保存成功');window.close();");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}
	
	@PageEvent("definedList")
	public int definedList() throws Exception {
		String publish_id = this.getParameter("publish_id");
		String type = this.getParameter("type");
		StringBuffer jsonStr = new StringBuffer();
		CommQuery cqbs=new CommQuery();
		String userid = SysManagerUtils.getUserId();
		try {
			String sql_root="";
			String sql="";
			String sql2="";
			if("look".equals(type)) {
				sql_root="select 1 from publishuser where publishid='"+publish_id+"' and islook='1'";
				sql="select userid,username,(select count(*) from publishuser b where b.userid=a.userid and b.publishid='"+publish_id+"' and islook='1') num from smt_user a where dept in ('9cf7df4b9c76453a826ee27e6c4f577e') and useful='1' and userid<>'"+userid+"' ";
				sql2="select userid,username,(select count(*) from publishuser b where b.userid=a.userid and b.publishid='"+publish_id+"' and islook='1') num from smt_user a where dept in ('824751e5191344de859d79be5a50d193') and useful='1' and userid<>'"+userid+"' ";
			} else if("update".equals(type)) {
				sql_root="select 1 from publishuser where publishid='"+publish_id+"' and ischange='1'";
				sql="select userid,username,(select count(*) from publishuser b where b.userid=a.userid and b.publishid='"+publish_id+"' and ischange='1') num from smt_user a where dept in ('9cf7df4b9c76453a826ee27e6c4f577e') and useful='1' and userid<>'"+userid+"'  ";
				sql2="select userid,username,(select count(*) from publishuser b where b.userid=a.userid and b.publishid='"+publish_id+"' and ischange='1') num from smt_user a where dept in ('824751e5191344de859d79be5a50d193') and useful='1' and userid<>'"+userid+"'  ";
			}
			List<HashMap<String, Object>> list;
			list=cqbs.getListBySQL(sql_root);
			List<HashMap<String, Object>> listuser;
			List<HashMap<String, Object>> listuser2;
			listuser = cqbs.getListBySQL(sql);
			listuser2 = cqbs.getListBySQL(sql2);
			
			if (listuser != null && listuser.size() > 0) {
				
				boolean flag=false;
				if(list!=null&&list.size()>0&&list.size()==listuser.size()) {
					flag=true;
				}
				jsonStr.append("[");
				jsonStr.append("{\"text\" :\"干部处室\" ,\"id\" :\"9cf7df4b9c76453a826ee27e6c4f577e\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser.size(); j++) {
					String muserid =listuser.get(j).get("userid").toString();// 用户组ID
					String username = listuser.get(j).get("username").toString();// 用户组名称
					String num = listuser.get(j).get("num").toString();// 是否已定时（1为是，0位否）
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ username
							+ "\" ,\"id\" :\""
							+ muserid
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser.size() - 1) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]},");
				jsonStr.append("{\"text\" :\"组织部领导\" ,\"id\" :\"824751e5191344de859d79be5a50d193\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int j = 0; j < listuser2.size(); j++) {
					String muserid =listuser2.get(j).get("userid").toString();// 用户组ID
					String username = listuser2.get(j).get("username").toString();// 用户组名称
					String num = listuser2.get(j).get("num").toString();// 是否已定时（1为是，0位否）
					boolean str=false;
					if("1".equals(num)){
						str= true;
					}
					
					jsonStr.append("{\"text\" :\""
							+ username
							+ "\" ,\"id\" :\""
							+ muserid
							+ "\" ,\"checked\" :"
							+ str
							+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
	
					if (j != listuser2.size()-1 ) {
						jsonStr.append(",");
					}
	
				}
				jsonStr.append("]}");
			}
			jsonStr.append("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
}
