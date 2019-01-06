package cn.myfreecloud.store.web;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.exception.UserActiveJokeExcption;
import cn.myfreecloud.store.service.UserService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.UUIDUtil;
import cn.myfreecloud.store.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = BeanFactory.getInstance(UserService.class);

    public String test1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("我执行了");
        int flag = 2;
        if (flag == 1) {
            //重定向  name 直接告诉我地址 我帮你
            return "redirect:/index.jsp";
        } else if (flag == 2) {
            return "user/test";
        } else {
            //直接写回数据
            response.getWriter().print("asdas");
            return null;
        }
    }

    /**
     * 请求转发到 注册页面
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "user/register";
    }

    /**
     * 真实的注册逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取提交数据
            Map<String, String[]> parameterMap = request.getParameterMap();
            //封装一个user对象
            User user = new User();
            BeanUtils.populate(user, parameterMap);
            //手动封装  uid  state  code-- 激活码
            user.setUid(UUIDUtil.getId());
            user.setState(Constant.USER_IS_NOT_ACTIVE);
            user.setCode(UUIDUtil.getId());
            //调用service完成注册逻辑
            userService.regist(user);
//			没有异常
//			操作成功了
//				需要给用户一个提示页面 提示注册成功
            request.setAttribute("msg", "恭喜你注册成功,请去激活");
            return "info";
        } catch (Exception e) {
            e.printStackTrace();
//			有异常
//			操作失败了
//				给用户一个提示页面 请重新注册
            request.setAttribute("msg", "注册失败了,请重新注册");
            return "info";
        }
    }

    /**
     * 激活逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //		获取传入的code参数
            String code = request.getParameter("code");
            //		将code参数传给service 完成激活
            userService.active(code);
            //		判断有无异常
            //			无异常
            //				提示用去登录
            request.setAttribute("msg", "激活成功,请去登录");
            return "info";
        } catch (UserActiveJokeExcption uje) {
            uje.printStackTrace();
//			有异常
            //				提示用激活失败 请重新激活
            request.setAttribute("msg", "小伙子 不要捣乱");
            return "info";
        } catch (Exception e) {
            e.printStackTrace();
//			有异常
            //				提示用激活失败 请重新激活
            request.setAttribute("msg", "激活失败请重试");
            return "info";
        }
    }

    /**
     * 登录逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取账号密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //将账号密码作为参数 传递给service 返回user
        try {
            User user = userService.login(username, password);
            //判断user是否为空
            if (user == null) {
                //不匹配
                //request.setAttribute("msg", "账号密码不匹配");
                //return "noprefix:/login";
                request.getSession().setAttribute("msg", "账号密码不匹配");
                return "redirect:" + request.getContextPath() + "/login";

            } else {
                ////////////////////添加激活逻辑//////////////////////////
                if (Constant.USER_IS_ACTIVE != user.getState()) {
                    request.getSession().setAttribute("msg", "用户未激活,请先激活再登录");
                    return "redirect:" + request.getContextPath() + "/login";
                }
                /////////////////////////////////////////////
                //保存用户状态
                request.getSession().setAttribute("user", user);
                //重定向首页
                String xxxURL = request.getParameter("xxxURL");
                if (xxxURL != null) {
                    return "redirectx:" + xxxURL;
                }

                return "redirect:" + request.getContextPath() + "/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("msg", "服务器异常请重试");
            return "redirect:" + request.getContextPath() + "/login";
        }
    }

    /**
     * 销毁session
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        return "redirect:" + request.getContextPath() + "/index";
    }

}
