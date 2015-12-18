<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.domain.Address"%>
<%@page import="org.glc.domain.Orgnization"%>
<%@page import="org.glc.domain.ErrorMessage"%>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@ page import="org.glc.domain.ErrorMessage" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@ page import="us.glos.mi.db.DBCache" %>
<%@ page import="us.glos.mi.util.Converter" %>
<%@ page import="us.glos.mi.domain.NameValuePair" %>
<%@ page import="us.glos.mi.domain.ModAdminParam" %>
<%@ page import="us.glos.mi.domain.ContactParam" %>
<%@ page import="us.glos.mi.domain.ModelInfo" %>
<%@ page import="us.glos.mi.domain.Contact"%>
<%

String approot=this.getServletContext().getContextPath();
User usr=(User)session.getAttribute(User.getClassName());
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
ModelInfo model=null;
if(request.getAttribute(ModelInfo.getClassName())!=null)
	model=(ModelInfo)request.getAttribute(ModelInfo.getClassName());
ArrayList<Contact> developers=null;
ArrayList<Contact> distributors=null;
if(model!=null)
{
	if(model.getDevelopers()!=null)
		developers=model.getDevelopers();
	if(model.getDistributors()!=null)
		distributors=model.getDistributors();
}
ErrorMessage err=null;
if(request.getAttribute(ErrorMessage.getClassName())!=null)
	err=(ErrorMessage)request.getAttribute(ErrorMessage.getClassName());
