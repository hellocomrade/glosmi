<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.domain.UserStats" %>
<%@ page import="org.glc.utils.UTCDateFormatter" %>
<%@ page import="org.glc.utils.HTMLUtilities" %>
<%@ page import="us.glos.mi.db.SystemStatistics" %>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="us.glos.mi.domain.ModAdminParam"%>
<%@page import="us.glos.mi.domain.AppAdminParam"%>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%
UTCDateFormatter format=new UTCDateFormatter();
User usr=(User)session.getAttribute(User.getClassName());

SystemStatistics stat=(SystemStatistics)request.getAttribute(SystemStatistics.class.getName());
UserStats ustats=stat.getUserStats();

ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
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
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.tabs.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
	
	<script type="text/javascript" src="../js/common.js"></script>
	<!--  <script type="text/javascript" src="../js/controls/index.js"></script>-->

	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./web-info.controls.index.css" rel="stylesheet" type="text/css" />
	<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("admin_index.jsp")));
else
	out.println("Administrator Home");
%>
	</title>
</head>
<body>
<jsp:include page="../../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<jsp:include page="messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:include page="/WEB-INF/controls/agency-widget.jsp" flush="true"/>
	
		<div style="margin-left:auto;margin-right:auto;width:960px;padding-left:20px;">
		<div class="column" style="float:left;">
			<div class="portlet">
				<div class="portlet-header">profile</div>
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
			<div class="portlet">
				<div class="portlet-header">User Management</div>
				<div class="portlet-content">
				    <div>There are <%=stat.getTotalUserCount() %> users in the system</div>
				    <div><%=ustats.modUser %> users have privilege to add new model</div>
				    <div><%=ustats.appUser %> users have privilege to add new application</div>
				    <div><%=ustats.adminUser %> users have the administrator privilege</div>
				    <div>There are <%=stat.getOnlineUserCount() %> user(s) currently online</div>
				    <br/>
				    <a href="../adm/addusers.jsp" target="_self">Add New User</a>
				    <strong>|</strong>
				    <a href="../adm/useradmin.jsp" target="_self">More Options</a>
				</div>
			</div>
	
			

		</div>
<%
int[] counts=stat.getModelCounts(usr.getId());
%>
		<div class="column" style="float:left;">

			<div class="portlet">
				<div class="portlet-header">Model Inventory</div>
				<div class="portlet-content">
					<div>There are <%=counts[0] %> models in the inventory</div>
					<div><%=counts[3] %> of them are submitted and approved</div>
					<div><%=counts[4] %> of them are disabled</div>
					<br/>
					<div>There are <%=counts[2] %> models waiting for approval</div>
					<br/>
					<div>There are <%=counts[1] %> saved drafts</div>
<%
	if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
	{
%>
					<br/>
					<a href="../mod/modadmin.glos?mod_action=new">Add New Model</a>
					<strong>|</strong>
				    <a href="../mod/">More Options</a>
<%
	}
%>
				</div>

			</div>
<%
counts=stat.getAppCounts(usr.getId());
%>
			<div class="portlet">
				<div class="portlet-header">Application Inventory</div>
				<div class="portlet-content">
					<div>There are <%=counts[0] %> applications in the inventory</div>
					<div><%=counts[3] %> of them are submitted and approved</div>
					<div><%=counts[4] %> of them are disabled</div>
					<br/>
					<div>There are <%=counts[2] %> applications waiting for the approval</div>
					<br/>
					<div>There are <%=counts[1] %> saved drafts</div>
<%
	if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
	{
%>
					<br/>
				    <a href="../app/appadmin.glos?app_action=new">Add New Application</a>
					<strong>|</strong>
				    <a href="../app/">More Options</a>
<%
	}
%>
				</div>
			</div>

		</div>
<%
counts=stat.getPendings();
ModAdminParam modparam=new ModAdminParam();
AppAdminParam apparam=new AppAdminParam();
%>
		<div class="column" style="float:left;">

			<div class="portlet">
				<div class="portlet-header">Pending Audit Requests</div>

				<div class="portlet-content">
					<div>There are <%=counts[0]+counts[1]%> pending request(s)</div>
					<div><%=counts[0] %> of them are model(s)</div>
					<div><%=counts[1] %> of them are application(s)</div>
<%
	if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
	{
%>
					<br/>
					<a href="../mod/modadmin.glos?<%=modparam.getActionParamName() %>=submit">Go to Model Pending List</a><br/>
<%
	}
	if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
	{
%>
					<a href="../app/appadmin.glos?<%=apparam.getActionParamName() %>=submit">Go to Application Pending List</a>
<%
	}
%>
				</div>
			</div>
