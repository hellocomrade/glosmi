package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.AppForm;

public class AppDraftValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		AppAdminParam apparam=new AppAdminParam();
		
		if(Validation.basicValidation(request, apparam.getActionParamName()))
		{
			apparam.setAction(request.getParameter(apparam.getActionParamName()));
			if(apparam.getAction()!=null)
			{
				String aj=null;
		    	if(Validation.basicValidation(request, apparam.getAjaxParamName()))
		    		aj=request.getParameter(apparam.getAjaxParamName());
		    	if(aj==null||!aj.equals("1"))
		    	{
		    		apparam.setAjaxCall(false);
		    	}
		    	else
		    		apparam.setAjaxCall(true);
		    	if(apparam.isApprove())
		    	{
		    		
		    	}
		    	else if(apparam.isRemove())
		    	{
		    		if(Validation.basicValidation(request,apparam.getAppDraftIdParamName()))
		    		{
		    			int appid=-1;
		    			AppInfo app=null;
		    			try
						{
							appid=Integer.parseInt(request.getParameter(apparam.getAppDraftIdParamName()));
							app=new AppInfo();
							app.setSerialId(appid);
							apparam.setApplication(app);
							request.setAttribute(AppAdminParam.getClassName(), apparam);
			    			return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(apparam.getAppDraftIdParamName(),"Draft Id is not valid.");
							
						}
		    		}
		    		else
		    			errMsg.addError(apparam.getAppDraftIdParamName(),"Application Draft Id can not be empty.");
		    	}
		    	else if(apparam.isSave()&&apparam.isAjaxCall())//ignore all errors except for model name
		    	{
		    		if(Validation.basicValidation(request, apparam.getAppNameParamName()))
		    		{
		    			AppInfo app=new AppInfo();
		    			if(Validation.basicValidation(request, apparam.getModIdParamName()))
		    			{
		    				try
		    				{
		    					app.setModId(Integer.parseInt(request.getParameter(apparam.getModIdParamName())));
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(apparam.getModIdParamName(),"Model Id is not valid.");
		    					
		    				}
		    			}
		    			AppForm.FillRequired(request, app, apparam, errMsg);
		    			AppForm.FillNoneRequired(request, app, apparam, errMsg);
		    			apparam.setApplication(app);
		    			request.setAttribute(AppAdminParam.getClassName(), apparam);
		    			return true;
		    		}
		    		else
		    		{
		    			errMsg.setAjax(true);
		    			errMsg.setAuthorized(true);
		    			errMsg.addError("Error Message:","Model Name can not be empty.");
		    		}
		    		
		    	}
		    	else if(apparam.isUpdate())
		    	{
		    		if(Validation.basicValidation(request, apparam.getAppNameParamName()))
		    		{
		    			if(Validation.basicValidation(request, apparam.getAppDraftIdParamName()))
		    			{
		    				boolean result=true;
		    				AppInfo app=new AppInfo();
		    				try
		    				{
		    					app.setSerialId(Integer.parseInt(request.getParameter(apparam.getAppDraftIdParamName())));
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(apparam.getAppDraftIdParamName(),"Draft Id is not valid.");
		    					result=false;
		    					
		    				}
		    				if(result)
		    				{
		    					if(Validation.basicValidation(request, apparam.getModIdParamName()))
				    			{
				    				try
				    				{
				    					app.setModId(Integer.parseInt(request.getParameter(apparam.getModIdParamName())));
				    				}
				    				catch(NumberFormatException ne)
				    				{
				    					errMsg.addError(apparam.getModIdParamName(),"Model Id is not valid.");
				    					
				    				}
				    			}
		    					AppForm.FillRequired(request, app, apparam, errMsg);
		    					AppForm.FillNoneRequired(request, app, apparam, errMsg);
		    					apparam.setApplication(app);
		    					request.setAttribute(AppAdminParam.getClassName(), apparam);
		    					return true;
		    				}
		    				
		    			}
		    			else
		    				errMsg.addError(apparam.getAppDraftIdParamName(),"Application Draft Id can not be empty.");
		    		}
		    		else
		    			errMsg.addError(apparam.getAppNameParamName(),"Application Name can not be empty.");
		    		
		    	}
			}
			else
				errMsg.addError(apparam.getActionParamName(),"The action specified is not defined.");
		}
		else
			errMsg.addError(apparam.getActionParamName(),"No action is given.");
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
