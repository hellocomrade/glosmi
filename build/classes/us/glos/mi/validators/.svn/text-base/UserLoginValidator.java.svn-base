package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.UserParam;

public class UserLoginValidator implements IValidParam {

	//private final static String USER_PARAM="usr";
	//private final static String PASSWORD_PARAM="passwd";
	//private final static String REMEMBER_ME="rme";
	//private final static String LOG_OUT="logout";
	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		//HttpServletRequest req=(HttpServletRequest)request;
		UserParam usr=new UserParam();
		//HttpServletResponse rsp=(HttpServletResponse)response;
		if(Validation.basicValidation(request, usr.getUserParamName())
				&&Validation.basicValidation(request, usr.getPasswordParamName()))
				
		{
			String email=request.getParameter(usr.getUserParamName());
			email=email.trim();
			if(Validation.isValidEmail(email))
			{
				
				usr.setUserName(email);
				usr.setPassword(request.getParameter(usr.getPasswordParamName()));
				usr.setLastUri(request.getParameter(usr.getLastUrlParamName()));
				usr.setRemembered(null!=request.getParameter(usr.getRememberedParamName()));
				usr.setLogout(null!=request.getParameter(usr.getLogoutParamName()));
				request.setAttribute(UserParam.getClassName(), usr);
				return true;
			}
			else
			{
				String path=((HttpServletRequest)request).getServletPath();
				if(path!=null&&path.length()>0)
				{
					path=path.substring(0,path.length()-1);
					String url=ConfigManager.getActionForward(path, "error");
					if(url!=null)
					{
						ErrorMessage errMsg=new ErrorMessage();
						errMsg.addError("err", "User name or password is not valid");
						request.setAttribute(ErrorMessage.getClassName(), errMsg);
						errMsg.setForward(true);
						errMsg.setForwardURL(url);
					}
				}
				return false;
			}
		}
		else if(request.getParameter(usr.getLogoutParamName())!=null)
		{
			usr.setLogout(true);
			request.setAttribute(UserParam.getClassName(), usr);
			return true;
		}
		else if(request.getParameter(usr.getLastUrlParamName())!=null)
		{
			usr.setLastUri(request.getParameter(usr.getLastUrlParamName()));
			usr.setUserName(null);
			usr.setPassword(null);
			request.setAttribute(UserParam.getClassName(), usr);
			return true;
		}
		request.setAttribute(UserParam.getClassName(), usr);
		return true;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
