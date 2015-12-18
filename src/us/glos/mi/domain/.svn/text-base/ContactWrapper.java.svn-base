package us.glos.mi.domain;

import org.glc.IJSONEnabled;
import org.glc.utils.FIPSCodes;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.Contact;
import us.glos.mi.domain.UserAdminParam;

public class ContactWrapper implements IJSONEnabled{

	private Contact cnt;
	public ContactWrapper(Contact cnt)
	{
		this.cnt=cnt;
	}
	
	@Override
	public String ToJSON() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		String temp=null;
		if(this.cnt!=null)
		{
			UserAdminParam cp=new UserAdminParam();
			sb.append('{');
			sb.append(String.format("\"%s\":",cp.getUserParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(cnt.getEmail())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",cp.getFirstNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(cnt.getFirstName())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",cp.getLastNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(cnt.getLastName())));
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getOrgnizationIdParamName()));
			sb.append('"');
			temp=cnt.getOrgnization().getName();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getStreet1ParamName()));
			sb.append('"');
			temp=cnt.getAddress().getAddress1();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getStreet2ParamName()));
			sb.append('"');
			temp=cnt.getAddress().getAddress2();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getCityParamName()));
			sb.append('"');
			temp=cnt.getAddress().getCity();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getCountryParamName()));
			sb.append('"');
			temp=cnt.getAddress().getCountry();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			if(temp!=null)
			{
				sb.append(String.format("\"%s\":",cp.getStateParamName()));
				sb.append('"');
				String temp1=cnt.getAddress().getState();
				if(temp1!=null)
				{
					if(temp.equalsIgnoreCase(FIPSCodes.CountryCode.US))
						temp1=FIPSCodes.US_State_FIPS.get(temp1);
					else if(temp.equalsIgnoreCase(FIPSCodes.CountryCode.CA))
						temp1=FIPSCodes.CA_Province_FIPS.get(temp1);
					else 
						temp1=null;
					if(temp1!=null)
						sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp1)));
					else
						sb.append("");
				}
				else
					sb.append("");
				sb.append('"');
				sb.append(',');
			}
			
			sb.append(String.format("\"%s\":",cp.getZipcodeParamName()));
			sb.append('"');
			temp=cnt.getAddress().getZipcode();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			sb.append(String.format("\"%s\":",cp.getPhoneParamName()));
			sb.append('"');
			temp=cnt.getAddress().getPhone();
			if(temp!=null)
				sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(temp)));
			else
				sb.append("");
			sb.append('"');
			sb.append(',');
			
			if(sb.charAt(sb.length()-1)==',')
				sb.deleteCharAt(sb.length()-1);
			sb.append('}');
		}
		return sb.toString();
	}

}
