package us.glos.mi.domain;

import org.glc.domain.IAjaxAware;
import us.glos.mi.domain.Contact;


public class ContactParam implements IAjaxAware {

	
	public static class ContactAction
	{
		public static String AddNew="new";
		public static String Update="update";
		public static String Remove="remove";
	}
	
	public static String getClassName()
	{
		return ContactParam.class.getName();
	}
	
	private static String CNT_NEW="0";
	private static String CNT_UPDATE="1";
	private static String CNT_EXIST="2";
	
	public static boolean isNewContact(String val)
	{
		return CNT_NEW.equals(val);
	}
	public static boolean isUpdateContact(String val)
	{
		return CNT_UPDATE.equals(val);
	}
	public static boolean isExistContact(String val)
	{
		return CNT_EXIST.equals(val);
	}
	private boolean isAjax;
	private Contact contact=null;
	private boolean isNew;
	private boolean isUpdate;
	private boolean isRemove;
	
	long timestamp;
	public ContactParam()
	{
		this.isAjax=false;
		this.isNew=false;
		this.isUpdate=false;
		this.isRemove=false;
		
		this.timestamp=0;
	}
	public String getAction() {
		if(this.isNew)
			return ContactAction.AddNew;
		else if(this.isUpdate)
			return ContactAction.Update;
		else if(this.isRemove)
			return ContactAction.Remove;
		else
			return null;
	}
	public void setAction(String action) {
		if(action!=null)
		{
			if(action.equalsIgnoreCase(ContactAction.AddNew))
				this.isNew=true;
			else if(action.equalsIgnoreCase(ContactAction.Update))
				this.isUpdate=true;
			else if(action.equalsIgnoreCase(ContactAction.Remove))
				this.isRemove=true;
		}
	}
	@Override
	public boolean isAjaxCall() {
		// TODO Auto-generated method stub
		return this.isAjax;
	}
	public void setAjax(boolean aj)
	{
		this.isAjax=aj;
	}
	public boolean isAjax() {
		return isAjax;
	}
	
	public boolean isNew() {
		return isNew;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public boolean isRemove() {
		return isRemove;
	}
	public Contact getContact() {
		return contact;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public String getContactIdParamName()
	{
		return "cnt_cnt_id[]";
	}
	public String getFirstNameParamName()
	{
		return "cnt_firstname[]";
	}
	public String getLastNameParamName()
	{
		return "cnt_lastname[]";
	}
	
	public String getAddress1ParamName()
	{
		return "cnt_add1[]";
	}
	public String getAddress2ParamName()
	{
		return "cnt_add2[]";
	}
	public String getCityParamName()
	{
		return "cnt_city[]";
	}
	public String getCountryParamName()
	{
		return "cnt_country[]";
	}
	public String getStateParamName()
	{
		return "cnt_state[]";
	}
	public String getProvinceParamName()
	{
		return "cnt_province[]";
	}
	public String getZipParamName()
	{
		return "cnt_zip[]";
	}
	public String getPhoneParamName()
	{
		return "cnt_phone[]";
	}
	public String getEmailParamName()
	{
		return "cnt_email[]";
	}
	public String getOrgIdParamName()
	{
		return "cnt_orgid[]";
	}
	public String getWebSiteParamName()
	{
		return "cnt_website[]";
	}
	public String getAjaxParamName()
	{
		return "isajaxcnt";
	}
	public String getDeveloperParamName()
	{
		return "cnt_devel_";
	}
	public String getDistributorParamName()
	{
		return "cnt_distr_";
	}
	public String getContactParamName()
	{
		return "cnt_conta_";
	}
	public String getContactFlag()
	{
		return "cnt_flag[]";
	}
	public String getNewContactVal()
	{
		return CNT_NEW;
	}
	public String getUpdateContactVal()
	{
		return CNT_UPDATE;
	}
	public String getExistContactVal()
	{
		return CNT_EXIST;
	}
}
