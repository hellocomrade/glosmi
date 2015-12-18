<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="us.glos.mi.db.DBCache" %>

<jsp:useBean id="usr" scope="page" class="us.glos.mi.domain.UserParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New User</title>
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
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
	
<script type="text/javascript">
	function validate_addUser()
	{
		var result=true;
		if($("#usr1").val().length==0||validateEmail($("#usr1").val())==false)
		{
			textBoxError($("#usr1"));
			$("#err_usr1").html("<span style='color:red;'>User name must be a valid email</span>");
			result=false;
		}
		else
		{
			textBoxResume($("#usr1"));
			$("#err_usr1").html("");
		}
		if($("#fname1").val().length==0)
		{
			textBoxError($("#fname1"));
			$("#err_fname1").html("First name can not be empty");
			result=false;
		}
		else
		{
			textBoxResume($("#fname1"));
			$("#err_fname1").html("");
		}
		if($("#lname1").val().length==0)
		{
			textBoxError($("#lname1"));
			$("#err_lname1").html("Last name can not be empty");
			result=false;
		}
		else
		{
			textBoxResume($("#lname1"));
			$("#err_lname1").html("");
		}
		if($("#isadm").attr("checked")==false&&$("#ismod").attr("checked")==false&&$("#isapp").attr("checked")==false)
		{
			$("#err_isadm").html("&nbsp;At least one group needs to be checked");
			result=false;
		}
		else
			$("#err_isadm").html("");
		if($("#newpasswd1").val().length==0)
		{
			textBoxError($("#newpasswd1"));
			$("#err_newpasswd1").html("Password can not be empty");
			result=false;
		}
		else
		{
			textBoxResume($("#newpasswd1"));
			$("#err_newpasswd1").html("");
		}
		if($("#renewpsd1").val()!=$("#newpasswd1").val())
		{
			textBoxError($("#renewpsd1"));
			$("#err_renewpsd1").html("Passwords typed are not identical");
			result=false;
		}
		else
		{
			textBoxResume($("#renewpsd1"));
			$("#err_renewpsd1").html("");
		}
		if($("#country1").val().length==0&&($("#state1").val().length>0||$("#province1").val().length>0))
		{
			textBoxError($("#country1"));
			$("#err_country1").html("Country name is not specified");
			result=false;
		}
		else
		{
			textBoxResume($("#country1"));
			$("#err_country1").html("");
		}
		if($("#zip1").val().length>0)
		{
			if($("#country1 option:selected").val()=='ca'&&validateCAPostalcode($("#zip1").val())==false)
			{
				textBoxError($("#zip1"));
				$("#err_zip1").html("Postal code is not valid");
				result=false;
			}
			else if($("#country1 option:selected").val()=='us'&&validateUSZipcode($("#zip1").val())==false)
			{
				textBoxError($("#zip1"));
				$("#err_zip1").html("Zipcode is not valid");
				result=false;
			}
			else
			{
				textBoxResume($("#zip1"));
				$("#err_zip1").html("");
			}
		}
		else
		{
			textBoxResume($("#zip1"));
			$("#err_zip1").html("");
		}
		if($("#phone1").val().length>0&&validatePhone($("#phone1").val())==false)
		{
		
			textBoxError($("#phone1"));
			$("#err_phone1").html("Phone number is not valid");
			result=false;
		}
		else
		{
			textBoxResume($("#phone1"));
			$("#err_phone1").html("");
		}
		return result;
	}
	$(function(){
		$("#subtn").button();
		$("#retbtn").button();
		$("#btn_avl").button();
		$("#subtn").click(function(){
			if(validate_addUser())
			{
		
				$.post($("#frm_new").attr('action'),$("#frm_new").serialize(),
                                    function(data){
                               			if(data==0)
                               				confirmMessageBox("User has been created successfully!");
                               			else
                               			{
                               				errorMessageBox("Failed to add new user.");
                               				data=eval("("+data+")");
                               				var len=data.length;
                               				for(i=0;i<len;++i)
                               				{
                               					if(data[i].id=="isadm")
                               					{
                               						$("#err_"+data[i].id).html(data[i].err);
                               					}
                               					else
                               					{
                               						textBoxError($("#"+data[i].id+"1"));
                               						$("#err_"+data[i].id+"1").html(data[i].err);
                               					}
                               				}	
                               			}
                               			
            	});
            }
			return false;
		});
		$("#retbtn").click(function(){
			$("#usr1").val("");
			$("#fname1").val("");
			$("#lname1").val("");
			$("#org1").val("");
			$("#newpasswd1").val("");
			$("#renewpsd").val("");
			$("#add11").val("");
			$("#add21").val("");
			$("#city1").val("");
			$("#country1").val("");
			$("#state1").val("");
			$("#province1").val("");
			$("#country1").val("");
			$("#zip1").val("");
			$("#phone1").val("");
			$("#isadm").attr("checked",false);
			$("#ismod").attr("checked",false);
			$("#isapp").attr("checked",false);
		});
		$("#btn_avl").click(function(){
		<%
			String url=String.format("%s%s",this.getServletContext().getContextPath(),"/pub/useravail.glos");
			out.println("var url='"+url+"';");
		%>
						var email=$("#usr1").val();
						if(validateEmail(email))
						{
							$.get(url,{<%=usr.getUserParamName() %>:email},function(data)
															{
																if(data!='0')
																{
																	$("#err_usr1").html("<span style='color:#3366cc;'>"+email+" is available</span>");
																}
																else
																{
																	$("#err_usr1").html("<span style='color:red;'>"+email+" is not available</span>");
																}
															});
						}
						else
							errorMessageBox("It's not a valid email address!"); 
		});
	});
	$(document).ready(function() {
   		$("#country1").change(function(){
		if('ca'==$("#country1 option:selected").val())
		{
			$("#state1").hide();
			$("#province1").show();
		}
		else
		{
			$("#province1").hide();
			$("#state1").show();
			
		}
	});
 });
	
	
	
