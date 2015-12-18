<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@page import="us.glos.mi.domain.UserParam"%>
<%@page import="us.glos.mi.domain.ModelInfo"%>
<%@page import="us.glos.mi.domain.AppInfo"%>
<%@page import="us.glos.mi.domain.Attachment"%>
<%@ page import="us.glos.mi.domain.Contact"%>
<%@ page import="us.glos.mi.domain.SearchResultParam" %>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%
User usr=null;
if(session!=null)
{
	
	if(session.getAttribute(User.getClassName()) instanceof User)
	{
		usr=(User)session.getAttribute(User.getClassName());
	}
	//else
	//	session.invalidate();
}
boolean isOwnerView=false;
String uri=request.getAttribute("URI")==null?null:request.getAttribute("URI").toString();
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
ModelInfo mod=null;
if(request.getAttribute(ModelInfo.getClassName()) instanceof ModelInfo)
	mod=(ModelInfo)request.getAttribute(ModelInfo.getClassName());
if(mod==null)
	response.sendRedirect("/pub/error.jsp");
ArrayList<AppInfo> apps=null;
if(request.getAttribute("MOD_APPS") instanceof ArrayList<?>)
	apps=(ArrayList<AppInfo>)request.getAttribute("MOD_APPS");
if(request.getAttribute("OWNER_VIEW")!=null&&request.getAttribute("OWNER_VIEW").equals("1"))
	isOwnerView=true;
ArrayList<Attachment> modFile1=null;
ArrayList<Attachment> modFile2=null;
HashMap<String,ArrayList<Attachment>> modFile3=null;
if(request.getAttribute("MOD_FILES1") instanceof ArrayList<?>)
	modFile1=(ArrayList<Attachment>)request.getAttribute("MOD_FILES1");
if(request.getAttribute("MOD_FILES2") instanceof ArrayList<?>)
	modFile2=(ArrayList<Attachment>)request.getAttribute("MOD_FILES2");
if(request.getAttribute("MOD_FILES3") instanceof HashMap<?,?>)
	modFile3=(HashMap<String,ArrayList<Attachment>>)request.getAttribute("MOD_FILES3");
int Section1Limit=4;
int Section2Limit=6;
int Section3Limit=4;
if(request.getAttribute("MOD_SEC1_LIMIT")!=null)
{
	try
	{
		Section1Limit=Integer.parseInt(request.getAttribute("MOD_SEC1_LIMIT").toString());
	}
	catch(NumberFormatException ne)
	{
		Section1Limit=4;
	}
}
if(request.getAttribute("MOD_SEC2_LIMIT")!=null)
{
	try
	{
		Section2Limit=Integer.parseInt(request.getAttribute("MOD_SEC2_LIMIT").toString());
	}
	catch(NumberFormatException ne)
	{
		Section2Limit=6;
	}
}
if(request.getAttribute("MOD_SEC3_LIMIT")!=null)
{
	try
	{
		Section3Limit=Integer.parseInt(request.getAttribute("MOD_SEC3_LIMIT").toString());
	}
	catch(NumberFormatException ne)
	{
		Section3Limit=4;
	}
}
int app_id=0;
if(request.getAttribute("OWNER_APP_ID")!=null)
{
	try
	{
		app_id=Integer.parseInt(request.getAttribute("OWNER_APP_ID").toString());		
	}
	catch(NumberFormatException ne)
	{
		app_id=0;
	}
}
String empty=null;
//String approot=this.getServletContext().getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<% 
if(literals!=null)
	out.println(String.format("%s--%s",literals.getText("title"),mod.getName()));
else
	out.println("Model Detail");
%>
</title>
<script type="text/javascript" src="../../js/jquery1.8/jquery-1.4.2.js"></script>
<%if((isOwnerView&&app_id==0)||(app_id>0)){ %>
<script type="text/javascript" src="../../js/jquery1.8/jquery.ui.core.js"></script>
<script type="text/javascript" src="../../js/jquery1.8/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../../js/jquery1.8/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="../../js/jquery1.8/jquery.ui.position.js"></script>
<script type="text/javascript" src="../../js/common.js"></script>
<link type="text/css" href="../../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
<%
}
%>
<script type="text/javascript" src="../fancybox/jquery.fancybox-1.3.1.js"></script>
<link rel="stylesheet" type="text/css" href="../fancybox/jquery.fancybox-1.3.1.css" media="screen" />

