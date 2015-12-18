<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.glc.domain.User" %>
<%@ page import="org.glc.utils.HTMLUtilities" %>
<%@ page import="org.glc.utils.UTCDateFormatter" %>
<%@ page import="us.glos.mi.db.DBCache" %>
<%@page import="org.glc.ILiteralProvider"%>
<%
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
%>
<jsp:useBean id="usrap" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("usersearchresult.jsp")));
else
	out.println("User Search Result");
%>
</title>
	<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.mouse.js"></script>
	
	<script type="text/javascript" src="../js/jquery1.8/datatable/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.tabs.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link type="text/css" href="../js/jquery1.8/datatable/css/data_table_jui.css" rel="stylesheet" />
	<link href="./adm.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
function trim(str) {
	$("#extract").html(str);
	str=$.trim($("#extract span").text());
	return str;
}

jQuery.fn.dataTableExt.oSort['date-time-asc'] = function(a, b) {
	var astr=trim(a);
	var bstr=trim(b);
	var x=0;
	var y=0;
	if (astr != ''&&astr!='Never') {
		var frDatea = astr.split(' ');
		var frTimea = frDatea[1].split(':');
		var frDatea2 = frDatea[0].split('-');
		x = (frDatea2[0] + frDatea2[1] + frDatea2[2] + frTimea[0] + frTimea[1] + frTimea[2]) * 1;
	} else {
		x = -1;
	}

	if (bstr != ''&&bstr!='Never') {
		var frDateb = bstr.split(' ');
		var frTimeb = frDateb[1].split(':');
		frDateb = frDateb[0].split('-');
		y = (frDateb[0] + frDateb[1] + frDateb[2] + frTimeb[0] + frTimeb[1] + frTimeb[2]) * 1;		                
	} else {
		y = -1;		                
	}
	var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
	return z;
};

jQuery.fn.dataTableExt.oSort['date-time-desc'] = function(a, b) {
	var astr=trim(a);
	var bstr=trim(b);
	var x=0;
	var y=0;
	if (astr != ''&&astr!='Never') {
		var frDatea = astr.split(' ');
		var frTimea = frDatea[1].split(':');
		var frDatea2 = frDatea[0].split('-');
		var x = (frDatea2[0] + frDatea2[1] + frDatea2[2] + frTimea[0] + frTimea[1] + frTimea[2]) * 1;		                
	} else {
		var x = -1;		                
	}

	if (bstr != ''&&bstr!='Never') {
		var frDateb = bstr.split(' ');
		var frTimeb = frDateb[1].split(':');
		frDateb = frDateb[0].split('-');
		var y = (frDateb[0] + frDateb[1] + frDateb[2] + frTimeb[0] + frTimeb[1] + frTimeb[2]) * 1;		                
	} else {
		var y = -1;		                
	}		            
	var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));		            
	return z;
}; 
	</script>
	<script type="text/javascript">
<%
	String url=String.format("%s%s",this.getServletContext().getContextPath(),"/pub/useravail.glos");
	out.println("var url_adm='"+url+"';");
	url=String.format("%s%s",this.getServletContext().getContextPath(),"/adm/userman.glos");
	out.println("var url_man='"+url+"';");
