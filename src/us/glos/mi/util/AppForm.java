package us.glos.mi.util;

import javax.servlet.ServletRequest;

import org.glc.domain.Address;
import org.glc.domain.ErrorMessage;
import org.glc.domain.Orgnization;
import org.glc.utils.Validation;

import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ContactParam;

public class AppForm {
	public static Contact GetAppContact(ServletRequest request,ErrorMessage errMsg,boolean isDraft)
	{
		if(request==null||errMsg==null)return null;
		Contact usr=null;
		Address add=null;
		Orgnization org=null;
		AppAdminParam apparam=new AppAdminParam();
		if(!isDraft)//if it's not for draft, do the prerequisite check first
			isDraft=Validation.basicValidation(request, apparam.getAppFirstNameParamName())
			&&Validation.basicValidation(request, apparam.getAppLastNameParamName())
			&&Validation.basicValidation(request, apparam.getAppEmailParamName());
		
		if(isDraft)//doesn't mean the code below only for draft
		{
			usr=new Contact();
			if(Validation.basicValidation(request, apparam.getAppCntIdParamName()))
			{
				int i=0;
				try
				{
					i=Integer.parseInt(request.getParameter(apparam.getAppCntIdParamName()));
				}
				catch(NumberFormatException ne)
				{
					errMsg.addError(apparam.getAppCntIdParamName(),"Contact Id is not valid.");
				}
				if(i>0)
					usr.setId(i);
			}
	    	if(Validation.basicValidation(request, apparam.getAppFirstNameParamName()))
			{
				usr.setFirstName(request.getParameter(apparam.getAppFirstNameParamName()).trim());
			}
	    	else
	    	{
	    		errMsg.addError(apparam.getAppFirstNameParamName(),"Contact's First name can not be empty.");
				
	    	}
			if(Validation.basicValidation(request, apparam.getAppLastNameParamName()))
			{
				usr.setLastName(request.getParameter(apparam.getAppLastNameParamName()).trim());
			}
			else
	    	{
	    		errMsg.addError(apparam.getAppLastNameParamName(),"Contact's Last name can not be empty.");
				
	    	}
			if(Validation.basicValidation(request, apparam.getAppEmailParamName())&&
					(Validation.isValidEmail(request.getParameter(apparam.getAppEmailParamName()).trim())
							||request.getParameter(apparam.getAppEmailParamName()).trim().equalsIgnoreCase("n/a")))
			{
				usr.setEmail(request.getParameter(apparam.getAppEmailParamName()).trim());
			}
			else
	    	{
				usr.setEmail(request.getParameter(apparam.getAppEmailParamName()));
				errMsg.addError(apparam.getAppEmailParamName(),"Contact's Email can not be empty or email is not valid.");
				
	    	}
			if(Validation.basicValidation(request, apparam.getAppAddress1ParamName()))
			{
				if(add==null)add=new Address();
				add.setAddress1(request.getParameter(apparam.getAppAddress1ParamName()).trim());
			}
			if(Validation.basicValidation(request, apparam.getAppAddress2ParamName()))
			{
				if(add==null)add=new Address();
				add.setAddress2(request.getParameter(apparam.getAppAddress2ParamName()).trim());
			}
			if(Validation.basicValidation(request, apparam.getAppCityParamName()))
			{
				if(Validation.isValidCity(request.getParameter(apparam.getAppCityParamName()).trim()))
				{
					if(add==null)add=new Address();
					add.setCity(request.getParameter(apparam.getAppCityParamName()).trim());
				}
				else
				{
					add.setCity(request.getParameter(apparam.getAppCityParamName()));
					errMsg.addError(apparam.getAppCityParamName(),"Contact's city is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, apparam.getAppCountryParamName()))
			{
				if(Validation.isValidNorthAmericanCountry(request.getParameter(apparam.getAppCountryParamName()).trim()))
				{
					if(add==null)add=new Address();
					add.setCountry(request.getParameter(apparam.getAppCountryParamName()).trim());
				}
				else
				{
					errMsg.addError(apparam.getAppCountryParamName(),"Contact's country name is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, apparam.getAppStateParamName())||
					Validation.basicValidation(request, apparam.getAppProvinceParamName()))
			{
				if(add!=null&&add.getCountry()==null)
				{
					errMsg.addError(apparam.getAppCountryParamName(), "Contact's country name is not specified.");
					
				}
				if(add!=null)
				{
					String temp=null;
					if(request.getParameter(apparam.getAppStateParamName())!=null&&Validation.isUS(add.getCountry()))
						temp=request.getParameter(apparam.getAppStateParamName()).trim();
					else if(request.getParameter(apparam.getAppProvinceParamName())!=null&&Validation.isCA(add.getCountry()))
						temp=request.getParameter(apparam.getAppProvinceParamName()).trim();
					if(Validation.isValidStateFIPS(temp, add.getCountry()))
						add.setState(temp);
					else
					{
						errMsg.addError(apparam.getAppStateParamName(),"State or province name is not valid.");
						
					}
				}
			}
			if(Validation.basicValidation(request, apparam.getAppPhoneParamName()))
			{
				String temp=request.getParameter(apparam.getAppPhoneParamName()).trim();
				if((temp=Validation.isValidPhone(temp))!=null)
				{
					if(add==null)add=new Address();
					add.setPhone(temp);
				}
				else
				{
					add.setPhone(temp);
					errMsg.addError(apparam.getAppPhoneParamName(),"Phone number is not valid.");
					
				}
			}
			if(add!=null&&add.getCountry()!=null&&Validation.basicValidation(request, apparam.getAppZipParamName()))
			{
				String temp=request.getParameter(apparam.getAppZipParamName()).trim();
				if(Validation.isValidPostalCode(temp,add.getCountry()))
					add.setZipcode(temp);
				else
				{
					add.setZipcode(temp);
					errMsg.addError(apparam.getAppZipParamName(),"Zipcode is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, apparam.getAppOrgIdParamName()))
			{
				org=new Orgnization();
				String temp=request.getParameter(apparam.getAppOrgIdParamName()).trim();
				org.setName(temp);
				org.setAbbrev(temp);
			}
			if(Validation.basicValidation(request, apparam.getAppContactFlagParamName()))
			{
				String temp=request.getParameter(apparam.getAppContactFlagParamName()).trim();
				if(ContactParam.isExistContact(temp))
					usr.setExist(true);
				else if(ContactParam.isUpdateContact(temp))
					usr.setUpdate(true);
				else if(ContactParam.isNewContact(temp))
					usr.setNew(true);
			}
			if(org!=null)usr.setOrgnization(org);
			if(add!=null)usr.setAddress(add);
			
		}
		else//draft won't care 
		{
			errMsg.addError(apparam.getAppFirstNameParamName(), "Contact's First Name can not be empty.");
			errMsg.addError(apparam.getAppLastNameParamName(), "Contact's Last Name can not be empty.");
			errMsg.addError(apparam.getAppEmailParamName(), "Contact's Email can not be empty.");
			usr=new Contact();
			usr.setFirstName(request.getParameter(apparam.getAppFirstNameParamName()));
			usr.setLastName(request.getParameter(apparam.getAppLastNameParamName()));
			usr.setEmail(request.getParameter(apparam.getAppEmailParamName()));
		}
		return usr;
	}
	public static boolean FillRequired(ServletRequest request,AppInfo app,AppAdminParam apparam,ErrorMessage errMsg)
	{
		if(request==null||app==null||apparam==null||errMsg==null)return false;
		int ecount=errMsg.getErrorCount();
		String temp=null;
		if(Validation.basicValidation(request, apparam.getAppNameParamName()))
		{
			temp=request.getParameter(apparam.getAppNameParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(apparam.getAppNameParamName(),"Application Name can not have more than 255 characters.");
			else
				app.setName(temp);
		}
		else
			errMsg.addError(apparam.getAppNameParamName(),"Application Name can not be empty.");
		if(Validation.basicValidation(request, apparam.getAppLocationParamName()))
		{
			temp=request.getParameter(apparam.getAppLocationParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(apparam.getAppLocationParamName(),"Application Location can not have more than 255 characters.");
			else
				app.setLocation(temp);
		}
		else
			errMsg.addError(apparam.getAppLocationParamName(),"Application Location can not be empty.");
		if(Validation.basicValidation(request, apparam.getModelNameParamName()))
		{
			temp=request.getParameter(apparam.getModelNameParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(apparam.getModelNameParamName(),"Model Name can not have more than 255 characters.");
			else
				app.setModelName(temp);
		}
		else
			errMsg.addError(apparam.getModelNameParamName(),"Model Name can not be empty.");
		if(Validation.basicValidation(request, apparam.getAppPurposeParamName()))
		{
			app.setAppPurpose(request.getParameter(apparam.getAppPurposeParamName()).trim());
		}
		else
			errMsg.addError(apparam.getAppPurposeParamName(),"Application purpose can not be empty.");
		if(Validation.basicValidation(request, apparam.getAppDescParamName()))
		{
			app.setAppDescription(request.getParameter(apparam.getAppDescParamName()).trim());
		}
		else
			errMsg.addError(apparam.getAppDescParamName(),"Application description can not be empty.");
		
		Contact cnt=null;
		cnt=AppForm.GetAppContact(request, errMsg,true);
		if(cnt!=null)
		{
			cnt.setContact(true);
			app.setContact(cnt);
		}
		//url is not required, but it's filled, need to check if url is valid
		if(Validation.basicValidation(request, apparam.getAppUrlParamName()))
		{
			temp=request.getParameter(apparam.getAppUrlParamName()).trim();
			app.setUrl(temp);
			if(false==Validation.isValidUrl(temp))
				errMsg.addError(apparam.getAppUrlParamName(),"Url is not valid.");
		}
		
		return ecount==errMsg.getErrorCount();
	}
	public static boolean FillNoneRequired(ServletRequest request,AppInfo app,AppAdminParam apparam,ErrorMessage errMsg)
	{
		if(request==null||app==null||apparam==null||errMsg==null)return false;
		int ecount=errMsg.getErrorCount();
		if(Validation.basicValidation(request, apparam.getAppNoteParamName()))
		{
			app.setAppNote(request.getParameter(apparam.getAppNoteParamName()).trim());
		}
		
		return ecount==errMsg.getErrorCount();
	}
}
