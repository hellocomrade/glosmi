<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="org.glc.ILiteralProvider"%>
<%@page import="org.glc.utils.HTMLUtilities"%>
<%@page import="org.glc.domain.User"%>
<%@page import="org.glc.domain.Orgnization"%>
<%@page import="us.glos.mi.domain.ModelInfo"%>
<%@page import="us.glos.mi.domain.AppInfo"%>
<%@page import="us.glos.mi.domain.Contact"%>
<%
ILiteralProvider literals=null;
Orgnization org=null;
ArrayList<Contact> cnts=null;
ArrayList<ModelInfo> models=null;
ArrayList<AppInfo> apps=null;
String empty="";
String na="N/A";
if(request.getAttribute(ILiteralProvider.class.getName()) instanceof ILiteralProvider)
	literals=(ILiteralProvider)request.getAttribute(ILiteralProvider.class.getName());
if(request.getAttribute(Orgnization.class.getName()) instanceof Orgnization)
	org=(Orgnization)request.getAttribute(Orgnization.class.getName());
if(org==null)
	response.sendRedirect("/pub/error.jsp");
if(request.getAttribute("ORG_APP") instanceof ArrayList<?>)
	apps=(ArrayList<AppInfo>)request.getAttribute("ORG_APP");
if(request.getAttribute("ORG_MODEL") instanceof ArrayList<?>)
	models=(ArrayList<ModelInfo>)request.getAttribute("ORG_MODEL");
if(request.getAttribute("ORG_PEOPLE") instanceof ArrayList<?>)
	cnts=(ArrayList<Contact>)request.getAttribute("ORG_PEOPLE");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<% 
if(literals!=null)
	out.println(String.format("%s--%s",literals.getText("title"),org.getName()));
else
	out.println("Organization Information");
%>
</title>
<link href="../display.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="wrapper">
	<div id="topbar">

	</div>
	<div id="header">
		<img src="../../img/glmibanner.png" alt="GLOS Model Inventory" style="width:400px;height:80px;margin-top:10px;"/>
		
	</div>
	<div class="separator"></div>
	<div id="content">
		<h1><%=org.getName() %></h1><p>
			<span style="font-weight:bold;">Description:</span><%=org.getDescription()==null?na:HTMLUtilities.filterDisplay(org.getDescription())%>
		</p>
		<p>
			<span style="font-weight:bold;">Website:&nbsp;</span>
<%if(org.getUrl()==null){%>
			<span><%=na %></span>
<%}else{ %>
			<a href='<%=org.getUrl()==null?na:HTMLUtilities.filterDisplay(org.getUrl())%>' target='_blank'><%=org.getUrl()==null?na:HTMLUtilities.filterDisplay(org.getUrl())%></a>
<%} %>
		</p>
		<h2 style="color:#cc6600;margin-top:4px;">People:</h2>
<%
if(cnts!=null&&!cnts.isEmpty())
{
	out.println("<table style='width:100%;'>");
	for(Contact cnt:cnts)
	{
		out.println("<tr>");
		out.println(String.format("<td><a href='../people/%d' target='_blank'>%s %s</a></td>",cnt.getId(),cnt.getFirstName(),cnt.getLastName()));
		out.println("</tr>");
	}
	out.println("</table>");
}
else
{
	out.print(na);
}
%>
		<br/><br/>
		<h2 style="color:#cc6600;margin-top:4px;">Models:</h2>
<%
if(models!=null&&!models.isEmpty())
{
	out.println("<table style='width:100%;'>");
	for(ModelInfo mod:models)
	{
		out.println("<tr>");
		out.println(String.format("<td><a href='../model/%d' target='_blank'>%s</a></td>",mod.getId(),mod.getName()));
		out.println("</tr>");
	}
	out.println("</table>");
}
else
{
	out.print(na);
}
%>
		<br/><br/>
		<h2 style="color:#cc6600;margin-top:4px;">Applications:</h2>
<%
if(apps!=null&&!apps.isEmpty())
{
	out.println("<table style='width:100%;'>");
	for(AppInfo app:apps)
	{
		out.println("<tr>");
		out.println(String.format("<td><a href='../app/%d' target='_blank'>%s</a></td>",app.getId(),app.getName()));
		out.println("</tr>");
	}
	out.println("</table>");
}
else
{
	out.print(na);
}
%>
	</div>
	<div class="separator"></div>
</div>
</body>
</html>