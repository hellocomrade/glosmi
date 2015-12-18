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
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("appnew.jsp")));
else
	out.println("New Application");
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
	
	<link type="text/css" href="../js/jquery1.8/css/redmond/jquery.ui.css" rel="stylesheet" />
	<link href="app.css" rel="stylesheet" type="text/css" />
	
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

		$("input[name=cnt_devel_cnt_firstname[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_lastname[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_email[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if(validateEmail($.trim($(this).val()))==false)
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});
		//$("input[name=cnt_devel_cnt_phone[]]:visible")
		$("input[name=cnt_devel_cnt_phone[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				if(validatePhone(temp)==false)
				{
					textBoxError($(this));
					result=false;
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_zip[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				var cny=$(this).parent().siblings().find("#mod_country").val();
				if(cny=="us")
				{
					if(validateUSZipcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						 textBoxResume($(this));
				}
				else if(cny=='ca')
				{
					if(validateCAPostalcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						textBoxResume($(this));
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_firstname[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_lastname[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_email[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if(validateEmail($.trim($(this).val()))==false)
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_phone[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				if(validatePhone(temp)==false)
				{
					textBoxError($(this));
					result=false;
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_zip[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				var cny=$(this).parent().siblings().find("#mod_country").val();
				if(cny=="us")
				{
					if(validateUSZipcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						 textBoxResume($(this));
				}
				else if(cny=='ca')
				{
					if(validateCAPostalcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						textBoxResume($(this));
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

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
			if(false==validateEmail($.trim($("input[name=app_cnt_email]").val())))
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
		$("#btn_dev_add").button();
		$("#btn_dist_add").button();

		$("#btn_modsearch").click(function(event){
			app_mod_search('app_mod_name','app_mod_id');
		});
		
		var dev_count=<%=developers==null?1:developers.size()%>;
		var dist_count=<%=distributors==null?1:distributors.size()%>;
		$("#btn_dev_add").click(function(event){
			if($("div[id^='"+cnt_dev_div+"']").size()<5)
			{
				//due to the bug of autocomplete,clone event can't be used,manually attach events
				var elem=$("#dev_clone").clone().attr("id",cnt_dev_div+(dev_count)).insertBefore($("#err_devs")).fadeIn();
				elem.find(".a_dev_remove").click(dev_remove_callback);
				elem.find(".app_country").change(countryChangeCallback);
				elem.children("#dev_cnt_id").val("");
				++dev_count;
			}
			else
				errorMessageBox("You can add at most 5 developers!");
		});
		function dev_remove_callback(event)
		{
			event.preventDefault();
			
			if($("div[id^='"+cnt_dev_div+"']").size()>1)
			{
				$(this).parent().parent().remove();
			}
			else
				errorMessageBox("You can not remove all of them!");
				
		}
		$(".a_dev_remove").click(dev_remove_callback);

		$("#btn_dist_add").click(function(event){
			if($("div[id^='"+cnt_dist_div+"']").size()<5)
			{
				var elem=$("#dist_clone").clone().attr("id",cnt_dist_div+(dist_count)).insertBefore($("#err_dists")).fadeIn();
				elem.children($("#dist_cnt_id")).val("");
				elem.find(".a_dist_remove").click(dist_remove_callback);
				elem.find(".app_country").change(countryChangeCallback);
				++dist_count;
			}
			else
				errorMessageBox("You can add at most 5 distributors!");
		});
		function dist_remove_callback(event)
		{
			event.preventDefault();
			if($("div[id^='"+cnt_dist_div+"']").size()>1)
			{
				$(this).parent().parent().remove();
			}
			else
				errorMessageBox("You can not remove all of them!");
				
		}
		$(".a_dist_remove").click(dist_remove_callback);

		function countryChangeCallback()
		{
			var td=$(this).parent().siblings('#td_state');
			
			if('ca'==$(this).val())
			{
				td.children("#app_state").hide();
				td.children("#app_province").show();
			}
			else
			{
				td.children("#app_province").hide();
				td.children("#app_state").show();
				
			}
		}
		$(".app_country").change(countryChangeCallback);

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
			  submit();
		});
		function submit()
		{
			if(validate_form())
			{
			}
			else
				$("#err_prompt_div").show();
		}
	});
	</script>
</head>
<body>
<%
    if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
    {	
%>
<jsp:include page="../../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<%
    }
    else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
    {
%>
<jsp:include page="../../mod/topbar.jsp" flush="true">
	<jsp:param name="id" value="mod_dock"/>
</jsp:include>
<%
    }
    else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
    {
%>
<jsp:include page="../../app/topbar.jsp" flush="true">
	<jsp:param name="id" value="app_dock"/>
</jsp:include>
<%
    }
%>
<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:include page="/WEB-INF/controls/modsearch-widget.jsp" flush="true"/>
<div id="err_prompt_div" style="color:red;text-align:center;<%=err!=null?"display:block;":"display:none;"%>">
    Please fix the errors on both 2 tabs.
</div>
<div style="margin-left:auto;margin-right:auto;width:980px;position:relative;">
	<div style="margin-left:auto;margin-right:auto;width:810px;">
		<div id="app_tabs">
			<form name="app_frm" id="app_frm" method="POST" action="">
				<input type="hidden" id="app_action" name="<%=apparam.getActionParamName()%>" value="" />
				<input type="hidden" id="app_id" name="<%=apparam.getAppIdParamName()%>" value="<%=(app==null)?"":app.getId()%>" />
				<input type="hidden" id="draft_id" name="<%=apparam.getAppDraftIdParamName()%>" value="<%=(app==null)?"":app.getSerialId()%>" />
				<input type="hidden" id="app_ajax" name="<%=apparam.getAjaxParamName()%>" value="0" />
				<input type="hidden" id="app_mod_id" name="<%=apparam.getModIdParamName()%>" value="<%=(app==null)?"":app.getModId()%>" />
				<input type="hidden" id="app_mod_draft_id" name="<%=apparam.getModDraftIdParamName()%>" value="<%=(app==null)?"":app.getModDraftId()%>" />
				<ul>
					<li><a href="#appinfo">Application</a></li>
					<li><a href="#appcontact">People</a></li>
					<!--  <li><a href="#appref">Reference</a></li>-->
				</ul>
				<div id="appinfo">
					<div class="formitem">
<%
String errMsg=null;
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppNameParamName());
%>
						<div>Application Name:</div>
						<div>
							<input type="text" name="<%=apparam.getAppNameParamName() %>" id="app_name" class="textbox-app"  size="70" value="<%=(app==null)?"":HTMLUtilities.filterDisplay(app.getName())%>"/>
							<div id="err_app_name" style="color:red;display:inline;"><%=errMsg==null?"":errMsg %></div>
						</div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getModelNameParamName());
%>
						<div>Model Name(The name of the model upon which this application is developed):</div>
						<input type="text" name="<%=apparam.getModelNameParamName() %>" id="app_mod_name" class="textbox-app" disabled="disabled" size="70" value="<%=(app==null)?"":HTMLUtilities.filterDisplay(app.getModelName()) %>" />
						<input type="button" id="btn_modsearch" value="Search Model" />
						<div id="err_app_mod_name" style="color:red;display:inline;"><%=errMsg==null?"":errMsg %></div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppPurposeParamName());
%>
						<div>Primary Purpose:</div>
						<div>
							<textarea name="<%=apparam.getAppPurposeParamName() %>" id="app_purpose" class="textbox-app" rows="10" cols="90"><%=(app==null)?"":HTMLUtilities.filterDisplay(app.getAppPurpose())%></textarea>
							<br/><div id="err_app_purpose" style="color:red;display:inline;"><%=errMsg==null?"":errMsg %></div>
						</div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppDescParamName());
%>
						<div>Description:</div>
						<div>
							<textarea name="<%=apparam.getAppDescParamName()%>" id="app_desc" class="textbox-app" rows="10" cols="90"><%=(app==null)?"":HTMLUtilities.filterDisplay(app.getAppDescription())%></textarea>
							<br/><div id="err_app_desc" style="color:red;display:inline;"><%=errMsg==null?"":errMsg %></div>
						</div>
					</div>
					<div class="formitem">
<%
if(err!=null)
	errMsg=err.getErrorMsg(apparam.getAppNoteParamName());
%>
						<div>Note:</div>
						<div>
							<textarea name="<%=apparam.getAppNoteParamName()%>" id="app_note" class="textbox-app" rows="10" cols="90"><%=(app==null)?"":HTMLUtilities.filterDisplay(app.getAppNote())%></textarea>
							<br/><div id="err_app_note" style="color:red;display:inline;"><%=errMsg==null?"":errMsg %></div>
						</div>
					</div>
					<a id="a_appinfo_next" href="#" style="font-size:larger;">Next Page</a>
				</div>
				<div id="appcontact">
					<fieldset id="fld_dev">
						<legend class="fd_legend">Developers:</legend>
<%
Contact cnt=null;
int len=0;
if(developers!=null&&developers.isEmpty()==false)
{
	len=developers.size();
}
else
{
	cnt=new Contact();
	developers=new ArrayList<Contact>();
	developers.add(cnt);
	len=1;
}
	for(int i=0;i<len;++i)
	{
		cnt=developers.get(i);
%>
					<div id="<%=cntparam.getDeveloperParamName()+Integer.toString(i)%>" style="position:relative;">
						<input type="hidden" name="<%=cntparam.getDeveloperParamName()+cntparam.getContactIdParamName() %>" id="dev_cnt_id" value="<%=cnt.getId()>0?cnt.getId():"" %>" />
						<table id="tbl">
				       		<tr id="tr">
				    			<td id="td">
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="app_fname" name="<%=cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName() %>" value="<%=cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="app_lname" name="<%=cntparam.getDeveloperParamName()+cntparam.getLastNameParamName() %>" value="<%=cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="app_email" name="<%=cntparam.getDeveloperParamName()+cntparam.getEmailParamName() %>" value="<%=cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If First Name and Last Name can not apply, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Orgnization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName() %>" value="<%=cnt.getOrgnization()!=null?HTMLUtilities.filterDisplay(cnt.getOrgnization().getName()):"" %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="app_add1" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress1()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="app_add2" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress2()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="app_city" name="<%=cntparam.getDeveloperParamName()+cntparam.getCityParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getCity()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
<%
boolean isUS=true;

if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null)
{
	isUS=cnt.getAddress().getCountry().equalsIgnoreCase("us");
}
%>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
				    				<select id="app_country" name="<%=cntparam.getDeveloperParamName()+cntparam.getCountryParamName() %>" class="textbox-app app_country">
				    					<option value="" 
<%
if(cnt.getAddress()==null||cnt.getAddress().getCountry()==null)
{
	out.print("selected='selected'");
}
%>				    					
				    					>----</option>
				    					<option value="us" 
<%
if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null&&isUS)
{
	out.print("selected='selected'");
}
%>				    					
				    					>United States</option>
				    					<option value="ca" 
<%
if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null&&isUS==false)
{
	out.print("selected='selected'");
}
%>				    					
				    					>Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
		
				    				<glc:statesselect id="app_state" name="<%=cntparam.getDeveloperParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>" selected="<%=(isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):"" %>"/>
				    				<glc:statesselect id="app_province" name="<%=cntparam.getDeveloperParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>" selected="<%=(!isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):"" %>"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="app_zip" name="<%=cntparam.getDeveloperParamName()+cntparam.getZipParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getZipcode()):"" %>" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="app_phone" name="<%=cntparam.getDeveloperParamName()+cntparam.getPhoneParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getPhone()):"" %>" class="loginfont textbox-app" size="25"/>
								</td>
							</tr>
						
						</table>
						<div style="float:right;top:0;right:0;position:absolute;">
							<a href="#" id="a_dev_remove" class="a_dev_remove">Remove</a>
						</div>
						<hr/>
					</div>
<%
}
%>
					
<%
StringBuilder sb=new StringBuilder();
if(err!=null)
{
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getLastNameParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getLastNameParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getEmailParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getEmailParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getCityParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getCityParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getCountryParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getCountryParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getStateParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getStateParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getZipParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getZipParamName())));
	if(err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getPhoneParamName())!=null)
		sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDeveloperParamName()+cntparam.getPhoneParamName())));
}

