package com.insigma.odin.framework.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 安全过滤器request重写Wrapper
 * @author wengsh
 *
 */
public class SafeFilterHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest orgRequest;

    Log log= LogFactory.getLog(SafeFilterHttpServletRequestWrapper.class);
    public SafeFilterHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(filterValue(parameter));
        if (values==null)  {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = filterValue(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(filterValue(parameter));
        if (value != null) {
            value = filterValue(value);
        }
        return value;
    }
    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof SafeFilterHttpServletRequestWrapper) {
            return ((SafeFilterHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(filterValue(name));
        if (value != null) {
            value = filterValue(value);
        }
        return value;
    }




    /**
     * 过滤代码
     *
     *
     * @param s
     * @return
     */
    public String filterValue(String s)
    {
        if (s == null || s.equals("")) {
            return s;
        }
        //防止sql注入
        s=StringEscapeUtils.escapeSql(s);
        //防止xss攻击
        s=stripXSS(s);

        return s;
    }

    /**
     * stripXSS
     * @param value
     * @return
     */
    private String stripXSS(String value)
    {
        if (value != null)
        {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
        	//value = value.replaceAll("&", "&amp;");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onerror= expressions
            scriptPattern = Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            //Avoid embed
            scriptPattern = Pattern.compile("<embed(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid object
            scriptPattern = Pattern.compile("<object(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid layer
            scriptPattern = Pattern.compile("<layer(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid style
            scriptPattern = Pattern.compile("<style(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid meta
            scriptPattern = Pattern.compile("<meta(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid link
            scriptPattern = Pattern.compile("<link(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //Avoid xml
            scriptPattern = Pattern.compile("<xml(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            //将容易引起xss漏洞的半角字符直接替换成全角字符
            //value = escape(value);
        }
        return value;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * @param s
     * @return
     */
    public String escape(String s)
    {
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            switch (c)
            {
               /* case '>':
                    sb.append('＞');// 全角大于号
                    break;
                case '<':
                    sb.append('＜');// 全角小于号
                    break;*/
                case '\'':
                    sb.append('‘');// 全角单引号
                    break;
                case '\"':
                    sb.append('“');// 全角双引号
                    break;
                case '\\':
                    sb.append('＼');// 全角斜线
                    break;
                case '%':
                    sb.append('％'); // 全角冒号
                    break;
                default:
                    sb.append(c);
                    break;
            }

        }
        return sb.toString();
    }
}
