package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;

import org.glc.utils.Validation;
import us.glos.mi.domain.UserParam;

public class UserAvailValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		UserParam usr=new UserParam();
		if(Validation.basicValidation(request, usr.getUserParamName()))
		{
			String temp=request.getParameter(usr.getUserParamName()).trim();
			if(Validation.isValidEmail(temp))
			{
				usr.setUserName(temp);
				request.setAttribute(UserParam.getClassName(), usr);
				return true;
			}
		}
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
