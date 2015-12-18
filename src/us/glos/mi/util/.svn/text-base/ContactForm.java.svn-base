package us.glos.mi.util;

import javax.servlet.ServletRequest;

import org.glc.domain.Address;
import org.glc.domain.ErrorMessage;
import org.glc.domain.Orgnization;
import org.glc.utils.Validation;

import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ContactParam;

public class ContactForm {
	private static Contact _GetContactFromArray(ServletRequest request,int index,String prefix,ErrorMessage errMsg)
	{
		Contact cnt=null;
		String[] sArr=null;
		ContactParam param=null;
		String temp=null;
		if(request!=null&&index>=0)
		{
			if(prefix==null)prefix="";
			cnt=new Contact();
			param=new ContactParam();
			sArr=request.getParameterValues(prefix+param.getContactIdParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index].equals("")==false)
			{
				int i=0;
				try
				{
					i=Integer.parseInt(sArr[index]);
					
				}
				catch(NumberFormatException nfe)
				{
					i=0;
				}
				if(i>0)
					cnt.setId(i);
			}
			sArr=request.getParameterValues(prefix+param.getFirstNameParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				temp=sArr[index].trim();
				if(temp.length()>255)
					errMsg.addError(prefix+param.getFirstNameParamName(), "First Name can not have more than 255 characters.");
				else
					cnt.setFirstName(temp);
			}
			else
				errMsg.addError(prefix+param.getFirstNameParamName(), "First Name can not be empty.");
			sArr=request.getParameterValues(prefix+param.getLastNameParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				temp=sArr[index].trim();
				if(temp.length()>255)
					errMsg.addError(prefix+param.getLastNameParamName(), "Last Name can not have more than 255 characters.");
				else
					cnt.setLastName(temp);
			}
			else
				errMsg.addError(prefix+param.getLastNameParamName(), "Last Name can not be empty.");
			sArr=request.getParameterValues(prefix+param.getEmailParamName());
			if(sArr!=null&&index<sArr.length&&
					(Validation.isValidEmail(sArr[index].trim())||
					(sArr[index]!=null&&sArr[index].trim().equalsIgnoreCase("n/a")))
					)
			{
				temp=sArr[index].trim();
				if(temp.length()>128)
					errMsg.addError(prefix+param.getEmailParamName(), "Email can not have more than 128 characters.");
				else
					cnt.setEmail(temp);
			}
			else
			{
				cnt.setEmail(sArr[index]);
				errMsg.addError(prefix+param.getEmailParamName(), "Email can not be empty or email is not valid.");
			}
			sArr=request.getParameterValues(prefix+param.getOrgIdParamName());
			Orgnization org=null;
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				org=new Orgnization();
				temp=sArr[index].trim();
				if(temp.length()<256)
				{
					org.setName(temp);
					org.setAbbrev(temp);
				}
				cnt.setOrgnization(org);
				
			}
			Address add=null;
			sArr=request.getParameterValues(prefix+param.getAddress1ParamName());
			
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				if(add==null)
					add=new Address();
				temp=sArr[index].trim();
				if(temp.length()<256)
					add.setAddress1(temp);
			}
			sArr=request.getParameterValues(prefix+param.getAddress2ParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				if(add==null)
					add=new Address();
				temp=sArr[index].trim();
				if(temp.length()<256)
					add.setAddress2(temp);
			}
			sArr=request.getParameterValues(prefix+param.getCityParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals(""))
			{
				if(add==null)
					add=new Address();
				if(Validation.isValidCity(sArr[index].trim()))
				{
					temp=sArr[index].trim();
					if(temp.length()>128)
						errMsg.addError(prefix+param.getCityParamName(), "City name can not have more than 128 characters.");
					else
						add.setCity(temp);
				}
				else
				{
					add.setCity(sArr[index]);
					errMsg.addError(prefix+param.getCityParamName(), "City name is not valid.");
				}
			}
			sArr=request.getParameterValues(prefix+param.getCountryParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&sArr[index].equals("")==false)
			{
				if(add==null)
					add=new Address();
				if(Validation.isValidNorthAmericanCountry(sArr[index].trim()))
					add.setCountry(sArr[index].trim());
				else
					errMsg.addError(prefix+param.getCountryParamName(), "Country name is not valid.");
			}
			sArr=request.getParameterValues(prefix+param.getStateParamName());
			String[] sArr1=request.getParameterValues(prefix+param.getProvinceParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&sArr[index].equals("")==false&&add!=null&&add.getCountry()!=null&&Validation.isUS(add.getCountry()))
			{
				if(Validation.isValidStateFIPS(sArr[index].trim(), add.getCountry()))
					add.setState(sArr[index].trim());
				else
					errMsg.addError(prefix+param.getStateParamName(), "State's FIPS is not valid.");
			}
			else if(sArr1!=null&&index<sArr1.length&&sArr1[index]!=null&&sArr1[index].equals("")==false&&add!=null&&add.getCountry()!=null&&Validation.isCA(add.getCountry()))
			{
				if(Validation.isValidStateFIPS(sArr1[index].trim(), add.getCountry()))
					add.setState(sArr1[index].trim());
				else
					errMsg.addError(prefix+param.getProvinceParamName(), "Province's FIPS is not valid.");
			}
			sArr=request.getParameterValues(prefix+param.getZipParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&!sArr[index].equals("")&&add!=null&&add.getCountry()!=null)
			{
				if(Validation.isValidPostalCode(sArr[index].trim(), add.getCountry()))
				{
					temp=sArr[index].trim();
					if(temp.length()>64)
						errMsg.addError(prefix+param.getZipParamName(), "Zip/Postal code can not have more than 64 characters.");
					else
						add.setZipcode(temp);
				}
				else
				{
					add.setZipcode(sArr[index]);
					errMsg.addError(prefix+param.getZipParamName(), "Zip/Postal code is not valid.");
				}
			}
			
			sArr=request.getParameterValues(prefix+param.getPhoneParamName());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&sArr[index].equals("")==false)
			{
				if(add==null)
					add=new Address();
				temp=Validation.isValidPhone(sArr[index].trim());
				if(temp!=null)
				{
					if(temp.length()>128)
						errMsg.addError(prefix+param.getPhoneParamName(), "Phone number can not have more than 128 characters.");
					else
						add.setPhone(temp);
				}
				else
				{
					add.setPhone(sArr[index]);
					errMsg.addError(prefix+param.getPhoneParamName(), "Phone number is not valid.");
				}
			}
			if(add!=null)
				cnt.setAddress(add);
			sArr=request.getParameterValues(prefix+param.getContactFlag());
			if(sArr!=null&&index<sArr.length&&sArr[index]!=null&&sArr[index].equals("")==false)
			{
				if(ContactParam.isExistContact(sArr[index].trim()))
					cnt.setExist(true);
				else if(ContactParam.isUpdateContact(sArr[index].trim()))
					cnt.setUpdate(true);
				else if(ContactParam.isNewContact(sArr[index].trim()))
					cnt.setNew(true);
			}
		}
		return cnt;
	}
	public static Contact GetDeveloper(ServletRequest request,int index,ErrorMessage errMsg)
	{
		ContactParam param=new ContactParam();
		return _GetContactFromArray(request,index,param.getDeveloperParamName(),errMsg);
		
	}
	public static Contact GetDistributor(ServletRequest request,int index,ErrorMessage errMsg)
	{
		ContactParam param=new ContactParam();
		return _GetContactFromArray(request,index,param.getDistributorParamName(),errMsg);
	}
}
