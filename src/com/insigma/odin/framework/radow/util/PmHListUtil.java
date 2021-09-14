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
 * ��Ҫ�ǽ�HList��ֵȥ�����PM��Ԫ����ȥ
 * @author Jinwei
 * @date 2009-10-27
 */
public class PmHListUtil {

	private PmHListUtil() {
	}

	/**
	 * ��PMHList�ĵ�һ����ѯ��� �Զ��PageModel��Ԫ����ȥ
	 * ���ݸ�ʽ��[{\"aae135\":\"false\",\"aac003\":\"\",\"rewage\":\"\",\"household\":\"\",\"household_combo\":\"����ѡ��...\",\"wlwgry\":\"\",\"wlwgry_combo\":\"����ѡ��...\",\"ylcblb\":\"\",\"ylcblb_combo\":\"����ѡ��...\",\"ybcblb\":\"\",\"ybcblb_combo\":\"����ѡ��...\"}]
	 * 
	 * @param hlist  PMHList�ĵ�һ����ѯ���
	 * @param pm	 ҳ��ģ��
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
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * 
	 * @param pm  ҳ��ģ��
	 * @param sql
	 *            ��ѯ��sql
	 * @param sqlType
	 *            ��ѯ�����HQL��SQL
	 * @param start
	 *            ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit
	 *            ȡ����������Ҫ���ڷ�ҳʱ
	 * @return ��Ų�����Ķ��󼯺ϵķ�ҳ���ݵ�PageQueryData���󣬼���ȫ�����������ڴ��ҳ�����ֺõ����ݷ���PageQueryData������
	 * @throws RadowException
	 */
	public static PageQueryData pageQuery(PageModel pm, String sql,
			String sqlType, int start, int limit) throws RadowException {
		
		//��ҳ����ȫ������
		/*String sort= request.getParameter("sort");//Ҫ���������--���趨�壬ext�Զ���
        String dir= request.getParameter("dir");//Ҫ����ķ�ʽ--���趨�壬ext�Զ���
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
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * 
	 * @param pm  ҳ��ģ��
	 * @param sql
	 *            ��ѯ��sql
	 * @param sqlType
	 *            ��ѯ�����HQL��SQL
	 * @param start
	 *            ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit
	 *            ȡ����������Ҫ���ڷ�ҳʱ
	 * @param map
	 *            �Զ���ҳ�����ݣ�SQL��ѯ������ʱ
	 * @return ��Ų�����Ķ��󼯺ϵķ�ҳ���ݵ�PageQueryData���󣬼���ȫ�����������ڴ��ҳ�����ֺõ����ݷ���PageQueryData������
	 * @throws RadowException
	 */
	public static PageQueryData pageQuery(PageModel pm, String sql,
			String sqlType, int start, int limit,HashMap<String,List<String>> map) throws RadowException {
		
		//��ҳ����ȫ������
		/*String sort= request.getParameter("sort");//Ҫ���������--���趨�壬ext�Զ���
        String dir= request.getParameter("dir");//Ҫ����ķ�ʽ--���趨�壬ext�Զ���
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
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * 
	 * @param pm  ҳ��ģ��
	 * @param sql
	 *            ��ѯ��sql
	 * @param sqlType
	 *            ��ѯ�����HQL��SQL
	 * @param start
	 *            ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit
	 *            ȡ����������Ҫ���ڷ�ҳʱ
	 * @return ��Ų�����Ķ��󼯺ϵķ�ҳ���ݵ�PageQueryData���󣬼���ȫ�����������ڴ��ҳ�����ֺõ����ݷ���PageQueryData������
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
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲�����ʹ���첽����ͬʱ���б�ҳ���ݺ������ݵĲ�ѯ��ʹ��ѯ���٣�
	 * 
	 * @param pm  ҳ��ģ��
	 * @param sql
	 *            ��ѯ��sql
	 * @param sqlType
	 *            ��ѯ�����HQL��SQL
	 * @param start
	 *            ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit
	 *            ȡ����������Ҫ���ڷ�ҳʱ
	 * @return ��Ų�����Ķ��󼯺ϵķ�ҳ���ݵ�PageQueryData���󣬼���ȫ�����������ڴ��ҳ�����ֺõ����ݷ���PageQueryData������
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
	 * ��һ��List��Ԫ��ΪJava Bean����ļ��Ͻ����ڴ��з�ҳ������PageQueryData����
	 * ������sql���ʱ����Ѿ��ֺã����ǲ�ȫ������ʹ�ú�̨�����ҳ
	 * 
	 * @param entityList   javaBen�Ķ���ļ���
	 * @param start			��ʼ��Ŀ
	 * @param limit			������Ŀ
	 * @return   һ�����javaBen��List�ļ��Ͻ����ڴ��ҳ������PageQueryData������
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
			throw new RadowException("ת��ʱ�����쳣��");
		}
		pq.setTotalCount(entityList.size());
		return pq;
	}
	
	/**
	 * ��һ��List��Ԫ��ΪJava Bean����ļ�������PageQueryData����
	 * 
	 * @param entityList    javaBen�Ķ���ļ���
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
			throw new RadowException("ת��ʱ�����쳣��");
		}
		pq.setTotalCount(entityList.size());
		return pq;
	}
	
	/**
	 * ��ȡһ��HList����
	 * @param name �硰div_1����"div_1.grid1" Ϊnullʱ����һ��û�г�ʼ���ݵ�HList����
	 * @param pm ��ǰPageModel
	 * @return  ����name�õ���Ԫ�ؼ��ϲ�����PMHList����
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
