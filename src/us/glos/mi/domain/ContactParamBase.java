package us.glos.mi.domain;

import org.glc.domain.IAjaxAware;

public class ContactParamBase implements IAjaxAware {

	private boolean isAjax;
	private Contact contact=null;
	public ContactParamBase()
	{
		this.isAjax=false;
	}
	@Override
	public boolean isAjaxCall() {
		// TODO Auto-generated method stub
		return this.isAjax;
	}
	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getEmailParamName()
	{
		return "contact_email[]";
	}
	public String getFirstNameParamName()
	{
		return "contact_fname[]";
	}
	public String getLastNameParamName()
	{
		return "contact_lname[]";
	}
	public String getStreet1ParamName()
	{
		return "contact_street1[]";
	}
	public String getStreet2ParamName()
	{
		return "contact_street2[]";
	}
	public String getCityParamName()
	{
		return "contact_city[]";
	}
	public String getStateParamName()
	{
		return "contact_state[]";
	}
	public String getProvinceParamName()
	{
		return "contact_province[]";
	}
	public String getCountryParamName()
	{
		return "contact_country[]";
	}
	public String getZipcodeParamName()
	{
		return "contact_zip[]";
	}
	public String getPhoneParamName()
	{
		return "contact_phone[]";
	}
	public String getOrgnizationIdParamName()
	{
		return "contact_orgid[]";
	}
	
}
