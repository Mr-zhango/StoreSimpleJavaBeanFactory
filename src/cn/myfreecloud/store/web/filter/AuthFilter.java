package cn.myfreecloud.store.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 用户过滤器
 */
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		Object user = req.getSession().getAttribute("user");

		StringBuffer path = req.getRequestURL();
		String queryString = req.getQueryString();

		//保存获取参数
		String pid = request.getParameter("pid");
		if(pid != null){
			int num =Integer.parseInt(request.getParameter("num"));
			path.append("?").append(queryString);
			path.append("&pid="+pid+"&num="+num);
		}

		String encode = URLEncoder.encode(path.toString());
		if(user==null){
			resp.sendRedirect(req.getContextPath()+"/login.jsp?xxxURL="+encode);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
    public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
