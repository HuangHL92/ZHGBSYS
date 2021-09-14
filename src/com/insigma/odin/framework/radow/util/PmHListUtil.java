package com.insigma.odin.framework.radow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.data.PMHList;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.sys.comm.CommQueryBS;

/**
 * 主要是将HList的值去出来填到PM的元素里去
 * @author Jinwei
 * @date 2009-10-27
 */
public class PmHListUtil {

	private PmHListUtil() {
	}

	/**
	 * 将PMHList的第一条查询结果 自动填到PageModel的元素里去
	 * 数据格式如[{\"aae135\":\"false\",\"aac003\":\"\",\"rewage\":\"\",\"household\":\"\",\"household_combo\":\"请您选择...\",\"wlwgry\":\"\",\"wlwgry_combo\":\"请您选择...\",\"ylcblb\":\"\",\"ylcblb_combo\":\"请您选择...\",\"ybcblb\":\"\",\"ybcblb_combo\":\"请您选择...\"}]
	 * 
	 * @param hlist  PMHList的第一条查询结果
	 * @param pm	 页面模型
	 * @throws RadowException
	 */
	public static void autoFillPm(PMHList hlist, PageModel pm)
			throws RadowException {
		try {
			String id = hlist.getId();
			PageElement root = null;
			HashMap<String, PageElement> map = null;
			if(id!=null && !id.equals("")){
				root = pm.getPageElement(id);
				if(root.getType().equals(ElementType.NORMAL)){
					if (pm.getWebPage() == null) {
						pm.getPageModelParser().parse();
					}
					map = root.getChildElements();
				}else if(root.getType().equals(ElementType.GRID) || root.getType().equals(ElementType.EDITORGRID)){
					root.setValue(hlist.getJSONArray().toString());
				}
			}else{
				root = pm.getWebPage();
				if(root==null){
					pm.getPageModelParser().parse();
					root = pm.getWebPage();
				}
				map = root.getChildElements();
			}
			if(map!=null){
				Set<String> names = map.keySet();
				for (Iterator<String> it = names.iterator(); it.hasNext();) {
					String name = it.next();
					String value = hlist.getString(name);
					if(value!=null){
						map.get(name).setValue(value);
					}
				}
			}
		} catch (Exception e) {
			throw new RadowException(e.getMessage(), e);
		}
	}

	/**
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * 
	 * @param pm  页面模型
	 * @param sql
	 *            查询的sql
	 * @param sqlType
	 *            查询的类别，HQL或SQL
	 * @param start
	 *            从那条记录开始，-1为取所有记录
	 * @param limit
	 *            取多少条，主要用在分页时
	 * @return 存放查出来的对象集合的分页数据的PageQueryData对象，即是全部查出后进行内存分页，将分好的数据放入PageQueryData对象中
	 * @throws RadowException
	 */
	public static PageQueryData pageQuery(PageModel pm, String sql,
			String sqlType, int start, int limit) throws RadowException {
		
		//分页，做全局排序
		/*String sort= request.getParameter("sort");//要排序的列名--无需定义，ext自动后传
        String dir= request.getParameter("dir");//要排序的方式--无需定义，ext自动后传
        String orderby = "";
        if(sort!=null&&!"".equals(sort)){
        	orderby = "order by "+sort+" "+dir;
        }*/
		
		
		
		try {
			CommQueryBS bs = new CommQueryBS();
			PageQueryData data = bs.query(sql, sqlType, start, limit);
			pm.setSelfDefResData(data);
			return data;
		} catch (AppException e) {
			throw new RadowException(e.getMessage(), e);
		}
	}
	
