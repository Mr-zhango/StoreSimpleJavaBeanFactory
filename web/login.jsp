<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        .container .row div {
            /* position:relative;
            float:left; */
        }

        font {
            color: #666;
            font-size: 22px;
            font-weight: normal;
            padding-right: 17px;
        }
    </style>
    <script type="text/javascript">
        function checkCode(obj) {
            //获取图片标签img,参数obj
            //改变obj对象的src的属性值
            //alert()
            obj.src = "${pageContext.request.contextPath}/checkImg?a=" + new Date().getTime();
        }

    </script>
</head>
<body>


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
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">手机数码<span class="sr-only">(current)</span></a></li>
                    <li><a href="#">电脑办公</a></li>
                    <li><a href="#">电脑办公</a></li>
                    <li><a href="#">电脑办公</a></li>
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


<div class="container">
    <div class="row">
        <div class="col-md-6">
            <!--<img src="<%--${pageContext.request.contextPath}--%>/resources/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
        </div>
        <div class="col-md-6">
            <div <%--style="width:440px;border:1px solid #E7E7E7;background:#fff;"--%>>
                <font>会员登录</font>USER LOGIN ${msg }
                <%
                    session.removeAttribute("msg");
                %>

                <div>&nbsp;</div>
                <form class="form-horizontal" action="${pageContext.request.contextPath }/user" method="post">
                    <input type="hidden" name="md" value="login">
                    <input type="hidden" name="xxxURL" value="${param.xxxURL }">

                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username" required="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="inputPassword3" placeholder="请输入密码" name="password" required="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword4" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="inputPassword4" name="code" placeholder="请输入验证码" required="true">
                        </div>
                        <div class="col-sm-3">
                            <td><img src="${pageContext.request.contextPath}/checkImg" onclick="checkCode(this)"/></td>
                        </div>

                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> 自动登录
                                </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input type="checkbox"> 记住用户名
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" width="100" value="登录" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/resources/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                                           height:35px;width:100px;color:white;">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div style="margin-top:50px;">
    <img src="${pageContext.request.contextPath}/resources/image/footer.jpg" width="100%" height="78" alt="我们的优势"
         title="我们的优势"/>
</div>

<div style="text-align: center;margin-top: 5px;">
    <ul class="list-inline">
        <li><a>关于我们</a></li>
        <li><a>联系我们</a></li>
        <li><a>招贤纳士</a></li>
        <li><a>法律声明</a></li>
        <li><a>友情链接</a></li>
        <li><a target="_blank">支付方式</a></li>
        <li><a target="_blank">配送方式</a></li>
        <li><a>服务声明</a></li>
        <li><a>广告声明</a></li>
    </ul>
</div>
<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
    Copyright &copy; 20014-2019 自由云商城 版权所有
</div>
</body>
</html>