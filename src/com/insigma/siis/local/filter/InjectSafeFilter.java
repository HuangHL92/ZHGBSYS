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
			+ " or |--|(+)|like";// ���˵���sql�ؼ��֣������ֶ����

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
		// ����������������
		Enumeration params = req.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			// �õ�������
			String name = params.nextElement().toString();
			if (name.equals("querySQL") || name.equals("userlog")) {
				continue;
			}
			// System.out.println("name===========================" + name +
			// "--");
			// �õ�������Ӧֵ
			String[] value = req.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				sql = sql + value[i];
			}
		}

		//System.out.println("============================SQL:" + sql);
		// ��sql�ؼ��֣���ת��error.html
		// !req.getRequestURI().equals("/sionline/logonAction.do")
		//�����ǽ���¼������У�飬��Ŀ����Ҫ�����ػ��޸ģ���������޷�������¼
		if (!req.getRequestURI().equals("/sionline/logonAction.do")
		// && !req.getRequestURI().equals("/sionline/common/commQueryAction.do")
		// && !req.getRequestURI().equals("/sionline/radowAction.do")
		// && !req.getRequestURI().equals("/sionline//radowAction.do")
		) {
			if (sqlValidate(sql)) {
				throw new IOException("����������Ĳ����к��зǷ��ַ�");
				// String ip = req.getRemoteAddr();
			} else {
				chain.doFilter(args0, args1);
			}
		} else {
			chain.doFilter(args0, args1);
		}
	}

	// Ч��
	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// ͳһתΪСд
		String badStr = BADSTR;// ���˵���sql�ؼ��֣������ֶ����
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				CommonQueryBS.systemOut(str);
				CommonQueryBS.systemOut("Ч��Ƿ��ַ���:" + badStrs[i]);
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