%>
	var oTable;
	var selectedTr;
	function validate_admin_update_usr()
	{
		var result=true;
		var msg="";
		if($("#fname1").val().length==0)
		{
			textBoxError($("#fname1"));
			msg+="<div>First Name can not be empty.</div>";
			result=false;
		}
		else
			textBoxResume($("#fname1"));
		if($("#lname1").val().length==0)
		{
			textBoxError($("#lname1"));
			msg+="<div>Last Name can not be empty.</div>";
			result=false;
		}
		else
			textBoxResume($("#lname1"));
		if($("#country1").val().length==0&&($("#state1").val().length>0||$("#province1").val().length>0))
		{
			textBoxError($("#country1"));
			msg+="<div>Country name is not specified</div>";
			result=false;
		}
		else
			textBoxResume($("#country1"));
		if($("#zip1").val().length>0)
		{
			if($("#country1 option:selected").val()=='ca'&&validateCAPostalcode($("#zip1").val())==false)
			{
				textBoxError($("#zip1"));
				msg+="<div>Postal code is not valid.</div>";
				result=false;
			}
			else if($("#country1 option:selected").val()=='us'&&validateUSZipcode($("#zip1").val())==false)
			{
				textBoxError($("#zip1"));
				msg+="<div>Zipcode is not valid.</div>";
				result=false;
			}
			else
				textBoxResume($("#zip1"));
		}
		else
			textBoxResume($("#zip1"));
		if($("#phone1").val().length>0&&validatePhone($("#phone1").val())==false)
		{
			
			textBoxError($("#phone1"));
			msg+="<div>Phone number is not valid.</div>";
			result=false;
		}
		else
			textBoxResume($("#phone1"));	
		if(result==false)
			$("#errmsg_update_usr").html("<br/>"+msg);
		else
			$("#errmsg_update_usr").html("");
		return result;
	}
	function validate_admin_reset_passwd()
	{
		var result=true;
		var msg="";
		if($("#newpsd1").val().length==0)
		{
			textBoxError($("#newpsd1"));
			msg+="<div>Password can not be empty.</div>";
			result=false;
		}
		else
			textBoxResume($("#newpsd1"));
		if($("#newpsd1").val()!=$("#renewpsd1").val())
		{
			textBoxError($("#renewpsd1"));
			msg+="<div>Passwords are not identical.</div>";
			result=false;
		}
		else
			textBoxResume($("#renewpsd1"));
		if(result==false)
			$("#errmsg_update_passwd").html("<br/>"+msg);
		else
			$("#errmsg_update_passwd").html("");
		return result;
	}
	function validate_admin_chg_grp()
	{
		var result=true;
		var msg="";
		if($("#isadm").attr("checked")==false&&
		   $("#ismod").attr("checked")==false&&
		   $("#isapp").attr("checked")==false)
		{
			msg="<div>At least one group is needed.</div>"
			result=false;
		}
		if(result==false)
			$("#errmsg_chg_grp").html("<br/>"+msg);
		else
			$("#errmsg_chg_grp").html("");
		return result;
	}
	var user_tabs=null;
	$(function() {
		
	 	
		user_tabs=$("#user_tabs").tabs();
		//user_tabs.hide();
		$("#btn_profile_update").button();
		$("#btn_profile_update").click(function(){
			if(validate_admin_update_usr())
			{
		
				$.post($("#frm_adm_update_user").attr('action'),$("#frm_adm_update_user").serialize(),
                                    function(data){
                               			if(data==0)
                               			{
                               				if(selectedTr!=null)
                               				{
                               					selectedTr.context.children[1].innerHTML=$.trim($("#lname1").val());
                               					selectedTr.context.children[2].innerHTML=$.trim($("#fname1").val());
                               					selectedTr.context.children[4].innerHTML=$.trim($("#org1").val());
                               				}
                                   			confirmMessageBox("User's profile has been updated successfully!");
                               			}
                                   		else
                               			{
                               				errorMessageBox("Failed to update user's profolio.");
                               				parseFieldError(eval("("+data+")"),$("#errmsg_update_usr"));
                               				
                               			}
                 
                               			
            	});
            }
			return false;
		});
		$("#btn_password_reset").button();
		$("#btn_password_reset").click(function(){
			if(validate_admin_reset_passwd())
			{
		
				$.post($("#frm_adm_chnage_passwd").attr('action'),$("#frm_adm_chnage_passwd").serialize(),
                                    function(data){
                               			if(data==0)
                               				confirmMessageBox("User's password has been reset!");
                               			else
                               			{
                               				errorMessageBox("Failed to reset user's password.");
                               				parseFieldError(eval("("+data+")"),$("#errmsg_chg_grp"));
                               				
                               			}
                 
                               			
            	});
            }
			return false;
		});
		$("#btn_chg_grp").button();
		$("#btn_chg_grp").click(function(){
			if(validate_admin_chg_grp())
			{
				$.post($("#frm_adm_change_grp").attr('action'),$("#frm_adm_change_grp").serialize(),
                        function(data){
                   			if(data==0)
                   				confirmMessageBox("User's group has been updated!");
                   			else
                   			{
                   				errorMessageBox("Failed to update user's group.");
                   				parseFieldError(eval("("+data+")"),$("#errmsg_update_passwd"));
                   				
                   			}
				});
			}
			return false;
		});
		
		$("#btn_chg_stat").button();
		$("#btn_chg_stat").click(function(){
			if(true)
			{
				$.post($("#frm_adm_change_status").attr('action'),$("#frm_adm_change_status").serialize(),
                        function(data){
                   			if(data==0)
                   			{
								if(selectedTr!=null)
                   					selectedTr.context.children[6].innerHTML=$("#active").attr("checked")==true?"Activated":"Disabled";
                   				confirmMessageBox("User's status has been updated!");
                   			}
                   			else
                   			{
                   				errorMessageBox("Failed to update user's status.");
                   				parseFieldError(eval("("+data+")"),$("#errmsg_chg_stat"));
                   				
                   			}
				});
			}
			return false;
		});
		
	});
	var user_tabs_dialog=null;
	$(document).ready( function() {
		
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
		$("#uresult tbody").click(function(event) {
			/*$(oTable.fnSettings().aoData).each(function (){
				$(this.nTr).removeClass('row_selected');
			});*/
			if(selectedTr!=null)
				selectedTr.removeClass('row_selected');
			selectedTr=$(event.target.parentNode);
			//alert(selectedTr.context.children[1].innerHTML);
			$(event.target.parentNode).addClass('row_selected');
			
			var pos=oTable.fnGetPosition(event.target);
			var data=oTable.fnGetData( pos[0] );
			//alert(data[0]);
			//selectedTr.context.children[1].innerHTML="hello";
			var id=$.trim(data[0]);
			id=id.replace(/\n/g,"");
			$.get(url_man,{uid:id,isajax:1},function(data)
									{
										
										fillForm(data);
									});
			/*$("#dlg_user_editor").dialog({
			    	height:600,
			    	width:650,
			    	//position:[500,150],
			    	modal:true,
			    	buttons:{
			    				'Close':function(){
 									
 									$(this).dialog("close");}
							}
			    				
			    });*/
			 //$("#dlg_user_editor").parent().appendTo($("#frm_usr_update"));
			//user_tabs.show();
			if(user_tabs_dialog==null)
			{
				user_tabs_dialog=user_tabs.dialog({
		    		height:600,
		    		width:650,
		    	
		    		modal:true,
		    		buttons:{
		    				'Close':function(){
									
									$(this).dialog("close");}
						}
		    				
		    	});
			}
			else
				user_tabs_dialog.dialog('open');
		});
		oTable=$('#uresult').dataTable( {
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"aoColumns": [
							null,
							null,
							null,
							null,
							null,
							{ "sType": "date-time" },
							null
						]
		} );
		
		//fnShowHide(0);
		
	} );
		function switch2Loc(id,time)
		{
			var value=time+' GMT';
	        var result=Date.parse(value);
	        if(result!=null)
	            $("#"+id).html(result.toString("yyyy-MM-dd HH:mm:ss"));
		}
		function fillForm(data)
		{
			resetTextBox();
			var usr=eval("("+data+")");
			$("#uid").html(usr.usr);
			$("#u_email").val(usr.usr);
			$("#u_email_p").val(usr.usr);
			$("#u_email_g").val(usr.usr);
			$("#u_email_s").val(usr.usr);
			$("#fname1").val(usr.fname);
			$("#lname1").val(usr.lname);
			$("#add11").val(usr.street1);
			$("#add21").val(usr.street2);
			$("#city1").val(usr.city);
			$("#zip1").val(usr.zip);
			$("#phone1").val(usr.phone);
			$("#org1").val(usr.orgid);
			$("#country1").val(usr.country);
			if(usr.country=='us')
			{
				$("#state1").val(usr.state);
				$("#state1").show();
				$("#province1").hide();
			}
			else if(usr.country=='ca')
			{
				$("#province1").val(usr.state);
				$("#state1").hide();
				$("#province1").show();
			}
			
			if(usr.isadm)
				$("#isadm").attr("checked",true);
			else
				$("#isadm").attr("checked",false);
			if(usr.ismod)
				$("#ismod").attr("checked",true);
			else
				$("#ismod").attr("checked",false);
			if(usr.isapp)
				$("#isapp").attr("checked",true);
			else
				$("#isapp").attr("checked",false);
			if(usr.isactivate)
			{
				$("#active").attr("checked",true);
				$("#disable").attr("checked",false);
			}
			else
			{
				$("#disable").attr("checked",true);
				$("#active").attr("checked",false);
			}
			
		}
		function resetTextBox()
		{
			textBoxResume($("#fname1"));
			textBoxResume($("#lname1"));
			textBoxResume($("#country1"));
			textBoxResume($("#zip1"));
			textBoxResume($("#phone1"));
			$("#errmsg_update_usr").html("");
			$("#errmsg_update_passwd").html("");
			$("#errmsg_chg_grp").html("");
			$("#errmsg_chg_stat").html("");
			$("#newpsd1").val("");
			$("#renewpsd1").val("");
			textBoxResume($("#newpsd1"));
			textBoxResume($("#renewpsd1"));
			
			
		}
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
	<div style="margin-left:auto;margin-right:auto;width:1080px;">
		<table cellpadding="0" cellspacing="0" border="0" id="uresult" class="display">
			<thead>
				<tr>
					<th width="2%">ID</th>
					<th width="15%">Last Name</th>
					<th width="15%">First Name</th>
					<th width="20%">Email</th>
					<th width="30%">Affiliation</th>
					<th width="15%">Last Login</th>
					<th width="3%">Status</th>
				</tr>
			</thead>
			<tbody>
		
