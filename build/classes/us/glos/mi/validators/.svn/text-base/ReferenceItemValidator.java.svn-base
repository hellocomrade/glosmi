package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.ReferenceParam;

public class ReferenceItemValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		ReferenceParam param=new ReferenceParam();
		if(Validation.basicValidation(request, param.getCategoryParamName()))
		{
			param.setCategoryItem(request.getParameter(param.getCategoryParamName()).trim());
			request.setAttribute(ReferenceParam.getClassName(),param);
			return true;
			
		}
		else if(Validation.basicValidation(request, param.getThemeParamName()))
		{
			param.setThemeItem(request.getParameter(param.getThemeParamName()).trim());
			request.setAttribute(ReferenceParam.getClassName(),param);
			return true;
		}
		else
			errMsg.addError("err","No reference item name is specified.");
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
