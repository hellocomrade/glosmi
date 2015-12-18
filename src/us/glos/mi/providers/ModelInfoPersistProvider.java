package us.glos.mi.providers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.ModelInfo;
import us.glos.mi.dao.serialize.IPersistent;
import us.glos.mi.dao.serialize.ModelInfoPersistContext;

public class ModelInfoPersistProvider implements IPersistent<ModelInfoPersistContext> {

	private IPersistent<ModelInfoPersistContext> dao=null;
	@SuppressWarnings("unused")
	private ModelInfoPersistProvider(){}
	public ModelInfoPersistProvider(IPersistent<ModelInfoPersistContext> p)
	{
		this.dao=p;
	}
	public static ModelInfo getModel(byte[] bytes)
	{
		ModelInfo mod=null;
		if(bytes!=null)
		{
			ByteArrayInputStream instream=new ByteArrayInputStream(bytes);
    		ObjectInputStream iobj=null;
    		try {
				iobj=new ObjectInputStream(instream);
				mod=(ModelInfo)iobj.readObject();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
				    if(iobj!=null)
					    iobj.close();
				    if(instream!=null)
				    	instream.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return mod;
	}
	@Override
	public byte[] read(ModelInfoPersistContext context) {
		// TODO Auto-generated method stub
		
		return this.dao.read(context);
	}
    public ModelInfo readModel(ModelInfoPersistContext context)
    {
    	ModelInfo mod=null;
    	byte[] bytes=this.dao.read(context);
    	if(bytes!=null)
    	{
    		ByteArrayInputStream instream=new ByteArrayInputStream(bytes);
    		ObjectInputStream iobj=null;
    		try {
				iobj=new ObjectInputStream(instream);
				mod=(ModelInfo)iobj.readObject();
				if(mod!=null)
				{
					mod.setSerialId(context.draftId);
					mod.setLastUpdateDate(context.lastUpdateDate);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
				    if(iobj!=null)
					    iobj.close();
				    if(instream!=null)
				    	instream.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
				}
			}
    	}
    	return mod;
    }
	@Override
	public boolean writeTo(ModelInfoPersistContext context,Object obj) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(this.dao!=null&&context!=null&&obj!=null&&obj instanceof Serializable&&obj instanceof ModelInfo)
		{
			ByteArrayOutputStream ostream=new ByteArrayOutputStream();
			ObjectOutputStream oobj=null;
			try {
				oobj=new ObjectOutputStream(ostream);
				oobj.writeObject(obj);
				oobj.flush();
				byte[] bytes=ostream.toByteArray();
				ostream.close();
				oobj.close();
				result=this.dao.writeTo(context, bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
				    if(oobj!=null)
					    oobj.close();
				    if(ostream!=null)
				    	ostream.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return result;
	}
	@Override
	public ArrayList<byte[]> readMore(ModelInfoPersistContext context) {
		// TODO Auto-generated method stub
		return this.dao.readMore(context);
	}
	public ArrayList<ModelInfo> readModels(ModelInfoPersistContext context) {
		ArrayList<ModelInfo> list=null;
		ModelInfo mod=null;
		ByteArrayInputStream instream=null;
		ObjectInputStream iobj=null;
    	ArrayList<byte[]> morebytes=this.dao.readMore(context);
    	if(morebytes!=null)
    	{
    		list=new ArrayList<ModelInfo>();
    		for(byte[] bytes:morebytes)
    		{
    		    instream=new ByteArrayInputStream(bytes);
    		    try {
    		    	iobj=new ObjectInputStream(instream);
    		    	mod=(ModelInfo)iobj.readObject();
    		    	if(mod!=null)
    		    	{
    		    		mod.setSerialId(context.draftId);
    		    		mod.setLastUpdateDate(context.lastUpdateDate);
    		    		list.add(mod);
    		    	}
    		    	
    		    } catch (IOException e) {
    		    	// TODO Auto-generated catch block
    		    	Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
    		    } catch (ClassNotFoundException e) {
    		    	// TODO Auto-generated catch block
    		    	Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
    		    }
    		    finally
    		    {
    		    	try
    				{
    				    if(iobj!=null)
    					    iobj.close();
    				    if(instream!=null)
    				    	instream.close();
    				    iobj=null;
    				    instream=null;
    				}
    				catch (IOException e) {
    					// TODO Auto-generated catch block
    					Logger.writeLog("ModelInfoPersistProvider:"+e.getMessage(), LogLevel.SEVERE);
    				}
    		    }
    		}
    	}
    	return list;
	}
	@Override
	public boolean remove(ModelInfoPersistContext key) {
		// TODO Auto-generated method stub
		return this.dao.remove(key);
	}

}