<%
ArrayList<User> list=null;
String temp=null;
String id=null;
if(request.getAttribute("USER_SEARCH_RESULT_KEY") instanceof ArrayList)
{
	list=(ArrayList<User>)request.getAttribute("USER_SEARCH_RESULT_KEY");
	if(!list.isEmpty())
	{
		UTCDateFormatter format=new UTCDateFormatter();
		for(User usr:list)
		{
			out.println("<tr>");
			out.println("<td>");
			out.println(HTMLUtilities.filterDisplay(String.format("%d",usr.getId())));
			out.println("</td>");
			out.println("<td>");
			out.println(HTMLUtilities.filterDisplay(usr.getLastName()));
			out.println("</td>");
			out.println("<td>");
			out.println(HTMLUtilities.filterDisplay(usr.getFirstName()));
			out.println("</td>");
			out.println("<td>");
			out.println(HTMLUtilities.filterDisplay(usr.getEmail()));
			out.println("</td>");
			out.println("<td>");
			out.println(HTMLUtilities.filterDisplay(usr.getOrgnization().getName()));
			out.println("</td>");
			out.println("<td>");
			if(usr.getLastLoginDate()==null)
				out.println("Never");
			else
			{
				temp=format.format(usr.getLastLoginDate());
				id=String.format("span_date_%s",Integer.toString(usr.getId()));
				out.println(String.format("<span id='%s'></span>",id));
				out.println("<script>");
				out.println(String.format("switch2Loc('%s','%s');",id,temp));
				out.println("</script>");
			}
			out.println("</td>");
			out.println("<td>");
			if(usr.isActivate())
				out.println("Activated");
			else
				out.println("Disabled");
			out.println("</td>");
			out.println("</tr>");
		}
		
	}
}
%>
			</tbody>
		</table>
