package cn.myfreecloud.store.web;

import cn.myfreecloud.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RotationServlet extends BaseServlet {

    /**
     * 添加轮播图
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String addRrotation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return super.index(request, response);
    }
}
