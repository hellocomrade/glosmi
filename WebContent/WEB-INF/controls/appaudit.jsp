<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="org.glc.utils.ServerUtilities" %>
<%@page import="org.glc.domain.Address"%>
<%@page import="org.glc.domain.Orgnization"%>
<%@ page import="us.glos.mi.db.DBCache" %>
<%@page import="org.glc.domain.ErrorMessage"%>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@page import="us.glos.mi.domain.AppInfo"%>
<%@ page import="us.glos.mi.domain.Contact"%>
<%
User usr=(User)session.getAttribute(User.getClassName());
ErrorMessage err=null;
AppInfo app=null;
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
if(request.getAttribute(AppInfo.getClassName())!=null)
{
	app=(AppInfo)request.getAttribute(AppInfo.getClassName());
}
ArrayList<Contact> developers=null;
ArrayList<Contact> distributors=null;
if(app!=null)
{
	if(app.getDevelopers()!=null)
		developers=app.getDevelopers();
	if(app.getDistributors()!=null)
		distributors=app.getDistributors();
}
if(request.getAttribute(ErrorMessage.getClassName())!=null)
	err=(ErrorMessage)request.getAttribute(ErrorMessage.getClassName());
String empty="";
%>
<jsp:useBean id="apparam" scope="page" class="us.glos.mi.domain.AppAdminParam" />
<jsp:useBean id="cntparam" scope="page" class="us.glos.mi.domain.ContactParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<% 
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("appaudit.jsp")));
else
	out.println("Audit Application");