<link href="../display.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var url_prefix='../..';
	var div_prefix='mod_div_sec';
	var interval_id=null;
	var framename=null;
	/*var hFileDone={
			onFileUploaded:function()//(id,url,desc,sid,ismod)
			{
				var cnt=$('#iframe1').contents.find('body').text();
				if(cnt!=null)
				{
					clearInterval();
					alert(cnt);
					return;
				}
				if(process_dialog!=null)
				{
					process_dialog.dialog('close');
					
				}
				if(id<0)
				{
					$('#mod_sec1_submit').attr("disabled", false);
					errorMessageBox("Failed to upload the file. Please try again later!"); 
				}
				if(ismod==1&&id>0&&url!=null&&sid>0)
				{
					var div=$('#'+div_prefix+sid);
					var table="";
					
						table='<div id="mod_sec'+sid+'_list_'+id+'"><table><tr><td rowspan="2">';
						table+='<a href="../..'+url+'" target="_blank">';
						if(sid==1||sid==3)
							table+='<img src="../..'+url+'" style="width:100px;height:100px;"/></a></td>';
						else if(sid==2)
							table+=desc+'</td>';
						table+='<td><input type="text" id="img_desc" name="img_desc" value="'+desc+'" size="50" class="textbox-app"/>';
						table+='<input type="hidden" id="img_id" name="img_id" value="'+id+'" /></td></tr><tr><td>';
						table+='<input type="button" value="Update Description" name="mod_sec'+sid+'_update_desc"/>';
						table+='<input type="button" value="Remove File" name="mod_sec'+sid+'_remove"/></td></tr></table><hr/></div>';
					
					
					div.append(table);
					if(sid==1)
					{
						$('[name=mod_sec1_update_desc]').click(onUpdateModSec1ImgDesc);
						$('[name=mod_sec1_remove]').click(onRemoveModSec1Img);
						++mod_sec1_count;
						if(mod_sec1_count==4)
							$('#mod_sec1_upload_panel').hide();
						$('#mod_sec1_submit').attr("disabled", false);
					}
					else if(sid==2)
					{
						$('[name=mod_sec2_update_desc]').click(onUpdateModSec2ImgDesc);
						$('[name=mod_sec2_remove]').click(onRemoveModSec2Img);
						++mod_sec2_count;
						if(mod_sec2_count==4)
							$('#mod_sec2_upload_panel').hide();
						$('#mod_sec2_submit').attr("disabled", false);
					}
					else if(sid==3)
					{
						$('[name=mod_sec3_update_desc]').click(onUpdateModSec3ImgDesc);
						$('[name=mod_sec3_remove]').click(onRemoveModSec3Img);
						++mod_sec3_count;
						if(mod_sec3_count==4)
							$('#mod_sec3_upload_panel').hide();
						$('#mod_sec3_submit').attr("disabled", false);
					}
					confirmMessageBox("The picture has been uploaded successfully!");
					
				}
			}
	};*/
	function onFileUploaded()//(id,url,desc,sid,ismod)
	{
		var cnt=$('#iframe1').contents().find('pre').text();
		if(cnt==null||cnt=='')
			cnt=$('#iframe1').contents().find('body').text();
		
		if(cnt==null||cnt=='')
		{
			return;
		}
		clearInterval(interval_id);
		var obj=$.parseJSON(cnt);
		var id=obj.id;
		var url=obj.url;
		var desc=obj.desc;
		var sid=obj.sid;
		var ismod=obj.ismod;
		if(process_dialog!=null)
		{
			process_dialog.dialog('close');
			
		}
		if(id<0)
		{
			$('#mod_sec1_submit').attr("disabled", false);
			errorMessageBox("Failed to upload the file. Please try again later!"); 
		}
		if(ismod==1&&id>0&&url!=null&&sid>0)
		{
			var div=$('#'+div_prefix+sid);
			var table="";
			
				table='<div id="mod_sec'+sid+'_list_'+id+'"><table><tr><td rowspan="2">';
				table+='<a href="../..'+url+'" target="_blank">';
				if(sid==1||sid==3)
					table+='<img src="../..'+url+'" style="width:100px;height:100px;"/></a></td>';
				else if(sid==2)
					table+=desc+'</td>';
				table+='<td><input type="text" id="img_desc" name="img_desc" value="'+desc+'" size="50" class="textbox-app"/>';
				table+='<input type="hidden" id="img_id" name="img_id" value="'+id+'" /></td></tr><tr><td>';
				table+='<input type="button" value="Update Description" name="mod_sec'+sid+'_update_desc"/>';
				table+='<input type="button" value="Remove File" name="mod_sec'+sid+'_remove"/></td></tr></table><hr/></div>';
			
			
			div.append(table);
			if(sid==1)
			{
				$('[name=mod_sec1_update_desc]').click(onUpdateModSec1ImgDesc);
				$('[name=mod_sec1_remove]').click(onRemoveModSec1Img);
				++mod_sec1_count;
				if(mod_sec1_count==4)
					$('#mod_sec1_upload_panel').hide();
				$('#mod_sec1_submit').attr("disabled", false);
			}
			else if(sid==2)
			{
				$('[name=mod_sec2_update_desc]').click(onUpdateModSec2ImgDesc);
				$('[name=mod_sec2_remove]').click(onRemoveModSec2Img);
				++mod_sec2_count;
				if(mod_sec2_count==4)
					$('#mod_sec2_upload_panel').hide();
				$('#mod_sec2_submit').attr("disabled", false);
			}
			else if(sid==3)
			{
				$('[name=mod_sec3_update_desc]').click(onUpdateModSec3ImgDesc);
				$('[name=mod_sec3_remove]').click(onRemoveModSec3Img);
				++mod_sec3_count;
				if(mod_sec3_count==4)
					$('#mod_sec3_upload_panel').hide();
				$('#mod_sec3_submit').attr("disabled", false);
			}
			confirmMessageBox("The picture has been uploaded successfully!");
			
		}
	}
	function onUpdateModSec1ImgDesc()
	{
		var tr=$(this).parent().parent().siblings()[0];
		var desc=$(tr).find('[name=img_desc]');
		var id=$(tr).find('[name=img_id]');
		if($.trim(desc.val())=="")
		{
			textBoxError(desc);
		}
		else
		{
			textBoxResume(desc);
			if(id.val()!="")
			{
				$(this).attr("disabled", true);
				$('[name=mod_sec1_remove]').attr("disabled", true);
				var btn=$(this);
				$.get("../../mod/fileadmin.glos",
						{
							ismod:1,
							file_desc:desc.val(),
							fid:id.val()
						},
						function(data)
						{
							if(process_dialog!=null)
								process_dialog.dialog('close');
							btn.attr("disabled", false);
							$('[name=mod_sec1_remove]').attr("disabled", false);
							if(data=='0')
								confirmMessageBox("The description has been changed successfully!");
							else
								errorMessageBox("Failed to change the description. Please try again later!"); 
						});
				process_dialog=$('#div_process');
				$('#div_process_prompt').html("Updating Description");
				process_dialog.dialog({
							modal:true,
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
				
				});
						
			}
		}
	}
	function onRemoveModSec1Img(){
		var tr=$(this).parent().parent().siblings()[0];
		var id=$(tr).find('[name=img_id]');
				
		if(id.val()!="")
		{
			$(this).attr("disabled", true);
			var btn=$(this);
			$.get("../../mod/fileadmin.glos",
					{
						ismod:1,
						fid:id.val()
					},
					function(data)
					{
						if(process_dialog!=null)
							process_dialog.dialog('close');
						btn.attr("disabled", false);
						if(data=='0')
						{
							var t=$('div#mod_sec1_list_'+id.val());
							t.remove();
							--mod_sec1_count;
							if(mod_sec1_count<4)
								$('#mod_sec1_upload_panel').show();
							confirmMessageBox("The picture has been removed successfully!");
						}
						else
							errorMessageBox("Failed to remove the file. Please try again later!"); 
					});
			process_dialog=$('#div_process');
			$('#div_process_prompt').html("Removing File");
			process_dialog.dialog({
						modal:true,
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
			
			});
		}
	}
	function onUpdateModSec2ImgDesc()
	{
		var tr=$(this).parent().parent().siblings()[0];
		var desc=$(tr).find('[name=img_desc]');
		var id=$(tr).find('[name=img_id]');
		if($.trim(desc.val())=="")
		{
			textBoxError(desc);
		}
		else
		{
			textBoxResume(desc);
			if(id.val()!="")
			{
				$(this).attr("disabled", true);
				$('[name=mod_sec2_remove]').attr("disabled", true);
				var btn=$(this);
				$.get("../../mod/fileadmin.glos",
						{
							ismod:1,
							file_desc:desc.val(),
							fid:id.val()
						},
						function(data)
						{
							if(process_dialog!=null)
								process_dialog.dialog('close');
							btn.attr("disabled", false);
							$('[name=mod_sec2_remove]').attr("disabled", false);
							if(data=='0')
								confirmMessageBox("The description has been changed successfully!");
							else
								errorMessageBox("Failed to change the description. Please try again later!"); 
						});
				process_dialog=$('#div_process');
				$('#div_process_prompt').html("Updating Description");
				process_dialog.dialog({
							modal:true,
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
				
				});
						
			}
		}
	}
	function onRemoveModSec2Img(){
		var tr=$(this).parent().parent().siblings()[0];
		var id=$(tr).find('[name=img_id]');
				
		if(id.val()!="")
		{
			$(this).attr("disabled", true);
			var btn=$(this);
			$.get("../../mod/fileadmin.glos",
					{
						ismod:1,
						fid:id.val()
					},
					function(data)
					{
						if(process_dialog!=null)
							process_dialog.dialog('close');
						btn.attr("disabled", false);
						if(data=='0')
						{
							var t=$('div#mod_sec2_list_'+id.val());
							t.remove();
							--mod_sec2_count;
							if(mod_sec2_count<4)
								$('#mod_sec2_upload_panel').show();
							confirmMessageBox("The picture has been removed successfully!");
						}
						else
							errorMessageBox("Failed to remove the file. Please try again later!"); 
					});
			process_dialog=$('#div_process');
			$('#div_process_prompt').html("Removing File");
			process_dialog.dialog({
						modal:true,
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
			
			});
		}
	}
	function onUpdateModSec3ImgDesc()
	{
		var tr=$(this).parent().parent().siblings()[0];
		var desc=$(tr).find('[name=img_desc]');
		var id=$(tr).find('[name=img_id]');
		if($.trim(desc.val())=="")
		{
			textBoxError(desc);
		}
		else
		{
			textBoxResume(desc);
			if(id.val()!="")
			{
				$(this).attr("disabled", true);
				$('[name=mod_sec3_remove]').attr("disabled", true);
				var btn=$(this);
				$.get("../../app/fileadmin.glos",
						{
							file_desc:desc.val(),
							fid:id.val()
						},
						function(data)
						{
							if(process_dialog!=null)
								process_dialog.dialog('close');
							btn.attr("disabled", false);
							$('[name=mod_sec3_remove]').attr("disabled", false);
							if(data=='0')
								confirmMessageBox("The description has been changed successfully!");
							else
								errorMessageBox("Failed to change the description. Please try again later!"); 
						});
				process_dialog=$('#div_process');
				$('#div_process_prompt').html("Updating Description");
				process_dialog.dialog({
							modal:true,
							open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
				
				});
						
			}
		}
	}
	function onRemoveModSec3Img(){
		var tr=$(this).parent().parent().siblings()[0];
		var id=$(tr).find('[name=img_id]');
				
		if(id.val()!="")
		{
			$(this).attr("disabled", true);
			var btn=$(this);
			$.get("../../app/fileadmin.glos",
					{
						fid:id.val()
					},
					function(data)
					{
						if(process_dialog!=null)
							process_dialog.dialog('close');
						btn.attr("disabled", false);
						if(data=='0')
						{
							var t=$('div#mod_sec3_list_'+id.val());
							t.remove();
							--mod_sec3_count;
							if(mod_sec3_count<4)
								$('#mod_sec3_upload_panel').show();
							confirmMessageBox("The picture has been removed successfully!");
						}
						else
							errorMessageBox("Failed to remove the file. Please try again later!"); 
					});
			process_dialog=$('#div_process');
			$('#div_process_prompt').html("Removing File");
			process_dialog.dialog({
						modal:true,
						open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
			
			});
		}
	}
	var process_dialog=null;
	$(document).ready(function() {
		$('#mod_sec1_submit').click(function(){
			var frm=$('#mod_sec1_frm');
			var desc=$('#img_desc_1').find('#img_desc');
			if(frm!=null&&desc!=null)
			{
				var result=true;
				if($('#mod_upload_sec1').val()=="")
				{
					textBoxError($("#mod_upload_sec1"));
					result=false;
				}
				else
					textBoxResume($("#mod_upload_sec1"));
				if($.trim(desc.val())=="")
				{
					textBoxError(desc);
					result=false;
				}
				else
					textBoxResume(desc);
				if(result==true)
				{
					$(this).attr("disabled", true);
					$('#mod_sec1_frm').submit();
					
					interval_id=setInterval("onFileUploaded()",2000);
					process_dialog=$('#div_process');
					$('#div_process_prompt').html("Uploading File");
					process_dialog.dialog({
								modal:true,
								open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
					
					});
				}
			}
			
		});

		if($('[name=mod_sec1_update_desc]').length>0)
			$('[name=mod_sec1_update_desc]').click(onUpdateModSec1ImgDesc);
		if($('[name=mod_sec1_remove]').length>0)
			$('[name=mod_sec1_remove]').click(onRemoveModSec1Img);

		$('#mod_sec2_submit').click(function(){
			var frm=$('#mod_sec2_frm');
			var desc=$('#img_desc_2').find('#img_desc');
			if(frm!=null&&desc!=null)
			{
				var result=true;
				if($('#mod_upload_sec2').val()=="")
				{
					textBoxError($("#mod_upload_sec2"));
					result=false;
				}
				else
					textBoxResume($("#mod_upload_sec2"));
				if($.trim(desc.val())=="")
				{
					textBoxError(desc);
					result=false;
				}
				else
					textBoxResume(desc);
				if(result==true)
				{
					$(this).attr("disabled", true);
					$('#mod_sec2_frm').submit();
					interval_id=setInterval("onFileUploaded()",2000);
					process_dialog=$('#div_process');
					$('#div_process_prompt').html("Uploading File");
					process_dialog.dialog({
								modal:true,
								open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
					
					});
				}
			}
			
		});
		if($('[name=mod_sec2_update_desc]').length>0)
			$('[name=mod_sec2_update_desc]').click(onUpdateModSec2ImgDesc);
		if($('[name=mod_sec2_remove]').length>0)
			$('[name=mod_sec2_remove]').click(onRemoveModSec2Img);

		$('#mod_sec3_submit').click(function(){
			var frm=$('#mod_sec3_frm');
			var desc=$('#img_desc_3').find('#img_desc');
			if(frm!=null&&desc!=null)
			{
				var result=true;
				if($('#mod_upload_sec3').val()=="")
				{
					textBoxError($("#mod_upload_sec3"));
					result=false;
				}
				else
					textBoxResume($("#mod_upload_sec3"));
				if($.trim(desc.val())=="")
				{
					textBoxError(desc);
					result=false;
				}
				else
					textBoxResume(desc);
				if(result==true)
				{
					$(this).attr("disabled", true);
					$('#mod_sec3_frm').submit();
					interval_id=setInterval("onFileUploaded()",2000);
					process_dialog=$('#div_process');
					$('#div_process_prompt').html("Uploading File");
					process_dialog.dialog({
								modal:true,
								open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
					
					});
				}
			}
			
		});
		if($('[name=mod_sec3_update_desc]').length>0)
			$('[name=mod_sec3_update_desc]').click(onUpdateModSec3ImgDesc);
		if($('[name=mod_sec3_remove]').length>0)
			$('[name=mod_sec3_remove]').click(onRemoveModSec3Img);
	});
