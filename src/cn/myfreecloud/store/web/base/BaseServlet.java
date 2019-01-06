package cn.myfreecloud.store.web.base;

import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.web.auth.Auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
    //设置跳转的后缀
    private static final String SUFFIX = ".jsp";
    //设置跳转路径的前缀
    private static final String PREFIX = "/WEB-INF/jsp/";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取md的参数
        String md = request.getParameter("md");
        ////为空默认跳转首页
        if (md == null) {
            //为空默认跳转首页
            md = "index";
        }
        try {

            //获取方法名字
            Method method = this.getClass().getMethod(md, HttpServletRequest.class, HttpServletResponse.class);

            Auth annotation = method.getAnnotation(Auth.class);
            if (annotation != null) {
                if ("admin".equals(annotation.value())) {
                    User user = (User) request.getSession().getAttribute("user");
                    if (user != null) {
                        String content = user.getContent();
                        //判断用户的content(角色字段)
                        //当前登录用户不是超级管理员
                        if (!"1".equals(content)) {
                            //设置相应的字符集
                            response.setContentType("text/html;charset=utf-8");
                            //设置响应的状态值
                            response.setStatus(403);
                            response.getWriter().print("滚犊子!");
                            return;
                        }
                    }
                }
            }

            String path = (String) method.invoke(this, request, response);
            if (path != null) {
                if (path.startsWith("redirect:")) {
                    //返回重定向的路径(重定向到jsp页面)
                    String location = path.substring("redirect:".length());
                    response.sendRedirect(location + SUFFIX);
                } else if (path.startsWith("redirectx:")) {
                    //重定向到servlet(不带jsp的)
                    String location = path.substring("redirectx:".length());
                    response.sendRedirect(location);
                } else {
                    if (path.startsWith("noprefix:")) {
                        //代表了不想要 补上前缀
                        request.getRequestDispatcher(path.substring("noprefix:".length()) + SUFFIX).forward(request, response);
                    } else {
                        request.getRequestDispatcher(PREFIX + path + SUFFIX).forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    //重定向到默认页面
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "redirect:" + request.getContextPath() + "/index";
    }

}
