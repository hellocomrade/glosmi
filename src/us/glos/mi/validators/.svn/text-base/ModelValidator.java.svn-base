package us.glos.mi.validators;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.ServerUtilities;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.util.DataTableParamParser;
import us.glos.mi.util.ModelForm;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class ModelValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response){
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		ModAdminParam modparam=new ModAdminParam();
		int modid=-1;
		if(Validation.basicValidation(request, modparam.getActionParamName()))
		{
			modparam.setAction(request.getParameter(modparam.getActionParamName()));
			if(modparam.getAction()!=null)
			{
				String aj=null;
		    	if(Validation.basicValidation(request, modparam.getAjaxParamName()))
		    		aj=request.getParameter(modparam.getAjaxParamName());
		    	if(aj==null||!aj.equals("1"))
		    	{
		    		modparam.setAjax(false);
		    	}
		    	else
		    		modparam.setAjax(true);
		    	if(modparam.isAudit())
		    	{
		    		if(Validation.basicValidation(request, modparam.getDraftIdParamName()))//for new approval
		    		{
		    			try
						{
							modid=Integer.parseInt(request.getParameter(modparam.getDraftIdParamName()));
							ModelInfo mi=new ModelInfo();
							mi.setSerialId(modid);
							modparam.setModel(mi);
							request.setAttribute(ModAdminParam.getClassName(), modparam);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(modparam.getModIdParamName(),"Model Serial Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(modparam.getModIdParamName(),"No model Serial Id or model Id is specified.");
		    		
		    	}
		    	else if(modparam.isUpdate())
		    	{
		    		if(Validation.basicValidation(request, modparam.getModIdParamName()))
		    		{
		    			try
						{
							modid=Integer.parseInt(request.getParameter(modparam.getModIdParamName()));
							ModelInfo mi=new ModelInfo();
							mi.setId(modid);
							modparam.setModel(mi);
							request.setAttribute(ModAdminParam.getClassName(), modparam);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(modparam.getModIdParamName(),"No model Id is specified.");
		    	}
		    	else if(modparam.isApprove())
		    	{
		    		boolean bExtraErr=false;
		    		ModelInfo mod=new ModelInfo();
		    		ModelForm.FillNoneRequired(request, mod, modparam, errMsg);
		    		//if(ModelForm.FillRequired(request, mod, modparam, errMsg))
		    		bExtraErr=!ModelForm.FillRequired(request, mod, modparam, errMsg);
		    		{
		    			
		    			int i=0;
		    			
		    			if(Validation.basicValidation(request,modparam.getDraftIdParamName()))
		    			{
		    				try
		    				{
		    					
		    					i=Integer.parseInt(request.getParameter(modparam.getDraftIdParamName()));
		    					if(i>0)
		    						mod.setSerialId(i);
		    					//else
		    					//	bExtraErr=true;
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(modparam.getDraftIdParamName(),"Model Draft Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			i=0;
		    			if(Validation.basicValidation(request, modparam.getModIdParamName()))
		    			{
		    				try
		    				{
		    					i=Integer.parseInt(request.getParameter(modparam.getModIdParamName()));
		    					if(i>0)
		    						mod.setId(i);
		    					/*else
		    					{
		    						errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
		    						bExtraErr=true;
		    					}*/
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			if(Validation.basicValidation(request, modparam.getModApprovalFlagParam()))
		    			{
		    				mod.setApprovedFlag(request.getParameter(modparam.getModApprovalFlagParam()));
		    			}
		    			else
		    			{
		    				errMsg.addError(modparam.getModApprovalFlagParam(),"Model Approval Flag is empty.");
		    				bExtraErr=true;
		    			}
		    			if(mod.getId()<=0&&mod.getSerialId()<=0)
		    				bExtraErr=true;
		    			if(!bExtraErr)
		    			{
		    				modparam.setModel(mod);
		    				request.setAttribute(ModAdminParam.getClassName(), modparam);
		    				
		    				return true;
		    			}
		    		}
		    		//request.setAttribute(ErrorMessage.getClassName(), errMsg);
		    		//String url=ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error");
		    		try
		    		{
		    			ServerUtilities.SetKeyValueOnAction(((HttpServletRequest)request), ((HttpServletRequest)request).getServletPath(), "error", ModelInfo.getClassName(), mod);
		    		}
		    		catch(ServletException s)
		    		{
		    			errMsg.setForward(false);
		    			errMsg.setRedirectURL(ConfigManager.getAppSetting("ERROR_PAGE"));
		    			return false;
		    		}
		    		//if(url!=null&&!url.equals(""))
		    		{
		    			//equest.setAttribute(ModelInfo.getClassName(),mod);
		    			errMsg.setForward(!ConfigManager.isForwardRedirect(((HttpServletRequest)request).getServletPath(), "error"));
		    			errMsg.setForwardURL(ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error"));
		    		}
		    	}
		    	else if(modparam.isRemove())
		    	{
		    		if(Validation.basicValidation(request,modparam.getDraftIdParamName()))
	    			{
	    				try
	    				{
	    					int i=0;
	    					ModelInfo mod=new ModelInfo();
	    					i=Integer.parseInt(request.getParameter(modparam.getDraftIdParamName()));
	    					if(i>0)
	    					{
	    						mod.setSerialId(i);
	    						modparam.setModel(mod);
			    				request.setAttribute(ModAdminParam.getClassName(), modparam);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(modparam.getDraftIdParamName(),"Model Draft Id is empty.");
	    					
	    				}
	    				catch(NumberFormatException ne)
	    				{
	    					errMsg.addError(modparam.getDraftIdParamName(),"Model Draft Id is not valid.");
	    					
	    				}
	    			}
		    		else if(Validation.basicValidation(request,modparam.getModIdParamName()))
		    		{
		    			try
	    				{
	    					int i=0;
	    					
	    					i=Integer.parseInt(request.getParameter(modparam.getModIdParamName()));
	    					if(i>0)
	    					{
	    						ModelInfo mod=new ModelInfo();
	    						mod.setId(i);
	    						modparam.setModel(mod);
			    				request.setAttribute(ModAdminParam.getClassName(), modparam);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(modparam.getModIdParamName(),"Model Id is empty.");
	    					
	    				}
	    				catch(NumberFormatException ne)
	    				{
	    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
	    					
	    				}
		    		}
		    	}
		    	else if(modparam.isChangeOwner())
		    	{
		    		if(Validation.basicValidation(request,modparam.getModEmailParamName())
		    				&&Validation.isValidEmail(request.getParameter(modparam.getModEmailParamName())))
		    		{
		    			if(Validation.basicValidation(request,modparam.getModIdParamName()))
		    			{
		    				int i=0;
		    				try
		    				{
		    					i=Integer.parseInt(request.getParameter(modparam.getModIdParamName()));
		    					if(i>0)
		    					{
		    						ModelInfo mod=new ModelInfo();
		    						mod.setId(i);
		    						mod.setOwnerEmail(request.getParameter(modparam.getModEmailParamName()).trim());
		    						modparam.setModel(mod);
				    				request.setAttribute(ModAdminParam.getClassName(), modparam);
				    				return true;
		    					}
		    					else
		    						errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
		    				}
		    				catch(NumberFormatException e)
		    				{
		    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
		    				}
		    			}
		    			else
		    				errMsg.addError(modparam.getModIdParamName(),"Model Id is empty.");
		    		}
		    		else
		    			errMsg.addError(modparam.getModEmailParamName(), "Email is empty or not valid.");
		    	}
		    	else if(modparam.isChangeStatus())
		    	{
		    		if(Validation.basicValidation(request,modparam.getModIdParamName())
		    				&&Validation.basicValidation(request, modparam.getModStatusParamName()))
	    			{
	    				int i=0;
	    				String s=null;
	    				try
	    				{
	    					i=Integer.parseInt(request.getParameter(modparam.getModIdParamName()));
	    					s=request.getParameter(modparam.getModStatusParamName()).trim();
	    					if(i>0)
	    					{
	    						ModelInfo mod=new ModelInfo();
	    						mod.setId(i);
	    						mod.setActive(s.equals("1"));
	    						modparam.setModel(mod);
			    				request.setAttribute(ModAdminParam.getClassName(), modparam);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
	    				}
	    				catch(NumberFormatException e)
	    				{
	    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
	    				}
	    			}
		    	}
			}
			else
				errMsg.addError(modparam.getActionParamName(),"The action specified is not defined.");
		}
		else
			errMsg.addError(modparam.getActionParamName(),"No action is given.");
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	
}
