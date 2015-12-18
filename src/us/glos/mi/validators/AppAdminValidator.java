package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.DataTableParamParser;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class AppAdminValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		int appid=-1;
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
		    	if(apparam.isNew())
		    	{
		    		request.setAttribute(AppAdminParam.getClassName(), apparam);
	    		    return true;
		    	}
		    	else if(apparam.isList())
		    	{
		    		DataTableParams dtp=DataTableParamParser.ParseDataTableParameters(request);
		    		if(dtp!=null)
		    		{
		    		    apparam.setAjaxCall(true);
		    			request.setAttribute(DataTableParams.getClassName(), dtp);
		    		    request.setAttribute(AppAdminParam.getClassName(), apparam);
		    		    return true;
		    		   
		    		       	
		    		}
		    		else
		    		{
		    		    request.setAttribute(AppAdminParam.getClassName(), apparam);
		    		    return true;
		    		}
		    		
		    	}
		    	else if(apparam.isSubmit())
		    	{
		    		DataTableParams dtp=DataTableParamParser.ParseDataTableParameters(request);
		    		if(dtp!=null)
		    		{
		    		    apparam.setAjaxCall(true);
		    			request.setAttribute(DataTableParams.getClassName(), dtp);
		    		    request.setAttribute(AppAdminParam.getClassName(), apparam);
		    		    return true;
		    		   
		    		       	//errMsg.addError("datatable","No parameter for displaying the table");
		    		}
		    		else
		    		{
		    		    request.setAttribute(AppAdminParam.getClassName(), apparam);
		    		    return true;
		    		}
		    	}
		    	else if(apparam.isUpdate())
		    	{
		    		
		    		if(Validation.basicValidation(request, apparam.getAppIdParamName()))
		    		{
		    			try
						{
							appid=Integer.parseInt(request.getParameter(apparam.getAppIdParamName()));
							AppInfo app=new AppInfo();
							app.setId(appid);
							apparam.setApplication(app);
							request.setAttribute(AppAdminParam.getClassName(), apparam);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(apparam.getAppIdParamName(),"Application Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(apparam.getAppIdParamName(),"No application Id is specified.");
		    	}
		    	else if(apparam.isAudit())
		    	{
		    		if(Validation.basicValidation(request, apparam.getAppDraftIdParamName()))
		    		{
		    			try
						{
							appid=Integer.parseInt(request.getParameter(apparam.getAppDraftIdParamName()));
							AppInfo app=new AppInfo();
							app.setSerialId(appid);
							apparam.setApplication(app);
							request.setAttribute(AppAdminParam.getClassName(), apparam);
							return true;
						}
						catch(NumberFormatException ne)
						{
							errMsg.addError(apparam.getAppDraftIdParamName(),"Application Serial Id is not valid.");
						}
		    		}
		    		else
		    			errMsg.addError(apparam.getAppDraftIdParamName(),"No application Serial Id is specified.");
		    		
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
