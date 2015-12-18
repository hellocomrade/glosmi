package us.glos.mi.domain;

import org.glc.IJSONEnabled;
import org.glc.domain.User;
import org.glc.utils.HTMLUtilities;
import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.UserAdminParam;

public class UserWrapper implements IJSONEnabled {

	private User usr=null;
	public UserWrapper(User u)
	{
		this.usr=u;
	}
	@Override
	public String ToJSON() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		if(this.usr!=null)
		{
			UserAdminParam up=new UserAdminParam();
			sb.append('{');
			sb.append(String.format("\"%s\":",up.getUserParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getEmail())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getFirstNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getFirstName())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getLastNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getLastName())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getIsAdmParamName()));
			sb.append(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())?"true":"false");
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getIsModParamName()));
			sb.append(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())?"true":"false");
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getIsAppParamName()));
			sb.append(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())?"true":"false");
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getStreet1ParamName()));
			if(usr.getAddress().getAddress1()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getAddress1())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getStreet2ParamName()));
			if(usr.getAddress().getAddress2()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getAddress2())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getCityParamName()));
			if(usr.getAddress().getCity()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getCity())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getStateParamName()));
			if(usr.getAddress().getState()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getState())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getCountryParamName()));
			if(usr.getAddress().getCountry()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getCountry())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getZipcodeParamName()));
			if(usr.getAddress().getZipcode()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getZipcode())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getPhoneParamName()));
			if(usr.getAddress().getPhone()==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getAddress().getPhone())));
			    sb.append('"');
			}
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getIsActivateParamName()));
			sb.append(usr.isActivate()?"true":"false");
			sb.append(',');
			sb.append(String.format("\"%s\":",up.getOrgnizationIdParamName()));
			if(usr==null)
				sb.append("null");
			else
			{
			    sb.append('"');
			    sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(usr.getOrgnization().getName())));
			    sb.append('"');
			}
			sb.append('}');
		}
		return sb.toString();
	}

}
