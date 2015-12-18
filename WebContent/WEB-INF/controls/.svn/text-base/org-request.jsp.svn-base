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
String empty="";

%>
<jsp:useBean id="org" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<script type="text/javascript">
function validate_org()
{
	var result=true;
	var message="<div style='color:red;'>";
	if($.trim($("#oname").val())=="")
	{
		result=false;
		textBoxError($("#oname"));
		message+="Organization name can not be empty";
		message+="<br/>";
	}
	else
		textBoxResume($("#oname"));
	if($.trim($("#oabb").val())=="")
	{
		result=false;
		textBoxError($("#oabb"));
		message+="Organization abbreiviation can not be empty";
		message+="<br/>";
	}
	else
		textBoxResume($("#oabb"));
	if($.trim($("#odesc").val())=="")
	{
		result=false;
		textBoxError($("#odesc"));
		message+="Organization description can not be empty";
		message+="<br/>";
	}
	else if($.trim($("#odesc").val()).length>1000)
	{
		result=false;
		textBoxError($("#odesc"));
		message+="Organization description can not have more than 1000 characters";
		message+="<br/>";
	}
	else
		textBoxResume($("#odesc"));
	message+="</div>";
	$("#errmsg_org_req").html(message);
	return result;
}
function showOrgRequestDialog()
{
	$("#dlg_org_request").dialog({
    	height:450,
    	width:700,
    	//position:[500,150],
    	modal:true,
    	buttons:{
    				'Close':function(){$(this).dialog("close");},
    				'Send Request':function(){
    					    if(true==validate_org())
    					    {
    					        $.get('<%=request.getParameter("url")%>',
    					        		{
    					        			<%=org.getOrganizationNameParamName()%>:$.trim($("#oname").val()),
    					        			<%=org.getOrgnizationIdParamName()%>:$.trim($("#oabb").val()),
    					        			<%=org.getOrganizationDescriptionParamName()%>:$.trim($("#odesc").val())
    					        		},
                                    function(data){
                               			if(data=='0')
                               			{
                               				confirmMessageBox("Your request has been sent successfully!");
                               			}
                               			else
                               			{
                               				errorMessageBox("Failed to send your reuqest.");
                               				$("#dlg_org_request").dialog("open");
                               			}
                               			
                               			
                               	});
                               	$(this).dialog("close");
                            }
    					
    					
    				}
    			}
    });
}
</script>
<div id="dlg_org_request" title="Request to Add New Organization" style="display:none;">
					
				    <table>
				    	<tr>
				    		<td>
				    			Your organization is not on our list. That means you may not be able to submit the model/application. Please fill out the form below and send it to our administrator. We will process it and contact you with further information.
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Your Organization's Name:</div>
				    			<input type="text" id="oname" name="<%=org.getOrganizationNameParamName() %>" value="" class="loginfont textbox-app" size="35"/>
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Your preferred Organization Abbreviation:</div>
				    			<input type="text" id="oabb" name="<%=org.getOrgnizationIdParamName() %>" value="" class="loginfont textbox-app" size="35"/>
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Description of your Organization (No more than 1000 characters):</div>
				    			<textarea id="odesc" name="<%=org.getOrganizationDescriptionParamName()%>" class="textbox-app" rows="10" cols="90"></textarea>
				    		</td>
				    	</tr>
				    	
					</table>
							    	   
		    	    <div id="errmsg_org_req" style="color:red;font-size:smaller;">
		    		
		    	    </div>
</div>
