<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/org.glc.tld" prefix="glc" %>
<%@ taglib uri="/WEB-INF/tlds/us.glos.mi.tld" prefix="glos" %>
<%@ page import="org.glc.domain.User" %>
<%@ page import="us.glos.mi.domain.UserAdminParam" %>
<%@ page import="org.glc.utils.HTMLUtilities" %>
<%@ page import="us.glos.mi.db.DBCache" %>
<%
User usr=null;
if(session.getAttribute(User.getClassName()) instanceof User)
	usr=(User)session.getAttribute(User.getClassName());

if(usr==null)
	return;
String empty="";

%>
<jsp:useBean id="usrap" scope="page" class="us.glos.mi.domain.UserAdminParam" />
<!--
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="../js/controls/user-widget.js"></script>
-->
<div id="dlg_update" title="Update My Profile" style="display:none;">
				    <table>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">First Name:</div>
				    			<input type="text" id="fname" name="<%=usrap.getFirstNameParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getFirstName()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Last Name:</div>
				    			<input type="text" id="lname" name="<%=usrap.getLastNameParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getLastName()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td>
				    			<div style="font-size:smaller;">Address1:</div>
				    			<input type="text" id="add1" name="<%=usrap.getStreet1ParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getAddress().getAddress1()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Address2:</div>
				    			<input type="text" id="add2" name="<%=usrap.getStreet2ParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getAddress().getAddress2()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    		    <div style="font-size:smaller;">City:</div>
				    			<input type="text" id="city" name="<%=usrap.getCityParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getAddress().getCity()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    	</tr>
				    	<tr>
				    	<%
				    		boolean isUS=true;
				    		boolean isCA=false;
				    		if(usr.getAddress()!=null&&usr.getAddress().getCountry()!=null)
				    		{
				    			isUS=usr.getAddress().getCountry().equals("us");
				    			isCA=usr.getAddress().getCountry().equals("ca");
				    		}
				    	%>
				    		<td>
				    		    <div style="font-size:smaller;">Country:</div>
				    			<select id="country" name="<%=usrap.getCountryParamName() %>" class="loginfont textbox-app">
				    				<option value="">----</option>
				    				<option value="us" <%if(isUS) out.print(" selected='selected' "); %>>United States</option>
				    				<option value="ca" <%if(isCA) out.print(" selected='selected' "); %>>Canada</option>
				    			</select>
				    		</td>
				    		<td>	
				    		    <div style="font-size:smaller;">State/Province:</div>
				    			<glc:statesselect id="state" name="<%=usrap.getStateParamName() %>" selected="<%=isUS?HTMLUtilities.filterDisplay(usr.getAddress().getState()):empty%>" cssClass="loginfont textbox-app" country="us" hide="<%=isCA %>"/>
				    			<glc:statesselect id="province" name="<%=usrap.getProvinceParamName() %>" selected="<%=isCA?HTMLUtilities.filterDisplay(usr.getAddress().getState()):empty %>" cssClass="loginfont textbox-app" country="ca" hide="<%=isUS %>" />
				    		</td>
				    		
				    	</tr>
				    	<tr>
				    		<td>
				    		    <div style="font-size:smaller;">Zip Code/Postal Code:</div>
				    			<input type="text" id="zip" name="<%=usrap.getZipcodeParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getAddress().getZipcode()) %>" class="loginfont textbox-app" size="25"/>
				    		</td>
				    		<td>
				    		    <div style="font-size:smaller;">Phone:</div>
				    			<input type="text" id="phone" name="<%=usrap.getPhoneParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getAddress().getPhone()) %>" class="loginfont textbox-app" size="25"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div style="font-size:smaller;">Organization:</div>
								<glos:agencydisplay keyValue="<%=DBCache.getAgencyNames() %>" id="org" name="<%=usrap.getOrgnizationIdParamName() %>" value="<%=HTMLUtilities.filterDisplay(usr.getOrgnization().getName()) %>" cssClass="ui-widget loginfont textbox-app" size="50"/>
							</td>
						</tr>
					</table>
					<input type="hidden" name="<%=usrap.getActionParamName() %>" value="update" />
					<input type="hidden" name="<%=usrap.getAjaxParamName() %>" value="1" />
		    	    <a href="#" onclick="showOrgRequestDialog();return false;" style="font-size:smaller;">What if my orgnization is not on the list?</a>
		    	    <br/><br/>
		    	    <div id="errmsg_u" style="color:red;font-size:smaller;">
		    		
		    	    </div>
				</div>
<script type="text/javascript">
	$("#country").change(function(){
		if('ca'==$("#country option:selected").val())
		{
			$("#state").hide();
			$("#province").show();
		}
		else
		{
			$("#province").hide();
			$("#state").show();
			
		}
	});
</script>