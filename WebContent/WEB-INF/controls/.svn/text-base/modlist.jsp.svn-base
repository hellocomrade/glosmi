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
<jsp:useBean id="modparam" scope="page" class="us.glos.mi.domain.ModAdminParam" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>
<%
if(literals!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("modlist.jsp")));
else
	out.println("Model List");
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

	<link type="text/css" href="../js/jquery1.8/css/black-tie/jquery.ui.css" rel="stylesheet" />
	<link href="./mod.css" rel="stylesheet" type="text/css" />
	
	<link type="text/css" href="../js/jquery1.8/datatable/css/data_table_jui.css" rel="stylesheet" />
<script type="text/javascript">
	var selectedTr;
	var oTable;
<%if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
{	
%>
	function removeModel(url,modid)
	{
		$("#confirm_div").dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$.get(
						url,
						{isajaxmod:'1'},
						function(text)
						{
							if(text=="0")
							{
								confirmMessageBox("Model is removed.");
								window.location="modadmin.glos?mod_action=list";
							}
							else
								errorMessageBox("Failed to remove selected model.");
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
		sOut += '<tr><td style="font-weight:bold;vertical-align:top;">Description:</td><td >'+aData[2]+'</td></tr>';
		sOut += '<tr><td style="font-weight:bold;vertical-align:top;">Developers:</td><td >'+'<span id="'+devid+'"></span>'+'</td></tr>';
		sOut += '<tr><td colspan="2"><div style="height:8px;">&nbsp;</div></td></tr>';
		sOut += '<tr><td colspan="2"><a href="../pub/model/'+id+'" target="_blank">Customize</a>&nbsp;&nbsp;';
<%if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
{	
%>
		sOut +='<a href="../adm/modaudit.glos?mod_action=update&modid='+id+'">Audit</a>&nbsp;&nbsp;<a href="#" onclick="admin_chgown(\'../adm/modaudit.glos?mod_action=changeowner&modid='+id+'\');return false;">Reassign Owner</a>&nbsp;&nbsp;<a href="#" onclick="admin_chg_status(\'../adm/modaudit.glos?mod_action=changestatus&modid='+id+'\','+id+');return false;">Status</a>&nbsp;&nbsp;<a href="#" onclick="removeModel(\'../adm/modaudit.glos?mod_action=remove&modid='+id+'\','+id+');return false;">Remove</a>';
<%
}
else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
{
%>
	sOut +='<a href="./modadmin.glos?mod_action=update&modid='+id+'">Edit</a>&nbsp;&nbsp;';
<%
}
%>
		sOut += '</td></tr>';
		sOut += '</table>';
		
		return sOut;
	}

	var dev_url="../pub/modchkdev.glos"
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
						"url": dev_url+"?modid="+id,
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
	    oTable=$('#modellist').dataTable( {
<%
if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
{
%>
		    "bStateSave": true,
<%
}
%>
		    "bProcessing": true,
		    "bServerSide": true,
		    "bFilter": false,
		    "bJQueryUI": true,
		    //"bStateSave": true,
		    "sPaginationType": "full_numbers",
		    "sAjaxSource": "./modadmin.glos?<%=modparam.getActionParamName() %>=list",
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
						     "sWidth":'30%'
						 },
						 { 
							 "bVisible": false,
							 "sWidth":'0%'
					     },
					     {    
						     "sWidth":'10%'
						 },
						 {    
						     "sWidth":'15%',
						     "fnRender":function(oObj){
						     	return switch2Loc(oObj.aData[4]);
					     	 }
						 },
						 {    
						     "sWidth":'5%'
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
	    
	    $("#modellist tbody").click(function(event) {
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
   
%>

<jsp:include page="/WEB-INF/controls/messageBox.jsp" flush="true">
	<jsp:param name="cid" value="div_confirm"/>
	<jsp:param name="eid" value="div_error"/>
</jsp:include>
<jsp:include page="/WEB-INF/controls/status-owner-widget.jsp" flush="true"/>

<div style="margin-left:auto;margin-right:auto;width:1080px;">
	<table cellpadding="0" cellspacing="0" border="0" id="modellist" class="display">
	    <thead>
				<tr>
					
					<th >ID</th>
					<!--  <th width="25%">Model Name</th>
					<th width="50%">Model Description</th>-->
					<th >Model Name</th>
					<th >Model Description</th>
					<th >Version</th>
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
		<span id="con_del">Are you sure you want to remove this model?</span>
	</p>
</div>	

</body>
</html>