package com.insigma.siis.local.pagemodel.fxyp.gbtp;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GWYLPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String id = this.getPageElement("query_id").getValue();
		String wayname= HBUtil.getValueFromTab("wayname", "zdgw_way", "wayid='"+id+"'");
		/*String sql = "select mxname,mxdsec from zdgw_zgmx where wayid='"+id+"'";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		String jsonData = JSON.toJSONString(nmzwList);
		
		this.getExecuteSG().addExecuteCode("setGWTJInfo("+jsonData+")");*/
		this.getExecuteSG().addExecuteCode("$('.gwmane').text('岗位："+wayname+"');Photo_List.ajaxSubmit('ShowData','pages.fxyp.gbtp.GWYL')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		try {
			String selsql = "select  a01.a0000,a01.a0101,a01.a0104,a01.a0107,a0117,a0111a,a0140,a0141,a0144,a0134,a0196,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,a01.a0288,a0192f ";

			String id = this.request.getParameter("query_id");
			String querysql= HBUtil.getValueFromTab("sql", "zdgw_way", "wayid='"+id+"'");
			querysql = selsql + querysql.substring(16);
			//System.out.println(sql);
			this.pageQuery(querysql, "SQL", 0, 12);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("tpbj.onclick")
	public int tpbj() throws RadowException {
		LinkedHashSet<String> selected = new LinkedHashSet<String>();
		// 从cookie中的获取之前选择的人员id
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("jggl.tpbj.ids".equals(cookie.getName())) {
					String cookieValue = cookie.getValue();
					String[] ids = cookieValue.split("#");
					for (String id : ids) {
						if (!StringUtils.isEmpty(id)) {
							selected.add(id);
						}
					}
				}
			}
		}
		// 从列表或者小资料中获取选择的人员
		String a0000s = this.getPageElement("a0000s").getValue();
		if (!StringUtils.isEmpty(a0000s)) {
			String[] a0000Array = a0000s.split(",");
			for (int i = 0; i < a0000Array.length; i++) {
				selected.add(a0000Array[i]);
			}
		}

		if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.FAILD;
		} else {
			String json = JSON.toJSONString(selected);
			this.getExecuteSG()
					.addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','同屏比较',1330,731,null,'"
							+ this.request.getContextPath() + "',window,{"
							+ "maximizable:false,resizable:false,RMRY:'同屏比较',data:" + json + ",addPerson:true},true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
}
