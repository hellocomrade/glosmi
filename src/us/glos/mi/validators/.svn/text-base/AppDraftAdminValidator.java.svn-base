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

public class AppDraftAdminValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		AppAdminParam apparam=new AppAdminParam();
		boolean result=true;
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
		    	if(apparam.isList())
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
		    		if(Validation.basicValidation(request, apparam.getAppDraftIdParamName()))
		    		{
		    			AppInfo app=new AppInfo();
		    			try
		    			{
		    				app.setSerialId(Integer.parseInt(request.getParameter(apparam.getAppDraftIdParamName())));
		    			}
		    			catch(NumberFormatException ne)
		    			{
		    				result=false;
		    				errMsg.addError(apparam.getModIdParamName(), "Draft Id is not valid.");
		    			}
		    			if(result)
		    			{
		    				apparam.setApplication(app);
		    				request.setAttribute(AppAdminParam.getClassName(), apparam);
		    				return result;
		    			}
		    		}
		    		else
		    			errMsg.addError(apparam.getAppDraftIdParamName(), "Draft Id is empty.");
		    	}
			}
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
