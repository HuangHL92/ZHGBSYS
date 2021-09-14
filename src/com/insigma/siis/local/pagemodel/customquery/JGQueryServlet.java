package com.insigma.siis.local.pagemodel.customquery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class JGQueryServlet extends HttpServlet {
	private static int rownum = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map mapCodeValue =new HashMap();//用于存放从任免表打开的树的path
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method"); 
		String queryName = request.getParameter("queryName");
		String queryNum = request.getParameter("queryNum");
		String codeType = request.getParameter("codeType");
		String codeValue = request.getParameter("codeValue");
		String codeName = request.getParameter("codeName");
		String sourceFalg = request.getParameter("sourceFalg");
		if("JGQuery".equals(method)){
			treeQueryName(request,response,queryName,queryNum);
		}
		if("JGQuery_jgxx".equals(method)){//机构信息界面 机构定位
			treeQueryName_jgxx(request,response,queryName,queryNum);
		}
		if("JGQuery_xzcc".equals(method)){//现职级、现职务层次   职位定位
			treeQueryName_xzcc(request,response,codeType,codeValue);
		}
	}
	public String treeQueryName(HttpServletRequest request, HttpServletResponse response,String queryName,String queryNum) throws IOException{
		List<String> path_arr = treeSearchDeptName(queryName,queryNum);
		JSONArray jsonArray=JSONArray.fromObject(path_arr);
		String result=jsonArray.toString();
		PrintWriter writer=response.getWriter();
		writer.write(result);//返回json数组
		writer.flush();
		writer.close();
		//response.getWriter().print("{success:true,result:[{'path':'" + path + "'}]}");
		return "success"; 
	}
	
	public String treeQueryName_jgxx(HttpServletRequest request, HttpServletResponse response,String queryName,String queryNum) throws IOException{
		List<String> path_arr = treeSearchDeptName_jgxx(queryName,queryNum);
		JSONArray jsonArray=JSONArray.fromObject(path_arr);
		String result=jsonArray.toString();
		PrintWriter writer=response.getWriter();
		writer.write(result);//返回json数组
		writer.flush();
		writer.close();
		//response.getWriter().print("{success:true,result:[{'path':'" + path + "'}]}");
		return "success"; 
	}
	public List<String> treeSearchDeptName_jgxx(String queryName,String queryNum) {
		queryName=queryName.replace("\'", "");
		String userid=SysUtil.getCacheCurrentUser().getId();
		 List<String> path_list = new ArrayList<String>() ;//返回值
		//获取用户权限范围内的最高级别单位的id
		List<HashMap> listH =null;
		try {
			if(DBType.ORACLE==DBUtil.getDBType()){
				String sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+userid+"' order by length(b0111) asc) where ROWNUM =1";
				listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				String sql="select b0111 from competence_userdept t where userid='"+userid+"' order by length(b0111) asc limit 1";
				listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			}
		}catch(AppException e){
			e.printStackTrace();
		}
		String node="";
		int nodelength=0;
		if(listH!=null&&listH.size()>0){
			node=(String)listH.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
			//node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
			nodelength=node.length();
		}else{// 没有查询结果
			path_list.add("0");
			path_list.add(""+listH.size());
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "select t.b0111 from b01 t ,competence_userdept s where t.b0111=s.b0111 and s.userid='"+userid+"' and (t.b0101 like '%"+queryName+"%' or b0114='"+queryName+"') order by t.b0111"; 
        List<Object> list = sess.createSQLQuery(sql).list();
       
		
		Integer querynum = Integer.parseInt(queryNum);
		if (list != null && list.size() > 0) {
			if(querynum == list.size()){ //最后一个查询结果
				path_list.add("2");
				path_list.add(""+list.size());
			}else if(querynum>list.size()){//  请求速度太快，参数异常
				path_list.add("3");
				path_list.add(""+list.size());
			} else{//有查询结果
				path_list.add("1");
				path_list.add(""+list.size());
				Object pp = list.get(querynum);
				String path1 = pp.toString();
				//机构树可能存在多个显示的根结点，重新生成当前节点(path1)的根结点
				node=path1.substring(0, node.length());
				String path = "0/-1/"+node;
				if(path1.length() < nodelength+1){
					path_list.add(path);
				}else{
					//String[] path1_arr = path1.substring(8,path1.length()).split("\\.");
					String[] path1_arr = path1.substring(nodelength+1,path1.length()).split("\\.");
					String truepath = path;
					//String path2 = "001.001";
					String path2=node;//显示的根结点
					for(int j = 0; j < path1_arr.length; j ++){
						path2 += "."+path1_arr[j];//生成当前节点
						if(path2.length()>=nodelength){//当前节点长度大于等于显示的根结点长度
							truepath += "/" + path2;
						}
					}
					path_list.add(truepath);
				}
			}
		}else{// 没有查询结果
			path_list.add("0");
			path_list.add(""+list.size());
		}
		return path_list;
	}
	public String treeQueryName_xzcc(HttpServletRequest request, HttpServletResponse response,String code_type,String code_value) throws IOException{
		if("orgTreeJsonData".equals(code_type)) {
			List<String> path_arr = treeSearchDeptName_xzcc_orgTreeJsonData(code_type,code_value);
			StringBuffer curPath=new StringBuffer("");
			for (int i = path_arr.size()-1; i > -1; i--) {
				if("-1".equals(path_arr.get(i))){
					curPath.append("/"+path_arr.get(i));
					continue ;
				}else if (i >0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}else if(i == 0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}
			}
			List lista=new ArrayList();
			lista.add(curPath.toString());
			System.out.println("curPath.toString()--------"+curPath.toString());
			JSONArray jsonArray=JSONArray.fromObject(lista);
			String result=jsonArray.toString();
			PrintWriter writer=response.getWriter();
			writer.write(result);//返回json数组
			writer.flush();
			writer.close();
			return "success"; 
		}else if("ZB122".equals(code_type) || "GB6864".equals(code_type)){
			List<String> path_arr = treeSearchDeptName_xzcc(code_type,code_value);
			StringBuffer curPath=new StringBuffer("");
			for (int i = path_arr.size()-1; i > -1; i--) {
				if("-1".equals(path_arr.get(i))){
					curPath.append("/"+path_arr.get(i));
					continue ;
				}else if (i >0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}else if(i == 0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}
			}
			System.out.println(curPath.toString());
			List lista=new ArrayList();
			lista.add(curPath.toString());
			JSONArray jsonArray=JSONArray.fromObject(lista);
			String result=jsonArray.toString();
			PrintWriter writer=response.getWriter();
			writer.write(result);//返回json数组
			writer.flush();
			writer.close();
			return "success"; 
		}else if("ZB64".equals(code_type) || "GB16835".equals(code_type)){
			List<String> path_arr = treeSearchDeptName_xzcc(code_type,code_value);
			StringBuffer curPath=new StringBuffer("");
			for (int i = path_arr.size()-1; i > -1; i--) {
				if("-1".equals(path_arr.get(i))){
					curPath.append("/"+path_arr.get(i));
					continue ;
				}else if (i >0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}else if(i == 0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}
			}
			System.out.println(curPath.toString());
			List lista=new ArrayList();
			lista.add(curPath.toString());
			JSONArray jsonArray=JSONArray.fromObject(lista);
			String result=jsonArray.toString();
			PrintWriter writer=response.getWriter();
			writer.write(result);//返回json数组
			writer.flush();
			writer.close();
			return "success"; 
		}else {
			List<String> path_arr = treeSearchDeptName_xzcc(code_type,code_value);
			StringBuffer curPath=new StringBuffer("");
			for (int i = path_arr.size()-1; i > -1; i--) {
				if("-1".equals(path_arr.get(i))){
					curPath.append("/"+path_arr.get(i));
					continue ;
				}else if (i >0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}else if(i == 0){
					curPath.append("/"+path_arr.get(i)+"|true");
					continue ;
				}
			}
			System.out.println(curPath.toString());
			List lista=new ArrayList();
			lista.add(curPath.toString());
			JSONArray jsonArray=JSONArray.fromObject(lista);
			String result=jsonArray.toString();
			PrintWriter writer=response.getWriter();
			writer.write(result);//返回json数组
			writer.flush();
			writer.close();
			return "success"; 
		}
	}
	public List<String> treeSearchDeptName_xzcc(String code_type,String code_value) {
		 List<HashMap> listH =null;
		 List<String> lists =new ArrayList<String>();
		String sql = "select sub_code_value,code_value from code_value where  code_status='1' and code_type='"+code_type+"' and code_value ='"+code_value+"'";
		try {
			listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			if(listH.size()>0){
				HashMap hashMap = listH.get(0);
				String sub_code_value =hashMap.get("sub_code_value")+"";
				lists.add(code_value);
				lists.add(sub_code_value);
				if( !"-1".equals(sub_code_value)){
					treeSearchDeptName_xzcc_Per(code_type,sub_code_value,lists);
				}
			}
		} catch (AppException e) {
			System.out.println("树结构定位：无该人此职位信息！");
			e.printStackTrace();
		}
		return lists;
	}
	public List<String> treeSearchDeptName_xzcc_orgTreeJsonData(String code_type,String code_value) {
		String sql="select b0121 from b01 where b0111='"+code_value+"'";
		 List<HashMap> listH =null;
		 List<String> lists =new ArrayList<String>();
		try {
			listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			if(listH.size()>0){
				HashMap hashMap = listH.get(0);
				String b0121 =hashMap.get("b0121")+"";
				lists.add(code_value);
				lists.add(b0121);
				if( !"-1".equals(b0121)){
					treeSearchDeptName_xzcc_orgTreeJsonData_Per(code_type,b0121,lists);
				}
			}
		} catch (AppException e) {
			System.out.println("树结构定位：无该人此职位信息！");
			e.printStackTrace();
		}
		return lists;
	}
	public List<String> treeSearchDeptName_xzcc_Per(String code_type,String sub_code_value,List list) {
		 List<HashMap> listH =null;
		String sql = "select sub_code_value,code_value from code_value where  code_status='1'  and code_type='"+code_type+"' and code_value ='"+sub_code_value+"'";
		try {
			listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			HashMap hashMap = listH.get(0);
			String s =hashMap.get("sub_code_value")+"";
			list.add(s);
			if( !"-1".equals(s)){
				treeSearchDeptName_xzcc_Per(code_type,s, list);
			}
		} catch (AppException e) {
			System.out.println("树结构定位：无该人此职位信息！");
		}
		return list;
	}
	public List<String> treeSearchDeptName_xzcc_orgTreeJsonData_Per(String code_type,String code_value,List list) {
		 List<HashMap> listH =null;
		 String sql="select b0121 from b01 where b0111='"+code_value+"'";
		try {
			listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			HashMap hashMap = listH.get(0);
			String s =hashMap.get("b0121")+"";
			list.add(s);
			if( !"-1".equals(s)){
				treeSearchDeptName_xzcc_orgTreeJsonData_Per(code_type,s, list);
			}
		} catch (AppException e) {
			System.out.println("树结构定位：无该人此职位信息！");
		}
		return list;
	}
	public List<String> treeSearchDeptName(String queryName,String queryNum) {
		String userid=SysUtil.getCacheCurrentUser().getId();
		 List<String> path_list = new ArrayList<String>() ;//返回值
		//获取用户权限范围内的最高级别单位的id
		List<HashMap> listH =null;
		try {
			if(DBType.ORACLE==DBUtil.getDBType()){
				String sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+userid+"' order by length(b0111) asc) where ROWNUM =1";
				listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				String sql="select b0111 from competence_userdept t where userid='"+userid+"' order by length(b0111) asc limit 1";
				listH=CommonQueryBS.getQueryInfoByManulSQL(sql);
			}
		}catch(AppException e){
			e.printStackTrace();
		}
		String node="";
		int nodelength=0;
		if(listH!=null&&listH.size()>0){
			node=(String)listH.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
			//node=node.substring(0, node.length()-4);//获取用户权限范围内的最高级别单位的上一级单位id
			nodelength=node.length();
		}else{// 没有查询结果
			path_list.add("2");
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "select t.b0111 from b01 t ,competence_userdept s where t.b0111=s.b0111 and s.userid='"+userid+"' and t.b0101 like '%"+queryName+"%' order by t.b0111"; 
       List<Object> list = sess.createSQLQuery(sql).list();
      
		
		Integer querynum = Integer.parseInt(queryNum);
		if (list != null && list.size() > 0) {
			if(querynum == list.size()){ //最后一个查询结果
				path_list.add("2");
				
			}/*else if(querynum>list.size()){//  请求速度太快，参数异常
				path_list.add("3");
				path_list.add(""+list.size());
			}*/ else{//有查询结果
				path_list.add("1");
				
				Object pp = list.get(querynum);
				String path1 = pp.toString();
				//机构树可能存在多个显示的根结点，重新生成当前节点(path1)的根结点
				node=path1.substring(0, node.length());
				String path = "0/-1/"+node;
				if(path1.length() < nodelength+1){
					path_list.add(path);
				}else{
					//String[] path1_arr = path1.substring(8,path1.length()).split("\\.");
					String[] path1_arr = path1.substring(nodelength+1,path1.length()).split("\\.");
					String truepath = path;
					//String path2 = "001.001";
					String path2=node;//显示的根结点
					for(int j = 0; j < path1_arr.length; j ++){
						path2 += "."+path1_arr[j];//生成当前节点
						if(path2.length()>=nodelength){//当前节点长度大于等于显示的根结点长度
							truepath += "/" + path2;
						}
					}
					path_list.add(truepath);
				}
			}
		}else{// 没有查询结果
			path_list.add("2");
		}
		return path_list;
		
//		HBSession sess = HBUtil.getHBSession();
//		String sql = "select b0111 from b01 where b0101 like '%"+queryName+"%' order by b0111"; 
//        List<Object> list = sess.createSQLQuery(sql).list();
//        List<String> path_list = new ArrayList<String>() ;
//		String path = "0/-1/001.001";
//		Integer querynum = Integer.parseInt(queryNum);
//		if (list != null && list.size() > 0) {
//			if(querynum == list.size()){
//				path_list.add("2");
//			}else{
//				path_list.add("1");
//				Object pp = list.get(querynum);
//				String path1 = pp.toString();
//				if(path1.length() < 8){			//根机构
//					path_list.add(path);
//				}else{
//					String[] path1_arr = path1.substring(8,path1.length()).split("\\.");
//					String truepath = path;
//					String path2 = "001.001";
//					for(int j = 0; j < path1_arr.length; j ++){
//						path2 += "."+path1_arr[j];
//						truepath += "/" + path2;
//					}
//					path_list.add(truepath);
//				}
//			}
//		}else{
//			path_list.add("2");
//		}
//		return path_list;
	}

}
