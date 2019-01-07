package cn.myfreecloud.store.web;

import cn.myfreecloud.store.domain.PageBean;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.service.ProductService;
import cn.myfreecloud.store.service.UserService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.web.base.BaseServlet;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
    private UserService userService = BeanFactory.getInstance(UserService.class);
    private ProductService productService = BeanFactory.getInstance(ProductService.class);

    /**
     * 分页查询商品数据
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        //easyui传参
        //page---->pageNumber
        try {
            //rows----->pageSize
            int pageNumber = Integer.parseInt(request.getParameter("page"));
            int pageSize = Integer.parseInt(request.getParameter("rows"));
            //接收 到参数以后 调用service 分页查询
            PageBean<Product> pb = productService.findByPage(pageNumber, pageSize);
            //要求返回的数据格式
            /**
             * {
             total:总条数,--------pageBean的total
             rows:[{数据对象,.....}]---pageBean中的data
             }
             */
            HashMap<String, Object> result = new HashMap<>();
            Object total = pb.getTotal();
            result.put("total", total);
            Object rows = pb.getData();
            result.put("rows", rows);
            //写回去

            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setExcludes(new String[]{"pdate"});
            JSONObject fromObject = JSONObject.fromObject(result, jsonConfig);

            response.getWriter().print(fromObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //负责 首页展示
            //准备数据
            //准备最新商品 9条
            System.out.println(request.getRequestURI());
            List<Product> news = productService.findNews();
            //准备热门商品 9条
            List<Product> hots = productService.findHots();
            //请求转发一个页面数展示
            request.setAttribute("news", news);
            request.setAttribute("hots", hots);
            return "product/index";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "查询首页商品出问题了");
            return "info";
        }
    }

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String findByPageWithCate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //带来 cid  代表某个分类
            String cid = request.getParameter("cid");
            //获取第几页  当前的页码
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            //我自己确定  每页条数  12
            int pageSize = 12;
            //调用service 查询数据--->准备 pageBean需要五样数据
            PageBean<Product> pb = productService.findByPageWithCate(cid, pageNumber, pageSize);
            request.setAttribute("pb", pb);
            request.setAttribute("cid", cid);
            //交给jsp完事
            return "product/product_list";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "分页查询商品出问题了");
            return "info";
        }
    }

    /**
     * 商品的详情
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String info(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取pid
            String pid = request.getParameter("pid");
            //调用service准备数据
            Product product = productService.findbyId(pid);
            //给页面展示
            request.setAttribute("p", product);
            return "product/product_info";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "查询商品出问题了");
            return "info";
        }
    }

    /**
     * 商品的详情回显
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String huixianById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取cid
        String pid = request.getParameter("pid");
        //调用service查询
        Product product;
        try {
            product = productService.findbyId(pid);
            //转换json格式
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setExcludes(new String[]{"pdate"});
            JSONObject fromObject = JSONObject.fromObject(product, jsonConfig);
            response.getWriter().print(JSONObject.fromObject(fromObject).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String del(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取cname值
        String pid = request.getParameter("pid");
        //调用service保存分类
        try {
            productService.delProductById(pid);
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