%>
</title>
	<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.tabs.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.mouse.js"></script>
	
	<!-- <script type="text/javascript" src="../js/jquery1.8/datatable/jquery.dataTables.min.js"></script>-->
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
	<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>
	
	<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
	<link href="adm.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
	var cnt_dev_div="<%=cntparam.getDeveloperParamName()%>";
	var cnt_dist_div="<%=cntparam.getDistributorParamName()%>";

	function validate_form()
	{
		var result=true;
		
		
		if($.trim($("#app_name").val())=="")
		{
			result=false;
			textBoxError($("#app_name"));
			$("#err_app_name").text("Application Name can not be empty");
			$("#err_app_name").show();
		}
		else
		{
			$("#err_app_name").hide();
			textBoxResume($("#app_name"));
		}

		if($.trim($("#app_url").val())!="")
		{
			if(validateUrl($.trim($("#app_url").val()))==false)
			{
				result=false;
				textBoxError($("#app_url"));
				$("#err_app_url").text("Application website is not a valid URL");
				$("#err_app_url").show();
			}
			else
			{
				$("#err_app_url").hide();
				textBoxResume($("#app_url"));
			}
		}
		else
		{
			$("#err_app_url").hide();
			textBoxResume($("#app_url"));
		}

		if($.trim($("#app_loc").val())=="")
		{
			result=false;
			textBoxError($("#app_loc"));
			$("#err_app_loc").text("Application Location can not be empty");
			$("#err_app_loc").show();
		}
		else
		{
			$("#err_app_loc").hide();
			textBoxResume($("#app_loc"));
		}
		
		if($.trim($("#app_mod_name").val())=="")
		{
			result=false;
			textBoxError($("#app_mod_name"));
			$("#err_app_mod_name").text("Model Name can not be empty");
			$("#err_app_mod_name").show();
		}
		else
		{
			$("#err_app_mod_name").hide();
			textBoxResume($("#app_mod_name"));
		}

		if($.trim($("#app_purpose").val())=="")
		{
			result=false;
			textBoxError($("#app_purpose"));
			$("#err_app_purpose").text("Application Primary Purpose can not be empty");
			$("#err_app_purpose").show();
		}
		else
		{
			$("#err_app_purpose").hide();
			textBoxResume($("#app_purpose"));
		}

		if($.trim($("#app_desc").val())=="")
		{
			result=false;
			textBoxError($("#app_desc"));
			$("#err_app_desc").text("Application Description can not be empty");
			$("#err_app_desc").show();
		}
		else
		{
			$("#err_app_desc").hide();
			textBoxResume($("#app_desc"));
		}

		

		if($.trim($("input[name=app_cnt_firstname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=app_cnt_firstname]"));
			
		}
		else
		{
			textBoxResume($("input[name=app_cnt_firstname]"));
		}

		if($.trim($("input[name=app_cnt_lastname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=app_cnt_lastname]"));
			
		}
		else
		{
			textBoxResume($("input[name=app_cnt_lastname]"));
		}
		
		if($.trim($("input[name=app_cnt_email]").val())!="")
		{
			if(false==validateEmail($.trim($("input[name=app_cnt_email]").val()))&&$.trim($("input[name=app_cnt_email]").val()).toLowerCase()!="n/a")
			{
				result=false;
				textBoxError($("input[name=app_cnt_email]"));
			}
			else
				textBoxResume($("input[name=app_cnt_email]"));
		}
		else
		{
			result=false;
			textBoxError($("input[name=app_cnt_email]"));
		}

		if($("input[name=app_cnt_zip]").val()!="")
		{
			var val=$.trim($("input[name=app_cnt_zip]").val());
			var cny=$("#app_cnt_country option:selected").val();
			if(cny=='ca'&&validateCAPostalcode(val)==false)
			{
				textBoxError($("input[name=app_cnt_zip]"));
				result=false;
			}
			else if(cny=='us'&&validateUSZipcode(val)==false)
			{
				textBoxError($("input[name=app_cnt_zip]"));
				result=false;
			}
			else
			{
				textBoxResume($("input[name=app_cnt_zip]"));
				
			}
		}
		else
		{
			textBoxResume($("#app_cnt_zip"));
			
		}

		if($.trim($("input[name=app_cnt_phone]").val())!="")
		{
			if(validatePhone($.trim($("input[name=app_cnt_phone]").val()))==false)
			{
				result=false;
				textBoxError($("input[name=app_cnt_phone]"));
			}
			else
			{
				textBoxResume($("input[name=app_cnt_phone]"));
			}		
		}
		else
			textBoxResume($("input[name=app_cnt_phone]"));
		
		
		return result;
	}
	
	$(function() {
		$("#app_tabs").tabs();
		$("#a_appinfo_next").button();
		$("#a_appcontact_prev").button();
		$("#a_appcontact_next").button();
		$("#a_appaudit_prev").button();
		$("#a_app_submit_btn").button();
		

		$("#btn_modsearch").click(function(event){
			app_mod_search('app_mod_name','app_mod_name_hidden','app_mod_id');
		});
		
		$("#a_app_submit").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=app_frm]").attr("action","appaudit.glos");
				$("#app_action").val("approve");
				$("[name=app_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		$("#a_app_submit_btn").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=app_frm]").attr("action","appaudit.glos");
				$("#app_action").val("approve");
				$("[name=app_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		
		$("#a_contact_search").click(function(event){
			admin_contact_search();
		});
		$("#a_org_tool").click(function(event){
			admin_org_tool();
		});

		$("#chk_same_cnt").click(function(){
			$("input[name=app_cnt_firstname]").val($("#fname").val());
			$("input[name=app_cnt_lastname]").val($("#lname").val());
			$("input[name=app_cnt_add1]").val($("#add1").val());
			$("input[name=app_cnt_add2]").val($("#add2").val());
			$("input[name=app_cnt_city]").val($("#city").val());
			$("select[name=app_cnt_country]").val($("#country").val());
			if($("#country").val()=='us')
			{
				$("select[name=app_cnt_state]").val($("#state").val());
				$("select[name=app_cnt_province]").hide();
				$("select[name=app_cnt_state]").show();
			}
			else if($("#country").val()=='ca')
			{
				$("select[name=app_cnt_province]").val($("#province").val());
				$("select[name=app_cnt_state]").hide();
				$("select[name=app_cnt_province]").show();
			}
			$("input[name=app_cnt_zip]").val($("#zip").val());
			$("input[name=app_cnt_phone]").val($("#phone").val());
			$("input[name=app_cnt_orgid]").val($("#org").val());
		});
		
		$("#a_appinfo_next").click(function(event){
			  $("#app_tabs").tabs('select',1);
		});
		$("#a_appcontact_prev").click(function(event){
			  $("#app_tabs").tabs('select',0);
		});
		$("#a_appcontact_next").click(function(event){
			$("#app_tabs").tabs('select',2);
		});
		$("#a_appaudit_prev").click(function(event){
			  $("#app_tabs").tabs('select',1);
		});
		
	});
	</script>
</head>
<body>

<jsp:include page="../../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>

<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:include page="/WEB-INF/controls/modsearch-widget.jsp" flush="true"/>
<jsp:include page="/WEB-INF/controls/contactsearch-widget.jsp" flush="true"/>
<jsp:include page="/WEB-INF/controls/agency-widget.jsp" flush="true"/>
<div id="err_prompt_div" style="color:red;text-align:center;<%=err!=null?"display:block;":"display:none;"%>">
    Please fix the errors on both 2 tabs.
   <div> 
<%
	if(err!=null)
	{
		String global_err=err.getHTMLFriendlyGlobalMessage("<span>","</span><br/>");
		if(global_err!=null&&!global_err.equals(""))
			out.println(global_err);
	}
%>
    </div>
</div>
<div style="margin-left:auto;margin-right:auto;width:980px;position:relative;">
	<div style="margin-left:auto;margin-right:auto;width:810px;">
		<div id="app_tabs">
			<form name="app_frm" id="app_frm" method="POST" action="">
				<input type="hidden" id="app_action" name="<%=apparam.getActionParamName()%>" value="" />
				<input type="hidden" id="app_id" name="<%=apparam.getAppIdParamName()%>" value="<%=(app==null)?empty:app.getId()%>" />
				<input type="hidden" id="draft_id" name="<%=apparam.getAppDraftIdParamName()%>" value="<%=(app==null)?empty:app.getSerialId()%>" />
				<input type="hidden" id="app_ajax" name="<%=apparam.getAjaxParamName()%>" value="0" />
				<input type="hidden" id="app_mod_id" name="<%=apparam.getModIdParamName()%>" value="<%=(app==null)?empty:app.getModId()%>" />
				<input type="hidden" id="app_mod_draft_id" name="<%=apparam.getModDraftIdParamName()%>" value="<%=(app==null)?empty:app.getModDraftId()%>" />
				<ul>
					<li><a href="#appinfo">Application</a></li>
					<li><a href="#appcontact">People</a></li>
					<li><a href="#appaudit">Audit</a></li>
					
				</ul>
				<div id="appinfo">
					<div class="formitem">
<%
if(app!=null&&app.getId()>0&&app.getSerialId()>0)
{
%>
					<div style="font-weight:bold;">Reason for Update:</div>
					
					<textarea class="textbox-app" rows="10" cols="90"><%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getUpdateReason())%></textarea>
<%
}
%>
					</div>
					<div class="formitem">
