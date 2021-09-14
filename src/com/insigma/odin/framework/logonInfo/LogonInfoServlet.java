package com.insigma.odin.framework.logonInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.Userauthentication;

public class LogonInfoServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*String isPWDAuth = request.getParameter("isPWDAuth");
		if("1".equals(isPWDAuth)){
			userpwdauth(request, response);
		}else{
			userauth(request, response);
		}
		*/
	}
	
	
	public static void doLogonAuth(String token){
		try {
			HBSession sess = HBUtil.getHBSession();
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String username = user.getLoginname();
			Userauthentication ua = new Userauthentication();
			ua.setToken(token);
			ua.setUsername(username);
			sess.saveOrUpdate(ua);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据token 校验是否已有用户登录， 返回登录用户名。
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userauth(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String isLogon = "true";
		String formToken = request.getParameter("token");
		if(formToken == null){
			formToken = "";
		}
		//String sessionToken = (String)request.getSession().getAttribute("token");
		try {
			String username = "";
			Userauthentication ua = (Userauthentication)HBUtil.getHBSession().get(Userauthentication.class, formToken);
			if(ua != null){
				username = ua.getUsername();
				/*String sql = "from SmtUser where loginname=?";
				List<SmtUser> users = HBUtil.getHBSession().createQuery(sql).setString(0, username).list();
				if(users!=null && users.size()>0){
					username = users.get(0).getId();
				}else{
					username = "";
				}*/
			}else{
				isLogon = "false";
			}
			
			response.setContentType("text/html;charset=utf-8");  
			PrintWriter pw = response.getWriter();
			pw.write("{\"username\" : \""+username+"\",\"isLogon\" : "+isLogon+"}");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			isLogon = "false";
			response.setContentType("text/html;charset=utf-8");  
			PrintWriter pw = response.getWriter();
			pw.write("{\"isLogon\" : "+isLogon+"}");
			pw.flush();
			pw.close();
			e.printStackTrace();
		}finally{
			//request.getSession().invalidate();
		}
	}
	
	/**
	 * 通过返回的用户名 密码 去登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userpwdauth(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String isLogon = "true";
		try {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String username = user.getLoginname();
			String password = user.getPasswd();
			response.setContentType("text/html;charset=utf-8");  
			PrintWriter pw = response.getWriter();
			pw.write("{\"username\" : \""+username+"\",\"password\" : \""+password+"\",\"isLogon\" : "+isLogon+"}");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			isLogon = "false";
			response.setContentType("text/html;charset=utf-8");  
			PrintWriter pw = response.getWriter();
			pw.write("{\"isLogon\" : "+isLogon+"}");
			pw.flush();
			pw.close();
			e.printStackTrace();
		}
	}
	
	
}