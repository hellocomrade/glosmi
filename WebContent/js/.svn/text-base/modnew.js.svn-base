var cnt_dev_div="<%=cntparam.getDeveloperParamName()%>";
	var cnt_dist_div="<%=cntparam.getDistributorParamName()%>";
	function validate_form()
	{
		var result=true;
		if($("input[name=mod_cat[]][checked]").size()==0)
		{
			result=false;
			$("#err_mod_cat").text("At least one Model Category must be selected");
			$("#err_mod_cat").show();
		}
		else
			$("#err_mod_cat").hide();
		
		if($.trim($("#mod_name").val())=="")
		{
			result=false;
			textBoxError($("#mod_name"));
			$("#err_mod_name").text("Model Name can not be empty");
			$("#err_mod_name").show();
		}
		else
		{
			$("#err_mod_name").hide();
			textBoxResume($("#mod_name"));
		}
		
		if($.trim($("#mod_ver").val())=="")
		{
			result=false;
			textBoxError($("#mod_ver"));
			$("#err_mod_ver").text("Model Version can not be empty");
			$("#err_mod_ver").show();
		}
		else
		{
			$("#err_mod_ver").hide();
			textBoxResume($("#mod_ver"));
		}

		if(!$("input[name=modavailid]").is(':checked'))
		{
			result=false;
			$("#err_mod_avail").text("One Model Applicability must be selected");
			$("#err_mod_avail").show();
		}
		else
			$("#err_mod_avail").hide();

		if($("input[name=mod_theme[]][checked]").size()==0)
		{
			result=false;
			$("#err_mod_theme").text("At least one Model Theme must be selected");
			$("#err_mod_theme").show();
		}
		else
			$("#err_mod_theme").hide();

		if($.trim($("#mod_purpose").val())=="")
		{
			result=false;
			textBoxError($("#mod_purpose"));
			$("#err_mod_desc").text("Model Primary Purpose can not be empty");
			$("#err_mod_desc").show();
		}
		else
		{
			$("#err_mod_desc").hide();
			textBoxResume($("#mod_purpose"));
		}

		if($.trim($("#mod_attri").val())=="")
		{
			result=false;
			textBoxError($("#mod_attri"));
			$("#err_mod_attri").text("Model Primary Purpose can not be empty");
			$("#err_mod_attri").show();
		}
		else
		{
			$("#err_mod_attri").hide();
			textBoxResume($("#mod_attri"));
		}

		if($.trim($("#mod_datareq").val())=="")
		{
			result=false;
			textBoxError($("#mod_datareq"));
			$("#err_mod_datareq").text("Model Primary Purpose can not be empty");
			$("#err_mod_datareq").show();
		}
		else
		{
			$("#err_mod_datareq").hide();
			textBoxResume($("#mod_datareq"));
		}
		
		$("input[name=cnt_devel_cnt_firstname[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_lastname[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_email[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if(validateEmail($.trim($(this).val()))==false)
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});
		//$("input[name=cnt_devel_cnt_phone[]]:visible")
		$("input[name=cnt_devel_cnt_phone[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				if(validatePhone(temp)==false)
				{
					textBoxError($(this));
					result=false;
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_devel_cnt_zip[]]").each(function(){
			if("dev_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				var cny=$(this).parent().siblings().find("#mod_country").val();
				if(cny=="us")
				{
					if(validateUSZipcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						 textBoxResume($(this));
				}
				else if(cny=='ca')
				{
					if(validateCAPostalcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						textBoxResume($(this));
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_firstname[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_lastname[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if($.trim($(this).val())=="")
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_email[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			if(validateEmail($.trim($(this).val()))==false)
			{
				textBoxError($(this));
				result=false;
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_phone[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				if(validatePhone(temp)==false)
				{
					textBoxError($(this));
					result=false;
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		$("input[name=cnt_distr_cnt_zip[]]").each(function(){
			if("dist_clone"==$(this).parent().parent().parent().parent().parent().get(0).id)
				return;
			var temp=$.trim($(this).val());
			if(temp!="")
			{
				var cny=$(this).parent().siblings().find("#mod_country").val();
				if(cny=="us")
				{
					if(validateUSZipcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						 textBoxResume($(this));
				}
				else if(cny=='ca')
				{
					if(validateCAPostalcode(temp)==false)
					{
						textBoxError($(this));
						result=false;
					}
					else
						textBoxResume($(this));
				}
				else
					textBoxResume($(this));
			}
			else
				textBoxResume($(this));
		});

		if($.trim($("input[name=mod_cnt_firstname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=mod_cnt_firstname]"));
			
		}
		else
		{
			textBoxResume($("input[name=mod_cnt_firstname]"));
		}

		if($.trim($("input[name=mod_cnt_lastname]").val())=="")
		{
			result=false;
			textBoxError($("input[name=mod_cnt_lastname]"));
			
		}
		else
		{
			textBoxResume($("input[name=mod_cnt_lastname]"));
		}
		
		if($.trim($("input[name=mod_cnt_email]").val())!="")
		{
			if(false==validateEmail($.trim($("input[name=mod_cnt_email]").val())))
			{
				result=false;
				textBoxError($("input[name=mod_cnt_email]"));
			}
			else
				textBoxResume($("input[name=mod_cnt_email]"));
		}
		else
		{
			result=false;
			textBoxError($("input[name=mod_cnt_email]"));
		}

		if($("input[name=mod_cnt_zip]").val()!="")
		{
			var val=$.trim($("input[name=mod_cnt_zip]").val());
			var cny=$("#mod_cnt_country option:selected").val();
			if(cny=='ca'&&validateCAPostalcode(val)==false)
			{
				textBoxError($("input[name=mod_cnt_zip]"));
				result=false;
			}
			else if(cny=='us'&&validateUSZipcode(val)==false)
			{
				textBoxError($("input[name=mod_cnt_zip]"));
				result=false;
			}
			else
			{
				textBoxResume($("input[name=mod_cnt_zip]"));
				
			}
		}
		else
		{
			textBoxResume($("#mod_cnt_zip"));
			
		}

		if($.trim($("input[name=mod_cnt_phone]").val())!="")
		{
			if(validatePhone($.trim($("input[name=mod_cnt_phone]").val()))==false)
			{
				result=false;
				textBoxError($("input[name=mod_cnt_phone]"));
			}
			else
			{
				textBoxResume($("input[name=mod_cnt_phone]"));
			}		
		}
		else
			textBoxResume($("input[name=mod_cnt_phone]"));
		
		if($.trim($("#mod_easyuse").val())=="")
		{
			result=false;
			textBoxError($("#mod_easyuse"));
			$("#err_mod_easyuse").text("Model Skill Level can not be empty");
			$("#err_mod_easyuse").show();
		}
		else
		{
			$("#err_mod_easyuse").hide();
			textBoxResume($("#mod_easyuse"));
		}
		return result;
	}
	
	$(function() {
		$("#mod_tabs").tabs();
		$("#a_modinfo_next").button();
		$("#a_modpeople_prev").button();
		$("#a_modpeople_next").button();
		$("#a_modother_prev").button();
		$("#a_mod_submit_btn").button();
		
		$("#btn_dev_add").button();
		$("#btn_dist_add").button();
		//$("#btn_dev_addp").button();
		//$("#btn_dev_addo").button();
		//$("#btn_dev_edit").button();
		//$("#btn_dev_remo").button();
		
		$("#a_modinfo_next").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',1);
		});
		$("#a_modpeople_prev").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',0);
		});
		$("#a_modpeople_next").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',2);
		});
		$("#a_modother_prev").click(function(event){
			  //event.preventDefault();
			  $("#mod_tabs").tabs('select',1);
		});
		$("#a_model_submit").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=mod_frm]").attr("action","modopt.glos");
				$("#mod_action").val("submit");
				$("[name=mod_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		$("#a_mod_submit_btn").click(function(event){
			event.preventDefault();
			if(true==validate_form())
			{
				$("[name=mod_frm]").attr("action","modopt.glos");
				$("#mod_action").val("submit");
				$("[name=mod_frm]").submit();
			}
			else
				$("#err_prompt_div").show();
		});
		$("#a_model_save").click(function(event){
			event.preventDefault();
			if($.trim($("#mod_name").val())=="")
			{
				result=false;
				textBoxError($("#mod_name"));
				$("#err_mod_name").text("Model Name can not be empty");
				$("#err_mod_name").show();
				errorMessageBox("Failed to save the draft. Model name must not be empty!");
				return;
			}
			else
			{
				$("#err_mod_name").hide();
				textBoxResume($("#mod_name"));
			}
			$("[name=mod_frm]").attr("action","moddraft.glos");
			$("#mod_ajax").val("1");
			var val=$("#draft_id").val();
			if(val==""||val<=0)//save
			{
				$("#mod_action").val("save");
				
				//$("#mod_frm").submit();
				$.post(
						"moddraft.glos",
						$("#mod_frm").serialize(),
						function(text)
						{
							var did=parseInt(text);
							if(did>0)
							{
								$("#draft_id").val(did);
								confirmMessageBox('Your draft has been successfully saved.');
							}
							else
							{
								if(did==-2)
									errorMessageBox('Failed to save the draft. You can only save no more than 8 drafts.');
								else
									errorMessageBox("Failed to save the draft.");
							}
						}
				);
			}
			else if(val>0)//update
			{
				$("#mod_action").val("update");
				
				$.post(
						"moddraft.glos",
						$("#mod_frm").serialize(),
						function(text)
						{
							var id=parseInt(text);
							if(id==0)
							{
								$("draft_id").val(id);
								confirmMessageBox('Your draft has been successfully updated.');
							}
							else
							{
								errorMessageBox('Failed to update the draft.');
							}
						}
				);
			}
		});
		var dev_count=<%=developers==null?1:developers.size()%>;
		var dist_count=<%=distributors==null?1:distributors.size()%>;
		$("#btn_dev_add").click(function(event){
			if($("div[id^='"+cnt_dev_div+"']").size()<5)
			{
				//var elem=$("div[id^='"+cnt_dev_div+"']").first().clone(true).attr("id",cnt_dev_div+(dev_count)).insertBefore($("#btn_dev_add")).fadeIn();
				//due to the bug of autocomplete,clone event can't be used,manually attach events
				var elem=$("#dev_clone").clone().attr("id",cnt_dev_div+(dev_count)).insertBefore($("#err_devs")).fadeIn();
				elem.find(".a_dev_remove").click(dev_remove_callback);
				elem.find(".mod_country").change(countryChangeCallback);
				//var foo=lem.find("#mod_org").autocomplete("enable");
				//var clone=$("#dev_clone").clone(true);
				//clone.attr("id",cnt_dev_div+(dev_count));
				//var elem=clone.insertBefore($("#btn_dev_add")).fadeIn();
				elem.children("#dev_cnt_id").val("");
				/*elem.children("#dev_cnt_id").children("table")
				elem.find("#mod_fname").val("");
				elem.find("#mod_lname").val("");*/
				++dev_count;
			}
			else
				errorMessageBox("You can add at most 5 developers!");
		});
		function dev_remove_callback(event)
		{
			event.preventDefault();
			
			if($("div[id^='"+cnt_dev_div+"']").size()>1)
			{
				$(this).parent().parent().remove();
			}
			else
				errorMessageBox("You can not remove all of them!");
				
		}
		$(".a_dev_remove").click(dev_remove_callback);
		$("#btn_dist_add").click(function(event){
			if($("div[id^='"+cnt_dist_div+"']").size()<5)
			{
				var elem=$("#dist_clone").clone().attr("id",cnt_dist_div+(dist_count)).insertBefore($("#err_dists")).fadeIn();
				elem.children($("#dist_cnt_id")).val("");
				elem.find(".a_dist_remove").click(dist_remove_callback);
				elem.find(".mod_country").change(countryChangeCallback);
				++dist_count;
			}
			else
				errorMessageBox("You can add at most 5 distributors!");
		});
		function dist_remove_callback(event)
		{
			event.preventDefault();
			if($("div[id^='"+cnt_dist_div+"']").size()>1)
			{
				$(this).parent().parent().remove();
			}
			else
				errorMessageBox("You can not remove all of them!");
				
		}
		$(".a_dist_remove").click(dist_remove_callback);

		function countryChangeCallback()
		{
			var td=$(this).parent().siblings('#td_state');
			
			if('ca'==$(this).val())
			{
				td.children("#mod_state").hide();
				td.children("#mod_province").show();
			}
			else
			{
				td.children("#mod_province").hide();
				td.children("#mod_state").show();
				
			}
		}
		$(".mod_country").change(countryChangeCallback);

		$("#chk_same_cnt").click(function(){
			$("input[name=mod_cnt_firstname]").val($("#fname").val());
			$("input[name=mod_cnt_lastname]").val($("#lname").val());
			$("input[name=mod_cnt_add1]").val($("#add1").val());
			$("input[name=mod_cnt_add2]").val($("#add2").val());
			$("input[name=mod_cnt_city]").val($("#city").val());
			$("select[name=mod_cnt_country]").val($("#country").val());
			if($("#country").val()=='us')
			{
				$("select[name=mod_cnt_state]").val($("#state").val());
				$("select[name=mod_cnt_province]").hide();
				$("select[name=mod_cnt_state]").show();
			}
			else if($("#country").val()=='ca')
			{
				$("select[name=mod_cnt_province]").val($("#province").val());
				$("select[name=mod_cnt_state]").hide();
				$("select[name=mod_cnt_province]").show();
			}
			$("input[name=mod_cnt_zip]").val($("#zip").val());
			$("input[name=mod_cnt_phone]").val($("#phone").val());
			$("input[name=mod_cnt_orgid]").val($("#org").val());
		});
	});
	