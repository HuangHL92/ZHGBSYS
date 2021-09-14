package com.insigma.siis.local.pagemodel.notice;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.transform.Transformers;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.NoticeListVo;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class NoticeWindowPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		
		//this.setNextEventName("noticeSetgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 通知公告列表数据加载
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	@NoRequiredValidate
	public int noticeSetgrid(int start,int limit) throws RadowException{
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		String sql = "select a.id id,a.title title,a.fileName fileName,a.fileUrl fileUrl,a.updateTime updateTime, a.a0000Name a0000Name,b.see see "
				+ "from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId"
				+ " where b.recipientid = '"+user.getId()+"' and rownum<=6 order by b.see,a.updatetime";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 查看通知公告详情的双击事件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String id =this.getPageElement("noticeSetgrid").getValue("id",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		
		this.getExecuteSG().addExecuteCode("addTab('通知公告详情','"+id+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false,false);");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	//ajax获取通知公告数据
	/**
	 * 自动编码
	 * @throws AppException 
	 */
	@PageEvent("ajaxNotices")
	public int ajaxNotices() throws AppException{
		
		String ctxPath = request.getContextPath();
		HBSession sess = HBUtil.getHBSession();

		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//通知公告
		String sql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			sql = "select a.id as id,a.title as content,b.see see,a.updateTime dateShow from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime";
		}else{
			sql = "select a.id as id,a.title as content,b.see see,a.updateTime dateShow from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime";
		}
		List<NoticeListVo> noticeList = sess.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(NoticeListVo.class)).list();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(NoticeListVo nv : noticeList){
			Map<String,String> m = new HashMap<String,String>();
			m.put("id", nv.getId());
			m.put("content", nv.getContent());
			m.put("date", nv.getDateShow());
			list.add(m);
		}

		String v = JSONArray.fromObject(list).toString();
		
		//返回数据
		this.setSelfDefResData(v);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
