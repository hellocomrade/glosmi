<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>

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

<jsp:include page="topbar.jsp" flush="true">
	<jsp:param name="id" value="mod_dock"/>
</jsp:include>


<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
</body>
</html>