%>
					<div id="err_devs" style="color:red;"><%=sb.toString() %></div>
					<br/><input id="btn_dev_add" type="button" value="Add A Developer" style="width:200px;"/>
			</fieldset>
			<br />
			<fieldset>
				<legend class="fd_legend">Distributors:</legend>
<%
cnt=null;
len=0;
if(distributors!=null&&distributors.isEmpty()==false)
{
	len=distributors.size();
}
else
{
	cnt=new Contact();
	len=1;
	distributors=new ArrayList<Contact>();
	distributors.add(cnt);
}
	for(int i=0;i<len;++i)
	{
		cnt=distributors.get(i);
%>
				<div id="<%=cntparam.getDistributorParamName()+Integer.toString(i)%>" style="position:relative;">
					<input type="hidden" name="<%=cntparam.getDistributorParamName()+cntparam.getContactIdParamName() %>" id="dist_cnt_id" value="<%=cnt.getId()>0?cnt.getId():"" %>" />
					<table>
				       		<tr>
				    			<td>
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="app_fname" name="<%=cntparam.getDistributorParamName()+cntparam.getFirstNameParamName() %>" value="<%=cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="app_lname" name="<%=cntparam.getDistributorParamName()+cntparam.getLastNameParamName() %>" value="<%=cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):"" %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="app_email" name="<%=cntparam.getDistributorParamName()+cntparam.getEmailParamName() %>" value="<%=cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):"" %>"  class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If First Name and Last Name can not apply, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Orgnization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=cntparam.getDistributorParamName()+cntparam.getOrgIdParamName() %>" value="<%=cnt.getOrgnization()!=null?HTMLUtilities.filterDisplay(cnt.getOrgnization().getName()):"" %>"  cssClass="ui-widget loginfont textbox-app" size="50"/>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="app_add1" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress1ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress1()):"" %>"  class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="app_add2" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress2ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress2()):"" %>"  class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="app_city" name="<%=cntparam.getDistributorParamName()+cntparam.getCityParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getCity()):"" %>"  class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
<%
boolean isUS=true;

