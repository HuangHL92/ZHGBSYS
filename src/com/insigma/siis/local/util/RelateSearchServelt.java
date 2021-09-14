package com.insigma.siis.local.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;

public class RelateSearchServelt extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public RelateSearchServelt() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyword = URLDecoder.decode(request.getParameter("keyword")+"", "UTF-8");
		String dept = request.getParameter("dept");
		String card = request.getParameter("card");
		String usertype = request.getParameter("usertype");
		List<HashMap<String, String>> listData=getListDate(keyword,dept,card,usertype);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");//设置浏览器按utf-8格式解析
		if(listData !=null && listData.size()>0){			
			response.getWriter().write("1@"+toJson(listData).toString());//发送响应
		}else{
			response.getWriter().write("2@error");//发送响应
		}
	}
	
	/**
	 * 智能搜索提示--获得关键字
	 * @param keyword
	 * @param dept
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getListDate(String keyword,String dept,String card,String usertype){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HBSession sess = HBUtil.getHBSession();
		String sql =" select distinct a.a0101,a.a0192a,a.a0184,a.a0000 from a01 a  "
				+ " inner join a02 b on a.a0000=b.a0000 and b.a0255='1' "
				+ " where (a.a0101 like '"+keyword.toUpperCase()+"%' or a.a0102 like '"+keyword.toUpperCase()+"%')";
		//非超级管理员
		if(usertype==null||!"system".equals(usertype)){
			sql+=" and b.a0201b in (select b0111 from competence_userdept where userid ='"+dept+"')";
		}
		if(card!=null && !"".equals(card.trim())){
			sql+=" and a.a0184 like '"+card+"%'";
		}
		List<Object[]> a01list = sess.createSQLQuery(sql).list();
		if(a01list != null && a01list.size()>20){
			for( int i = 0;i<10;i++ ){
				Object[] a01temp = a01list.get(i);
				HashMap<String, String> map =new HashMap<String, String>();
				map.put("size", "11");
				map.put("xmgl", a01temp[0]+"#9#"+a01temp[1]+"#9#"+(a01temp[2]==null?"":a01temp[2])+"#9#"+a01temp[3]);
				list.add(map);
			}
			HashMap<String, String> map =new HashMap<String, String>();
			map.put("size", "11");
			map.put("xmgl", "......#9#......#9#......#9#......");
			list.add(map);
		}else{
			for(Object[] a01:a01list){
				HashMap<String, String> map =new HashMap<String, String>();
				map.put("size", a01list.size()+"");
				map.put("xmgl", a01[0]+"#9#"+a01[1]+"#9#"+(a01[2]==null?"":a01[2])+"#9#"+a01[3]);
				list.add(map);
			}
		}
	
		return list;
	}
	
	
	public StringBuffer toJson(List<HashMap<String, String>> list){
		StringBuffer ss=new StringBuffer();
		if(list==null||list.size()==0){
			return ss;
		}
		ss.append("[");
		for(HashMap<String,String> map:list){
			ss.append("{");
	        for (String key : map.keySet()) {
	        	ss.append("\"").append(key).append("\":\"").append(map.get(key))
	                    .append("\"").append(",");
	        }
	        ss.deleteCharAt(ss.length() - 1);
	        ss.append("},");
		}
		ss.deleteCharAt(ss.length() - 1);
		ss.append("]");
		return ss;
	}
}