<%
String errMsg=null;
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppNameParamName());
%>
						<div>Application Name:</div>
						<div>
							<input type="text" name="<%=apparam.getAppNameParamName() %>" id="app_name" class="textbox-app"  size="70" value="<%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getName())%>"/>
							<div id="err_app_name" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
<%
errMsg=null;
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppUrlParamName());
%>					
					<div class="formitem">
						<div>Website:</div>
						<div>
							<input type="text" name="<%=apparam.getAppUrlParamName() %>" id="app_url" class="textbox-app"  size="70" value="<%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getUrl())%>"/>
							<div id="err_app_url" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
					
					<div class="formitem">
<%

if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppLocationParamName());
%>
						<div>Location(Please describe the geographic location that this application is applied):</div>
						<div>
							<input type="text" name="<%=apparam.getAppLocationParamName() %>" id="app_loc" class="textbox-app"  size="70" value="<%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getLocation())%>"/>
							<div id="err_app_loc" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
					
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getModelNameParamName());
%>
						<div>Model Name(The name of the model upon which this application is developed):</div>
						<input type="text" id="app_mod_name" class="textbox-app" disabled="disabled" size="70" value="<%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getModelName()) %>" />
						<input type="hidden" name="<%=apparam.getModelNameParamName() %>" id="app_mod_name_hidden" value="<%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getModelName()) %>" />
						<input type="button" id="btn_modsearch" value="Search Model" />
						<div id="err_app_mod_name" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppPurposeParamName());
%>
						<div>Primary Purpose:</div>
						<div>
							<textarea name="<%=apparam.getAppPurposeParamName() %>" id="app_purpose" class="textbox-app" rows="10" cols="90"><%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getAppPurpose())%></textarea>
							<br/><div id="err_app_purpose" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppDescParamName());
%>
						<div>Description:</div>
						<div>
							<textarea name="<%=apparam.getAppDescParamName()%>" id="app_desc" class="textbox-app" rows="10" cols="90"><%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getAppDescription())%></textarea>
							<br/><div id="err_app_desc" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppNoteParamName());
