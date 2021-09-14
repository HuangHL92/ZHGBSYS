package com.insigma.siis.local.pagemodel.comm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.util.SysUtil;

public class DataPermissionUtil {

	/**
	 * ��ȡ��ǰ�û��Ŀɼ��С��硢�򡢴����
	 * ��ʽΪ��'330800','330822123456'
	 * @return
	 */
	public static String getCueUserAAA148(){
		List<GroupVO> groups = SysUtil.getCacheCurrentUser().getUserGroups();
		StringBuffer aaa148 = new StringBuffer();
		if(groups!=null && groups.size()>0){
			for(Iterator<GroupVO> it = groups.iterator();it.hasNext();){
				GroupVO g = it.next();
				if(!aaa148.toString().equals("")){
					aaa148.append(",");
				}
				aaa148.append("'");
				aaa148.append(g.getId());
				aaa148.append("'");
			}
		}else{
			aaa148.append("'");
			aaa148.append(
					(String) HBUtil.getHBSession().
						createSQLQuery("select aaa005 from aa01 a where a.aaa001 ='AREA_ID' ")
							.list().iterator().next());
			aaa148.append("'");
		}
		return aaa148.toString();
	}
	
	/**
	 * ��ȡ��ǰ�û����鼯�϶���(����һ��List�������ŵ���Group��Id)
	 * @return List �����ŵ���Group��Id
	 */
	public static List<String> getCueUserGroupList(){
		List<String> list = new ArrayList<String>();
		List<GroupVO> groups = SysUtil.getCacheCurrentUser().getUserGroups();
		if(groups!=null && groups.size()>0){
			for(Iterator<GroupVO> it = groups.iterator();it.hasNext();){
				GroupVO g = it.next();
				list.add(g.getId());
			}
		}else{
			list.add((String) HBUtil.getHBSession().createSQLQuery("select aaa005 from aa01 a where a.aaa001 ='AREA_ID' ").list().iterator().next());
		}
		return list;
	}
	
}