String empty="";
%>
<jsp:useBean id="modparam" scope="page" class="us.glos.mi.domain.ModAdminParam" />
<jsp:useBean id="cntparam" scope="page" class="us.glos.mi.domain.ContactParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("modnew.jsp")));
else
	out.println("New Model");
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
	<!--  <script type="text/javascript" src="../js/modnew.js"></script>-->
	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="./mod.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
	var cnt_dev_div="<%=cntparam.getDeveloperParamName()%>";
	var cnt_dist_div="<%=cntparam.getDistributorParamName()%>";
	function validate_form()
	{
		var result=true;
		
		if($("#mod_update_rea").length>0)
		{
			if($.trim($("#mod_update_rea").val())=="")
			{
				result=false;
				textBoxError($("#mod_update_rea"));
				$("#err_mod_update_rea").text("Reason for Update can not be empty.");
				$("#err_mod_update_rea").show();
			}
			else if($.trim($("#mod_update_rea").val()).length>500)
			{
				result=false;
				textBoxError($("#mod_update_rea"));
				$("#err_mod_update_rea").text("Reason for Update can not have more than 500 characters.");
				$("#err_mod_update_rea").show();
			}
			else
			{
				$("#err_mod_update_rea").hide();
				textBoxResume($("#mod_update_rea"));
			}
		}
		if($("input[name=mod_cat[]][checked]").size()==0)
		{
			result=false;
			$("#err_mod_cat").text("At least one Model Category must be selected");
			$("#err_mod_cat").show();
		}
		else
			$("#err_mod_cat").hide();
		
		if($.trim($("#mod_name").val())=="")
		{
			result=false;
			textBoxError($("#mod_name"));
			$("#err_mod_name").text("Model Name can not be empty");
			$("#err_mod_name").show();
		}
		else if($.trim($("#mod_name").val()).length>255)
		{
			result=false;
			textBoxError($("#mod_name"));
			$("#err_mod_name").text("Model Name can not have more than 255 characters");
			$("#err_mod_name").show();
		}
		else
		{
			$("#err_mod_name").hide();
			textBoxResume($("#mod_name"));
		}

		if($.trim($("#mod_url").val())!="")
		{
			if(validateUrl($.trim($("#mod_url").val()))==false)
			{
				result=false;
				textBoxError($("#mod_url"));
				$("#err_mod_url").text("Model website is not a valid URL");
				$("#err_mod_url").show();
			}
			else
			{
				$("#err_mod_url").hide();
				textBoxResume($("#mod_url"));
			}
		}
		else
		{
			$("#err_mod_url").hide();
			textBoxResume($("#mod_url"));
		}

		
		if($.trim($("#mod_ver").val())=="")
		{
			result=false;
			textBoxError($("#mod_ver"));
			$("#err_mod_ver").text("Model Version can not be empty");
			$("#err_mod_ver").show();
		}
		else if($.trim($("#mod_ver").val()).length>255)
		{
			result=false;
			textBoxError($("#mod_ver"));
			$("#err_mod_ver").text("Model Version can not have more than 255 characters");
			$("#err_mod_ver").show();
		}
		else
		{
			$("#err_mod_ver").hide();
			textBoxResume($("#mod_ver"));
		}
		if($.trim($("#mod_abbrev").val()).length>255)
		{
			result=false;
			textBoxError($("#mod_abbrev"));
			$("#err_mod_abbrev").text("Model Acronym can not have more than 255 characters");
			$("#err_mod_abbrev").show();
		}
		else
		{
			$("#err_mod_abbrev").hide();
			textBoxResume($("#mod_abbrev"));
		}
		if(!$("input[name=modavailid]").is(':checked'))
		{
			result=false;
			$("#err_mod_avail").text("One Model Applicability must be selected");
			$("#err_mod_avail").show();
		}
		else
			$("#err_mod_avail").hide();

		if($("input[name=mod_theme[]][checked]").size()==0)
		{
			result=false;
			$("#err_mod_theme").text("At least one Model Theme must be selected");
			$("#err_mod_theme").show();
		}
		else
			$("#err_mod_theme").hide();

		if($.trim($("#mod_purpose").val())=="")
		{
			result=false;
			textBoxError($("#mod_purpose"));
			$("#err_mod_desc").text("Model Primary Purpose can not be empty");
			$("#err_mod_desc").show();
		}
		else
		{
			$("#err_mod_desc").hide();
			textBoxResume($("#mod_purpose"));
		}

		if($.trim($("#mod_attri").val())=="")
		{
			result=false;
			textBoxError($("#mod_attri"));
			$("#err_mod_attri").text("Model Primary Purpose can not be empty");
			$("#err_mod_attri").show();
		}
		else
		{
			$("#err_mod_attri").hide();
			textBoxResume($("#mod_attri"));
		}

		if($.trim($("#mod_datareq").val())=="")
		{
			result=false;
			textBoxError($("#mod_datareq"));
			$("#err_mod_datareq").text("Model Primary Purpose can not be empty");
			$("#err_mod_datareq").show();
		}
		else
		{
			$("#err_mod_datareq").hide();
			textBoxResume($("#mod_datareq"));
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
			if(validateEmail($.trim($(this).val()))==false&&$.trim($(this).val()).toLowerCase()!='n/a')
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
			if(validateEmail($.trim($(this).val()))==false&&$.trim($(this).val()).toLowerCase()!='n/a')
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

		if($.trim($("input[name=mod_cnt_firstname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=mod_cnt_firstname]"));
			
		}
		else
		{
			textBoxResume($("input[name=mod_cnt_firstname]"));
		}

		if($.trim($("input[name=mod_cnt_lastname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=mod_cnt_lastname]"));
			
		}
		else
		{
			textBoxResume($("input[name=mod_cnt_lastname]"));
		}
		
		if($.trim($("input[name=mod_cnt_email]").val())!="")
		{
			if(false==validateEmail($.trim($("input[name=mod_cnt_email]").val()))&&$.trim($("input[name=mod_cnt_email]").val()).toLowerCase()!='n/a')
			{
				result=false;
				textBoxError($("input[name=mod_cnt_email]"));
			}
			else
				textBoxResume($("input[name=mod_cnt_email]"));
		}
		else
		{
			result=false;
			textBoxError($("input[name=mod_cnt_email]"));
		}

		if($("input[name=mod_cnt_zip]").val()!="")
		{
			var val=$.trim($("input[name=mod_cnt_zip]").val());
			var cny=$("#mod_cnt_country option:selected").val();
			if(cny=='ca'&&validateCAPostalcode(val)==false)
			{
				textBoxError($("input[name=mod_cnt_zip]"));
				result=false;
			}
			else if(cny=='us'&&validateUSZipcode(val)==false)
			{
				textBoxError($("input[name=mod_cnt_zip]"));
				result=false;
			}
			else
			{
				textBoxResume($("input[name=mod_cnt_zip]"));
				
			}
		}
		else
		{
			textBoxResume($("#mod_cnt_zip"));
			
		}

		if($.trim($("input[name=mod_cnt_phone]").val())!="")
		{
			if(validatePhone($.trim($("input[name=mod_cnt_phone]").val()))==false)
			{
				result=false;
				textBoxError($("input[name=mod_cnt_phone]"));
			}
			else
			{
				textBoxResume($("input[name=mod_cnt_phone]"));
			}		
		}
		else
			textBoxResume($("input[name=mod_cnt_phone]"));
		
		if($.trim($("#mod_easyuse").val())=="")
		{
			result=false;
			textBoxError($("#mod_easyuse"));
			$("#err_mod_easyuse").text("Model Skill Level can not be empty");
			$("#err_mod_easyuse").show();
		}
		else
		{
			$("#err_mod_easyuse").hide();
			textBoxResume($("#mod_easyuse"));
		}
		return result;
	}
	
	$(function() {
		$("#mod_tabs").tabs();
		$("#a_modinfo_next").button();
		$("#a_modpeople_prev").button();
		$("#a_modpeople_next").button();
		$("#a_modother_prev").button();
		$("#a_mod_submit_btn").button();
		
		$("#btn_dev_add").button();
		$("#btn_dist_add").button();
		//$("#btn_dev_addp").button();
		//$("#btn_dev_addo").button();
		//$("#btn_dev_edit").button();
		//$("#btn_dev_remo").button();
		
		$("#a_modinfo_next").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',1);
		});
		$("#a_modpeople_prev").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',0);
		});
		$("#a_modpeople_next").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',2);
		});
		$("#a_modother_prev").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',1);
		});
		$("#a_model_submit").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=mod_frm]").attr("action","modopt.glos");
				$("#mod_action").val("submit");
				$("[name=mod_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		$("#a_mod_submit_btn").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=mod_frm]").attr("action","modopt.glos");
				$("#mod_action").val("submit");
				$("[name=mod_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		$("#a_model_save").click(function(event){
			event.preventDefault();
			if($.trim($("#mod_name").val())=="")
			{
				result=false;
				textBoxError($("#mod_name"));
				$("#err_mod_name").text("Model Name can not be empty");
				$("#err_mod_name").show();
				errorMessageBox("Failed to save the draft. Model name must not be empty!");
				return;
			}
			else
			{
				$("#err_mod_name").hide();
				textBoxResume($("#mod_name"));
			}
			$("[name=mod_frm]").attr("action","moddraft.glos");
			$("#mod_ajax").val("1");
			var val=$("#draft_id").val();
			if(val==""||val<=0)//save
			{
				$("#mod_action").val("save");
				
				//$("#mod_frm").submit();
				$.post(
						"moddraft.glos",
						$("#mod_frm").serialize(),
						function(text)
						{
							var did=parseInt(text);
							if(did>0)
							{
								$("#draft_id").val(did);
								confirmMessageBox('Your draft has been successfully saved.');
							}
							else
							{
								if(did==-2)
									errorMessageBox('Failed to save the draft. You can only save no more than 8 drafts.');
								else
									errorMessageBox("Failed to save the draft.");
							}
						}
				);
			}
			else if(val>0)//update
			{
				$("#mod_action").val("update");
				
				$.post(
						"moddraft.glos",
						$("#mod_frm").serialize(),
						function(text)
						{
							var id=parseInt(text);
							if(id==0)
							{
								$("draft_id").val(id);
								confirmMessageBox('Your draft has been successfully updated.');
							}
							else
							{
								errorMessageBox('Failed to update the draft.');
							}
						}
				);
			}
		});
		var dev_count=<%=developers==null?1:developers.size()%>;
		var dist_count=<%=distributors==null?1:distributors.size()%>;
		$("#btn_dev_add").click(function(event){
			if($("div[id^='"+cnt_dev_div+"']").size()<5)
			{
				//var elem=$("div[id^='"+cnt_dev_div+"']").first().clone(true).attr("id",cnt_dev_div+(dev_count)).insertBefore($("#btn_dev_add")).fadeIn();
				//due to the bug of autocomplete,clone event can't be used,manually attach events
				var elem=$("#dev_clone").clone().attr("id",cnt_dev_div+(dev_count)).insertBefore($("#err_devs")).fadeIn();
				elem.find(".a_dev_remove").click(dev_remove_callback);
				elem.find(".mod_country").change(countryChangeCallback);
				//var foo=lem.find("#mod_org").autocomplete("enable");
				//var clone=$("#dev_clone").clone(true);
				//clone.attr("id",cnt_dev_div+(dev_count));
				//var elem=clone.insertBefore($("#btn_dev_add")).fadeIn();
				elem.children("#dev_cnt_id").val("");
				/*elem.children("#dev_cnt_id").children("table")
				elem.find("#mod_fname").val("");
				elem.find("#mod_lname").val("");*/
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
				elem.find(".mod_country").change(countryChangeCallback);
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
				td.children("#mod_state").hide();
				td.children("#mod_province").show();
			}
			else
			{
				td.children("#mod_province").hide();
				td.children("#mod_state").show();
				
			}
		}
		$(".mod_country").change(countryChangeCallback);

		$("#chk_same_cnt").click(function(){
			$("input[name=mod_cnt_firstname]").val($("#fname").val());
			$("input[name=mod_cnt_lastname]").val($("#lname").val());
			$("input[name=mod_cnt_add1]").val($("#add1").val());
			$("input[name=mod_cnt_add2]").val($("#add2").val());
			$("input[name=mod_cnt_city]").val($("#city").val());
			$("select[name=mod_cnt_country]").val($("#country").val());
			if($("#country").val()=='us')
			{
				$("select[name=mod_cnt_state]").val($("#state").val());
				$("select[name=mod_cnt_province]").hide();
				$("select[name=mod_cnt_state]").show();
			}
			else if($("#country").val()=='ca')
			{
				$("select[name=mod_cnt_province]").val($("#province").val());
				$("select[name=mod_cnt_state]").hide();
				$("select[name=mod_cnt_province]").show();
			}
			$("input[name=mod_cnt_zip]").val($("#zip").val());
			$("input[name=mod_cnt_phone]").val($("#phone").val());
			$("input[name=mod_cnt_orgid]").val($("#org").val());
		});
	});
	
	</script>
	
