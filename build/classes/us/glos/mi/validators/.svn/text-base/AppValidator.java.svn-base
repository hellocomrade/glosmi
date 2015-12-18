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

import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.util.AppForm;

public class AppValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		AppAdminParam ap=new AppAdminParam();
		int appid=-1;
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
		    	if(ap.isAudit())
		    	{
		    		if(Validation.basicValidation(request, ap.getAppDraftIdParamName()))//for new approval
		    		{
		    			try
						{
							appid=Integer.parseInt(request.getParameter(ap.getAppDraftIdParamName()));
							AppInfo app=new AppInfo();
							app.setSerialId(appid);
							ap.setApplication(app);
							request.setAttribute(AppAdminParam.getClassName(), ap);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(ap.getAppDraftIdParamName(),"Application Serial Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(ap.getAppDraftIdParamName(),"No application Serial Id or application Id is specified.");
		    		
		    	}
		    	else if(ap.isUpdate())
		    	{
		    		if(Validation.basicValidation(request, ap.getAppIdParamName()))
		    		{
		    			try
						{
							appid=Integer.parseInt(request.getParameter(ap.getAppIdParamName()));
							AppInfo app=new AppInfo();
							app.setId(appid);
							ap.setApplication(app);
							request.setAttribute(AppAdminParam.getClassName(), ap);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(ap.getAppIdParamName(),"Application Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(ap.getAppIdParamName(),"No application Id is specified.");
		    	}
		    	else if(ap.isApprove())
		    	{
		    		AppInfo app=new AppInfo();
		    		boolean bExtraErr=false;
		    		AppForm.FillNoneRequired(request, app, ap, errMsg);
		    		//if(AppForm.FillRequired(request, app, ap, errMsg))
		    		bExtraErr=!AppForm.FillRequired(request, app, ap, errMsg);
		    		{
		    			
		    			int i=0;
		    			
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
		    					errMsg.addError(ap.getAppDraftIdParamName(),"Application Draft Id is not valid.");
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
		    						errMsg.addError(modparam.getModIdParamName(),"Model Id is not valid.");
		    						bExtraErr=true;
		    					}*/
		    				}
		    				catch(NumberFormatException ne)
		    				{
		    					errMsg.addError(ap.getAppIdParamName(),"Application Id is not valid.");
		    					bExtraErr=true;
		    				}
		    			}
		    			if(Validation.basicValidation(request, ap.getAppApprovalFlagParam()))
		    			{
		    				app.setApprovedFlag(request.getParameter(ap.getAppApprovalFlagParam()));
		    			}
		    			else
		    			{
		    				errMsg.addError(ap.getAppApprovalFlagParam(),"Application Approval Flag is empty.");
		    				bExtraErr=true;
		    			}
		    			if(app.getId()<=0&&app.getSerialId()<=0)
		    				bExtraErr=true;
		    			if(app.getModId()<=0)//an app must rely on a model
		    				bExtraErr=true;
		    			if(!bExtraErr)
		    			{
		    				ap.setApplication(app);
		    				request.setAttribute(AppAdminParam.getClassName(), ap);
		    				
		    				return true;
		    			}
		    		}
		    		//request.setAttribute(ErrorMessage.getClassName(), errMsg);
		    		/*String url=ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error");
		    		if(url!=null&&!url.equals(""))
		    		{
		    			request.setAttribute(AppInfo.getClassName(),ap);
		    			errMsg.setForward(true);
		    			errMsg.setForwardURL(url);
		    		}*/
		    		try
		    		{
		    			ServerUtilities.SetKeyValueOnAction(((HttpServletRequest)request), ((HttpServletRequest)request).getServletPath(), "error", AppInfo.getClassName(), app);
		    		}
		    		catch(ServletException s)
		    		{
		    			errMsg.setForward(false);
		    			errMsg.setRedirectURL(ConfigManager.getAppSetting("ERROR_PAGE"));
		    			return false;
		    		}
		    		errMsg.setForward(!ConfigManager.isForwardRedirect(((HttpServletRequest)request).getServletPath(), "error"));
		    		errMsg.setForwardURL(ConfigManager.getActionForward(((HttpServletRequest)request).getServletPath(), "error"));
		    		
		    	}
		    	else if(ap.isRemove())
		    	{
		    		if(Validation.basicValidation(request,ap.getAppDraftIdParamName()))
	    			{
	    				try
	    				{
	    					int i=0;
	    					AppInfo app=new AppInfo();
	    					i=Integer.parseInt(request.getParameter(ap.getAppDraftIdParamName()));
	    					if(i>0)
	    					{
	    						app.setSerialId(i);
	    						ap.setApplication(app);
			    				request.setAttribute(AppAdminParam.getClassName(), ap);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(ap.getAppDraftIdParamName(),"Application Draft Id is empty.");
	    					
	    				}
	    				catch(NumberFormatException ne)
	    				{
	    					errMsg.addError(ap.getAppDraftIdParamName(),"Application Draft Id is not valid.");
	    					
	    				}
	    			}
		    		else if(Validation.basicValidation(request,ap.getAppIdParamName()))
		    		{
		    			try
	    				{
	    					int i=0;
	    					
	    					i=Integer.parseInt(request.getParameter(ap.getAppIdParamName()));
	    					if(i>0)
	    					{
	    						AppInfo app=new AppInfo();
	    						app.setId(i);
	    						ap.setApplication(app);
			    				request.setAttribute(AppAdminParam.getClassName(), ap);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(ap.getAppIdParamName(),"Application Id is empty.");
	    					
	    				}
	    				catch(NumberFormatException ne)
	    				{
	    					errMsg.addError(ap.getAppIdParamName(),"Application Id is not valid.");
	    					
	    				}
		    		}
		    	}
		    	else if(ap.isChangeOwner())
		    	{
		    		if(Validation.basicValidation(request,ap.getAppEmailParamName())
		    				&&Validation.isValidEmail(request.getParameter(ap.getAppEmailParamName())))
		    		{
		    			if(Validation.basicValidation(request,ap.getAppIdParamName()))
		    			{
		    				int i=0;
		    				try
		    				{
		    					i=Integer.parseInt(request.getParameter(ap.getAppIdParamName()));
		    					if(i>0)
		    					{
		    						AppInfo app=new AppInfo();
		    						app.setId(i);
		    						app.setOwnerEmail(request.getParameter(ap.getAppEmailParamName()).trim());
		    						ap.setApplication(app);
		    						request.setAttribute(AppAdminParam.getClassName(), ap);
				    				return true;
		    					}
		    					else
		    						errMsg.addError(ap.getAppIdParamName(),"Application Id is not valid.");
		    				}
		    				catch(NumberFormatException e)
		    				{
		    					errMsg.addError(ap.getAppIdParamName(),"Application Id is not valid.");
		    				}
		    			}
		    			else
		    				errMsg.addError(ap.getAppIdParamName(),"Application Id is empty.");
		    		}
		    		else
		    			errMsg.addError(ap.getAppEmailParamName(), "Email is empty or not valid.");
		    	}
		    	else if(ap.isChangeStatus())
		    	{
		    		if(Validation.basicValidation(request,ap.getAppIdParamName())
		    				&&Validation.basicValidation(request, ap.getAppStatusParamName()))
	    			{
	    				int i=0;
	    				String s=null;
	    				try
	    				{
	    					i=Integer.parseInt(request.getParameter(ap.getAppIdParamName()));
	    					s=request.getParameter(ap.getAppStatusParamName()).trim();
	    					if(i>0)
	    					{
	    						AppInfo app=new AppInfo();
	    						app.setId(i);
	    						app.setActive(s.equals("1"));
	    						ap.setApplication(app);
			    				request.setAttribute(AppAdminParam.getClassName(), ap);
			    				return true;
	    					}
	    					else
	    						errMsg.addError(ap.getAppIdParamName(),"Applicatoin Id is not valid.");
	    				}
	    				catch(NumberFormatException e)
	    				{
	    					errMsg.addError(ap.getAppIdParamName(),"Application Draft Id is not valid.");
	    				}
	    			}
		    	}
			}
			else
				errMsg.addError(ap.getActionParamName(),"The action specified is not defined.");
		}
		else
			errMsg.addError(ap.getActionParamName(),"No action is given.");
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
