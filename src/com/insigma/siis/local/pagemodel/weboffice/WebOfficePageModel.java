package com.insigma.siis.local.pagemodel.weboffice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.LinkedMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.comm.JsonCovert;

public class WebOfficePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX(){
		this.getExecuteSG().addExecuteCode("haveCJ();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static String expword() {
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createQuery("from WebOffice").list();
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/**
	 * smaple:String tree="[{id: 2,text: '儿子节点2',children: [{id: 3,text:'孙子节点',leaf: true}]},{id: 2,text: '儿子节点2',children: [{id: 3,text:'孙子节点',leaf: true}]}]";
	 * @return
	 * @throws RadowException
	 */
/*	@PageEvent("orgTreeJsonData")
	public int orgTreeJsonData() throws RadowException {
		*//** 获取root节点值 *//*
		// String node = this.getParameter("node");
		HBSession sess = HBUtil.getHBSession();
		StringBuffer sb_tree = new StringBuffer("[");
		String First = "select distinct table_code,col_lection_name from code_table_col where table_code in ('A01','A36')";//根目录
		String Second = "SELECT table_code,col_code,col_name from code_table_col where table_code in ('A01','A36')and ISUSE='1'";//子目录'1'表示字段有效
		List<Object[]> Second_ = sess.createSQLQuery(Second).list();
		List<Object[]> First_ = sess.createSQLQuery(First).list();
		*//** 第一级树 *//*
		for (int i = 0; i < First_.size(); i++) {
			Object[] First_object = First_.get(i);
			String TableName = First_object[0].toString();
			sb_tree.append(
					"{'id' :'" + First_object[0].toString() + "' ,'text' :'" + First_object[1].toString() + "',");
			sb_tree.append(" children:[");
			*//** 第二级树 *//*
			for (int j = 0; j < Second_.size(); j++) {
				Object[] Second_object = Second_.get(j);
				if (TableName.equals(Second_object[0].toString())) {
					sb_tree.append("{'text' :'" + Second_object[2].toString() + "' ,'id' :'"
							+ Second_object[1].toString() + "' ,'leaf' :true" + " ,'iconCls' :'leaf'}");
					if (j != Second_.size() - 1) {
						sb_tree.append(",");
					}
				}
			}
			sb_tree.append("]}");
			if (i != First_.size() - 1) {
				sb_tree.append(",");
			}
		}
		sb_tree.append("]");
		//sb_tree = sb_tree.substring(0,sb_tree.toString());
		sb_tree.deleteCharAt(sb_tree.length() - 4);
		System.out.println("========bbbbbbbbbbbb========"+sb_tree);
		String string = sb_tree.toString();
		this.setSelfDefResData(string);
		return EventRtnType.XML_SUCCESS;
	}*/
	
	
	@PageEvent("orgTreeJsonData")
	public int orgTreeJsonData() throws RadowException {
		/** 获取root节点值 */
		HBSession sess = HBUtil.getHBSession();
		//String First = "select distinct table_code,col_lection_name from code_table_col where table_code in ('A01','A36')and ISUSE='1'";//根目录
		//String First = "select distinct table_code,col_lection_name from code_table_col where  ISUSE='1' order by  table_code" ;//根目录
		//String First = "select distinct table_code,col_lection_name from code_table_col  order by  table_code" ;//根目录
		// 张献维 2018-10-31 限制显示信息集于信息项
		String First = "select table_code,table_name from code_table where islook='1' order by  table_code" ;//根目录
		//String First = "select distinct (table_code||'#'||col_data_type_should)AS table_code,col_lection_name from code_table_col  order by  table_code" ;//根目录
		List<Object[]> First_ = sess.createSQLQuery(First).list();
		JsonCovert js = new JsonCovert();
		List<Map> list =new ArrayList<Map>();
		//System.out.println("1========================="+First_.size());
		/** 第一级树 */
		for (int i = 0; i < First_.size(); i++) {
			Object[] First_object = First_.get(i);//A36, 家庭成员及社会关系信息集
			String TableName = First_object[0].toString();//A36
			Map map=new LinkedMap();
    		map.put("id", First_object[0].toString());//A36
    		map.put("text",First_object[1].toString());//id=A36, text=家庭成员及社会关系信息集
    		list.add(map);
        	map.put("children",getTableItems(TableName));
		}
		//System.out.println("2========================="+list.size());
		String jsonstr=js.writeUtilJSON(list);
		//jsonstr=jsonstr.replace("\"", "'").replace("'children'", "children");
		this.setSelfDefResData(jsonstr);
		return EventRtnType.XML_SUCCESS;
	}
	
	public List<Map> getTableItems(String tableName){
		HBSession sess = HBUtil.getHBSession();
		//String Second = "SELECT table_code,col_code,col_name from code_table_col where table_code='"+tableName+"' and islook='1'  order by  col_code";//子目录'1'信息集有效
		// 张献维 2018-10-31 限制显示信息集于信息项
		String Second = "SELECT table_code,col_code,col_name from code_table_col where table_code='"+tableName+"' and islook='1' order by  col_code";
		//String Second = "SELECT table_code,(col_code||'#'||col_data_type_should)AS col_code,col_name from code_table_col where table_code='"+tableName+"'  order by  col_code";//子目录'1'信息集有效
		//System.out.println("========================"+Second);
		List<Object[]> Second_ = sess.createSQLQuery(Second).list();
		

		List<Map> childrenlist =new ArrayList();
		for (int j = 0; j < Second_.size(); j++) {
			Object[] Second_object = Second_.get(j);//[A36, A3601, 人员姓名
			Map map1=new LinkedMap();
    		map1.put("text", Second_object[2].toString());//{text=人员姓名}
    		map1.put("id",Second_object[1].toString());//{text=人员姓名}
    		map1.put("leaf",true);
    		map1.put("iconCls","leaf");
    		childrenlist.add(map1);
		}
		return childrenlist;
	}
	
	@PageEvent("updatename")
	public int updatename(String text){
		HBSession sess = HBUtil.getHBSession();
		String[] split = text.split(",");
		String id=split[1];
		String filename=split[2];
		String sql="update weboffice set filename='"+filename+"' where id='"+id+"'";
		sess.createSQLQuery(sql).executeUpdate();
		this.setMainMessage("方案名已修改成功");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	@PageEvent("expWeboffice")
	public int expWordws(String type)throws AppException, RadowException{
		String Path="pages/weboffice/WebOffice_Setup.exe";
		Path=request.getSession().getServletContext().getRealPath(Path);
	    this.getPageElement("downfile").setValue(Path.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	

}
