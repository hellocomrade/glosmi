package us.glos.mi;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.IValidParam;
import org.glc.Logger;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;



public class MIUserInputValidator implements IValidParam{

	private java.util.HashMap<String, IValidParam> validators=new java.util.HashMap<String, IValidParam>();
	private IValidParam getValidator(String path)
	{
		if(this.validators.get(path)==null)
		{
		    String temp=ConfigManager.getProviderParam(this.getClass().getName(),path);
		    if(temp!=null)
			{
				try
				{
				    Class<IValidParam> iclass=(Class<IValidParam>)Class.forName(temp);
				    this.validators.put(path, iclass.getConstructor().newInstance(new Object[0]));
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
		}
		return this.validators.get(path);
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public boolean IsParamValid(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		HttpServletRequest req=(HttpServletRequest)request;
		
		String sc=req.getServletPath();
		IValidParam validator=this.getValidator(sc);
		if(validator!=null)
			return validator.IsParamValid(request, response);
		else
		    return true;
	}
	

}