</head>
<body>
<%
    if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
    {	
%>
<jsp:include page="../../adm/topbar.jsp" flush="true">
	<jsp:param name="id" value="admin_dock"/>
</jsp:include>
<%
    }
    else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
    {
%>
<jsp:include page="../../mod/topbar.jsp" flush="true">
	<jsp:param name="id" value="mod_dock"/>
</jsp:include>
<%
    }
   
%>
<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<div id="err_prompt_div" style="color:red;text-align:center;<%=err!=null?"display:block;":"display:none;"%>">
    Please fix the errors on all 3 tabs.
</div>

<div style="margin-left:auto;margin-right:auto;width:980px;position:relative;">
<div style="margin-left:auto;margin-right:auto;width:810px;">

	<div id="mod_tabs">
		<form name="mod_frm" id="mod_frm" method="POST" action="">
		<input type="hidden" id="mod_action" name="<%=modparam.getActionParamName()%>" value="" />
		<input type="hidden" id="mod_id" name="<%=modparam.getModIdParamName()%>" value="<%=(model==null)?empty:model.getId()%>" />
		<input type="hidden" id="draft_id" name="<%=modparam.getDraftIdParamName() %>" value="<%=(model==null)?empty:model.getSerialId()%>" />
		<input type="hidden" id="mod_ajax" name="<%=modparam.getAjaxParamName() %>" value="0" />
		<ul>
			<li><a href="#modinfo">Model</a></li>
			<li><a href="#modpeople">People</a></li>
			<li><a href="#modother">Other Information</a></li>
		</ul>
