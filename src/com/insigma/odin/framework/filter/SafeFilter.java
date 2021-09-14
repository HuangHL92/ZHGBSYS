package com.insigma.odin.framework.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.JsonToMapParam;
import com.lbs.leaf.GlobalConfig;

/**
 * 网站安全过滤器
 * 1.是否登录过滤
 * 2 csrf referer 过滤
 * 3 xss及sql注入过滤
 *
 */
public class SafeFilter implements javax.servlet.Filter {


    public void destroy() {
        // TODO Auto-generated method stub

    }
    private Map<String, String> pageModelmap = null;
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String pageModel = request.getParameter("pageModel");//pages.customquery.CustomQuery
        String eventNames = request.getParameter("eventNames");//mQuery.onclick
        /**
		 * 3xss及sql注入过滤
		 **/
		if(pageModelmap!=null && pageModel!=null && eventNames != null){
			if(pageModelmap.get(pageModel)!=null && pageModelmap.get(pageModel).indexOf(eventNames)!=-1){
				SafeFilterHttpServletRequestWrapper safeRequest = new SafeFilterHttpServletRequestWrapper(request);
	        	filterChain.doFilter(safeRequest, servletResponse);
	        	return;
			}
		}
		filterChain.doFilter(request, servletResponse);
       
    }



	public void init(FilterConfig arg0) throws ServletException {
		String pageModel = arg0.getInitParameter("pageModel");
		pageModelmap = JsonToMapParam.convertToParamConfig(pageModel);
	}

  

}
