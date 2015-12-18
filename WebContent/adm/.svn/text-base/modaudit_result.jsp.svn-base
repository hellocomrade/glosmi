<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.utils.ServerUtilities"%>
<%
String val=null;
String msg=null;
if(session.getAttribute("MODEL_AUDIT_RESULT_VALUE")!=null&&!session.getAttribute("MODEL_AUDIT_RESULT_VALUE").equals("")
   &&session.getAttribute("MODEL_AUDIT_RESULT_MESSAGE")!=null&&!session.getAttribute("MODEL_AUDIT_RESULT_MESSAGE").equals(""))
{
	val=session.getAttribute("MODEL_AUDIT_RESULT_VALUE").toString();
	msg=session.getAttribute("MODEL_AUDIT_RESULT_MESSAGE").toString();
	
	session.removeAttribute("MODEL_AUDIT_RESULT_VALUE");
	session.removeAttribute("MODEL_AUDIT_RESULT_MESSAGE");
}
else
{
	response.sendRedirect("../mod");
	return;
}
User usr=(User)session.getAttribute(User.getClassName());
ILiteralProvider literals=ServerUtilities.GetLiteralProvider();


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("/adm/modaudit_result.jsp")));
else
	out.println("Model Audit Result");
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

	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
    if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
    {	
%>
<jsp:include page="topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<%
    }
%>
<div style="margin-left:auto;margin-right:auto;width:500px;">
<%
if(val.equals("0"))
	out.print(String.format("<div class='%s'>%s</div>","resultsuccess",msg));
else
	out.print(String.format("<div class='%s'>%s</div>","resulterror",msg));
%>
	

</div>
<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
</body>
</html>