<%
String errMsg=null;
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModUpdateReasonParam());
%>
		<div id="modinfo">
			<div>
<%
if(model!=null&&model.getId()>0)
{
%>
				<fieldset>
					<legend class="fd_legend">Reason for Update:</legend>
					<div>(State the reason of update. This will help the auditor deccide wheather this update should be considered as a revison on the existing model or be considered as a new model. No more than 200 words)</div>
					<textarea name="<%=modparam.getModUpdateReasonParam() %>" id="mod_update_rea" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getUpdateReason())%></textarea>
						<br/><div id="err_mod_update_rea" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
				</fieldset>
<%
}
%>
				<fieldset>
					<legend class="fd_legend">Model Category (Check all that apply):</legend>
					<glos:checkboxdisplay keyValues="<%=DBCache.getModelCategories() %>" namePrefix="<%=modparam.getModCategoryParamName() %>" divId="mod_cat_div" checkedIds="<%=(model==null)?null:Converter.ToStringList(model.getCategoryIds())%>"/>
<%
errMsg=null;
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModCategoryParamName());
%>
					<br/><div id='err_mod_cat' style='color:red;<%=errMsg==null?"display:none;":empty %>'><%=errMsg==null?empty:errMsg %></div>
					
				</fieldset><br/>
				<fieldset>
					<legend class="fd_legend">Name and Acronym (no more than 255 characters):</legend>
					<div>Name:</div>
					<div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModNameParamName());
%>
						<input type="text" name="<%=modparam.getModNameParamName() %>" id="mod_name" style="" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" size="50" value="<%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getName())%>"/>
						<div id="err_mod_name" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModAbbrevParamName());
%>							
					<div style="">Acronym:</div>
					<div>
						<input type="text" name="<%=modparam.getModAbbrevParamName() %>" id="mod_abbrev" class="textbox-app" size="50" value="<%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getAbbreviation())%>"/>
						<div id="err_mod_abbrev" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModUrlParamName());