%>
						<div>Note:</div>
						<div>
							<textarea name="<%=apparam.getAppNoteParamName()%>" id="app_note" class="textbox-app" rows="10" cols="90"><%=(app==null)?empty:HTMLUtilities.filterDisplay(app.getAppNote())%></textarea>
							<br/><div id="err_app_note" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
						</div>
					</div>
					<a id="a_appinfo_next" href="#" style="font-size:larger;">Next Page</a>
				</div>
				<div id="appcontact">
					
<%
Contact cnt=null;
Address add=null;
Orgnization org=null;
if(app!=null&&app.getContacts()!=null&&app.getContacts().isEmpty()==false)
{
	cnt=app.getContacts().get(0);
	add=cnt.getAddress();
	org=cnt.getOrgnization();
}
%>
			<fieldset>
				<legend class="fd_legend">Contact:</legend>
				<input type="hidden" name="<%=apparam.getAppCntIdParamName() %>" value="<%=cnt==null?empty:(cnt.getId()>0?cnt.getId():empty) %>"  />
				<input type="checkbox" id="chk_same_cnt" /><span style="font-size:smaller;font-weight:bold;">The contact information is as same as mine.</span>
				<table>
				       	<tr>
				    		<td>
				    			<div style="font-size:smaller;">First Name:</div>
				    			<input type="text" id="app_fname" name="<%=apparam.getAppFirstNameParamName() %>" value="<%=cnt==null?empty:(cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Last Name:</div>
				    			<input type="text" id="app_lname" name="<%=apparam.getAppLastNameParamName() %>" value="<%=cnt==null?empty:(cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<div style="font-size:smaller;">Email:</div>
				    			<input type="text" id="app_email" name="<%=apparam.getAppEmailParamName() %>" value="<%=cnt==null?empty:(cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Address1:</div>
				    			<input type="text" id="app_add1" name="<%=apparam.getAppAddress1ParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getAddress1()) %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Address2:</div>
				    			<input type="text" id="app_add2" name="<%=apparam.getAppAddress2ParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getAddress2()) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    		    <div style="font-size:smaller;">City:</div>
				    			<input type="text" id="app_city" name="<%=apparam.getAppCityParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getCity()) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Country:</div>
<%
boolean isUS=true;

if(add!=null&&add.getCountry()!=null)
{
	isUS=add.getCountry().equalsIgnoreCase("us");
}
%>				    		  
				    			<select id="app_country" name="<%=apparam.getAppCountryParamName() %>" class="textbox-app app_country">
				    				<option value="" 
<%
if(add==null||add.getCountry()==null)
{
	out.print("selected='selected'");
}
%>				    				
				    				>----</option>
				    				<option value="us" 
<%
if(add!=null&&add.getCountry()!=null&&isUS)
{
	out.print("selected='selected'");
}
%>				    				
				    				>United States</option>
				    				<option value="ca" 
<%
if(add!=null&&add.getCountry()!=null&&isUS==false)
{
	out.print("selected='selected'");
}
%>					    				
				    				>Canada</option>
				    			</select>
				    		</td>
				    		<td id="td_state">	
				    		    <div style="font-size:smaller;">State/Province:</div>
				    			<glc:statesselect id="app_state" name="<%=apparam.getAppStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>" selected="<%=(isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):empty %>"/>
				    			<glc:statesselect id="app_province" name="<%=apparam.getAppProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>"  selected="<%=(!isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):empty %>"/>
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    			<input type="text" id="app_zip" name="<%=apparam.getAppZipParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getZipcode()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Phone:</div>
				    			<input type="text" id="app_phone" name="<%=apparam.getAppPhoneParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getPhone())%>" class="loginfont textbox-app" size="25"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="font-size:smaller;">Organization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=apparam.getAppOrgIdParamName() %>" value="<%=org==null?empty:HTMLUtilities.filterDisplay(org.getName()) %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
							</td>
						</tr>
						<tr>
<%
boolean isnewapp=true;
if(app.getId()>0)
	isnewapp=false;
