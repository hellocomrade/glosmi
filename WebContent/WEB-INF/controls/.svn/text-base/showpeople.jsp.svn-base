<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.utils.FIPSCodes"%>
<%@page import="org.glc.utils.Validation"%>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@ page import="us.glos.mi.domain.SearchResultParam" %>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@page import="us.glos.mi.domain.UserParam"%>
<%
ILiteralProvider literals=null;
User contact=null;
String mask="--";
String na="N/A";
boolean isShowDetail=false;
String uri=request.getAttribute("URI")==null?null:request.getAttribute("URI").toString();
if(session.getAttribute(User.getClassName()) instanceof User)
	isShowDetail=true;
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
if(request.getAttribute(User.getClassName()) instanceof User)
	contact=(User)request.getAttribute(User.getClassName());

if(contact==null)
	response.sendRedirect("../error.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<% 
if(literals!=null)
	out.println(String.format("%s--%s %s",literals.getText("title"),contact.getLastName(),contact.getFirstName()));
else
	out.println("Contact Information");
%>
</title>
<script type="text/javascript" src="../../js/jquery1.8/jquery-1.4.2.js"></script>

<link href="../display.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="wrapper">
	<div id="topbar">
<%
String url_param=null;
UserParam up=new UserParam();
if(isShowDetail==false)
{
	url_param=uri==null?"":"?"+up.getLastUrlParamName()+"="+uri;
%>
		<a href="../../pub/<%=url_param%>" style="float:right;margin-right:4px;">Login</a>
<%
}
else
{
%>
		<a href="../../pub/?logout" style="float:right;margin-right:4px;">Logout</a>
<%
}
%>
	</div>
	<div id="header">
		<img src="../../img/glmibanner.png" alt="GLOS Model Inventory" style="width:400px;height:80px;margin-top:10px;"/>
		
	</div>
	<div class="separator"></div>
	<div id="content">
		<h1>People's Profile</h1>
		<div class="profile">
			<table style="width:95%">
				<tr align="left" valign="top">
					<td rowspan="6" style="width:50px;">
						
						<img src="../../img/profile.png" alt="Profile Picture"/>
					</td>
					<td colspan="3">
						<span class="label">Name:&nbsp;</span><span><%=HTMLUtilities.filterDisplay(contact.getFirstName())%>&nbsp;<%=HTMLUtilities.filterDisplay(contact.getLastName())%></span>
					</td>
				</tr>
				<tr align="left" valign="top">
					<td colspan="3">
						<span class="label">Email:&nbsp;</span><span><%=isShowDetail?HTMLUtilities.filterDisplay(contact.getEmail()):mask %></span>
					</td>
				</tr>
				<tr align="left" valign="top">
					<td colspan="3">
						<span class="label">Affiliation:&nbsp;</span><span><%=contact.getOrgnization().getName()==null?na:String.format("<a href='../org/%d' target='_blank'>%s</a>",contact.getOrgnization().getId(),HTMLUtilities.filterDisplay(contact.getOrgnization().getName())) %></span>
					</td>
				</tr>
				</tr>
				<tr align="left" valign="top">
					<td style="width:31%;">
						<span class="label">Address1:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getAddress1()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getAddress1())):mask %></span>
					</td>
					<td style="width:31%;">
						<span class="label">Address2:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getAddress2()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getAddress2())):mask %></span>
					</td>
					<td style="width:31%;">
						<span class="label">City:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getCity()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getCity())):mask %></span>
					</td>
				</tr>
				<tr align="left" valign="top">
					<td>
<%
String state=isShowDetail?na:mask;
if(isShowDetail&&contact.getAddress().getState()!=null&&contact.getAddress().getCountry()!=null)
{
	if(Validation.isUS(contact.getAddress().getCountry()))
		state=FIPSCodes.US_State_FIPS.get(contact.getAddress().getState());
	else
		state=FIPSCodes.CA_Province_FIPS.get(contact.getAddress().getState());
}
%>
						<span class="label">State/Province:&nbsp;</span><span><%=state%></span>
					</td>
					<td>
						<span class="label">Country:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getCountry()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getCountry().toUpperCase())):mask %></span>
					</td>
					<td>
						<span class="label">Zipcode/Postal Code:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getZipcode()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getZipcode())):mask %></span>
					</td>
				</tr>
				<tr align="left" valign="top">
					<td>
						<span class="label">Phone:&nbsp;</span><span><%=isShowDetail?(contact.getAddress().getPhone()==null?na:HTMLUtilities.filterDisplay(contact.getAddress().getPhone())):mask %></span>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<br/><br/>
		<h2 style="color:#cc6600;margin-top:4px;">Related Model:</h2>
		<div id="div_relmods"></div>

		<br/>
		<h2 style="color:#cc6600;margin-top:4px;">Related Application:</h2>
		<div id="div_relapps"></div>
<script type="text/javascript">
$(function() {
	$.get("../quickmodsearch.glos",
      	  {
      	      p:"1",
      	      cid:'<%=contact.getId()%>'
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
      				table+="<td><a href='../model/"+objs[i].modid+"' target='_blank'>"+objs[i].modname+"</a></td>";
      			table+="</tr>";
      			
  			}
  			table+="</table>";
  			$("#div_relmods").html(table);
  	  		}
  	  	  });


	$.get("../appquicksearch.glos",
	      	  {
	      	      id:'<%=contact.getId()%>'
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
	      				table+="<td><a href='../app/"+objs[i].appid+"' target='_blank'>"+objs[i].appname+"</a></td>";
	      			table+="</tr>";
	      			
	  			}
	  			table+="</table>";
	  			$("#div_relapps").html(table);
	  	  		}
	  	  	  });
});
</script>
	</div>
	<div class="separator"></div>
</div>
</body>
</html>