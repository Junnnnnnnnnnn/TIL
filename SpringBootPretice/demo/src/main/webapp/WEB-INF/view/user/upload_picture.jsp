<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<form action="/openapi/predict" method="GET">
	<input type="file">
	<input type="submit">
</form>
</body>
</html>
