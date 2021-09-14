package com.insigma.odin.framework.sys.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utils.CommonParamUtil;
import org.apache.axis.utils.StringUtils;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.util.BrowserUtil;
import com.insigma.odin.framework.safe.SafeControlCenter;
import com.insigma.odin.framework.safe.util.SafeConst;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.JsonToMapParam;
import com.insigma.odin.framework.util.LogHelper;
import com.insigma.odin.framework.util.MenuBuilder;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.axis.gzoa.caClient2;
import com.insigma.siis.local.pagemodel.zj.Utils;
import com.lbs.leaf.util.LeafHttpUtil;

/**
 * 安全过滤器
 *
 * @author
 *
 */
public class SlSafetyFilter extends AbstractSafetyFilter {

	protected static LogHelper sysLog = LogHelper.getLogger(com.insigma.odin.framework.sys.auth.SlSafetyFilter.class);
	public SlSafetyFilter(){

	}

	private boolean isSessionTimeout(HttpSession httpsession1,HttpServletRequest httpservletrequest,HttpServletResponse httpservletresponse,String contextPath) throws IOException{
		if(httpsession1 == null){
            sysLog.error("Session超时，取到的Session为null！");
            doError(httpservletrequest, "没有登录或超时，请重新登录！");
            String bicp_path = GlobalNames.sysConfig.get("BICP_PATH");
            if(bicp_path != null && !bicp_path.equals("")){
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(bicp_path+"/odin?m=logout"));
            }else{
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + GlobalNames.RELOGON_PAGE));
            }
            return true;
        }
        //通过是否能获取当前用户，验证会话是否超时
        CurrentUser currentUser = (CurrentUser)httpsession1.getAttribute(GlobalNames.CURRENT_USER);
        if(currentUser == null){
            sysLog.error("Session超时，取到的CURRENT_USER为null！");
            doError(httpservletrequest, "没有登录或超时，请重新登录！");
            String bicp_path = GlobalNames.sysConfig.get("BICP_PATH");
            if(bicp_path != null && !bicp_path.equals("")){
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(bicp_path+"/odin?m=logout"));
            }else{
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + GlobalNames.RELOGON_PAGE));
            }
            return true;
        }
		return false;
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException {

        HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
        HttpServletResponse httpservletresponse = (HttpServletResponse)servletresponse;
        HttpSession httpsession1 = httpservletrequest.getSession(true);
        String contextPath = httpservletrequest.getContextPath();
        String servletPath = httpservletrequest.getServletPath();
        String resource = LeafHttpUtil.getRequestResource(httpservletrequest);
        String functionName = LeafHttpUtil.calcReqFunction(httpservletrequest);

        if(functionName.indexOf("radowAction.do")>0){
        	String pageModel = servletrequest.getParameter("pageModel");
        	String eventNames = servletrequest.getParameter("eventNames");
        	String model = servletrequest.getParameter("model");
        	String bs = servletrequest.getParameter("bs");
        	if(pageModel!=null){
        		functionName += "&pageModel="+pageModel;
        		if(model!=null){
        			functionName += "&model="+model;
        		}
        		if(bs!=null){
        			functionName += "&bs="+bs;
        		}
        		//暂时不管，先只验证到PageModel这层
        		/*
        		if(eventNames!=null){
        			functionName += "&eventNames="+eventNames;
        		}
        		*/
        	}
        }
        SysfunctionManager.setCurrentRequestFunctionCache(functionName);
        SysfunctionManager.setModuleSysfunctionidCache(httpservletrequest.getParameter("_modulesysfunctionid"));

        //验证是否是忽略的路径或者自由请求
        if(isUrlShouldIgnore(servletPath) || isFreeRequests(functionName)){
        	if(this.isHalfPub){
        		this.isHalfPub = false;
        		if(isSessionTimeout(httpsession1, httpservletrequest, httpservletresponse, contextPath)){
                	return;
                }
        	}
            filterchain.doFilter(servletrequest, servletresponse);
            return;
        }
        //验证是否是登陆请求
        if(GlobalNames.LOGON_ACTION.equals(servletPath)){
        	//验证用户登陆信息是否合法
            try {
				if(validUser(httpservletrequest)){
					BrowserUtil.delayByAa01((HttpServletRequest)servletrequest,BrowserUtil.logontype);
				    RequestDispatcher requestdispatcher = servCtx.getRequestDispatcher(GlobalNames.INDEX_PAGE);
				    requestdispatcher.forward(httpservletrequest, httpservletresponse);
				}else{
				    httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + GlobalNames.LOGON_DIALOG_PAGE));
				}
			} catch (Exception e) {
				e.printStackTrace();
				httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + GlobalNames.LOGON_DIALOG_PAGE));
			}
            return;
        }
        //验证会话是否超时
        if(isSessionTimeout(httpsession1, httpservletrequest, httpservletresponse, contextPath)){
        	return;
        }
        //验证是否是退出登陆请求
        if(GlobalNames.LOGOFF_ACTION.equals(servletPath)){
        	sysLog.doLogoffLog((CurrentUser)httpsession1.getAttribute(GlobalNames.CURRENT_USER));
            if(httpsession1 != null){
                httpsession1.invalidate();
            }
            String bicp_path = GlobalNames.sysConfig.get("BICP_PATH");
            if(bicp_path != null && !bicp_path.equals("")){
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(bicp_path+"/odin?m=logout"));
            }else{
//            	PrintWriter out = httpservletresponse.getWriter();
//                out.print("<script language=javascript>window.top.close();");
//                out.print("</script>");
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath/*contextPath+ GlobalNames.RELOGON_PAGE*/));
            }

            return;
        }
        //验证是否是重新登陆请求
        if(GlobalNames.RELOGON_ACTION.equals(servletPath)){
            sysLog.doLogoffLog((CurrentUser)httpsession1.getAttribute(GlobalNames.CURRENT_USER));
            if(httpsession1 != null)
                httpsession1.invalidate();
            String bicp_path = GlobalNames.sysConfig.get("BICP_PATH");
            if(bicp_path != null && !bicp_path.equals("")){
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(bicp_path+"/odin?m=logout"));
            }else{
            	httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + GlobalNames.RELOGON_PAGE));
            }
            return;
        }
        //验证是否清楚会话
        if(GlobalNames.CLEAN_SESSION_ACTION.equals(servletPath)){
            if(httpsession1 != null)
            {
                String s4 = servletrequest.getParameter("name");
                if(s4 != null)
                {
                    String as[] = s4.split(",");
                    for(int i = 0; i < as.length; i++){
                        httpsession1.removeAttribute(as[i]);
                    }
                }
            }
            return;
        }
        //权限过滤
        List currentFunctionList = (List)httpsession1.getAttribute(GlobalNames.FUNCTION_LIST);
        if(!permissionFilter(httpservletrequest, currentFunctionList, functionName)){
            httpservletresponse.sendRedirect(httpservletresponse.encodeRedirectURL(contextPath + "/error/notAllowVisit.jsp"));
            return;
        } else{
            filterchain.doFilter(servletrequest, servletresponse);
            return;
        }
    }
    /**
     * 验证用户是否合法，并将用户信息、功能列表信息缓存
     * @param httpservletrequest
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private boolean validUser(HttpServletRequest httpservletrequest) throws Exception{
        String jsonString = httpservletrequest.getParameter("params");
        Map<String,String> map = JsonToMapParam.convertToParamConfig(jsonString);
        String token = map.get("token");
        String token1 = map.get("token1");
        String token2 = map.get("token2");
        String tokenOA = httpservletrequest.getParameter("tokenOA");
        String userScene = map.get("scene");
        Map<String,String> userOA = null;
        /**
         * userOA demo
         * result is {id=1, user_name=系统管理员, user_id=1, not_login=0}
         *
        	user.setId("U000");
        	user.setLoginname(userLoginName);
        	user.setName("平台管理员");
         */
        //tokenOA = "257509a8e9tzLIMpL0ddHYUitajmQr2VPSNh2Szc/n1/ZTcURQO2BlDilO4QkdIHLQCClYACtaYHgnt/TXdfR2eiCW5qgm2SHEhpAlqmnAbg";
        /**
         * 贵州
         */
        if(!StringUtils.isEmpty(tokenOA)) {
        	userOA = caClient2.getUserByToken(tokenOA);
        	//userOA.put("id", "205");
        	Object[] obj_user = (Object[]) HBUtil.getHBSession().createSQLQuery("select k.USERID,k.LOGINNAME,k.USERNAME from smt_user k,smT_oauser w WHERE k.userid=w.self_userid and w.id='"+userOA.get("id")+"'").uniqueResult();
        	map.put("username", (String) obj_user[1]);
        	map.put("userid", (String) obj_user[0]);
        	map.put("password", "");
        }
        //获取sign  ganbu=显示 机构、人员、数据交换
        //         xitong=显示 系统管理
        String sign = map.get("sign");
        if(!StringUtils.isEmpty(CommonParamUtil.PARAM_MAP.get("startup"))){
            // 便于修改启动的业务项目 在commonParam.properties 中配置
            sign=CommonParamUtil.PARAM_MAP.get("startup");
        }
        if(sign!=null||userOA!=null){
        	httpservletrequest.getSession().setAttribute("noPwd","yes");

        	//httpservletrequest.getSession().setAttribute("gbSign", "ganbu");
        	//httpservletrequest.getSession().setAttribute("gbSign", "xitong");
        	//httpservletrequest.getSession().setAttribute("gbSign", "renmian");
        	//httpservletrequest.getSession().setAttribute("gbSign", "zhonghechaxun");
        	//httpservletrequest.getSession().setAttribute("gbSign", "banzi");
        	//httpservletrequest.getSession().setAttribute("gbSign", "Family");
        	//httpservletrequest.getSession().setAttribute("gbSign", "huiyi");
        }/*else{
        	httpservletrequest.getSession().setAttribute("gbSign", sign);
        }*/
        httpservletrequest.getSession().setAttribute("gbSign", sign);


        HttpSession httpsession = httpservletrequest.getSession(true);
        userScene = (String)httpsession.getAttribute("LoginScene");
        if(userScene == null || "".equals(userScene)){
        	userScene = "sce:VERFYCODE;USER_PASS";
        }
        String[] params = userScene.split(":");
        if(token1!=null && !token1.equals("")){
        	DesEncrypt des = new DesEncrypt();//实例化一个对像
            des.getKey("17E501F0CFE74EC6AE0B559D969E927E");//生成密匙
            String strEnc = des.getDesString(token1);
            //String[] info = strEnc.split("@#$%");
            map.put("password", strEnc.substring(0,strEnc.indexOf("@")));
            map.put("username", strEnc.substring(strEnc.indexOf("%")+1));
        }

        AuthParamConfig aconfig = new AuthParamConfig();

        aconfig.setParamMap(map);
        aconfig.setRequest(httpservletrequest);
        SceneVO cueScene = null;
        try { //判断场景信息是否在数据库也存在并有效（非只是存在于文件里）
        	List<Object> scencList = PrivilegeManager.getInstance().getISceneControl().queryByName(params[0], true);
        	if(scencList!=null&&scencList.size()>0){
        		SceneVO sv = (SceneVO)scencList.get(0);
        		if(sv.getStatus().equals("0")){
        			doError(httpservletrequest, "该场景信息已经被注销！");
                    return false;
        		}
        		cueScene = sv;
        	}else{
        		doError(httpservletrequest, "不存在该场景信息！");
                return false;
        	}
		} catch (Exception e) {
        	doError(httpservletrequest, "用户验证失败:"+e.getMessage());
            return false;
		}
        if(token!=null && !token.equals("")){ //单点登录
        	Object obj = HBUtil.getHBSession().createSQLQuery("select username from SYNO_USER_AUTHENTICATION WHERE TOKEN='"+token+"'").uniqueResult();
        	if (obj == null){
        		doError(httpservletrequest, "用户验证失败");
                return false;
        	}
        	Object obj_username = HBUtil.getHBSession().createSQLQuery("select k.username from zwhzyq.smt_user k,SYNO_USER_AUTHENTICATION w WHERE k.userid=w.userid and TOKEN='"+token+"'").uniqueResult();
        	if (obj_username == null || obj_username.toString().equals("")){
        		obj_username = "超级管理员";
        	}
        	map.put("username", obj!=null ?obj+"" : "");
            UserVO user = new UserVO(); 		//前面的验证已经通过，则该用户名对应的用户存在且有效
            UserVO userAdmin = new UserVO(); 	//系统功能全部权限
            String userLoginName = obj!=null ?obj+"" : "";
            String userPassword = map.get("password");
            List list = PrivilegeManager.getInstance().getIUserControl().queryByName(map.get("username"), true);
            if(list.size()==0){//数据库中不存在时候吧页面的数据存入构建好的UserVO中，由后面的登录中的判断
            	user.setLoginname(userLoginName);
            	user.setId(GlobalNames.sysConfig.get("SUPER_ID"));
            	user.setPasswd(userPassword);
            	user.setName(obj_username+"");
            	userAdmin.setLoginname(userLoginName);
            	userAdmin.setId("40288103556cc97701556d629135000f");
            	userAdmin.setPasswd(userPassword);
            	userAdmin.setName("超级管理员");
            }else{
            	user = (UserVO)list.get(0);
            	user.setName(obj_username+"");
            	userAdmin.setLoginname(userLoginName);
            	userAdmin.setId("40288103556cc97701556d629135000f");
            	userAdmin.setPasswd(userPassword);
            	userAdmin.setName("超级管理员");
            }
            CurrentUser currentuser = new CurrentUser();
            BeanUtil.propertyCopy(user, currentuser);
            List<Object> functionList = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(userAdmin, cueScene.getSceneid(), false);


            List<GroupVO> userGroups = PrivilegeManager.getInstance().getIGroupControl().getGroupsByUserId(userAdmin.getId());
            currentuser.setIplist(httpservletrequest.getRemoteAddr());
            currentuser.setFunctionList(functionList);
            currentuser.setUserGroups(userGroups);
            currentuser.setUserVO(user);
            currentuser.setSceneVO(cueScene);
            currentuser.setTsFlag(userLoginName);
            currentuser.putUserOtherInfo("sessionid",httpservletrequest.getSession().getId());
            currentuser.putUserOtherInfo("dataGroup", PrivilegeManager.getInstance().getIDataGroupControl().getAllDataGroupByUserId(userAdmin.getId(), true));
            currentuser.putUserOtherInfo("dataExtGroup", PrivilegeManager.getInstance().getIDataGroupControl().getExtDataGroupByUserId(userAdmin.getId()));
            List<String> functionButtonList = Utils.getButtonList();
            httpsession.setAttribute("function_button_list", functionButtonList);
            httpsession.setAttribute(GlobalNames.CURRENT_USER, currentuser);
            httpsession.setAttribute(GlobalNames.FUNCTION_LIST, functionList);
            SysUtil.setCacheCurrentUser(currentuser);
            sysLog.doLogonLog(currentuser);

            //遍历SESSION中的变量
            Enumeration<?> enums = httpsession.getAttributeNames();
            Object sesskey = null;
            while(enums.hasMoreElements()){
            	sesskey = enums.nextElement();
            	sysLog.info("login session attribute:" + sesskey +": " + httpsession.getAttribute((String)sesskey));
            }
            if(httpsession.getAttribute("LoginManagerBean") != null){
        		httpsession.removeAttribute("LoginManagerBean");
        	}

            //safe validate
            SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_LOGINCOUNT, SafeConst.PDT_INSIIS_COMP_ODIN);
            SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_EXPIRATION,SafeConst.PDT_INSIIS_COMP_ODIN);
            SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_APPSERVER,SafeConst.PDT_INSIIS_COMP_ODIN);
            SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_IP, SafeConst.PDT_INSIIS_COMP_ODIN);

            MenuBuilder mb = new MenuBuilder();
    		httpsession.setAttribute("EPMenuData",mb.getMenu(httpservletrequest));
            return true;
        }
        String noPwd = (String) httpservletrequest.getSession().getAttribute("noPwd");
        if("yes".equals(noPwd)){//不校验密码

        }else{
        	if(!AuthResolver.resolver(params[1], aconfig)){
            	doError(httpservletrequest, "用户验证失败:"+httpservletrequest.getAttribute(GlobalNames.EXCEPTION_KEY));
                return false;
            }
        }
        //httpservletrequest.getSession().setAttribute("noPwd","");

        //前面的验证已经通过，则该用户名对应的用户存在且有效
        UserVO user = new UserVO();
        String userLoginName = map.get("username");
        String userPassword = map.get("password");
        List list = null;
        if("root".equals(userLoginName)){
        	list = PrivilegeManager.getInstance().getIUserControl().queryByName("system", true);
        }else{
            list = PrivilegeManager.getInstance().getIUserControl().queryByName(map.get("username"), true);
        }
        //把超级管理员从数据库中移到jar包中，如果在数据库中找不到这个用户就把页面上的值出入当前用户中进行是不是超级管理员的判断
        if(list.size()==0){//数据库中不存在时候吧页面的数据存入构建好的UserVO中，由后面的登录中的判断
        	user.setLoginname(userLoginName);
        	user.setId(GlobalNames.sysConfig.get("SUPER_ID"));
        	user.setPasswd(userPassword);
        	//user.setName("超级管理员");
        }else{//如果数据库总存在与超级管理员同名的，则判断密码是不是一样的如果密码也一样的话，认为是超级管理员登录；
        	user = (UserVO)list.get(0);
        	if(userPassword!=null&&userPassword.equals(GlobalNames.sysConfig.get("SUPER_PASSWORD"))){
        		user.setLoginname(userLoginName);
            	user.setId(GlobalNames.sysConfig.get("SUPER_ID"));
            	user.setPasswd(userPassword);
            	//user.setName("超级管理员");
        	}
        }
        CurrentUser currentuser = new CurrentUser();
        BeanUtil.propertyCopy(user, currentuser);
        //SysUtil.getCurrentUser(httpservletrequest);
        List<Object> functionList = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(user, cueScene.getSceneid(), false);

        //root 获取到system的所有权限之后，再改id  loginname  name
        if("root".equals(userLoginName)){
        	user.setId("U000");
        	user.setLoginname(userLoginName);
        	user.setName("系统管理员");
        }
        //增加BICP系统菜单的整合 start
