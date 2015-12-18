<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String home=request.getAttribute("HOME").toString();
String last_url=request.getAttribute("LAST_URI").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link href="pub.css" rel="stylesheet" type="text/css" />
<style>
body{
	background:url("../img/fisheye/main-bg.gif") repeat scroll 0 0 #FFFFFF;
	font:12px Arial,Helvetica,sans-serif;
	margin:0;
	padding:0;
}
</style>
</head>
<body>
<div style="margin-left:auto;margin-right:auto;width:500px;margin-top:200px;">
	<div class='tip'>
		<div>
			<span style="font-size:16px;font-weight:bold;">Please select the page you want to go to:</span>
			<ul>
				<li>
					<a href="<%=home%>">Your Home Page</a><br/><br/>
				</li>
				<li>
					<a href="<%=last_url%>">The Last Page You Accessed Before Login</a>
				</li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>