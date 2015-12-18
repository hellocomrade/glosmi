<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.ILiteralProvider"%>
<%
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("usersearch.jsp")));
else
	out.println("Search User");
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
	
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	$(function(){
		$("#btn_su").button();
		$("#btn_su").keyup(function(event){
			if(event.keyCode==13)
				$("#btn_su").submit();
		}
		);
	});
	</script>
</head>
<body>
<jsp:include page="../../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<jsp:include page="messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include> 
<form action="usersearch.glos" method="GET">
	<div style="margin-left:auto;margin-right:auto;width:30%;" class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
		<div class="ui-widget-header ui-corner-all" style="margin:0.3em;">Search Users By Name or Email:</div>
		<div class="newuserform">
			
			<input type="text" id="key" name="key" value="" style="width:200px;"/>&nbsp;<input id="btn_su" type="submit" value="Search" />
			<br/>
			<div class="tip">
				<div>Search Tip:</div>
				<ul>
					<li>
						Entering user's first name, last name or email or any combination of three. 
					</li>
					<li>
						Wildcard(*) is supported for searching emails from same domain. So, a search like "*@foo.com" will match all emails with the domain
						name "foo.com".
					</li>
					<li>
						typing "all" as the keyword will display full user list.
					</li>
			</div>
		</div> 
		
	</div>
</form>
</body>
</html>