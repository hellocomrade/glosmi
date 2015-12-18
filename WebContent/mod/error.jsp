<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.utils.ServerUtilities"%>
<%
User usr=(User)session.getAttribute(User.getClassName());
ILiteralProvider literals=ServerUtilities.GetLiteralProvider();
String msg=null;
if(null!=session.getAttribute("MODEL_ERROR"))
{
	msg=session.getAttribute("MODEL_ERROR").toString().trim();
	session.setAttribute("MODEL_ERROR",null);
}
else
{
	response.sendRedirect("./");
	return;
}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("/mod/error.jsp")));
else
	out.println("Error");
%>
</title>
    <script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.mouse.js"></script>
	
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>

	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="./mod.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
    if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
    {	
%>
<jsp:include page="../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<%
    }
    else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
    {
%>
<jsp:include page="topbar.jsp" flush="true">
	<jsp:param name="id" value="mod_dock"/>
</jsp:include>
<%
    }
   
%>
<div style="margin-left:auto;margin-right:auto;width:500px;">
	<div class='resulterror'>
<%=msg%>
	</div>
</div>
</body>
</html>