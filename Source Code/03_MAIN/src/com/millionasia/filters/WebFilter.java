package com.millionasia.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebFilter  implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//For OWSAP
		response.addIntHeader("Strict-Transport-Security", 120);
		response.addHeader("X-Frame-Options", "deny");
		response.addHeader("X-XSS-Protection", "1; mode=block");
		response.addHeader("X-Content-Type-Options", "nosniff");
		
		//暫時將所有教育雲的功能導向到首頁
		/*
		Boolean isEduCloud   = Boolean.valueOf((String)Configurations.getParamValue("IS_EDU_CLOUD"));
		if(isEduCloud){
			String uri = request.getRequestURI();

			if(!uri.contains("index_edu.jsp") && uri.contains(".jsp")){
				response.sendRedirect("index_edu.jsp");
				return;
			}
		}
		*/

		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}



	
}