	/**
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * 
	 * @param pm  页面模型
	 * @param sql
	 *            查询的sql
	 * @param sqlType
	 *            查询的类别，HQL或SQL
	 * @param start
	 *            从那条记录开始，-1为取所有记录
	 * @param limit
	 *            取多少条，主要用在分页时
	 * @param map
	 *            自定义页面数据，SQL查询不出来时
	 * @return 存放查出来的对象集合的分页数据的PageQueryData对象，即是全部查出后进行内存分页，将分好的数据放入PageQueryData对象中
	 * @throws RadowException
	 */
	public static PageQueryData pageQuery(PageModel pm, String sql,
			String sqlType, int start, int limit,HashMap<String,List<String>> map) throws RadowException {
		
		//分页，做全局排序
		/*String sort= request.getParameter("sort");//要排序的列名--无需定义，ext自动后传
        String dir= request.getParameter("dir");//要排序的方式--无需定义，ext自动后传
        String orderby = "";
        if(sort!=null&&!"".equals(sort)){
        	orderby = "order by "+sort+" "+dir;
        }*/
		
		
		
		try {
			CommQueryBS bs = new CommQueryBS();
			PageQueryData data = bs.query(sql, sqlType, start, limit,map);
			pm.setSelfDefResData(data);
			return data;
		} catch (AppException e) {
			throw new RadowException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * 
	 * @param pm  页面模型
	 * @param sql
	 *            查询的sql
	 * @param sqlType
	 *            查询的类别，HQL或SQL
	 * @param start
	 *            从那条记录开始，-1为取所有记录
	 * @param limit
	 *            取多少条，主要用在分页时
	 * @return 存放查出来的对象集合的分页数据的PageQueryData对象，即是全部查出后进行内存分页，将分好的数据放入PageQueryData对象中
	 * @throws RadowException
	 */
	public static PageQueryData pageQueryNoCount(PageModel pm, String sql,
			String sqlType, int start, int limit,int totalCount) throws RadowException {
		try {
			CommQueryBS bs = new CommQueryBS();
			PageQueryData data = bs.queryNoCount(sql, sqlType, start, limit, totalCount);
			pm.setSelfDefResData(data);
			return data;
		} catch (AppException e) {
			throw new RadowException(e.getMessage(), e);
		}
	}
	
	/**
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作（使用异步方法同时进行本页数据和总数据的查询，使查询提速）
	 * 
	 * @param pm  页面模型
	 * @param sql
	 *            查询的sql
	 * @param sqlType
	 *            查询的类别，HQL或SQL
	 * @param start
	 *            从那条记录开始，-1为取所有记录
	 * @param limit
	 *            取多少条，主要用在分页时
	 * @return 存放查出来的对象集合的分页数据的PageQueryData对象，即是全部查出后进行内存分页，将分好的数据放入PageQueryData对象中
	 * @throws RadowException
	 */
	public static PageQueryData pageQueryByAsynchronous(PageModel pm, String sql,
			String sqlType, int start, int limit) throws RadowException {
		try {
			CommQueryBS bs = new CommQueryBS();
			PageQueryData data = bs.queryByAsynchronous(sql, sqlType, start, limit);
			pm.setSelfDefResData(data);
			return data;
		} catch (AppException e) {
			throw new RadowException(e.getMessage(), e);
		}
	}
	
	

	/**
	 * 把一个List里元素为Java Bean对象的集合进行内存中分页并生成PageQueryData对象，
	 * 即不是sql查的时候就已经分好，而是查全部，在使用后台代码分页
	 * 
	 * @param entityList   javaBen的对象的集合
	 * @param start			起始数目
	 * @param limit			限制数目
	 * @return   一个存放javaBen的List的集合进行内存分页并存入PageQueryData对象中
	 * @throws RadowException
	 * @throws AppException
	 */
	public static PageQueryData getPageQueryData(List<Object> entityList,
			int start, int limit) throws RadowException{
		PageQueryData pq = new PageQueryData();
		if (entityList == null || entityList.size() == 0) {
			try {
				pq.setData(HQuery.fromList(entityList));
			} catch (AppException e) {
				e.printStackTrace();
			}
			pq.setTotalCount(0);
			return pq;
		}
		
		List<Object> listByPage = new ArrayList<Object>();

		if (entityList.size() <= start) {
			return null;
		}

		if (entityList.size() <= start + limit) {
			for (int i = start; i < entityList.size(); i++) {
				listByPage.add(entityList.get(i));
			}
		}else {
			for (int i = start; i < start + limit; i++) {
				listByPage.add(entityList.get(i));
			}
		}
		try {
			pq.setData(HQuery.fromList(listByPage));
		} catch (AppException e) {
			throw new RadowException("转换时发生异常！");
		}
		pq.setTotalCount(entityList.size());
		return pq;
	}
	
	/**
	 * 把一个List里元素为Java Bean对象的集合生成PageQueryData对象，
	 * 
	 * @param entityList    javaBen的对象的集合
	 * @return              
	 * @throws RadowException
	 * @throws AppException
	 */
	public static PageQueryData getQueryData(List<Object> entityList) throws RadowException{
		PageQueryData pq = new PageQueryData();
		if (entityList == null || entityList.size() == 0) {
			try {
				pq.setData(HQuery.fromList(entityList));
			} catch (AppException e) {
				e.printStackTrace();
			}
			pq.setTotalCount(0);
			return pq;
		}
		try {
			pq.setData(HQuery.fromList(entityList));
		} catch (AppException e) {
			throw new RadowException("转换时发生异常！");
		}
		pq.setTotalCount(entityList.size());
		return pq;
	}
	
	/**
	 * 获取一个HList对象
	 * @param name 如“div_1”或"div_1.grid1" 为null时创建一个没有初始数据的HList对象
	 * @param pm 当前PageModel
	 * @return  根据name得到的元素集合并放入PMHList对象
	 * @throws RadowException 
	 */
	public static PMHList getPMHList(String name,PageModel pm) throws RadowException{
		PMHList hlist = null;
		if(name!=null && !name.equals("")){
			PageElement pe = pm.getPageElement(name);
			String type = pe.getType();
			List<HashMap<String, Object>> list = null;
			if(type.equals(ElementType.NORMAL)){
				list = new ArrayList<HashMap<String, Object>>();
				HashMap<String,Object> map = new HashMap<String,Object>();
				HashMap<String,PageElement> ce = pe.getChildElements();
				for(Iterator<String> it = ce.keySet().iterator();it.hasNext();){
					String childName = it.next();
					map.put(childName, ce.get(childName).getValue());
				}
				list.add(map);
			}else if(type.equals(ElementType.GRID) || type.equals(ElementType.EDITORGRID)){
				list = pe.getValueList();
			}
			hlist = new PMHList(JSONArray.fromObject(list));
			hlist.setId(name);
		}else{
			hlist = new PMHList();
		}
		return hlist;
	}

}
