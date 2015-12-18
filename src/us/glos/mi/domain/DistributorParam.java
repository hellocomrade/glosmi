package us.glos.mi.domain;

public class DistributorParam extends ContactParamBase {
	@Override
	public String getEmailParamName()
	{
		return "distributor_email";
	}
	@Override
	public String getFirstNameParamName()
	{
		return "distributor_fname";
	}
	@Override
	public String getLastNameParamName()
	{
		return "distributor_lname";
	}
	@Override
	public String getStreet1ParamName()
	{
		return "distributor_street1";
	}
	@Override
	public String getStreet2ParamName()
	{
		return "distributor_street2";
	}
	@Override
	public String getCityParamName()
	{
		return "distributor_city";
	}
	@Override
	public String getStateParamName()
	{
		return "distributor_state";
	}
	@Override
	public String getProvinceParamName()
	{
		return "distributor_province";
	}
	@Override
	public String getCountryParamName()
	{
		return "distributor_country";
	}
	@Override
	public String getZipcodeParamName()
	{
		return "distributor_zip";
	}
	@Override
	public String getPhoneParamName()
	{
		return "distributor_phone";
	}
	@Override
	public String getOrgnizationIdParamName()
	{
		return "distributor_orgid";
	}
}