%>
					<div style="">Website:</div>
					<div>
						<input type="text" name="<%=modparam.getModUrlParamName() %>" id="mod_url" class="textbox-app" size="50" value="<%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getUrl())%>"/>
						<div id="err_mod_url" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					</div>
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Current Version (no more than 255 characters):</legend>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModVerNoParamName());
%>
					<div>
						<input type="text" name="<%=modparam.getModVerNoParamName() %>" id="mod_ver" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" size="50" value="<%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getVersionNo())%>"/>
						<div id="err_mod_ver" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					</div>
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Model Availability:</legend>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModAvailParamName());
%>
					<glos:radiodisplay keyValues="<%=DBCache.getModelAvailibilites() %>" name="<%=modparam.getModAvailParamName() %>" idPrefix="mod_avail_radio" divId="mod_avail_div" checkedId="<%=(model==null)?-1:model.getAvailableId()%>"/>
					<br/><div id='err_mod_avail' style='color:red;<%=errMsg==null?"display:none;":empty %>'><%=errMsg==null?empty:errMsg %></div>
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Applicability for Assessing GLOS Themes (check all that apply):</legend>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModThemeParamName());
%>
					<glos:checkboxdisplay keyValues="<%=DBCache.getThemes() %>" namePrefix="<%=modparam.getModThemeParamName() %>" divId="mod_theme_div" checkedIds="<%=(model==null)?null:Converter.ToStringList(model.getThemeIds())%>"/>
					<br/><div id='err_mod_theme' style='color:red;<%=errMsg==null?"display:none;":empty %>'><%=errMsg==null?empty:errMsg %></div>
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Primary Purposes:</legend>
					<div style="font-size:smaller">
						(Original objective or research question the model was designed to answer. Other purposes can be included in the notes. Include
						whether use is management or research or both. Please include specific location information for use as search keywords. At a minimum, please list the associated main lake or lakes, such as Superior, Michigan, Huron, Erie and/or Ontario. You may list multiple locations at various geographic scales to serve as additional keywords, i.e. St. Mary's River, St. Lawrence River, Alexandria Bay)
					</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModDescParamName());
%>
						<textarea name="<%=modparam.getModDescParamName() %>" id="mod_purpose" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getDescription())%></textarea>
						<br/><div id="err_mod_desc" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
					
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Model Characteristics:</legend>
					<div style="font-size:smaller">
						(Please include a brief	description of the model or model application including the number of physical dimensions, time and
						space resolution and kinetic complexity.)
					</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModAttriParamName());
%>
					<textarea name="<%=modparam.getModAttriParamName() %>" id="mod_attri" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getAttribute())%></textarea>
					<br/><div id="err_mod_attri" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
				</fieldset>
				<br/>
				<fieldset>
					<legend class="fd_legend">Data Requirements:</legend>
					<div style="font-size:smaller">
						(List the types of data or major input parameters the model requires to run. If the list is long,
						then summarize the data by type.)
					</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModDataReqParamName());