</script>
</head>
<body>
<div style="display:none;" id="div_process" title="Please Wait">
<div style="margin-left:auto;margin-right:auto;" align="center">
<p id="div_process_prompt"></p>
<img src="../../img/circle-preloader.gif" />
</div>
</div>
<jsp:include page="messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<div id="wrapper">
	<div id="topbar" align="right" style="padding-right:4px">
<%
String url_param=null;
UserParam up=new UserParam();
if(usr==null)
{
	url_param=uri==null?"":"?"+up.getLastUrlParamName()+"="+uri;
%>
		<a href="../../pub/<%=url_param%>">Login</a>
<%
}
else
{
	url_param="";//uri==null?"":"&"+up.getLastUrlParamName()+"="+uri;
	if(isOwnerView)
	{
		if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
		{
%>
	  <a href="../../adm/modaudit.glos?mod_action=update&modid=<%=mod.getId() %>">Audit Model</a> |
<%
		}
		else
		{
%>
	   <a href="../../mod/modadmin.glos?mod_action=update&modid=<%=mod.getId() %>">Edit Model</a> | 
<%
		}
	}
%>
		<a href="../../pub/?logout<%=url_param%>">Logout</a>
<%
}
%>
	</div>
	<div id="header">
		<img src="../../img/glmibanner.png" alt="GLOS Model Inventory" style="width:400px;height:80px;margin-top:10px;"/>
		<!--  <img src="" alt="Your own logo" style="width:400px;height:80px;margin-top:10px;"/>-->
	</div>
	<div class="separator"></div>
	<div id="content">
		<h1>
			<%=HTMLUtilities.filterDisplay(mod.getName())%>
