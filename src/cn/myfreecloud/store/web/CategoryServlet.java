package cn.myfreecloud.store.web;

import cn.myfreecloud.store.domain.Category;
import cn.myfreecloud.store.exception.ProductHasRelCategoryException;
import cn.myfreecloud.store.service.CategoryService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.UUIDUtil;
import cn.myfreecloud.store.web.base.BaseServlet;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService = BeanFactory.getInstance(CategoryService.class);

    /**
     * 删除分类
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String del(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取cname值
        String cid = request.getParameter("cid");
        //调用service保存分类
        try {
            categoryService.del(cid);
            //操作成功了?
            //回写1
            response.getWriter().print("1");
        } catch (ProductHasRelCategoryException e) {
            e.printStackTrace();
            //操作失败了
            //回写0
            response.getWriter().print("2");
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败了
            //回写0
            response.getWriter().print("0");
        }
        return null;
    }

    /**
     * 更新分类
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取cname值
        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        //封装对象
        Category category = new Category();
        category.setCid(cid);
        category.setCname(cname);
        //调用service保存分类
        try {
            categoryService.update(category);
            //操作成功了?
            //回写1
            response.getWriter().print("1");
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败了
            //回写0
            response.getWriter().print("0");
        }
        return null;
    }

    /**
     * 回显
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String huixianById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取cid
        String cid = request.getParameter("cid");
        //调用service查询
        Category category;
        try {
            category = categoryService.findById(cid);
            //转换json格式
            response.getWriter().print(JSONObject.fromObject(category).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询分页信息
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //解决回写乱码
        response.setContentType("text/html;charset=utf-8");
        //前端ajax请求 要求返回 分类信息列表  要求的是json格式
        String json;
        try {
            json = categoryService.list();
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("");
        }
        return null;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取cname值
        String cname = request.getParameter("cname");
        //封装对象
        Category category = new Category();
        category.setCid(UUIDUtil.getId());
        category.setCname(cname);
        //调用service保存分类
        try {
            categoryService.save(category);
            //操作成功了?
            //回写1
            response.getWriter().print("1");
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败了
            //回写0
            response.getWriter().print("0");
        }
        return null;
    }

}
