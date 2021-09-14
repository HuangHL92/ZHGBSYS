package com.insigma.siis.local.comm.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.sys.comm.CommQueryBS;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.pagemodel.comm.DataPermissionUtil;

public class CommAreaManager {
	
	/**
	 * 当该ID所处行政区小于level时，该方法用来获取其之上的等于该level的行政区划
	 * @param id
	 * @param level
	 * @return
	 * @throws AppException 
	 */
	@SuppressWarnings("unchecked")
	private String[] getRealLevelNodeInfo(String id,String level) throws AppException{
		String[] area = new String[2];
		///2017.05.09
		/**String sql = "select * from aa26 a where a.aab301='"+id+"'";
		CommQueryBS cq = new CommQueryBS();
		List<HashMap<String, String>> list = (List<HashMap<String, String>>) cq.query(sql, "SQL", -1, 15).getData();
		if(list!=null&&list.size()>0){
			HashMap<String, String> map = list.get(0);
			if(level!=null && Integer.parseInt(level)< Integer.parseInt(map.get("aaa147"))){
				return getRealLevelNodeInfo(map.get("aaa148"),level);
			}else{
				//area[0] = id;
				//area[1] = text;
				area[0] = id;
				area[1] = map.get("aaa146");
				return area;
			}
		}**/
		return area;
	}

	/**
	 * 行政区划搜索
	 * @param level
	 * @param node
	 * @return
	 * @throws AppException 
	 */
	@SuppressWarnings("unchecked")
	public String query(String level,String node) throws AppException{
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");  
		if(node.indexOf(",")>0 || node.indexOf(":")>0){
			String[] areas = node.split(",");
			for(int i=0;i<areas.length;i++){
				String[] area = areas[i].split(":");
				area = getRealLevelNodeInfo(area[0],level);
				if(i>0){
					buffer.append(",");
				}
				if(buffer.indexOf(area[0])<0){
					buffer.append("{\"text\" :\"" + area[1]+ "\" ,\"id\" :\"" + area[0]+ "\" ,\"cls\" :\"folder\"}");
				}
			}
		}else{
			//String sql = "select * from aa26 a where a.aaa148='"+(node.equals("0")?GlobalNames.udSysConfig.get("UD_AREA_ID"):node)+"'";
			String sql = "select * from smt_group a where a.parentid='"+(node.equals("0")?GlobalNames.udSysConfig.get("UD_AREA_ID"):node)+"'";
			if(level!=null&&!level.equals("")){
				sql += " and a.rate<='"+level+"'";
			}    
			sql += " order by a.groupid ";
			
			CommQueryBS cq = new CommQueryBS();   
			List<HashMap<String, String>> list = (List<HashMap<String, String>>) cq.query(sql, "SQL", -1, 15).getData();
			List<String> nodeIds = new ArrayList<String>();
			int i = 0;                
			for(Iterator<HashMap<String, String>> it = list.iterator();it.hasNext();i++){
				HashMap<String, String> map = it.next();
				String aab301 = map.get("groupid");
				String aaa146 = map.get("description");
				if(nodeIds.contains(aab301)) {
					continue;
				}else {
					nodeIds.add(aab301);
				}
				if(i>0){
					buffer.append(",");
				}
				buffer.append("{\"text\" :\"" + aaa146+ "\" ,\"id\" :\"" + aab301+ "\" ,\"cls\" :\"folder\"}");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	/**
	 * 根据村名进行行政区划的搜索
	 * @param level 级别
	 * @param key 搜索的行政区划名
	 * @return
	 * @throws AppException
	 */
	public PageQueryData queryByKeywords(String level,String key)throws AppException{
		/**String aaa148 = DataPermissionUtil.getCueUserAAA148(); //获得该用户可见的最上级行政区划
		String sql = "select distinct a.* from aaa1 t,aa26 a where (a.aab301 in("+aaa148+") or t.aaa148 in("+aaa148+")) and a.aab301 = t.aab301";
		if(level!=null&&!level.equals("")){
			sql += " and a.aaa147<='"+level+"'";
		}
		sql += " and a.aaa146 ='"+key+"' ";
		CommQueryBS cq = new CommQueryBS();   
		PageQueryData queryData = cq.query(sql, "SQL", -1, 15);
		return queryData;**/
		return null;
	}
	
}
