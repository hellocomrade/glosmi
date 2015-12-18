<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@ page import="us.glos.mi.UserPrivilegeMask" %>
<%@page import="org.glc.domain.User"%>
<%

String approot=this.getServletContext().getContextPath();
User usr=(User)session.getAttribute(User.getClassName());
ILiteralProvider literals=null;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("filelist.jsp")));
else
	out.println("File List");
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
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>
<%
	if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
	{
%>
	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="../mod/mod.css" rel="stylesheet" type="text/css" />
<%
	}
	else
	{
%>
	<link type="text/css" href="../js/jquery1.8/css/redmond/jquery.ui.css" rel="stylesheet" />
	<link href="../app/app.css" rel="stylesheet" type="text/css" />
<%
	}
%>	
	<link type="text/css" href="../js/jquery1.8/datatable/css/data_table_jui.css" rel="stylesheet" />
<script type="text/javascript">
var root_path="<%=this.getServletContext().getContextPath()%>";
var selectedTr=null;
function switch2Loc(time)
{
	var value=time+' GMT';
    var result=Date.parse(value);
    return result.toString("yyyy-MM-dd hh:mm:ss tt");
}
function removeFile(url,mod)
{
	$("#confirm_div").dialog({
		modal: true,
		buttons: {
			Ok: function() {
				$.get(
					url,
					{ismod:mod?"1":""},
					function(text)
					{
						if(text=="0")
						{
							confirmMessageBox("File is removed.");
							window.location="fileadmin.glos?islist=1";
						}
						else
							errorMessageBox("Failed to remove selected file.");
					}
				);
				$(this).dialog('close');
			},
			Cancel:function(){
				$(this).dialog('close');
			}
		}
	});
}
<%
if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
{
%>
	function checkFile(url)
	{
		$.get(
				url,
				function(text)
				{
					if(text=="0")
					{
						confirmMessageBox("File status has been changed.");
						window.location="fileadmin.glos?islist=1";
					}
					else
						errorMessageBox("Failed to change file status.");
						
					});
					
	}
<%
}
%>
function fnFormatDetails ( nTr,devid )
{
	var aData = oTable.fnGetData( nTr );
	selectedTr=nTr;
	
	var id=$(nTr).children().find("img").siblings('span').html();
	var ismod=(aData[1]==1);
	var rid=aData[2];
	var url=ismod?"../pub/model/"+rid:"../pub/app/"+rid;
	
	var sOut = '<br/><table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
	sOut+='<tr><td><a target="_blank" href="'+url+'">Go to Page</a>&nbsp;&nbsp;';
<%
if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
{
%>
	if(aData[6]=='Unchecked')
		sOut+='<a href="#" onclick="checkFile(\'../adm/fileadmin.glos?checked=1&fid='+id+'\');return false;">Mark as Checked</a>&nbsp;&nbsp;';
	else
		sOut+='<a href="#" onclick="checkFile(\'../adm/fileadmin.glos?checked=0&fid='+id+'\');return false;">Mark as Unchecked</a>&nbsp;&nbsp;';		
<%
}
%>
<%
if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
{
%>
	sOut+='<a href="#" onclick="removeFile(\'../mod/fileadmin.glos?fid='+id+'\','+ismod+');return false;">Remove</a>';
<%
}
else
{
%>
sOut+='<a href="#" onclick="removeFile(\'../app/fileadmin.glos?fid='+id+'\','+ismod+');return false;">Remove</a>';
<%
}
%>
	sOut += '</td></tr>';
	sOut += '</table><br/>';
	
	return sOut;
}
function fnOpenClose ( oSettings )
{
	$('td img', oTable.fnGetNodes() ).each( function () {
		$(this).click( function () {
			var nTr = this.parentNode.parentNode;
			if ( this.src.match('details_close') )
			{
				/* This row is already open - close it */
				this.src = "../js/jquery1.8/datatable/images/details_open.png";
				oTable.fnClose( nTr );
			}
			else
			{
				/* Open this row */
				this.src = "../js/jquery1.8/datatable/images/details_close.png";
				var id=$(nTr).children().find("img").siblings('span').html();
				oTable.fnOpen( nTr, fnFormatDetails(nTr,'sp_mdev'+id), 'details' );
				
				
			}
		} );
	} );
}
$(document).ready(function() {
    oTable=$('#filelist').dataTable( {
	    "bProcessing": true,
	    "bServerSide": true,
	    "bFilter": false,
	    "bJQueryUI": true,
	    "sPaginationType": "full_numbers",
	    "sAjaxSource": "./fileadmin.glos?islist=1",
	    "aaSorting": [[ 0, 'asc' ]],
	    "bAutoWidth":false,
	    "fnDrawCallback": fnOpenClose,
	    "aoColumns":[
				     {   
					     "fnRender": function ( oObj ) {
								       return "<img src='../js/jquery1.8/datatable/images/details_open.png' />&nbsp;"+'<span>'+oObj.aData[0] +'</span>';
					 				},
					     "sWidth":'7%' 
					 },
					 { 
						 "bVisible":false,
						 "sWidth":'0%'
				     },
				     { 
						 "bVisible":false,
						 "sWidth":'0%'
				     },
					 {    
					     "sWidth":'15%',
					     "fnRender":function(oObj){
					     	return "<a href='"+root_path+oObj.aData[4]+"' target='_blank'>"+oObj.aData[3]+"</a>";
				     	 }
					 },
					 
					 { 
						 "bVisible":false,
						 "sWidth":'0%'
				     },
				    
					 {    
					     "sWidth":'20%',
					     "fnRender":function(oObj){
					     	return switch2Loc(oObj.aData[5]);
				     	 }
					 },
					 {    
					     "sWidth":'15%'
					 },
<%
if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
{	
%>
					 {    
					     "sWidth":'18%'
					 },
					 {    
					     "sWidth":'15%'
					 }
<%
}else{
%>
					{    
						"sWidth":'0%',
						"bVisible": false
					},
					{    
						"sWidth":'0%',
						"bVisible": false
					}
<%
}
%>
	  		     ]
	    
	    
    } );
} );
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
	<jsp:param name="id" value="admin_dock"/>
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

<div style="margin-left:auto;margin-right:auto;width:1080px;">
	<table cellpadding="0" cellspacing="0" border="0" id="filelist" class="display">
	    <thead>
				<tr>
					
					<th >ID</th>
					<th >Type ID</th>
					<th >Reference ID</th>
					<th >Description</th>
					<th >URL</th>
					<th >Last Update</th>
					<th >Status</th>
					<th >Owner Email</th>
					<th >Owner Name</th>
				</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="8" class="dataTables_empty">Loading data......</td>
			</tr>
		</tbody>
	</table>
</div>

<div id="confirm_div" title="Confirm Delete" style="display:none;">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		<span id="con_del">Are you sure you want to remove this file?</span>
	</p>
</div>	

</body>
</html>