package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.glc.IValidParam;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.util.DataTableParamParser;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class ModDraftAdminValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		ErrorMessage errMsg=new ErrorMessage();
		ModAdminParam modparam=new ModAdminParam();
		boolean result=true;
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
		    	if(modparam.isList())
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
		    		if(Validation.basicValidation(request, modparam.getDraftIdParamName()))
		    		{
		    			ModelInfo mod=new ModelInfo();
		    			try
		    			{
		    				mod.setSerialId(Integer.parseInt(request.getParameter(modparam.getDraftIdParamName())));
		    			}
		    			catch(NumberFormatException ne)
		    			{
		    				result=false;
		    				errMsg.addError(modparam.getModIdParamName(), "Draft Id is not valid.");
		    			}
		    			if(result)
		    			{
		    				modparam.setModel(mod);
		    				request.setAttribute(ModAdminParam.getClassName(), modparam);
		    				return result;
		    			}
		    		}
		    		else
		    			errMsg.addError(modparam.getModIdParamName(), "Draft Id is empty.");
		    	}
			}
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
