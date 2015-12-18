package org.glc.xmlconfig;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;



public class ConfigManager {
	
	private static AppConfig config=null;
	
	public static synchronized void init(ServletContext context,boolean isReset)
	{
		if(context==null)return;
		if(isReset)
		{
			config.Clear();
		}
		try
		{
			//URL url=context.getResource("/WEB-INF/app-config.xml");
			String path=context.getRealPath("/WEB-INF/app-config.xml");
			if(path==null)
				return;
			Digester digester=new Digester();
		    digester.setValidating(false);
		    digester.addObjectCreate("app-config", AppConfig.class);
		    digester.addObjectCreate("app-config/datasource", SimpleDataSource.class);
		    digester.addSetProperties("app-config/datasource", "type", "tvalue");
		    digester.addSetProperties("app-config/datasource", "name", "name");
		    digester.addBeanPropertySetter("app-config/datasource", "value");
		    digester.addSetNext("app-config/datasource", "addDataSource");
		    
		    digester.addObjectCreate("app-config/action-mapping/action", Action.class);
		    digester.addSetProperties("app-config/action-mapping/action", "path", "path");
		    digester.addSetProperties("app-config/action-mapping/action", "type", "type");
		    digester.addSetProperties("app-config/action-mapping/action", "scope", "range");
		    digester.addObjectCreate("app-config/action-mapping/action/forward", Forward.class);
		    digester.addSetProperties("app-config/action-mapping/action/forward", "name","name");
		    digester.addSetProperties("app-config/action-mapping/action/forward", "path","path");
		    digester.addSetProperties("app-config/action-mapping/action/forward", "redirect","isRedirect");
		    digester.addSetNext("app-config/action-mapping/action/forward", "setForward");
		    digester.addSetNext("app-config/action-mapping/action","addAction");
		    digester.addObjectCreate("app-config/appsetting", AppSetting.class);
		    digester.addSetProperties("app-config/appsetting", new String[]{"key","value"}, new String[]{"key","value"});
		    digester.addSetNext("app-config/appsetting", "addAppSetting");
		    digester.addBeanPropertySetter("app-config/loglevel", "level");
		    digester.addBeanPropertySetter("app-config/checkAlternativeTableFor", "table");
		    digester.addObjectCreate("app-config/unit-mapping/unit", UnitMap.class);
		    digester.addSetProperties("app-config/unit-mapping/unit", "abbre", "abbre");
		    digester.addSetProperties("app-config/unit-mapping/unit", "metric", "metric");
		    digester.addSetProperties("app-config/unit-mapping/unit", "english", "english");
		    digester.addSetNext("app-config/unit-mapping/unit", "addUnitMap");
		    digester.addObjectCreate("app-config/providers/provider", Provider.class);
		    digester.addSetProperties("app-config/providers/provider","name","name");
		    digester.addSetProperties("app-config/providers/provider","type","type");
		    digester.addObjectCreate("app-config/providers/provider/param",AppSetting.class);
		    digester.addSetProperties("app-config/providers/provider/param", new String[]{"key","value"}, new String[]{"key","value"});
		    digester.addSetNext("app-config/providers/provider/param","addParam");
		    //digester.addSetProperties("app-config/provider",new String[]{"type","dao"},new String[]{"type","dao"});
		    digester.addSetNext("app-config/providers/provider","addProvider");
		    File input=new File(path);
		    if(input.exists())
		        config=(AppConfig)digester.parse(input);
		    
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	public static Action.Scope getActionScope(String type)
	{
		Action act=(config==null?null:config.actions.get(type));
		if(act!=null)
			return act.getScope();
		else
			return null;
	}
    public static String[] getActionTypes()
    {
    	String[] result=null;
    	if(config!=null)
    	{
    		result=new String[config.actions.size()];
    		config.actions.keySet().toArray(result);
    	}
    	return result;
    }
    public static String getActionForward(String type,String result)
    {
    	if(config==null||!config.actions.containsKey(type))return null;
    	return config.actions.get(type).getForward(result);
    	
    }
    public static boolean isForwardRedirect(String type,String result)
    {
    	if(config==null||!config.actions.containsKey(type))return false;
    	return config.actions.get(type).isRedirect(result);
    }
    public static String getActionPath(String type)
    {
    	if(config==null||!config.actions.containsKey(type))return null;
    	return config.actions.get(type).getPath();
    }
    
    public static String getUnitMetric(String abbre)
    {
    	if(config==null||!config.unitmapping.containsKey(abbre))return null;
    	return config.unitmapping.get(abbre).getMetric();
    }
    public static String getUnitEnglish(String abbre)
    {
    	if(config==null||!config.unitmapping.containsKey(abbre))return null;
    	return config.unitmapping.get(abbre).getEnglish();
    }
    public static String getSimpleDataSourceValue(String name)
    {
    	if(config==null||!config.dss.containsKey(name))return null;
    	return config.dss.get(name).getValue();
    }
    public static SimpleDataSource.DSType getSimpleDataSourceType(String name)
    {
    	if(config==null||!config.dss.containsKey(name))return null;
    	return config.dss.get(name).getType();
    }
    public static LogLevel getLogLevel()
    {
    	if(config==null)return null;
    	return config.getLogLevel();
    }
    public static String getAppSetting(String key)
    {
    	if(config!=null)
    	{
    	    AppSetting as=config.appsettings.get(key);
    	    if(as!=null)
    	    {
    	    	return as.getValue();
    	    }
    	}
    	return null;
    }
    public static String getProviderParam(String type,String key)
    {
    	if(config!=null&&config.providers.containsKey(type))
    	{
    		return config.providers.get(type).getValueByKey(key);
    	}
    	return null;
    }
    public static boolean isCheckAlternativeTable(Long l)
    {
    	if(config!=null)
    		return config.isCheckAlternativeTable(l);
    	else
    		return false;
    }
}