</script>	
</head>
<body>
<jsp:useBean id="usrap" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<jsp:include page="topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<jsp:include page="../WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:include page="/WEB-INF/controls/agency-widget.jsp" flush="true"/>
<form id="frm_new" action="useradd.glos">
	<div style="margin-left:auto;margin-right:auto;width:600px;" class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
		<div class="ui-widget-header ui-corner-all" style="margin:0.3em;">Register New User</div>
		<div class="newuserform">
				    			<div style="font-size:medium;">Email/User Name:</div>
				    			<input type="text" id="usr1" name="<%=usrap.getUserParamName() %>" value="" class="loginfont textbox-app newusertext ui-widget"/>&nbsp;&nbsp;<input type="button" id="btn_avl" value="Check Availability" class="ui-widget loginfont"/>
				    			<div id="err_usr1" style="color:red;"></div>
				    			<div style="font-size:medium;">First Name:</div>
				    			<input type="text" id="fname1" name="<%=usrap.getFirstNameParamName() %>" value="" class="loginfont textbox-app newusertext ui-widget"/>
				    			<span id="err_fname1" style="color:red;"></span>
				    		    <div style="font-size:medium;">Last Name:</div>
				    			<input type="text" id="lname1" name="<%=usrap.getLastNameParamName() %>" value="" class="loginfont textbox-app newusertext ui-widget"/>
				    			<span id="err_lname1" style="color:red;"></span>
				    			<div style="font-size:medium;">Orgnization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="org1" name="<%=usrap.getOrgnizationIdParamName() %>" value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
								<div>
									<a href="#" id="a_org_tool" style="font-size:smaller;">Add New Organization</a>
								</div>
<script type="text/javascript">
$("#a_org_tool").click(function(event){
	admin_org_tool();
});
</script>								
				    			
				    			<div style="font-size:medium;">Group:</div>
				    			<input type="checkbox" id="isadm" name="<%=usrap.getIsAdmParamName() %>" value="1" class="loginfont textbox-app"/>Administrator
				    			<input type="checkbox" id="ismod" name="<%=usrap.getIsModParamName() %>" value="1" class="loginfont textbox-app"/>Modeler
				    			<input type="checkbox" id="isapp" name="<%=usrap.getIsAppParamName() %>" value="1" class="loginfont textbox-app"/>Application Developer
				    			<span id="err_isadm" style="color:red;"></span>
				    			<div style="font-size:medium;">Password:</div>
		    					<input type="password" id="newpasswd1" name="<%=usrap.getNewPasswordParamName() %>" value="" class="loginfont textbox-app ui-widget"/>
		    		    		<span id="err_newpasswd1" style="color:red;"></span>
		    		    		<div style="font-size:medium;">Retype Password:</div>
		    					<input type="password" id="renewpsd1" name="txt_psdr" value="" class="loginfont textbox-app ui-widget"/>
				    			<span id="err_renewpsd1" style="color:red;"></span>
				    			<div style="font-size:medium;">Address1:</div>
				    			<input type="text" id="add11" name=<%=usrap.getStreet1ParamName() %> value="" class="loginfont textbox-app newusertext ui-widget"/>
				    	
				    		    <div style="font-size:medium;">Address2:</div>
				    			<input type="text" id="add21" name=<%=usrap.getStreet2ParamName() %> value="" class="loginfont textbox-app newusertext ui-widget"/>
				    	
				    		    <div style="font-size:medium;">City:</div>
				    			<input type="text" id="city1" name=<%=usrap.getCityParamName() %> value="" class="loginfont textbox-app newusertext ui-widget"/>
				    	
				    		    <div style="font-size:medium;">Country:</div>
				    			<select id="country1" name="<%=usrap.getCountryParamName() %>" class="loginfont textbox-app">
				    				<option value="">----</option>
				    				<option value="us" >United States</option>
				    				<option value="ca" >Canada</option>
				    			</select>
				    			<span id="err_country1" style="color:red;"></span>
				    		    <div style="font-size:medium;">State/Province:</div>
				    			<glc:statesselect id="state1" name="<%=usrap.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" />
				    			<glc:statesselect id="province1" name="<%=usrap.getProvinceParamName() %>" cssClass="loginfont textbox-app" country="ca" hide="true"/>
				    			<span id="err_state1" style="color:red;"></span>
				    		    <div style="font-size:medium;">Zip Code/Postal Code:</div>
				    			<input type="text" id="zip1" name=<%=usrap.getZipcodeParamName() %> value="" class="loginfont textbox-app newusertext ui-widget"/>
				    			<span id="err_zip1" style="color:red;"></span>
				    		    <div style="font-size:medium;">Phone:</div>
				    			<input type="text" id="phone1" name=<%=usrap.getPhoneParamName() %> value="" class="loginfont textbox-app newusertext ui-widget"/>
								<span id="err_phone1" style="color:red;"></span>
								
								<br/><br/>
								<input id="subtn" type="submit" value="Create" />&nbsp;&nbsp;
								<input id="retbtn" type="button" value="Reset"/>
		</div>	
	</div>
	<input type="hidden"  name="<%=usrap.getActionParamName() %>" value="new" />
	<input type="hidden"  name="<%=usrap.getAjaxParamName() %>" value="1" />
	
	
</form>
</body>
</html>