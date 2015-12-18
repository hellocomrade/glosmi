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
<jsp:useBean id="apparam" scope="page" class="us.glos.mi.domain.AppAdminParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("apppendinglist.jsp")));
else
	out.println("Pending Application List");
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

	<link type="text/css" href="../js/jquery1.8/css/redmond/jquery.ui.css" rel="stylesheet" />
	<link href="./app.css" rel="stylesheet" type="text/css" />
	
	<link type="text/css" href="../js/jquery1.8/datatable/css/data_table_jui.css" rel="stylesheet" />
<script type="text/javascript">
	var selectedTr;
	var oTable;
<%if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
{	
%>
	function removeApp(url,id)
	{
		$("#confirm_div").dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$.get(
						url,
						{isajaxapp:'1'},
						function(text)
						{
							if(text=="0")
							{
								confirmMessageBox("Application is removed.");
								window.location="appadmin.glos?app_action=submit";
							}
							else
								errorMessageBox("Failed to remove selected application.");
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
}
%>
	function fnFormatDetails ( nTr,devid )
	{
		var aData = oTable.fnGetData( nTr );
		var id=$(nTr).children().find("img").siblings('span').html();
		var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		sOut += '<tr><td style="font-weight:bold;vertical-align:top;">Description:</td><td >'+aData[3]+'</td></tr>';
		sOut += '<tr><td style="font-weight:bold;vertical-align:top;">Contacts:</td><td >'+'<span id="'+devid+'"></span>'+'</td></tr>';
		sOut += '<tr><td colspan="2"><div style="height:8px;">&nbsp;</div></td></tr>';
		sOut += '<tr><td colspan="2">';
<%if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
{	
%>
		sOut +='<a href="../adm/appaudit.glos?app_action=audit&appdraftid='+id+'">Audit</a>&nbsp;&nbsp;<a href="#" onclick="removeApp(\'../adm/appaudit.glos?app_action=remove&appdraftid='+id+'\','+id+');return false;">Remove</a>';
<%
}
else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
{
%>
	sOut +='';
<%
}
%>
		sOut += '</td></tr>';
		sOut += '</table>';
		
		return sOut;
	}

	var dev_url="../pub/appchkdev.glos"
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
					
					$.ajax( {
						"url": dev_url+"?appid="+id,
						//"data": data,
						"success": function(data){
										$("#sp_mdev"+id).html(data);
						           },
						"dataType": "text",
						"cache": false,
						"error": function (XMLHttpRequest, textStatus, errorThrown) {
							errorMessageBox(textStatus);
						}
					} );
				}
			} );
		} );
	}
	function switch2Loc(time)
	{
		var value=time+' GMT';
        var result=Date.parse(value);
        return result.toString("yyyy-MM-dd hh:mm:ss tt");
	}
    $(document).ready(function() {
	    oTable=$('#pendingapplist').dataTable( {
		    "bProcessing": true,
		    "bServerSide": true,
		    "bFilter": false,
		    "bJQueryUI": true,
<%
if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
{
%>
		    "bStateSave": true,
<%
}
%>
		    "sPaginationType": "full_numbers",
		    "sAjaxSource": "./appadmin.glos?<%=apparam.getActionParamName() %>=submit",
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
						     "sWidth":'15%'
						 },
						 {    
						     "sWidth":'15%'
						 },
						 { 
							 "bVisible":false,
							 "sWidth":'0%'
					     },
						 {    
						     "sWidth":'20%',
						     "fnRender":function(oObj){
						     	return switch2Loc(oObj.aData[4]);
					     	 }
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
	    //oTable.fnSetColumnVis( 2, false);
	    
	    $("#pendingapplist tbody").click(function(event) {
	    	if(selectedTr!=null)
				selectedTr.removeClass('row_selected');
			selectedTr=$(event.target.parentNode);
			$(event.target.parentNode).addClass('row_selected');
	    });
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
<jsp:include page="/WEB-INF/controls/status-owner-widget.jsp" flush="true"/>

<div style="margin-left:auto;margin-right:auto;width:1080px;">
	<table cellpadding="0" cellspacing="0" border="0" id="pendingapplist" class="display">
	    <thead>
				<tr>
					
					<th >ID</th>
					<th >Application Name</th>
					<th >Model Name</th>
					<th>Description</th>
					<th >Last Update</th>
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
		<span id="con_del">Are you sure you want to remove this application?</span>
	</p>
</div>	

</body>
</html>