<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="us.glos.mi.domain.UserAdminParam" %>

<jsp:useBean id="usrap" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<!--  
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="../js/user-widget.js"></script>
-->
<div id='dlg_passwd' title='Change My Password' style="display:none;">
		    		<div style="font-size:smaller;">Old Password:</div>
		    		<input type="password" id="oldpsd"
		    		name="<%=usrap.getOldPasswordParamName() %>" value="" class="loginfont textbox-app" size="25"/>
		    	
		    		<div style="font-size:smaller;">New Password:</div>
		    		<input type="password" id="newpsd"
		    		name="<%=usrap.getNewPasswordParamName() %>" value="" class="loginfont textbox-app" size="25"/>
		    	
		    		<div style="font-size:smaller;">Retype Password:</div>
		    		<input type="password" id="renewpsd"
		    		name="txt_psdr" value="" class="loginfont textbox-app" size="25"/>
		    	   	<input type="hidden" name="<%=usrap.getActionParamName() %>" value="chgpasswd" />
		    	    <input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
		    	    <div id="errmsg" style="color:red;font-size:smaller;">
		    		
		    	    </div>
</div>