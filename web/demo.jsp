<%@page import="com.itheima.store.domain.Demo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Demo demo= new  Demo();
		demo.setId(1);
		demo.setName("xiaming");
		request.setAttribute("demo", demo);
	%>
	
	${demo.id }
</body>
</html>