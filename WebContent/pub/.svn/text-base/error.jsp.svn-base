<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="org.glc.utils.ServerUtilities" %>
<%
ILiteralProvider literals=null;
literals=ServerUtilities.GetLiteralProvider();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("/pub/error.jsp")));
else
	out.println("Error");
%>
</title>
<style>
.resulterror
{
	font-weight:bold;
	font-size:14px;
	margin-top:1em;
	border:1px solid #b3b3b3;
	color:#353535;
	background:#ffcccc url('<%=this.getServletContext().getContextPath()%>/img/error.png') no-repeat;
	padding:0.5em;
	height:200px;
	padding-left:50px;
}
</style>
</head>
<body>
<div style="height:100px;width:100%;"></div>
<div style="margin-left:auto;margin-right:auto;width:500px;">
	<div class='resulterror'>
		<div>
			Oops, an unexpected error occurred. The reason might be the one of the folowing:
			<ul>
				<li>if you are a registered user, the session is timeout.</li>
				<li>The page you requested doesn't exist or you don't have the privilege to access.</li>
				<li>Server side error, please report to the system administrator.</li>
			</ul>
		</div>
		<p>You can try to recover by clicking the go-back button on your browser or click the link below to login again if you are a registered user.</p>
	</div>
	<div>
		<br/>
		<a href="<%=this.getServletContext().getContextPath()%>/pub">Go to login page</a>
	</div>
</div>
</body>
</html>