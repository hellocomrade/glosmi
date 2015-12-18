<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.domain.UserAdminParam" %>
<%
User usr=null;
if(session.getAttribute(User.getClassName()) instanceof User)
	usr=(User)session.getAttribute(User.getClassName());

if(usr==null)
	return;

 
%>
<jsp:useBean id="usrser" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<div id="dlg_contact_search" title="Search Contact" style="display:none;">
	<div style="width:90%">
	<table>
		<tr>
			<td>
				<div style="font-size:smaller;">First Name:</div>
				<input type="text" id="searchc_fname" value="" class="loginfont textbox-app" size="25"/>
			</td>
			<td>
			    <div style="font-size:smaller;">Last Name:</div>
		  		<input type="text" id="searchc_lname" value="" class="loginfont textbox-app" size="25"/>
			</td>
			<td>
				<div style="font-size:smaller;">Email:</div>
				<input type="text" id="searchc_email" value="" class="loginfont textbox-app" size="25"/>
			</td>
			
		</tr>
		<tr>
			<td colspan="3">
				<input type="button" value="Search" id="searchc_search"/>
			</td>
		</tr>
	</table>
	<hr/>
	<div id="cnt_search_result">
		
	</div>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("#searchc_search").click(function(){
		 if($.trim($("input#searchc_fname").val()).length>0
			&&$.trim($("input#searchc_lname").val()).length>0
			&&$.trim($("input#searchc_email").val()).length>0)
		 {
			 textBoxResume($("#searchc_fname"));
			 textBoxResume($("#searchc_lname"));
			 textBoxResume($("#searchc_email"));
			 $.get("../adm/contactsearch.glos",
		        	  {
		        	      <%=usrser.getFirstNameParamName()%>:$.trim($("#searchc_fname").val()),
		        	      <%=usrser.getLastNameParamName() %>:$.trim($("#searchc_lname").val()),
		        	      <%=usrser.getUserParamName() %>:$.trim($("#searchc_email").val())
	        	  	  },
                   function(data){
	                    var result="";
              			if(data=="-1")
              			{
                  			result="<span style='color:red;'>Not all three fields are filled.</span>";
              			}
              			else if(data=="-2")
              			{
                  			result="<span style='color:red;'>Email address is not valid.</span>";
              			}
              			else if(data=="0")
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
                      				table+="<td colspan='2'><span style='font-weight:bold;'>Email:</span>"+objs[i].usr+"</td>";
                      			table+="</tr>";
                      			table+="<tr>";
                  					table+="<td><span style='font-weight:bold;'>First Name:</span>"+objs[i].fname+"</td>";
                  					table+="<td><span style='font-weight:bold;'>Last Name:</span>"+objs[i].lname+"</td>";
                  				table+="</tr>";
                  				table+="<tr>";
              						table+="<td colspan='2'><span style='font-weight:bold;'>Organization:</span>"+objs[i].orgid+"</td>";
              					table+="</tr>";
              					table+="<tr>";
              						table+="<td><span style='font-weight:bold;'>Address 1:</span>"+objs[i].street1+"</td>";
              						table+="<td><span style='font-weight:bold;'>Address 2:</span>"+objs[i].street2+"</td>";
              					table+="</tr>";
              					table+="<tr>";
          							table+="<td colspan='2'><span style='font-weight:bold;'>City:</span>"+objs[i].city+"</td>";
          						table+="</tr>";
          						table+="<tr>";
          							table+="<td><span style='font-weight:bold;'>State/Province:</span>"+objs[i].state+"</td>";
          							table+="<td><span style='font-weight:bold;'>Country:</span>"+objs[i].country+"</td>";
          						table+="</tr>";
          						table+="<tr>";
          							table+="<td><span style='font-weight:bold;'>Zipcode/Postal Code:</span>"+objs[i].zip+"</td>";
          							table+="<td><span style='font-weight:bold;'>Phone:</span>"+objs[i].phone+"</td>";
          						table+="</tr>";
                  				table+="</table></td></tr>";
                  			}
                  			table+="</table>";
                  			result=table;
              			}
              			$("#cnt_search_result").html(result);
              			
              });
		 }
		 else
		 {
			 textBoxResume($("#searchc_fname"));
			 textBoxResume($("#searchc_lname"));
			 textBoxResume($("#searchc_email"));
			 if($.trim($("input#searchc_fname").val()).length==0)
				 textBoxError($("#searchc_fname"));
			 if($.trim($("input#searchc_lname").val()).length==0)
				 textBoxError($("#searchc_lname"));
			 if($.trim($("input#searchc_email").val()).length==0)
				 textBoxError($("#searchc_email"));
			 $("#cnt_search_result").html("<span style='color:red;'>Please fill in all three fields.</span>");
		 }
	});
	
});
function admin_contact_search()
{
	$("#dlg_contact_search").dialog({
    	height:300,
    	width:600,
    	modal:false,
    	position:'center',
    	buttons:{
    				'Close':function()
    				{
					    $("#searchc_fname").val("");
		 				$("#searchc_lname").val("");
		 				$("#searchc_email").val("");
		 				$("#cnt_search_result").html("");
		 				textBoxResume($("#searchc_fname"));
		 				textBoxResume($("#searchc_lname"));
		 				textBoxResume($("#searchc_email"));
						$(this).dialog("close");
						
					},
    				'Clear':function(){
    					    
						$("#searchc_fname").val("");
		 				$("#searchc_lname").val("");
		 				$("#searchc_email").val("");
		 				$("#cnt_search_result").html("");
		 				textBoxResume($("#searchc_fname"));
		 				textBoxResume($("#searchc_lname"));
		 				textBoxResume($("#searchc_email"));
    				}
    			}
    	});
}
</script>