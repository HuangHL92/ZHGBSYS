package com.insigma.siis.local.pagemodel.dataverify;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ImpRefeshServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("dorefresh")) {//页面刷新
			dorefresh(request, response);
		} else if (method.equals("query")) {//页面刷新
			query(request, response);
		}
		
	} 

	

	private void query(HttpServletRequest request, HttpServletResponse response) {
		HBSession sess = HBUtil.getHBSession();
		String uuid = request.getParameter("uuid");
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
        JSONObject jsonObject = new JSONObject();  
        JSONObject Object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
		try {
			conn = sess.connection();
			pst = conn.prepareStatement("select * from imp_process where imprecordid='" +uuid+"'  order by PROCESS_TYPE asc");
			rs = pst.executeQuery();
			while(rs.next()){
				String name = rs.getString("PROCESS_NAME");
				String status = rs.getString("PROCESS_STATUS");
				String info = rs.getString("PROCESS_INFO");
		        jsonObject.put("name", name); 
		        jsonObject.put("status", status);
		        jsonObject.put("info", info);
		        jsonArray.add(jsonObject);  // a piece of data assembled successful
		        
			}
			Object.element("datas", jsonArray);
			CommonQueryBS.systemOut(Object.toString());  //debugging ...
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pst != null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		PrintWriter out = null;
        try {
        	out = response.getWriter();
            out = response.getWriter();
            out.write(Object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }

        }
		
	}



	private void dorefresh(HttpServletRequest request,
			HttpServletResponse response) {
		HBSession sess = HBUtil.getHBSession();
		String uuid = request.getParameter("uuid");
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map map2= new HashMap();
		Map<String, Map<String, String>> map= new HashMap<String, Map<String, String>>();//create a map for transferring to client 
		try {
			conn = sess.connection();
			pst = conn.prepareStatement("select * from imp_process t where imprecordid = '" + uuid + "'  order by PROCESS_TYPE asc");
			rs = pst.executeQuery();
			while(rs.next()){
				Map<String, String> map1= new HashMap<String, String>();
				map1.put("info", rs.getString("PROCESS_INFO")!=null? rs.getString("PROCESS_INFO").replaceAll(":", "："):"");
				map1.put("status", rs.getString("process_status"));
 				map.put("type"+rs.getString("PROCESS_TYPE"), map1);// assemble success
			}
			map2.put("data", map);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pst != null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		JSONObject jsonObject = JSONObject.fromObject(map2); //change into json String
        
        try {
            String outStr = jsonObject.toString();
            CommonQueryBS.systemOut(outStr);
            response.getOutputStream().write(outStr.toString().getBytes("GBK"));  
            response.setContentType("text/json; charset=GBK"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	 
	    
}
