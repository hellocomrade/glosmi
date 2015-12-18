package us.glos.mi.providers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.glc.Logger;
import org.glc.domain.Orgnization;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.dao.IAgencyDAO;


public class _AgencyDataProvider implements IMutableDataProvider<String> {

	private String CACHE_DURATION_NAME="CACHE_DURATION";
	private String AGENCY_DAO_NAME="AGENCY_DAO_NAME";
	private IAgencyDAO dao=null;
	private long cache_length=1800000;
	private long time;
	private List<String> list;
	boolean isPending=false;
	
	public _AgencyDataProvider()
	{
		this.time=0;
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
					this.cache_length=Long.parseLong(temp);
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
	
	@Override
	public synchronized List<String> getCachedObject() {
		// TODO Auto-generated method stub
		
		//if(this.isPending)//update is going
		while(this.isPending)
		{
			try
			{
				//this.wait();
				this.wait(500);//bogus
			}
			catch(InterruptedException ie)
			{

			}
			
			//return java.util.Collections.unmodifiableList(this.list);//this.getCachedObject();
		}
		//else
		{
			long t=java.util.Calendar.getInstance().getTimeInMillis();
			if(this.list!=null&&t-this.time<=this.cache_length)
			{
				return java.util.Collections.unmodifiableList(this.list);
				
			}
			else if(this.list==null)//start up
				this.getBackgroundData();
			else if(this.list!=null&&
					t-this.time>this.cache_length)//expired
				this.getBackgroundData();
			return this.getCachedObject();
		}
			
	}

	@Override
	public void run() {
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

					synchronized(this)
					{
						if(this.list!=null)
						{
							//prevent memory leak???
							this.list=null;
						}
						this.list=l;
						this.time=Calendar.getInstance().getTimeInMillis();
						this.isPending=false;
						this.notifyAll();
					}

				}
			}
			catch(Exception e){
				synchronized(this)
				{
					this.isPending=false;
					this.notifyAll();
				}
			}
			
		}
	}
	private void getBackgroundData()
	{
		isPending=true;
		new Thread(this).start();
	}

}
