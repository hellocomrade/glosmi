package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.util.ModelForm;

public class ModOptValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ModelInfo mod=null;
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
		    	if(modparam.isSubmit())
		    	{
		    		mod=new ModelInfo();
		    		ModelForm.FillNoneRequired(request, mod, modparam, errMsg);
		    		if(ModelForm.FillRequired(request, mod, modparam, errMsg))
		    		{
		    			
		    			int i=0;
		    			boolean bExtraErr=false;
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
		    			/*if(mod.getId()<=0&&mod.getSerialId()<=0)
		    				bExtraErr=true;*/
		    			if(mod.getId()>0)//update
		    			{
		    				if(Validation.basicValidation(request, modparam.getModUpdateReasonParam()))
		    				{
		    					String temp=request.getParameter(modparam.getModUpdateReasonParam());
		    					if(temp.trim().length()>500)
		    						temp=temp.trim().substring(0, 199);
		    					mod.setUpdateReason(temp);
		    				}
		    				else
		    				{
		    					errMsg.addError(modparam.getModUpdateReasonParam(),"Reason for update can not be empty.");
		    					bExtraErr=true;
		    				}
		    			}
		    			if(!bExtraErr)
		    			{
		    				modparam.setModel(mod);
		    				request.setAttribute(ModAdminParam.getClassName(), modparam);
		    				
		    				return true;
		    			}
		    		}
		    		//request.setAttribute(ErrorMessage.getClassName(), errMsg);
		    		String url=ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error");
		    		if(url!=null&&!url.equals(""))
		    		{
		    			request.setAttribute(ModelInfo.getClassName(),mod);
		    			errMsg.setForward(true);
		    			errMsg.setForwardURL(url);
		    		}
		    	}
		    	else if(modparam.isRemove())
		    	{
		    		
		    		if(Validation.basicValidation(request, modparam.getModIdParamName()))
	    			{
	    				try
	    				{
	    					int i=0;
	    					i=Integer.parseInt(request.getParameter(modparam.getDraftIdParamName()));
	    					if(i>0)
	    					{
	    						mod=new ModelInfo();
	    						mod.setId(i);
	    					
	    						modparam.setModel(mod);
	    						request.setAttribute(ModAdminParam.getClassName(), modparam);
		    				
	    						return true;
	    					}
	    					else
	    						errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
	    					
	    				}
	    				catch(NumberFormatException ne)
	    				{
	    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
	    					
	    				}
	    			}
		    		else
		    			errMsg.addError(modparam.getModIdParamName(),"Model Id can not be empty.");
		    	}
			}
		}
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
