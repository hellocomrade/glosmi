<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Great Lakes Model Inventory: User Management</title>
    	<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
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
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
   		
      

		
	</script>
</head>
<body>
<jsp:include page="topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<jsp:include page="../WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include> 
<table width="30%" cellspacing="3" cellpadding="5" border="0" align="center" style="margin-top:100px;">
	<tr align="center">
		<td>
			<a id="a_addnew" href="./addusers.jsp" style="font-size:larger;"><img src="../img/addnewuser.png" alt="Add New User"/></a>
		</td>
		<td>
			<a id="a_search" href="./usersearch.glos" style="font-size:larger;"><img src="../img/existinguser.png" alt="Existing User"/></a>
		</td>
		
	</tr>
	<tr align="center">
		<td>
			<span style="font-weight:bold;">Add New User</span>
		</td>
		<td>
			<span style="font-weight:bold;">Existing User</span>
		</td>
	</tr>
</table>
</body>
</html>