if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null)
{
	isUS=cnt.getAddress().getCountry().equalsIgnoreCase("us");
}
%>

				    				<select id="app_country" name="<%=cntparam.getDistributorParamName()+cntparam.getCountryParamName() %>" class="textbox-app app_country">
				    					<option value="" 
<%
if(cnt.getAddress()==null||cnt.getAddress().getCountry()==null)
{
	out.print("selected='selected'");
}
%>					    					
				    					>----</option>
				    					<option value="us" 
<%
if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null&&isUS)
{
	out.print("selected='selected'");
}
%>				    			
				    					>United States</option>
				    					<option value="ca" 
<%
if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null&&isUS==false)
{
	out.print("selected='selected'");
}
%>				    					
				    					>Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
				    				<glc:statesselect id="app_state" name="<%=cntparam.getDistributorParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>"  selected="<%=(isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):"" %>"/>
				    				<glc:statesselect id="app_province" name="<%=cntparam.getDistributorParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>"  selected="<%=(!isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):"" %>"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="app_zip" name="<%=cntparam.getDistributorParamName()+cntparam.getZipParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getZipcode()):"" %>"  class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="app_phone" name="<%=cntparam.getDistributorParamName()+cntparam.getPhoneParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getPhone()):"" %>"  class="loginfont textbox-app" size="25"/>
								</td>
							</tr>
						
						</table>
						<div style="float:right;top:0;right:0;position:absolute;">
							<a href="#" id="a_dist_remove" class="a_dist_remove">Remove</a>
						</div>
						<hr/>
				</div>