<%
if(mod.getAbbreviation()!=null)
	out.println(String.format("<span>(%s)</span>",mod.getAbbreviation()));
%>
		</h1>
		<div id="version"><span>Version: </span><%=HTMLUtilities.filterDisplay(mod.getVersionNo())%></div>
		<div id="developer">
			<div style="font-weight:bold;">Developers:</div>
<%
ArrayList<Contact> devs=mod.getDevelopers();
if(devs!=null)
{
for(Contact c:devs)
{
%>
				<a target="_blank" href="../people/<%=c.getId() %>"><%=HTMLUtilities.filterDisplay(c.getFirstName())+" "+HTMLUtilities.filterDisplay(c.getLastName()) %></a><br/>
<%
}
}
else
{
%>
				<span>N/A</span><br/>
<%
}
%>
			<div style="font-weight:bold;">Distributors:</div>
<%
devs=mod.getDistributors();
if(devs!=null)
{
for(Contact c:devs)
{
%>
				<a target="_blank" href="../people/<%=c.getId() %>"><%=HTMLUtilities.filterDisplay(c.getFirstName())+" "+HTMLUtilities.filterDisplay(c.getLastName()) %></a><br/>
<%
}
}
else
{
%>
				<span>N/A</span><br/>
<%
}
%>
			<div style="font-weight:bold;">Contact:</div>
