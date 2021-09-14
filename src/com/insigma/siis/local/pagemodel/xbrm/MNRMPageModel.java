package com.insigma.siis.local.pagemodel.xbrm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

/**
 * 模拟干部任免
 * @author a
 *
 */
public class MNRMPageModel extends PageModel{

	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 显示拟任信息
	 * @param b0111
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("showDetail")
	public int showDetail(String b0111) throws RadowException{
		
		String a0000s = this.getPageElement("a0000s").getValue(); //拟任名单
		List<String> a0000_list = Arrays.asList(a0000s.split("@#@"));
		
		HBSession session = HBUtil.getHBSession();
		
		//单位现有女性人数
		Integer woman_num = 0;
		String woman_sql = "select count(1) from A01 where exists("
				+ " select A0000 from A02 where A01.A0000=A02.A0000 and A0201D='1' and A0201B like :a0195"
				+ " ) and A0104='2'";
		SQLQuery woman_query = session.createSQLQuery(woman_sql); 
		woman_query.setString("a0195", b0111+"%");
		woman_num = Integer.valueOf(woman_query.list().get(0).toString());
		
		//单位现有少数名族人数
		Integer minority_num = 0;
		String minority_sql = "select count(1) from A01 where exists("
				+ " select A0000 from A02 where A01.A0000=A02.A0000 and A0201D='1' and A0201B like :a0195"
				+ " ) and A0117!='01'";
		SQLQuery minority_query = session.createSQLQuery(minority_sql);
		minority_query.setString("a0195", b0111+"%");
		minority_num = Integer.valueOf(minority_query.list().get(0).toString());
		
		//单位现有非共产党员人数
		Integer nocommunist_num = 0;
		String nocommunist_sql = "select count(1) from A01 where exists("
				+ " select A0000 from A02 where A01.A0000=A02.A0000 and A0201D='1' and A0201B like :a0195"
				+ " ) and A0141!='01'";
		SQLQuery nocommunist_query = session.createSQLQuery(nocommunist_sql);
		nocommunist_query.setString("a0195", b0111+"%");
		nocommunist_num = Integer.valueOf(nocommunist_query.list().get(0).toString());
		
		
		//选择的人员中女性人数
		Integer now_woman_num = 0;
		String now_woman_sql = "select count(1) from A01 where A0104='2' and A0000 in (:a0000)";
		SQLQuery now_woman_query = session.createSQLQuery(now_woman_sql);
		now_woman_query.setParameterList("a0000", a0000_list);
		now_woman_num = Integer.valueOf(now_woman_query.list().get(0).toString());
		
		//选择的人员中少数名族人数
		Integer now_minority_num = 0;
		String now_minority_sql = "select count(1) from A01 where A0117!='01' and A0000 in (:a0000)";
		SQLQuery now_minority_query = session.createSQLQuery(now_minority_sql);
		now_minority_query.setParameterList("a0000", a0000_list);
		now_minority_num = Integer.valueOf(now_minority_query.list().get(0).toString());

		//选择的人员中少数名族人数
		Integer now_nocommunist_num = 0;
		String now_nocommunist_sql = "select count(1) from A01 where A0141!='01' and A0000 in (:a0000)";
		SQLQuery now_nocommunist_query = session.createSQLQuery(now_nocommunist_sql);
		now_nocommunist_query.setParameterList("a0000", a0000_list);
		now_nocommunist_num = Integer.valueOf(now_nocommunist_query.list().get(0).toString());
		
		//grid信息集
		List<HashMap<String, Object>> result_list = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> result_map ;
		
		if((woman_num+now_woman_num)<=0){
			result_map = new HashMap<String, Object>();
			result_map.put("detail", "缺少女性班子成员");
			result_list.add(result_map);
		}
		
		if((minority_num+now_minority_num)<=0){
			result_map = new HashMap<String, Object>();
			result_map.put("detail", "缺少少数名族班子成员");
			result_list.add(result_map);
		}
		
		if((nocommunist_num+now_nocommunist_num)<=0){
			result_map = new HashMap<String, Object>();
			result_map.put("detail", "缺少非共产党员班子成员");
			result_list.add(result_map);
		}
		
		this.getPageElement("gridMnrm").setValueList(result_list);
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
