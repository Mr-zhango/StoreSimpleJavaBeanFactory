<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#dg').datagrid({
                url: '${ctx}/category?md=list',    //数据来源地址
                columns: [[
                    {field: 'cid', title: '分类id', width: 100},
                    {field: 'cname', title: '分类名字', width: 100},
                    /*
                        value:代表此field 映射的值
                        row:此次遍历到对象整体
                        index:遍历的索引

                    */

                    {
                        field: '操作', title: '操作', width: 100, align: "center", formatter: function (value, row, index) {
                            return "<a href='javascript:;' onclick='huixian(\"" + row.cid + "\")'>修改</a>|<a href='javascript:;'  onclick='shanchu(\"" + row.cid + "\")'>删除</a>";
                        }
                    }
                ]],
                fit: true,
                fitColumns: true,
                toolbar: [{
                    iconCls: 'icon-add',
                    text: "添加分类",
                    handler: function () {
                        //把添加对画框显示
                        $("#dd").dialog("open");
                    }
                }]
            });


        })

        function saveCategory() {
            //提交表单数据
            $('#saveForm').form('submit', {
                url: "${ctx}/category?md=add",
                success: function (data) {
                    //关闭对话框
                    $("#dd").dialog("close");
                    //服务器返回的数据
                    if ("1" == data) {
                        //成功了
                        parent.$.messager.show({
                            title: '我的消息',
                            msg: '添加分类成功',
                            timeout: 5000,
                            showType: 'slide'
                        });
                        $("#dg").datagrid("reload");
                    } else {
                        //失败
                        $.messager.alert('我的消息', '添加失败！');
                    }
                }
            });
        }

        function huixian(cid) {
            //打开一个对话框
            $("#dd1").dialog("open");
            //对话框中的表单内容  有原来的数据
            $('#updateForm').form('load', '${ctx}/category?md=huixianById&cid=' + cid);
            //{cid:1,cname:"手机数码"}
        }

        function updateCategory() {
            //提交表单数据
            $('#updateForm').form('submit', {
                url: "${ctx}/category?md=update",
                success: function (data) {
                    //关闭对话框
                    $("#dd1").dialog("close");
                    //服务器返回的数据
                    if ("1" == data) {
                        //成功了
                        parent.$.messager.show({
                            title: '我的消息',
                            msg: '更新分类成功',
                            timeout: 5000,
                            showType: 'slide'
                        });
                        $("#dg").datagrid("reload");
                    } else {
                        //失败
                        $.messager.alert('我的消息', '更新失败！');
                    }
                }
            });
        }

        //删除
        function shanchu(cid) {
            //弹出确认框
            $.messager.confirm('确认对话框', '您确认要删除吗?', function (r) {
                if (r) {
                    var url = "${ctx}/category";
                    var params = "md=del&cid=" + cid;
                    //发起请求  删除分类
                    $.post(url, params, function (data) {
                        //成功返回1
                        if ("1" == data) {
                            parent.$.messager.show({
                                title: '我的消息',
                                msg: '删除分类成功',
                                timeout: 5000,
                                showType: 'slide'
                            });
                            //重新加载数据
                            $("#dg").datagrid("reload");
                        } else if ("2" == data) {
                            //失败0
                            $.messager.alert('我的消息', '该分类下存在商品不能删除!!!');
                        } else {
                            //失败0
                            $.messager.alert('我的消息', '删除失败！');
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<table id="dg"></table>
<div id="dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-add',resizable:true,modal:true,closed:true">
    <form method="post" id="saveForm">
        <table width="100%">
            <tr align="center">
                <td>
                    分类名称:
                </td>
                <td>
                    <input type="text" class="easyui-textbox" name="cname"><br>
                </td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <input type="button" class="easyui-linkbutton" value="保存" onclick="saveCategory()"
                           style="width:80px"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dd1" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-add',resizable:true,modal:true,closed:true">
    <form method="post" id="updateForm">
        <input type="hidden" name="cid">
        <table width="100%">
            <tr align="center">
                <td>
                    分类名称:
                </td>
                <td>
                    <input type="text" class="easyui-textbox" name="cname"><br>
                </td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <input type="button" class="easyui-linkbutton" value="保存" onclick="updateCategory()"
                           style="width:80px"/>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>