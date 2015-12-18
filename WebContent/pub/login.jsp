<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.glc.domain.User" %>
<%
User usr=(User)session.getAttribute(User.getClassName());
String approot=this.getServletContext().getContextPath();
if(usr!=null)
{
	response.sendRedirect(approot+"/pub/login.glos");
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="us.glos.mi.domain.UserParam"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--  <meta http-equiv="Expires" content="-1">-->
<meta http-equiv="Pragma" content="no-cache">
<link href="./pub.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="../js/jquery1.8/css/ui-lightness/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery1.8/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.button.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<title>Login</title>
<script type="text/javascript">
	$(function(){
		$("#invite_a").button();
		$("#invite_a").click(function(){alert('a');return false;});
		
	});
	
	function validate()
	{
		var result=false;
		var u=document.getElementById('usr');
		var p=document.getElementById('passwd');
		var e=document.getElementById('errmsg');
		if(u.value.length==0)
		{
		    if(jscss('check',u,'textbox-app'))
		    	jscss('swap',u,'textbox-app','textbox-error');
		    result=false;
		}
		else if(false==validateEmail(u.value))
		{
			if(jscss('check',u,'textbox-app'))
				jscss('swap',u,'textbox-app','textbox-error');
		    result=false;
		}
		else
		{
			if(jscss('check',u,'textbox-error'))
				jscss('swap',u,'textbox-error','textbox-app');
			result=true;
		}
		
		if(p.value.length==0)
		{
			if(jscss('check',p,'textbox-app'))
		    	jscss('swap',p,'textbox-app','textbox-error');
		    result=false;
		}
		else
		{
			if(jscss('check',p,'textbox-error'))
				jscss('swap',p,'textbox-error','textbox-app');
			result=true;
		}
		
		if(result==false)
			e.style['visibility']='visible';
		else
			e.style['visibility']='hidden';
		return result;
	}
	
	function init()
	{
		if(getURLParam("error")>0)
		{
			var e=document.getElementById('errmsg');
			e.style['visibility']='visible';
		}
	}
</script>
</head>
<body bgcolor="#ffffff" onload="init();">
	<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center">
		<tbody>
			<!--  <tr valign="top">
				<td width="1%">
					<img height="80" width="90" border="0" align="left" src="" />
				</td>
				<td width="99%" valign="top" bgcolor="#ffffff">
					<table width="100%" cellpadding="1">
						<tbody>
							<tr valign="bottom">
								<td>
									<div align="right">&nbsp;</div>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" class="strip">
									Great Lakes Model Inventory Member Login
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>-->
			
		</tbody>
	</table>
	<table width="94%" cellspacing="1" cellpadding="5" align="center">
		<tbody>
			<tr>
				<td valign="top" style="text-align:left;">
					<b>Welcome to the Great Lakes Model Inventory hosted by Great Lakes Observing System</b>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="75%" valign="top">
					<p style="margin-bottom:0pt;text-align:left;">
						GLMI is blublublu...
					</p>
					<table width="90%" cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<td>
									<div>Image1</div>
								</td>
								<td>Feature 1 blublublu</td>
							</tr>
							<tr>
								<td>
									<div>Image2</div>
								</td>
								<td>Feature 2 blublublu</td>
							</tr>
							<tr>
								<td>
									<div>Image3</div>
								</td>
								<td>Feature 3 blublublu</td>
							</tr>
						</tbody>
					</table>
				</td>

				<td valign="top">

					<div id="login">

						<form id="form1" method="post" onsubmit="return validate();" action="<%out.println(approot+"/pub/login.glos"); %>">
							<table class="loginform" width="100%" cellspacing="3" cellpadding="5" border="0">
								<tbody>
									<tr>
										<td nowrap="nowrap" valign="top" bgcolor="#e8eefa" style="text-align:center;">
											<table cellspacing="0" cellpadding="1" border="0" align="center" style="text-align:center;">
												<tbody>
													<tr>
														<td align="center" colspan="2">
															<b class="loginfont">Sign in with your Email account.</b>
														</td>
													</tr>
													<tr style="visibility:hidden;" id="errmsg">
														<td align="center" colspan="2"><span style="color:#ff0000;">User name or password error</span></td>
													</tr>
													<!-- 
													<tr>
														<td align="center" colspan="2"><br/></td>
													</tr>
													-->
													<jsp:useBean id="usrp" scope="page" class="us.glos.mi.domain.UserParam" />
													<%
														Cookie[] cookies=request.getCookies();
														String up="";
														String pp="";
														if(cookies!=null)
														{
														    for(int i=0;i<cookies.length;++i)
														    {
															    if(cookies[i].getName().equals(usrp.getUserParamName()))
																    up=cookies[i].getValue();
															    else if(cookies[i].getName().equals(usrp.getPasswordParamName()))
																    pp=cookies[i].getValue();
														    }
														}
													%>
													<tr>
														<td nowrap="nowrap">
															<div align="right">
																<span style="font-size:smaller;">Username: </span>
															<div>
														</td>
														<td>
															<input id="usr" type="text" value='<%= up%>' size="18" name="<jsp:getProperty name="usrp" property="userParamName" />" class="loginfont textbox-app"/>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2"></td>
													</tr>
													<tr>
														<td nowrap="nowrap">
															<div align="right">
																<span style="font-size:smaller;">Password: </span>
															<div>
														</td>
														<td>
															<input id="passwd" type="password" value="<%= pp%>" size="18" name="<jsp:getProperty name="usrp" property="passwordParamName" />" class="loginfont textbox-app"/>
														</td>
													</tr>
													<tr>
														<td></td>
														<td align="left"></td>
													</tr>
													<tr>
														<td valign="top" align="right">
															<input id="pcookie" name="<jsp:getProperty name="usrp" property="rememberedParamName" />" type="checkbox" <%if(up!=null) out.println("checked"); %> />
														</td>
														<td align="left">
															<label class="loginfont">Remember me</label>
														</td>
													</tr>
													<tr>
														<td></td>
														<td align="left">
															<input type="submit" value="Sign in" name="signin" id="signin" class="loginfont"/>
														</td>
													</tr>
													<tr>
														<td align="center" colspan="2"><br/></td>
													</tr>
													<tr>
														<td align="center" colspan="2">
															<a href="#" class="loginfont">Forget your password?</a>
														</td>
													</tr>
												</tbody>
											</table>	
										</td>
									</tr>
								</tbody>
							</table>
						</form>

					</div>
					<br/>
					<table id="links" width="100%" cellpadding="0" bgcolor="#e8eefa" class="loginform">
						<tbody>
							<tr bgcolor="#e8eefa">
								<td valign="top">
									<div align="center" style="margin:10px 0pt;">
										<span class="loginfont">Want to join GLMI? It's free.</span>
									</div>
									<div align="center" style="margin:10px 0pt;">
									    <a id="invite_a" href="#" style="font-size:larger;">Join Now >></a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>

				</td>

			</tr>
		</tbody>
	</table>
	<br/>
	<table width="95%" cellspacing="0" cellpadding="0" bgcolor="#c3d9ff" align="center" style="margin-bottom:5px;">
		<tbody>
			<tr>
				<td>
					<div align="center" style="margin-top:4px;margin-bottom:4px;">
						<a class="loginfont" href="" target="_blank">Term</a>-
						<a class="loginfont" href="" target="_blank">Help</a>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>