<%
devs=mod.getContacts();
if(devs!=null)
{
for(Contact c:devs)
{
%>
				<a target="_blank" href="../people/<%=c.getId() %>"><%=HTMLUtilities.filterDisplay(c.getFirstName())+" "+HTMLUtilities.filterDisplay(c.getLastName()) %></a><br/>
<%
}
}
else
{
%>
				<span>N/A</span><br/>
<%
}
%>
		</div>
			<p style="margin-top:0;">
			<%=HTMLUtilities.filterDisplay(mod.getDescription())%>
			<%=mod.getUrl()==null?"":String.format("&nbsp;&nbsp;For more information, please visit:&nbsp;<a href='%s' target='_blank'>%s</a>",HTMLUtilities.filterDisplay(mod.getUrl()),HTMLUtilities.filterDisplay(mod.getUrl())) %>
			</p>
			
			
			
		<div>
<%
if(isOwnerView)
{
%>
	
	<iframe id="iframe1" src="about:blank" name="modfrmupload1" id="modfrmupload1" style="display:none;">
	</iframe>
	<iframe src="about:blank" name="modfrmupload2" id="modfrmupload2" style="display:none;">
	</iframe>
<%
}
%>
<%
int fcount=Section1Limit;
	if(modFile1!=null&&!modFile1.isEmpty())
    	fcount=Section1Limit-modFile1.size();
	if(isOwnerView&&app_id==0)
	{
%>
<script type="text/javascript">
	var mod_sec1_count=<%=Section1Limit-fcount%>;
</script>
	<div class="tip" style="font-weight:bold;clear:both;">
		You could upload up to <%=Section1Limit %> pictures for this model. File size can not exceed 10 MB. 
		We ONLY accept png, jpg, bmp, gif and tif formats.
		Simply follow 3 steps to upload the picture.
	</div><br/>

    <div id="mod_sec1_upload_panel" style="display:<%=fcount>0?"block":"none" %>;">
		<form id="mod_sec1_frm" action="../../mod/fileupload/<%=mod.getId()%>" method="post" enctype="multipart/form-data" target="modfrmupload1">
			<table>
				<tr>
					<td>
						<span style="font-weight:bold;">1. Locate Picture:</span>
					</td>
					<td>
						<input type="file" name="mod_upload_sec1" id="mod_upload_sec1" size="50" class="textbox-app"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						(You can NOT attach a file with the name containing '+' or '&amp;' character.) 
					</td>
				</tr>
				<tr>
					<td>
						<span style="font-weight:bold;">2. Describe Picture:</span>
					</td>
					<td id="img_desc_1">
						<input type="text" name="img_desc" id="img_desc" size="50" class="textbox-app"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						(Description can NOT be longer than 100 characters.)
					</td>
				</tr>
				<tr>
					<td>
						<span style="font-weight:bold;">3:</span>
					</td>
					<td>
						<input type="button" value="Upload Picture" style="font-weight:bold;" id="mod_sec1_submit"/>
					</td>
				</tr>
			</table>
			<input type="hidden" name="section_id" value="1"/>
			
		</form>
		<hr/>
	</div>
	<div id='mod_div_sec1'>
<%
		if(modFile1!=null)
		{
			
			for(Attachment att:modFile1)
			{
			
%>
  <div id="mod_sec1_list_<%=att.getId()%>"><table>
	<tr>
		<td rowspan="2">
			<a href="../..<%=att.getPath()%>" target="_blank"><img src="../..<%=att.getPath() %>" style="width:100px;height:100px;"/></a>
		</td>
		<td>
			
			<input type="text" id="img_desc" name="img_desc" value="<%=att.getDescription()==null?"":att.getDescription() %>" size="50" class="textbox-app"/>
			<input type="hidden" id="img_id" name="img_id" value="<%=att.getId() %>" />
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="Update Description" name="mod_sec1_update_desc"/>
			<input type="button" value="Remove File" name="mod_sec1_remove"/>
    	</td>
    </tr>
   </table><hr/>
   </div>
<%
			}
			
		}
%>
	</div>
<%
	}
	else//client view
	{
		
		if(modFile1!=null)
		{
			for(int i=0;i<modFile1.size();++i)
			{
%>
			<a id="mod_s1_img<%=i %>" href="../..<%=modFile1.get(i).getPath() %>" title="<%=String.format("%s<br/><a href='../..%s' target='_blank'>View Full Size Picture</a>",modFile1.get(i).getDescription(),modFile1.get(i).getPath()) %>">
				<img src="../..<%=modFile1.get(i).getPath() %>" style="width:180px;height:180px;"/>
			</a>
<script type="text/javascript">
$("a#mod_s1_img<%=i%>").fancybox({

	'autoScale':true,
	'titlePosition'	: 'inside'
	
});
</script>
<%			
			}
		}
	}

