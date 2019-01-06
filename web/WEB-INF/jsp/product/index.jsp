<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：轮播条
            -->
			<div class="container-fluid">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
					</ol>
					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${pageContext.request.contextPath}/resources/img/1.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/resources/img/2.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/resources/img/3.jpg">
							<div class="carousel-caption">

							</div>
						</div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/resources/img/4.jpg">
                            <div class="carousel-caption">

                            </div>
                        </div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：商品显示
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/resources/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath}/resources/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="product_info.htm">
							<img src="${pageContext.request.contextPath}/resources/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
					<c:forEach items="${hots }" var="p">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${ctx }/product?md=info&pid=${p.pid}">
								<img src="${pageContext.request.contextPath}/${p.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="${ctx }/product?md=info&pid=${p.pid}" style='color:#666'>${p.pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${p.shop_price }</font></p>
						</div>
					</c:forEach>
					
				</div>
			</div>
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：广告部分
            -->
            <div class="container-fluid">
				<img src="${pageContext.request.contextPath}/resources/products/hao/ad.jpg" width="100%"/>
			</div>
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：商品显示
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/resources/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath}/resources/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="product_info.htm">
							<img src="${pageContext.request.contextPath}/resources/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
					<c:forEach items="${news }" var="p">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${ctx }/product?md=info&pid=${p.pid}">
								<img src="${pageContext.request.contextPath}/${p.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="${ctx }/product?md=info&pid=${p.pid}" style='color:#666'>${p.pname }</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${p.shop_price }</font></p>
						</div>
					</c:forEach>
				</div>
			</div>			
<%@ include file="../foot.jsp" %>