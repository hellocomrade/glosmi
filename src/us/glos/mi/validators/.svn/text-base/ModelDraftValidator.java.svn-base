package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.util.ModelForm;

public class ModelDraftValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		ModAdminParam modparam=new ModAdminParam();
		
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
		    	if(modparam.isApprove())
		    	{
		    		
		    	}
		    	else if(modparam.isRemove())
		    	{
		    		if(Validation.basicValidation(request,modparam.getDraftIdParamName()))
		    		{
		    			int modid=-1;
		    			ModelInfo mod=null;
		    			try
						{
							modid=Integer.parseInt(request.getParameter(modparam.getDraftIdParamName()));
							mod=new ModelInfo();
							mod.setSerialId(modid);
							modparam.setModel(mod);
							request.setAttribute(ModAdminParam.getClassName(), modparam);
			    			return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(modparam.getDraftIdParamName(),"Draft Id is not valid.");
							
						}
		    		}
		    		else
		    			errMsg.addError(modparam.getDraftIdParamName(),"Model Id can not be empty.");
		    	}
		    	else if(modparam.isSave()&&modparam.isAjaxCall())//ignore all errors except for model name
		    	{
		    		if(Validation.basicValidation(request, modparam.getModNameParamName()))
		    		{
		    			ModelInfo mod=new ModelInfo();
		    			ModelForm.FillRequired(request, mod, modparam, errMsg);
		    			ModelForm.FillNoneRequired(request, mod, modparam, errMsg);
		    			modparam.setModel(mod);
		    			request.setAttribute(ModAdminParam.getClassName(), modparam);
		    			return true;
		    		}
		    		else
		    		{
		    			errMsg.setAjax(true);
		    			errMsg.setAuthorized(true);
		    			errMsg.addError("Error Message:","Model Name can not be empty.");
		    		}
		    		
		    	}
		    	else if(modparam.isSubmit())
		    	{
		    		
		    	}
		    	else if(modparam.isUpdate())
		    	{
		    		if(Validation.basicValidation(request, modparam.getModNameParamName()))
		    		{
		    			if(Validation.basicValidation(request, modparam.getDraftIdParamName()))
		    			{
		    				boolean result=true;
		    				ModelInfo mod=new ModelInfo();
		    				try
		    				{
		    					mod.setSerialId(Integer.parseInt(request.getParameter(modparam.getDraftIdParamName())));
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(modparam.getDraftIdParamName(),"Draft Id is not valid.");
		    					result=false;
		    					
		    				}
		    				if(result)
		    				{
		    					ModelForm.FillRequired(request, mod, modparam, errMsg);
		    					ModelForm.FillNoneRequired(request, mod, modparam, errMsg);
		    					modparam.setModel(mod);
		    					request.setAttribute(ModAdminParam.getClassName(), modparam);
		    					return true;
		    				}
		    				
		    			}
		    			else
		    				errMsg.addError(modparam.getDraftIdParamName(),"Model Draft Id can not be empty.");
		    		}
		    		else
		    			errMsg.addError(modparam.getModNameParamName(),"Model Name can not be empty.");
		    		
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