%>
					<textarea name="<%=modparam.getModDataReqParamName() %>" id="mod_datareq" class="<%=errMsg==null?"textbox-app":"textbox-error"%>" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getDataRequirement())%></textarea>
					<br/><div id="err_mod_datareq" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
				</fieldset>
				<br/>
				
			</div>
			<a id="a_modinfo_next" href="#" style="font-size:larger;">Next Page</a>
		</div>
		<div id="modpeople">
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
						<input type="hidden" name="<%=cntparam.getDeveloperParamName()+cntparam.getContactIdParamName() %>" id="dev_cnt_id" value="<%=cnt.getId()>0?cnt.getId():empty %>" />
						<table id="tbl">
				       		<tr id="tr">
				    			<td id="td">
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="mod_fname" name="<%=cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName() %>" value="<%=cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="mod_lname" name="<%=cntparam.getDeveloperParamName()+cntparam.getLastNameParamName() %>" value="<%=cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="mod_email" name="<%=cntparam.getDeveloperParamName()+cntparam.getEmailParamName() %>" value="<%=cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If Name and Email are not available or can't be applied, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Organization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="mod_org" name="<%=cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName() %>" value="<%=cnt.getOrgnization()!=null?HTMLUtilities.filterDisplay(cnt.getOrgnization().getName()):empty %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
									<a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my organization is not on the list?</a>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="mod_add1" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress1()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="mod_add2" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress2()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="mod_city" name="<%=cntparam.getDeveloperParamName()+cntparam.getCityParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getCity()):empty %>" class="textbox-app" size="25"/>
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
				    				<select id="mod_country" name="<%=cntparam.getDeveloperParamName()+cntparam.getCountryParamName() %>" class="textbox-app mod_country">
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
		
				    				<glc:statesselect id="mod_state" name="<%=cntparam.getDeveloperParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>" selected="<%=(isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):empty %>"/>
				    				<glc:statesselect id="mod_province" name="<%=cntparam.getDeveloperParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>" selected="<%=(!isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):empty %>"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="mod_zip" name="<%=cntparam.getDeveloperParamName()+cntparam.getZipParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getZipcode()):empty %>" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="mod_phone" name="<%=cntparam.getDeveloperParamName()+cntparam.getPhoneParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getPhone()):empty %>" class="loginfont textbox-app" size="25"/>
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
					<input type="hidden" name="<%=cntparam.getDistributorParamName()+cntparam.getContactIdParamName() %>" id="dist_cnt_id" value="<%=cnt.getId()>0?cnt.getId():empty %>" />
					<table>
				       		<tr>
				    			<td>
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="mod_fname" name="<%=cntparam.getDistributorParamName()+cntparam.getFirstNameParamName() %>" value="<%=cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="mod_lname" name="<%=cntparam.getDistributorParamName()+cntparam.getLastNameParamName() %>" value="<%=cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):empty %>" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="mod_email" name="<%=cntparam.getDistributorParamName()+cntparam.getEmailParamName() %>" value="<%=cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):empty %>"  class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If Name and Email are not available or can't be applied, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Organization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="mod_org" name="<%=cntparam.getDistributorParamName()+cntparam.getOrgIdParamName() %>" value="<%=cnt.getOrgnization()!=null?HTMLUtilities.filterDisplay(cnt.getOrgnization().getName()):empty %>"  cssClass="ui-widget loginfont textbox-app" size="50"/>
									<a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my organization is not on the list?</a>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="mod_add1" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress1ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress1()):empty %>"  class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="mod_add2" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress2ParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getAddress2()):empty %>"  class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="mod_city" name="<%=cntparam.getDistributorParamName()+cntparam.getCityParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getCity()):empty %>"  class="textbox-app" size="25"/>
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

				    				<select id="mod_country" name="<%=cntparam.getDistributorParamName()+cntparam.getCountryParamName() %>" class="textbox-app mod_country">
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
				    				<glc:statesselect id="mod_state" name="<%=cntparam.getDistributorParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>"  selected="<%=(isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):empty %>"/>
				    				<glc:statesselect id="mod_province" name="<%=cntparam.getDistributorParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>"  selected="<%=(!isUS&&cnt.getAddress()!=null)?HTMLUtilities.filterDisplay(cnt.getAddress().getState()):empty %>"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="mod_zip" name="<%=cntparam.getDistributorParamName()+cntparam.getZipParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getZipcode()):empty %>"  class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="mod_phone" name="<%=cntparam.getDistributorParamName()+cntparam.getPhoneParamName() %>" value="<%=cnt.getAddress()!=null?HTMLUtilities.filterDisplay(cnt.getAddress().getPhone()):empty %>"  class="loginfont textbox-app" size="25"/>
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
if(model!=null&&model.getContacts()!=null&&model.getContacts().isEmpty()==false)
{
	cnt=model.getContacts().get(0);
	add=cnt.getAddress();
	org=cnt.getOrgnization();
}
%>
			<fieldset>
				<legend class="fd_legend">Contact:</legend>
				<input type="hidden" name="<%=modparam.getModCntIdParamName() %>" value="<%=cnt==null?empty:(cnt.getId()>0?cnt.getId():empty) %>"  />
				<input type="checkbox" id="chk_same_cnt" /><span style="font-size:smaller;font-weight:bold;">The contact information is as same as mine.</span>
				<table>
				       	<tr>
				    		<td>
				    			<div style="font-size:smaller;">First Name:</div>
				    			<input type="text" id="mod_fname" name="<%=modparam.getModFirstNameParamName() %>" value="<%=cnt==null?empty:(cnt.getFirstName()!=null?HTMLUtilities.filterDisplay(cnt.getFirstName()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Last Name:</div>
				    			<input type="text" id="mod_lname" name="<%=modparam.getModLastNameParamName() %>" value="<%=cnt==null?empty:(cnt.getLastName()!=null?HTMLUtilities.filterDisplay(cnt.getLastName()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<div style="font-size:smaller;">Email:</div>
				    			<input type="text" id="mod_email" name="<%=modparam.getModEmailParamName() %>" value="<%=cnt==null?empty:(cnt.getEmail()!=null?HTMLUtilities.filterDisplay(cnt.getEmail()):empty) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Address1:</div>
				    			<input type="text" id="mod_add1" name="<%=modparam.getModAddress1ParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getAddress1()) %>" class="textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Address2:</div>
				    			<input type="text" id="mod_add2" name="<%=modparam.getModAddress2ParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getAddress2()) %>" class="textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    		    <div style="font-size:smaller;">City:</div>
				    			<input type="text" id="mod_city" name="<%=modparam.getModCityParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getCity()) %>" class="textbox-app" size="25"/>
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
				    			<select id="mod_country" name="<%=modparam.getModCountryParamName() %>" class="textbox-app mod_country">
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
				    			<glc:statesselect id="mod_state" name="<%=modparam.getModStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="<%=!isUS %>" selected="<%=(isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):empty %>"/>
				    			<glc:statesselect id="mod_province" name="<%=modparam.getModProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>"  selected="<%=(!isUS&&add!=null)?HTMLUtilities.filterDisplay(add.getState()):empty %>"/>
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    			<input type="text" id="mod_zip" name="<%=modparam.getModZipParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getZipcode()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Phone:</div>
				    			<input type="text" id="mod_phone" name="<%=modparam.getModPhoneParamName() %>" value="<%=add==null?empty:HTMLUtilities.filterDisplay(add.getPhone())%>" class="loginfont textbox-app" size="25"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="font-size:smaller;">Organization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="mod_org" name="<%=modparam.getModOrgIdParamName() %>" value="<%=org==null?empty:HTMLUtilities.filterDisplay(org.getName()) %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
								<a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my organization is not on the list?</a>
							</td>
						</tr>
					</table>
