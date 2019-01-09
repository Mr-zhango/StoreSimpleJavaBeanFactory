package cn.myfreecloud.store.web;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.service.UserService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.web.auth.Auth;
import cn.myfreecloud.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    UserService userService = BeanFactory.getInstance(UserService.class);


    /**
     * 管理员的登录逻辑
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取账号密码
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //验证码
            String code = request.getParameter("code");

            //将账号密码作为参数 传递给service 返回user
            HttpSession session = request.getSession();
            // 把object类型的session转化为String类型的session
            String session_code = (String) session.getAttribute("checkcode_session");
            if(session_code.equalsIgnoreCase(code)){
                // 验证码正确
                User user=userService.login(username,password);
                //判断user是否为空
                if(user==null){
                    //不匹配
                    //request.setAttribute("msg", "账号密码不匹配");
                    //return "noprefix:/login";
                    request.getSession().setAttribute("msg", "账号密码不匹配");
                    return "admin/index";

                }else if(Constant.USER_IS_ADMIN!=Integer.parseInt(user.getContent())){
                    ////////////////////添加管理员验证逻辑//////////////////////////
                    request.getSession().setAttribute("msg", "抱歉,该用户不是管理员");
                    return "admin/index";
                }else{
                    //保存用户状态
                    request.getSession().setAttribute("user", user);
                    //重定向首页
                    return "admin/home";
                }
            }else{
                request.getSession().setAttribute("msg", "验证码错误");
                return "admin/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("msg", "服务器异常请重试");
            return "admin/index";
        }

    }


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
