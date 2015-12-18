package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.AppForm;

public class AppOptValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		AppInfo app=null;
		ErrorMessage errMsg=new ErrorMessage();
		AppAdminParam ap=new AppAdminParam();
		if(Validation.basicValidation(request, ap.getActionParamName()))
		{
			ap.setAction(request.getParameter(ap.getActionParamName()));
			if(ap.getAction()!=null)
			{
				String aj=null;
		    	if(Validation.basicValidation(request, ap.getAjaxParamName()))
		    		aj=request.getParameter(ap.getAjaxParamName());
		    	if(aj==null||!aj.equals("1"))
		    	{
		    		ap.setAjaxCall(false);
		    	}
		    	else
		    		ap.setAjaxCall(true);
		    	if(ap.isSubmit())
		    	{
		    		app=new AppInfo();
		    		AppForm.FillNoneRequired(request, app, ap, errMsg);
		    		if(AppForm.FillRequired(request, app, ap, errMsg))
		    		{
		    			
		    			int i=0;
		    			boolean bExtraErr=false;
		    			if(Validation.basicValidation(request,ap.getAppDraftIdParamName()))
		    			{
		    				try
		    				{
		    					
		    					i=Integer.parseInt(request.getParameter(ap.getAppDraftIdParamName()));
		    					if(i>0)
		    						app.setSerialId(i);
		    					//else
		    					//	bExtraErr=true;
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(ap.getAppDraftIdParamName(),"Applicatoin Draft Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			i=0;
		    			if(Validation.basicValidation(request, ap.getModIdParamName()))
		    			{
		    				try
		    				{
		    					i=Integer.parseInt(request.getParameter(ap.getModIdParamName()));
		    					if(i>0)
		    						app.setModId(i);
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(ap.getModIdParamName(),"Model Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			i=0;
		    			if(Validation.basicValidation(request, ap.getAppIdParamName()))
		    			{
		    				try
		    				{
		    					i=Integer.parseInt(request.getParameter(ap.getAppIdParamName()));
		    					if(i>0)
		    						app.setId(i);
		    					/*else
		    					{
		    						errMsg.addError(ap.getModIdParamName(),"Model Id is not valid.");
		    						bExtraErr=true;
		    					}*/
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(ap.getModIdParamName(),"Application Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			/*if(app.getId()<=0&&app.getSerialId()<=0)
		    				bExtraErr=true;*/
		    			if(app.getModId()<=0)//an app must rely on a model
		    				bExtraErr=true;
		    			if(app.getId()>0)//update
		    			{
		    				if(Validation.basicValidation(request, ap.getAppUpdateReasonParam()))
		    				{
		    					String temp=request.getParameter(ap.getAppUpdateReasonParam());
		    					if(temp.trim().length()>500)
		    						temp=temp.trim().substring(0, 199);
		    					app.setUpdateReason(temp);
		    				}
		    				else
		    				{
		    					errMsg.addError(ap.getAppUpdateReasonParam(),"Reason for update can not be empty.");
		    					bExtraErr=true;
		    				}
		    			}
		    			if(!bExtraErr)
		    			{
		    				ap.setApplication(app);
		    				request.setAttribute(AppAdminParam.getClassName(), ap);
		    				
		    				return true;
		    			}
		    		}
		    		//request.setAttribute(ErrorMessage.getClassName(), errMsg);
		    		String url=ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error");
		    		if(url!=null&&!url.equals(""))
		    		{
		    			request.setAttribute(AppInfo.getClassName(),app);
		    			errMsg.setForward(true);
		    			errMsg.setForwardURL(url);
		    		}
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
