package com.insigma.siis.local.pagemodel.zhsearch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

/**
 * @author lixy
 *
 */
public class SearchCondPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		//this.getExecuteSG().addExecuteCode("init()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//照片
	@PageEvent("Show")
	public int Show() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		String sql="select count(1) from (select 1 lx, qvid  from qryview q  where  1=1 and type='4' "
		+ " union select 2 lx,queryid from customquery )";
		
		String size = sess.createSQLQuery(sql).uniqueResult().toString();
		
		this.getExecuteSG().addExecuteCode("setlength('"+size+"')");
		
		if("0".equals(size)){
			this.setMainMessage("无信息");
			return EventRtnType.FAILD;
		}
		
		this.setNextEventName("picshow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//照片页面数据加载显示
	@PageEvent("picshow")
	public int picshow() throws RadowException, AppException, UnsupportedEncodingException{
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		HBSession sess=HBUtil.getHBSession();
		String page=this.getPageElement("page").getValue();
		int pagesize=10;
		int pages=Integer.valueOf(page);
		int start=(pages-1)*pagesize;
		int end=pages*pagesize;
		
		//String newsql = afa.replace(CommSQL.getComFields(userid,sid), "a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a"+",(select sort from A01SEARCHTEMP tmp where sessionid = '"+sid+"' and tmp.a0000 = a01.a0000) as sort ");
		String newsql = "select qtext,lx,qname,qid from (select '自定义查询' qtext,'2' lx,chinesename qname,"
				+ "qvid qid from qryview q where 1=1 and type='4' union select '常用查询' qtext,'1' lx,"
				+ "QUERYNAME qname,queryid qid from customquery ) order by lx,qid";
		
		String fysql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			fysql="select tt.* from (select t.*,rownum rn from ("+newsql+") t ) tt where rn>"+start+" and rn<="+end;
		}else{
			fysql = "select * from ("+newsql+") t limit "+start+","+end;
		}
		
		List<Object[]>  list=sess.createSQLQuery(fysql).list();
		for(int i=0;i<list.size();i++){
			Object[] info=list.get(i);
			Object qtext=info[0];
			Object lx=info[1];
			Object qname=info[2];
			Object qid=info[3];
			
			this.getExecuteSG().addExecuteCode("showpic('"+i+"','"+qid+"','"+qname+"','"+lx+"','"+qtext+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//照片页面数据加载显示
	@PageEvent("updateBtn.onclick")
	public int updateBtn() throws RadowException{
		String id = "";
		try {
			String ids = this.getPageElement("picA0000s").getValue();
			if(ids==null || "".equals(ids.trim())){
				
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			ids = ids.substring(0, ids.length()-1);
			ids = ids.replaceAll("\\'", "");
			String[] str = ids.split(",");
			if(str.length>1){
				//throw new AppException("请仅勾选一人进行操作！");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			id = ids;
			String[] idarr = id.split("@");
			if(idarr[1].equals("1")) {
				this.getExecuteSG().addExecuteCode("groupQuery('"+idarr[0]+"')");
			} else {
				this.getExecuteSG().addExecuteCode("userDefined2('"+idarr[0]+"','','','','')");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("获取数据异常");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initListAddGroupFlag")
	public int doMemberQueryAdd() throws RadowException, AppException {
		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		if (attribute != null || !"".equals(attribute + "")) {
			this.request.getSession().removeAttribute("listAddGroupSession");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}