<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>自由云商城后台管理系统</title>
    <%--easyUI使用的js--开始--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.easyui.min.js"></script>
    <%--easyUI使用的js--结束--%>
    <script type="text/javascript">
        function addTab(obj) {
            //obj代表啥?
            var text = $(obj).text();
            //获取属性
            var url = $(obj).attr("data-url");
            //判断有无
            if ($("#tt").tabs("exists", text)) {
                $("#tt").tabs("select", text);
            } else {
                //新添选项卡
                $("#tt").tabs("add", {
                    title: text,
                    selected: true,
                    closable: true,
                    content: '<iframe src="' + url + '" style="width:100%;height:100%;border:0px;"></iframe>'
                })
            }

        }
    </script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:600px;height:400px;" data-options="fit:true">

    <div data-options="region:'west',title:'菜单管理'" style="width:180px;">
        <%--data-options="fit:true"  自适应大小效果--%>
        <div id="aa" class="easyui-accordion" data-options="fit:true">
            <div title="分类管理" data-options="iconCls:'icon-search'" style="overflow:auto;padding:10px;">
                <a href="javascript:;" onclick="addTab(this)" data-url="${ctx}/admin?md=category_list">分类列表</a>
            </div>
            <div title="商品管理" data-options="iconCls:'icon-add'" style="padding:10px;">
                <a href="javascript:;" onclick="addTab(this)" data-url="${ctx}/admin?md=product_list">商品列表</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center'" style="background:#eee;">
        <div id="tt" class="easyui-tabs" data-options="fit:true">
            <div title="欢迎页" style="">
                欢迎使用自由云网络后台管理系统
            </div>
        </div>
    </div>
</div>
</body>
</html>