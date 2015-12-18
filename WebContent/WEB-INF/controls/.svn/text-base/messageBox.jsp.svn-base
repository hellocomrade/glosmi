<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!--  
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.dialog.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.position.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="../js/jquery1.8/jquery.ui.resizable.js"></script>

-->
<script>
function confirmMessageBox(msg)
{
	$("#__conMsg").html(msg);
	$("#<%=request.getParameter("cid") %>").dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
				}
			}
		});
		
}

function errorMessageBox(msg)
{
	$("#__errMsg").html(msg);
	$("#<%=request.getParameter("eid") %>").dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$(this).dialog('close');
				}
			}
		});
		
}
</script>
<div id="<%=request.getParameter("cid") %>" title="Confirm" style="display:none;">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		<span id="__conMsg"></span>
	</p>
</div>
<div id="<%=request.getParameter("eid") %>" title="Error" style="display:none;">
	<p>
		<span class="ui-icon ui-icon-circle-close" style="float:left; margin:0 7px 50px 0;"></span>
		<span id="__errMsg"></span>
	</p>
</div>
