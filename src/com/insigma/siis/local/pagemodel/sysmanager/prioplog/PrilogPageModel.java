package com.insigma.siis.local.pagemodel.sysmanager.prioplog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.entity.SmtLog;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.HQuery;
import com.insigma.odin.framework.util.DateUtil;

public class PrilogPageModel extends PageModel {

	@Override
	public int doInit() {
		this.setNextEventName("loggrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btn_query.onclick")
	public int doQuery() throws RadowException {
		this.setNextEventName("loggrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("loggrid.dogridquery")
	public int dogridQuery(int start, int limit) throws RadowException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		String model = this.getPageElement("model").getValue().trim();
		String mess = this.getPageElement("otherMess").getValue().trim();
		String timefr = this.getPageElement("timefr").getValue().trim();
		if(!model.equals("")){
			map.put("model", model);
		}
		if(!mess.equals("")){
			map.put("mess", mess);
		}
		if(!timefr.equals("")){
			try {
				Date timeto_d = DateUtil.addDays(timefr, DateUtil.NORMAL_DATE_FORMAT, 1);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String timeto_s = sd.format(timeto_d);
				map.put("timefr", timefr);
				map.put("timeto", timeto_s);
			} catch (AppException e) {
				throw new RadowException(e.getMessage());
			}
		}
		PageQueryData pq = this.getPageQueryData(start, limit, map);
		this.setSelfDefResData(pq);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("dogriddelete")
	public int delete(String logid) throws RadowException {
		this.addNextEvent(NextEventValue.YES, "dodelete",logid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("dodelete")
	public int doDelete(String logid) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		SmtLog log = (SmtLog)sess.createQuery("from SmtLog where logid=:logid").setString("logid", logid).uniqueResult();
		sess.delete(log);
		sess.flush();
		this.getPageElement("loggrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("clean.onclick")
	@NoRequiredValidate
	public int clean() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	private PageQueryData getPageQueryData(int start, int limit, HashMap params) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		hql.append("select count(*) from SmtLog sl where 1=1 ");
		if(!params.isEmpty()){
			if(params.get("model") != null){
				hql.append( " and otherInfo like '%model:"+params.get("model")+"%'");
			}
			if(params.get("mess") != null){
				hql.append( " and (loginname like '%"+params.get("mess")+"%' or opdesc like '%"+
					params.get("mess")+"%' or opaddress like '%"+params.get("mess")+"%')");
			}
			if(params.get("timefr") != null && params.get("timeto") != null){
				hql.append(  " and opdate between to_date('"+params.get("timefr")+"' ,'yyyy-MM-dd') and to_date('"+params.get("timeto")+"' ,'yyyy-MM-dd')");
			}
		}
		Query query = sess.createQuery(hql.toString());
		// 取记录总数
		int totalcount = Integer.parseInt(query.list().get(0).toString());
		pd.setTotalCount(totalcount);
		List apps = queryAllOrByPage(start, limit, params);
		try {
			pd.setData(HQuery.fromList(apps));
		} catch (AppException e) {
			throw new RadowException(e.getMessage());
		}
		return pd;
	}
	
	private List queryAllOrByPage(int start, int limit, HashMap params) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "from SmtLog where 1=1 ";
		if(!params.isEmpty()){
			if(params.get("model") != null){
				hql += " and otherInfo like '%model:"+params.get("model")+"%'";
			}
			if(params.get("mess") != null){
				hql += " and (loginname like '%"+params.get("mess")+"%' or opdesc like '%"+
					params.get("mess")+"%' or opaddress like '%"+params.get("mess")+"%')";
			}
			if(params.get("timefr") != null && params.get("timeto") != null){
				hql += " and opdate between to_date('"+params.get("timefr")+"' ,'yyyy-MM-dd') and to_date('"+params.get("timeto")+"' ,'yyyy-MM-dd')";
			}
		}
		List list = null;
		if(limit == -1 || start == -1){
			list = sess.createQuery(hql).list();
		}else{
			Query q = sess.createQuery(hql);
			q.setFirstResult(start); 
			q.setMaxResults(limit);
			list = q.list();
		}
		return list;
	}

}
