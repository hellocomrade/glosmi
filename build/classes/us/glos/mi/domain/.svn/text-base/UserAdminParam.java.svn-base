package us.glos.mi.domain;

import org.glc.domain.User;
import org.glc.domain.IAjaxAware;

public class UserAdminParam implements IAjaxAware{
	
	public static class UserAdminAction
	{
		public static String AddNew="new";
		public static String Update="update";
		public static String Remove="remove";
		public static String ChangePassword="chgpasswd";
		public static String ChangeGroup="chggroup";
		public static String ChangeStatus="chgstatus";
	}
	private User usr;
	private String oldPassword;
	private String newPassword;
	private String orgKey;
	private String email;
	boolean isNew;
	boolean isUpdate;
	boolean isRemove;
	boolean isChangePassword;
	boolean isChangeGroup;
	boolean isChangeStatus;
	boolean isAjax;
	boolean isAdm;
	boolean isMod;
	boolean isApp;
	boolean isActive;
	public static String getClassName()
	{
		return UserAdminParam.class.getName();
	}
	
	public UserAdminParam()
	{
		this.isNew=false;
		this.isUpdate=false;
		this.isRemove=false;
		this.isChangePassword=false;
		this.isChangeGroup=false;
		this.isChangeStatus=false;
		this.usr=null;
		this.email=null;
		this.isAjax=false;
		this.isAdm=false;
		this.isMod=false;
		this.isApp=false;
		this.isActive=false;
	}

	public User getUsr() {
		return usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

	public String getAction() {
		if(this.isNew)
			return UserAdminAction.AddNew;
		else if(this.isUpdate)
			return UserAdminAction.Update;
		else if(this.isRemove)
			return UserAdminAction.Remove;
		else if(this.isChangePassword)
			return UserAdminAction.ChangePassword;
		else if(this.isChangeGroup)
			return UserAdminAction.ChangeGroup;
		else if(this.isChangeStatus)
			return UserAdminAction.ChangeStatus;
		else
			return null;
	}

	public void setAction(String action) {
		if(action!=null)
		{
			if(action.equalsIgnoreCase(UserAdminAction.AddNew))
				this.isNew=true;
			else if(action.equalsIgnoreCase(UserAdminAction.Update))
				this.isUpdate=true;
			else if(action.equalsIgnoreCase(UserAdminAction.Remove))
				this.isRemove=true;
			else if(action.equalsIgnoreCase(UserAdminAction.ChangePassword))
				this.isChangePassword=true;
			else if(action.equalsIgnoreCase(UserAdminAction.ChangeGroup))
				this.isChangeGroup=true;
			else if(action.equalsIgnoreCase(UserAdminAction.ChangeStatus))
				this.isChangeStatus=true;
		}
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getOrgId() {
		return this.orgKey;
	}

	public void setOrgId(String orgId) {
		this.orgKey = orgId;
	}

	public boolean isNew() {
		return isNew;
	}
	
	public boolean isUpdate() {
		return isUpdate;
	}

	public boolean isChangePassword()
	{
		return isChangePassword;
	}

	public boolean isRemove() {
		return isRemove;
	}
	
	public boolean isChangeGroup() {
		return isChangeGroup;
	}

	public boolean isChangeStatus() {
		return isChangeStatus;
	}

	public boolean isAjaxCall() {
		return isAjax;
	}

	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}
	
	public boolean isAdm() {
		return isAdm;
	}

	public void setAdm(boolean isAdm) {
		this.isAdm = isAdm;
	}

	public boolean isMod() {
		return isMod;
	}

	public void setMod(boolean isMod) {
		this.isMod = isMod;
	}

	public boolean isApp() {
		return isApp;
	}

	public void setApp(boolean isApp) {
		this.isApp = isApp;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getActionParamName()
	{
		return "action";
	}
	public String getUserParamName()
	{
		return "usr";
	}
	public String getOldPasswordParamName()
	{
		return "oldpasswd";
	}
	public String getNewPasswordParamName()
	{
		return "newpasswd";
	}
	public String getFirstNameParamName()
	{
		return "fname";
	}
	public String getLastNameParamName()
	{
		return "lname";
	}
	public String getStreet1ParamName()
	{
		return "street1";
	}
	public String getStreet2ParamName()
	{
		return "street2";
	}
	public String getCityParamName()
	{
		return "city";
	}
	public String getStateParamName()
	{
		return "state";
	}
	public String getProvinceParamName()
	{
		return "province";
	}
	public String getCountryParamName()
	{
		return "country";
	}
	public String getZipcodeParamName()
	{
		return "zip";
	}
	public String getPhoneParamName()
	{
		return "phone";
	}
	public String getOrgnizationIdParamName()
	{
		return "orgid";
	}
	public String getOrganizationNameParamName()
	{
		return "org_name";
	}
	public String getOrganizationDescriptionParamName()
	{
		return "org_desc";
	}
	public String getAjaxParamName()
	{
		return "isajax";
	}
	public String getIsAdmParamName()
	{
		return "isadm";
	}
	public String getIsModParamName()
	{
		return "ismod";
	}
	public String getIsAppParamName()
	{
		return "isapp";
	}
	public String getIsActivateParamName()
	{
		return "isactivate";
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
