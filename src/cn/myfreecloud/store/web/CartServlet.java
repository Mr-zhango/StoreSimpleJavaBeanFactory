package cn.myfreecloud.store.web;

import cn.myfreecloud.store.domain.Cart;
import cn.myfreecloud.store.domain.CartItem;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.service.ProductService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = -1162113947556291800L;

	private ProductService productService= BeanFactory.getInstance(ProductService.class);

    /**
     * 展示购物车-->跳转页面的方法
     */
    public String list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "cart/list";
    }

    /**
     * 获取购物车 封装方法
     * @param request
     * @return
     */
    private Cart getCart(HttpServletRequest request) {
        //尝试去session获取cart对象
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;

    }


    /**
     * 添加一个购物项
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取参数
            String pid = request.getParameter("pid");
            int num =Integer.parseInt( request.getParameter("num"));
            Product findbyId = productService.findbyId(pid);
            //封装成一个cartItem对象
            CartItem cartItem = new CartItem(findbyId, num);
            //把它添加到购物车????

            Cart cart= getCart(request);
            //添加购物项到购物车
            cart.addCartItem(cartItem);
            //重定向到购物车展示页面
            return "redirectx:"+request.getContextPath()+"/cart?md=list";

            //return "cart/list";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "添加购物车出问题了");
            return "info";
        }
    }

    /**
     * 删除购物项
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String del(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取productId
        String pid = request.getParameter("pid");
        //从购物删除

        //获取购物车
        Cart cart = getCart(request);
        //删除购物项
        cart.removeCartItem(pid);
        //重定向(也可以不适用重定向,使用ajax方法来实现)
        return "redirectx:"+request.getContextPath()+"/cart?md=list";
    }


	/**
	 * 清空购物项
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//从购物删除
		getCart(request).clear();
		//重定向
		return "redirectx:"+request.getContextPath()+"/cart?md=list";
	}
	
}
