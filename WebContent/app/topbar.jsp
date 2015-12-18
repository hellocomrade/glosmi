<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%
User usr=(User)session.getAttribute(User.getClassName());
%>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="../js/jquery1.8/interface.js"></script>
<script type="text/javascript" src="../js/controls/user-widget.js"></script>
<!--[if lt IE 7]>
 <style type="text/css">
 .dock img { behavior: url(../js/iepngfix.htc) }
 </style>
<![endif]-->

<div align="right" class="head-container">
	<a href="http://glos.us/" target="_blank">
        <img src="../img/logo_glos.gif" alt="GLOS" hspace="0" align="left" border="0">
    </a>
    <img src="../img/logo_glmi.gif" alt="Great Lakes Observing System | Modeling Inventory" hspace="0" align="left" border="0">    
	<div class="dock" id="<%=request.getParameter("id") %>">
		<div class="dock-container">
    		<a class="dock-item" href="../pub"><img src="../img/fisheye/home.png" alt="home" /><span>Home</span></a> 
<%
	if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
	{
%> 			
  			<a class="dock-item" href="../mod/"><img src="../img/fisheye/models.png" alt="model" /><span>Models</span></a> 
<%
	}
%>
  			<a class="dock-item" href="../app/"><img src="../img/fisheye/applications.png" alt="application" /><span>Applications</span></a> 
			<!--  
  			<a class="dock-item" href="#"><img src="../img/fisheye/video.png" alt="video" /><span>Video</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/history.png" alt="history" /><span>History</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/calendar.png" alt="calendar" /><span>Calendar</span></a> 
  			-->
  			<a class="dock-item" href="../app/fileadmin.glos?islist=1"><img src="../img/fisheye/attachment_mgt.png" alt="files" /><span>Files</span></a> 
  			<a class="dock-item" href="../pub/search.html" target="_blank"><img src="../img/fisheye/search_page.png" alt="search" /><span>Search</span></a> 
  			<a class="dock-item" href="#" onclick="Dlg_Update_Profile();return false;"><img src="../img/fisheye/profile_entry.png" alt="Profile" /><span>Profile</span></a> 
  			<a class="dock-item" href="#" onclick="Dlg_Passwd();return false;"><img src="../img/fisheye/password.png" alt="password" /><span>Password</span></a> 
  			<a class="dock-item" href="../pub/?logout"><img src="../img/fisheye/logout.png" alt="logout" /><span>Logout</span></a> 
  		</div>
	</div>
</div>
<div style="margin-top:40px;height:1px;" align="center"></div>
<script type="text/javascript">
	
	$(document).ready(
		function()
		{
			$('#<%=request.getParameter("id") %>').Fisheye(
				{
					maxWidth: 50,
					items: 'a',
					itemsText: 'span',
					container: '.dock-container',
					itemWidth: 40,
					proximity: 50,
					halign : 'right'
				}
			)
		}
	);

</script>

<div id="div_addon">
    <form id="frm_chgp" action="../app/useradmin.glos">
		<jsp:include page="../WEB-INF/controls/user-password.jsp" flush="true"/>
	</form>
	<form id="frm_update" action="../app/useradmin.glos">
		<jsp:include page="../WEB-INF/controls/user-update.jsp" flush="true"/>
	</form>
	<jsp:include page="../WEB-INF/controls/org-request.jsp" flush="true">
		<jsp:param name="url" value="../pub/orgrequest.glos"/>
	</jsp:include>
</div>