<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%
User usr=null;
if(session.getAttribute(User.getClassName()) instanceof User)
	usr=(User)session.getAttribute(User.getClassName());

if(usr==null&&!(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())))
	return;
%>
<jsp:useBean id="sow_ap" scope="page" class="us.glos.mi.domain.AppAdminParam" />
<div id="dlg_chg_owner" title="Change Owner" style="display:none;">
	<div>Please enter new owner's email:</div>
	<input type="text" id="chown_email" value="" class="loginfont textbox-app" size="25"/>
	<div id="chown_err_msg" style="color:red;"></div><br/>
	<input type="button" value="Apply" id="chown_btn"/>
</div>

<div id="dlg_chg_status" title="Change Status" style="display:none;">
	<div>
		<input type="radio" name="chstas_radio" id="chstas_enable" value="1"/>Enabled<br/>
		<input type="radio" name="chstas_radio" id="chstas_disable" value="0"/>Disabled
	</div>
	<div id="chstas_err_msg" style="color:red;"></div><br/>
	<input type="button" value="Apply" id="chstas_btn"/>
</div>
<script type="text/javascript">
var chown_url=null;
var chstas_url=null;

function admin_chgown(url)
{
	chown_url=url;
	$("#dlg_chg_owner").dialog({
    	height:300,
    	width:400,
    	modal:false,
    	position:'center',
    	buttons:{
    				'Close':function()
    				{
					    $("#chown_email").val("");
		 				$("#chown_err_msg").html("");
		 				textBoxResume($("#chown_email"));
						$(this).dialog("close");
						
					}
    			}
    	});
}
$("#chown_btn").click(function(){
	if($.trim($("input#chown_email").val()).length>0&&true==validateEmail($("input#chown_email").val()))
	{
		textBoxResume($("#chown_email"));
		$("#chown_err_msg").text("");
		$.get(chown_url,
				{
					<%=sow_ap.getAppEmailParamName()%>:$.trim($("input#chown_email").val())
				},
				function(data){
					if(data=="0")
					{
						confirmMessageBox("The owner has been changed successfully!");
						window.location="appadmin.glos?app_action=list";
					}
					else if(data=="-1")
					{
						errorMessageBox("The user you specified doesn't exist in the system or is lack of privilege to own this record!");
					}
					else if(data=="-2")
						errorMessageBox("Failed to update owner!");
				});
	}
	else
	{
		textBoxError($("#chown_email"));
		$("#chown_err_msg").text("Please enter a valid email address.");
	}
});
function admin_chg_status(url,id)
{
	chstas_url=url;
	$.get("../adm/appstatus.glos",
			{
				<%=sow_ap.getAppIdParamName()%>:id
			},
			function(data){
				if(data=="0")
				{
					$("#chstas_disable").attr("checked",true);
					$("#chstas_enable").attr("checked",false);
				}
				else//returnning 1 means enabled
				{
					$("#chstas_disable").attr("checked",false);
					$("#chstas_enable").attr("checked",true);
				}
			});
	$("#dlg_chg_status").dialog({
    	height:300,
    	width:400,
    	modal:false,
    	position:'center',
    	buttons:{
    				'Close':function()
    				{
					    $("#chstas_enable").attr("checked",false);
					    $("#chstas_disable").attr("checked",false);
		 				$("#chstas_err_msg").html("");
		 				
						$(this).dialog("close");
						
					}
    			}
    	});
}

$("#chstas_btn").click(function(){
	
	$.get(chstas_url,
			{
				<%=sow_ap.getAppStatusParamName()%>:$("[name=chstas_radio]:checked").val()
			},
			function(data){
				if(data=="0")
				{
					confirmMessageBox("The status has been changed successfully!");
					window.location="appadmin.glos?<%=sow_ap.getActionParamName()%>=list";
				}
				else if(data=="-1")
				{
					errorMessageBox("Failed to change the status!");
				}
				
			});
});

</script>