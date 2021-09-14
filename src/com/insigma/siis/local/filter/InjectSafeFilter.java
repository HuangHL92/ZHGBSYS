package com.insigma.siis.local.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.insigma.odin.framework.AppException;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class InjectSafeFilter /* extends AbstractSafetyFilter */implements Filter {

	private static String BADSTR = " and |exec|execute|insert|select |delete|update|count|drop|master|truncate|"
			+ "char |declare|sitename|net user|xp_cmdshell|like'|exec|execute|insert|create |"
			+ "table|from|grant| use |group_concat|column_name|"
			+ "information_schema.columns|table_schema|union|where|"
			+ " or |--|(+)|like";// 过滤掉的sql关键字，可以手动添加

	/**
	 * Default constructor.
	 */
	public InjectSafeFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest args0, ServletResponse args1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) args0;
		// 获得所有请求参数名
		Enumeration params = req.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			// 得到参数名
			String name = params.nextElement().toString();
			if (name.equals("querySQL") || name.equals("userlog")) {
				continue;
			}
			// System.out.println("name===========================" + name +
			// "--");
			// 得到参数对应值
			String[] value = req.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				sql = sql + value[i];
			}
		}

		//System.out.println("============================SQL:" + sql);
		// 有sql关键字，跳转到error.html
		// !req.getRequestURI().equals("/sionline/logonAction.do")
		//这里是将登录请求不做校验，项目组需要做本地化修改，否则可能无法正常登录
		if (!req.getRequestURI().equals("/sionline/logonAction.do")
		// && !req.getRequestURI().equals("/sionline/common/commQueryAction.do")
		// && !req.getRequestURI().equals("/sionline/radowAction.do")
		// && !req.getRequestURI().equals("/sionline//radowAction.do")
		) {
			if (sqlValidate(sql)) {
				throw new IOException("您发送请求的参数中含有非法字符");
				// String ip = req.getRemoteAddr();
			} else {
				chain.doFilter(args0, args1);
			}
		} else {
			chain.doFilter(args0, args1);
		}
	}

	// 效验
	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String badStr = BADSTR;// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				CommonQueryBS.systemOut(str);
				CommonQueryBS.systemOut("效验非法字符串:" + badStrs[i]);
				return true;
			}
		}
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String badstr = fConfig.getInitParameter("badstr");
		if (badstr != null && !badstr.trim().equals("")) {
			BADSTR = badstr;
		}
	}
	
	public static void main(String[] args) throws AppException {
		sqlValidate("V%2BSTCw13lCusOMzQyLlaxw%3D%3D");
	}

}
