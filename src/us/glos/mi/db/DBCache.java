package us.glos.mi.db;

import java.util.List;

import us.glos.mi.domain.NameValuePair;
import us.glos.mi.providers.AgencyDataProvider;
import us.glos.mi.providers.NameValueCacheProvider;

public class DBCache {
	private static AgencyDataProvider agency;
	//private static ThemeDataProvider themes;
	private static NameValueCacheProvider themes;
	private static String THEME_CACHE_PROVIDER="ThemeCache";
	private static NameValueCacheProvider contactCategory;
	private static String CONTACT_CAT_CACHE_PROVIDER="ContactCategoryCache";
	private static NameValueCacheProvider modAvailibilty;
	private static String MOD_AVAIL_CACHE_PROVIDER="ModelAvailCache";
	private static NameValueCacheProvider modCategory;
	private static String MOD_CAT_CACHE_PROVIDER="ModelCategoryCache";
	private static NameValueCacheProvider modStatus;
	private static String MOD_STATUS_CACHE_PROVIDER="ModelStatusCache";
	
	static
	{
		agency=new AgencyDataProvider();
		themes=new NameValueCacheProvider(THEME_CACHE_PROVIDER);
		contactCategory=new NameValueCacheProvider(CONTACT_CAT_CACHE_PROVIDER);
		modAvailibilty=new NameValueCacheProvider(MOD_AVAIL_CACHE_PROVIDER);
		modCategory=new NameValueCacheProvider(MOD_CAT_CACHE_PROVIDER);
		modStatus=new NameValueCacheProvider(MOD_STATUS_CACHE_PROVIDER);
	}
	
	public static List<String> getAgencyNames()
	{
		return agency.getCachedEntry();
	}
	public static List<NameValuePair> getThemes()
	{
		return themes.getCachedEntry();
	}
	public static List<NameValuePair> getContactCategories()
	{
		return contactCategory.getCachedEntry();
	}
	public static List<NameValuePair> getModelAvailibilites()
	{
		return modAvailibilty.getCachedEntry();
	}
	public static List<NameValuePair> getModelCategories()
	{
		return modCategory.getCachedEntry();
	}
	public static List<NameValuePair> getModelStatus()
	{
		return modStatus.getCachedEntry();
	}
}
