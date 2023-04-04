package etu2032.framework.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class FilterMapping implements Filter{
	public void doFilter( ServletRequest arg0 , ServletResponse arg1 , FilterChain arg2 ) 
						throws ServletException , IOException{
			
			HttpServletRequest request = (HttpServletRequest)arg0;
			HttpServletResponse response = (HttpServletResponse)arg1;
			String uri = request.getRequestURI();
			String url = uri.substring(request.getContextPath().length()).trim();
			PrintWriter out = response.getWriter();
			// if( uri.contains("[.]") ){
			if( uri.matches(".*\\..*") ){
				arg2.doFilter( arg0 , arg1 );
			}else{
				// out.println(uri);
				request.getRequestDispatcher("/FrontServlet"+url).forward(request , response);
			}
	}
}