<div style="display:none;" id="extract"></div>		
	</div>
	
		
			
			<div id="user_tabs" title="User Editor" style="display:none;">
				<ul>
					<li><a href="#tab-profolio">Profolio</a></li>
					<li><a href="#tab-password">Password</a></li>
					<li><a href="#tab-group">Group</a></li>
					<li><a href="#tab-status">Status</a></li>
				</ul>
				<div id="tab-profolio">
					<form id="frm_adm_update_user" action="useradmin.glos">
					<table>
						<tr>
							<td colspan="2">
								
				    			<span>User Name/Email:</span><div class="loginfont textbox-app" id="uid"></div>
				    		
							</td>
						</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">First Name:</div>
				    			<input type="text" id="fname1" name="<%=usrap.getFirstNameParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Last Name:</div>
				    			<input type="text" id="lname1" name="<%=usrap.getLastNameParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Address1:</div>
				    			<input type="text" id="add11" name="<%=usrap.getStreet1ParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Address2:</div>
				    			<input type="text" id="add21" name="<%=usrap.getStreet2ParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    		    <div style="font-size:smaller;">City:</div>
				    			<input type="text" id="city1" name="<%=usrap.getCityParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Country:</div>
				    			<select id="country1" name="<%=usrap.getCountryParamName() %>" class="loginfont textbox-app">
				    				<option value="">----</option>
				    				<option value="us">United States</option>
				    				<option value="ca">Canada</option>
				    			</select>
				    		</td>
				    		<td>	
				    		    <div style="font-size:smaller;">State/Province:</div>
				    			<glc:statesselect id="state1" name="<%=usrap.getStateParamName() %>" selected="" cssClass="loginfont textbox-app" country="us" />
				    			<glc:statesselect id="province1" name="<%=usrap.getProvinceParamName() %>" selected="" cssClass="loginfont textbox-app" country="ca" hide="true" />
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    			<input type="text" id="zip1" name="<%=usrap.getZipcodeParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Phone:</div>
				    			<input type="text" id="phone1" name="<%=usrap.getPhoneParamName() %>" value="" class="loginfont textbox-app" size="25"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="font-size:smaller;">Orgnization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="org1" name="<%=usrap.getOrgnizationIdParamName() %>" value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
							</td>
						</tr>
					</table>
						<input type="hidden" name="<%=usrap.getActionParamName() %>" value="update" />
						<input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
						<input id="u_email" type="hidden" name="<%=usrap.getUserParamName() %>" value="" />
						<a href="#" style="font-size:smaller;">What if my orgnization is not on the list?</a>
						<div id="errmsg_update_usr" style="color:red;font-size:smaller;">
		    		
		    	    	</div>
						<br/><input id="btn_profile_update" type="submit" value="Update" />
					</form>
					
				</div>
				<div id="tab-password">
					<form id="frm_adm_chnage_passwd" action="useradmin.glos">
						<div style="font-size:smaller;">New Password:</div>
		    			<input type="password" id="newpsd1" name="<%=usrap.getNewPasswordParamName() %>" value="" class="loginfont textbox-app" size="25"/>
		    			<div style="font-size:smaller;">Retype Password:</div>
		    			<input type="password" id="renewpsd1" name="txt_psdr1" value="" class="loginfont textbox-app" size="25"/>
		    	   		<input type="hidden" name="<%=usrap.getActionParamName() %>" value="chgpasswd" />
		    	    	<input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
		    	    	<input id="u_email_p" type="hidden" name="<%=usrap.getUserParamName() %>" value="" />
		    	    	<div id="errmsg_update_passwd" style="color:red;font-size:smaller;"></div>
		    			<br/>
		    			<input id="btn_password_reset" type="submit" value="Reset" />
					</form>
				</div>
				<div id="tab-group">
					<form id="frm_adm_change_grp" action="userman.glos">
						<div style="font-size:medium;">Group:</div>
				    		<input type="checkbox" id="isadm" name="<%=usrap.getIsAdmParamName() %>" value="1" class="loginfont textbox-app"/>Administrator
				    		<input type="checkbox" id="ismod" name="<%=usrap.getIsModParamName() %>" value="1" class="loginfont textbox-app"/>Modeler
				    		<input type="checkbox" id="isapp" name="<%=usrap.getIsAppParamName() %>" value="1" class="loginfont textbox-app"/>Application Developer
						<input type="hidden" name="<%=usrap.getActionParamName() %>" value="chggroup" />
						<input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
						<input id="u_email_g" type="hidden" name="<%=usrap.getUserParamName() %>" value="" />
						<div id="errmsg_chg_grp" style="color:red;font-size:smaller;"></div>
		    			<br/><input id="btn_chg_grp" type="submit" value="Update" />
					</form>
				</div>
				<div id="tab-status">
					<form id="frm_adm_change_status" action="userman.glos">
						<div style="font-size:medium;">Status:</div>
				    		<input type="radio" id="active" name="<%=usrap.getIsActivateParamName() %>" value="1" class="loginfont textbox-app"/>Activated
				    		<input type="radio" id="disable" name="<%=usrap.getIsActivateParamName() %>" value="0" class="loginfont textbox-app"/>Disabled
				    	<input type="hidden" name="<%=usrap.getActionParamName() %>" value="chgstatus" />
						<input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
						<input id="u_email_s" type="hidden" name="<%=usrap.getUserParamName() %>" value="" />
						<div id="errmsg_chg_stat" style="color:red;font-size:smaller;"></div>
		    			<br/><input id="btn_chg_stat" type="submit" value="Update" />
					</form>
				</div>
			</div>
			
		
	
</body>
</html>