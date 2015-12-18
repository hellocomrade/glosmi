<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="org.glc.utils.ServerUtilities" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%
ILiteralProvider literals=null;
literals=ServerUtilities.GetLiteralProvider();

User usr=(User)session.getAttribute(User.getClassName());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.mouse.js"></script>
	
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	
	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="../pub/web-info.controls.index.css" rel="stylesheet" type="text/css" />
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("/mod/index.jsp")));
else
	out.println("My Models");
%>
</title>
<script type="text/javascript">
$(document).ready(function() {
	   $('#a_addnew').mouseover(function(){
		   $('#div_help').html("Creat a new model record and submit it to the system for reviewing. You could always save it as a draft during editting.");
		   $('#div_help').show();
	   });
	   $('#a_approved').mouseover(function(){
		   $('#div_help').html("Access approved models. You are allowed to update them and also customize the public interface by adding pictures and reference files.");
		   $('#div_help').show();
	   });
	   $('#a_pending').mouseover(function(){
		   $('#div_help').html("Check the list of models you submitted that are currently waiting for being reviewed.");
		   $('#div_help').show();
	   });
	   $('#a_draft').mouseover(function(){
		   $('#div_help').html("Retrieve the drafts you saved and contiune your editing. Note that you can only save 8 files in the draft box.");
		   $('#div_help').show();
	   });
	 });
</script>
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

<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:useBean id="modparam" scope="page" class="us.glos.mi.domain.ModAdminParam" />
	
	<table width="30%" cellspacing="3" cellpadding="5" border="0" align="center" style="margin-top:100px;padding-left:20px;">
		<tr align="center">
			<td>
				<a id="a_addnew" href="./modadmin.glos?<%=modparam.getActionParamName() %>=new" style="font-size:larger;"><img src="../img/newmodel.png" alt="New Model"/></a>
			</td>
			<td>
				<a id="a_approved" href="./modadmin.glos?<%=modparam.getActionParamName() %>=list" style="font-size:larger;"><img src="../img/approvemodel.png" alt="Approved Model"/></a>
			</td>
			<td>
				<a id="a_pending" href="./modadmin.glos?<%=modparam.getActionParamName() %>=submit" style="font-size:larger;"><img src="../img/pendingmodel.png" alt="Pending Model"/></a>
			</td>
			<td>
				<a id="a_draft" href="./moddraftadmin.glos?<%=modparam.getActionParamName() %>=list" style="font-size:larger;"><img src="../img/savedraft.png" alt="Saved Draft"/></a>
			</td>
			
		</tr>
		<tr align="center">
			<td>
				<span style="font-weight:bold;">New Model</span>
			</td>
			<td>
				<span style="font-weight:bold;">Approved Model</span>
			</td>
			<td>
				<span style="font-weight:bold;">Pending Model</span>
			</td>
			<td>
				<span style="font-weight:bold;">Saved Draft</span>
			</td>
			
		</tr>
		<tr align="left">
			<td colspan="4">
				<br/>
				<div id="div_help" style="display:none;font-size:14px;font-family:arial,sans-serif;" class="tip"></div>
			</td>
		</tr>
	</table>
</body>
</html>