package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class SaveStatisticsPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		String SQ005 = SysManagerUtils.getUserId();
		String sql="select SQ001,SQ002,SQ003,SQ004 from statistics_query where SQ003 = '3' and SQ005 = '"+SQ005+"'";
		List<Object[]> Objects=HBUtil.getHBSession().createSQLQuery(sql).list();
		for(Object[] obj:Objects){
			String id=(String) obj[0];
			String name=(String) obj[1];
			String i=(String) obj[2];
			String SQ004=(String) obj[3];
			this.getExecuteSG().addExecuteCode("addChild ('"+i+"','"+name+"','"+id+"','"+SQ004+"')");
		}
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		Map<String,String> map = TwoDStatisticsPageModel.ewdata;
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击保存按钮
	@PageEvent("save.onclick")
	@Transaction
	public int save() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		String type = null;
		Integer vert_length = null;
		Integer tran_length = null;
		String SQ001 = null;
		if(!param.contains("@")){
			this.setMainMessage("操作失败,请刷新页面重试！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			type = param.split("@")[0];
			if("1".equals(type)){
				SQ001 = param.split("@")[1];
			}else{
				vert_length = Integer.parseInt(param.split("@")[1]);
				tran_length = Integer.parseInt(param.split("@")[2]);
				SQ001 = param.split("@")[3];
			}
		}
		String SQ002 = this.getPageElement("conditionName").getValue();
		SQ002 = StringFilter(SQ002);//过滤特殊字符串
		if(SQ002.length()>20){
			this.setMainMessage("保存名称长度限定20字符,请重新输入！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String SQ005 = SysManagerUtils.getUserId();
		if(StringUtil.isEmpty(SQ002)){
			this.setMainMessage("请输入保存名称");
			return EventRtnType.NORMAL_SUCCESS;			
		}
		String sql = "select SQ001,SQ002 from statistics_query where SQ005 = '"+SQ005+"' ";
		List<Object[]> li = sess.createSQLQuery(sql).list();
		for(int i=0;i<li.size();i++){
			Object[] a=li.get(i);
			String id = a[0].toString();
			String name = a[1].toString();
			if(name.equals(SQ002)){
				this.getExecuteSG().addExecuteCode("replaceName('"+id+"');");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		List<Map<String, Object>> list = GeneralStatisticsPageModel.query_condition;
		Map<String,String> map = TwoDStatisticsPageModel.ewdata;
		if(type.equals("1")){
			String SQ003 = "3";
			String SQ004 = "1";
			String sql1 = "insert into statistics_query (SQ001,SQ002,SQ003,SQ004,SQ005) values ('"+SQ001+"','"+SQ002+"','3','1','"+SQ005+"')";
			sess.createSQLQuery(sql1).executeUpdate();
			for(int i=0;i<list.size();i++){
				String QC001 = list.get(i).get("uuid").toString();
				String QC002 = list.get(i).get("Queryname").toString();
				String QC003 = list.get(i).get("Querysql").toString().replace("'", "|");
				String QC004 = list.get(i).get("Gridstring").toString();
				String sql2 = "insert into query_condition(QC001,QC002,QC003,SQ001,QC004) values (?,?,?,?,?)";
				//sess.createSQLQuery(sql2).executeUpdate();
				try {
					PreparedStatement ps = conn.prepareStatement("insert into query_condition(QC001,QC002,QC003,SQ001,QC004) values (?,?,?,?,?)");
					ps.setString(1, QC001);
					ps.setString(2, QC002);
					ps.setString(3, QC003);
					ps.setString(4, SQ001);
					ps.setString(5, QC004);
					ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(type.equals("2")){
				String SQ003 = "3";
				String SQ004 = "2";
				String sql1 = "insert into statistics_query(SQ001,SQ002,SQ003,SQ004,SQ005) values ('"+SQ001+"','"+SQ002+"','3','2','"+SQ005+"')";
				sess.createSQLQuery(sql1).executeUpdate();
				String QC001 = UUID.randomUUID().toString();
				String transverseStatisticsID = map.get("transverseStatisticsID");
				String verticalStatisticsID = map.get("verticalStatisticsID");
				String QC003_tran = transverseStatisticsID+"@";
				String QC003_vert = verticalStatisticsID+"@";
				for(int i = 0; i < tran_length; i++){
					String tran_i = map.get("tran"+i);
					if(tran_i !=null && tran_i.length()>0){
						QC003_tran += tran_i+"@";
					}
				}
				QC003_tran = QC003_tran.substring(0,QC003_tran.length()-1);
				for(int i = 0; i < vert_length; i++){
					String vert_i = map.get("vert"+i);
					if(vert_i !=null && vert_i.length()>0){
						QC003_vert += vert_i+"@";
					}
				}
				QC003_vert = QC003_vert.substring(0,QC003_vert.length()-1);
				String QC003 = QC003_vert + "|" + QC003_tran ; 
				String sql2 = "insert into query_condition(QC001,QC002,QC003,SQ001) values ('"+QC001+"','"+SQ002+"','"+QC003+"','"+SQ001+"')";
				sess.createSQLQuery(sql2).executeUpdate();
			}
		//this.setMainMessage("保存成功");
		this.closeCueWindow("SaveStatistics");
		//this.getExecuteSG().addExecuteCode("reloadpp();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*//点击删除按钮
	@PageEvent("delBtn.onclick")
	@Transaction
	public int delBtn() throws RadowException{
		String SQ001 = this.getPageElement("checknodeid").getValue();
		String SQ002 = this.getPageElement("name").getValue();
		if(StringUtil.isEmpty(SQ001)){
			this.setMainMessage("请选择删除条件");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("removeMess();");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	//确认删除并刷新
	@PageEvent("reloadpp")
	public int reloadpp() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String SQ001 = this.getPageElement("checknodeid").getValue();
		String SQ002 = this.getPageElement("name").getValue();
		sess.createSQLQuery("delete from query_condition where SQ001 = '"+SQ001+"'").executeUpdate();
		sess.createSQLQuery("delete from statistics_query where SQ001 = '"+SQ001+"'").executeUpdate();
		this.setMainMessage("删除成功");
		this.reloadPageByYes();
		this.getExecuteSG().addExecuteCode("reloadpp();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	*/
	// 过滤特殊字符  
    public   static   String StringFilter(String str) throws PatternSyntaxException {     
          // 只允许字母和数字       
          // String   regEx  =  "[^a-zA-Z0-9]";                     
          // 清除掉所有特殊字符  
          String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
          Pattern p = Pattern.compile(regEx);     
          Matcher m = p.matcher(str);     
          return m.replaceAll("").trim();     
    } 
    
	//点击关闭按钮
	@PageEvent("close.onclick")
	public int close() throws RadowException{
		this.closeCueWindow("SaveStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");
	}
	
	@PageEvent("replaceEvent")
	public int replaceEvent(String id) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		String type = null;
		Integer vert_length = null;
		Integer tran_length = null;
		String SQ001 = null;
		if(!param.contains("@")){
			this.setMainMessage("操作失败,请刷新页面重试！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			type = param.split("@")[0];
			if("1".equals(type)){
				SQ001 = param.split("@")[1];
			}else{
				vert_length = Integer.parseInt(param.split("@")[1]);
				tran_length = Integer.parseInt(param.split("@")[2]);
				SQ001 = param.split("@")[3];
			}
		}
		String SQ002 = this.getPageElement("conditionName").getValue();
		SQ002 = StringFilter(SQ002);//过滤特殊字符串
		List<Map<String, Object>> list = GeneralStatisticsPageModel.query_condition;
		Map<String,String> map = TwoDStatisticsPageModel.ewdata;
		if(type.equals("1")){
			String sql2 = "delete FROM query_condition WHERE SQ001 = '"+id+"' ";
			sess.createSQLQuery(sql2).executeUpdate();
			String sql1 = "update statistics_query set SQ001 = '"+SQ001+"',SQ004 = '1',SQ002 = '"+SQ002+"' where SQ001 = '"+id+"' ";
			sess.createSQLQuery(sql1).executeUpdate();
			for(int i=0;i<list.size();i++){
				String QC001 = list.get(i).get("uuid").toString();
				String QC002 = list.get(i).get("Queryname").toString();
				String QC003 = list.get(i).get("Querysql").toString().replace("'", "|");
				String QC004 = list.get(i).get("Gridstring").toString();
				try {
					PreparedStatement ps = conn.prepareStatement("insert into query_condition(QC001,QC002,QC003,SQ001,QC004) values (?,?,?,?,?)");
					ps.setString(1, QC001);
					ps.setString(2, QC002);
					ps.setString(3, QC003);
					ps.setString(4, SQ001);
					ps.setString(5, QC004);
					ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(type.equals("2")){
			String sql2 = "delete FROM query_condition WHERE SQ001 = '"+id+"' ";
			sess.createSQLQuery(sql2).executeUpdate();
			String sql1 = "update statistics_query set SQ001 = '"+SQ001+"',SQ004 = '2',SQ002 = '"+SQ002+"' where SQ001 = '"+id+"' ";
			sess.createSQLQuery(sql1).executeUpdate();
			String QC001 = UUID.randomUUID().toString();
			String transverseStatisticsID = map.get("transverseStatisticsID");
			String verticalStatisticsID = map.get("verticalStatisticsID");
			String QC003_tran = transverseStatisticsID+"@";
			String QC003_vert = verticalStatisticsID+"@";
			for(int i = 0; i < tran_length; i++){
				String tran_i = map.get("tran"+i);
				if(tran_i !=null && tran_i.length()>0){
					QC003_tran += tran_i+"@";
				}
			}
			QC003_tran = QC003_tran.substring(0,QC003_tran.length()-1);
			for(int i = 0; i < vert_length; i++){
				String vert_i = map.get("vert"+i);
				if(vert_i !=null && vert_i.length()>0){
					QC003_vert += vert_i+"@";
				}
			}
			QC003_vert = QC003_vert.substring(0,QC003_vert.length()-1);
			String QC003 = QC003_vert + "|" + QC003_tran ; 
			String sql3 = "insert into query_condition(QC001,QC002,QC003,SQ001) values ('"+QC001+"','"+SQ002+"','"+QC003+"','"+SQ001+"')";
			sess.createSQLQuery(sql3).executeUpdate();
		}
		//this.setMainMessage("保存成功");
		this.closeCueWindow("SaveStatistics");
		//this.getExecuteSG().addExecuteCode("reloadpp();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public void closeCueWindowByYes(String arg0) {
		// TODO Auto-generated method stub
		this.setShowMsg(true);
		this.addNextBackFunc(NextEventValue.YES, "parent.odin.ext.getCmp('"+arg0+"').close();");
	}
}