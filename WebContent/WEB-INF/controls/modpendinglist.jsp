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
if(literals!=null&&literals.getText("modpendinglist.jsp")!=null)
	out.println(String.format("%s%s",literals.getText("title"),literals.getText("modpendinglist.jsp")));
else
	out.println("Pending Model List");
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
	var remove_url='../adm/modaudit.glos?mod_action=remove&draftid=';
	
	var usr_email='<%=usr.getEmail()%>';
	function remove(id)
	{
		var mid=parseInt(id);
		if(mid>0)
		{
			$("#confirm_div").dialog({
				modal: true,
				buttons: {
					Ok: function() {
						$.get(
							remove_url+mid,
							{isajaxmod:'1'},
							function(text)
							{
								if(text=="0")
								{
									confirmMessageBox("Pending model is removed.");
									window.location="modadmin.glos?mod_action=submit";
								}
								else
									errorMessageBox("Failed to remove selected draft.");
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
	}
	function switch2Loc(time)
	{
		var value=time+' GMT';
        var result=Date.parse(value);
        return result.toString("yyyy-MM-dd hh:mm:ss tt");
	}
	function fnFormatDetails ( nTr,devid )
	{
		var aData = oTable.fnGetData( nTr );
		var id=$(nTr).children().find("img").siblings('span').html();
		var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
		sOut += '<tr><td style="font-weight:bold;vertical-align:top;">Description:</td><td >'+aData[2]+'</td></tr>';
		sOut += '<tr><td colspan="2"><div style="height:8px;">&nbsp;</div></td></tr>';
<%if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())){%>		
			sOut += '<tr><td colspan="2"><a href="../adm/modaudit.glos?mod_action=audit&draftid='+id+'">Aduit</a>&nbsp;&nbsp;<a href="#" onclick="remove('+id+');return false;">Remove</a>';
<%}%>		
	
		sOut += '</tr>';
		sOut += '</table>';
		
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
					oTable.fnOpen( nTr, fnFormatDetails(nTr,'sp_mdev'), 'details' );
					var id=$(nTr).children().find("img").siblings('span').html();
					
				}
			} );
		} );
	}

    $(document).ready(function() {
	    oTable=$('#modellist').dataTable( {
		    "bProcessing": true,
		    "bServerSide": true,
		    "bFilter": false,
		    "bJQueryUI": true,
		    //"bStateSave": true,
		    "sPaginationType": "full_numbers",
		    "sAjaxSource": "./modadmin.glos?<%=modparam.getActionParamName()%>=submit",
		    "aaSorting": [[ 0, 'asc' ]],
		    "bAutoWidth":false,
		    "fnDrawCallback": fnOpenClose,
		    "aoColumns":[
					     {   
						     "fnRender": function ( oObj ) {
									       return "<img src='../js/jquery1.8/datatable/images/details_open.png' />&nbsp;"+'<span>'+oObj.aData[0] +'</span> ' ;
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
						     "sWidth":'15%',
						     "fnRender":function(oObj){
						     	return switch2Loc(oObj.aData[3]);
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
	
<div id="confirm_div" title="Confirm Delete" style="display:none;">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		<span id="con_del">Are you sure you want to remove this pending model?</span>
	</p>
</div>	
<div style="margin-left:auto;margin-right:auto;width:1080px;">

	<table cellpadding="0" cellspacing="0" border="0" id="modellist" class="display">
	    <thead>
				<tr>
					
					<th >ID</th>
					<!--  <th width="25%">Model Name</th>
					<th width="50%">Model Description</th>-->
					<th >Model Name</th>
					<th >Model Description</th>
					<th >Last Update</th>
					<th >Owner Email</th>
					<th >Owner Name</th>
				</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="6" class="dataTables_empty">Loading data......</td>
			</tr>
		</tbody>
	</table>
</div>

</body>
</html>