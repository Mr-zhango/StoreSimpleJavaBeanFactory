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
    <%--富文本编辑器需要的js--开始--%>
    <!-- 富文本编辑器 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/plugins/kindeditor/themes/default/default.css"/>
    <script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugins/kindeditor/kindeditor-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugins/kindeditor/lang/zh_CN.js"></script>
    <%--富文本编辑器需要的js--结束--%>
    <script type="text/javascript">
        $(function () {
            $('#dg').datagrid({
                url: '${ctx}/product?md=findByPage',//商品数据的来源地址
                columns: [[
                    {field: 'pid', title: '商品id', width: 70},
                    {field: 'pname', title: '商品名称', width: 50},
                    {field: 'shop_price', title: '价格', width: 20},
                    {field: 'pdesc', title: '描述', width: 100},
                    {
                        field: 'is_hot', title: '是否热门', width: 20, formatter: function (value, row, index) {
                            if (value == 1) {
                                return "是";
                            } else {
                                return "否";
                            }

                        }
                    },
                    {
                        field: 'pimage', title: '图片', width: 60, formatter: function (value, row, index) {
                            return "<img src='${ctx}/" + value + "' width='60px' />"

                        }
                    }, {
                        field: '操作', title: '操作', width: 20, align: "center", formatter: function (value, row, index) {
                            return "<a href='javascript:;' onclick='huixian(\"" + row.pid + "\")'>修改</a>|<a href='javascript:;'  onclick='shanchu(\"" + row.pid + "\")'>删除</a>";
                        }
                    }
                ]],
                pagination: true,
                fit: true,
                fitColumns: true,
                toolbar: [{
                    iconCls: 'icon-add',
                    text: "添加商品",
                    handler: function () {
                        $("#dd").dialog("open").panel('refresh');
                    },//关闭缓存
                    cache: false,
                }]

            });
            //渲染下拉选择框
            $('#caid').combobox({
                url: '${ctx}/category?md=list',
                valueField: 'cid',   //控制的option值
                textField: 'cname'  //控制显示的内容
            });
        })

        //添加商品js函数
        function save_product() {
            //需要将保存商品 form表单提交
            $('#save_form').form('submit', {
                url: "${ctx}/upload",    //?
                success: function (data) {
                    //关闭对话框
                    $("#dd").dialog("close");
                    //服务器返回的数据
                    if ("1" == data) {
                        //成功了
                        parent.$.messager.show({
                            title: '我的消息',
                            msg: '添加商品成功',
                            timeout: 3000,
                            showType: 'slide',
                            //关闭缓存
                            cache: false,

                        });
                        $("#dg").datagrid("reload");
                        //清空form表单的内容
                        $('#save_form').form('clear');
                    } else {
                        //失败
                        $.messager.alert('我的消息', '添加商品失败！');
                    }
                }
            });
        }

        function huixian(pid) {
            //打开一个对话框
            $("#dd").dialog("open");
            //对话框中的表单内容  有原来的数据
            $('#save_form').form('load', '${ctx}/product?md=huixianById&pid=' + pid);
            //{cid:1,cname:"手机数码"}
        }

        //删除
        function shanchu(pid) {
            //弹出确认框
            $.messager.confirm('确认对话框', '您确认要删除吗?', function (r) {
                if (r) {
                    var url = "${ctx}/product";
                    var params = "md=del&pid=" + pid;
                    //发起请求  删除分类
                    $.post(url, params, function (data) {
                        //成功返回1
                        if ("1" == data) {
                            parent.$.messager.show({
                                title: '我的消息',
                                msg: '删除商品成功',
                                timeout: 5000,
                                showType: 'slide'
                            });
                            //重新加载数据
                            $("#dg").datagrid("reload");
                        } else {
                            //失败0
                            $.messager.alert('我的消息', '删除商品失败！');
                        }
                    });
                }
            });
        }

    </script>
</head>
<body>
<table id="dg"></table>
<div id="dd" class="easyui-dialog" title="My Dialog" style="width:500px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
    <form id="save_form" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>商品名称:</td>
                <td>
                    <input class="easyui-textbox" name="pname" style="width:100%">
                </td>
                <td>所属分类:</td>
                <td>
                    <input id="caid" name="cid" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>市场价格:</td>
                <td>
                    <input class="easyui-textbox" name="market_price" style="width:100%">
                </td>
                <td>商城价格:</td>
                <td>
                    <input class="easyui-textbox" name="shop_price" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>是否热门:</td>
                <td>
                    <select class="easyui-combobox" name="is_hot" style="width:100%" data-options="panelHeight:'auto'">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td>图片:</td>
                <td>
                    <input name="pimage" class="easyui-filebox" data-options="buttonText:'选择文件'" style="width:100%">
                </td>
            </tr>
            <tr>
                <td>商品描述:</td>
                <div class="col-md-10 data editer">
                    <td colspan="3">
                        <%--<input class="easyui-textbox" name="pdesc" data-options="multiline:true">--%>
                        <textarea rows="4" class="form-control" name="pdesc" placeholder="请填写商品描述111"></textarea>
                    </td>
                </div>
            </tr>
            <tr style="text-align:center;padding:5px 0">
                <td colspan="4">
                    <input type="button" value="保存" class="easyui-linkbutton" onclick="save_product()"
                           style="width:80px">
                    <input type="reset" value="重置" class="easyui-linkbutton" style="width:80px">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>