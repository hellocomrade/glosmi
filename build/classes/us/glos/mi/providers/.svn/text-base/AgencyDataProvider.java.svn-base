package us.glos.mi.providers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.glc.Logger;
import org.glc.domain.Orgnization;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;
import org.glc.cache.TemporalCacheBase;

import us.glos.mi.dao.IAgencyDAO;


public class AgencyDataProvider extends TemporalCacheBase<List<String>>{

	private String CACHE_DURATION_NAME="CACHE_DURATION";
	private String AGENCY_DAO_NAME="AGENCY_DAO_NAME";
	private IAgencyDAO dao=null;
		
	private List<String> readonlyList;
	//private volatile boolean isPending=false;
	
	public AgencyDataProvider()
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
			temp=ConfigManager.getProviderParam(this.getClass().getName(),AGENCY_DAO_NAME);
			if(temp!=null)
			{
				Class<IAgencyDAO> iclass=(Class<IAgencyDAO>)Class.forName(temp);
				this.dao=iclass.getConstructor().newInstance(new Object[0]);
			}
			else
			{
				Logger.writeLog("AGENCY_DAO_NAME can not be null", LogLevel.SEVERE);
				throw new NullPointerException("AGENCY_DAO_NAME can not be null");
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
	
	protected List<String> getCache()
	{
		return this.readonlyList;
	}	
	
	protected synchronized void run() {
		// TODO Auto-generated method stub
		if(dao!=null)
		{
			try
			{
				ArrayList<Orgnization> result=dao.getAllAgencies();

				if(result!=null&&result.isEmpty()==false)
				{
					ArrayList<String> l=new ArrayList<String>();
					for(Orgnization org:result)
					{
						if(org!=null)
						{
							if(org.getName()!=null)
								l.add(org.getName());
							if(org.getAbbrev()!=null)
								l.add(org.getAbbrev());
						}
					}

					if(!l.isEmpty())
						java.util.Collections.sort(l);
					this.cachedObj=null;
					this.cachedObj=l;
					this.readonlyList=null;
					this.readonlyList=java.util.Collections.unmodifiableList(this.cachedObj);
					
					
					

				}
			}
			catch(Exception e){
				Logger.writeLog("AgencyDataProvider:run: Failed to update cached data",LogLevel.SEVERE);
				Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
			}
			
			
		}
	}
	

}

