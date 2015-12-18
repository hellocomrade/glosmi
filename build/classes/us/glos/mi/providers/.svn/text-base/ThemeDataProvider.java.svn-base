package us.glos.mi.providers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.glc.Logger;
import org.glc.cache.TemporalCacheBase;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.NameValuePair;
import us.glos.mi.dao.INameValueDAO;
public class ThemeDataProvider extends TemporalCacheBase<List<NameValuePair>> {
	INameValueDAO<NameValuePair> dao;
	private String CACHE_DURATION_NAME="CACHE_DURATION";
	private String THEME_DAO_NAME="THEME_DAO_NAME";
	private List<NameValuePair> readonlyList;
	public ThemeDataProvider()
	{
		this.last_update_time=0;
		String temp=null;
		try
		{
			temp=ConfigManager.getProviderParam(this.getClass().getName(),CACHE_DURATION_NAME);
			if(temp==null||temp.equals(""))
			{
				Logger.writeLog("CACHE_DURATION can not be null", LogLevel.SEVERE);
				throw new NullPointerException("CACHE_DURATION can not be null");
			}
			else
			{
				try
				{
					this.cache_expiry_after=Long.parseLong(temp);
					if(this.cache_expiry_after<300000)
						this.cache_expiry_after=300000;
				}
				catch(NumberFormatException nfe)
				{
					Logger.writeLog("Can not cast CACHE_DURATION to Long.",LogLevel.SEVERE);
					throw nfe;
				}
			}
			temp=ConfigManager.getProviderParam(this.getClass().getName(),THEME_DAO_NAME);
			if(temp!=null)
			{
				Class<INameValueDAO<NameValuePair>> iclass=(Class<INameValueDAO<NameValuePair>>)Class.forName(temp);
				this.dao=iclass.getConstructor().newInstance(new Object[0]);
			}
			else
			{
				Logger.writeLog("THEME_DAO_NAME can not be null", LogLevel.SEVERE);
				throw new NullPointerException("THEME_DAO_NAME can not be null");
			}
		}
		catch(ClassNotFoundException cfe)
		{
			Logger.writeLog(cfe.getMessage(), LogLevel.SEVERE);
		}
		catch(IllegalAccessException iae)
		{
			Logger.writeLog(iae.getMessage(), LogLevel.SEVERE);
		}
		catch(InvocationTargetException ite)
		{
			Logger.writeLog(ite.getMessage(), LogLevel.SEVERE);
		}
		catch(InstantiationException ie)
		{
			Logger.writeLog(ie.getMessage(), LogLevel.SEVERE);
		}
		catch(NoSuchMethodException nse)
		{
			Logger.writeLog(nse.getMessage(), LogLevel.SEVERE);
		}
	}
	@Override
	protected List<NameValuePair> getCache()
	{
		return this.readonlyList;
	}
	@Override
	protected synchronized void run() {
		// TODO Auto-generated method stub
		if(dao!=null)
		{
			try
			{
				ArrayList<NameValuePair> result=dao.getAllNameValuePairs();

				if(result!=null&&result.isEmpty()==false)
				{
					this.cachedObj=null;
					this.cachedObj=result;
					this.readonlyList=null;
					this.readonlyList=java.util.Collections.unmodifiableList(this.cachedObj);
				}
			}
			catch(Exception e){
				Logger.writeLog("ThemeDataProvider:run: Failed to update cached data",LogLevel.SEVERE);
				Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
			}
		}
	}
}
