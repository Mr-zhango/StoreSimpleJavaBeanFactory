<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>自由云商城后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="${pageContext.request.contextPath }/css/general.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath }/css/main.css"
          rel="stylesheet" type="text/css"/>

    <style type="text/css">
        body {
            color: white;
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
<body style="background: #278296">
<center></center>
<form method="post"
      action="${pageContext.request.contextPath }/admin?md=adminLogin"
      target="_parent" name='theForm' onsubmit="return validate()">
    <table cellspacing="0" cellpadding="0" style="margin-top: 100px"
           align="center">
        <tr>
            <td style="padding-left: 200px">

                <h1>自由云商城后台管理系统</h1>
                    ${msg }
                <table>
                    <tr>
                        <td><h2>管理员姓名：</h2></td>
                        <td><input type="text" name="username" style="width:150px;height:30px;font-size:16px;font-family:arial;" required="true"/></td>
                    </tr>
                    <tr>
                        <td><h2>管理员密码：</h2></td>
                        <td><input type="password" name="password" style="width:150px;height:30px;font-size:16px;font-family:arial;" required="true"/></td>
                    </tr>
                    <tr>
                        <td><h2>请输入验证码:</h2></td>
                        <td><input type="text" name="code" style="width:150px;height:30px;font-size:16px;font-family:arial;"/ required="true"></td>
                        <td><img src="${pageContext.request.contextPath}/checkImg" onclick="checkCode(this)"/></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="进入管理系统" class="button"
                                   style="width:150px;height:60px;font-size:20px;font-family:arial;font-weight:bold;text-align:center;"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
<script language="JavaScript">
</script>
</body>