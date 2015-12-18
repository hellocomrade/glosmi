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
<div id="dlg_org_update" title="Update Organization" style="display:none;">
	<div>
		<form id="frm_org_update" method="POST">
			<table>
				<tr>
				    <td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Name:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_name_u" name="adm_org_name_u" value="" class="loginfont textbox-app" size="50"/>
				    </td>
				    
				</tr>
				<tr>
				    <td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Abbreviation:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_abbrev_u" name="adm_org_abbrev_u"  value="" class="loginfont textbox-app" size="50"/>
				    </td>
				</tr>
				<tr>
					<td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Website:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_url_u" name="adm_org_url_u" value="" class="loginfont textbox-app" size="50"/>
				    </td>
				</tr>
				<tr>
					<td colspan="2">
						<span style="font-size:smaller;font-weight:bold;">Organization Description:</span>
						<br/>
						<textarea id="adm_org_desc_u" name="adm_org_desc_u" class="textbox-app" rows="10" cols="70"></textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" name="adm_org_id_u" id="adm_org_id_u" value=""/>
			<input type="hidden" name="adm_org_action" value="update"/>
		</form>
		<div id="err_org_update" style="color:red;"></div>
		<input type="button" value="Update" id="btn_update_org"/>
	</div>
</div>

    <div id="org_tabs" title="Agency Tool" style="display:none;">
	    <ul>
			<li><a href="#tab-search-org">Search Organization</a></li>
			<li><a href="#tab-list-org">Full Organization List</a></li>
			<li><a href="#tab-new-org">Add New Organization</a></li>
		</ul>
		<div id="tab-search-org">
			<div style="width:90%">
				<span style="font-size:smaller;font-weight:bold;">Enter Keywords:</span>
				<input type="text" id="search_org_key" value="" class="loginfont textbox-app" size="50"/>
				<input type="button" value="Search" id="btn_search_org"/>
				<hr/>
				<div id="org_search_result" style="width:100%;">
		
				</div>
			</div>
		</div>
		<div id="tab-list-org">
			<div style="width:90%">
				<div id="org_list" style="width:100%;overflow:auto;">
				</div>
			</div>
		</div>
		<div id="tab-new-org">
			<div style="width:90%">
				<table>
				<tr>
				    <td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Name:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_name" value="" class="loginfont textbox-app" size="50"/>
				    </td>
				    
				</tr>
				<tr>
				    <td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Abbreviation:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_abbrev" value="" class="loginfont textbox-app" size="50"/>
				    </td>
				</tr>
				<tr>
					<td>
					    <span style="font-size:smaller;font-weight:bold;">Organization Website:</span>
					</td>
					<td>
					    <input type="text" id="adm_org_url" value="" class="loginfont textbox-app" size="50"/>
				    </td>
				</tr>
				<tr>
					<td colspan="2">
						<span style="font-size:smaller;font-weight:bold;">Organization Description:</span>
						<br/>
						<textarea id="adm_org_desc" class="textbox-app" rows="10" cols="70"></textarea>
					</td>
				</tr>
				</table>
				<div id="err_org_add" style="color:red;"></div>
				<input type="button" value="Add" id="btn_add_org"/>
			</div>
		</div>
	</div>

<script type="text/javascript">
var cur_update_id=0;
function admin_org_dialog_cleanup()
{
	$("#search_org_key").val("");
	$("#adm_org_name").val("");
	$("#adm_org_abbrev").val("");
	$("#adm_org_desc").val("");
	$("#adm_org_url").val("");
	$("#org_search_result").html("");
	textBoxResume($("#adm_org_name_u"));
	textBoxResume($("#adm_org_abbrev_u"));
	$('#err_org_update').html("");
	textBoxResume($("#adm_org_name"));
	textBoxResume($("#adm_org_abbrev"));
	$('#err_org_add').html('');
	textBoxResume($("#search_org_key"));
}
function addUpdateEvent()
{
	$('.adm_org_update_a').click(function(){
		cur_update_id=($(this).siblings().val());
		if(cur_update_id>0)
		{
			$('#adm_org_name_u').val('');
			$('#adm_org_abbrev_u').val('');
			$('#adm_org_url_u').val('');
			$('#adm_org_desc_u').val('');
			$.get("../adm/orgopt.glos",
	              	  {action:"get",oid:cur_update_id},
	              	  function(data){
						if(data!='')
						{
							var obj=$.parseJSON(data);
							$('#adm_org_name_u').val(obj.name);
							$('#adm_org_abbrev_u').val(obj.abbrev);
							$('#adm_org_url_u').val(obj.url);
							$('#adm_org_desc_u').val(obj.desc);
							
						}
		          });
			$('#dlg_org_update').dialog({
				height:500,
				width:650,
				modal:true,
				buttons:{
							'Close':function(){$(this).dialog("close");}
							
						}
							
			});
		}
		else
			cur_update_id=0;
	});
}