//        String rtnmsg = BicpService.loadOtherUserFunctions(userLoginName,httpservletrequest.getSession().getId(),functionList);
//        if (!rtnmsg.equals("")){
//        	doError(httpservletrequest, rtnmsg);
//            return false;
//        }
        // end by zhengwy

        List<GroupVO> userGroups = PrivilegeManager.getInstance().getIGroupControl().getGroupsByUserId(user.getId());
        currentuser.setIplist(httpservletrequest.getRemoteAddr());
        currentuser.setFunctionList(functionList);
        currentuser.setUserGroups(userGroups);
        currentuser.setUserVO(user);
        currentuser.setSceneVO(cueScene);
        currentuser.setTsFlag(userLoginName);
        currentuser.putUserOtherInfo("sessionid",httpservletrequest.getSession().getId());
        currentuser.putUserOtherInfo("dataGroup", PrivilegeManager.getInstance().getIDataGroupControl().getAllDataGroupByUserId(user.getId(), true));
        currentuser.putUserOtherInfo("dataExtGroup", PrivilegeManager.getInstance().getIDataGroupControl().getExtDataGroupByUserId(user.getId()));
        httpsession.setAttribute(GlobalNames.CURRENT_USER, currentuser);
        httpsession.setAttribute(GlobalNames.FUNCTION_LIST, functionList);
        SysUtil.setCacheCurrentUser(currentuser);
        sysLog.doLogonLog(currentuser);

        //遍历SESSION中的变量
        Enumeration<?> enums = httpsession.getAttributeNames();
        Object sesskey = null;
        while(enums.hasMoreElements()){
        	sesskey = enums.nextElement();
        	//sysLog.info("login session attribute:" + sesskey +": " + httpsession.getAttribute((String)sesskey));
        }
        if(httpsession.getAttribute("LoginManagerBean") != null){
    		httpsession.removeAttribute("LoginManagerBean");
    	}

        //safe validate
        SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_LOGINCOUNT, SafeConst.PDT_INSIIS_COMP_ODIN);
        SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_EXPIRATION,SafeConst.PDT_INSIIS_COMP_ODIN);
        SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_APPSERVER,SafeConst.PDT_INSIIS_COMP_ODIN);
        SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(SafeConst.VT_IP, SafeConst.PDT_INSIIS_COMP_ODIN);

        MenuBuilder mb = new MenuBuilder();
		httpsession.setAttribute("EPMenuData",mb.getMenu(httpservletrequest));
        return true;

    }


    protected boolean afterLogonProcess(HttpServletRequest httpservletrequest){
        return true;
    }
    /**
     * 验证该权限是否被授权
     * @param httpservletrequest
     * @param list
     * @param s
     * @return
     */
    @SuppressWarnings("unchecked")
	private boolean permissionFilter(HttpServletRequest httpservletrequest, List functionList, String functionName){
    	//验证该方法是否是通用查询和返回，如果是则不用验证权限
        String method = httpservletrequest.getParameter(GlobalNames.METHOD);
        String servletPath = httpservletrequest.getServletPath();
        CurrentUser currentuser = (CurrentUser)httpservletrequest.getSession().getAttribute(GlobalNames.CURRENT_USER);
        if(servletPath!=null &&("/common/commQueryAction.do").equalsIgnoreCase(servletPath)){
        	return true;
        }
        if(servletPath!=null &&("/common/pageQueryAction.do").equalsIgnoreCase(servletPath)){
        	return true;
        }
        if(method != null && ("commonQuery".equals(method) || "goBack".equals(method))){
            return true;
        }
        sysLog.info("========##=====request function : " + functionName);

        //验证是否自由请求
        if(isFreeRequests(functionName)){
            return true;
        }

        //循环查找请求是否在功能列表中
        boolean flag = false;
        for(Iterator iterator = functionList.iterator(); iterator.hasNext();){
        	FunctionVO func = (FunctionVO)iterator.next();
            if(func.getLocation()!=null && func.getLocation().equals(functionName)){
            	flag = true;
            	break;
            }
        }

        if(!flag){
        	//记录操作日志
        	sysLog.doOptLog(currentuser, functionName, null,"0");
            doError(httpservletrequest, "没有访问权限！" + functionName);

        }
        return flag;
    }

    protected void afterValidateAccessProcess(HttpServletRequest httpservletrequest){
    }

}
