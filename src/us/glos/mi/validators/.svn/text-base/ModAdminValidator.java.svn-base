package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.util.DataTableParamParser;
import us.glos.mi.util.ModelForm;
import us.glos.mi.util.DataTableParamParser.DataTableParams;
import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ContactParamBase;
import us.glos.mi.domain.DeveloperParam;
import us.glos.mi.domain.DistributorParam;

public class ModAdminValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		int modid=-1;
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
		    	if(modparam.isNew())
		    	{
		    		request.setAttribute(ModAdminParam.getClassName(), modparam);
	    		    return true;
		    	}
		    	else if(modparam.isList())
		    	{
		    		DataTableParams dtp=DataTableParamParser.ParseDataTableParameters(request);
		    		if(dtp!=null)
		    		{
		    		    modparam.setAjax(true);
		    			request.setAttribute(DataTableParams.getClassName(), dtp);
		    		    request.setAttribute(ModAdminParam.getClassName(), modparam);
		    		    return true;
		    		   
		    		       	//errMsg.addError("datatable","No parameter for displaying the table");
		    		}
		    		else
		    		{
		    		    request.setAttribute(ModAdminParam.getClassName(), modparam);
		    		    return true;
		    		}
		    		
		    	}
		    	else if(modparam.isSubmit())
		    	{
		    		DataTableParams dtp=DataTableParamParser.ParseDataTableParameters(request);
		    		if(dtp!=null)
		    		{
		    		    modparam.setAjax(true);
		    			request.setAttribute(DataTableParams.getClassName(), dtp);
		    		    request.setAttribute(ModAdminParam.getClassName(), modparam);
		    		    return true;
		    		   
		    		       	//errMsg.addError("datatable","No parameter for displaying the table");
		    		}
		    		else
		    		{
		    		    request.setAttribute(ModAdminParam.getClassName(), modparam);
		    		    return true;
		    		}
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
		    	else if(modparam.isAudit())
		    	{
		    		if(Validation.basicValidation(request, modparam.getDraftIdParamName()))
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
		    			errMsg.addError(modparam.getModIdParamName(),"No model Serial Id is specified.");
		    		
		    	}
		    	/*else if(modparam.isApprove())
		    	{
		    		ModelInfo mod=new ModelInfo();
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
		    					else
		    						bExtraErr=true;
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
		    					
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
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
		    	}*/
		    	
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