<%
}

	sb=new StringBuilder();
	if(err!=null)
	{
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getFirstNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getFirstNameParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getLastNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getLastNameParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getEmailParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getEmailParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getOrgIdParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getOrgIdParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getAddress1ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getAddress1ParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getAddress2ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getAddress2ParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getCityParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getCityParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getCountryParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getCountryParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getStateParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getStateParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getZipParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getZipParamName())));
		if(err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getPhoneParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(cntparam.getDistributorParamName()+cntparam.getPhoneParamName())));
	}
%>
				<div id="err_dists" style="color:red;"><%=sb.toString() %></div>
				<br/><input id="btn_dist_add" type="button" value="Add A Distributor" style="width:200px;"/>
			</fieldset>
			<br />
<%
cnt=null;
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
				<input type="hidden" name="<%=apparam.getAppCntIdParamName() %>" value="<%=cnt==null?"":(cnt.getId()>0?cnt.getId():"") %>"  />
				<input type="checkbox" id="chk_same_cnt" /><span style="font-size:smaller;font-weight:bold;">The contact information is as same as mine.</span>
				<table>
				       	<tr>
				    		<td>
				    			<div style="font-size:smaller;">First Name:</div>
				    			<input type="text" id="app_fname" name="<%=apparam.getAppFirstNameParamName() %>" value="<%=cnt==null?"":(cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):"") %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Last Name:</div>
				    			<input type="text" id="app_lname" name="<%=apparam.getAppLastNameParamName() %>" value="<%=cnt==null?"":(cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):"") %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<div style="font-size:smaller;">Email:</div>
				    			<input type="text" id="app_email" name="<%=apparam.getAppEmailParamName() %>" value="<%=cnt==null?"":(cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):"") %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Address1:</div>
				    			<input type="text" id="app_add1" name="<%=apparam.getAppAddress1ParamName() %>" value="<%=add==null?"":HTMLUtilities.filterDisplay(add.getAddress1()) %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Address2:</div>
				    			<input type="text" id="app_add2" name="<%=apparam.getAppAddress2ParamName() %>" value="<%=add==null?"":HTMLUtilities.filterDisplay(add.getAddress2()) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    		    <div style="font-size:smaller;">City:</div>
				    			<input type="text" id="app_city" name="<%=apparam.getAppCityParamName() %>" value="<%=add==null?"":HTMLUtilities.filterDisplay(add.getCity()) %>" class="textbox-app" size="25"/>
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
				    			<glc:statesselect id="app_state" name="<%=apparam.getAppStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>" selected="<%=(isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):"" %>"/>
				    			<glc:statesselect id="app_province" name="<%=apparam.getAppProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>"  selected="<%=(!isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):"" %>"/>
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    			<input type="text" id="app_zip" name="<%=apparam.getAppZipParamName() %>" value="<%=add==null?"":HTMLUtilities.filterDisplay(add.getZipcode()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Phone:</div>
				    			<input type="text" id="app_phone" name="<%=apparam.getAppPhoneParamName() %>" value="<%=add==null?"":HTMLUtilities.filterDisplay(add.getPhone())%>" class="loginfont textbox-app" size="25"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="font-size:smaller;">Orgnization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=apparam.getAppOrgIdParamName() %>" value="<%=org==null?"":HTMLUtilities.filterDisplay(org.getName()) %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
							</td>
						</tr>
					</table>
