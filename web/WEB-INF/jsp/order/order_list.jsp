<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../header.jsp" %>	

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<c:forEach items="${pb.data }" var="order">
								<tbody>
									<tr class="success">
										<th colspan="2">
										订单编号:
										<a href="${ctx }/order?md=info&oid=${order.oid } ">${order.oid }</a>
										</th>
										<th colspan="1">状态:
											<c:if test="${order.state==0 }">未付款</c:if>
											<c:if test="${order.state==1 }">已付款</c:if>
											<c:if test="${order.state==2 }">已发货</c:if>
											<c:if test="${order.state==3 }">已完成</c:if>
										 </th>
										<th colspan="2">下单时间:
											<fmt:formatDate value="${order.ordertime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										 </th>
									</tr>
									<tr class="warning">
										<th>图片</th>
										<th>商品</th>
										<th>价格</th>
										<th>数量</th>
										<th>小计</th>
									</tr>
									<c:forEach items="${order.items }" var="oi">
										<tr class="active">
											<td width="60" width="40%">
												<input type="hidden" name="id" value="22">
												<img src="${ctx}/${oi.product.pimage}" width="70" height="60">
											</td>
											<td width="30%">
												<a target="_blank">${oi.product.pname }</a>
											</td>
											<td width="20%">
												&yen;${oi.product.shop_price }
											</td>
											<td width="10%">
												${oi.count }
											</td>
											<td width="15%">
												<span class="subtotal">&yen;${oi.subtotal}</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
						</c:forEach>
					
					</table>
				</div>
			</div>
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
			<!-- class="disabled" -->
				<c:if test="${pb.pageNumber==1 }">
					<li class="disabled" >
						<a href="javascript:;" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pb.pageNumber!=1 }">
					<li >
						<a href="${ctx }/order?md=findMyOrdersByPage&pageNumber=${pb.pageNumber-1}&cid=${cid}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				
				<!--中间 页码  -->
				<c:forEach begin="1" end="${ pb.totalPage}" var="n">
					<c:if test="${pb.pageNumber-n<=5&&n-pb.pageNumber<=4 }">
							<c:if test="${n==pb.pageNumber }">
								<li  class="active"><a href="javascript:;">${n }</a></li>
							</c:if>
							<c:if test="${n!=pb.pageNumber }">
								<li ><a href="${ctx }/order?md=findMyOrdersByPage&pageNumber=${n}&cid=${cid}">${n }</a></li>
							</c:if>
					</c:if>
				</c:forEach>
				
				<c:if test="${pb.pageNumber>=pb.totalPage }">
					<li class="disabled">
						<a href="javascript:;" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				
				<c:if test="${pb.pageNumber<pb.totalPage }">
					<li >
						<a href="${ctx }/order?md=findMyOrdersByPage&pageNumber=${pb.pageNumber+1}&cid=${cid}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
			</ul>
		</div>

<%@ include file="../foot.jsp" %>	