package cn.myfreecloud.store.web;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.domain.*;
import cn.myfreecloud.store.service.OrderService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.PaymentUtil;
import cn.myfreecloud.store.utils.UUIDUtil;
import cn.myfreecloud.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService= BeanFactory.getInstance(OrderService.class);
	

	/**
	 * 
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		
		if(isValid){
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				//修改订单状态
				//根据id 获取订单  调用service 修改了
				

                //公网环境使用这个代码,直到程序接收到success之后才去修改数据库状态
				
				response.getWriter().print("success");
			}
			Order findById;
			try {
				findById = orderService.findById(r6_Order);
				findById.setState(Constant.ORDER_YIFUKUAN);
				orderService.update(findById);
				return "info";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			//
			request.setAttribute("msg", "小伙子逗我呢?");
			return "info";
		}
		
		
		
		return null;
	}
	/**
	 * 准备支付
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String toPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取参数

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        try {
            name = new String(name.getBytes("UTF-8"), "UTF-8");
            address = new String(address.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

		String oid = request.getParameter("oid");

		String telephone = request.getParameter("telephone");
		
		//这些属于订单信息 完善
		try {
			Order order=orderService.findById(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			//调用service更新订单信息
			orderService.update(order);
			////////////////////////////////考虑第三方支付/////////////////////////
			
			// 组织发送支付公司需要哪些数据
			String pd_FrpId = request.getParameter("pd_FrpId");
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			//需要提交的金额
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址(回调函数)
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);


			
			//发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			////////////////////////////////考虑第三方支付/////////////////////////
            //重定向到第三方的支付平台
			return "redirectx:"+sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "暂未开通支付功能");
			return "info";
		}
	}
	/**
	 * 查看某个订单的详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String info(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取订单id
		String oid = request.getParameter("oid");
		//调用service查询
		Order order;
		try {
			order = orderService.findByIdWithItems(oid);
			request.setAttribute("order", order);
			//交给jsp展示
			return "order/order_info";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查看订单失败");
			return "info";
		}
		
	}
	/**
	 * 分页查看当前登录的订单列表 分页
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取当前登录人
		User user = currentUser(request);
		if(user==null){
			//去登录去
			return "redirect:"+request.getContextPath()+"/login";
		}
		//获取页码
		int pageNumber =Integer.parseInt( request.getParameter("pageNumber"));
		//设置pageSize=3
		int pageSize=3;
		//把三个值作为参数 传给service 负责查询
		PageBean<Order> orders;
		try {
			orders = orderService.findMyOrdersByPage(user.getUid(),pageNumber,pageSize);
			//将pageBean放入request域中
			request.setAttribute("pb", orders);
			//请求转发到订单列表展示
			return "order/order_list";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "分页查看订单失败");
			return "info";
		}
		
	}
	/**
	 * 创建订单
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取当前登录用户  没登录 登录去
		User user=currentUser(request);
		if(user==null){
			//去登录去(使用重定向方法)
			return "redirect:"+request.getContextPath()+"/login";
		}
		//获取购物车
		Cart cart = getCart(request);
		//购物车--->订单
		Order order = new Order();
		order.setOid(UUIDUtil.getId());
		order.setOrdertime(new Date());
		order.setState(Constant.ORDER_WEIFUKUAN);
		order.setTotal(cart.getTotal());
		order.setUid(user.getUid());
		
		//提高性能
		List<OrderItem> ois=new ArrayList<>(cart.getItems().size());
		//购物项--->订单项
		Collection<CartItem> items = cart.getItems();
		for (CartItem cartItem : items) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtil.getId());
			orderItem.setOid(order.getOid());
			orderItem.setCount(cartItem.getNum());
			orderItem.setPid(cartItem.getProduct().getPid());
			orderItem.setSubtotal(cartItem.getSubTotal());
			ois.add(orderItem);
		}
		
		order.setItems(ois);
		try {
			orderService.save(order);
			cart.clear();
			//request.setAttribute("order", order);
			//return "order/order_info";
			return "redirectx:"+request.getContextPath()+"/order?md=info&oid="+order.getOid();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "提交订单出问题了");
			return "info";
			
		}
	}
	private User currentUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			return user;
		}
		return null;
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
	
}