<%

	sb=new StringBuilder();
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
					<a id="a_appcontact_next" href="#" style="font-size:larger;">Submit</a>
				</div>
				<!-- <div id="appref">
				</div>-->
			</form>
		</div>
		<div style="height:300px;width:80px;top:2px;right:3px;position:absolute;" class="ui-widget-header ui-corner-all">
			<table align="center">
				<tr>
					<td>
						<a href="#" id="a_model_save">Save</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="#" id="a_model_submit">Submit</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="../pub">Quit</a>
					</td>
				</tr>
			</table>
		</div>
		<br/>
	</div>
</div>
<!-- Clone -->
				<div id="dev_clone" style="position:relative;display:none;">
						<input type="hidden" name="<%=cntparam.getDeveloperParamName()+cntparam.getContactIdParamName() %>" id="dev_cnt_id" value="" />
						<table>
				       		<tr>
				    			<td>
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="app_fname" name="<%=cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="app_lname" name="<%=cntparam.getDeveloperParamName()+cntparam.getLastNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="app_email" name="<%=cntparam.getDeveloperParamName()+cntparam.getEmailParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If First Name and Last Name can not apply, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Orgnization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName() %>" value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="app_add1" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="app_add2" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="app_city" name="<%=cntparam.getDeveloperParamName()+cntparam.getCityParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
				    				<select id="app_country" name="<%=cntparam.getDeveloperParamName()+cntparam.getCountryParamName() %>" class="textbox-app app_country">
				    					<option value="" selected='selected'>----</option>
				    					<option value="us" >United States</option>
				    					<option value="ca" >Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
				    				<glc:statesselect id="app_state" name="<%=cntparam.getDeveloperParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app"  country="us" hide="false"/>
				    				<glc:statesselect id="app_province" name="<%=cntparam.getDeveloperParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="true" />
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="app_zip" name="<%=cntparam.getDeveloperParamName()+cntparam.getZipParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="app_phone" name="<%=cntparam.getDeveloperParamName()+cntparam.getPhoneParamName() %>" value="" class="loginfont textbox-app" size="25"/>
								</td>
							</tr>
						
						</table>
						<div style="float:right;top:0;right:0;position:absolute;">
							<a href="#" id="a_dev_remove" class="a_dev_remove">Remove</a>
						</div>
						<hr/>
					</div>
					
					<div id="dist_clone" style="position:relative;display:none;">
					<input type="hidden" name="<%=cntparam.getDistributorParamName()+cntparam.getContactIdParamName() %>" id="dist_cnt_id" value="" />
					<table>
				       		<tr>
				    			<td>
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="app_fname" name="<%=cntparam.getDistributorParamName()+cntparam.getFirstNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="app_lname" name="<%=cntparam.getDistributorParamName()+cntparam.getLastNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="app_email" name="<%=cntparam.getDistributorParamName()+cntparam.getEmailParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If First Name and Last Name can not apply, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Orgnization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="app_org" name="<%=cntparam.getDistributorParamName()+"0"+cntparam.getOrgIdParamName() %>" value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="app_add1" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress1ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="app_add2" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress2ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="app_city" name="<%=cntparam.getDistributorParamName()+cntparam.getCityParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
				    				<select id="app_country" name="<%=cntparam.getDistributorParamName()+cntparam.getCountryParamName() %>" class="textbox-app app_country">
				    					<option value="" selected='selected'>----</option>
				    					<option value="us" >United States</option>
				    					<option value="ca" >Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
				    				<glc:statesselect id="app_state" name="<%=cntparam.getDistributorParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="false"/>
				    				<glc:statesselect id="app_province" name="<%=cntparam.getDistributorParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="true" />
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="app_zip" name="<%=cntparam.getDistributorParamName()+cntparam.getZipParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="app_phone" name="<%=cntparam.getDistributorParamName()+cntparam.getPhoneParamName() %>" value="" class="loginfont textbox-app" size="25"/>
								</td>
							</tr>
						
						</table>
						<div style="float:right;top:0;right:0;position:absolute;">
							<a href="#" id="a_dist_remove" class="a_dist_remove">Remove</a>
						</div>
						<hr/>
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