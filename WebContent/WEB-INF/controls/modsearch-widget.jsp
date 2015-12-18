<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.domain.SearchResultParam" %>
<%
User usr=null;
if(session.getAttribute(User.getClassName()) instanceof User)
	usr=(User)session.getAttribute(User.getClassName());

if(usr==null)
	return;

 
%>

<div id="dlg_mod_search" title="Search Model By Name" style="display:none;">
	<div style="width:90%">
	<span style="font-size:smaller;font-weight:bold;">Enter Keywords:</span>
	<input type="text" id="search_mod_name" value="" class="loginfont textbox-app" size="50"/>
	<input type="button" value="Search" id="btn_search_mod"/>
	<br/><span style="font-size:smaller">Please select the model from the search result by clicking on the radio button.</span>
	<hr/>
	<div id="mod_search_result" style="width:100%;">
		
	</div>
	</div>
</div>

<script type="text/javascript">
var current_mod_id=-1;
var current_mod_name=null;
$(function() {
	$("#btn_search_mod").click(function(){
		 if($.trim($("input#search_mod_name").val()).length>0)
		 {
			 textBoxResume($("#search_mod_name"));
			
			 $.get("../app/appmodsearch.glos",
		        	  {
		        	      <%=SearchResultParam.getSearchWordsParam()%>:$.trim($("#search_mod_name").val())
	        	  	  },
                   function(data){
	                    var result="";
              			if(data=="-1")
              			{
                  			result="<span style='color:red;'>Not all three fields are filled.</span>";
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
                  				table+="<tr><td><table align='left' class='tip2' style='margin-bottom:6px;width:100%;'>";
                      			table+="<tr>";
                      				table+="<td><input type='radio' value="+objs[i].modid+" name='rd_mod_id'/><span style='font-weight:bold;font-size:12px;'>"+objs[i].modname+"</span></td>";
                      			table+="</tr>";
                      			table+="<tr>";
                  					table+="<td><span style='font-weight:bold;'>Version:</span>"+objs[i].modver+"</td>";
                  				table+="</tr>";
                  				table+="<tr>";
              						table+="<td ><span style='font-weight:bold;'>Description:</span>"+objs[i].moddesc+"</td>";
              					table+="</tr>";
              					table+="</table></td></tr>";
                  			}
                  			table+="</table>";
                  			result=table;
              			}
              			$("#mod_search_result").html(result);
              			$("#mod_search_result").find("[name=rd_mod_id]").click(function(){
              				current_mod_id=$(this).val();
              				current_mod_name=$(this).siblings('span').text();
              				if(modeltextbox!=null&&modelidbox!=null)
              				{
                  				$("#"+modeltextbox).val(current_mod_name);
                  				$("#"+modelvaluebox).val(current_mod_name);
                  				$("#"+modelidbox).val(current_mod_id);
              				}
              			});
              			
              });
		 }
		 else
		 {
			 if($.trim($("input#search_mod_name").val()).length==0)
				 textBoxError($("#search_mod_name"));
			 $("#mod_search_result").html("<span style='color:red;'>Please enter the keywords in the textbox.</span>");
		 }
	});
	
});
var modeltextbox=null;
var modelvaluebox=null;
var modelidbox=null;
function app_mod_search(modnameidfld,modelvaluefld,modidfld)
{
	modeltextbox=modnameidfld;
	modelvaluebox=modelvaluefld;
	modelidbox=modidfld;
	$("#dlg_mod_search").dialog({
    	height:400,
    	width:800,
    	modal:true,
    	position:'center',
    	buttons:{
    				'Close':function()
    				{
					    $("#search_mod_name").val("");
		 				
		 				$("#mod_search_result").html("");
		 				textBoxResume($("#search_mod_name"));
		 				
						$(this).dialog("close");
						
					},
    				'Clear':function(){
    					    
						$("#search_mod_name").val("");
		 				
		 				$("#mod_search_result").html("");
		 				textBoxResume($("#search_mod_name"));
		 				
    				}
    			}
    	});
}
</script>