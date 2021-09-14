package com.insigma.odin.framework.sys.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


public class DologonHzbAction extends ActionSupport{
	
	public ActionForward doTransHzb(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest httpservletrequest,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("mainHzb");
	}
	
	/**
	 * 设置
	 * 法律知识考试成绩、党章党纪考试成绩
	 * http://localhost:8080/hzb/dologonHzbAction.do?method=setJs08Score&&data={}
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setJs08Score(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data=request.getParameter("data");
		/**
		 * {"id":"321111123","persion":[{"user_id":"123","dzdgdj_grade":"3","flzs_grade":"1"}
		 * ,{"user_id":"321","dzdgdj_grade":"2","flzs_grade":"3"}
		 * ,{"user_id":"123321","dzdgdj_grade":"3","flzs_grade":"4"}]}
		 */
		HBSession sess = HBUtil.getHBSession();
		try{
			JSONObject jsonobj = JSONObject.fromObject(data);
			String rbid=jsonobj.getString("id");
			JSONArray jsonArray = jsonobj.getJSONArray("persion");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				String js0100=obj.getString("user_id");
				String js0810=obj.getString("flzs_grade");
				String js0811=obj.getString("dzdgdj_grade");
				String sql = "update js08 set js0810='"+js0810+"',js0811='"+js0811+"' where RB_ID='"+rbid+"' and js0100='"+js0100+"'";
				System.out.println(sql);
				sess.createSQLQuery(sql).executeUpdate();
			}
//			Map map = new HashMap();
//			map.put("111", "22222222222222");
			this.doSuccess(request, "ok");
		}catch(Exception e){
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
	
	public ActionForward doTransXTGL(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest httpservletrequest,
			HttpServletResponse response) throws Exception {
        
		return mapping.findForward("mainMan");
	}
	
	public static boolean getDisableSys(String system) {
		UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
		List list = HBUtil.getHBSession().createSQLQuery("select r.roleid from Smt_Role r,Smt_Act a" +
				" where r.roleid=a.roleid and a.objectid='"+userVo.getId()+"' and r.hostsys in ('" +
						(system.equals("1")?"1','3":system)+"')").list();
		CommonQueryBS.systemOut("select r.roleid from Smt_Role r,Smt_Act a" +
				" where r.roleid=a.roleid and a.objectid='"+userVo.getId()+"' and r.hostsys in ('" +
						(system.equals("1")?"1','3":system)+"')");
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	
	public ActionForward doTransCheck(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String system = request.getParameter("system");
			Map map = new HashMap();
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			if(userVo == null){
				map.put("boo", true);
				map.put("logon", false);
			} else 
			if(userVo.getId().equals("40288103556cc97701556d629135000f")
					||userVo.getId().equals("U001")){
				map.put("boo", false);
			} else {
				List list = HBUtil.getHBSession().createSQLQuery("select r.roleid from Smt_Role r,Smt_Act a" +
						" where r.roleid=a.roleid and a.objectid='"+userVo.getId()+"' and r.hostsys in ('" +
								(system.equals("1")?"1','3":(system.equals("2")?"2','3":system))+"')").list();
				CommonQueryBS.systemOut("select r.roleid from Smt_Role r,Smt_Act a" +
						" where r.roleid=a.roleid and a.objectid='"+userVo.getId()+"' and r.hostsys in ('" +
								(system.equals("1")?"1','3":system)+"')");
				if(list != null && list.size() > 0){
					map.put("boo", false);
				} else {
					map.put("boo", true);
				}
				map.put("logon", true);
			}
			this.doSuccess(request, "ok", map);
		} catch (Exception e) {
			this.doError(request, e);
		}
		return this.ajaxResponse(request, response);
	}
}
