package us.glos.mi.domain;

public class SearchResultParam {
	public static String getSearchWordsParam()
	{
		return "key";
	}
	public static String getFuzzyMatchNextParam()
	{
		return "fn";
	}
	public static String getFuzzyMatchPrevParam()
	{
		return "fp";
	}
	public static String getFuzzyMatchParam()
	{
		return "f";
	}
	public static String getSearchResultParam()
	{
		return "MODEL_SEARCH_RESULT";
	}
	public static String getSearchOffsetNextParam()
	{
		return "MODEL_SEARCH_OFFSET_NEXT";
	}
	public static String getSearchOffsetPrevParam()
	{
		return "MODEL_SEARCH_OFFSET_PREV";
	}
	public static String getSearchOffsetRistParam()
	{
		return "of";
	}
	public static String getSearchRecordPerPageParam()
	{
		return "MODEL_SEARCH_LIMIT";
	}
	public static String getSearchNextURLParam()
	{
		return "MODEL_SEARCH_URL_NEXT";
	}
	public static String getSearchPrevURLParam()
	{
		return "MODEL_SEARCH_URL_PREV";
	}
	public static String getSearchCountParam()
	{
		return "MODEL_SEARCH_COUNT";
	}
	public static String getFilterCategoryParam()
	{
		return "model_filter_cat[]";
	}
	public static String getFilterThemeParam()
	{
		return "model_filter_the[]";
	}
	public static String getFilterAvailParam()
	{
		return "model_filter_avail";
	}
}
