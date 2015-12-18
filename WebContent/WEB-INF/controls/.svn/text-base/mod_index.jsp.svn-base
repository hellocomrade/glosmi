<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@ page import="org.glc.utils.UTCDateFormatter" %>
<%@ page import="org.glc.utils.HTMLUtilities" %>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="us.glos.mi.db.SystemStatistics" %>
<%
UTCDateFormatter format=new UTCDateFormatter();
User usr=(User)session.getAttribute(User.getClassName());
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
SystemStatistics stat=(SystemStatistics)request.getAttribute(SystemStatistics.class.getName());
%>
<jsp:useBean id="usrap" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
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
	<!--  <script type="text/javascript" src="../js/controls/index.js"></script>-->

	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="./web-info.controls.index.css" rel="stylesheet" type="text/css" />
	<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("mod_index.jsp")));
else
	out.println("Modeler Home");
%>
	</title>
</head>
<body>
<jsp:include page="../../mod/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<jsp:include page="messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>

	
		<div style="margin-left:auto;margin-right:auto;width:700px;padding-left:20px;">
		<div class="column" style="float:left;">
			<div class="portlet">
				<div class="portlet-header">Profile</div>
				<div class="portlet-content">
					<div>Hi <%=HTMLUtilities.filterDisplay(usr.getFirstName()) %></div>
					<div>Your last login was at 
					    <glc:datedisplay datetime="<%=format.format(usr.getLastLoginDate()) %>" jspath="../js/tags/"/>
					</div>

					<br/>
					<a href="#" onclick="Dlg_Update_Profile();return false;">Edit Profile</a>
					<strong>|</strong>
					<a href="#" onclick="Dlg_Passwd();return false;">Change Password</a>
				</div>
			</div>
<%
int[] counts=stat.getAttachmentCounts(usr.getId());
%>
			<div class="portlet">
				<div class="portlet-header">Attachment Management</div>
				<div class="portlet-content">
					
					<div>Your have <%=counts[0]%> attachment(s)</div>
					<div><%=counts[1]%> are attached to models</div>
					<div><%=counts[2]%> are attached to applications</div>
					<br/>
					<a href="../mod/fileadmin.glos?islist=1">View Attachment</a>
				</div>
			</div>
	
			

		</div>
<%
counts=stat.getModelCounts(usr.getId());
%>
		<div class="column" style="float:right;">

			<div class="portlet">
				<div class="portlet-header">Model Inventory</div>
				<div class="portlet-content">
					<div>I have <%=counts[0] %> models in the inventory</div>
					<div><%=counts[3] %> of them are submitted and approved</div>
					<div><%=counts[4] %> of them are disabled</div>
					<br/>
					
					<div>I have <%=counts[2] %> models that are pending</div>
					
					<br/>
					<div>I have <%=counts[1] %> saved drafts</div>
					<br/>
					<a href="../mod/modadmin.glos?mod_action=new">Add New Model</a>
					<strong>|</strong>
				    <a href="../mod/">More Options</a>
				</div>

			</div>
<%
	if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
	{
		counts=stat.getAppCounts(usr.getId());
%>
			<div class="portlet">
				<div class="portlet-header">Application Inventory</div>
				<div class="portlet-content">
					<div>I have <%=counts[0] %> applications in the inventory</div>
					<div><%=counts[3] %> of them are submitted and approved</div>
					<div><%=counts[4] %> of them are disabled</div>
					<br/>
					<div>I have <%=counts[2] %> models that are pending</div>
					<br/>
					<div>I have <%=counts[1] %> saved drafts</div>
					<br/>
				    <a href="../app/appadmin.glos?app_action=new">Add New Application</a>
					<strong>|</strong>
				    <a href="../app/">More Options</a>
				</div>
			</div>
<%
	}
%>

		</div>

		
		</div>
		
		
	
		
	
<script type="text/javascript">
	$(function() {
		$(".column").sortable({
			connectWith: '.column'
		});

		$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
			.find(".portlet-header")
				.addClass("ui-widget-header ui-corner-all")
				.prepend('<span class="ui-icon ui-icon-minusthick"></span>')
				.end()
			.find(".portlet-content");

		$(".portlet-header .ui-icon").click(function() {
			$(this).toggleClass("ui-icon-minusthick").toggleClass("ui-icon-plusthick");
			$(this).parents(".portlet:first").find(".portlet-content").toggle();
		});

		$(".column").disableSelection();
	});
</script>


</body>
</html>