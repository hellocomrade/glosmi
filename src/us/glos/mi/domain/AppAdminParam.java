package us.glos.mi.domain;

import org.glc.domain.IAjaxAware;

public class AppAdminParam extends DocManagementParam implements IAjaxAware{

	public static String getClassName()
	{
		return AppAdminParam.class.getName();
	}
	
	
	private boolean isAjaxCall;
	private AppInfo application=null;
	public AppAdminParam()
	{
		
		this.isAjaxCall=false;
	}
	
	@Override
	public boolean isAjaxCall() {
		// TODO Auto-generated method stub
		return isAjaxCall;
	}
	public void setAjaxCall(boolean isAjax) {
		this.isAjaxCall = isAjax;
	}

	public AppInfo getApplication() {
		return application;
	}

	public void setApplication(AppInfo application) {
		this.application = application;
	}
	
	public String getActionParamName()
	{
		return "app_action";
	}
	public String getIsNewParamName()
	{
		return "isnewapp";
	}
	public String getIsUpdateParamName()
	{
		return "isupdateapp";
	}
	public String getIsSaveParamName()
	{
		return "issaveapp";
	}
	public String getIsListParamName()
	{
		return "islistapp";
	}
	public String getIsRemoveParamName()
	{
		return "isremoveapp";
	}
	public String getIsSubmitParamName()
	{
		return "issubmitapp";
	}
	public String getIsApproveParamName()
	{
		return "isapproveapp";
	}
	public String getChangeOwnerParamName()
	{
		return "ischangeowner_app";
	}
	public String getChangeStatusParamName()
	{
		return "ischangestatus_app";
	}
	public String getAjaxParamName()
	{
		return "isajaxapp";
	}
	public String getModIdParamName()
	{
		return "modid_app";
	}
	public String getModDraftIdParamName()
	{
		return "moddraftid_app";
	}
	public String getAppDraftIdParamName()
	{
		return "appdraftid";
	}
	public String getAppIdParamName()
	{
		return "appid";
	}
	public String getAppNameParamName()
	{
		return "appname";
	}
	public String getAppLocationParamName()
	{
		return "apploc";
	}
	public String getModelNameParamName()
	{
		return "modname_app";
	}
	public String getAppPurposeParamName()
	{
		return "apppurpose";
	}
	public String getAppDescParamName()
	{
		return "appdesc";
	}
	public String getAppNoteParamName()
	{
		return "appnote";
	}
	public String getAppCntIdParamName()
	{
		return "app_cnt_id";
	}
	public String getAppFirstNameParamName()
	{
		return "app_cnt_firstname";
	}
	public String getAppLastNameParamName()
	{
		return "app_cnt_lastname";
	}
	
	public String getAppAddress1ParamName()
	{
		return "app_cnt_add1";
	}
	public String getAppAddress2ParamName()
	{
		return "app_cnt_add2";
	}
	public String getAppCityParamName()
	{
		return "app_cnt_city";
	}
	public String getAppCountryParamName()
	{
		return "app_cnt_country";
	}
	public String getAppStateParamName()
	{
		return "app_cnt_state";
	}
	public String getAppProvinceParamName()
	{
		return "app_cnt_province";
	}
	public String getAppZipParamName()
	{
		return "app_cnt_zip";
	}
	public String getAppPhoneParamName()
	{
		return "app_cnt_phone";
	}
	public String getAppEmailParamName()
	{
		return "app_cnt_email";
	}
	public String getAppOrgIdParamName()
	{
		return "app_cnt_orgid";
	}
	public String getAppStatusParamName()
	{
		return "app_status_val";
	}
	public String getAppContactFlagParamName()
	{
		return "app_cnt_flag";
	}
	public String getAppApprovalFlagParam()
	{
		return "app_app_flag";
	}
	public String getAppUpdateReasonParam()
	{
		return "app_update_rea";
	}
	public String getAppUrlParamName()
	{
		return "app_url";
	}
}
