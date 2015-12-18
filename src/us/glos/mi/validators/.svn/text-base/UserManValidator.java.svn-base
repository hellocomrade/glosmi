package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;
import org.glc.domain.User;
import us.glos.mi.domain.UserAdminParam;


public class UserManValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		//we don't do non-ajax
		errMsg.setAjax(true);
		UserAdminParam param=new UserAdminParam();
		String temp=null;
		String aj=null;
		if(Validation.basicValidation(request, param.getAjaxParamName()))
			aj=request.getParameter(param.getAjaxParamName());
    	if(aj==null||!aj.equals("1"))
    	{
    		errMsg.addError(param.getAjaxParamName(), "I only do ajax.");
    	}
    	else
    	{
    		param.setAjax(true);
    		if(Validation.basicValidation(request, param.getActionParamName()))
    		{
    			param.setAction(request.getParameter(param.getActionParamName()));
    			if(Validation.basicValidation(request,param.getUserParamName())&&
    					Validation.isValidEmail(request.getParameter(param.getUserParamName())))
    			{
    				User usr=new User();
    				usr.setEmail(request.getParameter(param.getUserParamName()));
    				usr.setUsername(request.getParameter(param.getUserParamName()));
    				param.setUsr(usr);
    				if(param.isChangeGroup())
    				{
    					if(Validation.basicValidation(request, param.getIsAdmParamName()))
    						param.setAdm(true);
    					if(Validation.basicValidation(request, param.getIsModParamName()))
    						param.setMod(true);
    					if(Validation.basicValidation(request, param.getIsAppParamName()))
    						param.setApp(true);
    					if(!param.isAdm()&&!param.isMod()&&!param.isApp())
    						errMsg.addError(param.getIsAdmParamName(), "At leaset one group is required.");
    					else
    					{
    						
    						request.setAttribute(UserAdminParam.getClassName(), param);
    						return true;
    					}
    				}
    				else if(param.isChangeStatus())
    				{
    					if(Validation.basicValidation(request,param.getIsActivateParamName()))
    					{
    						if(request.getParameter(param.getIsActivateParamName()).equals("1"))
    						{
    							param.setActive(true);
    						}
    						else
    							param.setActive(false);
    						request.setAttribute(UserAdminParam.getClassName(), param);
    						return true;
    					}
    					else
    					{
    						errMsg.addError(param.getIsActivateParamName(), "Field is required.");
    					}
    				}
    			}
    		}
    		else if(((HttpServletRequest)request).getMethod()=="GET"&&request.getParameter("uid")!=null)//query user info
    			return true;
    		else
    			errMsg.addError(param.getUserParamName(), "at leaset uid is required for GET.");
    	}
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
