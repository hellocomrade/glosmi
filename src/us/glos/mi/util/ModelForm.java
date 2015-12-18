package us.glos.mi.util;

import javax.servlet.ServletRequest;

import org.glc.domain.Orgnization;
import org.glc.domain.Address;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Validation;

import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ContactParam;
import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;

public class ModelForm {
	
	public static Contact GetModelContact(ServletRequest request,ErrorMessage errMsg,boolean isDraft)
	{
		if(request==null||errMsg==null)return null;
		Contact usr=null;
		Address add=null;
		Orgnization org=null;
		ModAdminParam modparam=new ModAdminParam();
		if(!isDraft)//if it's not for draft, do the prerequisite check first
			isDraft=Validation.basicValidation(request, modparam.getModFirstNameParamName())
			&&Validation.basicValidation(request, modparam.getModLastNameParamName())
			&&Validation.basicValidation(request, modparam.getModEmailParamName());
		
		if(isDraft)//doesn't mean the code below only for draft
		{
			usr=new Contact();
			if(Validation.basicValidation(request, modparam.getModCntIdParamName()))
			{
				int i=0;
				try
				{
					i=Integer.parseInt(request.getParameter(modparam.getModCntIdParamName()));
				}
				catch(NumberFormatException ne)
				{
					errMsg.addError(modparam.getModCntIdParamName(),"Contact Id is not valid.");
				}
				if(i>0)
					usr.setId(i);
			}
	    	if(Validation.basicValidation(request, modparam.getModFirstNameParamName()))
			{
				usr.setFirstName(request.getParameter(modparam.getModFirstNameParamName()).trim());
			}
	    	else
	    	{
	    		errMsg.addError(modparam.getModFirstNameParamName(),"Contact's First name can not be empty.");
				
	    	}
			if(Validation.basicValidation(request, modparam.getModLastNameParamName()))
			{
				usr.setLastName(request.getParameter(modparam.getModLastNameParamName()).trim());
			}
			else
	    	{
	    		errMsg.addError(modparam.getModLastNameParamName(),"Contact's Last name can not be empty.");
				
	    	}
			if(Validation.basicValidation(request, modparam.getModEmailParamName())&&
					(Validation.isValidEmail(request.getParameter(modparam.getModEmailParamName()).trim())
					||request.getParameter(modparam.getModEmailParamName()).trim().equalsIgnoreCase("n/a"))
				)
			{
				usr.setEmail(request.getParameter(modparam.getModEmailParamName()).trim());
			}
			else
	    	{
				usr.setEmail(request.getParameter(modparam.getModEmailParamName()));
				errMsg.addError(modparam.getModEmailParamName(),"Contact's Email can not be empty or email is not valid.");
				
	    	}
			if(Validation.basicValidation(request, modparam.getModAddress1ParamName()))
			{
				if(add==null)add=new Address();
				add.setAddress1(request.getParameter(modparam.getModAddress1ParamName()).trim());
			}
			if(Validation.basicValidation(request, modparam.getModAddress2ParamName()))
			{
				if(add==null)add=new Address();
				add.setAddress2(request.getParameter(modparam.getModAddress2ParamName()).trim());
			}
			if(Validation.basicValidation(request, modparam.getModCityParamName()))
			{
				if(Validation.isValidCity(request.getParameter(modparam.getModCityParamName()).trim()))
				{
					if(add==null)add=new Address();
					add.setCity(request.getParameter(modparam.getModCityParamName()).trim());
				}
				else
				{
					add.setCity(request.getParameter(modparam.getModCityParamName()));
					errMsg.addError(modparam.getModCityParamName(),"Contact's city is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, modparam.getModCountryParamName()))
			{
				if(Validation.isValidNorthAmericanCountry(request.getParameter(modparam.getModCountryParamName()).trim()))
				{
					if(add==null)add=new Address();
					add.setCountry(request.getParameter(modparam.getModCountryParamName()).trim());
				}
				else
				{
					errMsg.addError(modparam.getModCountryParamName(),"Contact's country name is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, modparam.getModStateParamName())||
					Validation.basicValidation(request, modparam.getModProvinceParamName()))
			{
				if(add!=null&&add.getCountry()==null)
				{
					errMsg.addError(modparam.getModCountryParamName(), "Contact's country name is not specified.");
					
				}
				if(add!=null)
				{
					String temp=null;
					if(request.getParameter(modparam.getModStateParamName())!=null&&Validation.isUS(add.getCountry()))
						temp=request.getParameter(modparam.getModStateParamName()).trim();
					else if(request.getParameter(modparam.getModProvinceParamName())!=null&&Validation.isCA(add.getCountry()))
						temp=request.getParameter(modparam.getModProvinceParamName()).trim();
					if(Validation.isValidStateFIPS(temp, add.getCountry()))
						add.setState(temp);
					else
					{
						errMsg.addError(modparam.getModStateParamName(),"State or province name is not valid.");
						
					}
				}
			}
			if(Validation.basicValidation(request, modparam.getModPhoneParamName()))
			{
				String temp=request.getParameter(modparam.getModPhoneParamName()).trim();
				if((temp=Validation.isValidPhone(temp))!=null)
				{
					if(add==null)add=new Address();
					add.setPhone(temp);
				}
				else
				{
					add.setPhone(temp);
					errMsg.addError(modparam.getModPhoneParamName(),"Phone number is not valid.");
					
				}
			}
			if(add!=null&&add.getCountry()!=null&&Validation.basicValidation(request, modparam.getModZipParamName()))
			{
				String temp=request.getParameter(modparam.getModZipParamName()).trim();
				if(Validation.isValidPostalCode(temp,add.getCountry()))
					add.setZipcode(temp);
				else
				{
					add.setZipcode(temp);
					errMsg.addError(modparam.getModZipParamName(),"Zipcode is not valid.");
					
				}
			}
			if(Validation.basicValidation(request, modparam.getModOrgIdParamName()))
			{
				org=new Orgnization();
				String temp=request.getParameter(modparam.getModOrgIdParamName()).trim();
				org.setName(temp);
				org.setAbbrev(temp);
			}
			if(Validation.basicValidation(request, modparam.getModContactFlagParamName()))
			{
				String temp=request.getParameter(modparam.getModContactFlagParamName()).trim();
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
			errMsg.addError(modparam.getModFirstNameParamName(), "Contact's First Name can not be empty.");
			errMsg.addError(modparam.getModLastNameParamName(), "Contact's Last Name can not be empty.");
			errMsg.addError(modparam.getModEmailParamName(), "Contact's Email can not be empty.");
			usr=new Contact();
			usr.setFirstName(request.getParameter(modparam.getModFirstNameParamName()));
			usr.setLastName(request.getParameter(modparam.getModLastNameParamName()));
			usr.setEmail(request.getParameter(modparam.getModEmailParamName()));
		}
		return usr;
	}
	public static boolean FillRequired(ServletRequest request,ModelInfo mod,ModAdminParam modparam,ErrorMessage errMsg)
	{
		String[] strs=null;
		String temp=null;
		int[] itemp=null;
		boolean result=true;
		if(request==null||mod==null||modparam==null||errMsg==null)return false;
		int ecount=errMsg.getErrorCount();
		if(Validation.basicValidation(request, modparam.getModNameParamName()))
		{
			temp=request.getParameter(modparam.getModNameParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(modparam.getModNameParamName(),"Model Name can not have more than 255 characters.");
			else
				mod.setName(temp);
		}
		else
			errMsg.addError(modparam.getModNameParamName(),"Model Name can not be empty.");
		if(Validation.basicValidation(request, modparam.getModVerNoParamName()))
		{
			temp=request.getParameter(modparam.getModVerNoParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(modparam.getModVerNoParamName(),"Model Version can not have more than 255 characters.");
			else
				mod.setVersionNo(temp);
		}
		else
			errMsg.addError(modparam.getModVerNoParamName(),"Model Version can not be empty.");
		if(Validation.basicGroupValidation(request,modparam.getModCategoryParamName()))
		{
			strs=request.getParameterValues(modparam.getModCategoryParamName());
			itemp=new int[strs.length];
			
			for(int i=0;i<strs.length;++i)
			{
				try
				{
					itemp[i]=Integer.parseInt(strs[i]);
				}
				catch(NumberFormatException ne)
				{
					errMsg.addError(modparam.getModCategoryParamName(),"Model Category Id is not valid.");
					result=false;
					break;
				}
			}
			if(result)
			{
				mod.setCategoryIds(itemp);
			}
		}
		else
			errMsg.addError(modparam.getModCategoryParamName(),"At least one Model Category must be selected.");
		if(Validation.basicGroupValidation(request,modparam.getModThemeParamName()))
		{
			strs=request.getParameterValues(modparam.getModThemeParamName());
			itemp=new int[strs.length];
			for(int i=0;i<strs.length;++i)
			{
				try
				{
					itemp[i]=Integer.parseInt(strs[i]);
				}
				catch(NumberFormatException ne)
				{
					errMsg.addError(modparam.getModThemeParamName(),"Model Theme Id is not valid.");
					result=false;
					break;
				}
			}
			if(result)
			{
				mod.setThemeIds(itemp);
			}
		}
		else
			errMsg.addError(modparam.getModThemeParamName(),"At least one Model Theme must be selected.");
		if(Validation.basicValidation(request, modparam.getModAvailParamName()))
		{
			try
			{
				mod.setAvailableId(Integer.parseInt(request.getParameter(modparam.getModAvailParamName())));
			}
			catch(NumberFormatException ne)
    		{
    			errMsg.addError(modparam.getModAvailParamName(),"Model Applicability Id is not valid.");
    			
    		}
		}
		else
			errMsg.addError(modparam.getModAvailParamName(),"One Model Applicability must be selected.");
		if(Validation.basicValidation(request, modparam.getModDescParamName()))
		{
			mod.setDescription(request.getParameter(modparam.getModDescParamName()).trim());
		}
		else
			errMsg.addError(modparam.getModDescParamName(),"Model Primary Purpose can not be empty.");
		if(Validation.basicValidation(request, modparam.getModAttriParamName()))
		{
			mod.setAttribute(request.getParameter(modparam.getModAttriParamName().trim()));
		}
		else
			errMsg.addError(modparam.getModAttriParamName(),"Model Characteristics can not be empty.");
		if(Validation.basicValidation(request, modparam.getModDataReqParamName()))
		{
		    mod.setDataRequirement(request.getParameter(modparam.getModDataReqParamName()));
			
		}
		else
			errMsg.addError(modparam.getModDataReqParamName(),"Model Data Requirements can not be empty.");
		ContactParam cntparam=new ContactParam();
		String[] tmpArr1=null;
		String[] tmpArr2=null;
		if(Validation.basicGroupValidationAllowEmpty(request, cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName())
				&&Validation.basicGroupValidationAllowEmpty(request, cntparam.getDeveloperParamName()+cntparam.getLastNameParamName()))
		{
			tmpArr1=request.getParameterValues(cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName());
			tmpArr2=request.getParameterValues(cntparam.getDeveloperParamName()+cntparam.getLastNameParamName());
			if(tmpArr1.length==tmpArr2.length&&tmpArr1.length>0)
			{
				int len=tmpArr1.length;
				Contact cnt=null;
				for(int i=0;i<len;++i)
				{
					cnt=ContactForm.GetDeveloper(request, i,errMsg);
					if(cnt!=null)
					{
						/*if(cnt.isUpdate()&&cnt.getId()<=0)
						{
							errMsg.addError(cntparam.getDeveloperParamName(),"Contact flag is set to update, but this person's id is empty.");
							
						}
						if(cnt.isExist()&&cnt.getId()<=0)
						{
							errMsg.addError(cntparam.getDeveloperParamName(),"Contact flag is set to exist, but this person's id is empty.");
							
						}*/
						cnt.setDeveloper(true);
						mod.setDeveloper(cnt);
					}
					cnt=null;
				}
			}
		}
		else
		{
			errMsg.addError(cntparam.getDeveloperParamName()+cntparam.getFirstNameParamName(), "Developer's First Name can not be empty.");
			errMsg.addError(cntparam.getDeveloperParamName()+cntparam.getLastNameParamName(), "Developer's Last Name can not be empty.");
		}
		if(Validation.basicGroupValidationAllowEmpty(request, cntparam.getDistributorParamName()+cntparam.getFirstNameParamName())
				&&Validation.basicGroupValidationAllowEmpty(request, cntparam.getDistributorParamName()+cntparam.getLastNameParamName()))
		{
			tmpArr1=request.getParameterValues(cntparam.getDistributorParamName()+cntparam.getFirstNameParamName());
			tmpArr2=request.getParameterValues(cntparam.getDistributorParamName()+cntparam.getLastNameParamName());
			if(tmpArr1.length==tmpArr2.length&&tmpArr1.length>0)
			{
				int len=tmpArr1.length;
				Contact cnt=null;
				for(int i=0;i<len;++i)
				{
					cnt=ContactForm.GetDistributor(request, i,errMsg);
					if(cnt!=null)
					{
						/*if(cnt.isUpdate()&&cnt.getId()<=0)
						{
							errMsg.addError(cntparam.getDistributorParamName(),"Contact flag is set to update, but this person's id is empty.");
							
						}
						if(cnt.isExist()&&cnt.getId()<=0)
						{
							errMsg.addError(cntparam.getDistributorParamName(),"Contact flag is set to exist, but this person's id is empty.");
							
						}*/
						cnt.setDistributor(true);
						mod.setDistributor(cnt);
					}
					cnt=null;
				}
			}
		}
		else
		{
			errMsg.addError(cntparam.getDistributorParamName()+cntparam.getFirstNameParamName(), "Distributor's First Name can not be empty.");
			errMsg.addError(cntparam.getDistributorParamName()+cntparam.getLastNameParamName(), "Distributor's Last Name can not be empty.");
		}
		Contact cnt=null;
		cnt=ModelForm.GetModelContact(request, errMsg,true);
		if(cnt!=null)
		{
			/*if(cnt.isUpdate()&&cnt.getId()<=0)
			{
				errMsg.addError(modparam.getModCntIdParamName(),"Contact flag is set to update, but this person's id is empty.");
				
			}
			if(cnt.isExist()&&cnt.getId()<=0)
			{
				errMsg.addError(modparam.getModCntIdParamName(),"Contact flag is set to exist, but this person's id is empty.");
				
			}*/
			cnt.setContact(true);
			mod.setContact(cnt);
		}
		if(Validation.basicValidation(request, modparam.getModEasyUseParamName()))
		{
			mod.setSkillLevel(request.getParameter(modparam.getModEasyUseParamName()));
		}
		else
		{
			errMsg.addError(modparam.getModEasyUseParamName(),"Model Skill Level can not be empty.");
		}
		
		if(Validation.basicValidation(request, modparam.getModAbbrevParamName()))
		{
			temp=request.getParameter(modparam.getModAbbrevParamName()).trim();
			if(temp.length()>255)
				errMsg.addError(modparam.getModAbbrevParamName(),"Model Abbreviation can not have more than 255 characters.");
			else
				mod.setAbbreviation(temp);
		}
		//url is not required, but it's filled, need to check if url is valid
		if(Validation.basicValidation(request, modparam.getModUrlParamName()))
		{
			temp=request.getParameter(modparam.getModUrlParamName()).trim();
			if(Validation.isValidUrl(temp))
				mod.setUrl(temp);
			else
			{
				mod.setUrl(temp);
				errMsg.addError(modparam.getModUrlParamName(), "Url is not valid.");
			}
		}
		return ecount==errMsg.getErrorCount();
	}
	public static boolean FillNoneRequired(ServletRequest request,ModelInfo mod,ModAdminParam modparam,ErrorMessage errMsg)
	{
		if(request==null||mod==null||modparam==null||errMsg==null)return false;
		int ecount=errMsg.getErrorCount();
		
		
		if(Validation.basicValidation(request, modparam.getModStrengthParamName()))
		{
			mod.setStrength(request.getParameter(modparam.getModStrengthParamName()).trim());
		}
		
		if(Validation.basicValidation(request, modparam.getModNoteParamName()))
		{
			mod.setNote(request.getParameter(modparam.getModNoteParamName()).trim());
		}
		
		
		return ecount==errMsg.getErrorCount();
	}
}
