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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/kindeditor-4.1.10/themes/default/default.css"/>
    <script charset="utf-8" src="${pageContext.request.contextPath}/resources/kindeditor-4.1.10/kindeditor-all.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/resources/kindeditor-4.1.10/lang/zh-CN.js"></script>
    <%--富文本编辑器需要的js--结束--%>
    <script type="text/javascript">
        $(function () {
            $('#dg').datagrid({
                url: '${ctx}/product?md=findByPage',//商品数据的来源地址
                columns: [[
                    {field: 'pid', title: '商品id', width: 70},
                    {field: 'pname', title: '名字', width: 50},
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
                    },{
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
                        $("#dd").dialog("open");
                    }
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


        //
        //商品详情编辑器
        $(function () {
            //详情编辑器
            KindEditor.ready(function (K) {
                this.editor
                    = K.create('textarea[id="editor"]', {
                    items: ['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'multiimage',
                        'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                        'anchor', 'link', 'unlink'],
                    uploadJson: '/images',//指定上传图片的服务器端程序
                    fileManagerJson: '/images',//指定浏览远程图片的服务器端程序
                    allowFileManager: true
                });
            });
        });


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
                <td colspan="3">
                    <input class="easyui-textbox" name="pdesc" data-options="multiline:true" >
                    <%--<textarea id="editor" style="width:600px;height:400px;visibility:hidden;"></textarea>--%>
                </td>
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