var admin_org_tab=null;
function admin_org_tool()
{
admin_org_dialog_cleanup();
initTab();
//$("#dlg_org_tool")
admin_org_tab.dialog({
	height:540,
	width:650,
	modal:false,
	buttons:{
				'Close':function(){$(this).dialog("close");},
				'Clear':function(){
				    
					admin_org_dialog_cleanup();
	 				
				}
			}
				
});
}

function initTab()
{
admin_org_tab=$("#org_tabs").tabs({
	show:function(event,ui)
    {
        if('1'==ui.index)
        {
        	$.get("../adm/orgopt.glos",
              	  {action:"list"},
                    function(data){
                         var result="";
               			if(data=="0")
               			{
                   			result="<span style='font-weight:bold;'>No record returned.</span>";
               			}
               			else
               			{
                   			var objs=$.parseJSON(data);
                   			var len=objs.length;
                   			var i=0;
                   			var table="<table style='width:100%;'>";
                   			for(i=0;i<len;++i)
                   			{
                   				table+="<tr><td><table align='left' class='tip' style='margin-bottom:6px;width:100%;'>";
                       			
                       			table+="<tr>";
                   					table+="<td><span style='font-weight:bold;'>Name:</span>"+objs[i].name+"</td>";
                   				table+="</tr>";
                   				table+="<tr>";
               						table+="<td ><span style='font-weight:bold;'>Abbreviation:</span>"+objs[i].abbrev+"</td>";
               					table+="</tr>";
               					table+="<tr>";
           							table+="<td colspan='2'><a href='#' class='adm_org_update_a' >Update</a><input type='hidden' class='oid' value='"+objs[i].id+"'/></td>";
           						table+="</tr>";
               					table+="</table></td></tr>";
                   			}
                   			table+="</table>";
                   			result=table;
               			}
               			$("#org_list").html(result);
               			addUpdateEvent();
               			
               });
      	 
        }
        
    }


});

$("#btn_update_org").click(function(){
	if($.trim($("input#adm_org_name_u").val()).length>0&&$.trim($("input#adm_org_abbrev_u").val()).length>0)
	{
		textBoxResume($("#adm_org_name_u"));
		textBoxResume($("#adm_org_abbrev_u"));
		$("#adm_org_id_u").val(cur_update_id);
		$('#err_org_update').html("");
		$.post("../adm/orgopt.glos",
				  //$("#dlg_org_update").serialize(),
				  {
		             action:"update",
		             oid:cur_update_id,
		             name:$.trim($("input#adm_org_name_u").val()),
	        	     abbrev:$.trim($("input#adm_org_abbrev_u").val()),
	        	     desc:$.trim($("#adm_org_desc_u").val()),
	        	     url:$.trim($("input#adm_org_url_u").val())
		          },
				  function(data){
					  if(data=="0")
					  {
						  confirmMessageBox("The organization has been successfully updated.");
					  }
					  else if(data=="1")
					  {
						  errorMessageBox("Failed to update the organization.");
					  }
					  else if(data=="2")
					  {
						  textBoxError($("#adm_org_name_u"));
						  $('#err_org_update').html('<div>Organization name has been used.</div>')
					  }
					  else if(data=="3")
					  {
						  textBoxError($("#adm_org_abbrev_u"));
						  $('#err_org_update').html('<div>Organization abbreviation has been used.</div>');
					  }
				  }

				);
	}
	else
	{
		var err="";
		if($.trim($("input#adm_org_name_u").val()).length==0)
		{
			textBoxError($("#adm_org_name_u"));
			err="<div>Organization name can not be empty</div>";
		}
		else
			textBoxResume($("#adm_org_name_u"));
		if($.trim($("input#adm_org_abbrev_u").val()).length==0)
		{
			textBoxError($("#adm_org_abbrev_u"));
			err=err+"<div>Organization abbreviation can not be empty</div>";
		}
		else
			textBoxResume($("#adm_org_abbrev_u"));
		$('#err_org_update').html(err);
	}
	
});

$("#btn_add_org").click(function(){
	if($.trim($("input#adm_org_name").val()).length>0&&$.trim($("input#adm_org_abbrev").val()).length>0)
	{
		textBoxResume($("#adm_org_name"));
		textBoxResume($("#adm_org_abbrev"));
		$('#err_org_add').html('');
		$.post("../adm/orgopt.glos",

				  {
		             action:"add",
		             name:$.trim($("input#adm_org_name").val()),
	        	     abbrev:$.trim($("input#adm_org_abbrev").val()),
	        	     desc:$.trim($("#adm_org_desc").val()),
	        	     url:$.trim($("input#adm_org_url").val())
		          },
				  function(data){
					  if(data=="0")
					  {
						  confirmMessageBox("The organization has been successfully added.");
					  }
					  else if(data=="1")
					  {
						  errorMessageBox("Failed to add organization.");
					  }
					  else if(data=="2")
					  {
						  textBoxError($("#adm_org_name"));
						  $('#err_org_add').html('<div>Organization name has been used.</div>')
					  }
					  else if(data=="3")
					  {
						  textBoxError($("#adm_org_abbrev"));
						  $('#err_org_add').html('<div>Organization abbreviation has been used.</div>');
					  }
				  }

				);
	}
	else
	{
		var err="";
		if($.trim($("input#adm_org_name").val()).length==0)
		{
			textBoxError($("#adm_org_name"));
			err="<div>Organization name can not be empty</div>";
		}
		else
			textBoxResume($("#adm_org_name"));
		if($.trim($("input#adm_org_abbrev").val()).length==0)
		{
			textBoxError($("#adm_org_abbrev"));
			err=err+"<div>Organization abbreviation can not be empty</div>";
		}
		else
			textBoxResume($("#adm_org_abbrev"));
		$('#err_org_add').html(err);
	}
});

$("#btn_search_org").click(function(){
	 if($.trim($("input#search_org_key").val()).length>0)
	 {
		 textBoxResume($("#search_org_key"));
		
		 $.get("../adm/orgopt.glos",
        	  {
				action:"search",
   	      		key:$.trim($("#search_org_key").val())
       	  	  },
              function(data){
                   var result="";
         			if(data=="0")
         			{
             			result="<span style='font-weight:bold;'>No record returned.</span>";
         			}
         			else
         			{
             			var objs=$.parseJSON(data);
             			var len=objs.length;
             			var i=0;
             			var table="<table style='width:100%;'>";
             			for(i=0;i<len;++i)
             			{
             				table+="<tr><td><table align='left' class='tip' style='margin-bottom:6px;width:100%;'>";
                 			
                 			table+="<tr>";
             					table+="<td><span style='font-weight:bold;'>Name:</span>"+objs[i].name+"</td>";
             				table+="</tr>";
             				table+="<tr>";
         						table+="<td ><span style='font-weight:bold;'>Abbreviation:</span>"+objs[i].abbrev+"</td>";
         					table+="</tr>";
         					table+="<tr>";
     							table+="<td ><span style='font-weight:bold;'>Website:</span>"+objs[i].url+"</td>";
     						table+="</tr>";
     						table+="<tr>";
     							table+="<td ><span style='font-weight:bold;'>Description:</span>"+objs[i].desc+"</td>";
     						table+="</tr>";
     						table+="<tr>";
   								table+="<td colspan='2'><a href='#' class='adm_org_update_a'>Update</a><input type='hidden' class='oid' value='"+objs[i].id+"'/></td>";
   							table+="</tr>";
         					table+="</table></td></tr>";
             			}
             			table+="</table>";
             			result=table;
         			}
         			$("#org_search_result").html(result);
         			addUpdateEvent();
         			
         });
	 }
	 else
	 {
		 if($.trim($("input#search_org_key").val()).length==0)
			 textBoxError($("#search_org_key"));
		 $("#org_search_result").html("<span style='color:red;'>Please enter the keyword in the textbox.</span>");
	 }
});
}
</script>