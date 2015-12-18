package us.glos.mi.validators;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.glc.IValidParam;
import org.glc.domain.User;
import org.glc.domain.Address;
import org.glc.domain.Orgnization;
import org.glc.utils.Validation;
import org.glc.domain.ErrorMessage;

import us.glos.mi.domain.UserAdminParam;

public class UserAdminValidator implements IValidParam {

	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		//HttpServletRequest req=(HttpServletRequest)request;
		ErrorMessage errMsg=new ErrorMessage();
		//we don't do non-ajax
		errMsg.setAjax(true);
		UserAdminParam param=new UserAdminParam();
		String temp=null;
		if(Validation.basicValidation(request, param.getActionParamName()))
		{
		    param.setAction(request.getParameter(param.getActionParamName()));
		    if(param.getAction()!=null)
		    {
		    	String aj=null;
		    	param.setAjax(false);
		    	if(Validation.basicValidation(request, param.getAjaxParamName()))
		    	{
		    		aj=request.getParameter(param.getAjaxParamName());
		    		if(aj.equals("1"))
		    			param.setAjax(true);
		       	}
		    	else
		    		errMsg.addError(param.getAjaxParamName(), "I only do ajax.");
		    	if(param.isAjaxCall())
		    	{
		    		if(param.isChangePassword())
		    		{
		    			if(Validation.basicValidation(request, param.getNewPasswordParamName())&&
		    					Validation.isValidPassword(request.getParameter(param.getNewPasswordParamName())))
		    			{
		    				if(Validation.basicValidation(request, param.getOldPasswordParamName()))
		    					param.setOldPassword(request.getParameter(param.getOldPasswordParamName()));
		    				param.setNewPassword(request.getParameter(param.getNewPasswordParamName()));
		    				if(Validation.basicValidation(request, param.getUserParamName()))
		    				{
		    					temp=request.getParameter(param.getUserParamName()).trim();
		    					if(Validation.isValidEmail(temp))
		    					{
		    						param.setEmail(temp);
		    					}
		    					else
		    						errMsg.addError(param.getUserParamName(),"Email is not valid.");
		    				}
		    				request.setAttribute(UserAdminParam.getClassName(), param);
		    				return true;
		    			}
		    			else
		    				errMsg.addError(param.getNewPasswordParamName(),"Password is empty or is not compliant with rules.");
		    		}
		    		else if(param.isNew())
		    		{
		    			if(Validation.basicValidation(request, param.getUserParamName()))
		    			{
		    				temp=request.getParameter(param.getUserParamName()).trim();
		    				if(Validation.isValidEmail(temp))
		    				{
		    					User usr=new User();
		    					Address add=new Address();

		    					usr.setUsername(temp);
		    					usr.setEmail(usr.getUsername());
		    					if(Validation.basicValidation(request, param.getNewPasswordParamName())&&
		    							Validation.isValidPassword(request.getParameter(param.getNewPasswordParamName())))
		    					{
		    						temp=request.getParameter(param.getNewPasswordParamName()).trim();

		    						param.setNewPassword(temp);
		    						if(Validation.basicValidation(request,param.getIsAdmParamName()))
		    							param.setAdm(true);
		    						if(Validation.basicValidation(request,param.getIsModParamName()))
		    							param.setMod(true);
		    						if(Validation.basicValidation(request,param.getIsAppParamName()))
		    							param.setApp(true);
		    						if(param.isAdm()==false&&param.isMod()==false&&param.isApp()==false)
		    							errMsg.addError(param.getIsAdmParamName(),"User needs to have a default group.");
		    						else
		    						{
		    							if(true==this.fill(request, param, usr, add,errMsg))
		    							{
		    								usr.setAddress(add);
		    								param.setUsr(usr);
		    								request.setAttribute(UserAdminParam.getClassName(), param);
		    								return true;
		    							}
		    						}


		    					}
		    					else
		    						errMsg.addError(param.getNewPasswordParamName(),"Password is empty or is not compliant with rules.");
		    				}
		    				else
		    					errMsg.addError(param.getUserParamName(),"User name is not a valid email address.");
		    			}
		    			else
		    				errMsg.addError(param.getUserParamName(),"User name can not be empty.");
		    		}
		    		else if(param.isUpdate())
		    		{
		    			User usr=new User();
		    			Address add=new Address();
		    			//email is only required for admin to update other users' info
		    			if(Validation.basicValidation(request, param.getUserParamName()))
		    			{
		    				temp=request.getParameter(param.getUserParamName());
		    				if(Validation.isValidEmail(temp))
		    				{
		    					usr.setEmail(temp);
		    					usr.setUsername(temp);
		    				}
		    			}
		    			if(true==this.fill(request, param, usr, add,errMsg))
		    			{
		    				usr.setAddress(add);
		    				param.setUsr(usr);
		    				request.setAttribute(UserAdminParam.getClassName(), param);
		    				return true;
		    			}
		    			//else
		    				//	return false;
		    		}
		    		else if(param.isRemove())
		    		{
		    			User usr=new User();
		    			if(Validation.basicValidation(request, param.getUserParamName()))
		    			{
		    				temp=request.getParameter(param.getUserParamName());
		    				if(Validation.isValidEmail(temp))
		    				{
		    					usr.setEmail(temp);
		    					usr.setUsername(temp);
		    					param.setUsr(usr);
		    					request.setAttribute(UserAdminParam.getClassName(), param);
		    					return true;
		    				}
		    				else
		    				{
		    					errMsg.addError(param.getUserParamName(),"User name is not a valid email address.");
		    					//return false;
		    				}
		    			}
		    			else
		    				errMsg.addError(param.getUserParamName(),"User name can not be empty.");
		    		}
		    	}
		    }
		    else
		    	errMsg.addError(param.getActionParamName(),"The action specified is not defined.");
		}
		else
			errMsg.addError(param.getActionParamName(),"No action is given.");
		
		
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		return false;
	}
    private boolean fill(ServletRequest request,UserAdminParam param,User usr,Address add,ErrorMessage errMsg)
    {
    	String temp=null;
    	int count=errMsg.getErrorCount();
    	if(Validation.basicValidation(request, param.getFirstNameParamName()))
		{
			temp=request.getParameter(param.getFirstNameParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(param.getFirstNameParamName(),"First name can not have more than 255 characters.");
			else
				usr.setFirstName(temp);
		}
    	else
    	{
    		errMsg.addError(param.getFirstNameParamName(),"First name can not be empty.");
			//return false;
    	}
		if(Validation.basicValidation(request, param.getLastNameParamName()))
		{
			temp=request.getParameter(param.getLastNameParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(param.getLastNameParamName(),"Last name can not have more than 255 characters.");
			else
				usr.setLastName(temp);
		}
		else
    	{
    		errMsg.addError(param.getLastNameParamName(),"Last name can not be empty.");
			//return false;
    	}
		if(Validation.basicValidation(request, param.getStreet1ParamName()))
		{
			temp=request.getParameter(param.getStreet1ParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(param.getStreet1ParamName(),"Street name can not have more than 255 characters.");
			else
				add.setAddress1(temp);
		}
		if(Validation.basicValidation(request, param.getStreet2ParamName()))
		{
			temp=request.getParameter(param.getStreet2ParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(param.getStreet2ParamName(),"Street name can not have more than 255 characters.");
			else
				add.setAddress2(temp);
		}
		if(Validation.basicValidation(request, param.getCityParamName()))
		{
			temp=request.getParameter(param.getCityParamName()).trim();
			if(temp.length()>128)
			{
				errMsg.addError(param.getCityParamName(),"City name can not have more than 128 characters.");
			}
			else if(Validation.isValidCity(temp))
			{
			    add.setCity(temp);
			}
			else
			{
				errMsg.addError(param.getCityParamName(),"City name is not valid.");
				//return false;
			}
		}
		if(Validation.basicValidation(request, param.getCountryParamName()))
		{
			temp=request.getParameter(param.getCountryParamName()).trim();
			if(Validation.isValidNorthAmericanCountry(temp))
				add.setCountry(temp);
			else
			{
				errMsg.addError(param.getCountryParamName(),"Country name is not valid.");
				//return false;
			}
		}
		if(Validation.basicValidation(request, param.getStateParamName())||
				Validation.basicValidation(request, param.getProvinceParamName()))
		{
			if(add.getCountry()==null)
			{
				errMsg.addError(param.getCountryParamName(), "Country name is not specified.");
				//return false;
			}
			if(request.getParameter(param.getStateParamName())!=null&&Validation.isUS(add.getCountry()))
				temp=request.getParameter(param.getStateParamName()).trim();
			else if(request.getParameter(param.getProvinceParamName())!=null&&Validation.isCA(add.getCountry()))
				temp=request.getParameter(param.getProvinceParamName()).trim();
			if(Validation.isValidStateFIPS(temp, add.getCountry()))
			    add.setState(temp);
			else
			{
				errMsg.addError(param.getStateParamName(),"State or province name is not valid.");
				//return false;
			}
		}
		if(Validation.basicValidation(request, param.getPhoneParamName()))
		{
			temp=request.getParameter(param.getPhoneParamName()).trim();
			if(temp.length()>128)
			{
				errMsg.addError(param.getPhoneParamName(),"Phone number can not have more than 128 characters.");
			}
			else if((temp=Validation.isValidPhone(temp))!=null)
				add.setPhone(temp);
			else
			{
				errMsg.addError(param.getPhoneParamName(),"Phone number is not valid.");
				//return false;
			}
		}
		if(add.getCountry()!=null&&Validation.basicValidation(request, param.getZipcodeParamName()))
		{
			temp=request.getParameter(param.getZipcodeParamName()).trim();
			if(temp.length()>64)
			{
				errMsg.addError(param.getZipcodeParamName(),"Zipcode/Postal code can not have more than 64 characters.");
			}
			else if(Validation.isValidPostalCode(temp,add.getCountry()))
				add.setZipcode(temp);
			else
			{
				errMsg.addError(param.getZipcodeParamName(),"Zipcode is not valid.");
				//return false;
			}
		}
		if(Validation.basicValidation(request, param.getOrgnizationIdParamName()))
		{
			temp=request.getParameter(param.getOrgnizationIdParamName()).trim();
			/*int i=-1;
			try
			{
			    i=Integer.parseInt(temp);
			}
			catch(NumberFormatException ne)
			{
				errMsg.addError(param.getOrgnizationIdParamName(),"Organization name is not valid.");
				return false;
			}*/
			//if(i>0)
			param.setOrgId(temp);
			/*else
			{
				errMsg.addError(param.getOrgnizationIdParamName(),"Organization name is not valid.");
				return false;
			}*/
				
		}
		return count==errMsg.getErrorCount();
    }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
