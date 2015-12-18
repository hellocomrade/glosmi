function validate_Passwd()
{
	var result=true;
	var op=document.getElementById('oldpsd');
	var p1=document.getElementById('newpsd');
	var p2=document.getElementById('renewpsd');
	var e=document.getElementById('errmsg');
	var msg="";
	if(op.value.length==0)
	{
	    if(jscss('check',op,'textbox-app'))
	    {
	    	jscss('swap',op,'textbox-app','textbox-error');
	    	
	    }
	    msg+="<div>Please type your old password.</div>";
	    result=false;
	}
	else
	{
			if(jscss('check',op,'textbox-error'))
				jscss('swap',op,'textbox-error','textbox-app');
			
	}
	if(p1.value.length==0)
	{
	    if(jscss('check',p1,'textbox-app'))
	    {
	    	jscss('swap',p1,'textbox-app','textbox-error');
	    	
	    }
	    msg+="<div>Please type your new password.</div>";
	    result=false;
	}
	else
	{
		if(jscss('check',p1,'textbox-error'))
			jscss('swap',p1,'textbox-error','textbox-app');
		
	}
	if(p2.value.length==0)
	{
	    if(jscss('check',p2,'textbox-app'))
	    {
	    	jscss('swap',p2,'textbox-app','textbox-error');
	    	
	    	
	    }
	    msg+="<div>Please re-type your new password.</div>";
	    result=false;
    }
    else
	{
		if(jscss('check',p2,'textbox-error'))
			jscss('swap',p2,'textbox-error','textbox-app');
		
	}
    if(result==true)
	{
	    if(p1.value!=p2.value)
	    {
	        if(jscss('check',p1,'textbox-app'))
	        	jscss('swap',p1,'textbox-app','textbox-error');
	        if(jscss('check',p2,'textbox-app'))
	        	jscss('swap',p2,'textbox-app','textbox-error');
	        msg+="<div>The new passwords don't match.</div>";
	    	result=false;
	    }
	    else
	    {
	        if(jscss('check',p1,'textbox-error'))
				jscss('swap',p1,'textbox-error','textbox-app');
	        if(jscss('check',p2,'textbox-error'))
				jscss('swap',p2,'textbox-error','textbox-app');
			
	    }
	    
	    
	}
	if(result==true)
	{
	    if(op.value==p1.value)
	    {
	        msg+="<div>The old and new password can not be identical.</div>";
	        result=false;
	    }
	    
	}
	e.innerHTML="";
	if(result==false)
	    e.innerHTML=msg;
	return result;
}

function validate_Update()
{
	var result=true;
	var msg="";
	if($("#fname").val().length==0)
	{
		textBoxError($("#fname"));
		msg+="<div>First Name can not be empty.</div>";
		result=false;
	}
	else
		textBoxResume($("#fname"));
	if($("#lname").val().length==0)
	{
		textBoxError($("#lname"));
		msg+="<div>Last Name can not be empty.</div>";
		result=false;
	}
	else
		textBoxResume($("#lname"));
	if($("#country").val().length==0&&($("#state").val().length>0||$("#province").val().length>0))
	{
		textBoxError($("#country"));
		msg+="<div>Country name is not specified</div>";
		result=false;
	}
	else
		textBoxResume($("#country"));
	if($("#zip").val().length>0)
	{
		if($("#country option:selected").val()=='ca'&&validateCAPostalcode($("#zip").val())==false)
		{
			textBoxError($("#zip"));
			msg+="<div>Postal code is not valid.</div>";
			result=false;
		}
		else if($("#country option:selected").val()=='us'&&validateUSZipcode($("#zip").val())==false)
		{
			textBoxError($("#zip"));
			msg+="<div>Zipcode is not valid.</div>";
			result=false;
		}
		else
			textBoxResume($("#zip"));
	}
	else
		textBoxResume($("#zip"));
	if($("#phone").val().length>0&&validatePhone($("#phone").val())==false)
	{
		
		textBoxError($("#phone"));
		msg+="<div>Phone number is not valid.</div>";
		result=false;
	}
	else
		textBoxResume($("#phone"));	
	if(result==false)
		$("#errmsg_u").html("<br/>"+msg);
	else
		$("#errmsg_u").html("");
	return result;
}

function Dlg_Passwd()
{
    //if($("#dlg_passwd").length==0)
    //    $("#div_addon").append("");
    
    $("#dlg_passwd").dialog({
    	height:300,
    	width:300,
    	modal:true,
    	position:'center',
    	buttons:{
    				'Cancel':function(){$(this).dialog("close");},
    				'OK':function(){
    					    if(true==validate_Passwd())
    					    {
    					        //$(this).dialog("close");
    					        //var done = $('.ui-dialog-buttonpane').find('button:first');
    					        //var btns=$('.selector').dialog('option', 'buttons');
    					        
    					        /*var done=$(":button:contains('OK')");
    					        done.addClass('ui-state-disabled');
								done.attr('disabled', 'disabled');*/
								$(this).dialog("close");
    					        $.post($("#frm_chgp").attr('action'),$("#frm_chgp").serialize(),
                                    function(data){
                               			//if(data=='0')
                               			//{alert(data);
                               			//    done.attr("value","Done");
                               			    //done.attr('disabled', '');
                               			    //done.removeClass('ui-state-disabled');
                               			    
                               			//}
                               			if(data==0)
                               				confirmMessageBox("Your password has been changed successfully!");
                               			else
                               				errorMessageBox("Failed to change your password. Please try again later!"); 
                               			
                               });
                               	$("input#oldpsd").val("");
	                            $("input#newpsd").val("");
	 							$("input#renewpsd").val("");
    					    //$(this).dialog("close");
    						}
    					
    					
    				}
    			}
    });
    $("#dlg_passwd").parent().appendTo($("#frm_chgp"));
}

function Dlg_Update_Profile()
{
    $("#dlg_update").dialog({
    	height:450,
    	width:450,
    	//position:[500,150],
    	modal:true,
    	buttons:{
    				'Cancel':function(){$(this).dialog("close");},
    				'OK':function(){
    					    if(true==validate_Update())
    					    {
    					        var done=$(":button:contains('OK')");
    					        //done.addClass('ui-state-disabled');
								//done.attr('disabled', 'disabled');
								$.post($("#frm_update").attr('action'),$("#frm_update").serialize(),
                                    function(data){
                               			if(data=='0')
                               			{
                               				confirmMessageBox("Your profolio has been updated successfully!");
                               			}
                               			else if(data=='1')
                               			{
                               				//done.addClass('ui-state-enabled');
											//done.attr('disabled', '');
											//parseFieldError(eval("("+data+")"),$("#errmsg_u"));
                               				$("#errmsg_u").html("Failed to update your profolio");
											$("#dlg_update").dialog("open");
                               			}
                               			else if(data=='2')
                               			{
                               				
                               				$("#errmsg_u").html("Organization name doesn't exist");
                               				$("#dlg_update").dialog("open");
                               			}
                               			
                               	});
                               	$(this).dialog("close");
                            }
    					
    					
    				}
    			}
    });
    $("#dlg_update").parent().appendTo($("#frm_update"));
}