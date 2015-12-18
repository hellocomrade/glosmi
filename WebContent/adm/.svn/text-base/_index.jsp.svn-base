<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.glc.domain.User" %>
<%
User usr=(User)session.getAttribute(User.getClassName());

	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.sortable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/interface.js"></script>
<!--[if lt IE 7]>
 <style type="text/css">
 .dock img { behavior: url(iepngfix.htc) }
 </style>
<![endif]-->
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
	<title>Insert title here</title>
</head>
<body>
	<div class="dock" id="dock">
		<div class="dock-container">
    		<a class="dock-item" href="#"><img src="../img/fisheye/home.png" alt="home" /><span>Home</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/email.png" alt="contact" /><span>Contact</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/portfolio.png" alt="portfolio" /><span>Portfolio</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/music.png" alt="music" /><span>Music</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/video.png" alt="video" /><span>Video</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/history.png" alt="history" /><span>History</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/calendar.png" alt="calendar" /><span>Calendar</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/link.png" alt="links" /><span>Links</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/rss.png" alt="rss" /><span>RSS</span></a> 
  			<a class="dock-item" href="#"><img src="../img/fisheye/rss2.png" alt="rss" /><span>RSS2</span></a> 
  		</div>
	</div>
<script type="text/javascript">
	
	$(document).ready(
		function()
		{
			$('#dock').Fisheye(
				{
					maxWidth: 50,
					items: 'a',
					itemsText: 'span',
					container: '.dock-container',
					itemWidth: 40,
					proximity: 50,
					halign : 'center'
				}
			)
		}
	);

</script>
	<div style="margin-top:59px;height:1px;" align="center"></div>
		
	<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center">
		<tr valign="top">
		<td width="10%">
		</td>
		<td>
		<div style="position:relative;width:100%">&nbsp;
		<div class="column" style="top:0;left:0px;position:absolute;">
			<div class="portlet">
				<div class="portlet-header">profolio</div>
				<div class="portlet-content">
					<div>Hi <%=usr.getFirstName() %></div>
					<div> Your last login was at <%=usr.getLastLoginDate().toString() %></div>
					<br/>
					<a href="#">Edit profolio</a><br/>
					<a href="#">Change password</a>
				</div>
			</div>
			<div class="portlet">
				<div class="portlet-header">User Management</div>
				<div class="portlet-content">
				    <div>There are ? users in the system</div>
				    <div>? users have privilege to add new model</div>
				    <div>? users have privilege to add new application</div>
				    <div>? users have the administrator privilege</div>
				    <div>There are ? user(s) currently online</div>
				    <br/>
				    <a href="#">More details</a>
				</div>
			</div>
	
			

		</div>

		<div class="column" style="top:0;left:375px;position:absolute;">

			<div class="portlet">
				<div class="portlet-header">Model Inventory</div>
				<div class="portlet-content">
					<div>There are ? models in the inventory</div>
					<div>? of them are submitted and approved</div>
					<div>? of them are saved</div>
					<div>? of them are submitted and waiting for the approval</div>
					<br/>
				    <a href="#">More details</a>
				</div>

			</div>
			<div class="portlet">
				<div class="portlet-header">Application Inventory</div>
				<div class="portlet-content">
					<div>There are ? applications in the inventory</div>
					<div>? of them are submitted and approved</div>
					<div>? of them are saved</div>
					<div>? of them are submitted and waiting for the approval</div>
					<br/>
				    <a href="#">More details</a>
				</div>
			</div>

		</div>

		<div class="column" style="right:0px;top:0;position:absolute;">

			<div class="portlet">
				<div class="portlet-header">Pending Audit Requests</div>

				<div class="portlet-content">
					<div>There are ? pending request(s)</div>
					<div>? of them are model(s)</div>
					<div>? of them are model(s)</div>
					<br/>
				    <a href="#">More details</a>
				</div>
			</div>

	
			

		</div>
		</div>
		
		</td>
		<td width="10%">
		</td>
	</table>
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