%>

		</div>

		<h2>Model Characteristics:</h2>
		<p style="margin-top:0;">
			<%=HTMLUtilities.filterDisplay(mod.getAttribute())%>
		</p>
		<h2>Data Requirement:</h2>
		<p style="margin-top:0;">
			<%=HTMLUtilities.filterDisplay(mod.getDataRequirement())%>
		</p>
		<h2>Skill Requirement/Skill Level:</h2>
		<p style="margin-top:0;">
			<%=mod.getSkillLevel()==null||mod.getSkillLevel().trim().equals("")?"N/A":HTMLUtilities.filterDisplay(mod.getSkillLevel())%>
		</p>
		<h2>Strength and Weakness:</h2>
		<p style="margin-top:0;">
			<%=mod.getStrength()==null||mod.getStrength().trim().equals("")?"N/A":HTMLUtilities.filterDisplay(mod.getStrength())%>
		</p>
		<h2>Note:</h2>
		<p style="margin-top:0;">
			<%=mod.getNote()==null||mod.getNote().trim().equals("")?"N/A":HTMLUtilities.filterDisplay(mod.getNote())%>
		</p>
		<h2>Reference List:</h2>
<%
fcount=Section2Limit;
if(modFile2!=null&&!modFile2.isEmpty())
	fcount=Section2Limit-modFile2.size();