<%

	sb=new StringBuilder();
	if(err!=null)
	{
		if(err.getErrorMsg(modparam.getModFirstNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModFirstNameParamName())));
		if(err.getErrorMsg(modparam.getModLastNameParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModLastNameParamName())));
		if(err.getErrorMsg(modparam.getModEmailParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModEmailParamName())));
		if(err.getErrorMsg(modparam.getModOrgIdParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModOrgIdParamName())));
		if(err.getErrorMsg(modparam.getModAddress1ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModAddress1ParamName())));
		if(err.getErrorMsg(modparam.getModAddress2ParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModAddress2ParamName())));
		if(err.getErrorMsg(modparam.getModCityParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModCityParamName())));
		if(err.getErrorMsg(modparam.getModCountryParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModCountryParamName())));
		if(err.getErrorMsg(modparam.getModStateParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModStateParamName())));
		if(err.getErrorMsg(modparam.getModZipParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModZipParamName())));
		if(err.getErrorMsg(modparam.getModPhoneParamName())!=null)
			sb.append(String.format("<div>%s</div>",err.getErrorMsg(modparam.getModPhoneParamName())));
	}
%>
					<div id="err_cnt" style="color:red;"><%=sb.toString() %></div>
			</fieldset>
			<br/>
			<a id="a_modpeople_prev" href="#" style="font-size:larger;">Prev Page</a>
			<a id="a_modpeople_next" href="#" style="font-size:larger;">Next Page</a>
		</div>
		<div id="modother">
			<fieldset>
				<legend class="fd_legend">Easy of Use/Skill level:</legend>
				<div style="font-size:smaller">
					(Include any specific information that the user may need to know to run the model, i.e.
					 GIS, advanced programming in JAVA, FORTRAN...)
				</div>
<%
if(err!=null)
	errMsg=err.getErrorMsg(modparam.getModEasyUseParamName());
%>
				<textarea name="<%=modparam.getModEasyUseParamName() %>" id="mod_easyuse" class="textbox-app" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getSkillLevel()) %></textarea>
				<br/><div id="err_mod_easyuse" style="color:red;display:inline;"><%=errMsg==null?empty:errMsg %></div>
			</fieldset>
			<br/>
			<fieldset>
				<legend class="fd_legend">Strength and Weaknesses (no more than 255 characters):</legend>
				<div style="font-size:smaller">
					(List items that separate this model from others in the field or that make it unique. If the model has any known limitations or caveats for
					 its use list those as well.)
				</div>
				<textarea name="<%=modparam.getModStrengthParamName() %>" id="mod_stren" class="textbox-app" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getStrength())%></textarea>
			</fieldset>
			<br/>
			<fieldset>
				<legend class="fd_legend">Notes:</legend>
				<div style="font-size:smaller">
						(Include any additional information about the model that would help others understand the model. If the model
						is still under development, include an estimated completion date. Include a link to model documentation if avaiable)
				</div>
				<textarea name="<%=modparam.getModNoteParamName() %>" id="mod_note" class="textbox-app" rows="10" cols="90"><%=(model==null)?empty:HTMLUtilities.filterDisplay(model.getNote())%></textarea>
			</fieldset>
			<br/>
				
			<a id="a_modother_prev" href="#" style="font-size:larger;">Prev Page</a>
			<a id="a_mod_submit_btn" href="#" style="font-size:larger;">Submit</a>
		</div>
		<input type="hidden" name="<%=cntparam.getStateParamName() %>" value="," />
		</form>
	</div>
</div>
<div style="height:300px;width:80px;top:2px;right:3px;position:absolute;" class="ui-widget-header ui-corner-all">
	<table align="center">
		<tr>
			<td>
				<a href="#" id="a_model_save" title="Save As Draft"></a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="#" id="a_model_submit" title="Submit"></a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="../pub" id="a_model_quit" title="Quit without Save"></a>
			</td>
		</tr>
	</table>