<%
counts=stat.getAttachmentCounts(usr.getId());
%>
			<div class="portlet">
				<div class="portlet-header">Attachment Management</div>

				<div class="portlet-content">
					<div>There are <%=counts[0]%> attachment(s)</div>
					<div><%=counts[1]%> of them are attached to models</div>
					<div><%=counts[2]%> of them are attached to applications</div>
					<br/>
				    <a href="../adm/fileadmin.glos?islist=1">View Attachment</a>
				</div>
			</div>
			<div class="portlet">
				<div class="portlet-header">Reference Table Management</div>

				<div class="portlet-content">
					<a href="#" id="a_org_tool">Add New Organization</a><br/><br/>
					<a href="#" id="a_ref_add_cat">Add New Item to Model Category</a><br/><br/>
					<a href="#" id="a_ref_add_the">Add New Item to GLOS Theme</a><br/>
					
				</div>
			</div>
			

		</div>
		</div>
		
		
<div id='dlg_ref_cat' title='Add New Item to Model Category' style="display:none;">
	<div style="font-size:smaller;">New Category Name:</div>
	    <input type="text" id="ref_cat_new" value="" class="loginfont textbox-app" size="25"/>
		<div id="errmsg_ref_cat" style="color:red;font-size:smaller;">
		</div>
</div>

<div id='dlg_ref_the' title='Add New Item to GLOS Theme' style="display:none;">
	<div style="font-size:smaller;">New GLOS Theme Name:</div>
	    <input type="text" id="ref_the_new" value="" class="loginfont textbox-app" size="25"/>
		<div id="errmsg_ref_the" style="color:red;font-size:smaller;">
		</div>
</div>

<jsp:useBean id="refparam" scope="page" class="us.glos.mi.domain.ReferenceParam" />		
	
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

		$("#a_ref_add_cat").click(function(){
			$("#dlg_ref_cat").dialog({
	    	height:200,
	    	width:300,
	    	modal:true,
	    	position:'center',
	    	buttons:{
	    				'Close':function()
	    				{
							$("input#ref_cat_new").val("");
               				$("#errmsg_ref_cat").text("");
               				textBoxResume($("#ref_cat_new"));
			    			$(this).dialog("close");
						},
	    				'Add':function(){
	    					    if($.trim($("input#ref_cat_new").val()).length>0)
	    					    {
	    					        $.get("../adm/refopt.glos",
	    					        	  {<%=refparam.getCategoryParamName()%>:$.trim($("input#ref_cat_new").val())},
	                                    function(data){
	                               			if(data==0)
	                               				confirmMessageBox("New Item has been added to the Category list!");
	                               			else
	                               				errorMessageBox("Failed to add new item into category list. Please try again later!"); 
	                               			
	                               });
	                               	$("input#ref_cat_new").val("");
	                               	$("#errmsg_ref_cat").text("");
	                               	textBoxResume($("#ref_cat_new"));
	    						}
	    					    else
	    					    {
	    					    	textBoxError($("#ref_cat_new"));
	    					    	$("#errmsg_ref_cat").text("Please fill out the Category Name.");
	    					    }
	    					
	    				}
	    			}
	    	});
		});

		$("#a_ref_add_the").click(function(){
			$("#dlg_ref_the").dialog({
	    	height:200,
	    	width:300,
	    	modal:true,
	    	position:'center',
	    	buttons:{
	    				'Close':function()
	    				{
							$("input#ref_the_new").val("");
               				$("#errmsg_ref_the").text("");
               				textBoxResume($("#ref_the_new"));
			    			$(this).dialog("close");
						},
	    				'Add':function(){
	    					    if($.trim($("input#ref_the_new").val()).length>0)
	    					    {
	    					        $.get("../adm/refopt.glos",
	    					        	  {<%=refparam.getThemeParamName()%>:$.trim($("input#ref_the_new").val())},
	                                    function(data){
	                               			if(data==0)
	                               				confirmMessageBox("New Item has been added to the GLOS Theme list!");
	                               			else
	                               				errorMessageBox("Failed to add new item into GLOS Theme list. Please try again later!"); 
	                               			
	                               });
	                               	$("input#ref_the_new").val("");
	                               	$("#errmsg_ref_the").text("");
	                               	textBoxResume($("#ref_the_new"));
	    						}
	    					    else
	    					    {
	    					    	textBoxError($("#ref_the_new"));
	    					    	$("#errmsg_ref_the").text("Please fill out the Theme Name.");
	    					    }
	    					
	    				}
	    			}
	    	});
		});
		
		$("#a_org_tool").click(function(event){
			admin_org_tool();
		});
	});
</script>


</body>
</html>