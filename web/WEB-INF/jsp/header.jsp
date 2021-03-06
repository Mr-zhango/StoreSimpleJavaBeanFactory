<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>我的商城</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		//在页面加载成功以后 发起ajax 请求  要分类信息列表
		$(function(){
			var url="${ctx}/category"
			var params="md=list";	
			$.post(url,params,function(data){
			//数据返回以后  遍历返回分类列表 拼装成li标签 添加到固定位置
				if(data!=null){
					//遍历
					$.each(data,function(index,ele){
						//开始拼接li标签
						//<li><a href="product_list.htm">手机数码</a></li>
						var li="<li><a href='${ctx}/product?md=findByPageWithCate&pageNumber=1&cid="+ele.cid+"'>"+ele.cname+"</a></li>";
						$("#cateList").append(li);
					});
				}
			},"json");
		})
		
		</script>
	</head>
	<body>
		<div class="container-fluid">

			<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/resources/img/logo.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/resources/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top: 30px">
					<ol class="list-inline">
						<c:if test="${empty user }">
							<li><a href="${ctx }/login.jsp">登录</a></li>
							<li><a href="${ctx }/user?md=registUI">注册</a></li>
							<li><a href="${ctx }/cart?md=list">购物车</a></li>
						</c:if>
						<c:if test="${ not empty user }">
							<li>${user.name }欢迎您</li>
							<li><a href="${ctx }/user?md=logout">退出登录</a></li>
							<li><a href="${ctx }/order?md=findMyOrdersByPage&pageNumber=1">我的订单</a></li>
							<li><a href="${ctx }/cart?md=list">购物车</a></li>
						</c:if>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="index.jsp">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="cateList">
								
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>