%>
							<td colspan="3">
									<fieldset>
										<legend>User Update Type</legend>
										<div>
											<select name="<%=apparam.getAppContactFlagParamName() %>" class="loginfont textbox-app">
												<option value="<%=cntparam.getNewContactVal() %>" <%=isnewapp?"selected":empty%>>New Contact(Insert)</option>
												<option value="<%=cntparam.getUpdateContactVal() %>">Update Existing Contact(Update)</option>
												<option value="<%=cntparam.getExistContactVal() %>" <%=isnewapp?empty:"selected"%>>Existing Contact(No Action)</option>
											</select>
										</div>
									</fieldset>	
								</td>
							</tr>
					</table>
<%

	StringBuilder sb=new StringBuilder();
	if(err!=null)
	{
		if(err.getErrorMsg(apparam.getAppFirstNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppFirstNameParamName())));
		if(err.getErrorMsg(apparam.getAppLastNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppLastNameParamName())));
		if(err.getErrorMsg(apparam.getAppEmailParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppEmailParamName())));
		if(err.getErrorMsg(apparam.getAppOrgIdParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppOrgIdParamName())));
		if(err.getErrorMsg(apparam.getAppAddress1ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppAddress1ParamName())));
		if(err.getErrorMsg(apparam.getAppAddress2ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppAddress2ParamName())));
		if(err.getErrorMsg(apparam.getAppCityParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppCityParamName())));
		if(err.getErrorMsg(apparam.getAppCountryParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppCountryParamName())));
		if(err.getErrorMsg(apparam.getAppStateParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppStateParamName())));
		if(err.getErrorMsg(apparam.getAppZipParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppZipParamName())));
		if(err.getErrorMsg(apparam.getAppPhoneParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(apparam.getAppPhoneParamName())));
	}
%>
					<div id="err_cnt" style="color:red;"><%=sb.toString() %></div>
			</fieldset>
			<br/>
					<a id="a_appcontact_prev" href="#" style="font-size:larger;">Prev Page</a>
					<a id="a_appcontact_next" href="#" style="font-size:larger;">Next Page</a>
					
				</div>
				<!-- <div id="appref">
				</div>-->
				<div id="appaudit">
					<p>
					This model is submitted by <% out.print(HTMLUtilities.filterDisplay(app.getOwnerName())); %> (<% out.print(HTMLUtilities.filterDisplay(app.getOwnerEmail()));%>)&nbsp;
<%
	if(app.getSerialId()>0)
	{
		if(app.getId()>0)
			out.println(String.format("based upon an EXISTING application:<a href='./appaudit.glos?%s=update&%s=%d' target='_blank'><span style='font-weight:bold;'>%s</span></a>.",
		                          apparam.getActionParamName(),apparam.getAppIdParamName(),app.getId(),app.getName()));
		else
			out.println("as a NEW application.");
	}
	else
	{
		out.println("and currently opened by an auditor directly for update/check purpose.");
	}
%>
			</p>
			<fieldset>
				<legend class="fd_legend">Approval Action:</legend>
				<input type="radio" name="<%=apparam.getAppApprovalFlagParam() %>" value="<%=apparam.getNewModelFlag() %>" <%=isnewapp?"checked":empty%>/>Approve as a new application<br/>
				<input type="radio" name="<%=apparam.getAppApprovalFlagParam() %>" value="<%=apparam.getUpdateModelFlag() %>" <%if(app.getId()<=0)out.print("disabled"); %> <%=isnewapp?empty:"checked"%>/>Approve as an update on the existing application<br/>
			</fieldset>
			<br/>
					<a id="a_appaudit_prev" href="#" style="font-size:larger;">Prev Page</a>
					<a id="a_app_submit_btn" href="#" style="font-size:larger;">Approve</a>
				</div>
			</form>
		</div>
		
		<div style="height:300px;width:80px;top:2px;right:3px;position:absolute;" class="ui-widget-header ui-corner-all">
			<table align="center">
				<tr>
					<td>
						<a href="#" id="a_app_submit" title="Approve"></a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="#" id="a_contact_search" title="Search People"></a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="#" id="a_org_tool" title="Manage Organization"></a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="../pub" id="a_audit_quit" title="Quit"></a>
					</td>
				</tr>
			</table>
		</div>
		<br/>
	</div>
</div>

<script type="text/javascript">
	$("#app_country").change(function(){
		if('ca'==$("#app_country option:selected").val())
		{
			$("#app_state").hide();
			$("#app_province").show();
		}
		else
		{
			$("#app_province").hide();
			$("#app_state").show();
			
		}
	});
</script>
</body>


</html>