package us.glos.mi;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.glc.StartPoint;
//import us.glos.mi.providers.MutableReferenceDataProvider;

public class MIStartPoint extends StartPoint {

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//MutableReferenceDataProvider p=new MutableReferenceDataProvider();
		//config.getServletContext().setAttribute(MutableReferenceDataProvider.class.getName(), p);
	}
	
}
