package cn.myfreecloud.store.web;


import cn.myfreecloud.store.web.auth.Auth;
import cn.myfreecloud.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 超级管理员才能访问的方法(test方法)
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Auth("admin")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/test";
    }

    /**
     * 默认跳转首页的方法
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/index";
    }

    /**
     * 主页
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/home";
    }

    /**
     * 分类列表
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String category_list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/category/list";
    }

    /**
     * 产品列表
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String product_list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/product/list";
    }
}