if(isOwnerView&&app_id==0)
{
%>
<script type="text/javascript">
	var mod_sec2_count=<%=Section2Limit-fcount%>;
</script>
	<div class="tip" style="font-weight:bold;">
		You could upload up to <%=Section2Limit %> files as references. File size can not exceed 10 MB. 
		Simply follow 3 steps to upload the file.
	</div><br/>
	<div id="mod_sec1_upload_pane2" style="display:<%=fcount>0?"block":"none" %>;">
		<form id="mod_sec2_frm" action="../../mod/fileupload/<%=mod.getId()%>" method="post" enctype="multipart/form-data" target="modfrmupload1">
			<table>
				<tr>
					<td>
						<span style="font-weight:bold;">1. Locate File:</span>
					</td>
					<td>
						<input type="file" name="mod_upload_sec2" id="mod_upload_sec2" size="50" class="textbox-app"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						(You can NOT attach a file with the name containing '+' or '&amp;' character.) 
					</td>
				</tr>
				<tr>
					<td>
						<span style="font-weight:bold;">2. Tile of the File:</span>
					</td>
					<td id="img_desc_2">
						<input type="text" name="img_desc" id="img_desc" size="50" class="textbox-app"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						(Title can NOT be more than 100 characters.)
					</td>
				</tr>
				<tr>
					<td>
						<span style="font-weight:bold;">3:</span>
					</td>
					<td>
						<input type="button" value="Upload File" style="font-weight:bold;" id="mod_sec2_submit"/>
					</td>
				</tr>
			</table>
			<input type="hidden" name="section_id" value="2"/>
			
		</form>
		<hr/>
	</div>
	<div id="mod_div_sec2">
<%
		
	if(modFile2!=null)
	{
        for(Attachment att:modFile2)
		{
			
%>
  <div id="mod_sec2_list_<%=att.getId()%>"><table>
	<tr>
		<td rowspan="2">
			<a href="../..<%=att.getPath()%>" target="_blank"><%=att.getDescription()==null?"No Title":att.getDescription() %></a>
		</td>
		<td>
			
			<input type="text" id="img_desc" name="img_desc" value="<%=att.getDescription()==null?"":att.getDescription() %>" size="50" class="textbox-app"/>
			<input type="hidden" id="img_id" name="img_id" value="<%=att.getId() %>" />
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="Update Description" name="mod_sec2_update_desc"/>
			<input type="button" value="Remove File" name="mod_sec2_remove"/>
    	</td>
    </tr>
   </table><hr/>

	</div>	
<%
		}
	}
%>
	</div><!--mod_div_sec2  -->
<%
}
else
{

	if(modFile2!=null)
	{
		out.println("<ol>");
		for(int i=0;i<modFile2.size();++i)
		{
%>
			<li><a id="mod_s2_img<%=i %>" href="../..<%=modFile2.get(i).getPath() %>">
				<%=modFile2.get(i).getDescription() %>
			</a></li>

<%
		}
		out.println("</ol>");
	}
	else
		out.print("<p>N/A</p>");
}
%>
		<h2>Applications Using This Model:</h2>
		
