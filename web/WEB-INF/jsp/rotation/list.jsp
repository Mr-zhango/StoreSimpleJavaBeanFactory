<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>	
<script>
	function del(pid){
		//弹出对话框
		if(confirm("确定删除此购物项?")){
			//发起求(页面重定向)
			location.href="${ctx}/cart?md=del&pid="+pid;			
		}
		
	}
	function clearCart(){
		//弹出对话框
		if(confirm("确定删除购物车?")){
            //发起求(页面重定向)
			location.href="${ctx}/cart?md=clear";			
		}
		
	}

	function createOrder(){
        //发起求(页面重定向)
		location.href="${ctx}/order?md=create";			
		
	}


</script>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							
							<c:forEach items="${cart.items }" var="ci">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${ctx}/${ci.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank">${ci.product.pname }</a>
									</td>
									<td width="20%">
										&yen;${ci.product.shop_price }
									</td>
									<td width="10%">
										${ci.num }
									</td>
									<td width="15%">
										<span class="subtotal">&yen;${ci.subTotal}</span>
									</td>
									<td>
										<a href="javascript:;" class="delete" onclick="del('${ci.product.pid}')">删除</a>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					商品金额: <strong style="color:#ff6600;">&yen;${cart.total }元</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<c:if test="${not empty cart.items }">
						<a href="javascript:;" id="clear" class="clear" onclick="clearCart()">清空购物车</a>
						<a href="javascript:;">
							<input type="button" onclick="createOrder()" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/resources/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
							height:35px;width:100px;color:white;">
						</a>
					</c:if>
				</div>
			</div>

		</div>
<%@ include file="../foot.jsp" %>	