</div>
</div><br/>
				
				<!-- Clone -->
				<div id="dev_clone" style="position:relative;display:none;">
						<input type="hidden" name="<%=cntparam.getDeveloperParamName()+cntparam.getContactIdParamName() %>" id="dev_cnt_id" value="" />
						<table>
				       		<tr>
				    			<td>
				    				<div style="font-size:smaller;">First Name:</div>
				    				<input type="text" id="mod_fname" name="<%=cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="mod_lname" name="<%=cntparam.getDeveloperParamName()+cntparam.getLastNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="mod_email" name="<%=cntparam.getDeveloperParamName()+cntparam.getEmailParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If Name and Email are not available or can't be applied, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Organization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="mod_org" name="<%=cntparam.getDeveloperParamName()+cntparam.getOrgIdParamName() %>" value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
									<a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my organization is not on the list?</a>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="mod_add1" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress1ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="mod_add2" name="<%=cntparam.getDeveloperParamName()+cntparam.getAddress2ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="mod_city" name="<%=cntparam.getDeveloperParamName()+cntparam.getCityParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
				    				<select id="mod_country" name="<%=cntparam.getDeveloperParamName()+cntparam.getCountryParamName() %>" class="textbox-app mod_country">
				    					<option value="" selected='selected'>----</option>
				    					<option value="us" >United States</option>
				    					<option value="ca" >Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
				    				<glc:statesselect id="mod_state" name="<%=cntparam.getDeveloperParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app"  country="us" hide="false"/>
				    				<glc:statesselect id="mod_province" name="<%=cntparam.getDeveloperParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="true" />
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="mod_zip" name="<%=cntparam.getDeveloperParamName()+cntparam.getZipParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="mod_phone" name="<%=cntparam.getDeveloperParamName()+cntparam.getPhoneParamName() %>" value="" class="loginfont textbox-app" size="25"/>
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
				    				<input type="text" id="mod_fname" name="<%=cntparam.getDistributorParamName()+cntparam.getFirstNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Last Name:</div>
				    				<input type="text" id="mod_lname" name="<%=cntparam.getDistributorParamName()+cntparam.getLastNameParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    				<div style="font-size:smaller;">Email:</div>
				    				<input type="text" id="mod_email" name="<%=cntparam.getDistributorParamName()+cntparam.getEmailParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
								<td colspan="3">
									<span style="font-size:smaller;">(If Name and Email are not available or can't be applied, please put 'N/A' instead.)</span>
								</td>
							</tr>
				    		<tr>
								<td colspan="3">
									<div style="font-size:smaller;">Organization:</div>
									<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="mod_org" name='<%=cntparam.getDistributorParamName()+"0"+cntparam.getOrgIdParamName() %>' value="" cssClass="ui-widget loginfont textbox-app" size="50"/>
									<a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my organization is not on the list?</a>
								</td>
							</tr>
				    		<tr>
				    			<td>
				    				<div style="font-size:smaller;">Address1:</div>
				    				<input type="text" id="mod_add1" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress1ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Address2:</div>
				    				<input type="text" id="mod_add2" name="<%=cntparam.getDistributorParamName()+cntparam.getAddress2ParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">City:</div>
				    				<input type="text" id="mod_city" name="<%=cntparam.getDistributorParamName()+cntparam.getCityParamName() %>" value="" class="textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>
				    		    	<div style="font-size:smaller;">Country:</div>
				    				<select id="mod_country" name="<%=cntparam.getDistributorParamName()+cntparam.getCountryParamName() %>" class="textbox-app mod_country">
				    					<option value="" selected='selected'>----</option>
				    					<option value="us" >United States</option>
				    					<option value="ca" >Canada</option>
				    				</select>
				    			</td>
				    			<td id="td_state">	
				    		    	<div style="font-size:smaller;">State/Province:</div>
				    				<glc:statesselect id="mod_state" name="<%=cntparam.getDistributorParamName()+cntparam.getStateParamName() %>"  cssClass="loginfont textbox-app" country="us" hide="false"/>
				    				<glc:statesselect id="mod_province" name="<%=cntparam.getDistributorParamName()+cntparam.getProvinceParamName() %>"  cssClass="loginfont textbox-app" country="ca" hide="true" />
				    			</td>
				    			<td>
				    		    	<div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    				<input type="text" id="mod_zip" name="<%=cntparam.getDistributorParamName()+cntparam.getZipParamName() %>" value="" class="loginfont textbox-app" size="25"/>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td colspan="3">
				    		    	<div style="font-size:smaller;">Phone:</div>
				    				<input type="text" id="mod_phone" name="<%=cntparam.getDistributorParamName()+cntparam.getPhoneParamName() %>" value="" class="loginfont textbox-app" size="25"/>
								</td>
							</tr>
						
						</table>
						<div style="float:right;top:0;right:0;position:absolute;">
							<a href="#" id="a_dist_remove" class="a_dist_remove">Remove</a>
						</div>
						<hr/>
				</div>
					
<script type="text/javascript">
	$("#mod_country").change(function(){
		if('ca'==$("#mod_country option:selected").val())
		{
			$("#mod_state").hide();
			$("#mod_province").show();
		}
		else
		{
			$("#mod_province").hide();
			$("#mod_state").show();
			
		}
	});
</script>

</body>
</html>