<%
if(apps==null||apps.size()==0)
{
%>
	N/A
<%
}
else
{
	if(apps!=null&&!apps.isEmpty())
	{
		for(AppInfo app:apps)
		{
			String APP_ID=String.format("%s",app.getId());
%>
	<div id="div_app">
		<table>
			<tr align="left">
				<td style="width:120px;text-algin:" valign="top">
					<span style="font-weight:bold;">Application Name:</span>
				</td>
				<td>
					<%=app.getName()==null?"N/A": HTMLUtilities.filterDisplay(app.getName())%>
				</td>
			</tr>
			<tr align="left">
				<td style="width:120px;text-algin:" valign="top">
					<span style="font-weight:bold;">Application Website:</span>
				</td>
				<td>
					<%=app.getUrl()==null?"N/A": String.format("<a href='%s' target='_blank'>%s</a>",HTMLUtilities.filterDisplay(app.getUrl()),HTMLUtilities.filterDisplay(app.getUrl()))%>
				</td>
			</tr>
			<tr align="left">
				<td style="width:120px;text-algin:" valign="top">
					<span style="font-weight:bold;">Contact:</span>
				</td>
				<td>
<%
if(app.getContacts()!=null&&!app.getContacts().isEmpty())
{
	int id=0;
	String name=null;
	for(Contact c:app.getContacts())
	{
		id=c.getId();
		name=c.getFirstName()+" "+c.getLastName();
%>
					<a target="_blank" href='../people/<%=id %>'><%=HTMLUtilities.filterDisplay(name) %></a>
<%
	}
}
else
{
%>
					N/A
<%
}
%>
				</td>
			</tr>
			<tr align="left">
				<td valign="top">
					<span style="font-weight:bold;">Location:</span>
				</td>
				<td>
					<%=app.getLocation()==null?"N/A":HTMLUtilities.filterDisplay(app.getLocation()) %>
				</td>
			</tr>
			<tr align="left">
				<td valign="top">
					<span style="font-weight:bold;">Primary Purpose:</span>
				</td>
				<td>
					<%=app.getAppPurpose()==null?"N/A":HTMLUtilities.filterDisplay(app.getAppPurpose())%>
				</td>
			</tr>
			<tr align="left">
				<td valign="top">
					<span style="font-weight:bold;">Description:</span>
				</td>
				<td>
					<%=app.getAppDescription()==null?"N/A":HTMLUtilities.filterDisplay(app.getAppDescription()) %>
				</td>
			</tr>
			<tr align="left">
				<td valign="top">
					<span style="font-weight:bold;">Note:</span>
				</td>
				<td>
					<%=app.getAppNote()==null?"N/A":HTMLUtilities.filterDisplay(app.getAppNote()) %>
				</td>
			</tr>
		</table>
	</div>
	
<%			
			if(usr!=null&&app_id==app.getId())
			{
				fcount=Section3Limit;
				
				if(modFile3!=null&&!modFile3.isEmpty()&&modFile3.get(APP_ID)!=null&&!modFile3.get(APP_ID).isEmpty())
    				fcount=Section3Limit-modFile3.get(APP_ID).size();
%>
<script type="text/javascript">
var mod_sec3_count=<%=Section3Limit-fcount%>;
</script>
<iframe src="about:blank" name="modfrmupload3" id="modfrmupload3" style="display:none;">
</iframe>
<div class="tip" style="font-weight:bold;">
You could upload up to <%=Section3Limit %> pictures for the application above. File size can not exceed 10 MB. 
We ONLY accept png, jpg, bmp, gif and tif formats.
Simply follow 3 steps to upload the picture.
</div><br/>	
<div id="mod_sec3_upload_panel" style="display:<%=fcount>0?"block":"none" %>;">
<form id="mod_sec3_frm" action="../../app/fileupload/<%=app_id%>" method="post" enctype="multipart/form-data" target="modfrmupload1">
<table>
	<tr>
		<td>
			<span style="font-weight:bold;">1. Locate Picture:</span>
		</td>
		<td>
			<input type="file" name="mod_upload_sec3" id="mod_upload_sec3" size="50" class="textbox-app"/>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			(You can NOT attach a file with the name containing '+' or '&amp;' character.) 
		</td>
	</tr>
	<tr>
		<td>
			<span style="font-weight:bold;">2. Describe Picture:</span>
		</td>
		<td id="img_desc_3">
			<input type="text" name="img_desc" id="img_desc" size="50" class="textbox-app"/>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			(Description can NOT be longer than 100 characters.)
		</td>
	</tr>
	<tr>
		<td>
			<span style="font-weight:bold;">3:</span>
		</td>
		<td>
			<input type="button" value="Upload Picture" style="font-weight:bold;" id="mod_sec3_submit"/>
		</td>
	</tr>
</table>
<input type="hidden" name="section_id" value="3"/>

</form>
<hr/>
</div>
<div id='mod_div_sec3'>
<%
				if(fcount!=Section3Limit)
				{
					
					ArrayList<Attachment> atts=modFile3.get(APP_ID);
					for(Attachment att:atts)
					{
			
%>
  <div id="mod_sec3_list_<%=att.getId()%>"><table>
	<tr>
		<td rowspan="2">
			<a href="../..<%=att.getPath()%>" target="_blank"><img src="../..<%=att.getPath() %>" style="width:100px;height:100px;"/></a>
		</td>
		<td>
			
			<input type="text" id="img_desc" name="img_desc" value="<%=att.getDescription()==null?"":att.getDescription() %>" size="50" class="textbox-app"/>
			<input type="hidden" id="img_id" name="img_id" value="<%=att.getId() %>" />
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="Update Description" name="mod_sec3_update_desc"/>
			<input type="button" value="Remove File" name="mod_sec3_remove"/>
    	</td>
    </tr>
   </table><hr/>
   </div>
<%
					}
					
				}
%>
	</div>
<%
			}
			else//client view
			{
				
				if(modFile3!=null&&!modFile3.isEmpty()&&modFile3.get(APP_ID)!=null&&!modFile3.get(APP_ID).isEmpty())
				{
					ArrayList<Attachment> cas=modFile3.get(APP_ID);
					for(int i=0;i<cas.size();++i)
					{
%>
					<a id="mod_s3_img<%=i %>" href="../..<%=cas.get(i).getPath() %>" title="<%=String.format("%s<br/><a href='../..%s' target='_blank'>View Full Size Picture</a>",cas.get(i).getDescription(),cas.get(i).getPath()) %>">
						<img src="../..<%=cas.get(i).getPath() %>" style="width:180px;height:180px;"/>
					</a>
		<script type="text/javascript">
		$("a#mod_s3_img<%=i%>").fancybox({

			'autoScale':true,
			'titlePosition'	: 'inside'
			
		});
		</script>
<%		
					}
					out.println("<hr/>");
				}
				else
					out.println("<hr/>");
			}
		}
	}
}
%>
		
		<br/><br/>
		<h2 style="color:#cc6600;margin-top:4px;">Related Model:</h2>
		<div id="div_relmods">
		</div>
<script type="text/javascript">
$(function() {
	$.get("../quickmodsearch.glos",
      	  {
      	      <%=SearchResultParam.getSearchWordsParam()%>:'<%=mod.getName()%>',
      	      p:"1"
  	  	  },
         function(data){
  	  		var objs=$.parseJSON(data);
  	  		if(objs!=null)
  	  		{
  			var len=objs.length;
  			var i=0;
  			var table="<table style='width:100%;'>";
  			for(i=0;i<len;++i)
  			{
  				
      			table+="<tr>";
      				table+="<td><a href='"+objs[i].modid+"' target='_blank'>"+objs[i].modname+"</a></td>";
      			table+="</tr>";
      			
  			}
  			table+="</table>";
  			$("#div_relmods").html(table);
  	  		}
  	  	  });
});
</script>
	</div>
	<div class="separator"></div>
</div>
</body>
</html>
